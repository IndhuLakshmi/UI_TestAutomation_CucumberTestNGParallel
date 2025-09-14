package com.framework.utils;

import com.github.javafaker.Faker;

/**
 * Simple wrapper around Faker for random test data.
 */
public class FakerUtil {
    private static final Faker faker = new Faker();

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomName() {
        return faker.name().fullName();
    }
}
