package br.com.meli.bootcamp.wave2.quality.config.exceptions.responses;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@Getter
public class ValidationError extends ApiError {

  private List<FieldValidationError> violations;

  public ValidationError(HttpStatus statusCode, List<FieldValidationError> fieldErrors,
      ZonedDateTime timeError) {
    super(
        "field_constraint_violation", "One or more fields validation failed!", statusCode.value(),
        timeError);
    this.violations = fieldErrors;
  }
}
