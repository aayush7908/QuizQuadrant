package com.example.quizquadrant.model;

import com.example.quizquadrant.model.type.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_email",
                        columnNames = "email"
                )
        }
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(50)"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "VARCHAR(20)"
    )
    private String lastName;

    @Column(
            name = "profile_image_url",
            columnDefinition = "TINYTEXT"
    )
    private String profileImageUrl;

    @Column(
            name = "account_created_on",
            nullable = false,
            columnDefinition = "DATETIME"
    )
    private LocalDateTime accountCreatedOn;

    @Column(
            name = "email_verified_on",
            columnDefinition = "DATETIME"
    )
    private LocalDateTime emailVerifiedOn;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "role",
            nullable = false,
            columnDefinition = "VARCHAR(10)"
    )
    private Role role;

    @OneToMany(
            mappedBy = "createdBy",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<Question> createdQuestions;

    @OneToMany(
            mappedBy = "createdBy",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<Exam> createdExams;

    @OneToMany(
            mappedBy = "createdBy",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<DraftQuestion> draftQuestions;

    @OneToMany(
            mappedBy = "createdBy",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<DraftExam> draftExams;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<ExamCandidate> enrolledExams;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE
    )
    @JsonBackReference
    private List<ExamResponse> examResponses;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}