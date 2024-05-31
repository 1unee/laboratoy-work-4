package com.oneune.laboratory.work.configs;

import com.google.common.cache.CacheBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import java.util.concurrent.ConcurrentMap;

@Configuration
@Log4j2
public class CacheConfig {

    @Bean("userCacheManager")
    public CacheManager cacheManager() {
        log.info("Custom cache manager bean was autowired");
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                ConcurrentMap<Object, Object> cacheMap = CacheBuilder.newBuilder()
                        .expireAfterWrite(Duration.ofSeconds(5))
                        .build()
                        .asMap();
                return new ConcurrentMapCache(name, cacheMap,false);
            }
        };
    }
}
