package org.slavbx.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    private Long id;

    @Column
    String title;

    @Column
    String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;

    @Enumerated(EnumType.STRING)
    @Column
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> comments;
}
