package cn.huan.map.mapspringboot.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

/**
 * 读取疫情数据
 */
public class UrlResultUtil {
    // X509TrustManager接口的实例管理使用哪一个 X509 证书来验证远端的安全套接字。决定是根据信任的证
//书授权、证书撤消列表、在线状态检查或其他方式做出的。
    private LmX509TrustManager xtm = new LmX509TrustManager();
//HostnameVerifier类是用于主机名验证的基接口
    private LmHostnameVerifier hnv = new LmHostnameVerifier();
    /**
     * 初始化context及connection
     */
    private void initContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            X509TrustManager[] xtmArray = new X509TrustManager[]{xtm};
            sslContext.init(null, xtmArray, new java.security.SecureRandom());
            System.out.println("初始化正常!");
        } catch (GeneralSecurityException gse) {
            System.out.println("初始化SSL异常!" + gse);
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
                    .getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }


// 以charSet为指定的编码格式，读取给出的urlStr的地址中的XML文件字符串
    public String getString(String urlStr, String charSet) {

        String errorMsg = "";


// 没有设置字符集的时候，使用UTF-8的字符
        if (charSet == null || "".equals(charSet)) {
            charSet = "UTF-8";
        }
        try {
            URL url = new URL(urlStr);
            if ("https".equals(url.getProtocol())) {
// 初始化证书，信任HTTPS证书
                initContext();
            }
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charSet));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            bufferedReader.close();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errorMsg;
        }
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = URLEncoder.encode("恭喜你，作业已完成！验证码是：189568。如非本人操作，请忽略本短信！【开课吧新职课】", "utf-8");

        String url = "https://api.jisuapi.com/sms/send?appkey=62958a3a6ef3c56d&mobile=18895684649&content=" + content;
        //String url = "https://zaixianke.com/yq/all";
//String url = "http://www.baidu.com";
        System.out.println(url);
        String charset = "utf-8";
        UrlResultUtil util = new UrlResultUtil();
        String result = util.getString(url, charset);
        System.out.println("result=" + result);
    }


    /**
     * 证书信任管理器类
     */


    private class LmX509TrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }
        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain, String authType)
                throws java.security.cert.CertificateException {
        }
        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }


    /**
     * 主机校验类
     */


    private class LmHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
