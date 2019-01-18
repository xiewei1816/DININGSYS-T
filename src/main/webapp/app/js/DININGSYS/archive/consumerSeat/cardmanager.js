var infoView;
$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

	var consumerid = $("#consumerid").val();

	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/consumerSeat/getCards?consumerid="+consumerid,
		colM : "cardnum",
		queryForm : "queryForm",
		colNames : "台卡号",
		colWid : {"id":40},
		loadComplete : function() {
		}
	});


    /* ******************************  新增台卡  **************************** */
    $("#add").click(function () {
        $.get('DININGSYS/consumerSeat/toAddCardView?id='+consumerid, function (dataStr) {
            var w = $(document.body).width() * 0.6 + 'px';
            var h = $(document.body).height() * 0.6 + 'px';
            infoView = layer.open({
                type: 1,
                title: "添加",
                skin: 'layui-layer-rim',
                area: [w, h],
                content: dataStr,
                btn: ['保存', '关闭'],
                yes: function () {
					callBack();
                }
            });
        });
    });



	$("#delete").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
        if(rowids.length > 1){
            layer.alert('只能选择一条数据',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var rowId = $("#" + pageUtil.tabId).jqGrid('getGridParam','selrow');
		layer.confirm('请注意,该功能删除的数据不可逆.你确定吗?', {
			icon : 0,
			btn : ['确定','取消'] //按钮
		}, function(index){
			jQuery.ajax({
				url : 'DININGSYS/consumerSeat/deleteCards',
				data : {"id":rowId + ""},
				type : "POST",
				dataType:"json",
				error : function(request) {
					layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				},
				success : function(data) {
					if(data.success == 'OK'){
						layer.close(index);
						$("#" + pageUtil.tabId).trigger("reloadGrid");
					}
				}
			});
		}, function(){

		});
	});

});

function callBack() {
    var num = $("#cardnum").val();
    if(num == null || num == ''){
        layer.alert("卡号不能为空");
        return;
    }
    var param = $("#myfrom").serialize();
    $.ajax({
        type: 'POST',
        url: 'DININGSYS/consumerSeat/saveCard',
        dataType: 'JSON',
        data: param,
        success: function (data) {
            if (data.success == 'OK' ) {
                layer.close(infoView);
                $("#grid-table").trigger("reloadGrid");
            }else if(data.success == "false"){
                layer.alert(data.error,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            }
        }
    })
}