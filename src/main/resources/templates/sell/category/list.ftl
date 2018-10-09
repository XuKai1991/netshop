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
                    <a href="javascript:preAddCategory()" type="button"
                       class="btn btn-default btn-primary">新增</a>
                    </br></br>
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                        <#--<th>类目id</th>-->
                            <th>类目编号</th>
                            <th>类目名称</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list categoryList as category>
                        <tr align="center">
                        <#--<td>${category.categoryId}</td>-->
                            <td>${category.categoryType}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.createTime}</td>
                            <td>${category.updateTime}</td>
                            <td>
                                <a href="javascript:preEditCategory(${category.categoryId})" type="button"
                                   class="btn btn-default btn-danger">修改</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<#-- 修改商品类目弹窗 -->
<div id="editModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <form id="editForm" name="editForm" action="/netshop/seller/category/save" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                    <h4 id="editModalTitle" class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <input id="categoryIdForEdit" type="hidden" class="form-control" aria-describedby="basic-addon1"
                           name="categoryId"/>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">类目编号</span>
                        <input id="categoryTypeForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="categoryType"/>
                    </div>
                    </br>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">类目名称</span>
                        <input id="categoryNameForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="categoryName"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">确定</button>
                </div>
            </form>
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

<#-- 新订单弹窗 -->
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
    ;

    webSocket.onopen = function (event) {
        console.log("建立连接");
    };

    webSocket.onclose = function (event) {
        console.log("关闭连接");
    };

    webSocket.onmessage = function (event) {
        console.log("收到消息：" + event.data)
        // 弹窗提醒、播放音乐
        $("#myModal").modal('show');
        document.getElementById('notice').play();
    };

    webSocket.onerror = function () {
        console.log("websocket通信发生错误！");
    };

    webSocket.onbeforeunload = function () {
        webSocket.close();
    };

    $(function () {
        $("#editForm").ajaxForm(function (result) {
            if ($("#categoryIdForEdit").val() == "") {
                $("#hintModalTitle").text("新增类目");
                if (result.msg == "success") {
                    $("#hintModalBody").text("类目添加成功！");
                } else {
                    $("#hintModalBody").text("类目添加失败，" + result.msg + ",请重试！");
                }
            } else {
                $("#hintModalTitle").text("修改类目");
                if (result.msg == "success") {
                    $("#hintModalBody").text("类目修改成功！");
                } else {
                    $("#hintModalBody").text("类目修改失败，" + result.msg + ",请重试！");
                }
            }
            $("#editModal").modal("hide");
            $("#hintModalCancel").hide();
            $("#hintModalConfirm").attr("href", "javascript:location.reload()");
            $("#hintModal").modal();
        });

        $("#editForm").bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                categoryTypeForEdit: {
                    message: '类目编号验证失败',
                    validators: {
                        notEmpty: {
                            message: '类目编号不能为空'
                        }
                    }
                },
                categoryNameForEdit: {
                    message: '类目名称验证失败',
                    validators: {
                        notEmpty: {
                            message: '类目名称不能为空'
                        }
                    }
                }
            }
        });
    });

    function preAddCategory() {
        $("#editModalTitle").text("新增类目");
        $("#categoryTypeForEdit").removeAttr("readonly");
        $("#categoryIdForEdit").val("");
        $("#categoryTypeForEdit").val("");
        $("#categoryNameForEdit").val("");
        $('#editModal').modal();
    }

    function preEditCategory(categoryId) {
        $("#editModalTitle").text("修改类目");
        $.get("/netshop/seller/category/detail", {categoryId: categoryId}, function (result) {
            if (result.msg == "success") {
                var category = result.data;
                $("#categoryIdForEdit").val(category.categoryId);
                $("#categoryTypeForEdit").val(category.categoryType);
                $("#categoryTypeForEdit").attr("readonly", "readonly");
                $("#categoryNameForEdit").val(category.categoryName);
                $('#editModal').modal();
            }
        });
    }
</script>
</body>
</html>