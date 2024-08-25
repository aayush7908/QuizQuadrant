package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.User;
import com.example.quizquadrant.model.type.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private static String email;
    private static User user;

    @BeforeAll
    static void initializeParameters() {
        email = "john@gmail.com";
        user = User
                .builder()
                .email(email)
                .password("abcd")
                .firstName("Aayush")
                .lastName("Dalal")
                .accountCreatedOn(LocalDateTime.now())
                .isEmailVerified(false)
                .role(Role.STUDENT)
                .build();
    }

    @Test
    void canSave() {
//        when
        User savedUser = userRepository.save(user);
        System.out.println("saved user: " + savedUser);

//        then
        Assertions.assertNotEquals(savedUser, null);
    }

    @Test
    void canFindUserByEmail() {
//        when
        Optional<User> userOptional = userRepository.findByEmail(email);
        boolean expected = userOptional.isPresent();

//        then
        Assertions.assertTrue(expected);
    }

}