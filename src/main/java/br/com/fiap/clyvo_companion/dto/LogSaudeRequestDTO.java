package br.com.fiap.clyvo_companion.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogSaudeRequestDTO {

    @NotNull(message = "Selecione o pet")
    private Long idPet;

    @NotNull(message = "Informe a data e hora do registro")
    @PastOrPresent(message = "A data do registro não pode ser futura")
    private LocalDateTime dtRegistro;

    @NotNull(message = "Informe o valor da métrica")
    @DecimalMin(value = "0.00", message = "O valor da métrica deve ser maior ou igual a 0")
    @DecimalMax(value = "5000.00", message = "O valor da métrica deve ser no máximo 5000")
    private BigDecimal vlMetrica;

    @NotBlank(message = "Informe a métrica")
    @Size(max = 30, message = "A métrica deve ter no máximo 30 caracteres")
    private String metrica;

    @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
    private String obs;
}
