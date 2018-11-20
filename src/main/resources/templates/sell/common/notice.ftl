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
                <button onclick="javascrtpt:window.location='/seller/order/list'" type="button"
                        class="btn btn-primary">查看新的订单
                </button>
            </div>
        </div>
    </div>
</div>

<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/mp3/song.mp3" type="audio/mpeg"/>
</audio>

<script>
    var webSocket = null;
    if ('WebSocket' in window) {
        // webSocket = new WebSocket("ws://localhost:8085/webSocket");
        webSocket = new WebSocket("ws://106.14.183.207:8085/webSocket");
    } else {
        alert("该浏览器不支持websocket！");
    }

    webSocket.onopen = function (event) {
        console.log("建立连接");
    };

    webSocket.onclose = function (event) {
        console.log("关闭连接");
    };

    webSocket.onmessage = function (event) {
        console.log("收到消息：" + event.data);
        if ($.cookie('shopId') == event.data) {
            // 弹窗提醒、播放音乐
            $("#myModal").modal('show');
            document.getElementById('notice').play();
        }
    };

    webSocket.onerror = function () {
        console.log("websocket通信发生错误！");
    };

    webSocket.onbeforeunload = function () {
        webSocket.close();
    };
</script>