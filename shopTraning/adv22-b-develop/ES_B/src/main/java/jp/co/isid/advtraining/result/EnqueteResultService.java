package jp.co.isid.advtraining.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.VM.ChoiceVM;
import jp.co.isid.advtraining.VM.ResultEnqueteVM;
import jp.co.isid.advtraining.VM.ResultQuestionVM;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteAnswerDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.dao.EsqUserDao;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EnqueteAnswer;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;



@Service
public class EnqueteResultService {

	@Autowired
	public EnqueteAdminUserDao enqueteAdminUserDao;

	@Autowired
	EnqueteAnswerDao enqueteAnswerDao;

	@Autowired
	EsqUserDao esqUserDao;

	@Autowired
	EnqueteDao enqueteDao;


	/* アンケートIDを元にResultEnqueteVMを返却する */
	public ResultEnqueteVM getResultEnqueteVM(Integer enqueteId,String esqId) {

		// resultnqueteObject生成
		ResultEnqueteVM resultEnqueteVM = new ResultEnqueteVM();


		// アンケート情報の取得
		Enquete enquete = enqueteAnswerDao.selectEnqueteByEnqueteId(enqueteId);

		// エラー対策
		if (enquete == null) {
			return null;
		}
		resultEnqueteVM.setEnquete(enquete);



		//Questionの全要素の取得
		List<Question> questionList = enqueteAnswerDao.selectQuestionByEnqueteId(enqueteId);

		//resultQuestionVMLIstオブジェクト生成
		List<ResultQuestionVM> resultQuestionVMLIst = new ArrayList<ResultQuestionVM>();


		for (int i = 0; i < questionList.size(); i++) {
			//QuestionをresultQuestionVM格納
			ResultQuestionVM resultQuestionVM = new ResultQuestionVM();
			Question question = questionList.get(i);
			resultQuestionVM.setQuestion(question);


				//ChoiceをchoiceVM格納
				List<Choice> choiceList = enqueteAnswerDao.selectChoiceByQuestionId(question.getQuestionId());
				List<ChoiceVM> choiceVMList = new ArrayList<ChoiceVM>() ;
				for (int j = 0; j < choiceList.size(); j++) {
					Choice choice =choiceList.get(j);
					ChoiceVM choiceVM =new ChoiceVM();
					choiceVM.setChoice(choice);
					choiceVMList.add(choiceVM);
				}

				//取得したChoiceVMをChoiceVMListにし、resultQuestionVMに格納
				resultQuestionVM.setChoiceVMList(choiceVMList);


			//取得したQuestionVMを、QuestionVMListに追加
			resultQuestionVMLIst.add(resultQuestionVM);
		}


//		取得したQuestionVMListを、resultEnqueteVMに格納
		resultEnqueteVM.setResultQuestionVMList(resultQuestionVMLIst);


		//対象ユーザーの名前の取得(UserName)
		String userName =esqUserDao.selectById(esqId).getUserName();
		resultEnqueteVM.setUserName(userName);

		//enqueteAnswe取得
		EnqueteAnswer enqueteAnswer =enqueteAnswerDao.selectEnqueteAnswerByEnqueteIdAndEsqId(enqueteId, esqId);

		//questionAnswerList取得
		List<QuestionAnswer> questionAnswerList =enqueteAnswerDao.selectQuestionAnswerByEnqueteAnswerId(enqueteAnswer.getEnqueteAnswerId());

		//choiceAnswerListオブジェクトを生成し、choiceAnswerListを取得
		List<ChoiceAnswer> choiceAnswerList = new ArrayList<ChoiceAnswer>();
		for(int i=0; i<questionAnswerList.size(); i++) {
			choiceAnswerList.addAll(enqueteAnswerDao.selectChoiceByQuestionAnswerId(questionAnswerList.get(i).getQuestionAnswerId()));
		}




// 回答を取得して回答VMに入れる処理→アンケート定義VMに入れる処理に変更

		// questionIdをキーにquestionAnswerをマップ化
		//Map<キーのデータ型, 値のデータ型> マップ名 = new HashMap<>();
		//マップ名.put(キー, 値);

		Map<Integer, QuestionAnswer> questionAnswerMap = new HashMap<>();
		for (int i=0;i<questionAnswerList.size(); i++) {
			QuestionAnswer questionAnswer = questionAnswerList.get(i);
		    questionAnswerMap.put(questionAnswer.getQuestionId(),questionAnswer);
		    }


		// choiceIdをキーにchoiceAnswerをマップ化
		Map<Integer, ChoiceAnswer> choiceAnswerMap = new HashMap<>();
		for (int i=0;i<choiceAnswerList.size(); i++) {
			ChoiceAnswer choiceAnswer = choiceAnswerList.get(i);
			choiceAnswerMap.put(choiceAnswer.getChoiceId(), choiceAnswer);
		}


		// アンケート定義VMに回答情報をセット

		//resultEnqueteVMの中のResultQuestionVMListのi番目を指定
		for (int i=0;i<resultEnqueteVM.getResultQuestionVMList().size(); i++) {
			ResultQuestionVM resultQuestionVM = resultEnqueteVM.getResultQuestionVMList().get(i);

			//回答情報があったらが存在したら、resultQuestionVMにquestionAnswerMapから取得したquestionAnswerをセット
		    if (questionAnswerMap.containsKey(resultQuestionVM.getQuestion().getQuestionId())) {
		    	resultQuestionVM.setQuestionAnswer(questionAnswerMap.get(resultQuestionVM.getQuestion().getQuestionId()));






		    	//resultQuestionVMの中のResultChoiceVMListのi番目を指定
		    	for (int j=0;j< resultQuestionVM.getChoiceVMList().size();j++) {
		    		ChoiceVM choiceVM = resultQuestionVM.getChoiceVMList().get(j);

		    		//回答情報があったらが存在したら、ChoiceVM.にchoiceAnswerMapから取得したchoiceAnswerをセット
		    		 if (choiceAnswerMap.containsKey(choiceVM.getChoice().getChoiceId())) {
		    			choiceVM.setAnswered(true);
		    			 //ChoiceVM.setAnswered(choiceAnswerMap.get(chiceVM.getChoice().getChoiceId()));
		    		 }

		    	}

		    }
		}
//		resultEnqueteVMに返す
		return resultEnqueteVM;

	}


	public int checkAdmin(Integer enqueteId, String esqId) {
		int z = 0;
		Enquete enqueteID = enqueteDao.selectById(enqueteId);
		if (enqueteID == null) {
			z = 1;
			return z;
		}
		EnqueteAdminUser adminUserID = enqueteAdminUserDao.selectById(enqueteId, esqId);
		if (adminUserID.getDeleteFlag() != 0) {
			z = 2;
			return z;
		}
		return z;
	}


}





































