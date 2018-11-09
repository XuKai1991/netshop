<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                卖家管理系统
            </a>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i
                    class="fa fa-fw fa-plus"></i>订单管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/netshop/seller/order/list">列表</a></li>
                <li><a href="javascript:refreshLogistics()">刷新物流</a></li>
            </ul>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i
                    class="fa fa-fw fa-plus"></i>商品管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/netshop/seller/product/list">列表</a></li>
                <li><a href="/netshop/seller/product/index">新增</a></li>
            </ul>
        </li>
        <li>
            <a href="/netshop/seller/category/list"><i class="fa fa-fw fa-list-alt"></i>类目管理</a>
        </li>
    <#--<li class="dropdown open">-->
    <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i-->
    <#--class="fa fa-fw fa-plus"></i>统计<span class="caret"></span></a>-->
    <#--<ul class="dropdown-menu" role="menu">-->
    <#--<li class="dropdown-header">操作</li>-->
    <#--<li><a href="#">销量</a></li>-->
    <#--<li><a href="#">财务</a></li>-->
    <#--</ul>-->
    <#--</li>-->
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i
                    class="fa fa-fw fa-plus"></i>系统管理<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/netshop/seller/listBuyer/">买家管理</a></li>
            <#--<li><a href="#">管理员账号</a></li>-->
            </ul>
        </li>
        <li>
            <a href="/netshop/seller/logout"><i class="fa fa-fw fa-list-alt"></i>注销</a>
        </li>
    </ul>
</nav>

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

<script>
    function refreshLogistics() {
        $.get("/netshop/express/refreshLogistics", function (result) {
            if (result.msg == "success") {
                $("#hintModalTitle").text("刷新物流");
                if (result.msg == "success") {
                    $("#hintModalBody").text("刷新物流成功！");
                }
                $("#hintModalCancel").hide();
                $("#hintModalConfirm").attr("href", "javascript:location.reload()");
                $("#hintModal").modal();
            } else {
                $("#hintModalBody").text("刷新物流失败，请重试！");
            }
        });
    }

</script>