/**
 * Created by mrren on 2016-08-17.
 */
var personalCenter = function () {

    var updateUserBaseInfo = function () {
        
        if($("#userBaseInfo").valid()){
	        $.ajax({
	            type:'POST',
	            url:'DININGSYS/personalCenter/updateUserBaseInfo',
	            data:$("#userBaseInfo").serialize(),
	            dataType:'JSON',
	            success:function (data) {
	                if(data.success){
	                    showAjaxMsg("操作成功！")
	                }else{
	                    showAjaxMsg("操作失败！")
	                }
	            }
	        })
        }
    };

    var checkCurrentPwd = function () {
        $(".currentPwd").on("blur",function () {
            if(jQuery.trim($(this).val() != "")){
                var checkPwd = sha256_digest($(this).val());
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/personalCenter/checkOldPwd',
                    data:{oldPwd:checkPwd},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            $(".oldPwdDiv").removeClass("has-error");
                            $(".oldPwdDiv").toggleClass("has-success");
                        }else{
                            $(".oldPwdDiv").removeClass("has-success");
                            $(".oldPwdDiv").addClass("has-error");
                        }
                    }
                })
            }
        })
    };

    var modifyPwd = function () {
        $(".modifyPwd").on("click",function () {
            var newPwd = sha256_digest($(".reNewPwd").val());
            if($(".oldPwdDiv").hasClass("has-error")){
                showAjaxMsg("您的当前密码输入错误！");
                return;
            }
            if($.trim($(".reNewPwd").val()) != $.trim($(".newPwd").val())){
                showAjaxMsg("两次输入的新密码不一致");
                return;
            }
            if($("#modifyPwd").valid()){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/personalCenter/updatePwd',
                    data:{newPwd:newPwd},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            showAjaxMsg("操作成功！");
                            $("#modifyPwd")[0].reset();
                        }else{
                            showAjaxMsg("操作失败！")
                        }
                    }
                })
            }
        })
    };

    return {
        init: function () {
            checkCurrentPwd();
            modifyPwd();
        },
        updateUserBaseInfo:function () {
            updateUserBaseInfo();
        }
    };
}();