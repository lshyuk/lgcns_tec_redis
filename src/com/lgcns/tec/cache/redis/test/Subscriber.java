package com.lgcns.tec.cache.redis.test;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends JedisPubSub {
    
	@Override
	public void onMessage( String channel, String message )
    {
    	System.out.println("subscribe onMessage : [" + message + "] "  + channel );
    }
    
	@Override
	public void onPMessage( String pattern, String channel, String message )
	{
		
	}
	
	@Override
	public void onSubscribe( String channel, int subscribedChannel )
	{
		System.out.println("subscribe onSubscribe : [" + channel + "] " + subscribedChannel );
	}
    
	
	@Override
	public void onUnsubscribe( String channel, int subscribedChannels )
	{
		
	}
	
	@Override
	public void onPSubscribe( String channel, int subscribedChannels )
	{
		
	}
	
	@Override
	public void onPUnsubscribe( String channel, int subscribedChannels )
	{
		
	}

	
	
}
