package configuration;

import applicant.dto.ApplyCompanyRegistrationRequest;
import ddd.DomainService;
import ddd.Stub;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = {ApplyCompanyRegistrationRequest.class, ApplyCompanyRegistrationResponse.class},
        includeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = {DomainService.class, Stub.class})
)
public class DomainConfiguration {
}
