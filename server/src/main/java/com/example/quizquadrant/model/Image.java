package com.example.quizquadrant.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
@IdClass(ImageKey.class)
public class Image {
    @Id
    @Column(
            name = "type",
            columnDefinition = "CHAR(1)"
    )
    private String type;

    @Id
    private Long refId;

    @Column(
            name = "imageUrl",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imageUrl;
}
