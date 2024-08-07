package com.example.quizquadrant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(
                        name ="uk_user_email",
                        columnNames = "email"
                )
        }
)
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition ="VARCHAR(20)"
    )
    private String name;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition ="VARCHAR(50)"
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "type",
            columnDefinition ="CHAR(1)"
    )
    private String type;

    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Exam> examsCreated;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<Result> examResults;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.REMOVE
    )
    @JsonManagedReference
    private List<ExamResponses> examResponses;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getType()));
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