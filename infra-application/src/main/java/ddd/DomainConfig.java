package ddd;

import annotation.DomainService;
import annotation.Stub;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"usecase", "controller"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {DomainService.class, Stub.class})})
public class DomainConfig {
}
