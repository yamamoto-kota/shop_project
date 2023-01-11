package jp.co.isid.advtraining.edit;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.Form.ChoiceForm;
import jp.co.isid.advtraining.Form.EnqueteForm;
import jp.co.isid.advtraining.Form.QuestionForm;
import jp.co.isid.advtraining.VM.EnqueteEditVM;
import jp.co.isid.advtraining.login.LoginUser;


@Controller
@RequestMapping("/edit")
public class EnqueteEditController {

	@Autowired
	EnqueteEditService enqueteEditService;

	public String modelAndViewForEdit(EnqueteForm enqueteForm,EnqueteEditVM enqueteEditVM, Model model,LoginUser loginUser) {
		model.addAttribute("ENQUETEEDITVM",enqueteEditVM);
		model.addAttribute("enqueteForm",enqueteForm);
		model.addAttribute("LOGINUSER", loginUser);

		return "edit/V201_01EnqueteEditView";
	}

	@RequestMapping(value="",method=RequestMethod.GET)
	public String edit(@AuthenticationPrincipal LoginUser loginUser,@RequestParam("enqueteId")Integer enqueteId,RedirectAttributes redirectAttributes,Model model) {

		String esqId=loginUser.getEsqId();
		String ret=enqueteEditService.checkEdit(enqueteId, esqId);
		if(ret!=null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE",ret);
			return "redirect:/list";
		}
		EnqueteEditVM enqueteEditVM=enqueteEditService.getEnqueteEditVM();
		EnqueteForm  enqueteForm=enqueteEditService.getEnqueteDetail(enqueteId);
		return modelAndViewForEdit(enqueteForm,enqueteEditVM,model,loginUser);
	}


	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(@AuthenticationPrincipal LoginUser loginUser,Model model) {
		EnqueteForm enqueteForm=new EnqueteForm();

		//初期の2つの選択肢を作成
		List<ChoiceForm> tmpChoiceFormList=new ArrayList<ChoiceForm>();
		ChoiceForm tmpChoiceForm1=new ChoiceForm();
		tmpChoiceForm1.setChoiceNumber(1);
		tmpChoiceFormList.add(tmpChoiceForm1);
		ChoiceForm tmpChoiceForm2=new ChoiceForm();
		tmpChoiceForm2.setChoiceNumber(2);
		tmpChoiceFormList.add(tmpChoiceForm2);
		QuestionForm tmpQuestionForm=new QuestionForm();
		tmpQuestionForm.setChoiceFormList(tmpChoiceFormList);
		tmpQuestionForm.setQuestionNumber(1);
		List<QuestionForm> tmpQuestionFormList=new ArrayList<QuestionForm>();
		tmpQuestionFormList.add(tmpQuestionForm);
		enqueteForm.setQuestionFormList(tmpQuestionFormList);

		EnqueteEditVM enqueteEditVM=enqueteEditService.getEnqueteEditVM();
		return modelAndViewForEdit(enqueteForm,enqueteEditVM,model,loginUser);
	}

