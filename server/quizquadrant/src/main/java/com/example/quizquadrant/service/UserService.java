package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.UserProfileDto;
import com.example.quizquadrant.dto.UserProfileOngoingAndFutureExamDto;
import com.example.quizquadrant.dto.UserProfilePastExamDto;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Result;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> getUsersByEmailId(List<String> emailIds) {
        return userRepository.findUsersByEmail(emailIds);
    }

    public UserProfileDto getUserProfile(Long userId) {
//        TODO fetch userID from JWT token ...
        Long selfUserId = 1L;   // hardcoded temp ...
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<Long> examsCreated = new ArrayList<>();
            for(Exam exam: user.getExamsCreated()) {
                examsCreated.add(exam.getId());
            }
            List<UserProfilePastExamDto> pastExams = new ArrayList<>();
            List<UserProfileOngoingAndFutureExamDto> ongoingExams = new ArrayList<>();
            List<UserProfileOngoingAndFutureExamDto> futureExams = new ArrayList<>();

            if(!userId.equals(selfUserId)) {
                ongoingExams = null;
                futureExams = null;
            }

            for(Result result: user.getExamResults()) {
                Exam exam = result.getExam();
                if(exam.getStartDateTime().isBefore(LocalDateTime.now())) {
                    if(exam.getStartDateTime().plusMinutes(exam.getDuration()).isBefore(LocalDateTime.now())) {
                        pastExams.add(
                                new UserProfilePastExamDto(
                                        exam.getId(),
                                        exam.getTitle(),
                                        exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                        exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                        exam.getDuration(),
                                        exam.getTotalMarks(),
                                        result.getMarks(),
                                        result.getIsPresent(),
                                        exam.getIsResultGenerated()
                                )
                        );
                    } else if(userId.equals(selfUserId)) {
                        ongoingExams.add(
                                new UserProfileOngoingAndFutureExamDto(
                                        exam.getId(),
                                        exam.getTitle(),
                                        exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                        exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                        exam.getDuration(),
                                        exam.getTotalMarks()
                                )
                        );
                    }
                } else if(userId.equals(selfUserId)) {
                    futureExams.add(
                            new UserProfileOngoingAndFutureExamDto(
                                    exam.getId(),
                                    exam.getTitle(),
                                    exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                    exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                    exam.getDuration(),
                                    exam.getTotalMarks()
                            )
                    );
                }
            }
            UserProfileDto userProfileDto = new UserProfileDto(
                    user.getName(),
                    user.getEmail(),
                    user.getType(),
                    examsCreated,
                    pastExams,      // title, startDate, startTime, duration, totalMarks, isResultGenerated, marks, isPresent
                    ongoingExams,   // title, startDate, startTime, duration, totalMarks
                    futureExams     // title, startDate, startTime, duration, totalMarks
            );

            return userProfileDto;

        } else {
            return null;

        }
    }
}
