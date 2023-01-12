package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;

public class ResultQuestionVM {


	private Question question;
	private QuestionAnswer questionAnswer;
	private List<ChoiceVM> choiceVMList;
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public QuestionAnswer getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(QuestionAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public List<ChoiceVM> getChoiceVMList() {
		return choiceVMList;
	}
	public void setChoiceVMList(List<ChoiceVM> choiceVMList) {
		this.choiceVMList = choiceVMList;
	}



}
