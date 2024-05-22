package com.br.luccasdev.projectSpringSecurity.repository;

import com.br.luccasdev.projectSpringSecurity.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
