var checkId;
var addmIndex;
var radioValue;
$(function () {
	
	$("#save").click(function(){

    	 layer.confirm('确认保存当前数据?', {icon: 3, title:'提示'}, function(index){
			var content = '';
			content +="{\"serviceItemId\":\""+$("#serviceItemId").val()+"\",\"serviceItemCode\":\""+$("#serviceItemCode").val()+"\",\"serviceItemName\":\""+$("#serviceItemName").val()+"\",";
			content +="\"minConsumptionItemId\":\""+$("#minConsumptionItemId").val()+"\",\"minConsumptionItemCode\":\""+$("#minConsumptionItemCode").val()+"\",";
			content +="\"minConsumptionItemName\":\""+$("#minConsumptionItemName").val()+"\",\"minConsumeType\":\""+radioValue+"\"}";
            $.ajax({
                     type:'POST',
                     url:path+'/DININGSYS/DgSpecialItem/save',
                     data:{data:content},
                     dataType:'JSON',
                     success:function (data) {
                         if(data.success){
                         	 
                         }
                     }
                 });
                 layer.close(index);
            });
    });
	
    $("#add-m1").click(function(){
    	checkId = 1;
    	$.get(path+"/DININGSYS/DgItemGiftPlan/addHostItem",function(str){
    		addmIndex= layer.open({
                type: 1,
                title:'促销品项方案-品项选择窗口',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str
            });
        })
    });
    
    
    $("#sub-m1").click(function(){
    	/**
    	 * 都设置为null
    	 */
	   	 $("#serviceItemId").val("");
		 $("#serviceItemCode").val("");
		 $("#serviceItemName").val("");
    });
    
    
    $("#add-m2").click(function(){
    	checkId = 2;
    	$.get(path+"/DININGSYS/DgItemGiftPlan/addHostItem",function(str){
    		addmIndex= layer.open({
                type: 1,
                title:'促销品项方案-品项选择窗口',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str
            });
        })
    });
    
    
    $("#sub-m2").click(function(){
    	/**
    	 * 都设置为null
    	 */
	   	 $("#minConsumptionItemId").val("");
		 $("#minConsumptionItemCode").val("");
		 $("#minConsumptionItemName").val("");
    });
	radioInit();
	
});



function callBack(id,code,name)
{
	layer.close(addmIndex); //关闭窗口
	if(checkId == 1)
	{
		 $("#serviceItemName").val(name);
		 $("#serviceItemCode").val(code);
		 $("#serviceItemId").val(id);
	}
	else
	{
		 $("#minConsumptionItemName").val(name);
		 $("#minConsumptionItemCode").val(code);
		 $("#minConsumptionItemId").val(id);
	}
}


function radioInit()
{
	 /**
	初始化所有radio
     **/  
	$('input:radio').each(function() {
	if ($(this).prop("checked") == true) {
		radioValue = $(this).val();
	}
	});
    $('input:radio').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$("input:radio").change(function() {
		if ($(this).prop("checked") == true) {
			radioValue = $(this).val();
		}
	});	
}