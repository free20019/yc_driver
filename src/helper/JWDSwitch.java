package helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JWDSwitch {
	public static String getplace(Object object, Object object2) {
		// String
		// path="http://gc.ditu.aliyun.com/regeocoding?l="+lati+","+longti+"&type=100";
		String path = "http://restapi.amap.com/v3/geocode/regeo?location="
				+ object
				+ ","
				+ object2
				+ "&key=0a54a59bdc431189d9405b3f2937921a&radius=100&extensions=all";
		String place = "";
		// 参数直接加载url后面
		try {
			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			StringBuffer buffer = new StringBuffer();
			if (conn.getResponseCode() == 200) { // 200表示请求成功
				InputStream is = conn.getInputStream(); // 以输入流的形式返回
				InputStreamReader inputStreamReader = new InputStreamReader(is,
						"utf-8");
				// 将输入流转换成字符串
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);

				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				is.close();
				is = null;
				conn.disconnect();
				JSONObject jsonObject = JSONObject
						.fromObject(buffer.toString());
				// 得出整个坐标反馈信息
				// System.out.println(buffer.toString());

				// 转换成json数据处理
				// {"queryLocation":[39.938133,116.395739],"addrList":[{"type":"doorPlate","status":1,"name":"地安门外大街万年胡同1号","admCode":"110102","admName":"北京市,北京市,西城区,","addr":"","nearestPoint":[116.39546,39.93850],"distance":45.804}]}

				String addrList = jsonObject.getString("regeocode");
				JSONObject jsonObject1 = JSONObject.fromObject(addrList);
				String addrList1 = jsonObject1.getString("formatted_address");
				// System.out.println(addrList);
				String roads = jsonObject1.getString("roads");
				JSONArray jsonarry = JSONArray.fromObject(roads);
				String roadName = "";
				for (int i = 0; i < jsonarry.size(); i++) {
					JSONObject jsonObject2 = jsonarry.getJSONObject(i);
					// //String roadName =
					// jsonObject2.getString("admName")+","+jsonObject2.getString("name");
					// //路名（这才是我最终想要的）
					roadName = jsonObject2.getString("name");
				}
				place = addrList1 + " " + roadName;
				if (addrList1.equals("[]")) {
					place = "未知道路";
				}
			}
		} catch (Exception e) {
			// 查找路段出错
			String path1 = "http://gc.ditu.aliyun.com/regeocoding?l=" + object
					+ "," + object2 + "&type=100";
			// 参数直接加载url后面
			try {
				URL url = new URL(path1);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				StringBuffer buffer = new StringBuffer();
				if (conn.getResponseCode() == 200) { // 200表示请求成功
					InputStream is = conn.getInputStream(); // 以输入流的形式返回
					InputStreamReader inputStreamReader = new InputStreamReader(
							is, "utf-8");
					// 将输入流转换成字符串
					BufferedReader bufferedReader = new BufferedReader(
							inputStreamReader);

					String str = null;
					while ((str = bufferedReader.readLine()) != null) {
						buffer.append(str);
					}
					bufferedReader.close();
					inputStreamReader.close();
					// 释放资源
					is.close();
					is = null;
					conn.disconnect();
					JSONObject jsonObject = JSONObject.fromObject(buffer
							.toString());
					// 得出整个坐标反馈信息
					// System.out.println(buffer.toString());

					// 转换成json数据处理
					// {"queryLocation":[39.938133,116.395739],"addrList":[{"type":"doorPlate","status":1,"name":"地安门外大街万年胡同1号","admCode":"110102","admName":"北京市,北京市,西城区,","addr":"","nearestPoint":[116.39546,39.93850],"distance":45.804}]}

					String addrList = jsonObject.getString("addrList");
					// System.out.println(addrList); //地址信息

					JSONArray jsonarry = JSONArray.fromObject(addrList);
					for (int i = 0; i < jsonarry.size(); i++) {
						JSONObject jsonObject2 = jsonarry.getJSONObject(i);
						String roadName = jsonObject2.getString("admName")
								+ "," + jsonObject2.getString("name"); // 路名（这才是我最终想要的）
						place = roadName;
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// e.printStackTrace();
		}
		return place;
	}
}
