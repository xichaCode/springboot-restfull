package com.zeekr.testclew.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author xibu
 * @Date RedisConfig 19:11
 * @Version 1.0
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 分割符
     */
    public  static  final String DECOLLATOR = ":";

    /**
     * 应用前缀
     */
    public static final String APP_PREFIX = "redis";

    /**
     * 缓存名前缀
     */
    public static final String CACHE_NAMES_PREFIX = APP_PREFIX + DECOLLATOR + "cacheName";

    /**
     * 永不过期的缓存名
     */
    public static final String CACHE_NAME_FOREVER = CACHE_NAMES_PREFIX + "forever";

    /**
     * 10分钟有效期的缓存名
     */
    public static final  String CACHE_NAME_MINUTES_10 = CACHE_NAMES_PREFIX + "minutes-10";

    /**
     * 30分钟有效期的缓存名
     */

    public static final  String CACHE_NAME_MINUTES_30 = CACHE_NAMES_PREFIX + "minutes-30";

    /**
     * 1个小时有效期的缓存名
     */

    public static final String CACHE_NAME_HOURS_01 = CACHE_NAMES_PREFIX + "hours-1";

    /**
     * 12小时有效期缓存名
     */
    public static final String CACHE_NAME_HOURS_12 = CACHE_NAMES_PREFIX + "hours-12";
    /**
     * 24小时有效期缓存名
     */
    public static final String CACHE_NAME_HOURS_24 = CACHE_NAMES_PREFIX + "hours-24";

    /**
     * 30天有效期的缓存名
     */
    public static final String CACHE_NAME_DAYS_30 = CACHE_NAMES_PREFIX + "days-30";

    /**
     *
     * @param redisTemplate
     * @return
     */


    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate){
        RedisCacheConfiguration defultCachegirue = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.
                        fromSerializer(redisTemplate.getStringSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.
                        fromSerializer(redisTemplate.getValueSerializer()))
                .disableCachingNullValues()
                .entryTtl(Duration.ofHours(1));
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                .cacheDefaults(defultCachegirue)
                .build();
        return redisCacheManager;
    }

}
