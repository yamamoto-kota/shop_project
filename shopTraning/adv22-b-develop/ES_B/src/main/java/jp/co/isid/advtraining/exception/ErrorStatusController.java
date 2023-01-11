package jp.co.isid.advtraining.exception;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorStatusController {
	@Autowired
	HttpSession session;

	//システムエラー発生時（SystemExceptionHandlerからの呼び出し）、エラー画面に遷移する
	@RequestMapping(value = "systemError", method = RequestMethod.GET)
	public String systemError() {

		//sessionidの破棄
		session.invalidate();

		return "V900ErrorView";
	}

	@RequestMapping(value = "/400", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String badRequest(Model model) {
		System.err.println("400エラー発生");
		session.invalidate();
		model.addAttribute("mainReason","ErrorCode:400");
		model.addAttribute("subReason",
				"Bad Request");
		return "V900ErrorCode";
	}


	@RequestMapping(value = "/403", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String forbidden(Model model) {
		System.err.println("403エラー発生");
		session.invalidate();
		model.addAttribute("mainReason","ErrorCode:403");
		model.addAttribute("subReason","不正アクセスがありました");
		return "V900ErrorCode";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound(Model model) {
		System.err.println("404エラー発生");
		session.invalidate();
		model.addAttribute("mainReason","ErrorCode:404");
		model.addAttribute("subReason","入力されたURL情報に誤りがあります。");
		return "V900ErrorCode";
	}

	@RequestMapping(value = "/405", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String methodNotAllowed(Model model) {
		//PostのURLをブックマークして、後ほど直接URL入力のGET送信時
		//@ExceptionHandler({HttpRequestMethodNotSupportedException.class})記述優先
		System.err.println("405エラー発生");
		session.invalidate();
		model.addAttribute("mainReason","ErrorCode:405");
		model.addAttribute("subReason","正しい画面遷移ではないようです。");
		return "V900ErrorCode";
	}

	@RequestMapping(value = "/500", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String serverError(Model model) {
		System.err.println("500エラー発生");
		session.invalidate();
		model.addAttribute("mainReason","ErrorCode:500");
		model.addAttribute("subReason","プログラムエラーが発生しました。");
		return "V900ErrorCode";
	}
}
