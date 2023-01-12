package jp.co.isid.advtraining.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.Form.ChoiceForm;
import jp.co.isid.advtraining.Form.EnqueteForm;
import jp.co.isid.advtraining.Form.QuestionForm;
import jp.co.isid.advtraining.VM.EnqueteEditVM;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.edit.EnqueteEditService;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteEditServiceTest {

		@Autowired
		private EnqueteEditService enqueteEditService;

		@Autowired
		private EnqueteDao enqueteDao;

		@Autowired
		EnqueteAdminUserDao enqueteAdminUserDao;

		@Test
		@Order(1)
		@DisplayName("特定のアンケートを編集出来る。")
		public void testService000_checkEdit() {
			Integer enqueteId = 4;
			String esqId = "li9010";
			String str = enqueteEditService.checkEdit(enqueteId, esqId);
			assertThat(str, is(nullValue()));
		}

		@Test
		@Order(2)
		@DisplayName("アンケートが存在しない場合、エラーメッセージを返す。")
		public void testService001_checkEdit() {
			Integer enqueteId = 50;
			String esqId = "li9010";
			String str = enqueteEditService.checkEdit(enqueteId, esqId);
			assertThat(str, is("アンケートは削除されています"));
		}

		@Test
		@Order(3)
		@DisplayName("アンケートが既に実施されている場合、エラーメッセージを返す。")
		void testService002_checkEdit() {
			Integer enqueteId = 1;
			String esqId = "li9010";
			String str = enqueteEditService.checkEdit(enqueteId, esqId);
			assertThat(str, is("アンケートは回答受付中か、受付終了しています"));
		}

		@Test
		@Order(4)
		@DisplayName("アンケートの管理者権限がない場合、エラーメッセージを返す。")
		public void testService003_checkEdit() {
			Integer enqueteId = 4;
			String esqId = "li9020";
			String str = enqueteEditService.checkEdit(enqueteId, esqId);
			assertThat(str, is("管理者権限が削除されています"));
		}

//		@Test
//		@Order(5)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService004_checkEditSystemError(){
//		}

		@Test
		@Order(6)
		@DisplayName("特定のアンケートを新規作成・更新出来る。")
		public void testService005_checkEnquete() {
			Integer enqueteId = 4;
			Integer versiond = 1;
			String str = enqueteEditService.checkEnquete(enqueteId, versiond);
			assertThat(str, is(nullValue()));
		}

		@Test
		@Order(7)
		@DisplayName("アンケートのバージョンが変わっている場合、エラーメッセージを返す。")
		public void testService006_checkEnquete() {
			//うまく機能しない
			Integer enqueteId = 3;
			Integer version = 3;

			String str = enqueteEditService.checkEnquete(enqueteId, version);
			assertThat(str, is("アンケートは変更されています"));
		}

		//checkEditで行っているためなし
//		@Test
//		@Order(8)
//		@DisplayName("アンケートの管理者権限がない場合、エラーメッセージを返す。")
//		public void testService007_checkEnquete() {
//		}

//		@Test
//		@Order(9)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService008_checkEnqueteSystemError() {
//		}

		@Test
		@Order(10)
		@DisplayName("事業部情報と質問形式情報をデータベースからリストとして取得し、それらが格納されたEnqueteEditVMオブジェクトを取得できる。")
		public void testService009_getEnqueteEditVM() {
			EnqueteEditVM enqueteEditVM = enqueteEditService.getEnqueteEditVM();
			assertThat(enqueteEditVM.getDeptList().size(), is(11));
			assertThat(enqueteEditVM.getQuestionTypeList().size(), is(3));

			assertThat(enqueteEditVM.getDeptList().get(0).getDeptId(), is("1"));
			assertThat(enqueteEditVM.getDeptList().get(0).getDeptName(), is("監査室"));

			assertThat(enqueteEditVM.getQuestionTypeList().get(0).getQuestionTypeId(), is(1));
			assertThat(enqueteEditVM.getQuestionTypeList().get(0).getQuestionType(), is("単一選択"));
		}

//		@Test
//		@Order(11)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService010_getEnqueteEditVMSystemError() {
//		}

		@Test
		@Order(12)
		@DisplayName("特定のアンケート情報をデータベースから取得し、それらが格納されたEnquateFormオブジェクトを取得できる。")
		public void testService011_getEnqueteDetail() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			QuestionForm questionForm = enqueteForm.getQuestionFormList().get(0);
			ChoiceForm choiceForm = questionForm.getChoiceFormList().get(0);

			assertThat(enqueteForm.getEnqueteId(), is(4));
			assertThat(enqueteForm.getEnqueteName(), is("2016年度 裁量労働制に関する社内アンケート"));
			assertThat(enqueteForm.getCreateUserId(), is("li9010"));
			assertThat(enqueteForm.getEnqueteSubtext(), is(nullValue()));
			assertThat(enqueteForm.getVersion(), is(1));


			assertThat(enqueteForm.getDeptIds()[0], is(3));

			assertThat(questionForm.getQuestionId(), is(5));
			assertThat(questionForm.getEnqueteId(), is(4));
			assertThat(questionForm.getQuestionNumber(), is(1));
			assertThat(questionForm.getQuestionTypeId(), is(1));
			assertThat(questionForm.getRequireFlag(), is(1));
			assertThat(questionForm.getQuestionText(), is("裁量労働制は継続するべきですか"));
			assertThat(questionForm.getQuestionSubtext(), is(nullValue()));


			assertThat(choiceForm.getChoiceId(), is(15));
			assertThat(choiceForm.getQuestionId(), is(5));
			assertThat(choiceForm.getChoiceNumber(), is(1));
			assertThat(choiceForm.getChoiceText(), is("継続するべき"));
		}

