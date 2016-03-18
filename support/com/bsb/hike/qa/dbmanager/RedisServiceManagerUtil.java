package com.bsb.hike.qa.dbmanager;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.bsb.hike.base.HikeAutomationConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


public class RedisServiceManagerUtil 
{

	private JedisPool redisPool;
	public static final int DEFAULT_TIMEOUT = 5000;
	private static volatile RedisServiceManagerUtil redisServiceManagerUtil = null;
	private static int maxRetryCount = 1;

	public static RedisServiceManagerUtil getInstance()
	{
		if (redisServiceManagerUtil == null)
		{
			synchronized(RedisServiceManagerUtil.class) {
				if(redisServiceManagerUtil == null) {
					redisServiceManagerUtil = new RedisServiceManagerUtil(HikeAutomationConfig.getInstance().getRedisDbHost(), HikeAutomationConfig.getInstance().getRedisDbPort(), 0);
				}
			}
		}
		return redisServiceManagerUtil;
	}

	public RedisServiceManagerUtil(String redisHost, int redisPort, int dbnum)
	{
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxActive(100);
		poolConfig.setMaxIdle(20);
		redisPool = new JedisPool(poolConfig, redisHost, redisPort, DEFAULT_TIMEOUT, null, dbnum);
	}

	//***************************** Hike Redis related functions ************************
	public String getUserPin(String msisdn){
		return get("pincodes-"+msisdn);
	}

	public void setUserPin(String msisdn, String pin){
		setKey("pincodes-"+msisdn, pin);
		System.out.println("User Pin is set as"+get("pincodes-"+msisdn));
	}

	public String getUserUid(String msisdn){
		return hget("msisdns", msisdn);
	}

	public String getVMN(String from,String to){
		String value=hget("comviva::"+to, from);
		if(StringUtils.isEmpty(value))
			return null;
		else{
			int index=Integer.parseInt(value.split("::")[0])-1;
			return lindex("scCOMVIVA", index);
		}

	}
	public boolean isDnd(String msisdn)
	{
		if(getBit("traidnd_"+msisdn.substring(3, 9), Long.valueOf(msisdn.substring(9, 13)).longValue()))
		{
			return true;
		}
		return false;
	}

	public void setDnd(String msisdn)
	{
		setBit("traidnd_"+msisdn.substring(3, 9), Long.valueOf(msisdn.substring(9, 13)).longValue(),true);
	}
	// ********************** Redis DB operations *************************

	private Object callGenericMethodWithRetry(Method method, Object... args) 
	{
		int retryCount = 0;
		while(retryCount <= maxRetryCount)
		{
			try
			{
				return callGenericMethod(method, args);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				if(retryCount++ == maxRetryCount)
				{
					throw new RuntimeException(e.getMessage());  
				}
			}
		}
		return null;
	}

	private Object callGenericMethod(Method method, Object... args) throws Exception
	{
		Jedis redis = null;
		try
		{
			redis = redisPool.getResource();
			return method.invoke(redis, args);
		}
		catch (JedisConnectionException e)
		{
			if (redis != null)
			{
				redisPool.returnBrokenResource(redis);
				redis = null;
			}
			throw e;
		}
		finally
		{
			if (redis != null)
			{
				redisPool.returnResource(redis);
			}
		}

	}

	private Method getMethodUtil(String functionName, Class<?>... args)
	{        
		Method method = null;
		try
		{ 
			method = Jedis.class.getMethod(functionName, args); 
		}
		catch (SecurityException e) { e.printStackTrace(); }
		catch (NoSuchMethodException e) {e.printStackTrace();}
		return method;
	}

	public boolean exists(String key)
    {
        return (Boolean) callGenericMethodWithRetry(getMethodUtil("exists", String.class), key);
    }

