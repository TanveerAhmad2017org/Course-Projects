package mitm;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class EncryptPassword {
	
	private static Random rd = new SecureRandom();
	
	
	private static Error printUsage()
	{
		System.err.println("Usage: java EncryptPassword <plainFile> <EncryptFile> <Key>");
		System.exit(1);
		return null;
	}
	
	public static void main(String[] args)
	{
		encryptPwdFile();		
	}
	
	public static void encryptPwdFile()
	{
		try
		{
			//input and output file 
			String plainFile = Utility.plainPwdFile; //1st argument is input file
			String encryptFile = Utility.encryptePwdFile; //2nd argument is output file
			//String key = args[2]; //3rd argument is key(used to encrypt the input file)
			//FileReader fr = new FileReader(plainFile);
			
			
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			BufferedReader br = new BufferedReader(new FileReader(plainFile));
			
			
			//format of plainFile: each line contain one <username, key> pair
			while(true)
			{
				String line = br.readLine();
				if(line == null) break;
				String[] arr = line.split(",");
				if(arr.length!=2)
					throw new Exception("format of plainFile: each line contain one <username, key> pair!");
				String username = arr[0];
				String password = arr[1];
				outStream.write(SaltHashPwd(username,password));
			}
			br.close();
			
			byte[] entryBytes = outStream.toByteArray();
			//System.out.println("Before encrypt: "+ Utility.ByteToString(entryBytes));
			Aes aes = new Aes(Utility.pwdEncryptKey);
			byte[] encBytes = aes.encrypt(entryBytes);
			//System.out.println("After encrypt: "+ Utility.ByteToString(encBytes));
		
			
			FileOutputStream fos = new FileOutputStream(encryptFile);
			fos.write(encBytes);
			fos.close();
		
		}catch(Exception e)
		{
			System.err.println(e.toString());
		}
	}
	
	public static byte[] SaltHashPwd(String username, String password) throws Exception
	{
		//length definition: username <= 100bytes; salt = 16 bytes;  passwordHash = 30 bytes
		//byte[] entryBytes = new byte[100+16+30];
		byte[] user;
		byte[] salt = new byte[16];
		
		byte[] passwordHash;
		byte[] fixedLengthPasswordHash = new byte[30];
		
		//assign value to all the parameters
		ByteBuffer userBytes = ByteBuffer.allocate(100);
		userBytes.put(username.getBytes());
		user = userBytes.array();
		rd.nextBytes(salt);
	
		
		
		Aes aes = new Aes(Utility.pwdEncryptKey);
		byte[] saltedPwd = Utility.concat2byte(salt, password.getBytes());		
		passwordHash = aes.hmac.getmac(saltedPwd);
		fixedLengthPasswordHash = Arrays.copyOf(passwordHash, fixedLengthPasswordHash.length);
		
//		System.out.println("truncate hashpassword= " +fixedLengthPasswordHash+ ", length= "+ fixedLengthPasswordHash.length);
		
		byte[] entryByte = Utility.concat3byte(user, salt, fixedLengthPasswordHash);
			
	
//		System.out.println("Before encrypt length: "+entryByte.length);
		//System.out.println("Before encrypt: "+ Utility.ByteToString(entryByte));
		return entryByte;
		//return aes.encrypt(entryByte);
		
	}
	
	
	
	
	
}
