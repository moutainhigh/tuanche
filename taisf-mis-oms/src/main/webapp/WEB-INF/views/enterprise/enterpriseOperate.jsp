<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
	<title>企业操作页</title>
	<meta name="keywords" content="新增企业  编辑企业">
	<meta name="description" content="新增企业  编辑企业">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit|ie-comp|ie-stand" />
	<link rel="${staticIconUrl}/shortcut icon" href="${staticIconUrl}/favicon.ico">
	<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style type=text/css>
		.tdfont{font-size:13px}
		.table>tbody>tr>td {
			border: 1px solid white
		}
	</style>
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/refresh.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/date.proto.js${VERSION}"></script>
	<!-- iCheck -->
	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/geography.cascade.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/jquery.form.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>

	<script type="text/javascript" src="${staticResourceUrl}/js/printArea.js${VERSION}001"></script>
	<!-- Page-Level Scripts -->
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="float-e-margins">
			<div class="ibox-title">
				<c:if test="${operate==3}">
					<h5>添加企业</h5>
				</c:if>
				<c:if test="${operate==2}">
					<h5>编辑企业</h5>
				</c:if>
				<c:if test="${operate==1}">
					<h5>查看企业</h5>
				</c:if>
			</div>
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<label style="font-size:13px;color:#1bb394">基本信息</label>
						<table class="table">
							<tr>
								<td align="right" style="font-weight:bold;font-size:13px">供餐信息:</td>
								<td> <select class="form-control" id="province" name="provinceCode" >  
				                        <option value="">-请选择省-</option>  
				                    </select></td>
							</tr>
							<tr>
								<td align="right" style="font-weight:bold;font-size:13px">企业编号:</td>
								<td align="center"><input type="text" id="enterpriseCode" name="enterpriseCode" class="form-control"
									<c:if test="${operate==1}">readonly="true"</c:if> value="${enterprise.enterpriseCode}"></td>
								<td align="right" style="font-weight:bold;font-size:13px">企业名称:</td>
								<td align="center"><input type="text" id="enterpriseName" name="enterpriseName" class="form-control"
									<c:if test="${operate==1}">readonly="true"</c:if> value="${enterprise.enterpriseName}"></td>
								<td align="right" style="font-weight:bold;">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话:</td>
								<td align="center"><input type="text" id="enterpriseTel" name="enterpriseTel" class="form-control"
									<c:if test="${operate==1}">readonly="true"</c:if> value="${enterprise.enterpriseTel}"></td>
							</tr>
							<tr>
								<td align="right" style="font-weight:bold;font-size:13px">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</td>
								<td colspan="5">
									 <select class="form-control" style="width:150px;float:left;margin-right:8px;" id="province" name="provinceCode" onchange="selectCitys(this)" >  
				                        <option value="">-请选择省-</option>  
				                        <c:forEach var="pv" items="${provinceList}">  
				                            <option  value="${pv.code}">${pv.name}</option>
				                        </c:forEach>  
				                    </select>
				                    <select class="form-control" style="width:150px;float:left;margin-right:8px;" id="city" name="cityCode" onchange="selectAreas(this)">  
				                          <option value="">-请选择市-</option>
				                        <c:if test="${ not empty citylist}" > 
				                        <c:forEach var="pv" items="${citylist}">  
				                            <option  value="${pv.code}">${pv.name}</option>
				                        </c:forEach> 
				                         </c:if>
				                    </select>
				                    <select class="form-control" style="width:150px;float:left;margin-right:8px;" id="area" name="countyCode"> 
				                         <option value="">-请选择区\县-</option>
				                        <c:if test="${ not empty countylist}" > 
				                        <c:forEach var="pv" items="${countylist}">  
				                            <option  value="${pv.code}">${pv.name}</option>
				                        </c:forEach>  
				                        </c:if>
				                    </select>
				                    <input type="text" id="street" name="street" class="form-control" style="width:300px;"
				                    	<c:if test="${operate==1}">readonly="true"</c:if> value="${enterprise.street}"/>
								</td>
							</tr>
						</table>
						<div class="row">
							<div class="col-sm-5"></div>
							<div class="col-sm-2">
								<c:if test="${operate==1}">
									<button class="btn btn-white" type="button" onclick="toList();">返回</button>
								</c:if>
								<c:if test="${operate==2}">
									<button class="btn btn-primary" id="saveBtn" type="button"
										onclick="editProduct();">保存</button>
								</c:if>
								<c:if test="${operate==3}">
									<button class="btn btn-primary" id="saveBtn" type="button"
										onclick="addProduct();">添加</button>
								</c:if>
							</div>
							<div class="col-sm-1">
								<button class="btn btn-white" type="button" onclick="toList();">取消</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>

    function selectCitys() {
		$("#city option").remove();
	    $("#area option").remove();
	    var p= $('#province option:selected') .val(); 
	     $.ajax({
	        type: "post",
	        contentType: "application/json",
	        url: "base/region/listByParentCode",
	        data: "{pid:'" +  p + "'}",
	        success: function (data) {
	        	$("#city").append("<option value=''>--请选择--</option>");
	        	json = eval(data)
	            for (var i = 0; i < json.length; i++) {
	                var a = "<option value='" + json[i].code + "'>" + json[i].name + "</option>";
	                $("#city").append(a);
	            }
	        	$("#area").append("<option value=\"\">--请选择--</option>");
	        }
	    }) 
	}
	function selectAreas() {
	    $("#area option").remove();
	    var p= $('#city option:selected') .val(); 
	     $.ajax({
	        type: "post",
	        contentType: "application/json",
	        url: "base/region/listByParentCode",
	        data: "{pid:'" +  p + "'}",
	        success: function (data) {
	        	$("#area").append("<option value=\"\">--请选择--</option>");
	        	json = eval(data)
	            for (var i = 0; i < json.length; i++) {
	                var a = "<option value='" + json[i].code + "'>" + json[i].name + "</option>";
	                $("#area").append(a);
	            }
	        }
	    }) 
	}

    function array_remove_repeat(a) { // 去重
        var r = [];
        for(var i = 0; i < a.length; i ++) {
            var flag = true;
            var temp = a[i];
            for(var j = 0; j < r.length; j ++) {
                if(temp.productName === r[j].productName && temp.productId === r[j].productId) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                r.push(temp);
            }
        }
        return r;
    }

</script>
</body>
</html>
