<%@page import="com.taisf.services.common.valenum.EnterpriseTypeEnum" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
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
    <style type=text/css>
        .tdfont {
            font-size: 13px
        }
    </style>
</head>

<body class="gray-bg">

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <div class="form-group">

                    <label class="col-sm-1 control-label mtop">菜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                    <div class="col-sm-2">
                        <input id="productNameS" type="text" value="" class="form-control">
                    </div>
                    <label class="col-xs-1 col-sm-1 control-label mtop">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类:</label>
                    <div class="col-xs-2 col-sm-2">
                        <select class="form-control" name="productClassify" id="productClassifyS">
                            <option value="">--请选择--</option>
                            <option value="1">--大荤--</option>
                            <option value="2">--小荤--</option>
                            <option value="3">--素--</option>
                        </select>
                    </div>
                    <label class="col-xs-1 col-sm-1 control-label mtop">供餐类型:</label>
                    <div class="col-xs-2 col-sm-2">
                        <select class="form-control" name="productClassify" id="productTypeS">
                            <option value="">--请选择--</option>
                            <option value="1">--全部--</option>
                            <option value="2">--老板餐--</option>
                            <option value="3">--员工餐--</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row" style="margin-top: 10px;">
                <div class="form-group">
                    <label class="col-xs-1 col-sm-1 control-label mtop">菜品属性:</label>
                    <div class="col-xs-2 col-sm-2">
                        <select class="form-control" name="productClassify" id="productSourceS">
                            <option value="">--全部--</option>
                            <option value="1">--普通餐--</option>
                            <option value="2">--西餐--</option>
                            <option value="3">--清真--</option>
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
                    <table id="listTable" class="table table-bordered" data-click-to-select="true"
                           data-toggle="table" data-side-pagination="server"
                           data-pagination="true" data-page-list="[5,10,20,50]"
                           data-pagination="true" data-page-size="10"
                           data-pagination-first-text="首页" data-pagination-pre-text="上一页"
                           data-pagination-next-text="下一页" data-pagination-last-text="末页"
                           data-content-type="application/x-www-form-urlencoded"
                           data-query-params="paginationParam" data-method="post"
                           data-single-select="true"
                           data-url="supplierProduct/pageList">
                        <thead>
                        <tr>
                            <th data-field="id" data-width="10%"
                                data-align="center"><span class="tdfont">ID</span></th>
                            <th data-field="productName" data-width="10%"
                                data-align="center"><span class="tdfont">菜单名称</span></th>
                            <th data-field="productType" data-width="15%" data-formatter="formatProductType"
                                data-align="center"><span class="tdfont">供餐类型</span></th>
                            <th data-field="productSource" data-width="10%" data-formatter="formatProductSource"
                                data-align="center"><span class="tdfont">菜品属性</span></th>
                            <th data-field="productClassify" data-width="10%" data-formatter="formatProductClassify"
                                data-align="center"><span class="tdfont">分类</span></th>
                            <th data-field="priceSale" data-width="10%"
                                data-align="center"><span class="tdfont">单价</span></th>
                            <th data-field="isDel" data-width="10%" data-formatter="formatStatus"
                                data-align="center"><span class="tdfont">状态</span></th>
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
<script>
    function paginationParam(params) {
        var openTime = $("#openTime").val();
        var tillTime = $("#tillTime").val();

        if (openTime == "") {
            openTime = undefined;
        } else {
            openTime += " 00:00:00";
        }
        if (tillTime == "") {
            tillTime = undefined;
        } else
            tillTime += " 00:00:00";

        return {
            limit: params.limit,
            page: $("#listTable").bootstrapTable("getOptions").pageNumber,
            openTime: openTime,
            tillTime: tillTime,
            productName: $("#productNameS").val(),
            productClassify: $("#productClassifyS").val(),
            productType: $("#productTypeS").val(),
            productSource: $("#productSourceS").val(),
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
    function formatProductType(value, row, index) {
        if (value == 1) {
            return "全部";
        } else if (value == 2) {
            return "老板餐";
        } else if (value == 3) {
            return "员工餐";
        }
    }
    function formatProductSource(value, row, index) {
        if (value == 1) {
            return "普通餐";
        } else if (value == 2) {
            return "西餐";
        } else if (value == 3) {
            return "清真";
        }
    }
    function formatProductClassify(value, row, index) {
        if (value == 1) {
            return "大荤";
        } else if (value == 2) {
            return "小荤";
        } else if (value == 3) {
            return "素";
        }
    }
    function formatStatus(value, row, index) {
        if (value == 1) {
            return "已添加";
        } else {
            return "未添加";
        }
    }
    // 操作列
    function formatOperate(value, row, index) {
        var result = "";
        if (row.isDel == 1) {
            result = result + "<a title='撤回' onclick='revocation(" + row.id + ")')>撤回</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        } else {
            result = result + "<a title='添加' onclick='addSupplierProduct(" + row.id + ")')>添加</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        return result;
    }
    //撤回
    function revocation(id) {
        $.ajax({
            data: {
                'id': id,
            },
            type: "post",
            dataType: "json",
            url: 'supplierProduct/revocation',
            success: function (result) {
                if (result.code === 0) {
                    layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
                    $('#listTable').bootstrapTable('refresh');
                } else {
                    layer.alert(result.msg, {icon: 5, time: 2000, title: '提示'});
                    $("#saveBtn").removeAttr("disabled");
                }
            },
            error: function (result) {
                layer.alert("未知错误", {icon: 5, time: 2000, title: '提示'});
                $("#saveBtn").removeAttr("disabled");
            }
        });
    }
    //添加
    function addSupplierProduct(id) {
        $.ajax({
            data: {
                'id': id,
            },
            type: "post",
            dataType: "json",
            url: 'supplierProduct/addSupplierProduct',
            success: function (result) {
                if (result.code === 0) {
                    layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
                    $('#listTable').bootstrapTable('refresh');
                } else {
                    layer.alert(result.msg, {icon: 5, time: 2000, title: '提示'});
                    $("#saveBtn").removeAttr("disabled");
                }
            },
            error: function (result) {
                layer.alert("未知错误", {icon: 5, time: 2000, title: '提示'});
                $("#saveBtn").removeAttr("disabled");
            }
        });
    }
    function query() {
        $("#listTable").bootstrapTable("selectPage", 1);
    }
</script>

</body>
</html>
