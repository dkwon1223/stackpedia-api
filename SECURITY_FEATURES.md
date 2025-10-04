# Security Features Implementation

## Overview
This document describes the security features implemented to improve the production-readiness of the StackPedia API.

## Features Implemented

### 1. Password Strength Validation

**Location:** `src/main/java/com/dkwondev/stackpedia_v2_api/validation/`

**Requirements:**
- Minimum 8 characters
- At least one uppercase letter (A-Z)
- At least one lowercase letter (a-z)
- At least one digit (0-9)
- At least one special character (!@#$%^&*()_+-=[]{};\:'"|,.<>/?)

**Usage:**
The `@ValidPassword` annotation is applied to the password field in the User entity. Validation occurs automatically during user signup.

**Example:**
```java
@ValidPassword
private String password;
```

### 2. Email Verification

**Location:**
- Entity: `src/main/java/com/dkwondev/stackpedia_v2_api/model/entity/EmailVerificationToken.java`
- Service: `src/main/java/com/dkwondev/stackpedia_v2_api/service/EmailService.java`
- Migration: `src/main/resources/db/migration/V1.08__add_email_verification.sql`

**Flow:**
1. User signs up with valid credentials
2. Account is created but `enabled` is set to `false`
3. Verification email is sent with a unique token (valid for 24 hours)
4. User clicks verification link: `/api/user/verify?token={token}`
5. Account is enabled and user can log in

**Database Changes:**
- Added `email_verified` column to users table
- Added `enabled` column to users table
- Created `email_verification_tokens` table

**Configuration:**
Development (uses local mail server like MailHog):
```properties
spring.mail.host=localhost
spring.mail.port=1025
```

Production (requires environment variables):
```properties
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

### 3. Rate Limiting

**Location:** `src/main/java/com/dkwondev/stackpedia_v2_api/filter/RateLimitFilter.java`

**Implementation:**
- Uses Bucket4j library for token bucket algorithm
- Applied to `/api/user/login` and `/api/user/signup` endpoints
- Limit: 5 requests per minute per IP address
- Returns HTTP 429 (Too Many Requests) when limit exceeded

**Key Features:**
- IP-based tracking (handles X-Forwarded-For header for proxies)
- In-memory cache using ConcurrentHashMap
- Protects against brute force attacks and account enumeration

## Testing

### Testing Password Validation
Try to signup with weak passwords:
```bash
# Should fail - no uppercase
curl -X POST http://localhost:8080/api/user/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"weakpass123!"}'

# Should succeed
curl -X POST http://localhost:8080/api/user/signup \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"StrongPass123!"}'
```

### Testing Email Verification
1. Sign up a new user
2. Check email server for verification link
3. Click link or make GET request to `/api/user/verify?token={token}`
4. Try to login (should work after verification)

For development, use [MailHog](https://github.com/mailhog/MailHog):
```bash
# Install and run MailHog
brew install mailhog
mailhog
# Access UI at http://localhost:8025
```

### Testing Rate Limiting
```bash
# Run this 6 times quickly to trigger rate limiting
for i in {1..6}; do
  curl -X POST http://localhost:8080/api/user/login \
    -H "Content-Type: application/json" \
    -d '{"username":"test","password":"test"}'
  echo ""
done
# 6th request should return 429 Too Many Requests
```

## Production Setup

### Environment Variables
Set these variables in your production environment:

```bash
# Email Configuration
MAIL_HOST=smtp.gmail.com  # or your SMTP server
MAIL_PORT=587
MAIL_USERNAME=your-email@example.com
MAIL_PASSWORD=your-app-password
```

### Email Provider Setup
For production, consider using:
- **Gmail**: Enable 2FA and use App Password
- **SendGrid**: Dedicated email service with high deliverability
- **AWS SES**: Cost-effective for high volume
- **Mailgun**: Developer-friendly API

### Frontend Integration
Update the verification URL in `EmailService.java` to point to your frontend:
```java
String verificationUrl = "https://stackpedia.dev/verify?token=" + token;
```

Your frontend should:
1. Extract token from URL query parameter
2. Make GET request to `/api/user/verify?token={token}`
3. Display success/error message
4. Redirect to login page

## Security Considerations

### Email Verification
- Tokens expire after 24 hours
- Tokens are deleted after successful verification
- Accounts remain disabled until email is verified
- Consider adding "resend verification email" endpoint

### Rate Limiting
- Current limit (5 req/min) is conservative for production
- May need to adjust based on legitimate traffic patterns
- Consider implementing account lockout after multiple failed login attempts
- For distributed systems, use Redis instead of in-memory cache

### Password Validation
- Password requirements are enforced on signup only
- Consider adding "change password" endpoint with same validation
- Passwords are hashed with BCrypt before storage
- Never log or transmit passwords in plain text

## Future Enhancements

1. **Refresh Tokens**: Implement refresh token mechanism to reduce JWT expiration time
2. **Account Lockout**: Lock account after N failed login attempts
3. **2FA**: Add two-factor authentication option
4. **Password Reset**: Implement forgot password flow
5. **Security Headers**: Add CSP, HSTS, X-Frame-Options headers
6. **Audit Logging**: Log all authentication attempts and security events
7. **Distributed Rate Limiting**: Use Redis for multi-instance deployments