<%--
  Created by zhshuo.
  Date: 2017-02-08
  Time: 13:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>就餐人数情况-时段</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/numberOfMeals/numberOfMeals.js"></script>
    <script>
        $(document).ready(function () {
            numberOfMeals.pageInit();
        });
    </script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
            	<li id="dateFastSel" ></li>
                <li>
                    <span class="title">时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="startTime" name="startTime" placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">至</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间" readonly>
                    </div>
                </li>
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
				<input class="btn btn-success" id="doSearch" type="button" value="查询">
			</div>
        </form>
    </div>
    <div class="btn-toolbar">
        <span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
        <span id="refresh" class="greenbtn"><i class="fa fa-refresh"></i>刷新</span>
        <span id="print" class="graybtn"><i class="fa fa-print"></i>打印</span>
    </div>
    <div class="col-md-12">
        <div class="col-md-12" id="tableDiv">
            <table id="numberOfMeals"></table>
        </div>
        <%--<div class="col-md-8" id="chartsDiv">
            <div id="main" style="width: 600px;height:400px;"></div>
            <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['利润', '支出', '收入']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'value'
                    }
                ],
                yAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        data : ['周一','周二','周三','周四','周五','周六','周日']
                    }
                ],
                series : [
                    {
                        name:'利润',
                        type:'bar',
                        label: {
                            normal: {
                                show: true,
                                position: 'inside'
                            }
                        },
                        data:[200, 170, 240, 244, 200, 220, 210]
                    },
                    {
                        name:'收入',
                        type:'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true
                            }
                        },
                        data:[320, 302, 341, 374, 390, 450, 420]
                    },
                    {
                        name:'支出',
                        type:'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                position: 'left'
                            }
                        },
                        data:[-120, -132, -101, -134, -190, -230, -210]
                    }
                ]
            };


            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            </script>
        </div>--%>
    </div>
</div>
</body>
</html>