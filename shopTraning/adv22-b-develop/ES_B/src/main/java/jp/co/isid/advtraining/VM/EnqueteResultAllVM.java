package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.AnswerStatus;

public class EnqueteResultAllVM {

	private EnqueteVM enqueteVM;
	private List<AnswerStatus> respondentList;

	public EnqueteVM getEnqueteVM() {
		return enqueteVM;
	}
	public void setEnqueteVM(EnqueteVM enqueteVM) {
		this.enqueteVM = enqueteVM;
	}
	public List<AnswerStatus> getRespondentList() {
		return respondentList;
	}
	public void setRespondentList(List<AnswerStatus> respondentList) {
		this.respondentList = respondentList;
	}

}
