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
@Table(name = "choice")
public class Choice{

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    Integer choiceId;

    /**  */
    @Column(name = "question_id")
    Integer questionId;

    /**  */
    @Column(name = "choice_number")
    Integer choiceNumber;

    /**  */
    @Column(name = "choice_text")
    String choiceText;

    /**  */
    @Version
    @Column(name = "version")
    Integer version;

    /**
     * Returns the choiceId.
     *
     * @return the choiceId
     */
    public Integer getChoiceId() {
        return choiceId;
    }

    /**
     * Sets the choiceId.
     *
     * @param choiceId the choiceId
     */
    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

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
     * Returns the choiceNumber.
     *
     * @return the choiceNumber
     */
    public Integer getChoiceNumber() {
        return choiceNumber;
    }

    /**
     * Sets the choiceNumber.
     *
     * @param choiceNumber the choiceNumber
     */
    public void setChoiceNumber(Integer choiceNumber) {
        this.choiceNumber = choiceNumber;
    }

    /**
     * Returns the choiceText.
     *
     * @return the choiceText
     */
    public String getChoiceText() {
        return choiceText;
    }

    /**
     * Sets the choiceText.
     *
     * @param choiceText the choiceText
     */
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
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
