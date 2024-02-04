package com.jhipster.demo.store.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KariaUserTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KariaUser getKariaUserSample1() {
        return new KariaUser()
            .id(1L)
            .firstName("firstName1")
            .lastName("lastName1")
            .email("email1")
            .phone("phone1")
            .addressLine1("addressLine11")
            .addressLine2("addressLine21")
            .city("city1");
    }

    public static KariaUser getKariaUserSample2() {
        return new KariaUser()
            .id(2L)
            .firstName("firstName2")
            .lastName("lastName2")
            .email("email2")
            .phone("phone2")
            .addressLine1("addressLine12")
            .addressLine2("addressLine22")
            .city("city2");
    }

    public static KariaUser getKariaUserRandomSampleGenerator() {
        return new KariaUser()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .addressLine1(UUID.randomUUID().toString())
            .addressLine2(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString());
    }
}
