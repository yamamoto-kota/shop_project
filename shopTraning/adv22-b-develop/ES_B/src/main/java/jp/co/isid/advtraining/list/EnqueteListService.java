package jp.co.isid.advtraining.list;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.dao.ChoiceDao;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.dao.EnqueteDeptDao;
import jp.co.isid.advtraining.dao.EnqueteListDao;
import jp.co.isid.advtraining.dao.QuestionDao;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EnqueteDept;
import jp.co.isid.advtraining.entity.Question;

@Service
@Transactional(rollbackFor = { Exception.class })
public class EnqueteListService {
	//アンケート一覧に関するDao
	@Autowired
	EnqueteListDao enqueteListDao;

	//ChoiceテーブルのDao
	@Autowired
	ChoiceDao choiceDao;

	//QuestionテーブルのDao
	@Autowired
	QuestionDao questionDao;

	//EnqueteAdminUserテーブルのDao
	@Autowired
	EnqueteAdminUserDao enqueteAdminUserDao;

	//EnqueteDeptテーブルのDao
	@Autowired
	EnqueteDeptDao enqueteDeptDao;

	//EnqueteテーブルのDao
	@Autowired
	EnqueteDao enqueteDao;

	public List<Enquete> getNoAnswerList(String esqId) {
		/*未回答アンケート内容(アンケートID,アンケート名,アンケート状況ID)をリストとして取得し、結果を返す。*/
		return enqueteListDao.noAnswerList(esqId);
	}

	public List<Enquete> getCompleteAnswerList(String esqId) {
		/*未回答アンケート内容(アンケートID,アンケート名,アンケート状況ID)をリストとして取得し、結果を返す。*/
		return enqueteListDao.completeAnswerList(esqId);
	}

	public List<Enquete> getBeforeEnqueteList(String esqId) {
		/*未回答アンケート内容(アンケートID,アンケート名,アンケート状況ID)をリストとして取得し、結果を返す。*/
		return enqueteListDao.beforeEnqueteList(esqId);
	}

	public List<Enquete> getStartEnqueteList(String esqId) {
		/*未回答アンケート内容(アンケートID,アンケート名,アンケート状況ID)をリストとして取得し、結果を返す。*/
		return enqueteListDao.startEnqueteList(esqId);
	}

	public List<Enquete> getFinishEnqueteList(String esqId) {
		/*未回答アンケート内容(アンケートID,アンケート名,アンケート状況ID)をリストとして取得し、結果を返す。*/
		return enqueteListDao.finishEnqueteList(esqId);
	}

	public String checkList(Integer enqueteId, Integer enqueteStateIdCre[], String esqId) {
		/*排他制御を行い、条件に当てはまる場合はエラーメッセージを返す
			処理内容は以下のとおり。*/
		/*1. String result変数にnullを初期値として設定しておく*/
		String result = null;

		/*2. EnquateDao#selectById(Integer enqueteId)を使用し、アンケート情報を取得する*/
		Enquete enquete = null;
		enquete = enqueteDao.selectById(enqueteId);

		/*3. 2がnullの場合、1に「アンケートは削除されています。」という文字列を格納し、戻り値として返す*/
		if (enquete == null) {
			result = "アンケートは削除されています。";
			return result;
		}

		/*5. 3のenqueteStatsIdが引数のenqueteStateIdCre[]と異なる場合、2に「他のユーザによりアンケート状況が更新されました。
		 * 対象アンケートの状態を再度ご確認ください。」という文字列を格納し、戻り値として返す*/
		if ((enquete.getEnqueteStateId() == enqueteStateIdCre[0]
				|| enquete.getEnqueteStateId() == enqueteStateIdCre[1]) == false) {
			result = "他のユーザによりアンケート状況が更新されました。対象アンケートの状態を再度ご確認ください。";
			return result;
		}

		/* 6. EnqueteAdminUserDao#selectById(Integer enqueteId, String esqId)を使用し、
		 * アンケート管理者情報を取得する*/
		EnqueteAdminUser enquateAdminUser = enqueteAdminUserDao.selectById(enqueteId, esqId);

		/*7. 6.の deleteFlagが1 の場合、2に「管理者権限がありません。」という文字列を格納し、
		 * 戻り値として返す*/
		if (enquateAdminUser.getDeleteFlag() == 1) {
			result = "管理者権限がありません。";
			return result;
		}

		/*8. 戻り値を返す*/
		return result;
	}

