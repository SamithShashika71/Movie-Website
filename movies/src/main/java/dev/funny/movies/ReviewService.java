package dev.funny.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        // 1. Create and save the review
        Review review = reviewRepository.insert(new Review(reviewBody));

        // 2. Update the Movie's reviewIds list with the new review ID
        mongoTemplate.updateFirst(
            new Query(Criteria.where("imdbId").is(imdbId)),
            new Update().push("reviewIds", review.getId()),
            Movie.class
        );

        return review;
    }
}
