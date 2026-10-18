package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void shouldStoreAndRetrieveValueFromRedis() {
        String key = "lms:test";

        try {
            redisTemplate.opsForValue()
                    .set(key, "Redis connected!");

            String result = redisTemplate.opsForValue()
                    .get(key);

            assertEquals("Redis connected!", result);
        } finally {
            redisTemplate.delete(key);
        }
    }
}