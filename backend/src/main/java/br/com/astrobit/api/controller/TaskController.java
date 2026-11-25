package br.com.astrobit.api.controller;

import br.com.astrobit.api.dto.TaskCompletionRequest;
import br.com.astrobit.api.dto.TaskRequest;
import br.com.astrobit.api.dto.TaskResponse;
import br.com.astrobit.api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Endpoints de tarefas em /api/tasks
 * Apenas recebe as requisições e repassa ao TaskService, as regras de negócio ficam no service
*/

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // O padrão "all" evita que o switch do service receba null quando a URL vem sem ?status=
    @GetMapping
    public List<TaskResponse> list(@RequestParam(defaultValue = "all") String status) {
        return taskService.list(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.create(taskRequest);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable UUID id, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.update(id, taskRequest);
    }

    // Mesmo caminho do PUT: o Spring diferencia pelo verbo HTTP
    // PATCH altera só o status de conclusão
    @PatchMapping("/{id}")
    public TaskResponse updateCompletion(@PathVariable UUID id, @RequestBody @Valid TaskCompletionRequest taskCompletionRequest) {
        return taskService.updateCompletion(id, taskCompletionRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        taskService.delete(id);
    }
}
