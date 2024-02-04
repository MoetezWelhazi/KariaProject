package com.jhipster.demo.product.domain;

import static com.jhipster.demo.product.domain.PropertyTestSamples.*;
import static com.jhipster.demo.product.domain.ReviewTestSamples.*;
import static com.jhipster.demo.product.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.product.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = getPropertySample1();
        Property property2 = new Property();
        assertThat(property1).isNotEqualTo(property2);

        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);

        property2 = getPropertySample2();
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    void reviewTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        property.addReview(reviewBack);
        assertThat(property.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getPropertyId()).isEqualTo(property);

        property.removeReview(reviewBack);
        assertThat(property.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getPropertyId()).isNull();

        property.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(property.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getPropertyId()).isEqualTo(property);

        property.setReviews(new HashSet<>());
        assertThat(property.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getPropertyId()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        Property property = getPropertyRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        property.addTags(tagBack);
        assertThat(property.getTags()).containsOnly(tagBack);

        property.removeTags(tagBack);
        assertThat(property.getTags()).doesNotContain(tagBack);

        property.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(property.getTags()).containsOnly(tagBack);

        property.setTags(new HashSet<>());
        assertThat(property.getTags()).doesNotContain(tagBack);
    }
}
