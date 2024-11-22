package org.slavbx.taskmanager.repository.specification;

import org.slavbx.taskmanager.model.Comment;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecifications {
    public static Specification<Comment> hasAuthor(Long authorId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
          if (authorId == null) return null;
          return criteriaBuilder.equal(root.get("author").get("id"), authorId);
        };
    }
}
