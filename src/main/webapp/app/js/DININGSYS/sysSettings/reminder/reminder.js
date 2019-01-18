$(function () {
    /*品项打折方案有效期*/
    $("#grid-table-item-discount").jqGrid({
        url: "DININGSYS/reminder/itemDiscountPan",
        datatype: "JSON",
        mtype: "GET",
        rowNum:-1,
        autowidth:true,
        rownumbers:true,//添加左侧行号
        colNames: ["id","提醒内容"],
        colModel: [
            { name: "id",width:30},
            { name: "reminder"},
        ],
        autoencode: true,
        styleUI : 'Bootstrap'
    });
    
    /*品项打折方案有效期*/
    $("#grid-table-pro-discount").jqGrid({
        url: "DININGSYS/reminder/giftDiscountPan",
        datatype: "JSON",
        mtype: "GET",
        rowNum:-1,
        autowidth:true,
        rownumbers:true,//添加左侧行号
        colNames: ["id","提醒内容"],
        colModel: [
            { name: "id",width:30},
            { name: "reminder"},
        ],
        autoencode: true,
        styleUI : 'Bootstrap'
    });
    
    
    
    /*品项打折方案有效期*/
    $("#grid-table-member-discount").jqGrid({
        url: "DININGSYS/reminder/memberDiscountPan",
        datatype: "JSON",
        mtype: "GET",
        rowNum:-1,
        autowidth:true,
        rownumbers:true,//添加左侧行号
        colNames: ["id","提醒内容"],
        colModel: [
            { name: "id",width:30},
            { name: "reminder"},
        ],
        autoencode: true,
        styleUI : 'Bootstrap'
    });
    
    /*品项打折方案有效期*/
    $("#grid-table-important-discount").jqGrid({
        url: "DININGSYS/reminder/importtantActiDiscountPan",
        datatype: "JSON",
        mtype: "GET",
        rowNum:-1,
        autowidth:true,
        rownumbers:true,//添加左侧行号
        colNames: ["id","提醒内容"],
        colModel: [
            { name: "id",width:30},
            { name: "reminder"},
        ],
        autoencode: true,
        styleUI : 'Bootstrap'
    });
    
    
    $(window).resize(function(){
    	$("#grid-table-item-discount").setGridWidth($(window).width()*0.5-40);
    	$("#grid-table-important-discount").setGridWidth(document.body.clientWidth*0.5-40);
    	$("#grid-table-member-discount").setGridWidth($(window).width()*0.5-40);
    	$("#grid-table-pro-discount").setGridWidth(document.body.clientWidth*0.5-40);
    });
	    
});