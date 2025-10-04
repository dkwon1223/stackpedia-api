package com.dkwondev.stackpedia_v2_api.oauth2;

import com.dkwondev.stackpedia_v2_api.exception.ValidationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("github")) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new ValidationException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}