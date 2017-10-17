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

                    <label class="col-sm-1 control-label mtop">企业名称:</label>
                    <div class="col-sm-2">
                        <input id="productNameS" type="text" value="" class="form-control">
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
                           data-url="product/pageList">
                        <thead>
                        <tr>
                            <th data-field="id" data-width="10%"
                                data-align="center"><span class="tdfont">企业编号</span></th>
                            <th data-field="productName" data-width="10%"
                                data-align="center"><span class="tdfont">企业名称</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周一</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周二</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周三</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周四</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周五</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周六</span></th>
                            <th data-field="productType" data-width="10%"
                                data-align="center"><span class="tdfont">周日</span></th>
                            <th data-field="handle" data-width="10%" data-align="center"
                                data-formatter="formatOperate"><span class="tdfont">操作</span></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 编辑 -->
<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">设置送餐日期</h4>
            </div>
            <div class="col-sm-14">
                <div class="ibox float-e-margins">
                    <div class="fc-content" style="position: relative;">
                        <div class="fc-view fc-view-month fc-grid" style="position:relative"
                             unselectable="on">
                            <table class="fc-border-separate" border="1" cellspacing="0"
                                   style="width:100%" cellspacing="0">
                                <thead>
                                <tr class="fc-first fc-last">

                                    <th class="fc-day-header fc-一 fc-widget-header fc-first"
                                        style="width: 193px;">一
                                    </th>
                                    <th class="fc-day-header fc-二 fc-widget-header"
                                        style="width: 193px;">二
                                    </th>
                                    <th class="fc-day-header fc-三 fc-widget-header"
                                        style="width: 193px;">三
                                    </th>
                                    <th class="fc-day-header fc-四 fc-widget-header"
                                        style="width: 193px;">四
                                    </th>
                                    <th class="fc-day-header fc-五 fc-widget-header"
                                        style="width: 193px;">五
                                    </th>
                                    <th class="fc-day-header fc-六 fc-widget-header"
                                        style="width: 193px;">六
                                    </th>
                                    <th class="fc-day-header fc-日 fc-widget-header fc-last"
                                        style="width: 193px;">日
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="table">

                               <%-- <tr>
                                    <td>
                                        <div>
                                            <div>
                                                <div style="text-align:center;margin-top: 20px;"><span
                                                        style="color: #0099ff;font-size: 15px;">6.14</span></div>
                                                <div style="height: 27px;text-align:center;">
                                                    <label><input name="" type="checkbox" value=""
                                                                  onclick="dispatching('id',this)"/>配送</label>
                                                </div>
                                            </div>
                                        </div>
                                    </td>

                                </tr>--%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="saveBtnE" type="button" onclick="editSaveProduct();">保存</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script>
    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $("#listTable").bootstrapTable("getOptions").pageNumber,
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
    // 操作列
    function formatOperate(value, row, index) {
        var result = "";
        result = result + "<a title='设置' onclick='toedit(" + row.id + ")'  data-toggle='modal' data-target='#editModal')>设置</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        return result;
    }
    function toedit(id) {
        $.ajax({
            data: {
                'id': id,
            },
            type: "post",
            dataType: "json",
            url: 'product/toedit',
            success: function (result) {
                if (result.code === 0) {
                    $("#table").empty();
                    var html = ""
                    for (var i = 0; i < 3; i++) {
                        html += '<tr>';
                        for (var j = 0; j < 7; j++) {
                            html += '<td><div><div><div style="text-align:center;margin-top: 20px;">' +
                                '<span style="color: #0099ff;font-size: 15px;">6.14</span></div><div  style="height: 27px;text-align:center;">' +
                                '<label><input name="" type="checkbox" value="" onclick="dispatching(this,\'1\')" />配送</label>' +
                                '</div></div></div></td>';
                        }
                        html += '</tr>';
                    }
                    $("#table").append(html);
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
    function dispatching(obj,id) {
        var status;
        obj.checked == true ? status =1 : status = 2
        console.log(obj.checked);
        console.log(status);
            $.ajax({
                data: {
                    'id': id,
                    'dayType': status,
                },
                type: "post",
                dataType: "json",
                url: '',
                success: function (result) {
                    if (result.code === 0) {
                        layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
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
