package helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Client {
	
	
	private Socket socket;
	public Client(){
		try{
			socket=new Socket("192.168.0.49",9995);
			System.out.println("socket连接成功！");
		}catch(Exception e){
			System.out.println("socket连接失败！");
			e.printStackTrace();
		}
	}
	//多线程启动
	public String start(String phone,String message){
		String [] phs = phone.split(",");
		String errordh="";
		ServerHander hander=new ServerHander();
		Thread t=new Thread(hander);
		t.setDaemon(true);
		t.start();
		try {
			int flag1=-1,flag2=-1,flag3=-1;
			flag1=login();
			System.out.println(phone+"--flag1 = "+flag1);
			if(flag1==0){
				for (int i = 0; i < phs.length; i++) {
					if(phs[i].length()==11){
						flag2=sendMes(phs[i],message);
					}
					if(flag2!=0){
						errordh+=phs[i]+",";
					}
				}
				flag3=logout();
			}else{
				return "{\"sfdl\":\"1\",\"errordh\":\""+errordh+"\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"sfdl\":\"1\",\"errordh\":\""+errordh+"\"}";
		}
		return "{\"sfdl\":\"0\",\"errordh\":\""+errordh+"\"}";
	}
	//登入
	public int login(){
		int flag=-1;
		try {
			OutputStream out=socket.getOutputStream();
			InputStream in=socket.getInputStream();
			byte funcNo=0x01;
			byte[] serialNumber=genSerialNumber();
			String con="baojing"+'\0'+"123456"+'\0';
			byte[] content=con.getBytes();
			byte[] head=ToMsgDataPacket(funcNo,serialNumber,content);
			out.write(head);
			byte[] b=new byte[20];
			in=socket.getInputStream();
			in.read(b);
			flag=b[8];
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	//发送
	public int sendMes(String phone,String message){
		int flag=-1;
		try{
			OutputStream out=socket.getOutputStream();
			InputStream in=socket.getInputStream();
			byte funcNo=0x03;
			byte[] serialNumber=genSerialNumber();
			String con1=""+'\0'+phone+'\0'+"0"+'\0';
			String con3=""+'\0'+"XX"+message+'\0';
			byte[] b1=con1.getBytes();
			byte[] b2=getTimes();
			byte[] b3=con3.getBytes();
			byte[] content=new byte[b1.length+b2.length+b3.length];
			int j=0;
			for(int i=0;i<b1.length;i++){
				content[j++]=b1[i];
			}
			for(int i=0;i<b2.length;i++){
				content[j++]=b2[i];
			}
			for(int i=0;i<b3.length;i++){
				content[j++]=b3[i];
			}
			
			byte[] head=ToMsgDataPacket(funcNo,serialNumber,content);
			out.write(head);
			byte[] b=new byte[20];
			in=socket.getInputStream();
			in.read(b);
			flag=b[8];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//登出
	public int logout(){
		int flag=-1;
		try {
			OutputStream out=socket.getOutputStream();
			InputStream in=socket.getInputStream();
			byte funcNo=0x02;
			byte[] serialNumber=genSerialNumber();
			String con="baojing"+'\0';
			byte[] content=con.getBytes();
			byte[] head=ToMsgDataPacket(funcNo,serialNumber,content);
			out.write(head);
			byte[] b=new byte[20];
			in=socket.getInputStream();
			in.read(b);
			flag=b[8];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	private class ServerHander implements Runnable{
		public void run(){
			
		}
	}

	public static byte[] genSerialNumber(){
		Random ran=new Random();
		int n=ran.nextInt(65500);
		byte[] b=new byte[2];
		b[0]=(byte)(n/256);
		b[1]=(byte)(n%256);
		return b;
	}

	public static byte[] ToMsgDataPacket(byte funcNo,byte[] serialNumber,byte[] content){//打包
		int pos;
		byte[] head=new byte[11+content.length];
		head[0]= (byte)0xff;
		head[1]=0x27;
		int n=2+1+1+2+1+content.length;
		head[2]=(byte) (n/256);
		head[3]=(byte) (n%256);
		head[4]=funcNo;
		head[5]=0;
		head[6]=serialNumber[0];
		head[7]=serialNumber[1];
		for(int i=0;i<content.length;i++){
			head[8+i]=content[i];
		}
		pos=8+content.length;
		int s=0;
		for(int i=2;i<8+content.length;i++){
			if(head[i]==-1){
				s+=255;
			}else{
				s+=head[i];
			}
			
		}
		head[pos]=(byte)s;
		head[pos+1]=(byte)0xff;
		head[pos+2]=0x28;
		return head;
	}

	public static byte[] getTimes(){//获取开机启动到现在的时间
        Process process;
        int time=0;
		try {
			process = Runtime.getRuntime().exec(
			        "cmd /c net statistics workstation");
			 String startUpTime = "";
		        BufferedReader bufferedReader = new BufferedReader(
		                new InputStreamReader(process.getInputStream(),"gbk"));
		        int i = 0;
		        String timeWith = "";
		        while ((timeWith = bufferedReader.readLine()) != null) {
		            if (i == 3) {
		                startUpTime = timeWith;
		            }
		            i++;
		        }
		        process.waitFor();
		        String s=startUpTime.substring(8);
		        Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-09-12 12:12:12");
//		        System.out.println("2015-09-12 12:12:12");
		        long time1=date1.getTime();
		        Date date2=new Date();
		        long time2=date2.getTime();
		        time=(int)(time2-time1);
		        
		        byte[] a=new byte[4];
		        int q=time;
		        a[0]=(byte)(q%256);
		        q=q/256;
		        a[1]=(byte)(q%256);
		        q=q/256;
		        a[2]=(byte)(q%256);
		        q=q/256;
		        a[3]=(byte)(q%256);
		        q=q/256;
//		        System.out.println(a);
		        return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
    }
	
}
