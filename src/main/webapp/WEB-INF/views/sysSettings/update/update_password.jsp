<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>营业市别档案管理</title>
		<jsp:include page="../../head.jsp"/>
		<script src="app/lib/security/sha256.js" type="text/javascript"></script>
	</head>
<style>
.content{position: absolute; top: 50%; left: 50%; width: 370px; height: 300px;  margin-left: -185px; margin-top: -150px; }
.input-text{width: 370px;height: 32px;border: #dddddd 1px solid;padding-left: 15px;line-height: 32px;color:#b1a9a9;  margin-bottom:10px; background-color: #FAFFBD;}
.btn-update{width: 370px;height: 33px;background:#37b5f9; font-size:15px; line-height:33px; text-align:center; border:0px; color:#fff; border-radius:3px; margin-top: 20px;cursor:pointer;}
</style>
<body>
	<div class="content">
	    <form class="update-form">
	    	<div>
	    		<input type="text" name="old_password" placeholder="原密码" class="input-text old_password" autocomplete="off" >
	    	</div>
	    	<div>
	    		<input type="password" name="new1_password" placeholder="新密码" class="input-text new1_password" autocomplete="off">
	    	</div>
	    	<div>
	    		<input type="password" name="new2_password" placeholder="确认密码" class="input-text new2_password" autocomplete="off">
	    	</div>
	    	<div class="btn-update">修改</div>
	    </form>
	</div>
	<script>
	$(function(){
		$(".btn-update").click(function(){
			var old =$('.old_password').val();
			var pass1 = $('.new1_password').val();
			var pass2 = $('.new2_password').val();
			
			if(checkFormEmpty(old,pass1,pass2)){
				if(checkFormLength(old,pass1,pass2)){
					if(checkFormSame(pass1,pass2)){
						/* $('.old_password').val(sha256_digest($('.old_password').val()));
						$('.new1_password').val(sha256_digest($('.new1_password').val()));
						$('.new2_password').val(sha256_digest($('.new2_password').val())); */
						var data =  'old_password='+sha256_digest($('.old_password').val())+'&new1_password='+sha256_digest($('.new1_password').val())+'&new2_password='+sha256_digest($('.new2_password').val());
						$.ajax({
				            type: "POST",
				            url: "DININGSYS/sysconfig/updatePassword",
				            data: data,
				            dataType:'json',
				            async: true,
				            success:function(data){
				            	if(data.success){
					                layer.alert('密码修改成功!',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
					                $('.old_password').val('');
									$('.new1_password').val('');
									$('.new2_password').val('');
				            	}else{
				            		layer.tips('原密码不正确!', $(".old_password") , {guide: 1, tipsMore:true, time: 2000}); 
				            	}
				            },
				            error:function (data, status, e){
				            	layer.alert('请求失败！',{title :'提示',icon: 2, skin: 'layer-ext-moon'});
				            }
				        }); 
					}
				}
			}
		});
		
	});
	
	/**
	 * 表单验证非空
	 */
    function checkFormEmpty(old,pass1,pass2){
    	var flag = true;
    	if(old == ""){
    		layer.tips('原密码不能为空!', $(".old_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	if(pass1 == ""){
    		layer.tips('新密码不能为空!', $(".new1_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	if(pass2 == ""){
    		layer.tips('确认密码不能为空!', $(".new2_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	return flag;
    }
	
	/**
	 * 表单验证字符长度
	 */
    function checkFormLength(old,pass1,pass2){
    	var flag = true;
    	if(old.length < 6){
    		layer.tips('原密码长度不够!', $(".old_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	if(pass1.length < 6){
    		layer.tips('新密码长度不够!', $(".new1_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	if(pass2.length < 6){
    		layer.tips('确认密码长度不够!', $(".new2_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	return flag;
    }
	
	/**
	 * 表单验证新密码与确认密码是否一致
	 */
    function checkFormSame(pass1,pass2){
    	var flag = true;
    	if(pass1 != pass2){
    		layer.tips('两次密码不一致!', $(".new2_password") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	return flag;
    }
	</script>
<body>
</html>
