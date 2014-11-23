package zex.encryptor;

import java.util.BitSet;

public class ByteConverter{
	public Integer toPositiveInt(BitSet set) throws IllegalArgumentException{
		if(set.size() > 31) throw new IllegalArgumentException("BitSet greater than Integer.MAX_VALUE");
		int answer = 0;
		for(int i = 0 ; i < set.size() ; i++){
			if(set.get(i))
				answer += Encryptor.pow(2, i);
		}
		return new Integer(answer);
	}
	public Long toPositiveLong(BitSet set){
		if(set.size() > 63) throw new IllegalArgumentException("BitSet greater than Long.MAX_VALUE");
		long answer = 0;
		for(int i = 0 ; i < set.size() ; i++){
			if(set.get(i))
				answer += Encryptor.pow(2, i);
		}
		return new Long(answer);
	}
}
