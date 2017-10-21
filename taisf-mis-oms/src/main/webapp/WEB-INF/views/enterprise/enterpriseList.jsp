<%@ page import="com.taisf.services.common.valenum.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath}">
<title>企业管理</title>
<meta name="keywords" content="企业管理">
<meta name="description" content="企业管理">
<meta http-equiv="X-UA-Compatible" content="IE=edge">  
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

<style type=text/css>
.tdfont{font-size:13px}
</style>
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox">
			<div class="ibox-content">
				<div class="row">
					<div class="form-group">
						<label class="col-sm-1 control-label mtop">企业名称:</label>
						<div class="col-sm-2">
							<input id="enterpriseName" type="text" value="" class="form-control">
						</div>
						<label class="col-sm-1 control-label mtop">开户日期:</label>
						<div class="col-sm-2">
                           <input id="openTime" name="openTime" value="" class="laydate-icon form-control layer-date">
                       	</div>
                       	<label class="col-sm-1 control-label mtop">截止日期:</label>
                       	<div class="col-sm-2">
                           <input id="tillTime" name="tillTime" value="" class="laydate-icon form-control layer-date">
                       	</div>

						<label class="col-sm-1 control-label mtop">企业类型:</label>
						<div class="col-sm-2">
							<select class="form-control" name="enterpriseType" id="enterpriseType">
								<option value="">--请选择--</option>
								<c:forEach items="<%=EnterpriseTypeEnum.values()%>" var="e" >
									<option value="${e.code}">${e.name}</option>
								</c:forEach>
                        	</select>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top:10px">
                	<div class="form-group">
						<label class="col-sm-1 control-label mtop">平台经理:</label>
	                    <div class="col-sm-2">
	                        <select class="form-control" name="manger" id="manger">
	                            <option value="">--请选择--</option>
	                            <c:forEach items="${employees}" var="emp" >
	                                <option  value="${emp.userId}">${emp.empName}</option>
	                            </c:forEach>
	                        </select>
	                    </div>
	                    <label class="col-sm-1 control-label mtop">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
						<div class="col-sm-2">
							<select class="form-control" name="productType"  id="productType">
								<option value="">--请选择--</option>
								<c:forEach items="<%=EnterpriseStatusEnum.values()%>" var="e" >
									<option value="${e.code}">${e.name}</option>
								</c:forEach>
                        	</select>
						</div>
	                    <div class="col-sm-1">
							<button class="btn btn-primary" type="button" onclick="query();">
								<i class="fa fa-search"></i>&nbsp;搜索
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Panel Other -->
		<div class="float-e-margins">
			<div class="ibox-content">
				<div class="row row-lg">
						<!-- Example Pagination -->
					<div class="col-sm-12">				
						<button id="addMenuButton" type="button" onclick="addEnterprise();" class="btn btn-primary" data-toggle="modal" data-target="#addModal">
							<i class="fa fa-plus"></i>&nbsp;添加企业
						</button>
						<table id="listTable" class="table table-bordered" data-click-to-select="true"
							data-toggle="table" data-side-pagination="server"
							data-pagination="true" data-page-list="[5,10,20,50]"
							data-pagination="true" data-page-size="10"
							data-pagination-first-text="首页" data-pagination-pre-text="上一页"
							data-pagination-next-text="下一页" data-pagination-last-text="末页"
							data-content-type="application/x-www-form-urlencoded"
							data-query-params="paginationParam" data-method="post"
							data-single-select="true"
							data-url="base/enterprise/pageList">
							<thead>																																															
								<tr>						
									<th data-field="id" data-visible="false"></th>

									<th data-field="enterpriseCode" data-width="10%"
										data-align="center"><span class="tdfont">企业编号</span></th>
									<th data-field="enterpriseName" data-width="10%"
										data-align="center" ><span class="tdfont">企业名称</span></th>
									<th data-field="enterpriseType" data-width="15%" data-formatter="formatType"
										data-align="center" ><span class="tdfont">企业类型</span></th>
									<th data-field="packing" data-width="10%"
										data-align="center" ><span class="tdfont">用餐总数</span></th>
									<th data-field="unit" data-width="10%" data-formatter="formatMeal"
										data-align="center" ><span class="tdfont">供餐信息</span></th>
									<th data-field="openTime" data-width="10%" data-formatter="formatDate"
										data-align="center" ><span class="tdfont">开户日期</span></th>
									<th data-field="tillTime" data-width="10%" data-formatter="formatDate"
										data-align="center" ><span class="tdfont">截止日期</span></th>
									<th data-field="enterpriseStatus" data-width="10%" data-formatter="formatStatus"
										data-align="center" ><span class="tdfont">状态</span></th>
									<th data-field="handle" data-width="15%" data-align="center"
										data-formatter="formatOperate"><span class="tdfont">操作</span></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/refresh.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/date.proto.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>


	<!-- Page-Level Scripts -->
	<script>
		$(function (){
			//初始化日期
			CommonUtils.datePickerFormat("openTime");
			CommonUtils.datePickerFormat("tillTime");
		});

		function paginationParam(params) {
			var openTime = $("#openTime").val();
			var tillTime = $("#tillTime").val();

			if (openTime == "") {
				openTime = undefined;
			} else {
				openTime = openTime.replace(/-/g,"/");
				openTime += " 00:00:00";
			}
			if (tillTime == "") {
				tillTime = tillTime.replace(/-/g,"/");
				tillTime = undefined;
			} else
				tillTime += " 23:59:59";

			return {
				limit : params.limit,
				page : $("#listTable").bootstrapTable("getOptions").pageNumber,
				openTime : openTime,
				tillTime : tillTime,
				enterpriseName : $("#enterpriseName").val(),
				enterpriseType : $("#enterpriseType").find("option:selected")
						.val(),
				manger : $("#manger").val()
			};
		}

		// 格式化时间
		function formatDate(value, row, index) {
			if (value != null) {
				var _date = new Date(value);
				return _date.format("yyyy-MM-dd");
			} else {
				return "-";
			}
		}
		
		// 企业类型
		function formatMeal(value, row, index) {
			if(row.forLunch == "1") {
				if(row.forDinner == "1") {
					return "午餐/晚餐";
				}
				return "午餐";
			}
			if(row.forDinner == "1") {
				return "晚餐";
			}
		}
		
		// 企业状态
		function formatStatus(value, row, index) {
			if (value == '0') {
				return "未提交";
			} else if(value=='1'){
				return "正常";
			} else if(value=='2'){
				return "已过期";
			} else if (value=='3'){
				return "停止合作";
			} else{
				return "未提交";
			} 
		}
		
		// 企业类型
		function formatType(value, row, index) {
			if (value == '1') {
				return "平台委托";
			} else if(value=='2'){
				return "自主开发";
			} else{
				return "平台委托";
			} 
		}

		// 操作列
		function formatOperate(value, row, index) {
			var result = "";
			result = result + "<a title='编辑' href=javascript:editEnterprise('"
					+ "base/enterprise/operatePage?id=" + row.id + "&operate=2')>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			result = result + "<a title='查看' href=javascript:viewEnterprise('"
					+ "base/enterprise/operatePage?id=" + row.id + "&operate=1')>查看</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			if(row.enterpriseStatus == 1) {
			result = result + "<a title='停止合作' href=javascript:stopEnterprise('" + row.id + "')>停止</a>";
			}
			return result;
		}

		function editEnterprise(url) {
			$.openNewTab(new Date().getTime(), url, "编辑企业");
		}

		function viewEnterprise(url) {
			$.openNewTab(new Date().getTime(), url, "查看企业");
		}
		
		function stopEnterprise(id) {
			var status = 3;
			
	   	    //确认是否停止合作
	   		layer.confirm("确认停止与该企业合作吗？", {icon: 5, title:'提示'},function(index){
	   			$.ajax({
	   				type: "POST",
	   				url: "base/enterprise/changeStatus",
		           	dataType:"json",
		           	traditional: true,
		           	data: {'id':id, 'enterpriseStatus':status},
		           	success: function (result) {
		        	   if(result.code === 0){
			        		$('#listTable').bootstrapTable('refresh');
	                	}else{
			        		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	                	}
		          	},
		           	error: function(result) {
		              alert("error:"+result);
		            }
			     });
	   		  
	   		  layer.close(index);
	   		});
		}

		//跳转添加企业页
		function addEnterprise() {
			var url = "base/enterprise/operatePage?operate=3";
			$.openNewTab(new Date().getTime(), url, "添加企业");
		}

		function query() {
			$("#listTable").bootstrapTable("selectPage", 1);
		}
	</script>

</body>
</html>
