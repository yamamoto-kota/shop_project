package jp.co.isid.advtraining.edit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jp.co.isid.advtraining.Form.ChoiceForm;
import jp.co.isid.advtraining.Form.EnqueteForm;
import jp.co.isid.advtraining.Form.QuestionForm;
import jp.co.isid.advtraining.VM.EnqueteEditVM;
import jp.co.isid.advtraining.dao.ChoiceDao;
import jp.co.isid.advtraining.dao.DeptDao;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.dao.EnqueteDeptDao;
import jp.co.isid.advtraining.dao.QuestionDao;
import jp.co.isid.advtraining.dao.QuestionTypeDao;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.Dept;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EnqueteDept;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionType;

@Service
@Transactional(rollbackFor = { Exception.class })
public class EnqueteEditService {

	@Autowired
	EnqueteDao enqueteDao;
	@Autowired
	QuestionDao questionDao;
	@Autowired
	ChoiceDao choiceDao;
	@Autowired
	DeptDao deptDao;
	@Autowired
	EnqueteDeptDao enqueteDeptDao;
	@Autowired
	QuestionTypeDao questionTypeDao;
	@Autowired
	EnqueteAdminUserDao enqueteAdminUserDao;

	public List<String> checkNull(EnqueteForm enqueteForm) {
		List<String> check = new ArrayList<>();
		if (CollectionUtils.isEmpty(enqueteForm.getQuestionFormList())) {
			check.add("enqueteName,質問内容が存在しません");
		} else {
			for (int i = 0; i < enqueteForm.getQuestionFormList().size(); i++) {
				if (enqueteForm.getQuestionFormList().get(i).getQuestionNumber() == null) {
					check.add("enqueteName,質問番号が存在しません");
				}
				if (enqueteForm.getQuestionFormList().get(i).getRequireFlag() == null) {
					check.add("enqueteName,必須フラグが存在しません");
				}

				if (enqueteForm.getQuestionFormList().get(i).getQuestionTypeId() == null) {
					check.add("enqueteName,質問形式が存在しません");
				} else {
					if (enqueteForm.getQuestionFormList().get(i).getQuestionTypeId() != 3) {
						if (enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size() <= 1) {
							check.add("enqueteName,質問項目内容は2つ以上必要です");
						}
						for (int j = 0; j < enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size(); j++) {
							if (enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceNumber() == null) {
								check.add("enqueteName,質問項目番号が存在しません");
							}
						}
					}
				}
			}
		}
		return check;
	}

	public String checkEdit(Integer enqueteId, String esqId){
		String result = null;
		Enquete retEnquete = enqueteDao.selectById(enqueteId);
		if (retEnquete == null) {
			result = "アンケートは削除されています";
			return result;
		}
		if (retEnquete.getEnqueteStateId() >= 3) {
			result = "アンケートは回答受付中か、受付終了しています";
			return result;
		}
		EnqueteAdminUser retEnqueteAdminUser = enqueteAdminUserDao.selectById(enqueteId, esqId);
		if (retEnqueteAdminUser.getDeleteFlag() == 1) {
			result = "管理者権限が削除されています";
			return result;
		}
		return result;
	}

	public String checkEnquete(Integer enqueteId, Integer version) {
		String result = null;
		Enquete retEnquete = enqueteDao.selectById(enqueteId);

		if (retEnquete.getVersion() != version) {
			result = "アンケートは変更されています";
			return result;
		}
		return result;
	}

	public EnqueteEditVM getEnqueteEditVM() {
		EnqueteEditVM enqueteEditVM = new EnqueteEditVM();
		List<Dept> retDeptList = deptDao.selectAll();
		enqueteEditVM.setDeptList(retDeptList);
		List<QuestionType> retQuestionTypeList = questionTypeDao.selectAll();
		enqueteEditVM.setQuestionTypeList(retQuestionTypeList);
		return enqueteEditVM;
	}

