package org.slavbx.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

/**
 * Класс представляет сущность задачи.
 * Содержит информацию о задаче, включая её идентификатор,
 * заголовок, описание, автора, исполнителя, приоритет,
 * статус и связанные комментарии
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * Уникальный идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    private Long id;
    /**
     * Заголовок задачи
     */
    @Column
    String title;

    /**
     * Описание задачи
     */
    @Column
    String description;

    /**
     * Пользователь, создавший задачу
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Пользователь, выполняющий задачу
     */
    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;

    /**
     * Приоритет задачи
     */
    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    /**
     * Статус задачи
     */
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    /**
     * Список комментариев, связанных с задачей
     */
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments;
}
