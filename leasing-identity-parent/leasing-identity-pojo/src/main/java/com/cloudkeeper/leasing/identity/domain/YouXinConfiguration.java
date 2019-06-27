package com.cloudkeeper.leasing.identity.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "youxing")
@EqualsAndHashCode(callSuper = false)
public class YouXinConfiguration {
    private String corn1;
    private String corn2;

}

