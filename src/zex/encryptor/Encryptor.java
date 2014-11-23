package zex.encryptor;

import java.io.File;

import zex.command.ReturnValue;
import zex.explorer.FileStream;

public class Encryptor {
	private static byte[] data = null;
	private static byte[] crypted = null;
	
	public static ReturnValue crypt(String password, File destination, boolean encrypt){
		data = FileStream.data;
		FileStream.data = null;
		int length = password.length();
		int hashcode = (password.hashCode() & 0x7fffffff) % data.length;
		int hash_power = 0;
		int mean = 0;
		int scatter = 0;
		
		//Since hashcode is too large of a value to make a long number composed of hashcode number of bytes
		//Will use hashcode as a maximum value for the binary strings that will encoded.
		for(int i = 0; hashcode > 0; i++){	
			hash_power++;
		}
		
		//Calculating sample mean of password
		for(int i = 0; i < length; i++){
			mean += password.charAt(i);
		}
		mean /= length;
		
		//Calculating sample scatter of password
		for(int i = 0; i < password.length(); i++){
			scatter += pow((password.charAt(i) - mean), 2);
		}
		scatter /= length - 1;
		
		System.err.println("Password: " + password);
		System.err.println("Hashcode max value" + pow(2, hash_power));
		System.err.println("Length: " + length);
		System.err.println("Mean: " + mean);
		System.err.println("Scatter: " + scatter);
		
		if(encrypt){
			crypted = new byte[data.length];		
		}else{
			crypted = data;
			data = new byte[crypted.length];
		}
		return new ReturnValue("Test", false);
	}
	/*
	 * Level 1:
	 * Encrypt at the Length Level: Encrypt n binary strings of length i bytes, where n is the number of times i 
	 * fits fully in the data.length. The last bytes will be treated as a single x-byte string to be encrypted (n mod i = x).
	 * Start at the beginning or the end based on the sample mean of the characters of the password.
	 * Level 2:
	 * Encrypt at the sample scatter level
	 * Level 3:
	 * Encrypt at the byte level.
	 * Level 4:
	 * Embed the password in the file. During decrypt, if input password and decrypted password do not match, abort.
	 * Level 5:
	 * Encrypt at the sample mean level.
	 * Level 6:
	 * Encrypt at the Hashcode Level. Use the hashcode of the password to determine the length of the bytes to be decoded.
	 * Start at the beginning or the end based on the sample scatter of the characters of the password.
	 */
	//ASCII for password: 32 - 126 = 95 characters
	//....... /[\x20-\x7E]{8,}/
	
	/**
	 * Reverse the content of the file
	 * @return
	 */
	private static void encryptLevelOne(boolean encrypt){
		if(encrypt){
			for(int i = 0; i < data.length ; i++){
				crypted[i] = data[data.length - (i + 1)];
			}
		}else{
			
		}
	}
	
	/**
	 * Applies the encryption to the byte level
	 * @return
	 */
	private static String encryptLevelTwo(int length, boolean encrypt){
		if(encrypt){
			//if()
		}else{
			
		}
		return null;
	}
	
	/**
	 * Applies the encryption to the doublebyte level
	 * @return
	 */
	private static String encryptLevelThree(){
		return null;
	}
	
	private static String encryptLevelFour(){
		return null;
	}
	
	private static String encryptLevelFive(){
		return null;
	}
	
	private static String encryptLevelSix(){
		return null;
	}
	
	public static byte[] getResult(boolean encrypt){
		if(encrypt){
			byte[] array = Encryptor.crypted;
			crypted = null;
			data = null;
			return array;
		}else{
			byte[] array = Encryptor.data;
			crypted = null;
			data = null;
			return array;
		}
	}

	private static int encrypt(int value, int max){
		int token = max;
		int n = 0;
		for(; token > 0; n++){
			token /= 2;
		}
		if((max & (max - 1)) == 0 && n > 0){
			n--;
		}
		return encrypt(0, pow(2, n)-1, value, n);
		
	}
	
	/*private static String encrypt(String binary_str){
		long max = pow(value.length(), 2);
		long value = 0;
		for(int i = 0 ; i < binary_str.length() ; i++){
			
		}
	}*/
	
	private static int encrypt(int low, int high, int val, int n){
		if(n == 0){
			return val;
		}else{
			int middown = (high + low)/2, midup = middown + 1;
			int newval = 0, newlow = 0, newhigh = 0;
			if(val >= midup){
				newval = middown - (val- midup);
				newhigh = middown;
				newlow = low;
			}else if(val <= middown){
				newval = midup - (val - middown);
				newhigh = high;
				newlow = midup;
			}
			return encrypt(newlow, newhigh, newval, n-1);
		}
	}
	
	static int pow(int base, int n){
		int value = 1;
		for(int i = 0 ; i < n ; i++){
			value *= base;
		}
		return value;
	}
	
}
