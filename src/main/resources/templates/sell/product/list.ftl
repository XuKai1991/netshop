<html>
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
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th>货号</th>
                            <th>名称</th>
                            <th>主图</th>
                            <th>展示图</th>
                            <th>单价</th>
                            <th>进价</th>
                            <th>颜色</th>
                            <th>尺码</th>
                            <th>库存</th>
                        <#--<th>描述</th>-->
                            <th>类目</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                        <tr align="center">
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="50" width="50" src="${productInfo.productImgMd}" alt=""></td>
                            <td><img height="50" width="50" src="${productInfo.productDetailImg}" alt=""></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productPurchasePrice}</td>
                            <td>${productInfo.productColor}</td>
                            <td>${productInfo.productSize}</td>
                            <td>${productInfo.productStock}</td>
                        <#--<td>${productInfo.productDescription}</td>-->
                            <td>
                                <#list categoryList as category>
                                    <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                        ${category.getCategoryName()}
                                    </#if>
                                </#list>
                            </td>
                            <td>${productInfo.getProductStatusEnum().message}</td>
                            <td>${productInfo.getCreateTime()}</td>
                            <td>${productInfo.getUpdateTime()}</td>
                            <td><a href="/netshop/seller/product/index?productId=${productInfo.productId}" type="button"
                                   class="btn btn-default btn-danger">修改</a></td>
                            <td>
                                <#if productInfo.getProductStatusEnum().message == "在售">
                                    <a href="/netshop/seller/product/off_sale?productId=${productInfo.productId}"
                                       type="button"
                                       class="btn btn-default btn-danger">下架</a>
                                <#else>
                                    <a href="/netshop/seller/product/on_sale?productId=${productInfo.productId}"
                                       type="button"
                                       class="btn btn-default btn-danger">上架</a>
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
                        <li><a href="/netshop/seller/product/list?page=1&size=${size}">首页</a></li>
                        <li><a href="/netshop/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..productInfoPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                                <li><a href="/netshop/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte productInfoPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                        <li class="disabled"><a href="#">尾页</a></li>
                    <#else>
                        <li><a href="/netshop/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                        <li><a href="/netshop/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">尾页</a>
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
                <button onclick="javascrtpt:window.location='/netshop/seller/order/list'" type="button"
                        class="btn btn-primary">查看新的订单
                </button>
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

</script>
</body>
</html>