	public EnqueteForm getEnqueteDetail(Integer enqueteId) {
		EnqueteForm enqueteForm = new EnqueteForm();

		Enquete retEnquete = enqueteDao.selectById(enqueteId);
		enqueteForm.setEnqueteId(retEnquete.getEnqueteId());//引数はわざわざgetせずともenqueteIdでもいい
		enqueteForm.setEnqueteName(retEnquete.getEnqueteName());
		enqueteForm.setCreateUserId(retEnquete.getCreateUserId());
		enqueteForm.setEnqueteSubtext(retEnquete.getEnqueteSubtext());
		enqueteForm.setVersion(retEnquete.getVersion());

		List<EnqueteDept> retEnqueteDept = enqueteDeptDao.selectByEnqueteId(enqueteId);

		Integer[] deptIds = new Integer[retEnqueteDept.size()];
		for (int i = 0; i < retEnqueteDept.size(); i++) {
			deptIds[i] = retEnqueteDept.get(i).getDeptId();
		}
		enqueteForm.setDeptIds(deptIds);

		List<Question> retQuestionList = questionDao.selectByEnqueteId(enqueteId);
		List<QuestionForm> tmpQuestionFormList = new ArrayList<QuestionForm>();//forの外で宣言しとく
		for (int i = 0; i < retQuestionList.size(); i++) {
			Question tmpQuestion = retQuestionList.get(i);
			QuestionForm tmpQuestionForm = new QuestionForm();
			//tmpQuestionVM.setQuestion(tmpQuestion);の分解バージョン
			tmpQuestionForm.setQuestionId(tmpQuestion.getQuestionId());
			tmpQuestionForm.setEnqueteId(tmpQuestion.getEnqueteId());
			tmpQuestionForm.setQuestionNumber(tmpQuestion.getQuestionNumber());
			tmpQuestionForm.setQuestionTypeId(tmpQuestion.getQuestionTypeId());
			tmpQuestionForm.setRequireFlag(tmpQuestion.getRequireFlag());
			tmpQuestionForm.setQuestionText(tmpQuestion.getQuestionText());
			tmpQuestionForm.setQuestionSubtext(tmpQuestion.getQuestionSubtext());

			List<Choice> retChoiceList = choiceDao.selectByQuestionId(tmpQuestion.getQuestionId());
			List<ChoiceForm> tmpChoiceFormList = new ArrayList<ChoiceForm>();//forの外で宣言しとく
			for (int j = 0; j < retChoiceList.size(); j++) {
				Choice tmpChoice = retChoiceList.get(j);
				ChoiceForm tmpChoiceForm = new ChoiceForm();
				tmpChoiceForm.setChoiceId(tmpChoice.getChoiceId());
				tmpChoiceForm.setQuestionId(tmpChoice.getQuestionId());
				tmpChoiceForm.setChoiceNumber(tmpChoice.getChoiceNumber());
				tmpChoiceForm.setChoiceText(tmpChoice.getChoiceText());
				tmpChoiceFormList.add(tmpChoiceForm);
			}
			tmpQuestionForm.setChoiceFormList(tmpChoiceFormList);//逆算、分解のやつらと同列の行為
			tmpQuestionFormList.add(tmpQuestionForm);
		}
		enqueteForm.setQuestionFormList(tmpQuestionFormList);

		return enqueteForm;
	}

	public Integer insertEnquete(EnqueteForm enqueteForm,Integer enqueteStateId){
		Integer ret=null;
		Integer insertCount=0;
		Integer currentEnqueteId=0;

		Enquete enquete=new Enquete();
		enquete.setEnqueteName(enqueteForm.getEnqueteName());
		enquete.setCreateUserId(enqueteForm.getCreateUserId());//サービスに来る前にコントローラーで入れてある
		enquete.setEnqueteSubtext(enqueteForm.getEnqueteSubtext());
		enquete.setVersion(1);
		enquete.setEnqueteStateId(enqueteStateId);
		enquete.setCreateDate(LocalDate.now());
		insertCount+=enqueteDao.insert(enquete);
		currentEnqueteId=enquete.getEnqueteId();

		for(int i=0;i<enqueteForm.getDeptIds().length;i++) {
			EnqueteDept enqueteDept=new EnqueteDept();
			enqueteDept.setEnqueteId(currentEnqueteId);
			enqueteDept.setDeptId(enqueteForm.getDeptIds()[i]);
			insertCount+=enqueteDeptDao.insert(enqueteDept);
		}

		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			Question question=new Question();
			question.setEnqueteId(currentEnqueteId);
			question.setQuestionNumber(enqueteForm.getQuestionFormList().get(i).getQuestionNumber());
			question.setQuestionTypeId(enqueteForm.getQuestionFormList().get(i).getQuestionTypeId());
			question.setRequireFlag(enqueteForm.getQuestionFormList().get(i).getRequireFlag());
			question.setQuestionText(enqueteForm.getQuestionFormList().get(i).getQuestionText());
			question.setQuestionSubtext(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext());
			question.setVersion(1);
			insertCount+=questionDao.insert(question);
			Integer currentQuestionId=question.getQuestionId();

			for(int j=0;j<enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size();j++) {
				Choice choice=new Choice();
				choice.setQuestionId(currentQuestionId);
				choice.setChoiceNumber(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceNumber());
				choice.setChoiceText(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceText());
				choice.setVersion(1);
				insertCount+=choiceDao.insert(choice);
			}
		}

