package jp.co.isid.advtraining.answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.Form.EnqueteAnswerForm;
import jp.co.isid.advtraining.Form.QuestionAnswerForm;
import jp.co.isid.advtraining.VM.EnqueteAnswerVM;
import jp.co.isid.advtraining.VM.EnqueteVM;
import jp.co.isid.advtraining.VM.QuestionAnswerVM;
import jp.co.isid.advtraining.VM.QuestionVM;
import jp.co.isid.advtraining.dao.ChoiceAnswerDao;
import jp.co.isid.advtraining.dao.EnqueteAnswerDao;
import jp.co.isid.advtraining.dao.EnqueteDeptDao;
import jp.co.isid.advtraining.dao.QuestionAnswerDao;
import jp.co.isid.advtraining.dao.QuestionTypeDao;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAnswer;
import jp.co.isid.advtraining.entity.EnqueteDept;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;

@Service
public class EnqueteAnswerService {

	@Autowired
	EnqueteAnswerDao enqueteAnswerDao;

	@Autowired
	QuestionAnswerDao questionAnswerDao;

	@Autowired
	ChoiceAnswerDao choiceAnswerDao;

	@Autowired
	QuestionTypeDao questionTypeDao;

	@Autowired
	EnqueteDeptDao enqueteDeptDao;

	public int adminUser(Integer enqueteId, Integer deptId) {

		EnqueteDept enqueteDept = enqueteDeptDao.selectById(enqueteId, deptId);

		//アンケート回答対象の事業部でない場合は1を返す
		if(enqueteDept == null) {
			return 1;
		}
		return 0;
	}

	/* アンケートIDを元にEnqueteVMを返却する */
	public EnqueteVM getEnqueteVM(Integer enqueteId) {

		// enqueteObject生成
		EnqueteVM enqueteVM = new EnqueteVM();

		// アンケート情報の取得
		Enquete enquete = enqueteAnswerDao.selectEnqueteByEnqueteId(enqueteId);
		// エラー対策
		if (enquete == null) {
			return null;
		}
		enqueteVM.setEnquete(enquete);

		// Questionの全要素の取得
		List<Question> questionList = enqueteAnswerDao.selectQuestionByEnqueteId(enqueteId);

		List<QuestionVM> questionVMList = new ArrayList<QuestionVM>();

		// questionId=i
		for (int i = 0; i < questionList.size(); i++) {
			// 質問内容の取得とVM格納
			QuestionVM questionVM = new QuestionVM();
			Question question = questionList.get(i);
			questionVM.setQuestion(question);

			if (question.getQuestionTypeId() != 3) {
				// 質問項目の取得(質問番号依存)とVM格納
				List<Choice> choiceList = enqueteAnswerDao.selectChoiceByQuestionId(question.getQuestionId());
				questionVM.setChoiceList(choiceList);

			}

			// i番目の質問内容の質問項目のVM格納
			questionVMList.add(questionVM);
		}

		enqueteVM.setQuestionVMList(questionVMList);

		// enqueteVMを返却
		return enqueteVM;
	}

