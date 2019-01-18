$(function(){
	winresize();
	var isani = false;
	$(".left-menus").on("click",".nav .nav-list > li label",function(){
		if($(this).parent().find("ul").size() != 0){
			if(!isani){
				isani = true;
				if($(this).parent().find("ul").is(":hidden")){
					$(this).parent().addClass("curr").find("ul").slideDown(300,function(){
						$(this).parent().closest("li").find(".fa-angle-left").removeClass("fa-angle-left")
						.addClass("fa-angle-down");
						isani = false;
					})
					$(this).parent().siblings(".curr").removeClass("curr").find("ul").slideUp(300,function(){
						$(this).parent().closest("li").find(".fa-angle-down").removeClass("fa-angle-down")
						.addClass("fa-angle-left");
						isani = false;
					});
				}else{
					$(this).parent().removeClass("curr").find("ul").slideUp(300,function(){
						$(this).parent().closest("li").find(".fa-angle-down").removeClass("fa-angle-down")
						.addClass("fa-angle-left");
						isani = false;
					});
				}
			}
		}else{
			menuItemClick($(this).parent());
		}
	}).on("click",".nav .nav-list > li li",function(){
		menuItemClick($(this));
	});;
	$(window).resize(function(){
		winresize();
	});
	$(".right-container").on("click",".container-tab label",function(){
		var tabs = $(this).closest(".tab-contents");
		var tbw = tabs.width()/2;
		var poleft = $(this).position().left;
		tabs.animate({scrollLeft:tabs.scrollLeft() + (poleft - tbw)},300);
		if(!$(this).hasClass("curr")){
			var ind = $(this).index();
			$(this).closest(".right-container")
			.find(".content-pages iframe:eq("+ind+")")
			.addClass("curr").siblings(".curr").removeClass("curr");
			$(this).addClass("curr").siblings(".curr").removeClass("curr");
		}
	}).on("click",".container-tab label i",function(e){
		var parent = $(this).parent();
		if(parent.hasClass("curr")){
            var dataUploadJudge = parent.text().trim();
            if(isDataUploading){
                if(dataUploadJudge == "数据上传"){
                    if(!confirm("关闭该窗口会导致数据上传中断，是否关闭")){
                        return;
                    }
                }
			}
			var index = parent.index();
			index = index == $(".right-container .tab-contents label:last").index() ? index - 1 : index + 1;
			$(".right-container .container-tab label:eq("+index+")").trigger("click");
		}
		$(this).closest(".right-container").find(".content-pages iframe:eq("+parent.index()+")").remove();
		parent.remove();
	}).on("click",".container-tab .right-tab",function(e){
		var tabs = $(this).siblings(".tab-contents");
		var totalWid = 0;
		tabs.find("label").each(function(i,e){
			totalWid += $(e).outerWidth();
		});
		if(tabs.scrollLeft() + tabs.width() < totalWid){
			tabs.animate({scrollLeft:tabs.scrollLeft() + tabs.width()},300);
		}
	}).on("click",".container-tab .left-tab",function(e){
		var tabs = $(this).siblings(".tab-contents");
		var totalWid = 0;
		tabs.find("label").each(function(i,e){
			totalWid += $(e).outerWidth();
		});
		if(tabs.scrollLeft() > 0){
			tabs.animate({scrollLeft:tabs.scrollLeft() - tabs.width()},300);
		}
	}).on("click",".container-tab .dropdown-menu li",function(e){
		if($(this).hasClass("tabShowActive")){
			var activeTab = $(this).closest(".container-tab").find(".tab-contents").find(".curr");
			activeTab.trigger("click");
		}else if($(this).hasClass("tabCloseAll")){
			$(this).closest(".container-tab").find(".tab-contents").scrollLeft(0);
			$(this).closest(".container-tab").find("label:first").trigger("click");
			$($(this).closest(".container-tab").find("label:not(:first)")).each(function () {
                var dataUploadJudge = $(this).text().trim();
                if(isDataUploading){
                    if(dataUploadJudge == "数据上传") {
                        if (!confirm("关闭该窗口会导致数据上传中断，是否关闭")) {
                            return;
                        }
                    }
                }
                $(this).remove();
            })
			$(this).closest(".right-container").find(".content-pages iframe:not(:first)").remove();
		}else if($(this).hasClass("tabCloseOther")){
			$(this).closest(".container-tab").find(".tab-contents").scrollLeft(0);
			$($(this).closest(".container-tab").find("label:not(:first,.curr)")).each(function () {
                var dataUploadJudge = $(this).text().trim();
                if(dataUploadJudge == "数据上传"){
                	if(isDataUploading){
                        if(!confirm("关闭该窗口会导致数据上传中断，是否关闭")){
                            return;
                        }
					}
                }
                $(this).remove();
            });
			$(this).closest(".right-container").find(".content-pages iframe:not(:first,.curr)").remove();
		}
	});
	$(".right-container .container-tab label:first").trigger("click");
	
	$(".scrolbar").click(function(){
		if($(this).find("i").hasClass("fa-angle-left")){
			$(".sys-logo").hide();
			$(".left-menus").width(60).find(".lefthide").hide();
			$(".left-menus .nav-list > li.curr label").trigger("click");
			$(".left-menus .nav-list > li").css({textIndent:8,fontSize:30});
			$(this).css({left:0,borderRadius:"0 0 50px 0"});
			$(".right-container").css({marginLeft:65});
			$(this).find("i").removeClass("fa-angle-left").addClass("fa-angle-right");
			
		}else{
			$(".left-menus").width(240);
			$(".left-menus .nav-list > li").css({textIndent:15,fontSize:14});
			$(this).css({left:180,borderRadius:"0 0 0 50px"});
			$(".right-container").css({marginLeft:245});
			$(this).find("i").removeClass("fa-angle-right").addClass("fa-angle-left");
			window.setTimeout(function(){
				$(".left-menus").find(".lefthide").show();
				$(".sys-logo").show();
			},300);
		}
	});
});
function menuItemClick(mod){
	var tabcont = $(".right-container .tab-contents div");
	var index = mod.index(".left-menus .nav li");
	var href = mod.attr("shref");
	if(tabcont.find("label[sind='"+index+"']").size() == 0){
		var ifrcont = $(".right-container .content-pages");
		var name = mod.text();
		var label = $("<label sind='"+index+"'>"+name+"<i class='fa fa-times-circle'></i></label>");
		var iframe = $("<iframe src='"+sysCtx + "/" + href+"'></iframe>");
		tabcont.append(label);
		ifrcont.append(iframe);
		label.trigger("click");
	}else{
		var curInd = tabcont.find("label.curr").attr("sind");
		if(curInd == index){
			$(".content-pages iframe.curr").attr("src",sysCtx + "/" + href);
		}else{
			tabcont.find("label[sind='"+index+"']").trigger("click");
		}
	}
}

function winresize(){
	var hei = $(window).height() - $(".bottom-container").outerHeight();
	if($(".left-menus .nav .slimScrollDiv").size() == 0){
		$(".left-menus .nav .nav-list").slimScroll({
			height: (hei - $(".left-menus .userinfos").outerHeight()) + "px",
			railOpacity: .8,
			wheelStep: 10,
			color:"#fff"
		})
	}else{
		$(".left-menus .nav .slimScrollDiv,.left-menus .nav .nav-list")
		.height(hei - $(".left-menus .userinfos").outerHeight());
	}
	$(".right-container").height(hei);
	$(".right-container .content-pages").outerHeight(hei - 
		$(".right-container .toolbars").outerHeight() - $(".right-container .container-tab").outerHeight());
}

var isDataUploading = false;