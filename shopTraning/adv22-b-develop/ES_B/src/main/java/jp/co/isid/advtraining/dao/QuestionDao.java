
package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.Question;

/**
 */
@ConfigAutowireable
@Dao
public interface QuestionDao {

    /**
     * @param questionId
     * @return the Question entity
     */
    @Select
    Question selectById(Integer questionId);

    /**
     * @param questionId
     * @param version
     * @return the Question entity
     */
    @Select(ensureResult = true)
    Question selectByIdAndVersion(Integer questionId, Integer version);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Question entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Question entity);

    @Delete
    int delete(Question entity);

    /**
     * @param questionList
     * @return affected rows
     */
    @BatchDelete
    int[] delete(List<Question> questionList);

    @Select
	List<Question> selectByEnqueteId(Integer enqueteId);
}