	/*
	 * アンケートIDとESQIDを元にEnqueteAnswerVMを返却する 回答参照時に使用
	 */
	public EnqueteAnswerVM getEnqueteAnswerVM(Integer enqueteId, String esqId) {

		EnqueteAnswerVM enqueteAnswerVM = new EnqueteAnswerVM();
		EnqueteAnswer enqueteAnswer = enqueteAnswerDao.selectEnqueteAnswerByEnqueteIdAndEsqId(enqueteId, esqId);

		// エラー対策
		if (enqueteAnswer == null) { return null; }

		enqueteAnswerVM.setEnqueteAnswer(enqueteAnswer);

		// QuestionAnswerの要素取得
		Integer enqueteAnswerId = enqueteAnswerVM.getEnqueteAnswer().getEnqueteAnswerId();
		List<QuestionAnswer> questionAnswerList = enqueteAnswerDao.selectQuestionAnswerByEnqueteAnswerId(enqueteAnswerId);

		List<QuestionAnswerVM> questionAnswerVMList = new ArrayList<QuestionAnswerVM>();

		// questionAnswerVMの内容の取得
		for (int i = 0; i < questionAnswerList.size(); i++) {
			QuestionAnswerVM questionAnswerVM = new QuestionAnswerVM();
			QuestionAnswer questionAnswer = questionAnswerList.get(i);
			questionAnswerVM.setQuestionAnswer(questionAnswer);

			List<ChoiceAnswer> choiceAnswerList = enqueteAnswerDao
					.selectChoiceByQuestionAnswerId(questionAnswer.getQuestionAnswerId());
			questionAnswerVM.setChoiceAnswerList(choiceAnswerList);
			questionAnswerVMList.add(questionAnswerVM);
		}

		enqueteAnswerVM.setQuestionAnswerVMList(questionAnswerVMList);

		return enqueteAnswerVM;
	}

	/**
	 *
	 * @param enqueteAnswerForm
	 * @param enqueteVM
	 * @param enqueteId
	 */
	// String esqId
	public void insertAnswer(EnqueteAnswerForm enqueteAnswerForm, EnqueteVM enqueteVM, Integer enqueteId, String esqId) {

		//enquete_answerテーブルへの挿入
		EnqueteAnswer enqueteAnswer = new EnqueteAnswer();
		enqueteAnswer.setEnqueteId(enqueteId);
		enqueteAnswer.setEsqId(esqId);
		enqueteAnswer.setAnswerDate(LocalDate.now());

		// 取得したenqueteAnswerをDBに格納
		enqueteAnswerDao.insert(enqueteAnswer);

		//quesion_answerテーブルへの挿入
		for(int i=0; i<enqueteAnswerForm.getQuestionAnswerFormList().size(); i++) {
			QuestionAnswer questionAnswer = new QuestionAnswer();
			questionAnswer.setEnqueteAnswerId(enqueteAnswer.getEnqueteAnswerId());
			//questionAnswer.setQuestionId(enqueteAnswerForm.getQuestionAnswerFormList().get(i).getQuestionId());
			questionAnswer.setQuestionId(enqueteVM.getQuestionVMList().get(i).getQuestion().getQuestionId());


			//【自由記述】の場合question_answerテーブルにanswerTextを挿入
			int questionTypeId = enqueteVM.getQuestionVMList().get(i).getQuestion().getQuestionTypeId();
				if(questionTypeId == 3) {
					questionAnswer.setAnswerText(enqueteAnswerForm.getQuestionAnswerFormList().get(i).getAnswerText());

					// 取得したquestionAnswerをDBに格納
					questionAnswerDao.insert(questionAnswer);

			//【単一選択】【複数選択】
				}else {

					// 取得したquestionAnswerをDBに格納
					questionAnswerDao.insert(questionAnswer);

					//単一選択のエラー対策
					//【単一選択】
					if(!(questionTypeId == 1 && enqueteAnswerForm.getQuestionAnswerFormList().get(i).getChoiceIdList()==null )) {

						for(int j=0; j<enqueteAnswerForm.getQuestionAnswerFormList().get(i).getChoiceIdList().size(); j++) {
							ChoiceAnswer choiceAnswer = new ChoiceAnswer();
							choiceAnswer.setQuestionAnswerId(questionAnswer.getQuestionAnswerId());
							choiceAnswer.setChoiceId(enqueteAnswerForm.getQuestionAnswerFormList().get(i).getChoiceIdList().get(j));

							// 取得したchoiceAnswerをDBに格納
							choiceAnswerDao.insert(choiceAnswer);
						}

					}
				}

		}
	}

}

	/**
	 *
	 * @return questionTypeList
	 */
//	public List<QuestionType> getQuestionType() {
//
//		List<QuestionType> questionTypeList = questionTypeDao.selectAll();
//
//		return questionTypeList;
//	}
