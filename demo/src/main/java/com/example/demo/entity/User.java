package com.example.demo.entity;

import com.example.demo.enums.AppRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,unique = true,length = 150)
    private String email;

    @Column(nullable = false,length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    Set<AppRole> roles=new HashSet<>();

    //instructor To course
    @OneToMany(mappedBy = "instructor",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Course> courses=new ArrayList<>();

    //Student To Course but enrollment
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Enrollment> enrollments=new ArrayList<>();
}
