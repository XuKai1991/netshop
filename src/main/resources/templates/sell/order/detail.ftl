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
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单编号</th>
                            <th>应付金额</th>
                            <th>实付金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderActualAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品货号</th>
                            <th>商品名称</th>
                            <th>商品图</th>
                            <th>单价</th>
                            <th>颜色</th>
                            <th>尺码</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                        <tr align="center">
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td><img height="100" width="100" src="${(orderDetail.productImgMd)!''}" alt=""></td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productColor}</td>
                            <td>${orderDetail.productSize}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--操作-->
                <div class="col-md-12 column">
                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                    <a href="/netshop/seller/order/finish?orderId=${orderDTO.orderId}" type="button"
                       class="btn btn-default btn-primary">完结订单</a>
                    <a href="/netshop/seller/order/cancel?orderId=${orderDTO.orderId}" type="button"
                       class="btn btn-default btn-danger">取消订单</a>
                </#if>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>