/*
*	ExMobi4.x+ JS
*	Version	: 1.0.0
*	Author	: liuhai
*	Email	:
*	Weibo	:
*	Copyright 2016 (c)
*/
var TASK_TIME='';
//采用回调函数的方式获取
function select_initData(date) {
	TASK_TIME=date;
	var newdate = com_formatDate(new Date());
	var sql ="";
	if(date==newdate){
	 sql = "select * from CZ_TASK_INSPECTION where TASK_ENDWORK_DATE !='" + newdate + "' or TASK_ENDWORK_DATE is null";	
	}else{
		sql = "select c1.*,c2.TASK_TIME as TASK_TIME from CZ_TASK_INSPECTION c1 INNER JOIN CZ_TASK_RECORD c2 where c1.TASK_ID=c2.TASK_ID and c2.REMARK1=0 and TASK_TIME='"+date+"'";	
	}
	var j = 0;
	var k = 0;
	var html_initData1 = '';
	html_initData1 += "<table align='center'>";
	html_initData1 += "<tbody><tr>";
	html_initData1 += " <th class='text-center' style='width:10%'>序号</th>";
	html_initData1 += "  <th  style='width:25%;text-align:center;'>当天工作任务</th>";
	html_initData1 += " <th style='width:25%'>完成期限</th>";
	html_initData1 += " <th style='width:25%'>执行人</th>";
	html_initData1 += "  <th style='width:20%'>操作</th></tr>";
	var html_initData2 = '';
	html_initData2 += "<table align='center'>";
	html_initData2 += "<tbody><tr>";
	html_initData2 += " <th class='text-center' style='width:10%'>序号</th>";
	html_initData2 += "   <th style='width:25%;text-align:center;'>当天工作任务</th>";
	html_initData2 += " <th style='width:25%'>完成期限</th>";
	html_initData2 += " <th style='width:25%'>执行人</th>";
	html_initData2 += "  <th style='width:20%'>操作</th></tr>";
	$.each(db_query(sql), function(i, item) {
		var state = item.TASK_STATE;
		var nowDate = com_getNowFormatDate();
		var quaryDate = item.PLANNED_COMPLETION_TIME;
		var TASK_NAME = item.TASK_NAME.substring(0, item.TASK_NAME.indexOf("2"));
		
		var times = item.TASK_NAME.substring(item.TASK_NAME.indexOf("2"));
		times = times.replace(/-/g, ':').replace(' ', ':');
		times = times.split(':');
		var time1 = new Date(times[0], (times[1] - 1), times[2], times[3], times[4], times[5]);
		//任务执行时间
		var hours=time1.getHours();
		var newHouse=new Date().getHours();
		
		var ID = item.TASK_ID;
		if (state == "" || state == "UNFINISHED") {
			j = j + 1;
			html_initData1 += "  <tr><td class='text-center'>" + j + "</td><td class='text-center'>";
			if(hours==newHouse){
			html_initData1 += " <a href='#' onclick='next(\"" + ID + "\")'>"+TASK_NAME + "</a>";				
			}else{
				html_initData1 += TASK_NAME ;
			}
			html_initData1 += "</td><td>" + hours + "</td>";
			html_initData1 += "<td>" + item.REMARKONE + "</td><td>";
			html_initData1 += "<a href='#' onclick='add(\"" + ID + "\")'><span class='mark-success'>添加</span></a>";
			html_initData1 += "<a href='#' onclick='delet(\"" + ID + "\")'><span class='mark-cancel'>删除</span></a></td></tr>";
		} else if (state == "FINISHED") {
			k = k + 1;
			html_initData2 += "  <tr><td class='text-center'>" + k + "</td><td class='text-center'>";
			html_initData2 += " <a href='patrolinspection_port.html?id=" + item.TASK_ID + "&na=" + na + "'>";
			html_initData2 += TASK_NAME + "</a></td>";
			html_initData2 += "<td>" + hours + "</td>";
			html_initData2 += "<td>" + item.REMARKONE + "</td><td>";
			html_initData2 += "<a href='patrolinspection_port.html?id=" + item.TASK_ID + "&na=" + na + "'><span class='mark-success'>已完成</span></a></td></tr>";
		}
	});

	html_initData1 += "  </tbody></table>";
	html_initData2 += "  </tbody></table>";
	$("#table1").html(html_initData1);
	$("#table2").html(html_initData2);
}

var tabbar = document.getElementById("tabbar");
var tab = tabbar.querySelectorAll(".tab");
function tabActive(index) {
	for (var i = 0,
	    t; t = tab[i++]; ) {
		t.classList.remove("active");
	}
	tab[index].classList.add("active");
}

