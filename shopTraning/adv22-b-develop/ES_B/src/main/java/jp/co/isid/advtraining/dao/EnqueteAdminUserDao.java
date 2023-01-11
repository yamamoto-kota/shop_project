
package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.AdminEsqUserDept;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EsqUserDept;

/**
 */
@ConfigAutowireable
@Dao
public interface EnqueteAdminUserDao {

    /**
     * @param enqueteId
     * @param esqId
     * @return the EnqueteAdminUser entity
     */
    @Select
    EnqueteAdminUser selectById(Integer enqueteId, String esqId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EnqueteAdminUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EnqueteAdminUser entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(EnqueteAdminUser entity);

    @BatchDelete
    int[] delete(List<EnqueteAdminUser> enqueteAdminUserList);

    @Select
	  List<AdminEsqUserDept> getEnqueteAdminUser(Integer enqueteId);

    @Select
	  List<EsqUserDept> selectBySearchWord(Integer enqueteId, String searchWord);

    @Select
    List<EnqueteAdminUser> selectByEnqueteId(Integer enqueteId);



}
