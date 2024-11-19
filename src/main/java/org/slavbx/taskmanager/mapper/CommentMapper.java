package org.slavbx.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slavbx.taskmanager.dto.CommentDTO;
import org.slavbx.taskmanager.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "taskId", source = "task.id")
    CommentDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "taskId", source = "task.id")
    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);

    //Comment commentDTOToComment(commentDTO commentDTO);

    default Page<CommentDTO> commentsPageToCommentDTOsPage(Page<Comment> commentsPage) {
        List<CommentDTO> commentDTOs = commentsToCommentDTOs(commentsPage.getContent());
        return new PageImpl<>(commentDTOs, commentsPage.getPageable(), commentsPage.getTotalElements());
    }
}