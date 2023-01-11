package jp.co.isid.advtraining.entity;

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
@Table(name = "question")
public class Question {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    Integer questionId;

    /**  */
    @Column(name = "enquete_id")
    Integer enqueteId;

    /**  */
    @Column(name = "question_number")
    Integer questionNumber;

    /**  */
    @Column(name = "question_type_id")
    Integer questionTypeId;

    /**  */
    @Column(name = "require_flag")
    Integer requireFlag;

    /**  */
    @Column(name = "question_text")
    String questionText;

    /**  */
    @Column(name = "question_subtext")
    String questionSubtext;

    /**  */
    @Version
    @Column(name = "version")
    Integer version;

    /**
     * Returns the questionId.
     *
     * @return the questionId
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * Sets the questionId.
     *
     * @param questionId the questionId
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
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
     * Returns the questionNumber.
     *
     * @return the questionNumber
     */
    public Integer getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Sets the questionNumber.
     *
     * @param questionNumber the questionNumber
     */
    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * Returns the questionTypeId.
     *
     * @return the questionTypeId
     */
    public Integer getQuestionTypeId() {
        return questionTypeId;
    }

    /**
     * Sets the questionTypeId.
     *
     * @param questionTypeId the questionTypeId
     */
    public void setQuestionTypeId(Integer questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    /**
     * Returns the requireFlag.
     *
     * @return the requireFlag
     */
    public Integer getRequireFlag() {
        return requireFlag;
    }

    /**
     * Sets the requireFlag.
     *
     * @param requireFlag the requireFlag
     */
    public void setRequireFlag(Integer requireFlag) {
        this.requireFlag = requireFlag;
    }

    /**
     * Returns the questionText.
     *
     * @return the questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the questionText.
     *
     * @param questionText the questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Returns the questionSubtext.
     *
     * @return the questionSubtext
     */
    public String getQuestionSubtext() {
        return questionSubtext;
    }

    /**
     * Sets the questionSubtext.
     *
     * @param questionSubtext the questionSubtext
     */
    public void setQuestionSubtext(String questionSubtext) {
        this.questionSubtext = questionSubtext;
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
