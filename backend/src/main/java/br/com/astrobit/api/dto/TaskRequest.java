package br.com.astrobit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Dados recebidos para criar ou editar uma tarefa (POST e PUT)
 * As validações são verificadas pelo @Valid no controller
 * Se alguma falhar, a API responde 400 com a mensagem definida aqui
 */
public record TaskRequest(

        @NotBlank(message = "O título é obrigatório.")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
        String title,

        // Opcional: sem @NotBlank. O @Size ignora null, então só limita o tamanho quando há texto
        // Texto vazio vira null no service (blankToNull)
        @Size(max = 300, message = "A descrição deve ter no máximo 300 caracteres.")
        String description,

        @NotNull(message = "A data é obrigatória.")
        LocalDate dueDate
) {
}
