// 2017-06-07 欧增奇修改手动初始化菜单
var _menus =
{
	basic	: []
};
function easyuiMain(tempMenusJson)
{
	_menus.basic = tempMenusJson || [];

	// 处理参数的有需要动态替换的值
	if (_menus.basic.length > 0)
	{
		$.each(_menus.basic, function(name, values)
		{
			$.each(values.menus, function(key, value)
			{
				var $_url = value.url || "";
				if ($_url.length > 0)
				{
					// 待优化 -> 优化思路 值的类为param.cookie 动态读取readCookie后面的值
					value.url = $_url.replace(/param.cookie.userID/g, readCookie("userID"));
				}
			});
		});
	}

	tabClose();
	tabCloseEven();

	$('#css3menu a').click(function()
	{
		$('#css3menu a').removeClass('active');
		$(this).addClass('active');

		var d = _menus[$(this).attr('name')];
		Clearnav();
		addNav(d);
		InitLeftMenu();
	});

	// 导航菜单绑定初始化
	$("#wnav").accordion(
	{
		animate	: false
	});

	var firstMenuName = $('#css3menu a:first').attr('name');
	addNav(_menus[firstMenuName]);
	InitLeftMenu();

	// 设置默认打开地址，如果没有找到默认第一个
	if (_menus.basic.length > 0)
	{
		var _menuname = "";
		var _url = "";
		var _icon = "";
		$.each(_menus.basic, function(name, values)
		{
			$.each(values.menus, function(key, value)
			{
				// 获取默认打开的页面
				if (!$.isEmptyObject(value.default_open) && value.default_open == "1")
				{
					_menuname = value.menuname || "";
					_url = value.url || "";
					_icon = value.icon || "";
				}
			});
		});
		if (_url.length <= 0)
		{
			_menuname = _menus.basic[0].menus[0].menuname || "";
			_url = _menus.basic[0].menus[0].url || "";
			_icon = _menus.basic[0].menus[0].icon || "";
		}
		if (_url.length > 0)
		{
			addTab(_menuname || "", _url || "", 'icon ' + _icon);
		}
	}
};

function Clearnav()
{
	var pp = $('#wnav').accordion('panels');

	$.each(pp, function(i, n)
	{
		if (n)
		{
			var t = n.panel('options').title;
			$('#wnav').accordion('remove', t);
		}
	});

	pp = $('#wnav').accordion('getSelected');
	if (pp)
	{
		var title = pp.panel('options').title;
		$('#wnav').accordion('remove', title);
	}
}

function addNav(data)
{

	$.each(data, function(i, sm)
	{
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.menus, function(j, o)
		{
			menulist += '<li><div><a ref="' + o.menuid + '" href="'+_url+'" rel="' + o.url + '" ><span class="icon ' + o.icon + '" >&nbsp;</span><span class="nav">' + o.menuname + '</span></a></div></li> ';
		});
		menulist += '</ul>';

		$('#wnav').accordion('add',
		{
			title	: sm.menuname,
			content	: menulist,
			iconCls	: 'icon ' + sm.icon
		});

	});

	var pp = $('#wnav').accordion('panels');
	var t = pp[0].panel('options').title;
	$('#wnav').accordion('select', t);

}

// 初始化左侧
function InitLeftMenu()
{

	hoverMenuItem();

	$('#wnav li a').live('click', function()
	{
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid, icon);

		addTab(tabTitle, url, icon);
		$('#wnav li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});

}

/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem()
{
	$(".easyui-accordion").find('a').hover(function()
	{
		$(this).parent().addClass("hover");
	}, function()
	{
		$(this).parent().removeClass("hover");
	});
}

// 获取左侧导航的图标
function getIcon(menuid)
{
	var icon = 'icon ';
	$.each(_menus, function(i, n)
	{
		$.each(n, function(j, o)
		{
			$.each(o.menus, function(k, m)
			{
				if (m.menuid == menuid)
				{
					icon += m.icon;
					return false;
				}
			});
		});
	});
	return icon;
}

function addTab(subtitle, url, icon)
{
	if (!$('#tabs').tabs('exists', subtitle))
	{
		$('#tabs').tabs('add',
		{
			title		: subtitle,
			content		: createFrame(url),
			closable	: true,
			icon		: icon
		});
	} else
	{
		$('#tabs').tabs('select', subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url)
{
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function()
	{
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e)
	{
		$('#mm').menu('show',
		{
			left	: e.pageX,
			top		: e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven()
{
	// 刷新
	$('#mm-tabupdate').click(function()
	{
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',
		{
			tab		: currTab,
			options	:
			{
				content	: createFrame(url)
			}
		});
	});
	// 关闭当前
	$('#mm-tabclose').click(function()
	{
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function()
	{
		$('.tabs-inner span').each(function(i, n)
		{
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function()
	{
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function()
	{
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0)
		{
			// msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n)
		{
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function()
	{
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0)
		{
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n)
		{
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function()
	{
		$('#mm').menu('hide');
	});
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType)
{
	$.messager.alert(title, msgString, msgType);
}

// 本地时钟
function clockon()
{
	var now = new Date();
	var year = now.getFullYear(); // getFullYear getYear
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month + 1;
	if (month < 10)
		month = "0" + month;
	if (date < 10)
		date = "0" + date;
	if (hour < 10)
		hour = "0" + hour;
	if (minu < 10)
		minu = "0" + minu;
	if (sec < 10)
		sec = "0" + sec;
	var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	week = arr_week[day];
	var time = "";
	time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

	$("#bgclock").html(time);

	var timer = setTimeout("clockon()", 200);
}
