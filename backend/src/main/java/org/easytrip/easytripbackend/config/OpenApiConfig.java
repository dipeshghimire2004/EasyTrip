package org.easytrip.easytripbackend.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyTrip API")
                .version("1.0")
                        .description("API documentation for Easy trip Backend")
                        .contact(new Contact()
                                .name("Dipesh Ghimire")
                                .email("ghimiredipesh2004@gmail.com")));
    }
}
