package jp.co.isid.advtraining.Form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnqueteForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer enqueteId;
	private String enqueteName;
	private String createUserId;
	private String enqueteSubtext;
	private Integer[] deptIds;
	private int version;
	private List<QuestionForm> questionFormList;

	public EnqueteForm() {
		this.questionFormList = new ArrayList<QuestionForm>();
	}

	public Integer getEnqueteId() {
		return enqueteId;
	}

	public void setEnqueteId(Integer enqueteId) {
		this.enqueteId = enqueteId;
	}

	public String getEnqueteName() {
		return enqueteName;
	}

	public void setEnqueteName(String enqueteName) {
		this.enqueteName = enqueteName;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getEnqueteSubtext() {
		return enqueteSubtext;
	}

	public void setEnqueteSubtext(String enqueteSubtext) {
		this.enqueteSubtext = enqueteSubtext;
	}

	public Integer[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Integer[] deptIds) {
		this.deptIds = deptIds;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<QuestionForm> getQuestionFormList() {
		return questionFormList;
	}

	public void setQuestionFormList(List<QuestionForm> questionFormList) {
		this.questionFormList = questionFormList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}