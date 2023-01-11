package jp.co.isid.advtraining.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.VM.ResultEnqueteVM;
import jp.co.isid.advtraining.login.LoginUser;


@Controller
@RequestMapping("/result")
public class EnqueteResultController {

@Autowired

	private EnqueteResultService enqueteResultService;


	@RequestMapping(value = "",method = RequestMethod.POST)

	public String index(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("enqueteId") Integer enqueteId ,@RequestParam("esqId") String esqId, RedirectAttributes redirectAttributes,Model model) {
		int z = enqueteResultService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(z == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています。");
			return "redirect:/list";
		}
		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
			return "redirect:/list";
		}
//		//1. loginUser.getEsqId()を使用し、ESQIDを受け取る。(loginUserEsqId)
//		//ログインユーザーのESQID
//		String loginUserEsqId = loginUser.getEsqId();
//
//
//		//2. enqueteIdと１を引数にEnqueteResultService#checkadmin(Integer enqueteId, String esqId)を使用し、文字列を受け取る　。(effect)
//		String effect = enqueteResultService.checkAdmin(enqueteId, loginUserEsqId) ;
//
//		//3. 2.がnullでない場合
////		   3_1. 1をredirect.addFlashAttributeで引数のエラーメッセージとして格納する
////	　　　　　redirectAttributes.addFlashAttribute("ERRORMESSAGE", effect);
//			if (effect !=  null) {
//
//				redirectAttribute.addFlashAttribute("ERRORMESSAG",effect);
//				return "/V200_01EnqueteListView";
//			}
//

//			@RequestMapping(value="",  method=RequestMethod.GET)
//			public String index(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes, @RequestParam("enqueteId") Integer enqueteId, Model model){
//				int z = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
//				if(z == 1) {
//					redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています。");
//					return "redirect:/list";
//				}
//				if(z == 2) {
//					redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
//					return "redirect:/list";
//				}
//				//サービスからenqueteIDを使用してVMの情報を取得
//				AdminUserVM adminUserVM = enqueteAdminUserService.getEnqueteAdminUser(enqueteId);
//				//modelにADMINUSERVMとLOGINUSERを格納
//				model.addAttribute("ADMINUSERVM", adminUserVM);
//				model.addAttribute("LOGINUSER", loginUser);
//				//adminUserのV205_01EnqueteAdminUserViewに遷移するように記述
//				return "adminUser/V205_01EnqueteAdminUserView";





		//左辺：変数（EnqueteVM：返ってくる値の形、enqueteVM：箱の名前）を作成、右辺で使いたいservice、serviceのメソッドと引数を呼んでくる
		ResultEnqueteVM resultEnqueteVM = enqueteResultService.getResultEnqueteVM(enqueteId,esqId);



		//modelに4,5で取得したアンケート情報を格納する
		model.addAttribute("ENQUETEVM",resultEnqueteVM);

		model.addAttribute("LOGINUSER",loginUser);
		//return値を返す


		return "result/V204_02EnqueteResultView";

}


	@RequestMapping(value = "/backToERA", method = RequestMethod.GET)
    public String backToERA(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("enqueteId") Integer enqueteId ,RedirectAttributes redirectAttributes,Model model) {

		model.addAttribute("enqueteId", enqueteId);


		//2. enqueteIdと１を引数にEnqueteResultService#checkadmin(Integer enqueteId, String esqId)を使用し、文字列を受け取る。(outcome)
		int z = enqueteResultService.checkAdmin(enqueteId, loginUser.getEsqId());

		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
			return "redirect:/list";

		}
		else {
			return "resultAll/V203_01EnquteResultAllView";
		}
}
}