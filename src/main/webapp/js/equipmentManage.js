var ipPort = "http://localhost:10238";

/*
 * 参数定义
 */
// 新增数据行请求url
var addRowUrl = "insertEquipentData.action", modifyRowUrl = "modifyEquipentData.action", deleteRowUrl = "deleteEquipentData.action", searchDataUrl = "getEqupmentDetailInfo.action", excelDownloadUrl = ipPort
		+ "/iot_equipment/ExportExcelOfEquServlet.action", excelUploadUrl = ipPort
		+ "/iot_equipment/ImportOfEquServlet.action", modifyBatchUrl = ipPort
		+ "/iot_equipment/modifyEquipmentBatchServlet.action", equipmentDetailUrl = "/iot_equipment/equipment_detail.html", equipmentDelInfoUrl = "/iot_equipment/equipmentHisDelOperate.html", getHisOperateUrl = "/iot_equipment/equipmentHisOperate.html", findUserAuthorityUrl = ipPort
		+ "/iot_usermanager/user/authority",
// 表格id
tableId = "#equipement_table",
// 当前用户名
currentUserName = getCookie("userID"),
// 当前用户名权限
currentUserAuthority = [],
// 权限配置对应表
equipementAuthorityConfig = {
	"新增" : ".equipment_add",
	"修改" : ".equipment_modify",
	"删除" : ".equipment_delete",
	"批量新增" : ".equipment_addBatch",
	"批量修改" : ".equipment_modifyBatch",
	"导入" : ".equipment_importExcel",
	"导出" : ".equipment_exportExcel",
	"导出台账" : ".equipment_gram",
	"回收站" : ".equipment_bin"
},
// 操作按钮对应事件配置
equipementAuthEventConfig = {
	"新增" : "addClickFunction",
	"修改" : "modifyClickFunction",
	"删除" : "deleteClickFunction",
	"批量新增" : "addBatchFunction",
	"批量修改" : "modifyBatchFunction",
	"导入" : "importExcelClickFunction",
	"导出" : "exportXTCF",
	"导出台账" : "exportExcelClickFunction",
	"回收站" : "binCF"
};

var equipment_file_url = "http://192.168.18.114:10238/soa-elfinder-web/";

/**
 * 页面初始化
 */
