/*
*	ExMobi4.x+ JS
*	Version	: 1.0.0
*	Author	: liuhai
*	Email	:
*	Weibo	:
*	Copyright 2016 (c)
*/

//上一级  TASK_ID 任务实例ID
var TASK_ID = '',
    bool,
    POINT_ID = '',
    TASK_TIME = '';

document.addEventListener("plusready", onPlusReady, false);
function onPlusReady() {
	header(1);
	TASK_ID = getQueryString("TASK_ID");
	TASK_TIME = getQueryString('TASK_TIME');
	getpartition();
}

function getpartition() {
	bool = 'true';
	var sql = "SELECT * from CZ_TASK_INSPECTION_POINT where TASK_ID=" + TASK_ID;
	var results = db_query(sql);

	//if (results.length == 1) {
	//jump('patrolinspection_project.html?TYPE=1&TASK_POINT_ID=' + results[0].TASK_POINT_ID + '&TASK_ID=' + TASK_ID);
	//} else {
	var html_port = "";
	html_port += '<table border="1"><tbody><tr>';
	html_port += ' <td class="text-center">序号</td>';
	html_port += ' <td class="text-center">巡检点</td>';
	html_port += ' <td>状态</td>';
	html_port += ' <td class="text-center" colspan="2">操作</td>';
	html_port += '</tr>';
	$.each(results, function(i, item) {

		sql_CZ_TASK = "select * from CZ_TASK_RECORD where TASK_POINT_ID='" + item.TASK_POINT_ID + "' and TASK_TIME='" + TASK_TIME + "' and REMARK1=1";
		var resul = db_query(sql_CZ_TASK);
		var staty;
		if (resul.length > 0) {
			staty = resul[0].REMARK2;
		} else {
			staty = 'UNFINISHED';
		}

		html_port += '<tr><td>' + (i + 1) + '</td>';
		var TASK_POINT_ID = item.TASK_POINT_ID;
		if (staty == 'TERMINATION') {
			html_port += ' <td class="text-center">' + item.TASK_POINT_NAME + '</td><td>';
			html_port += ' <a href="#" onclick="javascript:termination(\'' + TASK_POINT_ID + '\')">';
			html_port += '<span class="mark-disable">终止</span></a></td>';
			html_port += '<td class="text-center" colspan="2">' + item.REMARKONE + '</td>';
		} else {
			if (staty == 'UNFINISHED' || staty == '') {
				bool = 'false';
				html_port += ' <td class="text-center">' + item.TASK_POINT_NAME + '</td><td>';
				html_port += '<a href=>';
				html_port += '<span class="mark-cancel">未完成</span></a>';
				html_port += '</td>';
				//if (item.TASK_POINT_RFID == '' || item.TASK_POINT_RFID == null) {
				html_port += '<td class="text-center">' + '<a href="#" onclick="javascript:next(\'' + TASK_POINT_ID + '\')">执行 </a></td>';
				html_port += '<td class="text-center"><a href="#" onclick="javascript:termination(\'' + TASK_POINT_ID + '\')">终止</a></td>';
				//} else {
				//html_port += '<td class="text-center">' + '<a href="#" onclick="javascript:deviceentry_rfid_scaner(\'' + TASK_POINT_ID + '\')">扫描</a></td>';
				//html_port += '<td class="text-center"><a href="#" onclick="javascript:termination(\'' + TASK_POINT_ID + '\')">终止</a></td>';
				//}
			} else {
				html_port += ' <td>' + item.TASK_POINT_NAME + '</td><td>';
				html_port += '<a href="#" onclick="javascript:next(\'' + TASK_POINT_ID + '\')">';
				html_port += '<span class="mark-success">已完成</span></a></td>';
				if (item.TASK_POINT_RFID == '' || item.TASK_POINT_RFID == null) {
					html_port += '<td class="text-center" colspan="2">' + '<a href="#" onclick="javascript:next(\'' + TASK_POINT_ID + '\')">执行</a></td>';
				} else {
					html_port += '<td class="text-center" colspan="2">' + '<a href="#" onclick="javascript:deviceentry_rfid_scaner(\'' + TASK_POINT_ID + '\')">执行</a></td>';
				}
			}

		}
		html_port += '</tr>';
	});

	html_port += '</tbody></table>';
	if (bool == 'true') {
		
		var sql_CZ_TASK = "select * from CZ_TASK_RECORD where TASK_ID='" + TASK_ID + "' and TASK_TIME='" + TASK_TIME + "' and REMARK1=0";
		var resul = db_query(sql_CZ_TASK);
		if (resul.length == 0) {
			var sql_EQU = "insert into CZ_TASK_RECORD values('" + com_getUuid('uuid') + "',''," + TASK_ID + ",'','','" + com_formatDate(new Date()) + "','" + com_getNowFormatDate() + "','','','','0','FINISHED')";
			nativePage.executeScript('executeBySqlByDefualt("' + sql_EQU + '")');
		}
		//var sqls = "select * from CZ_TASK_INSPECTION where TASK_ID='" + TASK_ID + "'";
		//var result = db_query(sqls);
		//if (result[0].TASK_STATE != 'FINISHED') {
			//updates();
			//alert("所有巡检点已完成！");
		//}
	}
	$("#table1").html(html_port);
	//}
}

