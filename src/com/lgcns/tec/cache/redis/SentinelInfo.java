package com.lgcns.tec.cache.redis;

import redis.clients.jedis.Jedis;

public class SentinelInfo {
	
    public static void getSwitchMasterInfo()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getSentinel();
	    	getSwitchMasterInfo ( jedisInst );
    	} catch (Exception e ) {
    		e.printStackTrace();
    	} finally {
	    	jedisInst.close();
	    	Redis.releaseSentinelPool();
    	}
    }
    
    public static void getSwitchMasterInfo( Jedis jedisInst )
    {
    	jedisInst.subscribe(new SentinelSubscriber(), "+switch-master");   
    	
    	System.out.println("Done Subscribe");
    		
    }
    
    public static void getODownInfo()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getSentinel();
	    	getODownInfo ( jedisInst );
    	} catch (Exception e ) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    		Redis.releaseSentinelPool();
    	}
    }
    
    public static void getODownInfo( Jedis jedisInst )
    {
    	jedisInst.subscribe(new SentinelSubscriber(), "+odown");   
    	
    	System.out.println("Done Subscribe");    	
    	
    }
    

}
