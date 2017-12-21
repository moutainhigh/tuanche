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
    <script src="${staticResourceUrl}/js/jquery.form.js${VERSION}"></script>
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css" rel="stylesheet">
    <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>
    <style type=text/css>
        .tdfont {
            font-size: 13px
        }
    </style>
    <style>
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }

        .room-pic {
            float: left;
        }

        .room-pic p {
            text-align: center;
        }

        .blueimp-gallery > .title {
            left: 0;
            bottom: 45px;
            top: auto;
            width: 100%;
            text-align: center;
        }

        .picline {
            display: inline-block;
        }

        .picjz {
            display: inline-block;
            vertical-align: middle;
        }
    </style>
    <style type="text/css">
        .file {
            position: relative;
            display: inline-block;
            background: #1ab394;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #FFFFFF;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
            margin: 20px 0px 12px 0px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }

        .content li {
            float: left;
        }
    </style>
</head>

<body class="gray-bg">
<!-- 图片预览continer -->
<div id="blueimp-gallery" class="blueimp-gallery">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <div class="form-group">

                    <label class="col-sm-1 control-label mtop">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                    <div class="col-sm-2">
                        <input id="userNameS" type="text" value="" class="form-control">
                    </div>
                    <label class="col-sm-1 control-label mtop">手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;:</label>
                    <div class="col-sm-2">
                        <input id="userPhoneS" type="text" value="" class="form-control">
                    </div>
                    <label class="col-xs-1 col-sm-1 control-label mtop">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
                    <div class="col-xs-2 col-sm-2">
                        <select class="form-control" name="userStatusS" id="userStatusS">
                            <option value="">--请选择--</option>
                            <option value="1">--退款中--</option>
                            <option value="2">--已退款--</option>
                            <option value="3">--退款中--</option>
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
                           data-url="refund/pageList">
                        <thead>
                        <tr>
                            <th data-field="id" data-width="10%"
                                data-align="center"><span class="tdfont">ID</span></th>
                            <th data-field="createTime" data-width="10%" data-formatter="formatDate"
                                data-align="center"><span class="tdfont">时间</span></th>
                            <th data-field="refundName" data-width="15%"
                                data-align="center"><span class="tdfont">姓名</span></th>
                            <th data-field="userPhone" data-width="15%"
                                data-align="center"><span class="tdfont">手机号</span></th>
                            <th data-field="payFee" data-width="15%"
                                data-align="center"><span class="tdfont">订单金额</span></th>
                            <th data-field="refundFee" data-width="10%"
                                data-align="center"><span class="tdfont">退款金额</span></th>
                            <th data-field="refundType" data-width="10%" data-formatter="formatRefundType"
                                data-align="center"><span class="tdfont">退款类型</span></th>
                            <th data-field="refundStatus" data-width="10%" data-formatter="formatRefundStatus"
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
<!-- 编辑 -->
<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight"  style="width: 350;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">确认退款</h4>
            </div>
            <div class="col-sm-14">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <form id="editForm2" class="form-horizontal m-t">

                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款金额:</label>
                                <div class="col-sm-8">
                                    <input id="refundFee_E" name="refundFee_E" type="text"
                                           class="form-control">
                                </div>
                            </div>

                            <input type="hidden" class="form-control" id="ID_E" name="id" value=""/>
                            <!-- 用于 将表单缓存清空 -->
                            <input id="editReset" type="reset" style="display:none;"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" id="saveBtnE" type="button" onclick="editSaveProduct();">确定</button>
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 查看详情 -->
<div class="modal inmodal" id="detailModal" tabindex="-1" role="dialog" aria-hidden="true">

    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">退款详情</h4>
            </div>
            <div class="col-sm-14">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <form id="formD" class="form-horizontal m-t">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">支付业务单号</label>
                                <div class="col-sm-8">
                                    <input  readonly id="refundSn" name="refundSn" type="text"
                                           class="form-control">
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-sm-3 control-label">订单编号</label>
                                <div class="col-sm-8">
                                    <input readonly id="orderSn" name="orderSn" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款类型</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundType" name="refundType" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">来源</label>
                                <div class="col-sm-8">
                                    <input readonly id="sourceType" name="sourceType" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款状态</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundStatus" name="refundStatus" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款类型</label>
                                <div class="col-sm-8">
                                    <input readonly id="cardType" name="cardType" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款账号</label>
                                <div class="col-sm-8">
                                    <input readonly id="cardNo" name="cardNo" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款人</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundName" name="refundName" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款uid</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundUid" name="refundUid" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">收款id</label>
                                <div class="col-sm-8">
                                    <input readonly id="recordId" name="recordId" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款失败重试次数</label>
                                <div class="col-sm-8">
                                    <input readonly id="retryTimes" name="retryTimes" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">支付金额</label>
                                <div class="col-sm-8">
                                    <input readonly id="payFee" name="payFee" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款金额</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundFee" name="refundFee" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">退款成功时间</label>
                                <div class="col-sm-8">
                                    <input readonly id="refundTime" name="refundTime" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <input type="hidden" class="form-control" id="productIdD" name="id" value=""/>
                            <!-- 用于 将表单缓存清空 -->
                            <input id="editReset" type="reset" style="display:none;"/>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
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
            userName: $("#userNameS").val(),
            userPhone: $("#userPhoneS").val(),
            userStatus: $("#userStatusS").val(),
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
    function formatPrice(value, row, index) {
        if (value != null) {
            return (value/100).toFixed(2);
        } else {
            return "-";
        }
    }
    function formatRefundType(value, row, index) {
        if (value == 1) {
            return "原路返回";
        } else if (value == 2) {
            return "银行卡";
        } else if (value == 3) {
            return "余额";
        }
    }
    //1.待审核 2.审核失败 3.审核成功 4.打款成功 5.打款失败 6.打款中 7.调用支付平台失败 ',
    function formatRefundStatus(value, row, index) {
        if (value == 1) {
            return "待审核";
        } else if (value == 2) {
            return "审核失败";
        } else if (value == 3) {
            return "审核成功";
        }else if (value == 3) {
            return "打款成功";
        }else if (value == 3) {
            return "打款失败";
        }else if (value == 3) {
            return "打款中";
        }else if (value == 3) {
            return "调用支付平台失败";
        }
    }

    // 操作列
    function formatOperate(value, row, index) {
        var result = "";
        result = result + "<a title='详情' onclick='detail(" + row.id + ")'  data-toggle='modal' data-target='#detailModal')>详情</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        result = result + "<a title='确认退款' onclick='toedit(" + row.id + ")'  data-toggle='modal' data-target='#editModal')>确认退款</a>&nbsp;&nbsp;&nbsp;&nbsp;";
        result = result + "<a title='驳回' onclick='reject(" + row.id + ")'  >驳回</a>";
        return result;
    }
    //编辑菜品
    function toedit(id) {
        $.ajax({
            data: {
                'id': id,
            },
            type: "post",
            dataType: "json",
            url: 'refund/toedit',
            success: function (result) {
                if (result.code === 0) {
                    $('#ID_E').val(result.data.id);
                    $('#refundFee_E').val(result.data.refundFee);
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

    function detail(id) {
        $.ajax({
            data: {
                'id': id,
            },
            type: "post",
            dataType: "json",
            url: 'refund/toedit',
            success: function (result) {
                if (result.code === 0) {
                    $('#refundSn').val(result.data.refundSn);
                    $('#orderSn').val(result.data.orderSn);
                    if(result.data.refundType ==1){
                        $('#refundType').val("原路返回");
                    }else if(result.data.refundType ==2){
                        $('#refundType').val("银行卡");
                    }else if(result.data.refundType ==3){
                        $('#refundType').val("余额");
                    }
                    if(result.data.sourceType ==1){
                        $('#sourceType').val("安卓");
                    }else if(result.data.sourceType ==2){
                        $('#sourceType').val("IOS");
                    }else if(result.data.sourceType ==3){
                        $('#sourceType').val("M站");
                    }else if(result.data.sourceType ==4){
                        $('#sourceType').val("微信");
                    }else{
                        $('#sourceType').val("其他");
                    }

                    if(result.data.refundStatus ==1){
                        $('#refundStatus').val("待审核");
                    }else if(result.data.refundStatus ==2){
                        $('#refundStatus').val("审核失败");
                    }else if(result.data.refundStatus ==3){
                        $('#refundStatus').val("审核成功");
                    }else if(result.data.refundStatus ==4){
                        $('#refundStatus').val("打款成功");
                    }else if(result.data.refundStatus ==5){
                        $('#refundStatus').val("打款失败");
                    }else if(result.data.refundStatus ==6){
                        $('#refundStatus').val("打款中");
                    }else if(result.data.refundStatus ==7){
                        $('#refundStatus').val("调用支付平台失败 ");
                    }

                    if(result.data.cardType ==1){
                        $('#cardType').val("微信");
                    }else if(result.data.cardType ==2){
                        $('#cardType').val("支付宝");
                    }else if(result.data.cardType ==3){
                        $('#cardType').val("银行卡");
                    }
                    $('#cardNo').val(result.data.cardNo);
                    $('#refundName').val(result.data.refundName);
                    $('#refundUid').val(result.data.refundUid);
                    $('#recordId').val(result.data.recordId);
                    $('#retryTimes').val(result.data.retryTimes);
                    $('#payFee').val(result.data.payFee);
                    $('#refundFee').val(result.data.refundFee);
                    $('#refundTime').val(result.data.refundTime);



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

    //新增销售员工
    function saveProduct() {
        $("#saveBtn").attr("disabled", "disabled");
        if ($("#productName").val() == null || $("#productName").val() == "") {
            layer.alert("菜品名称不能为空", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;
        if ($("#productClassify option:selected").val() == null || $("#productClassify option:selected").val() == "") {
            layer.alert("请选择分类", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;

        var temp = document.getElementsByName("productType");
        var productType;
        for (var i = 0; i < temp.length; i++) {
            if (temp[i].checked)
                productType = temp[i].value;
        }
        if (productType == null || productType == "") {
            layer.alert("请选择供餐类型", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;
        if ($('input[name="productSource"]:checked').val() == null || $('input[name="productSource"]:checked').val() == "") {
            layer.alert("请选择菜品属性", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;
        if ($("#priceSale").val() == null || $("#priceSale").val() == "") {
            layer.alert("单价不能为空", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;
        if ($("#productDes").val() == null || $("#productDes").val() == "") {
            layer.alert("描述不能为空", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtn").removeAttr("disabled");
            return false;
        }
        ;

        $.ajax({
            data: {
                'productName': $("#productName").val(),
                'productClassify': $("#productClassify option:selected").val(),
                'productType': productType,
                'productSource': $('input[name="productSource"]:checked').val(),
                'price': $("#priceSale").val(),
                'productDes': $("#productDes").val(),
                'productPic': $("#imgUrl-1").val(),
            },
            type: "post",
            dataType: "json",
            url: 'product/addProduct',
            success: function (result) {
                if (result.code === 0) {
                    layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
                    $('#listTable').bootstrapTable('refresh');
                    $('#myModal').modal('hide');
                    $("input[type=reset]").trigger("click");
                    $("#saveBtn").removeAttr("disabled");
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
    function editSaveProduct() {
        $("#saveBtnE").attr("disabled", "disabled");
        if ($("#refundFee_E").val() == null || $("#refundFee_E").val() == "") {
            layer.alert("请输入退款金额", {icon: 5, time: 2000, title: '提示'});
            $("#saveBtnE").removeAttr("disabled");
            return false;
        }

        $.ajax({
            data: {
                'refundFee': $("#refundFee_E").val(),
                'id': $("#ID_E").val(),
                'refundStatus': 3,
            },
            type: "post",
            dataType: "json",
            url: 'refund/updateRefund',
            success: function (result) {
                if (result.code === 0) {
                    layer.alert("操作成功", {icon: 6, time: 2000, title: '提示'});
                    $('#listTable').bootstrapTable('refresh');
                    $('#editModal').modal('hide');
                    $("#editReset").trigger("click");
                    $("#saveBtnE").removeAttr("disabled");
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
    function query() {
        $("#listTable").bootstrapTable("selectPage", 1);
    }

    function reject(id){
        layer.confirm("确认驳回吗？", {icon: 6, title:'提示'},function(index){
            $.ajax({
                type:"post",
                url:"refund/updateRefund",
                dataType: "json",
                data:{
                    "id":id,
                    "refundStatus":2
                },
                success: function (result) {
                    if(result.code == 0){
                        layer.alert("驳回成功", {icon: 6, time: 2000, title: '提示'});
                        $('#listTable').bootstrapTable('refresh');
                    }
                }
            });
            layer.close(index);
        });

    }

    function formReset(){
        $('#showImg-1').attr('href', "");
        $("#imgSizeImgSrc-1").attr("src", "");
        $("#imgUrl-1").val("")
    }

</script>
</body>
</html>
