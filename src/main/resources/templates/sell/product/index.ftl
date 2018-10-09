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
                    <form role="form" method="post" action="/netshop/seller/product/save">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control"
                                   value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control"
                                   value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>进价</label>
                            <input name="productPurchasePrice" type="text" class="form-control"
                                   value="${(productInfo.productPurchasePrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>颜色（使用“_”分隔）</label>
                            <input name="productColor" type="text" class="form-control"
                                   value="${(productInfo.productColor)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>尺码</label>
                            <input name="productSize" type="text" class="form-control"
                                   value="${(productInfo.productSize)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="text" class="form-control"
                                   value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control"
                                   value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>主图</label>
                            <img id="productImgMd" height="100" width="100" src="${(productInfo.productImgMd)!''}"
                                 alt="">
                            <input id="imgMd" name="file" accept="image/*" type="file" style="display: none"/>
                            <input id="imgMdInput" name="productImgMd" type="text" class="form-control"
                                   value="${(productInfo.productImgMd)!''}" style="display: none"/>
                            <button id="submit_imgMd" type="button">确定修改图片</button>
                        </div>
                        <div class="form-group">
                            <label>商品详情图</label>
                            <img id="productDetailImg" height="100" width="100"
                                 src="${(productInfo.productDetailImg)!''}" alt="">
                            <input id="detailImg" name="file" accept="image/*" type="file" style="display: none"/>
                            <button id="submit_detailImgMd" type="button">确定添加图片</button>
                            <input id="detailImgInput" name="productDetailImg" type="text" class="form-control"
                                   value="${(productInfo.productDetailImg)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
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