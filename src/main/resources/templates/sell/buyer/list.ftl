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
                    <form class="form-inline" role="form" id="searchForm" action="/netshop/seller/listBuyer">
                        <input id="s_page" name="page" type="hidden" class="form-control"
                               aria-describedby="basic-addon1"/>
                        <input id="s_size" name="size" type="hidden" class="form-control"
                               aria-describedby="basic-addon1"/>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">用户编号</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="buyerId" id="s_buyerId"
                                   value="<#if s_buyer.buyerId??>${s_buyer.buyerId}</#if>"
                                   style="width: 200px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">用户名</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="username" id="s_username"
                                   value="<#if s_buyer.username??>${s_buyer.username}</#if>"
                                   style="width: 110px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">手机</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="phone" id="s_phone"
                                   value="<#if s_buyer.phone??>${s_buyer.phone}</#if>"
                                   style="width: 120px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">邮箱</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="email" id="s_email"
                                   value="<#if s_buyer.email??>${s_buyer.email}</#if>"
                                   style="width: 160px">
                        </div>
                        <a type="button" class="btn btn-default btn-primary" href="javascript:resetSearchValue()">清空</a>
                        <button id="searchSubmit" type="submit" class="btn btn-default btn-primary">搜索</button>
                        <a href="javascript:preAddBuyer()" type="button"
                           class="btn btn-default btn-danger">新增买家用户</a>
                    </form>
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">用户编号</th>
                            <th style="text-align: center">用户名</th>
                            <th style="text-align: center">密码</th>
                            <th style="text-align: center">手机</th>
                            <th style="text-align: center">邮箱</th>
                            <th style="text-align: center">创建时间</th>
                            <th style="text-align: center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if buyerInfoPage.getNumberOfElements() == 0>
                            <tr align="center">
                                <td colspan="11"><a style="color: #ff000f">抱歉，找不到符合条件的用户 !</a></td>
                            </tr>
                            <#else>
                                <#list buyerInfoPage.content as buyerInfo>
                                <tr align="center">
                                    <td style="vertical-align:middle">${buyerInfo.buyerId}</td>
                                    <td style="vertical-align:middle">${buyerInfo.username}</td>
                                    <td style="vertical-align:middle">${buyerInfo.password}</td>
                                    <td style="vertical-align:middle">${buyerInfo.phone}</td>
                                    <td style="vertical-align:middle">${buyerInfo.email}</td>
                                    <td style="vertical-align:middle">${buyerInfo.createTime}</td>
                                    <td style="vertical-align:middle; width:170px">
                                        <a href="javascript:preEditBuyer(${buyerInfo.buyerId})"
                                           type="button"
                                           class="btn btn-default btn-danger">修改</a>
                                        <a href="javascript:preDeleteBuyer(${buyerInfo.buyerId})" type="button"
                                           class="btn btn-default btn-danger">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>

                <#if buyerInfoPage.getNumberOfElements() != 0>
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

                        <#list 1..buyerInfoPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                                <li><a href="javascript:search(${index}, ${size})">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte buyerInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                            <li><a href="javascript:search(${currentPage + 1}, ${size})">下一页</a></li>
                            <li><a href="javascript:search(${buyerInfoPage.getTotalPages()}, ${size})">尾页</a>
                        </#if>
                        </ul>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>

<#-- 修改买家信息弹窗 -->
<div id="editModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
     aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <form id="editForm" name="editForm" action="/netshop/buyer/save" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span>
                    </button>
                    <h4 id="editModalTitle" class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <input id="buyerIdForEdit" type="hidden" class="form-control" aria-describedby="basic-addon1"
                           name="buyerId"/>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">用户名</span>
                        <input id="usernameForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="username"/>
                    </div>
                    </br>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">密码</span>
                        <input id="passwordForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="password"/>
                    </div>
                    </br>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">手机</span>
                        <input id="phoneForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="phone"/>
                    </div>
                    </br>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">邮箱</span>
                        <input id="emailForEdit" type="text" class="form-control" aria-describedby="basic-addon1"
                               name="email"/>
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

<script>
    $(function () {
        $("#editForm").ajaxForm(function (result) {
            if ($("#buyerIdForEdit").val() === "") {
                $("#hintModalTitle").text("新增买家用户");
                if (result.msg === "success") {
                    $("#hintModalBody").text("买家用户添加成功！");
                } else {
                    $("#hintModalBody").text("买家用户添加失败，" + result.msg + ",请重试！");
                }
            } else {
                $("#hintModalTitle").text("修改买家用户");
                if (result.msg === "success") {
                    $("#hintModalBody").text("买家用户修改成功！");
                } else {
                    $("#hintModalBody").text("买家用户修改失败，" + result.msg + ",请重试！");
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

    // 添加类目提示
    function preAddBuyer() {
        $("#editModalTitle").text("新增买家用户");
        $("#buyerIdForEdit").val("");
        $("#usernameForEdit").val("");
        $("#usernameForEdit").removeAttr("readonly");
        $("#passwordForEdit").val("");
        $("#phoneForEdit").val("");
        $("#emailForEdit").val("");
        $("#emailForEdit").removeAttr("readonly");
        $('#editModal').modal();
    }

    // 修改类目提示
    function preEditBuyer(buyerId) {
        $("#editModalTitle").text("修改买家用户");
        $.get("/netshop/buyer/findByBuyerId", {buyerId: buyerId}, function (result) {
            if (result.msg != "success") {
                return;
            }
            var buyerInfo = result.data;
            $("#buyerIdForEdit").val(buyerInfo.buyerId);
            $("#usernameForEdit").val(buyerInfo.username);
            $("#usernameForEdit").attr("readonly", "readonly");
            $("#passwordForEdit").val(buyerInfo.password);
            $("#phoneForEdit").val(buyerInfo.phone);
            $("#emailForEdit").val(buyerInfo.email);
            $("#emailForEdit").attr("readonly", "readonly");
            $('#editModal').modal();
        });
    }

    // 删除买家提示
    function preDeleteBuyer(buyerId) {
        $("#hintModalTitle").text("删除买家用户");
        $("#hintModalBody").text("您确定要删除该买家用户吗？");
        $("#hintModalConfirm").attr("href", "/netshop/seller/deleteBuyer?buyerId=" + buyerId);
        $("#hintModal").modal();
    }

    /**
     * 清空搜索
     */
    function resetSearchValue() {
        $("#s_buyerId").val("");
        $("#s_username").val("");
        $("#s_phone").val("");
        $("#s_email").val("");
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
</body>
</html>