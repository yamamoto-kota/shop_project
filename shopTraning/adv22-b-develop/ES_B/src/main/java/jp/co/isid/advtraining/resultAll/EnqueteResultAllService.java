package jp.co.isid.advtraining.resultAll;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.VM.EnqueteAnswerVM;
import jp.co.isid.advtraining.VM.EnqueteResultAllVM;
import jp.co.isid.advtraining.VM.EnqueteVM;
import jp.co.isid.advtraining.VM.QuestionAnswerVM;
import jp.co.isid.advtraining.VM.QuestionVM;
import jp.co.isid.advtraining.dao.ChoiceDao;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteAnswerDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.dao.EnqueteResultAllDao;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EnqueteAnswer;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;
import jp.co.isid.advtraining.result.EnqueteResultService;




@Service
public class EnqueteResultAllService {


	@Autowired
	EnqueteAdminUserDao enqueteAdminUserDao;

	@Autowired
	EnqueteResultAllDao enqueteResultAllDao;

	@Autowired
	ChoiceDao choiceDao;

	@Autowired
	EnqueteDao enqueteDao;

	@Autowired
	EnqueteAnswerDao enqueteAnswerDao;

	@Autowired
	EnqueteResultService enqueteResultService= new EnqueteResultService();

	EnqueteAnswer enqueteAnswer= new EnqueteAnswer();


	EnqueteAdminUser enqueteAdminUser= new EnqueteAdminUser();


	AnswerStatus answerStatus= new AnswerStatus();


	EnqueteResultAllVM enqueteResultAllVM = new EnqueteResultAllVM();


	EnqueteVM enqueteVM= new EnqueteVM();

	EnqueteAnswerVM enqueteAnswerVM= new EnqueteAnswerVM();





	List<AnswerStatus> respondentList=new ArrayList<AnswerStatus>();


	/* アンケートIDを元にEnqueteVMを返却する */
	private EnqueteVM getEnqueteVM(Integer enqueteId) {

		// enqueteObject生成
		EnqueteVM enqueteVM = new EnqueteVM();

		// アンケート情報の取得
		Enquete enquete = enqueteAnswerDao.selectEnqueteByEnqueteId(enqueteId);
		// エラー対策
		if (enquete == null) {
			return null;
		}
		enqueteVM.setEnquete(enquete);

		  //Questionの全要素の取得
		List<Question> questionList = enqueteAnswerDao.selectQuestionByEnqueteId(enqueteId);

		List<QuestionVM> questionVMList = new ArrayList<QuestionVM>();

		//questionId=i
		  for(int i=0;i< questionList.size();i++) {
			  //質問内容の取得とVM格納
			  QuestionVM questionVM = new QuestionVM();
			  Question question =questionList.get(i);
			  questionVM.setQuestion(question);

			  System.out.println("/////////////////////////////////"+i);

				  //質問項目の取得(質問番号依存)とVM格納
				  List<Choice> choiceList = enqueteAnswerDao.selectChoiceByQuestionId(question.getQuestionId());
				  questionVM.setChoiceList(choiceList);
				  for(int c=0; c<choiceList.size(); c++) {
				  System.out.println(choiceList.get(c).getChoiceText());
				  }

			  //i番目の質問内容の質問項目のVM格納
			  questionVMList.add(questionVM);
		  }

		  enqueteVM.setQuestionVMList(questionVMList);

		// enqueteVMを返却
		return enqueteVM;
	}






	/* アンケートIDとESQIDを元にEnqueteAnswerVMを返却する */
	public EnqueteAnswerVM getEnqueteAnswerVM(Integer enqueteId, String esqId) {

		EnqueteAnswerVM enqueteAnswerVM = new EnqueteAnswerVM();

		EnqueteAnswer enqueteAnswer = enqueteAnswerDao.selectEnqueteAnswerByEnqueteIdAndEsqId(enqueteId, esqId);
		// エラー対策
		if (enqueteAnswer == null) {
			return null;
		}
		enqueteAnswerVM.setEnqueteAnswer(enqueteAnswer);

		// QuestionAnswerの要素取得
		List<QuestionAnswer> questionAnswerList = enqueteAnswerDao.selectQuestionAnswerByEnqueteAnswerId(enqueteAnswer.getEnqueteAnswerId());

		List<QuestionAnswerVM> questionAnswerVMList = new ArrayList<QuestionAnswerVM>();

		// questionAnswerVMの内容の取得
		for (int i = 0; i < questionAnswerList.size(); i++) {
			QuestionAnswerVM questionAnswerVM = new QuestionAnswerVM();
			QuestionAnswer questionAnswer = questionAnswerList.get(i);
			questionAnswerVM.setQuestionAnswer(questionAnswer);

			List<ChoiceAnswer> choiceAnswerList = enqueteAnswerDao.selectChoiceByQuestionAnswerId(questionAnswer.getQuestionAnswerId());
			questionAnswerVM.setChoiceAnswerList(choiceAnswerList);
			questionAnswerVMList.add(questionAnswerVM);
		}

		enqueteAnswerVM.setQuestionAnswerVMList(questionAnswerVMList);

		return enqueteAnswerVM;
	}







	//管理者か否かの判定
	/*	public String checkadmin(Integer enqueteId, String esqId) {
			String result=null;
			enqueteAdminUser=enqueteAdminUserDao.selectById(enqueteId, esqId);
			Integer admin=enqueteAdminUser.getDeleteFlag();
			if(admin==1) {
				result="管理者権限がありません。";
			}

			return result;
		}*/


