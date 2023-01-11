package jp.co.isid.advtraining.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.list.EnqueteListService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteListServiceTest {

	@Autowired
	private EnqueteListService enqueteListService;

	@Test
	@Order(1)
	@DisplayName("該当するESQ-IDの未回答アンケート情報をリストとして取得できる")
	public void testService000_getNoAnswerList() {
		List<Enquete> test = new ArrayList<Enquete>();
		test = enqueteListService.getNoAnswerList("li9010");

		List<Enquete> expected = new ArrayList<>();

		Enquete A = new Enquete();
		A.setEnqueteId(39);
		A.setEnqueteName("映画に関するアンケート");
		A.setEnqueteStateId(3);
		expected.add(A);

		int i = 0;
		for (Enquete enquete : test) {
			assertEquals(enquete.getEnqueteId(), expected.get(i).getEnqueteId());
			assertEquals(enquete.getEnqueteName(), expected.get(i).getEnqueteName());
			assertEquals(enquete.getEnqueteStateId(), expected.get(i).getEnqueteStateId());
			i++;
		}
	}

	@Test
	@Order(2)
	@DisplayName("該当するESQ-IDの回答済みアンケート情報をリストとして取得できる")
	public void testService001_getCompleteAnswerList() {
		List<Enquete> test = new ArrayList<Enquete>();
		test = enqueteListService.getCompleteAnswerList("li9010");

		List<Enquete> expected = new ArrayList<>();

		Enquete A = new Enquete();
		A.setEnqueteId(2);
		A.setEnqueteName("2016年度 クラウドコンピューティングの利用に関するアンケート調査");
		A.setEnqueteStateId(4);
		expected.add(A);

		Enquete B = new Enquete();
		B.setEnqueteId(1);
		B.setEnqueteName("2016年度 定期健診・人間ドック 受診前問診票");
		B.setEnqueteStateId(3);
		expected.add(B);

		int i = 0;
		for (Enquete enquete : test) {
			assertEquals(enquete.getEnqueteId(), expected.get(i).getEnqueteId());
			assertEquals(enquete.getEnqueteName(), expected.get(i).getEnqueteName());
			assertEquals(enquete.getEnqueteStateId(), expected.get(i).getEnqueteStateId());
			i++;
		}
	}

	@Test
	@Order(3)
	@DisplayName("該当するESQ-IDの実施前アンケート情報をリストとして取得できる")
	public void testService002_getBeforeEnqueteList() {
		List<Enquete> test = new ArrayList<Enquete>();
		test = enqueteListService.getBeforeEnqueteList("li9015");

		List<Enquete> expected = new ArrayList<>();

		Enquete A = new Enquete();
		A.setEnqueteId(4);
		A.setEnqueteName("2016年度 裁量労働制に関する社内アンケート");
		A.setEnqueteStateId(1);
		expected.add(A);

		int i = 0;
		for (Enquete enquete : test) {
			assertEquals(enquete.getEnqueteId(), expected.get(i).getEnqueteId());
			assertEquals(enquete.getEnqueteName(), expected.get(i).getEnqueteName());
			assertEquals(enquete.getEnqueteStateId(), expected.get(i).getEnqueteStateId());
			i++;
		}
	}

	@Test
	@Order(4)
	@DisplayName("該当するESQ-IDの実施中アンケート情報をリストとして取得できる")
	public void testService003_getStartEnqueteList() {
		List<Enquete> test = new ArrayList<Enquete>();
		test = enqueteListService.getStartEnqueteList("li9010");

		List<Enquete> expected = new ArrayList<>();

		Enquete A = new Enquete();
		A.setEnqueteId(40);
		A.setEnqueteName("TexAIntelligenceについて");
		A.setEnqueteStateId(3);
		expected.add(A);

		Enquete B = new Enquete();
		B.setEnqueteId(1);
		B.setEnqueteName("2016年度 定期健診・人間ドック 受診前問診票");
		B.setEnqueteStateId(3);
		expected.add(B);

		Enquete C = new Enquete();
		C.setEnqueteId(6);
		C.setEnqueteName("ビジネススキルアップセミナー(bss)実施に関するアンケート(ビジネスソリューション事業部限定)");
		C.setEnqueteStateId(3);
		expected.add(C);

		Enquete D = new Enquete();
		D.setEnqueteId(39);
		D.setEnqueteName("映画に関するアンケート");
		D.setEnqueteStateId(3);
		expected.add(D);

		int i = 0;
		for (Enquete enquete : test) {
			assertEquals(enquete.getEnqueteId(), expected.get(i).getEnqueteId());
			assertEquals(enquete.getEnqueteName(), expected.get(i).getEnqueteName());
			assertEquals(enquete.getEnqueteStateId(), expected.get(i).getEnqueteStateId());
			i++;
		}
	}

	@Test
	@Order(5)
	@DisplayName("該当するESQ-IDの実施完了アンケート情報をリストとして取得できる")
	public void testService004_getFinishEnqueteList() {
		List<Enquete> test = new ArrayList<Enquete>();
		test = enqueteListService.getFinishEnqueteList("li9010");

		List<Enquete> expected = new ArrayList<>();

		Enquete A = new Enquete();
		A.setEnqueteId(2);
		A.setEnqueteName("2016年度 クラウドコンピューティングの利用に関するアンケート調査");
		A.setEnqueteStateId(4);
		expected.add(A);

		Enquete B = new Enquete();
		B.setEnqueteId(5);
		B.setEnqueteName("情報セキュリティに関する緊急意識調査");
		B.setEnqueteStateId(4);
		expected.add(B);

		int i = 0;
		for (Enquete enquete : test) {
			assertEquals(enquete.getEnqueteId(), expected.get(i).getEnqueteId());
			assertEquals(enquete.getEnqueteName(), expected.get(i).getEnqueteName());
			assertEquals(enquete.getEnqueteStateId(), expected.get(i).getEnqueteStateId());
			i++;
		}
	}

	@Test
	@Order(6)
	@DisplayName("排他制御されているとき、nullを返す")
	public void testService005_checkList() {

		Integer enqueteStateIdCre[] = { 1, 2 };
		String test = enqueteListService.checkList(3, enqueteStateIdCre, "li9010");

		String expected = null;

		assertEquals(test, expected);
	}

	@Test
	@Order(7)
	@DisplayName("アンケートが存在していない場合、エラーメッセージを返す")
	public void testService006_checkList() {

		Integer enqueteStateIdCre[] = { 1, 2 };
		String test = enqueteListService.checkList(100, enqueteStateIdCre, "li9010");

		String expected = "アンケートは削除されています。";

		assertEquals(test, expected);
	}

	@Test
	@Order(8)
	@DisplayName("アンケート状況が一致していない場合は、エラーメッセージを返す")
	public void testService007_checkList() {

		Integer enqueteStateIdCre[] = { 1, 2 };
		String test = enqueteListService.checkList(1, enqueteStateIdCre, "li9010");

		String expected = "他のユーザによりアンケート状況が更新されました。対象アンケートの状態を再度ご確認ください。";

		assertEquals(test, expected);
	}

	@Test
	@Order(9)
	@DisplayName("管理者権限が剝奪されている場合、エラーメッセージを返す")
	public void testService008_checkList() {

		Integer enqueteStateIdCre[] = { 1, 2 };
		String test = enqueteListService.checkList(3, enqueteStateIdCre, "li9012");

		String expected = "管理者権限がありません。";

		assertEquals(test, expected);
	}

	@Test
	@Order(10)
	@DisplayName("アンケートIDの該当情報を削除できる")
	public void testService009_deleteEnquete() {

		boolean test = true;
		test = enqueteListService.deleteEnquete(4);

		boolean expected = true;

		assertEquals(test, expected);
	}

	@Test
	@Order(11)
	@DisplayName("特定のアンケートIDが見つからず削除できない")
	public void testService010_deleteEnquete() {

		boolean ret = true;
		ret = enqueteListService.deleteEnquete(100);

		boolean expected = false;

		assertEquals(ret, expected);
	}

	@Test
	@Order(12)
	@DisplayName("特定のアンケートの開始日付とアンケート状況を更新できる")
	public void testService0011_startEnqueteUpdate() {

		boolean test = true;
		test = enqueteListService.startEnqueteUpdate(3);

		boolean expected = true;

		assertEquals(test, expected);
	}

	@Test
	@Order(13)
	@DisplayName("特定のアンケートIDが見つからず更新できない")
	public void testService0012_startEnqueteUpdate() {

		boolean test = true;
		test = enqueteListService.startEnqueteUpdate(100);

		boolean expected = false;

		assertEquals(test, expected);
	}

	@Test
	@Order(14)
	@DisplayName("特定のアンケートの終了日付とアンケート状況を更新できる")
	public void testService0013_finishEnqueteUpdate() {

		boolean test = true;
		test = enqueteListService.finishEnqueteUpdate(3);

		boolean expected = true;

		assertEquals(test, expected);
	}

	@Test
	@Order(15)
	@DisplayName("特定のアンケートIDが見つからず更新できない")
	public void testService0014_finishEnqueteUpdate() {

		boolean test = true;
		test = enqueteListService.finishEnqueteUpdate(100);

		boolean expected = false;

		assertEquals(test, expected);
	}
}
