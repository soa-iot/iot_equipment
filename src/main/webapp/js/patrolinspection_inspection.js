/*
*	ExMobi4.x+ JS
*	Version	: 1.0.0
*	Author	: zengshuai
*	Email	:
*	Weibo	:
*	Copyright 2017 (c)
*/
//TASK_REQUIRE_ID:id
var TASK_REQUIRE_ID = '',TASK_CONTENT_ID = '',uuid = '',problemdescribe = '',problemstate = '';

//问题来源
var rfid = "";
//问题来源
var processtype = '1';
var PROBLEMTYPE = '';
var optval = '';
//所属专业
var PROFESSION = '';
//问题类别/状态
var PROBLEMCLASS = '';
//不安全行为/状况
var REMARKFIVE = '';
//具体行为
var REMARKSIX = '';
var TASK_POINT_ID='';
var TASK_ID='';
document.addEventListener("plusready", onPlusReady, false);
function onPlusReady() {
	TASK_REQUIRE_ID = getQueryString('TASK_REQUIRE_ID');
	TASK_CONTENT_ID = getQueryString('TASK_CONTENT_ID');
	TASK_POINT_ID = getQueryString('TASK_POINT_ID');
	TASK_ID = getQueryString('TASK_ID');
	//此异常的id
	uuid = com_getUuid('uuid');

	////加载问题处理单元
	//s1_text1_bold();
	//问题申请人处理加载
	probpeoplen();
	//数据回写
	getpartition();

	//加载装置单元
	selectUnit();
	//加载所属专业
	profess();
	//问题类别/状态
	problemclass();
	//不安全行为/状况
	remakfive();
	question();

	selectphoto();
	selectvoid();

}

//加载装置单元
function selectUnit() {
	var sql = "select c1.* from CZ_TASK_INSPECTION c1, CZ_TASK_INSPECTION_POINT c2,CZ_TASK_INSPECTION_CONTENT c3 where c1.TASK_ID=c2.TASK_ID and c2.TASK_POINT_ID=c3.TASK_POINT_ID and c3.TASK_CONTENT_ID='" + TASK_CONTENT_ID + "'";
	var restr = db_query(sql);
	//$("#installationuUnit").append("<option value='" + restr[0].EQU_UNIT + "'>" + restr[0].EQU_UNIT + "</option>");
	if (optval == null || optval == '') {
		optval = restr[0].EQU_UNIT;
	}
	//装置单元下拉框处初始化数据
	var sql_UNIT = "select * from CZ_WELLSTATION_NEWS where wel_id>0";
	var unitResults = db_query(sql_UNIT);
	//追加写option之前先做清空处理
	$("#installationuUnit").empty();
	//除开净化厂之外的所有单元装置结果
	$("#installationuUnit").append("<option value=''>点击此处选择装置单元</option>");
	$.each(unitResults, function(i, item) {
		if (optval != '') {
			if (item.WEL_NAME == optval) {
				$("#installationuUnit").append("<option value='" + item.WEL_ID + "' selected = 'selected'>" + item.WEL_NAME + "</option>");
			} else {
				$("#installationuUnit").append("<option value='" + item.WEL_ID + "'>" + item.WEL_NAME + "</option>");
			}

		} else {
			$("#installationuUnit").append("<option value='" + item.WEL_ID + "'>" + item.WEL_NAME + "</option>");
		}
	});
}

/********************************************************************************************************/
//更改装置单元下拉框出发事件
var valUnit = "";
var textUnit = "";
function changeUnit() {
	var options = $("#installationuUnit option:selected");
	//选中的value
	valUnit = options.val();
	//选中的text
	textUnit = options.text();
}