	public int checkAdmin(Integer enqueteId, String esqId) {
		int z = 0;

		EnqueteAdminUser adminUserID = enqueteAdminUserDao.selectById(enqueteId, esqId);
		if (adminUserID == null) {
			z = 2;
			return z;
		}
		if (adminUserID.getDeleteFlag() != 0) {
			z = 2;
			return z;
		}
		return z;
	}



	public EnqueteResultAllVM getEnqueteResultAllVM(Integer enqueteId) {

		//回答者一覧をVMに格納
		respondentList=enqueteResultAllDao.selectRespondentList(enqueteId);
		enqueteResultAllVM.setRespondentList(respondentList);

		//enqueteVNを格納
		enqueteVM= getEnqueteVM(enqueteId);
		enqueteResultAllVM.setEnqueteVM(enqueteVM);


		return enqueteResultAllVM;
	}


	//csvファイル作成
	private String csvData(Integer enqueteId) {
		StringBuilder csv =new StringBuilder();

		//esqID,名前のラベル出力
		csv.append("\uFEFF");
		csv.append("ESQID"+",");

		//serviceからgeEenqueteVMメソッド呼び出す
			enqueteVM= getEnqueteVM(enqueteId);

			//enquetVMからquestionText取り出す
			List<QuestionVM> questionVMList= new ArrayList<QuestionVM>();
			questionVMList=enqueteVM.getQuestionVMList();

			//質問文書き込み
			for(QuestionVM qVM:questionVMList) {
				csv.append("\"");
				csv.append(qVM.getQuestion().getQuestionText().replace("\"", "\"\""));

				if(qVM.getQuestion().getQuestionSubtext()!=null) {

					csv.append("("+qVM.getQuestion().getQuestionSubtext().replace("\"", "\"\"")+")");

				}


				//選択肢書き込み
				Integer questionType=qVM.getQuestion().getQuestionTypeId();

				//回答が選択式の場合
				int j=0;
				if(questionType!=3) {
					csv.append("(");
				List<Choice> choiceList=new ArrayList<Choice>();
				choiceList=qVM.getChoiceList();
				for(Choice choice:choiceList) {



					//選択肢の番号、および選択肢を書き込み


					csv.append(choice.getChoiceNumber()+":");



					csv.append(choice.getChoiceText().replace("\"", "\"\""));



					j=j+1;
					if(j<choiceList.size())
					csv.append("、");}

				csv.append(")");
				csv.append("\"");
				csv.append(",");
				}

				//回答が自由記述の場合
				else {
					csv.append("\"");
					csv.append(",");

				}

			}
			//ここまででラベル出力完了
			csv.append("\n");


						//ユーザーごとの回答内容出力

							//アンケート回答者のESQIDを取得し、それを引数としてfor文実行
						respondentList=enqueteResultAllDao.selectRespondentList(enqueteId);
						for(AnswerStatus respondent:respondentList) {

							//esqID、ユーザー名出力
							csv.append(respondent.getEsqId()+",");

							//for文の引数に使うesqID取得
							String esqId=respondent.getEsqId();

							//serviceからgeEenqueteAnswerVM(enqueteId,esqid)メソッド呼び出す
							enqueteAnswerVM  = getEnqueteAnswerVM(enqueteId, esqId);

							//enqueteAnaswerVMからQuestionAnswerVMList取り出す
							List<QuestionAnswerVM> questionAnswerVMList=enqueteAnswerVM.getQuestionAnswerVMList();

							//設問の回答内容格納
							for(QuestionAnswerVM questionansVM:questionAnswerVMList) {

								//記述の場合
								if(questionansVM.getQuestionAnswer().getAnswerText() != null) {
									csv.append("\"");
								csv.append(questionansVM.getQuestionAnswer().getAnswerText().replace("\"", "\"\""));
								csv.append("\"");
								csv.append(",");
								}else {

								//選択式の場合
								List<ChoiceAnswer> choiceAnswerList= questionansVM.getChoiceAnswerList();
								for(ChoiceAnswer choiceans:choiceAnswerList) {
									Integer choiceId = choiceans.getChoiceId();
									Integer choiceNum=choiceDao.selectByChoiceNumber(choiceId);
									csv.append(choiceNum+" ");
								}
								csv.append(",");
								}

							}
							csv.append("\n");
						}

		return new String(csv);
	}


	//csvファイルの中身をコントローラーに渡す
	public List<String> csvDownload(Integer enqueteId) {



		//ファイル名、およびファイルの中身を書き込むリスト作成
		List<String> enqueteDownload=new ArrayList<String>();
		Enquete enquete=new Enquete();

		//ファイル名格納
		enquete=enqueteDao.selectById(enqueteId);


		//文字列の置換

		if(enquete!=null) {
		enqueteDownload.add(enquete.getEnqueteName().replace(" ", "").replace("　", ""));

		//文字コード変換
		String csv=csvData(enqueteId);
		try {
			byte[] bytes = csv.getBytes("UTF-8");
			String newcsv = new String(bytes,"UTF-8");

			//アンケート内容格納
			enqueteDownload.add(newcsv);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

System.out.println(enqueteDownload.get(1));
		}
		return enqueteDownload;
	}

}
