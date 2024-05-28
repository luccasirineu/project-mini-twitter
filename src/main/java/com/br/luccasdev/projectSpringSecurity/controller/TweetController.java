package com.br.luccasdev.projectSpringSecurity.controller;

import com.br.luccasdev.projectSpringSecurity.controller.dto.TweetDto;
import com.br.luccasdev.projectSpringSecurity.model.Tweet;
import com.br.luccasdev.projectSpringSecurity.repository.TweetRepository;
import com.br.luccasdev.projectSpringSecurity.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody TweetDto tweetDto,
                                            JwtAuthenticationToken token){
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(tweetDto.content());

        tweetRepository.save(tweet);

        return ResponseEntity.ok().build();
    }
}
