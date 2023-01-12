package jp.co.isid.advtraining.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.answerStatus.EnqueteAnswerStatusViewService;
import jp.co.isid.advtraining.dao.EnqueteAnswerStatusViewDao;
import jp.co.isid.advtraining.entity.AnswerStatus;
import jp.co.isid.advtraining.entity.Dept;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteAnswerStatusViewServiceTest {

	@Autowired
	private EnqueteAnswerStatusViewDao enqueteAnswerStatusViewDao;
	@Autowired
	private EnqueteAnswerStatusViewService enqueteAnswerStatusViewService;

//	//①アンケート対象部署に所属する未回答者をすべて返す。 public List<AnswerStatus>getUnsanswerdList1(Integer enqueteId)
	@Test
	@Order(1)
	@DisplayName("アンケートIDに対応した未回答者リストが返る。")
    public void testService000_getUnsanswerdList1() {
		Integer enqueteId=1;
		List<AnswerStatus> enq= enqueteAnswerStatusViewService.getUnsanswerdList1(enqueteId);
		List<AnswerStatus> testDao=enqueteAnswerStatusViewDao.selectUnanswered1(enqueteId);
		assertEquals(enq.size(),(20));
		assertEquals(testDao.size(),(20));

	assertThat(enq.get(0).getEsqId(),is("li9013"));
	assertThat(enq.get(0).getUserName(),is("中村恵一"));
	assertThat(enq.get(0).getDeptId(),is(2));
	assertThat(enq.get(0).getDeptName(),is("経営企画本部"));//2

	assertThat(enq.get(1).getEsqId(),is("li9014"));
	assertThat(enq.get(1).getUserName(),is("住田広"));
	assertThat(enq.get(1).getDeptId(),is(2));
	assertThat(enq.get(1).getDeptName(),is("経営企画本部"));//2

	assertThat(enq.get(2).getEsqId(),is("li9015"));
	assertThat(enq.get(2).getUserName(),is("落合蒼依"));
	assertThat(enq.get(2).getDeptId(),is(3));
	assertThat(enq.get(2).getDeptName(),is("コーポレート本部"));//3

	assertThat(enq.get(3).getEsqId(),is("li9016"));
	assertThat(enq.get(3).getUserName(),is("皆川富美子"));
	assertThat(enq.get(3).getDeptId(),is(3));
	assertThat(enq.get(3).getDeptName(),is("コーポレート本部"));//3

	assertThat(enq.get(4).getEsqId(),is("li9017"));
	assertThat(enq.get(4).getUserName(),is("江田政利"));
	assertThat(enq.get(4).getDeptId(),is(4));
	assertThat(enq.get(4).getDeptName(),is("Xイノベーション本部"));//4

	assertThat(enq.get(5).getEsqId(),is("li9018"));
	assertThat(enq.get(5).getUserName(),is("上野莉穂"));
	assertThat(enq.get(5).getDeptId(),is(4));
	assertThat(enq.get(5).getDeptName(),is("Xイノベーション本部"));//4

	assertThat(enq.get(6).getEsqId(),is("li9019"));
	assertThat(enq.get(6).getUserName(),is("鬼頭南"));
	assertThat(enq.get(6).getDeptId(),is(5));
	assertThat(enq.get(6).getDeptName(),is("事業推進室"));//5

	assertThat(enq.get(7).getEsqId(),is("li9020"));
	assertThat(enq.get(7).getUserName(),is("川原菜奈"));
	assertThat(enq.get(7).getDeptId(),is(5));
	assertThat(enq.get(7).getDeptName(),is("事業推進室"));//5

	assertThat(enq.get(8).getEsqId(),is("li9021"));
	assertThat(enq.get(8).getUserName(),is("宮原三男"));
	assertThat(enq.get(8).getDeptId(),is(6));
	assertThat(enq.get(8).getDeptName(),is("金融ソリューション事業部"));//6

	assertThat(enq.get(9).getEsqId(),is("li9022"));
	assertThat(enq.get(9).getUserName(),is("長瀬正美"));
	assertThat(enq.get(9).getDeptId(),is(6));
	assertThat(enq.get(9).getDeptName(),is("金融ソリューション事業部"));//6

	assertThat(enq.get(10).getEsqId(),is("li9023"));
	assertThat(enq.get(10).getUserName(),is("川崎富雄"));
	assertThat(enq.get(10).getDeptId(),is(7));
	assertThat(enq.get(10).getDeptName(),is("グループ経営ソリューション事業部"));//7

	assertThat(enq.get(11).getEsqId(),is("li9024"));
	assertThat(enq.get(11).getUserName(),is("三枝芳郎"));
	assertThat(enq.get(11).getDeptId(),is(7));
	assertThat(enq.get(11).getDeptName(),is("グループ経営ソリューション事業部"));//7

	assertThat(enq.get(12).getEsqId(),is("li9025"));
	assertThat(enq.get(12).getUserName(),is("粕谷敏仁"));
	assertThat(enq.get(12).getDeptId(),is(8));
	assertThat(enq.get(12).getDeptName(),is("HCM事業部"));//8

	assertThat(enq.get(13).getEsqId(),is("li9027"));
	assertThat(enq.get(13).getUserName(),is("山下永二"));
	assertThat(enq.get(13).getDeptId(),is(9));
	assertThat(enq.get(13).getDeptName(),is("製造ソリューション事業部"));//9

	assertThat(enq.get(14).getEsqId(),is("li9028"));
	assertThat(enq.get(14).getUserName(),is("朝倉哲雄"));
	assertThat(enq.get(14).getDeptId(),is(9));
	assertThat(enq.get(14).getDeptName(),is("製造ソリューション事業部"));//9

	assertThat(enq.get(15).getEsqId(),is("li9029"));
	assertThat(enq.get(15).getUserName(),is("吉川伍朗"));
	assertThat(enq.get(15).getDeptId(),is(10));
	assertThat(enq.get(15).getDeptName(),is("コミュニケーションIT事業部"));//10

	assertThat(enq.get(16).getEsqId(),is("li9030"));
	assertThat(enq.get(16).getUserName(),is("玉田雅典"));
	assertThat(enq.get(16).getDeptId(),is(10));
	assertThat(enq.get(16).getDeptName(),is("コミュニケーションIT事業部"));//10

	assertThat(enq.get(17).getEsqId(),is("li9031"));
	assertThat(enq.get(17).getUserName(),is("吉野孝二"));
	assertThat(enq.get(17).getDeptId(),is(11));
	assertThat(enq.get(17).getDeptName(),is("エンタープライズIT事業部"));//11

	assertThat(enq.get(18).getEsqId(),is("li9032"));
	assertThat(enq.get(18).getUserName(),is("塩沢知美"));
	assertThat(enq.get(18).getDeptId(),is(11));
	assertThat(enq.get(18).getDeptName(),is("エンタープライズIT事業部"));//11

	assertThat(enq.get(19).getEsqId(),is("li9033"));
	assertThat(enq.get(19).getUserName(),is("蛭田良彦"));
	assertThat(enq.get(19).getDeptId(),is(11));
	assertThat(enq.get(19).getDeptName(),is("エンタープライズIT事業部"));//11


	}
	@Test
	@Order(2)
	@DisplayName("受けとったアンケートIDに対応するアンケートが存在しないため空のリストが返る。")
    public void testService001_getUnsanswerdList1() {
		Integer enqueteId=100;
		List<AnswerStatus> enq= enqueteAnswerStatusViewService.getUnsanswerdList1(enqueteId);
		assertThat(enq,is(empty()));
	}

//②アンケート対象部署の未回答者の総数を返す。 public Integer getAllunans2(Integer enqueteId)
	@Test
	@Order(3)
	@DisplayName("アンケートIDに対応した未回答者数が返る。")
    public void testService002_getAllunans2() {
		Integer enqueteId=1;
		Integer enq= enqueteAnswerStatusViewService.getAllunans2(enqueteId);
		assertEquals(enq, 20);

	}
	@Test
	@Order(4)
	@DisplayName("受けとったアンケートIDに対応するアンケートが存在しないため0が返る。")
    public void testService003_getAllunans2() {
		Integer enqueteId=100;
		Integer enq= enqueteAnswerStatusViewService.getAllunans2(enqueteId);
		assertThat(enq,is(0));

	}

//③アンケート対象部署に所属するユーザーの総数を返す。 public Integer getAlluser3(Integer enqueteId)
	@Test
	@Order(5)
	@DisplayName("アンケートIDに対応した部署に所属するユーザーの総数が返る。")
    public void testService004_getAlluser3() {
		Integer enqueteId=1;
		Integer enq= enqueteAnswerStatusViewService.getAlluser3(enqueteId);
		assertEquals(enq, 24);
	}
	@Test
	@Order(6)
	@DisplayName("受けとったアンケートIDに対応するアンケートが存在しないため0が返る。")
    public void testService005_getAlluser3() {
		Integer enqueteId=100;
		Integer enq= enqueteAnswerStatusViewService.getAlluser3(enqueteId);
		assertThat(enq,is(0));
	}

	//⑤部署ごとに所属しているアンケート未回答者の総数を返す。public List<Integer> getUnans5(Integer enqueteId)
	@Test
	@Order(7)
	@DisplayName("アンケートIDに対応した各部署に所属する未回答者の総数が返る。")
    public void testService006_getUnans5() {
		Integer enqueteId=1;
		List<Integer> enq= enqueteAnswerStatusViewService.getUnans5(enqueteId);
		assertEquals(enq.get(0), 0);
		assertEquals(enq.get(1), 2);
		assertEquals(enq.get(2), 2);
		assertEquals(enq.get(3), 2);
		assertEquals(enq.get(4), 2);
		assertEquals(enq.get(5), 2);
		assertEquals(enq.get(6), 2);
		assertEquals(enq.get(7), 1);
		assertEquals(enq.get(8), 2);
		assertEquals(enq.get(9), 2);
		assertEquals(enq.get(10), 3);
	}
	@Test
	@Order(8)
	@DisplayName("受けとったenqueteIdに対応するアンケートが存在しないため空が返る。")
    public void testService007_getUnans5() {
		Integer enqueteId=100;
		List<Integer> enq= enqueteAnswerStatusViewService.getUnans5(enqueteId);
		assertThat(enq,is(empty()));
	}

//⑥部署ごとに所属しているユーザーの総数を返す。public List<Integer> getUser6(Integer enqueteId)
	@Test
	@Order(9)
	@DisplayName("アンケートIDに対応した各部署に所属する未回答者の総数が返る。")
	public void testService008_getUser6(){
		Integer enqueteId=1;
		List<Integer> enq=enqueteAnswerStatusViewService.getUser6(enqueteId);
		assertThat(enq.get(0)	,is(3));
		assertThat(enq.get(1)	,is(2));
		assertThat(enq.get(2)	,is(2));
		assertThat(enq.get(3)	,is(2));
		assertThat(enq.get(4)	,is(2));
		assertThat(enq.get(5)	,is(2));
		assertThat(enq.get(6)	,is(2));
		assertThat(enq.get(7)	,is(2));
		assertThat(enq.get(8)	,is(2));
		assertThat(enq.get(9)	,is(2));
		assertThat(enq.get(10)	,is(3));

	}
	@Test
	@Order(10)
	@DisplayName("受けとったenqueteIdに対応するアンケートが存在しないため空が返る。")
	public void testService009_getUser6() {
		Integer enqueteId=100;
		List<Integer> enq= enqueteAnswerStatusViewService.getUser6(enqueteId);
		assertThat(enq,is(empty()));
	}

//⑧アンケート対象部署のIDと部署名リスト返す。public List<Dept>getEnqueteAnsDeptList8(Integer enqueteId)
	@Test
	@Order(11)
	@DisplayName("引数によって指定したアンケート対象部署の部署IDと部署名が返る。")
	public void testService010_getEnqueteAnsDeptList8() {
	Integer enqueteId=1;
	List<Dept> enq= enqueteAnswerStatusViewService.getEnqueteAnsDeptList8(enqueteId);

	assertThat(enq.get(0).getDeptId(),is("1"));
	assertThat(enq.get(0).getDeptName(),is("監査室"));
	assertThat(enq.get(1).getDeptId(),is("2"));
	assertThat(enq.get(1).getDeptName(),is("経営企画本部"));
	assertThat(enq.get(2).getDeptId(),is("3"));
	assertThat(enq.get(2).getDeptName(),is("コーポレート本部"));
	assertThat(enq.get(3).getDeptId(),is("4"));
	assertThat(enq.get(3).getDeptName(),is("Xイノベーション本部"));
	assertThat(enq.get(4).getDeptId(),is("5"));
	assertThat(enq.get(4).getDeptName(),is("事業推進室"));
	assertThat(enq.get(5).getDeptId(),is("6"));
	assertThat(enq.get(5).getDeptName(),is("金融ソリューション事業部"));
	assertThat(enq.get(6).getDeptId(),is("7"));
	assertThat(enq.get(6).getDeptName(),is("グループ経営ソリューション事業部"));
	assertThat(enq.get(7).getDeptId(),is("8"));
	assertThat(enq.get(7).getDeptName(),is("HCM事業部"));
	assertThat(enq.get(8).getDeptId(),is("9"));
	assertThat(enq.get(8).getDeptName(),is("製造ソリューション事業部"));
	assertThat(enq.get(9).getDeptId(),is("10"));
	assertThat(enq.get(9).getDeptName(),is("コミュニケーションIT事業部"));
	assertThat(enq.get(10).getDeptId(),is("11"));
	assertThat(enq.get(10).getDeptName(),is("エンタープライズIT事業部"));
	}
	@Test
	@Order(12)
	@DisplayName("受け取ったenqueteIdに対応する部署が存在しない場合空が返る。")
	public void testService011_getEnqueteAnsDeptList8() {
		Integer enqueteId=100;
		List<Dept> enq= enqueteAnswerStatusViewService.getEnqueteAnsDeptList8(enqueteId);
		assertThat(enq,is(empty()));
	}

//⑨アンケートIDからアンケート名を返すよpublic String sgetEnqueteName9(Integer enqueteId)
	@Test
	@Order(13)
	@DisplayName("引数によって指定したアンケート名が返る。")
	public void testService012_sgetEnqueteName9() {
	Integer enqueteId=1;
	String enq= enqueteAnswerStatusViewService.sgetEnqueteName9(enqueteId);
	assertEquals(enq, "2016年度 定期健診・人間ドック 受診前問診票");
	}
	@Test
	@Order(14)
	@DisplayName("受け取ったenqueteIdに対応するアンケートが存在しない場合nullが返る。")
	public void testService013_sgetEnqueteName9() {
		Integer enqueteId=100;
		String enq= enqueteAnswerStatusViewService.sgetEnqueteName9(enqueteId);
		assertThat(enq,is(nullValue()));
	}

}
