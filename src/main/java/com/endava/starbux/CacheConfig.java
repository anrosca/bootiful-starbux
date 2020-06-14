package com.endava.starbux;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
@ConditionalOnClass(RedisTemplate.class)
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class CacheConfig {

    @ConditionalOnProperty(name = "cache.type", havingValue = "hashmap")
    static class HashMapCacheConfig {
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("drinkCache");
        }
    }

    @ConditionalOnProperty(name = "cache.type", havingValue = "redis")
    static class RedisCacheConfig {

        @Bean
        public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, String> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }

        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName("localhost");
            configuration.setPort(6379);
            return new LettuceConnectionFactory(configuration);
        }

        @Bean
        public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory) {
            Map<String, RedisCacheConfiguration> cacheConfigurations =
                    Map.of("drinkCache", RedisCacheConfiguration
                            .defaultCacheConfig()
                            .entryTtl(Duration.ofMinutes(10)));
            return RedisCacheManager.builder(redisConnectionFactory)
                    .withInitialCacheConfigurations(cacheConfigurations)
                    .build();
        }
    }

}
