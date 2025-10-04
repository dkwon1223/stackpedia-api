-- Add email_verified and enabled columns to users table
ALTER TABLE users ADD COLUMN email_verified BOOLEAN DEFAULT FALSE;
ALTER TABLE users ADD COLUMN enabled BOOLEAN DEFAULT TRUE;

-- Create email_verification_tokens table
CREATE TABLE email_verification_tokens (
    token_id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create index on token for faster lookups
CREATE INDEX idx_verification_token ON email_verification_tokens(token);

-- Create index on user_id for faster lookups
CREATE INDEX idx_verification_user_id ON email_verification_tokens(user_id);