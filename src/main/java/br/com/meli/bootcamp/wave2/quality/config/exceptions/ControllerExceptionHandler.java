package br.com.meli.bootcamp.wave2.quality.config.exceptions;

import br.com.meli.bootcamp.wave2.quality.config.exceptions.responses.ApiError;
import br.com.meli.bootcamp.wave2.quality.config.exceptions.responses.FieldValidationError;
import br.com.meli.bootcamp.wave2.quality.config.exceptions.responses.ValidationError;
import br.com.meli.bootcamp.wave2.quality.exceptions.ApiException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

  @Autowired
  private Clock clock;

  /**
   * Handle all exceptions created by us in the API
   *
   * @param exception - Exception to be handled
   * @return Human friendly response
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiError> handleApiException(ApiException exception) {
    var errorDto = new ApiError(exception, ZonedDateTime.now(this.clock));
    return ResponseEntity.status(errorDto.getStatusCode()).body(errorDto);
  }

  /**
   * This exception is thrown when a body validation fails
   *
   * @param exception - Exception to be handled
   * @return Human friendly response
   */
  @ApiResponse(
      responseCode = "400",
      description = "Invalid input data.",
      content = @Content(
          schema = @Schema(implementation = ValidationError.class),
          mediaType = "application/json"
      )
  )
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationError> handleInvalidMethodArgument(
      MethodArgumentNotValidException exception) {
    var fieldErrors =
        exception.getFieldErrors().stream()
                 .map(fieldError -> new FieldValidationError(
                     fieldError.getField(),
                     fieldError.getDefaultMessage())
                 ).collect(Collectors.toList());

    var response = new ValidationError(
        HttpStatus.BAD_REQUEST,
        fieldErrors,
        ZonedDateTime.now(this.clock)
    );

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  /**
   * This exception is thrown when a request param validation fails
   *
   * @param exception - Exception to be handled
   * @return Human friendly response
   */
  @ApiResponse(
      responseCode = "400",
      description = "Invalid input data.",
      content =
      @Content(
          schema = @Schema(implementation = ValidationError.class),
          mediaType = "application/json"))
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ValidationError> handleInvalidInput(
      ConstraintViolationException exception) {

    var fieldErrors =
        exception.getConstraintViolations().stream()
                 .map(
                     violation -> new FieldValidationError(
                         violation.getPropertyPath().toString(),
                         violation.getMessage(),
                         violation.getInvalidValue()))
                 .collect(Collectors.toList());

    var response = new ValidationError(
        HttpStatus.BAD_REQUEST,
        fieldErrors,
        ZonedDateTime.now(this.clock)
    );

    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  /**
   * This exception is thrown when the server can't parse the user input
   *
   * @param exception - Exception to be handled
   * @return Human friendly response
   */
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiError> handleMissingParams(HttpMessageNotReadableException exception) {
    var response = new ApiError(
        "unprocessable_entity",
        "Unable to parse request body!",
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        ZonedDateTime.now(this.clock)
    );
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  /**
   * This exception is thrown when we don't have the route mapping that the user is looking for
   *
   * @param exception - Exception to be handled
   * @return Human friendly response
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ApiError> noRouteFound(
      HttpServletRequest req, NoHandlerFoundException exception) {
    ApiError apiError =
        new ApiError(
            "route_not_found",
            String.format("Route %s not found", req.getRequestURI()),
            HttpStatus.NOT_FOUND.value(), ZonedDateTime.now(this.clock));
    return ResponseEntity.status(apiError.getStatusCode()).body(apiError);
  }
}
