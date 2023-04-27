package com.nexign.crm.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    private final String secretKey = "655468576D5A7134743777217A24432646294A404E635266556A586E32723575";
    private final long validityInMs = 3 * 3600 * 1000;
}
