package br.com.astrobit.api.exception;

/**
 * Lançada quando uma tarefa não é encontrada pelo id.
 * O GlobalExceptionHandler transforma esta exceção em uma resposta 404.
*/

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
