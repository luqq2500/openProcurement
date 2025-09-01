package ddd;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "usecase",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {EventService.class}))
public class EventConfig {

}
