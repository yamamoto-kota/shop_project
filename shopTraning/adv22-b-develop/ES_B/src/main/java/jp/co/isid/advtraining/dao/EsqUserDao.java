
package jp.co.isid.advtraining.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.EsqUser;

/**
 */
@ConfigAutowireable
@Dao
public interface EsqUserDao {

    /**
     * @param esqId
     * @return the EsqUser entity
     */
    @Select
    EsqUser selectById(String esqId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EsqUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EsqUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(EsqUser entity);
}
