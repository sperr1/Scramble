package com.scramble;

import java.util.UUID;

/**
 * POJO for the input. We're attempting to marshal into this from
 * a JSON containing a base64-encoded String.
 * 
 * However, since we're going to need it decoded out of b64 in the leadup
 * to encryption, ScrambleInput runs codec on whatever text it's constructed
 * with.
 * @author AWS_admin
 *
 */
public class ScrambleInput {

	private final String b64Plaintext;
	private final UUID taskID;
	
	public ScrambleInput(String plain){
		this.b64Plaintext = Scramble.codec(plain, 1);
		this.taskID = UUID.randomUUID();
	}
	
	public String getText(){
		return this.b64Plaintext;
	}
	
	public UUID getID(){
		return this.taskID;
	}
}
