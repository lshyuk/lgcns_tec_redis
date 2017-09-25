package com.lgcns.tec.cache.redis.test;

import com.lgcns.tec.cache.redis.Redis;

import redis.clients.jedis.Jedis;

public class PublishMain {
    public static void main(String args[])
    {
    	publishMessage();
    }
    
    public static void publishMessage()
    {
    	Jedis jedisInst = null;
    	
    	jedisInst = Redis.getInstance();
    	
    	jedisInst.publish( "channel_1", "DO YOU HEAR ME" );
    	
    }
}
