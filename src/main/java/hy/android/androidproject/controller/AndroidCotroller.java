package hy.android.androidproject.controller;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Logger;
import com.baidu.aip.util.Base64Util;
import com.google.gson.Gson;
import hy.android.androidproject.entity.UserInfo;
import hy.android.androidproject.util.FaceTest;
import hy.android.androidproject.util.FaceUtil;
import hy.android.androidproject.util.FileUtil;
import hy.android.androidproject.util.User;
import org.apache.tomcat.util.buf.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

//@RestController
@ResponseBody
@Controller
public class AndroidCotroller {
    @Autowired
    MongoTemplate mongotemplate;

    @RequestMapping(value = "/uploadPictrue",method = RequestMethod.POST)
    public String search(String images){
       //String images = request.getParameter("images");
        Gson gson = new Gson();
        FaceTest faceTest = new FaceTest();
        System.out.println(images);
        System.out.println("开始执行");
        if(images != null || images != ""){
            //获取请求参数
            System.out.println("++++++++++++++++++++" + images.substring(0, 100));
            String result = faceTest.search(images);
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.get("error_msg").toString();
            if (message.equals("SUCCESS")){
                return result;
            }else{
                return "";
            }
        }
        return "base64是空字符串呀";
    }

    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public String detect(String imagesBase64){
        String imageType = "BASE64";
        System.out.println("imageBase64: " + imagesBase64);
        FaceUtil faceUtil = new FaceUtil();
        JSONObject jsonObject = faceUtil.faceUpload(imagesBase64, imageType);
        System.out.println(jsonObject.toString(2));
        System.out.println("----------------------------------------------------");
//	    System.out.println(res.getString("error_msg"));
        JSONObject face_list = jsonObject.getJSONObject("result").getJSONArray("face_list").getJSONObject(0);
        System.out.println(face_list.toString());
        return face_list.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(String imagesBase64, String name, String userid, String gender){
        System.out.println("imageBase64:" + imagesBase64 + " name:" + name + " userid:" + userid + " gender:" + gender);
        FaceUtil faceUtil = new FaceUtil();
        String result = faceUtil.add(imagesBase64, name, userid, gender);
        UserInfo userInfo = new UserInfo(name,userid,gender,imagesBase64,"admin_1");
        mongotemplate.save(userInfo);
        return result;
    }

    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    public String merge(String imagesBase1, String imagesBase2){
        FaceUtil faceUtil = new FaceUtil();
        String result = faceUtil.faceMerge(imagesBase1,imagesBase2);

        JSONObject json = new JSONObject(result);
        JSONObject image = (JSONObject) json.get("result");
        String merge_image = image.get("merge_image").toString();
        System.out.println("-----------------------------");
        System.out.println("merge_image:--" + merge_image.substring(0,100));
        return merge_image;
    }

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public String match(String imagesBase1, String imagesBase2){
        FaceUtil faceUtil = new FaceUtil();
        String json = faceUtil.faceMatch(imagesBase1,imagesBase2);

        JSONObject result = new JSONObject(json);
        JSONObject image = (JSONObject) result.get("result");
        String score = image.get("score").toString();
        System.out.println(score);
        return score;
    }
}

