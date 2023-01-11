package jp.co.isid.advtraining.exception;

import org.seasar.doma.DomaException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class SystemErrorExceptionHandler {

//	/**
//		* システム例外(SystemErroeException.DataAccessException)をハンドリングする。
//		*/
//	@ExceptionHandler({ Exception.class })
//	public String handleError(Model model) {
//		return "redirect:/errorPage";
//	}
//

	@ExceptionHandler({DomaException.class})
	public String handleErrorException(DomaException e,RedirectAttributes redirectAttributes) {

		System.err.println("DB系エラー クラス名"+e.getClass().getName());
		System.err.println("エラーメッセージ"+e.getMessage());

		redirectAttributes.addFlashAttribute("mainReason",
				"サーバー接続エラー");
		redirectAttributes.addFlashAttribute("subReason",
				"何らかの理由でデーターベースに接続できませんでした。");
		return "redirect:/errorPage";
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public String handleErrorException2(HttpRequestMethodNotSupportedException e,RedirectAttributes redirectAttributes) {
		//Customizerに記述している/405
		System.err.println("csrfや405はここ　クラス名"+e.getClass().getName());
		System.err.println("エラーメッセージ"+e.getMessage());

		redirectAttributes.addFlashAttribute("mainReason",
				"不明な遷移");
		redirectAttributes.addFlashAttribute("subReason",
				"正しい遷移が行われませんでした。");

		return "redirect:/errorPage";
	}

	@ExceptionHandler({Exception.class})
	public String handleErrorException3(Exception e,RedirectAttributes redirectAttributes) {

		System.err.println("上記以外の例外クラス名"+e.getClass().getName());
		System.err.println("エラーメッセージ"+e.getMessage());

		redirectAttributes.addFlashAttribute("mainReason",
				"何らかのエラー");
		redirectAttributes.addFlashAttribute("subReason",
				e.getMessage());

		return "redirect:/errorPage";
	}
}
