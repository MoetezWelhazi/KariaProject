package com.jhipster.demo.product.domain;

import static com.jhipster.demo.product.domain.PropertyTestSamples.*;
import static com.jhipster.demo.product.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void propertyIdTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        review.setPropertyId(propertyBack);
        assertThat(review.getPropertyId()).isEqualTo(propertyBack);

        review.propertyId(null);
        assertThat(review.getPropertyId()).isNull();
    }
}
