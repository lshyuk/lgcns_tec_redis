package com.lgcns.tec.cache.redis.config;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import redis.clients.jedis.JedisShardInfo;

public class RedisClientConfig{

	private static RedisClientConfig configInstance;
	private static Configuration config;
	
	private String useDefaultValue;
	private String serverHost;
    private int serverPort;
    private int  connctionTimeout;
    private int soTimeout;
    private String password;
    private int database;
    private String clientName;
    private boolean ssl;  // not implement yet
    private String sslSocketFactory;  // not implement yet
    private String sslParameters; // not implement yet
    private String hostnameVerifier;
    private LinkedList<JedisShardInfo> masterServerList;
    private LinkedList<JedisShardInfo> sentinelServerList;
    
    
    public static RedisClientConfig getInstance()
    {
        if ( configInstance == null ) configInstance = new RedisClientConfig();
        
        return configInstance;
    }
    
    /**
	 * @return the masterServerList
	 */
	public LinkedList<JedisShardInfo> getMasterServerList() {
		return masterServerList;
	}

	/**
	 * @param masterServerList the masterServerList to set
	 */
	public void setMasterServerList(LinkedList<JedisShardInfo> masterServerList) {
		this.masterServerList = masterServerList;
	}


	/**
	 * 
	 * @return
	 */
	public LinkedList<JedisShardInfo> getSentinelServerList() {
		return sentinelServerList;
	}


	
	public void setSentinelServerList(LinkedList<JedisShardInfo> sentinelServerList) {
		this.sentinelServerList = sentinelServerList;
	}
	
	public String getUseDefaultValue()
    {
    	return useDefaultValue;
    }

    public void setUseDefaultValue( String useDefaultValue )
    {
    	if ( "y".equalsIgnoreCase(useDefaultValue) == true ) this.useDefaultValue = "y";
    	else if ( "n".equalsIgnoreCase(useDefaultValue) == true ) this.useDefaultValue = "n";
    	else this.useDefaultValue = "n";    	
    }
    
	/**
	 * @return the host
	 * @throws Exception 
	 */
	public String getServerHost() {
		return serverHost;
	}
	
	/**
	 * @param host the host to set
	 */
	public void setServerHost(String host) {	
		if( host == null ) {
			if ( "y".equals( getUseDefaultValue()) ) this.serverHost = Constants.DEFAULT_SERVER_HOST;
			else this.serverHost = "";
		}
		
		this.serverHost = host;
	}
	
	/**
	 * @return the port
	 */
	public int getServerPort() {
		return serverPort;
	}
	
	/**
	 * @param port the port to set
	 */
	public void setServerPort(int port) {
		this.serverPort = port;
	}
	
	/**
	 * @return the connctionTimeout
	 */
	public int getConnctionTimeout() {
		return connctionTimeout;
	}
	
	/**
	 * @param connctionTimeout the connctionTimeout to set
	 */
	public void setConnctionTimeout(int connctionTimeout) {
		this.connctionTimeout = connctionTimeout;
	}
	
	/**
	 * @return the soTimeout
	 */
	public int getSoTimeout() {
		return soTimeout;
	}
	
	/**
	 * @param soTimeout the soTimeout to set
	 */
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the database
	 */
	public int getDatabase() {
		return database;
	}
	
