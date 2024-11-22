package org.slavbx.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slavbx.taskmanager.dto.CommentDTO;
import org.slavbx.taskmanager.dto.CommentRequestDTO;
import org.slavbx.taskmanager.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "taskId", source = "task.id")
    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "author", expression = "java(null)")
    @Mapping(target = "task.id", source = "taskId")
    @Mapping(target = "dateTime", expression = "java(java.time.LocalDateTime.now())")
    Comment commentRequestDTOToComment(CommentRequestDTO commentRequestDTO);

    default Page<CommentDTO> commentsPageToCommentDTOsPage(Page<Comment> commentsPage) {
        List<CommentDTO> commentDTOs = commentsToCommentDTOs(commentsPage.getContent());
        return new PageImpl<>(commentDTOs, commentsPage.getPageable(), commentsPage.getTotalElements());
    }
}