<html>
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
                    <form class="form-inline" role="form" id="searchForm" action="/netshop/seller/product/list">
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">货号</span>
                            <input id="s_page" name="page" type="hidden" class="form-control"
                                   aria-describedby="basic-addon1"/>
                            <input id="s_size" name="size" type="hidden" class="form-control"
                                   aria-describedby="basic-addon1"/>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="productId" id="s_productId"
                                   value="<#if s_productInfo.productId??>${s_productInfo.productId}</#if>"
                                   style="width: 150px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">名称</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="productName" id="s_productName"
                                   value="<#if s_productInfo.productName??>${s_productInfo.productName}</#if>"
                                   style="width: 200px">
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">类目</span>
                            <select name="categoryType" id="s_categoryType" class="form-control" style="width: 100px">
                                <option value=""
                                    <#if (s_productInfo.categoryType)?? && s_productInfo.categoryType == "">
                                        selected
                                    </#if>
                                >全部
                                </option>
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                        <#if (s_productInfo.categoryType)?? && s_productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                    >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon">状态</span>
                            <select name="productStatus" id="s_productStatus" class="form-control" style="width: 100px">
                                <option value="-1"
                                    <#if (s_productInfo.productStatus)?? && s_productInfo.productStatus == -1>
                                        selected
                                    </#if>
                                >全部
                                </option>
                                <option value="0"
                                    <#if (s_productInfo.productStatus)?? && s_productInfo.productStatus == 0>
                                        selected
                                    </#if>
                                >在售
                                </option>
                                <option value="1"
                                    <#if (s_productInfo.productStatus)?? && s_productInfo.productStatus == 1>
                                        selected
                                    </#if>
                                >停售
                                </option>
                            </select>
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon" id="basic-addon1">价格区间</span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="minPrice" id="minPrice" value="<#if minPrice??>${minPrice?c}</#if>"
                                   style="width: 60px">
                            <span class="input-group-addon" id="basic-addon1">></span>
                            <input type="text" class="form-control" placeholder="" aria-describedby="basic-addon1"
                                   name="maxPrice" id="maxPrice" value="<#if maxPrice??>${maxPrice?c}</#if>"
                                   style="width: 60px">
                        </div>
                        <a type="button" class="btn btn-default btn-primary" href="javascript:resetSearchValue()">清空</a>
                        <button id="searchSubmit" type="submit" class="btn btn-default btn-primary">搜索</button>
                    </form>
                    <table class="table table-bordered table-condensed table-striped">
                        <thead>
                        <tr>
                            <th style="text-align: center">货号</th>
                            <th style="text-align: center;width: 300px">名称</th>
                            <th style="text-align: center">图片</th>
                        <#--<th>展示图</th>-->
                            <th style="text-align: center">单价</th>
                            <th style="text-align: center">进价</th>
                            <th style="text-align: center;width: 150px">颜色</th>
                            <th style="text-align: center;width: 150px">尺码</th>
                            <th style="text-align: center">库存</th>
                        <#--<th>描述</th>-->
                            <th style="text-align: center;width: 70px">类目</th>
                            <th style="text-align: center;width: 50px">状态</th>
                        <#--<th style="text-align: center">创建时间</th>-->
                        <#--<th style="text-align: center">修改时间</th>-->
                            <th style="text-align: center;width: 126px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if productInfoPage.getNumberOfElements() == 0>
                        <tr align="center">
                            <td colspan="11"><a style="color: #ff000f">抱歉，找不到符合条件的商品 !</a></td>
                        </tr>
                        <#else>
                            <#list productInfoPage.content as productInfo>
                                <tr align="center">
                                    <td style="vertical-align:middle">${productInfo.productId}</td>
                                    <td style="vertical-align:middle">${productInfo.productName}</td>
                                    <td><img height="50" width="50" src="${productInfo.productImgMd}" alt=""></td>
                                <#--<td><img height="50" width="50" src="${productInfo.productDetailImg}" alt=""></td>-->
                                    <td style="vertical-align:middle">${productInfo.productPrice?c}</td>
                                    <td style="vertical-align:middle">${productInfo.productPurchasePrice?c}</td>
                                    <td style="vertical-align:middle">${productInfo.productColor}</td>
                                    <td style="vertical-align:middle">${productInfo.productSize}</td>
                                    <td style="vertical-align:middle">${productInfo.productStock?c}</td>
                                <#--<td>${productInfo.productDescription}</td>-->
                                    <td style="vertical-align:middle">
                                        <#list categoryList as category>
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                                ${category.getCategoryName()}
                                            </#if>
                                        </#list>
                                    </td>
                                    <td style="vertical-align:middle">${productInfo.getProductStatusEnum().message}</td>
                                <#--<td style="padding-top: 20">${productInfo.getCreateTime()}</td>-->
                                <#--<td style="padding-top: 20">${productInfo.getUpdateTime()}</td>-->
                                    <td style="vertical-align:middle">
                                        <a href="/netshop/seller/product/index?productId=${productInfo.productId}"
                                           type="button"
                                           class="btn btn-default btn-danger">修改</a>
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
                        </#if>
                        </tbody>
                    </table>
                </div>

                <#if productInfoPage.getNumberOfElements() != 0>
                <#--分页-->
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">首页</a></li>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                        <#--<li><a href="/netshop/seller/product/list?page=1&size=${size}">首页</a></li>-->
                        <#--<li><a href="/netshop/seller/product/list?page=${currentPage - 1}&size=${size}">上一页</a></li>-->
                            <li><a href="javascript:search(1, ${size})">首页</a></li>
                            <li><a href="javascript:search(${currentPage - 1}, ${size})">上一页</a></li>
                        </#if>
                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#elseif index lte (currentPage + 3) && index gte (currentPage - 3)>
                            <#--<li><a href="/netshop/seller/product/list?page=${index}&size=${size}">${index}</a></li>-->
                                    <li><a href="javascript:search(${index}, ${size})">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                            <li class="disabled"><a href="#">尾页</a></li>
                        <#else>
                        <#--<li><a href="/netshop/seller/product/list?page=${currentPage + 1}&size=${size}">下一页</a></li>-->
                        <#--<li><a href="/netshop/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">尾页</a>-->
                            <li><a href="javascript:search(${currentPage + 1}, ${size})">下一页</a></li>
                            <li><a href="javascript:search(${productInfoPage.getTotalPages()}, ${size})">尾页</a>
                            </li>
                        </#if>
                        </ul>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>

<script>
    /**
     * 清空搜索
     */
    function resetSearchValue() {
        $("#s_productId").val("");
        $("#s_productName").val("");
        $("#s_categoryType").val("");
        $("#s_productStatus").val(-1);
        $("#minPrice").val("");
        $("#maxPrice").val("");
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