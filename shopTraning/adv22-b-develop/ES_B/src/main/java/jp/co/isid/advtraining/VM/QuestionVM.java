package jp.co.isid.advtraining.VM;



import java.util.List;

import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.Question;

public class QuestionVM {

	private Question question;
	private List<Choice> choiceList;

	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<Choice> getChoiceList() {
		return choiceList;
	}
	public void setChoiceList(List<Choice> choiceList) {
		this.choiceList = choiceList;
	}

}
