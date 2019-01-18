/**
 * Created by zhshuo on 2017-04-21.
 */
var dataUpload = function () {
    var STOPFLAF = false;

    function pageInit() {
        $("#chooseAll").click(function () {
            $("#dataUploadModelChoose").find("input[type='checkbox']").each(function () {
                $(this).prop("checked",true);
            })
        })

        $("#cancelChooseAll").click(function () {
            $("#dataUploadModelChoose").find("input[type='checkbox']").each(function () {
                $(this).prop("checked",false);
            })
        })

        $("#upload").click(function () {
            var chooseDataModel = [];
            var chooseData = $("#dataUploadModelChoose").find("input[type='checkbox']:checked");

            if(chooseData.length == 0) {
                layer.alert("没有选择上传模块!");
                return;
            }

            $(chooseData).each(function () {
                chooseDataModel.push($(this).val())
            })

            dataUpload(chooseDataModel);

        })

        function dataUpload(chooseData){
            window.parent.isDataUploading = true;
            if(chooseData.length < 1){
                messageAppend(new Date().toLocaleString()+"  所有已选模块数据已全部上传完毕","green");
                window.parent.isDataUploading = false;
                return;
            }
            if(!STOPFLAF){
                var firstEle = chooseData.shift(),eleName = returnTextByCode(firstEle);
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/system/dataUpload/dataUpload',
                    data:{dataModel:firstEle},
                    dataType:'JSON',
                    beforeSend:function (XMLHttpRequest) {
                        messageAppend(new Date().toLocaleString()+"  "+eleName+"——模块准备上传中。。。");
                        return true;
                    },
                    success:function(data){
                        if(data.success)
                        {
                            messageAppend(new Date().toLocaleString()+"  "+data['successMsg']);
                        }else{
                            messageAppend(new Date().toLocaleString()+"  "+data['errorMsg'],'red');
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        messageAppend(new Date().toLocaleString()+"  "+eleName+"——模块上传失败！");
                    }
                }).done(function () {
                    if(!STOPFLAF){
                        dataUpload(chooseData);
                    }
                })
            }else{
                messageAppend(new Date().toLocaleString()+"  已经停止数据上传！","red");
                window.parent.isDataUploading = false;
            }

        }

        $("#clearScreen").click(function () {
            $("#messageAppendDiv").html("");
        })

        $("#cancelUpload").click(function () {
            window.parent.isDataUploading = false;
        })

        function messageAppend(message,style){
            if(style)
                $("#messageAppendDiv").append("<samp style='color: "+style+"'>"+message+"</samp><br>")
            else
                $("#messageAppendDiv").append("<samp>"+message+"</samp><br>")

            $("#messageAppendDiv").scrollTop($("#messageAppendDiv")[0].scrollHeight);
        }

        function returnTextByCode(code){
           return $("#dataUploadModelChoose").find("input[type='checkbox'][value='"+code+"']").attr("msg");
        }

    }

    return {
        pageInit:function () {
            pageInit();
        }
    }

}();
