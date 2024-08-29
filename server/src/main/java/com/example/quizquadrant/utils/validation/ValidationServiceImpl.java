package com.example.quizquadrant.utils.validation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.utils.error.BadRequestError;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private final Pattern passwordRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$");

    @Override
    public void validateEmail(String email) throws Exception {
        boolean isEmailValid = isEmailValid(email);
        if (!isEmailValid) {
            throw new BadRequestError("Invalid email address");
        }
    }

    @Override
    public void validatePassword(String password) throws Exception {
        boolean isPasswordValid = isPasswordValid(password);
        if (!isPasswordValid) {
            throw new BadRequestError("Invalid password");
        }
    }

    @Override
    public void validatePageSize(Integer pageSize) throws Exception {
        boolean isPageSizeValid = isPageSizeValid(pageSize);
        if (!isPageSizeValid) {
            throw new BadRequestError("Invalid page size");
        }
    }

    @Override
    public void validateUserRole(String role) throws Exception {
        boolean isRoleValid = isRoleValid(role);
        if (!isRoleValid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateQuestionType(String type) throws Exception {
        boolean isQuestionTypeValid = isQuestionTypeValid(type);
        if (!isQuestionTypeValid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateExamStartDateTime(LocalDateTime examStartDateTime) throws Exception {
        boolean isExamStartDateTimeValid = isExamStartDateTimeValid(examStartDateTime);
        if (!isExamStartDateTimeValid) {
            throw new BadRequestError("Exam can start only after 30 minutes of creation");
        }
    }

    @Override
    public void validateRegisterInput(RegisterRequestDto registerRequestDto) throws Exception {
        boolean isRegisterInputInvalid = isRegisterInputInvalid(registerRequestDto);
        if (isRegisterInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateEmail(registerRequestDto.email());
        validatePassword(registerRequestDto.password());
        validateUserRole(registerRequestDto.role());
    }

    @Override
    public void validateLoginInput(LoginRequestDto loginRequestDto) throws Exception {
        boolean isLoginInputInvalid = isLoginInputInvalid(loginRequestDto);
        if (isLoginInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateEmail(loginRequestDto.email());
        validatePassword(loginRequestDto.password());
    }

    @Override
    public void validateVerifyEmailInput(VerifyEmailRequestDto verifyEmailRequestDto) throws Exception {
        boolean isVerifyEmailInputInvalid = isVerifyEmailInputInvalid(verifyEmailRequestDto);
        if (isVerifyEmailInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateEmail(verifyEmailRequestDto.email());
    }

    @Override
    public void validateUpdateUserNameInput(UserDto userDto) throws Exception {
        boolean isUpdateUserNameInputInvalid = isUpdateUserNameInputInvalid(userDto);
        if (isUpdateUserNameInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateCreateSubtopicInput(SubtopicDto subtopicDto) throws Exception {
        boolean isCreateSubtopicInputInvalid = isCreateSubtopicInputInvalid(subtopicDto);
        if (isCreateSubtopicInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateUpdateSubtopicInput(SubtopicDto subtopicDto) throws Exception {
        boolean isUpdateSubtopicInputInvalid = isUpdateSubtopicInputInvalid(subtopicDto);
        if (isUpdateSubtopicInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateCreateSubjectInput(SubjectDto subjectDto) throws Exception {
        boolean isCreateSubjectInputInvalid = isCreateSubjectInputInvalid(subjectDto);
        if (isCreateSubjectInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateUpdateSubjectInput(SubjectDto subjectDto) throws Exception {
        boolean isUpdateSubjectInputInvalid = isUpdateSubjectInputInvalid(subjectDto);
        if (isUpdateSubjectInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateCreateQuestionInput(QuestionDto questionDto) throws Exception {
        boolean isQuestionInputInvalid = isQuestionInputInvalid(questionDto);
        if (isQuestionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateQuestionType(questionDto.type());
        for (OptionDto optionDto : questionDto.options()) {
            validateCreateOptionInput(optionDto);
        }
        validateCreateSolutionInput(questionDto.solution());
    }

    @Override
    public void validateUpdateQuestionInput(QuestionDto questionDto) throws Exception {
        boolean isQuestionInputInvalid = isQuestionInputInvalid(questionDto);
        if (isQuestionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateQuestionType(questionDto.type());
        for (OptionDto optionDto : questionDto.options()) {
            validateUpdateOptionInput(optionDto);
        }
        validateUpdateSolutionInput(questionDto.solution());
    }

    @Override
    public void validateCreateOptionInput(OptionDto optionDto) throws Exception {
        boolean isCreateOptionInputInvalid = isCreateOptionInputInvalid(optionDto);
        if (isCreateOptionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateUpdateOptionInput(OptionDto optionDto) throws Exception {
        boolean isUpdateOptionInputInvalid = isUpdateOptionInputInvalid(optionDto);
        if (isUpdateOptionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateCreateSolutionInput(SolutionDto solutionDto) throws Exception {
        boolean isCreateSolutionInputInvalid = isCreateSolutionInputInvalid(solutionDto);
        if (isCreateSolutionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateUpdateSolutionInput(SolutionDto solutionDto) throws Exception {
        boolean isUpdateSolutionInputInvalid = isUpdateSolutionInputInvalid(solutionDto);
        if (isUpdateSolutionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateCreateExamInput(ExamDto examDto) throws Exception {
        boolean isCreateExamInputInvalid = isCreateExamInputInvalid(examDto);
        if (isCreateExamInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateExamStartDateTime(examDto.startDateTime());
        for (QuestionDto questionDto : examDto.questions()) {
            validateExamQuestionInput(questionDto);
        }
        for (UserDto userDto : examDto.candidates()) {
            validateEmail(userDto.email());
        }
    }

    @Override
    public void validateExamQuestionInput(QuestionDto questionDto) throws Exception {
        boolean isExamQuestionInputInvalid = isExamQuestionInputInvalid(questionDto);
        if (isExamQuestionInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
    }

    @Override
    public void validateUpdateExamDetailsInput(ExamDto examDto) throws Exception {
        boolean isUpdateExamDetailsInputInvalid = isUpdateExamDetailsInputInvalid(examDto);
        if (isUpdateExamDetailsInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        validateExamStartDateTime(examDto.startDateTime());
    }

    @Override
    public void validateUpdateExamCandidatesInput(ExamDto examDto) throws Exception {
        boolean isUpdateExamCandidatesInputInvalid = isUpdateExamCandidatesInputInvalid(examDto);
        if (isUpdateExamCandidatesInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        for (UserDto userDto : examDto.candidates()) {
            validateEmail(userDto.email());
        }
    }

    @Override
    public void validateUpdateExamQuestionsInput(ExamDto examDto) throws Exception {
        boolean isUpdateExamQuestionsInputInvalid = isUpdateExamQuestionsInputInvalid(examDto);
        if (isUpdateExamQuestionsInputInvalid) {
            throw new BadRequestError("Invalid data");
        }
        for (QuestionDto questionDto : examDto.questions()) {
            validateExamQuestionInput(questionDto);
        }
    }


//    helper methods for internal check

    private boolean isEmailValid(String email) {
        return emailRegex
                .matcher(email)
                .matches();
    }

    private boolean isPasswordValid(String password) {
        return passwordRegex
                .matcher(password)
                .matches();
    }

    private boolean isPageSizeValid(Integer pageSize) {
        return (
                pageSize >= 0 &&
                        pageSize <= 20
        );
    }

    private boolean isRoleValid(String role) {
        role = role.toUpperCase();
        return (
                role.equals(Role.STUDENT.name()) ||
                        role.equals(Role.TEACHER.name())
        );
    }

    private boolean isQuestionTypeValid(String type) {
        type = type.toUpperCase();
        return (
                type.equals(QuestionType.MCQ.name()) ||
                        type.equals(QuestionType.MSQ.name())
        );
    }

    private boolean isExamStartDateTimeValid(LocalDateTime examStartDateTime) {
        return LocalDateTime
                .now()
                .plusMinutes(30)
                .isBefore(examStartDateTime);
    }

    private boolean isRegisterInputInvalid(RegisterRequestDto registerRequestDto) {
        return (
                registerRequestDto.email() == null ||
                        registerRequestDto.password() == null ||
                        registerRequestDto.firstName() == null ||
                        registerRequestDto.lastName() == null ||
                        registerRequestDto.role() == null
        );
    }

    private boolean isLoginInputInvalid(LoginRequestDto loginRequestDto) {
        return (
                loginRequestDto.email() == null ||
                        loginRequestDto.password() == null
        );
    }

    private boolean isVerifyEmailInputInvalid(VerifyEmailRequestDto verifyEmailRequestDto) {
        return (
                verifyEmailRequestDto.email() == null ||
                        verifyEmailRequestDto.otp() == null
        );
    }

    private boolean isUpdateUserNameInputInvalid(UserDto userDto) {
        return (
                userDto.firstName() == null ||
                        userDto.lastName() == null
        );
    }

    private boolean isCreateSubtopicInputInvalid(SubtopicDto subtopicDto) {
        return (
                subtopicDto.name() == null ||
                        subtopicDto.subject() == null ||
                        subtopicDto.subject().id() == null
        );
    }

    private boolean isUpdateSubtopicInputInvalid(SubtopicDto subtopicDto) {
        return (
                subtopicDto.name() == null ||
                        subtopicDto.subject() == null ||
                        subtopicDto.subject().id() == null
        );
    }

    private boolean isCreateSubjectInputInvalid(SubjectDto subjectDto) {
        return (
                subjectDto.name() == null
        );
    }

    private boolean isUpdateSubjectInputInvalid(SubjectDto subjectDto) {
        return (
                subjectDto.name() == null
        );
    }

    private boolean isQuestionInputInvalid(QuestionDto questionDto) {
        return (
                questionDto.type() == null ||
                        questionDto.isPublic() == null ||
                        questionDto.statement() == null ||
                        questionDto.subtopic() == null ||
                        questionDto.subtopic().id() == null ||
                        questionDto.options() == null ||
                        questionDto.options().size() != 4 ||
                        questionDto.solution() == null
        );
    }

    private boolean isCreateOptionInputInvalid(OptionDto optionDto) {
        return (
                optionDto.statement() == null ||
                        optionDto.isCorrect() == null
        );
    }

    private boolean isUpdateOptionInputInvalid(OptionDto optionDto) {
        return (
                optionDto.id() == null ||
                        optionDto.statement() == null ||
                        optionDto.isCorrect() == null
        );
    }

    private boolean isCreateSolutionInputInvalid(SolutionDto solutionDto) {
        return (
                solutionDto.statement() == null
        );
    }

    private boolean isUpdateSolutionInputInvalid(SolutionDto solutionDto) {
        return (
                solutionDto.id() == null ||
                        solutionDto.statement() == null
        );
    }

    private boolean isCreateExamInputInvalid(ExamDto examDto) {
        return (
                examDto.title() == null ||
                        examDto.startDateTime() == null ||
                        examDto.durationInMinutes() == null ||
                        examDto.questions() == null ||
                        examDto.questions().isEmpty() ||
                        examDto.candidates() == null ||
                        examDto.candidates().isEmpty()
        );
    }

    private boolean isExamQuestionInputInvalid(QuestionDto questionDto) {
        return (
                questionDto.positiveMarks() == null ||
                        questionDto.negativeMarks() == null ||
                        (
                                questionDto.id() == null &&
                                        isQuestionInputInvalid(questionDto)
                        )
        );
    }

    private boolean isUpdateExamDetailsInputInvalid(ExamDto examDto) {
        return (
                examDto.id() == null ||
                        examDto.title() == null ||
                        examDto.startDateTime() == null ||
                        examDto.durationInMinutes() == null
        );
    }

    private boolean isUpdateExamCandidatesInputInvalid(ExamDto examDto) {
        return (
                examDto.id() == null ||
                        examDto.candidates() == null ||
                        examDto.candidates().isEmpty()
        );
    }

    private boolean isUpdateExamQuestionsInputInvalid(ExamDto examDto) {
        return (
                examDto.id() == null ||
                        examDto.questions() == null ||
                        examDto.questions().isEmpty()
        );
    }

}