$(function() {
	console.log('------------location---------------');
	console.log(getUrlParamValueByName("userid"));
	console.log(getUrlParamValueByName("currentLastMenoId"));
	/*
	 * 数据库新增字段 设备大类：TOTAL_EQUIPMENT、SECONDCLASS_EQUIPMENT
	 */
	// 数据表格初始化列配置
	var tableColumns = [{
				field : 'ck',
				checkbox : true
			},
			// { field:'EQU_ID', title:'设备ID', align:'center',width:"80" },
			{
				field : 'EQU_MEMO_TWO',
				title : '设备状态',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_NAME',
				title : '名称',
				align : 'center',
				width : "80"
			}, {
				field : 'ELEC',
				title : '功能描述',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_MODEL',
				title : '规格型号',
				align : 'center',
				width : "80"
			}, {
				field : 'MEASURE_PRIN',
				title : '数量',
				align : 'center',
				width : "80"
			}, {
				field : 'FLUX',
				title : '单位',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_MANUFACTURER',
				title : '制造厂',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_COMMISSION_DATE',
				title : '投用时间',
				align : 'center',
				width : "80"
			}, {
				field : 'CAPCITY',
				title : '备注',
				align : 'center',
				width : "80"
			}];
	var baseTableColumns = [{
				field : 'ck',
				checkbox : true
			},
			// { field:'EQU_ID', title:'设备ID', align:'center' ,width:"80"},
			{
				field : 'WEL_NAME',
				title : '装置列',
				align : 'center',
				width : "80"
			}, {
				field : 'WEL_UNIT',
				title : '装置单元',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_MEMO_ONE',
				title : '设备类别',
				align : 'center',
				width : "80"
			}, {
				field : 'EQU_POSITION_NUM',
				title : '设备位号',
				align : 'center',
				width : "100"
			}, {
				field : 'EQU_NAME',
				title : '设备名称',
				align : 'center',
				width : "80"
			}];

	// 数据表格参数初始化配置
	var tableOption = {
		url : searchDataUrl,
		columns : [tableColumns],
		toolbar : "#equipment_search_toolbar",
		striped : true,
		queryParams : {
			"EQU_MEMO_ONE" : "控制系统"
		},
		method : "post",
		pagination : true,
		rownumbers : true,
		checkOnSelect : true,
		selectOnCheck : true,
		singleSelect : true,
		pageNumber : 1,
		pageSize : 50,
		pageList : [50, 100, 150, 200],
		onRowContextMenu : rightClickRowFunction,
		onClickCell : tableSingleClick
		// onDblClickCell : tableDbclickCell,
		// onAfterEdit : onAfterEdit
	};
	// 不能编辑列配置
	var unEditColumns = ["EQU_POSITION_NUM"],
	// 当前选择的树节点
	currentNodeName = "",
	// 当前选择的树节点的父节点
	currentNodeParentName = "",
	// 当前选择的行号
	currentRowIndex = "",
	// 单元格是否编辑状态
	editIndex = undefined;

	/*
	 * 页面初始化
	 */
	// 关闭弹出窗口
	// $( '#equipment_uploadExcel' ).window( 'close' );
	// $( '#addTableRow' ).window( 'close' );
	// 初始化
	$('.equipment_highSearch').css({
				'display' : 'none'
			});
	$('.equpment-type').tree('collapseAll');
	$('#easyui-tree-first').click();

	// 初始化表格
	$(tableId).datagrid(tableOption);

	// 初始化用户工具栏权限
	findUserAuthority();

	// 动态生成工具栏按钮权限
	setCurrentAuthority();

	$('#fileImport').filebox({
				buttonText : ''
			})

	/*
	 * 事件绑定
	 */
	// 高级搜索-单击事件绑定
	$('.equipment_highSearch').on('click', highSearchClickFunction);
	// 查询按钮-单击事件绑定
	$('.equipment_search').on('click', searchClickFunction);
	// 新增按钮-单击事件绑定addBatchFunction
	// $( '.equipment_add' ).on( 'click', addClickFunction );
	// 批量新增按钮-单击事件绑定
	// $( '.equipment_addBatch' ).on( 'click', addBatchFunction );
	// 修改按钮-单击事件绑定
	// $( '.equipment_modify' ).on( 'click', modifyClickFunction );
	// 删除按钮-单击事件绑定
	// $( '.equipment_delete' ).on( 'click', deleteClickFunction );
	// 导入按钮-单击事件绑定
	// $( '.equipment_importExcel' ).on( 'click', importExcelClickFunction );
	// 导入提交按钮-单击事件绑定
	$('#equipment_uploadExcelDetail').on('click', importExcelUploadFunction);
	// 导入关闭按钮-单击事件绑定
	$('#equipment_closeExcelDetail').on('click', importExcelCloseFunction);
	// 右键行查看历史数据-单击事件绑定
	// $( '#div_hisoperate' ).on( 'click', getHisOperateCF );

	/*
	 * 工具栏按钮-查询用户任务权限
	 */
	function findUserAuthority() {
		console.log('工具栏按钮-查询用户任务权限……');
		// $.getJSON( findUserAuthorityUrl+"/" + getUrlParamValueByName (
		// "userid" ) +
		// "/" + getUrlParamValueByName ( "currentLastMenoId" ),
		// findAuthoritySuccessFunction );
		$.ajax({
					type : "GET",// 请求方式
					async : false,
					url : findUserAuthorityUrl + "/"
							+ getUrlParamValueByName("userid") + "/"
							+ getUrlParamValueByName("currentLastMenoId"),
					dataType : "jsonp", // 数据类型可以为 text xml json script jsonp
					jsonp : "callback",
					success : findAuthoritySuccessFunction
				})
	}

	/*
	 * 工具栏按钮-查询用户任务权限成功回调函数
	 */
	function findAuthoritySuccessFunction(jsonData) {
		console.log('工具栏按钮-查询用户任务权限成功回调函数……');
		console.log(jsonData)
		if (jsonData != null) {
			// 赋值
			$.each(jsonData, function(index, item) {
						currentUserAuthority.push(item.name);
					})
			console.log(currentUserAuthority);
		} else {
			$.messager.show({
						title : '提示页面',
						msg : '查询权限失败',
						timeout : 2000,
						showType : 'slide'
					});
		}
	}

	/*
	 * 工具栏按钮-当前状态下按钮权限
	 */
	function setCurrentAuthority() {
		console.log('工具栏按钮-当前状态下按钮权限……');
		console.log(currentNodeName);
		if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
				|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备"
				|| currentNodeName == "全部设备") {
			// 取消所有单击事件绑定,设置disabled
			$.each(equipementAuthEventConfig, function(key, value) {
						console.log(equipementAuthorityConfig[key]);
						$(equipementAuthorityConfig[key]).off('click',
								function() {
									eval(equipementAuthEventConfig[key] + "()");
								}).addClass('l-btn-disabled');
					})
		} else {
			$.each(currentUserAuthority, function(index, item) {
						// 设置所有单击事件绑定,设置disabled
						console.log(equipementAuthEventConfig[item]);
						var eventName = equipementAuthEventConfig[item];
						$(equipementAuthorityConfig[item]).on('click',
								function() {
									eval(eventName + "()");
								})
						if (equipementAuthorityConfig[item] == "导出") {
							$(equipementAuthorityConfig[item]).hover(
									function() {
										$("#exportList").show();
									}, function() {
										$("#exportList").hide();
									})
						}
						$(equipementAuthorityConfig[item])
								.removeClass('l-btn-disabled');
					})
			// 设置导出和导出Excel模板
			$(equipementAuthorityConfig["导出台账"]).on('click', function() {
						eval(equipementAuthEventConfig["导出台账"] + "()");
					}).removeClass('l-btn-disabled');
			$(equipementAuthorityConfig["导出"]).on('click', function() {
						eval(equipementAuthEventConfig["导出"] + "()");
					}).removeClass('l-btn-disabled');

			$(equipementAuthorityConfig["新增"]).on('click', function() {
						eval(equipementAuthEventConfig["新增"] + "()");
					}).removeClass('l-btn-disabled');

			$(equipementAuthorityConfig["修改"]).on('click', function() {
						eval(equipementAuthEventConfig["修改"] + "()");
					}).removeClass('l-btn-disabled');

			$(equipementAuthorityConfig["删除"]).on('click', function() {
						eval(equipementAuthEventConfig["删除"] + "()");
					}).removeClass('l-btn-disabled');
			$(equipementAuthorityConfig["批量新增"]).on('click', function() {
						eval(equipementAuthEventConfig["批量新增"] + "()");
					}).removeClass('l-btn-disabled');
			$(equipementAuthorityConfig["批量修改"]).on('click', function() {
						eval(equipementAuthEventConfig["批量修改"] + "()");
					}).removeClass('l-btn-disabled');
			$(equipementAuthorityConfig["导入"]).on('click', function() {
						eval(equipementAuthEventConfig["导入"] + "()");
					}).removeClass('l-btn-disabled');

			$(equipementAuthorityConfig["回收站"]).on('click', function() {
						eval(equipementAuthEventConfig["回收站"] + "()");
					}).removeClass('l-btn-disabled');
		}

	}

	/*
	 * 高级搜索-单击事件绑定回调函数
	 */
	function highSearchClickFunction() {
		console.log('高级搜索-单击事件……');
		// 打开关闭-css切换
		var $highSearchDiv = $('.equipment_highSearch_item');
		/*
		 * if( $highSearchDiv ){ if( $highSearchDiv.hasClass(
		 * '.equipment_highSearch_itemOpen' ) ){ $highSearchDiv.removeClass(
		 * '.equipment_highSearch_itemOpen' ) .addClass(
		 * '.equipment_highSearch_itemClose' ); }else{
		 * $highSearchDiv.removeClass( 'equipment_highSearch_itemClose' )
		 * .addClass( '.equipment_highSearch_itemOpen' ); } }
		 */
		if ($highSearchDiv[0].style.display == 'none') {
			$highSearchDiv[0].style.display = 'block';
		} else {
			$highSearchDiv[0].style.display = 'none';
		}

	}

	/*
	 * 查询按钮-单击事件绑定
	 */
	function searchClickFunction() {
		console.log('查询按钮-单击事件……');

		// 获取参数-json
		var urlParamTemp = {};
		$.each($('#equipment_search_toolbar').find('input[type=text]'),
				function(index, item) {
					urlParamTemp[$(item).attr('name')] = $(item).val();
				})
		// 加入当前节点字段参数
		if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
				|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备"
				|| currentNodeName == "全部设备") {
			urlParamTemp["SECONDCLASS_EQUIPMENT"] = currentNodeName;
		} else {
			urlParamTemp["EQU_MEMO_ONE"] = currentNodeName
		}
		console.log('查询按钮-单击事件……urlParamTemp:' + urlParamTemp);

		// 请求数据
		$(tableId).datagrid('load', urlParamTemp);
	}

	/*
	 * 新增按钮-单击事件绑定
	 */
	function addClickFunction() {
		console.log('新增按钮-单击事件……');
		// 打开窗口
		$('#addTableRow').window('open');

		// 新增行页面-动态生成
		var html = activeGenerateAddRowHtml();

		// 加载页面
		$('#addTableRow').empty();
		$('#addTableRow').append(html);
		// 按钮初始化按钮
		$("#addSubmitBtn").linkbutton({
					text : " 提 交 ",
					iconCls : "icon-add",
					iconAlign : "left"
				});
		$("#addCancelBtn").linkbutton({
					text : " 取 消 ",
					iconCls : "icon-remove",
					iconAlign : "left"
				});

		// 初始化模拟数据
	}

	/*
	 * 批量新增按钮-单击事件绑定
	 */
	function addBatchFunction() {
		console.log('新增按钮-单击事件……');

		var urlParamTemp = {};
		// 设置导出类型
		urlParamTemp.moduleType = "2";
		urlParamTemp["EQU_MEMO_ONE"] = currentNodeName;

		// 发送请求
		var param = $.param(urlParamTemp);
		window.location.href = excelDownloadUrl + "?" + param;
	}

	/*
	 * 修改按钮-单击事件绑定
	 */
	function modifyClickFunction() {
		console.log('修改按钮-单击事件……');
		// 弹出页面
		$('#modifyTableRow').window('open');

		// 判断是否选中一行
		var row = $(tableId).datagrid('getSelected');
		console.log(row);
		if (row == null) {
			$.messager.show({
						title : '提示页面',
						msg : '请选择一行数据……',
						timeout : 2000,
						showType : 'slide'
					});
			$('#modifyTableRow').window('close');
			return;
		}

		// 动态生成修改页面，加载页面currentRowIndex
		$('#modifyTableRow').empty();
		var html = activeModifyRowHtml();
		$('#modifyTableRow').append(html);
		// 按钮初始化按钮
		$("#modifySubmitBtn").linkbutton({
					text : " 提 交 ",
					iconCls : "icon-add",
					iconAlign : "left"
				});
		$("#modifyCancelBtn").linkbutton({
					text : " 取 消 ",
					iconCls : "icon-remove",
					iconAlign : "left"
				});
		// 数据初始化
		$.each($('#modifyTableRow input[type=text]'), function(index, item) {
					$(item).val(row[$(item).attr('name')]);
				})
	}

	/*
	 * 删除按钮-单击事件绑定
	 */
	function deleteClickFunction() {
		console.log('删除按钮-单击事件……');
		// 检查是否选中一行
		var row = $('#equipement_table').datagrid('getSelected');
		console.log(row);
		if (row == null) {
			$.messager.show({
						title : '提示页面',
						msg : '请选择一行数据……',
						timeout : 2000,
						showType : 'slide'
					});
			return;
		}

		// 获取行信息，异步删除
		deleteCheckedRow(row);
	}

	/*
	 * 根据数据行id，删除该行
	 */
	function deleteCheckedRow(selectedRow) {
		console.log('删除按钮-单击事件……');
		$.ajax({
					type : "POST",
					url : deleteRowUrl,
					data : {
						"EQU_POSITION_NUM" : selectedRow["EQU_POSITION_NUM"]
					},
					async : true, // 默认
					dataType : "json",
					beforeSend : function(XMLHttpRequest) {
					},
					complete : function(XMLHttpRequest, textStatus) {
					},
					success : deleteRowDataFunction,
					error : function() {
						$.messager.show({
									title : '提示页面',
									msg : '删除失败',
									timeout : 2000,
									showType : 'slide'
								});
					}
				});

	}

	/*
	 * 删除数据-成功回调函数
	 */
	function deleteRowDataFunction(jsonData) {
		console.log('删除按钮-单击事件回调函数……');
		// 获取新增页面数据json
		var row = $(tableId).datagrid('getSelected');
		var rowIndex = $(tableId).datagrid("getRowIndex", row);
		console.log('删除表格页-提交按钮单击事件回调函数……row' + row);
		if (jsonData != null) {
			if (jsonData.data != null && jsonData.state == 0) {
				// 提示
				$.messager.show({
							title : '提示页面',
							msg : '删除成功',
							timeout : 2000,
							showType : 'slide'
						});
				// 前端删除数据
				$(tableId).datagrid("deleteRow", rowIndex);
			} else {
				$.messager.show({
							title : '提示页面',
							msg : '删除失败，数据保存失败',
							timeout : 2000,
							showType : 'slide'
						});
			}
		} else {
			$.messager.show({
						title : '提示页面',
						msg : '删除失败，返回数据为空',
						timeout : 2000,
						showType : 'slide'
					});
		}
	}

	/*
	 * 左侧树结构-单击事件
	 */
	var needCheck = 'no';
	$('.equpment-type').tree({
		onClick : function(node) {
			console.log('左侧树结构-单击事件……');
			console.log('左侧树结构-node:' + node.text);
			// console.log( node );
			// 设置节点名称和父节点名称
			currentNodeName = $.trim(node.text);
			currentNodeParentName = $('.equpment-type').tree('getParent',
					node.target).text;
			// console.log( $( node.target ).closest( 'ul' ).before( 'div'
			// ).find( '.tree-title' ) );
			// currentNodeParentName = $( node.target ).closest( 'ul' ).before(
			// 'div' ).find( '.tree-title' ).text();
			console.log('左侧树结构-currentNodeParentName:' + currentNodeParentName);

			/*
			 * 动态生成表格工具栏
			 */
			// 清空
			if (currentNodeName != "仪表设备" || currentNodeName != "机械设备"
					|| currentNodeName != "电气设备" || currentNodeName != "分析化验设备"
					|| currentNodeName != "全部设备") {
				$('.equipment_highSearch_item').empty();
				var searchToolHtml = activeGenerateSearchToolHtml(currentNodeName);
				$('.equipment_highSearch_item').append(searchToolHtml);
			}
			// 动态生成工具栏按钮权限
			setCurrentAuthority();
			// 高级搜索隐藏与显示
			if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
					|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备"
					|| currentNodeName == "全部设备") {
				$('.equipment_highSearch').css({
							'display' : 'none'
						});
			} else {
				$('.equipment_highSearch').css({
							'display' : 'inline-block'
						});
			}
			/*
			 * 动态生成表格展示列
			 */
			// 动态生成列tableColumns
			var currentTableColumns = activeGenerateTableColumns();
			tableOption.columns = [currentTableColumns];
			// 重新加载表格
			if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
					|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备"
					|| currentNodeName == "全部设备") {
				// $( tableId ).datagrid( 'load', {
				// "SECONDCLASS_EQUIPMENT" : currentNodeName
				// });
				tableOption.queryParams = {
					"SECONDCLASS_EQUIPMENT" : currentNodeName
				};
			} else {
				// $( tableId ).datagrid( 'load', {
				// "EQU_MEMO_ONE" : currentNodeName
				// });
				tableOption.queryParams = {
					"EQU_MEMO_ONE" : currentNodeName,
					"needCheck" : needCheck
				};

			}
			$(tableId).datagrid(tableOption);
		}
	})

	/*
	 * 该方法用于关闭上一个焦点的editing状态
	 */
	function endEditing() {
		if (editIndex == undefined) {
			return true;
		}
		if ($(tableId).datagrid('validateRow', editIndex)) {
			$(tableId).datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	};

	/*
	 * 单击事件
	 */
	function tableSingleClick() {
		console.log('表格单元格-单击事件……');
		currentRowIndex = index;
		// endEditing();
	}

	/*
	 * 双击事件
	 */
	function tableDbclickCell(index, field, value) {
		console.log('表格单元格-双击事件……');
		currentRowIndex = index;
		if (endEditing()) {
			$(tableId).datagrid('selectRow', index).datagrid('editCell', {
						index : index,
						field : field
					});
			editIndex = index;
		}
	}

	/*
	 * 单元格失去焦点执行回调函数
	 */
	function onAfterEdit(index, row, changes) {
		console.log('表格单元格-失去焦点事件……');
		var updatedRow = $(tableId).datagrid('getChanges', 'updated');
		if (updatedRow.length < 1) {
			editRow = undefined;
			$(tableId).datagrid('unselectAll');
			return;
		} else {
			console.log('单元格编辑-改变信息' + $(tableId).datagrid('acceptChanges'));
			// 传值,可做在线编辑功能
			/* submitForm(index, row, changes); */
		}
	};

	/*
	 * 动态生成表格工具栏 return html
	 */
	function activeGenerateSearchToolHtml(currentNodeName) {
		console.log('表格工具栏-动态生成……');
		// 重新动态生成html
		var activeHtml = '<div style = "display:-webkit-inline-box;width:100%;height=10px;"></div>';

		$.each(equipmentStructure, function(key, value) {
			if (currentNodeName == key && currentNodeName != '全部设备'
					&& currentNodeName != '仪表设备' && currentNodeName != '机械设备'
					&& currentNodeName != '电气设备' && currentNodeName != '分析化验设备') {
				var remark = 0;
				activeHtml += '<div style = "display:table"><div style = "display:table-row;width:100%">';
				$.each(value, function(key1, value1) {
					remark = parseInt(remark) + 1;
					activeHtml = activeHtml
							+ '<div style = "display:table-cell;width:20%;"><span>'
							+ key1
							+ ':</span> '
							+ ' <input type="text" name="'
							+ value1
							+ '"'
							+ ' id="'
							+ value1
							+ '" '
							+ ' style="line-height:20px;border:1px solid #ccc"></div>';
					// 换行
					if (remark % 5 == 0) {
						activeHtml = activeHtml
								+ '</div><div style = "display:-webkit-inline-box;width:100%;height=10px;"></div><div style = "display:table-row;width:100%;">';
					}
				})
			}
		});
		activeHtml += '</div></div>';
		// console.log( activeHtml );
		return activeHtml;
	}

	/*
	 * 动态生成表格数据列columns return [json,json]
	 */
	function activeGenerateTableColumns() {
		console.log('表格列-动态生成……');
		var totalTableColumns = [];
		console.log(baseTableColumns);
		/*
		 * $.each(baseTableColumns, function(key, value) {
		 * totalTableColumns.push(value); })
		 */
		var checkbox = {
			field : 'ck',
			checkbox : true
		};
		totalTableColumns.push(checkbox);
		$.each(equipmentStructure, function(key, value) {
			if (currentNodeName == key && currentNodeName != '全部设备'
					&& currentNodeName != '仪表设备' && currentNodeName != '机械设备'
					&& currentNodeName != '电气设备' && currentNodeName != '分析化验设备') {
				$.each(value, function(key1, value1) {
							var json = {};
							json.field = value1;
							json.title = key1;
							json.align = "center";
							json.width = "80";
							// 设置可编辑列unEditColumns
							$.each(unEditColumns, function(index, item) {
										if (value1 != item) {
											json.editor = "text";
										}
									})
							totalTableColumns.push(json);
						})
			}
		})
		console.log(totalTableColumns);
		return totalTableColumns;
	}

	/*
	 * 新增行页面-动态生成 return html
	 */
	function activeGenerateAddRowHtml() {
		console.log('新增表格列-动态生成……');
		// console.log( '新增表格列-动态生成……currentNodeName' + currentNodeName );
		currentNodeName = $.trim(currentNodeName);
		// 前部
		var beforeHtml = ' <form id="addTableRowForm" method="post"> ';
		// 中部
		var middleActiveHtml = '';
		$.each(equipmentStructure, function(key, value) {
			if (currentNodeName == key || "全部设备" == key) {
				$.each(value, function(key1, value1) {
					middleActiveHtml = middleActiveHtml
							+ ' <div style="margin-left:40px;"> '
							+ ' 	<label for="name">'
							+ key1
							+ ':</label>     '
							+ '   	<input class="easyui-validatebox" type="text"    '
							+ '   		name="'
							+ value1
							+ '" data-options="required:true" /> '
							+ ' </div>  '
							+ '<p style="height:5px;margin:0px;padding:0px;"></p>';
				})
			}
		})

		middleActiveHtml = middleActiveHtml
				+ ' <div style="margin-left:40px;"> ' + ' 	<label for="name">'
				+ '设备类型' + ':</label>     '
				+ '   	<input class="easyui-validatebox" type="text"    '
				+ '   		name="' + 'EQU_MEMO_ONE'
				+ '" data-options="required:true" /> ' + ' </div>  '
				+ '<p style="height:5px;margin:0px;padding:0px;"></p>';

		// 特殊分类字段
		var secondEquipmentType = ' <div  style="margin-left:40px;"> '
				+ ' 	<label for="name">设备大类:</label>     '
				+ '   	<input class="easyui-validatebox" type="text" readonly="readonly"  '
				+ '   		name="SECONDCLASS_EQUIPMENT" value="'
				+ currentNodeParentName + '"'
				+ '			data-options="required:true" /> ' + ' </div>  '
				+ '<p style="height:5px;margin:0px;padding:0px;"></p>';
		// 最后
		var endHtml = ' <div style="text-align:center;padding:5px">    '
				+ ' 	<a href="#"  id="addSubmitBtn" '
				+ '     	onclick="addHtmlSubmitClick()"  ></a>   '
				+ ' 	<a href="#" class="easyui-linkbutton"  id="addCancelBtn" '
				+ '     	onclick="addHtmlCancelClick()"	></a> ' + '	</div>'
				+ ' </form> ';
		// console.log( '新增表格列-动态生成……html:' + beforeHtml + middleActiveHtml +
		// endHtml );
		return beforeHtml + secondEquipmentType + middleActiveHtml + endHtml;
	}

	/*
	 * 动态生成修改行页面 return html
	 */
	function activeModifyRowHtml() {
		console.log('新增表格列-动态生成……');
		currentNodeName = $.trim(currentNodeName);
		// 前部
		var beforeHtml = ' <form id="modifyTableRowForm" method="post"> ';
		// 中部
		var middleActiveHtml = '', readOnlyInput = '';
		$.each(equipmentStructure, function(key, value) {
			if (currentNodeName == key || "全部设备" == key) {
				$.each(value, function(key1, value1) {
					readOnlyInput = "";
					if (value1 == "EQU_MEMO_ONE"
							|| value1 == "EQU_POSITION_NUM") {
						readOnlyInput = "readonly = readonly";
					}
					middleActiveHtml = middleActiveHtml
							+ ' <div  style="margin-left:40px;"> '
							+ ' 	<label for="name">'
							+ key1
							+ ':</label>     '
							+ '   <input class="easyui-validatebox" type="text" '
							+ readOnlyInput
							+ '   		name="'
							+ value1
							+ '" data-options=""/> '
							+ ' </div>  '
							+ '<p style="height:5px;margin:0px;padding:0px;"></p>';
				})
			}
		})
		// 最后
		var endHtml = ' <div style="text-align:center;padding:5px">    '
				+ ' 	<a href="#"  id="modifySubmitBtn" '
				+ '     	onclick="modifyHtmlSubmitClick()"  ></a>   '
				+ ' 	<a href="#" id="modifyCancelBtn" '
				+ '     	onclick="modifyHtmlCancelClick()"	></a> ' + '	</div>'
				+ ' </form> ';
		// console.log( '新增表格列-动态生成……html:' + beforeHtml + middleActiveHtml +
		// endHtml );
		return beforeHtml + middleActiveHtml + endHtml;
	}

	/*
	 * 导入功能-单击事件importExcelUploadFunction
	 */
	function importExcelClickFunction() {
		console.log('导入功能-单击事件……');
		$('#equipment_uploadExcel').window('open');

	}

	/*
	 * 导入上传功能-单击事件
	 */
	$('#equipment_closeExcelDetail').click(function() {
				$('#importFileForm label').html("");
				$("#equipment_uploadExcel").window("close");
			});
	function importExcelUploadFunction() {
		console.log('导入上传功能-单击事件……');
		// 获取上传文件信息
		var file = $("#_easyui_textbox_input4")[0].files[0];
		console.log("获取文件的基本信息" + file);
		// 判断控件中是否存在文件内容，如果不存在，弹出提示信息，阻止进一步操作
		if (file == null) {
			alert('错误，请选择文件');
			return;
		}
		// 获取文件名称
		var fileName = file.name;
		console.log(fileName);
		// 获取文件类型名称
		var file_typename = fileName.substring(fileName.lastIndexOf('.'),
				fileName.length);
		// 这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
		if (file_typename == '.xlsx' || file_typename == '.xls') {
			// 计算文件大小
			var fileSize = 0;
			// 如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
			if (file.size > 1024 * 1024) {
				fileSize = Math.round(file.size * 100 / 1024 * 1024) / 100;
				if (fileSize > 10) {
					alert('错误，文件超过10MB，禁止上传！');
					return;
				}
				fileSize = fileSize.toString() + 'MB';
			} else {
				fileSize = (Math.round(file.size * 100 / 1024) / 100)
						.toString()
						+ 'KB';
			}
			// 将文件名和文件大小显示在前端label文本中
			$('#fileName').append("<span style='color:Blue'>文件名: " + file.name
					+ ',大小: ' + fileSize + "</span>")

			// 获取form数据
			$('input[name="equ_type"]').val(currentNodeName);
			var formData = new FormData();
			formData.set('fiel', $("#_easyui_textbox_input4")[0].files[0]);
			$.ajax({
						url : excelUploadUrl + "?equ_type=" + currentNodeName,
						type : 'POST',
						data : formData,
						async : false,
						contentType : false,
						processData : false,
						dataType : "json",
						success : function(jsonData) {
							if (jsonData.state == 0) {
								$.messager.show({
											title : '提示页面',
											msg : '导入成功',
											timeout : 2000,
											showType : 'slide'
										});
								$('#equipment_uploadExcel').window('close');
								$('#equipement_table').datagrid('reload');
							} else {
								$('#uploadInfo')
										.append("<span style='color:Red'>"
												+ jsonData.msg + "("
												+ jsonData.data + ")"
												+ "</span>")
							}

						},
						error : function(returnInfo) {
							// 上传失败时显示上传失败信息
							$('#uploadInfo').append("<span style='color:Red'>"
									+ returnInfo + "</span>")
						}
					})
		} else {
			$.messager.show({
						title : '提示页面',
						msg : '文件类型错误',
						timeout : 2000,
						showType : 'slide'
					})
			// 将错误信息显示在前端label文本中
			$('#fileName')
					.append("<span style='color:Red'>错误提示:上传文件应该是.xlsx后缀而不应该是"
							+ file_typename + ",请重新选择文件</span>")
		}
	}

	/*
	 * 导入关闭功能-单击事件
	 */
	function importExcelCloseFunction() {
		console.log('导入关闭功能-单击事件……');
		$('#equipment_uploadExcel').window('close');
	}

	/*
	 * 导出按钮-单击事件
	 */
	function exportExcelClickFunction() {
		console.log('导出按钮-单击事件……');

		// 获取参数-json
		var urlParamTemp = {};
		$.each($('#equipment_search_toolbar').find('input[type=text]'),
				function(index, item) {
					urlParamTemp[$(item).attr('name')] = $(item).val();
				})
		// 加入当前节点字段参数
		if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
				|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备") {
			urlParamTemp["SECONDCLASS_EQUIPMENT"] = currentNodeName;
		} else {
			urlParamTemp["EQU_MEMO_ONE"] = currentNodeName
		}
		console.log(urlParamTemp);
		// 设置导出类型
		urlParamTemp.moduleType = "1";

		// 发送请求
		var param = $.param(urlParamTemp);
		window.location.href = excelDownloadUrl + "?" + param;
	}

	/*
	 * 批量新增按钮-单击事件
	 */
	function modifyBatchFunction() {
		console.log(' 批量新增按钮-单击事件…');
		// 获取参数-json
		var urlParamTemp = {};
		$.each($('#equipment_search_toolbar').find('input[type=text]'),
				function(index, item) {
					urlParamTemp[$(item).attr('name')] = $(item).val();
				})
		// 加入当前节点字段参数
		if (currentNodeName == "仪表设备" || currentNodeName == "机械设备"
				|| currentNodeName == "电气设备" || currentNodeName == "分析化验设备") {
			urlParamTemp["SECONDCLASS_EQUIPMENT"] = currentNodeName;
		} else {
			urlParamTemp["EQU_MEMO_ONE"] = currentNodeName
		}
		console.log(urlParamTemp);
		// 设置导出类型
		urlParamTemp.moduleType = "1";

		// 发送请求
		var param = $.param(urlParamTemp);
		window.location.href = modifyBatchUrl + "?" + param;
	}

	/*
	 * 数据表格列编辑要求的源码扩展
	 */
	$.extend($.fn.datagrid.methods, {
				editCell : function(jq, param) {
					return jq.each(function() {
								var opts = $(this).datagrid('options');
								var fields = $(this).datagrid(
										'getColumnFields', true).concat($(this)
										.datagrid('getColumnFields'));
								for (var i = 0; i < fields.length; i++) {
									var col = $(this).datagrid(
											'getColumnOption', fields[i]);
									col.editor1 = col.editor;
									if (fields[i] != param.field) {
										col.editor = null;
									}
								}
								$(this).datagrid('beginEdit', param.index);
								for (var i = 0; i < fields.length; i++) {
									var col = $(this).datagrid(
											'getColumnOption', fields[i]);
									col.editor = col.editor1;
								}
							});
				}
			});

		/*
		 * 右键弹出菜单事件 $( '#menu' ).click( function(){ console.log( '右键弹出菜单-单击事件……' );
		 * console.log( encodeURI(encodeURI(EQU_MEMO_ONE)) ); if( item.name ==
		 * "detail" ){ var url=equipmentDetailUrl +
		 * "?a=1&DETAIL_URL=1&EQU_ID="+EQU_ID+"&EQU_MEMO_ONE=" +
		 * encodeURI(encodeURI(EQU_MEMO_ONE)) + "&EQU_POSITION_NUM=" +
		 * EQU_POSITION_NUM; self.parent.addTab(EQU_POSITION_NUM, url,
		 * "icon-add"); }else if( item.name == "hisoperate" ){
		 * getHisOperateCF(); } })
		 */

		/*
		 * 右键行查看历史数据-单击事件绑定
		 * 
		 * function getHisOperateCF(){ console.log( '右键行查看历史数据-单击事件……' ); }
		 */
		/*
		 * 
		 * $.ajax({ url:'getNeedCheckCounts.action?needCheck=yes',
		 * dataType:'json', success:function(returnData){
		 * 
		 * $('.checkEqu>span').html(returnData.total);
		 * $('.equipment_checkEqu').click(function(){
		 * if($('#checkEqu').is(':checked')){ needCheck='yes';
		 * $('#tree>li:contains("设备大类")').hide(); var
		 * needCheckEqu=returnData.data; var arr=[];
		 * 
		 * for(var a=0;a<needCheckEqu.length;a++){
		 * arr.push(needCheckEqu[a].EQU_MEMO_ONE) }
		 * console.log(arr.indexOf("压力表")); //将不存在检修的设备类型影藏
		 * 
		 * $('.equpment-type ul>li').each(function(i){
		 * console.log($(this).text()); if(arr.indexOf($(this).text())==-1){
		 * $(this).hide(); } }); }else{ $('#tree :hidden').show();
		 * needCheck='no'; } }); } });
		 */

})

