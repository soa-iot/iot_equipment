/*
 *	ExMobi4.x+ JS
 *	Version	: 1.0.0
 *	Author	: zengshuai
 *	Email	:
 *	Weibo	:
 *	Copyright 2017 (c)
 */
var host = "";
var port = "";
var TASK_CONTENT_ID = '',
    sql_CZ = '',
    TASK_POINT_ID = '',
    TASK_ID = '',
    TASK_REQUIRE_ID = '';
var TASK_TIME = '';
document.addEventListener("plusready", onPlusReady, false);
function onPlusReady() {
	host = CacheUtil.getCache("host");
	port = CacheUtil.getCache("port");
	header(1);
	console.log("加载数据");
	TASK_CONTENT_ID = getQueryString('TASK_CONTENT_ID');
	TASK_POINT_ID = getQueryString('TASK_POINT_ID');
	TASK_ID = getQueryString('TASK_ID');
	TASK_TIME = getQueryString('TASK_TIME');
	getpartition();
	selectvoid();
	selectphoto();
}

function getpartition() {
	sql_CZ = "select * from CZ_TASK_INSPECTION_REQUIRE where TASK_CONTENT_ID='" + TASK_CONTENT_ID + "'";
	var results = db_query(sql_CZ);
	TASK_REQUIRE_ID = results[0].TASK_REQUIRE_ID;
	var html_port = "";
	html_port += '<tbody><tr>';
	html_port += ' <td  style="width:25%">仪表位号</td>';
	html_port += ' <td  style="width:25%">检查项</td>';
	html_port += ' <td  style="width:20%">范围</td>';
	html_port += ' <td  style="width:30%">仪表值</td>';
	html_port += '</tr>';
	$.each(results, function(i, item) {
		//判断是否需要填值
		//位FALSE时候无法改变值
		if (item.REMARKFOUR == "FALSE") {
			if (item.TASK_REQUIRE_TYPE == '3') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				html_port += ' <td>' + item.TASK_REQUIRE_CONTEXT + '</td>';
				if (item.TASK_REQUIRE_SYMBOL == 'ge') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '≤</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'gt') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '< </td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'le') {
					html_port += ' <td> ≤' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'lt') {
					html_port += ' <td> <' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'ba') {
					html_port += ' <td>' + item.TASK_REQUIRE_MIX + '-' + item.TASK_REQUIRE_MAX + '</td>';
				}
				html_port += '<td></td>';
				html_port += '</tr>';
			} else if (item.TASK_REQUIRE_TYPE == '2') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				var sql = "select * from CZ_CCS_PARA where CCS_PARA_ID='" + item.TASK_CCS_PARA_ID + "'";
				var result = db_query(sql);
				if (result[0].TASK_REQUIRE_SYMBOL == 'ge') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '≤</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'gt') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '< </td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'le') {
					html_port += ' <td> ≤' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'lt') {
					html_port += ' <td> <' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'ba') {
					html_port += ' <td>' + item.TASK_REQUIRE_MIX + '-' + item.TASK_REQUIRE_MAX + '</td>';
				}
				html_port += '<td></td>';
				html_port += '</tr>';
			} else if (item.TASK_REQUIRE_TYPE == '1') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				html_port += ' <td>' + item.TASK_REQUIRE_CONTEXT + '</td>';
				var TASK_REQUIRE_RES_DESC = item.TASK_REQUIRE_RES_DESC;
				var arr = TASK_REQUIRE_RES_DESC.split(',');
				html_port += ' <td>' + arr[0] + '/' + arr[1] + '</td>';
				html_port += '<td></td>';
				html_port += '</tr>';
			}
		} else {//不为FALSE的时候可以填值
			if (item.TASK_REQUIRE_TYPE == '3') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				html_port += ' <td>' + item.TASK_REQUIRE_CONTEXT + '</td>';
				if (item.TASK_REQUIRE_SYMBOL == 'ge') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '≤</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'gt') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '< </td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'le') {
					html_port += ' <td> ≤' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'lt') {
					html_port += ' <td> <' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == 'ba') {
					html_port += ' <td>' + item.TASK_REQUIRE_MIX + '-' + item.TASK_REQUIRE_MAX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == '') {
					html_port += ' <td> </td>';
				}
				html_port += '<td><input type="text" onfocus="builesdcs(\'' + item.TASK_REQUIRE_ID + '\',\'' + item.remarkone + '\')"  ONBLUR="buileskip(\'' + item.TASK_REQUIRE_ID + '\')"  style="width:50%;" id="' + item.TASK_REQUIRE_ID + '" value="' + item.TASK_REQUIRE_RESULT + '"/>' + item.TASK_REQUIRE_UNIT;
				html_port += '<input type="text" readonly="readonly" style="width:50%;" id="' + item.TASK_REQUIRE_ID + 'dcs" /></td>';
				html_port += '</tr>';
			} else if (item.TASK_REQUIRE_TYPE == '2') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				html_port += ' <td>' + item.TASK_REQUIRE_CONTEXT + '</td>';
				var sql = "select * from CZ_CCS_PARA where CCS_PARA_ID='" + item.TASK_CCS_PARA_ID + "'";
				var result = db_query(sql);
				if (result[0].TASK_REQUIRE_SYMBOL == 'ge') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '≤</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'gt') {
					html_port += ' <td>' + item.TASK_REQUIRE_MAX + '< </td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'le') {
					html_port += ' <td> ≤' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'lt') {
					html_port += ' <td> <' + item.TASK_REQUIRE_MIX + '</td>';
				}
				if (result[0].TASK_REQUIRE_SYMBOL == 'ba') {
					html_port += ' <td>' + item.TASK_REQUIRE_MIX + '-' + item.TASK_REQUIRE_MAX + '</td>';
				}
				if (item.TASK_REQUIRE_SYMBOL == '') {
					html_port += ' <td> </td>';
				}
				html_port += '<td><input type="text" onfocus="builesdcs(\'' + item.TASK_REQUIRE_ID + '\',\'' + item.remarkone + '\')" ONBLUR="buileskip(\'' + item.TASK_REQUIRE_ID + '\')" style="width:50%;" id="' + item.TASK_REQUIRE_ID + '" value="' + item.TASK_REQUIRE_RESULT + '"/>' + item.TASK_REQUIRE_UNIT + '</td>';
				html_port += '<input type="text" readonly="readonly" style="width:50%;" id="' + item.TASK_REQUIRE_ID + 'dcs" /></td>';
				html_port += '</tr>';
			} else if (item.TASK_REQUIRE_TYPE == '1') {
				html_port += '<tr><td>' + item.TASK_EQU_POSITION_NUM + '</td>';
				html_port += ' <td>' + item.TASK_REQUIRE_CONTEXT + '</td>';

				var TASK_REQUIRE_RES_DESC = item.TASK_REQUIRE_RES_DESC;
				//console.log(TASK_REQUIRE_RES_DESC);
				var arr = TASK_REQUIRE_RES_DESC.split(',');
				if (item.MULCHOICE == 1) {
					html_port += ' <td>在用设备</td>';
					var TASK_REQUIRE_ID = item.TASK_REQUIRE_ID;
					html_port += '<td>';
					for (var i = 0; i < arr.length; i++) {
						if (item.TASK_REQUIRE_RESULT.indexOf(arr[i]) >= 0) {
							html_port += '<input name="test" type="checkbox" value="' + arr[i] + '" checked="checked"/>' + arr[i];
						} else {
							html_port += '<input name="test" type="checkbox" value="' + arr[i] + '"/>' + arr[i];
						}
					}
					html_port += '</td>';
				} else {
					html_port += ' <td>' + arr[0] + '/' + arr[1] + '</td>';
					var TASK_REQUIRE_ID = item.TASK_REQUIRE_ID;
					if (item.TASK_REQUIRE_RESULT == arr[0]) {
						html_port += '<td><input name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[0] + '" checked="checked"/>' + arr[0];
						html_port += '<input onclick="buileskip(\'' + TASK_REQUIRE_ID + '\')" name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[1] + '" />' + arr[1] + '</td>';
					} else if (item.TASK_REQUIRE_RESULT == arr[1]) {
						html_port += '<td><input name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[0] + '" />' + arr[0];
						html_port += '<input onclick="buileskip(\'' + TASK_REQUIRE_ID + '\')" name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[1] + '" checked="checked"/>' + arr[1] + '</td>';
					} else {
						html_port += '<td><input name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[0] + '" />' + arr[0];
						html_port += '<input onclick="buileskip(\'' + TASK_REQUIRE_ID + '\')" name="' + item.TASK_REQUIRE_ID + '" type="radio" value="' + arr[1] + '" />' + arr[1] + '</td>';
					}
				}
				html_port += '</tr>';
			}
		}
	});

	html_port += '</tbody>';
	$("#table_project").html(html_port);

	var newdate = com_formatDate(new Date());
	if (newdate != TASK_TIME) {
		var sql = "SELECT * from CZ_TASK_RECORD where TASK_TIME=" + TASK_TIME + "&TASK_ID=" + TASK_ID;
		var results = db_query(sql);
		$.each(results, function(i, item) {
			$("#" + item.TASK_REQUIRE_ID).val(item.VALUE);
			$("#" + item.TASK_REQUIRE_ID + 'dcs').val(item.UNIT);
		});
	}

}

