package hy.android.androidproject.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;

/**
 * 人脸探测
 */
public class FaceUtil {

	/**
	 * 人脸检测
	 */
	// 认证参数
	private static final String APP_ID = "17614773";
	private static final String API_KEY = "BZoMCRsTG8MyG84yeYwV234H";
	private static final String SECRET_KEY = "eG80SOV9dV9cUhfhZiHlLnx1eYE6GSdm";

	// 检测人脸附加参数
	private static final String FACE_FIELD = "quality,type,beauty,age";

	// 全局返回状态码
	private static final String SUCCESS = "SUCCESS";

	private static AipFace client = null;

	/**
	 * 初始化
	 * @return
	 */
	static {
		client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);// 建立连接的超时时间（单位：毫秒）
		client.setSocketTimeoutInMillis(60000);// 通过打开的连接传输数据的超时时间（单位：毫秒）
		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
		// client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
		// client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
	}
	public static JSONObject faceUpload(String image, String imageType) {
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();

		options.put("liveness_control", "NONE");// 活体
		options.put("face_field", "age,beauty,gender,type,expression,faceshape,emotion,race,facetype");
		System.out.println(options.toString());
		// 发起调用
		JSONObject res = client.detect(image, imageType, options);
		return res;
	}

	/**
	 * 添加人脸到人脸库
	 * 
	 * @return
	 */
	public static String add(String uid, String userInfo, String groupId) {
		// 请求url
		String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
		try {
			// 本地文件路径
			String filePath = "E:/workSpace/face/src/main/resources/3.JPG";
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			// String filePath2 = "E:/workSpace/face/src/main/resources/1.JPG";
			// byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
			// String imgStr2 = Base64Util.encode(imgData2);
			// String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

			String param = "uid=" + uid + "&user_info=" + userInfo + "&group_id=" + groupId + "&images=" + imgParam;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";

			String result = HttpUtil.post(url, accessToken, param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 人脸对比
	 * 
	 * @return
	 */
	public static String match(String filePath, String filePath2) {
		// 请求url
		String url = "https://aip.baidubce.com/rest/2.0/face/v2/match";
		try {
			// 本地文件路径
			//String filePath = "E:/workSpace/face/src/main/resources/2.JPG";
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			//String filePath2 = "E:/workSpace/face/src/main/resources/1.JPG";
			byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
			String imgStr2 = Base64Util.encode(imgData2);
			String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

			String param = "images=" + imgParam + "," + imgParam2;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";

			String result = HttpUtil.post(url, accessToken, param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 人脸查找
	 * 
	 * @return
	 */
	public static String identify(String filePath) {
		// 请求url
		//String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
		String url = "https://aip.baidubce.com/rest/2.0/face/v2/search";
		try {
			// 本地文件路径
			
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			// String filePath2 = "E:/workSpace/face/src/main/resources/2.JPG";
			// byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
			// String imgStr2 = Base64Util.encode(imgData2);
			// String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

			String param = "group_id=" + "AndroidFaceR" + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images="
					+ imgParam;
					// + "," + imgParam2;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";

			String result = HttpUtil.post(url, accessToken, param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String add(String imageBase64, String name, String userid,String gender){
        String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";
        String result = "添加失败";
        // 请求url
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageBase64);
			map.put("group_id", "admin_1");
			map.put("user_id", userid);
			map.put("user_info", name + "-" + gender);
			map.put("liveness_control", "NORMAL");
			map.put("image_type", "BASE64");
			map.put("quality_control", "LOW");

			String param = GsonUtils.toJson(map);
			// 客户端可自行缓存，过期后重新获取。
			result = HttpUtil.post(url, accessToken, "application/json", param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	
	public static String matchTwo() {

		// 请求url
		String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
		try {

			byte[] bytes1 = FileUtil.readFileByBytes("D:\\个人简历\\22.jpg");
			byte[] bytes2 = FileUtil.readFileByBytes("D:\\个人简历\\33.jpg");
			String image1 = Base64Util.encode(bytes1);
			String image2 = Base64Util.encode(bytes2);

			List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();

			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("image", image1);
			map1.put("image_type", "BASE64");
			map1.put("face_type", "LIVE");
			map1.put("quality_control", "LOW");
			map1.put("liveness_control", "NORMAL");

			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("image", image2);
			map2.put("image_type", "BASE64");
			map2.put("face_type", "LIVE");
			map2.put("quality_control", "LOW");
			map2.put("liveness_control", "NORMAL");

			images.add(map1);
			images.add(map2);

			String param = GsonUtils.toJson(images);

			String result = HttpUtil.post(url, accessToken, "application/json", param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String faceMerge(String image1, String image2) {
		// 请求url
		String url = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
		System.out.println("image1: + " + image1.substring(0,100));
		System.out.println("image2: + " + image2.substring(0,100));
		try {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> image_templateMap = new HashMap<>();
			image_templateMap.put("image", image1);
			image_templateMap.put("image_type", "BASE64");
			image_templateMap.put("quality_control", "NONE");
			map.put("image_template", image_templateMap);
			Map<String, Object> image_targetMap = new HashMap<>();
			image_targetMap.put("image", image2);
			image_targetMap.put("image_type", "BASE64");
			image_targetMap.put("quality_control", "NONE");
			map.put("image_target", image_targetMap);

			String param = GsonUtils.toJson(map);

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";

			String result = HttpUtil.post(url, accessToken, "application/json", param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String faceMatch(String image1, String image2) {
		// 请求url
		String accessToken = "24.f9e68abe93a0877cd60cc2e66ea52055.2592000.1574589011.282335-17614773";
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
		try {
			List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();

			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("image", image1);
			map1.put("image_type", "BASE64");
			map1.put("face_type", "LIVE");
			map1.put("quality_control", "LOW");
			map1.put("liveness_control", "NORMAL");

			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("image", image2);
			map2.put("image_type", "BASE64");
			map2.put("face_type", "LIVE");
			map2.put("quality_control", "LOW");
			map2.put("liveness_control", "NORMAL");

			images.add(map1);
			images.add(map2);

			String param = GsonUtils.toJson(images);

			String result = HttpUtil.post(url, accessToken, "application/json", param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}