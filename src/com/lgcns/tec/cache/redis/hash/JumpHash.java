package com.lgcns.tec.cache.redis.hash;

import static com.google.common.primitives.UnsignedLong.valueOf;
import com.google.common.primitives.UnsignedLong;
import redis.clients.util.Hashing;


public class JumpHash implements Hashing {
	static UnsignedLong constant = valueOf(2862933555777941757L);
	//static long constant2 = 1L<<31;
	static int bucketSize;

/*	public static long consistent(int buckets, Object ob) {
		return consistent(buckets, ob.hashCode());
	}

	public static long consistent(int buckets, String ob) {
		return consistent(buckets, ob.hashCode());
	}
	
	public static long consistent(int buckets, byte[] ba) {
		return consistent(buckets, ba.hashCode());
	}
	
	*//** Jump Consistent Hashing algorithm
	 *
	 * @param buckets number of buckets
	 * @param keyHash hash of a key
	 * @return selected bucket
	 *//*
	public static long consistent(int buckets, long keyHash) {

		UnsignedLong key = fromLongBits(keyHash);

		long b = -1, j = 0;
		while( j < buckets ) {
			b = j;

			key = key.times(constant).plus( ONE );
			UnsignedLong keyShift = fromLongBits( key.longValue() >>> 33 ).plus( ONE );

			j = (long)( (b+1) * ( constant2 / keyShift.doubleValue() ) );
		}

		return (int) b;
	}*/

	public JumpHash( int bucketSize )
	{
		JumpHash.bucketSize = bucketSize;
	}
	
	public static long jumpConsistentHash(final int buckets, final Object o) { 
	  	return jumpConsistentHash( buckets, o.hashCode() ); 
	} 
	private static final long JUMP = 1L << 31;
	private static final long CONSTANT = Long.parseUnsignedLong("2862933555777941757"); 

	
	  public static int jumpConsistentHash(final int buckets, final long key) { 
		  	//checkBuckets(buckets); 
		   	long k = key; 
		    long b = -1; 
		  	long j = 0; 
		   
		  	while (j < buckets) { 
		    b = j; 
		    k = k * CONSTANT + 1L; 
		    j = (long) ((b + 1L) * (JUMP / toDouble((k >>> 33) + 1L))); 
		    } 
		    	return (int) b; 
     } 
	  private static final long UNSIGNED_MASK = 0x7fffffffffffffffL;
	  private static double toDouble(final long n) { 
	  	double d = n & UNSIGNED_MASK; 
	  	if (n < 0) { 
	  	    d += 0x1.0p63; 
		} 
	  	return d; 
     } 

	
	@Override
	public long hash(String key) {
		long bucket1 = JumpHash.jumpConsistentHash( bucketSize, key );
		return bucket1;
	}

	@Override
	public long hash(byte[] key) {

		//System.out.println( "hash for bytearray [" + key + "]");
		long bucket1 = JumpHash.jumpConsistentHash( bucketSize, key );
		
		return bucket1;
	}
}
