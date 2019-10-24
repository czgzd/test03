package com.sinocarbon.integral.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sinocarbon.integral.constant.ErrorCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author wxw
 * @description 基于spring和redis的redisTemplate工具类
 *              Redis目前支持5种数据类型（String,List,Hash,Set,Sorted Set）
 *              1.针对所有的hash都是以h开头的方法 2.针对所有的Set都是以s开头的方法 3.针对所有的List都是以l开头的方法
 */
@Component
@Slf4j
public class RedisUtil {

	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate redisTpl; // jdbcTemplate
	@SuppressWarnings("rawtypes")
	@Resource(name = "cacheRedisTemplate")
	private RedisTemplate redisTemplate; // jdbcTemplate

	@Autowired
	JackSonUtil jacksonUtil;
	
	/**
	 * 
	 * @description 指定内存中某个键值对在多少秒后失效(5种类型通用)
	 * @param key 键
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 根据key获取过期时间（秒）(5种类型通用)
	 * @param key 键
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * @description 判断key是否存在(5种类型通用),存在返回true,反之false
	 * @param key 键
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 根据key删除相应的缓存(5种类型通用)
	 * @param key 键
	 * @return void
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 
	 * @description 根据key获取相应的缓存（string类型）
	 * @param key 键
	 * @return Object
	 * @author gemingming
	 * @date 2019/07/04
	 */
	public Object get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 
	 * @description 将数据存入redis（string类型）
	 * @param key   键
	 * @param value 值
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value.toString());
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}

	}

	/**
	 * 
	 * @description 将数据存入redis 并指定过期时间（秒）（string类型）
	 * @param key   键
	 * @param value 值
	 * @param time  过期时间
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description value递增（value肯定是数字啦）（string类型）
	 * @param key   键
	 * @param delta 递增值
	 * @throws Exception
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long incr(String key, long delta) {
		if (delta < 0) {
			log.info(ErrorCode.CODE_1002002_MSG);
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 
	 * @description value递减（value肯定是数字啦）（string类型）
	 * @param key   键
	 * @param delta 递减值
	 * @throws Exception
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long decr(String key, long delta) {
		if (delta < 0) {
			log.info(ErrorCode.CODE_1002003_MSG);
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 
	 * @description 获取hash的某个key下的value（Hash类型）
	 * @param key      键
	 * @param hashName 哈希名
	 * @return Object
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public Object hget(String hashName, String key) {
		return redisTemplate.opsForHash().get(hashName, key);
	}

	/**
	 * 
	 * @description 根据hashName获取其 所有的 键值对（Hash类型）
	 * @param hashName 哈希名
	 * @throws Exception
	 * @return Map<Object,Object>
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> hmget(String hashName) {
		Map<Object, Object> dataMap = redisTemplate.opsForHash().entries(hashName);
		if(dataMap==null) {
			return new HashMap<>();
		}
		return dataMap;
	}

	/**
	 * 
	 * @description 给hash赋值（Map全集，适用于全量更新）（Hash类型）
	 * @param hashName 哈希名
	 * @param map      键值对
	 * @throws Exception
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hmset(String hashName, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(hashName, map);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 给hash赋值（全量） 并指定过期时间（Hash类型）
	 * @param hashName 哈希名
	 * @param map      键值对
	 * @param time     过期时间（秒）
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hmset(String hashName, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(hashName, map);
			if (time > 0) {
				expire(hashName, time);
			}
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 向hash中赋一个值（适用于增量更新）（Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @param value    值
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hset(String hashName, String key, Object value) {
		try {
			redisTemplate.opsForHash().put(hashName, key, value);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 向hash中赋一个值,并指定过期时间（适用于增量更新）（Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @param value    值
	 * @param time     过期时间(秒)
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hset(String hashName, String key, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(hashName, key, value);
			if (time > 0) {
				expire(hashName, time);
			}
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 批量删除hash中的键值（Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @throws Exception
	 * @return void
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public void hdel(String hashName, Object... key) {
		redisTemplate.opsForHash().delete(hashName, key);
	}

	/**
	 * 
	 * @description 判断hash中是否有此键值对 （Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @throws Exception
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean hHasKey(String hashName, String key) {
		return redisTemplate.opsForHash().hasKey(hashName, key);
	}

	/**
	 * 
	 * @description value累加（Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @param by       增量值
	 * @throws Exception
	 * @return double
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public double hincr(String hashName, String key, double by) {
		return redisTemplate.opsForHash().increment(hashName, key, by);
	}

	/**
	 * 
	 * @description value递减（Hash类型）
	 * @param hashName 哈希名
	 * @param key      键
	 * @param by       增量值
	 * @throws Exception
	 * @return double
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public double hdecr(String hashName, String key, double by) {
		return redisTemplate.opsForHash().increment(hashName, key, -by);
	}

	/**
	 * 
	 * @description 全量更新hashMap（（Hash类型））
	 * @param hashName
	 * @param map
	 * @throws Exception
	 * @return boolean
	 * @author wangxiaowei
	 * @date 2019/07/11
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean hsetAllSpecial(String hashName, Map map) {
		try {
			redisTemplate.opsForHash().putAll(hashName, map);

			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 根据key获取键值(set类型)
	 * @param key 键
	 * @throws Exception
	 * @return Set<Object>
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return new HashSet<>();
		}

	}

	/**
	 * 
	 * @description 判断该key下是否有此value(set类型)
	 * @param key   键
	 * @param value 值
	 * @throws Exception
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 赋值（增量），可同时赋多个(set类型)
	 * @param key   键
	 * @param value 值
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description 赋值（增量），可同时赋多个，并指定过期时间(set类型)
	 * @param key   键
	 * @param value 值
	 * @param time  时间（秒）
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			@SuppressWarnings("unchecked")
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description 获取set的长度(set类型)
	 * @param key 键
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description 移除values(set类型)
	 * @param key    键
	 * @param values 值
	 * @throws Exception
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long setRemove(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().remove(key, values);
			
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description 获取value(list类型)
	 * @param key   键
	 * @param start 从第几个获取
	 * @param end   到第几个结束 （0～-1）表示获取所有的
	 * @return List<Object>
	 * @author gemingming
	 */
	@SuppressWarnings("unchecked")
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @description 获取value长度(list类型)
	 * @param key 键
	 * @return long
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description 根据索引获取value(list类型)
	 * @param key   键
	 * @param index 索引 负数表示从末尾倒过来
	 * @return Object
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return null;
		}
	}

	/**
	 * 
	 * @description 赋值（增量）(list类型)
	 * @param key   键
	 * @param value 值
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 赋值（增量）,并指定过期时间(list类型)
	 * @param key   键
	 * @param value 值
	 * @param time  过期时间
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 赋值（全量）(list类型)
	 * @param key   键
	 * @param value 值
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 赋值,并指定过期时间（全量）(list类型)
	 * @param key   键
	 * @param value 值
	 * @param time  过期时间
	 * @return boolean
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 根据索引修改值(list类型)
	 * @param key   键
	 * @param index 索引
	 * @return value 修改值
	 * @author gemingming
	 * @date 2019/07/04
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}
	}

	/**
	 * 
	 * @description 移除N个固定value(list类型)
	 * @param key   键
	 * @param count 要移除的个数
	 * @param value 移除的值
	 * @return long 实际移除的个数
	 * @author gemingming
	 * @date 2019/07/04
	 */
	@SuppressWarnings("unchecked")
	public long lRemove(String key, long count, Object value) {
		try {
			return  redisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return 0;
		}
	}

	/**
	 * 
	 * @description redis 切换库
	 * @param databaseNum 数据库号
	 * @return boolean
	 * @author wangxiaowei
	 * @date 2019/06/28
	 */
	public boolean chooseRedisDatabase(Integer databaseNum) {

		try {
			LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate
					.getConnectionFactory();
			jedisConnectionFactory.setDatabase(databaseNum);
			redisTemplate.setConnectionFactory(jedisConnectionFactory);
			jedisConnectionFactory.resetConnection();
			return true;
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return false;
		}

	}

	/**
	 * 
	 * @description 排序sort,分页查询,小到大排列（Sorted Set类型）
	 * @param pageNum  页码
	 * @param pageSize 每页大小 pageNum 和pageSize都为0则查所有即 start为0，end为-1
	 * @return Set<Object>
	 * @author wangxiaowei
	 * @date 2019/07/01
	 */
	@SuppressWarnings("unchecked")
	public Set<Object> sortSetPage(String sortName, Integer pageNum, Integer pagesize) {
		long start = pagesize * (pageNum - 1);
		long end = start + pagesize - 1;
		return redisTemplate.opsForZSet().range(sortName, start, end);
	}

	/**
	 * 
	 * @description 排序sort,分页查询,大到小排列（Sorted Set类型）
	 * @param pageNum  页码
	 * @param pageSize 每页大小 pageNum 和pageSize都为0则查所有即 start为0，end为-1
	 * @return Set<Object>
	 * @author wangxiaowei
	 * @date 2019/07/01
	 */
	@SuppressWarnings("unchecked")
	public Set<Object> sortSetPageReverse(String sortName, Integer pageNum, Integer pagesize) {
		long start = pagesize * (pageNum - 1);
		long end = start + pagesize - 1;
		return redisTemplate.opsForZSet().reverseRange(sortName, start, end);
	}

	/**
	 * 
	 * @description sortSet 增加值（增量）（Sorted Set类型）
	 * @param sortName sortSet 名称
	 * @param key      键
	 * @param value    值
	 * @return void
	 * @author wangxiaowei
	 * @date 2019/07/01
	 */
	@SuppressWarnings("unchecked")
	public void sortSetAdd(String sortName, Object key, double value) {
		redisTemplate.opsForZSet().add(sortName, key, value);
	}

	/**
	 * 
	 * @description 向sortSet 添加数据（Sorted Set类型）
	 * @param sortSetName 排序名
	 * @param tuples      值 集合
	 * @throws Exception
	 * @return void
	 * @author wangxiaowei
	 * @date 2019/07/01
	 * @modifyDescription
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public void sortSetAddAll(String sortSetName, @SuppressWarnings("rawtypes") Set tuples) {
		redisTemplate.opsForZSet().add(sortSetName, tuples);
	}

	/**
	 * @description 根据key 取值
	 * @param sortName sortSet 名称（Sorted Set类型）
	 * @param sortKey  对应的成员key 不能为null
	 * @throws Exception
	 * @return double 对应的值
	 * @author wangxiaowei
	 * @date 2019/07/05
	 */
	@SuppressWarnings("unchecked")
	public double sortSetGetScoreByKey(String sortName, Object sortKey) {
		return redisTemplate.opsForZSet().score(sortName, sortKey);

	}
	
	/**
	 * @description 增加分数 
	 * @param sortSetName  名称
	 * @param key  
	 * @param score  分数
	 * @throws Exception
	 * @return Double   
	 * @author wangxiaowei 
	 * @date 2019/07/17
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public Double sortSetIncrScore(String sortSetName, String key, double score) {
	    return redisTemplate.opsForZSet().incrementScore(sortSetName, key, score);
	}
	
	/**
	 * 
	 * @description 根据key删除一个或多个元素
	 * @param sortSetName 集合名称
	 * @param keys  key集合
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/17
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public void sortSetRemoveValueByKeys(String sortSetName,Object...keys) {
		
		redisTemplate.opsForZSet().remove(sortSetName, keys);
		
	}
	
	

	/**
	 * 
	 * @description 删除所有大的key,适用于所有类型     
	 * @param name 名称
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/15
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public void delKeys(String name) {
		
		redisTemplate.unlink(name);
		
		
	}
	/**
	 * 
	 * @description 批量删除所有大key ,适用于所有类型  
	 * @param names  名称集合
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/17
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public  void delkeysBatch(Set<String> names) {
		redisTemplate.unlink(names);
		
	}
	
	
	/**
	 * 
	 * @description  根据前缀获取所有的key
	 * @param prex key前缀
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/17
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
    @SuppressWarnings("unchecked")
	public  Set<String>  getKeysByPrex( String prex) {
       return redisTemplate.keys(prex + "*");
    }
 
    
    /**
     * 
     * @description 根据后缀获取所有的key
     * @param suffix  后缀
     * @throws Exception
     * @return Set<String>   
     * @author wangxiaowei 
     * @date 2019/07/17
     * @modifier xxx
     * @modifyDate 修改日期
     */
    @SuppressWarnings("unchecked")
	public Set<String>  getKeysBySuffix(String suffix) {
      return redisTemplate.keys("*"+suffix);
    }

    

    /**
     * 
     * @description 设置Map 单个实体类存储或list<实体类>
     * @param hashName hash名称
     * @param key
     * @param javaBean  Java实体类
     * @throws Exception
     * @return Object   
     * @author wangxiaowei 
     * @date 2019/07/17
     * @modifier xxx
     * @modifyDate 修改日期
     */
    public void hsetJson(String hashName,String key,Object javaBean) {
    	
    	
    	try {
			hset(hashName, key, jacksonUtil.java2json(javaBean));
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
		}
    }
    /**
     * 
     * @description 获取map中的json串并转换成javaBean返回
     * @param hashName  名称
     * @param key 
     * @param beanClass 目标对象class
     * @throws Exception
     * @return Object   
     * @author wangxiaowei 
     * @date 2019/07/17
     * @modifier xxx
     * @modifyDate 修改日期
     */
  
	public <T>T hgetObjByJson(String hashName,String key,Class<T> beanClass) {
    	
		Object obj;
    	
       try {
    	   obj = hget(hashName, key);
    	   if(obj ==null) {
   			return null;
   		   }
		return	jacksonUtil.json2javaBean(hget(hashName, key).toString(), beanClass);
	} catch (Exception e) {
		log.error(ErrorCode.CODE_1002001_MSG, e);
		return null;
			
	}
    	
    }
    
   
    
    /**
     * 
     * @description 获取map中的list<eneity>json串并返回
     * @param hashName  名称
     * @param key 
     * @param beanClass 实体类class
     * @throws Exception
     * @return List<T>   
     * @author wangxiaowei 
     * @date 2019/07/18
     * @modifier xxx
     * @modifyDate 修改日期
     */
	public <T> List<T> hgetListByJson(String hashName,String key,Class<T> beanClass) {
    	
    	Object obj;
		
    	try {
    		obj = hget(hashName, key);
    		if(obj ==null) {
    			return new ArrayList<>();
    		}
    		return jacksonUtil.json2list(hget(hashName, key).toString(), beanClass);
		} catch (Exception e) {
			log.error(ErrorCode.CODE_1002001_MSG, e);
			return new ArrayList<>();
		}
    }
    
	/**
	 * 
	 * @description 获取SortSet  正序 从小到大
	 * @param sortName  名称
	 * @throws Exception
	 * @return Cursor<ZSetOperations.TypedTuple<Object>>   key-value
	 * @author wangxiaowei 
	 * @date 2019/07/23
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@SuppressWarnings("unchecked")
	public Cursor<ZSetOperations.TypedTuple<Object>> sortSetGetAll(String sortName){
		
		return redisTemplate.opsForZSet().scan(sortName, ScanOptions.NONE);
		
		
	}
	
	/**
	 * 
	 * @description 倒序 大到小  key-value ,pageNum和pageSize都是 则查所有
	 * @param sortName
	 * @param pageNum
	 * @param pagesize
	 * @throws Exception
	 * @return Set<ZSetOperations.TypedTuple<Object>>   key-value
	 * @author wangxiaowei 
	 * @date 2019/07/23
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
 @SuppressWarnings("unchecked")
public Set<ZSetOperations.TypedTuple<Object>>  sortRevorseRang(String sortName,Integer pageNum, Integer pagesize){
	 long start = pagesize * (pageNum - 1);
	long end = start + pagesize - 1;
	 
	return redisTemplate.opsForZSet().reverseRangeWithScores(sortName,start,end);
	 
 }
	
	
}
