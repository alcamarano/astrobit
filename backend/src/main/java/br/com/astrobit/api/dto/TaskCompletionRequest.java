package br.com.astrobit.api.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Dados recebidos para concluir ou desmarcar uma tarefa (PATCH).
 * Separado do TaskRequest porque este endpoint recebe apenas o status.
*/

public record TaskCompletionRequest(
    // Boolean (objeto), e não boolean (primitivo):
    // O primitivo nunca é null e viraria false se o campo faltasse no JSON, sem o @NotNull perceber.
    @NotNull(message = "O status de conclusão é obrigatório.")
    Boolean completed
) { }
