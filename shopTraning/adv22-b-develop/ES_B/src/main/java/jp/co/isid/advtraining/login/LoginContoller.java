package jp.co.isid.advtraining.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginContoller {

	 @RequestMapping(value = "", method = RequestMethod.GET)
	    public String index() {
	        return "login/V101_01LoginView";
	    }

}
