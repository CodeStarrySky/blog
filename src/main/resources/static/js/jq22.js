$(function(){
	systole();
});

function systole(){
	console.log("systole");
	if(!$(".history").length){
		return;
	}
	var $warpEle = $(".history-date"),
		$targetA = $warpEle.find("h2 a,ul li dl dt a"),
		parentH,
		eleTop = [];
	$warpEle.parent().css({"height":99});
	// parentH = $warpEle.parent().height();
	/**
	 * 计算高度值
	 */

	parentH = $warpEle.find("li").length*$warpEle.find("li").outerHeight(true) + $warpEle.find("h2").length*$warpEle.find("h2").outerHeight(true);
	// console.log($warpEle.find("li").length,$warpEle.find("li").outerHeight(true),$warpEle.find("h2").length,$warpEle.find("h2").outerHeight(true));
	// console.log(parentH);
	
	setTimeout(function(){
		
		$warpEle.find("ul").children(":not('h2:first')").each(function(idx){
			eleTop.push($(this).position().top);
			$(this).css({"margin-top":-eleTop[idx]}).children().hide();
		}).animate({"margin-top":0}, 1600).children().fadeIn();

		$warpEle.parent().animate({"height":parentH}, 2600);

		$warpEle.find("ul").children(":not('h2:first')").addClass("bounceInDown").css({"-webkit-animation-duration":"2s","-webkit-animation-delay":"0","-webkit-animation-timing-function":"ease","-webkit-animation-fill-mode":"both"}).end().children("h2").css({"position":"relative"});

	}, 0);

	$targetA.click(function(){
		$(this).parent().css({"position":"relative"});
		$(this).parent().siblings().slideToggle();
		$warpEle.parent().removeAttr("style");
		$warpEle.parent().css("margin-top",52);
		return false;
	});
};