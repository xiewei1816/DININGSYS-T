var pageUtil = {
	operStat : 0,
	loadInd : 0,
	editInd : 0,
	tabId : "grid-table",
	formId : "editForm",
	myModal : "myModal",
	pager : "grid-pager",
	imageFile : "file",
	isSaveBtn : false,
	initFormVals : null,
	tabHei : 0,
	treeOpt : null,
	numberCodes : [{key:"8",value:""},{key:"9",value:""},{key:"37",value:""},{key:"39",value:""},{key:"c+65",value:""}
			 ,{key:"c+67",value:""},{key:"c+86",value:""},{key:"c+90",value:""},{key:"110",value:""},{key:"48",value:"0"}
			 ,{key:"49",value:"1"},{key:"50",value:"2"},{key:"51",value:"3"},{key:"52",value:"4"}
			 ,{key:"53",value:"5"},{key:"54",value:"6"},{key:"55",value:"7"},{key:"56",value:"8"}
			 ,{key:"57",value:"9"},{key:"96",value:"0"},{key:"97",value:"1"},{key:"98",value:"2"}
			 ,{key:"99",value:"3"},{key:"100",value:"4"},{key:"101",value:"5"},{key:"102",value:"6"}
			 ,{key:"103",value:"7"},{key:"104",value:"8"},{key:"105",value:"9"},{key:"109",value:""}
			 ,{key:"189",value:""},{key:"190",value:"."}],
	viewDatas : function(opt){
		var gr = $("#" + pageUtil.tabId).jqGrid('getGridParam', 'selarrrow');
		var grs = $("#" + pageUtil.tabId).jqGrid('getGridParam', 'selrow');
		pageUtil.operStat = 2;
		if (grs == undefined) {
			layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		if ((gr + "").split(",").length > 1) {
			layer.alert('请选择一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		if(opt.before != undefined && typeof opt.before == "function"){
			opt.before();
		}
		$.ajax({
			url : opt.url,
			data : {id:grs},
			type : "POST",
			dataType:"json",
			beforeSend : function() {
				pageUtil.loadInd = layer.msg("数据获取中,请等待...", {icon: 16});
			},
			error : function(request) {
				layer.close(pageUtil.loadInd); 
				layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
			},
			success : function(data) {
				layer.close(pageUtil.loadInd);
				if(opt.success != undefined && typeof opt.success == "function"){
					opt.success(data);
					$("form input,form textarea").prop("readonly",true);
					$("form select,from :radio,from :checkbox").prop("disabled",true);
					$(".modal-dialog .modal-footer .btn-primary").hide();
				}
			}
		});
	},
	editDialogOpen : function(opt){
		var editDiv = $("#"+pageUtil.formId).parent();
		var str = pageUtil.operStat == 1 ? "新增" : "修改"; 
		pageUtil.editInd = layer.open({
    		title : str + editDiv.attr("title") || str + "数据",
    		type : 1,
    		area : [ ($("body").width() - 100) + "px",($(window).height() - 100) + "px"],
    		content : editDiv,
    		zIndex : 1050,
    		success: function(layero, index){
    			pageUtil.initFromVal();
    		},
    		end : function(layero, index){
    			pageUtil.initFromVal();  
    		},
    		btn : ["确认","取消"],
    		yes: function(index, layero){
    			pageUtil.saveDatas(opt);
    		},
    		btn2: function(index, layero){
    			layer.close(index);
    		}
    	});
	},
	checkUpdVals : function(form){
		var messages = true;
		$("#"+(pageUtil.formId || form)+" .isnumber").each(function(i,e){
			var val = $(e).val();
			var label = $(e).closest("li").find(".title").text();
			if(isNaN(Number(val))){
				layer.tips('格式不正确，必输为数字！', $(this),{
					tipsMore:true
				});
				messages = false;
			}
		});
		$("#"+(pageUtil.formId || form)+" [dete]").each(function(i,e){
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
				case "clstype" : {
					if($(e).val() == ""){
						layer.tips(label + "不能为空,请在左侧类别中选择！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-email" : {
					var e_m = $(e).val();
					var pot = e_m.indexOf("@");
					var com = e_m.indexOf(".com");
					if(e_m != "" && (pot == -1 || com == -1 || pot == 0 ||com != e_m.length - 4  || com - pot <= 1)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-idcard" : {
					var idcard = $(e).val();
					var reg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/; //身份证号验证规则
					if(!reg.test(idcard)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-mobile" : {
					var mobile = $(e).val();
					var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
					if(mobile != "" && !reg.test(mobile)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-phoneormobile" : {
					var pom = $(e).val();
					var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
					var reg2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/; //座机号验证规则
					if(pom != "" && !(reg.test(pom)||reg2.test(pom))){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-positive" : {
					var height = $(e).val();
					var reg = /^[0-9]*$/; //正整数验证规则
					if(!reg.test(height)){
						layer.tips(label + "格式不正确，请填写正整数！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-date" : {
					var date = $(e).val();
					var reg = /^(?:[01]\d|2[0-3])(?::[0-5]\d)$/; //日期验证规则
					if(date != "" && !reg.test(date)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-datetime" : {
					var datetime = $(e).val();
					var reg = /^\d{4}-(0\d|1[0-2])-([0-2]\d|3[01])( ([01]\d|2[0-3])\:[0-5]\d\:[0-5]\d)?$/; //日期时间验证规则
					if(datetime != "" && !reg.test(datetime)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "em-money" : {
					var money = $(e).val();
					var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //金钱验证规则
					if(money != "" && !reg.test(money)){
						layer.tips(label + "格式不正确，最多保留两位小数！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "en-num" : {
					var enNum = $(e).val();
					var reg = /^[a-zA-Z\d]+$/;
					if(!reg.test(enNum)){
						layer.tips(label + "格式不正确，请输入英文或数字或英文与数字组合", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "pass" : {
					if($(e).val() == ""){
						layer.tips(label + "不能为空！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "rpass" : {
					var pass = $("#"+pageUtil.formId+" [dete='pass']").val();
					var rpass = $(e).val();
					if(rpass == ""){
						layer.tips(label + "不能为空！", $(e),{
							tipsMore:true
						});
						messages = false;
					}else if(pass != rpass){
						layer.tips("两次输入密码不一致！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
				};
				break;
				case "note" : {
					/*var id = $(e).attr("id");
					if(KE.getContent() == ""){
						messages += "内容不能为空！<br>";
					}
					*/
				}
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
	getUpdingData : function(data,form){
		$.each(data,function(i,e){
			var inputs = $("#"+(form || pageUtil.formId)+" .edit_" + i);
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
					case "pass" : $(m).val("*********").prop("disabled",true);
					break;
					case "a" : $(m).attr("href","/Gateway/newsiconcat/" + e);
					break;
					case "img" : $(m).attr("src","/Gateway/newsiconcat/" + e);
					break;
					/*case "note" : {
						var id = $(m).attr("id");
						KE.html(id, e);
					};
					break;*/
				}
			});
		});
	},
	addOper : function(opt){
		pageUtil.operStat = 1;
		if(opt.addBefore != undefined && typeof opt.addBefore == "function"){
			opt.addBefore();
		}
		pageUtil.editDialogOpen(opt);
	},
	updOper : function(opt){
		var gr = $("#" + pageUtil.tabId).jqGrid('getGridParam', 'selarrrow');
		var grs = $("#" + pageUtil.tabId).jqGrid('getGridParam', 'selrow');
		pageUtil.operStat = 2;
		if (grs == undefined) {
			layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		if ((gr + "").split(",").length > 1) {
			layer.alert('请选择一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		var isSure = true;
		if(opt.updBefore != undefined && typeof opt.updBefore == "function"){
			var reutn = opt.updBefore();
			isSure = reutn == undefined || reutn == null ? true : reutn;
		}
		if(!isSure){
			return;
		}
		$.ajax({
			url : opt.updUrl,
			data : {id:grs},
			type : "POST",
			dataType:"json",
			beforeSend : function() {
				pageUtil.loadInd = layer.msg("数据获取中,请等待...", {icon: 16});
			},
			error : function(request) {
				layer.close(pageUtil.loadInd); 
				layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
			},
			success : function(data) {
				layer.close(pageUtil.loadInd);
				pageUtil.editDialogOpen(opt);
				if(opt.updSuccess != undefined && typeof opt.updSuccess == "function"){
					opt.updSuccess(data);
				}
				
			}
		});
	},
	delOper : function(opt){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		if(opt.before != undefined && typeof opt.before == "function"){
			opt.before();
		}
		
		layer.confirm('确认是否删除该数据?', {
		  icon : 0,
		  btn : ['删除','取消'] //按钮
		  }, function(index){
			  jQuery.ajax({
					url : opt.url,
					data : {"editIds":rowids + ""},
					type : "POST",
					dataType:"json",
					error : function(request) {
						layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(data) {
						layer.close(index);
						$("#" + pageUtil.tabId).trigger("reloadGrid");
						if(opt.success != undefined && typeof opt.success == "function"){
							opt.success(data);
						}
					}
				});
		  }, function(){
			  
		 });
	},
	initFromVal : function(form){
		pageUtil.operStat = 0;
		var formid = form || pageUtil.formId;
		$(".edit-tabs li:first").trigger("click");
		$("#"+formid+" input:not(.gjeDate,.notread),#"+formid+" textarea,#"+formid+" select").prop("readonly",false);
		$("#"+formid+" input:not(.getclassTypev),#"+formid+" textarea,#"+formid+" select").prop("disabled",false);
		$("#"+formid+" [editype!='note']").each(function(i,e){
			var type = $(e).attr("editype");
			switch(type){
				case "val" : {
					if($(e).is("select")){
						$(e).val($(e).attr("defaultVal") ? 
						$(e).attr("defaultVal") : $(e).find("option:first").val());
						if($(e).hasClass("chosens")){
							$(e).trigger('chosen:updated');
						}
					}else{
						if(!$(e).is(".gjeDate")){
							$(e).val($(e).attr("defaultVal") ? $(e).attr("defaultVal") : "");
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
				case "pass" : $(e).val("").prop("disabled",false);
				break;
				case "a" : $(e).attr("href","");
				break;
				case "img" : $(e).attr("src","");
				break;
				/*case "note" : {
					var id = $(e).attr("id");
					KE.html(id, "");
				};
				break;*/
			}
		});
		if(pageUtil.initFormVals != undefined && pageUtil.initFormVals != null
				&& typeof pageUtil.initFormVals == "function"){
			pageUtil.initFormVals();
		}
	},
	saveDatas : function(opt){
		if(!pageUtil.checkUpdVals(opt.form)){
			return;
		}
		var isSure = true;
		if(opt.saveBefore != undefined && typeof opt.saveBefore == "function"){
			var reutn = opt.saveBefore();
			isSure = reutn == undefined || reutn == null ? true : reutn;
		}
		if(!isSure){
			return;
		}
		var notHasFile = $("#"+pageUtil.formId + " :file").size() == 0 ? true : false;
		var ajaxs = {
				url : opt.saveUrl,
				data : notHasFile ? $('#'+pageUtil.formId).serialize() : new FormData($("#"+pageUtil.formId)[0]),
				type : "POST",
				dataType : "json",
	            processData: notHasFile,  
				beforeSend : function() {
					pageUtil.loadInd = layer.msg("数据保存中,请等待...", {icon: 16});
				},
				error : function(request) {
					layer.close(pageUtil.loadInd); 
					layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				},
				success : function(data) {
					var isSuc = true;
					if(opt.saveSuccess != undefined && typeof opt.saveSuccess == "function"){
						var succ = opt.saveSuccess(data);
						isSuc = succ != undefined ? succ : isSuc;
					}
					if(!isSuc){
						return;
					}
					layer.close(pageUtil.loadInd); 
					layer.close(pageUtil.editInd);
					$("#" + pageUtil.tabId).trigger("reloadGrid");
				}
			}
		ajaxs = notHasFile ? ajaxs : $.extend(ajaxs,{contentType:false});
		jQuery.ajax(ajaxs);
	},
	jcropPic : function(opt){
		
	},
	pageInit : function(opt){
		opt = opt == undefined ? {} : opt;
		pageUtil.initFormVals = opt.initFormVals || null;
		$.jgrid.defaults.styleUI="Bootstrap";
		$("select.chosens").chosen({
    		width : "200px"
    	});
		
		var upOrDown = $("<div class='upOrDown'><i class='fa fa-chevron-up'></i></div>");
		
		$(".search-wrapper").append(upOrDown);
		
		var updani = false;
		upOrDown.click(function(){
			if(!updani){
				var i = $(this).find("i");
				updani = true;
				if(i.hasClass("fa-chevron-up")){
					$(".search-wrapper form").slideUp(300,function(){
						updani = false;
						$(window).trigger("resize");
						i.removeClass("fa-chevron-up").addClass("fa-chevron-down");
					});
				}else{
					$(".search-wrapper form").slideDown(300,function(){
						updani = false;
						$(window).trigger("resize");
						i.removeClass("fa-chevron-down").addClass("fa-chevron-up");
					});
				}
			}
		});
		
		$(".edit-tabs li:not(.curr)").click(function(){
			$(this).addClass("curr").siblings(".curr").removeClass("curr");
			$(this).parent().siblings(".edit-contents:visible").hide();
			$(this).parent().siblings(".edit-contents:eq("+$(this).index()+")").show();
		});
		
		$(".gjeDate").each(function(i,e){
			var min = $(e).attr("minDate") == undefined ? "1900-01-01 00:00:00" : $(e).attr("minDate");
			var gjeformat = $(e).attr("gjeformat") == undefined ? "YYYY-MM-DD hh:mm:ss" : $(e).attr("gjeformat");
			var date = {
				dateCell:"#"+$(e).attr("id"),
				format:gjeformat,
				isinitVal:true, 
				isTime:true, 
			    festival: true, 
				minDate:min,
				zIndex:10520,
				choosefun: function(datas){
					if($(e).attr("end") != undefined){
						$("#"+$(e).attr("end")).data("date").minDate = datas;
					}
					if($(e).attr("start") != undefined){
						$("#"+$(e).attr("start")).data("date").maxDate = datas;
					}
			    }
			}
			$(e).data("date",date).prop("readonly",true);
			jeDate($(e).data("date"));
			if($(this).hasClass("notdefTime")){
				$(e).val("");
			}
		});
		
		$(".isnumber").each(function(i,e){
			if(!$(e).attr("defaultval")){
				$(e).attr("defaultval","0");
			}
			$(e).keydown(function(e){
				var code = e.keyCode + "";
				var isAdopt = false;
				$.each(pageUtil.numberCodes,function(i,m){
					var cod = m.key.indexOf("+") != -1 ? m.key.split("+")[1] : m.key;
					if(m.key.indexOf("+") != -1){
						isAdopt = code == cod && e.ctrlKey ? true : isAdopt;
					}else{
						isAdopt = code == cod ? true : isAdopt;
					}
				});
				if(!isAdopt){
					e.preventDefault();
				}
			}).blur(function(){
				var val = $(this).val();
				if(val == "" || isNaN(Number(val))){
					val = $(this).attr("defaultval") || 0;
					$(this).val(val);
				}
			})
		});
		
		$(".query-pan :text").keyup(function(e){
			if(e.keyCode == 13){
				$(".query-pan button").trigger("click");
			}
		});
		
		$(document).mouseover(function(e){
			if(!pageUtil.isSaveBtn&&$(e.target).is("#save")){
				pageUtil.isSaveBtn = true;
			}else if(pageUtil.isSaveBtn = true && !$(e.target).is("#save")){
				pageUtil.isSaveBtn = false;
			}
		});
		
		var needh = $(".search-wrapper").outerHeight() + $(".btn-toolbar").outerHeight() + 80;
		pageUtil.tabHei = ($(window).height() - needh);
		
		$(window).resize(function(){
			needh = $(".search-wrapper").outerHeight() + $(".btn-toolbar").outerHeight() + 80
			+ $("#" + pageUtil.tabId).closest(".ui-jqgrid").find(".ui-jqgrid-sdiv").height();
			$("#" + pageUtil.tabId).jqGrid("setGridWidth",$("#" + pageUtil.tabId).closest(".jqGrid_wrapper").width(),true);
			$("#" + pageUtil.tabId).jqGrid("setGridHeight",$(window).height() - needh,true);
			layer.style(pageUtil.editInd, {
			  width: ($("body").width() - 100) + "px",
			  height : ($("body").height() - 100) + "px",
			});  
		});
		
		$(".query-pan .search-btns").click(function(e){
			e.preventDefault();
			$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
			$("#" + pageUtil.tabId).trigger("reloadGrid");
		});
	}
};
$.fn.extend({
	getJqGrid:function(options){
		var colM = [];
		$.each(options.colM.split(","),function(i,e){
			var mod = $.extend(
					{name:e,index:e}
					,options.colWid[e] == undefined ? {} : {width:options.colWid[e]}
					,options.sorttype == undefined || options.sorttype[e] == undefined ? {} : {sorttype:options.sorttype[e]}
					,options.editable == undefined || options.editable[e] == undefined ? {} : {editable:options.editable[e]}
					,options.align == undefined || options.align[e] == undefined ? {} : {align:options.align[e]}
					,options.classes == undefined || options.classes[e] == undefined ? {} : {classes:options.classes[e]}
					,options.datefmt == undefined || options.datefmt[e] == undefined ? {} : {datefmt:options.datefmt[e]}
					,options.fixed == undefined || options.fixed[e] == undefined ? {} : {fixed:options.fixed[e]}
					,options.formatter == undefined || options.formatter[e] == undefined ? {} : {formatter:options.formatter[e]}
					,options.hidedlg == undefined || options.hidedlg[e] == undefined ? {} : {hidedlg:options.hidedlg[e]}
					,options.hidden == undefined || options.hidden[e] == undefined ? {} : {hidden:options.hidden[e]});
			colM.push(mod);
		});
		var heig = options.dynamicHei != undefined ? $(window).height() - options.dynamicHei
				: options.tabHei != undefined ? options.tabHei : pageUtil.tabHei;
		
		var that = this;
		
		if(options.dynamicHei != undefined){
			$(window).resize(function(){
				$(that).jqGrid("setGridWidth",$(that).closest(".jqGrid_wrapper").width(),true);
				$(that).jqGrid("setGridHeight",$(window).height() - options.dynamicHei,true);
			});
		}
		
		var gridOptions = {
				footerrow : options.footerrow || false,
				height : heig,
				colNames : options.colNames.split(","),
				colModel : colM,
				viewrecords : true,
				altRows : true,
				//toppager: true,
				rownumbers : options.rownumbers == undefined ? false : options.rownumbers,
				multiselect : options.checkbox == undefined ? true : options.checkbox,
				//multikey: "ctrlKey",
				multiboxonly : true,
				loadComplete : function(){
					if(options.footerrow && options.tabHei == undefined){
						var needh = $(".search-wrapper").outerHeight() + $(".btn-toolbar").outerHeight() + 80
						+ $("#" + pageUtil.tabId).closest(".ui-jqgrid").find(".ui-jqgrid-sdiv").height();
						$("#" + pageUtil.tabId).jqGrid("setGridHeight",$(window).height() - needh,true);
					}
					if(options.loadComplete != undefined && typeof options.loadComplete == "function"){
						options.loadComplete();
					}
				},
				onCellSelect : options.onCellSelect == undefined ? function(){} : options.onCellSelect,
				onSelectRow : options.onSelectRow == undefined ? function(){} : options.onSelectRow,
				onSelectAll : options.onSelectAll == undefined ? function(){} : options.onSelectAll,
				afterInsertRow : options.afterInsertRow == undefined ? function(){} : options.afterInsertRow,
				caption : options.caption == undefined ? "" : options.caption,
				autowidth : true
			};
		
		gridOptions = options.url != undefined && options.url != "" 
			? $.extend(gridOptions,{url:options.url,datatype : "json",mtype : "POST"
						,postData :options.queryForm == undefined ? {} : changeJOSNr(options.queryForm)})
			: $.extend(gridOptions,{data:options.data,datatype : "local"});
						
		gridOptions = options.hasPager != undefined && !options.hasPager ? $.extend(gridOptions,{scroll:true}) 
				: $.extend(gridOptions,{pager : options.pager || pageUtil.pager,rowNum : options.rowNum == undefined ? 20 : options.rowNum,
						rowList : options.rowList == undefined ? [ 20, 50, 100 ] : options.rowList.split(","),});
				
		$(this).jqGrid(gridOptions);
	}
});
function changeJOSNr(formId){
	var form = $("#" + formId).serializeArray();
	var jsons = "{";
	for(var i = 0 ;i < form.length;i++){
		var name = form[i].name;
		var val = form[i].value;
		jsons += jsons == "{" ? "\"" + name + "\":\"" + val + "\"" : ",\"" + name + "\":\"" + val + "\"";
	}
	return JSON.parse(jsons + "}");
}
function getnowTime(){
	var date = new Date();
	var mouth = date.getMonth() + 1;
	mouth = mouth < 10 ? "0" + mouth : mouth;
	var year = date.getFullYear();
	var hours = date.getHours();
	hours = hours < 10 ? "0" + hours : hours;
	var minu = date.getMinutes();
	minu = minu < 10 ? "0" + minu : minu;
	var sec = date.getSeconds();
	sec = sec < 10 ? "0" + sec : sec;
	var day = date.getDate();
	day = day < 10 ? "0" + day : day;
	return year + "-" + mouth + "-" + day;
}
function getTxt1CursorPosition(oTxt1){
    var cursurPosition=-1;
    if(oTxt1.selectionStart){//非IE浏览器
        cursurPosition= oTxt1.selectionStart;
    }else{//IE
    	if(document.selection){
    		var range = document.selection.createRange();
            range.moveStart("character",-oTxt1.value.length);
            cursurPosition=range.text.length;
    	}else{
    		cursurPosition = 0;
    	}
        
    }
    return cursurPosition;
}
function getSelectTxt(){
	var userSelection = "";
	if (window.getSelection) {
		userSelection = window.getSelection();
	}else if (document.selection) {
		userSelection = document.selection.createRange();
	}
	return userSelection;
}

function chinese2Pinyin(cobj,pobj){
    var pinyinObj = "#"+pobj;

    var $pinyinObj = $(pinyinObj);

    var chinese = $(cobj).val();

    if($.trim(chinese).length > 0){
        if($.trim(chinese).length > 0){
            $.post('DININGSYS/chiness2ShortPinyin',{chinese:chinese},function (data) {
                $pinyinObj.val(data);
            })
        }
    }
}
