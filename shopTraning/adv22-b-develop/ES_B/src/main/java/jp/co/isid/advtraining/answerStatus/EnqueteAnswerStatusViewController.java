package jp.co.isid.advtraining.answerStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.isid.advtraining.adminUser.EnqueteAdminUserService;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.entity.Dept;
import jp.co.isid.advtraining.login.LoginUser;

@Controller
@Transactional
@RequestMapping("/status")
public class EnqueteAnswerStatusViewController {

	//Service呼び出し
	private EnqueteAnswerStatusViewService  enqueteAnswerStatusViewService;
	@Autowired
	public EnqueteAnswerStatusViewController(EnqueteAnswerStatusViewService  enqueteAnswerStatusViewService) {
		this.enqueteAnswerStatusViewService=enqueteAnswerStatusViewService;
	}
	@Autowired
	 private EnqueteAdminUserService  enqueteAdminUserService;


	//アンケート回答者状況画面を表示
	@RequestMapping(value="",method = RequestMethod.GET)
	public String answerStatus(RedirectAttributes redirectAttributes, @AuthenticationPrincipal LoginUser loginUser ,@RequestParam("enqueteId") Integer enqueteId,Integer deptId,Model model) {

		model.addAttribute("LOGINUSER", loginUser);

		//管理者権限を持っているかを判断
		int admin = enqueteAdminUserService.checkAdmin(enqueteId, loginUser.getEsqId());
		if(admin == 1) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "アンケートは削除されています。");
			return "redirect:/list";
		}
		if(admin == 2) {
			redirectAttributes.addFlashAttribute("ERRORMESSAGE", "管理者権限がありません。");
			return "redirect:/list";
		}


		//Dao,Service①アンケート対象部署に所属する未回答者をすべて返す ○使う
		List<AnswerStatus> answerStatusList=enqueteAnswerStatusViewService.getUnsanswerdList1(enqueteId);
		//UNANSWERD=未回答者リスト
		model.addAttribute("UNANSWERD",answerStatusList);



		//Dao,Service②アンケート対象部署未回答者の総数  ○使う
		Integer allunans=enqueteAnswerStatusViewService.getAllunans2(enqueteId);
		//ALLUNANS=アンケート対象部署の未回答者の総数
		model.addAttribute("ALLUNANS",allunans);

		//Dao,Service③アンケート対象部署に所属しているすべてのユーザー総数  ○使う
		Integer alluser=enqueteAnswerStatusViewService.getAlluser3(enqueteId);
		//ALLUSER=アンケート対象部署に所属するユーザー数
		model.addAttribute("ALLUSER",alluser);

		//Dao,Service④アンケート対象部署のIDリスト  ○使う
		List<Integer> deptIdList=enqueteAnswerStatusViewService.getTergetDepartmentList4(enqueteId);
		//TERGETDEPARTMENT=アンケート対象部署のIDリスト
		model.addAttribute("TERGETDEPARTMENT", deptIdList);

//		//Dao,Service⑤指定部署の未回答者人数  ×
//		Integer unans=enqueteAnswerStatusViewService.getUnans5(enqueteId,deptId);
	    //UNANS=部署ごとのアンケート未回答者の人数
//		model.addAttribute("UNANS", unans);

		//Dao,Service⑤⑤指定部署の未回答者人数
				List <Integer>deptNoList=enqueteAnswerStatusViewService.getUnans5(enqueteId);
				model.addAttribute("DEPTNOLIST", deptNoList);

		//Dao,Service⑥部署ごとの所属人数  ○
		List<Integer> deptAllUser=enqueteAnswerStatusViewService.getUser6(enqueteId);
		//user=部署ごとの所属している人数
		model.addAttribute("USER", deptAllUser);





		//Dao,Service⑦ (画面モック)未回答者の左側の情報表示
		List<AnswerStatus> allUnansList=enqueteAnswerStatusViewService.getAllunanswered7(enqueteId);
		//DEOTALLUNS=モック左側未回答者
		model.addAttribute("DEPTALLUNANS", allUnansList);








		//出来た
		//Dao,Servvice⑧ ④の部署名を追加したバージョン
		List<Dept> deptList=enqueteAnswerStatusViewService.getEnqueteAnsDeptList8(enqueteId);
		//DEPTLIST=部署ID,部署名格納中
		model.addAttribute("DEPTLIST", deptList);

		//出来た
		//Dao,Service⑨ アンケートIDを取得しアンケート名取得
		String enqName=enqueteAnswerStatusViewService.sgetEnqueteName9(enqueteId);
		model.addAttribute("ENQNAME", enqName);

		//アンケート回答者状況ページに遷移
		return "status/V204_01EnqueteAnswerStatusView";
	}
}


