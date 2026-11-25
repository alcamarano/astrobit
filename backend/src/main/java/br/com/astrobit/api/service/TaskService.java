package br.com.astrobit.api.service;

import br.com.astrobit.api.dto.TaskCompletionRequest;
import br.com.astrobit.api.dto.TaskRequest;
import br.com.astrobit.api.dto.TaskResponse;
import br.com.astrobit.api.entity.Task;
import br.com.astrobit.api.exception.TaskNotFoundException;
import br.com.astrobit.api.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Regras de negócio das tarefas: listagem com filtro, criação, edição, conclusão e exclusão
 * Recebe e devolve DTOs; a entidade não sai daqui
*/

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Lista as tarefas ordenadas pela data
     * Filtros: "pending" (pendentes), "done" (concluídas)
     * qualquer outro valor devolve todas
    */
    @Transactional(readOnly = true)
    public List<TaskResponse> list(String status) {
        List<Task> tasks = switch (status) {
            case "pending" -> taskRepository.findByCompletedOrderByDueDateAsc(false);
            case "done" -> taskRepository.findByCompletedOrderByDueDateAsc(true);
            default -> taskRepository.findAllByOrderByDueDateAsc();
        };

        return tasks.stream().map(TaskResponse::from).toList();
    }

    @Transactional
    public TaskResponse create(TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.title());
        task.setDescription(blankToNull(taskRequest.description()));
        task.setDueDate(taskRequest.dueDate());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    /**
     * Edita título, descrição e data de uma tarefa existente
     * Lança TaskNotFoundException (404) se o id não existir
    */
    @Transactional
    public TaskResponse update(UUID id, TaskRequest taskRequest) {
        Task task = findTaskById(id);

        task.setTitle(taskRequest.title());
        task.setDescription(blankToNull(taskRequest.description()));
        task.setDueDate(taskRequest.dueDate());

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    @Transactional
    public TaskResponse updateCompletion(UUID id, TaskCompletionRequest taskCompletionRequest) {
        Task task = findTaskById(id);

        task.setCompleted(taskCompletionRequest.completed());

        // Concluir registra o momento da conclusão; desmarcar apaga esse registro
        if (taskCompletionRequest.completed()) {
            task.setCompletedAt(Instant.now());
        } else {
            task.setCompletedAt(null);
        }

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    /**
     * Apaga uma tarefa
     * Lança TaskNotFoundException (404) se o id não existir
    */
    @Transactional
    public void delete(UUID id) {
        Task task = findTaskById(id);

        taskRepository.delete(task);
    }

    // Busca usada por update, updateCompletion e delete
    // centraliza a busca e a exceção de "não encontrada"
    private Task findTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada."));
    }

    // Texto vazio ou só com espaços vira null, para "sem valor ter uma única representação no banco
    private String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        } else {
            return value;
        }
    }
}