function getpartition() {
	//加载数据
	var sql = "select * from CZ_TASK_PROBLEM_REPORT WHERE TAS_ID = '" + TASK_REQUIRE_ID + "'";
	var results = db_query(sql);
	if (results.length == 0) {
		a();
	}
	$.each(results, function(i, item) {
		uuid = item.T_PROBLEM_REP_ID;
		$("#creature").val(item.APPLYPEOPLE);
		PROBLEMTYPE = item.PROBLEMTYPE;
		optval = item.WEL_NAME;
		//alert("shuju==>>"+item.PROBLEMDESCRIBE);
		document.getElementById("textarea1").innerHTML = item.PROBLEMDESCRIBE;
		var state = item.PROBLEMSTATE;
		if (state == "UNFINISHED") {
			document.getElementById("UNFINISHED").checked = true;
		} else if (state == "FINISHED") {
			document.getElementById("FINISHED").checked = true;
		} else {
			document.getElementById("REPORTED").checked = true;
		}

		PROFESSION = item.PROFESSION;
		PROBLEMCLASS = item.PROBLEMCLASS;
		REMARKFIVE = item.REMARKFIVE;
		REMARKSIX = item.REMARKSIX;
		remarksix(REMARKFIVE);
	});
	//关联设备
	var sql_AND_EQU = "select * from CZ_PROCESS_EQUIPMENT where T_PROBLEM_REP_ID='" + uuid + "'";
	var eqresults = db_query(sql_AND_EQU);
	var html_eq = '';
	html_eq += '<table>';
	$.each(eqresults, function(i, item) {
		var sqleq = "select * from CZ_EQUIPMENT_NEWS where EQU_RFID ='" + item.RFID + "'";
		var eqres = db_query(sqleq);
		html_eq += '<tr><td rowSpan="2">' + (i + 1) + '</td>';
		html_eq += '<td>设备名称 : ' + eqres[0].EQU_NAME + '</td>';
		html_eq += '<td rowSpan="2"><a href="#" onclick="eqdelete(\'' + item.ID + '\')">删除 </a></td></tr>';
		html_eq += '<tr><td>设备标签 : ' + eqres[0].EQU_BILLBOARD_LABEL + '</td></tr>';
		//html_eq += '<tr><td>'+(i+1)+'</td><td>';
		//html_eq += '设备名称:' + eqres[0].EQU_NAME+'</td><td>'+eqres[0].EQU_BILLBOARD_LABEL;
		//html_eq += '</td><td><a href="#" onclick="eqdelete(\'' + item.ID + '\')">删除 </a>';
		//html_eq += '</td></tr>';
	});
	html_eq += '</table>';
	$("#showEquipment").html(html_eq);
	return;
}

//删除关联设备
function eqdelete(R_E_ID) {
	//删除关联关系
	var delet_EQU = "delete from CZ_PROCESS_EQUIPMENT where ID='" + R_E_ID + "'";
	db_execute(delet_EQU);
	//删除完毕后重新加载页面
	getpartition();
}

//保存功能
function pollingsave() {
	//var id = '001';
	//taskId = "002";
	problemdescribe = document.getElementById("textarea1").value;
	var problem = document.getElementsByName("problem");
	for (var i = 0; i < problem.length; i++) {
		if (problem[i].checked == true) {
			problemstate = problem[i].value;
			break;
		}
	}
	var uname = $("#creature").val();
	if (uname == null || uname == '') {
		alert("请选择申请人！");
		return;
	}

	//问题类型
	var questiontype = $("#questiontype").val();
	if (questiontype == '') {
		alert("请选择问题类型！");
		return;
	}
	//问题类型处理
	var questiontypes = questiontype.split(',');
	//var s1_text1_bold = $.trim($("#s1_text1_bold").val());
	//var s2_text1_bold = $.trim($("#s2_text1_bold").val());
	//if (s2_text1_bold == '' || s2_text1_bold == 0) {
	//alert("请选择问题处理单元！");
	//return;
	//}
	//判断是否选择了装置单元
	valUnit = $("#installationuUnit option:selected").val();
	textUnit = $("#installationuUnit option:selected").text();
	//选中的value
	var DEPET = $("#DEPET").val();
	if (PROFESSION.length <= 0) {
		alert("请先选择所属专业！");
		return;
	}
	if (PROBLEMCLASS.length <= 0) {
		alert("请先选择问题类别/状态！");
		return;
	}
	if (REMARKFIVE.length <= 0) {
		alert("请先选择不安全行为/状况！");
		return;
	}
	if (REMARKSIX.length <= 0) {
		alert("请先选择具体行为！");
		return;
	}
	var sql = "insert into CZ_TASK_PROBLEM_REPORT(T_PROBLEM_REP_ID,TAS_ID,PROBLEMDESCRIBE,PROBLEMSTATE,PROCESSTYPE,RFID,WEL_ID,WEL_NAME,ORGANIZATION_SITE_OFFICE,APPLYPEOPLE,PROBLEMTYPE,ADRESS,DEPET,PROBLEMCLASS,REMARKSIX,REMARKSEVEN,PROFESSION,REMARKFIVE) values('" + uuid + "','" + TASK_REQUIRE_ID + "','" + problemdescribe + "','" + problemstate + "','" + processtype + "','" + rfid + "','" + valUnit + "','" + textUnit + "','0','" + uname + "','" + questiontypes[0] + "','" + questiontypes[1] + "','" + DEPET + "','" + PROBLEMCLASS + "','" + REMARKSIX + "','" + uname + "','" + PROFESSION + "','" + REMARKFIVE + "')";
	var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='" + problemstate + ",DEPET='" + DEPET + "',PROBLEMCLASS='" + PROBLEMCLASS + "',REMARKSIX='" + REMARKSIX + "',PROFESSION='" + PROFESSION + "',REMARKFIVE='" + REMARKFIVE + "' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
	if (!problemdescribe.trim()) {
		alert("请填写异常问题描述！");
		return;
	} else if (!problemstate) {
		alert("请选择问题状态！");
		return;
	} else if (!valUnit) {
		alert("请选择装置单元！");
		return;
	} else {
		//判断是否已经添加了异常信息 有（更新） 无（添加
		var sql_r = "select * from CZ_TASK_PROBLEM_REPORT where TAS_ID='" + TASK_REQUIRE_ID + "'";
		var rest = db_query(sql_r);
		if (rest.length > 0) {
			update();
		} else {
			db_execute(sql);
			//nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
		}
		db_execute(sqlS);
		//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
		transfer();
		alert("保存成功!");
		back();
	}
}

