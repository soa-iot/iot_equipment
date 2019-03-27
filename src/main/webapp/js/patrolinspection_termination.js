/**
 *巡回检查_任务超期处理
 */
var TASK_ID = '';
//任务ID
var type = '';
var taskId="";
//分类
var TASK_POINT_ID = '';
var TASK_CONTENT_ID = '';
document.addEventListener("plusready", onPlusReady, false);
function onPlusReady() {
	TASK_ID = getQueryString('TASK_ID');
	TASK_POINT_ID = getQueryString('TASK_POINT_ID');
	type = getQueryString('type');
	if (type == 1) {
		taskId=TASK_POINT_ID;
		initdata1();
	}
	if (type == 2) {
		
		TASK_CONTENT_ID = getQueryString('TASK_CONTENT_ID');
		taskId=TASK_CONTENT_ID;
		initdata2();
	}
	//加载照片
	selectphoto();
	//加载视频
	selectvoid();
	//获取当前用户名
}

function initdata1() {
	//获取当前任务信息
	var sql = "select * from CZ_TASK_INSPECTION_POINT where TASK_POINT_ID='" + TASK_POINT_ID + "'";
	var result = db_query(sql);
	document.getElementById("title").innerText = result[0].TASK_POINT_NAME;
	var remake=result[0].REMARKONE;
	if(result[0].REMARKONE==""||result[0].REMARKONE==null){
		remake="备用";
	}
	document.getElementById("textarea1").value = remake;
}

function initdata2() {
	//获取当前任务信息
	var sql = "select * from CZ_TASK_INSPECTION_CONTENT where TASK_CONTENT_ID='" + TASK_CONTENT_ID + "'";
	var result = db_query(sql);
	document.getElementById("title").innerText = result[0].TASK_CONTENT_NAME;
	var remake=result[0].REMARKONE;
	if(result[0].REMARKONE==""||result[0].REMARKONE==null){
		remake="备用";
	}
	document.getElementById("textarea1").value = remake;
}

//保存
function save() {
	problemDescription = $("#textarea1").val();
	if (problemDescription.replace(/(^\s*)/g, "") == '' || problemDescription.replace(/(^\s*)/g, "") == null) {
		alert("请输入终止原因！");
		return;
	} else {
		if (type == 1) {
			var sql = "update CZ_TASK_INSPECTION_POINT set TASK_POINT_STATE='TERMINATION',REMARKONE='" + problemDescription + "' where TASK_POINT_ID='" + TASK_POINT_ID + "'";
			db_execute(sql);
			insertfilrp();
			alert('保存成功！');
			window.open("patrolinspection_port.html?TASK_ID=" + TASK_ID );
		}
		if (type == 2) {
			var sql = "update CZ_TASK_INSPECTION_CONTENT set TASK_CONTENT_STATE='TERMINATION',REMARKONE='" + problemDescription + "' where TASK_CONTENT_ID='" + TASK_CONTENT_ID + "'";
			db_execute(sql);
			insertfilrp();
			alert('保存成功！');
			window.open("patrolinspection_project.html?TASK_POINT_ID=" + TASK_POINT_ID + "&TASK_ID=" + TASK_ID);
		}

	}
}

/****照相功能**********************************************************************************************/
function getCamera() {
	com_getCamera_save('xjoverdue_p', taskId, 0, function(result, data) {
		selectphoto();
	});
}

//查询照片数据
function selectphoto() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjoverdue_p' and PHO_CATEGORY_ID='" + taskId + "'";
	nativePage.executeScript('queryAndReturnByDefualt("' + sql + '","photo_finished_callback")');
}

function photo_finished_callback(results) {
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
		//重新加载照片
		selectphoto();
		//重新加载视频
		selectvoid();
	} catch(e) {
		alert(e);
	}
}

/****视频功能**********************************************************************************************/
function getCameras() {
	com_getCamera_saves('xjoverdue_v', taskId, 1, function(result, data) {
		selectvoid();
	});
}

function selectvoid() {
	var sql = "select * from CZ_M_PHOTO where PHO_CATEGORY='xjoverdue_v' and PHO_CATEGORY_ID='" + taskId + "'";
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

//打开视频
function voides(path) {
	nativePage.executeScript('testOpenVideo2("' + path + '")');
}

//附件添加到附件信息表
function insertfilrp() {
	var sql_xjcontent_p = "select * from CZ_M_PHOTO where PHO_CATEGORY_ID='" + taskId + "'";
	//照片附件
	var results_xjcontent_p = db_query(sql_xjcontent_p);
	$.each(results_xjcontent_p, function(i, item) {
		//主键 任务外键  图片名称  上传人  序号
		var sql_1 = "insert into CZ_TASK_INSPECTION_PICTURE(TASK_INSPECTION_PIC_ID,TASK_CONTENT_ID,INS_STORE_NAME,TASK_INSPECTION_UPLOAD_PEOPLE,TASK_INSPECTION_ORDER,REMARKONE)" + " values ('" + item.PHO_ID + "','" + item.PHO_CATEGORY_ID + "','" + item.PHO_NAME + "','" + name + "','" + i + "','" + item.REMARK + "')";
		db_execute(sql_1);
	});
}


//页面跳转
function chocked() {
	var reason = '备用,装置检修,其他';
	var html_reason = '';
	var reasons = reason.split(',');
	for (var i = 0; i < reasons.length; i++) {
		html_reason += '&nbsp;&nbsp;&nbsp;<tr><input type="checkbox" name="check">';
		html_reason += '<span>' + reasons[i] + '</span></input><br />';
	}
	layer.open({
		title : '选择原因',
		closeBtn : 1, //不显示关闭按钮
		area : ['500px', 'auto'],
		btn : ['确定'],
		yes : function(index, layero) {
			//按钮【按钮一】的回调
			confirms();
		},
		shadeClose : true,
		type : 1,
		skin : 'layui-layer-demo', //样式类名
		closeBtn : 0, //不显示关闭按钮
		anim : 2,
		shadeClose : true, //开启遮罩关闭
		content : html_reason
	});

	//jump('issued_list.html?uuid=' + uuid);
}

function confirms() {
	var coades = '';
	//确认
	var i = 0;
	$('input[name="check"]:checked').each(function() {
		if (i == 0) {
			coades = $(this).next('span').text();
		} else {
			coades = coades + '、' + $(this).next('span').text();
		}
		i++;
	});
	$("#textarea1").val(coades);
	layer.closeAll();
}