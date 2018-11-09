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
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">订单编号</th>
                            <th style="text-align: center">姓名</th>
                            <th style="text-align: center">手机</th>
                            <th style="text-align: center">应付</th>
                            <th style="text-align: center">实付</th>
                            <th style="text-align: center;width: 350px">地址</th>
                            <th style="text-align: center;width: 200px">备注</th>
                            <th style="text-align: center">创建时间</th>
                            <th style="text-align: center">更新时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr align="center">
                            <td style="vertical-align:middle">${orderDTO.orderId}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerName}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerPhone}</td>
                            <td style="vertical-align:middle">${orderDTO.orderAmount?c}</td>
                            <td style="vertical-align:middle">${orderDTO.orderActualAmount?c}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerAddress}</td>
                            <td style="vertical-align:middle"><#if orderDTO.orderRemark??>${orderDTO.orderRemark}</#if></td>
                            <td style="vertical-align:middle">${orderDTO.createTime}</td>
                            <td style="vertical-align:middle">${orderDTO.updateTime}</td>
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
                        <a href="javascript:preSendGoods(${orderDTO.orderId})"
                           type="button"
                           class="btn btn-default btn-primary">发货</a>
                    </#if>
                    <#if orderDTO.getOrderStatusEnum().message == "已发货">
                        <a href="javascript:viewLogistics(${orderDTO.orderId})"
                           type="button"
                           class="btn btn-default btn-primary">查看物流</a>
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

<#-- 订单操作 -->
    <#include "../order/action.ftl">
</body>
</html>