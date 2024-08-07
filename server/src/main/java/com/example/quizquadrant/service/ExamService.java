package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.*;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final PrivateQuestionService privateQuestionService;
    private final UserService userService;
    private final ResultService resultService;


    @Autowired
    public ExamService(ExamRepository examRepository, PrivateQuestionService privateQuestionService, UserService userService, ResultService resultService) {
        this.examRepository = examRepository;
        this.privateQuestionService = privateQuestionService;
        this.userService = userService;
        this.resultService = resultService;
    }

    public Exam createExam(Long creatorId, CreateExamDto createExamDto) {
//        TODO get userID from JWT token
        User user = userService.getUserById(creatorId);

        String[] date = createExamDto.startDate().split("-");   // yyyy-mm-dd
        String[] time = createExamDto.startTime().split(":");   // 20:00
        int totalMarks = 0;
        for(CreateQuestionDto q : createExamDto.questionDtos()) {
            totalMarks += q.positiveMarks();
        }

        Exam exam = new Exam(
                createExamDto.title(),
                LocalDateTime.of(
                        Integer.parseInt(date[0]),  // year
                        Integer.parseInt(date[1]),  // month
                        Integer.parseInt(date[2]),  // day
                        Integer.parseInt(time[0]),  // hour
                        Integer.parseInt(time[1])   // minute
                ),
                false,
                createExamDto.duration(),
                totalMarks,
                user
        );
        exam = examRepository.save(exam);

        List<PrivateQuestion> privateQuestions = privateQuestionService.createPrivateQuestions(createExamDto.questionDtos(), exam);
//        exam.setPrivateQuestions(privateQuestions);

        List<User> users = userService.getUsersByEmailId(createExamDto.emailIds());
        List<Result> results = resultService.createResults(users, exam);
//        exam.setExamResults(results);

        return exam;
    }

    public ExamDto getExamById(Long userId, Long examId) {
//        TODO get userID from JWT token & authorize whether his email is included in EXAM ...

        User user = userService.getUserById(userId);
        Optional<Exam> examOptional = examRepository.findById(examId);
        ExamDto examDto = null;

        if(examOptional.isPresent()) {
            Exam exam = examOptional.get();
            if(!resultService.getIsFinished(user,exam)) {
                List<Long> questionIds = new ArrayList<>();
                for (PrivateQuestion privateQuestion : exam.getPrivateQuestions()) {
                    questionIds.add(privateQuestion.getId());
                }

                examDto = new ExamDto(
                        exam.getId(),
                        exam.getTitle(),
                        exam.getDuration(),
                        exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                        exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                        user.getName(),
                        user.getEmail(),
                        questionIds
                );

                resultService.markUserPresent(user, exam);
            }
        }

        return examDto;
    }

    public Exam getExamById (Long examId) {
        return
                examRepository.findById(examId).orElse(null);

    }

    public Boolean calculateResult(Long examId) {
        examRepository.markResultGenerated(examId);
        return resultService.calculateResult(this.getExamById(examId));
    }

    public List<LeaderBoardDto> getLeaderboard (Long examId) {
        return resultService.getLeaderBoard(this.getExamById(examId));
    }

    public List<LeaderBoardDto> getAllResult (Long examId) {
        return resultService.getAllResult(this.getExamById(examId));
    }

    public void setExamFinished(Long examId, Long userId) {
        resultService.markExamFinished(userService.getUserById(userId),this.getExamById(examId));
    }
}