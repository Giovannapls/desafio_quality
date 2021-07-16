package br.com.meli.bootcamp.wave2.quality.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

  @Bean
  public OpenAPI springOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Real Estate API")
                .description("API for calculations related to a property (area, value, etc)")
                .version("0.0.1"))
        .externalDocs(
            new ExternalDocumentation()
                .description("Github")
                .url("https://github.com/Victor-Planas/desafio_quality"));
  }

}
