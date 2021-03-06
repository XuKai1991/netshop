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
                    <form class="form-inline" role="form" id="searchForm" action="/seller/order/list">
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
                                <#if orderStatusMap??>
                                    <#list orderStatusMap?keys as key>
                                        <option value="${key!}"
                                        <#if (s_order.orderStatus)?? && s_order.orderStatus == key>
                                            selected
                                        </#if>>${orderStatusMap[key]!}
                                        </option>
                                    </#list>
                                </#if>
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
                        <#if orderDTOPage.getNumberOfElements() == 0>
                        <tr align="center">
                            <td colspan="11"><a style="color: #ff000f">抱歉，找不到符合条件的订单 !</a></td>
                        </tr>
                        <#else>
                            <#list orderDTOPage.content as orderDTO>
                                <tr align="center">
                                    <td style="vertical-align:middle">${orderDTO.orderId}</td>
                                    <td style="vertical-align:middle;width: 70px">${orderDTO.buyerName}</td>
                                    <td style="vertical-align:middle">${orderDTO.buyerPhone}</td>
                                    <td style="vertical-align:middle;width: 290px">${orderDTO.buyerAddress}</td>
                                    <td style="vertical-align:middle">${orderDTO.orderAmount?c}</td>
                                    <td style="vertical-align:middle">${orderDTO.orderActualAmount?c}</td>
                                    <td style="vertical-align:middle; width:64px">${orderDTO.getOrderStatusEnum().message}</td>
                                    <td style="vertical-align:middle">${orderDTO.createTime}</td>
                                <#--<td style="vertical-align:middle; width:170px">-->
                                    <td style="vertical-align:middle; width:245px">
                                        <a href="/seller/order/detail?orderId=${orderDTO.orderId}"
                                           type="button"
                                           class="btn btn-default btn-primary">详情</a>
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
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>

                <#if orderDTOPage.getNumberOfElements() != 0>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">首页</a></li>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="javascript:search(1, ${size})">首页</a></li>
                            <li><a href="javascript:search(${currentPage - 1}, ${size})">上一页</a></li>
                        </#if>

                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                                <li><a href="javascript:search(${index}, ${size})">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                            <li><a href="javascript:search(${currentPage + 1}, ${size})">下一页</a></li>
                            <li><a href="javascript:search(${orderDTOPage.getTotalPages()}, ${size})">尾页</a>
                        </#if>
                        </ul>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>

<#-- 订单操作 -->
    <#include "../order/action.ftl">
</body>
</html>