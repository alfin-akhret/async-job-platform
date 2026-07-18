package com.alfin.asyncjob.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.notification")
public class NotificationProperties {
    private int maxRetry;
    private String defaultSender;

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getDefaultSender() {
        return defaultSender;
    }

    public void setDefaultSender(String defaultSender) {
        this.defaultSender = defaultSender;
    }
}
