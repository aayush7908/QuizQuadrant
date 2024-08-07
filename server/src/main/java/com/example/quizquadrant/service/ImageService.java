package com.example.quizquadrant.service;

import com.example.quizquadrant.model.*;
import com.example.quizquadrant.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String getImageById(Long id, String type) {
            ImageKey imageKey = new ImageKey();
            imageKey.setRefId(id);
            imageKey.setType(type);
            Optional<Image> imageOptional = imageRepository.findById(imageKey);
            return imageOptional.map(Image::getImageUrl).orElse(null);
    }

    public Image createImage(String type, Long refId ,String imgURL) {
       Image newImage  = new Image(type, refId, imgURL);
       return imageRepository.save(newImage);
    }

    public void removeImage(String type, Long refId) {
        imageRepository.deleteImage(type, refId);
    }
}
