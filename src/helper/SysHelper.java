package helper;

import java.security.MessageDigest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.apache.commons.codec.binary.Base64;
/**
 */
@SuppressWarnings("all")
public class SysHelper {

	private static SysHelper sysHelper = new SysHelper();

	public static SysHelper getInstance() {
		return sysHelper;
	}
	public static String getCurrentDate() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		return df.format(new Date());
	}
	public static String getCNDate() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
		return df.format(new Date());
	}
	 public static String[] daysBetween(String startTime,String endTime)  {  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		long between_days = 0;
		
		try {
			Date d1 = sdf.parse(startTime);
			Date d2 =sdf.parse(endTime); 
			Calendar cal = Calendar.getInstance();    
	        cal.setTime(d1);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(d2);    
	        long time2 = cal.getTimeInMillis();         
	        between_days=(time2-time1)/(1000*3600*24); 
	        
	        String[] days = new String[(int) between_days+1];
	        
	        for(int i=0;i<days.length;i++){
	        	cal.setTime(d1);
		        int day=cal.get(Calendar.DATE); 
		        cal.set(Calendar.DATE,day+i); 
		        
		        days[i]=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
	        }
	        
	        return days;
		} catch (ParseException e) {
			e.printStackTrace();
			return null; 
		}  
		
                 
    }    
	public String getTimeString(long time) {
		Date date = new Date(time);
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/M/d H:m:s");
		String strTime = bartDateFormat.format(date);

		return strTime;
	}

	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath().replaceAll("%20", " ");
		System.out.println(path);
		return path;
	}

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public String getWebRoot() {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			// throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}


	// List 转换成 String
	// 1、转换成 String str = "'XXX','XXX','XXX'";
	public String arrayToString(List a, String separator) {
		String result = "";
		if (a.size() > 0) {
			result = "'" + a.get(0) + "'"; // start with the first element
			for (int i = 1; i < a.size(); i++) {
				result = result.toString() + separator + "'"
						+ a.get(i).toString() + "'";
			}
		}
		return result;
	}

	// 2、转换成 String str = "XXX,XXX,XXX";
	public String arrayToString(List a) {
		StringBuilder result = new StringBuilder();
		if (a.size() > 0) {
			result.append(a.get(0)); // start with the first element
			for (int i = 1; i < a.size(); i++) {
				result.append("," + a.get(i).toString());
			}
		}
		return result.toString();
	}




	public String getIpAddress(HttpServletRequest request) {
		if(null == request){
			return "0.0.0.0";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	// 正则表达式判断NMS IP正确性,
	public boolean isCorrectNmsIP(String ip) {
		Pattern pattern = Pattern
				.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]){1}\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]){1}\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]){1}\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]){1}(:([1-9]|[1-9]\\d{3}|[1-6][0-5][0-5][0-3][0-5])){0,1}$");
		Matcher ipMatcher = pattern.matcher(ip);

		Matcher localMatcher = Pattern
				.compile(
						"^localhost(:([1-9]|[1-9]\\d{3}|[1-6][0-5][0-5][0-3][0-5])){0,1}$")
				.matcher(ip);

		Matcher domainMatcher = Pattern
				.compile(
						"^([\\w]([\\w]{0,61}[\\w])?\\.)+[a-zA-Z]{2,6}(:([1-9]|[1-9]\\d{3}|[1-6][0-5][0-5][0-3][0-5])){0,1}$")
				.matcher(ip);

		return ipMatcher.matches() || localMatcher.matches();
	}

	// 设备类型
	public String getDeviceType(Integer i) {
		String result = "";
		if (null == i) {
			result = "其它";
		} else if (0 == i) {
			result = "PC";
		} else if (1 == i) {
			result = "Phone";
		} else if (3 == i) {
			result = "CPE";
		} else {
			result = "其它";
		}
		return result;
	}

	public List<Integer> strArrToIntArr(List<String> strArr) {
		List<Integer> intArr = new ArrayList<Integer>();
		for (String str : strArr) {
			intArr.add(Integer.valueOf(str));
		}
		return intArr;
	}

	// 删除list里面元素的值为空或值为null的元素
	public ArrayList deleteArrayListEmptyItem(ArrayList<Object> arrayList) {
		if (null == arrayList) {
			arrayList = new ArrayList(0);
			return arrayList;
		}
		Iterator<Object> iterator = arrayList.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (null == object || "".equals(object.toString().trim())) {
				iterator.remove();
			}
		}
		return arrayList;
	}

	// 传入一个字符串,按逗号split后排序,返回新的字符串
	public String sortStringBySeparator(String str, String separator) {
		String[] strArray = str.split(",");
		Arrays.sort(strArray);
		String res = org.apache.commons.lang.StringUtils.join(strArray,
				separator);
		return res;
	}

	public String[] getBrowserInfo(String userAgent) {
		String browsername = "";
		String browserversion = "";
		String browser = userAgent;
		String subsString = "";
		String info[] = new String[2];
		if (browser.contains("MSIE")) {
			subsString = browser.substring(browser.indexOf("MSIE"));
			info = (subsString.split(";")[0]).split(" ");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Firefox")) {
			subsString = browser.substring(browser.indexOf("Firefox"));
			info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Chrome")) {
			subsString = browser.substring(browser.indexOf("Chrome"));
			info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Opera")) {
			subsString = browser.substring(browser.indexOf("Opera"));
			info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Safari")) {
			subsString = browser.substring(browser.indexOf("Safari"));
			info = (subsString.split(" ")[0]).split("/");
			browsername = info[0];
			browserversion = info[1];
		} else if (browser.contains("Flash")) {
			info[0] = "Flash";
			info[1] = "v1";
		}
		return info;
	}

	public List<Long> getLongList(List<Integer> integerList){
		if(null == integerList || integerList.size() == 0 ){
			return new ArrayList<Long>(0);
		}
		List<Long> longList = new ArrayList<Long>();
		for(int i=0,j=0; i< integerList.size(); i++){
			if(integerList.get(i) instanceof Integer ||  StringUtils.isNumeric(String.valueOf(integerList.get(i)))){
				Integer integer = Integer.parseInt(String.valueOf(integerList.get(i)));
				longList.add(j++, integer.longValue());
			}
		}
		return longList;
	}

	public List<Integer> getIntegerList(List<Long> longList){
		if(null == longList || longList.size() == 0 ){
			return new ArrayList<Integer>(0);
		}
		List<Integer> integerList = new ArrayList<Integer>();
		for(int i=0; i< longList.size(); i++){
			if(longList.get(i) instanceof Long ||  StringUtils.isNumeric(String.valueOf(longList.get(i)))){
				Long lon = Long.parseLong(String.valueOf(longList.get(i)));
				integerList.add(i, lon.intValue());
			}
		}
		return integerList;
	}
	
	
	public String genBase64(String content){
		String result = "";
		try {
			result =  new String(Base64.encodeBase64URLSafeString( MessageDigest.getInstance("MD5").digest(content.getBytes())));
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(sysHelper.genBase64("sfsaafasfasdfqwerqwfafds"));
	}
}
