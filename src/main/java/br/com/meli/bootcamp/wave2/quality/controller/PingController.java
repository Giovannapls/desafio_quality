package br.com.meli.bootcamp.wave2.quality.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Ping Controller",
        description = "Controller used to check if the API is working")
@RestController
public class PingController {

    @Operation(
            summary = "Test if the API is working",
            description = "Return pong if the request succeed")
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
