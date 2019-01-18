function checkUpdValForms(form){
		var messages = true;
		$("#"+form+" .isnumber").each(function(i,e){
			var val = $(e).val();
			var label = $(e).closest(".form-inline").find(".title").text();
			if(isNaN(Number(val))){
				layer.tips('格式不正确，必输为数字！', $(this),{
					tipsMore:true
				});
				messages = false;
			}
		});
		$("#"+form+" [dete]").each(function(i,e){
			if($(this).attr("disabled")) //disabled就跳过
			{
				return;
			}
			var date = $(e).attr("dete");
			var label = $(e).closest(".form-inline").find("."+$(e).attr("name")+"_title").text().replace("*","");
			switch(date){
				case "val" : {
					if($(e).val() == ""){
						layer.tips(label + "不能为空！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "clstype" : {
					if($(e).val() == "" || $(e).val() == '0'){ //为空或者为0
						layer.tips(label + "不能为空,请在左侧类别中选择！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
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
                }
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
                }
                    break;
				case "em-mobile" : {
					var mobile = $(e).val();
					var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
					if(!reg.test(mobile)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "em-phoneormobile" : {
					var pom = $(e).val();
					var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
					var reg2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/; //座机号验证规则
					if(!(reg.test(pom)||reg2.test(pom))){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "em-positive" : {
					var height = $(e).val();
					var reg = /^[0-9]*$/; //正整数验证规则
					if(!reg.test(height)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "em-date" : {
					var date = $(e).val();
					var reg = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/; //日期验证规则
					if(!reg.test(date)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "em-datetime" : {
					var datetime = $(e).val();
					var reg = /^\d{4}-(0\d|1[0-2])-([0-2]\d|3[01])( ([01]\d|2[0-3])\:[0-5]\d\:[0-5]\d)?$/; //日期时间验证规则
					if(!reg.test(datetime)){
						layer.tips(label + "格式不正确！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
				case "em-money" : {
					var money = $(e).val();
					var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //金钱验证规则
					if(!reg.test(money)){
						layer.tips(label + "格式不正确，最多保留两位小数！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
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
                }
                    break;
				case "pass" : {
					if($(e).val() == ""){
						layer.tips(label + "不能为空！", $(e),{
							tipsMore:true
						});
						messages = false;
					}
                }
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
                }
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
                }
                    break;
				case "discount" : { //打折验证
					var discount = $(e).val();
					var r = /^([1]?\d{1,2})$/;	//0-100的正整数  	
					if(!r.test(discount)){
						layer.tips(label + "格式不正确，必须是1-100整数", $(e),{
							tipsMore:true
						});
						
						messages = false;
					}
					else if(Number(discount) < 1 || Number(discount) >100)
					{
						layer.tips(label + "格式不正确，必须是1-100整数", $(e),{
							tipsMore:true
						});
						
						messages = false;
					}
                }
                    break;
				case "isnumber" : {
					var r = /^[1-9]*[1-9][0-9]*$/;
					var val = $(e).val();
					if(!r.test(val) && val != '0'){
						layer.tips('格式不正确，必输为正整数!', $(this),{
							tipsMore:true
						});
						messages = false;
					}
                }
                    break;
			}
		});
		return messages;
}


function checkUpdVal(e){
	var messages = true;
		var date = $(e).attr("dete");
		var label = $(e).closest(".form-inline").find("."+$(e).attr("name")+"_title").text().replace("*","");
		switch(date){
			case "val" : {
				if($(e).val() == ""){
					layer.tips(label + "不能为空！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "clstype" : {
				if($(e).val() == "" || $(e).val() == '0'){ //为空或者为0
					layer.tips(label + "不能为空,请在左侧类别中选择！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
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
            }
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
            }
                break;
			case "em-mobile" : {
				var mobile = $(e).val();
				var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
				if(!reg.test(mobile)){
					layer.tips(label + "格式不正确！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "em-phoneormobile" : {
				var pom = $(e).val();
				var reg = /^1[3|4|5|7|8][0-9]{9}$/; //手机号验证规则
				var reg2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/; //座机号验证规则
				if(!(reg.test(pom)||reg2.test(pom))){
					layer.tips(label + "格式不正确！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "em-positive" : {
				var height = $(e).val();
				var reg = /^[0-9]*$/; //正整数验证规则
				if(!reg.test(height)){
					layer.tips(label + "格式不正确！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "em-date" : {
				var date = $(e).val();
				var reg = /^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2})$/; //日期验证规则
				if(!reg.test(date)){
					layer.tips(label + "格式不正确！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "em-datetime" : {
				var datetime = $(e).val();
				var reg = /^\d{4}-(0\d|1[0-2])-([0-2]\d|3[01])( ([01]\d|2[0-3])\:[0-5]\d\:[0-5]\d)?$/; //日期时间验证规则
				if(!reg.test(datetime)){
					layer.tips(label + "格式不正确！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
			case "em-money" : {
				var money = $(e).val();
				var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //金钱验证规则
				if(!reg.test(money)){
					layer.tips(label + "格式不正确，最多保留两位小数！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
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
            }
                break;
			case "pass" : {
				if($(e).val() == ""){
					layer.tips(label + "不能为空！", $(e),{
						tipsMore:true
					});
					messages = false;
				}
            }
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
            }
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
            }
                break;
			case "discount" : { //打折验证
				var discount = $(e).val();
				var r = /^([1]?\d{1,2})$/;	//0-100的正整数  	
				if(!r.test(discount)){
					layer.tips(label + "格式不正确，必须是1-100整数", $(e),{
						tipsMore:true
					});
					
					messages = false;
				}
				else if(Number(discount) < 1 || Number(discount) >100)
				{
					layer.tips(label + "格式不正确，必须是1-100整数", $(e),{
						tipsMore:true
					});
					
					messages = false;
				}
            }
                break;
			case "isnumber" : {
				var r = /^[1-9]*[1-9][0-9]*$/;
				var val = $(e).val();
				if(!r.test(val) && val != '0'){
					layer.tips('格式不正确，必输为整数!', $(this),{
						tipsMore:true
					});
					messages = false;
				}
            }
                break;
		}
	return messages;
}