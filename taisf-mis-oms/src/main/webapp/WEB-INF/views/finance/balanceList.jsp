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

            <div class="row" style="margin-top: 10px;">
                <div class="form-group">


                    <label class="col-sm-1 control-label mtop">企业编号:</label>
                    <div class="col-sm-2">
                        <input id="enterpriseCode" name="enterpriseCode" class="form-control">
                    </div>
                    <%--<label class="col-sm-1 control-label mtop">企业名称:</label>--%>
                    <%--<div class="col-sm-2">--%>
                    <%--<input id="enterpriseName" name="enterpriseName"  class="form-control">--%>
                    <%--</div>--%>


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
                           data-query-params="paginationAccount" data-method="post"
                           data-single-select="true"
                           data-url="finance/balanceListPage">
                        <thead>
                        <tr>

                            <th data-field="enterpriseCode" data-width="10%"
                                data-align="center"><span class="tdfont">企业编号</span></th>
                            <th data-field="enterpriseName" data-width="10%"
                                data-align="center"><span class="tdfont">企业名称</span></th>
                            <th data-field="allBalance" data-width="10%"
                                data-align="center"><span class="tdfont">账号总金额</span></th>
                            <th data-field="drawBalance" data-width="10%"
                                data-align="center"><span class="tdfont">可分配金额</span></th>

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


<!-- 弹出用户详情 -->
<div class="modal inmodal" id="detailModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">


            <div class="modal-header" style="padding: 15px 6px;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">员工明细查询</h4>
            </div>


            <!-- 搜索框panel -->
            <div class="ibox float-e-margins">
                <div class="ibox-content">

                    <!-- 搜索框panel -->

                    <input id="id_enterpriseCode" name="enterpriseCode" type="hidden"
                           class="form-control">

                    <label class="col-sm-1 control-label mtop">电话:</label>
                    <div class="col-sm-2">
                        <input id="userPhone" name="userPhone" type="text"
                               class="form-control">
                    </div>


                    <div class="col-sm-1">
                        <button class="btn btn-primary" type="button" onclick="queryEmp();">
                            <i class="fa fa-search"></i>&nbsp;搜索
                        </button>
                    </div>
                </div>
            </div>


            <!-- 列表 -->
            <div class="ibox float-e-margins" style="margin-bottom:0px">
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <!-- 弹出框列表 -->
                                    <table id="listTableEmp" class="table table-bordered" data-click-to-select="true"
                                           data-toggle="table" data-side-pagination="server"
                                           data-pagination="true" data-page-list="[5,10,20,50]"
                                           data-pagination="true" data-page-size="10"
                                           data-pagination-first-text="首页" data-pagination-pre-text="上一页"
                                           data-pagination-next-text="下一页" data-pagination-last-text="末页"
                                           data-content-type="application/x-www-form-urlencoded"
                                           data-query-params="paginationAccountS" data-method="post"
                                           data-single-select="true"
                                           data-url="finance/accountPage">
                                        <thead>
                                        <tr>
                                            <th data-field="userCode" data-width="20%">编号</th>
                                            <th data-field="userPhone" data-width="30%" data-align="center">手机号</th>
                                            <th data-field="userName" data-width="30%" data-align="center">姓名</th>
                                            <th data-field="drawBalance" data-width="30%" data-formatter="formatePrice"
                                                data-align="center">账户余额
                                            </th>
                                            <th data-field="userStatus" data-width="20%" data-formatter="formateUserStatus"
                                                data-align="center">状态</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">返回</button>
            </div>
        </div>
    </div>
</div>
<!-- end -->






<!-- 平均分配 -->
<div class="modal inmodal" id="avgModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">


            <div class="modal-header" style="padding: 15px 6px;">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">平均分配金额-金额填写之后会自动平均分配</h4>
            </div>

            <div class="ibox float-e-margins">

                <input id="avg_enterpriseCode" name="enterpriseCode" type="hidden"
                       class="form-control">

                <div class="ibox-content">

                    <!-- 搜索框panel -->

                    <label class="col-sm-1 control-label mtop">员工()人,每人分配金额:</label>
                    <div class="col-sm-2">
                        <input id="empMoney" name="empMoney" type="text"
                               class="form-control">
                    </div>

                </div>

                <div class="ibox-content">
                    <label class="col-sm-1 control-label mtop">老板()人,每人分配金额:</label>
                    <div class="col-sm-2">
                        <input id="bossMoney" name="bossMoney" type="text"
                               class="form-control">
                    </div>

                </div>

                <div class="ibox-content">
                    <div class="col-sm-1">
                        <button class="btn btn-primary" type="button" onclick="queryEmp();">
                            <i class="fa fa-search"></i>&nbsp;确认
                        </button>
                    </div>
                </div>

            </div>


            <!-- 列表 -->
            <div class="ibox float-e-margins" style="margin-bottom:0px">
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                    <!-- 弹出框列表 -->
                                    <table id="listTableEmp" class="table table-bordered" data-click-to-select="true"
                                           data-toggle="table" data-side-pagination="server"
                                           data-pagination="true" data-page-list="[5,10,20,50]"
                                           data-pagination="true" data-page-size="10"
                                           data-pagination-first-text="首页" data-pagination-pre-text="上一页"
                                           data-pagination-next-text="下一页" data-pagination-last-text="末页"
                                           data-content-type="application/x-www-form-urlencoded"
                                           data-query-params="paginationAccountS" data-method="post"
                                           data-single-select="true"
                                           data-url="finance/accountPage">
                                        <thead>
                                        <tr>
                                            <th data-field="userCode" data-width="20%">编号</th>
                                            <th data-field="userPhone" data-width="30%" data-align="center">手机号</th>
                                            <th data-field="userName" data-width="30%" data-align="center">姓名</th>
                                            <th data-field="drawBalance" data-width="30%" data-formatter="formatePrice"
                                                data-align="center">账户余额
                                            </th>
                                            <th data-field="userStatus" data-width="20%" data-formatter="formateUserStatus"
                                                data-align="center">状态</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">返回</button>
            </div>
        </div>
    </div>
