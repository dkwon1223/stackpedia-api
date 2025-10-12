package com.dkwondev.stackpedia_v2_api.oauth2;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        // GitHub may return email directly in attributes (if public)
        String email = (String) attributes.get("email");

        // If email is null, it might be because the user has set it to private
        // In this case, we need to check if there are email objects in the attributes
        // The email field might be null even with proper scopes if the user's email is private
        if (email == null || email.isEmpty()) {
            // GitHub sometimes provides login (username) which we can use as fallback
            // But we should log this for debugging
            return null;
        }

        return email;
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}