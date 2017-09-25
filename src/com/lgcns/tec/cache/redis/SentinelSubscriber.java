package com.lgcns.tec.cache.redis;

import redis.clients.jedis.JedisPubSub;

public class SentinelSubscriber  extends JedisPubSub {
    
	@Override
	public void onMessage( String channel, String message )
    {
    	if ( "+switch-master".equals( channel ) == true ) {
    		System.out.println("Switch Master");
    		System.out.println( message );
    		
    		// update server list
    		
    	}
    	else if ( "+odown".equals( channel ) == true ) {
    		System.out.println("ODOWN LIST");
    		System.out.println( message );
    		
    	} else {
    		System.err.println( "Unknown Channel ID [" + channel + "]");
    	}
    }
    
	@Override
	public void onPMessage( String pattern, String channel, String message )
	{
		
	}
	
	@Override
	public void onSubscribe( String channel, int subscribedChannel )
	{
		System.out.println("subscribe onSubscribe : [" + channel + "] is registered... " + subscribedChannel );
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

