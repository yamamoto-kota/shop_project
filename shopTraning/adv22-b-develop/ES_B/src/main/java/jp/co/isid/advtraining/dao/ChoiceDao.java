
package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.Choice;

/**
 */
@ConfigAutowireable
@Dao
public interface ChoiceDao {

    /**
     * @param choiceId
     * @return the Choice entity
     */
    @Select
    Choice selectById(Integer choiceId);

    /**
     * @param choiceId
     * @param version
     * @return the Choice entity
     */
    @Select(ensureResult = true)
    Choice selectByIdAndVersion(Integer choiceId, Integer version);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Choice entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Choice entity);

    @Delete
    int delete(Choice entity);

    /**
     * @param choiceList
     * @return affected rows
     */
    @BatchDelete
    int[] delete(List<Choice> choiceList);

    @Select
    List<Choice> selectByQuestionId(Integer questionId);

    @Select
	Integer selectByChoiceNumber(Integer choiceId);

}
