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
	@RequestMapping(value="/")
	public ResponseEntity<ScrambleObj> inputPlaintext(@RequestParam(value="plaintext") String plaintext){
		ScrambleObj sObj = new ScrambleObj();
		sObj.setText(plaintext);
		return new ResponseEntity<ScrambleObj>(sObj, HttpStatus.OK);
	};

	/*
	 * Step two is, somehow, to take the JSON from step one, marshal it into
	 * a ScrambleObj, do stuff to it, and then return that object so that
	 * Spring can cough up a JSON on the server end.
	 */
	@RequestMapping(value="/", method = RequestMethod.POST)
	public ResponseEntity<ScrambleObj> scramblerize(@RequestBody ScrambleObj sObj){
		if(sObj!=null){
			/*ScrambleInput sInput = new ScrambleInput(sObj.getText());
			String ciphering = Scramble.doAES(sInput.getText(), 0);
			ScrambleOutput sOutput = new ScrambleOutput(sInput.getID(),ciphering, Scramble.getKey());
			sObj.setText(sOutput.getCiphertext());
			sObj.setIdent(sOutput.getID());
			sObj.setKey(sOutput.getKey());*/
			sObj.setText(sObj.getText().concat(", scramblerizing.")); //at this point I'm just experimenting to figure out if anything's changing on the object.
		}
		return new ResponseEntity<ScrambleObj>(sObj, HttpStatus.OK);
	}
}
