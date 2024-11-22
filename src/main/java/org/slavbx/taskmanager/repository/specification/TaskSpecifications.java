package org.slavbx.taskmanager.repository.specification;

import org.slavbx.taskmanager.model.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> hasAuthor(Long authorId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
          if (authorId == null) return null;
          return criteriaBuilder.equal(root.get("author").get("id"), authorId);
        };
    }

    public static Specification<Task> hasPerformer(Long performerId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (performerId == null) return null;
            return criteriaBuilder.equal(root.get("performer").get("id"), performerId);
        };
    }
}
