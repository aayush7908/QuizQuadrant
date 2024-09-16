package com.example.quizquadrant.utils.validation;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.type.QuestionType;
import com.example.quizquadrant.model.type.Role;
import com.example.quizquadrant.utils.error.BadRequestError;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private final Pattern emailRegex = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    private final Pattern passwordRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$");

    @Override
    public UUID validateAndGetUUID(String id) throws Exception {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validatePageNumber(Integer pageNumber) throws Exception {
        boolean isPageNumberValid = isPageNumberValid(pageNumber);
        if (!isPageNumberValid) {
            throw new BadRequestError("Invalid Page Number");
        }
    }

    @Override
    public void validatePageSize(Integer pageSize) throws Exception {
        boolean isPageSizeValid = isPageSizeValid(pageSize);
        if (!isPageSizeValid) {
            throw new BadRequestError("Invalid Page Size");
        }
    }

    @Override
    public void validateEmail(
            String email
    ) throws Exception {
        boolean isEmailValid = isEmailValid(email);
        if (!isEmailValid) {
            throw new BadRequestError("Invalid Email Address");
        }
    }

    @Override
    public void validatePassword(
            String password
    ) throws Exception {
        boolean isPasswordValid = isPasswordValid(password);
        if (!isPasswordValid) {
            throw new BadRequestError("Invalid Password");
        }
    }

    @Override
    public void validateName(
            String name
    ) throws Exception {
        boolean isNameValid = isNameValid(name);
        if (!isNameValid) {
            throw new BadRequestError("Invalid Name");
        }
    }

    @Override
    public void validateUserRole(
            String role
    ) throws Exception {
        boolean isRoleValid = isRoleValid(role);
        if (!isRoleValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateRegisterInput(
            RegisterRequestDto registerRequestDto
    ) throws Exception {
        validateEmail(registerRequestDto.email());
        validatePassword(registerRequestDto.password());
        validateName(registerRequestDto.firstName());
        validateName(registerRequestDto.lastName());
        validateUserRole(registerRequestDto.role());
    }

    @Override
    public void validateLoginInput(
            LoginRequestDto loginRequestDto
    ) throws Exception {
        validateEmail(loginRequestDto.email());
        validatePassword(loginRequestDto.password());
    }

    @Override
    public void validateUpdateUserNameInput(
            UpdateUserNameRequestDto updateUserNameRequestDto
    ) throws Exception {
        validateName(updateUserNameRequestDto.firstName());
        validateName(updateUserNameRequestDto.lastName());
    }

    @Override
    public void validateSubjectRequestInput(
            SubjectRequestDto subjectRequestDto
    ) throws Exception {
        boolean isSubjectRequestInputValid = isSubjectRequestInputValid(
                subjectRequestDto
        );
        if (!isSubjectRequestInputValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateSubtopicRequestInput(
            SubtopicRequestDto subtopicRequestDto
    ) throws Exception {
        boolean isSubtopicRequestInputValid = isSubtopicRequestInputValid(
                subtopicRequestDto
        );
        if (!isSubtopicRequestInputValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateQuestionType(
            String type
    ) throws Exception {
        boolean isQuestionTypeValid = isQuestionTypeValid(type);
        if (!isQuestionTypeValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateSolutionRequestInput(
            SolutionRequestDto solutionRequestDto
    ) throws Exception {
        boolean isSolutionRequestInputValid = isSolutionRequestInputValid(solutionRequestDto);
        if (!isSolutionRequestInputValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateOptionRequestInput(
            OptionRequestDto optionRequestDto
    ) throws Exception {
        boolean isOptionRequestInputValid = isOptionRequestInputValid(optionRequestDto);
        if (!isOptionRequestInputValid) {
            throw new BadRequestError("Invalid Data");
        }
    }

    @Override
    public void validateQuestionRequestInput(
            QuestionRequestDto questionRequestDto
    ) throws Exception {
        boolean isQuestionRequestInputValid = isQuestionRequestInputValid(questionRequestDto);
        if (!isQuestionRequestInputValid) {
            throw new BadRequestError("Invalid Data");
        }
        validateQuestionType(questionRequestDto.type());
        for (OptionRequestDto optionRequestDto : questionRequestDto.options()) {
            validateOptionRequestInput(optionRequestDto);
        }
        validateSolutionRequestInput(questionRequestDto.solution());
    }

//    @Override
//    public void validateExamStartDateTime(LocalDateTime examStartDateTime) throws Exception {
//        boolean isExamStartDateTimeValid = isExamStartDateTimeValid(examStartDateTime);
//        if (!isExamStartDateTimeValid) {
//            throw new BadRequestError("Exam can start only after 30 minutes of creation");
//        }
//    }
//
//    @Override
//    public void validateUpdateSubtopicInput(SubtopicDto subtopicDto) throws Exception {
//        boolean isUpdateSubtopicInputInvalid = isUpdateSubtopicInputInvalid(subtopicDto);
//        if (isUpdateSubtopicInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//    }
//
//    @Override
//    public void validateUpdateSubjectInput(SubjectDto subjectDto) throws Exception {
//        boolean isUpdateSubjectInputInvalid = isUpdateSubjectInputInvalid(subjectDto);
//        if (isUpdateSubjectInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//    }
//
//    @Override
//    public void validateUpdateQuestionInput(QuestionDto questionDto) throws Exception {
//        boolean isQuestionInputInvalid = isQuestionInputValid(questionDto);
//        if (isQuestionInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//        validateQuestionType(questionDto.type());
//        for (OptionDto optionDto : questionDto.options()) {
//            validateUpdateOptionInput(optionDto);
//        }
//        validateUpdateSolutionInput(questionDto.solution());
//    }
//
//    @Override
//    public void validateUpdateOptionInput(OptionDto optionDto) throws Exception {
//        boolean isUpdateOptionInputInvalid = isUpdateOptionInputInvalid(optionDto);
//        if (isUpdateOptionInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//    }
//
//    @Override
//    public void validateUpdateSolutionInput(SolutionDto solutionDto) throws Exception {
//        boolean isUpdateSolutionInputInvalid = isUpdateSolutionInputInvalid(solutionDto);
//        if (isUpdateSolutionInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//    }
//
//    @Override
//    public void validateCreateExamInput(ExamDto examDto) throws Exception {
//        boolean isCreateExamInputInvalid = isCreateExamInputInvalid(examDto);
//        if (isCreateExamInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//        validateExamStartDateTime(examDto.startDateTime());
//        for (QuestionDto questionDto : examDto.questions()) {
//            System.out.println("svdlknsfv");
//            validateExamQuestionInput(questionDto);
//        }
//        for (UserDto userDto : examDto.candidates()) {
//            validateEmail(userDto.email());
//        }
//    }
//
//    @Override
//    public void validateExamQuestionInput(QuestionDto questionDto) throws Exception {
//        boolean isExamQuestionInputInvalid = isExamQuestionInputInvalid(questionDto);
//        if (isExamQuestionInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//    }
//
//    @Override
//    public void validateUpdateExamDetailsInput(ExamDto examDto) throws Exception {
//        boolean isUpdateExamDetailsInputInvalid = isUpdateExamDetailsInputInvalid(examDto);
//        if (isUpdateExamDetailsInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//        validateExamStartDateTime(examDto.startDateTime());
//    }
//
//    @Override
//    public void validateUpdateExamCandidatesInput(ExamDto examDto) throws Exception {
//        boolean isUpdateExamCandidatesInputInvalid = isUpdateExamCandidatesInputInvalid(examDto);
//        if (isUpdateExamCandidatesInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//        for (UserDto userDto : examDto.candidates()) {
//            validateEmail(userDto.email());
//        }
//    }
//
//    @Override
//    public void validateUpdateExamQuestionsInput(ExamDto examDto) throws Exception {
//        boolean isUpdateExamQuestionsInputInvalid = isUpdateExamQuestionsInputInvalid(examDto);
//        if (isUpdateExamQuestionsInputInvalid) {
//            throw new BadRequestError("Invalid data");
//        }
//        for (QuestionDto questionDto : examDto.questions()) {
//            validateExamQuestionInput(questionDto);
//        }
//    }


    //    helper methods for internal check
    private boolean isPageNumberValid(
            Integer pageNumber
    ) {
        return (
                pageNumber >= 0
        );
    }

    private boolean isPageSizeValid(
            Integer pageSize
    ) {
        return (
                pageSize >= 5 &&
                        pageSize <= 20
        );
    }

    private boolean isEmailValid(
            String email
    ) {
        return emailRegex
                .matcher(email)
                .matches();
    }

    private boolean isPasswordValid(
            String password
    ) {
        return passwordRegex
                .matcher(password)
                .matches();
    }

    private boolean isNameValid(
            String name
    ) {
        return !(
                name == null ||
                        name.length() < 3 ||
                        name.length() > 20
        );
    }

    private boolean isRoleValid(String role) {
        role = role.toUpperCase();
        return (
                role.equals(Role.STUDENT.name()) ||
                        role.equals(Role.TEACHER.name())
        );
    }

    private boolean isSubjectRequestInputValid(
            SubjectRequestDto subjectRequestDto
    ) {
        return !(
                subjectRequestDto.name() == null ||
                        subjectRequestDto.name().length() < 3 ||
                        subjectRequestDto.name().length() > 20
        );
    }

    private boolean isSubtopicRequestInputValid(
            SubtopicRequestDto subtopicRequestDto
    ) {
        return !(
                subtopicRequestDto.name() == null ||
                        subtopicRequestDto.name().length() < 3 ||
                        subtopicRequestDto.name().length() > 20 ||
                        subtopicRequestDto.subjectId() == null ||
                        subtopicRequestDto.subjectId().toString().isEmpty()
        );
    }

    private boolean isQuestionTypeValid(String type) {
        type = type.toUpperCase();
        return (
                type.equals(QuestionType.MCQ.name()) ||
                        type.equals(QuestionType.MSQ.name())
        );
    }

    private boolean isSolutionRequestInputValid(
            SolutionRequestDto solutionRequestDto
    ) {
        return !(
                solutionRequestDto.statement() == null ||
                        solutionRequestDto.statement().length() < 10
        );
    }

    private boolean isOptionRequestInputValid(
            OptionRequestDto optionRequestDto
    ) {
        return !(
                optionRequestDto.statement() == null ||
                        optionRequestDto.isCorrect() == null
        );
    }

    private boolean isQuestionRequestInputValid(
            QuestionRequestDto questionRequestDto
    ) {
        return !(
                questionRequestDto.type() == null ||
                        questionRequestDto.isPublic() == null ||
                        questionRequestDto.statement() == null ||
                        questionRequestDto.statement().length() < 10 ||
                        questionRequestDto.subtopic() == null ||
                        questionRequestDto.subtopic().id() == null ||
                        questionRequestDto.options() == null ||
                        questionRequestDto.options().size() != 4 ||
                        questionRequestDto.solution() == null
        );
    }

//    private boolean isExamStartDateTimeValid(LocalDateTime examStartDateTime) {
//        return LocalDateTime
//                .now()
//                .plusMinutes(30)
//                .isBefore(examStartDateTime);
//    }
//
//    private boolean isLoginInputInvalid(LoginRequestDto loginRequestDto) {
//        return (
//                loginRequestDto.email() == null ||
//                        loginRequestDto.password() == null
//        );
//    }
//
//    private boolean isUpdateUserNameInputInvalid(UserDto userDto) {
//        return (
//                userDto.firstName() == null ||
//                        userDto.lastName() == null
//        );
//    }
//
//    private boolean isUpdateSubtopicInputInvalid(SubtopicDto subtopicDto) {
//        return (
//                subtopicDto.name() == null ||
//                        subtopicDto.subject() == null ||
//                        subtopicDto.subject().id() == null
//        );
//    }
//
//    private boolean isUpdateSubjectInputInvalid(SubjectDto subjectDto) {
//        return (
//                subjectDto.name() == null
//        );
//    }
//
//    private boolean isUpdateOptionInputInvalid(OptionDto optionDto) {
//        return (
//                optionDto.id() == null ||
//                        optionDto.statement() == null ||
//                        optionDto.isCorrect() == null
//        );
//    }
//
//    private boolean isUpdateSolutionInputInvalid(SolutionDto solutionDto) {
//        return (
//                solutionDto.id() == null ||
//                        solutionDto.statement() == null
//        );
//    }
//
//    private boolean isCreateExamInputInvalid(ExamDto examDto) {
//        return (
//                examDto.title() == null ||
//                        examDto.startDateTime() == null ||
//                        examDto.durationInMinutes() == null ||
//                        examDto.durationInMinutes() < 10 ||
//                        examDto.questions() == null ||
//                        examDto.questions().isEmpty() ||
//                        examDto.candidates() == null ||
//                        examDto.candidates().isEmpty()
//        );
//    }
//
//    private boolean isExamQuestionInputInvalid(QuestionDto questionDto) {
//        return (
//                questionDto.positiveMarks() == null ||
//                        questionDto.negativeMarks() == null ||
//                        (
//                                questionDto.id() == null &&
//                                        isQuestionInputValid(questionDto)
//                        )
//        );
//    }
//
//    private boolean isUpdateExamDetailsInputInvalid(ExamDto examDto) {
//        return (
//                examDto.id() == null ||
//                        examDto.title() == null ||
//                        examDto.startDateTime() == null ||
//                        examDto.durationInMinutes() == null
//        );
//    }
//
//    private boolean isUpdateExamCandidatesInputInvalid(ExamDto examDto) {
//        return (
//                examDto.id() == null ||
//                        examDto.candidates() == null ||
//                        examDto.candidates().isEmpty()
//        );
//    }
//
//    private boolean isUpdateExamQuestionsInputInvalid(ExamDto examDto) {
//        return (
//                examDto.id() == null ||
//                        examDto.questions() == null ||
//                        examDto.questions().isEmpty()
//        );
//    }

}
