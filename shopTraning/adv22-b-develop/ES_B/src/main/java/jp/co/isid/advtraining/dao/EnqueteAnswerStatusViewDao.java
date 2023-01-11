
package jp.co.isid.advtraining.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.isid.advtraining.ConfigAutowireable;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.entity.Dept;

/**
 */
@ConfigAutowireable
@Dao
/*「①,②...」表記のはDao,Serviceでつながっているメソッド
 * */
public interface EnqueteAnswerStatusViewDao {

	//①アンケート対象部署に所属する未回答者をすべて返す
    @Select
    List<AnswerStatus> selectUnanswered1(Integer enqueteId);

  //②アンケート対象部署に所属するユーザーの総数を返す。
    @Select
    Integer selectAllunans2(Integer enqueteId);

  //③アンケート対象部署の未回答者の総数を返す。
    @Select
   Integer selectAlluser3(Integer enqueteId);

    //④アンケート対象部署のIDと部署名を返す。
    @Select
    List<Integer> selectTergetDepartment4(Integer enqueteId);




//    //⑤部署ごとに所属しているアンケート未回答者の総数を返す。
//    @Select
//    Integer selectUnans5(Integer enqueteId ,Integer deptId);
    //⑤部署ごとに所属しているアンケート未回答者の総数を返す。
    @Select
    List<Integer> selectUnans5(Integer enqueteId);


    //⑥部署ごとに所属しているユーザーの総数を返す。
    @Select
    Integer selectUser6(Integer deptId);

    //⑦部署ごとの未回答者の情報を返す
    @Select
    List<AnswerStatus> slectAllunanswered7(Integer enqueteId,Integer deptId);

  //⑧アンケート対象部署のIDと部署名リスト返す。
    @Select
    List<Dept> selectEnqueteAnsDeptList8(Integer enqueteId);

    //⑨アンケートIDからアンケート名取得
    @Select
    String selectEnqueteName9(Integer enqueteId);
}