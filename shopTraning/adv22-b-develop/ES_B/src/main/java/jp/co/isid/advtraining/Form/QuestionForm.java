package jp.co.isid.advtraining.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer questionId;
	private Integer enqueteId;
	private Integer questionNumber;
	private Integer questionTypeId;
	private Integer requireFlag;
	private String questionText;
	private String questionSubtext;
	private List<ChoiceForm> choiceFormList;

	public QuestionForm() {
		this.choiceFormList = new ArrayList<ChoiceForm>();
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getEnqueteId() {
		return enqueteId;
	}

	public void setEnqueteId(Integer enqueteId) {
		this.enqueteId = enqueteId;
	}

	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {//
		this.questionTypeId = questionTypeId;
	}

	public Integer getRequireFlag() {
		return requireFlag;
	}

	public void setRequireFlag(Integer requireFlag) {
		this.requireFlag = requireFlag;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionSubtext() {
		return questionSubtext;
	}

	public void setQuestionSubtext(String questionSubtext) {
		this.questionSubtext = questionSubtext;
	}

	public List<ChoiceForm> getChoiceFormList() {
		return choiceFormList;
	}

	public void setChoiceFormList(List<ChoiceForm> choiceFormList) {
		this.choiceFormList = choiceFormList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}