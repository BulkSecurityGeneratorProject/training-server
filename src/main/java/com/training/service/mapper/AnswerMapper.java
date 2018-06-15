package com.training.service.mapper;

import com.training.domain.*;
import com.training.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ProblemMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "problem.id", target = "problemId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "problemId", target = "problem")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
