<html xmlns="http://www.w3.org/1999/html">
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <#--<nav class="navbar navbar-default navbar-static">-->
                        <#--<div class="container-fluid">-->
                            <#--<div class="navbar-header">-->
                                <#--<button class="navbar-toggle collapsed" type="button" data-toggle="collapse"-->
                                        <#--data-target=".bs-example-js-navbar-collapse">-->
                                    <#--<span class="sr-only">Toggle navigation</span>-->
                                    <#--<span class="icon-bar"></span>-->
                                    <#--<span class="icon-bar"></span>-->
                                    <#--<span class="icon-bar"></span>-->
                                <#--</button>-->
                                <#--<a class="navbar-brand" href="#">订单选项</a>-->
                            <#--</div>-->
                            <#--<div class="collapse navbar-collapse bs-example-js-navbar-collapse">-->
                                <#--<ul class="nav navbar-nav">-->
                                    <#--<li class="dropdown open">-->
                                        <#--<a id="drop1" href="#" class="dropdown-toggle" data-toggle="dropdown"-->
                                           <#--role="button" aria-haspopup="true" aria-expanded="true">-->
                                            <#--订单状态-->
                                            <#--<span class="caret"></span>-->
                                        <#--</a>-->
                                        <#--<ul class="dropdown-menu" aria-labelledby="drop1">-->
                                            <#--<li><a href="#">新订单</a></li>-->
                                            <#--<li role="separator" class="divider"></li>-->
                                            <#--<li><a href="#">已取消</a></li>-->
                                            <#--<li role="separator" class="divider"></li>-->
                                            <#--<li><a href="#">已完结</a></li>-->
                                            <#--<li role="separator" class="divider"></li>-->
                                            <#--<li><a href="#">卖家删除</a></li>-->
                                        <#--</ul>-->
                                    <#--</li>-->

                                <#--</ul>-->
                            <#--</div>-->
                        <#--</div>-->
                    <#--</nav>-->

                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">订单编号</th>
                            <th style="text-align: center">姓名</th>
                            <th style="text-align: center">手机号</th>
                            <th style="text-align: center">地址</th>
                            <th style="text-align: center">应付</th>
                            <th style="text-align: center">实付</th>
                            <th style="text-align: center">订单状态</th>
                            <th style="text-align: center">创建时间</th>
                            <th style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                        <tr align="center">
                            <td style="vertical-align:middle">${orderDTO.orderId}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerName}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerPhone}</td>
                            <td style="vertical-align:middle">${orderDTO.buyerAddress}</td>
                            <td style="vertical-align:middle">${orderDTO.orderAmount}</td>
                            <td style="vertical-align:middle">${orderDTO.orderActualAmount}</td>
                            <td style="vertical-align:middle">${orderDTO.getOrderStatusEnum().message}</td>
                            <td style="vertical-align:middle">${orderDTO.createTime}</td>
                            <td>
                                <a href="/netshop/seller/order/detail?orderId=${orderDTO.orderId}" type="button"
                                   class="btn btn-default btn-primary">详情</a>
                                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                <a href="javascript:preCancelOrder(${orderDTO.orderId})" type="button"
                                   class="btn btn-default btn-danger">取消</a>
                                <a href="javascript:preEditOrder(${orderDTO.orderId}, ${orderDTO.orderAmount}, ${orderDTO.orderActualAmount})"
                                   type="button"
                                   class="btn btn-default btn-danger">修改</a>
                                </#if>
                                <#if orderDTO.getOrderStatusEnum().message == "买家删除" || orderDTO.getOrderStatusEnum().message == "已取消" || orderDTO.getOrderStatusEnum().message == "完结">
                                <a href="javascript:preDeleteOrder(${orderDTO.orderId})" type="button"
                                   class="btn btn-default btn-danger">删除</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">首页</a></li>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/netshop/seller/order/list?page=1&size=${size}">首页</a></li>
                        <li><a href="/netshop/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                                <li><a href="/netshop/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                        <li class="disabled"><a href="#">尾页</a></li>
                    <#else>
                        <li><a href="/netshop/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        <li>
                            <a href="/netshop/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}">尾页</a>
                        </li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                您有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button"
                        class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button onclick="javascrtpt:window.location='/netshop/seller/order/list'"
                        type="button"
                        class="btn btn-primary">查看新的订单
                </button>
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

<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/netshop/mp3/song.mp3" type="audio/mpeg"/>
</audio>

<script>
    var webSocket = null;

    if ('WebSocket' in window) {
        webSocket = new WebSocket("ws://localhost:8085/netshop/webSocket");
    } else {
        alert("该浏览器不支持websocket！");
    }

    webSocket.onopen = function (event) {
        console.log("建立连接");
    }

    webSocket.onclose = function (event) {
        console.log("关闭连接");
    }

    webSocket.onmessage = function (event) {
        console.log("收到消息：" + event.data)
        // 弹窗提醒、播放音乐
        $("#myModal").modal('show');
        document.getElementById('notice').play();
    }

    webSocket.onerror = function () {
        console.log("websocket通信发生错误！");
    }

    webSocket.onbeforeunload = function () {
        webSocket.close();
    }

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
</script>
</body>
</html>