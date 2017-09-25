package com.lgcns.tec.cache.redis.serializer;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.lang3.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

// kroyo pool 이용하는 방법 추가 확인 필요!
public class KryoSerializer<T> {

    private Kryo kryo = null;
    
    public KryoSerializer(Class<?> clazz) {
    	kryo = new Kryo();
        kryo.register(clazz);
    }
    
    public KryoSerializer(List<Class<?>> classes) {
    	//System.out.println("hash code for kryo register list : " + kryo.hashCode() );
        kryo = new Kryo();
        for (Class<?> clazz : classes) {
            kryo.register(clazz);
        }
    }
    
    public byte[] serialize(Object t) throws SerializationException {
        ByteArrayOutputStream objStream = new ByteArrayOutputStream();
        Output objOutput = new Output(objStream);
        kryo.writeClassAndObject(objOutput, t);
        objOutput.close();
        return objStream.toByteArray();
    }
    
    public T deserialize(byte[] bytes) throws SerializationException {
    	T readClassAndObject = (T) kryo.readClassAndObject(new Input(bytes));
		return readClassAndObject;
    }
}
