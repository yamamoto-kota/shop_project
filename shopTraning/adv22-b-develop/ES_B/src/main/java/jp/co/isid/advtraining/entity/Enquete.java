package jp.co.isid.advtraining.entity;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

/**
 *
 */
@Entity
@Table(name = "enquete")
public class Enquete {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enquete_id")
    Integer enqueteId;

    /**  */
    @Column(name = "enquete_name")
    String enqueteName;

    /**  */
    @Column(name = "enquete_state_id")
    Integer enqueteStateId;

    /**  */
    @Column(name = "create_user_id")
    String createUserId;

    /**  */
    @Column(name = "create_date")
    LocalDate createDate;

    /**  */
    @Column(name = "start_date")
    LocalDate startDate;

    /**  */
    @Column(name = "finish_date")
    LocalDate finishDate;

    /**  */
    @Column(name = "enquete_subtext")
    String enqueteSubtext;

    /**  */
    @Version
    @Column(name = "version")
    Integer version;

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
     * Returns the enqueteName.
     *
     * @return the enqueteName
     */
    public String getEnqueteName() {
        return enqueteName;
    }

    /**
     * Sets the enqueteName.
     *
     * @param enqueteName the enqueteName
     */
    public void setEnqueteName(String enqueteName) {
        this.enqueteName = enqueteName;
    }

    /**
     * Returns the enqueteStateId.
     *
     * @return the enqueteStateId
     */
    public Integer getEnqueteStateId() {
        return enqueteStateId;
    }

    /**
     * Sets the enqueteStateId.
     *
     * @param enqueteStateId the enqueteStateId
     */
    public void setEnqueteStateId(Integer enqueteStateId) {
        this.enqueteStateId = enqueteStateId;
    }

    /**
     * Returns the createUserId.
     *
     * @return the createUserId
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * Sets the createUserId.
     *
     * @param createUserId the createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * Returns the createDate.
     *
     * @return the createDate
     */
    public LocalDate getCreateDate() {
        return createDate;
    }

    /**
     * Sets the createDate.
     *
     * @param createDate the createDate
     */
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Returns the startDate.
     *
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the startDate.
     *
     * @param startDate the startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the finishDate.
     *
     * @return the finishDate
     */
    public LocalDate getFinishDate() {
        return finishDate;
    }

    /**
     * Sets the finishDate.
     *
     * @param finishDate the finishDate
     */
    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * Returns the enqueteSubtext.
     *
     * @return the enqueteSubtext
     */
    public String getEnqueteSubtext() {
        return enqueteSubtext;
    }

    /**
     * Sets the enqueteSubtext.
     *
     * @param enqueteSubtext the enqueteSubtext
     */
    public void setEnqueteSubtext(String enqueteSubtext) {
        this.enqueteSubtext = enqueteSubtext;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
