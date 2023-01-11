package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.Enquete;

public class EnqueteListVM {

	private List<Enquete> noAnswerListVM;
	private List<Enquete> completeAnswerListVM;
	private List<Enquete> beforeEnqueteListVM;
	private List<Enquete> startEnqueteListVM;
	private List<Enquete> finishEnqueteListVM;


	public List<Enquete> getNoAnswerListVM() {
		return noAnswerListVM;
	}
	public void setNoAnswerListVM(List<Enquete> noAnswerListVM) {
		this.noAnswerListVM = noAnswerListVM;
	}

	public List<Enquete> getCompleteAnswerListVM() {
		return completeAnswerListVM;
	}
	public void setCompleteAnswerListVM(List<Enquete> completeAnswerListVM) {
		this.completeAnswerListVM = completeAnswerListVM;
	}

	public List<Enquete> getStartEnqueteListVM() {
		return startEnqueteListVM;
	}
	public void setStartEnqueteListVM(List<Enquete> startEnqueteListVM) {
		this.startEnqueteListVM = startEnqueteListVM;
	}

	public List<Enquete> getBeforeEnqueteListVM() {
		return beforeEnqueteListVM;
	}
	public void setBeforeEnqueteListVM(List<Enquete> beforeEnqueteListVM) {
		this.beforeEnqueteListVM = beforeEnqueteListVM;
	}

	public List<Enquete> getFinishEnqueteListVM() {
		return finishEnqueteListVM;
	}
	public void setFinishEnqueteListVM(List<Enquete> finishEnqueteListVM) {
		this.finishEnqueteListVM = finishEnqueteListVM;
	}

}
