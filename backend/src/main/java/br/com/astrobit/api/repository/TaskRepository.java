package br.com.astrobit.api.repository;

import br.com.astrobit.api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Acesso ao banco para as tarefas
 * O Spring Data gera a implementação automaticamente
 * Os métodos declarados aqui têm a consulta montada a partir do próprio nome
*/

public interface TaskRepository extends JpaRepository<Task, UUID> {
    // Todas as tarefas, da data mais próxima para a mais distante.
    List<Task> findAllByOrderByDueDateAsc();
    // Tarefas filtradas pelo status de conclusão e data
    // true = concluídas | false = pendentes
    List<Task> findByCompletedOrderByDueDateAsc(boolean completed);
}