		EnqueteAdminUser enqueteAdminUser=new EnqueteAdminUser();
		enqueteAdminUser.setEnqueteId(currentEnqueteId);
		enqueteAdminUser.setEsqId(enqueteForm.getCreateUserId());
		enqueteAdminUser.setDeleteFlag(0);
		enqueteAdminUserDao.insert(enqueteAdminUser);

		if(insertCount!=0) {
			ret=currentEnqueteId;
		}

		return ret;//アンケートID or null を返却
	}

	public Integer updateEnquete(EnqueteForm enqueteForm,Integer enqueteStateId){
		Integer ret=null;
		Integer insertCount=0;

		Enquete enquete=new Enquete();
		enquete.setEnqueteId(enqueteForm.getEnqueteId());
		enquete.setEnqueteName(enqueteForm.getEnqueteName());
		enquete.setEnqueteStateId(enqueteStateId);
		enquete.setCreateUserId(enqueteForm.getCreateUserId());
		enquete.setCreateDate(LocalDate.now());
		enquete.setEnqueteSubtext(enqueteForm.getEnqueteSubtext());
		enquete.setVersion(enqueteForm.getVersion());
		enqueteDao.update(enquete);

		List<EnqueteDept> retEnqueteDeptList=enqueteDeptDao.selectByEnqueteId(enqueteForm.getEnqueteId());
		for(int i=0;i<retEnqueteDeptList.size();i++) {
			enqueteDeptDao.delete(retEnqueteDeptList.get(i));
		}

		for(int i=0;i<enqueteForm.getDeptIds().length;i++) {
			EnqueteDept enqueteDept=new EnqueteDept();
			enqueteDept.setEnqueteId(enqueteForm.getEnqueteId());
			enqueteDept.setDeptId(enqueteForm.getDeptIds()[i]);
			insertCount+=enqueteDeptDao.insert(enqueteDept);
		}

		List<Question> retQuestionList=questionDao.selectByEnqueteId(enqueteForm.getEnqueteId());
		for(int i=0;i<retQuestionList.size();i++) {
			List<Choice> retChoiceList=choiceDao.selectByQuestionId(retQuestionList.get(i).getQuestionId());
			for(int j=0;j<retChoiceList.size();j++) {
				choiceDao.delete(retChoiceList.get(j));
			}
			questionDao.delete(retQuestionList.get(i));//残骸問題箇所
		}

		for(int i=0;i<enqueteForm.getQuestionFormList().size();i++) {
			Question question=new Question();
			question.setEnqueteId(enqueteForm.getEnqueteId());
			question.setQuestionNumber(enqueteForm.getQuestionFormList().get(i).getQuestionNumber());
			question.setQuestionTypeId(enqueteForm.getQuestionFormList().get(i).getQuestionTypeId());
			question.setRequireFlag(enqueteForm.getQuestionFormList().get(i).getRequireFlag());
			question.setQuestionText(enqueteForm.getQuestionFormList().get(i).getQuestionText());
			question.setQuestionSubtext(enqueteForm.getQuestionFormList().get(i).getQuestionSubtext());
			question.setVersion(1);
			insertCount+=questionDao.insert(question);
			Integer currentQuestionId=question.getQuestionId();

			for(int j=0;j<enqueteForm.getQuestionFormList().get(i).getChoiceFormList().size();j++) {
				Choice choice=new Choice();
				choice.setQuestionId(currentQuestionId);
				choice.setChoiceNumber(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceNumber());
				choice.setChoiceText(enqueteForm.getQuestionFormList().get(i).getChoiceFormList().get(j).getChoiceText());
				question.setVersion(1);
				insertCount+=choiceDao.insert(choice);
			}
		}

		if(insertCount==0) {
			ret=null;
		}
		else {
			ret=0;
		}
		return ret;
	}

}
