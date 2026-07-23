package com.fieldservicemanagement.field_service_management.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProp {
    private Duration accessTtl;
    private Duration refreshTtl;
    private String secret;
}
