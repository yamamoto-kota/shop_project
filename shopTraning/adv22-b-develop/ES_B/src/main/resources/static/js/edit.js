function changeQuestionType(obj){
	let questionLoop=obj.getAttribute('questionLoop');//questionLoopを取得
	questionLoop=parseInt(questionLoop);

	let elementChoiceCount=document.getElementById("choiceCount"+questionLoop);//choiceCountを取得
	let choiceCount=elementChoiceCount.getAttribute('choiceCount');
	choiceCount=parseInt(choiceCount);

	let selected = obj.selectedIndex;//選択インデックスを取得(単一0,複数1,記述2)

	//alert("questionLoop="+questionLoop+",choiceCount="+choiceCount+",selected="+selected);

	//choiceAreaQ内を消す
	let elementParent=document.getElementById("choiceArea"+questionLoop);
	elementParent.innerHTML = '';

	//単一か複数だったら新規入力フォーム2個挿入
	if(selected==0||selected==1){
		elementParent.innerHTML =


/*	//▽ここから初期選択肢▽
	"<div class='row g-3'>"+
	"<div class='col-auto'>"+
	"<input class='form-control' type='text' size='120' placeholder='選択肢' id='choiceText"+(questionLoop+1)+"in0' name='questionFormList["+(questionLoop+1)+"].choiceFormList[0].choiceText' value=''>"+
	"</div>"+
	//ボタンが追加
	"<div class='col-auto'>"+
	"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+(questionLoop+1)+" choiceLoop="+0+" id='removeChoiceBtn"+(questionLoop+1)+"in"+0+"''>－</button>"+
	"</div>"+
	"</div>"+
	//△ここまで初期選択肢△*/






		"<div class='form-check'>"+
		"<div id='choiceGroup"+questionLoop+"in0'>"+
		"<div class='row g-3'>"+
		"<div class='col-auto'>"+
		"<input type='hidden' id='choiceNumber"+questionLoop+"in0' name='questionFormList["+questionLoop+"].choiceFormList[0].choiceNumber' value='1'>"+
		"<input class='form-control' type='text' size='120' maxlength='100' placeholder='選択肢' id='choiceText"+questionLoop+"in0' name='questionFormList["+questionLoop+"].choiceFormList[0].choiceText' value=''>"+
		"</div>"+
		//ボタンが追加
		"<div class='col-auto'>"+
		"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+(questionLoop)+" choiceLoop="+0+" id='removeChoiceBtn"+(questionLoop)+"in"+0+"''>－</button>"+
		"</div>"+
		"</div>"+
		"</div>"+

		"<div id='choiceGroup"+questionLoop+"in1'>"+
		"<div class='row g-3'>"+
		"<div class='col-auto'>"+
		"<input type='hidden' id='choiceNumber"+questionLoop+"in1' name='questionFormList["+questionLoop+"].choiceFormList[1].choiceNumber' value='2'>"+
		"<input class='form-control' type='text' size='120' maxlength='100' placeholder='選択肢' id='choiceText"+questionLoop+"in1' name='questionFormList["+questionLoop+"].choiceFormList[1].choiceText' value=''>"+
		"</div>"+
		"<div class='col-auto'>"+
		"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+(questionLoop)+" choiceLoop="+1+" id='removeChoiceBtn"+(questionLoop)+"in"+1+"''>－</button>"+
		"</div>"+
		"</div>"+
		"</div>"+

		"<div id='addedChoiceArea"+questionLoop+"'></div>"+
		"</div>"+
		"<br><button type='button' value='+' onclick='addChoice(this)' questionloop='"+questionLoop+"' id='addChoiceBtn"+questionLoop+"'>+&nbsp;選択肢の追加</button><br><br>"+

		"</div>";

		//choicecountを2に
		elementChoiceCount.setAttribute("choiceCount",2);
	}
	else{
		elementChoiceCount.setAttribute("choiceCount",0);
	}




}



