$(function () {
	$("#save").click(function(){

    	 layer.confirm('确认保存品项价格优先级?', {icon: 3, title:'提示'}, function(index){
			var content = '[';
			$.each($("ul li input:checkbox"),function(){
				content +="{\"id\":\""+$(this).attr('name')+"\",\"enable\":\""+$(this).val()+"\"},";
				
			});
	    	{
	    		content = content.substring(0,content.length-1);	
	    	}
	    	content += "]";
            $.ajax({
                     type:'POST',
                     url:path+'/DININGSYS/ProPricePriority/updata',
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
	
	$("#mo").click(function(){
        $.ajax({
            type:'POST',
            url:path+'/DININGSYS/ProPricePriority/mo',
            dataType:'JSON',
            success:function (data) {
                if(data.success){
                	$(".left-content").empty();
                	var newHtml ='';
                	for(var i=0;i<data.list.length;i++)
                	{
                		if(data.list[i].enable == '1')
                		{
                			
                    		newHtml+='<li move="0">'+
  						  '<div class="left-5">'+
  					      '<input type="checkbox"  name="'+data.list[i].id+'" checked>'+ 
  					      '<a onclick="aClick(this);" class="m-a">'+" "+data.list[i].name+'</a>'+
  					      '</div></li>';
                		}
                		else
                		{
                			newHtml+='<li move="0">'+
    						  '<div class="left-5">'+
    					      '<input type="checkbox"  name="'+data.list[i].id+'">'+ 
    					      '<a onclick="aClick(this);" class="m-a">'+" "+data.list[i].name+'</a>'+
    					      '</div></li>';
                		}
                	}
                	
                	$(".left-content").html(newHtml);
                	checkboxInit();
                }
            }
        });
        layer.close(index);
   });
   checkboxInit();
	
});

function checkboxInit()
{
	 /**
	初始化所有checkbox
     **/  
	$('input:checkbox').each(function() {
	if ($(this).prop("checked") == true) {
		$(this).val('1');
	}
	else
	{
		$(this).val('0');
	}
	});
    $('input:checkbox').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$("input:checkbox").change(function() {
		if ($(this).prop("checked") == true) {
			$(this).val('1');
		}
		else
		{
			$(this).val('0');
		}
	});	
}

function up()
{
	$.each($("a"),function(){ 
		  var onthis=$(this).parent().parent();
		  if(onthis.attr('move') == '1')
		  {
			  var getUp=onthis.prev(); 
			  if(getUp.has('a').length == 0 )
			  {
			  	return;
			  }
			  getUp.before(onthis); 
		  }
	 }); 
} 

function aClick(obj)
{
	var nthis = $(obj);
	var zParent = nthis.parent().parent();
	zParent.prevAll().find('a').css("background","#ffffff");
	zParent.prevAll().find('a').css("color","#000000");
	
	zParent.nextAll().find('a').css("background","#ffffff");
	zParent.nextAll().find('a').css("color","#000000");
	
	zParent.prevAll().attr("move",'0');
	zParent.nextAll().attr("move",'0');
	zParent.attr("move",'1');
	nthis.css("background","#0061ff");
	nthis.css("color","#ffffff");
}
function down(){ 
	 $.each($("a"),function(){ 
		  var onthis=$(this).parent().parent(); 
		  if(onthis.attr('move') == '1')
		  {
			  var getdown=onthis.next(); 
			  getdown.after(onthis); 
		  }
	 }); 
}