package jp.co.isid.advtraining.VM;

import java.util.ArrayList;
import java.util.List;

import jp.co.isid.advtraining.entity.Dept;
import jp.co.isid.advtraining.entity.QuestionType;

public class EnqueteEditVM {
	private List<Dept> deptList;
	private List<QuestionType> questionTypeList;

	public EnqueteEditVM() {
		this.deptList=new ArrayList<>();
		this.questionTypeList=new ArrayList<>();
	}

	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public List<QuestionType> getQuestionTypeList() {
		return questionTypeList;
	}
	public void setQuestionTypeList(List<QuestionType> questionTypeList) {
		this.questionTypeList = questionTypeList;
	}
}
