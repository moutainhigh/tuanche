<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
    <title>健客医院信息管理系统</title>
    <meta name="keywords" content="健客医院信息管理系统">
    <meta name="description" content="健客医院信息管理系统">

    <link rel="${staticIconUrl}/shortcut icon" href="${staticIconUrl}/favicon.ico"> 
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css" rel="stylesheet">
    <link href="${staticResourceUrl}/css/demo/jquery-ui.css" rel="stylesheet">
    <%--<link href="${staticResourceUrl}/css/demo/jquery.autocomplete.css" rel="stylesheet">--%>
    <link href="${staticResourceUrl}/css/demo/jquery-ui.css" rel="stylesheet">

    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">

    <style>
		.lightBoxGallery img {margin: 5px;width: 160px;}
		.room-pic {float: left;}
		.room-pic p {text-align: center;}
		.blueimp-gallery>.title {left: 0;bottom: 45px;top: auto;width: 100%;text-align: center;}
		.picline {display: inline-block;}
		.picjz {display: inline-block;vertical-align: middle;}
	</style>
	<style type="text/css">
		.table>tbody>tr>td {
			font-size: 14px;
			padding-top: 5px;
			padding-bottom: 5px;
		}
		.file {
		    position: relative;
		    display: inline-block;
		    background:#1ab394;
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
		 .content li{ float:left; }
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
        <div class="row">
            <div class="col-sm-12">
                <div class="float-e-margins">
                    <div class="ibox-title">
                        <c:if test="${operate==3}">
                            <h5>添加药品</h5>
                        </c:if>
                        <c:if test="${operate==2}">
                        <h5>编辑药品</h5>
                        </c:if>
                        <c:if test="${operate==1}">
                            <h5>查看药品</h5>
                        </c:if>
                    </div>
                    <div class="ibox-content">
                        <input type="hidden" id="jkCodeSearch" value="">
                        <table class="table table-bordered">
                                         
                             <tbody>
                             <c:if test="${operate !=3}">
                                 <tr>
                                     <td align="right">药品编号:</td>
                                     <td colspan=3>
                                         <input type="text" class="form-control" name="id"  id="id" value="${product.id }" readonly="true">
                                         <span class="input-group-btn"></span>
                                     </td>
                                 </tr>
                             </c:if>
                             <tr>
                                 <td align="right"><font color="red">*&nbsp;</font>药品名称:</td>
                                 <td align="center">
                                     <input id="tags"  placeholder="" name="productName" onchange="fillPinyin()" value="${product.productName}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                                 <td align="right">规格:</td>
                                 <td align="center">
                                     <input id="packing" name="packing" value="${product.packing}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                             </tr>

                             <tr>
                                 <td align="right">健客药品编号:</td>
                                 <td align="center">
                                 	<input id="jkCode" name="jkCode" value="${product.jkCode}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                                 <td align="right"><font color="red">*&nbsp;</font>条码:</td>
                                 <td align="center">
                                     <input id="barCode" name="barCode" value="${product.barCode}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                             </tr>

                             <tr>
                                 <td align="right">生产厂家:</td>
                                 <td align="center">
                                     <input id="manufacturer" name="manufacturer" value="${product.manufacturer}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                                 <td align="right">拼音码</td>
                                 <td align="center">
                                     <input id="jianpin" name="jianpin" value="${product.jianpin}" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                             </tr>

                             <tr>
                                 <td align="right"><font color="red">*&nbsp;</font>药品分类:</td>
                                 <td align="center">
                                     <select class="form-control" name="productType" id="productType">
                                         <c:if test="${operate==3}">
                                             <option value="0">--请选择--</option>
                                             <c:forEach items="${drugType}" var="d" >
                                                 <option  value="${d.typeCode}">${d.typeName}</option>
                                             </c:forEach>
                                         </c:if>
                                        <c:if test="${operate != 3}">
                                             <c:forEach items="${drugType}" var="d" >
                                                 <option <c:if test="${product.productTypeFirst==d.typeCode}" > selected="selected" </c:if> value="${d.typeCode}">${d.typeName}</option>
                                             </c:forEach>
                                        </c:if>

                                     </select>
                                 </td>
                                 <td align="right">单位:</td>
                                 <td align="center">
                                     <select class="form-control" name="unit" id="unit">
                                        <c:if test="${operate==3}">
                                             <option value="0">--请选择--</option>
                                             <c:forEach items="${unitType}" var="u" >
                                                 <option  value="${u.dataCode}">${u.dataValue}</option>
                                             </c:forEach>
                                        </c:if>
                                         <c:if test="${operate != 3}">
                                             <c:forEach items="${unitType}" var="u" >
                                                 <option <c:if test="${product.unit==u.dataValue}" > selected="selected" </c:if> value="${u.dataCode}">${u.dataValue}</option>
                                             </c:forEach>
                                         </c:if>
                                     </select>
                                 </td>
                             </tr>

                             <tr>
                                 <td align="right"><font color="red">*&nbsp;</font>销售价(元):</td>
                                 <td align="center">
                                     <input id="priceSale" name="priceSale" value="<c:if test="${operate!=3}"><fmt:formatNumber type="number" value="${product.priceSale/100}" pattern="0.00" maxFractionDigits="2"/></c:if>" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                                 <td align="right"><font color="red">*&nbsp;</font>公费价(元):</td>
                                 <td align="center">
                                     <input id="pricePublic" name="pricePublic" value="<c:if test="${operate!=3}"><fmt:formatNumber type="number" value="${product.pricePublic/100}" pattern="0.00" maxFractionDigits="2"/></c:if>" <c:if test="${operate==1}">readonly="true"</c:if> type="text" class="form-control" >
                                 </td>
                             </tr>

                             <tr>
                                 <td align="right"><font color="red">*&nbsp;</font>收费科目:</td>
                                 <td colspan=3>
                                     <select class="form-control" name="subjectCode" id="subjectCode">
                                         <c:if test="${operate==3}">
                                             <option value="0">--请选择--</option>
                                             <c:forEach items="${subjects}" var="s" >
                                                 <option  value="${s.dataCode}">${s.dataValue}</option>
                                             </c:forEach>
                                         </c:if>
                                         <c:if test="${operate != 3}">
                                             <c:forEach items="${subjects}" var="s" >
                                                 <option <c:if test="${product.subjectCode==s.dataCode}" > selected="selected" </c:if> value="${s.dataCode}">${s.dataValue}</option>
                                             </c:forEach>
                                         </c:if>
                                     </select>
                                 </td>
                             </tr>
                             </tbody>
                         </table>
                        
                        
                        <div class="row">
                          		<div class="col-sm-5"></div>
					           <div class="col-sm-2">
                               <c:if test="${operate==1}">
                               <button class="btn btn-white"  type="button" onclick="toList();">返回</button>
                               </c:if>
                                <c:if test="${operate==2}">
                               <button class="btn btn-primary" id="saveBtn" type="button" onclick="editProduct();">保存</button>
                                </c:if>
                                   <c:if test="${operate==3}">
                                       <button class="btn btn-primary" id="saveBtn" type="button" onclick="addProduct();">添加</button>
                                   </c:if>
					           </div>
                            <div class="col-sm-1">
                            <button class="btn btn-white"  type="button" onclick="toList();">取消</button>
	                         </div>
                        </div>
                        

                    </div>
                </div>
            </div>
        </div>
    </div>    



    <!-- 全局js -->
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

    <!-- 自定义js -->
    <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>

    <!-- iCheck -->
    <script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>

    <%--<script src="${staticResourceUrl}/js/demo/jquery.autocomplete.js${VERSION}"></script>--%>
    <script src="${staticResourceUrl}/js/demo/jquery-ui.js"></script>


    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/common/geography.cascade.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/common/refresh.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/common/custom.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/jquery.form.js${VERSION}"></script>
    
    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js"></script>

    <script src="${staticResourceUrl}/js/common/pinyin.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript">

        /*function pinyinFill(str) {
            var hanzi = str.value;
            $("#jianpin").val(codefans_net_CC2PY(hanzi));
        }*/
        //用户不小心删掉之前填充的，再次填充一次
        function fillPinyin() {
            var productName = $("#tags").val();
            if(isNullOrBlank(productName)){
                return;
            }
            $("#jianpin").val(codefans_net_CC2PY(productName));
        }

        //参数校验
        function checkParam() {
            //取值
            var productName = $("#tags").val();
            if(isNullOrBlank(productName)){
                return "药品名称必填哦!"
            }
            var packing = $("#packing").val();
            var jkCode = $("#jkCode").val();
            var barCode = $("#barCode").val();
            if(isNullOrBlank(barCode)){
                return "条码必填哦!"
            }
            var manufacturer = $("#manufacturer").val();
            var jianpin = $("#jianpin").val();
            var productType =$("#productType").find("option:selected").val();
            if(isNullOrBlank(productType) || productType==0){
                return "分类必选哦!"
            }

            var unit =$("#unit").find("option:selected").text();
            var priceSale = $("#priceSale").val();
            //关于价格填写的校验
            if(!$.isNumeric(priceSale)){
                return "请输入正确数字哦!";
            }
            var numPriceSale = priceSale.value/1;
            if(numPriceSale.toFixed(2) < numPriceSale){
                return "金额精确到分哦!"
            }
            /*if(priceSale.toFixed(2)<priceSale){
                return "价格精确到分哦!"
            }*/
            if(isNullOrBlank(priceSale)){
                return "销售价必填哦!"
            }
            var pricePublic = $("#pricePublic").val();
            if(isNullOrBlank(pricePublic)){
                return "公费价必填哦!"
            }
            if(!$.isNumeric(pricePublic)){
                return "请输入正确数字哦!";
            }
            var numPricePublic = pricePublic.value/1;
            if(numPricePublic.toFixed(2) < numPricePublic){
                return "金额精确到分哦!"
            }
            var subjectCode =$("#subjectCode").find("option:selected").val();
            if(isNullOrBlank(subjectCode) || subjectCode==0){
                return "收费科目必选哦!"
            }
        }
    //编辑药品
	function editProduct() {

         $("#saveBtn").attr("disabled","disabled");
         var id = $("#id").val();
         var unitVal = $("#unit").find("option:selected").val();

         if(isNullOrBlank(id)){
             layer.alert("药品编号不存在", {icon: 5,time: 2000, title:'提示'});
             $("#saveBtn").removeAttr("disabled");
             return;
         }
         var checkMsg = checkParam();
         if(!isNullOrBlank(checkMsg)){
             layer.alert(checkMsg, {icon: 5,time: 2000, title:'提示'});
             $("#saveBtn").removeAttr("disabled");
             return;
         }

         var priceSaleInt = parseInt($("#priceSale").val()*100);

        var pricePublicInt = parseInt($("#pricePublic").val()*100);

        CommonUtils.ajaxPostSubmit("his/product/operateProduct", {
        			"id" : $("#id").val(),
                    "operateType" : 0,
                    "productName" :  $("#tags").val(),
        			"packing" : $("#packing").val(),
                    "jkCode" : $("#jkCode").val(),
                    "barCode" : $("#barCode").val(),
                    "manufacturer" : $("#manufacturer").val(),
                    "jianpin" : $("#jianpin").val(),
                    "productTypeFirst" : $("#productType").find("option:selected").val(),
                    "unit" : $("#unit").find("option:selected").val()==0?"":$("#unit").find("option:selected").text(),
                    "priceSale" : priceSaleInt,
                    "pricePublic" : pricePublicInt,
                    "subjectCode" : $("#subjectCode").find("option:selected").val()
                }, function(data){
                    if(data){
                        if(data.code == 0){
                            layer.alert("修改成功", {icon: 6,time: 3000, title:'提示'});
                            $.callBackParent("his/product/productList",true,callBack);
                            /*setTimeout(function(){
                                $.callBackParent("his/product/productList",true,callBack);
                            }, 3000);*/

                        }else {
                            layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					        $("#saveBtn").removeAttr("disabled");
                        }
                    }
                }
        );
    }
	
    //添加药品
    function addProduct() {
        $("#saveBtn").attr("disabled","disabled");
        var checkMsg = checkParam();
        if(!isNullOrBlank(checkMsg)){
            layer.alert(checkMsg, {icon: 5,time: 2000, title:'提示'});
            $("#saveBtn").removeAttr("disabled");
            return;
        }

        var priceSaleInt = parseInt($("#priceSale").val()*100);

        var pricePublicInt = parseInt($("#pricePublic").val()*100);


        CommonUtils.ajaxPostSubmit("his/product/operateProduct", {
                "operateType" : 1,
                "productName" :  $("#tags").val(),
                "packing" : $("#packing").val(),
                "jkCode" : $("#jkCode").val(),
                "barCode" : $("#barCode").val(),
                "manufacturer" : $("#manufacturer").val(),
                "jianpin" : $("#jianpin").val(),
                "productTypeFirst" : $("#productType").find("option:selected").val(),
                "unit" : $("#unit").find("option:selected").val()==0?"":$("#unit").find("option:selected").text(),
                "priceSale" : priceSaleInt,
                "pricePublic" : pricePublicInt,
                "subjectCode" : $("#subjectCode").find("option:selected").val()
            }, function(data){
                if(data){
                    if(data.code == 0){
                        layer.alert("添加成功", {icon: 6,time: 3000, title:'提示'});
                        /*setTimeout(function(){
                            $.callBackParent("his/product/productList",true,callBack);
                        }, 3000);*/
                        $.callBackParent("his/product/productList",true,callBack);

                    }else {
                        layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
                        $("#saveBtn").removeAttr("disabled");
                    }
                }
            }
        );
    }
    function isNullOrBlank(obj){
        return obj == undefined || obj == null || $.trim(obj).length == 0;
    }
        $("#tags").autocomplete({
            minLength: 0,
            source: function( request, response ) {
                var term = $.trim(request.term);
                if(term==""|| term==undefined)    return;

                console.log(term);
                $.ajax({
                    url : 'his/product/searchDrug',
                    type : 'POST',
                    dataType:"json",
                    data: {
                        "keyWord":term

                    },
                    success: function (result) {
                        var data = result.data.pageResult;
                        console.log(data);
                        var rs = data;
                        for(var i in data){
                            data[i].value=data[i].nameCN;
                        }
                        console.log(rs);
                        response( rs );
                    }
                });
            },
            focus: function( event, ui ) {
                return false;
            },
            select: function( event, ui ) {
                fillInput(ui.item);
//                $("#autoFill").val("");
                return false;
            }
        });
    function fillInput(item) {
        $('#tags').val(item.nameCN);
        $('#jkCode').val(item.productCode);
        $("#manufacturer").val(item.manufacturer);
        $("#packing").val(item.packing);
        $("#jianpin").val(codefans_net_CC2PY($('#tags').val()));
    }
/*    function search(str) {
//	    alert(str.value);
        var key = str.value;
//        alert(key);
	    if(isNullOrBlank(key)){
	        return;
        }
        var availableTags = [];
        $.ajax({
            url : 'his/product/searchDrug',
            type : 'POST',
            dataType:"json",
            data: {
                "keyWord":str.value
            },
            success : function(result){
                var json = result.data.pageResult;
                //availableTags = result.data.pageResult;
                for(var i in json){
                    var temp={
                        value:json[i]["nameCN"],
                        key:json[i]["productCode"],
                    }
                    availableTags.push(temp);

                }
//                console.log(availableTags);
                $( "#tags" ).autocomplete({
                    source: availableTags
                });


            }
        });

    }
        $('#tags').on( "autocompleteselect", function( event, ui ) {
//            console.log(event);
//            console.log(ui);
            $('#productName').val($(this).val());
            $('#jkCode').val(ui.item.key);
        } );*/
        function callBack(parent){
            parent.refreshData("listTable");
        }
	function toList() {
        $.callBackParent("his/product/productList",true,callBack);
    }
	</script>	
	

</body>
</html>
