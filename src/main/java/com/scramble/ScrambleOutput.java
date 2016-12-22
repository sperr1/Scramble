package scramble;

import java.util.UUID;

/**
 * POJO for the output.
 * @author AWS_admin
 *
 */
public class ScrambleOutput {

	private final UUID taskID;
	private final String b64Ciphertext;
	private final String b64Key;
	
	public ScrambleOutput(UUID id, String c, String k){
		this.taskID = id;
		this.b64Ciphertext = c;
		this.b64Key = k;
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
