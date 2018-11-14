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
                        <div class="input-group" style="float: left;width: 100%">
                            <span class="input-group-addon">名称</span>
                            <input type="text" name="productName" style="width: 74.2%" class="form-control"
                                   placeholder="" value="${(productInfo.productName)!''}">
                        </div>
                        <br><br><br>
                        <div class="input-group" style="float: left;width: 40%">
                            <span class="input-group-addon">颜色</span>
                            <input type="text" name="productColor" style="width: 86.5%" class="form-control"
                                   placeholder="使用'_'分隔" value="${(productInfo.productColor)!''}">
                        </div>
                        <div class="input-group" style="float: left;width: 40%">
                            <span class="input-group-addon">尺码</span>
                            <input type="text" name="productSize" style="width: 86.5%" class="form-control"
                                   placeholder="使用'_'分隔" value="${(productInfo.productSize)!''}">
                        </div>
                        <br><br><br>
                        <div class="input-group" style="float: left;width: 20%">
                            <span class="input-group-addon">单价</span>
                            <input type="text" name="productPrice" style="width: 70%" class="form-control"
                                   placeholder="" value="${(productInfo.productPrice?c)!''}">
                        </div>
                        <div class="input-group" style="float: left;width: 20%">
                            <span class="input-group-addon">进价</span>
                            <input type="text" name="productPurchasePrice" style="width: 70%" class="form-control"
                                   placeholder="" value="${(productInfo.productPurchasePrice?c)!''}">
                        </div>
                        <div class="input-group" style="float: left;width: 20%">
                            <span class="input-group-addon">库存</span>
                            <input type="text" name="productStock" style="width: 70%" class="form-control"
                                   placeholder="" value="${(productInfo.productStock?c)!''}">
                        </div>
                        <div class="input-group" style="float: left;width: 20%">
                            <span class="input-group-addon">类目</span>
                            <select name="categoryType" class="form-control" style="width: 70%">
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
                        <br><br><br>
                        <div class="input-group" style="float: left;width: 100%">
                            <span class="input-group-addon">描述</span>
                            <textarea type="text" name="productDescription" style="width: 74.2%" class="form-control"
                                      placeholder=""
                                      value="${(productInfo.productDescription)!''}">${(productInfo.productDescription)!''}</textarea>
                        </div>
                        <br><br><br><br><br>
                        <div class="input-group">
                            <span class="input-group-addon">主图</span>
                            <img id="productImgMd" height="150" width="150"
                                 src="${(productInfo.productImgMd)!'/netshop/img/add.png'}"
                                 alt="">
                            <input id="imgMd" name="file" accept="image/*" type="file" style="display: none"/>
                            <input id="imgMdInput" name="productImgMd" type="text" class="form-control"
                                   value="${(productInfo.productImgMd)!''}" style="display: none"/>
                            <button id="submit_imgMd" type="button" class="btn btn-default btn-sm">确定修改图片</button>
                        </div>
                        <br>
                        <div class="input-group">
                            <span class="input-group-addon">副图</span>
                            <div id="detailImgShow" style="float: left">
                                <#if productInfo?? && productInfo.productDetailImg?? && productInfo.productDetailImg != "">
                                    <#list productInfo.productDetailImg?split("|") as detailImgUrl>
                                             <img id="${detailImgUrl[(detailImgUrl?index_of("M00")+10)..(detailImgUrl?length-5)]}"
                                                  height="150" width="150"
                                                  style="padding-bottom: 1px;padding-top: 1px"
                                                  src="${detailImgUrl}"
                                                  onclick="preDeleteDetailImg('${detailImgUrl[(detailImgUrl?index_of("M00")+10)..(detailImgUrl?length-5)]}')"
                                                  alt="">
                                    </#list>
                                </#if>
                                <img id="productDetailImg" height="150" width="150" src="/netshop/img/add.png"
                                     alt="">
                                <input id="detailImg" name="file" accept="image/*" type="file"
                                       style="display: none"/>
                                <button id="submit_detailImgMd" type="button" class="btn btn-default btn-sm">确定添加图片
                                </button>
                            </div>
                        </div>
                        <br>
                        <input type="text" hidden="hidden" id="detailImgInput" name="productDetailImg"
                               style="width: 95%"
                               placeholder="" value="${(productInfo.productDetailImg)!''}">
                        <input hidden="hidden" type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default btn-primary">确认</button>
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
                        $("#hintModalTitle").text("修改图片");
                        $("#hintModalBody").text("修改图片成功！");
                        $("#hintModalCancel").hide();
                        $("#hintModalConfirm").attr("data-dismiss", "modal");
                        $("#hintModal").modal();
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
                        // $("#productDetailImg").attr("src", data.data);
                        var imgUrl = data.data;
                        var imgMdInput = $("#detailImgInput").val();
                        if (imgMdInput == "") {
                            $("#detailImgInput").attr("value", imgUrl);
                        } else {
                            $("#detailImgInput").attr("value", imgMdInput + "|" + imgUrl);
                        }
                        var id = imgUrl.substr(imgUrl.indexOf("M00") + 10, imgUrl.length - imgUrl.indexOf("M00") - 14);
                        $('#productDetailImg').before("<img id=\"" + id + "\" height=\"150\" style=\"padding-bottom: 1px;padding-top: 1px\" width=\"150\" src=\"" + imgUrl + "\" onclick=\"preDeleteDetailImg('" + id + "')\" alt=\"\">");
                        // 将添加图片的符号恢复
                        $("#productDetailImg").attr("src", "/netshop/img/add.png");
                        $("#hintModalTitle").text("修改图片");
                        $("#hintModalBody").text("修改图片成功！");
                        $("#hintModalCancel").hide();
                        $("#hintModalConfirm").attr("data-dismiss", "modal");
                        $("#hintModal").modal();
                    }
                }
            });
        });

        // // 测试添加节点
        // $("#submit_detailImgMd_test").click(function () {
        //     alert("测试添加节点");
        //     var ImgUrl = "http://192.168.64.11/group1/M00/00/00/wKhAC1u9tb-AVrf-AABUY6JR46c266.jpg";
        //     // alert(ImgUrl.indexOf("jpg") + "  -  " + ImgUrl.indexOf("M00"));
        //     // var idLength = ImgUrl.indexOf("jpg") - ImgUrl.indexOf("M00");
        //     // alert(idLength);
        //     var id = ImgUrl.substr(ImgUrl.indexOf("M00") + 10, ImgUrl.indexOf("jpg") - ImgUrl.indexOf("M00") - 11);
        //     alert(id);
        //     $('#detailImgShow').append("<img id=\"" + id + "\" height=\"150\" width=\"150\" src=\"" + ImgUrl + "\" onclick=\"preDeleteDetailImg('" + id + "')\" alt=\"\">");
        //     var imgMdInput = $("#detailImgInput").val();
        //     if (imgMdInput == "") {
        //         $("#detailImgInput").attr("value", ImgUrl);
        //     } else {
        //         $("#detailImgInput").attr("value", imgMdInput + "|" + ImgUrl);
        //     }
        // });
        //
        // // 测试删除节点
        // $("#delete_detailImgMd_test").click(function () {
        //     alert("测试删除节点");
        //     var imgUrl = $("#wKhAC1vq1Q-AHTg7AAQgDiDK67s752").attr("src");
        //     alert(imgUrl);
        //     var imgMdInput = $("#wKhAC1vq1Q-AHTg7AAQgDiDK67s752").val();
        //     imgMdInput = imgMdInput.replace(imgUrl, "")
        //     if (imgMdInput.charAt(0) == "|") {
        //         imgMdInput = imgMdInput.replace("|", "");
        //     }
        //     $("#detailImgInput").attr("value", imgMdInput);
        // });
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

    // 删除详情图片提示
    function preDeleteDetailImg(detailImgId) {
        // alert("predelete:  " + detailImgId);
        $("#hintModalTitle").text("删除图片");
        $("#hintModalBody").text("您确定要删除该图片吗？");
        $("#hintModalConfirm").removeAttr("data-dismiss");
        $("#hintModalConfirm").attr("href", "javascript:deleteDetailImg(\'" + detailImgId + "\')");
        $("#hintModal").modal();
    }

    // 删除详情图片
    function deleteDetailImg(detailImgId) {
        // alert("delete:  " + detailImgId);
        $('#hintModal').modal("hide");
        var imgUrl = $("#" + detailImgId).attr("src");
        // alert(imgUrl);
        $("#" + detailImgId).remove();
        var detailImgInput = $("#detailImgInput").val();
        detailImgInput = detailImgInput.replace(imgUrl, "");
        // alert("first:  " + detailImgInput);
        if (detailImgInput.charAt(0) == "|") {
            detailImgInput = detailImgInput.replace("|", "");
        }
        // alert("second:  " + detailImgInput);
        if (detailImgInput.charAt(detailImgInput.length - 1) == "|") {
            detailImgInput = detailImgInput.substr(0, detailImgInput.length - 1);
        }
        // alert("third:  " + detailImgInput);
        $("#detailImgInput").attr("value", detailImgInput);
    }

</script>
</html>