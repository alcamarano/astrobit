package br.com.astrobit.api.dto;

import br.com.astrobit.api.entity.Task;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Formato de uma tarefa nas respostas da API
 * A entidade nunca é devolvida diretamente
 * Ela é sempre convertida para este DTO
 */
public record TaskResponse(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        boolean completed,
        Instant completedAt,
        Instant createdAt
) {
    /**
     * Converte a entidade Task para o formato de resposta
     * Concentra a conversão em um só lugar para não repeti-la no service
     */
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted(),
                task.getCompletedAt(),
                task.getCreatedAt()
        );
    }
}
