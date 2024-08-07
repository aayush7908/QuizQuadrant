package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.CreateQuestionDto;
import com.example.quizquadrant.dto.ExamOptionDto;
import com.example.quizquadrant.dto.ExamQuestionDto;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.dto.ExamOptionDto;
import com.example.quizquadrant.dto.ExamQuestionDto;
import com.example.quizquadrant.model.PrivateOption;
import com.example.quizquadrant.model.PrivateQuestion;
import com.example.quizquadrant.repository.PrivateQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrivateQuestionService {

    private final PrivateQuestionRepository privateQuestionRepository;
    private final ImageService imageService;
    private final PrivateSolutionService privateSolutionService;
    private final SubtopicService subtopicService;
    private final PrivateOptionService privateOptionService;
    private final UserService userService;
    private final ExamResponsesService examResponsesService;
    private final QuestionService questionService;


    @Autowired
    public PrivateQuestionService(PrivateQuestionRepository privateQuestionRepository, ImageService imageService, PrivateSolutionService privateSolutionService, SubtopicService subtopicService, PrivateOptionService privateOptionService, UserService userService, ExamResponsesService examResponsesService, QuestionService questionService) {
        this.privateQuestionRepository = privateQuestionRepository;
        this.imageService = imageService;
        this.privateSolutionService = privateSolutionService;
        this.subtopicService = subtopicService;
        this.privateOptionService = privateOptionService;
        this.userService = userService;
        this.examResponsesService = examResponsesService;
        this.questionService = questionService;
    }

    public ExamQuestionDto getPrivateQuestionById(Long userId, Long privateQuestionId) {
        Optional<PrivateQuestion> privateQuestionOptional = privateQuestionRepository.findById(privateQuestionId);
        User user = userService.getUserById(userId);
        if(privateQuestionOptional.isPresent() && user != null) {
            PrivateQuestion privateQuestion = privateQuestionOptional.get();
            privateOptionService.sortPrivateOptions(privateQuestion.getPrivateOptions());
            ExamResponses examResponse = examResponsesService.getExamResponsesByUserAndQuestion(user, privateQuestion);
            List<Boolean> markedOptions = new ArrayList<>();
            if(examResponse != null) {
                markedOptions.add(examResponse.getOptionAMarked());
                markedOptions.add(examResponse.getOptionBMarked());
                markedOptions.add(examResponse.getOptionCMarked());
                markedOptions.add(examResponse.getOptionDMarked());
            }
            List<ExamOptionDto> optionDtos = new ArrayList<>();
            for(int i = 0; i < privateQuestion.getPrivateOptions().size(); i++) {
                ExamOptionDto examOptionDto = new ExamOptionDto(
                        privateQuestion.getPrivateOptions().get(i).getId(),
                        privateQuestion.getPrivateOptions().get(i).getStatement(),
                        privateQuestion.getPrivateOptions().get(i).getHasImage(),
                        (privateQuestion.getPrivateOptions().get(i).getHasImage() ? imageService.getImageById(privateQuestion.getPrivateOptions().get(i).getId(), ImageTypes.PRIVATE_OPTION) : ""),
                        (examResponse != null && markedOptions.get(i))
                );
                optionDtos.add(examOptionDto);
            }
            return new ExamQuestionDto(
                    privateQuestion.getId(),
                    privateQuestion.getStatement(),
                    privateQuestion.getType(),
                    privateQuestion.getHasImage(),
                    privateQuestion.getPositiveMarks(),
                    privateQuestion.getNegativeMarks(),
                    optionDtos,
                    (privateQuestion.getHasImage() ? imageService.getImageById(privateQuestion.getId(), ImageTypes.PRIVATE_QUESTION) : "")
            );
        } else {
            return null;
        }
    }

    public List<PrivateQuestion> createPrivateQuestions(List<CreateQuestionDto> createQuestionDtos, Exam exam) {
        List<PrivateQuestion> privateQuestions = new ArrayList<>();

        for(CreateQuestionDto createQuestionDto: createQuestionDtos) {
            PrivateSolution privateSolution = privateSolutionService.createPrivateSolution(
                    !createQuestionDto.solutionImageURL().isEmpty(),
                    createQuestionDto.solutionStatement()
            );

            Subtopic subtopic = subtopicService.getSubtopicById(
                    createQuestionDto.subjectId(),
                    createQuestionDto.subtopicId()
            );

            PrivateQuestion privateQuestion = new PrivateQuestion(
                    createQuestionDto.questionStatement(),
                    createQuestionDto.type(),
                    !createQuestionDto.questionImageURL().isEmpty(),
                    createQuestionDto.positiveMarks(),
                    createQuestionDto.negativeMarks(),
                    subtopic,
                    exam,
                    privateSolution
            );
            privateQuestion = privateQuestionRepository.save(privateQuestion);

            PrivateOption privateOptionA = privateOptionService.createPrivateOption(
                    createQuestionDto.optionAStatement(),
                    !createQuestionDto.optionAImageURL().isEmpty(),
                    createQuestionDto.correctAnswer().contains("A"),
                    privateQuestion
            );

            PrivateOption privateOptionB = privateOptionService.createPrivateOption(
                    createQuestionDto.optionBStatement(),
                    !createQuestionDto.optionBImageURL().isEmpty(),
                    createQuestionDto.correctAnswer().contains("B"),
                    privateQuestion
            );

            PrivateOption privateOptionC = privateOptionService.createPrivateOption(
                    createQuestionDto.optionCStatement(),
                    !createQuestionDto.optionCImageURL().isEmpty(),
                    createQuestionDto.correctAnswer().contains("C"),
                    privateQuestion
            );

            PrivateOption privateOptionD = privateOptionService.createPrivateOption(
                    createQuestionDto.optionDStatement(),
                    !createQuestionDto.optionDImageURL().isEmpty(),
                    createQuestionDto.correctAnswer().contains("D"),
                    privateQuestion
            );

            if(privateQuestion.getHasImage()) {
                Image imgQues = imageService.createImage(ImageTypes.PRIVATE_QUESTION, privateQuestion.getId(), createQuestionDto.questionImageURL());
            }
            if(privateSolution.getHasImage()) {
                Image imgSol = imageService.createImage(ImageTypes.PRIVATE_SOLUTION, privateSolution.getId(), createQuestionDto.solutionImageURL());
            }
            if(privateOptionA.getHasImage()) {
                Image imgOptionA = imageService.createImage(ImageTypes.PRIVATE_OPTION, privateOptionA.getId(), createQuestionDto.optionAImageURL());
            }
            if(privateOptionB.getHasImage()) {
                Image imgOptionB = imageService.createImage(ImageTypes.PRIVATE_OPTION, privateOptionB.getId(), createQuestionDto.optionBImageURL());
            }
            if(privateOptionC.getHasImage()) {
                Image imgOptionC = imageService.createImage(ImageTypes.PRIVATE_OPTION, privateOptionC.getId(), createQuestionDto.optionCImageURL());
            }
            if(privateOptionD.getHasImage()) {
                Image imgOptionD = imageService.createImage(ImageTypes.PRIVATE_OPTION, privateOptionD.getId(), createQuestionDto.optionDImageURL());
            }

            privateQuestions.add(privateQuestion);
        }

        return privateQuestions;
    }

    public void transferPrivateQuestionToQuestion(List<PrivateQuestion> privateQuestionsList) {
        for(PrivateQuestion q : privateQuestionsList) {

            List<String> correctAnswers = new ArrayList<>();
            privateOptionService.sortPrivateOptions(q.getPrivateOptions());

            for(int i=0;i<4;i++) {
                char c = (char)('A'+i);
                if(q.getPrivateOptions().get(i).getIsCorrect()) {
                    correctAnswers.add(""+(c));
                }
            }

            String questionImgUrl = "";
            String optionAImgUrl = "";
            String optionBImgUrl = "";
            String optionCImgUrl = "";
            String optionDImgUrl = "";
            String solutionImgUrl = "";

            if(q.getHasImage()) {
                questionImgUrl = imageService.getImageById(q.getId(), ImageTypes.PRIVATE_QUESTION);
            }

            if(q.getPrivateOptions().get(0).getHasImage()) {
                optionAImgUrl = imageService.getImageById(q.getPrivateOptions().get(0).getId(), ImageTypes.PRIVATE_OPTION);
            }

            if(q.getPrivateOptions().get(1).getHasImage()) {
                optionBImgUrl = imageService.getImageById(q.getPrivateOptions().get(1).getId(), ImageTypes.PRIVATE_OPTION);
            }

            if(q.getPrivateOptions().get(2).getHasImage()) {
                optionCImgUrl = imageService.getImageById(q.getPrivateOptions().get(2).getId(), ImageTypes.PRIVATE_OPTION);
            }

            if(q.getPrivateOptions().get(3).getHasImage()) {
                optionDImgUrl = imageService.getImageById(q.getPrivateOptions().get(3).getId(), ImageTypes.PRIVATE_OPTION);
            }

            if(q.getPrivateSolution().getHasImage()) {
                solutionImgUrl = imageService.getImageById(q.getPrivateSolution().getId(), ImageTypes.PRIVATE_SOLUTION);
            }

            CreateQuestionDto createQuestionDto = new CreateQuestionDto(
                    q.getType(),
                    q.getSubtopic().getSubject().getId(),
                    q.getSubtopic().getId(),
                    q.getPositiveMarks(),
                    q.getNegativeMarks(),
                    q.getStatement(),
                    questionImgUrl,
                    q.getPrivateOptions().get(0).getStatement(),
                    optionAImgUrl,
                    q.getPrivateOptions().get(1).getStatement(),
                    optionBImgUrl,
                    q.getPrivateOptions().get(2).getStatement(),
                    optionCImgUrl,
                    q.getPrivateOptions().get(3).getStatement(),
                    optionDImgUrl,
                    q.getPrivateSolution().getStatement(),
                    solutionImgUrl,
                    correctAnswers
            );

            questionService.createQuestion(createQuestionDto);

            imageService.removeImage(ImageTypes.PRIVATE_QUESTION,q.getId());
            imageService.removeImage(ImageTypes.PRIVATE_OPTION,q.getPrivateOptions().get(0).getId());
            imageService.removeImage(ImageTypes.PRIVATE_OPTION,q.getPrivateOptions().get(1).getId());
            imageService.removeImage(ImageTypes.PRIVATE_OPTION,q.getPrivateOptions().get(2).getId());
            imageService.removeImage(ImageTypes.PRIVATE_OPTION,q.getPrivateOptions().get(3).getId());
            imageService.removeImage(ImageTypes.PRIVATE_SOLUTION,q.getPrivateSolution().getId());
            privateOptionService.removePrivateOptions(q.getPrivateOptions());
            this.removePrivateQuestion(q);
            privateSolutionService.removePrivateSolution(q.getPrivateSolution());
        }
//        removePrivateQuestions(privateQuestionsList);
    }

    public void removePrivateQuestions(List<PrivateQuestion> privateQuestions) {
        privateQuestionRepository.deletePrivateQuestions(privateQuestions);
    }

    public void removePrivateQuestion(PrivateQuestion privateQuestion) {
        privateQuestionRepository.deletePrivateQuestion(privateQuestion);
    }

}
