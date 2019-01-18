var itemFileColorManager = function () {
	
	function pageInit() {
	    $("#grid-table-color").jqGrid({
	        url: "DININGSYS/archive/itemFile/getColorDate",
	        datatype: "JSON",
	        mtype: "GET",
	        rowNum:-1,
	        autowidth:true,
	        rownumbers:true,//添加左侧行号
	        colNames: ["id","颜色名称"],
	        colModel: [
	            { name: "id",hidden:true},
	            { name: "name"}
	        ],
	        autoencode: true,
	        multiselect:true,
	        styleUI : 'Bootstrap'
   	    });
	 }
	 return {
        /*首页树 初始化*/
        pageInit:function () {
            pageInit();
        }
	 }
}();