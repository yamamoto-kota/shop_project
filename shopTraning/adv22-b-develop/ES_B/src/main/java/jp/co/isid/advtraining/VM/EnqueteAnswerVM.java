package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.EnqueteAnswer;

public class EnqueteAnswerVM {

	public EnqueteAnswerVM() {}

	private EnqueteAnswer enqueteAnswer;
	private List<QuestionAnswerVM> questionAnswerVMList;
	public EnqueteAnswer getEnqueteAnswer() {
		return enqueteAnswer;
	}
	public void setEnqueteAnswer(EnqueteAnswer enqueteAnswer) {
		this.enqueteAnswer = enqueteAnswer;
	}
	public List<QuestionAnswerVM> getQuestionAnswerVMList() {
		return questionAnswerVMList;
	}
	public void setQuestionAnswerVMList(List<QuestionAnswerVM> questionAnswerVMList) {
		this.questionAnswerVMList = questionAnswerVMList;
	}


	}





