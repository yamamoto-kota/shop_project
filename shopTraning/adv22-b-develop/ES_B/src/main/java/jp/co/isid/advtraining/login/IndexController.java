package jp.co.isid.advtraining.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
	@Autowired
	HttpSession session;

	 @RequestMapping(value = "", method = RequestMethod.GET)
	    public String index() {
	        return "redirect:/login";
	    }

//	 @RequestMapping(value = "errorPage", method = RequestMethod.GET)
//	    public String errorPage() {
//		 SecurityContextHolder.clearContext();
//	        return "error/V900ErrorView";
//	    }

		//システムエラー発生時（SystemExceptionHandlerからの呼び出し）、エラー画面に遷移する
		@RequestMapping(value = "errorPage", method = RequestMethod.GET)
		public String systemError() {

			//sessionidの破棄
			session.invalidate();

			return "error/V900ErrorView";
		}
}
