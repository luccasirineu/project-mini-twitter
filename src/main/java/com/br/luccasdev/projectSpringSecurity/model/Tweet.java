package com.br.luccasdev.projectSpringSecurity.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_tweets")
public class Tweet {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    private User user;

    private String content;

    @CreationTimestamp
    private Instant creationTimeStamp;
}
