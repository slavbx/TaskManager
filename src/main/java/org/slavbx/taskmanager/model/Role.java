package org.slavbx.taskmanager.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * Класс редставляет сущность роли пользователя.
 * Содержит информацию о роли, включая ее уникальный идентификатор и название
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "roles")
public class Role {
    /**
     * Уникальный идентификатор роли
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    private Long id;
    /**
     * Название роли
     */
    private String name;
}
