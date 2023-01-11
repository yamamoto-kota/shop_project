package jp.co.isid.advtraining.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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

import jp.co.isid.advtraining.VM.EnqueteResultAllVM;
import jp.co.isid.advtraining.VM.EnqueteVM;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.dao.EnqueteResultAllDao;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.resultAll.EnqueteResultAllService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteResultAllServiceTest {

	@Autowired
	EnqueteResultAllService enqueteResultAllService;

	@Autowired
	EnqueteResultAllDao enqueteResultAllDao;

	@Autowired
	EnqueteDao enqueteDao;




	Integer enqueteId=1;
	String esqId="li9010";
	String enqeteName = "2016年度 定期健診・人間ドック 受診前問診票";



	@Test
	@Order(1)
	@DisplayName("引数によってアンケートの集計結果が返ってくる")
	public void testService000_getEnqueteResultAllVM() {
		Integer enqueteId=2;
		EnqueteResultAllVM enqueteResultAllVM=enqueteResultAllService.getEnqueteResultAllVM(enqueteId);

	assertThat(enqueteResultAllVM.getEnqueteVM().getEnquete().getEnqueteName(),is(enqeteName));

	assertThat(enqueteResultAllVM.getRespondentList().get(0).getUserName(),is("須賀尚紀"));
	assertThat(enqueteResultAllVM.getRespondentList().get(0).getEsqId(),is("li9010"));
	assertThat(enqueteResultAllVM.getRespondentList().get(0).getDeptName() ,is("監査室"));
	assertThat(enqueteResultAllVM.getRespondentList().get(1).getUserName(),is("ジョンスミス"));
	assertThat(enqueteResultAllVM.getRespondentList().get(1).getEsqId(),is("li9011"));
	assertThat(enqueteResultAllVM.getRespondentList().get(1).getDeptName() ,is("監査室"));
	assertThat(enqueteResultAllVM.getRespondentList().get(2).getUserName(),is("川嶋英雄"));
	assertThat(enqueteResultAllVM.getRespondentList().get(2).getEsqId(),is("li9012"));
	assertThat(enqueteResultAllVM.getRespondentList().get(2).getDeptName() ,is("監査室"));



	//質問1
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getQuestion().getQuestionNumber() ,is(1));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getQuestion().getQuestionText() ,is("朝食,を食べていますか。"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getQuestion().getQuestionSubtext() ,is("今朝はトマトスープを食べました"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getQuestion().getQuestionTypeId() ,is(1));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getQuestion().getRequireFlag() ,is(1));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getChoiceList().get(0).getChoiceText() ,is("ほぼ毎日食べている"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getChoiceList().get(1).getChoiceText() ,is("ときどき食べている"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(0).getChoiceList().get(2).getChoiceText() ,is("ほとんど食べない"));

	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getQuestion().getQuestionNumber() ,is(2));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getQuestion().getQuestionText() ,is("質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getQuestion().getQuestionTypeId() ,is(2));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getQuestion().getRequireFlag() ,is(0));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(0).getChoiceText() ,is("食べる習慣がないから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(1).getChoiceText() ,is("食べる時間がないから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(2).getChoiceText() ,is("おなかが空かないから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(3).getChoiceText() ,is("準備するのが面倒だから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(4).getChoiceText() ,is("食べないほうが調子が良いから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(5).getChoiceText() ,is("ダイエットをしているから"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(1).getChoiceList().get(6).getChoiceText() ,is("その他"));

	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(2).getQuestion().getQuestionNumber() ,is(3));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(2).getQuestion().getQuestionText() ,is("質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。"));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(2).getQuestion().getQuestionTypeId() ,is(3));
	assertThat(enqueteResultAllVM.getEnqueteVM().getQuestionVMList().get(2).getQuestion().getRequireFlag() ,is(0));
	}


	@Test
	@Order(2)
	@DisplayName("受けとったアンケートIDに対応するアンケートが存在しないため空のリストが返る。")
	public void testService001_getEnqueteResultAllVM() {
		Integer enqueteId=100;
		EnqueteResultAllVM enqueteResultAllVM=enqueteResultAllService.getEnqueteResultAllVM(enqueteId);

		List<AnswerStatus> respomdent = enqueteResultAllVM.getRespondentList();
		assertThat(respomdent,is(empty()));

		EnqueteVM eVM = enqueteResultAllVM.getEnqueteVM();
		assertThat(eVM, is(nullValue()));

	}



	@Test
	@Order(3)
	@DisplayName("排他制御されているとき、nullを返す。")
	public void testService002_checkAdmin() {
	 int enq =enqueteResultAllService.checkAdmin(1, "li9010");

	       assertThat(enq,is(0));

	   }



	@Test
	@Order(4)
	@DisplayName("管理者権限が剝奪されている場合、エラーメッセージを返す。")
	public void testService003_checkAdmin() {

		int enq=enqueteResultAllService.checkAdmin(1, "li9012");
	        assertThat(enq, is(2));
	    }


	@Test
	@Order(5)
	@DisplayName("引数によってファイル名とアンケート結果が返ってくる")
	public void testService004_csvDownload() {
	Integer enqueteId=2;
	List<String> enq= enqueteResultAllService.csvDownload(enqueteId);

	assertThat(enq.get(0),is("2016年度定期健診・人間ドック受診前問診票"));
	assertThat(enq.get(1),is("ユーザー名,あなたの名前は何ですか？,\"朝食,を食べていますか。(今朝はトマトスープを食べました)(1:ほぼ毎日食べている、2:ときどき食べている、3:ほとんど食べない)\",\"質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。(1:食べる習慣がないから、2:食べる時間がないから、3:おなかが空かないから、4:準備するのが面倒だから、5:食べないほうが調子が良いから、6:ダイエットをしているから、7:その他)\",\"質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。\",\nli9010,須賀尚紀,1 ,,,\nli9011,ジョンスミス,3 ,1 2 3 4 5 6 7 ,\"昼まで起きないから\"\nli9012,川嶋英雄,3 ,1 2 3 4 5 6 ,,\nli9026,藤井信生,3 ,1 ,,\n"));
	}




	@Test
	@Order(6)
	@DisplayName("受けとったアンケートIDに対応するアンケートが存在しないため空のリストが返る。")
	public void testService005_csvDownload() {
	Integer enqueteId=100;
	List<String> enq= enqueteResultAllService.csvDownload(enqueteId);


	assertThat(enq, is(empty()));
	}

	@Test
	@Order(7)
	@DisplayName("引数によってファイル名とアンケート結果が返ってくる")
	public void testService006_csvDownload() {
	Integer enqueteId=5;
	List<String> enq= enqueteResultAllService.csvDownload(enqueteId);

	assertThat(enq.get(0),is("情報セキュリティに関する緊急意識調査"));
	assertThat(enq.get(1),is("ユーザー名,あなたの名前は何ですか？,\"情報区分について認識していますか。(極秘、秘、部内秘、社外秘の区分について説明出来ますか。)(1:完璧に理解している、2:ある程度理解している、3:そんな区分があることは知らない)\",\n"));
	}




}
