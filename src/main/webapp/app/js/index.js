/**
 * 初始化系统提示弹出层
 *
 * example
 *
 * showAjaxMsg("提示的信息","是否有确认按钮",function(){ 操作代码  closeAjaxShowMsg();},true)
 *
 *
 * showAjaxMsg("修改失败，请重新操作。",true,function(){
 *               alert("测试");
 *              closeAjaxShowMsg();
 *         });
 *
 *
 */
showAjaxMsg = function (msg,isShowSure,callBack,isHideClose) {
    $("#ajaxMessageShowPID").text(msg);
    if(isShowSure){
        $("#ajaxMessageShowSureBtn").show();
        $("#ajaxMessageShowSureBtn").bind('click',callBack);
    }else{
        $("#ajaxMessageShowSureBtn").hide();
    }
    if(isHideClose){
        $("#ajaxMessageShowCloseBtn").hide();
    }else{
        $("#ajaxMessageShowCloseBtn").show();
    }
    $("#ajaxShowBtnID").trigger("click");
};
closeAjaxShowMsg = function () {
    $("#ajaxMessageShow").modal('hide');
    $("#ajaxMessageShowSureBtn").unbind('click');
};
initAjaxMsgModal = function (){
    $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
        '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
        '<div class="progress progress-striped active">' +
        '<div class="progress-bar" style="width: 100%;"></div>' +
        '</div>' +
        '</div>';

    $.fn.modalmanager.defaults.resize = true;

    var $modal = $('#user_ajax_modal');


    $modal.on('click', '.update', function(){
        $modal.modal('loading');
        setTimeout(function(){
            $modal
                .modal('loading')
                .find('.modal-body')
                .prepend('<div class="alert alert-info fade in">' +
                    'Updated!<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '</div>');
        }, 1000);
    });
};
initAjaxShowCloseEvent = function(){
    $(".ajaxShowClose").bind('click',function () {
        closeAjaxShowMsg();
    })
};

/*关闭modal的方法，传入modal 的ID即可*/
closeModal = function(modal){
    var modal = "#"+modal;
    $(modal).modal("hide");
};

sendFile = function(obj,file) {
    var data = new FormData();
    data.append("file", file);
    $.ajax({
        data: data,
        type: "POST",
        url: "DININGSYS/summernoteFileUpload",
        dataType:'JSON',
        cache: false,
        contentType: false,
        processData: false,
        success: function(url) {
            obj = "#"+$(obj).attr("id");
            url = eval("("+url+")");
            $(obj).summernote('insertImage', url.path);
        }
    });
};

scrollTo = function (el, offeset) {
    pos = (el && el.size() > 0) ? el.offset().top : 0;
    jQuery('html,body').animate({
        scrollTop: pos + (offeset ? offeset : 0)
    }, 'slow');
},

$(function() {
    App.init();
    initAjaxMsgModal();
    initAjaxShowCloseEvent();
});