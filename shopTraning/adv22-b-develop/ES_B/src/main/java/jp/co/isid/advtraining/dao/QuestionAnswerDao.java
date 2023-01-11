
package jp.co.isid.advtraining.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.QuestionAnswer;

/**
 */
@ConfigAutowireable
@Dao
public interface QuestionAnswerDao {

    /**
     * @param questionAnswerId
     * @return the QuestionAnswer entity
     */
    @Select
    QuestionAnswer selectById(Integer questionAnswerId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(QuestionAnswer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(QuestionAnswer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(QuestionAnswer entity);
}
