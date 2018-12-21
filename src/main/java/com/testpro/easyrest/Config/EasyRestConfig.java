package com.testpro.easyrest.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "easyrest.restassured")
@Data
public class EasyRestConfig {

    private String baseurl;
}
