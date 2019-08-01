package helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;

import org.apache.poi.ss.usermodel.Workbook;

import helper.ExcelUtil;
public class DownloadAct {
	
//	@RequestMapping("daoexcle1")
//	@ResponseBody
//	public String abcd(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String a[]={"业户","区域","车辆总数","在线总数","离线总数","在线率","离线率"};//导出列明
//		String b[]={"COMP_AREA","COMP_AREA","SY","SX","WSX","SXL","WSXL"};//导出map中的key
//		String wjb="导出";//导出文件名
//		String gzb="中文";//导出sheet名
//		String josn="{\"datas:\":[{\"COMP_NAME\":\"杭州邻都运输有限公司\",\"COMP_AREA\":\"下城区\",\"SY\":24,\"SX\":0,\"WSX\":24,\"SXL\":\"0%\",\"WSXL\":\"100%\",\"RN\":1}],\"count\":100}";
//		List<Map<String, Object>>list = parseJSON2List(josn);
////		 List<Project> projects=createData();
////	        List<Map<String,Object>> list=createExcelRecord(projects);//导出的数据
//		download(request,response,a,b,wjb,gzb,list);
//		return null;
//	}
//	public static void main(String[] args) {
//		List<Map<String, Object>>list = parseJSON2List();
//		
//	}
	public static List<Map<String, Object>> parseJSON2Listx(String a,String name){
		JSONObject j = JSONObject.fromObject(a);
		String t = String.valueOf(j.get(name));
        JSONArray jsonArr = JSONArray.fromObject(t);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	/**
	 * 将JSON格式为[{a:1,b:2}]转换为
	 * list<map>方便导出数据
	 */
	public static List<Map<String, Object>> parseJSON2List2(String a){
        JSONArray jsonArr = JSONArray.fromObject(a);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	/**
	 * 将JSON格式为{datas:[{a:1,b:2}]}转换为
	 * list<map>方便导出数据
	 */
	public static List<Map<String, Object>> parseJSON2List1(String msg){
		String a=msg.substring(9, msg.length()-1);
        JSONArray jsonArr = JSONArray.fromObject(a);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	/**
	 * 将JSON格式为{"datas":{datas:[{a:1,b:2}]},count:1}转换为
	 * list<map>方便导出数据
	 */
	public static List<Map<String, Object>> parseJSON2List3(String msg){
		String a=msg.substring(18, msg.lastIndexOf(",")-1);
        JSONArray jsonArr = JSONArray.fromObject(a);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	/**
	 * 将JSON格式为{"DATA":[{a:1,b:2}]}转换为
	 * list<map>方便导出数据
	 */
	public static List<Map<String, Object>> parseJSON2List4(String msg){
		String a=msg.substring(8,msg.lastIndexOf("}"));
        JSONArray jsonArr = JSONArray.fromObject(a);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	/**
	 * 将JSON格式为{datas:[{a:1,b:2}],count:1}转换为
	 * list<map>方便导出数据
	 */
	public static List<Map<String, Object>> parseJSON2List(String msg){
		String a=msg.substring(9, msg.lastIndexOf(","));
        JSONArray jsonArr = JSONArray.fromObject(a);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while(it.hasNext()){
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }
	 public static Map<String, Object> parseJSON2Map(String jsonStr){
	        ListOrderedMap map = new ListOrderedMap();
	        //最外层解析
	        JSONObject json = JSONObject.fromObject(jsonStr);
	        for(Object k : json.keySet()){
	            Object v = json.get(k); 
	            //如果内层还是数组的话，继续解析
	            if(v instanceof JSONArray){
	                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	                Iterator<JSONObject> it = ((JSONArray)v).iterator();
	                while(it.hasNext()){
	                    JSONObject json2 = it.next();
	                    list.add(parseJSON2Map(json2.toString()));
	                }
	                map.put(k.toString(), list);
	            } else {
	                map.put(k.toString(), v);
	            }
	        }
	        return map;
	    }
    public static String download(HttpServletRequest request,
    		HttpServletResponse response, 
    		String[] a,String[] b,String gzb,List<Map<String,Object>> list) throws IOException{
        String fileName=gzb;//excle文件名
        //填充projects数据
        String columnNames[]=a;//列名
        String keys[]    =     b;//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames,gzb).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
    public static String downloadx(HttpServletRequest request,
    		HttpServletResponse response, 
    		String[] a,String[] b,String gzb,String gzb1,String gzb2,
    		List<Map<String,Object>> jg,List<Map<String,Object>> qd,List<Map<String,Object>> zd) throws IOException{
        String fileName=gzb;//excle文件名
        //填充projects数据
        String columnNames[]=a;//列名
        String keys[]    =     b;//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBookx(jg,qd,zd,keys,columnNames,gzb,gzb1,gzb2).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
//    private List<Project> createData() {
//    	List<Project>list=new ArrayList<Project>();
//    	for(int i=0; i<10;i++){
//    		Project p=new Project();
//    		p.setId(i);
//    		p.setName(i+"name");
//    		p.setRemarks(i+"remarks");
//    		p.setTechnology(i+"technology");
//    		list.add(p);
//    	}
//        return list;
//    }
//    private List<Map<String, Object>> createExcelRecord(List<Project> projects) {
//        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("sheetName", "sh20");//工作簿名
//        listmap.add(map);
//        Project project=null;
//        for (int j = 0; j < projects.size(); j++) {
//            project=projects.get(j);
//            Map<String, Object> mapValue = new HashMap<String, Object>();
//            mapValue.put("id", project.getId());
//            mapValue.put("name", project.getName());
//            mapValue.put("technology", project.getTechnology());
//            mapValue.put("remarks", project.getRemarks());
//            listmap.add(mapValue);
//        }
//        return listmap;
//    }
    
    public String download1(HttpServletRequest request,
    		HttpServletResponse response, 
    		String[] a,String[] b,String gzb,List<Map<String,Object>> list) throws IOException{
        //填充projects数据
        String columnNames[]=a;//列名
        String keys[]    =     b;//map中的key
        String filepath="";
        try {
        //Workbook wwb = ExcelUtil.createWorkBook(list,keys,columnNames,gzb);
		String logfile=request.getSession().getServletContext().getRealPath("/");
		logfile=logfile+"excel";
		filepath = logfile+"/test.xlsx";
		System.out.println(filepath);
		ExcelUtil.Excel2007AboveOperate(list,keys,columnNames,gzb,filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepath;
    }
}
