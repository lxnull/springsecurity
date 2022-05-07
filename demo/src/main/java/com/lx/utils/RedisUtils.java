package com.lx.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtils {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key k
     * @param time 时间(s)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取过期时间
     * @param key k
     * @return time 过期时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key k
     * @return true存在/false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param keys 一个或多个k
     */
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
            }
        }
    }

    // =========================String==========================
    /**
     * get
     * @param key k
     * @return value val
     */
    public <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * set key val
     * @param key
     * @param val
     * @return true/false
     */
    public boolean set(String key, Object val) {
        try {
            redisTemplate.opsForValue().set(key, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * SETEX k 时间（秒） v：存入并设置过期时间
     * @param key
     * @param time
     * @param val
     * @return true/false
     */
    public boolean set(String key, long time, Object val) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, val, time, TimeUnit.SECONDS);
            } else {
                set(key, val);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta 加多少
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于零");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key
     * @param delta 减多少
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于零");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // =========================Map==========================
    /**
     * hget
     * @param key k
     * @return field 字段名
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * hset 向key中存键值对，不存在时创建
     * @param key k
     * @return field 字段名
     */
    public boolean hSet(String key, String field, Object val) {
        try {
            redisTemplate.opsForHash().put(key, field, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hset 向key中存键值对，不存在时创建，并设置过期时间
     * @param key k
     * @param time 过期时间
     * @return field 字段名
     */
    public boolean hSet(String key, String field, Object val, long time) {
        try {
            redisTemplate.opsForHash().put(key, field, val);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hmget 获取多个hash中的值
     * @param key k
     * @return
     */
    public List<Object> hMGet(String key, Object... fields) {
        return redisTemplate.opsForHash().multiGet(key, (Collection<Object>) CollectionUtils.arrayToList(fields));
    }

    /**
     * hset key val
     * @param key
     * @param map 多个键值
     * @return true/false
     */
    public boolean hMSet(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hset key val
     * @param key
     * @param map 多个键值
     * @param time 过期时间
     * @return true/false
     */
    public boolean hMSet(String key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * hdel key field 删除hash中任意个数键值对
     * @param key
     * @param fields 字段名
     */
    public void hDel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * hexists key field 指定字段是否存在
     * @param key
     * @param field
     * @return true/false
     */
    public boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * hash元素递增
     * @param key
     * @param delta 加多少
     */
    public long hIncrBy(String key, String field, long delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * hash元素递增
     * @param key
     * @param delta 加/减多少
     */
    public double hIncrBy(String key, String field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 返回整个map
     * @param key
     * @return map
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // =========================Set==========================
    /**
     * SMEMBERS key 查看指定set的所有值
     * @param key
     * @return
     */
    public Set<Object> sMembers(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * sadd key val... # 向set中添加元素
     * @param key
     * @param vals
     * @return 存入的值的个数
     */
    public long sAdd(String key, Object... vals) {
        try {
            return redisTemplate.opsForSet().add(key, vals);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * sadd key val... # 向set中添加元素（设置过期时间）
     * @param key
     * @param vals
     * @param time
     * @return 存入的值的个数
     */
    public long sAdd(String key, long time, Object... vals) {
        try {
            Long count = redisTemplate.opsForSet().add(key, vals);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * SISMEMBER key val 判断一个元素是否在set集合中
     * @param key
     * @param val
     * @return true/false
     */
    public boolean sIsMember(String key, Object val) {
        try {
            return redisTemplate.opsForSet().isMember(key, val);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * srem key val 移除元素中指定的元素
     * @param key
     * @param vals
     * @return 移除的个数
     */
    public long sRem(String key, Object... vals) {
        try {
            return redisTemplate.opsForSet().remove(key, vals);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // =========================List==========================
    /**
     * LRANGE list 0 -1 获取list的内容
     * @param key
     * @param startIndex
     * @param endIndex
     * @return list内容
     */
    public List<Object> lRange(String key, long startIndex, long endIndex) {
        try {
            return redisTemplate.opsForList().range(key, startIndex, endIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * lindex：跟据索引获取list中元素
     * @param key
     * @param index
     * @return 跟据索引返回的元素
     */
    public Object lIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * LLEN list 获取list的长度
     * @param key
     * @return 长度
     */
    public long lLen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * lpush 将list放入缓存(单个)
     * @param key
     * @param val
     * @return true/false
     */
    public boolean lPush(String key, Object val) {
        try {
            redisTemplate.opsForList().leftPush(key, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lpush 将list放入缓存
     * @param key
     * @param val
     * @param time
     * @return true/false
     */
    public boolean lPush(String key, Object val, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, val);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * rpush 将list放入缓存(单个)
     * @param key
     * @param val
     * @return true/false
     */
    public boolean rPush(String key, Object val) {
        try {
            redisTemplate.opsForList().rightPush(key, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * rpush 将list放入缓存
     * @param key
     * @param val
     * @param time
     * @return true/false
     */
    public boolean rPush(String key, Object val, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, val);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lpush 将list放入缓存(单个)
     * @param key
     * @param vals
     * @return true/false
     */
    public boolean lPush(String key, List<Object> vals) {
        try {
            redisTemplate.opsForList().leftPushAll(key, vals);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lpush 将list放入缓存
     * @param key
     * @param vals
     * @param time
     * @return true/false
     */
    public boolean lPush(String key, List<Object> vals, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, vals);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * rpush 将list放入缓存(单个)
     * @param key
     * @param vals
     * @return true/false
     */
    public boolean rPush(String key, List<Object> vals) {
        try {
            redisTemplate.opsForList().rightPushAll(key, vals);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * rpush 将list放入缓存
     * @param key
     * @param vals
     * @param time
     * @return true/false
     */
    public boolean rPush(String key, List<Object> vals, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, vals);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lset key val index：跟据索引更新list中元素，若索引不存在则会报错
     * @param key
     * @param val
     * @param index
     * @return true/false
     */
    public boolean lSet(String key, Object val, long index) {
        try {
            redisTemplate.opsForList().set(key, index, val);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * lrem：移除指定的值
     * @param key
     * @param val
     * @param count
     * @return 移除的个数
     */
    public long lRem(String key, Object val, long count) {
        try {
            return redisTemplate.opsForList().remove(key, count, val);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