	@RequestMapping(value="temporarySaving",method=RequestMethod.POST)
	public String temporarySaving(@AuthenticationPrincipal LoginUser loginUser,EnqueteForm enqueteForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {
		EnqueteEditVM enqueteEditVM=enqueteEditService.getEnqueteEditVM();

		//requireFlag null→0の為に総入れ替え+questionFormListの無駄な尻尾を除去
		EnqueteForm exEnqueteForm=new EnqueteForm();
		exEnqueteForm.setEnqueteId(enqueteForm.getEnqueteId());
		exEnqueteForm.setEnqueteName(enqueteForm.getEnqueteName());
		exEnqueteForm.setCreateUserId(enqueteForm.getCreateUserId());
		exEnqueteForm.setEnqueteSubtext(enqueteForm.getEnqueteSubtext());
		exEnqueteForm.setDeptIds(enqueteForm.getDeptIds());
		exEnqueteForm.setVersion(enqueteForm.getVersion());
		List<QuestionForm> tmpQuestionFormList=new ArrayList<QuestionForm>();
		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			if(enqueteForm.getQuestionFormList().get(i).getQuestionNumber()==null) {
				break;
			}
			QuestionForm tmpQuestionForm=new QuestionForm();
			tmpQuestionForm.setQuestionId(enqueteForm.getQuestionFormList().get(i).getQuestionId());
			tmpQuestionForm.setEnqueteId(enqueteForm.getQuestionFormList().get(i).getEnqueteId());
			tmpQuestionForm.setQuestionNumber(enqueteForm.getQuestionFormList().get(i).getQuestionNumber());
			tmpQuestionForm.setQuestionTypeId(enqueteForm.getQuestionFormList().get(i).getQuestionTypeId());
			if(enqueteForm.getQuestionFormList().get(i).getRequireFlag()==null) {
				tmpQuestionForm.setRequireFlag(0);
			}
			else {
				tmpQuestionForm.setRequireFlag(1);
			}
			tmpQuestionForm.setQuestionText(enqueteForm.getQuestionFormList().get(i).getQuestionText());
			tmpQuestionForm.setQuestionSubtext(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext());
			tmpQuestionForm.setChoiceFormList(enqueteForm.getQuestionFormList().get(i).getChoiceFormList());
			tmpQuestionFormList.add(tmpQuestionForm);
		}
		exEnqueteForm.setQuestionFormList(tmpQuestionFormList);
		BeanUtils.copyProperties(exEnqueteForm, enqueteForm);

		//checkNull
		List<String> checkNullList=new ArrayList<>();
		checkNullList=enqueteEditService.checkNull(enqueteForm);
		if(!(checkNullList.isEmpty())) {
			for(int i=0;i<checkNullList.size();i++) {
				String[] errorCheck;
				errorCheck=checkNullList.get(i).split(",");
				bindingResult.rejectValue(errorCheck[0],"",errorCheck[1]);
			}
		}

		//checkEdit,checkEnqueteと初回のesqId埋め 初回は排他制御(checkEdit)とバージョンチェック(checkEnquete)なし
		if(!(enqueteForm.getCreateUserId().equals(""))){
			String ret=enqueteEditService.checkEdit(enqueteForm.getEnqueteId(),loginUser.getEsqId());
			if(ret!=null) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE",ret);
				return "redirect:/list";
			}
			ret=enqueteEditService.checkEnquete(enqueteForm.getEnqueteId(),enqueteForm.getVersion());
			if(ret!=null) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE",ret);
				return "redirect:/list";
			}
		}

		//手動入力チェック
		if(enqueteForm.getEnqueteName().isBlank())  {
			bindingResult.rejectValue("enqueteName","","アンケート名が未入力です");
		}
		if(
			enqueteForm.getEnqueteName().contains("\\") ||
			enqueteForm.getEnqueteName().contains("/") ||
			enqueteForm.getEnqueteName().contains(":") ||
			enqueteForm.getEnqueteName().contains("*") ||
			enqueteForm.getEnqueteName().contains("?") ||
			enqueteForm.getEnqueteName().contains("\"") ||
			enqueteForm.getEnqueteName().contains("<") ||
			enqueteForm.getEnqueteName().contains(">") ||
			enqueteForm.getEnqueteName().contains("|")||
			enqueteForm.getEnqueteName().contains(" ")||
			enqueteForm.getEnqueteName().contains("　")||
			enqueteForm.getEnqueteName().contains("\t")
		){
			bindingResult.rejectValue("enqueteName","","アンケート名には空白文字と次の文字は使えません: \\ / : * ? \" < > |");
		}
		if(enqueteForm.getEnqueteName().length()>100) {
			bindingResult.rejectValue("enqueteName","","アンケート名は100文字以下で入力してください");
		}
		if(enqueteForm.getEnqueteSubtext().length()>200) {
			bindingResult.rejectValue("enqueteSubtext","","アンケート補足説明は200文字以下で入力してください");
		}
		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			if(enqueteForm.getQuestionFormList().get(i).getQuestionText().length()>100) {
				bindingResult.rejectValue("questionFormList["+i+"].questionText","","質問内容は100文字以下で入力してください");
			}
			if(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext().length()>200) {
				bindingResult.rejectValue("questionFormList["+i+"].questionSubtext","","質問内容の補足説明は200文字以下で入力してください");
			}
			for(int j=0;j<enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size();j++) {
				if(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceText().length()>100) {
					bindingResult.rejectValue("questionFormList["+i+"].choiceFormList["+j+"].choiceText","","選択肢は100文字以下で入力してください");
				}
			}
		}

		if(bindingResult.hasErrors()) {
			return modelAndViewForEdit(enqueteForm,enqueteEditVM,model,loginUser);
		}

		Integer ret;
		if(enqueteForm.getCreateUserId().equals("")) {//新規作成
			enqueteForm.setCreateUserId(loginUser.getEsqId());
			ret=enqueteEditService.insertEnquete(enqueteForm,1/*一時保存だから1*/);
			enqueteForm.setEnqueteId(ret);
		}
		else{//更新
			ret=enqueteEditService.updateEnquete(enqueteForm, 1/*一時保存だから1*/);
		}

		if(ret==null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE","アンケートの一時保存ができませんでした");
			return "redirect:/list";
		}

		model.addAttribute("ENQUETEEDITVM",enqueteEditVM);
		model.addAttribute("enqueteForm",enqueteForm);
		return "redirect:/edit?enqueteId="+enqueteForm.getEnqueteId();
	}

	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@AuthenticationPrincipal LoginUser loginUser,EnqueteForm enqueteForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {//@Validated
		EnqueteEditVM enqueteEditVM=enqueteEditService.getEnqueteEditVM();

		//requireFlag null→0の為に総入れ替え+questionFormListの無駄な尻尾を除去
		EnqueteForm exEnqueteForm=new EnqueteForm();
		exEnqueteForm.setEnqueteId(enqueteForm.getEnqueteId());
		exEnqueteForm.setEnqueteName(enqueteForm.getEnqueteName());
		exEnqueteForm.setCreateUserId(enqueteForm.getCreateUserId());
		exEnqueteForm.setEnqueteSubtext(enqueteForm.getEnqueteSubtext());
		exEnqueteForm.setDeptIds(enqueteForm.getDeptIds());
		exEnqueteForm.setVersion(enqueteForm.getVersion());
		List<QuestionForm> tmpQuestionFormList=new ArrayList<QuestionForm>();
		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			if(enqueteForm.getQuestionFormList().get(i).getQuestionNumber()==null) {
				break;
			}
			QuestionForm tmpQuestionForm=new QuestionForm();
			tmpQuestionForm.setQuestionId(enqueteForm.getQuestionFormList().get(i).getQuestionId());
			tmpQuestionForm.setEnqueteId(enqueteForm.getQuestionFormList().get(i).getEnqueteId());
			tmpQuestionForm.setQuestionNumber(enqueteForm.getQuestionFormList().get(i).getQuestionNumber());
			tmpQuestionForm.setQuestionTypeId(enqueteForm.getQuestionFormList().get(i).getQuestionTypeId());
			if(enqueteForm.getQuestionFormList().get(i).getRequireFlag()==null) {
				tmpQuestionForm.setRequireFlag(0);
			}
			else {
				tmpQuestionForm.setRequireFlag(1);
			}
			tmpQuestionForm.setQuestionText(enqueteForm.getQuestionFormList().get(i).getQuestionText());
			tmpQuestionForm.setQuestionSubtext(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext());
			tmpQuestionForm.setChoiceFormList(enqueteForm.getQuestionFormList().get(i).getChoiceFormList());
			tmpQuestionFormList.add(tmpQuestionForm);
		}
		exEnqueteForm.setQuestionFormList(tmpQuestionFormList);
		BeanUtils.copyProperties(exEnqueteForm, enqueteForm);

		//checkNull
		List<String> checkNullList=new ArrayList<>();
		checkNullList=enqueteEditService.checkNull(enqueteForm);
		if(!(checkNullList.isEmpty())) {
			for(int i=0;i<checkNullList.size();i++) {
				String[] errorCheck;
				errorCheck=checkNullList.get(i).split(",");
				bindingResult.rejectValue(errorCheck[0],"",errorCheck[1]);
			}
		}

		//checkEdit,checkEnqueteと初回のesqId埋め 初回は排他制御(checkEdit)とバージョンチェック(checkEnquete)なし
		if(!(enqueteForm.getCreateUserId().equals(""))){
			String ret=enqueteEditService.checkEdit(enqueteForm.getEnqueteId(),loginUser.getEsqId());
			if(ret!=null) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE",ret);
				return "redirect:/list";
			}
			ret=enqueteEditService.checkEnquete(enqueteForm.getEnqueteId(),enqueteForm.getVersion());
			if(ret!=null) {
				redirectAttributes.addFlashAttribute("ERRORMESSAGE",ret);
				return "redirect:/list";
			}
		}

		//事業部は手動チェック
		if(enqueteForm.getDeptIds().length==0) {
			bindingResult.rejectValue("deptIds","","事業部が未選択です");
		}
		//その他入力チェックは@Validatedで自動
		//→自動だと無駄な尻尾にエラーがつくので、全部手動でやる
		if(enqueteForm.getEnqueteName().isBlank()) {
			bindingResult.rejectValue("enqueteName","","アンケート名が未入力です");
		}
		if(
			enqueteForm.getEnqueteName().contains("\\") ||
			enqueteForm.getEnqueteName().contains("/") ||
			enqueteForm.getEnqueteName().contains(":") ||
			enqueteForm.getEnqueteName().contains("*") ||
			enqueteForm.getEnqueteName().contains("?") ||
			enqueteForm.getEnqueteName().contains("\"") ||
			enqueteForm.getEnqueteName().contains("<") ||
			enqueteForm.getEnqueteName().contains(">") ||
			enqueteForm.getEnqueteName().contains("|")||
			enqueteForm.getEnqueteName().contains(" ")||
			enqueteForm.getEnqueteName().contains("　")||
			enqueteForm.getEnqueteName().contains("\t")
		){
			bindingResult.rejectValue("enqueteName","","アンケート名には空白文字と次の文字は使えません: \\ / : * ? \" < > |");
		}
		if(enqueteForm.getEnqueteName().length()>100) {
			bindingResult.rejectValue("enqueteName","","アンケート名は100文字以下で入力してください");
		}
		if(enqueteForm.getEnqueteSubtext().length()>200) {
			bindingResult.rejectValue("enqueteSubtext","","アンケート補足説明は200文字以下で入力してください");
		}
		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			if(enqueteForm.getQuestionFormList().get(i).getQuestionText().isBlank()) {
				bindingResult.rejectValue("questionFormList["+i+"].questionText","","質問内容が未入力です");
			}
			if(enqueteForm.getQuestionFormList().get(i).getQuestionText().length()>100) {
				bindingResult.rejectValue("questionFormList["+i+"].questionText","","質問内容は100文字以下で入力してください");
			}
			if(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext().length()>200) {
				bindingResult.rejectValue("questionFormList["+i+"].questionSubtext","","質問内容の補足説明は200文字以下で入力してください");
			}
			for(int j=0;j<enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size();j++) {
				if(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceText().isBlank()) {
					bindingResult.rejectValue("questionFormList["+i+"].choiceFormList["+j+"].choiceText","","選択肢が未入力です");
				}
				if(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceText().length()>100) {
					bindingResult.rejectValue("questionFormList["+i+"].choiceFormList["+j+"].choiceText","","選択肢は100文字以下で入力してください");
				}
			}
		}

		if(bindingResult.hasErrors()) {
			return modelAndViewForEdit(enqueteForm,enqueteEditVM,model,loginUser);
		}

		Integer ret;
		if(enqueteForm.getCreateUserId().equals("")) {//新規
			enqueteForm.setCreateUserId(loginUser.getEsqId());
			ret=enqueteEditService.insertEnquete(enqueteForm,2/*保存だから2*/);
		}
		else{//更新
			ret=enqueteEditService.updateEnquete(enqueteForm, 2/*保存だから2*/);
		}

		if(ret==null) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE","アンケートの保存ができませんでした");
			return "redirect:/list";
		}
		return "redirect:/list";
	}

	@RequestMapping(value="back",method=RequestMethod.POST)
	public String back() {
		return "redirect:/list";
	}

}