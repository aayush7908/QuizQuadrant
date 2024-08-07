package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.PrivateOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrivateOptionRepository extends JpaRepository<PrivateOption, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PrivateOption po where po in :privateOptions")
    public void deletePrivateOptions(List<PrivateOption> privateOptions);

}