/*
 * 右键事件
 */
var EQU_ID, EQU_MEMO_ONE, EQU_POSITION_NUM;
function rightClickRowFunction(e, rowIndex, rowData) {
	console.log('右键-单击事件……');

	e.preventDefault(); // 阻止浏览器捕获右键事件
	$(this).datagrid("clearSelections"); // 取消所有选中项
	$(this).datagrid("selectRow", rowIndex); // 根据索引选中该行
	var $row = $(this).datagrid("getSelected");
	EQU_ID = $row.EQU_ID;
	EQU_MEMO_ONE = $row.EQU_MEMO_ONE;
	EQU_POSITION_NUM = $row.EQU_POSITION_NUM;

	$('#menu').menu('show', {
				// 显示右键菜单
				left : e.pageX,// 在鼠标点击处显示菜单
				top : e.pageY,
				minWidth : 250
			});
	e.preventDefault(); // 阻止浏览器自带的右键菜单弹出
}

/**
 * 右键菜单的选项单击事件
 */
function rightMenoCF(item) {
	console.log('右键弹出菜单-单击事件……');
	console.log(item);
	console.log(encodeURI(encodeURI(EQU_MEMO_ONE)));
	if (item.name == "detail") {
		var url = equipmentDetailUrl + "?a=1&DETAIL_URL=1&EQU_ID=" + EQU_ID
				+ "&EQU_MEMO_ONE=" + encodeURI(encodeURI(EQU_MEMO_ONE))
				+ "&EQU_POSITION_NUM=" + EQU_POSITION_NUM;
		// self.parent.addTab(EQU_POSITION_NUM, url, "icon-filter");
		location.href = url;
	} else if (item.name == "hisoperate") {
		var url = getHisOperateUrl + "?EQU_ID=" + EQU_ID + "&EQU_POSITION_NUM="
				+ encodeURI(encodeURI(EQU_POSITION_NUM)) + "&EQU_MEMO_ONE="
				+ encodeURI(encodeURI(EQU_MEMO_ONE));
		// self.parent.addTab( "设备历史操作记录", url, "icon-back");
		location.href = url;
	} else if (item.name == "file") {
		// 查看设备文件（陈宇林修改）
		var url = equipment_file_url + "?dirClass=" + EQU_ID
				+ "#elf_A_aGVscA_E_E";
		location.href = url;
	}
}

