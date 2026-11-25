package br.com.astrobit.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
* Tarefa do usuário, mapeada para a tabela "tasks"
* A estrutura da tabela é criada pelo Flyway
* O Hibernate apenas valida se esta classe corresponde a ela
*/

@Entity
@Table(name = "tasks")
public class Task {

    //UUIDv7 gerado pela aplicação antes do INSERT
    //Não dá para adivinhar id na URL e mantém o índice eficiente
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "completed")
    private boolean completed;

    //Preenchido pelo service quando a tarefa é concluída
    // Volta a null quando ela é desmarcada
    @Column(name = "completed_at")
    private Instant completedAt;

    //updatable = false: a data de criação nunca é alterada depois do INSERT
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
    * Define a data de criação logo antes de salvar uma tarefa nova
    * Usado no lugar de @CreationTimestamp para que o valor já esteja no objeto
    * quando a resposta da API for montada
    */
    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }
}
