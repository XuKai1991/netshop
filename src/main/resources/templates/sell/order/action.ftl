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

<#-- 订单发货 -->
<div id="sendGoodsModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">订单发货</h4>
            </div>
            <div class="modal-body">
                <input id="orderIdForExpress" type="hidden" class="form-control" aria-describedby="basic-addon1"/>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">快递</span>
                    <select name="expressShipper" id="expressShipper" class="form-control">
                        <#if expressShipperEnumMap??>
                            <#list expressShipperEnumMap?keys as key>
                                <option value="${key!}">${expressShipperEnumMap[key]!}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                </br>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">单号</span>
                    <input id="expressNumber" type="text" class="form-control" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <a type="button" class="btn btn-primary" href="javascript:sendGoods()">确定</a>
            </div>
        </div>
    </div>
</div>

<#-- 查看物流 -->
<div id="viewLogisticsModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 id="viewLogisticsModalTitle" class="modal-title">物流信息</h4>
            </div>
            <div class="modal-body">
                <p id="viewLogisticsModalBody"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
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

    // 订单发货确认提示
    function preSendGoods(orderId) {
        $("#orderIdForExpress").val(orderId);
        $('#sendGoodsModal').modal();
    }

    // 将订单改为已支付
    // TODO
    // 手动确认支付是目前因为未接入线上支付的临时方法
    // 后期需要删除
    function editOrderPayed(orderId) {
        $.post("/netshop/seller/order/pay", {
            orderId: orderId,
        }, function (result) {
            $("#hintModalTitle").text("支付订单");
            if (result.msg == "success") {
                $("#hintModalBody").text("订单成功支付！");
            } else {
                $("#hintModalBody").text("订单支付失败，请重试！");
            }
            $("#hintModalCancel").hide();
            $("#hintModalConfirm").attr("href", "javascript:location.reload()");
            $("#hintModal").modal();
        });
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

    // 订单发货
    function sendGoods() {
        var orderId = $("#orderIdForExpress").val();
        var expressShipper = $("#expressShipper").val();
        var expressNumber = $("#expressNumber").val();
        $.post("/netshop/seller/order/send", {
            orderId: orderId,
            expressShipper: expressShipper,
            expressNumber: expressNumber
        }, function (result) {
            $('#sendGoodsModal').modal("hide");
            $("#hintModalTitle").text("订单发货");
            if (result.msg == "success") {
                $("#hintModalBody").text("订单发货成功！");
            } else {
                $("#hintModalBody").text("订单发货失败，请重试！");
            }
            $("#expressShipper").val("");
            $("#expressNumber").val("");
            $("#hintModalCancel").hide();
            $("#hintModalConfirm").attr("href", "javascript:location.reload()");
            $("#hintModal").modal();
        });
    }

    /**
     * 查看物流
     */
    function viewLogistics(orderId) {
        $.get("/netshop/express/findOne", {
            orderId: orderId
        }, function (result) {
            if (result.msg == "success") {
                var info;
                var logisticsDetail = result.data.logisticsDetail;
                if (logisticsDetail != "") {
                    var logisticsDetailJsonObject = $.parseJSON(logisticsDetail);
                    var detailList = logisticsDetailJsonObject.data;
                    info = "<h2>" + result.data.expressShipper + "</h2>";
                    for (var i = 0; i < detailList.length; i++) {
                        info = info + "<br>" + detailList[i].time + "&nbsp;&nbsp;&nbsp;&nbsp;" + detailList[i].context;
                    }
                } else {
                    info = "<h3>抱歉，暂无物流信息！</h3>";
                }
                $("#viewLogisticsModalBody").html(info);
            } else {
                $("#viewLogisticsModalBody").html("查看物流失败，请重试！");
            }
            $("#viewLogisticsModal").modal();
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