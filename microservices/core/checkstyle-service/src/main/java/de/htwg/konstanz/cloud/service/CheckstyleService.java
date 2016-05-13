package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class CheckstyleService {

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate(@RequestBody String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            String repositoryUrl = jsonObj.getString("repositoryUrl");
            CheckGitRep oCheckGitRep = new CheckGitRep();
            String json = oCheckGitRep.startIt();
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }

    @RequestMapping("/info")
    public String info() {
        return "Checkstyle-Service)";
    }

}
