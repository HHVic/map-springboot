package cn.huan.map.mapspringboot.controller;

import cn.huan.map.mapspringboot.utils.UrlResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class Data {


    @RequestMapping("/get")
    @ResponseBody
    public String get() throws UnsupportedEncodingException {
        String url = "https://zaixianke.com/yq/all";
        //String url = "https://zaixianke.com/yq/all";
//String url = "http://www.baidu.com";
        System.out.println(url);
        String charset = "utf-8";
        UrlResultUtil util = new UrlResultUtil();
        String result = util.getString(url, charset);
        System.out.println("result=" + result);
        return result;
    }
}