//更新数据
function update() {
	problemdescribe = document.getElementById("textarea1").value;
	var problem = document.getElementsByName("problem");
	for (var i = 0; i < problem.length; i++) {
		if (problem[i].checked == true) {
			problemstate = problem[i].value;
			break;
		}
	}
	var sql = "update CZ_TASK_PROBLEM_REPORT set PROBLEMDESCRIBE = '" + problemdescribe + "',PROBLEMSTATE = '" + problemstate + "',WEL_ID='" + valUnit + "',WEL_NAME='" + textUnit + "' where T_PROBLEM_REP_ID = '" + uuid + "'";
	//nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
	db_execute(sql);
	window.open("patrolinspection_inspection.html?TASK_REQUIRE_ID=" + TASK_REQUIRE_ID + "&TASK_CONTENT_ID=" + TASK_CONTENT_ID);
}

//附件添加到附件信息表
function transfer() {
	var sql_xjsingular_p = "select * from CZ_M_PHOTO where PHO_CATEGORY_ID='" + TASK_REQUIRE_ID + "'";
	var uname = $("#creature").val();
	//照片附件
	var results_xjsingular_p = db_query(sql_xjsingular_p);
	$.each(results_xjsingular_p, function(i, item) {
		//主键 任务外键  图片名称  上传人  序号
		//问题图片表
		var sql_1 = "insert into CZ_TASK_PROBLEM_REPORTPHO(T_PROBLEM_PHO_ID,T_PROBLEM_REP_ID,PHO_NAME,PHO_UPLOAD_PEOPLE,PROBLEMNUM,PHO_ADDRESS,REMARKONE,PHO_DISPIAY_NAME,PHO_UPLOAD_DATE,REMARK)" + " values ('" + item.PHO_ID + "','" + uuid + "','" + item.PHO_NAME + "','" + uname + "','" + i + "','" + item.PHO_PATH + "','" + item.REMARK + "','" + item.PHO_NAME + "','" + com_getNowFormatDate() + "','0')";
		db_execute(sql_1);
	});
}

//扫描RFID
function scansion() {
	nativePage.executeScript("getRfidAndReturn('deviceentry_rfid_scaner_rfidCallback')");
	//deviceentry_rfid_scaner_rfidCallback();
}

//调用设备扫描页面，获取RFID标签

