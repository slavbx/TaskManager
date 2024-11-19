package org.slavbx.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "performerId", source = "performer.id")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "performerId", source = "performer.id")
    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);

    //Habit habitDTOToHabit(HabitDTO habitDTO);

    default Page<TaskDTO> tasksPageToTaskDTOsPage(Page<Task> tasksPage) {
        List<TaskDTO> taskDTOs = tasksToTaskDTOs(tasksPage.getContent());
        return new PageImpl<>(taskDTOs, tasksPage.getPageable(), tasksPage.getTotalElements());
    }
}
