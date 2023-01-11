package jp.co.isid.advtraining.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 *
 */
@Entity
@Table(name = "dept")
public class Dept {

    /**  */
    @Id
    @Column(name = "DEPT_ID")
    String deptId;

    /**  */
    @Column(name = "DEPT_NAME")
    String deptName;

    /**
     * Returns the deptId.
     *
     * @return the deptId
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * Sets the deptId.
     *
     * @param deptId the deptId
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * Returns the deptName.
     *
     * @return the deptName
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * Sets the deptName.
     *
     * @param deptName the deptName
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
