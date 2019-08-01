package mvc.controllers;


import helper.DownloadAct;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.service.CommonServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 公共基础类，通用方法
 * 公司、分公司、车辆等下拉框
 */
@Controller
@RequestMapping(value = "/common")
public class CommonAction {
	private CommonServer commonService;

	public CommonServer getCommonServer() {
		return commonService;
	}

	@Autowired
	public void setCommonServer(CommonServer commonService) {
		this.commonService = commonService;
	}
	//手机号注册
	@RequestMapping(value = "/register")
	@ResponseBody
	public String register(HttpServletRequest request,@RequestParam("phone") String phone,@RequestParam("password") String password
			) {
		String msg = commonService.register(phone,password);
		return msg;
	}
	//登录
	@RequestMapping(value = "/login")
	@ResponseBody
	public String login(HttpServletRequest request,@RequestParam("phone") String phone,@RequestParam("password") String password
			) {
		String msg = commonService.login(request,phone,password);
		return msg;
	}
	//认证
	@RequestMapping(value = "/apply")
	@ResponseBody
	public String apply(HttpServletRequest request,@RequestParam("user_name") String user_name,@RequestParam("id_card") String id_card,@RequestParam("vehicle_no") String vehicle_no
			,@RequestParam("id_card_pic") String id_card_pic,@RequestParam("vehicle_pic") String vehicle_pic,@RequestParam("license_pic") String license_pic,@RequestParam("id") String id
			) {
		String msg = commonService.apply(request,user_name,id_card,vehicle_no,id_card_pic,vehicle_pic,license_pic,id);
		return msg;
	}
	//上传
	@RequestMapping(value = "/importfile")
    @ResponseBody
    public String importfile(HttpServletRequest request) throws IOException {
        String msg = commonService.importfile(request);
        return msg;
    }
	//查询订单
	@RequestMapping(value = "/order")
    @ResponseBody
    public String order(HttpServletRequest request,@RequestParam("time") String time,@RequestParam("id") String id) throws IOException {
        String msg = commonService.order(request,time,id);
        return msg;
    }
	//修改密码
	@RequestMapping(value = "/resetpassword")
	@ResponseBody
	public String resetpassword(HttpServletRequest request,@RequestParam("phone") String phone,@RequestParam("password") String password
			) {
		String msg = commonService.resetpassword(phone,password);
		return msg;
	}
	//司机注册
	@RequestMapping(value = "/registerDriver")
	@ResponseBody
	public String registerDriver(HttpServletRequest request,@RequestParam("driver_name") String driver_name,@RequestParam("phone") String phone,
			@RequestParam("company") String company,@RequestParam("license") String license,@RequestParam("vehicle") String vehicle,
			@RequestParam("vehicle_oblique_pic") String vehicle_oblique_pic,@RequestParam("vehicle_forward_pic") String vehicle_forward_pic,@RequestParam("license_pic") String license_pic,@RequestParam("openId") String openId) {
		String msg = commonService.registerDriver(driver_name,phone,company,license,vehicle,vehicle_oblique_pic,vehicle_forward_pic,license_pic,openId);
		return msg;
	}
	//司机信息修改
	@RequestMapping(value = "/editDriver")
	@ResponseBody
	public String editDriver(HttpServletRequest request,@RequestParam("phone") String phone,@RequestParam("id") String id) {
		String msg = commonService.editDriver(phone,id);
		return msg;
	}
	//司机信息查询
	@RequestMapping(value = "/queryDriver")
	@ResponseBody
	public String queryDriver(HttpServletRequest request,@RequestParam("id") String id) {
		String msg = commonService.queryDriver(id);
		return msg;
	}
	//查询订单(2)
	@RequestMapping(value = "/queryOrder")
    @ResponseBody
    public String queryOrder(HttpServletRequest request,@RequestParam("stime") String stime,@RequestParam("etime") String etime,@RequestParam("id") String id){
        String msg = commonService.queryOrder(request,stime,etime,id);
        return msg;
    }
	//登录验证
	@RequestMapping(value = "/applogin")
    @ResponseBody
    public String applogin(HttpServletRequest request,@RequestParam("code") String code){
        String msg = commonService.applogin(code);
        return msg;
    }
}
