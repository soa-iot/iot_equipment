
// 获取当前登录人的信息
function readCookie(name)
{
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

// 获取权限
function getPowerUrl(key)
{
	var json = [];

	// 如果没有登录直接返回空
	var userID = readCookie("userID");
	if ($.isEmptyObject(userID))
	{
		easyuiMain(json);
	}

	// 存入 sessionStorage 中 ，当然也可以是localStorage本地缓存，也可以是websql
	var jsonStorage = sessionStorage.getItem(userID + key);
	if ($.isEmptyObject(jsonStorage))
	{
		$.post("/CZ_PIOTMS/powerMenuServlet.action",
		{
			"key"		: key,
			"userID"	: userID
		}, function(jsonDate)
		{
			sessionStorage.setItem(userID + key, JSON.stringify(jsonDate));
			easyuiMain(jsonDate);
		}, "json");
	} else
	{
		easyuiMain(JSON.parse(jsonStorage));
	}
}
