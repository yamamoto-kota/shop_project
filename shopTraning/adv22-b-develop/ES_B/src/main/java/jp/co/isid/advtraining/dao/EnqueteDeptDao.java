
package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.BatchDelete;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.EnqueteDept;

/**
 */
@ConfigAutowireable
@Dao
public interface EnqueteDeptDao {

    /**
     * @param enqueteId
     * @param deptId
     * @return the EnqueteDept entity
     */
    @Select
    EnqueteDept selectById(Integer enqueteId, Integer deptId);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(EnqueteDept entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(EnqueteDept entity);

    @Delete
    int delete(EnqueteDept entity);

    /**
     * @param enqueteDeptList
     * @return affected rows
     */
    @BatchDelete
    int[] delete(List<EnqueteDept> enqueteDeptList);

    @Select
    List<EnqueteDept> selectByEnqueteId(Integer enqueteId);
}
