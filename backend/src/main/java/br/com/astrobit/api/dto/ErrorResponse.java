package br.com.astrobit.api.dto;

import java.time.Instant;

/**
 * Formato padrão de todas as respostas de erro da API
 * Com um formato único, o front-end sempre sabe onde ler a mensagem
 * Montado pelo GlobalExceptionHandler
*/

public record ErrorResponse(
        int status,
        String message,
        Instant timestamp
) { }
