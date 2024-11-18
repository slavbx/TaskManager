package org.slavbx.taskmanager.model;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "roles", schema = "taskmanager")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "taskmanager.roles_id_seq", allocationSize = 1)
    private Long id;

    private String name;
}
