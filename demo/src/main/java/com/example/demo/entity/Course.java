package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length =100 )
    private String title;

    @Column(nullable = false,length=250)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "instructor_id",nullable = false)
    private User instructor;

    @OneToMany(mappedBy = "course",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Lesson> lesson=new ArrayList<>();
}
