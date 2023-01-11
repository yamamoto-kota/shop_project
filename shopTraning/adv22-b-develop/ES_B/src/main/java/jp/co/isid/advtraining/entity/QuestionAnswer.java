package jp.co.isid.advtraining.entity;

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
@Table(name = "question_answer")
public class QuestionAnswer {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_answer_id")
    Integer questionAnswerId;

    /**  */
    @Column(name = "enquete_answer_id")
    Integer enqueteAnswerId;

    /**  */
    @Column(name = "question_id")
    Integer questionId;

    /**  */
    @Column(name = "answer_text")
    String answerText;

    /**
     * Returns the questionAnswerId.
     *
     * @return the questionAnswerId
     */
    public Integer getQuestionAnswerId() {
        return questionAnswerId;
    }

    /**
     * Sets the questionAnswerId.
     *
     * @param questionAnswerId the questionAnswerId
     */
    public void setQuestionAnswerId(Integer questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

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
     * Returns the answerText.
     *
     * @return the answerText
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * Sets the answerText.
     *
     * @param answerText the answerText
     */
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