//	//アンケート回答者状況画面を表示
//	@RequestMapping(value="",method = RequestMethod.GET)
//	public String answerStatus(Model model) {
//		Integer enqueteId = 1;   Integer deptId=null;
//
//		//Dao,Service①アンケート対象部署に所属する未回答者をすべて返す
//		List<AnswerStatus> answerStatusList=enqueteAnswerStatusViewService.getUnsanswerdList1(enqueteId);
//		//UNANSWERD=未回答者リスト
//		model.addAttribute("UNANSWERD",answerStatusList);
//
//		//Dao,Service②アンケート対象部署未回答者の総数
//		Integer allunans=enqueteAnswerStatusViewService.getAllunans2(enqueteId);
//		//ALLUNANS=アンケート対象部署の未回答者の総数
//		model.addAttribute("ALLUNANS",allunans);
//
//		//Dao,Service③アンケート対象部署に所属しているすべてのユーザー総数
//		Integer alluser=enqueteAnswerStatusViewService.getAlluser3(enqueteId);
//		//ALLUSER=アンケート対象部署に所属するユーザー数
//		model.addAttribute("ALLUSER",alluser);
//
//		//Dao,Service④アンケート対象部署のIDリスト
//		List<Integer> deptIdList=enqueteAnswerStatusViewService.getTergetDepartmentList4(enqueteId);
//		//TERGETDEPARTMENT=アンケート対象部署のIDリスト
//		model.addAttribute("TERGETDEPARTMENT", deptIdList);
//
//		//Dao,Service⑤指定部署の未回答者人数
//		Integer unans=enqueteAnswerStatusViewService.getUnans5(enqueteId,deptId);
//		//UNANS=部署ごとのアンケート未回答者の人数
//		model.addAttribute("UNANS", unans);
//
//		//Dao,Service⑥部署ごとの所属人数
//		Integer deptAllUser=enqueteAnswerStatusViewService.getUser6(enqueteId);
//		//user=部署ごとの所属している人数
//		model.addAttribute("USER", deptAllUser);
//
//		//Dao,Service⑦ (画面モック)未回答者の左側の情報表示
//		List<AnswerStatus> allUnansList=enqueteAnswerStatusViewService.getAllunanswered7(enqueteId);
//		//DEOTALLUNS=モック左側未回答者
//		model.addAttribute("DEPTALLUNANS", allUnansList);
//
//		//Dao,Servvice⑧ ④の部署名を追加したバージョン
//		List<Dept> deptList=enqueteAnswerStatusViewService.getEnqueteAnsDeptList8(enqueteId);
//		//DEPTLIST=部署ID,部署名格納中
//		model.addAttribute("DEPTLIST", deptList);
//
//		//Dao,Service⑨ アンケートIDを取得しアンケート名取得
//		String enqName=enqueteAnswerStatusViewService.sgetEnqueteName9(enqueteId);
//		model.addAttribute("ENQNAME", enqName);
//
//		//アンケート回答者状況ページに遷移
//		return "status/V204_01EnqueteAnswerStatusView";
//
//	}
//}
//
//////未回答者リスト
////List<AnswerStatus> answerStatusList=enqueteAnswerStatusViewService.getUnsanswerdList(enqueteId);
////model.addAttribute("UNANSWERD",answerStatusList);
////
//////アンケート対象部署に所属しているすべてのユーザー総数
////Integer alluser=enqueteAnswerStatusViewService.getAlluser(enqueteId);
////model.addAttribute("ALLUSER",alluser);
////
//////アンケート対象部署未回答者の総数
////Integer allunans=enqueteAnswerStatusViewService.getAllunans(enqueteId);
////model.addAttribute("ALLUNANS",allunans);
////
//////アンケート対象部署のIDリスト
////List<Integer> deptIdList=enqueteAnswerStatusViewService.getTergetDepartmentList(enqueteId);
////model.addAttribute("TERGETDEPARTMENT", deptIdList);
//
////////部署ごと未回答者人数
//////Integer unans=enqueteAnswerStatusViewService.getUnans(enqueteId, enqueteAnswerStatusViewService.getTergetDepartmentList(enqueteId));;
//////model.addAttribute("UNANS", unans);
//////
//
//
////////部署ごとの所属人数
//////Integer user=enqueteAnswerStatusViewService.getUser(enqueteAnswerStatusViewService.getTergetDepartmentList(enqueteId));
//////model.addAttribute("USER",user);