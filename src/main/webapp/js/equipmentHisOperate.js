(function(window){
	/**
	 * 参数定义
	 */
	var win = window, layer = layui.layer, table = layui.table, element = layui.element;
	var tableOption, optionCol;
	var EQU_ID, EQU_POSITION_NUM, EQU_MEMO_ONE;
	var getHisOperateAllUrl = "getEquipmentHisOperate.action",	
		resaveUrl = "resaveEquipentData.action",
		insertUrl = "insertEquipentData.action",
		getPositionNumUrl = "getEquipmentNum.action";
	
	/**
	 * 参数页面初始化
	 */		
	//初始化location参数
	console.log( '------初始化location参数-------' );
	var getUrlParam = function ( name ) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
	EQU_ID = getUrlParam( "EQU_ID" );
	EQU_POSITION_NUM = decodeURIComponent( getUrlParam( "EQU_POSITION_NUM" ) );
	EQU_MEMO_ONE = decodeURIComponent( getUrlParam( "EQU_MEMO_ONE" ) );
	console.log( EQU_ID );
	console.log( EQU_POSITION_NUM );
	console.log( EQU_MEMO_ONE );
	
	//初始化标题
	$( 'legend' ).text( '设备:' + EQU_POSITION_NUM + $( 'legend' ).text() );
	
	//初始化表格参数optionCol
	optionCol = [
		{fixed:'left',type: 'numbers'}	,
		{field: "TYPE", title: "操作类型", width: 100, align:'center', templet: '#operateType'},
		{field: "OPERATEDATE", title: "修改时间", width: 170, sort: true,align:'center'},
		{field: "EQU_MEMO_ONE", title: "设备大类", width: 100, sort: true,align:'center'},
		{field: "EQU_POSITION_NUM", title: "设备位号", width: 100, sort: true,align:'center'},
	];
	console.log( '------初始化表格参数optionCol-------' );
	$.each( equipmentStructure, function( index, item ){
		if( $.trim( EQU_MEMO_ONE ) == $.trim( index ) ){
			$.each( item, function( key, value ){
				console.log( 'key:' + key + " value:" + value );
				var json = {};
				json.field = value;
				json.title = key;
				json.width = 120;
				json.sort = true;
				json.align = 'center';
				console.log( json );
				optionCol.push( json );
			})
		}
	})
	optionCol.push( {fixed: 'right', title:'操作', toolbar: '#bar', width:100, align:'center'} );
	console.log( optionCol );
	
	//初始化表格参数tableOption
	tableOption = {
			elem: '#table_operate'
		    ,url: getHisOperateAllUrl
		   //,toolbar: '#toolbar'
		    ,title: '设备历史操作记录表'
		    ,cols: [optionCol]
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
			},
			where: {
				"positionNum": EQU_POSITION_NUM
			}
	};
	
	//初始化表格
	console.log( '------初始化表格-------' );
	table.render( tableOption );	
	
	
	/**
	 * 事件绑定
	 */
	table.on( 'toolbar(table_operate)' , function(obj){
		var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	    	case 'button_equipment':
	    		var data = checkStatus.data;
	    		btnFunc.search( data );
	    		break;
	    	case 'getCheckLength':
	    		var data = checkStatus.data;
	    		break;
	    	case 'isAll':
	    		break;
	    };
	});
	 //监听行工具事件
	table.on('tool(table_operate)', function(obj){
		var data = obj.data;
	    if(obj.event === 'del'){	    	
	    } else if(obj.event === 'resave'){
	    	btnFunc.resave( data );
	    }
	  });
	
	/**
	 * 函数
	 */
	var btnFunc = {
			"search": function( data ){
				console.log( '------搜索按钮单击事件-------' );
				console.log( data );
				//获取值
				var searchValue = $( '#input_equipment' ).val();
				console.log( searchValue );
				//重载表格
				table.reload( 'id1', {
					where: {
						"positionNum": $.trim( searchValue )
					}
				})
			},
			"resave": function( data ){
				console.log( '------恢复按钮单击事件-------' );
//				console.log( data );
//				console.log(  $.param( data ) );
				//检查数据是否存在-不存在直接插入
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
								//请求恢复数据
								$.ajax({
									type : "POST",
									url : resaveUrl ,
									data : $.param( data ) ,
									async : true, //默认
									dataType : "json",
									success : function( data ){
										console.log( '恢复按钮请求成功回调函数' );
										layer.msg( '恢复数据成功', {icon:1});
										//刷新表格
										table.reload('id1',{});
									},
									error : function(){
										layer.msg( '恢复按钮请求失败', {icon:3});
									}		       
								});
							}else if( jsonData.state == 0 && jsonData.data == "" ){
								btnFunc.insert( data );
							}else{
								layer.msg( '请求处理失败', {icon:2} );
							}
						}else{
							layer.msg( '请求发送失败', {icon:2} );
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
})( window )