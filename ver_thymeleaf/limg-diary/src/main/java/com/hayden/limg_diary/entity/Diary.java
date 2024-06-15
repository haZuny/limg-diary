package com.hayden.limg_diary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.Date;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int diary_id;

    private String content;

    @CreatedDate
    private Date date;

    private String feeling;

    private String image_path;

    @ManyToOne
    @JoinColumn
    private User user_id;
}
