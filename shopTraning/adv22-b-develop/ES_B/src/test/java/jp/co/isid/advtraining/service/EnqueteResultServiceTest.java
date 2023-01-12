package jp.co.isid.advtraining.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.VM.ResultEnqueteVM;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteAnswerDao;
import jp.co.isid.advtraining.dao.EsqUserDao;
import jp.co.isid.advtraining.result.EnqueteResultService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteResultServiceTest {

	@Autowired
	private EnqueteAdminUserDao enqueteAdminUserDao;
	@Autowired
	private EnqueteAnswerDao enqueteAnswerDao;
	@Autowired
	private EsqUserDao esqUserDao;
	@Autowired
	private EnqueteResultService enqueteResultService;


	Integer enqueteId=1;
	String esqId="li9011";


	@Test
	@Order(1)
	@DisplayName("受けとったアンケートIDに対応するアンケート情報が返ってくる場合。")
	public void testService000_getEnqueteResultVM() {
		Integer enqueteId=1;
		String esqId="li9011";
		ResultEnqueteVM resultEnqueteVM =enqueteResultService.getResultEnqueteVM(enqueteId, esqId);

		//題名：アンケート結果（ジョンスミス）
			assertThat(resultEnqueteVM.getUserName(),is("ジョンスミス"));

		//アンケート名
			assertThat(resultEnqueteVM.getEnquete().getEnqueteName(),is("2016年度 定期健診・人間ドック 受診前問診票"));

		//質問1
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(0).getQuestion().getQuestionNumber(),is(1));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(0).getQuestion().getQuestionText(),is("朝食を食べていますか。"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(0).getQuestion().getQuestionTypeId(),is(1));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(0).getQuestion().getRequireFlag(),is(1));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(0).getChoiceVMList().get(2).getChoice().getChoiceText(),is("ほとんど食べない"));


		//質問2
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getQuestion().getQuestionNumber(),is(2));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getQuestion().getQuestionText(),is("質問1で「ときどき食べている」殆ど食べない」と答えた方のみ：あなたが朝食を食べない理由として当てはまるものをすべてチェックしてください。"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getQuestion().getQuestionTypeId(),is(2));;
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(0).getChoice().getChoiceText(),is("食べる習慣がないから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(1).getChoice().getChoiceText(),is("食べる時間がないから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(2).getChoice().getChoiceText(),is("おなかが空かないから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(3).getChoice().getChoiceText(),is("準備するのが面倒だから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(4).getChoice().getChoiceText(),is("食べないほうが調子が良いから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(5).getChoice().getChoiceText(),is("ダイエットをしているから"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(1).getChoiceVMList().get(6).getChoice().getChoiceText(),is("その他"));

		//質問3
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(2).getQuestion().getQuestionNumber(),is(3));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(2).getQuestion().getQuestionText(),is("質問2で「その他」をチェックした方のみ：理由を具体的にお書きください。"));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(2).getQuestion().getQuestionTypeId(),is(3));
			assertThat(resultEnqueteVM.getResultQuestionVMList().get(2).getQuestionAnswer().getAnswerText(),is("昼まで起きないから"));
	}


	@Test
	@Order(2)
	@DisplayName("排他制御されているとき、nullを返す。")
	public void testService001_checkadmin() {
	int enq =enqueteResultService.checkAdmin(1, "li9010");

	       assertThat(enq,is(nullValue()));
	}



	 @Test
	 @Order(3)
	 @DisplayName("管理者権限が剝奪されている場合、エラーメッセージを返す。")
	 public void testService002_checkadmin() {

	 int enq=enqueteResultService.checkAdmin(3, "li9012");
	   	        assertThat(enq, is("管理者権限がありません。"));
	 }


	 @Test
	 @Order(4)
	 @DisplayName("受けとったアンケートIDに対応するアンケートIDが返ってくる場合。")
	 public void testService003_getEnqueteResulVM() {
		 Integer enqueteId=1;
		 String esqId="li9010";
		 ResultEnqueteVM resultEnqueteVM =enqueteResultService.getResultEnqueteVM(enqueteId, esqId);

		 assertThat(resultEnqueteVM.getEnquete().getEnqueteId(),is(1));
	 }

	 @Test
	 @Order(5)
	 @DisplayName("受けとったアンケートIDに対応するアンケートIDが返ってくる場合。")
	 public void testService004_getEnqueteResulVM() {
		 Integer enqueteId=100;
		 String esqId="li9010";
		 ResultEnqueteVM resultEnqueteVM =enqueteResultService.getResultEnqueteVM(enqueteId, esqId);

		 assertThat(resultEnqueteVM,is(nullValue()));
	 }


}
//
//
//
//}

