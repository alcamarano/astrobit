package br.com.astrobit.api.exception;

import br.com.astrobit.api.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

/**
 * Trata as exceções de todos os controllers e as converte em respostas no formato ErrorResponse
 * Para cada exceção, o Spring escolhe o tratador mais específico
 * O de Exception só recebe o que sobrar
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFound(TaskNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
    }

    // Devolve a mensagem da primeira validação que falhou (definida nos DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage(), Instant.now());
    }

    // Parâmetro da URL em formato errado (ex.: id que não é um UUID)
    // Erro do cliente: 400, e não 500
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Identificador inválido.", Instant.now());
    }

    // JSON malformado ou valor impossível de converter (ex.: data em outro formato)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotReadable(HttpMessageNotReadableException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Requisição com formato inválido.", Instant.now());
    }

    // Erros não previstos
    // O detalhe vai só para o log
    // O cliente recebe uma mensagem genérica, sem expor classes, SQL ou outros detalhes internos
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpected(Exception ex) {
        log.error("Erro inesperado", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno. Tente novamente mais tarde.", Instant.now());
    }
}
