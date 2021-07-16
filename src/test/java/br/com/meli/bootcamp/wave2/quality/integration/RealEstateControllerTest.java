package br.com.meli.bootcamp.wave2.quality.integration;


import br.com.meli.bootcamp.wave2.quality.responses.HouseSquaredMetersResponse;
import br.com.meli.bootcamp.wave2.quality.util.FileReaderUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Clock;
import java.time.Instant;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class RealEstateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String jsonPayload;
    private String invalidJsonPayload;

    @BeforeEach
    void setUp() throws IOException {
        jsonPayload = FileReaderUtil.readResourcesString("payloads/HousePayloadExample.json");
        invalidJsonPayload = FileReaderUtil.readResourcesString("payloads/HousePayloadWrongExample.json");
    }

    @Test
    void calculateSquareMeter_shouldReturnCorrectData() throws Exception {
        var expectedResponse = "{\n" +
                "  \"property_name\": \"Casa amarela e grande\",\n" +
                "  \"area\": 820\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/realEstate/propertyArea")
                .contentType("application/json")
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void calculateValue_shouldReturnPropertyPriceCorrectly() throws Exception {
        var expectedResponse = "{\n" +
                "  \"name\": \"Casa amarela e grande\",\n" +
                "  \"value\": 82000\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/realEstate/propertyPrice")
                .contentType("application/json")
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

    }

    @Test
    void getBiggestRoom_shouldReturnTheBiggestRoomInTheHouse() throws Exception {
        var expectedResponse = "{\n" +
                "  \"property_name\": \"Casa amarela e grande\",\n" +
                "  \"biggest_room\": {\n" +
                "    \"name\": \"Quarto azul e pequeno\",\n" +
                "    \"area\": 390,\n" +
                "    \"width\": 13,\n" +
                "    \"length\": 30\n" +
                "  }\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/realEstate/biggestRoom")
                .contentType("application/json")
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void getRoomsArea_shouldReturnAllRoomResponseValues() throws Exception {
        var expectedResponse = "{\n" +
                "  \"property_name\": \"Casa amarela e grande\",\n" +
                "  \"rooms\": [\n" +
                "    {\n" +
                "      \"name\": \"Quarto azul e pequeno\",\n" +
                "      \"area\": 390,\n" +
                "      \"width\": 13,\n" +
                "      \"length\": 30\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Quarto vermelho e bonito\",\n" +
                "      \"area\": 250,\n" +
                "      \"width\": 25,\n" +
                "      \"length\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Quarto rosa e amarelo\",\n" +
                "      \"area\": 180,\n" +
                "      \"width\": 15,\n" +
                "      \"length\": 12\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/realEstate/roomArea")
                .contentType("application/json")
                .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void calculateSquareMeterHouse_shouldThrowInvalidDistrictException() throws Exception {
        //arrange
        var expectedResponse = "{\n" +
                "  \"code\": \"invalid_district_name\",\n" +
                "  \"description\": \"District does not exist\",\n" +
                "  \"status_code\": 400,\n" +
                "  \"timestamp\": \"2011-12-03T07:15:30\"\n" +
                "}";

        //act
        mockMvc.perform(MockMvcRequestBuilders.post("/realEstate/propertyArea")
                .contentType("application/json")
                .content(invalidJsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponse));


    }
}
