
package jp.co.isid.advtraining.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.entity.Enquete;

/**
 */
@jp.co.isid.advtraining.ConfigAutowireable
@Dao
public interface EnqueteDao {

    /**
     * @param enqueteId
     * @return the Enquete entity
     */
    @Select
    Enquete selectById(Integer enqueteId);

    /**
     * @param enqueteId
     * @param version
     * @return the Enquete entity
     */
    @Select(ensureResult = true)
    Enquete selectByIdAndVersion(Integer enqueteId, Integer version);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(Enquete entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Enquete entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Enquete entity);
}
