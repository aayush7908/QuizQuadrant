package com.example.quizquadrant.utils.validation;

import com.example.quizquadrant.dto.authentication.LoginRequestDto;
import com.example.quizquadrant.dto.authentication.RegisterRequestDto;
import com.example.quizquadrant.dto.question.OptionRequestDto;
import com.example.quizquadrant.dto.question.QuestionRequestDto;
import com.example.quizquadrant.dto.question.SolutionRequestDto;
import com.example.quizquadrant.dto.subject.SubjectRequestDto;
import com.example.quizquadrant.dto.subtopic.SubtopicRequestDto;
import com.example.quizquadrant.dto.user.UpdateUserNameRequestDto;

import java.util.UUID;

public interface ValidationService {
    UUID validateAndGetUUID(
            String id
    ) throws Exception;

    void validatePageNumber(
            Integer pageNumber
    ) throws Exception;

    void validatePageSize(
            Integer pageSize
    ) throws Exception;

    void validateEmail(
            String email
    ) throws Exception;

    void validatePassword(
            String password
    ) throws Exception;

    void validateName(
            String name
    ) throws Exception;

    void validateUserRole(
            String role
    ) throws Exception;

    void validateRegisterInput(
            RegisterRequestDto registerRequestDto
    ) throws Exception;

    void validateLoginInput(
            LoginRequestDto loginRequestDto
    ) throws Exception;

    void validateUpdateUserNameInput(
            UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception;

    void validateSubjectRequestInput(
            SubjectRequestDto subjectRequestDto
    ) throws Exception;

    void validateSubtopicRequestInput(
            SubtopicRequestDto subtopicRequestDto
    ) throws Exception;

    void validateQuestionType(
            String type
    ) throws Exception;

    void validateSolutionRequestInput(
            SolutionRequestDto solutionRequestDto
    ) throws Exception;

    void validateQuestionRequestInput(
            QuestionRequestDto questionRequestDto
    ) throws Exception;

    void validateOptionRequestInput(
            OptionRequestDto optionRequestDto
    ) throws Exception;
//
//    void validateExamStartDateTime(LocalDateTime examStartDateTime) throws Exception;
//
//    void validateUpdateSubtopicInput(SubtopicDto subtopicDto) throws Exception;
//
//    void validateUpdateSubjectInput(SubjectDto subjectDto) throws Exception;
//
//    void validateUpdateQuestionInput(QuestionDto questionDto) throws Exception;
//
//    void validateUpdateOptionInput(OptionDto optionDto) throws Exception;
//
//    void validateUpdateSolutionInput(SolutionDto solutionDto) throws Exception;
//
//    void validateCreateExamInput(ExamDto examDto) throws Exception;
//
//    void validateExamQuestionInput(QuestionDto questionDto) throws Exception;
//
//    void validateUpdateExamDetailsInput(ExamDto examDto) throws Exception;
//
//    void validateUpdateExamCandidatesInput(ExamDto examDto) throws Exception;
//
//    void validateUpdateExamQuestionsInput(ExamDto examDto) throws Exception;
}