/****视频功能**********************************************************************************************/
function getCameras() {
	com_getCamera_saves('xjcontent_v', TASK_CONTENT_ID, 1, function(result, data) {
		selectvoid();
	});
}

function selectvoid() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjcontent_v' and PHO_CATEGORY_ID='" + TASK_CONTENT_ID + "'";
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

/****照相功能**********************************************************************************************/
function getCamera() {
	com_getCamera_save('xjcontent_p', TASK_CONTENT_ID, 0, function(result, data) {
		selectphoto();
	});
}

//查询照片数据
function selectphoto() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjcontent_p' and PHO_CATEGORY_ID='" + TASK_CONTENT_ID + "'";
	var results = db_query(sql);
	html_photo = '';
	$.each(results, function(i, item) {
		var id = item.PHO_ID;
		html_photo += '<figure>';
		html_photo += '<a href="' + item.PHO_PATH + '" data-size="571x800"> ';
		html_photo += '<img src="' + item.PHO_PATH + '" /> </a>';
		html_photo += "<input class='button grayscale' type='button' value='删除' style='width:100%;' onclick=\"deletePhoto('" + id + "')\"></input>";
		html_photo += '</figure>';
	});
	$("#photo").html(html_photo);
}

//删除照片
function deletePhoto(PHO_ID) {
	try {
		//删除照片文件
		var results = db_query("select PHO_PATH from CZ_M_PHOTO where PHO_ID='" + PHO_ID + "'");
		$.each(results, function(i, item) {
			if (!file_deleteFile(item.PHO_PATH)) {
				alert("删除文件失败");
				return;
			}
		});

		//删除任务图片表数据(先删)
		var sql1 = "delete from CZ_TASK_INSPECTION_PICTURE where TASK_INSPECTION_PIC_ID='" + PHO_ID + "'";
		if (!db_execute(sql1)) {
			alert("删除任务图片表数据失败！");
			return;
		}
		//删除照片临时表数据（后删）
		var sql2 = "delete from CZ_M_PHOTO where PHO_ID='" + PHO_ID + "'";
		if (!db_execute(sql2)) {
			alert("删除照片临时表数据失败！");
			return;
		}
		//重新加载照片和录像
		selectvoid();
		selectphoto();
	} catch(e) {
		alert(e);
	}
}

