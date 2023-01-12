package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAnswer;

public class ResultEnqueteVM {

	private Enquete enquete;
	private EnqueteAnswer enqueteAnswer;
	private List<ResultQuestionVM> resultQuestionVMList;
	private String  userName;

	public Enquete getEnquete() {
		return enquete;
	}
	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}
	public EnqueteAnswer getEnqueteAnswer() {
		return enqueteAnswer;
	}
	public void setEnqueteAnswer(EnqueteAnswer enqueteAnswer) {
		this.enqueteAnswer = enqueteAnswer;
	}
	public List<ResultQuestionVM> getResultQuestionVMList() {
		return resultQuestionVMList;
	}
	public void setResultQuestionVMList(List<ResultQuestionVM> resultQuestionVMList) {
		this.resultQuestionVMList = resultQuestionVMList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}





}
