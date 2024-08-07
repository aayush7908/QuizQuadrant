package com.example.quizquadrant.repository;

import com.example.quizquadrant.model.Image;
import com.example.quizquadrant.model.ImageKey;
import com.example.quizquadrant.model.PrivateSolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, ImageKey> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Image img where img.type = :type AND img.refId = :refId")
    public void deleteImage(String type, Long refId);
}
