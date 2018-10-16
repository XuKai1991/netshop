<html xmlns="http://www.w3.org/1999/html">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include "../common/nav.ftl">
<#--新订单提示-->
    <#include "../common/notice.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <form class="form-inline" role="form" id="searchForm" action="/netshop/seller/order/list">
                        <input id="s_page" name="page" type="hidden" class="form-control"
                               aria-describedby="basic-addon1"/>
                        <input id="s_size" name="size" type="hidden" class="form-control"
                               aria-describedby="basic-addon1"/>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">订单号</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="orderId" id="s_orderId"
                                   value="<#if s_order.orderId??>${s_order.orderId}</#if>"
                                   style="width: 150px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">姓名</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="buyerName" id="s_buyerName"
                                   value="<#if s_order.buyerName??>${s_order.buyerName}</#if>"
                                   style="width: 70px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">地址</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="buyerAddress" id="s_buyerAddress"
                                   value="<#if s_order.buyerAddress??>${s_order.buyerAddress}</#if>"
                                   style="width: 200px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">手机</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="buyerPhone" id="s_buyerPhone"
                                   value="<#if s_order.buyerPhone??>${s_order.buyerPhone}</#if>"
                                   style="width: 115px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">状态</span>
                            <select name="orderStatus" id="s_orderStatus" class="form-control" style="width: 110px">
                                <option value=""
                                    <#if (s_order.orderStatus)?? && s_order.orderStatus == "">
                                        selected
                                    </#if>
                                >全部
                                </option>
                                <option value="0"
                                    <#if (s_order.orderStatus)?? && s_order.orderStatus == "0">
                                        selected
                                    </#if>
                                >新订单
                                </option>
                                <option value="1"
                                    <#if (s_order.orderStatus)?? && s_order.orderStatus == "1">
                                        selected
                                    </#if>
                                >已完结
                                </option>
                                <option value="2"
                                    <#if (s_order.orderStatus)?? && s_order.orderStatus == "2">
                                        selected
                                    </#if>
                                >已取消
                                </option>
                                <option value="3"
                                    <#if (s_order.orderStatus)?? && s_order.orderStatus == "3">
                                        selected
                                    </#if>
                                >卖家删除
                                </option>
                            </select>
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">应付</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="minAmount" id="minAmount" value="<#if minAmount??>${minAmount}</#if>"
                                   style="width: 50px">
                            <span class="input-group-addon" id="basic-addon1">></span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="maxAmount" id="maxAmount" value="<#if maxAmount??>${maxAmount}</#if>"
                                   style="width: 50px">
                        </div>
                        <a type="button" class="btn btn-default btn-primary" href="javascript:resetSearchValue()">清空</a>
                        <button id="searchSubmit" type="submit" class="btn btn-default btn-primary">搜索</button>
                    </form>
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">订单号</th>
                            <th style="text-align: center">姓名</th>
                            <th style="text-align: center">手机</th>
                            <th style="text-align: center">地址</th>
                            <th style="text-align: center">应付</th>
                            <th style="text-align: center">实付</th>
                            <th style="text-align: center">状态</th>
                            <th style="text-align: center">创建时间</th>
                            <th style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if orderMasterPage.getNumberOfElements() == 0>
                        <tr align="center">
                            <td colspan="11"><a style="color: #ff000f">抱歉，找不到符合条件的订单 !</a></td>
                        </tr>
                        <#else>
                            <#list orderMasterPage.content as orderMaster>
                                <tr align="center">
                                    <td style="vertical-align:middle">${orderMaster.orderId}</td>
                                    <td style="vertical-align:middle">${orderMaster.buyerName}</td>
                                    <td style="vertical-align:middle">${orderMaster.buyerPhone}</td>
                                    <td style="vertical-align:middle">${orderMaster.buyerAddress}</td>
                                    <td style="vertical-align:middle">${orderMaster.orderAmount?c}</td>
                                    <td style="vertical-align:middle">${orderMaster.orderActualAmount?c}</td>
                                    <td style="vertical-align:middle">${orderMaster.getOrderStatusEnum().message}</td>
                                    <td style="vertical-align:middle">${orderMaster.createTime}</td>
                                    <td style="vertical-align:middle; width:170px">
                                        <a href="/netshop/seller/order/detail?orderId=${orderMaster.orderId}"
                                           type="button"
                                           class="btn btn-default btn-primary">详情</a>
                                        <#if orderMaster.getOrderStatusEnum().message == "新订单">
                                        <a href="javascript:preCancelOrder(${orderMaster.orderId})" type="button"
                                           class="btn btn-default btn-danger">取消</a>
                                        <a href="javascript:preEditOrder(${orderMaster.orderId}, ${orderMaster.orderAmount?c}, ${orderMaster.orderActualAmount?c})"
                                           type="button"
                                           class="btn btn-default btn-danger">修改</a>
                                        </#if>
                                        <#if orderMaster.getOrderStatusEnum().message == "买家删除" || orderMaster.getOrderStatusEnum().message == "已取消" || orderMaster.getOrderStatusEnum().message == "完结">
                                        <a href="javascript:preDeleteOrder(${orderMaster.orderId})" type="button"
                                           class="btn btn-default btn-danger">删除</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>

                <#if orderMasterPage.getNumberOfElements() != 0>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">首页</a></li>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                        <#--<li><a href="/netshop/seller/order/list?page=1&size=${size}">首页</a></li>-->
                        <#--<li><a href="/netshop/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>-->
                            <li><a href="javascript:search(1, ${size})">首页</a></li>
                            <li><a href="javascript:search(${currentPage - 1}, ${size})">上一页</a></li>
                        </#if>

                        <#list 1..orderMasterPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                            <#--<li><a href="/netshop/seller/order/list?page=${index}&size=${size}">${index}</a></li>-->
                                <li><a href="javascript:search(${index}, ${size})">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte orderMasterPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                        <#--<li><a href="/netshop/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>-->
                        <#--<li>-->
                        <#--<a href="/netshop/seller/order/list?page=${orderMasterPage.getTotalPages()}&size=${size}">尾页</a>-->
                        <#--</li>-->
                            <li><a href="javascript:search(${currentPage + 1}, ${size})">下一页</a></li>
                            <li><a href="javascript:search(${orderMasterPage.getTotalPages()}, ${size})">尾页</a>
                        </#if>
                        </ul>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>