function deviceentry_rfid_scaner_rfidCallback(result) {
	//function deviceentry_rfid_scaner_rfidCallback(){
	rfid = result.data;
	var sql = "select * from CZ_EQUIPMENT_NEWS where EQU_RFID ='" + rfid + "'";
	var results = db_query(sql);
	if (results.length <= 0) {
		rfid = '';
		alert("没有此设备信息");
	} else {
		var sql_ANDs = "select * from CZ_PROCESS_EQUIPMENT where T_PROBLEM_REP_ID='" + uuid + "' and RFID='" + rfid + "'";
		var eqresult = db_query(sql_ANDs);
		//该条数据的序号
		var order = eqresult.length + 1;
		if (eqresult.length > 0) {
			alert("已关联该设备！");
			return;
		} else {
			//扫描设备成功，添加到关系表
			var sql_EQU = "insert into CZ_PROCESS_EQUIPMENT(ID,RFID,T_PROBLEM_REP_ID,EQUIPMENT_NAME,ROUTINE_ORDER) values('" + com_getUuid() + "','" + rfid + "','" + uuid + "','" + results[0].EQU_NAME + "','" + order + "')";
			
			db_execute(sql_EQU);
			getpartition();
		}
	}
}

/****照相功能**********************************************************************************************/
function getCamera() {
	com_getCamera_save('xjsingular_p', TASK_REQUIRE_ID, 0, function(result, data) {
		selectphoto();
	});
}

//查询照片数据
function selectphoto() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjsingular_p' and PHO_CATEGORY_ID='" + TASK_REQUIRE_ID + "'";
	var results = db_query(sql);
	if (results.length > 0) {
		intphoto = 'TRUE';
	}
	html_photo = '';
	$.each(results, function(i, item) {
		var PHO_ID = item.PHO_ID;
		html_photo += '<figure>';
		html_photo += '<a href="' + item.PHO_PATH + '" data-size="571x800"> ';
		html_photo += '<img src="' + item.PHO_PATH + '" /> </a>';
		html_photo += "<input class='button grayscale' type='button' value='删除' style='width:100%;' onclick=\"deletePhoto('" + PHO_ID + "')\"></input>";
		html_photo += '</figure>';
	});
	$("#photo").html(html_photo);
}

//删除照片
function deletePhoto(PHO_ID) {
	var sql = "delete from CZ_M_PHOTO where PHO_ID='" + PHO_ID + "'";
	//删除照片文件
	var results = db_query("select PHO_PATH from CZ_M_PHOTO where PHO_ID='" + PHO_ID + "'");
	$.each(results, function(i, item) {
		if (!file_deleteFile(item.PHO_PATH)) {
			alert("删除文件失败");
			return;
		}
	});

	//删除照片数据
	nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
	//删除
	var sql1 = "delete from CZ_TASK_TEMPORARY_RESULEP where T_TEMPORARY_PHO_ID='" + PHO_ID + "'";
	nativePage.executeScript('executeBySqlByDefualt("' + sql1 + '")');
	//删除完成后刷新界面，重新展示
	//刷新照片
	selectphoto();
	//刷新录像
	selectvoid();
}

/****视频功能**********************************************************************************************/
function getCameras() {
	com_getCamera_saves('xjsingular_v', TASK_REQUIRE_ID, 1, function(result, data) {
		selectvoid();
	});
}

function selectvoid() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjsingular_v' and PHO_CATEGORY_ID='" + TASK_REQUIRE_ID + "'";
	var results = db_query(sql);
	if (results.length > 0) {
		intphoto = 'TRUE';
	}
	html_photo = '';
	$.each(results, function(i, item) {
		var id = item.PHO_ID;
		html_photo += '<table><tr><td>';
		html_photo += "<a href='#' onclick=\"voides('" + item.PHO_PATH + "')\" data-size='571x800'> ";
		html_photo += item.PHO_NAME + ' </a></td><td style="width:25%" align="right">';
		html_photo += "<input class='button grayscale' type='button' value='删除' style='width:50%;' onclick=\"deletePhoto('" + id + "')\"></input>";
		html_photo += '</td></tr><table>';
	});
	$("#void").html(html_photo);
}

function voides(path) {
	nativePage.executeScript('testOpenVideo2("' + path + '")');
}

//返回上一页面，需要传入
function back() {
	window.open("patrolinspection1_inspection1.html?TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_CONTENT_ID=" + TASK_CONTENT_ID+ "&TASK_ID=" + TASK_ID);
}


