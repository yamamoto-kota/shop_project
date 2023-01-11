package jp.co.isid.advtraining.answer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.Form.EnqueteAnswerForm;
import jp.co.isid.advtraining.Form.QuestionAnswerForm;
import jp.co.isid.advtraining.VM.EnqueteAnswerVM;
import jp.co.isid.advtraining.VM.EnqueteVM;
import jp.co.isid.advtraining.login.LoginUser;

@Controller
@SessionAttributes(types = EnqueteAnswerForm.class)
@RequestMapping("/answer")
public class EnqueteAnswerController {

	/* Service */
	@Autowired
	private EnqueteAnswerService enqueteAnswerService;

	/**
	 * 一覧画面から回答画面を表示する([回答する]ボタン)
	 * マッピングするURL： /index マッピングするHTTPメソッド： POST
	 *
	 * @param enqueteId アンケートID
	 * @param isAnswer  回答参照(0:回答 1:回答参照)
	 * @return アンケート回答画面（/answer/V202_01AnqueteAnswerView）
	 */

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes,
			@RequestParam("enqueteId") Integer enqueteId, @RequestParam("isAnswer") Integer isAnswer, Model model) {

		// 1.排他制御
		// 1-1.対象部署以外のアンケートを不正取得できない
		EnqueteVM enqueteVM = enqueteAnswerService.getEnqueteVM(enqueteId);
		if (enqueteVM == null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています。");
			return "redirect:/list";
		}
		// 1-2.対象部署以外のアンケートを不正取得できない
		int ans = enqueteAnswerService.adminUser(enqueteId, loginUser.getDeptId());
		if (ans == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートの対象部署ではありません。");
			return "redirect:/list";
		}

		// 2.Modelへの格納
		model.addAttribute("LOGINUSER", loginUser);
		model.addAttribute("ENQVM", enqueteVM);
		int enqueteStateId = enqueteVM.getEnquete().getEnqueteStateId();

		// 3.回答か回答参照かの場合分け-isAnsweredが0で回答だった場合
		if (isAnswer == 0) {

			// 3-1.排他制御
			// アンケート開始前の場合は一覧画面に遷移
			if (enqueteStateId != 3) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは受付終了しています。");
				return "redirect:/list";
			}

			model.addAttribute("isAnswer", isAnswer);
			EnqueteAnswerForm enqueteAnswerForm = new EnqueteAnswerForm();
			model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);

			// 4.回答か回答参照かの場合分け-isAnsweredが1で回答参照だった場合
		} else {

			// 4-1.enqueteStateIdが3以外の状態に変更された場合の対策
			// アンケート開始前の場合は一覧画面に遷移
			if (enqueteStateId == 1 || enqueteStateId == 2) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは受付終了しています。");
				return "redirect:/list";
			}

			model.addAttribute("isAnswer", isAnswer);
			EnqueteAnswerVM enqueteAnswerVM = enqueteAnswerService.getEnqueteAnswerVM(enqueteId, loginUser.getEsqId());

			// 4-4.EnqueteAnswerVM型のオブジェクトenqueteAnswerVMからEnqueteAnswerForm型のformにデータを移し替える。
			EnqueteAnswerForm enqueteAnswerForm = new EnqueteAnswerForm();
			List<QuestionAnswerForm> questionAnswerFormList = new ArrayList<>();

			// questionTypeによる分岐
			// i : 質問番号
			for (int i = 0; i < enqueteVM.getQuestionVMList().size(); i++) {
				// questionTypeによる分岐
				int currentquestionType = enqueteVM.getQuestionVMList().get(i).getQuestion().getQuestionTypeId();
				QuestionAnswerForm questionAnswerForm = new QuestionAnswerForm();

				// 【自由記述】
				if (currentquestionType == 3) {
					String answerText = enqueteAnswerVM.getQuestionAnswerVMList().get(i).getQuestionAnswer()
							.getAnswerText();
					questionAnswerForm.setAnswerText(answerText);
					// 【複数選択】
				} else if (currentquestionType == 2) {

					List<Integer> choiceIdList = new ArrayList<>();
					for (int j = 0; j < enqueteAnswerVM.getQuestionAnswerVMList().get(i).getChoiceAnswerList()
							.size(); j++) {
						Integer currentChoiceId = enqueteAnswerVM.getQuestionAnswerVMList().get(i).getChoiceAnswerList()
								.get(j).getChoiceId();
						choiceIdList.add(currentChoiceId);
					}
					questionAnswerForm.setChoiceIdList(choiceIdList);
					// 【単一選択】
				} else {

					if (!(enqueteAnswerVM.getQuestionAnswerVMList().get(0).getChoiceAnswerList() == null)) {
						List<Integer> choiceIdList = new ArrayList<>();
						for (int j = 0; j < enqueteAnswerVM.getQuestionAnswerVMList().get(i).getChoiceAnswerList()
								.size(); j++) {
							Integer currentChoiceId = enqueteAnswerVM.getQuestionAnswerVMList().get(i)
									.getChoiceAnswerList().get(j).getChoiceId();
							choiceIdList.add(currentChoiceId);
						}
						questionAnswerForm.setChoiceIdList(choiceIdList);
					}

				}
				questionAnswerFormList.add(questionAnswerForm);
			}
			enqueteAnswerForm.setQuestionAnswerFormList(questionAnswerFormList);
			model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);
		}

		// 5.遷移
		return "answer/V202_01EnqueteAnswerView";
	}

	/**
	 * 回答確認画面への遷移(V202_01[確認]ボタン→V202_02)
	 * マッピングするURL：/confirm マッピングするHTTPメソッド： POST
	 *
	 * @param enqueteId アンケートID
	 * @return アンケート回答画面（/answer/V202_02AnqueteAnswerConfirmView）
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes,
			@RequestParam("enqueteId") Integer enqueteId,
			@ModelAttribute("enqueteAnswerForm") EnqueteAnswerForm enqueteAnswerForm, BindingResult bindingResult,
			Model model) {

		// 1.Modelへの格納
		EnqueteVM enqueteVM = enqueteAnswerService.getEnqueteVM(enqueteId);
		model.addAttribute("LOGINUSER", loginUser);
		model.addAttribute("ENQVM", enqueteVM);
		model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);

		// 2.排他制御
		//2-1.
		if (enqueteAnswerService.getEnqueteAnswerVM(enqueteId, loginUser.getEsqId()) != null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは回答済みです");
			return "redirect:/list";
		}
		// 2-2.アンケート開始前の場合は一覧画面に遷移
		int enqueteStateId = enqueteVM.getEnquete().getEnqueteStateId();
		if (enqueteStateId != 3) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは受付終了しています。");
			return "redirect:/list";
		}

		// 3.入力チェック
		// i : 質問番号
		for (int i = 0; i < enqueteAnswerForm.getQuestionAnswerFormList().size(); i++) {

			// 必須回答 かつ formの回答がnull の場合rejectValueで値を返す
			// questiontypeによる分岐
			int currentquestionType = enqueteVM.getQuestionVMList().get(i).getQuestion().getQuestionTypeId();
			// 【自由記述】かつ必須のとき
			if (currentquestionType == 3 && enqueteVM.getQuestionVMList().get(i).getQuestion().getRequireFlag() == 1) {
				// 記述が空白のとき
				if (enqueteAnswerForm.getQuestionAnswerFormList().get(i).getAnswerText().equals("")) {
					bindingResult.rejectValue("questionAnswerFormList[" + i + "].questionId", "", "この質問は必須回答です。");
				}

				// 必須入力のとき
			} else if (enqueteVM.getQuestionVMList().get(i).getQuestion().getRequireFlag() == 1) {
				// 【単一選択】
				if (currentquestionType == 1
						&& enqueteAnswerForm.getQuestionAnswerFormList().get(i).getChoiceIdList() == null) {
					bindingResult.rejectValue("questionAnswerFormList[" + i + "].questionId", "", "この質問は必須回答です");

					// 【複数選択】
				} else if (currentquestionType == 2
						&& enqueteAnswerForm.getQuestionAnswerFormList().get(i).getChoiceIdList().isEmpty()) {
					bindingResult.rejectValue("questionAnswerFormList[" + i + "].questionId", "", "この質問は必須回答です");

				}
			}
		}

		if (bindingResult.hasErrors()) {

			// 3-3-1.Modelへの格納
			model.addAttribute("LOGINUSER", loginUser);
			model.addAttribute("ENQVM", enqueteVM);
			model.addAttribute("isAnswer", 0);
			// 未回答のため、空のEnqueteAnswerFormを作成
			model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);

			return "answer/V202_01EnqueteAnswerView";
		}

		// 3.遷移
		return "answer/V202_02EnqueteAnswerConfirmView";
	}

	/**
	 * V202_02 回答確認画面([送信]ボタン) -> 一覧画面 マッピングするURL：/submit マッピングするHTTPメソッド： POST
	 *
	 * @param enqueteId アンケートID
	 * @return アンケート回答画面（/answer/V202_02AnqueteAnswerConfirmView）
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes,
			@RequestParam("enqueteId") Integer enqueteId,
			@ModelAttribute("enqueteAnswerForm") EnqueteAnswerForm enqueteAnswerForm, Model model) {

		// 1.排他制御
		EnqueteVM enqueteVM = enqueteAnswerService.getEnqueteVM(enqueteId);
		if (enqueteVM.getEnquete().getEnqueteStateId() != 3) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは受付終了しています");
			return "redirect:/list";
		} else if (enqueteAnswerService.getEnqueteAnswerVM(enqueteId, loginUser.getEsqId()) != null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは回答済みです");
			return "redirect:/list";
		}

		// 2.サービスメソッドinsertAnswerの呼び出し
		enqueteAnswerService.insertAnswer(enqueteAnswerForm, enqueteVM, enqueteId, loginUser.getEsqId());

		// 3.遷移
		return "redirect:/list";
	}

	/**
	 * V202_02 回答確認画面からV202_01 回答画面への遷移
	 * マッピングするURL：/backToIndex マッピングするHTTPメソッド： POST
	 *
	 * @param enqueteId アンケートID
	 * @return アンケート回答画面（/answer/V202_01AnqueteAnswerView）
	 */
	@RequestMapping(value = "/backToIndex", method = RequestMethod.POST)
	public String backToIndex(@AuthenticationPrincipal LoginUser loginUser, RedirectAttributes redirectAttributes,
			@RequestParam("enqueteId") Integer enqueteId, EnqueteAnswerForm enqueteAnswerForm, Model model) {

		// 1.排他制御
		EnqueteVM enqueteVM = enqueteAnswerService.getEnqueteVM(enqueteId);
		// アンケート終了の場合は一覧画面に遷移
		int enqueteStateId = enqueteVM.getEnquete().getEnqueteStateId();
		if (enqueteStateId != 3) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは受付終了しています");
			return "redirect:/list";
		}

		// 2.Modelへの格納
		model.addAttribute("LOGINUSER", loginUser);
		model.addAttribute("ENQVM", enqueteVM);
		//回答確認画面にいる段階で未回答であるため、isAnswer=0 に設定
		model.addAttribute("isAnswer", 0);
		model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);

		// 3.遷移
		return "answer/V202_01EnqueteAnswerView";
	}

	/**
	 * 回答画面から一覧画面への遷移
	 * マッピングするURL：/goList マッピングするHTTPメソッド： POST
	 *
	 * @return アンケート回答画面（/answer/V202_01AnqueteAnswerView）
	 */
	@RequestMapping(value = "/goList", method = RequestMethod.POST)
	public String goList(@AuthenticationPrincipal LoginUser loginUser,
			@ModelAttribute("enqueteAnswerForm") EnqueteAnswerForm enqueteAnswerForm, Model model) {

		//1.modelへの格納
		model.addAttribute("enqueteAnswerForm", enqueteAnswerForm);

		// 2.遷移
		return "redirect:/list";
	}

}
