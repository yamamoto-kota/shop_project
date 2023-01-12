package jp.co.isid.advtraining.Form;

import java.util.List;

public class QuestionAnswerForm {

	private List<Integer> choiceIdList;
	private String answerText;
	//エラー番号表示のため
	private Integer questionId;

	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public List<Integer> getChoiceIdList() {
		return choiceIdList;
	}
	public void setChoiceIdList(List<Integer> choiceIdList) {
		this.choiceIdList = choiceIdList;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}



}