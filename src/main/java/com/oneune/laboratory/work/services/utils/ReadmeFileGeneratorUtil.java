package com.oneune.laboratory.work.services.utils;

import com.oneune.laboratory.work.configs.properties.AuthorProperties;
import com.oneune.laboratory.work.configs.properties.ServerProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public final class ReadmeFileGeneratorUtil implements CommandLineRunner {

    private final static String MD_DELIMITER = "@MD@";
    private final static String README_FILE_NAME = "README.md";

    AuthorProperties authorProperties;
    ServerProperties serverProperties;
    WebMvcProperties webMvcProperties;
    ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/README.template.md");
        String readmeFileTemplateContent = FileCopyUtils.copyToString(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)
        );

        Map<String, String> readmeFileProperties = this.getFilledPropertiesContainer();
        String readmeContent = setProperties(readmeFileTemplateContent, readmeFileProperties);

        File readmeFileResult = new File(README_FILE_NAME);
        FileCopyUtils.copy(readmeContent.getBytes(StandardCharsets.UTF_8), readmeFileResult);

        log.info("{} successfully created", README_FILE_NAME);
    }

    private Map<String, String> getFilledPropertiesContainer() {
        return Map.of(
                "author.personal.last-name", this.authorProperties.personal().getLastName(),
                "author.personal.first-name", this.authorProperties.personal().getFirstName(),
                "author.personal.middle-name", this.authorProperties.personal().getMiddleName(),
                "author.addition.university.group", this.authorProperties.addition().university().group(),
                "server.port", this.serverProperties.port().toString(),
                "spring.mvc.servlet.path", this.webMvcProperties.getServlet().getPath(),
                "server.api.version.auth", this.serverProperties.api().version().auth()
        );
    }

    private static String setProperties(String readmeFileTemplateContent,
                                        Map<String, String> readmeFileProperties) {
        String readmeFileResultContent = readmeFileTemplateContent;

        for (Map.Entry<String, String> entry : readmeFileProperties.entrySet()) {
            String target = MD_DELIMITER + entry.getKey() + MD_DELIMITER;
            readmeFileResultContent = readmeFileResultContent.replace(target, entry.getValue());
        }

        return readmeFileResultContent;
    }
}

