package com.jhipster.demo.product.domain;

import static com.jhipster.demo.product.domain.PropertyTestSamples.*;
import static com.jhipster.demo.product.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.product.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void propertiesTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        tag.addProperties(propertyBack);
        assertThat(tag.getProperties()).containsOnly(propertyBack);
        assertThat(propertyBack.getTags()).containsOnly(tag);

        tag.removeProperties(propertyBack);
        assertThat(tag.getProperties()).doesNotContain(propertyBack);
        assertThat(propertyBack.getTags()).doesNotContain(tag);

        tag.properties(new HashSet<>(Set.of(propertyBack)));
        assertThat(tag.getProperties()).containsOnly(propertyBack);
        assertThat(propertyBack.getTags()).containsOnly(tag);

        tag.setProperties(new HashSet<>());
        assertThat(tag.getProperties()).doesNotContain(propertyBack);
        assertThat(propertyBack.getTags()).doesNotContain(tag);
    }
}
