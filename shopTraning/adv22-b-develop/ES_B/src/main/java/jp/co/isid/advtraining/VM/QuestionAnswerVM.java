package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.QuestionAnswer;



public class QuestionAnswerVM {

	public QuestionAnswerVM() {}

	private QuestionAnswer questionAnswer;
	private List<ChoiceAnswer> choiceAnswerList;


	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(QuestionAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public List<ChoiceAnswer> getChoiceAnswerList() {
		return choiceAnswerList;
	}
	public void setChoiceAnswerList(List<ChoiceAnswer> choiceAnswerList) {
		this.choiceAnswerList = choiceAnswerList;
	}








}