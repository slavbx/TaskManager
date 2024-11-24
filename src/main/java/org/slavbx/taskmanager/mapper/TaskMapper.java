package org.slavbx.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.dto.TaskRequestDTO;
import org.slavbx.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * Mapper для преобразования объектов Task и TaskDTO.
 * Использует MapStruct для автоматического маппинга между {@link Task} и {@link TaskDTO},
 * а также для создания новых объектов Task на основе {@link TaskRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

    /**
     * Преобразует объект Task в TaskDTO.
     * Значения authorId и performerId маппируются из вложенных объектов.
     *
     * @param task Объект Task для преобразования.
     * @return Преобразованный объект TaskDTO.
     */
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "performerId", source = "performer.id")
    TaskDTO taskToTaskDTO(Task task);

    /**
     * Преобразует список объектов Task в список TaskDTO.
     * Значения authorId и performerId маппируются из вложенных объектов.
     *
     * @param tasks Список объектов Task для преобразования.
     * @return Список преобразованных объектов TaskDTO.
     */
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "performerId", source = "performer.id")
    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);

    /**
     * Преобразует TaskRequestDTO в объект Task.
     * Значения id, author и comments устанавливаются в null,
     * performerId маппируется в performer.id.
     *
     * @param taskRequestDTO Объект TaskRequestDTO для преобразования.
     * @return Преобразованный объект Task.
     */
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "author", expression = "java(null)")
    @Mapping(target = "comments", expression = "java(null)")
    @Mapping(target = "performer.id", source = "performerId")
    Task taskRequestDTOToTask(TaskRequestDTO taskRequestDTO);


    /**
     * Преобразует страницу объектов Task в страницу TaskDTO.
     * Использует метод tasksToTaskDTOs для преобразования содержимого страницы.
     *
     * @param tasksPage Страница объектов Task для преобразования.
     * @return Страница преобразованных объектов TaskDTO.
     */
    default Page<TaskDTO> tasksPageToTaskDTOsPage(Page<Task> tasksPage) {
        List<TaskDTO> taskDTOs = tasksToTaskDTOs(tasksPage.getContent());
        return new PageImpl<>(taskDTOs, tasksPage.getPageable(), tasksPage.getTotalElements());
    }
}
