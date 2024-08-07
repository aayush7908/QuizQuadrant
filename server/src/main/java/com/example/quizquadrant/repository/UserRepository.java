package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u WHERE u.email in :emailIds")
    public List<User> findUsersByEmail(List<String> emailIds);

    public Optional<User> findByEmail(String email);

}
