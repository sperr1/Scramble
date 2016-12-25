package com.scramble;

import java.util.UUID;

/**
 * POJO for the input. We're attempting to marshal into this from
 * a JSON containing a base64-encoded String.
 * 
 * @author AWS_admin
 *
 */
public class ScrambleInput {

	private String b64Plaintext;
	private UUID taskID;
	
	public ScrambleInput(String plain){
		//System.out.println("input string: "+plain);
		this.b64Plaintext = plain;
		//System.out.println("b64 plaintext: "+this.b64Plaintext);
		this.taskID = UUID.randomUUID();
	}
	
	public String getText(){
		return this.b64Plaintext;
	}
	
	public UUID getID(){
		return this.taskID;
	}
}
