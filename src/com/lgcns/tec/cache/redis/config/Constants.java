package com.lgcns.tec.cache.redis.config;

import redis.clients.jedis.Protocol;

public class Constants {
	
	// Redis Server Default Configuration
    public static final String DEFAULT_SERVER_HOST = Protocol.DEFAULT_HOST;
    public static final int DEFAULT_SERVER_PORT = Protocol.DEFAULT_PORT;
    public static final int DEFAULT_CONNECTION_TIMEOUT = Protocol.DEFAULT_TIMEOUT;
    public static final int DEFAULT_SO_TIMEOUT = Protocol.DEFAULT_TIMEOUT;
    public static final String DEFAULT_PASSWORD = "tecpass";
    public static final int DEFAULT_DATABASE = Protocol.DEFAULT_DATABASE;
    public static final String DEFAULT_CLIENTNAME = "";
    public static final boolean IS_SSL = false;
    public static final String DEFAULT_SSL_SOCKET_FACTORY = "";
    public static final String DEFAULT_SSL_PARAMETER = "";
    public static final String DEFAULT_HOSTNAME_VERIFIER = "";
    public static final int DEFAULT_SENTINEL_PORT = Protocol.DEFAULT_SENTINEL_PORT;
    
    // Redis Server Status Code
    public static final int STATUS_OK = 0;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_UNKNOWN = 2;
    
    
    // Others
    public static final String CONFIG_VALUE_SEPARATER = ";";

    

}
