/*
 *	ExMobi4.x+ JS
 *	Version	: 1.0.0
 *	Author	: liuhai
 *	Email	:
 *	Weibo	:
 *	Copyright 2016 (c)
 */
var TASK_POINT_ID = '';
var TASK_ID = '';
var sql_CZ_TASK = '';
var bool;
var TASK_TIME = '';
document.addEventListener("plusready", onPlusReady, false);
function onPlusReady() {
	header(1);
	getpartition();
}

function getpartition() {
	bool = 'true';
	TASK_ID = getQueryString('TASK_ID');
	TASK_POINT_ID = getQueryString('TASK_POINT_ID');
	TASK_TIME = getQueryString('TASK_TIME');
	sql_CZ_TASK = "select * from CZ_TASK_INSPECTION_CONTENT where TASK_POINT_ID='" + TASK_POINT_ID + "' order by TASK_CONTENT_ORDER";
	var results = db_query(sql_CZ_TASK);
	var html_port = "";
	html_port += '<tbody><tr>';
	html_port += ' <td>序号</td>';
	html_port += ' <td>巡检项目</td>';
	html_port += ' <td>状态</td>';
	html_port += ' <td class="text-center" colspan="2">操作</td>';
	html_port += '</tr>';
	$.each(results, function(i, item) {

		sql_CZ_TASK = "select * from CZ_TASK_RECORD where TASK_CONTENT_ID='" + item.TASK_CONTENT_ID + "' and TASK_TIME='" + TASK_TIME + "' and REMARK1=2";
		var resul = db_query(sql_CZ_TASK);
		var staty;
		if (resul.length > 0) {
			staty = resul[0].REMARK2;
		} else {
			staty = 'UNFINISHED';
		}
		html_port += '<tr><td>' + (i + 1) + '</td>';
		if (staty == 'TERMINATION') {
			html_port += ' <td>' + item.TASK_CONTENT_NAME + '</td>';
			html_port += '<td><a href="#" onclick="javascript:termination(\'' + item.TASK_CONTENT_ID + '\')">';
			html_port += '<span class="mark-disable">终止</span></a></td>';
			html_port += ' <td colspan="2">' + item.REMARKONE + '</td>';
		} else {
			if (staty == 'UNFINISHED' || staty == '') {
				bool = 'false';
				html_port += ' <td>' + item.TASK_CONTENT_NAME + '</td>';
				html_port += '<td><span class="mark-cancel">未完成</span></td><td>';
				html_port += '<a href="#" onclick="javascript:next(\'' + item.TASK_CONTENT_ID + '\')">进入  </a></td>';
				html_port += '<td><a href="#" onclick="javascript:termination(\'' + item.TASK_CONTENT_ID + '\')">终止</a></td>';
			} else {
				html_port += ' <td>' + item.TASK_CONTENT_NAME + '</td>';
				html_port += '<td><span class="mark-success">已完成</span></td>';
				html_port += '<td colspan="2" class="text-center">' + '<a href="#" onclick="javascript:next(\'' + item.TASK_CONTENT_ID + '\')"><span class="mark-success">进入</span></a></td>';
			}
		}
		html_port += '</tr>';
	});
	html_port += '</tbody>';
	$("#table1").html(html_port);
	if (bool == 'true') {

		sql_CZ_TASK = "select * from CZ_TASK_RECORD where TASK_POINT_ID='" + TASK_POINT_ID + "' and TASK_TIME='" + TASK_TIME + "' and REMARK1=1";
		var resul = db_query(sql_CZ_TASK);
		if (resul.length == 0) {
			var sql_EQU = "insert into CZ_TASK_RECORD values('" + com_getUuid('uuid') + "',''," + TASK_ID + ",'" + TASK_POINT_ID + "','','" + com_formatDate(new Date()) + "','" + com_getNowFormatDate() + "','','','','1','FINISHED')";
			nativePage.executeScript('executeBySqlByDefualt("' + sql_EQU + '")');
		}

		//var sqls = "select * from CZ_TASK_INSPECTION_POINT where TASK_POINT_ID='" + TASK_POINT_ID + "'";
		//var result = db_query(sqls);
		//if (result[0].TASK_POINT_STATE != 'FINISHED') {
			//updates();
			//alert("所有巡检项已完成！");
			////显示弹框后跳转到巡检点页面，根据查询到的结果获取到TASK_ID
			//back();
		//}
	}
}

//所有检查项完成的时候，向巡检点更新完成时间和完成状态
function updates() {
	var sqlw = 'select * from CZ_TASK_INSPECTION_POINT  where TASK_ID=(select TASK_ID from CZ_TASK_INSPECTION_POINT where TASK_POINT_ID="' + TASK_POINT_ID + '")';
	var result = db_query(sqlw);
	if (result.length == 1) {
		//定义完成时间
		var endTime = com_getNowFormatDate();
		var sql = "update CZ_TASK_INSPECTION_POINT set TASK_POINT_STATE='FINISHED',TASK_POINT_ENDTIME='" + endTime + "' where TASK_POINT_ID='" + TASK_POINT_ID + "'";
		nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
		//完成任务
		var sqls = "update CZ_TASK_INSPECTION set TASK_STATE='FINISHED',TASK_ENDWORK_DATE='" + endTime + "' where TASK_ID='" + TASK_ID + "'";
		nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
	} else {
		//定义完成时间
		var endTime = com_getNowFormatDate();
		var sql = "update CZ_TASK_INSPECTION_POINT set TASK_POINT_STATE='FINISHED',TASK_POINT_ENDTIME='" + endTime + "' where TASK_POINT_ID='" + TASK_POINT_ID + "'";
		nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
	}
}

//pad右下角点击返回
function back() {
	jump("patrolinspection_port.html?TASK_ID=" + TASK_ID+"&TASK_TIME="+TASK_TIME);
}

//下一步
function next(TASK_CONTENT_ID) {
	jump('patrolinspection1_inspection1.html?TASK_CONTENT_ID=' + TASK_CONTENT_ID + '&TASK_POINT_ID=' + TASK_POINT_ID + '&TASK_ID=' + TASK_ID + '&TASK_TIME=' + TASK_TIME);
}

//终止
function termination(TASK_CONTENT_ID) {
	if (confirm("确认终止?")) {
		jump("patrolinspection_termination.html?type=2&TASK_CONTENT_ID=" + TASK_CONTENT_ID + "&TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_ID=" + TASK_ID);
	} else {
		return;
	}
}

