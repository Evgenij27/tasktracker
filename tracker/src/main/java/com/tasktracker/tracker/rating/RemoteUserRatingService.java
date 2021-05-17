package com.tasktracker.tracker.rating;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class RemoteUserRatingService implements UserRatingService {

    private static final int DEFAULT_USER_RATING = 1;

    private final RestTemplate restTemplate;

    @Autowired
    public RemoteUserRatingService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public UserRating getUserRatingByUserId(Long userId) {
        URI uri = UriComponentsBuilder
            .fromUriString("http://localhost:8081/ratings/{userId}")
            .build(userId);
        try {
            return restTemplate.getForObject(uri, UserRating.class);
        } catch (RestClientException ex) {
            log.error(ex.getMessage(), ex);
            UserRating defaultRating = new UserRating();
            defaultRating.setUserId(userId);
            defaultRating.setRating(DEFAULT_USER_RATING);
            return defaultRating;
        }
    }
}
