package dev.funny.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {
        String reviewBody = payload.get("reviewBody");
        String imdbId = payload.get("imdbId");

        if (reviewBody == null || imdbId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Review review = service.createReview(reviewBody, imdbId);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
}
