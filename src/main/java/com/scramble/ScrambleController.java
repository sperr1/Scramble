package scramble;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class ScrambleController {

   @PostMapping("/scrambler")
   public ScrambleInput scramblerInput(@RequestParam(value="plaintext") String plaintext){
	   return new ScrambleInput(plaintext);
   };
}
