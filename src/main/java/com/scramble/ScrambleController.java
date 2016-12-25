package com.scramble;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the project. We're going from a POSTed string encoded to base64
 * at the start, and ultimately finishing off with a JSON of the encrypted
 * material, accessory data (key and UUID) included.
 * @author AWS_admin
 *
 */
@RestController
public class ScrambleController {

	/*
	 * Step one is to POST a string. Spring supposedly does the JSON stuff
	 * behind the scenes, so the result of this should be a JSON
	 */
	/*@RequestMapping(value="/")
	public ResponseEntity<ScrambleObj> inputPlaintext(@RequestParam(value="plaintext") String plaintext){
		ScrambleObj sObj = new ScrambleObj();
		sObj.setText(plaintext);
		return new ResponseEntity<ScrambleObj>(sObj, HttpStatus.OK);
	};*/

	/*
	 * Step two is, somehow, to take the JSON from step one, marshal it into
	 * a ScrambleObj, do stuff to it, and then return that object so that
	 * Spring can cough up a JSON on the server end.
	 */
	/*@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<ScrambleObj> scramblerize(@RequestBody String plaintext){
		ScrambleObj sObj = new ScrambleObj();
		sObj.setText(plaintext);
		if(sObj!=null){
			ScrambleInput sInput = new ScrambleInput(sObj.getText());
			String ciphering = Scramble.doAES(sInput.getText(), 0);
			ScrambleOutput sOutput = new ScrambleOutput(sInput.getID(),ciphering, Scramble.getKey());
			sObj.setText(sOutput.getCiphertext());
			sObj.setIdent(sOutput.getID());
			sObj.setKey(sOutput.getKey());
			//sObj.setText(sObj.getText() + ", scramblerizing."); //at this point I'm just experimenting to figure out if anything's changing on the object.
		}
		return new ResponseEntity<ScrambleObj>(sObj, HttpStatus.OK);
	}*/
	
	/*
	 * NEW PLAN: IGNORE ALL OF THE ABOVE, I'M AN IDIOT
	 * You send an HTTP POST with the String, as long as the header of the POST mentions
	 * it's a JSON, then Spring knows what to do with it.
	 * 
	 * THEN YOU MARSHAL IT INTO THE ACTUAL CLASSES.
	 * 
	 * AND THEN EVERYTHING WORKS AS IT SHOULD.
	 * 
	 */
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<ScrambleOutput> scramblerize(@RequestBody String plaintext){
		try{
			if(plaintext==null){ throw new Exception("Null String.");}
			ScrambleInput sInput= new ScrambleInput(plaintext);
			String ciphering = Scramble.doAES(sInput.getText(),0);
			ScrambleOutput sOutput = new ScrambleOutput(sInput.getID(),ciphering,Scramble.getKey());
			return new ResponseEntity<ScrambleOutput>(sOutput, HttpStatus.OK);
		}catch (Exception e){
			System.out.println("Error: "+e.toString());
			return null;
		}
		
	}
}
