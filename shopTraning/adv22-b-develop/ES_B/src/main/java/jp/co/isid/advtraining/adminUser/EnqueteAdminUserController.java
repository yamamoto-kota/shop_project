package jp.co.isid.advtraining.adminUser;

/**
 * EnqueteAdminUserController.java

 * All Rights Reserved, Copyright(c) Fujitsu Learning Media Limited
 */



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.VM.AdminUserVM;
import jp.co.isid.advtraining.login.LoginUser;

/**
 *
 * @author FLM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class EnqueteAdminUserController {

	@Autowired
	private EnqueteAdminUserService enqueteAdminUserService;

	@RequestMapping(value="",  method=RequestMethod.GET)
	public String index(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes, @RequestParam("enqueteId") Integer enqueteId, Model model){
		int z = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(z == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "対象アンケートが存在しません");
			return "redirect:/list";
		}
		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません");
			return "redirect:/list";
		}
		//サービスからenqueteIDを使用してVMの情報を取得
		AdminUserVM adminUserVM = enqueteAdminUserService.getEnqueteAdminUser(enqueteId);
		//modelにADMINUSERVMとLOGINUSERを格納
		model.addAttribute("ADMINUSERVM", adminUserVM);
		model.addAttribute("LOGINUSER", loginUser);
		//adminUserのV205_01EnqueteAdminUserViewに遷移するように記述
		return "adminUser/V205_01EnqueteAdminUserView";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	private String search(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes, @RequestParam("searchWord") String searchWord,
								@RequestParam("enqueteId") Integer enqueteId, Model model) {
		int z = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(z == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています");
			return "redirect:/list";
		}
		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません");
			return "redirect:/list";
		}
		AdminUserVM esquserVM = enqueteAdminUserService.search(enqueteId, searchWord);
		if(searchWord.equals("")) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE1", "searchWordが未入力です");
			return "redirect:/admin?enqueteId="+ enqueteId;
		}
		if(esquserVM == null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE1", "すでに追加されています");
			return "redirect:/admin?enqueteId="+ enqueteId;
		}
		if(esquserVM.getEsqUserDeptList().isEmpty()) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE1", "検索条件「"+searchWord+"」に合致する結果が⾒つかりませんでした");
			return "redirect:/admin?enqueteId="+ enqueteId;
		}
		model.addAttribute("ADMINUSERVM", esquserVM);
		model.addAttribute("LOGINUSER", loginUser);
		return "adminUser/V205_01EnqueteAdminUserView";
	}


	@RequestMapping(value="/insertupdate",  method=RequestMethod.POST)
	public String delete(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes, @RequestParam("enqueteId") Integer enqueteId,
								@RequestParam("esqId") String esqId, Model model){
		Integer deleteFlag = 1;
		int z = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(z == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています");
			return "redirect:/list";
		}
		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません");
			return "redirect:/list";
		}
		AdminUserVM adminVM = enqueteAdminUserService.getEnqueteAdminUser(enqueteId);
		if(esqId.equals(adminVM.getEnquete().getCreateUserId())) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケート作成者は削除できません");
			return "redirect:/list";
		}
		Boolean a = enqueteAdminUserService.insertUpdate(enqueteId, esqId, deleteFlag);
		if(a == true) {
			return "redirect:/admin?enqueteId="+ enqueteId;
		}else {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE2", "すでに削除されています");
			return "redirect:/admin?enqueteId="+ enqueteId;
		}
	}


	@RequestMapping(value="/add",  method=RequestMethod.POST)
	public String add(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes, @RequestParam("enqueteId") Integer enqueteId,
							@RequestParam("esqId") String esqId, Model model){
		Integer deleteFlag = 0;
		int z = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(z == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています");
			return "redirect:/list";
		}
		if(z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません");
			return "redirect:/list";
		}

		//追加
		Boolean a = enqueteAdminUserService.insertUpdate(enqueteId, esqId, deleteFlag);
		if(a == true) {
			return "redirect:/admin?enqueteId="+ enqueteId;
		}else {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE3", "すでに追加されています");
			return "redirect:/admin?enqueteId="+ enqueteId;
		}
	}


	@RequestMapping(value = "/back", method = RequestMethod.GET)public String back(Model model) {
	//リストにリダイレクトする
	return "redirect:/list";
	}
}