//巡检点完成之后，修改巡检状态，巡检任务完成时间和完成人
function updates() {
	//任务完成时间
	var sql = "update CZ_TASK_INSPECTION set TASK_STATE='FINISHED',TASK_ENDWORK_DATE='" + com_formatDate(new Date()) + "' where TASK_ID='" + TASK_ID + "'";
	nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
	jump("patrolinspection.html");
}

//调用设备扫描页面，获取RFID标签
function deviceentry_rfid_scaner(TASK_POINT_ID) {
	POINT_ID = TASK_POINT_ID;
	nativePage.executeScript("getRfidAndReturn('deviceentry_rfid_scaner_rfidCallback')");
}

function deviceentry_rfid_scaner_rfidCallback(result) {
	var rfid = result.data;
	var sql = "select * from CZ_TASK_INSPECTION_POINT where  TASK_POINT_RFID ='" + rfid + "' and TASK_POINT_ID= '" + POINT_ID + "'";
	var results = db_query(sql);
	if (results.length > 0) {
		//跳转之前，先插入时间
		var time = com_getNowFormatDate();
		var TASK_POINT_STARTTIME = results[0].TASK_POINT_STARTTIME;
		if (TASK_POINT_STARTTIME == null || TASK_POINT_STARTTIME == '') {
			var modify_port_time = "update CZ_TASK_INSPECTION_POINT set TASK_POINT_STARTTIME = '" + time + "' where TASK_POINT_ID='" + POINT_ID + "'";
			nativePage.executeScript('executeBySqlByDefualt("' + modify_port_time + '")');
		}
		window.open("patrolinspection_project.html?TASK_ID=" + TASK_ID + "&TASK_POINT_ID=" + results[0].TASK_POINT_ID);
	} else {
		alert('扫描失败，巡检点与巡检标签不匹配！');
	}
}

//下一步
function next(TASK_POINT_ID) {
	jump('patrolinspection_project.html?TYPE=1&TASK_POINT_ID=' + TASK_POINT_ID + '&TASK_ID=' + TASK_ID + '&TASK_TIME=' + TASK_TIME);
}

//右下角返回
function back() {
	var sqls = "select * from CZ_TASK_INSPECTION where TASK_ID='" + TASK_ID + "'";
	var result = db_query(sqls);
	window.open("patrolinspection.html?id=" + result[0].TASK_ID+"&TASK_TIME="+TASK_TIME);
}

//返回页面
function backs() {
	var sqls = "select * from CZ_TASK_INSPECTION where TASK_ID='" + TASK_ID + "'"+"&TASK_TIME="+TASK_TIME;
	var result = db_query(sqls);
	window.open("patrolinspection.html");
}

//终止
function termination(TASK_POINT_ID) {
	if (confirm("确认终止?")) {
		jump("patrolinspection_termination.html?type=1&TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_ID=" + TASK_ID);
	} else {
		return;
	}
}
