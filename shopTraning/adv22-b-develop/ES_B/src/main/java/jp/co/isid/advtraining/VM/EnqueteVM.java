package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.Enquete;

public class EnqueteVM {

	public EnqueteVM() {}
	private Enquete enquete;
	private List<QuestionVM> questionVMList;

	public Enquete getEnquete() {
		return enquete;
	}
	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}
	public List<QuestionVM> getQuestionVMList() {
		return questionVMList;
	}
	public void setQuestionVMList(List<QuestionVM> questionVMList) {
		this.questionVMList = questionVMList;
	}


}
