package br.com.meli.bootcamp.wave2.quality.config.exceptions.responses;

import br.com.meli.bootcamp.wave2.quality.exceptions.ApiException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiError {
  @Schema(description = "Code of the exception that occurred", example = "internal_error")
  private String code;

  @Schema(
      description = "More specific details about the error",
      example = "A internal server error occurred!")
  private String description;

  @Schema(description = "HTTP Status code of the error", example = "500")
  private Integer statusCode;

  @Schema(description = "When the error was registered in the server")
  private LocalDateTime timestamp;


  public ApiError(ApiException exception, LocalDateTime timeOfError) {
    this.code = exception.getCode();
    this.description = exception.getMessage();
    this.statusCode = exception.getStatusCode().value();
    this.timestamp = timeOfError;
  }

  public ApiError(String code, String description, Integer statusCode, LocalDateTime timeOfError) {
    this.code = code;
    this.description = description;
    this.statusCode = statusCode;
    this.timestamp = timeOfError;
  }
}