/**数据处理************************************************************/
//保存
var i = 0;

var time = com_getNowFormatDate();
var boo = 'true';
function submit() {
	boo = 'true';
	var results = db_query(sql_CZ);
	var ale = '';
	var dscval = '';
	$.each(results, function(i, item) {
		if (boo == 'true') {
			if (item.REMARKFOUR == "TRUE") {
				if (item.TASK_REQUIRE_TYPE == '1') {
					if (item.MULCHOICE == 1) {
						ale = clack();
					} else {
						ale = $('input[name="' + item.TASK_REQUIRE_ID + '"]:checked').val();
						if (ale == null || ale == '') {
							alert("请完成所有需要完成的任务！");
							boo = 'false';
							return;
						} else {
							boo = 'true';
						}
					}
				}
				if (item.TASK_REQUIRE_TYPE == '2') {
					ale = $("#" + item.TASK_REQUIRE_ID).val();
					var reg = /^(\-|\+)?\d+(\.\d+)?$/;
					if (item.TASK_REQUIRE_SYMBOL != '') {
						if (ale.replace(/(^s*)|(s*$)/g, "").length == 0) {
							alert("请完成所有需要完成的任务！");
							boo = 'false';
							return;
						} else if (!reg.test(ale)) {
							alert("请输入正确数值！");
							boo = 'false';
							return;
						} else {
							boo = 'true';
						}
					}
					if (item.TASK_RESULT_STATUS == 'TRUE' || item.TASK_RESULT_STATUS == '') {
						var sqls = "select * from CZ_TASK_PROBLEM_REPORT where TAS_ID='" + item.TASK_REQUIRE_ID + "'";
						var result = db_query(sqls);
						if (result == null || result == '') {
							buileskip(item.TASK_REQUIRE_ID);
						}
					}
				}
				if (item.TASK_REQUIRE_TYPE == '3') {
					ale = $("#" + item.TASK_REQUIRE_ID).val();
					if (item.TASK_REQUIRE_SYMBOL != '') {
						var reg = /^(\-|\+)?\d+(\.\d+)?$/;
						if (ale.replace(/(^s*)|(s*$)/g, "").length == 0) {
							alert("请完成所有需要完成的任务！");
							boo = 'false';
							return;
						} else if (!reg.test(ale)) {
							alert("请输入正确数值！");
							boo = 'false';
							return;
						} else {
							boo = 'true';
						}
					}
				}

				if (item.REMARKFOUR != 'REPORTED') {
					if (item.TASK_RESULT_STATUS == 'FALSE') {
						var sqls = "select * from CZ_TASK_PROBLEM_REPORT where TAS_ID='" + item.TASK_REQUIRE_ID + "'";
						var result = db_query(sqls);
						if (result == null || result == '') {
							if (boo == 'true') {
								buileskip(item.TASK_REQUIRE_ID);
								boo = 'false';
							}
						}
					}
				}
			}
		}
	});
	if (boo == 'true') {
		$.each(results, function(i, item) {
			if (item.TASK_REQUIRE_TYPE == '1') {
				if (item.MULCHOICE == 1) {
					ale = clack();
				} else {
					ale = $('input[name="' + item.TASK_REQUIRE_ID + '"]:checked').val();
				}
			}
			if (item.TASK_REQUIRE_TYPE == '2') {
				ale = $("#" + item.TASK_REQUIRE_ID).val();
				dscval = $("#" + item.TASK_REQUIRE_ID + 'dcs').val();
			}
			if (item.TASK_REQUIRE_TYPE == '3') {
				ale = $("#" + item.TASK_REQUIRE_ID).val();
				dscval = $("#" + item.TASK_REQUIRE_ID + 'dcs').val();
			}
			if (ale == undefined) {
				ale = '';
			}
			var sql = "update CZ_TASK_INSPECTION_REQUIRE set TASK_REQUIRE_RESULT='" + ale + "',TASK_REQUIRE_RECORDTIME='" + com_getNowFormatDate() + "' where TASK_REQUIRE_ID='" + item.TASK_REQUIRE_ID + "'";
			nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
			
			var del_EQU = "delete from CZ_TASK_RECORD where TASK_REQUIRE_ID=" + item.TASK_REQUIRE_ID + "and TASK_TIME='"+TASK_TIME+"'" ;
			nativePage.executeScript('executeBySqlByDefualt("' + del_EQU + '")');
			
			var sql_EQU = "insert into CZ_TASK_RECORD values('" + com_getUuid('uuid') + "','" + item.TASK_REQUIRE_ID + "'," + TASK_ID + ",'" + TASK_POINT_ID + "','" + TASK_CONTENT_ID + "','" + com_formatDate(new Date()) + "','" + com_getNowFormatDate() + "','" + item.TASK_REQUIRE_CONTEXT + "','" + ale + "','" + dscval + "','3','')";
			nativePage.executeScript('executeBySqlByDefualt("' + sql_EQU + '")');
		
		});
		up_server();
	}
}

