package org.slavbx.taskmanager.service;

import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.exception.NotFoundException;
import org.slavbx.taskmanager.model.Comment;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Page<Comment> getAllComments(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        return commentRepository.findAll(pageable);
    }

    public Comment getCommentById(Long id) throws NotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));
    }

    public ResponseDTO createComment(Comment comment) {
        comment.setId(null);
        Comment savedComment = commentRepository.save(comment);
        return new ResponseDTO(savedComment.getId(), "Comment was created");
    }

    public ResponseDTO delete(Long id) {
        commentRepository.deleteById(id);
        return new ResponseDTO(id, "Comment was deleted");
    }
}