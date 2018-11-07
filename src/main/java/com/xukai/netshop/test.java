package com.xukai.netshop;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/11/7 01:12
 * @modified By:
 */
public class test {

    public static void main(String[] args) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        // HttpGet httpGet = new HttpGet("http://www.kuaidi100.com/query?type=zhongtong&postid=75105714323606");
        // HttpHost proxy = new HttpHost("36.99.206.212", 38551);
        // RequestConfig config = RequestConfig.custom()
        //         .setConnectionRequestTimeout(10000)
        //         .setSocketTimeout(10000)
        //         .setProxy(proxy)
        //         .build();
        // httpGet.setConfig(config);
        // //设置头信息
        // httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0");
        // CloseableHttpResponse response = null;
        // try {
        //     response = httpClient.execute(httpGet);
        // } catch (Exception e) {
        // }
        // if (response != null) {
        //     HttpEntity entity = response.getEntity();
        //     String webContent = null;
        //     try {
        //         webContent = EntityUtils.toString(entity, "utf-8");
        //         System.out.println(webContent);
        //         response.close();
        //     } catch (Exception e) {
        //     }
        // }

        while (true) {
            try {
                int i = 4/0;
                System.out.println(i);
                System.out.println("hello");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
