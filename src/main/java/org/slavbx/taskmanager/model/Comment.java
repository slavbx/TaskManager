package org.slavbx.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс представляет сущность комментария.
 * Содержит информацию о комментарии, включая его текст, дату и
 * время создания, а также связи с задачей и автором
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comments")
public class Comment {
    /**
     * Уникальный идентификатор комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Дата и время создания комментария, по умолчанию - текущее время
     */
    @Builder.Default
    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();

    /**
     * Текст комментария
     */
    @Column
    private String text;

    /**
     * Связь с задачей, к которой относится комментарий
     */
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    /**
     * Связь с пользователем, который является автором комментария
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
