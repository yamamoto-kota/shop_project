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
@Table(name = "question_type")
public class QuestionType {

    /**  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id")
    Integer questionTypeId;

    /**  */
    @Column(name = "question_type")
    String questionType;

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
     * Returns the questionType.
     *
     * @return the questionType
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * Sets the questionType.
     *
     * @param questionType the questionType
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
