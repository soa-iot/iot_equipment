	var url = "";
	var type = "";
	var PIID = "";
	var ProblemID = "";
	var operatorID = "";
	function reloadData()
	{
		var warnType = $('#WARN_TYPE').combobox('getValue');
		var beginDate = $('#beginDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		var desc = $('#DESCRIBE').textbox('getValue');
		var status = $('#STATUS').combobox('getValue');
		$("#dg").datagrid("reload",
		{
			"WARN_TYPE" : warnType,
			"beginDate" : beginDate,
			"endDate" : endDate,
			"DESCRIBE" : desc,
			"STATUS": status
		});
	}
	function reloadAllData()
	{
		$('#WARN_TYPE').combobox('clear');
		$('#beginDate').datebox('clear');
		$('#endDate').datebox('clear');
		$('#DESCRIBE').textbox('clear');
		$('#STATUS').textbox('clear');
		$("#dg").datagrid("reload",
		{});
	}
	function sendMessage(val, row)
	{
		return '<a href="javascript:void(0)" onclick="sendMessageWindow()">发送短信</a>';
	}
	function showDetail(val, row, index)
	{
		return '<a href="javascript:void(0);" onclick="show(' + index + ')">查看详情</a>';
	}
	function show(index)
	{
		var row = $("#dg").datagrid("getData").rows[index];
	/* 	$('<div></div>').dialog(
		{
			id : 'newDialog',
			title : '详细信息',
			width : 550,
			height : 180,
			closed : false,
			cache : false,
			href : 'GetNoticeInfoServlet.action?DETAILS=true&WARN_TYPE=' + row.WARN_TYPE + '&KEY=' + row.KEY,
			modal : true
		}); */
		
		switch (row.WARN_TYPE) {
		case "1"://设备检定/校验预警
			$.ajax({
			    url:'./GetNoticeInfoServlet.action',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    data:{
			    	DETAILS:true,
			    	WARN_TYPE:row.WARN_TYPE,
			    	KEY:row.KEY
			    },
			    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
			    success:function(data){
			        var equName = data.EQU_NAME;
			        var equRfid = data.EQU_RFID;
			        window.location.href = "equipment_detail.html?EQU_ID="+row.KEY+"&EQU_NAME="+equName+"&EQU_RFID="+equRfid;
			    },
			    error:function(xhr,textStatus){
			       console.log("发送请求失败！！！");
			    }
			});
			break;
		case "2"://备品备件库存预警
			if(row.STATUS == "已处理"){
				//已处理，关联到备品备件
				window.location.href = "materialDetial.html?PIID="+row.REMARK1;//PIID
			} else if(row.STATUS == "未处理"){
				//未处理，提示其处理
				$.messager.alert('消息提醒','该备件未制定需求计划单或采购流程正在进行中!','info');
			}
			break;
		case "3"://能耗预警
			window.location.href = "http://192.168.18.22:10039/cz_energy/Energyanaly/energy.html";
			break;
		case "4"://HART预警
		case "5"://中控预警
		case "6"://中控对比预警
			var PIID = row.REMARK1;
			var desc = row.REMARK2;
			if(PIID != null && PIID.trim() != ""){
				var url = url = "questionProcessMonitor.html?PIID=" + PIID;
				var iTop = (window.screen.height - 30 - 900) / 2;
				var iLeft = (window.screen.width - 10 - 1200) / 2;
				window
						.open(
								url,
								'问题处理详细',
								'height=900, width=1200, top='
										+ iTop
										+ ', left='
										+ iLeft
										+ ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
			
			} else{
				//$.messager.alert('消息提示','未启动流程，无法查看！！！','error');
				if(desc == null || desc.trim() == ""){
					$.messager.alert('消息提示','未启动流程并且处理结果描述为空，无法查看详情！！！','error');
				} else {
					$.messager.alert('处理结果查看',desc,'info');
				}
				}
			break;
		default:
			break;
		}
		
		
		
		
		
	}
	function sendMessageWindow()
	{
		alert("短信发送成功");
	}
	function fixWidth(percent)
	{
		return (document.body.clientWidth - 430) * percent;
	}
	function init()
	{
		//console.log(getCookiePara("userDept"));
		url = "./GetNoticeInfoServlet.action";
		$('#dg').datagrid('options').url = url;
		$('#dg').datagrid("load",
		{});
		
		//获取cookie中的值
		var user = getCookiePara("userID");
		//console.log(user);
		
		/*获取处理按钮和启动流程按钮的权限人*/
		$.ajax({
		    url:url,
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:{
		    	user:user
		    },
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data){
		    	//console.log(">>>>>>>>>>>>>>>>");
		        if(data.hasPower == "TRUE"){
		        	$(".adminBtn").removeClass("adminBtn");
		        }
		    },
		    error:function(xhr,textStatus){
		       console.log("获取权限人失败 ！！！");
		    }
		});
	}
	
	//从cookie获取所传入参数的值
	function getCookiePara(key){
		var cookie = document.cookie;
		//console.log(cookie);
		var value = "";
		var strs = cookie.split(";");
		for(var i = 0;i < strs.length ;i ++){
			var arr = strs[i].split("=");
			if(key == arr[0].trim()){
				value = unescape(arr[1]);
				break;
			}
		}
		
		return value;
	}
	
	/*
		选择一条预警消息，将其状态改为已处理
	*/
	function changeStatus(){
		var row = $('#dg').datagrid('getSelected');
		if(row == null || row == ""){
			$.messager.alert('消息提示','请选择一条记录！！！','error');
			return;
		}
		
		if(row.STATUS == "已处理"){
			$.messager.alert('消息提示','该条预警信息的状态已经是已处理！！！','info');
			return;
		}
	
		var warnType = row.WARN_TYPE;
		switch (warnType) {
		case "1"://设备检定/校验预警
			$.messager.alert('消息提示','设备检定/校验预警不能修改状态！！！','error');
			break;
		case "2"://备品备件库存预警
			$.messager.alert('消息提示','备品备件库存预警不能修改状态！！！','error');	
			break;
		case "3"://能耗预警
			var message = "能耗预警需要您线下处理，若您已经确认处理，则可以点击确认；否者请先处理再标记为已处理状态！！！您确定要标记为已处理么？";
			doChangeStatus(message,row);
			break;
		case "4"://HART预警
		case "5"://中控预警
		case "6"://中控对比预警
			var message = "若您已经根据该预警信息启动检维修流程，并问题已经处理，则可修改为已处理状态！否则先启动流程，并处理好了之后再修改预警状态！您确定要标记为已处理么？";
			doChangeStatus(message,row);
			break;
		default:
			break;
		}	
	}
	
	
	function doChangeStatus(message,row){
		$.messager.confirm('消息提示',message,function(flag){
			//console.log(row);
			if(flag){
				/*
				发送请求，修改预警信息的状态为已处理
			*/
			$.messager.prompt('处理结果描述','请输入处理结果描述：',function(resultDesc){
				
				//console.log(">>>>>>>>>>>>>>>>>")
				$.ajax({
				    url:'./ChangeAlarmStatusServlet.action',
				    type:'POST', //GET
				    async:true,    //或false,是否异步
				    data:{
				    	WARN_TYPE : row.WARN_TYPE,
				    	ID : row.ID,
				    	RESULTDESC : resultDesc
				    },
				    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
				    success:function(data){
				    	//console.log("data>>>>>>>>>" + data);
				    	if(data.status == "0"){
				    		//console.log("?>>>>>>>>>>>>>");
				    		$.messager.alert('消息提示','修改预警状态成功！！！','info',reloadData);
				    	} else {
				    		console.log("1111>>>>>>>>>");
				    		$.messager.alert('消息提示','修改状态失败！！！','error');
				    	}
				    },
				    error:function(xhr,textStatus){
				    	console.log("22222>>>>>>>>>");
				    	$.messager.alert('消息提示','修改状态失败！！！','error');
				    }
				});
			});
				
			}
		});
	
	}
	
	
	
	/*
		启动流程
	*/
	function startProcess(){
		var row = $('#dg').datagrid('getSelected');
		if(row == null || row == ""){
			$.messager.alert('消息提示','请选择一条记录！！！','error');
			return;
		}
		var problemUrl = "http://192.168.18.22:10039/dynamicui/?UIID=6C573410756244F3A8BC2526CDAFF3F4&desc="+row.DESCRIBE;
		var warnType = row.WARN_TYPE;
		switch (warnType) {
		case "1"://设备检定/校验预警
			$.messager.alert('消息提示','设备检定/校验预警不能手动启动流程！！！','error');
			break;
		case "2"://备品备件库存预警
			$.messager.alert('消息提示','备品备件库存预警不能手动启动流程！！！','error');	
			break;
		case "3"://能耗预警
			$.messager.alert('消息提示','能耗预警不能手动启动流程！！！','error');
			break;
		case "4"://HART预警
		case "5"://中控预警
		case "6"://中控对比预警
			/* problemUrl = problemUrl + "&type=其他";
			var iTop = (window.screen.height - 30 - 700) / 2;
			var iLeft = (window.screen.width - 10 - 955) / 2;
			window.open(
							problemUrl,
							'问题上报',
							'height=700, width=955, top='
									+ iTop
									+ ', left='
									+ iLeft
									+ ', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no'); */
			
			$.ajax({
			    url:'./StartProcessOfAlarmServlet.action',
			    type:'POST', //GET
			    async:true,    //或false,是否异步
			    data:{
			    	WARN_TYPE : warnType,
			    	KEY : row.KEY,
			    	DESCRIBE : row.DESCRIBE,
			    	user : getCookiePara("userID"),
			    	ID : row.ID
			    },
			    dataType:'json',
			    success:function(data){
			    	if(data.status == "0"){
			    		//PIID
			    		var PIID = data.PIID;
			    		$.messager.alert('消息提示','启动流程成功！！！请至代办页面处理！','info',reloadData);
			    	} else if(data.status == "1") {
			    		$.messager.alert('消息提示','该条预警信息已经启动过一个流程，请至查看详情页面查看其最新情况！！！','error');
			    	} else {
			    		$.messager.alert('消息提示','启动流程过程中出现了问题，请联系管理员！！！','error');
			    	}
			    },
			    error:function(xhr,textStatus){
			    	$.messager.alert('消息提示','启动流程过程中出现了问题，请联系管理员！！！','error');
			    }
			});
			break;
		default:
			break;
		}
	}
	