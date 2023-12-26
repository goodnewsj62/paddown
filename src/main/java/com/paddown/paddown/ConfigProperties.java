package com.paddown.paddown;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("props")
public record ConfigProperties(
    String secretKey,
    int tokenexp,
    int refreshexp,
    String mediaUrl
) {}
