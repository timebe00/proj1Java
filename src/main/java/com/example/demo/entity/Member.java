package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.awt.*;
import java.lang.reflect.Array;

@JsonIgnoreProperties(value="hibernateLazyInitializer")
@Getter
@Setter
@EqualsAndHashCode(of = "memberNo")
@ToString
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberno")
    private int memberNo;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "file", columnDefinition ="TEXT", nullable = false)
    private String file;

    @Column(name = "ex", columnDefinition ="TEXT", nullable = false)
    private String ex;

    @Column(name = "id", nullable = false)
    private String id;
}
