package jp.co.isid.advtraining.answerStatus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.dao.EnqueteAnswerStatusViewDao;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.entity.Dept;

@Service
public class EnqueteAnswerStatusViewService {
	@Autowired
	public EnqueteAnswerStatusViewDao enqueteAnswerStatusViewDao;

	//①アンケート対象部署に所属する未回答者をすべて返す。
	public List<AnswerStatus>getUnsanswerdList1(Integer enqueteId){
		return enqueteAnswerStatusViewDao.selectUnanswered1(enqueteId);
	}

	//②アンケート対象部署の未回答者の総数を返す。
	public Integer getAllunans2(Integer enqueteId) {
		return enqueteAnswerStatusViewDao.selectAllunans2(enqueteId);
	}

	//③アンケート対象部署に所属するユーザーの総数を返す。
	public Integer getAlluser3(Integer enqueteId) {
		return enqueteAnswerStatusViewDao.selectAlluser3(enqueteId);
	}

	//④アンケート対象部署のIDリスト返す。
	public List<Integer>getTergetDepartmentList4(Integer enqueteId){
		return enqueteAnswerStatusViewDao.selectTergetDepartment4(enqueteId);
	}



//	//⑤部署ごとに所属しているアンケート未回答者の総数を返す。
//	public Integer getUnans5(Integer enqueteId ,Integer deptId) {
//		return enqueteAnswerStatusViewDao.selectUnans5(enqueteId, deptId);
//	}
	//⑤部署ごとに所属しているアンケート未回答者の総数を返す。
		public List<Integer> getUnans5(Integer enqueteId) {
			return enqueteAnswerStatusViewDao.selectUnans5(enqueteId);
		}

	//⑥部署ごとに所属しているユーザーの総数を返す。
	public List<Integer> getUser6(Integer enqueteId) {
		Integer count=0;
		List<Integer> deptUserList = new ArrayList<Integer>();

		List<Integer>deptList= this.getTergetDepartmentList4(enqueteId);
			for(Integer deptId:deptList ) {
				count=+enqueteAnswerStatusViewDao.selectUser6(deptId);
				deptUserList.add(count);
			}
		return deptUserList;
	}

	//⑦部署ごとの未回答者の情報を返す
	public List<AnswerStatus> getAllunanswered7(Integer enqueteId){
		//初期化
		List<AnswerStatus> allUnansList=null;
		//部署取得
		List<Integer>deptList= this.getTergetDepartmentList4(enqueteId);
		//各部署ごとの未回答者を格納
		for(Integer deptId:deptList ) {
			allUnansList= enqueteAnswerStatusViewDao.slectAllunanswered7(enqueteId, deptId);
		}
		return allUnansList;
	}

	////⑧アンケート対象部署のIDと部署名リスト返す。
	public List<Dept>getEnqueteAnsDeptList8(Integer enqueteId){
		return enqueteAnswerStatusViewDao.selectEnqueteAnsDeptList8(enqueteId);
	}

	//⑨アンケートIDからアンケート名を返すよ
	public String sgetEnqueteName9(Integer enqueteId) {
		return enqueteAnswerStatusViewDao.selectEnqueteName9(enqueteId);
	}

	}