package com.gz.pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.constant.Config;
import com.gz.pm.pojo.po.PmProUserRelTabExample;
import com.gz.pm.pojo.po.PmUserTab;
import com.gz.pm.pojo.vo.PmUserVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.service.PmProUserRelService;
import com.gz.pm.service.PmUserService;
import com.gz.pm.util.Page;

@Controller
@RequestMapping("/user")
public class PmUserController {
	
	@Autowired
	private PmUserService pmUserService;
	
	@Autowired
	private PmProUserRelService pmProUserRelService;
	
	/**
	 * 跳转到用户管理页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageuser")
	public ModelAndView manageuser()throws Exception{
		ModelAndView mv = new ModelAndView("/base/user/manageuser");
		return mv;
	}
	
	/**
	 * 查询用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryuser")
	@ResponseBody
	public Object queryuser(PmUserVo vo,Integer page,Integer rows)throws Exception{
		page = page==null?1:page;
		rows = rows==null?15:rows;
		PageBounds pb = new PageBounds(page,rows);
		Page p = pmUserService.findUserPage(vo,pb);
		return p;
	}
	
	/**
	 * 跳转到用户新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toadduser")
	public ModelAndView toadduser()throws Exception{
		ModelAndView view = new ModelAndView("/base/user/addsysuser");
		return view;
	}
	/**
	 * 跳转到用户新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveuser",method={RequestMethod.POST})
	@ResponseBody
	public Object saveuser(PmUserTab user)throws Exception{
		ReturnObj result = pmUserService.saveuser(user);
		return result;
	}
	
	/**
	 * 跳转到用户修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toedituser")
	public ModelAndView toedituser(String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/user/editsysuser");
		try {
			PmUserTab user = pmUserService.get(id);
			view.addObject("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	/**
	 * 跳转到用户新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteuser",method={RequestMethod.GET})
	@ResponseBody
	public Object deleteuser(HttpServletRequest request)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DELETE_SUCCESS_MSG,null);
		try {
			String idStr = request.getParameter("ids");
			String[] ids = idStr.split(",");
			PmProUserRelTabExample example = new PmProUserRelTabExample();
			PmProUserRelTabExample.Criteria cri = null;
			for(String id:ids){
				cri = example.createCriteria();
				cri.andUseridEqualTo(id);
				pmProUserRelService.deleteByExample(example); //删除项目人员关系表里的关系
				pmUserService.delete(id);
			}
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DELETE_FAIL_MSG);
		}
		return result;
	}
	/**
	 * 跳转到用户密码修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toupdatepwd")
	public ModelAndView toupdatepwd()throws Exception{
		ModelAndView view = new ModelAndView("/base/user/updatepwd");
		return view;
	}
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatepwd",method={RequestMethod.POST})
	@ResponseBody
	public Object updatepwd(HttpServletRequest request,String oldpwd,String newpwd)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.UPDATE_SUCCESS_MSG,null);
		try {
			HttpSession session = request.getSession();
			PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
			if(!oldpwd.equals(activeUser.getPwd())){
				result.setSuccess(ReturnObj.FAIL);
				result.setMsg("原密码输入错误！");
				return result;
			}
			PmUserTab tab = pmUserService.get(activeUser.getId());
			tab.setPwd(newpwd);
			pmUserService.updateSelective(tab);
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.UPDATE_FAIL_MSG);
		}
		return result;
	}
	
	
}
