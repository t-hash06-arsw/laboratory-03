package edu.eci.arsw.blueprints.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "edu\\.eci\\.arsw\\.blueprints\\.test\\..*"))
public class AppConfig {
}
