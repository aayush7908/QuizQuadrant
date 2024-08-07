package com.example.quizquadrant;

import com.example.quizquadrant.model.Subject;
import com.example.quizquadrant.model.Subtopic;
import com.example.quizquadrant.repository.SubjectRepository;
import com.example.quizquadrant.repository.SubtopicRepository;
import com.example.quizquadrant.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class QuizquadrantApplicationTests {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubtopicRepository subtopicRepository;

    @Autowired
    private UserRepository userRepository;



    @Test
    void contextLoads() {
    }

    Subject subject;
//    @Test
//    void addData(){
//        Subject subject  = new Subject();
//        subject.setSubName("DSA");
//        subject = subjectRepository.save(subject);
//        Subtopic subtopic = new Subtopic("Dynamic Programming",subject);
//        subtopic = subtopicRepository.save(subtopic);
//    }

    @Test
    void addData() {
        // Create and persist the Subject entity first
        subject  = new Subject();
        subject.setSubName("DSA");
        subject = subjectRepository.save(subject);

        // Use the persisted Subject object when creating the Subtopic
//        Subtopic subtopic = new Subtopic("Dynamic Programming", subject);
//        subtopic = subtopicRepository.save(subtopic);
    }

    @Test
    void addSubtopic() {
        Subtopic subtopic = new Subtopic("Dynamic Programming", subject);
        subtopic = subtopicRepository.save(subtopic);

    }


}
