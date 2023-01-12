
package jp.co.isid.advtraining.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.ChoiceAnswer;

/**
 */
@ConfigAutowireable
@Dao
public interface ChoiceAnswerDao {

    /**
     * @param choiceAnswerId
     * @return the ChoiceAnswer entity
     */
    @Select
    ChoiceAnswer selectById(Integer choiceAnswerId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(ChoiceAnswer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(ChoiceAnswer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(ChoiceAnswer entity);
}