	public boolean deleteEnquete(Integer enqueteId) {
		/*特定のEnqueteテーブルを削除するため、外部キー制約がある他のテーブル(Choice、Question、EnqueteAdminUser、EnqueteDept、Enquete)の行も削除しながら特定のEnqueteテーブルの行を削除する。
		処理内容は以下のとおり。*/
		/*1. boolean ret変数にtrueを初期値として設定しておく。*/
		boolean ret = true;

		/*2.特定のquestionIdと一致するChoiceテーブルの行を削除する。*/
		/*2.1.QuestionDao#selectByEnqueteIdを呼び出し、QuestionテーブルからenqueteIdと一致するQuestion情報をリストで取得する。*/
		List<Question> questionList = questionDao.selectByEnqueteId(enqueteId);

		/*2.2. Choice#selectByQuestionIdを呼び出し、2.1で取得したQuestionのquestionIdと一致するChoice情報を、Choiceテーブルから一つ一つ取得する。（for文）*/
		for (Question num : questionList) {
			List<Choice> choiceList = choiceDao.selectByQuestionId(num.getQuestionId());

			/*2.3. 2.2で取得した情報をChoiceDao#deleteで削除する。*/
			choiceDao.delete(choiceList);
		}

		/*3.特定のenqueteIdと一致するQuestionテーブルの行を削除する。*/
		/*3.1.QuestionDao#deleteを呼び出し、2.1の情報を引数にQuestion情報を削除する。*/
		questionDao.delete(questionList);

		/*4.特定のenqueteIdと一致するEnqueteAdminUserテーブルの行を削除する。*/
		/*4.1.EnqueteAdminUserDao#selectByIdを呼び出し、EnqueteAdminUserテーブルからenqueteIdと一致するEnqueteAdminUser情報リストで取得する。*/
		List<EnqueteAdminUser> enqueteAdminUserList = enqueteAdminUserDao.selectByEnqueteId(enqueteId);

		/*4.2.EnqueteAdminUserDao#deleteを呼び出し、4.1の情報を引数にEnqueteAdminUser情報を削除する。*/
		enqueteAdminUserDao.delete(enqueteAdminUserList);

		/*5.特定のenqueteIdと一致するEnqueteDeptテーブルの行を削除する。*/
		/*5.1.EnqueteDeptDao#selectByEnquateIdを呼び出し、EnqueteDeptテーブルからenqueteIdと一致するEnqueteDept情報をリストで取得する。*/
		List<EnqueteDept> enqueteDeptList = enqueteDeptDao.selectByEnqueteId(enqueteId);

		/*5.2.EnqueteDeptDao#deleteを呼び出し、5.1の情報を引数にEnqueteDept情報を削除する。*/
		enqueteDeptDao.delete(enqueteDeptList);

		/*6.特定のenqueteIdと一致するEnqueteテーブルの行を削除する。*/
		/*6.1.EnqueteDao#selectByIdを呼び出し、EnqueteテーブルからenqueteIdと一致するEnquete情報を取得する。*/
		Enquete enquete = enqueteDao.selectById(enqueteId);

		/*6.2.EnqueteDao#deleteを呼び出し、6.1の情報を引数にEnquete情報を削除する。*/
		enqueteDao.delete(enquete);

		/*7. 戻り値retを返す。*/
		return ret;
	}

	public boolean startEnqueteUpdate(Integer enqueteId) {
		/*特定のアンケートの開始日付とアンケート状況を更新する。
		 * 処理内容は以下のとおり。*/
		/*1. boolean ret変数にtrueを初期値として設定しておく。*/
		boolean ret = true;

		/*2.DBのenqueteテーブルから「enqueteId」に対するアンケート情報を取得する。*/
		Enquete enquete = enqueteDao.selectById(enqueteId);

		/*3.LocalDate型で変数名「current」に今日の日付を入れる。*/
		LocalDate current = LocalDate.now();

		/*4. 2で取得した情報の「startDate」に「current」を入れる。*/
		enquete.setStartDate(current);

		/*5. 2で取得した情報の「enqueteStateId」に「3」を入れる。*/
		enquete.setEnqueteStateId(3);

		/*6.DBに2の情報を更新する。*/
		enqueteDao.update(enquete);

		/*7. 戻り値retを返す。*/
		return ret;
	}

	public boolean finishEnqueteUpdate(Integer enqueteId) {
		/*特定のアンケートの終了日付とアンケート状況を更新する。
		 * 処理内容は以下のとおり。*/
		/*1. boolean ret変数にtrueを初期値として設定しておく。*/
		boolean ret = true;

		/*2.DBのenqueteテーブルから「enqueteId」に対するアンケート情報を取得する。*/
		Enquete enquete = enqueteDao.selectById(enqueteId);

		/*3.LocalDate型で変数名「current」に今日の日付を入れる。*/
		LocalDate current = LocalDate.now();

		/*4. 2で取得した情報の「finishDate」に「current」を入れる。*/
		enquete.setFinishDate(current);

		/*5. 2で取得した情報の「enqueteStateId」に「4」を入れる。*/
		enquete.setEnqueteStateId(4);

		/*6.DBに2の情報を更新する。*/
		enqueteDao.update(enquete);

		/*7. 戻り値retを返す。*/
		return ret;
	}
}