function addChoice(obj){
	questionLoop=obj.getAttribute('questionLoop');

	let elementChoiceCount = document.getElementById("choiceCount"+questionLoop);
	let choiceCount=elementChoiceCount.getAttribute('choiceCount');
	choiceCount=parseInt(choiceCount);

	let ele = document.createElement('div');
	ele.id ="choiceGroup"+questionLoop+"in"+choiceCount;
	ele.innerHTML =


	"<div class='row g-3'>"+
		"<div class='col-auto'>"+
		"<input type='hidden' id='choiceNumber"+questionLoop+"in"+choiceCount+"' name='questionFormList["+questionLoop+"].choiceFormList["+choiceCount+"].choiceNumber' value='"+(choiceCount+1)+"'>"+
		"<input  class='form-control' type='text' size='120' maxlength='100' placeholder='選択肢' name='questionFormList["+questionLoop+"].choiceFormList["+choiceCount+"].choiceText' id='choiceText"+questionLoop+"in"+choiceCount+"'> "+


		"</div>"+
		//<!-- エラーメッセージ -->
		//<!-- ▽削除ボタン▽ -->
		"<div class='col-auto'>"+
		"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+questionLoop+" choiceLoop="+choiceCount+" id='removeChoiceBtn"+questionLoop+"in"+choiceCount+"''>－</button>"+
		"</div>"+
		"<br>"+
		//<!-- △削除ボタン△ -->

	"</div>";

	let elementAddedChoiceArea = document.getElementById("addedChoiceArea"+questionLoop);
 	elementAddedChoiceArea.appendChild(ele);

	choiceCount = choiceCount+1;

	elementChoiceCount.setAttribute("choiceCount",choiceCount);

}


function removeChoice(obj){

	//questionLoopとchoiceLoopを取得
	let questionLoop=obj.getAttribute('questionLoop');//削除ボタンに付属のquestionLoopを取得
	let choiceLoop=obj.getAttribute('choiceLoop');//削除ボタンに付属のchoiceLoopを取得

	//choiceCountを取得
	let x1="choiceCount"+questionLoop;
	let elementChoiceCount = document.getElementById(x1);
	let choiceCount=elementChoiceCount.getAttribute('choiceCount');

    if(choiceCount > 2){



	//choiceGroupを消去
	let x2="choiceGroup"+questionLoop+"in"+choiceLoop;
	let elementRemoveChoiceGroup = document.getElementById(x2);
	elementRemoveChoiceGroup.remove();//choiceGroupを消す

	//後続-1処理
	choiceLoop=parseInt(choiceLoop);
	choiceCount=parseInt(choiceCount);
	for(let i=choiceLoop+1;i<choiceCount;i++){

		//choiceGroupQinCのidの変更
		let x3="choiceGroup"+questionLoop+"in"+i;
		let elementFollowingChoiceGroup = document.getElementById(x3);//後続のchoiceGroupタグを取得
		let x4="choiceGroup"+questionLoop+"in"+(i-1);
		elementFollowingChoiceGroup.setAttribute("id",x4);//後続のchoiceGroupに-1データを挿入

		//choiceTextQinC(入力フォーム)のidとnameを変更
		let x5="choiceText"+questionLoop+"in"+i;
		let elementFollowingChoiceText = document.getElementById(x5);//後続のchoiceTextタグを取得
		let x6="choiceText"+questionLoop+"in"+(i-1);
		elementFollowingChoiceText.setAttribute("id",x6);//後続のchoiceTextのidに-1データを挿入
		let x7="questionFormList["+questionLoop+"].choiceFormList["+(i-1)+"].choiceText";
		elementFollowingChoiceText.setAttribute("name",x7);//後続のchoiceTextのnameに-1データを挿入

		//removeChoiceBtnQinC(削除ボタン)のchoiceLoopとidを変更
		let x8="removeChoiceBtn"+questionLoop+"in"+i;
		let elementFollowingRemoveChoiceBtn = document.getElementById(x8);//後続のremoveChoiceBtnタグを取得
		let x9="removeChoiceBtn"+questionLoop+"in"+(i-1);
		elementFollowingRemoveChoiceBtn.setAttribute("id",x9);//後続のremoveChoiceBtnのidに-1データを挿入
		elementFollowingRemoveChoiceBtn.setAttribute("choiceLoop",i-1);//後続のremoveChoiceBtnのchoiceLoopに-1データを挿入

		//choiceNumberQinC(選択肢番号保持)のidとnameとvalueを変更
		let x10="choiceNumber"+questionLoop+"in"+i;
		let elementFollowingChoiceNumber = document.getElementById(x10);//後続のchoiceNumberタグを取得
		let x11="choiceNumber"+questionLoop+"in"+(i-1);
		elementFollowingChoiceNumber.setAttribute("id",x11);//後続のchoiceNumberのidに-1データを挿入
		let x12="questionFormList["+questionLoop+"].choiceFormList["+(i-1)+"].choiceNumber";
		elementFollowingChoiceNumber.setAttribute("name",x12);//後続のchoiceNumberのnameに-1データを挿入
		elementFollowingChoiceNumber.setAttribute("value",i);//後続のchoiceNumberのvalueに-1データを挿入

	}

	//choiceCountを1引く
	choiceCount=choiceCount-1;
	elementChoiceCount.setAttribute("choiceCount",choiceCount);
	}

}