</div>
<!-- end -->




<script>
    var enterpriseCode = "123";
    $(function () {
        //初始化日期
        CommonUtils.datePickerFormat('startTime');
        CommonUtils.datePickerFormat('endTime');
    });


    function formatePrice(value, row, index) {
        return (value/100).toFixed(2);
    }


    function paginationAccount(params) {
        return {
            enterpriseCode: $("#id_enterpriseCode").val(),
            userPhone: $("#userPhone").val(),
            limit: params.limit,
            page: $("#listTable").bootstrapTable("getOptions").pageNumber
        };

    }

    function paginationAccountS(params) {
        return {
            limit: params.limit,
            page: $("#listTableEmp").bootstrapTable("getOptions").pageNumber,
            enterpriseCode: $("#id_enterpriseCode").val(),
            userPhone: $("#userPhone").val(),
        };

    }

    function paginationParam(params) {
        return {
            enterpriseCode: $("#enterpriseCode").val(),
            limit: params.limit,
            page: $("#listTable").bootstrapTable("getOptions").pageNumber
        };

    }

    // 格式化时间
    function formateDate(value, row, index) {
        if (value != null) {
            var vDate = new Date(value);
            return vDate.format("yyyy-MM-dd HH:mm:ss");
        } else {
            return "";
        }
    }
    
    function formateUserStatus(value, row, index) {
        if (value == 1) {
            return "可用";
        } else if (value == 2) {
            return "激活";
        }else if (value == 3) {
            return "注销";
        }else if (value == 4) {
            return "冻结";
        } else {
            return value;
        }
    }

    function formatRechargeType(value, row, index) {
        if (value == 1) {
            return "企业充值";
        } else if (value == 2) {
            return "个人充值";
        } else {
            return "其他";
        }
    }
    function formatRechargeStatus(value, row, index) {
        if (value == 1) {
            return "成功";
        } else if (value == 2) {
            return "失败";
        } else {
            return value;
        }
    }

    function query() {
        $("#listTable").bootstrapTable("selectPage", 1);
    }

    function queryEmp() {
        $("#listTableEmp").bootstrapTable("selectPage", 1);
    }

    function avgMoney(enterpriseCode) {

        console.log(enterpriseCode)
        $("#avg_enterpriseCode").val(enterpriseCode)


        $.ajax({
            data: {},
            type: "post",
            dataType: "json",
            url: 'finance/enterpriseStats?enterpriseCode='+enterpriseCode,
            success: function (result) {

                console.log(enterpriseCode)
                if (result.code === 0) {
                    var drawBalance = result.drawBalance;
                    alert(drawBalance);

                    $('#avgModal').modal('show');

                } else {
                    layer.alert(result.msg, {icon: 5, time: 2000, title: '提示'});
                }
            },
            error: function (result) {
                layer.alert("未知错误", {icon: 5, time: 2000, title: '提示'});
            }
        });
    }

    function detail(enterpriseCode) {
        $("#id_enterpriseCode").val(enterpriseCode)
        $('#detailModal').modal('show');
        queryEmp();
    }


    function oneMoney(enterpriseCode) {
        $("#avg_enterpriseCode").val(enterpriseCode)
        $('#avgModal').modal('show');
        queryEmp();
    }


    // 操作列
    function formatOperate(value, row, index) {
        var result = "";
        result = result + "<a title='编辑' onclick='detail(\"" + row.enterpriseCode + "\")' >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        result = result + "<a title='编辑' onclick='avgMoney(\"" + row.enterpriseCode + "\")'  >平均分配</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        result = result + "<a title='查看' onclick='oneMoney(\"" + row.enterpriseCode + "\")'  >个人分配</a>";
        return result;
    }

</script>

</body>
</html>
