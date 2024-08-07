package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.CreateQuestionDto;
import com.example.quizquadrant.dto.PracticeOptionDto;
import com.example.quizquadrant.dto.PracticeQuestionDto;
import com.example.quizquadrant.dto.PracticeSolutionDto;
import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SubtopicService subtopicService;
    private final ImageService imageService;
    private  final SubjectService subjectService;
    private final SolutionService solutionService;
    private  final OptionService optionService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, SubtopicService subtopicService, ImageService imageService, SubjectService subjectService, SolutionService solutionService, OptionService optionService) {
        this.questionRepository = questionRepository;
        this.subtopicService = subtopicService;
        this.imageService = imageService;
        this.subjectService = subjectService;
        this.solutionService = solutionService;
        this.optionService = optionService;
    }

    public List<PracticeQuestionDto> getQuestionsBySubtopic(Long subjectId, Long subtopicId, Integer pageNumber) {
        System.out.println("page no "+pageNumber);

        Subtopic subtopic = subtopicService.getSubtopicById(subjectId, subtopicId);
        if(subtopic != null) {
            int pageSize = 5;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Question> questionPage = this.questionRepository.findQuestionsBySubtopic(subtopic, pageable);
            List<Question> questions = questionPage.getContent();
            List<PracticeQuestionDto> questionDtos = new ArrayList<>();
            for(Question question: questions) {
                PracticeSolutionDto solutionDto = new PracticeSolutionDto(
                        question.getSolution().getId(),
                        question.getSolution().getStatement(),
                        question.getSolution().getHasImage(),
                        (question.getSolution().getHasImage() ? imageService.getImageById(question.getSolution().getId(), ImageTypes.SOLUTION) : "")
                );
                List<PracticeOptionDto> optionDtos = new ArrayList<>();
                for(Option option: question.getOptions()) {
                    PracticeOptionDto optionDto = new PracticeOptionDto(
                            option.getId(),
                            option.getStatement(),
                            option.getHasImage(),
                            option.getIsCorrect(),
                            (option.getHasImage() ? imageService.getImageById(option.getId(), ImageTypes.OPTION) : "")
                    );
                    optionDtos.add(optionDto);
                }
                PracticeQuestionDto questionDto = new PracticeQuestionDto(
                        question.getId(),
                        question.getPositiveMarks(),
                        question.getNegativeMarks(),
                        question.getStatement(),
                        question.getType(),
                        question.getSubtopic().getSubtopicName(),
                        question.getSubtopic().getSubject().getSubName(),
                        solutionDto,
                        optionDtos,
                        (question.getHasImage() ? imageService.getImageById(question.getId(), ImageTypes.QUESTION) : "")
                );
                questionDtos.add(questionDto);
            }
            return questionDtos;
        } else {
            return null;
        }
    }

    public List<PracticeQuestionDto> getQuestionsBySubject(Long subjectId, Integer pageNumber) {
        List<Subtopic> subtopics = this.subjectService.getSubjectById(subjectId).getSubtopics();
        if(!subtopics.isEmpty()) {
            int pageSize = 5;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<Question> questionPage = this.questionRepository.findQuestionsBySubtopics(subtopics, pageable);
            List<Question> questions = questionPage.getContent();
            List<PracticeQuestionDto> questionDtos = new ArrayList<>();
            for(Question question: questions) {
                PracticeSolutionDto solutionDto = new PracticeSolutionDto(
                        question.getSolution().getId(),
                        question.getSolution().getStatement(),
                        question.getSolution().getHasImage(),
                        (question.getSolution().getHasImage() ? imageService.getImageById(question.getSolution().getId(), ImageTypes.SOLUTION) : "")
                );
                List<PracticeOptionDto> optionDtos = new ArrayList<>();
                for(Option option: question.getOptions()) {
                    PracticeOptionDto optionDto = new PracticeOptionDto(
                            option.getId(),
                            option.getStatement(),
                            option.getHasImage(),
                            option.getIsCorrect(),
                            (option.getHasImage() ? imageService.getImageById(option.getId(), ImageTypes.OPTION) : "")
                    );
                    optionDtos.add(optionDto);
                }
                PracticeQuestionDto questionDto = new PracticeQuestionDto(
                        question.getId(),
                        question.getPositiveMarks(),
                        question.getNegativeMarks(),
                        question.getStatement(),
                        question.getType(),
                        question.getSubtopic().getSubtopicName(),
                        question.getSubtopic().getSubject().getSubName(),
                        solutionDto,
                        optionDtos,
                        (question.getHasImage() ? imageService.getImageById(question.getId(), ImageTypes.QUESTION) : "")
                );
                questionDtos.add(questionDto);
            }
            return questionDtos;
        } else {
            return null;
        }
    }

    public Question createQuestion(CreateQuestionDto createQuestionDto) {
        Solution solution = solutionService.createSolution(
                !createQuestionDto.solutionImageURL().isEmpty(),
                createQuestionDto.solutionStatement()
        );

        Subtopic subtopic = subtopicService.getSubtopicById(
                createQuestionDto.subjectId(),
                createQuestionDto.subtopicId()
        );

        Question question = new Question(
                createQuestionDto.positiveMarks(),
                createQuestionDto.negativeMarks(),
                createQuestionDto.questionStatement(),
                createQuestionDto.type(),
                !createQuestionDto.questionImageURL().isEmpty(),
                subtopic,
                solution
        );
        question = questionRepository.save(question);

        Option optionA = optionService.createOption(
                createQuestionDto.optionAStatement(),
                !createQuestionDto.optionAImageURL().isEmpty(),
                createQuestionDto.correctAnswer().contains("A"),
                question
        );

        Option optionB = optionService.createOption(
                createQuestionDto.optionBStatement(),
                !createQuestionDto.optionBImageURL().isEmpty(),
                createQuestionDto.correctAnswer().contains("B"),
                question
        );

        Option optionC = optionService.createOption(
                createQuestionDto.optionCStatement(),
                !createQuestionDto.optionCImageURL().isEmpty(),
                createQuestionDto.correctAnswer().contains("C"),
                question
        );

        Option optionD = optionService.createOption(
                createQuestionDto.optionDStatement(),
                !createQuestionDto.optionDImageURL().isEmpty(),
                createQuestionDto.correctAnswer().contains("D"),
                question
        );

        if(question.getHasImage()) {
            Image imgQues = imageService.createImage(ImageTypes.QUESTION, question.getId(), createQuestionDto.questionImageURL());
        }
        if(solution.getHasImage()) {
            Image imgSol = imageService.createImage(ImageTypes.SOLUTION, solution.getId(), createQuestionDto.solutionImageURL());
        }
        if(optionA.getHasImage()) {
            Image imgOptionA = imageService.createImage(ImageTypes.OPTION, optionA.getId(), createQuestionDto.optionAImageURL());
        }
        if(optionB.getHasImage()) {
            Image imgOptionB = imageService.createImage(ImageTypes.OPTION, optionB.getId(), createQuestionDto.optionBImageURL());
        }
        if(optionC.getHasImage()) {
            Image imgOptionC = imageService.createImage(ImageTypes.OPTION, optionC.getId(), createQuestionDto.optionCImageURL());
        }
        if(optionD.getHasImage()) {
            Image imgOptionD = imageService.createImage(ImageTypes.OPTION, optionD.getId(), createQuestionDto.optionDImageURL());
        }

        return question;
    }

    public MockExamDto getQuestionIdsBySubtopics(CreateMockTestDto createMockTestDto, Integer total) {
        int pageSize = total;
        Pageable pageable = PageRequest.of(0, pageSize);
        List<Subtopic> subtopics = new ArrayList<>();
        for(SubtopicDto subtopicDto : createMockTestDto.subtopicDtos()) {
            Subtopic subtopic = subtopicService.getSubtopicById(subtopicDto.subId(), subtopicDto.subtopicId());
            subtopics.add(subtopic);
        }
        Optional<List<Long>> Qids = questionRepository.findQuestionIdsBySubtopics(subtopics, pageable);
        Long totalMarks = questionRepository.getSumOfPositiveMarks(Qids.orElse(null));

        MockExamDto mock = new MockExamDto(Qids.orElse(null), totalMarks);
        return mock;
    }


