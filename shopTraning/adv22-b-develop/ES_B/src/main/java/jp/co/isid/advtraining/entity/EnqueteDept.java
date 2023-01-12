package jp.co.isid.advtraining.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 *
 */
@Entity
@Table(name="enquete_dept")
public class EnqueteDept {

    /**  */
    @Id
    @Column(name = "enquete_id")
    Integer enqueteId;

    /**  */
    @Id
    @Column(name = "dept_id")
    Integer deptId;

    /**
     * Returns the enqueteId.
     *
     * @return the enqueteId
     */
    public Integer getEnqueteId() {
        return enqueteId;
    }

    /**
     * Sets the enqueteId.
     *
     * @param enqueteId the enqueteId
     */
    public void setEnqueteId(Integer enqueteId) {
        this.enqueteId = enqueteId;
    }

    /**
     * Returns the deptId.
     *
     * @return the deptId
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * Sets the deptId.
     *
     * @param deptId the deptId
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
