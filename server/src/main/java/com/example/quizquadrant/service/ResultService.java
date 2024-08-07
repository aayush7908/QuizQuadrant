package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.LeaderBoardDto;
import com.example.quizquadrant.model.*;
import com.example.quizquadrant.repository.ExamRepository;
import com.example.quizquadrant.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final ExamResponsesService examResponsesService;
    private final PrivateOptionService privateOptionService;
    private final PrivateQuestionService privateQuestionService;

    @Autowired
    public ResultService(ResultRepository resultRepository, ExamRepository examRepository, ExamResponsesService examResponsesService, PrivateOptionService privateOptionService, PrivateQuestionService privateQuestionService) {
        this.resultRepository = resultRepository;
        this.examResponsesService = examResponsesService;
        this.privateOptionService = privateOptionService;
        this.privateQuestionService = privateQuestionService;
    }

    public List<Result> createResults(List<User> users, Exam exam) {
        List<Result> results = new ArrayList<>();

        for (User user : users) {
            Result result = new Result(
                    user,
                    exam,
                    false,
                    false,
                    0
            );
            result = resultRepository.save(result);

            results.add(result);
        }

        return results;
    }

    public void markUserPresent(User user, Exam exam) {
        resultRepository.updateIsPresentResultByUserAndExam(user, exam);
    }

    public void markExamFinished(User user, Exam exam) {
        resultRepository.updateIsFinishedResultByUserAndExam(user, exam);
    }

    public void updateMarksOfUser(User user, Exam exam, Integer marks) {
        resultRepository.updateResultMarksByUserAndExam(user, exam, marks);
    }

    public Boolean calculateResult(Exam exam) {
        if (exam != null) {
            for (Result result : exam.getExamResults()) {
                User user = result.getUser();
                int userMarks = 0;


                for (PrivateQuestion privateQuestion : exam.getPrivateQuestions()) {
                    ExamResponses examResponse = examResponsesService.getExamResponsesByUserAndQuestion(user, privateQuestion);
                    if (examResponse != null) {
                        int questionMark = privateQuestion.getNegativeMarks();
                        privateOptionService.sortPrivateOptions(privateQuestion.getPrivateOptions());

                        if ((examResponse.getOptionAMarked() == privateQuestion.getPrivateOptions().get(0).getIsCorrect())
                                && (examResponse.getOptionBMarked() == privateQuestion.getPrivateOptions().get(1).getIsCorrect())
                                && (examResponse.getOptionCMarked() == privateQuestion.getPrivateOptions().get(2).getIsCorrect())
                                && (examResponse.getOptionDMarked() == privateQuestion.getPrivateOptions().get(3).getIsCorrect())
                        ) {
                            questionMark = privateQuestion.getPositiveMarks();
                        }
                        userMarks += questionMark;
                    }
                }
                examResponsesService.removeResponses(user, exam.getPrivateQuestions());
                updateMarksOfUser(user, exam, userMarks);
            }

            privateQuestionService.transferPrivateQuestionToQuestion(exam.getPrivateQuestions());

            return true;
        } else {
            return false;
        }
    }

    public List<LeaderBoardDto> getLeaderBoard(Exam exam) {
        List<Result> topResults = resultRepository.findTop10ResultsByIsPresentAndExamOrderByMarksDesc(true, exam);
        List<LeaderBoardDto> lds = new ArrayList<>();
        for (Result result : topResults) {
            LeaderBoardDto ld = new LeaderBoardDto(result.getUser().getId(), result.getUser().getName(), result.getMarks(), result.getIsPresent());
            lds.add(ld);
        }
        return lds;

    }

    public List<LeaderBoardDto> getAllResult(Exam exam) {
        List<Result> results = resultRepository.findByExamOrderByMarksDesc(exam);
        List<LeaderBoardDto> lds = new ArrayList<>();
        for (Result result : results) {
            LeaderBoardDto ld = new LeaderBoardDto(result.getUser().getId(), result.getUser().getName(), result.getMarks(), result.getIsPresent());
            lds.add(ld);
        }
        return lds;

    }

    public Boolean getIsFinished(User user, Exam exam) {
        Result res = resultRepository.findResultsByExamAndUser(exam, user);
        return res.getIsFinished();
    }

}
