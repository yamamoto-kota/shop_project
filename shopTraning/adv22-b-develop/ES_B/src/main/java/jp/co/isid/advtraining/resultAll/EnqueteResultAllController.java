package jp.co.isid.advtraining.resultAll;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.VM.EnqueteResultAllVM;
import jp.co.isid.advtraining.login.LoginUser;

@Controller
@RequestMapping("/resultAll")

public class EnqueteResultAllController {

	@Autowired
	private EnqueteResultAllService enqueteAllResultService;

	EnqueteResultAllVM enqueteResultAllVM = new EnqueteResultAllVM();

	//集計結果画面に遷移
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String input(@AuthenticationPrincipal LoginUser loginUser, @RequestParam("enqueteId") Integer enqueteId,
			RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("LOGINUSER", loginUser);

		int z = enqueteAllResultService.checkAdmin(enqueteId, loginUser.getEsqId());
		//管理者ではないなら

		if (z == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
			return "redirect:/list";
		}

		//管理者なら
		enqueteResultAllVM = enqueteAllResultService.getEnqueteResultAllVM(enqueteId);
		model.addAttribute("ENQUETERESULTALLVM", enqueteResultAllVM);

		return "resultAll/V203_01EnquteResultAllView";
	}

	//管理者権限確認
	public String unAdmin(String esqId, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
		return "redirect:/list";
	}

	//CSVファイルダウンロード
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String download(@RequestParam("enqueteId") Integer enqueteId, RedirectAttributes redirectAttributes,
			HttpServletResponse httpServletResponse, @AuthenticationPrincipal LoginUser loginUser) {

		//csvファイル作成
		//ファイル命名、スペースの確保

		int z = enqueteAllResultService.checkAdmin(enqueteId, loginUser.getEsqId());
		//管理者ではないなら

		if (z == 2) {

//			unAdmin(loginUser.getEsqId(), redirectAttributes);
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
			return "redirect:/list";
		} else {

			ServletOutputStream out;
			try {
				out = httpServletResponse.getOutputStream();
				String fileName = (enqueteAllResultService.csvDownload(enqueteId).get(0));
				fileName = fileName.replace("", "");
				httpServletResponse.addHeader("Content-Disposition",
						"attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".csv\"");
				httpServletResponse.setContentType("text/csv;charset=shift_jis");
				out.write(enqueteAllResultService.csvDownload(enqueteId).get(1).getBytes("UTF-8"));
			out.close();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
		return null;
	}

}
