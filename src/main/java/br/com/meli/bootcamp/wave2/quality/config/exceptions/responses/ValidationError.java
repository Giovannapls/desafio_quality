package br.com.meli.bootcamp.wave2.quality.config.exceptions.responses;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ValidationError extends ApiError {

  private List<FieldValidationError> violations;

  public ValidationError(HttpStatus statusCode, List<FieldValidationError> fieldErrors) {
    super(
        "field_constraint_violation", "One or more fields validation failed!", statusCode.value());
    this.violations = fieldErrors;
  }
}
