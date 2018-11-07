<!DOCTYPE html>
<html lang="en">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix" style="margin-bottom:15px">
                <div class="col-md-8 column">
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">订单编号</th>
                            <th style="text-align: center">应付金额</th>
                            <th style="text-align: center">实付金额</th>
                            <th style="text-align: center">物流详情</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr align="center">
                            <td style="vertical-align:middle">${orderDTO.orderId}</td>
                            <td style="vertical-align:middle">${orderDTO.orderAmount?c}</td>
                            <td style="vertical-align:middle">${orderDTO.orderActualAmount?c}</td>
                            <td style="vertical-align:middle">圆通
                                <a href="#"
                                   type="button"
                                   class="btn-sm btn-default btn-primary">查看详情</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">商品货号</th>
                            <th style="text-align: center">商品名称</th>
                            <th style="text-align: center">商品图</th>
                            <th style="text-align: center">单价</th>
                            <th style="text-align: center">进价</th>
                            <th style="text-align: center">颜色</th>
                            <th style="text-align: center">尺码</th>
                            <th style="text-align: center">数量</th>
                            <th style="text-align: center">总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                        <tr align="center">
                            <td style="vertical-align:middle">${orderDetail.productId}</td>
                            <td style="vertical-align:middle">${orderDetail.productName}</td>
                            <td style="vertical-align:middle">
                                <img height="150" width="150" src="${(orderDetail.productImgMd)!''}" alt="">
                            </td>
                            <td style="vertical-align:middle">${orderDetail.productPrice?c}</td>
                            <td style="vertical-align:middle">${orderDetail.productPurchasePrice?c}</td>
                            <td style="vertical-align:middle">${orderDetail.productColor}</td>
                            <td style="vertical-align:middle">${orderDetail.productSize}</td>
                            <td style="vertical-align:middle">${orderDetail.productQuantity}</td>
                            <td style="vertical-align:middle">${(orderDetail.productQuantity * orderDetail.productPrice)?c}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--操作-->
                <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().message == "未支付">
                    <a href="javascript:preEditOrder(${orderDTO.orderId}, ${orderDTO.orderAmount?c}, ${orderDTO.orderActualAmount?c})"
                       type="button"
                       class="btn btn-default btn-primary">改价</a>
                    <a href="javascript:editOrderPayed(${orderDTO.orderId})"
                       type="button"
                       class="btn btn-default btn-primary">已付</a>
                </#if>
                <#if orderDTO.getOrderStatusEnum().message == "已支付">
                    <a href="/netshop/seller/order/send?orderId=${orderDTO.orderId}"
                       type="button"
                       class="btn btn-default btn-primary">发货</a>
                </#if>
                <#if orderDTO.getOrderStatusEnum().message == "未支付" || orderDTO.getOrderStatusEnum().message == "已支付">
                    <a href="javascript:preCancelOrder(${orderDTO.orderId})" type="button"
                       class="btn btn-default btn-danger">取消</a>
                </#if>
                <#if orderDTO.getOrderStatusEnum().message == "买家删除" || orderDTO.getOrderStatusEnum().message == "已取消" || orderDTO.getOrderStatusEnum().message == "已收货">
                    <a href="javascript:preDeleteOrder(${orderDTO.orderId})" type="button"
                       class="btn btn-default btn-danger">删除</a>
                </#if>
                </div>
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

<#-- 查看物流详情 -->
<div id="expressModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
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

<script>

    // 完结订单确认提示
    function preFinishOrder(id) {
        $("#hintModalTitle").text("完结订单");
        $("#hintModalBody").text("您确定要完结该订单吗？");
        $("#hintModalConfirm").attr("href", "/netshop/seller/order/finish?orderId=" + id);
        $("#hintModal").modal();
    }

    // 取消订单确认提示
    function preCancelOrder(id) {
        $("#hintModalTitle").text("取消订单");
        $("#hintModalBody").text("您确定要取消该订单吗？");
        $("#hintModalConfirm").attr("href", "/netshop/seller/order/cancel?orderId=" + id);
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
</script>
</body>
</html>