/**
 * 新增页面删除按钮-单击事件
 */
function addHtmlCancelClick() {
	console.log('新增表格页-取消按钮单击事件');
	$('#addTableRow').window('close');
}

/**
 * 新增页面提交按钮-单击事件
 */
function addHtmlSubmitClick() {
	console.log('新增表格页-提交按钮单击事件');
	// 获取数据
	var addData = $('#addTableRowForm').serialize();
	console.log('新增表格列-动态生成……data:' + addData);

	// 提交
	$.ajax({
				type : "POST",
				url : addRowUrl,
				data : addData,
				async : true, // 默认
				dataType : "json",
				beforeSend : function(XMLHttpRequest) {
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				success : addHtmlSubmitSuccessFunction,
				error : function() {
					$.messager.show({
								title : '提示页面',
								msg : '新增失败，',
								timeout : 2000,
								showType : 'slide'
							});
				}
			});

}

/**
 * 保存新增行数据-成功回调函数
 */
function addHtmlSubmitSuccessFunction(jsonData) {
	console.log('新增表格页-提交按钮单击事件回调函数……');
	// 获取新增页面数据json
	var addRowData = $('#addTableRowForm').serialize();
	addRowData = decodeURIComponent(addRowData, true);
	var addRowJson = formToJson(addRowData);
	console.log(addRowJson);
	if (jsonData != null) {
		if (jsonData.data != null && jsonData.state == 0) {
			// 提示
			$.messager.show({
						title : '提示页面',
						msg : '新增成功',
						timeout : 2000,
						showType : 'slide'
					});
			// 前端直接在第一行增加一条数据
			var index = 0;
			activeAddRowData(index, addRowJson);

			// 关闭页面
			$('#addTableRow').window('close');
		} else {
			$.messager.show({
						title : '提示页面',
						msg : '新增失败，数据保存失败',
						timeout : 2000,
						showType : 'slide'
					});
		}
	} else {
		$.messager.show({
					title : '提示页面',
					msg : '新增失败，返回数据为空',
					timeout : 2000,
					showType : 'slide'
				});
	}
}
/*
 * 修改页面删除按钮-单击事件
 */
function modifyHtmlCancelClick() {
	console.log('修改表格页-取消按钮单击事件');
	$('#modifyTableRow').window('close');
}

/*
 * 修改页面提交按钮-单击事件
 */
function modifyHtmlSubmitClick() {
	console.log('修改表格页-提交按钮单击事件回调函数');
	// 获取数据
	var modifyData = $('#modifyTableRowForm').serialize();
	console.log(modifyData);
	// 提交
	$.ajax({
				type : "POST",
				url : modifyRowUrl,
				data : modifyData,
				async : true, // 默认
				dataType : "json",
				beforeSend : function(XMLHttpRequest) {
				},
				complete : function(XMLHttpRequest, textStatus) {
				},
				success : modifyHtmlSubmitSuccessFunction,
				error : function() {
					$.messager.show({
								title : '提示页面',
								msg : '修改失败',
								timeout : 2000,
								showType : 'slide'
							});
				}
			});

}

/**
 * 修改行数据-成功回调函数
 */
function modifyHtmlSubmitSuccessFunction(jsonData) {
	console.log('修改表格页-提交按钮单击事件回调函数……');
	// 获取新增页面数据json
	// var modifyRowJson = $( '#modifyTableRowForm' ).serializeArray();
	var modifyRowData = $('#modifyTableRowForm').serialize();
	modifyRowData = decodeURIComponent(modifyRowData, true);
	// var modifyRowData = $( '#modifyTableRowForm' ).serializeArray();
	// var modifyRowJson = serializeFormJSON( modifyRowData );
	var modifyRowJson = formToJson(modifyRowData);
	console.log(modifyRowJson);
	if (jsonData != null) {
		if (jsonData.data != null && jsonData.state == 0) {
			// 提示
			$.messager.show({
						title : '提示页面',
						msg : '修改成功',
						timeout : 2000,
						showType : 'slide'
					});
			// 前端修改更新数据
			activeModifyRowData(modifyRowJson);

			// 关闭页面
			$('#modifyTableRow').window('close');
		} else {
			$.messager.show({
						title : '提示页面',
						msg : '修改失败，数据保存失败',
						timeout : 2000,
						showType : 'slide'
					});
		}
	} else {
		$.messager.show({
					title : '提示页面',
					msg : '修改失败，返回数据为空',
					timeout : 2000,
					showType : 'slide'
				});
	}
}

/**
 * 新增表格一行数据-动态生成
 */
function activeAddRowData(index, rowJson) {
	console.log('表格一行数据-动态生成……');
	$(tableId).datagrid('insertRow', {
				index : 0, // 索引从0开始
				row : JSON.parse(rowJson)
			});
}

/**
 * 更新表格一行数据-动态生成
 */
function activeModifyRowData(rowJson) {
	console.log('更新表格一行数据-刷新表格数据……');
	var row = $(tableId).datagrid('getSelected');
	var rowIndex = $(tableId).datagrid("getRowIndex", row);
	// console.log( rowIndex );
	// console.log( JSON.parse( rowIndex ) );
	$(tableId).datagrid("updateRow", {
				index : rowIndex,
				row : JSON.parse(rowJson)
			})
}

/**
 * 导出按钮悬停事件
 */
function exportXTCF() {
	console.log('导出按钮悬停事件……');
	// 获取当前导出组件位置
	var widths = $('#exports').offset().left + $('#exports')[0].offsetWidth;
	var heights = $('#exports').offset().top;

	// 定位生成
	$('#exportList').css({
				'position' : "absolute",
				'top' : heights,
				'left' : widths,
				'z-index' : 20,
				'height' : '160px',
				'overflow' : "hidden"
			});

	// 显示
	$('#exportList').show();
}

/**
 * 回收站单击事件
 */
function binCF() {
	console.log('---------回收站单击事件----------');
	// 跳转页面
	// self.parent.addTab( "设备回收站", equipmentDelInfoUrl, "icon-cut");
	location.href = equipmentDelInfoUrl;
}

/*
 * $(Form).serialize()转化为JSON
 */
function serializeFormJSON(serializeData) {
	var o = {};
	serializeData = decodeURIComponent(serializeData, true);// 防止中文乱码
	$.each(serializeData, function(k, v) {
				if (o[v.name]) {
					// if (!o[v.name].push) {
					// o[v.name] = [o[v.name]];
					// }
					o[v.name].push(v.value || '');
				} else {
					o[v.name] = v.value || '';
				}
			});
	return o;
};

function formToJson(data) {
	data = data.replace(/&/g, "\",\"");
	data = data.replace(/=/g, "\":\"");
	data = "{\"" + data + "\"}";
	return data;
}
