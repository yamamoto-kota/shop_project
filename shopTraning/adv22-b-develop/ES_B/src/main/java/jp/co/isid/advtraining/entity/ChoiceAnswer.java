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
@Table(name = "choice_answer")
public class ChoiceAnswer {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_answer_id")
    Integer choiceAnswerId;

    /**  */
    @Column(name = "question_answer_id")
    Integer questionAnswerId;

    /**  */
    @Column(name = "choice_id")
    Integer choiceId;

    /**
     * Returns the choiceAnswerId.
     *
     * @return the choiceAnswerId
     */
    public Integer getChoiceAnswerId() {
        return choiceAnswerId;
    }

    /**
     * Sets the choiceAnswerId.
     *
     * @param choiceAnswerId the choiceAnswerId
     */
    public void setChoiceAnswerId(Integer choiceAnswerId) {
        this.choiceAnswerId = choiceAnswerId;
    }

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
}
