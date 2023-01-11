package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.AnswerStatus;

@ConfigAutowireable
@Dao

public interface EnqueteResultAllDao {

	@Select
	List<AnswerStatus> selectRespondentList(Integer enqueteId);

}
