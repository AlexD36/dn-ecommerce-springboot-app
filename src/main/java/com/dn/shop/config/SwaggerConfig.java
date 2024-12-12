import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration class for setting up API documentation.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Creates a Docket bean for Swagger configuration.
     * This method configures the Docket instance to specify the API documentation settings.
     * 
     * @return Docket instance configured for Swagger 2, which includes API selection criteria.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dn.shop.controller")) // Ensure this package is correct
                .paths(PathSelectors.any())
                .build();
    }
} 