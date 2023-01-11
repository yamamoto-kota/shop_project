
package jp.co.isid.advtraining.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.EnqueteState;

/**
 */
@ConfigAutowireable
@Dao
public interface EnqueteStateDao {

    /**
     * @param enqueteStateId
     * @return the EnqueteState entity
     */
    @Select
    EnqueteState selectById(Integer enqueteStateId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EnqueteState entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EnqueteState entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(EnqueteState entity);
}
