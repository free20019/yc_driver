package excle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class JsyshExcel {
	public static final String EXCEL_PATH = "src/excle/jsysjshTest.xlsx";
	
	@Test
	public static void test(String date,String EXCEL_PATH) throws Exception {
		try {
			System.out.println(EXCEL_PATH);
			readXls(date,EXCEL_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	 public static void readXls(String date,String EXCEL_PATH) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
//		Connection connection = DriverManager.getConnection("jdbc:mysql://rm-bp1yzjgy99vq4o6c2.mysql.rds.aliyuncs.com:3306/wyc","wyc","tw85450077");
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wyc","root","123456");
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//		String date = df.format(new Date());
	 	File file = new File(EXCEL_PATH);
	 	System.out.println((file.exists()));
        InputStream is = new FileInputStream(EXCEL_PATH);
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
        
        // 循环工作表Sheet
       aa: for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	String SQBH = hssfRow.getCell(0).toString();
                    String zjhm = hssfRow.getCell(4).toString();
                    boolean flagaaa =zjhm.equals("");
                    if(zjhm.equals("")||zjhm==null){break aa;}
                    //is_zjhm
//                    String is_zjhm = hssfRow.getCell(7).toString();
                    //is_zjlx
                    String is_zjlx = "";
                    if(hssfRow.getCell(8)==null){
                    	is_zjlx = "0";
                    }else{
                    	is_zjlx = hssfRow.getCell(8).toString();
                    }
                    
                    
                    
                    //Is_Drug
                    String Is_Drug = "";
                    if(hssfRow.getCell(9)==null){
                    	Is_Drug = "0";
                    }else{
                    	Is_Drug = hssfRow.getCell(9).toString();
                    }
                    
                    //Is_ViolentCrime
                    String Is_ViolentCrime="";
                    if(hssfRow.getCell(10)==null){
                    	Is_ViolentCrime = "0";
                    }else{
                    	Is_ViolentCrime = hssfRow.getCell(10).toString();
                    }
                    
                    //Is_TrafficAccident
                    String Is_TrafficAccident="";
                    if(hssfRow.getCell(11)==null){
                    	Is_TrafficAccident = "0";
                    }else{
                    	Is_TrafficAccident = hssfRow.getCell(11).toString();
                    }
                    
                    //Is_DangerousDriving
                    String Is_DangerousDriving="";
                    if(hssfRow.getCell(12)==null){
                    	Is_DangerousDriving = "0";
                    }else{
                    	Is_DangerousDriving = hssfRow.getCell(12).toString();
                    }
                   
                    //Is_DrunkDriver
                    String Is_DrunkDriver = "";
                    if(hssfRow.getCell(15)==null){
                    	Is_DrunkDriver = "0";
                    }else{
                    	Is_DrunkDriver = hssfRow.getCell(15).toString();
                    }
                    
                    
//                    String zafjsj = hssfRow.getCell(14).toString();
                    String zafjcsyy = ""; 
                    String flag="0";
                    if(is_zjlx.equals("否")){
                  	   flag=is_zjlx = "1";
                  	   zafjcsyy +="取得本市户籍或取得浙江省居住证6个月以上或取得浙江省临时居住证12个月以上（该项不符合）；";
                     }else if(is_zjlx.equals("")){
                  	   is_zjlx = "0";
                     }
                   if(Is_Drug.equals("否")){
                	   flag=Is_Drug = "1";
                	   zafjcsyy +="无吸毒记录（该项不符合）；";
                   }else if(Is_Drug.equals("")){
                	   Is_Drug = "0";
                   }
                   if(Is_ViolentCrime.equals("否")){
                	   flag=Is_ViolentCrime = "1";
                	   zafjcsyy +="无暴力犯罪记录（该项不符合）；";
                   }else if(Is_ViolentCrime.equals("")){
                	   Is_ViolentCrime = "0";
                   }
                   if(Is_TrafficAccident.equals("否")){
                	   flag=Is_TrafficAccident = "1";
                	   zafjcsyy +="无交通肇事犯罪（该项不符合）；";
                   }else if(Is_TrafficAccident.equals("")){
                	   Is_TrafficAccident = "0";
                   }
                   
                   if(Is_DangerousDriving.equals("否")){
                	   flag= Is_DangerousDriving = "1";
                	   zafjcsyy+= "无危险驾驶犯罪记录（该项不符合）；";
                	   
                   }else if(Is_DangerousDriving.equals("")){
                	   Is_DangerousDriving = "0";
                   }
                   
                   if(Is_DrunkDriver.equals("否")){
                	   flag= Is_DrunkDriver = "1";
                	   zafjcsyy+= "无饮酒后驾驶记录（该项不符合）；";
                   }else if(Is_DrunkDriver.equals("")){
                	   Is_DrunkDriver = "0";
                   }
                   
                    
                    String sql = "update tb_jsyjcxx set "
                    		+ "is_zjlx=?,Is_Drug=?,Is_ViolentCrime=?,Is_TrafficAccident=?,"
                    		+ "Is_DangerousDriving=?,Is_DrunkDriver=? ,is_yx=?,bj=2 where SQBH=?";
                    System.out.println(sql);
                   PreparedStatement pst =  connection.prepareStatement(sql);
                   pst.setInt(1, Integer.parseInt(is_zjlx));
                   pst.setInt(2, Integer.parseInt(Is_Drug));
                   pst.setInt(3, Integer.parseInt(Is_ViolentCrime));
                   pst.setInt(4, Integer.parseInt(Is_TrafficAccident));
                   pst.setInt(5, Integer.parseInt(Is_DangerousDriving));
                   pst.setInt(6, Integer.parseInt(Is_DrunkDriver));
                   pst.setInt(7, Integer.parseInt(flag));
                   pst.setString(8, SQBH);
                 
                   pst.execute();
                   
                   String sql2 = "update tb_jsysqxx set is_yx=?,zafjcsyj =?,zafjcssj=now(),zafjcsyy=?,bj=2 where SQBH=?";
                   System.out.println(sql2);
                   PreparedStatement pst2 =  connection.prepareStatement(sql2);
                   pst2.setString(1, flag);
                  
//                 pst2.setString(2, date);
                  
                   pst2.setString(2, flag);
                   pst2.setString(3, zafjcsyy);
                   pst2.setString(4, SQBH);
                    pst2.execute();
                    
                    System.out.println(SQBH);
                    System.out.println(zafjcsyy);
                }
            }
        }
        
        connection.close();
    }
	    
	 
}
