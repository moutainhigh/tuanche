<%@page import="com.taisf.services.common.valenum.FinanceCheckTypeEnum" %>
<%@page import="com.taisf.services.common.valenum.EnterpriseTypeEnum" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
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
        .td-title {
            font-weight: bold;
            font-size: 13px
        }

        .table > tbody > tr > td {
            border: 0px solid white
        }

        .hr-line-dashed {
            margin: 0 0;
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
</head>

<body class="gray-bg">

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="float-e-margins">
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="row" style="margin-top: 10px;">
                        <div class="form-group">
                            <label class="col-xs-1 col-sm-1 control-label mtop">组合名称:</label>
                            <div class="col-xs-2 col-sm-2">
                                <input id="supplierPackageName" name="supplierPackage" type="text" value="${packageEntity.title}"
                                       class="form-control">
                                <input id="supplierPackageId" name="supplierPackage" type="hidden" value="${packageEntity.id}"
                                       class="form-control">
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">大荤:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="dahun" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty dahunList}">
                                        <c:forEach var="v" items="${dahunList}">
                                            <option value="${v.id}" ${packageEntity.bigCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">小荤:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="xiaohun" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty xiaohunList}">
                                        <c:forEach var="v" items="${xiaohunList}">
                                            <option value="${v.id}" ${packageEntity.smallCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">素:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="su" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty suList}">
                                        <c:forEach var="v" items="${suList}">
                                            <option value="${v.id}" ${packageEntity.suCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">
                        <div class="form-group">
                            <label class="col-xs-1 col-sm-1 control-label mtop">汤:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="tang" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty tangList}">
                                        <c:forEach var="v" items="${tangList}">
                                            <option value="${v.id}" ${packageEntity.tangCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">饮品:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="yinpin" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty yinpinList}">
                                        <c:forEach var="v" items="${yinpinList}">
                                            <option value="${v.id}" ${packageEntity.drinkCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">主食:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="zhushi" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty zhushiList}">
                                        <c:forEach var="v" items="${zhushiList}">
                                            <option value="${v.id}" ${packageEntity.foodCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">水果:</label>
                            <div class="col-xs-2 col-sm-2">
                                <select class="form-control" id="shuiguo" name="supplierCode">
                                    <option value="">-请选择-</option>
                                    <c:if test="${ not empty shuiguoList}">
                                        <c:forEach var="v" items="${shuiguoList}">
                                            <option value="${v.id}" ${packageEntity.fruitCode == v.id ? "selected" : ""}>${v.productName}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">
                        <div class="form-group">
                            <label class="col-xs-1 col-sm-1 control-label mtop">套餐价格:</label>
                            <div class="col-xs-2 col-sm-2">
                                <input id="packagePrice" name="packagePrice" type="text" value="${packageEntity.packagePrice}"
                                       class="form-control">
                            </div>
                            <label class="col-xs-1 col-sm-1 control-label mtop">上传图片:</label>
                            <div class="col-xs-2 col-sm-2">
                            <input type="file" id="pic">
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">
                        <div class="col-xs-4 col-sm-4"></div>

                        <div class="col-xs-1 col-sm-1">
                            <button class="btn btn-primary" id="saveBtnE" type="button" onclick="saveSupplierPackage();">
                                保存
                            </button>
                        </div>
                        <div class="col-xs-1 col-sm-1">
                            <button type="button" class="btn btn-white" onclick="toList()" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function saveSupplierPackage() {
            $("#saveBtnE").attr("disabled", "disabled");
            if ($("#supplierPackageName").val() == null || $("#supplierPackageName").val() == "") {
                layer.alert("组合名称不能为空", {icon: 5, time: 2000, title: '提示'});
                $("#saveBtnE").removeAttr("disabled");
                return false;
            };
            /*if ($("#productClassifyE option:selected").val() == null || $("#productClassifyE option:selected").val() == "") {
                layer.alert("请选择分类", {icon: 5, time: 2000, title: '提示'});
                $("#saveBtnE").removeAttr("disabled");
                return false;
            };*/
            $.ajax({
                data: {
                    'title': $("#supplierPackageName").val(),
                    'id': $("#supplierPackageId").val(),
                    'packagePic': $("#pic").val(),
                    'packagePrice': $("#packagePrice").val(),
                    'bigCode': $("#dahun option:selected").val(),
                    'smallCode': $("#xiaohun option:selected").val(),
                    'suCode': $("#su option:selected").val(),
                    'tangCode': $("#tang option:selected").val(),
                    'drinkCode': $("#yinpin option:selected").val(),
                    'foodCode': $("#zhushi option:selected").val(),
                    'fruitCode': $("#shuiguo option:selected").val(),
                },
                type: "post",
                dataType: "json",
                url: 'supplierProductPackage/updateSupplierProductPackage',
                success: function (result) {
                    if (result.code === 0) {
                        layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
                        $("#saveBtnE").removeAttr("disabled");
                        toList();
                    } else {
                        layer.alert(result.msg, {icon: 5, time: 2000, title: '提示'});
                        $("#saveBtnE").removeAttr("disabled");
                    }
                },
                error: function (result) {
                    layer.alert("未知错误", {icon: 5, time: 2000, title: '提示'});
                    $("#saveBtn").removeAttr("disabled");
                }
            });
        }

        function callBack(parent) {
            parent.refreshData("listTable");
        }
        function toList() {
            $.callBackParent("supplierProductPackage/list", true, callBack);
        }
    </script>
</body>
</html>
