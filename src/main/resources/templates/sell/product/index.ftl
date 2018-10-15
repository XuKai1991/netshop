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
                    <form role="form" method="post" action="/netshop/seller/product/save">
                        <div class="input-group">
                            <span class="input-group-addon">名称</span>
                            <input type="text" name="productName" style="width: 40%" class="form-control"
                                   placeholder="" value="${(productInfo.productName)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">单价</span>
                            <input type="text" name="productPrice" style="width: 10%" class="form-control"
                                   placeholder="" value="${(productInfo.productPrice?c)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">进价</span>
                            <input type="text" name="productPurchasePrice" style="width: 10%" class="form-control"
                                   placeholder="" value="${(productInfo.productPurchasePrice?c)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">颜色</span>
                            <input type="text" name="productColor" style="width: 30%" class="form-control"
                                   placeholder="使用'_'分隔" value="${(productInfo.productColor)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">尺码</span>
                            <input type="text" name="productSize" style="width: 30%" class="form-control"
                                   placeholder="使用'_'分隔" value="${(productInfo.productSize)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">库存</span>
                            <input type="text" name="productStock" style="width: 10%" class="form-control"
                                   placeholder="" value="${(productInfo.productStock?c)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">类目</span>
                            <select name="categoryType" class="form-control" style="width: 10%">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                                selected
                                            </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">描述</span>
                            <input type="text" name="productDescription" style="width: 60%" class="form-control"
                                   placeholder="" value="${(productInfo.productDescription)!''}">
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">主图</span>
                            <img id="productImgMd" height="250" width="250" src="${(productInfo.productImgMd)!''}"
                                 alt="">
                            <input id="imgMd" name="file" accept="image/*" type="file" style="display: none"/>
                            <input id="imgMdInput" name="productImgMd" type="text" class="form-control"
                                   value="${(productInfo.productImgMd)!''}" style="display: none"/>
                            <button id="submit_imgMd" type="button">确定修改图片</button>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">详情图</span>
                            <img id="productDetailImg" height="150" width="150" src="/netshop/img/add.png" alt="">
                            <input id="detailImg" name="file" accept="image/*" type="file" style="display: none"/>
                            <button id="submit_detailImgMd" type="button">确定添加图片</button>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">详情图URL</span>
                            <input type="text" id="detailImgInput" name="productDetailImg" style="width: 93%"
                                   class="form-control"
                                   placeholder="" value="${(productInfo.productDetailImg)!''}">
                        </div>
                        <br>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">确认</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    $(function () {
        // 主图片预览
        $("#productImgMd").click(function () {
            $("#imgMd").click(); // 隐藏了input:file样式后，点击头像就可以本地上传
            $("#imgMd").on("change", function () {
                var objUrl = getObjectURL(this.files[0]); // 获取图片的路径，该路径不是图片在本地的路径
                if (objUrl) {
                    $("#productImgMd").attr("src", objUrl); // 将图片路径存入src中，显示出图片
                }
            });
        });

        //图片上传
        $("#submit_imgMd").click(function () {
            $.ajaxFileUpload({
                url: "/netshop/seller/pic/upload",
                fileElementId: "imgMd", // 文件上传域的ID，这里是input的ID，而不是img的
                dataType: 'json', // 返回值类型 一般设置为json
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success: function (data) {
                    if (data.code == 200) {
                        $("#productImgMd").attr("src", data.data);
                        $("#imgMdInput").attr("value", data.data);
                        alert("修改成功");
                    }
                }
            });
        });

        // 详情图片预览
        $("#productDetailImg").click(function () {
            $("#detailImg").click(); // 隐藏了input:file样式后，点击头像就可以本地上传
            $("#detailImg").on("change", function () {
                var objUrl = getObjectURL(this.files[0]); // 获取图片的路径，该路径不是图片在本地的路径
                if (objUrl) {
                    $("#productDetailImg").attr("src", objUrl); // 将图片路径存入src中，显示出图片
                }
            });
        });

        // 详情图片上传
        $("#submit_detailImgMd").click(function () {
            $.ajaxFileUpload({
                url: "/netshop/seller/pic/upload",
                fileElementId: "detailImg", // 文件上传域的ID，这里是input的ID，而不是img的
                dataType: 'json', // 返回值类型 一般设置为json
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                success: function (data) {
                    if (data.code == 200) {
                        $("#productDetailImg").attr("src", data.data);
                        var imgMdInput = $("#detailImgInput").val();
                        if (imgMdInput == "") {
                            $("#detailImgInput").attr("value", data.data);
                        } else {
                            $("#detailImgInput").attr("value", imgMdInput + "|" + data.data);
                        }
                        alert("添加成功");
                    }
                }
            });
        });
    });

    // 建立一個可存取到該file的url
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

</script>
</html>