	/**
	 * @param database the database to set
	 */
	public void setDatabase(int database) {
		this.database = database;
	}
	
	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}
	
	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	/**
	 * @return the ssl
	 */
	public boolean isSsl() {
		return ssl;
	}
	
	/**
	 * @param ssl the ssl to set
	 */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
	
	/**
	 * @return the sslSocketFactory
	 */
	public String getSslSocketFactory() {
		return sslSocketFactory;
	}
	
	/**
	 * @param sslSocketFactory the sslSocketFactory to set
	 */
	public void setSslSocketFactory(String sslSocketFactory) {
		this.sslSocketFactory = sslSocketFactory;
	}
	
	/**
	 * @return the sslParameters
	 */
	public String getSslParameters() {
		return sslParameters;
	}
	
	/**
	 * @param sslParameters the sslParameters to set
	 */
	public void setSslParameters(String sslParameters) {
		this.sslParameters = sslParameters;
	}
	
	/**
	 * @return the hostnameVerifier
	 */
	public String getHostnameVerifier() {
		return hostnameVerifier;
	}
	
	/**
	 * @param hostnameVerifier the hostnameVerifier to set
	 */
	public void setHostnameVerifier(String hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	
    public boolean loadConfig()
    {
    	 //Configurations configs = new Configurations();
    	 try {
			
    		if( config == null ) {
        		String confFileName = System.getProperty("redis.client.config");
        		if ( confFileName == null ) confFileName = "C:/DevOnFrameStudio/workspace/TecRedisCommon/config/RedisClient.conf";
        		
    			config = new Configurations().properties(new File( confFileName ));
    		}
			
			this.setUseDefaultValue( getRedisClientConfigString(config, "use_default_value"));
			this.setServerHost( getRedisClientConfigString(config, "server_host"));
			this.setServerPort( getRedisClientConfigInt(config, "server_port"));
			this.setConnctionTimeout( getRedisClientConfigInt(config, "connectionTimeouts"));
			this.setSoTimeout( getRedisClientConfigInt (config, "soTimeout")) ;
			this.setPassword( getRedisClientConfigString(config, "password"));
			
			this.setDatabase( getRedisClientConfigInt(config,"database"));
			this.setClientName(getRedisClientConfigString(config, "clientName"));
			this.setSsl(getRedisClientConfigBoolean(config, "ssl"));
			this.setSslSocketFactory(getRedisClientConfigString(config, "sslSocketFactory"));
			this.setSslParameters( getRedisClientConfigString(config, "sslParameters"));
			this.setHostnameVerifier( getRedisClientConfigString(config, "hostnameVerifier"));
			this.setMasterServerList(getRedisServerListConfig(config, "master_server_list"));
			this.setSentinelServerList(getRedisServerListConfig(config, "sentinel_server_list"));

			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return true;
    }
    
    
    public String getRedisClientConfigString( Configuration config, String param ) {
    	String value = config.getString( param );    	
    	return value;
    }
    	
    public int getRedisClientConfigInt( Configuration config, String param ) {
    	int value = 0;    	
    	try { value = config.getInt( param ); } catch ( Exception e ) { value = 0; }    	
    	return value;
    }
    
    public LinkedList<JedisShardInfo> getRedisServerListConfig( Configuration config, String param ) {
    	  	
    	LinkedList<JedisShardInfo> redisMasterServerList = new LinkedList<JedisShardInfo>();
    	
    	try { 
    		String valuelist = config.getString( param ); 
    	
	    	StringTokenizer st = new StringTokenizer( valuelist, Constants.CONFIG_VALUE_SEPARATER );
	    	String serverInfo = null;
	    	String host;
	    	int port;
	    	
	    	while( st.hasMoreTokens() )
	    	{
	        	//ServerEntity redisServer = new ServerEntity();
	    		
	    		serverInfo = st.nextToken();
	    		StringTokenizer svi = new StringTokenizer( serverInfo, ":" );
	    		host = svi.nextToken();
	    		port = Integer.parseInt( svi.nextToken() );
	    		
	    		JedisShardInfo shardInfo = new JedisShardInfo( host, port );
	    		redisMasterServerList.add( shardInfo ); 
	    		
	    	}
    	} catch ( Exception e ) { e.printStackTrace(); }  
    	
    	return redisMasterServerList;
    }
    
    public boolean getRedisClientConfigBoolean( Configuration config, String param ) {
    	boolean value = false;    	
    	try { config.getBoolean( param ); } catch ( Exception e ) { value = false; }    	
    	return value;
    }
    
    public boolean printConfig()
    {
    	if( config == null ) {
    		System.err.println( "Redis Client Config File is not loaded (RedisClient.conf)");
    		// Exception 처리 ?? 또는 Default 설정 처리
    	}
    	
    	System.out.println( "================================================" );
    	System.out.println( "[Redis Client Configuration]");
    	System.out.println( "------------------------------------------------" );
    	 //Configurations configs = new Configurations();
		System.out.println( "Redis Server Host  : " + this.getServerHost());
		System.out.println( "Redis Server Port  : " + this.getServerPort());
		System.out.println( "Connection Timeout : " + this.getConnctionTimeout());
		System.out.println( "Socket Timeout     : " + this.getSoTimeout());
		System.out.println( "Password           : " + this.getPassword()); // encryption is not supported
		System.out.println( "Database           : " + this.getDatabase());
		System.out.println( "Client Name        : " + this.getClientName());
		System.out.println( "SSL                : " + this.isSsl());
		System.out.println( "SSL Socket Factory : " + this.getSslSocketFactory());
		System.out.println( "SSL Parameters     : " + this.getSslParameters());
		System.out.println( "Hostname Verifier  : " + this.getHostnameVerifier());
		Iterator<JedisShardInfo> masterServerList = this.getMasterServerList().iterator();
		JedisShardInfo masterServer;
		
		while ( masterServerList.hasNext() ) {
			masterServer = masterServerList.next();
		    System.out.println( "Server Host / Port : " + masterServer.getHost() + " / " + masterServer.getPort() );
		}
		System.out.println( "================================================" );
    	return true;
    }    
}
