package jp.co.isid.advtraining.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnqueteAnswerForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<QuestionAnswerForm> questionAnswerFormList;


	public EnqueteAnswerForm() {
		questionAnswerFormList = new ArrayList<QuestionAnswerForm>();
	}

	public List<QuestionAnswerForm> getQuestionAnswerFormList() {
		return questionAnswerFormList;
	}

	public void setQuestionAnswerFormList(List<QuestionAnswerForm> questionAnswerFormList) {
		this.questionAnswerFormList = questionAnswerFormList;
	}





}