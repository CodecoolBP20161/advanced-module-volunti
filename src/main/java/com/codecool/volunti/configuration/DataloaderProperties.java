package com.codecool.volunti.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dataloader")
public class DataloaderProperties {

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled;

}
