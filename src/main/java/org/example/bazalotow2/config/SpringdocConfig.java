package org.example.bazalotow2.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "basicAuth",
    scheme = "basic"
)
public class SpringdocConfig {
}
