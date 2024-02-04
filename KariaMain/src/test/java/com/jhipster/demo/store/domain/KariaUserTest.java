package com.jhipster.demo.store.domain;

import static com.jhipster.demo.store.domain.KariaUserTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jhipster.demo.store.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KariaUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KariaUser.class);
        KariaUser kariaUser1 = getKariaUserSample1();
        KariaUser kariaUser2 = new KariaUser();
        assertThat(kariaUser1).isNotEqualTo(kariaUser2);

        kariaUser2.setId(kariaUser1.getId());
        assertThat(kariaUser1).isEqualTo(kariaUser2);

        kariaUser2 = getKariaUserSample2();
        assertThat(kariaUser1).isNotEqualTo(kariaUser2);
    }
}
