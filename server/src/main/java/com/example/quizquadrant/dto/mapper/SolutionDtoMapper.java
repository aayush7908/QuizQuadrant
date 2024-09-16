package com.example.quizquadrant.dto.mapper;

import com.example.quizquadrant.dto.SolutionDto;
import com.example.quizquadrant.model.Solution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolutionDtoMapper {

    public SolutionDto toDto(
            Solution solution
    ) {
        return SolutionDto
                .builder()
                .id(solution.getId())
                .statement(solution.getStatement())
                .imageUrl(solution.getImageUrl())
                .build();
    }

}
