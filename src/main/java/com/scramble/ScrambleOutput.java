package com.scramble;

import java.util.UUID;

/**
 * POJO for the output.
 * 
 * @author AWS_admin
 *
 */
public class ScrambleOutput {

	private UUID taskID;
	private String b64Ciphertext;
	private String b64Key;
	
	public ScrambleOutput(UUID id, String cipher, String key){
		this.taskID = id;
		this.b64Ciphertext = cipher;
		this.b64Key = key;
	}
	
	public UUID getID(){
		return this.taskID;
	}
	
	public String getCiphertext(){
		return this.b64Ciphertext;
	}
	
	public String getKey(){
		return this.b64Key;
	}
	
}
