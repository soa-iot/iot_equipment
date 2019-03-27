(function(window){
	/**
	 * 参数定义
	 */
	var win = window, layer = layui.layer, table = layui.table, element = layui.element, laydate = layui.laydate;
	var tableOption, optionCol;
	var getHisDelOperateAllUrl = "getEquipmentDelHisOperate.action",
		modifyUrl = "modifyEquipentData.action",
		insertUrl = "insertEquipentData.action",
		getPositionNumUrl = "getEquipmentNum.action";
	
	/**
	 * 参数页面初始化
	 */
	//初始化日期input_time
	laydate.render({
		elem: '#input_time',
		type: 'date',
		format: 'yyyy-MM-dd'
		//value: new Date()
	});
	element.init();
	
	//初始化表格参数tableOption
	tableOption = {
			elem: '#table_operate'
		    ,url: getHisDelOperateAllUrl
		    ,toolbar: '#toolbar'
		    ,title: '设备历史删除记录表'
		    ,cols: [[
		    	{fixed:'left',type: 'numbers'}	,
				{field: "TYPE", title: "操作类型", width: '10%', align:'center', templet: '#operateType'},
				{field: "OPERATEDATE", title: "修改时间", width: '16%', sort: true,align:'center'},
				{field:'EQU_POSITION_NUM', title:'设备位号', width:'12%', align:'center'},
		    	{field:'WEL_NAME', title:'装置列名', width:'12%', align:'center'},
		    	{field:'WEL_UNIT', title:'装置单元', width:'12%', align:'center'},
		    	{field:'EQU_MEMO_ONE', title:'设备类别', width:'12%', align:'center'},		    	
		    	{field:'EQU_NAME', title:'设备名称', width:'10%', align:'center'},
		    	{fixed: 'right', title:'操作', toolbar: '#bar', width:'18%', align:'center'}
		    ]]
		    ,page: true
		    ,limit: 10
		    ,limits: [5,10,15,20]
			,id: "id1"
			,parseData: function(res){ //res 即为原始返回的数据
			    return {
			        "code": res.state, //解析接口状态
			        "msg": res.message, //解析提示文本
			        "count": res.count, //解析数据长度
			        "data": res.data //解析数据列表
			    };
			}
	};
	
	//初始化表格
	console.log( '------初始化表格-------' );
	table.render( tableOption );				
	
	/**
	 * 函数
	 */
	var resaveDataF = {
			"update": function( data ){
				//恢复更新设备数据
				$.ajax({
					type : "POST",
					url : modifyUrl ,
					data : $.param( data ) ,
					async : true, //默认
					dataType : "json",
					success : function( jsonData ){
						if( jsonData != null ){
							if( jsonData.state == 0 && jsonData.data != null ){
								console.log( '------恢复按钮请求成功回调函数-------' );
								layer.msg( '恢复数据成功', {icon:1});
								//刷新表格
								table.reload();
								laydate.render();
							}else{
								layer.msg( '请求处理失败', {icon:3} );
							}							
						}else{
							layer.msg( '请求发送失败', {icon:3} );
						}												
					},
					error : function(){
						layer.msg( '恢复按钮请求失败', {icon:3});
					}		       
				});
			},
			"insert": function( data ){
				//恢复更新设备数据
				$.ajax({
					type : "POST",
					url : insertUrl ,
					data : $.param( data ) ,
					async : true, //默认
					dataType : "json",
					success : function( jsonData ){
						if( jsonData != null ){
							if( jsonData.state == 0 && jsonData.data != null ){
								console.log( '------恢复按钮请求成功回调函数-------' );
								layer.msg( '恢复数据成功', {icon:1});
								//刷新表格
								table.reload('id1',{});
								laydate.render();
							}else{
								layer.msg( '请求处理失败', {icon:3} );
							}							
						}else{
							layer.msg( '请求发送失败', {icon:3} );
						}		
					},
					error : function(){
						layer.msg( '恢复按钮请求失败', {icon:3});
					}		       
				});
			}
	}
	var btnFunc = {
			"searchTime": function( data ){
				console.log( '------搜索时间按钮单击事件-------' );
				//获取值
				var timeValue = $( '#input_time').val();
				console.log( timeValue );
				
				//重载表格
				table.reload( 'id1', {
					where: {
						"time": timeValue
					}
				});
				laydate.render();
			},
			"searchNum": function( data ){
				console.log( '------搜索位号按钮单击事件-------' );
				//获取值
				var numValue = $( '#input_num' ).val();
				console.log( numValue );
				
				//重载表格
				table.reload( 'id1', {
					where: {
						"positionNum": numValue,
					}
				});
				laydate.render();
			},
			"reset": function( data ){
				console.log( '------重置按钮单击事件-------' );
				
				//重载表格
				table.reload( 'id1', {});
				laydate.render();
			},
			"resave": function( data ){
				console.log( '------恢复按钮单击事件-------' );
//				console.log( data );
//				console.log(  $.param( data ) );
				
				//检查数据是否存在
				console.log( '检查设备位号是否存在' );
				$.ajax({
					type : "get",
					url : getPositionNumUrl ,
					data : $.param( data ) ,
					async : true, //默认
					dataType : "json",
					success : function( jsonData ){
						console.log( '检查设备位号请求成功回调' );
						if( jsonData != null ){
							if( jsonData.state == 0 && jsonData.data != "" ){
								//提示
								layer.confirm('当前位号已经存在于台账，是否确定修改此尾号设备 ', {
									  btn: ['确定', '取消']
									}, function(index, layero){
										resaveDataF.update( data );										
									}, function(index){
										layer.close( index );
									}
								);
							}else if( jsonData.state == 0 && jsonData.data == "" ){
								resaveDataF.insert( data );
							}else{
								layer.msg( '请求处理失败', {icon:2} );
							}
						}else{
							layer.msg( '请求发送失败', {icon:2} );
						}						
					},
					error : function(){
						layer.msg( '恢复按钮请求失败', {icon:2});
					}		       
				});				
			}	
	}
	
	var rowF = {
			"detail": function( data ){
				console.log( '------查看设备历史操作详情页面-------' );
				console.log( data );
				var EQU_ID=data.EQU_ID, EQU_POSITION_NUM=data.EQU_POSITION_NUM, 
					EQU_MEMO_ONE=data.EQU_MEMO_ONE; 
				var param = "?a=1&DETAIL_URL=1&EQU_ID="+EQU_ID+"&EQU_MEMO_ONE=" + 
					encodeURI(encodeURI(EQU_MEMO_ONE)) + "&EQU_POSITION_NUM=" + 
					encodeURI(encodeURI(EQU_POSITION_NUM));
				//弹出页-具体设备操作历史记录
				layer.open({
					type: 2,
					title: '设备操作历史记录',
					btn: ['关闭'],
					area: ['700px','450px'],
					content: 'equipmentHisOperate.html' + param,
					//此处要求返回的格式
					success: function(){				
					},
					yes: function( index, sonDom ){
						layer.close( index );
					}
//					btn2: function(index, layero){
//					    //return false 开启该代码可禁止点击该按钮关闭
//						layer.close( index );
//					},
//					cancel: function( index, layero ){
//						layer.close( index );
//					},
				});
			}
	}
	
	/**
	 * 事件绑定
	 */
	table.on( 'toolbar(table_operate)' , function(obj){
		var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	    	case 'button_time':
	    		var data = checkStatus.data;
	    		btnFunc.searchTime( data );
	    		break;
	    	case 'button_num':
	    		var data = checkStatus.data;
	    		btnFunc.searchNum( data );
	    		break;
	    	case 'button_reset':
	    		btnFunc.reset( data );
	    		break;
	    };
	});
	
	//监听行工具事件
	table.on('tool(table_operate)', function(obj){
		var data = obj.data;
	    if(obj.event === 'detail'){	 
	    	rowF.detail( data );
	    } else if(obj.event === 'resave'){
	    	btnFunc.resave( data );
	    }
	});
})( window )