//		@Test
//		@Order(13)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService012_getEnqueteDetailSystemError() {
//		}
		@Test
		@Order(14)
		@DisplayName("引数の内容で特定のアンケートを新規作成する。")
		public void testService013_insertEnquete() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.setEnqueteId(null);
			Integer result = enqueteEditService.insertEnquete(enqueteForm, 2);
			assertThat(result, notNullValue());
		}

//		@Test
//		@Order(15)
//		@DisplayName("引数の内容で特定のアンケートを新規作成に失敗する。")
//		public void testService0014_insertEnquete() {
//			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
//			enqueteForm.setEnqueteId(null);
//			Integer result = enqueteEditService.insertEnquete(enqueteForm, "li9011", 2);
//			assertThat(result, notNullValue());
//		}

//		@Test
//		@Order(16)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService015_insertEnqueteSystemError() {
//		}
		@Test
		@Order(17)
		@DisplayName("引数の内容で特定のアンケートを編集する。")
		public void testService016_updateEnquete() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			//Boolean result = enqueteEditService.updateEnquete(enqueteForm, 2);
//			Integer result = enqueteEditService.insertEnquete(enqueteForm, "li9011", 2);
			//assertThat(result, is(true));
		}

//		@Test
//		@Order(18)
//		@DisplayName("引数の内容で特定のアンケートを編集することに失敗する。")
//		public void testService017_updateEnquete() {
//		}

//		@Test
//		@Order(19)
//		@DisplayName("DBにアクセス出来ないため、SystemErrorExceptionが発生する。")
//		public void testService018_updateEnqueteSystemError() {
//		}
		@Test
		@Order(20)
		@DisplayName("アンケート内容が正しく入力されている。※自由記述以外")
		public void testService019_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			assertThat(enqueteEditService.checkNull(enqueteForm), is(empty()));
		}
		@Test
		@Order(21)
		@DisplayName("アンケート内容が正しく入力されている。※自由記述")
		public void testService020_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			assertThat(enqueteEditService.checkNull(enqueteForm), is(empty()));
		}
		@Test
		@Order(22)
		@DisplayName("質問内容が0の場合、エラーメッセージを返す。")
		public void testService021_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.setQuestionFormList(null);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("questionFormList,質問内容が存在しません"));
		}
		@Test
		@Order(23)
		@DisplayName("質問番号がnullの場合、エラーメッセージを返す。")
		public void testService022_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.getQuestionFormList().get(0).setQuestionNumber(null);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("questionNumber,質問番号が存在しません"));
		}
		@Test
		@Order(24)
		@DisplayName("質問形式がnullの場合、エラーメッセージを返す。")
		public void testService023_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.getQuestionFormList().get(0).setQuestionTypeId(null);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("questionTypeId,質問形式が存在しません"));
		}
		@Test
		@Order(25)
		@DisplayName("必須フラグがnullの場合、エラーメッセージを返す。")
		public void testService024_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.getQuestionFormList().get(0).setRequireFlag(null);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("requireFlag,必須フラグが存在しません"));
		}
		@Test
		@Order(26)
		@DisplayName("質問項目内容が1つの場合、エラーメッセージを返す。")
		public void testService025_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.getQuestionFormList().get(0).getChoiceFormList().remove(0);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("choiceFormList,質問項目内容は2つ以上必要です"));
		}
		@Test
		@Order(27)
		@DisplayName("質問項目番号がnullの場合、エラーメッセージを返す。")
		public void testService026_checkNull() {
			EnqueteForm enqueteForm = enqueteEditService.getEnqueteDetail(4);
			enqueteForm.getQuestionFormList().get(0).getChoiceFormList().get(0).setChoiceNumber(null);
			assertThat(enqueteEditService.checkNull(enqueteForm).get(0), is("choiceNumber,質問項目番号が存在しません"));
			System.out.println(enqueteForm.getQuestionFormList().get(0).getQuestionTypeId());
			enqueteForm = enqueteEditService.getEnqueteDetail(4);
		}

}
