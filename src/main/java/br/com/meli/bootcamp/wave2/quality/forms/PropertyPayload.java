package br.com.meli.bootcamp.wave2.quality.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PropertyPayload {
    @Schema(example = "Casa amarela e grande")
    @NotEmpty(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "^[A-Z]+.*", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String propName;
    @NotEmpty(message = "O bairro não pode estar vazio.")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres.")
    @Schema(example = "Vila Pauliceia")
    private String propDistrict;
//    @NotNull(message = "O valor do metro quadrado no bairro não pode estar vazio.")
//    @DecimalMin(value = "0.01")
//    @Digits(integer = 13, fraction = 2)
//    private BigDecimal valueDistricM2;
    @NotEmpty(message = "Deve haver pelo menos um quarto.")
    @Valid
    private List<RoomPayload> rooms;



    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class RoomPayload {
        @Schema(example = "Quarto azul e pequeno")
        @NotEmpty(message = "O campo não pode estar vazio.")
        @Pattern(regexp = "^[A-Z]+.*", message = "O nome do cômodo deve começar com uma letra maiúscula.")
        @Size(max = 30, message = "O comprimento do nome do cômodo não pode exceder 30 caracteres.")
        private String roomName;
        @NotNull(message = "A largura do cômodo não pode estar vazia.")
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "25", message = "A largura máxima permitida por cômodo é de 25 metros.")
        private BigDecimal roomWidth;
        @NotNull(message = "A largura do cômodo não pode estar vazia.")
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "33", message = "O comprimento máximo permitido por cômodo é de 33 metros.")
        private BigDecimal roomLength;


    }
}