/**问题上报人处理************************************************************************************/
function probpeoplen() {
	var name = CacheUtil.getCache("name");
	var username = CacheUtil.getCache("username");
	//如果是组登陆，则username和name都有值，如果是人员登陆，则name为空，username有值，且人员登陆只能单人登陆
	if (!name) {//如果是人员登陆
		var html_name = '';
		html_name += '<option value = "' + username + '">' + username + '</option>';
		//申请人赋值
		$("#creature").html(html_name);
		var na = db_query('select * from CZ_PERSONNER_INFORMATION where PER_IDNUMBER="' + username + '"');
		var html_depet = '<option value = "' + na[0].PER_DEPARTMENT + '">' + na[0].PER_DEPARTMENT + '</option>';
		$("#DEPET").html(html_depet);
	} else {//如果是组登陆，判断是单人登陆还是多人登陆
		var html_depet = '<option value = "' + username + '">' + username + '</option>';
		$("#DEPET").html(html_depet);
		var names = name.split(",");
		if (names.length > 1) {//如果长度大于1，则是多人登陆，多人登录换文本框为下拉框
			var html_depet = '<option value = "' + username + '">' + username + '</option>';
			$("#DEPET").html(html_depet);
			$("#creature").empty();
			var html_name = '';
			html_name += '<option value="">请选择申请人</option>';
			for (var i = 0; i < names.length; i++) {
				html_name += '<option value="' + names[i] + '">' + names[i] + '</option>';
			}
			//申请人赋值
			$("#creature").html(html_name);
		} else {//单人登陆
			var html_name = '';
			html_name += '<option value = "' + names + '">' + names + '</option>';
			//申请人赋值
			$("#creature").html(html_name);
		}
	}
}

/***************************************************************/
//加载问题类型
function question() {
	var ce = '净化巡检,化验作业,电站巡检,机械巡检,仪表巡检,电工班巡检';
	var ces = ce.split(',');
	//追加写option之前先做清空处理
	$("#questiontype").empty();
	//除开净化厂之外的所有单元装置结果
	$("#questiontype").append("<option value=''>点击此处选择问题类型</option>");
	for (var i = 1; i < ces.length + 1; i++) {
		if (i <= 2) {
			if (PROBLEMTYPE == i) {
				$("#questiontype").append("<option value='" + i + ",0' selected = 'selected' >" + ces[i - 1] + "</option>");
			} else {
				$("#questiontype").append("<option value='" + i + ",0'>" + ces[i - 1] + "</option>");
			}
		} else {
			if (PROBLEMTYPE == i) {
				$("#questiontype").append("<option value='" + i + ",1' selected = 'selected'>" + ces[i - 1] + "</option>");
			} else {
				$("#questiontype").append("<option value='" + i + ",1'>" + ces[i - 1] + "</option>");
			}
		}
	}
}

//描述信息
function a() {
	var result = db_query("select * from CZ_TASK_INSPECTION_REQUIRE where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'");
	var sql = 'select c1.TASK_NAME TASK_NAME,c2.TASK_POINT_NAME TASK_POINT_NAME,c3.TASK_CONTENT_NAME TASK_CONTENT_NAME from CZ_TASK_INSPECTION c1,CZ_TASK_INSPECTION_POINT c2,CZ_TASK_INSPECTION_CONTENT c3 where  c1.TASK_ID=c2.TASK_ID and c2.TASK_POINT_ID=c3.TASK_POINT_ID and c3.TASK_CONTENT_ID="' + result[0].TASK_CONTENT_ID + '"';
	var resul = db_query(sql);

	var textq = '';
	if (result[0].TASK_REQUIRE_TYPE == '1') {
		textq = '出现异常！';
	} else {
		if (result[0].TASK_REQUIRE_SYMBOL == 'ge' || result[0].TASK_REQUIRE_SYMBOL == 'gt') {
			textq = '数据偏低！';
		}
		if (result[0].TASK_REQUIRE_SYMBOL == 'le' || result[0].TASK_REQUIRE_SYMBOL == 'lt') {
			textq = '数据偏高！';
		}
		if (result[0].TASK_REQUIRE_SYMBOL == 'ba') {
			textq = '出现异常！';
		}
	}
	var text = resul[0].TASK_NAME + " " + resul[0].TASK_POINT_NAME + " " + resul[0].TASK_CONTENT_NAME + " " + result[0].TASK_REQUIRE_CONTEXT + " " + result[0].TASK_EQU_POSITION_NUM + " " + textq;
	$("#textarea1").val(text);
}

