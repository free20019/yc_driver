package excle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ReadExcel {
	public static final String EXCEL_PATH = "src/excle/aaa.xlsx";
	public static void main(String[] args) {
		try {
			readXls();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 public static void readXls() throws IOException {
	        InputStream is = new FileInputStream(EXCEL_PATH);
	        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
	        // 循环工作表Sheet
	        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	            if (hssfSheet == null) {
	                continue;
	            }
	            // 循环行Row
	            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
	                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
	                if (hssfRow != null) {
	                    XSSFCell no = hssfRow.getCell(0);
	                    XSSFCell name = hssfRow.getCell(1);
	                    XSSFCell age = hssfRow.getCell(2);
	                    XSSFCell score = hssfRow.getCell(3);
	                    System.out.println(no + "  "+name+"  "+age+"   "+score);
	                }
	            }
	        }
	    }
	    
	     @SuppressWarnings("static-access")
	    private String getValue(HSSFCell hssfCell) {
	            if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
	                // 返回布尔类型的值
	                return String.valueOf(hssfCell.getBooleanCellValue());
	            } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
	                // 返回数值类型的值
	                return String.valueOf(hssfCell.getNumericCellValue());
	            } else {
	                // 返回字符串类型的值
	                return String.valueOf(hssfCell.getStringCellValue());
	            }
	        }
}