function addQuestion(obj){

	//questionLoopを取得
	let questionLoop=obj.getAttribute('questionLoop');//質問追加ボタンに付属のquestionLoopを取得

	//questionCountを取得
	let elementQuestionCount = document.getElementById('questionCount');//quetionCountタグを取得
	let questionCount=elementQuestionCount.getAttribute('questionCount');

	//後続+1処理
	questionLoop=parseInt(questionLoop);
	questionCount=parseInt(questionCount);
	for(let i=questionCount-1;i>questionLoop;i--){// id重複を避けるため逆順でループ
		//questionGroupQの変更
		let elementFollowingQuestionGroup=document.getElementById("questionGroup"+i);//タグを取得
		elementFollowingQuestionGroup.setAttribute("id","questionGroup"+(i+1));//idに+1データを挿入

		//questionNumberDispQの変更
		let x1="questionNumberDisp"+i;
		let elementFollowingQuestionNumberDisp = document.getElementById(x1);//後続のquestionNumberDispタグを取得
		let x2="質問"+(i+2);
		elementFollowingQuestionNumberDisp.innerHTML=x2;//後続のquestionNumberDispのspan内テキストを変更
		let x3="questionNumberDisp"+(i+1);
		elementFollowingQuestionNumberDisp.setAttribute("id",x3);//後続のquestionNumberDispのidに+1データを挿入

		//questionNumberQの変更
		let x4="questionNumber"+i;
		let elementFollowingQuestionNumber = document.getElementById(x4);//後続のquestionNumberタグを取得
		let x5="questionNumber"+(i+1);
		elementFollowingQuestionNumber.setAttribute("id",x5);//後続のquestionNumberのidに+1データを挿入
		let x6="questionFormList["+(i+1)+"].questionNumber";
		elementFollowingQuestionNumber.setAttribute("name",x6);//後続のquestionNumberのnameに+1データを挿入
		elementFollowingQuestionNumber.setAttribute("value",(i+2));//後続のquestionNumberのvalueに+1データを挿入

		//requireFlagQの変更
		let x7="requireFlag"+i;
		let elementFollowingRequireFlag = document.getElementById(x7);//後続のquestionNumberタグを取得
		let x8="requireFlag"+(i+1);
		elementFollowingRequireFlag.setAttribute("id",x8);//後続のrequireFlagのidに+1データを挿入
		let x9="questionFormList["+(i+1)+"].requireFlag";
		elementFollowingRequireFlag.setAttribute("name",x9);//後続のrequireFlagのnameに+1データを挿入

		//changeQuestionTypePdQの変更
		let elementFollowingChangeQuestionTypePd = document.getElementById("changeQuestionTypePd"+i);//後続のchangeTypePdタグを取得
		elementFollowingChangeQuestionTypePd.setAttribute("id","changeQuestionTypePd"+(i+1));//idに+1データを挿入
		elementFollowingChangeQuestionTypePd.setAttribute("name","questionFormList["+(i+1)+"].questionTypeId");//nameに+1データを挿入
		elementFollowingChangeQuestionTypePd.setAttribute("questionLoop",(i+1));//questionLoopに+1データを挿入

		//questionTextQを変更
		let x13="questionText"+i;
		let elementFollowingQuestionText = document.getElementById(x13);//後続のquestionTextタグを取得
		let x14="questionText"+(i+1);
		elementFollowingQuestionText.setAttribute("id",x14);//後続のquestionTextのidに+1データを挿入
		let x15="questionFormList["+(i+1)+"].questionText";
		elementFollowingQuestionText.setAttribute("name",x15);//後続のquestionTextのnameに+1データを挿入

		//questionSubtextQを変更
		let x16="questionSubtext"+i;
		let elementFollowingQuestionSubtext = document.getElementById(x16);//後続のquestionTextタグを取得
		let x17="questionSubtext"+(i+1);
		elementFollowingQuestionSubtext.setAttribute("id",x17);//後続のquestionSubtextのidに+1データを挿入
		let x18="questionFormList["+(i+1)+"].questionSubtext";
		elementFollowingQuestionSubtext.setAttribute("name",x18);//後続のquestionSubtextのnameに+1データを挿入

		//choiceAreaQを変更
		let elementFollowingChoiceArea = document.getElementById("choiceArea"+i);//タグを取得
		elementFollowingChoiceArea.setAttribute("id","choiceArea"+(i+1));//idに+1データを挿入

		//選択肢系の変更の為の内部ループ
		let elementCurrentChoiceCount = document.getElementById("choiceCount"+i);
		let currentChoiceCount=elementCurrentChoiceCount.getAttribute('choiceCount');
		//alert("typeofccc:"+typeof(currentChoiceCount));
		for(let j=currentChoiceCount-1;j>=0;j--){
			//alert("i="+i+"j="+j);
			//choiceGroupQinCを変更
			let elementFollowingChoiceGroup = document.getElementById("choiceGroup"+i+"in"+j);//タグ取得
			elementFollowingChoiceGroup.setAttribute("id","choiceGroup"+(i+1)+"in"+j);//id挿入

			//choiceNumberQinCを変更
			let elementFollowingChoiceNumber = document.getElementById("choiceNumber"+i+"in"+j);//タグ取得
			elementFollowingChoiceNumber.setAttribute("id","choiceNumber"+(i+1)+"in"+j);//id挿入
			elementFollowingChoiceNumber.setAttribute("name","questionFormList["+(i+1)+"].choiceFormList["+j+"].choiceNumber");//name挿入

			//choiceTextQinCを変更
			let elementFollowingChoiceText = document.getElementById("choiceText"+i+"in"+j);//タグ取得
			elementFollowingChoiceText.setAttribute("id","choiceText"+(i+1)+"in"+j);//id挿入
			elementFollowingChoiceText.setAttribute("name","questionFormList["+(i+1)+"].choiceFormList["+j+"].choiceText");//name挿入

			//if(j>1){
			//removeChoiceBtnQinCを変更
			let elementFollowingRemoveChoiceBtn = document.getElementById("removeChoiceBtn"+i+"in"+j);//タグ取得
			elementFollowingRemoveChoiceBtn.setAttribute("id","removeChoiceBtn"+(i+1)+"in"+j);//id挿入
			elementFollowingRemoveChoiceBtn.setAttribute("questionLoop",(i+1));//questionLoop挿入
			//}
		}

		if(currentChoiceCount!=0){//typeによる
			//addedChoiceAreaQのidを変更
			let elementFollowingAddedChoiceArea = document.getElementById("addedChoiceArea"+i);//タグ取得
			elementFollowingAddedChoiceArea.setAttribute("id","addedChoiceArea"+(i+1));//id挿入

			//addChoiceBtnのid,questionLoopを変更
			let elementFollowingAddChoiceBtn = document.getElementById("addChoiceBtn"+i);//タグ取得
			elementFollowingAddChoiceBtn.setAttribute("id","addChoiceBtn"+(i+1));//id挿入
			elementFollowingAddChoiceBtn.setAttribute("questionLoop",(i+1));//id挿入
		}

		//choiceCountQのidを変更
		let elementFollowingChoiceCount = document.getElementById("choiceCount"+i);//タグ取得
		elementFollowingChoiceCount.setAttribute("id","choiceCount"+(i+1));//id挿入
		//elementFollowingChoiceCount.setAttribute("choiceCount",currentChoiceCount);//choiceCount挿入→よく考えたらしなくていいじゃん

		//addQuestionBtnQのidとquestionLoopを変更
		let elementFollowingAddQuestionBtn = document.getElementById("addQuestionBtn"+i);//タグ取得
		elementFollowingAddQuestionBtn.setAttribute("id","addQuestionBtn"+(i+1));//id挿入
		elementFollowingAddQuestionBtn.setAttribute("questionLoop",(i+1));//questionLoop挿入

		//removeQuestionBtnAreaQのidを変更
		let elementFollowingRemoveQuestionBtnArea = document.getElementById("removeQuestionBtnArea"+i);//タグ取得
		elementFollowingRemoveQuestionBtnArea.setAttribute("id","removeQuestionBtnArea"+(i+1));//id挿入
		elementFollowingRemoveQuestionBtnArea.setAttribute("questionLoop",(i+1));//questionLoop挿入

		//removeQuestionBtnQのidとquestionLoopを変更
		let elementFollowingRemoveQuestionBtn = document.getElementById("removeQuestionBtn"+i);//タグ取得
		elementFollowingRemoveQuestionBtn.setAttribute("id","removeQuestionBtn"+(i+1));//id挿入
		elementFollowingRemoveQuestionBtn.setAttribute("questionLoop",(i+1));//questionLoop挿入

		//addedQuestionAreaQを変更
		let elementFollowingAddedQuestionArea = document.getElementById("addedQuestionArea"+i);
		elementFollowingAddedQuestionArea.setAttribute("id","addedQuestionArea"+(i+1));

	}//後続処理終了

	//addedQuestionAreaに質問フォームを挿入
	let ele = document.createElement('div');//残骸を消したい→消した
	ele.id ="questionGroup"+(questionLoop+1);

	ele.innerHTML =
	"<div style='display: inline-block; padding: 10px; margin-bottom: 10px; border: 1px solid #333333; border-radius: 10px;'>"+
	"<input type='hidden' id='questionNumber"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionNumber' value='"+(questionLoop+2)+"'>"+

	"<font color='#808080'><span id='questionNumberDisp"+(questionLoop+1)+"'>質問"+(questionLoop+2)+"</span></font>"+

	"<input type='checkbox' value='1' id='requireFlag"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].requireFlag'>回答必須<br>"+
	//謎の付随をここに追加予定
	"<input type='text' style='font-size: 100%' class='form-control' size='130' maxlength='100' placeholder='質問内容' id='questionText"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionText' value=''><br>"+
	"<input type='text' class='form-control' style='font-size: 100%' placeholder='質問内容の補足説明' id='questionSubtext"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionSubtext' value=''><br>"+

	//"<input type='text' id='questionText"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionText' value=''><br><br>"+
	//"<input type='text' id='questionSubtext"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionSubtext' value=''><br><br>"+

	"<font color='#808080'>質問形式&nbsp;(変更すると選択肢が消えます)</font><br>" +
	"<select onchange='changeQuestionType(this)' id='changeQuestionTypePd"+(questionLoop+1)+"' questionLoop='"+(questionLoop+1)+"' name='questionFormList["+(questionLoop+1)+"].questionTypeId'>"+
		"<option value='1' selected='selected'>単一選択</option>"+
		"<option value='2'>複数選択</option>"+
		"<option value='3'>自由記述</option>"+
	"</select><br><br>"+

	"<div id='choiceArea"+(questionLoop+1)+"'>"+

	"<div class='form-check'>"+
	"<div id='choiceGroup"+(questionLoop+1)+"in0'>"+
	"<input type='hidden' id='choiceNumber"+(questionLoop+1)+"in0' name='questionFormList["+(questionLoop+1)+"].choiceFormList[0].choiceNumber' value='1'>"+



	//▽ここから初期選択肢▽
	"<div class='row g-3'>"+
	"<div class='col-auto'>"+
	"<input class='form-control' type='text' size='120' maxlength='100' placeholder='選択肢' id='choiceText"+(questionLoop+1)+"in0' name='questionFormList["+(questionLoop+1)+"].choiceFormList[0].choiceText' value=''>"+
	"</div>"+
	//ボタンが追加
	"<div class='col-auto'>"+
	"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+(questionLoop+1)+" choiceLoop="+0+" id='removeChoiceBtn"+(questionLoop+1)+"in"+0+"''>－</button>"+
	"</div>"+
	"</div>"+
	//△ここまで初期選択肢△

	"</div>"+


	"<div id='choiceGroup"+(questionLoop+1)+"in1'>"+
	"<input type='hidden' id='choiceNumber"+(questionLoop+1)+"in1' name='questionFormList["+(questionLoop+1)+"].choiceFormList[1].choiceNumber' value='2'>"+

	"<div class='row g-3'>"+
	"<div class='col-auto'>"+
	"<input class='form-control' type='text' size='120' maxlength='100' placeholder='選択肢' id='choiceText"+(questionLoop+1)+"in1' name='questionFormList["+(questionLoop+1)+"].choiceFormList[1].choiceText' value=''>"+
	"</div>"+
	//ボタンが追加
	"<div class='col-auto'>"+
	"<button class='btn btn-danger rounded-pill btn-sm'  type='button' onclick='removeChoice(this)' questionLoop="+(questionLoop+1)+" choiceLoop="+1+" id='removeChoiceBtn"+(questionLoop+1)+"in"+1+"''>－</button>"+
	"</div>"+
	"</div>"+

	"</div>"+


	"<div id='addedChoiceArea"+(questionLoop+1)+"'></div>"+
	"</div>"+

	"<br><button type='button' value='+' onclick='addChoice(this)' questionloop='"+(questionLoop+1)+"' id='addChoiceBtn"+(questionLoop+1)+"'>+&nbsp;選択肢の追加</button><br><br>"+


	"</div>"+

	"<input type='hidden' choiceCount='2' id='choiceCount"+(questionLoop+1)+"'>"+
	"<hr color='#C0C0C0'>"+

	//"質問追加<input type='button' value='+' onclick='addQuestion(this)' id='addQuestionBtn"+(questionLoop+1)+"' questionLoop='"+(questionLoop+1)+"'>"+
	"<button class='btn btn-primary' type='button' onclick='addQuestion(this)' id='addQuestionBtn"+(questionLoop+1)+"' questionLoop='"+(questionLoop+1)+"'>質問の追加</button>&nbsp;&emsp;"+

	"<span id='removeQuestionBtnArea"+(questionLoop+1)+"'>"+
	//"質問削除<input type='button' value='-' onclick='removeQuestion(this)' id='removeQuestionBtn"+(questionLoop+1)+"' questionLoop='"+(questionLoop+1)+"'></div><br>"+
	"<button class='btn btn-outline-danger' type='button' onclick='removeQuestion(this)' id='removeQuestionBtn"+(questionLoop+1)+"' questionLoop='"+(questionLoop+1)+"'>質問の削除</button><span>"+
 	"</div>"+

	"</div><br><br>";

	//let parent_object = document.getElementById("addedQuestionArea"+questionLoop);
 	//parent_object.appendChild(ele);
 	//parent_object.prepend(ele);
 	//入れ子でなくafterでエリアの直後に入れる

	let elementArea = document.getElementById("addedQuestionArea"+questionLoop);
	elementArea.after(ele);

	let ele2 = document.createElement('div');
	ele2.id ="addedQuestionArea"+(questionLoop+1);

	let elementCurrentQuestionGroup = document.getElementById("questionGroup"+(questionLoop+1));
	elementCurrentQuestionGroup.after(ele2);

 	//questionCountを+1する
	questionCount=parseInt(questionCount);
	questionCount = questionCount+1;
	elementQuestionCount.setAttribute("questionCount",questionCount);


	if(questionCount>=2){
		let elementDispRemoveQuestionBtnArea = document.getElementById('removeQuestionBtnArea0');//QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
		elementDispRemoveQuestionBtnArea.innerHTML=
		//"質問削除<input type='button' value='-' onclick='removeQuestion(this)' id='removeQuestionBtn0' questionLoop='0'>";
		"<button class='btn btn-outline-danger' type='button' onclick='removeQuestion(this)' id='removeQuestionBtn0' questionLoop='0'>質問の削除</button>";
	}


}


