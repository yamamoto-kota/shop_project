package jp.co.isid.advtraining.Form;

import java.io.Serializable;

public class ChoiceForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer choiceId;
	private Integer questionId;
	private Integer choiceNumber;
	private String choiceText;

	public Integer getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getChoiceNumber() {
		return choiceNumber;
	}
	public void setChoiceNumber(Integer choiceNumber) {
		this.choiceNumber = choiceNumber;
	}
	public String getChoiceText() {
		return choiceText;
	}
	public void setChoiceText(String choiceText) {
		this.choiceText = choiceText;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}