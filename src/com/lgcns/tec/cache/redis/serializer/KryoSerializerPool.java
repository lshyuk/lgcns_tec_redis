package com.lgcns.tec.cache.redis.serializer;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.lang3.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class KryoSerializerPool {

   private static KryoPool pool = null;
    private Kryo kryo = null;
    
    private KryoFactory factory = new KryoFactory() {
    	  public Kryo create () {
    	    Kryo kryo = new Kryo();
    	    System.out.println("kryo object created...");
    	    return kryo;
    	  }
    };
    	
    private void getKryoPool()
    {
    	if ( pool == null ) {
    		pool = new KryoPool.Builder(factory).softReferences().build();
    		
    	    System.out.println("hash code for kryo pool : " + pool.hashCode() );
    	}
    }
    
    public KryoSerializerPool(Class<?> clazz) {
       
    	getKryoPool();
    	kryo = pool.borrow();
    	System.out.println("hash code for kryo register : " + kryo.hashCode() );
        kryo.register(clazz);
        pool.release(kryo);
    }
    
    public KryoSerializerPool(List<Class<?>> classes) {
    	getKryoPool();
    	System.out.println("hash code for kryo register list : " + kryo.hashCode() );
        
        for (Class<?> clazz : classes) {
            kryo.register(clazz);
        }
        pool.release(kryo);
    }
    
    public byte[] serialize(Object t) throws SerializationException {
    	getKryoPool();
        ByteArrayOutputStream objStream = new ByteArrayOutputStream();
        Output objOutput = new Output(objStream);
        System.out.println("hash code for kryo serialize : " + kryo.hashCode() );
        kryo.writeClassAndObject(objOutput, t);
        objOutput.close();
        pool.release(kryo);
        return objStream.toByteArray();
    }
    
    public Object deserialize(byte[] bytes) throws SerializationException {
    	System.out.println("hash code for kryo deserialize : " + kryo.hashCode() );
    	Object readClassAndObject = (Object) kryo.readClassAndObject(new Input(bytes));
		
        pool.release(kryo);
		return readClassAndObject;
    }
}
