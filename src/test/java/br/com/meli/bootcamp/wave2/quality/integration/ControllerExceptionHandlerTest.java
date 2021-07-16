package br.com.meli.bootcamp.wave2.quality.integration;

import br.com.meli.bootcamp.wave2.quality.QualityApplication;
import br.com.meli.bootcamp.wave2.quality.config.exceptions.responses.ApiError;
import br.com.meli.bootcamp.wave2.quality.config.exceptions.responses.ValidationError;
import br.com.meli.bootcamp.wave2.quality.controller.PingController;
import br.com.meli.bootcamp.wave2.quality.controller.RealEstateController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = QualityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControllerExceptionHandlerTest {

    @SpyBean
    private PingController pingController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private <T> RequestEntity<T> getDefaultRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new RequestEntity<>(headers, HttpMethod.GET, null);
    }

    @Test
    void notFound() {
        //When
        ResponseEntity<ApiError> response = this.testRestTemplate.exchange("/routeThatDoesntExistsOnTheServer", HttpMethod.GET, this.getDefaultRequestEntity(), ApiError.class);

        //Then
        assertThat(response)
                .extracting(el -> tuple(el.getStatusCode(), el.getBody().getCode()))
                .isEqualTo(tuple(HttpStatus.NOT_FOUND, "route_not_found"));
    }

    @Test
    void badRequest() {
        //Given


        var methodParameter = Mockito.mock(MethodParameter.class);
        var bindingResult = Mockito.mock(BindingResult.class);

        var exception = Mockito.spy(new MethodArgumentNotValidException(methodParameter, bindingResult));
//        var exception = new MethodArgumentNotValidException(methodParameter,bindingResult
        doReturn("RAP DO MINECRAFT!!!!!").when(exception).getMessage();
        when(exception.getFieldErrors()).thenReturn(
                Arrays.asList(new FieldError("age", "age", "Age must be at least 18"))
        );

        when(pingController.ping()).thenAnswer(invocation -> {
            throw exception;
        });

        //When
        var response = this.testRestTemplate.exchange("/ping", HttpMethod.GET, this.getDefaultRequestEntity(), ValidationError.class);

        //Then
        assertThat(response)
                .extracting(el -> tuple(el.getStatusCode(), el.getBody().getCode()))
                .isEqualTo(tuple(HttpStatus.BAD_REQUEST, "field_constraint_violation"));
    }

    @Test
    void invalidParameterInURL() {
        // Given
        var exception = new ConstraintViolationException(Sets.newSet(
                createViolation("name", "Name cannot be empty", "")
        ));


        Mockito.doThrow(exception).when(pingController)
                .ping();


        //When
        var response = this.testRestTemplate.exchange("/ping", HttpMethod.GET, this.getDefaultRequestEntity(), ValidationError.class);


        // Then
        assertThat(response)
                .extracting(el -> tuple(el.getStatusCode(), el.getBody().getCode()))
                .isEqualTo(tuple(HttpStatus.BAD_REQUEST, "field_constraint_violation"));

    }

    @Test
    void HandleMissingParams_BadRequest(){
// Given
        var httpInputMessage = Mockito.mock(HttpInputMessage.class);
        var exception = new HttpMessageNotReadableException("Teste", httpInputMessage);


        Mockito.doThrow(exception).when(pingController).ping();


        //When
        var response = this.testRestTemplate.exchange("/ping", HttpMethod.GET, this.getDefaultRequestEntity(), ApiError.class);


        // Then
        assertThat(response)
                .extracting(el -> tuple(el.getStatusCode(), el.getBody().getCode()))
                .isEqualTo(tuple(HttpStatus.UNPROCESSABLE_ENTITY, "unprocessable_entity"));
    }

    private ConstraintViolation<?> createViolation(String propertyPath, String message, String invalidValue) {
        var violation = Mockito.mock(ConstraintViolation.class, Answers.RETURNS_DEEP_STUBS);

        doReturn(message).when(violation).getMessage();
        when(violation.getPropertyPath().toString()).thenReturn(propertyPath);
        doReturn(invalidValue).when(violation).getInvalidValue();
        return violation;

    }
}
