package com.webdrp.config;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class MybatisRedisCache implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisRedisCache.class);

    private static final Pattern BLANK_REGEX = Pattern.compile("\\s{2,}");
    private static final String SPACE = " ";

    private static RedisTemplate<Object, Object> redisTemplate;

    private final String id;
    private final ReadWriteLock readWriteLock;

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("缓存ID必须");
        }
        this.id = BLANK_REGEX.matcher(id).replaceAll(SPACE);
        LOGGER.debug("缓存ID为："+id);
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        RedisTemplate<Object, Object> redisTemplate = getRedis();
        if (redisTemplate == null) {
            return 0;
        }
        try {
            return redisTemplate.opsForHash().size(id.getBytes()).intValue();
        } catch (RedisConnectionFailureException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(e.getMessage());
            }
            return 0;
        }
    }

    @Override
    public void putObject(final Object key, final Object value) {
        if (key != null && value != null) {
            RedisTemplate<Object, Object> redisTemplate = getRedis();

            if (redisTemplate != null) {

                String keyHash = BLANK_REGEX.matcher(key.toString()).replaceAll(SPACE);
                try {
                    redisTemplate.opsForHash().put(id.getBytes(), keyHash.getBytes(), serialize(value));
                    redisTemplate.expire(id.getBytes(),30,TimeUnit.SECONDS);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("put query result ({}) to cache", (id + "<>" + keyHash));
                    }
                } catch (RedisConnectionFailureException e) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn(e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public Object getObject(final Object key) {
        if (key == null) {
            return null;
        }
        RedisTemplate<Object, Object> redisTemplate = getRedis();
        if (redisTemplate != null) {
            String keyHash = BLANK_REGEX.matcher(key.toString()).replaceAll(SPACE);
            try {
                Object value = redisTemplate.opsForHash().get(id.getBytes(), keyHash.getBytes());
                if (value != null && value instanceof byte[]) {
                    Object result = unSerialize((byte[]) value);
                    if (result != null) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("get query result ({}) from cache", (id + "<>" + keyHash));
                        }
                        return result;
                    }
                }
            } catch (RedisConnectionFailureException e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public Object removeObject(final Object key) {
        if (key == null) {
            return null;
        }
        RedisTemplate<Object, Object> redisTemplate = getRedis();
        if (redisTemplate == null) {
            return null;
        }
        String keyHash = BLANK_REGEX.matcher(key.toString()).replaceAll(SPACE);
        try {
            Object obj = redisTemplate.opsForHash().delete(id.getBytes(), (Object) keyHash.getBytes());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("remove query result ({}) from cache", (id + "<>" + keyHash));
            }
            return obj;
        } catch (RedisConnectionFailureException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(e.getMessage());
            }
            return null;
        }
    }

    @Override
    public void clear() {
        RedisTemplate<Object, Object> redisTemplate = getRedis();
        if (redisTemplate != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("clear query result ({}) from cache", id);
            }
            try {
                redisTemplate.delete(id.getBytes());
            } catch (RedisConnectionFailureException e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(e.getMessage());
                }
            }
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String toString() {
        return "Redis {" + id + "}";
    }

    private RedisTemplate<Object, Object> getRedis() {
//        if (redisTemplate == null) {
//            redisTemplate = RedisContextUtils.getRedisTemplate();
//        }
        return redisTemplate;
    }

    public static void setJedisConnectionFactory(RedisTemplate<Object,Object> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }

    static byte[] serialize(Object obj) {
        if (obj == null) return null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(obj);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    static Object unSerialize(byte[] bytes) {
        if (bytes == null) return null;

        try {
            return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }
}