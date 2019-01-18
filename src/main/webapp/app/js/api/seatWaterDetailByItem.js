var seatWaterDetail = function() {
	function pagerInit() {
		var seatId = $("#seatId").val();
		var byItem = $("#byItem");
		var byService = $("#byService");
		var byBigItem = $("#byBigItem");
		var byServiceContain = $("#byServiceContain");
		$(".nav-tabs li a").click(function() {
			$(this).parent().addClass("active");
			$(this).parent().siblings().removeClass("active");

			var tabId = $(this).attr("id");
			var index = tabId.split("-")[1];
			for (var i = 0; i < $(".nav-tabs li").length; i++) {
				$(".content-" + i).hide();
			}
			$(".content-" + index).show();
		});
		$("input:radio").click(function() {
			if ($(this).val() == 1) {
				byItem.show();
				byService.hide();
				byBigItem.hide();
				byServiceContain.hide();
			} else if ($(this).val() == 2) {
				byItem.hide();
				byService.hide();
				byBigItem.show();
				byServiceContain.hide();
			} else if ($(this).val() == 3) {
				byItem.hide();
				byService.show();
				byBigItem.hide();
				byServiceContain.hide();
			} else if ($(this).val() == 4) {
				byItem.hide();
				byService.hide();
				byBigItem.hide();
				byServiceContain.show();
			}
		});
		
		$.ajax({
             type:'POST',
             url:'getOpenWaterDetailsAjax?seatId='+seatId,
             dataType:'JSON',
             beforeSend : function(){
             },
             success:function (d) {
            	 if(d != null)
            	 {
            		 for(var i in d.subVal)
            		 {
            			 var content = "";
            			 for(var c in d.subVal[i].list)
            			 {
            				 content += "<tr style='border-bottom: dotted 1px #ff0000;'>";
            				 content += "<td>"+d.subVal[i].list[c].itemNum +"</td>";
            				 content += "<td>"+d.subVal[i].list[c].itemName +"</td>";
            				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].unit +"</td>";
            				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].item_final_price +"</td>";
            				 content += "<td style='padding-right:10px;'>"+d.subVal[i].list[c].notes +"</td>";
            				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].item_file_number +"</td>";
            				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].subtotal +"</td>";
            				 content += "</tr>";
            				 for(var t in d.subVal[i].list[c].tcDetail)
            				 {
                				 content += "<tr style='border-bottom: dotted 0.5px #660000;'>";
                				 content += "<td>"+d.subVal[i].list[c].tcDetail[t].itemNum +"</td>";
                				 content += "<td>"+">>"+d.subVal[i].list[c].tcDetail[t].itemName +"</td>";
                				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].tcDetail[t].unit +"</td>";
                				 content += "<td style='text-align: center;'>0.00</td>";
                				 content += "<td style='padding-right:10px;'>"+d.subVal[i].list[c].tcDetail[t].notes+"</td>";
                				 content += "<td style='text-align: center;'>"+d.subVal[i].list[c].tcDetail[t].item_file_number +"</td>";
                				 content += "<td style='text-align: center;'>0.00</td>";
                				 content += "</tr>";
            				 }
            			 }
            			 $("#byService .content-"+i+" .tab-head").after(content)
            		 }
            		 
            		 for(var i in d.serviceVal)
            		 {
            			 var content = "";
            			 for(var c in d.serviceVal[i].list)
            			 {
            				 content += "<tr style='border-bottom: dotted 1px #ff0000;'>";
            				 content += "<td>"+d.serviceVal[i].list[c].itemNum +"</td>";
            				 content += "<td>"+d.serviceVal[i].list[c].itemName +"</td>";
            				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].unit +"</td>";
            				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].item_final_price +"</td>";
            				 content += "<td style='padding-right:10px;'>"+d.serviceVal[i].list[c].notes +"</td>";
            				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].item_file_number +"</td>";
            				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].subtotal +"</td>";
            				 content += "</tr>";
            				 for(var t in d.serviceVal[i].list[c].tcDetail)
            				 {
                				 content += "<tr style='border-bottom: dotted 0.5px #660000;'>";
                				 content += "<td>"+d.serviceVal[i].list[c].tcDetail[t].itemNum +"</td>";
                				 content += "<td>"+">>"+d.serviceVal[i].list[c].tcDetail[t].itemName +"</td>";
                				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].tcDetail[t].unit +"</td>";
                				 content += "<td style='text-align: center;'>0.00</td>";
                				 content += "<td style='padding-right:10px;'>"+d.serviceVal[i].list[c].tcDetail[t].notes+"</td>";
                				 content += "<td style='text-align: center;'>"+d.serviceVal[i].list[c].tcDetail[t].item_file_number +"</td>";
                				 content += "<td style='text-align: center;'>0.00</td>";
                				 content += "</tr>";
            				 }
            			 }
            			 $("#byServiceContain .content-"+i+" .tab-head").after(content);
            		 }
            		 
            		 for(var i in d.bigVal)
            		 {
            			 var content = ""; 
            			 for(var c in d.bigVal[i].details)
            			 {
            				 
            				 for(var l in d.bigVal[i].details[c].child)
            				 {
                				 content += "<tr style='border-bottom: dotted 1px #ff0000;'>";
                				 content += "<td>"+d.bigVal[i].details[c].child[l].itemNum +"</td>";
                				 content += "<td>"+d.bigVal[i].details[c].child[l].itemName +"</td>";
                				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].unit +"</td>";
                				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].item_final_price +"</td>";
                				 content += "<td style='padding-right:10px;'>"+d.bigVal[i].details[c].child[l].notes +"</td>";
                				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].item_file_number +"</td>";
                				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].subtotal +"</td>";
                				 content += "</tr>";
                				 for(var t in d.bigVal[i].details[c].child[l].tcDetail)
                				 {
                    				 content += "<tr style='border-bottom: dotted 0.5px #660000;'>";
                    				 content += "<td>"+d.bigVal[i].details[c].child[l].tcDetail[t].itemNum +"</td>";
                    				 content += "<td>"+">>"+d.bigVal[i].details[c].child[l].tcDetail[t].itemName +"</td>";
                    				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].tcDetail[t].unit +"</td>";
                    				 content += "<td style='text-align: center;'>0.00</td>";
                    				 content += "<td style='padding-right:10px;'>"+d.bigVal[i].details[c].child[l].tcDetail[t].notes+"</td>";
                    				 content += "<td style='text-align: center;'>"+d.bigVal[i].details[c].child[l].tcDetail[t].item_file_number +"</td>";
                    				 content += "<td style='text-align: center;'>0.00</td>";
                    				 content += "</tr>";
                				 }
            				 }
            				 content += "<tr style='border-bottom: dotted 1px #ff0000;'>";
            				 content += "<td class='big-size'>"+d.bigVal[i].details[c].bNum+"-"+
            				 		d.bigVal[i].details[c].bName+"</td>";
            				 content += "<td></td><td></td><td></td><td></td>";
            				 content += "<td></td>";
            				 content += "<td>"+d.bigVal[i].details[c].subtotal+"</td>";
            				 content += "</tr>";
            			 }
            			 $("#byBigItem .content-"+i+" .tab-head").after(content);
            		 }
            	 }
             }
         });
	}
	return {
		pagerInit : function() {
			pagerInit();
		}
	}

}();