//dcs数据获取
function builesdcs(TASK_REQUIRE_ID, mun) {
	var data={positionNum:mun};
	var url='http://10.89.90.118:9876/dcsInfo/position';
	$.ajax({
			url : url,
			type : "get",
			data : data,	
			cache : true,
			dataType : "jsonp",
			jsonp : "jsonpCallBack",
			success : function( msg ){
				return callbackName(msg.data);
			},
			error:function(){
				alert("访问服务器失败");	
			}
		});
}

//数据上传到服务器
function up_server() {
			updatea();
			//附件添加到附件信息表
			insertfilrpv();
			//问题上报
			transfer();
			//巡检项任务完成记录
	       var sql_EQU = "insert into CZ_TASK_RECORD values('" + com_getUuid('uuid') + "',''," + TASK_ID + ",'" + TASK_POINT_ID + "','" + TASK_CONTENT_ID + "','" + com_formatDate(new Date()) + "','" + com_getNowFormatDate() + "','','','','2','FINISHED')";
			nativePage.executeScript('executeBySqlByDefualt("' + sql_EQU + '")');
			
			alert("任务完成！");
			//跳转回上一页面
			back();
}

//将问题图片保存到上报表
function transfer() {
	var sql_xjcontent_p = "select * from CZ_M_PHOTO where PHO_CATEGORY_ID='" + TASK_CONTENT_ID + "'";
	//照片附件
	var results_xjcontent_p = db_query(sql_xjcontent_p);
	$.each(results_xjcontent_p, function(i, item) {
		//主键 任务外键  图片名称  上传人  序号
		var sql_1 = "insert into CZ_TASK_INSPECTION_PICTURE(TASK_INSPECTION_PIC_ID,TASK_CONTENT_ID,INS_STORE_NAME,TASK_INSPECTION_UPLOAD_PEOPLE,TASK_INSPECTION_ORDER)" + " values ('" + item.PHO_ID + "','" + item.PHO_CATEGORY_ID + "','" + item.PHO_NAME + "','" + name + "','" + i + "')";
		db_execute(sql_1);
	});
}

