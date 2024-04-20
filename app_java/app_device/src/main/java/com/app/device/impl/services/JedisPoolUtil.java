package com.app.device.impl.services;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class JedisPoolUtil {
    private final static Logger log = LoggerFactory.getLogger(JedisPoolUtil.class);
    private static final String PROPERTIES_PATH = "redis.properties";
    private static JedisPool jedisPool;
    @Autowired
    private Environment p;

    public void redisStart() {
        if (jedisPool == null) {
            log.info("NO jedispool---"+jedisPool);
            try {
                init();
            } catch (FileNotFoundException e) {
                log.error("初始化JedisPool出错！错误信息："+e.getMessage());
            } catch (IOException e) {
                log.error("初始化JedisPool出错！错误信息："+e.getMessage());
            }
        }else{
            log.info("exist jedispool---"+jedisPool);
        }
    }

    /**
     * 初始化Jedis连接池
     *
     * @throws IOException
     */
    private    void init() throws IOException {
        //String username = environment.getProperty("redis.port");
        //System.out.println("rabbitmq当前用户名为:  " + username);
        /*URL resource = JedisPoolUtil.class.getClassLoader().getResource(PROPERTIES_PATH);
        if (resource == null) {
            throw new FileNotFoundException("没有找到指定redis的配置文件:" + PROPERTIES_PATH);
        }
        log.info("resource redis---"+resource);

        //加载配置文件
        InputStream input = new FileInputStream(resource.getFile());
        Properties p = new Properties();
        p.load(input);*/


         // 获取redis.properties获取配置内容

        String host = p.getProperty("redis.host") == null ? "localhost" : p.getProperty("redis.host");
        int port = p.getProperty("redis.port") == null ? 6379 : Integer.parseInt(p.getProperty("redis.port"));
        log.info("redis.port--"+port);
        //port=8096;
        String auth = p.getProperty("redis.auth");
        log.info("auth--"+auth);
        //auth="linkom88";
        int poolTimeOut = p.getProperty("connectionTimeOut") == null ? 2000
                : Integer.parseInt(p.getProperty("connectionTimeOut"));

        //String host="121.40.242.143";
        log.info("redis.host--"+host);
        //int port=8096;
        //String auth="linkom88";
        //int poolTimeOut=2000;
        /**
         * 是否使用redis默认配置
         */

        boolean isSetDefault = p.getProperty("defaultSetting") == null ? true
                : Boolean.parseBoolean(p.getProperty("defaultSetting"));

        if (isSetDefault) {
            jedisPool = new JedisPool(new GenericObjectPoolConfig(), host, port, poolTimeOut, auth);
        } else {
            JedisPoolConfig config = new JedisPoolConfig();
            String blockWhenExhausted = p.getProperty("redis.blockWhenExhausted");
            if (blockWhenExhausted != null) {
                config.setBlockWhenExhausted(Boolean.parseBoolean(blockWhenExhausted));
            }
            String evictionPolicyClassName = p.getProperty("redis.evictionPolicyClassName");
            if (evictionPolicyClassName != null) {
                config.setEvictionPolicyClassName(evictionPolicyClassName);
            }
            String jmxEnabled = p.getProperty("redis.jmxEnabled");
            if (jmxEnabled != null) {
                config.setJmxEnabled(Boolean.parseBoolean(jmxEnabled));
            }
            String lifo = p.getProperty("redis.lifo");
            if (lifo != null) {
                config.setLifo(Boolean.parseBoolean(lifo));
            }
            String maxIdle = p.getProperty("redis.maxIdle");
            if (maxIdle != null) {
                config.setMaxIdle(Integer.parseInt(maxIdle));
            }
            String maxTotal = p.getProperty("redis.maxTotal");
            if (maxTotal != null) {
                config.setMaxTotal(Integer.parseInt(maxTotal));
            }
            String maxWaitMillis = p.getProperty("redis.maxWaitMillis");
            if (maxWaitMillis != null) {
                config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
            }
            String minEvictableIdleTimeMillis = p.getProperty("redis.minEvictableIdleTimeMillis");
            if (minEvictableIdleTimeMillis != null) {
                config.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
            }
            String minIdle = p.getProperty("redis.minIdle");
            if (minIdle != null) {
                config.setMinIdle(Integer.parseInt(minIdle));
            }
            String numTestsPerEvictionRun = p.getProperty("redis.numTestsPerEvictionRun");
            if (numTestsPerEvictionRun != null) {
                config.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
            }
            String softMinEvictableIdleTimeMillis = p.getProperty("redis.softMinEvictableIdleTimeMillis");
            if (softMinEvictableIdleTimeMillis != null) {
                config.setSoftMinEvictableIdleTimeMillis(Long.parseLong(softMinEvictableIdleTimeMillis));
            }
            String testOnBorrow = p.getProperty("redis.testOnBorrow");
            if (testOnBorrow != null) {
                config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
            }
            String testWhileIdle = p.getProperty("redis.testWhileIdle");
            if (testWhileIdle != null) {
                config.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
            }
            String timeBetweenEvictionRunsMillis = p.getProperty("redus.timeBetweenEvictionRunsMillis");
            if (timeBetweenEvictionRunsMillis != null) {
                config.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
            }


            jedisPool = new JedisPool(config, host, port, poolTimeOut, auth);
        }
        log.info("jedisPool--"+jedisPool);
    }

    /**
     * 从JedisPool连接池中获取一个连接
     * @return
     */
    public static  Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 将一个使用完的连接还给连接池
     * @param jedis
     */
    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
