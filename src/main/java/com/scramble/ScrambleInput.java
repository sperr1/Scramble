package scramble;

import java.util.UUID;

/**
 * POJO for the input. We're attempting to marshal into this from
 * a JSON containing a base64-encoded String.
 * @author AWS_admin
 *
 */
public class ScrambleInput {

	private final String b64Plaintext;
	private final UUID taskID;
	
	public ScrambleInput(String s){
		this.b64Plaintext = s;
		this.taskID = UUID.randomUUID();
	}
	
	public String getText(){
		return this.b64Plaintext;
	}
	
	public UUID getID(){
		return this.taskID;
	}
}
