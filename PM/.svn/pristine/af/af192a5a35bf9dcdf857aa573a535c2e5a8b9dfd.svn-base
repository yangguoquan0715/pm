package com.gz.pm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gz.pm.constant.Config;
import com.gz.pm.constant.ConsInfo;
import com.gz.pm.pojo.po.PmProjectTab;
import com.gz.pm.pojo.po.PmProjectTabExample;
import com.gz.pm.pojo.vo.PmUserVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.service.PmProjectService;
import com.gz.pm.service.PmUserService;
import com.gz.pm.util.PropertyManager;

/**
 * Title: LoginController
 * Description: 用户认证
 */
@Controller
public class LoginController {

	@Autowired
	private PmUserService pmUserService;
	
	@Autowired
	private PmProjectService pmProjectService;

	// 用户登陆页面
	@RequestMapping("/login")
	public ModelAndView login() throws Exception {
		ModelAndView mv = new ModelAndView("/base/login");
		List<PmProjectTab> list = pmProjectService.findList(new PmProjectTabExample());
		mv.addObject("projectList", list);
		// 数据获取传到页面
		return mv;
	}

	// 用户登陆提交
	@RequestMapping("/loginsubmit")
	@ResponseBody
	public Object loginsubmit(HttpSession session,PmUserVo userVo)
			throws Exception {
		
		String username = userVo.getUsername();
		String pwd = userVo.getPwd();
		String projectid = userVo.getProjectid();
		
		ReturnObj ret = new ReturnObj();
		
		PmUserVo activeUser = null;
		
		PropertyManager p = new PropertyManager("resources/admin.properties");
		String adminusername = p.getPropertyStr("adminusername");
		String adminpassword = p.getPropertyStr("adminpassword");
		if(username.equals(adminusername)&&pwd.equals(adminpassword)){  //先匹配是否是管理员，管理员账号和密码配置在admin.properties文件里
			activeUser = new PmUserVo();
			activeUser.setUsername(ConsInfo.SYSTEM_MANAGER);
			activeUser.setName("系统管理员");
			activeUser.setRole(3);   //角色为3的话表示管理员
		}else{  //非管理员的登录判断
			if(projectid==null||"".equals(projectid)){  //非管理员需要选择项目
				ret.setMsg("非管理员账号请选择项目！");
				ret.setSuccess(false);
				return ret;
			}
			// service，用户认证
			activeUser = pmUserService.getUserWithProjectRole(userVo);  //通过username和projectid查询出用户
			if(activeUser==null){
				ret.setMsg("该项目没有此人员！");
				ret.setSuccess(false);
				return ret;
			}else{
				if(!pwd.equals(activeUser.getPwd())){
					ret.setMsg("密码错误！");
					ret.setSuccess(false);
					return ret;
				}
			}
			PmProjectTab pro = pmProjectService.get(projectid);
			activeUser.setProjectname(pro.getName());
		}
		
		
		// 将用户身份信息写入session
		session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);
		
		ret.setMsg("登录成功！");
		ret.setSuccess(true);

		return ret;
	}
	
	//退出
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		//session过期
		session.invalidate();
		return "redirect:login.action";
	}

}
