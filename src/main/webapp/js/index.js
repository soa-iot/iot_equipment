
// 退出方法
function logout()
{
	document.cookie = "userID=" + null + ";path=/";
	document.cookie = "password=" + null + ";path=/";
	parent.location.href = "logout.jsp";
}
// 总控
$(document).ready(function()
{
	// 监控界面变化事件
	$(window).resize(function()
	{
		// 获取头部高度
		var headerHeight = $('.sys-header').outerHeight(true);
		// 修改左边的导航
		$('.aside-menu').css('top', (headerHeight <= 69 ? 69 : headerHeight) + 'px');
		// 修改右边的内容
		$('.main-content').css('height', 'calc(100% - ' + (headerHeight <= 69 ? 69 : headerHeight + 1) + 'px)');
	});

	// 左边导航点击跳转页面
	$('.aside-menu .menu-dt').click(function()
	{
		// 修改点击状态
		$('.aside-menu .menu-dt').removeClass('menu-selected');
		$(this).addClass('menu-selected');

		// 当前url
		var url = $(this).attr('data-url');
		if (!$.isEmptyObject(url))
		{
			$('.main-content .content-page').attr('src', url);
		}
	});

	// 退出按钮
	$('.logout').click(logout);

	$(window).resize();
});