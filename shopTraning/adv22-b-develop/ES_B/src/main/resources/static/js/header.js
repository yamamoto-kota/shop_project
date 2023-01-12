function showHeader(esqId, name, list, edit) {
	let addHeader = "";

	addHeader += '<header class="header">';
	addHeader += '<nav class=" navbar-light bg-light">';
	addHeader += '<div class="container-fluid ">';
	addHeader += '<div class="row">';
	addHeader += '<div class="col-3 mx-5 font-weight-bold font-italic  h2">ISID</div>';
	addHeader += '<div class="col-3 ml-auto"></div>';
	addHeader += '<div class="col-auto my-2 h5 ">' + esqId +'</div>';
	addHeader += '<div class="col-auto my-2 h5">' + name + '</div>';
	addHeader += '<div class="ml-auto ">';
	addHeader += '<a href="/logout">';
	addHeader += '<input class="btn btn btn-primary my-1 mx-1" type="submit" value="ログアウト" /></a>';
	addHeader += '</div>';
	addHeader += '</div>';
	addHeader += '</div>';
	addHeader += '</nav>';
	addHeader += '<nav class=" navbar-light bg-primary ">';
	addHeader += '<div class="text-center ">';
	addHeader += '<a class="navbar-brand  h3 text-white  " href="' + list + '">アンケート一覧</a>';
	addHeader += '<div class="navbar-brand  h3 text-white">|</div>';
	addHeader += '<a class="navbar-brand  h3 text-white" href="' + edit + '">アンケート作成</a>';
	addHeader += '</div>';
	addHeader += '<hr>';
	addHeader += '</nav>';
	addHeader += '</header>';
	document.getElementById("addHeader").innerHTML = addHeader;
}
