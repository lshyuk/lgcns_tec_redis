package com.lgcns.tec.cache.redis.test;

import com.lgcns.tec.cache.redis.Redis;

import redis.clients.jedis.Jedis;

public class SubscribeMain {
    public static void main(String args[])
    {
    	Jedis jedisInst = null;
    	
    	jedisInst = Redis.getInstance();
    	
    	jedisInst.subscribe(new Subscriber(), "channel_1");   
    	
    	System.out.println("Done Subscribe");
    }
}
