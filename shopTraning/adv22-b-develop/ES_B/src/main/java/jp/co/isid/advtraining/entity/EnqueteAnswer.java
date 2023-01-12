package jp.co.isid.advtraining.entity;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 *
 */
@Entity
@Table(name = "enquete_answer")
public class EnqueteAnswer {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enquete_answer_id")
    Integer enqueteAnswerId;

    /**  */
    @Column(name = "enquete_id")
    Integer enqueteId;

    /**  */
    @Column(name = "esq_id")
    String esqId;

    /**  */
    @Column(name = "answer_date")
    LocalDate answerDate;

    /**
     * Returns the enqueteAnswerId.
     *
     * @return the enqueteAnswerId
     */
    public Integer getEnqueteAnswerId() {
        return enqueteAnswerId;
    }

    /**
     * Sets the enqueteAnswerId.
     *
     * @param enqueteAnswerId the enqueteAnswerId
     */
    public void setEnqueteAnswerId(Integer enqueteAnswerId) {
        this.enqueteAnswerId = enqueteAnswerId;
    }

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
     * Returns the esqId.
     *
     * @return the esqId
     */
    public String getEsqId() {
        return esqId;
    }

    /**
     * Sets the esqId.
     *
     * @param esqId the esqId
     */
    public void setEsqId(String esqId) {
        this.esqId = esqId;
    }

    /**
     * Returns the answerDate.
     *
     * @return the answerDate
     */
    public LocalDate getAnswerDate() {
        return answerDate;
    }

    /**
     * Sets the answerDate.
     *
     * @param answerDate the answerDate
     */
    public void setAnswerDate(LocalDate answerDate) {
        this.answerDate = answerDate;
    }
}
