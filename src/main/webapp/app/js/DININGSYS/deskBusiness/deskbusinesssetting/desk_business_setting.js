/**
 * Created by zhshuo on 2016-11-04.
 */
var dbs_set = function () {

    var busi_per_tree;

    function tabFlush() {
        var currentLi = $("#myTab").find("li").filter(".active");
        currentLi.trigger("click");
    }

    function pageInit() {

        //加载第一项
        $.get('DININGSYS/deskbusinesssetting/busPage/seatserv',function (pageStr) {
            $("#dbscontent").html(pageStr);
        });
        //tab点击事件
        //直接点击tab或者点击树的职务，tab刷新函数
        $("#myTab li").click(function () {
            var liObjId = $(this).attr("id"),
                ajaxUrl = "DININGSYS/deskbusinesssetting/busPage/",
                contenObj = "#dbscontent";

            if(liObjId == "seatserv"){
                ajaxUrl += "seatserv";
            }else if(liObjId == "billserv"){
                ajaxUrl += "billserv";
            }else if(liObjId == "printersetting"){
                ajaxUrl += "printersetting";
            }else if(liObjId == "serialrul"){
                ajaxUrl += "serialrul";
            }else{
                ajaxUrl += "loungesetting";
            }

            $.get(ajaxUrl,function (pageStr) {
                $(contenObj).empty();
                $(contenObj).html(pageStr);
            })
        })
    }


    function saveInfoBtnInit() {
        $(".saveInfo").click(function () {
            var objId = $(this).attr("id");

            //客位设置保存
            if(objId == "seatservBtn"){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskbusinesssetting/editSeatservData',
                    data:$("#seatServForm").serialize(),
                    dataType:'JSON',
                    async:false,
                    success:function(data){
                        if(data.success){
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            }else if(objId == "billservBtn"){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskbusinesssetting/editBillservData',
                    data:$("#billservForm").serialize(),
                    async:false,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            } else if(objId == "pringterBtn"){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskbusinesssetting/editPringterData',
                    data:$("#pringterForm").serialize(),
                    async:false,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            } else if(objId == "serialRulBtn"){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskbusinesssetting/editSerialRulData',
                    data:$("#serialRulForm").serialize(),
                    async:false,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            } else if(objId == "loungeSettingBtn"){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskbusinesssetting/editLoungeSettingData',
                    data:$("#loungeSettingForm").serialize(),
                    async:false,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            }
        })
    }

    function tableSaveAjax(url,data) {
        $.ajax({
            type:'POST',
            url:url,
            data:data,
            dataType:'JSON',
            async:false,
            success:function(data){
                if(data.success){
                    //updateNodeName();
                    layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    tabFlush();
                }else{
                    layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                }
            }
        })
    }


    function itemCheckboxInit() {
        var radioObj = "input[type='checkbox']";
        $(radioObj).click(function () {
            if($(this).is(":checked")){
                $(this).val('1');
            }else{
                $(this).val('0');
            }
        });
        $(radioObj).each(function () {
            if($(this).is(":checked")){
                $(this).val('1');
            }else{
                $(this).val('0');
            }
        })
    }

    return {
        //首页页面初始化
        pageInit:function() {
            pageInit();
        },
        itemCheckboxInit:function(){
            itemCheckboxInit();
        },
        //保存按钮事件
        saveInfoBtnInit:function () {
            saveInfoBtnInit();
        }
    }

}();