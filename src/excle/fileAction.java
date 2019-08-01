package excle;
//package excle;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts2.ServletActionContext;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.tw.test.jxlTest;
//
//@SuppressWarnings("serial")
//public class FileAction extends ActionSupport {
//	private String xlsfilename;
//	private String fanhuei;
//	private String action;
//    private File file;
//    private String fileFileName;
//    private String fileFileContentType;
//    private String message = "0"; // 0格式错误 1成功(文件路径)  2失败
//    private String filePath;
//    private String vhic;
//    private List<String> list;
//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public String getXlsfilename() {
//		return xlsfilename;
//	}
//
//	public void setXlsfilename(String xlsfilename) {
//		this.xlsfilename = xlsfilename;
//	}
//
//	public String getFanhuei() {
//		return fanhuei;
//	}
//
//	public void setFanhuei(String fanhuei) {
//		this.fanhuei = fanhuei;
//	}
//
//	public String getAction() {
//		return action;
//	}
//
//	public void setAction(String action) {
//		this.action = action;
//	}
//
//	public String getVhic() {
//		return vhic;
//	}
//
//	public void setVhic(String vhic) {
//		this.vhic = vhic;
//	}
//
//	public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
//
//    public String getFileFileName() {
//        return fileFileName;
//    }
//
//    public void setFileFileName(String fileFileName) {
//        this.fileFileName = fileFileName;
//    }
//
//    public String getFileFileContentType() {
//        return fileFileContentType;
//    }
//
//    public void setFileFileContentType(String fileFileContentType) {
//        this.fileFileContentType = fileFileContentType;
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public String execute() throws Exception {
//        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20", " ")+"com/tw/excle/";
//        File file = new File(path); // 判断件夹是否存在,如果不存在则创建文件夹
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        String[] fileSuffix = new String[] { ".exe" };// 禁止文件
//        try {
//            File f = this.getFile();
//            // 判断文件格式
//            for (int i = 0; i < fileSuffix.length; i++) {
//                if (fileFileName.endsWith(fileSuffix[i])) {
//                    message = "0";
//                    return ERROR;
//                }
//            }
//            FileInputStream inputStream = new FileInputStream(f);
//            FileOutputStream outputStream = new FileOutputStream(path + "\\"
//                    + fileFileName);
//            byte[] buf = new byte[1024];
//            int length = 0;
//            while ((length = inputStream.read(buf)) != -1) {
//                outputStream.write(buf, 0, length);
//            }
//            inputStream.close();
//            outputStream.flush();
//            DelAllFile d=new DelAllFile();
//			d.delAllFile(Thread.currentThread().getContextClassLoader().getResource("").
//            		getPath().replaceAll("%20", " ")+"com/tw/excle/");
//            jxlTest j=new jxlTest();
////           list= j.readXls(Thread.currentThread().getContextClassLoader().getResource("").
////            		getPath().replaceAll("%20", " ")+"com/tw/excle/"+this.getFileFileName());
//            message="导入成功";
//        } catch (Exception e) {
//            e.printStackTrace();
//            message = "导入失败,请稍后重试";
//        }
//        return SUCCESS;
//    }
//
//    public String download() {
//        String path = filePath;
//        HttpServletResponse response = ServletActionContext.getResponse();
//        try {
//            // path是指欲下载的文件的路径。
//            File file = new File(path);
//            // 取得文件名。
//            String filename = file.getName();
//            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1)
//                    .toUpperCase();
//
//            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            String filenameString = new String(filename.getBytes("gbk"),
//                    "iso-8859-1");
//            response.addHeader("Content-Disposition", "attachment;filename="
//                    + filenameString);
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response
//                    .getOutputStream());
//            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//	public List<String> getList() {
//		return list;
//	}
//
//	public void setList(List<String> list) {
//		this.list = list;
//	}
//    
//}