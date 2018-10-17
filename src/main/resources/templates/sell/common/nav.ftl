<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                卖家管理系统
            </a>
        </li>
        <li>
            <a href="/netshop/seller/order/list"><i class="fa fa-fw fa-list-alt"></i>订单</a>
        </li>
        <li>
            <a href="/netshop/seller/category/list"><i class="fa fa-fw fa-list-alt"></i>类目</a>
        </li>
        <li class="dropdown open">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i
                    class="fa fa-fw fa-plus"></i>商品<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/netshop/seller/product/list">列表</a></li>
                <li><a href="/netshop/seller/product/index">新增</a></li>
            </ul>
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
                    class="fa fa-fw fa-plus"></i>系统<span class="caret"></span></a>
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