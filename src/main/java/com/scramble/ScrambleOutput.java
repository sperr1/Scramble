package com.scramble;

import java.util.UUID;

/**
 * POJO for the output.
 * 
 * Much like how ScrambleInput decodes its string on construction, ScrambleOutput
 * encodes its strings as per project requirements during its construction.
 * @author AWS_admin
 *
 */
public class ScrambleOutput {

	private final UUID taskID;
	private final String b64Ciphertext;
	private final String b64Key;
	
	public ScrambleOutput(UUID id, String cipher, String key){
		this.taskID = id;
		this.b64Ciphertext = Scramble.codec(cipher, 0);
		this.b64Key = Scramble.codec(key, 0);
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
