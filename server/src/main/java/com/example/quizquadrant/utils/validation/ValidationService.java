package com.example.quizquadrant.utils.validation;

import com.example.quizquadrant.dto.*;

import java.time.LocalDateTime;

public interface ValidationService {
    void validateEmail(String email) throws Exception;

    void validatePassword(String password) throws Exception;

    void validateUserRole(String role) throws Exception;

    void validateQuestionType(String type) throws Exception;

    void validateExamStartDateTime(LocalDateTime examStartDateTime) throws Exception;

    void validateRegisterInput(RegisterRequestDto registerRequestDto) throws Exception;

    void validateLoginInput(LoginRequestDto loginRequestDto) throws Exception;

    void validateVerifyEmailInput(VerifyEmailRequestDto verifyEmailRequestDto) throws Exception;

    void validateUpdateUserNameInput(UserDto userDto) throws Exception;

    void validateCreateSubtopicInput(SubtopicDto subtopicDto) throws Exception;

    void validateUpdateSubtopicInput(SubtopicDto subtopicDto) throws Exception;

    void validateCreateSubjectInput(SubjectDto subjectDto) throws Exception;

    void validateUpdateSubjectInput(SubjectDto subjectDto) throws Exception;

    void validateCreateQuestionInput(QuestionDto questionDto) throws Exception;

    void validateUpdateQuestionInput(QuestionDto questionDto) throws Exception;

    void validateCreateOptionInput(OptionDto optionDto) throws Exception;

    void validateUpdateOptionInput(OptionDto optionDto) throws Exception;

    void validateCreateSolutionInput(SolutionDto solutionDto) throws Exception;

    void validateUpdateSolutionInput(SolutionDto solutionDto) throws Exception;

    void validateCreateExamInput(ExamDto examDto) throws Exception;

    void validateExamQuestionInput(QuestionDto questionDto) throws Exception;

    void validateUpdateExamDetailsInput(ExamDto examDto) throws Exception;
    void validateUpdateExamCandidatesInput(ExamDto examDto) throws Exception;
    void validateUpdateExamQuestionsInput(ExamDto examDto) throws Exception;
}
