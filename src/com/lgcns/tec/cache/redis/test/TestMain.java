package com.lgcns.tec.cache.redis.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lgcns.tec.cache.redis.Redis;
import com.lgcns.tec.cache.redis.config.RedisClientConfig;
import com.lgcns.tec.cache.redis.hash.JumpHash;
import com.lgcns.tec.cache.redis.serializer.KryoSerializer;
import com.lgcns.tec.cache.redis.serializer.KryoSerializerPool;
import com.lgcns.tec.cache.redis.serializer.SimpleSerializer;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestMain {
    public static void main(String args[])
    {
 /*   	for ( int i = 0; i < 5; i++) 
    	{
    	    testRedisClientConfig();
    	    try { Thread.sleep(5000); } catch (Exception e) {}
    	}*/

    	
    	long beforeFreeMemory = Runtime.getRuntime().freeMemory() ;
        System.out.println("Free Memory (Before) : " + beforeFreeMemory );
        
    	//1. Delete Cache Data Sample
//    	Util cacheUtil = new Util();
//    	cacheUtil.flushAll();
        //setSimpleCacheDataWithExpire();
        //setSimpleCacheData();
        //getSimpleCacheData();
        //delSimpleCacheData();
        //renameKeyName();
        /*setMultiCacheData();
        getMultiCacheData();*/
        //appendCacheData();
        //setHashData();
        /*delHashData();
        getHashData();*/
        //getHashFields();
        //getHashValues();
        
        //setSetData();
        //removeSetData();
        //getSetDataCount();
        //getSetData();
        //isSetData();
        //setSetData();
        //intersectSetData();
        //getSetDataCount();
        //unionSetData();
        //diffSetData();
        
        //setObjectData();
        //getObjectData();
        
        //getHashMultiData();
        //lPushData();
        //lPopData();
        //lPushDataAndSort();
        //setTransactionData();
        //getTransactionData();
        //setPipelinedData();
        
        //clusterAddTest();
        
       // setShardData();

        // performance test for collection vs. object
/*        perfTestHashMap(1);        
        perfTestObject(1);
        perfTestObjectKryo(1);
        
        perfTestHashMap(10);        
        perfTestObject(10);
        perfTestObjectKryo(10);
        
        perfTestHashMap(100);        
        perfTestObject(100);
        perfTestObjectKryo(100);
        
        perfTestHashMap(1000);        
        perfTestObject(1000);
        perfTestObjectKryo(1000);

        perfTestHashMap(10000);        
        perfTestObject(10000);
        perfTestObjectKryo(10000);
        
        perfTestHashMap(100000);        
        perfTestObject(100000);        
        perfTestObjectKryo(100000);*/

        setObjectDataWithKryoPool();
        getObjectDataWithKryoPool();

        // Sentinel Fail Over Notification Test
/*        SentinelInfo.getSwitchMasterInfo();
        System.out.println("???----1");

        SentinelInfo.getODownInfo();
        System.out.println("???----2");
*/        
        //clientAddTest();
        
        /*
        clientTrxAddTest();
        clientTrxGetTest();        
    	clientPipeLineAddTest();
    	
    	shardClientAddTest();
        shardClientGetTest();
*/
    	
/*    	clientSetInstanceTestDfltSerialization();
    	clientGetInstanceTestDfltSerialization();*/
    	long afterFreeMemory = Runtime.getRuntime().freeMemory() ;
    	long usedMemory = beforeFreeMemory - afterFreeMemory;
    	System.out.println("Free Memory (After) : " + afterFreeMemory );
    	System.out.println("Used Memory (After) : " + ( usedMemory / 1024 / 1024 ) );
    }
    
    

    /**
     * 
     */
    public static void setSimpleCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.set("mykey_" + i,  "n" + i);
	    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }    
    
    /**
     * 
     */
    public static void setMultiCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	jedisInst.mset("mykey_1", "myvalue_1", "mykey_2", "myvalue_2", "mykey_3", "myvalue_3");
	    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }    

    /**
     * 
     */
    public static void getMultiCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        List<String> getList = jedisInst.mget("mykey_1","mykey_2", "mykey_3");
	        
		    for(int i=0; i<getList.size(); i++){
			    System.out.println(getList.get(i));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		}
    }
    
    /**
     * 
     */
    public static void appendCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();

	    	jedisInst.append( "mykey_1", "_tec" );
		    jedisInst.append( "mykey_2", "_tec" );
		    jedisInst.append( "mykey_3", "_tec" );
		    
	        List<String> getList = jedisInst.mget("mykey_1","mykey_2", "mykey_3");
	        
		    for(int i=0; i<getList.size(); i++){
			    System.out.println(getList.get(i));
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		}    	
    }
    
    /**
     * 
     */
    public static void setHashData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();

	    	jedisInst.hset( "hashtest_key", "myfield_1", "myvalue_1" );
		    jedisInst.hset( "hashtest_key", "myfield_2", "myvalue_2" );
		    jedisInst.hset( "hashtest_key", "myfield_3", "myvalue_3" );
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		} 
    }
    
    /**
     * 
     */
    public static void getHashMultiData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();

	    	List<String> hashDataList = jedisInst.hmget( "hashtest_key", "myfield_1", "myfield_2", "myfield_3");
	    	
	    	for(int idx = 0; idx < hashDataList.size() ; idx++ )
	    	{
				System.out.println( hashDataList.get(idx) );
			}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		}     	
    }
    
    /**
     * 
     */
    public static void getHashData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();

	    	String hashValue = jedisInst.hget( "hashtest_key", "myfield_2");
	    	System.out.println( "mytest_key -> myfield_2 : " + hashValue );
	    	
	    	Map<String, String> hashMap = jedisInst.hgetAll( "hashtest_key" );
	    	
	    	for( Map.Entry<String, String> entry : hashMap.entrySet() )
	    	{
	    		System.out.println( entry.getKey() + " : " + entry.getValue() );
	    	}
		    
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		} 
    }
    
    
    public static void delHashData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	    	jedisInst.hdel( "hashtest_key", "myfield_1" );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		} 
    }
    
    
    public static void getHashFields()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	    	Set<String> fieldSet = jedisInst.hkeys( "hashtest_key" );
	    	System.out.println( fieldSet );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		} 
    }
    
    public static void getHashValues()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	    	List<String> valueList = jedisInst.hvals( "hashtest_key" );
	    	System.out.println( valueList );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisInst.close();
		} 
    }
    
    /**
     * 
     */
    public static void setSimpleCacheDataWithExpire()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.setex("mykey_" + i, 10,  "n" + i);
	    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }    
    
    /**
     * 
     */
    public static void getSimpleCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance(); 
	        
	    	for (int i = 0; i < 1000; i++) {
	    		System.out.println( jedisInst.get("mykey_" + i) );
	    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }
    
    /**
     * 
     */
    public static void delSimpleCacheData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.del("mykey_" + i);
	    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }
    
    /**
     * 
     */
    public static void renameKeyName()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.rename("mykey_" + i, "mynewkey_" + i);
	    	}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }
    
    
    public static void lPushData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	jedisInst.lpush("mydata", "1");
	    	jedisInst.lpush("mydata", "6");
	    	jedisInst.lpush("mydata", "3");
	    	jedisInst.lpush("mydata", "2");

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }
    
    public static void lPopData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        
	    	long listsize = jedisInst.llen("mydata");
	    	for( int idx = 0; idx < listsize ; idx++ )
	    		System.out.print( jedisInst.lpop("mydata") );
	    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    }
    
    public static void lPushDataAndSort()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();
	        	    	
	    	jedisInst.lpush("mysortdata", "1");
	    	jedisInst.lpush("mysortdata", "6");
	    	jedisInst.lpush("mysortdata", "3");
	    	jedisInst.lpush("mysortdata", "2");

	    	List<?> sortedList = jedisInst.sort("mysortdata");
	    	
	    	for(int idx = 0; idx < sortedList.size() ; idx++){
				System.out.print( sortedList.get(idx) );
			}

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		jedisInst.close();
    	}
    } 
    
    
    
    /**
     * Transaction 에 대한 Set 테스트
     */
    public static void setTransactionData()
    {
    	Jedis jedisInst = null;
    	try {
	    	jedisInst = Redis.getInstance();  // redis.getInstance();
	        
	    	Transaction transaction = jedisInst.multi();
	    	for (int i = 0; i < 1000; i++) {
                transaction.set("transaction_" + i,  "trxn_" + i);
	    	}
	    	
	    	List<Object> result = transaction.exec();
	    	for( int idx = 0; idx < result.size(); idx++ )
	    		System.out.println( result.get(idx) );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    }   

    
    public static void getTransactionData()
    {
    	Jedis jedisInst = null;

    	try {
	    	jedisInst = Redis.getInstance();
	    	//Response<String> response = null;
	    	
	    	Transaction transaction = jedisInst.multi();
	    	
	    	for (int i = 0; i < 1000; i++) {
                transaction.get("transaction_" + i);
	    	}
	    	
	    	List<Object> result = transaction.exec();
	    	for( int idx = 0; idx < result.size(); idx++ )
	    		System.out.println( result.get(idx) );

    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    }  
    
    public static void setPipelinedData()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

	    	Pipeline pipeline = jedisInst.pipelined();
	    	
	    	for (int i = 0; i < 1000; i++) {
	    		pipeline.set("pipeline_" + i,  "n" + i);
	    	}

	    	pipeline.sync();
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    }    
    
    
    public static void setSetData()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.sadd("mySetKey",  "setdata" + i);
	    	}
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}

    }
    
    public static void removeSetData()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

	    	for (int i = 0; i < 1000; i++) {
	    		jedisInst.srem("mySetKey",  "setdata" + i);
	    	}
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}

    }
    
    
    public static long getSetData()
    {
    	Jedis jedisInst = null;
    	long dataCnt = 0;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		Set<String> setData = jedisInst.smembers("mySetKey");
    		
    		System.out.println( setData );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    	
    	return dataCnt;
    }  
    
    
    public static long isSetData()
    {
    	Jedis jedisInst = null;
    	long dataCnt = 0;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		boolean ismember = jedisInst.sismember("mySetKey", "setdata924" );
    		    		
    		System.out.println( ismember );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    	
    	return dataCnt;
    } 
    
    
    public static long getSetDataCount()
    {
    	Jedis jedisInst = null;
    	long dataCnt = 0;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		dataCnt = jedisInst.scard("mySetKey");
    		
    		System.out.println( "Number of Member of mySetKey : " + dataCnt );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    	
    	return dataCnt;
    }   
    
    
    public static long intersectSetData()
    {
    	Jedis jedisInst = null;
    	long dataCnt = 0;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		Set<String> intersectionSet = jedisInst.sinter( "mySetKey", "yourSetKey");
    		    		
    		System.out.println( "Size of interect : " + intersectionSet.size() );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    	
    	return dataCnt;
    }   
    
    
    public static long unionSetData()
    {
    	Jedis jedisInst = null;
    	long dataCnt = 0;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		Set<String> unionSet = jedisInst.sunion( "mySetKey", "yourSetKey" );
    		    		
    		System.out.println( "Size of union : " + unionSet.size() );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    	
    	return dataCnt;
    }    
        
    public static void diffSetData()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance(); // redis.getInstance();

    		Set<String> diffSet = jedisInst.sdiff("mySetKey", "yourSetKey");
    		
    		System.out.println( diffSet );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}
    }   
    
    
    
    public static void setObjectData()
    {
     	Jedis jedisInst = null;
    
     	try {
	    	jedisInst = Redis.getInstance();

	    	SampleVO svo = new SampleVO();
	    	svo.setName( "myName" );
	    	svo.setValue( "myValue" );
	    	
	    	jedisInst.set( ("myTestVOKey") .getBytes(), SimpleSerializer.serialize( svo ) );
	    	
     	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }
    
    
    public static void getObjectData()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance();
	    	
    		byte[] svo = jedisInst.get( "myTestVOKey".getBytes() );
    		SampleVO serverEntity = (SampleVO)SimpleSerializer.unserialize( svo );
    		
    		System.out.println(serverEntity.getName() );
    		System.out.println(serverEntity.getValue() );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}     	
    }    
    


    
    public static void  setObjectDataWithKryo()
    {
     	Jedis jedisInst = null;
    
     	try {
	    	jedisInst = Redis.getInstance();

	    	SampleVO svo = new SampleVO();
	    	svo.setName("myName");
	    	svo.setValue("myValue");
	    	
	    	KryoSerializer<SampleVO> marshaller = new KryoSerializer<SampleVO>( SampleVO.class );

	    	jedisInst.set( ("ObjectKryoKey_1") .getBytes(), marshaller.serialize( svo ) );  		

     	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }        
    
    
    public static void getObjectDataWithKryo()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance();
	    	
    		byte[] sampleVO = jedisInst.get( "ObjectKryoKey_1".getBytes() );
    		
    		KryoSerializer<Object> marshaller = new KryoSerializer<Object>( SampleVO.class );

    		SampleVO serverEntity = (SampleVO)marshaller.deserialize( sampleVO );
	    	System.out.println("Name : " + serverEntity.getName() );
	    	System.out.println("Name : " + serverEntity.getValue() );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    } 
    
    
    
    public static void  setObjectDataWithKryoPool()
    {
     	Jedis jedisInst = null;
    
     	try {
	    	jedisInst = Redis.getInstance();

	    	SampleVO svo = new SampleVO();
	    	svo.setName("myName");
	    	svo.setValue("myPoolValue");
	    	
	    	KryoSerializerPool marshaller = new KryoSerializerPool( SampleVO.class );
	    	jedisInst.set( ("ObjectKryoPoolKey_1") .getBytes(), marshaller.serialize( svo ) );  		

     	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }        
    
    
    public static void getObjectDataWithKryoPool()
    {
    	Jedis jedisInst = null;
    	
    	try {
	    	jedisInst = Redis.getInstance();
	    	
    		byte[] sampleVO = jedisInst.get( "ObjectKryoPoolKey_1".getBytes() );
    		                                  
    		KryoSerializer<Object> marshaller = new KryoSerializer<Object>( SampleVO.class );
    		SampleVO serverEntity = (SampleVO) marshaller.deserialize( sampleVO );  		
    		
	    	System.out.println("Name : " + serverEntity.getName() );
	    	System.out.println("Name : " + serverEntity.getValue() );
	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }
    
    public static void setShardData()
    {
    	ShardedJedis shardJedis = null;
    	try {
            JumpHash jh1 = new JumpHash(10);
            JumpHash jh2 = new JumpHash(11);
    		int bucket1 = (int) jh1.hash("shard_1");
    		int bucket2 = (int) jh2.hash("shard_1");
    		System.out.println( bucket1 + " : " + bucket2 );
	    	shardJedis = Redis.getShardedInstance(); 
	    	
	    	for (int i = 0; i < 1000; i++) {
	    		String result = shardJedis.set(i+"shard_" + i, "n" + i);
	    		
	    		if ( "OK".equalsIgnoreCase(result) == false ) {
	    			System.out.println( "shard_" + i + " is Failed ");
	    		}
	    	}
    	
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		shardJedis.close();
    	}
    }
        
    public static void getShardData()
    {
    	ShardedJedis sjedis = Redis.getShardedInstance(); 
    	
    	String value = null;
    	String key = null;
   	
    	try {
	    	for (int idx = 0; idx < 1000; idx++) {
	    		key = "shard_" + String.valueOf(idx);
	    		
	    		value = sjedis.get(String.valueOf( key ));
	    		
	    		System.out.println( value );
	    	 }	    	
    	} catch (Exception e ) {
    		e.printStackTrace();
    	} finally {
    		sjedis.close();
    	}    	
    }
    
    
    
    public static void perfTestHashMap( int number)
    {
    	Jedis jedisInst = null;
    	long startTime = 0;
    	long endTime = 0;
    	try {
	    	jedisInst = Redis.getInstance();

	    	startTime = System.currentTimeMillis();
	    	for( int idx = 0; idx < number; idx++)
	    	{
	    	   jedisInst.hset( "hashtest_key" + number, "myfield_1" + idx, "myvalue_1" + idx );		    
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTime = System.currentTimeMillis();
			System.out.println("Elaped Time for Hashmap (" + number + ")  : " + (endTime-startTime) + "(ms)");			 
			jedisInst.close();
		}
    }
    

    public static void perfTestObjectKryo(int number)
    {
    	Jedis jedisInst = null;
    	long startTime = 0;
    	long endTime = 0;
    	try {
	    	jedisInst = Redis.getInstance();

	    	startTime = System.currentTimeMillis();

	    	HashMap<String, String> hm = new HashMap<String, String>();
	    	
	    	for( int idx = 0; idx < number; idx++ )
               hm.put( "myfield_1" + idx, "myvalue_1" + idx );	    		
	    	
	    	KryoSerializer<HashMap<String, String>> marshaller = new KryoSerializer<HashMap<String, String>>( HashMap.class );

	    	jedisInst.set( ("kryoobjecttest_key" + number).getBytes(), marshaller.serialize( hm ) );  
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTime = System.currentTimeMillis();
			System.out.println("Elaped Time for Object Kryo (" + number + ")  : " + (endTime-startTime) + "(ms)");			 
			jedisInst.close();
		}    	
    }
    
    public static void perfTestObject(int number)
    {
    	Jedis jedisInst = null;
    	long startTime = 0;
    	long endTime = 0;
    	try {
	    	jedisInst = Redis.getInstance();

	    	startTime = System.currentTimeMillis();

	    	HashMap<String, String> hm = new HashMap<String, String>();
	    	
	    	for( int idx = 0; idx < number; idx++ )
               hm.put( "myfield_1" + idx, "myvalue_1" + idx );	    		
	    	
	    	jedisInst.set( ("objecttest_key" + number ).getBytes(), SimpleSerializer.serialize( hm ) );
	    	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			endTime = System.currentTimeMillis();
			System.out.println("Elaped Time for Object (" + number + ")  : " + (endTime-startTime) + "(ms)");			 
			jedisInst.close();
		}    	
    }
    
    /*    public static byte[] serialize(Object object) {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try {
        // Serialization
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
    } catch (Exception e) {
    	e.printStackTrace();
    }
    return null;
}

public static Object unserialize(byte[] bytes) {
    ByteArrayInputStream bais = null;
    try {
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    } catch (Exception e) {
    	e.printStackTrace();
    }
    return null;
}*/

    
    
    /**
     * ===========================================================================
     */
    
    
    
    public static void testRedisConnection(){
    	
    }
    
    /**
     * Jedis Client Configuration Loading
     * @return
     */
    public static RedisClientConfig testRedisClientConfig(){
    	//RedisClientConfig rcc = new RedisClientConfig();
    	RedisClientConfig rcc = RedisClientConfig.getInstance();
    	rcc.loadConfig();
    	rcc.printConfig();

    	return rcc;
    }
    
    /**
     * Element Adding into Sharded Redis
     */
    public static void shardClientAddTest()
    {
    	ShardedJedis shardJedis = null;
    	try {
    	//Redis redis = new Redis();
    	//redis.createShardedPool();
    	shardJedis = Redis.getShardedInstance(); // redis.getShardedInstance();
    	
    	/*
    	Collection<JedisShardInfo> shardInfoList = shardJedis.getAllShardInfo();

    	Iterator<JedisShardInfo> itr = shardInfoList.iterator();
    	JedisShardInfo shardInfo = null;
    	
    	System.out.println( "shard size : " + shardInfoList.size());  // 출력 결과가 480임... 480 개는 뭘까요?
    	
    	while( itr.hasNext() ){
    		shardInfo = itr.next();
    		System.out.println( "===================================" );
    		System.out.println( "Host : " + shardInfo.getHost() );
    		System.out.println( "Port : " + shardInfo.getPort() );
    		System.out.println( "Connection Timeout : " + shardInfo.getConnectionTimeout() );    		
    		System.out.println( "SO Timeout : " + shardInfo.getSoTimeout() );
    		System.out.println( "DB : " + shardInfo.getDb() );
    		System.out.println( "Weight : " + shardInfo.getWeight() );
    	}
    	*/
    	    	
    	long startTime = System.currentTimeMillis();
    	
    	for (int i = 0; i < 100000; i++) {
    		String result = shardJedis.set("shard_" + i, "n" + i);
    		
    		if ( "OK".equalsIgnoreCase(result) == false ) {
    			System.out.println( "shard_" + i + " is Failed ");
    		}
    	}
    	
    	long endTime = System.currentTimeMillis();
    	
    	System.out.println("Elaped Time (SET) (ms) : " + (endTime-startTime));
    	
    	System.out.println("Done");
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		shardJedis.close();
    	}
    	
/*    	List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
    			"127.0.0.1", 6379), new JedisShardInfo("127.0.0.1", 6389), new JedisShardInfo("127.0.0.1", 6399));

    	ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    	ShardedJedis one = pool.getResource();

    	long start = System.currentTimeMillis();
    	for (int i = 0; i < 1000; i++) {
    		String result = one.set("spn" + i, "n" + i);
    		System.out.println( result );
    	}
    	long end = System.currentTimeMillis();
    	
    	pool.returnResource(one);
    	System.out.println("Simple@Pool SET: " + ((end - start) / 1000.0) + " seconds");

    	pool.destroy();*/
    }
    
    /**
     * Sharding 된 데이터에 대한 Get 테스트
     */
    public static void shardClientGetTest ()
    {
    	//Redis redis = new Redis();
    	ShardedJedis sjedis = Redis.getShardedInstance(); // redis.getShardedInstance();
    	
    	int keyIdx = 0;
    	String value = null;
    	String key = null;
    	String expectedValue = null;
    	try {
	    	for (int i = 0; i < 1000; i++) {
	    		keyIdx = i % 1000;
	    		key = "shard_" + String.valueOf(keyIdx);
	    		
	    		value = sjedis.get(String.valueOf( key ));
	    		
	    	    expectedValue = "n" + String.valueOf( keyIdx );
	    	    
	    	    if ( expectedValue.equals(value) == false ) System.out.println( "[Key]" + key + " [Value]" + value + " : [Expected Value] : " + expectedValue );
	    	 }
	    	
	    	System.out.println( "Done" );
    	} catch (Exception e ) {
    		e.printStackTrace();
    	} finally {
    		sjedis.close();
    	}
    	
    }
    

    
   
    
    public static void clusterAddTest()
    {	
    	JedisCluster jedisCluster = null;
    	Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
    	
    	try {
    		long startTime = System.currentTimeMillis();
	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 15000) );
/*	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 15001) );
	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 15002) );
	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 25000) );
	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 25001) );
	    	clusterNodes.add( new HostAndPort( "10.77.241.173", 25002) );*/

	    	jedisCluster = new JedisCluster(clusterNodes, 35000);
	    	
/*	    	int idx = 0;
	    	Map<String, JedisPool> nodeList = jedisCluster.getClusterNodes();
	    	for ( String nodeName : nodeList.keySet() )				
			{
				try {
					System.out.println( "[" + nodeName + "]" );
					JedisPool jp = nodeList.get(nodeName);
					
					if( jp == null ) System.out.println( "jp is null ");
					else System.out.println( "jp : [" + jp + "]");
					Jedis jedis = nodeList.get(nodeName).getResource();
					if ( jedis == null ) System.out.println( nodeName + " is null ");
					else System.out.println( "jedis : [" + jedis + "]");
					System.out.println( "[" + jedis.clusterInfo() + "]" );
					System.out.println( idx++ );
				} catch (Exception se) { System.out.println( nodeName + " nodeName has problem "); }
				
			}*/
	    	
	    	//System.out.println("===========2");
	    	for (int i = 0; i < 1000; i++) {
	    		jedisCluster.set("mykey_" + i,  "n" + i);
	    	}
	    	//System.out.println("===========3");
	    	long endTime = System.currentTimeMillis();
	    	System.out.println("Elaped Time for Cluster Set (ms) : " + (endTime-startTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
/*			Map<String, JedisPool> nodeList = jedisCluster.getClusterNodes();
			System.out.println("Empty Key : " + nodeList.get(null) );
			System.out.println("nodelist length : " + nodeList.size() );
			int idx = 0;
			for ( String nodeName : nodeList.keySet() )				
			{
				try {
					System.out.println( "[" + nodeName + "]" );
					Jedis jedis = nodeList.get(nodeName).getResource();
					
				} catch (Exception se) {}
				System.out.println( idx++ );
			}*/
			e.printStackTrace();
		} finally {
			if( jedisCluster != null ) try { jedisCluster.close(); } catch (Exception e) {}
		}
    }


    
    
    public static void clientSetInstanceTestDfltSerialization()
    {
        //Redis redis = null;
    	
    	Jedis jedisInst = null;
    	try {
	    	//redis = new Redis();
	    	//redis.createPool(); 
	    	jedisInst = Redis.getInstance(); // redis.getInstance();
	    	
	    	//JedisPoolConfig redisConf = new JedisPoolConfig();
	    	SampleVO voObj = new SampleVO();
	    	voObj.setName("myname");
	    	voObj.setValue("myvalue");
	    	long startTime = System.currentTimeMillis();
	    	
    		//jedisInst.set( "RedisConfigObj".getBytes(), serialize( se ) );
	    	
	    	KryoSerializer<SampleVO> marshaller = new KryoSerializer<SampleVO>( SampleVO.class );
	    	
	    	jedisInst.set( ("RedisConfigObj") .getBytes(), marshaller.serialize( voObj ) );  		
	    	long endTime = System.currentTimeMillis();
	    	
	    	System.out.println("Elaped Time for Simple Set (ms) : " + (endTime-startTime));
    	    System.out.println("Done");
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }
    
    public static void clientGetInstanceTestDfltSerialization()
    {
       // Redis redis = null;
    	
    	Jedis jedisInst = null;
    	try {
	    	//redis = new Redis();
	    	//redis.createPool(); 
    		
	    	jedisInst = Redis.getInstance(); // redis.getInstance();
	    	
	    	//JedisPoolConfig redisConf = new JedisPoolConfig();
	    	//RedisClientConfig rcc = new RedisClientConfig();
	    	//RedisClientConfig rcc = RedisClientConfig.getInstance();
	    	RedisClientConfig rcc = RedisClientConfig.getInstance();
	    	rcc.loadConfig();
	    	long startTime = System.currentTimeMillis();
	    	
    		byte[] voEntity = jedisInst.get( "RedisConfigObj".getBytes() );
    		//ServerEntity serverEntity = (ServerEntity)unserialize( redisConfigObj );
    		
    		KryoSerializer<Object> marshaller = new KryoSerializer<Object>( SampleVO.class );

    		SampleVO sampleVO = (SampleVO)marshaller.deserialize( voEntity );
	    	long endTime = System.currentTimeMillis();
	    	System.out.println("My Name : " + sampleVO.getName() );
	    	System.out.println("My Value : " + sampleVO.getValue() );
	    	
	    	
	    	System.out.println("Elaped Time for Simple Get (ms) : " + (endTime-startTime));
    	    System.out.println("Done");
    	} catch (Exception e) {
    		System.out.println(e);
    	} finally {
    		jedisInst.close();
    	}    	
    }
    
    
/*    // TO DO : Enhance for performance using kryo ~~!!    
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // Serialization
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // Deserialization
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }*/
    
    
    
    public static void pingServers()
    {

    }
}
