package jp.co.isid.advtraining.dao;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.Choice;
import jp.co.isid.advtraining.entity.ChoiceAnswer;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAnswer;
import jp.co.isid.advtraining.entity.Question;
import jp.co.isid.advtraining.entity.QuestionAnswer;

/**
 */
@ConfigAutowireable
@Dao
public interface EnqueteAnswerDao {
    /**
     * @param enqueteAnswerId
     * @return the EnqueteAnswer entity
     */
    @Select
    EnqueteAnswer selectById(Integer enqueteAnswerId);
    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EnqueteAnswer entity);
    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EnqueteAnswer entity);
    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(EnqueteAnswer entity);



    //ここからオリジナル

    @Select
    Enquete selectEnqueteByEnqueteId(Integer enqueteId);

    @Select
    List<Question> selectQuestionByEnqueteId(Integer enqueteId);

    @Select
    List<Choice> selectChoiceByQuestionId(Integer questionId);

    @Select
    EnqueteAnswer selectEnqueteAnswerByEnqueteIdAndEsqId(Integer enqueteId,String esqId);

    @Select
    List<QuestionAnswer> selectQuestionAnswerByEnqueteAnswerId(Integer enqueteAnswerId);

    @Select
    List<ChoiceAnswer> selectChoiceByQuestionAnswerId(Integer questionAnswerId);

    @Select
    Integer selectIdentifyQuestionIdByEnqueteIdAndQuestionNumber(Integer enqueteId,Integer questionNumber);

    @Select
    Integer selectIdentifyQuestionTypeIdByEnqueteIdAndQuestionNumber(Integer enqueteId,Integer questionNumber);

    @Select
    Integer selectIdentifyQuestionAnswerIdByEnqueteAnswerIdAndQuestionId(Integer enqueteAnswerId,Integer questionId);

    @Select
    Integer selectIdentifyChoiceIdByQuestionIdAndChoiceNumber(Integer questionId,Integer choiceNumber);


}