//    public Long totalNumberOfQuestions (CreateMockTestDto createMockTestDto) {
//        List<Subtopic> subtopics = new ArrayList<>();
//        for(SubtopicDto subtopicDto : createMockTestDto.subtopicDtos()) {
//            Subtopic subtopic = subtopicService.getSubtopicById(subtopicDto.subId(), subtopicDto.subtopicId());
//            subtopics.add(subtopic);
//        }
//        return questionRepository.countAllBySubtopic(subtopics);
//    }



    //QUESTION BY ID
    public PracticeQuestionDto getQuestionById(Long questionId) {

        Question question = this.questionRepository.findById(questionId).get();
        PracticeSolutionDto solutionDto = new PracticeSolutionDto(
                question.getSolution().getId(),
                question.getSolution().getStatement(),
                question.getSolution().getHasImage(),
                (question.getSolution().getHasImage() ? imageService.getImageById(question.getSolution().getId(), ImageTypes.SOLUTION) : "")
        );
        List<PracticeOptionDto> optionDtos = new ArrayList<>();
        for(Option option: question.getOptions()) {
            PracticeOptionDto optionDto = new PracticeOptionDto(
                    option.getId(),
                    option.getStatement(),
                    option.getHasImage(),
                    option.getIsCorrect(),
                    (option.getHasImage() ? imageService.getImageById(option.getId(), ImageTypes.OPTION) : "")
            );
            optionDtos.add(optionDto);
        }
        PracticeQuestionDto questionDto = new PracticeQuestionDto(
                question.getId(),
                question.getPositiveMarks(),
                question.getNegativeMarks(),
                question.getStatement(),
                question.getType(),
                question.getSubtopic().getSubtopicName(),
                question.getSubtopic().getSubject().getSubName(),
                solutionDto,
                optionDtos,
                (question.getHasImage() ? imageService.getImageById(question.getId(), ImageTypes.QUESTION) : "")
        );

        return questionDto;

    }


}
