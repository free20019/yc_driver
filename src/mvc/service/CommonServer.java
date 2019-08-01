package mvc.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helper.JacksonUtil;
import helper.SystemConfig;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class CommonServer {    
	protected JdbcTemplate jdbcTemplate = null;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();

	//手机号注册
	public String register(String phone,String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count =0;
		String cx="select * from TB_DRIVER_REGISTER where phone='"+phone+"' and issh !='3'" ;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(cx);
		if(list.size()>0){
			map.put("info", "2");
			return jacksonUtil.toJson(map);
		}
		int id = findMaxId("TB_DRIVER_REGISTER","id");
		String sql = "insert into TB_DRIVER_REGISTER (phone,password,issh,id) values "
				+ " ('"+phone+"','"+password+"','4','"+id+"')";

		count = jdbcTemplate.update(sql);
		if(count>0){
			map.put("info", "0");
		}else{
			map.put("info", "1");
		}
		return jacksonUtil.toJson(map);
	}
	
	//登录
	public String login(HttpServletRequest request,String phone,String password){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = null;
		String sql= "select * from TB_DRIVER_REGISTER u where phone = ? and password = ?  and issh !='3'";
		System.out.println(sql);		
			list = jdbcTemplate.queryForList(sql,phone,password);
		if(list.size()>0){
			map.put("info", "0");
			map.put("msg", list.get(0).get("note"));
			if(String.valueOf(list.get(0).get("issh")).equals("null")){				
				map.put("issh", "5");
			}else{
				map.put("issh", list.get(0).get("issh"));
			}
//			request.getSession().setAttribute("phone", list.get(0).get("phone"));
//			request.getSession().setAttribute("password", list.get(0).get("password"));
//			request.getSession().setAttribute("id", list.get(0).get("id"));
//			request.getSession().setAttribute("issh", list.get(0).get("issh"));
//			request.getSession().setAttribute("vehicle", list.get(0).get("vehicle"));
			map.put("id", list.get(0).get("id"));
		}else{
			String cx= "select * from TB_DRIVER_REGISTER u where phone = ? and password = ?";
			List<Map<String, Object>> cxlist = jdbcTemplate.queryForList(cx,phone,password);
			if(cxlist.size()>0){
				map.put("msg", cxlist.get(0).get("note"));
			}else{
				map.put("msg", "");
			}
			map.put("info", "1");
		}
		return jacksonUtil.toJson(map);
	}
	
	//申请认证
	public String apply(HttpServletRequest request,String user_name,String id_card,String vehicle_no,String id_card_pic,String vehicle_pic,String license_pic,String id){
		Map<String, String> map = new HashMap<String, String>();
		String cx= "select * from TB_DRIVER_REGISTER u where vehicle_no='"+vehicle_no+"' and issh='0'";
		List<Map<String, Object>> cxcl  = jdbcTemplate.queryForList(cx);
		if(cxcl.size()==2||cxcl.size()>2){
			map.put("info", "2");		
			return jacksonUtil.toJson(map);
		}else{
			String cx2= "select * from TB_DRIVER_REGISTER u where id_card='"+id_card+"' and issh='0'";
			List<Map<String, Object>> cxcard  = jdbcTemplate.queryForList(cx2);
			if(cxcard.size()>0){
				map.put("info", "3");		
				return jacksonUtil.toJson(map);
			}
			String cx3= "select * from TB_DRIVER_REGISTER u where id='"+id+"' and issh='0'";
			List<Map<String, Object>> cxsh  = jdbcTemplate.queryForList(cx3);
			if(cxsh.size()>0){
				map.put("info", "4");		
				return jacksonUtil.toJson(map);
			}
			int count=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
			String sql = "update TB_DRIVER_REGISTER set user_name='"+user_name+"',id_card='"+id_card+
						"',vehicle_no='"+vehicle_no+"',id_card_pic='"+id_card_pic+"',vehicle_pic='"+vehicle_pic+"'"
						+ ",license_pic='"+license_pic+"',issh='2',add_time=to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),note='' where id='"+id+"'";			
			count = jdbcTemplate.update(sql);
			System.out.println("sql="+sql);
			if(count>0){
				map.put("info", "0");
			}else{
				map.put("info", "1");
			}
			return jacksonUtil.toJson(map);
		}
	}
	/**
     *  上传附件
     * @param
     * @return
     */
	public String importfile(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        String file_path ="E:\\upload\\image"+File.separator;//文件存储路径
        String upload_file_path="";
        File file =new File(file_path);
        if(!file.exists() && !file .isDirectory()){ //如果文件夹不存在则创建
            file.mkdirs();  //父文件不存在则一并创建   mkdir只创建对应文件 比如 image的父文件不存在 就会报错（文件路径不存在）
            upload_file_path=file_path;
        }else{
            upload_file_path=file_path;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();  // 设置工厂
        factory.setRepository(new File(file_path));// 设置文件存储位置
        factory.setSizeThreshold(2048 * 1024);// 设置大小，如果文件小于设置大小的话，放入内存中，如果大于的话则放入磁盘中
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");// 这里就是中文文件名处理的代码，其实只有一行
        List<FileItem> list;
        try {
            list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                } else {
                	if(item.getSize() > 4*1024*1000){
                		 map.put("msg","上传的图片过大");
                	}else{
                      String value = item.getName();
                      String prefix=value.substring(value.lastIndexOf("."),value.length()); //获取到文件类型后缀 比如  .jpg
                      Random random = new Random();
                      String ids = random.nextInt(9999)+System.currentTimeMillis()+"";
                      String filenames = ids+prefix;  //组合自己的文件名字 
                      item.write(new File(upload_file_path, filenames));  //将文件保存到文件夹中
//                      OssUtil.upload(upload_file_path+filenames,"complaints/"+filenames);
                      map.put("old_filename",value);
                      map.put("file_address",upload_file_path+filenames);
                      map.put("new_filename",filenames);
                      map.put("msg","0");
//                      if(info.equals("1")){
//                    	  map.put("picture","1");
//                      }
//                      if(info.equals("2")){
//                    	  map.put("picture","2");
//                      }
//                      if(info.equals("3")){
//                    	  map.put("picture","3");
//                      }
                	}
                }
            }
        } catch (Exception e) {//上传失败
            e.printStackTrace();
            map.put("msg","1");
        }
        return jacksonUtil.toJson(map);
    }  
	//查询订单
	public String order(HttpServletRequest request,String time,String id){
		String cx="select vehicle_no from TB_DRIVER_REGISTER where id='"+id+"'";
		List<Map<String, Object>> cxlist = jdbcTemplate.queryForList(cx);
		String vehicle="";
		if(cxlist.size()==1){
			vehicle=String.valueOf(cxlist.get(0).get("vehicle_no"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select ord.NICKNAME,ord.TOTAL_FEE,ord.STATUS,ord.COMMODITY_ID,ord.UPTIME,cow.owner_name,cha.channel_name,cha.province,cha.city,com.company_name,veh.TYPE,veh.VEHICLE_NO,ter.TERMINAL_TYPE,ter.terminal_num"
				+ " from TB_CAR_OWNER cow,tb_channel cha,tb_company com,tb_vehicle veh,TB_INSTALLATION ins,TB_TERMINAL ter,TB_ORDER ord "
				+ " where ord.MDT_NO=ter.TERMINAL_NUM and ter.ID =ins.TERMINAL_ID and veh.ID=ins.VEHICLE_ID and veh.OWNER_ID=cow.ID and cow.company_id=com.id and com.channel_id=cha.id ";
		sql += " and ord.STATUS= '1'";
		if(time!=null&&time.length()>0&&!time.equals("null")){
			sql += " and  To_Char(ord.UPTIME, 'yyyy-mm-dd') like '"+time+"%'";
		}
		if(vehicle!=null&&vehicle.length()>0&&!vehicle.equals("null")){
			sql += " and veh.vehicle_no = '"+vehicle+"'";
		}else{
			sql += " and veh.vehicle_no = ''";
		}
		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			double num=0.00;
			for(int i=0;i<list.size();i++){
				if(!String.valueOf(list.get(i).get("TOTAL_FEE")).equals("null")){
					double a=new Double((list.get(i).get("TOTAL_FEE")).toString());
					num +=a; 
					list.get(i).put("TOTAL_FEE", new Double((list.get(i).get("TOTAL_FEE")).toString())/100);
				}
			}
			map.put("num",num/100);
		}
		map.put("count", list.size());
		map.put("datas", list);
		return jacksonUtil.toJson(map);
	}
	//获取ID
	public int findMaxId(String table,String id){
		int id1 = 1;
		String sql = "select "+id+" from "+table+"  order by to_number("+id+") desc";
		System.out.println("findMaxId sql="+sql);
		List<Map<String, Object>> list = null;
		list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			id1 = Integer.parseInt(list.get(0).get(id).toString())+1;
		}
		return id1;
	}
	//修改密码
	public String resetpassword(String phone,String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count =0;
		String cx="select * from TB_DRIVER_REGISTER where phone='"+phone+"' and issh !='3'" ;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(cx);
		if(list.size()==0){
			map.put("info", "2");
			return jacksonUtil.toJson(map);
		}
		String sql = "update TB_DRIVER_REGISTER set password='"+password+"' where phone='"+phone+"' and issh !='3'";
		count = jdbcTemplate.update(sql);
		if(count>0){
			map.put("info", "0");
		}else{
			map.put("info", "1");
		}
		return jacksonUtil.toJson(map);
	}
	
	//司机注册
	public String registerDriver(String driver_name,String phone,String company,String license,String vehicle,String vehicle_oblique_pic,String vehicle_forward_pic,String license_pic, String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count =0;
		String cx="select * from TB_DRIVER_REGISTER where phone='"+phone+"' and issh !='3' and open_id!='"+openId+"'" ;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(cx);
		if(list.size()>0){
			map.put("info", "2");
			return jacksonUtil.toJson(map);
		}
		String cx2= "select * from TB_DRIVER_REGISTER u where vehicle_no='"+vehicle+"' and issh !='3'  and open_id!='"+openId+"'";
		List<Map<String, Object>> cxcl  = jdbcTemplate.queryForList(cx2);
		if(cxcl.size()==2||cxcl.size()>2){
			map.put("info", "3");		
			return jacksonUtil.toJson(map);
		}
		String cx3= "select * from TB_DRIVER_REGISTER u where id_card='"+license+"' and issh !='3'  and open_id!='"+openId+"'";
		List<Map<String, Object>> cxcard  = jdbcTemplate.queryForList(cx3);
		if(cxcard.size()>0){
			map.put("info", "4");		
			return jacksonUtil.toJson(map);
		}
		String cxopenid="delete from TB_DRIVER_REGISTER where open_id='"+openId+"' and issh !='0'";
		jdbcTemplate.update(cxopenid);
		int id = findMaxId("TB_DRIVER_REGISTER","id");			
		String sql = "insert into TB_DRIVER_REGISTER (user_name,phone,company,id_card,vehicle_no,id_card_pic,vehicle_pic,license_pic,issh,add_time,id,open_id) values "
				+ " ('"+driver_name+"','"+phone+"','"+company+"','"+license+"','"+vehicle+"','"+vehicle_oblique_pic+"','"+vehicle_forward_pic+"','"+license_pic+"','2',sysdate,'"+id+"','"+openId+"')";

		count = jdbcTemplate.update(sql);
		if(count>0){
			map.put("info", "0");
		}else{
			map.put("info", "1");
		}
		return jacksonUtil.toJson(map);
	}
	//司机信息修改
	public String editDriver(String phone,String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count =0;
		String cx="select * from TB_DRIVER_REGISTER where phone='"+phone+"' and issh !='3' and open_id !='"+id+"'" ;
		List<Map<String, Object>> list=jdbcTemplate.queryForList(cx);
		System.out.println("editDrivercx="+cx);
		if(list.size()>0){
			map.put("info", "2");
			return jacksonUtil.toJson(map);
		}
		String sql = "update TB_DRIVER_REGISTER set phone='"+phone+"' where open_id='"+id+"' and issh !='3'";
		System.out.println("editDriver="+sql);
		count = jdbcTemplate.update(sql);
		if(count>0){
			map.put("info", "0");
		}else{
			map.put("info", "1");
		}
		return jacksonUtil.toJson(map);
	}
	//司机信息查询
	public String queryDriver(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String cx="select * from TB_DRIVER_REGISTER where issh !='3' and open_id='"+id+"'" ;
		System.out.println("queryDriver="+cx);
		List<Map<String, Object>> list=jdbcTemplate.queryForList(cx);
		return jacksonUtil.toJson(list);
	}
	//查询订单(2)
	public String queryOrder(HttpServletRequest request,String stime,String etime,String id){
		String cx="select vehicle_no from TB_DRIVER_REGISTER where open_id='"+id+"'";
		List<Map<String, Object>> cxlist = jdbcTemplate.queryForList(cx);
		String vehicle="";
		if(cxlist.size()>0){
			vehicle=String.valueOf(cxlist.get(0).get("vehicle_no"));
		}else{
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select ord.NICKNAME,ord.TOTAL_FEE,ord.STATUS,ord.COMMODITY_ID,ord.UPTIME,cow.owner_name,cha.channel_name,cha.province,cha.city,com.company_name,veh.TYPE,veh.VEHICLE_NO,ter.TERMINAL_TYPE,ter.terminal_num"
				+ " from TB_CAR_OWNER cow,tb_channel cha,tb_company com,tb_vehicle veh,TB_INSTALLATION ins,TB_TERMINAL ter,TB_ORDER ord "
				+ " where ord.MDT_NO=ter.TERMINAL_NUM and ter.ID =ins.TERMINAL_ID and veh.ID=ins.VEHICLE_ID and veh.OWNER_ID=cow.ID and cow.company_id=com.id and com.channel_id=cha.id ";
		sql += " and ord.STATUS= '1'";
		if(stime!=null&&stime.length()>0&&!stime.equals("null")){
			sql += " and ord.UPTIME >=to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')";
		}
		if(etime!=null&&etime.length()>0&&!etime.equals("null")){
			sql += " and ord.UPTIME <=to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		}
		if(vehicle!=null&&vehicle.length()>0&&!vehicle.equals("null")){
			sql += " and veh.vehicle_no = '"+vehicle+"'";
		}else{
			sql += " and veh.vehicle_no = ''";
		}
		sql += " order by ord.UPTIME desc";
		System.out.println("queryOrder="+sql);
		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			double num=0.00;
			for(int i=0;i<list.size();i++){
				if(!String.valueOf(list.get(i).get("TOTAL_FEE")).equals("null")){
					double a=new Double((list.get(i).get("TOTAL_FEE")).toString());
					num +=a; 
					list.get(i).put("TOTAL_FEE", new Double((list.get(i).get("TOTAL_FEE")).toString())/100);
				}
			}
			map.put("num",num/100);
			map.put("royalty",num/100*0.3);
		}
		map.put("count", list.size());
		map.put("datas", list);
		return jacksonUtil.toJson(map);
	}
	/**
	 * 登录验证
	 */
	public String applogin(String code) {
		String result = "";
		try {
//			URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?js_code="+code+"&&appid=wx9827ec2abca8a12a&&secret=baf2ca273073cd613270e8e6834f9679&&grant_type=authorization_code ");
			URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?js_code="+code+"&&appid=wx06089b0ac39b52d7&&secret=c005504f472b40b7aeebf2cfc4c1621f&&grant_type=authorization_code ");		
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1096.1 Safari/536.6");
			conn.setRequestProperty("content-type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			conn.setDoOutput(true); // 需要输出
			if (conn.getResponseCode() == 302) {
				System.out.println(302);
				return null;
			}
			if (conn.getResponseCode() == 200) {
				System.out.println(200);
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String s = "";
			while ((s = rd.readLine()) != null) {
				sb.append(s);
			}
			// System.out.println(sb);
			if (sb.length() == 0) {
				sb.append("[]");
			}
			result = sb.toString();
			 System.out.println(result);
			rd.close();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		Map<String,Object> paramMap = jacksonUtil.toObject(result,new TypeReference<Map<String,Object>>() {});
		Map map = new HashMap<String, Object>();
		map.put("openid",paramMap.get("openid"));
		map.put("msg", 0);
		return jacksonUtil.toJson(map);
	}
}