    public long setnx(String key, String val)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("setnx", String.class, String.class), key, val);
    }

    public String rename(String oldkey, String newkey)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("rename", String.class, String.class), oldkey, newkey);
    }

    public long delete(String key)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("del", String.class), key);
    }

    public long setExpiration(String key, int seconds)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("expire", String.class, int.class), key, seconds);
    }

    public String get(String keyName)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("get", String.class), keyName);
    }

    public boolean getBit(String key, long offset)
    {
        return (Boolean) callGenericMethodWithRetry(getMethodUtil("getbit", String.class, long.class), key, offset);
    }

    public boolean setBit(String key, long offset, boolean value)
    {
        return (Boolean) callGenericMethodWithRetry(getMethodUtil("setbit", String.class, long.class, boolean.class), key, offset, value);
    }

    public List<String> mget(Collection<String> keys)
    {
        return mget((String[]) keys.toArray(new String[keys.size()]));
    }

    public List<String> mget(String... keys)
    {
        if(keys == null || keys.length == 0)
        {
            return null;
        }
        return (List<String>) callGenericMethodWithRetry(getMethodUtil("mget", String[].class), (Object)keys);
    }

    public Long incrBy(String key, long value)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("incrBy", String.class, long.class), key, value);
    }

    public String setKey(String field, String value)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("set", String.class, String.class), field, value);
    }

    public String setKeyEx(String key, String value, int ttl)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("setex", String.class, int.class, String.class), key, ttl, value);
    }

    public List<String> hmget(String key, Collection<String> fields)
    {
        return hmget(key, fields.toArray(new String[fields.size()]));
    }

    public List<String> hmget(String key, String[] fields)
    {
        if(fields == null || fields.length == 0)
        {
            return null;
        }
        return (List<String>) callGenericMethodWithRetry(getMethodUtil("hmget", String.class, String[].class), key, fields);
    }

    public String hget(String key, String field)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("hget", String.class, String.class), key, field);
    }
    
    public Map<String, String> hgetAll(String key)
    {
        return (Map<String, String>) callGenericMethodWithRetry(getMethodUtil("hgetAll", String.class), key);
    }
    
    public Long hset(String key, String field, String value)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("hset", String.class, String.class, String.class), key, field, value);
    }
    
    public Long hdel(String key, String... field)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("hdel", String.class, String[].class), key, field);
    }

    public boolean hsetnx(String key, String field, String value)
    {
        return 1 == (Long)callGenericMethodWithRetry(getMethodUtil("hsetnx", String.class, String.class, String.class), field, value);
    }

    public Long hlen(String mapName)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("hlen", String.class), mapName);
    }

    public Long hincrBy(String key, String field, long value)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("hincrBy", String.class, String.class, long.class), key, field, value);
    }

    public Long sAdd(String setName, String... members)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("sadd", String.class, String[].class), setName, members);
    }

    public boolean sIsMember(String setName, String member)
    {
        return (Boolean) callGenericMethodWithRetry(getMethodUtil("sismember", String.class, String.class), setName, member);
    }

    public Long sRem(String setName, String... member)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("srem", String.class, String[].class), setName, member);
    }

    public Set<String> smembers(String setName)
    {
        return (Set<String>) callGenericMethodWithRetry(getMethodUtil("smembers", String.class), setName);
    }

    /**
     * This function returns members of the set
     *
     * @param setName
     * @return
     */
    public long scard(String setName)
    {
        return (Long) callGenericMethodWithRetry(getMethodUtil("scard", String.class), setName);
    }

    public void sadd(String setname, Set<String> elements)
    {
        callGenericMethodWithRetry(getMethodUtil("sadd", String.class, String[].class), setname, (String[]) elements.toArray(new String[elements.size()]));
    }
    
    public String lindex(String listName, int index)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("lindex", String.class, long.class), listName, index);
    }
    
    public String keys(String pattern)
    {
        return (String) callGenericMethodWithRetry(getMethodUtil("keys", String.class), pattern);
    }
}
