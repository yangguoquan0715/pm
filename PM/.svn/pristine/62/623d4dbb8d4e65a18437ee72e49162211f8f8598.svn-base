package com.gz.pm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.constant.Config;
import com.gz.pm.pojo.po.PmTaskTab;
import com.gz.pm.pojo.vo.PmTaskVo;
import com.gz.pm.pojo.vo.PmUserVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.service.PmTaskService;
import com.gz.pm.service.PmUserService;
import com.gz.pm.util.Page;

@Controller
@RequestMapping("/task")
public class PmTaskController {

	@Autowired
	private PmTaskService pmTaskService;
	
	@Autowired
	private PmUserService pmUserService;
	
	/**
	 * 跳转到任务管理页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/managetask")
	public ModelAndView managetask(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView("/base/task/managetask");
		try {
			HttpSession session = request.getSession();
			PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
			List<PmUserVo> userList = pmUserService.getUserListByProjectId(activeUser.getProjectid());
			mv.addObject("userList", userList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 查询任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querytask")
	@ResponseBody
	public Object querytask(PmTaskVo vo,Integer page,Integer rows)throws Exception{
		page = page==null?1:page;
		rows = rows==null?15:rows;
		PageBounds pb = new PageBounds(page,rows);
		Page p = pmTaskService.findTaskPage(vo,pb);
		return p;
	}
	
	/**
	 * 跳转到任务新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toaddtask")
	public ModelAndView toaddtask(HttpServletRequest request)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/addtask");
		HttpSession session = request.getSession();
		try {
			PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
			List<PmUserVo> userList = pmUserService.getUserListByProjectId(activeUser.getProjectid());
			view.addObject("userList", userList);
			view.addObject("projectname", activeUser.getProjectname());
			view.addObject("projectid", activeUser.getProjectid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	/**
	 * 跳转到任务新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/savetask",method={RequestMethod.POST})
	@ResponseBody
	public Object savetask(PmTaskVo task)throws Exception{
		ReturnObj result = pmTaskService.savetask(task);
		return result;
	}
	
	/**
	 * 加载出任务，并根据action跳转到不同页面
	 * @param request
	 * @param action
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadtask")
	public ModelAndView loadtask(HttpServletRequest request,String action,String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/edittask");
		if("view".equals(action)){ //跳转到查看页面
			view.setViewName("/base/task/viewtask");
		}
		HttpSession session = request.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);

			List<PmUserVo> userList = pmUserService.getUserListByProjectId(activeUser.getProjectid());
			view.addObject("userList", userList);
			
			PmTaskTab task = pmTaskService.get(id);
			PmTaskVo taskVo = new PmTaskVo();
			BeanUtils.copyProperties(task, taskVo);
			taskVo.setProjectname(activeUser.getProjectname());
			taskVo.setStarttime(sdf.format(task.getJhstarttime()));
			taskVo.setEndtime(sdf.format(task.getJhendtime()));
			if("copy".equals(action)){  //跳转到复制页面，其实就是编辑页面，但是没有id
				taskVo.setId("");
			}
			view.addObject("task", taskVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	/**
	 * 跳转到任务新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deletetask",method={RequestMethod.GET})
	@ResponseBody
	public Object deletetask(HttpServletRequest request)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DELETE_SUCCESS_MSG,null);
		try {
			String idStr = request.getParameter("ids");
			String[] ids = idStr.split(",");
			for(String id:ids){
				pmTaskService.delete(id);
			}
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DELETE_FAIL_MSG);
		}
		return result;
	}
	/**
	 * 跳转到任务新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myTasks")
	public ModelAndView myTasks(HttpServletRequest request)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/mytasks");
		return view;
	}
	/**
	 * 查询我的任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querymytask")
	@ResponseBody
	public Object querymytask(HttpServletRequest request,PmTaskVo vo,Integer page,Integer rows)throws Exception{
		page = page==null?1:page;
		rows = rows==null?15:rows;
		HttpSession session = request.getSession();
		PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
		vo.setUserid(activeUser.getId());
		PageBounds pb = new PageBounds(page,rows);
		Page p = pmTaskService.findTaskPage(vo,pb);
		return p;
	}
	/**
	 * 启动任务
	 * @param request
	 * @param tab
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startTask")
	@ResponseBody
	public Object startTask(HttpServletRequest request,PmTaskTab tab)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DO_SUCCESS_MSG,null);
		try {
			tab.setSjstarttime(new Date());
			tab.setState(1);
			pmTaskService.updateSelective(tab);
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DO_FAIL_MSG);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 提交任务
	 * @param request
	 * @param tab
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitTask")
	@ResponseBody
	public Object submitTask(HttpServletRequest request,PmTaskTab tab)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DO_SUCCESS_MSG,null);
		try {
			tab.setState(2);
			pmTaskService.updateSelective(tab);
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DO_FAIL_MSG);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 跳转到我的bug页面
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myBug")
	public ModelAndView myBug(HttpServletRequest request)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/mybug");
		return view;
	}
	/**
	 * bug查看
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showBug")
	public ModelAndView showBug(HttpServletRequest request,String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/showbug");
		PmTaskTab tab = pmTaskService.get(id);
		view.addObject("bug", tab.getBug());
		return view;
	}
	/**
	 * 任务描述查看
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/showdes")
	public ModelAndView showdes(HttpServletRequest request,String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/showdes");
		PmTaskTab tab = pmTaskService.get(id);
		view.addObject("description", tab.getDescription());
		return view;
	}
	/**
	 * 查询我的bug
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querymybug")
	@ResponseBody
	public Object querymybug(HttpServletRequest request,PmTaskVo vo,Integer page,Integer rows)throws Exception{
		page = page==null?1:page;
		rows = rows==null?15:rows;
		HttpSession session = request.getSession();
		PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
		vo.setUserid(activeUser.getId());
		vo.setState(3);
		PageBounds pb = new PageBounds(page,rows);
		Page p = pmTaskService.findTaskPage(vo,pb);
		return p;
	}
	/**
	 * 查询测试任务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querytestingtask")
	@ResponseBody
	public Object querytestingtask(HttpServletRequest request,PmTaskVo vo,Integer page,Integer rows)throws Exception{
		page = page==null?1:page;
		rows = rows==null?15:rows;
		HttpSession session = request.getSession();
		PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
		vo.setState(2);
		vo.setProjectid(activeUser.getProjectid());
		PageBounds pb = new PageBounds(page,rows);
		Page p = pmTaskService.findTaskPage(vo,pb);
		return p;
	}
	/**
	 * 测试任务管理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/testingtask")
	public ModelAndView testingtask(HttpServletRequest request,String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/testingtask");
		return view;
	}
	/**
	 * 提出bug
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toproposebug")
	public ModelAndView toproposebug(HttpServletRequest request,String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/task/proposebug");
		view.addObject("id", id);
		PmTaskTab t = pmTaskService.get(id);
		view.addObject("bug", t.getBug());
		return view;
	}
	/**
	 * 完成任务
	 * @param request
	 * @param tab
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/finishtask")
	@ResponseBody
	public Object finishtask(HttpServletRequest request,PmTaskTab tab)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DO_SUCCESS_MSG,null);
		try {
			tab.setState(4);
			tab.setSjendtime(new Date());
			pmTaskService.updateSelective(tab);
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DO_FAIL_MSG);
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping("/proposebug")
	@ResponseBody
	public Object proposebug(HttpServletRequest request,PmTaskTab tab)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DO_SUCCESS_MSG,null);
		try {
			HttpSession session = request.getSession();
			PmUserVo activeUser = (PmUserVo) session.getAttribute(Config.ACTIVEUSER_KEY);
			tab.setState(3);
			tab.setTesterid(activeUser.getId());
			pmTaskService.updateSelective(tab);
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DO_FAIL_MSG);
			e.printStackTrace();
		}
		return result;
	}
}
