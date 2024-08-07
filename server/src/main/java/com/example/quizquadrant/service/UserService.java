package com.example.quizquadrant.service;

import com.example.quizquadrant.dto.UserProfileDto;
import com.example.quizquadrant.dto.UserProfileExamDto;
import com.example.quizquadrant.model.Exam;
import com.example.quizquadrant.model.Result;
import com.example.quizquadrant.model.User;
import com.example.quizquadrant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        var selfUser = userRepository.findByEmail(authenticatedUser.getUsername()).orElseThrow();
        Long selfUserId = selfUser.getId();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserProfileExamDto> examsCreated = new ArrayList<>();
            for (Exam exam : user.getExamsCreated()) {
                examsCreated.add(
                        new UserProfileExamDto(
                                exam.getId(),
                                exam.getTitle(),
                                exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                exam.getDuration(),
                                exam.getTotalMarks(),
                                -111,
                                false,
                                false,
                                exam.getIsResultGenerated()
                        ));
            }
            List<UserProfileExamDto> pastExams = new ArrayList<>();
            List<UserProfileExamDto> ongoingExams = new ArrayList<>();
            List<UserProfileExamDto> futureExams = new ArrayList<>();

            if (!userId.equals(selfUserId)) {
                ongoingExams = null;
                futureExams = null;
            }

            for (Result result : user.getExamResults()) {
                Exam exam = result.getExam();
                if (exam.getStartDateTime().isBefore(LocalDateTime.now())) {
                    if (exam.getStartDateTime().plusMinutes(exam.getDuration()).isBefore(LocalDateTime.now())) {
                        pastExams.add(
                                new UserProfileExamDto(
                                        exam.getId(),
                                        exam.getTitle(),
                                        exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                        exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                        exam.getDuration(),
                                        exam.getTotalMarks(),
                                        result.getMarks(),
                                        result.getIsPresent(),
                                        result.getIsFinished(),
                                        exam.getIsResultGenerated()
                                )
                        );
                    } else if (userId.equals(selfUserId)) {
                        ongoingExams.add(
                                new UserProfileExamDto(
                                        exam.getId(),
                                        exam.getTitle(),
                                        exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                        exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                        exam.getDuration(),
                                        exam.getTotalMarks(),
                                        -111,
                                        false,
                                        result.getIsFinished(),
                                        false
                                )
                        );
                    }
                } else if (userId.equals(selfUserId)) {
                    futureExams.add(
                            new UserProfileExamDto(
                                    exam.getId(),
                                    exam.getTitle(),
                                    exam.getStartDateTime().getYear() + "-" + exam.getStartDateTime().getMonthValue() + "-" + exam.getStartDateTime().getDayOfMonth(),
                                    exam.getStartDateTime().getHour() + ":" + exam.getStartDateTime().getMinute(),
                                    exam.getDuration(),
                                    exam.getTotalMarks(),
                                    -111,
                                    false,
                                    false,
                                    false
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