//修改任务状态
function updatea() {
	var sql = "update CZ_TASK_INSPECTION_CONTENT set TASK_CONTENT_STATE='FINISHED',TASK_CONTENT_ENDTIME='" + com_getNowFormatDate() + "' where TASK_CONTENT_ID='" + TASK_CONTENT_ID + "'";
	nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
}

//修改数据状态
function update(TASK_REQUIRE_ID) {
	var sql = "update CZ_TASK_INSPECTION_REQUIRE set REMARKONE='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
	nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
}

//打开视频播放
function voides(path) {
	nativePage.executeScript('testOpenVideo2("' + path + '")');
}

//处理事件
function buileskip(TASK_REQUIRE_ID) {
	var sql = "select * from CZ_TASK_INSPECTION_REQUIRE where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
	var result = db_query(sql);
	var arr = result[0].TASK_REQUIRE_RES_DESC.split(',');
	if (result[0].TASK_REQUIRE_TYPE == '1') {
		var value = $('input[name="' + TASK_REQUIRE_ID + '"]:checked').val();
		if (value == arr[1]) {
			layer.open({
				btnAlign : 'l',
				content : '设备数据异常！确定上报异常？',
				btn : ['确认', '取消', '已上报'],
				yes : function(index, layero) {
					//按钮【按钮一】的回调
					boo = 'false';
					up();
					//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
					//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
					//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
					inspection(result[0].TASK_REQUIRE_ID);
				},
				btn2 : function(index, layero) {
					//按钮【按钮二】的回调
					boo = 'false';
					return;
				},
				btn3 : function(index, layero) {
					//按钮【按钮三】的回调
					var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
					nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
					return;
				},
				cancel : function() {
				}
			});
		}
	}
	if (result[0].TASK_REQUIRE_TYPE == '2') {
		var tale = $("#" + TASK_REQUIRE_ID).val();
		if (result[0].TASK_REQUIRE_SYMBOL != '') {
			var reg = /^(\-|\+)?\d+(\.\d+)?$/;
			if (tale.replace(/(^s*)|(s*$)/g, "").length == 0) {
				alert("请输入数值！");
				boo = 'false';
				return;
			} else if (!reg.test(tale)) {
				alert("请输入正确数值！");
				boo = 'false';
				return;
			}
			var sqlc = "select * from CZ_CCS_PARA where CCS_PARA_ID='" + result[0].TASK_CCS_PARA_ID + "'";
			var resultc = db_query(sqlc);
			if (resultc[0].TASK_REQUIRE_SYMBOL == 'ge') {
				if (result[0].TASK_REQUIRE_MAX - tale <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (resultc[0].TASK_REQUIRE_SYMBOL == 'gt') {
				if (result[0].TASK_REQUIRE_MAX - tale < 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (resultc[0].TASK_REQUIRE_SYMBOL == 'le') {
				if (tale - result[0].TASK_REQUIRE_MIX <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (resultc[0].TASK_REQUIRE_SYMBOL == 'lt') {
				if (tale - result[0].TASK_REQUIRE_MIX < 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (resultc[0].TASK_REQUIRE_SYMBOL == 'ba') {
				if (result[0].TASK_REQUIRE_MIX - tale <= 0 && tale - result[0].TASK_REQUIRE_MAX <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
		}
	}
	if (result[0].TASK_REQUIRE_TYPE == '3') {
		var tale = $("#" + TASK_REQUIRE_ID).val();
		if (result[0].TASK_REQUIRE_SYMBOL != '') {
			var reg = /^(\-|\+)?\d+(\.\d+)?$/;
			if (tale.replace(/(^s*)|(s*$)/g, "").length == 0) {
				alert("请输入数值！");
				boo = 'false';
				return;
			} else if (!reg.test(tale)) {
				alert("请输入正确数值！");
				boo = 'false';
				return;
			}
			if (result[0].TASK_REQUIRE_SYMBOL == 'ge') {
				if (result[0].TASK_REQUIRE_MAX - tale <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (result[0].TASK_REQUIRE_SYMBOL == 'gt') {
				if (result[0].TASK_REQUIRE_MAX - tale < 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (result[0].TASK_REQUIRE_SYMBOL == 'le') {
				if (tale - result[0].TASK_REQUIRE_MIX <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (result[0].TASK_REQUIRE_SYMBOL == 'lt') {
				if (tale - result[0].TASK_REQUIRE_MIX < 0) {
					return
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
			if (result[0].TASK_REQUIRE_SYMBOL == 'ba') {
				if (result[0].TASK_REQUIRE_MIX - tale <= 0 && tale - result[0].TASK_REQUIRE_MAX <= 0) {
					return;
				} else {
					layer.open({
						btnAlign : 'l',
						content : '设备数据异常！确定上报异常？',
						btn : ['确认', '取消', '已上报'],
						yes : function(index, layero) {
							//按钮【按钮一】的回调
							boo = 'false';
							up();
							//var sqlS = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							//nativePage.executeScript('executeBySqlByDefualt("' + sqlS + '")');
							//window.open("patrolinspection_inspection.html?processtype=1&id=" + result[0].TASK_REQUIRE_ID + "&ids=" + result[0].TASK_CONTENT_ID);
							inspection(result[0].TASK_REQUIRE_ID);
							return;
						},
						btn2 : function(index, layero) {
							//按钮【按钮二】的回调
							boo = 'false';
							return;
						},
						btn3 : function(index, layero) {
							//按钮【按钮三】的回调
							var sqls = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='FALSE',REMARKFOUR='REPORTED' where TASK_REQUIRE_ID='" + TASK_REQUIRE_ID + "'";
							nativePage.executeScript('executeBySqlByDefualt("' + sqls + '")');
							return;
						},
						cancel : function() {
						}
					});
				}
			}
		} else {
		}
	}
}

function up() {
	var results = db_query(sql_CZ);
	$.each(results, function(i, item) {
		if (item.TASK_REQUIRE_TYPE == '1') {
			ale = $('input[name="' + item.TASK_REQUIRE_ID + '"]:checked').val();
		} else if (item.TASK_REQUIRE_TYPE == '2') {
			ale = $("#" + item.TASK_REQUIRE_ID).val();
		} else if (item.TASK_REQUIRE_TYPE == '3') {
			ale = $("#" + item.TASK_REQUIRE_ID).val();
		}
		var sql = "update CZ_TASK_INSPECTION_REQUIRE set TASK_REQUIRE_RESULT='" + ale + "' where TASK_REQUIRE_ID='" + item.TASK_REQUIRE_ID + "'";
		if (item.TASK_RESULT_STATUS == '' || item.TASK_RESULT_STATUS == null) {
			sql = "update CZ_TASK_INSPECTION_REQUIRE set TASK_RESULT_STATUS='TRUE',TASK_REQUIRE_RESULT='" + ale + "' where TASK_REQUIRE_ID='" + item.TASK_REQUIRE_ID + "'";
		}
		nativePage.executeScript('executeBySqlByDefualt("' + sql + '")');
	});
}

//附件添加到附件信息表
function insertfilrpv() {
	var sql_xjcontent_p = "select * from CZ_M_PHOTO where PHO_CATEGORY_ID='" + TASK_CONTENT_ID + "'";
	//照片附件
	var results_xjcontent_p = db_query(sql_xjcontent_p);
	$.each(results_xjcontent_p, function(i, item) {
		//主键 任务外键  图片名称  上传人  序号
		var sql_1 = "insert into CZ_TASK_INSPECTION_PICTURE(TASK_INSPECTION_PIC_ID,TASK_CONTENT_ID,INS_STORE_NAME,TASK_INSPECTION_UPLOAD_PEOPLE,TASK_INSPECTION_ORDER,TASK_INSPECTION_ADDRESS,REMARKONE)" + " values ('" + item.PHO_ID + "','" + item.PHO_CATEGORY_ID + "','" + item.PHO_NAME + "','" + name + "','" + i + "','" + item.PHO_PATH + "','" + item.REMARK + "')";
		db_execute(sql_1);
	});
}

//异常录入
function inspection(TASK_REQUIRE_ID) {
	window.open("patrolinspection_inspection.html?processtype=1&TASK_REQUIRE_ID=" + TASK_REQUIRE_ID + "&TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_CONTENT_ID=" + TASK_CONTENT_ID + "&TASK_ID=" + TASK_ID);
}

//右下角返回页面
function back() {
	jump("patrolinspection_project.html?TYPE=1&TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_ID=" + TASK_ID+ "&TASK_TIME=" + TASK_TIME);
}

function bluckgetUrl() {
	up();
	var html_url = '../../problemReport.html';
	//页面跳转
	jump(html_url);
}

//获取复选框的值
function clack() {
	var res = '';
	$('input[name="test"]:checked').each(function() {
		if (i == 0) {
			res = $(this).val();
		} else {
			res = res + "," + $(this).val();
		}
		i++;
	});
	return res;
}
