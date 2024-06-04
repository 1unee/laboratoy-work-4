package com.oneune.laboratory.work.configs;

import com.oneune.laboratory.work.configs.properties.AppProperties;
import com.oneune.laboratory.work.configs.properties.AuthorProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthorProperties.class)
public class OpenApiConfig {

    AuthorProperties authorProperties;
    AppProperties appProperties;

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail(this.authorProperties.email());
        contact.setName("%s (%s %s %s)".formatted(
                this.authorProperties.username(),
                this.authorProperties.personal().getLastName(),
                this.authorProperties.personal().getFirstName(),
                this.authorProperties.personal().getMiddleName()
        ));
        return contact;
    }

    private License getLicense() {
        return new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");
    }

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title(this.appProperties.name())
                .version(this.appProperties.version())
                .contact(this.getContact())
                .description(this.appProperties.description())
                .license(this.getLicense());
        return new OpenAPI().info(info);
    }
}
