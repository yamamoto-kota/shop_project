package jp.co.isid.advtraining.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.Form.EnqueteAnswerForm;
import jp.co.isid.advtraining.Form.QuestionAnswerForm;
import jp.co.isid.advtraining.VM.EnqueteAnswerVM;
import jp.co.isid.advtraining.VM.EnqueteVM;
import jp.co.isid.advtraining.VM.QuestionAnswerVM;
import jp.co.isid.advtraining.VM.QuestionVM;
import jp.co.isid.advtraining.answer.EnqueteAnswerService;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAnswer;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteAnswerServiceTest {

	@Autowired
	private EnqueteAnswerService enqueteAnswerService;

	@Test
	@Order(1)
	@DisplayName("EnqueteVMの取得")
	public void testService000_getEnqueteVM() {
		//引数
		EnqueteVM test = new EnqueteVM();
		test = enqueteAnswerService.getEnqueteVM(1);

		//検証内容
		Enquete enquete = new Enquete();
		enquete.setEnqueteId(1);
		enquete.setEnqueteName("2016年度 定期健診・人間ドック 受診前問診票");
		enquete.setEnqueteStateId(3);
		enquete.setCreateUserId("li9010");
		enquete.setCreateDate(LocalDate.of(2016,07,11));
		enquete.setStartDate(LocalDate.of(2016,07,12));
		enquete.setFinishDate(null);
		enquete.setEnqueteSubtext(null);

		Question question = new Question();
		question.setQuestionId(3);
		question.setEnqueteId(1);
		question.setQuestionNumber(3);
		question.setQuestionTypeId(3);
		question.setRequireFlag(0);
		question.setQuestionText("質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。");
		question.setQuestionSubtext(null);
		question.setVersion(1);

		Choice choice = new Choice();
		choice.setChoiceId(9);
		choice.setQuestionId(2);
		choice.setChoiceNumber(6);
		choice.setChoiceText("ダイエットをしているから");
		choice.setVersion(1);

		assertEquals(test.getEnquete().getEnqueteId(),enquete.getEnqueteId());
		assertEquals(test.getEnquete().getEnqueteName(), enquete.getEnqueteName());
		assertEquals(test.getEnquete().getEnqueteStateId(), enquete.getEnqueteStateId());
		assertEquals(test.getEnquete().getCreateUserId(), enquete.getCreateUserId());
		assertEquals(test.getEnquete().getCreateDate(), enquete.getCreateDate());
		assertEquals(test.getEnquete().getStartDate(), enquete.getStartDate());
		assertEquals(test.getEnquete().getFinishDate(), enquete.getFinishDate());
		assertEquals(test.getEnquete().getEnqueteSubtext(), enquete.getEnqueteSubtext());

		assertEquals(test.getQuestionVMList().get(2).getQuestion().getQuestionId(), question.getQuestionId());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getEnqueteId(), question.getEnqueteId());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getQuestionNumber(), question.getQuestionNumber());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getQuestionTypeId(), question.getQuestionTypeId());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getRequireFlag(), question.getRequireFlag());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getQuestionText(), question.getQuestionText());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getQuestionSubtext(), question.getQuestionSubtext());
		assertEquals(test.getQuestionVMList().get(2).getQuestion().getVersion(), question.getVersion());

		assertEquals(test.getQuestionVMList().get(1).getChoiceList().get(5).getChoiceId(), choice.getChoiceId());
		assertEquals(test.getQuestionVMList().get(1).getChoiceList().get(5).getQuestionId(), choice.getQuestionId());
		assertEquals(test.getQuestionVMList().get(1).getChoiceList().get(5).getChoiceNumber(), choice.getChoiceNumber());
		assertEquals(test.getQuestionVMList().get(1).getChoiceList().get(5).getChoiceText(), choice.getChoiceText());
		assertEquals(test.getQuestionVMList().get(1).getChoiceList().get(5).getVersion(), choice.getVersion());
	}

	@Test
	@Order(2)
	@DisplayName("EnqueteVMの取得(存在しないenqueteId)")
	public void testService001_getEnqueteVM() {
		//引数
		EnqueteVM test = new EnqueteVM();
		test = enqueteAnswerService.getEnqueteVM(1000);

		//検証内容
		assertEquals(test,null);
	}

	@Test
	@Order(3)
	@DisplayName("EnqueteAnswerVMの取得")
	public void testService002_getEnqueteAnswerVM() {
		//引数
		EnqueteAnswerVM test = new EnqueteAnswerVM();
		test = enqueteAnswerService.getEnqueteAnswerVM(1,"li9011");

		//検証内容
		EnqueteAnswer enqueteAnswer = new EnqueteAnswer();
		enqueteAnswer.setEnqueteAnswerId(1);
		enqueteAnswer.setEnqueteId(1);
		enqueteAnswer.setEsqId("li9011");
		enqueteAnswer.setAnswerDate(LocalDate.of(2014, 07, 11));

		QuestionAnswer questionAnswer = new QuestionAnswer();
		questionAnswer.setQuestionAnswerId(3);
		questionAnswer.setEnqueteAnswerId(1);
		questionAnswer.setQuestionId(3);
		questionAnswer.setAnswerText("昼まで起きないから");

		ChoiceAnswer choiceAnswer = new ChoiceAnswer();
		choiceAnswer.setChoiceAnswerId(7);
		choiceAnswer.setQuestionAnswerId(2);
		choiceAnswer.setChoiceId(9);

		assertEquals(test.getEnqueteAnswer().getEnqueteAnswerId(),enqueteAnswer.getEnqueteAnswerId());
		assertEquals(test.getEnqueteAnswer().getEnqueteId(), enqueteAnswer.getEnqueteId());
		assertEquals(test.getEnqueteAnswer().getEsqId(), enqueteAnswer.getEsqId());
		assertEquals(test.getEnqueteAnswer().getAnswerDate(), enqueteAnswer.getAnswerDate());

		assertEquals(test.getQuestionAnswerVMList().get(2).getQuestionAnswer().getQuestionAnswerId(), questionAnswer.getQuestionAnswerId());
		assertEquals(test.getQuestionAnswerVMList().get(2).getQuestionAnswer().getEnqueteAnswerId(), questionAnswer.getEnqueteAnswerId());
		assertEquals(test.getQuestionAnswerVMList().get(2).getQuestionAnswer().getQuestionId(), questionAnswer.getQuestionId());
		assertEquals(test.getQuestionAnswerVMList().get(2).getQuestionAnswer().getAnswerText(), questionAnswer.getAnswerText());

		assertEquals(test.getQuestionAnswerVMList().get(1).getChoiceAnswerList().get(5).getChoiceAnswerId(), choiceAnswer.getChoiceAnswerId());
		assertEquals(test.getQuestionAnswerVMList().get(1).getChoiceAnswerList().get(5).getQuestionAnswerId(), choiceAnswer.getQuestionAnswerId());
		assertEquals(test.getQuestionAnswerVMList().get(1).getChoiceAnswerList().get(5).getChoiceId(), choiceAnswer.getChoiceId());

	}

	@Test
	@Order(4)
	@DisplayName("EnqueteAnswerVMの取得(存在しないenqueteId)")
	public void testService003_getEnqueteAnswerVM() {
		//引数
		EnqueteAnswerVM test = new EnqueteAnswerVM();
		test = enqueteAnswerService.getEnqueteAnswerVM(1000,"li9011");

		//検証内容
		assertEquals(test,null);
	}

	@Test
	@Order(6)
	@DisplayName("DBに回答情報を挿入")
	public void testService005_insertAnswer() {

		//引数
		EnqueteAnswerForm enqueteAnswerForm = new EnqueteAnswerForm();
		List<QuestionAnswerForm> questionAnswerFormList = new ArrayList<>();

		List<Integer> choiceIdList = new ArrayList<>();
		choiceIdList.add(2);
		QuestionAnswerForm questionAnswerForm = new QuestionAnswerForm();
		questionAnswerForm.setChoiceIdList(choiceIdList);
		questionAnswerFormList.add(0,questionAnswerForm);

		choiceIdList = new ArrayList<>();
		choiceIdList.add(3);
		choiceIdList.add(7);
		questionAnswerForm = new QuestionAnswerForm();
		questionAnswerForm.setChoiceIdList(choiceIdList);
		questionAnswerFormList.add(1, questionAnswerForm);

		questionAnswerForm = new QuestionAnswerForm();
		questionAnswerForm.setAnswerText("金銭的な理由から");
		questionAnswerFormList.add(2, questionAnswerForm);

		enqueteAnswerForm.setQuestionAnswerFormList(questionAnswerFormList);


		//<EnqueteVM>
		//question[0]
		Question question = new Question();
		question.setQuestionId(1);
		question.setEnqueteId(1);
		question.setQuestionNumber(1);
		question.setQuestionTypeId(1);
		question.setRequireFlag(1);
		question.setQuestionText("朝食を食べていますか。");
		question.setQuestionSubtext(null);
		question.setVersion(1);
		QuestionVM questionVM = new QuestionVM();
		questionVM.setQuestion(question);

		//questionVM[0]-ChoiceList
		//質問項目[0]
		Choice choice = new Choice();
		choice.setChoiceId(1);
		choice.setQuestionId(1);
		choice.setChoiceNumber(1);
		choice.setChoiceText("ほぼ毎日食べている");
		choice.setVersion(1);
		List<Choice> choiceList = new ArrayList<>();
		choiceList.add(choice);

		//質問項目[1]
		choice = new Choice();
		choice.setChoiceId(2);
		choice.setQuestionId(1);
		choice.setChoiceNumber(2);
		choice.setChoiceText("ときどき食べている");
		choice.setVersion(1);
		choiceList.add(choice);

		//質問項目[2]
		choice = new Choice();
		choice.setChoiceId(3);
		choice.setQuestionId(1);
		choice.setChoiceNumber(3);
		choice.setChoiceText("ほとんど食べない");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		List<QuestionVM> questionVMList = new ArrayList<>();
		questionVMList.add(questionVM);


		//question[1]
		question = new Question();
		question.setQuestionId(2);
		question.setEnqueteId(1);
		question.setQuestionNumber(2);
		question.setQuestionTypeId(2);
		question.setRequireFlag(0);
		question.setQuestionText("質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。");
		question.setQuestionSubtext(null);
		question.setVersion(1);
		questionVM = new QuestionVM();
		questionVM.setQuestion(question);

		//questionVM[1]-ChoiceList
		//質問項目[0]
		choice = new Choice();
		choice.setChoiceId(4);
		choice.setQuestionId(2);
		choice.setChoiceNumber(1);
		choice.setChoiceText("食べる習慣がないから");
		choice.setVersion(1);
		choiceList = new ArrayList<>();
		choiceList.add(choice);

		//質問項目[1]
		choice = new Choice();
		choice.setChoiceId(5);
		choice.setQuestionId(2);
		choice.setChoiceNumber(2);
		choice.setChoiceText("食べる時間がないから");
		choice.setVersion(1);
		choiceList.add(choice);

		//質問項目[2]
		choice = new Choice();
		choice.setChoiceId(6);
		choice.setQuestionId(2);
		choice.setChoiceNumber(3);
		choice.setChoiceText("お腹が空かないから");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		//質問項目[3]
		choice = new Choice();
		choice.setChoiceId(7);
		choice.setQuestionId(2);
		choice.setChoiceNumber(4);
		choice.setChoiceText("準備するのが面倒だから");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		//質問項目[4]
		choice = new Choice();
		choice.setChoiceId(8);
		choice.setQuestionId(2);
		choice.setChoiceNumber(5);
		choice.setChoiceText("食べないほうが調子が良いから");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		//質問項目[5]
		choice = new Choice();
		choice.setChoiceId(9);
		choice.setQuestionId(2);
		choice.setChoiceNumber(6);
		choice.setChoiceText("ダイエットをしているから");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		//質問項目[6]
		choice = new Choice();
		choice.setChoiceId(10);
		choice.setQuestionId(2);
		choice.setChoiceNumber(7);
		choice.setChoiceText("その他");
		choice.setVersion(1);
		choiceList.add(choice);
		questionVM.setChoiceList(choiceList);

		questionVMList.add(questionVM);

		//question[2]
		question = new Question();
		question.setQuestionId(3);
		question.setEnqueteId(1);
		question.setQuestionNumber(3);
		question.setQuestionTypeId(3);
		question.setRequireFlag(0);
		question.setQuestionText("質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。");
		question.setQuestionSubtext(null);
		question.setVersion(1);
		questionVM = new QuestionVM();
		questionVM.setQuestion(question);

		questionVMList.add(questionVM);


		//enqueteVM
		Enquete enquete = new Enquete();

		enquete.setEnqueteId(1);
		enquete.setEnqueteName("2016年度 定期健診・人間ドック 受診前問診票");
		enquete.setEnqueteStateId(3);
		enquete.setCreateUserId("li9010");
		enquete.setCreateDate(LocalDate.of(2016,07,11));
		enquete.setStartDate(LocalDate.of(2016,07,12));
		enquete.setFinishDate(null);
		enquete.setEnqueteSubtext(null);
		enquete.setVersion(2);


		//EnqueteVM
		EnqueteVM enqueteVM = new EnqueteVM();
		enqueteVM.setEnquete(enquete);
		enqueteVM.setQuestionVMList(questionVMList);

		Integer enqueteId = 1;
		String esqId = "li9013";

		enqueteAnswerService.insertAnswer(enqueteAnswerForm, enqueteVM, enqueteId, esqId);

		//検証内容
		//enquete_answerテーブル assertEqueals( 期待値 , 実測値 )
		EnqueteAnswerVM enqueteAnswerVM = enqueteAnswerService.getEnqueteAnswerVM(1, "li9013");
		//aurtoincのためテストの度に期待値を調整
		assertEquals(22, enqueteAnswerVM.getEnqueteAnswer().getEnqueteAnswerId());
		assertEquals(1, enqueteAnswerVM.getEnqueteAnswer().getEnqueteId());
		assertEquals("li9013", enqueteAnswerVM.getEnqueteAnswer().getEsqId());
		//テスト実施日を記入
		assertEquals(LocalDate.of(2022,9,13), enqueteAnswerVM.getEnqueteAnswer().getAnswerDate());

		//question_answerテーブル
		QuestionAnswerVM questionAnswerVM = enqueteAnswerVM.getQuestionAnswerVMList().get(0);
		assertEquals(37, questionAnswerVM.getQuestionAnswer().getQuestionAnswerId());
		assertEquals(22, questionAnswerVM.getQuestionAnswer().getEnqueteAnswerId());
		assertEquals(1, questionAnswerVM.getQuestionAnswer().getQuestionId());
		assertEquals(null, questionAnswerVM.getQuestionAnswer().getAnswerText());

		questionAnswerVM = enqueteAnswerVM.getQuestionAnswerVMList().get(1);
		assertEquals(38, questionAnswerVM.getQuestionAnswer().getQuestionAnswerId());
		assertEquals(22, questionAnswerVM.getQuestionAnswer().getEnqueteAnswerId());
		assertEquals(2, questionAnswerVM.getQuestionAnswer().getQuestionId());
		assertEquals(null, questionAnswerVM.getQuestionAnswer().getAnswerText());

		questionAnswerVM = enqueteAnswerVM.getQuestionAnswerVMList().get(2);
		assertEquals(39, questionAnswerVM.getQuestionAnswer().getQuestionAnswerId());
		assertEquals(22, questionAnswerVM.getQuestionAnswer().getEnqueteAnswerId());
		assertEquals(3, questionAnswerVM.getQuestionAnswer().getQuestionId());
		assertEquals("金銭的な理由から", questionAnswerVM.getQuestionAnswer().getAnswerText());


		//choice_answerテーブル
		//choice_answer[0]
		ChoiceAnswer choiceAnswer = enqueteAnswerVM.getQuestionAnswerVMList().get(0).getChoiceAnswerList().get(0);
		assertEquals(42, choiceAnswer.getChoiceAnswerId());
		assertEquals(37, choiceAnswer.getQuestionAnswerId());
		assertEquals(2, choiceAnswer.getChoiceId());

		//choice_answer[1]
		choiceAnswer = enqueteAnswerVM.getQuestionAnswerVMList().get(1).getChoiceAnswerList().get(0);
		assertEquals(43, choiceAnswer.getChoiceAnswerId());
		assertEquals(38, choiceAnswer.getQuestionAnswerId());
		assertEquals(3, choiceAnswer.getChoiceId());

		//choice_answer[2]
		choiceAnswer = enqueteAnswerVM.getQuestionAnswerVMList().get(1).getChoiceAnswerList().get(1);
		assertEquals(44, choiceAnswer.getChoiceAnswerId());
		assertEquals(38, choiceAnswer.getQuestionAnswerId());
		assertEquals(7, choiceAnswer.getChoiceId());
	}

}
