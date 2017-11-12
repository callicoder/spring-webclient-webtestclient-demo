package com.example.webclientdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by rajeevkumarsingh on 10/11/17.
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Github github = new Github();

    public static class Github {
        private String username;
        private String token;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public Github getGithub() {
        return github;
    }
}
