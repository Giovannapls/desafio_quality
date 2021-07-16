package br.com.meli.bootcamp.wave2.quality.config.exceptions.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class RouteNotFoundError extends ApiError {
  @Schema(description = "Route that was not found in the server", example = "/non-registered/route")
  private String route;

  public RouteNotFoundError(String route, LocalDateTime errorTime) {
    super("route_not_found", "Unable to find route!", HttpStatus.NOT_FOUND.value(), errorTime);
    this.route = route;
  }
}
