package com.scramble;


import java.security.MessageDigest;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.*;

/**
 * Static class for handling encoding/decoding strings to and from base64 as well as
 * encrypting/decrypting data using AES.
 * @author AWS_admin
 *
 */
public class Scramble {


	private static Cipher c;
	private static SecretKeySpec sks;
	private static MessageDigest md = null;
	private static byte[] sha;
	private static final String KEY = "DEADBEEF";

	private static int hasInit = 0; //rudimentary attempt at singleton-style initialization, because I just want it to init once and never again
	
	/*
	 * In case you ever want to know what the key is. Protip: it's DEADBEEF. 
	 * Make no mistake, this is supposed to represent the hex value 0xDEADBEEF. It's kept as the plain
	 * string sans '0x' because a function down in init parses it knowing it's a hex value and gets all
	 * uppity at the '0x' part because there is no 'x' in hexadecimal.
	 * 
	 * 
	 * Anyway it's good form to have getters and setters where necessary. KEY gets a getter and nothing else
	 * because it's a final variable.
	 */
	public static String getKey(){
		return Scramble.KEY;
	}

	/*
	 * One-stop function for initializing the class's variables. Both codec and doAES invoke this function
	 * at their starts, and this function should only really do anything once in the entire lifespan of the program.
	 * hasInit is essentially a flag used to determine whether initialization has been performed.
	 * 
	 * Really, the point of init() being called at the start of any of the public access static functions is
	 * to ensure that no matter what, the class's parameters are set up for when AES encryption wizardry
	 * commences. codec() doesn't actually need it per se, but I decided that the act of calling one of
	 * Scramble's functions should come with it the consequence of making sure that Scramble is ready for
	 * anything. Simply put, all static functions should initialize the class parameters, even if those
	 * parameters aren't expected to be used by that particular function.
	 * 
	 * DEVNOTE: init() exists largely because the requirements of AES make it such that the key provided,
	 * 0xDEADBEEF, does not actually work. The solution to this is a bit of behind the scenes magic wherein
	 * we take our provided key, and apply SHA-1 to it to get a hash value. Since SHA-1 was designed as a
	 * cryptographic hash function, it is very good about creating values that change dramatically with even
	 * the smallest change of the input, and for our circumstances only really introduces the possibility of
	 * SHA-1 being a weak link to the security of the message; since we're not using DEADBEEF, but rather the
	 * first 16 bits of the SHA-1 of DEADBEEF, all an attacker needs is a value that yields a collision with 
	 * DEADBEEF's SHA. Simply put, using SHA-1 to make a fitting key kinda has the consequence of generating a
	 * bunch of "phantom keys" that would be equally valid in our encryption schema. If I were to rewrite the
	 * code such that the key could be defined by the user rather than be some internally defined arbitrary 
	 * hardcoded value, then theoretically you could have a Scramble init to encrypt with DEADBEEF, then have
	 * another initialized with a different key that nonetheless successfully decodes the DEADBEEF-encrypted
	 * message.
	 * 
	 * But that's just my rambling. Key stretching and other applications of Key Derivation Functions are
	 * largely the concern of cybersecurity experts to work on rather than the entities that rely on 
	 * cybersecurity technologies.
	 * 
	 * tl/dr: I stretched the 0xDEADBEEF key using SHA-1, my cybersec hobbyist's gut feeling tells me that
	 * this is a DANGEROUS IDEA in a real world situation.
	 */
	private static void init(){
		try{
			if(Scramble.hasInit==0){
				c = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				Scramble.sha= DatatypeConverter.parseHexBinary(Scramble.KEY);
				Scramble.md = MessageDigest.getInstance("SHA-1");
				sha = Scramble.md.digest(sha);
				sha = Arrays.copyOf(sha, 16);
				Scramble.sks = new SecretKeySpec(sha, "AES");
				Scramble.hasInit = 1;
			}
		}catch (Exception e){
			System.out.println("Error: "+e.getMessage());
		}
	}

	/*
	 * Encodes/Decodes a string to/from base64 encoding from/to UTF-8.
	 * PARAMS:
	 * 	(STRING) input - string to encode/decode. Does not check to see if input is or is not already in b64.
	 * 	(INT) mode -  0 to encode (enCODer), 1 to decode (DECoder).
	 * RETURNS:
	 * 	(STRING) a base64-encoded version of the input string if set to encode.
	 * 	(STRING) a UTF8-encoded version of the input string if set to decode.
	 */
	public static String codec(String input, int mode){
		Scramble.init();
		try{
			String out = input;
			if(mode==0)
			{
				Encoder e = Base64.getEncoder();
				String out64 = e.encodeToString(out.getBytes());
				return out64;
			}
			else if(mode==1){
				Decoder d = Base64.getDecoder();
				byte[] b = d.decode(out);
				String outUTF = new String(b, "UTF-8");
				return outUTF;
			}else{
				throw new Exception("Invalid mode ("+mode+").");
			}
		}catch (Exception e)
		{
			System.out.println("Error: "+e.toString());
		}
		return null;
	}


	/*
	 * Encrypts/Decrypts a string using AES encryption.
	 * 
	 * The implementation of doAES() has some jank going on; in particular, instead of reusing the code written
	 * to handle base-64 conversions, doAES() handles encoding and decoding within itself, making codec() redundant.
	 * In addition, doAES() is designed around a known factor, the usage of 0xDEADBEEF as an encryption key.
	 * 
	 * 
	 * PARAMS:
	 * 	(STRING) input - a string to encrypt/decrypt.
	 * 	(INT) mode - 0 to encrypt, 1 to decrypt.
	 * RETURNS:
	 * 	(STRING) - ciphertext if set to encrypt.
	 * 	(STRING) - plaintext if set to decrypt.
	 */
	public static String doAES(String input, int mode){
		Scramble.init();
		try{
			if(mode == 0){
				c.init(Cipher.ENCRYPT_MODE, Scramble.sks, c.getParameters());
				//System.out.println("ENCRYPTING: "+input);
				byte[] encode = c.doFinal(input.getBytes("UTF-8"));
				String encryptedText = Base64.getEncoder().encodeToString(encode);
				return encryptedText;
			}else if(mode == 1){
				c.init(Cipher.DECRYPT_MODE, Scramble.sks, c.getParameters());
				//System.out.println("DECRYPTING");
				byte[] decode = Base64.getDecoder().decode(input);
				String decryptedText = new String(c.doFinal(decode));
				return decryptedText;
			}else{
				throw new Exception("Invalid mode ("+mode+").");
			}
		}catch (Exception e){
			{
				System.out.println("Error: "+e.toString());
			}
			return null;
		}
	}
	/*
	public static void main (String[] args){
		try{
			String e = Scramble.doAES("plaintextstringtoencrypttoAES", 0);
			System.out.println(e);
			String d = Scramble.doAES(e, 1);
			System.out.println(d);
			
			String b = Scramble.codec("plaintextstringtoencrypttoAES", 0);
			System.out.println(b);
			String u = Scramble.codec(b, 1);
			System.out.println(u);
			String u2 = Scramble.codec(u, 1);
		}catch(Exception ex){
			System.out.println("ERROR: "+ex.toString());
		}
	}*/
}