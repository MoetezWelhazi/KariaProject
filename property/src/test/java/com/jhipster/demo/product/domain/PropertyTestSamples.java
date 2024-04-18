package com.jhipster.demo.product.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Property getPropertySample1() {
        return new Property()
            .id(1L)
            .ownerId("ownerId1")
            .name("name1")
            .description("description1")
            .address("address1")
            .location("location1");
    }

    public static Property getPropertySample2() {
        return new Property()
            .id(2L)
            .ownerId("ownerId2")
            .name("name2")
            .description("description2")
            .address("address2")
            .location("location2");
    }

    public static Property getPropertyRandomSampleGenerator() {
        return new Property()
            .id(longCount.incrementAndGet())
            .ownerId(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString());
    }
}
