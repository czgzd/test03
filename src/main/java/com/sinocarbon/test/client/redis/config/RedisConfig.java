package com.sinocarbon.test.client.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {

	/** redis数据库 */
	@Value("${spring.redis.database}")
	private int redisDatabase;
	
	/** redis数据库1 */
	@Value("${spring.redis.cache-database}")
	private int cacheDatabase;
	
	/** redis数据库ip */
	@Value("${spring.redis.host}")
	private String redisHost;
	
	/** redis数据库端口 */
	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.redis.timeout}")
	private int redisTimeout;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int redisPoolMaxActive;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private int redisPoolMaxWait;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int redisPoolMaxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int redisPoolMinIdle;

	@Bean(name = "oauthTokenRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        return buildRedisTemplate(buildConnectionFactory(jedisPoolConfig(),redisDatabase));
    }
	
	@Bean(name = "cacheRedisTemplate")
    public RedisTemplate<String, Object> cacheRedisTemplate() {
        return buildRedisTemplate(buildConnectionFactory(jedisPoolConfig(),cacheDatabase));
    }
	
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> buildRedisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
	
	/**
     * 连接池配置信息
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisPoolMaxIdle);
        jedisPoolConfig.setMinIdle(redisPoolMinIdle);
        jedisPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        jedisPoolConfig.setMaxTotal(redisPoolMaxActive);
        return jedisPoolConfig;
    }
    
    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @param database
     * @return
     */
    public JedisConnectionFactory buildConnectionFactory(JedisPoolConfig jedisPoolConfig, int database) {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(redisHost);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(database);
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(redisPort);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisBuilder=(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        jedisBuilder.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jedisBuilder.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

}
