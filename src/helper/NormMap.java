package helper;

import java.util.HashMap;
import java.util.Map;

public class NormMap {

	/**
	 * code 代码 ：
	 * 101：登陆账号错误
	 * 102：登陆密码错误
	 * 200：用户登陆正常
	 * 201：用户登录过期
	 * 301：请求失败
	 * 
	 * count 数据条数
	 * 
	 * data 数据（json）
	 * @return
	 */
	public static Map setMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "");
		map.put("count", "");
		map.put("data", "");
		return map;
	}
	
	
}