//选中的所属专业
function professUtil() {
	var options = $("#PROFESSION option:selected");
	//选中的value
	PROFESSION = options.val();
}

//加载所属专业
function profess() {
	var ce = '管理,工艺,机械,电气,仪表,环保,化验,物资,土建,HSE,消防,其他';
	var ces = ce.split(',');
	//追加写option之前先做清空处理
	$("#PROFESSION").empty();
	//除开净化厂之外的所有单元装置结果
	$("#PROFESSION").append("<option value=''>点击此处选择所属专业</option>");
	for (var i = 0; i < ces.length; i++) {
		if (PROFESSION == ces[i]) {
			$("#PROFESSION").append("<option value='" + ces[i] + "' selected = 'selected' >" + ces[i] + "</option>");
		} else {
			$("#PROFESSION").append("<option value='" + ces[i] + "'>" + ces[i] + "</option>");
		}
	}
}

//选中的问题类别/状态
function problemclassUnit() {
	var options = $("#PROBLEMCLASS option:selected");
	//选中的value
	PROBLEMCLASS = options.val();
}

//加载问题类别/状态
function problemclass() {
	var ce = '不安全状态卡,分厂级检查,总厂级检查,分公司及以上检查,体系审核,地方安全环保检查,总厂QHSE监督站,分公司HSE督察中心,其他';
	var ces = ce.split(',');
	//追加写option之前先做清空处理
	$("#PROBLEMCLASS").empty();
	//除开净化厂之外的所有单元装置结果
	$("#PROBLEMCLASS").append("<option value=''>点击此处选择问题类别/状态</option>");
	for (var i = 0; i < ces.length; i++) {
		if (PROBLEMCLASS == ces[i]) {
			$("#PROBLEMCLASS").append("<option value='" + ces[i] + "' selected = 'selected' >" + ces[i] + "</option>");
		} else {
			$("#PROBLEMCLASS").append("<option value='" + ces[i] + "'>" + ces[i] + "</option>");
		}
	}
}

//选中的不安全行为/状况
function remakfiveUnit() {
	var options = $("#REMARKFIVE option:selected");
	//选中的value
	REMARKFIVE = options.val();
	//选中的text
	remarksix(options.val());
}

//加载不安全行为/状况
function remakfive() {
	var natypes = db_query('select * from CZ_UNSAFE_TYPE order by TYPE_CODE');
	//追加写option之前先做清空处理
	$("#REMARKFIVE").empty();
	$("#REMARKFIVE").append("<option value=''>点击此处选择不安全行为/状况</option>");
	$.each(natypes, function(i, item) {
		if (REMARKFIVE == item.TYPE_NAME) {
			$("#REMARKFIVE").append("<option value='" + item.TYPE_NAME + "' selected = 'selected' >" + item.TYPE_NAME + "</option>");
		} else {
			$("#REMARKFIVE").append("<option value='" + item.TYPE_NAME + "'>" + item.TYPE_NAME + "</option>");
		}
	});
}

//选中的具体行为
function remarksixUnit() {
	var options = $("#REMARKSIX option:selected");
	//选中的value
	REMARKSIX = options.val();
}

//加载具体行为
function remarksix(val) {
	var natypes = db_query('select * from CZ_UNSAFE_TYPE where TYPE_NAME="' + val + '"');
	var nametypes = db_query('select * from CZ_UNSAFE_ACTION where TYPE_CODE="' + natypes[0.].TYPE_CODE + '"');
	$("#REMARKSIX").empty();
	$("#REMARKSIX").append("<option value=''>点击此处选择不安全行为/状况</option>");
	$.each(nametypes, function(i, item) {
		if (REMARKSIX == item.TYPE_NAME) {
			$("#REMARKSIX").append("<option value='" + item.TYPE_NAME + "' selected = 'selected' >" + item.TYPE_NAME + "</option>");
		} else {
			$("#REMARKSIX").append("<option value='" + item.TYPE_NAME + "'>" + item.TYPE_NAME + "</option>");
		}
	});
}


