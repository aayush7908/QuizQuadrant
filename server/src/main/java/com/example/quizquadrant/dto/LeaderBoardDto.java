package com.example.quizquadrant.dto;

public record LeaderBoardDto (
        Long userId,
        String name,
        Integer marks,
        Boolean isPresent
) {


}