<#-- 操作提示 -->
<div id="hintModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 id="hintModalTitle" class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <p id="hintModalBody"></p>
            </div>
            <div class="modal-footer">
                <button id="hintModalCancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <a id="hintModalConfirm" type="button" class="btn btn-primary" href="#">确定</a>
            </div>
        </div>
    </div>
</div>

<#-- 修改提示 -->
<div id="editModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改订单</h4>
            </div>
            <div class="modal-body">
                <input id="orderIdForEdit" type="hidden" class="form-control" aria-describedby="basic-addon1"/>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">实付金额</span>
                    <input id="amountForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                           disabled="disabled"/>
                </div>
                </br>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">应付金额</span>
                    <input id="actualAmountForEdit" type="text" class="form-control" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <a type="button" class="btn btn-primary" href="javascript:editOrder()">确定</a>
            </div>
        </div>
    </div>
</div>

<script>
    // 取消订单确认提示
    function preCancelOrder(id) {
        $("#hintModalTitle").text("取消订单");
        $("#hintModalBody").text("您确定要取消该订单吗？");
        $("#hintModalConfirm").attr("href", "/netshop/seller/order/cancel?orderId=" + id);
        $("#hintModal").modal();
    }

    // 删除订单确认提示
    function preDeleteOrder(id) {
        $("#hintModalTitle").text("删除订单");
        $("#hintModalBody").text("您确定要删除该订单吗？");
        $("#hintModalConfirm").attr("href", "/netshop/seller/order/delete?orderId=" + id);
        $("#hintModal").modal();
    }

    // 修改订单确认提示
    function preEditOrder(orderId, amount, actualAmount) {
        $("#orderIdForEdit").val(orderId);
        $("#amountForEdit").val(amount);
        $("#actualAmountForEdit").val(actualAmount);
        $('#editModal').modal();
    }

    // 修改订单
    function editOrder() {
        var orderId = $("#orderIdForEdit").val();
        var amount = $("#amountForEdit").val();
        var actualAmount = $("#actualAmountForEdit").val();
        $.post("/netshop/seller/order/edit", {
            orderId: orderId,
            amount: amount,
            actualAmount: actualAmount
        }, function (result) {
            $('#editModal').modal("hide");
            $("#hintModalTitle").text("修改订单");
            if (result.msg == "success") {
                $("#hintModalBody").text("订单修改成功！");
            } else {
                $("#hintModalBody").text("订单修改失败，请重试！");
            }
            $("#hintModalCancel").hide();
            $("#hintModalConfirm").attr("href", "javascript:location.reload()");
            $("#hintModal").modal();
        });
    }

    /**
     * 清空搜索
     */
    function resetSearchValue() {
        $("#s_orderId").val("");
        $("#s_buyerName").val("");
        $("#s_buyerAddress").val("");
        $("#s_buyerPhone").val("");
        $("#s_orderStatus").val("");
        $("#minAmount").val("");
        $("#maxAmount").val("");
    }

    /**
     * 翻页
     * @param s_page
     * @param s_size
     */
    function search(s_page, s_size) {
        $("#s_page").val(s_page);
        $("#s_size").val(s_size);
        $("#searchForm").submit();
    }
</script>
</body>
</html>