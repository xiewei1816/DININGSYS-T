var ydEdit = function () {
	function pagerInit() {
		 var ydTime ={
					format:"YYYY-MM-DD hh:mm:ss",
					isinitVal:true, //显示时间
					isTime:true, 
				    festival: true, //显示节日
					minDate:getNowFormatDate("00","00","00"), //设定最小日期为当前日期
					maxDate:getNowFormatDate("23","59","59"),
					zIndex:29891015,
					choosefun: function(elem,datas){
				    }
			};
			$('#ydTime').jeDate(ydTime);
			
			$("#seatAdd").click(function(){
				if($("#ydTime").val().length == 0){
					layer.alert("请先选择时间",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					return;
				}
		        $.get(path+"/DININGSYS/yqshapi/yd/getSeatPage",function(str){
		            var addIndex = layer.open({
		                type: 1,
		                title:'客位信息',
		                skin: 'layui-layer-rim',
		                area: ['70%', '70%'],
		                content: str,
		                btn:['确定','关闭'],
		                yes:function () {
		                	var grid = $("#grid-table-seat");
		                    var selectRow = grid.getGridParam("selarrrow");
		                    var seats = [];
		                    for(var i = 0;i < selectRow.length;i++){
			                    var name = grid.jqGrid('getCell',selectRow[i],'name');
			                    var id = grid.jqGrid('getCell',selectRow[i],'id');
			                    var seat = {
			                    		id:id,
			                    		name:name
			                    };
			                    seats.push(seat);
		                    }
                            console.log(seats.length);
		                    $(".seats").empty();
		                    var seatsContent = "";
		                    for( var i = 0; i <seats.length; i++){
		                    	seatsContent += "<li>"+seats[i].name+"<input type='hidden' name='seatIds["+i+"].seatId' "+
		                    	"value='"+seats[i].id+"'/></li>";
		                    }
		                    $(".seats").html(seatsContent);
		                    layer.close(addIndex);
		                }
		            });
		        })
		    });
		    
		    
		    $("#submit").click(function(){
		    		if(!checkUpdVals("editForm")){
            			return;
            		}
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/yqshapi/yd/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
								layer.alert('提交预定信息成功!',{title :'提示',icon: 6, skin: 'layer-ext-moon'}, function(){
								  window.location.href=path+'/DININGSYS/yqshapi/yd/getYdManager?posId='+$("#pId").val();
								});
                            }else {
                            	 layer.alert(data.msg,{title :'提示',icon: 5, skin: 'layer-ext-moon'});
                            }
                        }
                    });
            });
            
            
            function getNowFormatDate(hour,mintes,seconds) {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
			            + " " + hour + seperator2 + mintes
			            + seperator2 + seconds;
			    return currentdate;
			} 
    }
	return {
 		pagerInit:function (){
 			pagerInit();
 		}
	}
}();