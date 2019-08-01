package helper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtil {
	 public static Workbook createWorkBook(List<Map<String, Object>> list,
			 String []keys,String columnNames[],String gzb) {
	        // 创建excel工作簿
	        Workbook wb = new XSSFWorkbook();
	        // 创建第一个sheet（页），并命名
	        Sheet sheet = wb.createSheet(gzb);
	        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	        for(int i=0;i<keys.length;i++){
	            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
	        }

	        // 创建第一行
	        Row row = sheet.createRow((short) 0);

	        // 创建两种单元格格式
	        CellStyle cs = wb.createCellStyle();
	        CellStyle cs2 = wb.createCellStyle();

	        // 创建两种字体
	        Font f = wb.createFont();
	        Font f2 = wb.createFont();

	        // 创建第一种字体样式（用于列名）
	        f.setFontHeightInPoints((short) 10);
	        f.setColor(IndexedColors.BLACK.getIndex());
	        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

	        // 创建第二种字体样式（用于值）
	        f2.setFontHeightInPoints((short) 10);
	        f2.setColor(IndexedColors.BLACK.getIndex());

//	        Font f3=wb.createFont();
//	        f3.setFontHeightInPoints((short) 10);
//	        f3.setColor(IndexedColors.RED.getIndex());

	        // 设置第一种单元格的样式（用于列名）
	        cs.setFont(f);
	        cs.setBorderLeft(CellStyle.BORDER_THIN);
	        cs.setBorderRight(CellStyle.BORDER_THIN);
	        cs.setBorderTop(CellStyle.BORDER_THIN);
	        cs.setBorderBottom(CellStyle.BORDER_THIN);
	        cs.setAlignment(CellStyle.ALIGN_CENTER);

	        // 设置第二种单元格的样式（用于值）
	        cs2.setFont(f2);
	        cs2.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2.setBorderRight(CellStyle.BORDER_THIN);
	        cs2.setBorderTop(CellStyle.BORDER_THIN);
	        cs2.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2.setAlignment(CellStyle.ALIGN_CENTER);
	        //设置列名
	        for(int i=0;i<columnNames.length;i++){
	            Cell cell = row.createCell(i);
	            cell.setCellValue(columnNames[i]);
	            cell.setCellStyle(cs);
	        }
	        //设置每行每列的值
	        int rowss=1;
	        for (short i = 0; i < list.size(); i++) {
	            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	            // 创建一行，在页sheet上
	            Row row1 = sheet.createRow((short) rowss+i);
	            // 在row行上创建一个方格
	            for(short j=0;j<keys.length;j++){
	                Cell cell = row1.createCell(j);
	                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
	                cell.setCellStyle(cs2);
	            }
	        }
	        return wb;
	    }
	 public static Workbook createWorkBookx(List<Map<String, Object>> jg,
			 List<Map<String, Object>> qd,List<Map<String, Object>> zd,
			 String []keys,String columnNames[],String gzb,String gzb1,String gzb2) {
	        // 创建excel工作簿
	        Workbook wb = new HSSFWorkbook();
	        // 创建第一个sheet（页），并命名
	        Sheet sheet = wb.createSheet(gzb);
	        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	        for(int i=0;i<keys.length;i++){
	            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
	        }

	        // 创建第一行
	        Row row = sheet.createRow((short) 0);

	        // 创建两种单元格格式
	        CellStyle cs = wb.createCellStyle();
	        CellStyle cs2 = wb.createCellStyle();

	        // 创建两种字体
	        Font f = wb.createFont();
	        Font f2 = wb.createFont();

	        // 创建第一种字体样式（用于列名）
	        f.setFontHeightInPoints((short) 10);
	        f.setColor(IndexedColors.BLACK.getIndex());
	        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

	        // 创建第二种字体样式（用于值）
	        f2.setFontHeightInPoints((short) 10);
	        f2.setColor(IndexedColors.BLACK.getIndex());

//	        Font f3=wb.createFont();
//	        f3.setFontHeightInPoints((short) 10);
//	        f3.setColor(IndexedColors.RED.getIndex());

	        // 设置第一种单元格的样式（用于列名）
	        cs.setFont(f);
	        cs.setBorderLeft(CellStyle.BORDER_THIN);
	        cs.setBorderRight(CellStyle.BORDER_THIN);
	        cs.setBorderTop(CellStyle.BORDER_THIN);
	        cs.setBorderBottom(CellStyle.BORDER_THIN);
	        cs.setAlignment(CellStyle.ALIGN_CENTER);

	        // 设置第二种单元格的样式（用于值）
	        cs2.setFont(f2);
	        cs2.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2.setBorderRight(CellStyle.BORDER_THIN);
	        cs2.setBorderTop(CellStyle.BORDER_THIN);
	        cs2.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2.setAlignment(CellStyle.ALIGN_CENTER);
	        //设置列名
	        for(int i=0;i<columnNames.length;i++){
	            Cell cell = row.createCell(i);
	            cell.setCellValue(columnNames[i]);
	            cell.setCellStyle(cs);
	        }
	        //设置每行每列的值
	        int rowss=1;
	        for (short i = 0; i < jg.size(); i++) {
	            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	            // 创建一行，在页sheet上
	            Row row1 = sheet.createRow((short) rowss+i);
	            // 在row行上创建一个方格
	            for(short j=0;j<keys.length;j++){
	                Cell cell = row1.createCell(j);
	                cell.setCellValue(jg.get(i).get(keys[j]) == null?" ": jg.get(i).get(keys[j]).toString());
	                cell.setCellStyle(cs2);
	            }
	        }
	        
	        
	        
	        // 创建第二个sheet（页），并命名
	        Sheet sheet1 = wb.createSheet(gzb1);
	        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	        for(int i=0;i<keys.length;i++){
	        	sheet1.setColumnWidth((short) i, (short) (35.7 * 150));
	        }

	        // 创建第一行
	        Row row1 = sheet1.createRow((short) 0);

	        // 创建两种单元格格式
	        CellStyle csx = wb.createCellStyle();
	        CellStyle cs2x = wb.createCellStyle();

	        // 创建两种字体
	        Font fx = wb.createFont();
	        Font f2x = wb.createFont();

	        // 创建第一种字体样式（用于列名）
	        fx.setFontHeightInPoints((short) 10);
	        fx.setColor(IndexedColors.BLACK.getIndex());
	        fx.setBoldweight(Font.BOLDWEIGHT_BOLD);

	        // 创建第二种字体样式（用于值）
	        f2x.setFontHeightInPoints((short) 10);
	        f2x.setColor(IndexedColors.BLACK.getIndex());

//	        Font f3=wb.createFont();
//	        f3.setFontHeightInPoints((short) 10);
//	        f3.setColor(IndexedColors.RED.getIndex());

	        // 设置第一种单元格的样式（用于列名）
	        csx.setFont(f);
	        csx.setBorderLeft(CellStyle.BORDER_THIN);
	        csx.setBorderRight(CellStyle.BORDER_THIN);
	        csx.setBorderTop(CellStyle.BORDER_THIN);
	        csx.setBorderBottom(CellStyle.BORDER_THIN);
	        csx.setAlignment(CellStyle.ALIGN_CENTER);

	        // 设置第二种单元格的样式（用于值）
	        cs2x.setFont(f2);
	        cs2x.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2x.setBorderRight(CellStyle.BORDER_THIN);
	        cs2x.setBorderTop(CellStyle.BORDER_THIN);
	        cs2x.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2x.setAlignment(CellStyle.ALIGN_CENTER);
	        //设置列名
	        for(int i=0;i<columnNames.length;i++){
	            Cell cell = row1.createCell(i);
	            cell.setCellValue(columnNames[i]);
	            cell.setCellStyle(cs);
	        }
	        //设置每行每列的值
	        int rowssx=1;
	        for (short i = 0; i < qd.size(); i++) {
	            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	            // 创建一行，在页sheet上
	            Row row1x = sheet1.createRow((short) rowssx+i);
	            // 在row行上创建一个方格
	            for(short j=0;j<keys.length;j++){
	                Cell cell = row1x.createCell(j);
	                cell.setCellValue(qd.get(i).get(keys[j]) == null?" ": qd.get(i).get(keys[j]).toString());
	                cell.setCellStyle(cs2);
	            }
	        }
	        
	        
	        
	     // 创建第三个sheet（页），并命名
	        Sheet sheet11 = wb.createSheet(gzb2);
	        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	        for(int i=0;i<keys.length;i++){
	        	sheet11.setColumnWidth((short) i, (short) (35.7 * 150));
	        }

	        // 创建第一行
	        Row row11 = sheet11.createRow((short) 0);

	        // 创建两种单元格格式
	        CellStyle csx1 = wb.createCellStyle();
	        CellStyle cs2x1 = wb.createCellStyle();

	        // 创建两种字体
	        Font fx1 = wb.createFont();
	        Font f2x1 = wb.createFont();

	        // 创建第一种字体样式（用于列名）
	        fx1.setFontHeightInPoints((short) 10);
	        fx1.setColor(IndexedColors.BLACK.getIndex());
	        fx1.setBoldweight(Font.BOLDWEIGHT_BOLD);

	        // 创建第二种字体样式（用于值）
	        f2x1.setFontHeightInPoints((short) 10);
	        f2x1.setColor(IndexedColors.BLACK.getIndex());

//	        Font f3=wb.createFont();
//	        f3.setFontHeightInPoints((short) 10);
//	        f3.setColor(IndexedColors.RED.getIndex());

	        // 设置第一种单元格的样式（用于列名）
	        csx1.setFont(f);
	        csx1.setBorderLeft(CellStyle.BORDER_THIN);
	        csx1.setBorderRight(CellStyle.BORDER_THIN);
	        csx1.setBorderTop(CellStyle.BORDER_THIN);
	        csx1.setBorderBottom(CellStyle.BORDER_THIN);
	        csx1.setAlignment(CellStyle.ALIGN_CENTER);

	        // 设置第二种单元格的样式（用于值）
	        cs2x1.setFont(f2);
	        cs2x1.setBorderLeft(CellStyle.BORDER_THIN);
	        cs2x1.setBorderRight(CellStyle.BORDER_THIN);
	        cs2x1.setBorderTop(CellStyle.BORDER_THIN);
	        cs2x1.setBorderBottom(CellStyle.BORDER_THIN);
	        cs2x1.setAlignment(CellStyle.ALIGN_CENTER);
	        //设置列名
	        for(int i=0;i<columnNames.length;i++){
	            Cell cell = row11.createCell(i);
	            cell.setCellValue(columnNames[i]);
	            cell.setCellStyle(cs);
	        }
	        //设置每行每列的值
	        int rowssx1=1;
	        for (short i = 0; i < zd.size(); i++) {
	            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	            // 创建一行，在页sheet上
	            Row row1x = sheet11.createRow((short) rowssx1+i);
	            // 在row行上创建一个方格
	            for(short j=0;j<keys.length;j++){
	                Cell cell = row1x.createCell(j);
	                cell.setCellValue(zd.get(i).get(keys[j]) == null?" ": zd.get(i).get(keys[j]).toString());
	                cell.setCellStyle(cs2);
	            }
	        }
	        return wb;
	    }
	 
	 public static void Excel2007AboveOperate(List<Map<String, Object>> list,
			 String []keys,String columnNames[],String gzb,String filepath) throws IOException {
	        XSSFWorkbook workbook1 = new XSSFWorkbook();
	        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 1);
	        Sheet first = sxssfWorkbook.getSheetAt(0);
	        for (int i = 0; i < list.size(); i++) {
	            Row row = first.createRow(i);
	            for (int j = 0; j < columnNames.length; j++) {
	                if(i == 0) {
	                    // 首行
	                    row.createCell(j).setCellValue(columnNames[j]);
	                } else {
                        CellUtil.createCell(row, j, list.get(i-1).get(keys[j]) == null?" ": list.get(i-1).get(keys[j]).toString());
	                }
	            }
	        }
	        FileOutputStream out = new FileOutputStream(filepath);
	        sxssfWorkbook.write(out);
	        out.close();
	    }
	 
}
