$.fn.extend({
	lsTreeLoadInd : 0,
	lSTreeFunction : function(funcName,funcs){
		var func = eval("({\""+funcName+"\" : "+funcs+"})");
		var opt = $(this).data("lsTreeOpt");
		$(this).data({lsTreeOpt:$.extend(opt,func)});
	},
	getCheckNodes : function(isfath){
		var ids = "";
		isfath = isfath == undefined ? false : isfath;
		$(this).find(".content :checkbox:checked").each(function(i,e){
			if($(e).closest("li").hasClass("hasChild")){
				ids += isfath ? ids == "" ? $(e).val() : "," + $(e).val() : "";
			}else{
				ids += ids == "" ? $(e).val() : "," + $(e).val();
			}
		});
		return ids;
	},
	lSTreeOption : function(name,value){
		if(typeof value != "function"){
			var newOpt = {};
			if(typeof name == "string"){
				newOpt = {[name]: value };
			}else if(typeof name == "object"){
				newOpt = name
			}
			var opt = $(this).data("lsTreeOpt");
			$(this).data({lsTreeOpt:$.extend(opt,newOpt)});
		}
	},
	lSTreeRefresh : function(){
		$(this).loadLStreeData();
		$(this).find(".title :checkbox").prop("checked",false);
		var ref = $(this).data("lsTreeOpt").refresh;
		if(ref != undefined && typeof ref == "function"){
			ref();
		}
	},
	initFromVal : function(formid){
		$("#"+formid+" input:not(.has-read),#"+formid+" textarea,#"+formid+" select").prop("readonly",false);
		$("#"+formid+" input:not(.has-dis),#"+formid+" textarea,#"+formid+" select").prop("disabled",false);
		$("#"+formid+" [editype!='note']").each(function(i,e){
			var type = $(e).attr("editype");
			switch(type){
				case "val" : {
					if($(e).is("select")){
						$(e).val($(e).find("option:first").val());
						if($(e).hasClass("chosens")){
							$(e).trigger('chosen:updated');
						}
					}else{
						if(!$(e).is(".gjeDate")){
							$(e).val("");
						}
					}
				}
				break;
				case "radio" : {
					var index = $(e).parent().index();
					if(index == 0){
						$(e).trigger("click");
					}
				};
				break;
				case "check" : {
					$(e).prop("checked",false);
				};
				break;
			}
		});
	},
	getUpdingData : function(data,form){
		$.each(data,function(i,e){
			var inputs = $("#"+form+" .edit_" + i);
			$.each(inputs,function(j,m){
				var type = $(m).attr("editype");
				switch(type){
					case "val" : {
						$(m).val(e);
						if($(m).hasClass("chosens")){
							$(m).trigger('chosen:updated');
						}
					}
					break;
					case "radio" : {
						if($(m).val() == e){
							$(m).trigger("click");
						}
					};
					break;
					case "check" : {
						if($(m).val() == e){
							$(m).prop("checked",true);
						}
					};
					break;
					case "checks" : {
						if(("," + e + ",").indexOf("," + $(m).val() + ",") != -1){
							$(m).prop("checked",true);
						}
					};
					break;
				}
			});
		});
	},
	checkUpdVals : function(form){
		var messages = true;
		$("#"+form+" .isnumber").each(function(i,e){
			var val = $(e).val();
			var label = $(e).closest("li").find(".title").text();
			if(Number(val) + "" == "NaN"){
				layer.tips('格式不正确，必输为数字！', $(this),{
					tipsMore:true
				});
				messages = false;
			}
		});
		$("#"+form+" [dete]").each(function(i,e){
			var date = $(e).attr("dete");
			var label = $(e).closest("li").find(".title").text().replace("*","");
			switch(date){
				case "val" : {
					if($(e).val() == ""){
						layer.tips(label + "不能为空！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "check" : {
					var name = $(e).attr("name");
					if($(e).parent().index() == 0 
						&& $(e).parent().parent().find("[name='"+name+"']:checked").size() == 0){
						layer.tips("请至少选择一条" + label + "！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
			}
		});
		return messages;
	},
	classSave : function(index, layero,id){
		var opt = $(this).data("lsTreeOpt");
		var url = opt.saveUrl;
		var that = this;
		if(url == undefined || url == ""){
			layer.alert('请配置数据保存的URL！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		var issure = $(this).checkUpdVals("classForm");
		if(!issure){
			return;
		}
		
		var data = $("#classForm").serializeArray();
		var idname = opt.updIdName  != undefined && opt.updIdName != "" ? opt.updIdName
				: opt.nodeIdName != undefined && opt.nodeIdName != ""
					? opt.nodeIdName : "id";
		if(id){
			data.push({name:idname,value:id});
		}
		
		if(opt.saveBefore != undefined && typeof opt.saveBefore == "function"){
			var json = {};
			$.each(data,function(i,e){
				json = $.extend(json,{[e.name]:e.value});
			});
			var ret = opt.saveBefore(json);
			if(ret != undefined && !ret){
				return;
			}
		}
		
		$.ajax({
			url : url,
			data :  data,
			dataType : "json",
			type : "post",
			beforeSend : function() {
				$(that).lsTreeLoadInd = layer.msg("数据获取中,请等待...", {icon: 16});
			},
			error : function(request) {
				layer.close($(that).lsTreeLoadInd); 
				layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
			},
			success : function(d){
				layer.close($(that).lsTreeLoadInd);
				layer.close(index);
				$(that).lSTreeRefresh();
				if(opt.saveSuccess != undefined && typeof opt.saveSuccess == "function"){
					opt.saveSuccess(d);
				}
			}
		});
	},
	getLSTreeData : function(data){
		$(this).find(".content > ul").empty();
		var opt = $(this).data("lsTreeOpt");
		var nodeIdName = opt.nodeIdName || "id";
		var nodePidName = opt.nodePidName || "pid";
		var nodeTitleName = opt.nodeTitleName || "name";
		var allowSeries = opt.allowSeries || -1;
		
		var allowAdd = opt.allowAdd != undefined && !opt.allowAdd ? false : true;
		var allowEdit = opt.allowEdit != undefined && !opt.allowEdit ? false : true;
		var allowDelete = opt.allowDelete != undefined && !opt.allowDelete ? false : true;
		var isEdit = opt.isEdit != undefined && !opt.isEdit ? false : true;
		var initOpen = opt.initOpen || false;
		var isCheck = opt.isCheck != undefined ? opt.isCheck : true;
		
		var allowOperSeries = opt.allowOperSeries || [];
		allowOperSeries ="," + allowOperSeries.join(",") +",";
		
		var allowAddSeries = opt.allowAddSeries || [];
		allowAddSeries ="," + allowAddSeries.join(",") +",";
		
		var allowEditSeries = opt.allowEditSeries || [];
		allowEditSeries ="," + allowEditSeries.join(",") +",";
		
		var allowDeleteSeries = opt.allowDeleteSeries || [];
		allowDeleteSeries ="," + allowDeleteSeries.join(",") +",";
		
		var that = this;
		$(that).lsTreeLoadInd = layer.msg('LSTree正在加载数据中，请稍等', {icon: 16,time:1});
		
		$.each(data,function(i,e){
			
			var addstr = allowAdd ? "<i class='fa fa-plus'></i>" : "";
			var updstr = allowEdit ? "<i class='fa fa-edit'></i>" : "";
			var delstr = allowDelete ? "<i class='fa fa-trash'></i>" : "";
			
			var checkStr = isCheck ? "<label class='lscheck'><b></b><input type='checkbox' value='"
					+(e[nodeIdName] + "").split("-")[0]+"'><i></i></label>" : "";
			
			var editStr = isEdit ? "<div>"+ addstr + updstr + delstr +"</div>" : ""; 
			
			var li = $("<li sid='"+e[nodeIdName]+"' spid='"+e[nodePidName]+"' serind='1'><div>"+ checkStr
					+"<span title='"+e[nodeTitleName]+"'>" + e[nodeTitleName]+"</span>"+editStr+"</div></li>");
			
			li.data({"nodeData":e});
			
			$(that).find(".content > ul").append(li);
		});
		
		$.each($(that).find(".content li"),function(i,e){
			var pli =  $(that).find(".content li[sid='"+$(e).attr("spid")+"']");
			if(pli.size() != 0){
				if(pli.find("ul").size() == 0){
					var ul = $("<ul style='margin-left:15px;"+(initOpen ? "" : "display:none;")+"'></ul>");
					var rightico = $("<i class='fa "+(initOpen ? "fa-minus-square-o" : "fa-plus-square-o")+"'></i>");
					ul.append($(e));
					pli.addClass("hasChild").append(ul).find(" > div").prepend(rightico);
				}else{
					pli.find(" > ul").append($(e));
				}
			}
		});
		
		$.each($(that).find(".content li"),function(i,e){
			var pulleng = $(e).parents(".hasChild").size();
			$(e).attr("serind",pulleng + 1);
			if(allowSeries != -1 && pulleng >= allowSeries){
				$(e).remove();
			}
			if(allowOperSeries != ",," && allowOperSeries.indexOf(","+(pulleng + 1)+",") == -1){
				$(e).find(" > div div").remove();
			}
			if(pulleng == allowSeries - 1 
				|| (allowAddSeries != ",," && allowAddSeries.indexOf(","+(pulleng + 1)+",") == -1)){
				$(e).find(" > div .fa-plus").remove();
			}
			if(allowEditSeries != ",," && allowEditSeries.indexOf(","+(pulleng + 1)+",") == -1){
				$(e).find(" > div .fa-edit").remove();
			}
			if(allowDeleteSeries != ",," && allowDeleteSeries.indexOf(","+(pulleng + 1)+",") == -1){
				$(e).find(" > div .fa-trash").remove();
			}
		});
		layer.close($(that).lsTreeLoadInd);
		if(opt.success != undefined && typeof opt.success == "function"){
			opt.success(data);
		}
	},
	getLSTree : function(opt){
		var titleCont = opt.isEdit != undefined && opt.isEdit == false ? "" : "<i class='fa fa-plus'></i>";
		var isCheck = opt.isCheck != undefined ? opt.isCheck : true;
		var checkStr = isCheck ? "<label class='lscheck'><b></b><input type='checkbox'><i></i></label>" : "";
		
		var treeTitle = $("<span class='title'><i class='fa fa-sitemap'></i>"+titleCont
				+"<i class='fa fa-refresh'></i>"+checkStr+"<label class='titcon'></label>"
				+ "<span class='move'><i class='fa fa-chevron-left'></i></span></span>");
		
		var treeContent = $("<div class='content'><ul></ul></div>");
		var treeDom = this;
		var leftani = false;
		
		$(treeDom).addClass("ls-trees").append(treeTitle,treeContent).data({lsTreeOpt:opt});
		
		if(opt.dynamicHei != undefined){
			treeContent.height($(window).height() - parseInt(opt.dynamicHei));
		}else if(opt.fixedHei != undefined){
			treeContent.height(opt.fixedHei);
		}
		
		if(opt.dynamicWid != undefined){
			$(treeDom).width($(window).width() - parseInt(opt.dynamicWid));
		}else if(opt.fixedWid != undefined){
			$(treeDom).width(opt.fixedWid);
		}
		
		$(window).resize(function(){
			if(opt.dynamicWid != undefined && !leftani){
				$(treeDom).width($(window).width() - parseInt($(treeDom).data("lsTreeOpt").dynamicWid));
			}
			if(opt.dynamicHei != undefined && !leftani){
				treeContent.height($(window).height() - parseInt($(treeDom).data("lsTreeOpt").dynamicHei));
			}
		});
		
		treeTitle.find("label.titcon").text(opt.rootNodeName || "系统分类");
		
		$(treeDom).loadLStreeData();
		
		treeContent.on("click","li > div span",function(){
			var opt = $(treeDom).data("lsTreeOpt");
			var serind = $(this).closest("li").attr("serind");
			
			var allowClickSeries = opt.allowClickSeries || [];
			allowClickSeries ="," + allowClickSeries.join(",") +",";
			
			if(allowClickSeries == ",," || allowClickSeries.indexOf(","+serind+",") != -1){
				$(treeDom).find(".content li.curr,.title b").removeClass("curr");
				$(this).closest("li").addClass("curr");
				if(opt.nodeclick != undefined && typeof opt.nodeclick == "function"){
					opt.nodeclick($(this).closest("li"),$(this).closest("li").data("nodeData"));
				}
			}
		}).on("click",".lscheck",function(){
			if($(this).find(":checkbox").prop("checked")){
				$(this).closest("li").addClass("has-checked").find(":checkbox").prop("checked",true);
				$(this).closest("li").find("li").addClass("has-checked");
				
				$(this).parents("li").not($(this).closest("li")).each(function(i,e){
					if($(e).find(":checkbox").size() == $(e).find(":checkbox:checked").size() + 1){
						$(e).find(" > div :checkbox").prop("checked",true);
					}
					$(e).addClass("has-checked");
				});
				if(treeContent.find(":checkbox").size() == treeContent.find(":checkbox:checked").size()){
					treeTitle.find(".lscheck :checkbox").prop("checked",true);
				}
				treeTitle.find(".lscheck").addClass("has-checked");
			}else{
				$(this).closest("li").removeClass("has-checked").find(":checkbox").prop("checked",false);
				$(this).closest("li").find("li").removeClass("has-checked");
				$(this).parents("li").each(function(i,e){
					$(e).find(" > div :checkbox").prop("checked",false);
					if($(e).find(":checkbox:checked").size() == 0){
						$(e).removeClass("has-checked");
					}
				});
				if(treeTitle.find(".lscheck :checkbox").prop("checked")){
					treeTitle.find(".lscheck :checkbox").prop("checked",false);
				}
				if(treeContent.find(":checkbox:checked").size() == 0){
					treeTitle.find(".lscheck").removeClass("has-checked");
				}
			}
		}).on("click","li:not(.hasChild) span",function(){
			var chc = $(treeDom).data("lsTreeOpt").chclick;
			if(chc != undefined && typeof chc == "function"){
				$(treeDom).find(".content li.curr,.title b").removeClass("curr");
				$(this).closest("li").addClass("curr");
				chc($(this).closest("li"),$(this).closest("li").data("nodeData"));
			}
		}).on("click",".hasChild > div > .fa",function(){
			if($(this).hasClass("fa-plus-square-o")){
				$(this).removeClass("fa-plus-square-o").addClass("fa-minus-square-o");
				$(this).parent().siblings("ul").slideDown(300);
			}else{
				$(this).removeClass("fa-minus-square-o").addClass("fa-plus-square-o");
				$(this).parent().siblings("ul").slideUp(300);
			}
		}).on("click",".hasChild > div > span",function(){
			var pac = $(treeDom).data("lsTreeOpt").paclick;
			if(pac != null && typeof pac == "function"){
				pac($(this).closest("li"),$(this).closest("li").data("nodeData"));
			}
		}).on("click",".fa-plus",function(){
			var opt = $(treeDom).data("lsTreeOpt");
			if(opt.formId == undefined || opt.formId == ""){
				layer.alert('请配置数据编辑的FORM表单ID值！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				return;
			}
			var nodeData = $(this).closest("li").data("nodeData");
			var dom  = $(this).closest("li");
			var pid = $(this).closest("li").attr("sid");
			pid = pid.split("-")[0];
			var name = $(this).closest("li").find("span").attr("title");
			layer.open({
	    		title : "添加" + opt.rootNodeName,
	    		type : 1,
	    		area : [opt.formWid || "550px",opt.formHei || "420px"],
	    		content : $("#" + opt.formId),
	    		zIndex : 1050,
	    		success: function(layero, index){
	    			$(treeDom).initFromVal(opt.formId);
	    			var ado = $(treeDom).data("lsTreeOpt").addOpen;
	    			if(ado != undefined && typeof ado == "function"){
	    				ado(dom,nodeData);
	    			}
	    		},
	    		btn : ["保存","取消"],
	    		yes: function(index, layero){
	    			$(treeDom).classSave(index, layero);
	    		},
	    		btn2: function(index, layero){
	    			layer.close(index);
	    		}
	    	});
		}).on("click",".fa-edit",function(){
			var opt = $(treeDom).data("lsTreeOpt");
			if(opt.formId == undefined || opt.formId == ""){
				layer.alert('请配置数据编辑的FORM表单ID值！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				return;
			}
			var url = opt.getNodeUrl;
			if(url == undefined || url == ""){
				layer.alert('请配置获取节点数据的URL！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				return;
			}
			var id = $(this).closest("li").attr("sid");
			id = id.split("-")[0];
			var idName = opt.updIdName || opt.nodeIdName || "id";
			var postData = JSON.parse("{\""+idName+"\":\""+id+"\"}");
			
			var nodeData = $(this).closest("li").data("nodeData");
			var dom  = $(this).closest("li");
			$.ajax({
				url : url,
				data : postData,
				dataType : "json",
				type : "post",
				beforeSend : function() {
					$(treeDom).lsTreeLoadInd = layer.msg("数据获取中,请等待...", {icon: 16});
				},
				error : function(request) {
					layer.close($(treeDom).lsTreeLoadInd); 
					layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				},
				success : function(d){
					layer.close($(treeDom).lsTreeLoadInd);
					if(opt.getNodeSuccess != undefined && typeof opt.getNodeSuccess == "function"){
						opt.getNodeSuccess(d);
					}
	    			layer.open({
	    	    		title : "修改" + opt.rootNodeName,
	    	    		type : 1,
	    	    		area : [opt.formWid || "550px",opt.formHei || "420px"],
	    	    		content : $("#" + opt.formId),
	    	    		zIndex : 1050,
	    	    		success: function(layero, index){
	    	    			var edo = $(treeDom).data("lsTreeOpt").eidtOpen;
	    	    			if(edo != undefined && typeof edo == "function"){
	    	    				edo(dom,nodeData);
			    			}
	    	    		},
	    	    		btn : ["保存","取消"],
	    	    		yes : function(index, layero){
	    	    			$(treeDom).classSave(index, layero,id);
	    	    		},
	    	    		btn2 : function(index, layero){
	    	    			layer.close(index);
	    	    		}
	    	    	});
				}
			});
		}).on("click",".fa-trash",function(){
			var opt = $(treeDom).data("lsTreeOpt");
			var url = opt.delNodeUrl;
			if(url == undefined || url == ""){
				layer.alert('请配置删除节点数据的URL！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				return;
			}
			var id = $(this).closest("li").attr("sid");
			id = id.split("-")[0];
			var idName = opt.delIdName || opt.nodeIdName || "id";
			var postData = JSON.parse("{\""+idName+"\":\""+id+"\"}");
			layer.confirm('删除此数据及下属所有数据，确认吗？', {icon: 3, title:'提示'}, function(index){
				$.ajax({
					url : url,
					data : postData,
					dataType : "json",
					type : "post",
					beforeSend : function() {
						$(treeDom).lsTreeLoadInd = layer.msg("数据获取中,请等待...", {icon: 16});
					},
					error : function(request) {
						layer.close($(treeDom).lsTreeLoadInd); 
						layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(d){
						layer.close($(treeDom).lsTreeLoadInd);
						layer.close(index);
						$(treeDom).lSTreeRefresh();
						if(opt.delSuccess != undefined && typeof opt.delSuccess == "function"){
							opt.delSuccess(d);
						}
					}});
			  
			});
		});
		
		treeTitle.on("click",".fa-refresh",function(){
			$(treeDom).loadLStreeData();
			treeTitle.find(":checkbox").prop("checked",false);
			var ref = $(treeDom).data("lsTreeOpt").refresh;
			if(ref != undefined && typeof ref == "function"){
				ref();
			}
		}).on("click",".lscheck",function(){
			if($(this).find(":checkbox").prop("checked")){
				treeContent.find(":checkbox").prop("checked",true);
				treeContent.find("li").addClass("has-checked");
			}else{
				treeContent.find(":checkbox").prop("checked",false);
				treeContent.find("li").removeClass("has-checked");
				$(this).removeClass("has-checked");
			}
		});
		
		
		treeTitle.find(".move").click(function(){
			var opt = $(treeDom).data("lsTreeOpt");
			if(!leftani){
				var i = $(this).find(".fa");
				leftani = true;
				if(i.hasClass("fa-chevron-left")){
					if(opt.leftHide != undefined && typeof opt.leftHide == "function"){
						opt.leftHide();
					}
					treeContent.animate({opacity:0,width:"0%"},300);
					$(treeDom).animate({width:60},300,function(){
						i.removeClass("fa-chevron-left").addClass("fa-chevron-right");
						$(window).trigger("resize");
						leftani = false;
					});
				}else{
					var wid = opt.dynamicWid != undefined ? $(window).width() - parseInt(opt.dynamicWid)
							: opt.fixedWid != undefined ? opt.fixedWid : 220;
					
					if(opt.leftShow != undefined && typeof opt.leftShow == "function"){
						opt.leftShow();
					}
					treeContent.animate({opacity:1,width:"100%"},300);
					$(treeDom).animate({width:wid},300,function(){
						i.removeClass("fa-chevron-right").addClass("fa-chevron-left");
						$(window).trigger("resize");
						leftani = false;
					});
				}
			}
		});
		
		
		if(opt.isEdit == undefined || opt.isEdit){
			treeTitle.find(".fa-plus").click(function(){
				if($(treeDom).data("lsTreeOpt").formId == undefined || $(treeDom).data("lsTreeOpt").formId == ""){
					layer.alert('请配置数据编辑的FORM表单ID值！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					return;
				}
				layer.open({
		    		title : "添加" + $(treeDom).data("lsTreeOpt").rootNodeName,
		    		type : 1,
		    		area : [$(treeDom).data("lsTreeOpt").formWid || "550px",$(treeDom).data("lsTreeOpt").formHei || "420px"],
		    		content : $("#"+$(treeDom).data("lsTreeOpt").formId),
		    		zIndex : 1050,
		    		success: function(layero, index){
		    			$(treeDom).initFromVal($(treeDom).data("lsTreeOpt").formId);
		    			var ado = $(treeDom).data("lsTreeOpt").addOpen;
		    			if(ado != undefined && typeof ado == "function"){
		    				ado();
		    			}
		    		},
		    		btn : ["保存","取消"],
		    		yes: function(index, layero){
		    			$(treeDom).classSave(index, layero);
		    		},
		    		btn2: function(index, layero){
		    			layer.close(index);
		    		}
		    	});
			});
		}
	},
	loadLStreeData:function(){
		var opt = $(this).data("lsTreeOpt");
		var that = this;
		if(opt.url != undefined && opt.url != ""){
			$.ajax({
				url : opt.url,
				dataType : "json",
				type : "post",
				async : opt.async != undefined ? opt.async : true,
				data : opt.postData || {},
				beforeSend : function() {
					$(that).lsTreeLoadInd = layer.msg("数据获取中,请等待...", {icon: 16});
				},
				error : function(request) {
					layer.close($(that).lsTreeLoadInd); 
					layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				},
				success : function(data){
					layer.close($(that).lsTreeLoadInd); 
					$(that).getLSTreeData(data);
				}
			});
		}else{
			$(that).getLSTreeData(opt.localData);
		}
	}
});