function initTabbar() {
	for (var i = 0,
	    t; t = tab[i++]; ) {
		(function(i) {
			t.addEventListener("click", function() {
				carouselPage.slideTo(i - 1);
				tabActive(i - 1);
			}, false);
		})(i);
	}
}

//添加执行人
function add(T_ID) {
	var html = "";
	html += '<div>用户名:';
	html += '<input type="text" style="height:40px" id="unames" value="admin" />';
	html += '</div>';
	html += '<hr>';
	html += '<div>密&nbsp;&nbsp;&nbsp;&nbsp;码:';
	html += '<input type="password" style="height:40px" id="psw" value="123456" />';
	html += '</div>';
	layer.open({
		title : '添加执行人',
		type : 1,
		skin : 'layui-layer-rim', //加上边框
		area : ['420px', '240px'], //宽高
		btn : ['确定', '取消'],
		content : html,
		yes : function() {
			var unames = $("#unames").val();
			var psw = $("#psw").val();
			var sql = "select * from CZ_PERSONNER_INFORMATION where PER_IDNUMBER='" + unames + "' and PER_MEMO_ONE='" + psw + "'";
			if (db_query(sql) == null || db_query(sql) == "") {
				alert("用户名或密码错误！");
			} else {
				var a = new Array();
				var zxr = selName(T_ID);
				var arr_zxr = zxr.split(",");
				if (arr_zxr.indexOf(unames) == -1) {
					if (zxr.length > 0) {
						arr_zxr.push(unames);
						//写入用户执行表
						var sql_up = "update CZ_TASK_INSPECTION set REMARKONE='" + arr_zxr + "' where TASK_ID='" + T_ID + "'";
						nativePage.executeScript('executeBySqlByDefualt("' + sql_up + '")');
					} else {
						a.push(unames);
						//写入用户执行表
						var sql_up = "update CZ_TASK_INSPECTION set REMARKONE='" + a + "' where TASK_ID='" + T_ID + "'";
						nativePage.executeScript('executeBySqlByDefualt("' + sql_up + '")');
					}

				}
				layer.closeAll();
				index_p();
			}
		},
		btn2 : function() {

		}
	});
}

//删除执行人
function delet(TASK_ID) {
	var html = "";
	html += '<table>';
	var a = selName(TASK_ID);
	var arr = a.split(",");
	if (a.length > 0) {
		for (var i = 0; i < arr.length; i++) {
			html += '<tr>';
			html += '<td>用户名:</td>';
			html += '<td>' + arr[i] + '</td>';
			html += '<td><a href="#" onclick="remove_arr(\'' + arr[i] + '\',\'' + TASK_ID + '\')">删除</a></td>';
			html += '</tr>';
		}
		html += '</table>';
		layer.open({
			title : '删除执行人',
			type : 1,
			skin : 'layui-layer-rim', //加上边框
			area : ['420px', '440px'], //宽高
			btn : ['确定', '取消'],
			content : html,
			yes : function() {
				//删除关信息
				layer.closeAll();
				index_p();
			},
			btn2 : function() {
			}
		});
	} else {
		alert("没有执行人,请先添加执行人！");
		index_p();
	}
}

function remove_arr(name, TASK_ID) {
	var zxr = new Array();
	var arr_a = selName(TASK_ID);
	var arr = arr_a.split(",");
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] != name) {
			zxr.push(arr[i]);
		}
	}
	var sql_up = "update CZ_TASK_INSPECTION set REMARKONE='" + zxr + "' where TASK_ID='" + TASK_ID + "'";
	nativePage.executeScript('executeBySqlByDefualt("' + sql_up + '")');
	alert("删除成功");
	layer.closeAll();
	delet(TASK_ID);
}

function selName(id) {
	var zxr = new Array();
	var sql_s = "select * from CZ_TASK_INSPECTION where TASK_ID='" + id + "'";
	var rest = db_query(sql_s);
	zxr = rest[0].REMARKONE;
	return zxr;
}

//下一步跳转
function next(id) {
	var arr_a = selName(id);
	if (arr_a.length > 0) {
		window.open("patrolinspection_port.html?TASK_ID='" + id + "'&TASK_TIME="+TASK_TIME);
	} else {
		alert("请先添加执行人！");
	}
}

//pad点击返回上一级
function back() {
	window.open("../../index.html");
}

//返回上一级
function backs() {
	window.open("../../index.html");
}

