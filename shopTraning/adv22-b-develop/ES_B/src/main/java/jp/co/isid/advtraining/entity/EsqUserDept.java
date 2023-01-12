package jp.co.isid.advtraining.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "esq_user_dept")
public class EsqUserDept {
    /**  */
    @Id
    @Column(name = "esq_id")
    String esqId;

    /**  */
    @Column(name = "user_name")
    String userName;

    /**  */
    @Column(name = "dept_name")
    String deptName;

	public String getEsqId() {
		return esqId;
	}

	public void setEsqId(String esqId) {
		this.esqId = esqId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}




}
