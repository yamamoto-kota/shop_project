package jp.co.isid.advtraining.list;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.login.LoginUser;

@Controller
@RequestMapping("/list")
public class EnqueteListController {

	//EnqueteListServiceの変数
	@Autowired
	EnqueteListService enqueteListService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		/*1.未回答、回答済み、実施前、実施中、実施完了の5つのパターンのアンケートを以下の手順で取得する*/
		/*1.1.DBのアンケートテーブルから指定の「loginUser.getEsqId()（ESQ-ID）」に対するアンケート情報をリストで取得しEnqueteListVMに格納する ※*/
		List<Enquete> noAnswerListVM = enqueteListService.getNoAnswerList(loginUser.getEsqId());
		List<Enquete> completeAnswerListVM = enqueteListService.getCompleteAnswerList(loginUser.getEsqId());
		List<Enquete> beforeEnqueteListVM = enqueteListService.getBeforeEnqueteList(loginUser.getEsqId());
		List<Enquete> startEnqueteListVM = enqueteListService.getStartEnqueteList(loginUser.getEsqId());
		List<Enquete> finishEnqueteListVM = enqueteListService.getFinishEnqueteList(loginUser.getEsqId());

		/*1.2.引数のmodelに1.1で取得したアンケート情報を格納する*/
		model.addAttribute("NOANSWER", noAnswerListVM);
		model.addAttribute("COMPLETEANSWER", completeAnswerListVM);
		model.addAttribute("BEFOREENQUETE", beforeEnqueteListVM);
		model.addAttribute("STARTENQUETE", startEnqueteListVM);
		model.addAttribute("FINISHENQUETE", finishEnqueteListVM);

		//ヘッダーでログインユーザーを表示しているため、modelに格納する。
		model.addAttribute("LOGINUSER", loginUser);

		//2.return値を返す
		return "V200_01EnqueteListView";
	}

	@RequestMapping(value = "/deleteEnquete", method = RequestMethod.GET)
	public String deleteEnquete(@AuthenticationPrincipal LoginUser loginUser,
			@RequestParam("enqueteId") Integer enqueteId, RedirectAttributes redirectAttributes) {
		/*アンケート一覧画面で実施前アンケートの「実施する」ボタンを押下時に呼び出される。
			処理内容は以下のとおり。*/
		/*1.loginUser.getEsqId()を使用し、ESQIDを受け取る。*/
		String esqId = loginUser.getEsqId();

		/*2.コレクション変数を使用し、1と2を代入する。*/
		Integer enqueteStateIdCre[] = { 1, 2 };

		/*3.引数と1.でEnqueteEditService#checkList(Integer enqueteId, Integer enqueteStateIdCre[], String esqId)を使用し、文字列を受け取る*/
		String str = enqueteListService.checkList(enqueteId, enqueteStateIdCre, esqId);

		/*4. 3がnullでない場合*/
		/* 4_1. 3をredirect.addFlashAttributeで引数のエラーメッセージとして格納する redirectAttributes.addFlashAttribute("ERRORMESSAGE", '3の値' );*/
		if (str != null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", str);
			/*4_2. return値を返す*/
			return "redirect:/list";
		}

		/*5.EnqueteListServiceのstartEnqueteUpdateメソッドを「enqueteId」を引数に呼び出す。 ※*/
		boolean ret = enqueteListService.deleteEnquete(enqueteId);

		/*6. 5の戻り値がfalseな場合*/
		if (ret == false) {
			//6_1. redirect.addFlashAttributeでエラーメッセージを格納する。
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "削除されませんでした。");

			//6_2. return値を返す
			return "redirect:/list";
		}

		/*7.return値を返す。*/
		return "redirect:/list";
	}

	@RequestMapping(value = "/startEnquete", method = RequestMethod.GET)
	public String startEnquete(@AuthenticationPrincipal LoginUser loginUser,
			@RequestParam("enqueteId") Integer enqueteId, RedirectAttributes redirectAttributes) {
		/*アンケート一覧画面で実施前アンケートの「実施する」ボタンを押下時に呼び出される。
			処理内容は以下のとおり。*/
		/*1.loginUser.getEsqId()を使用し、ESQIDを受け取る。*/
		String esqId = loginUser.getEsqId();

		/*2.コレクション変数を使用し、2を代入する。*/
		Integer enqueteStateIdCre[] = { 2, 2 };

		/*3.引数と1.でEnqueteEditService#checkList(Integer enqueteId, Integer enqueteStateIdCre[], String esqId)を使用し、文字列を受け取る*/
		String str = enqueteListService.checkList(enqueteId, enqueteStateIdCre, esqId);

		/*4. 3がnullでない場合*/
		/* 4_1. 3をredirect.addFlashAttributeで引数のエラーメッセージとして格納する redirectAttributes.addFlashAttribute("ERRORMESSAGE", '3の値' );*/
		if (str != null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", str);
			/*4_2. return値を返す*/
			return "redirect:/list";
		}

		/*5.EnqueteListServiceのstartEnqueteUpdateメソッドを「enqueteId」を引数に呼び出す。 ※*/
		boolean ret = enqueteListService.startEnqueteUpdate(enqueteId);

		/*6. 5の戻り値がfalseな場合*/
		if (ret == false) {
			//6_1. redirect.addFlashAttributeでエラーメッセージを格納する。
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "このアンケートを実施できませんでした。");
			//6_2. return値を返す
			return "redirect:/list";
		}

		/*7.return値を返す。*/
		return "redirect:/list";
	}

	@RequestMapping(value = "/finishEnquete", method = RequestMethod.GET)
	public String finishEnquete(@AuthenticationPrincipal LoginUser loginUser,
			@RequestParam("enqueteId") Integer enqueteId, RedirectAttributes redirectAttributes) {
		/*アンケート一覧画面で実施中アンケートの「終了する」ボタンを押下時に呼び出される。
			処理内容は以下のとおり。*/
		/*1.loginUser.getEsqId()を使用し、ESQIDを受け取る。*/
		String esqId = loginUser.getEsqId();

		/*2.コレクション変数を使用し、3を代入する。*/
		Integer enqueteStateIdCre[] = { 3, 3 };

		/*3.引数と1.でEnqueteListService#checkList(Integer enqueteId, Integer enqueteStateIdCre[], String esqId)を使用し、文字列を受け取る*/
		String str = enqueteListService.checkList(enqueteId, enqueteStateIdCre, esqId);

		/*4. 3がnullでない場合*/
		/* 4_1. 3をredirect.addFlashAttributeで引数のエラーメッセージとして格納する redirectAttributes.addFlashAttribute("ERRORMESSAGE", '3の値' );*/
		if (str != null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", str);
			/*4_2. return値を返す*/
			return "redirect:/list";
		}

		/*5.EnqueteListServiceのfinishEnqueteUpdateメソッドを「enqueteId」を引数に呼び出す。 ※*/
		boolean ret = enqueteListService.finishEnqueteUpdate(enqueteId);

		/*6. 5の戻り値がfalseな場合*/
		if (ret == false) {
			//6_1. redirect.addFlashAttributeでエラーメッセージを格納する。
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "このアンケートを終了できませんでした。");
			//6_2. return値の1を返す
			return "redirect:/list";
		}

		/*7.return値の1を返す。*/
		return "redirect:/list";
	}

}