function removeQuestion(obj){

	//questionLoopを取得
	let questionLoop=obj.getAttribute('questionLoop');//質問追加ボタンに付属のquestionLoopを取得

	//questionCountを取得
	let elementQuestionCount = document.getElementById('questionCount');//quetionCountタグを取得
	let questionCount=elementQuestionCount.getAttribute('questionCount');


	//ずらしの理論に気づいたので、前に持ってきた
	//questionGroupQを消去
	let elementRemoveQuestionGroup = document.getElementById("questionGroup"+questionLoop);
	elementRemoveQuestionGroup.remove();

	//addedQuestionAreaQを消去
	let elementRemoveAddedQuestionArea = document.getElementById("addedQuestionArea"+questionLoop);//エリア削除してなかった
	elementRemoveAddedQuestionArea.remove();
	//ずらしの理論


	//後続-1処理
	questionLoop=parseInt(questionLoop);
	questionCount=parseInt(questionCount);
	for(let i=questionLoop+1;i<questionCount;i++){//昇順でループ
		//questionGroupQの変更
		let elementFollowingQuestionGroup=document.getElementById("questionGroup"+i);//タグを取得
		elementFollowingQuestionGroup.setAttribute("id","questionGroup"+(i-1));//-1データを挿入

		//questionNumberDispQの変更
		let x1="questionNumberDisp"+i;
		let elementFollowingQuestionNumberDisp = document.getElementById(x1);//後続のquestionNumberDispタグを取得
		let x2="質問"+i;
		elementFollowingQuestionNumberDisp.innerHTML=x2;//後続のquestionNumberDispのspan内テキストを変更
		let x3="questionNumberDisp"+(i-1);
		elementFollowingQuestionNumberDisp.setAttribute("id",x3);//後続のquestionNumberDispのidに-1データを挿入

		//questionNumberQの変更
		let x4="questionNumber"+i;
		let elementFollowingQuestionNumber = document.getElementById(x4);//後続のquestionNumberタグを取得
		let x5="questionNumber"+(i-1);
		elementFollowingQuestionNumber.setAttribute("id",x5);//後続のquestionNumberのidに-1データを挿入
		let x6="questionFormList["+(i-1)+"].questionNumber";
		elementFollowingQuestionNumber.setAttribute("name",x6);//後続のquestionNumberのnameに-1データを挿入
		elementFollowingQuestionNumber.setAttribute("value",i);//後続のquestionNumberのvalueに-1データを挿入

		//requireFlagQの変更
		let x7="requireFlag"+i;
		let elementFollowingRequireFlag = document.getElementById(x7);//後続のquestionNumberタグを取得
		let x8="requireFlag"+(i-1);
		elementFollowingRequireFlag.setAttribute("id",x8);//後続のrequireFlagのidに-1データを挿入
		let x9="questionFormList["+(i-1)+"].requireFlag";
		elementFollowingRequireFlag.setAttribute("name",x9);//後続のrequireFlagのnameに-1データを挿入

		//changeQuestionTypePdQの変更
		let elementFollowingChangeQuestionTypePd = document.getElementById("changeQuestionTypePd"+i);//後続のchangeTypePdタグを取得
		elementFollowingChangeQuestionTypePd.setAttribute("id","changeQuestionTypePd"+(i-1));//idに-1データを挿入
		elementFollowingChangeQuestionTypePd.setAttribute("name","questionFormList["+(i-1)+"].questionTypeId");//nameに-1データを挿入
		elementFollowingChangeQuestionTypePd.setAttribute("questionLoop",(i-1));//questionLoopに-1データを挿入

		//questionTextQを変更
		let x13="questionText"+i;
		let elementFollowingQuestionText = document.getElementById(x13);//後続のquestionTextタグを取得
		let x14="questionText"+(i-1);
		elementFollowingQuestionText.setAttribute("id",x14);//後続のquestionTextのidに-1データを挿入
		let x15="questionFormList["+(i-1)+"].questionText";
		elementFollowingQuestionText.setAttribute("name",x15);//後続のquestionTextのnameに-1データを挿入

		//questionSubtextQを変更
		let x16="questionSubtext"+i;
		let elementFollowingQuestionSubtext = document.getElementById(x16);//後続のquestionTextタグを取得
		let x17="questionSubtext"+(i-1);
		elementFollowingQuestionSubtext.setAttribute("id",x17);//後続のquestionSubtextのidに-1データを挿入
		let x18="questionFormList["+(i-1)+"].questionSubtext";
		elementFollowingQuestionSubtext.setAttribute("name",x18);//後続のquestionSubtextのnameに-1データを挿入

		//choiceAreaQを変更
		let elementFollowingChoiceArea = document.getElementById("choiceArea"+i);//タグを取得
		elementFollowingChoiceArea.setAttribute("id","choiceArea"+(i-1));//idに-1データを挿入

		//選択肢系の変更の為の内部ループ
		let elementCurrentChoiceCount = document.getElementById("choiceCount"+i);
		let currentChoiceCount=elementCurrentChoiceCount.getAttribute('choiceCount');
		for(let j=0;j<currentChoiceCount;j++){
			//choiceGroupQinCを変更
			let elementFollowingChoiceGroup = document.getElementById("choiceGroup"+i+"in"+j);//タグ取得
			elementFollowingChoiceGroup.setAttribute("id","choiceGroup"+(i-1)+"in"+j);//id挿入

			//choiceNumberQinCを変更
			let elementFollowingChoiceNumber = document.getElementById("choiceNumber"+i+"in"+j);//タグ取得
			elementFollowingChoiceNumber.setAttribute("id","choiceNumber"+(i-1)+"in"+j);//id挿入
			elementFollowingChoiceNumber.setAttribute("name","questionFormList["+(i-1)+"].choiceFormList["+j+"].choiceNumber");//name挿入

			//choiceTextQinCを変更
			let elementFollowingChoiceText = document.getElementById("choiceText"+i+"in"+j);//タグ取得
			elementFollowingChoiceText.setAttribute("id","choiceText"+(i-1)+"in"+j);//id挿入
			elementFollowingChoiceText.setAttribute("name","questionFormList["+(i-1)+"].choiceFormList["+j+"].choiceText");//name挿入

			//if(j>1){
			//removeChoiceBtnQinCを変更
			let elementFollowingRemoveChoiceBtn = document.getElementById("removeChoiceBtn"+i+"in"+j);//タグ取得
			elementFollowingRemoveChoiceBtn.setAttribute("id","removeChoiceBtn"+(i-1)+"in"+j);//id挿入
			elementFollowingRemoveChoiceBtn.setAttribute("questionLoop",(i-1));//questionLoop挿入
			//}
		}

		if(currentChoiceCount!=0){
			//addedChoiceAreaQのidを変更
			let elementFollowingAddedChoiceArea = document.getElementById("addedChoiceArea"+i);//タグ取得
			elementFollowingAddedChoiceArea.setAttribute("id","addedChoiceArea"+(i-1));//id挿入

			//addChoiceBtnのid,questionLoopを変更
			let elementFollowingAddChoiceBtn = document.getElementById("addChoiceBtn"+i);//タグ取得
			elementFollowingAddChoiceBtn.setAttribute("id","addChoiceBtn"+(i-1));//id挿入
			elementFollowingAddChoiceBtn.setAttribute("questionLoop",(i-1));//id挿入
		}

		//choiceCountQのidとchoiceCountを変更
		let elementFollowingChoiceCount = document.getElementById("choiceCount"+i);//タグ取得
		elementFollowingChoiceCount.setAttribute("id","choiceCount"+(i-1));//id挿入

		//addQuestionBtnQのidとquestionLoopを変更
		let elementFollowingAddQuestionBtn = document.getElementById("addQuestionBtn"+i);//タグ取得
		elementFollowingAddQuestionBtn.setAttribute("id","addQuestionBtn"+(i-1));//id挿入
		elementFollowingAddQuestionBtn.setAttribute("questionLoop",(i-1));//questionLoop挿入

		//removeQuestionBtnAreaQのidを変更
		let elementFollowingRemoveQuestionBtnArea = document.getElementById("removeQuestionBtnArea"+i);//タグ取得
		elementFollowingRemoveQuestionBtnArea.setAttribute("id","removeQuestionBtnArea"+(i-1));//id挿入
		elementFollowingRemoveQuestionBtnArea.setAttribute("questionLoop",(i-1));//questionLoop挿入

		//removeQuestionBtnQのidとquestionLoopを変更
		let elementFollowingRemoveQuestionBtn = document.getElementById("removeQuestionBtn"+i);//タグ取得
		elementFollowingRemoveQuestionBtn.setAttribute("id","removeQuestionBtn"+(i-1));//id挿入
		elementFollowingRemoveQuestionBtn.setAttribute("questionLoop",(i-1));//questionLoop挿入

		//addedQuestionAreaQを変更
		let elementFollowingAddedQuestionArea = document.getElementById("addedQuestionArea"+i);
		elementFollowingAddedQuestionArea.setAttribute("id","addedQuestionArea"+(i-1));

	}//後続処理終了


 	//questionCountを-1する
	questionCount=parseInt(questionCount);
	questionCount = questionCount-1;
	elementQuestionCount.setAttribute("questionCount",questionCount);
	if(questionCount==1){
		let elementRemoveRemoveQuestionBtnArea = document.getElementById("removeQuestionBtnArea0");
		elementRemoveRemoveQuestionBtnArea.innerHTML="";
	}


}

//---------------------------------------------
function test(){

}