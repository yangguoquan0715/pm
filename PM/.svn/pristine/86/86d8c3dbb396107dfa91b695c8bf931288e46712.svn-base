package com.gz.pm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gz.pm.pojo.po.PmProUserRelTab;
import com.gz.pm.pojo.po.PmProUserRelTabExample;
import com.gz.pm.pojo.po.PmProjectTab;
import com.gz.pm.pojo.po.PmProjectTabExample;
import com.gz.pm.pojo.vo.PmProjectTreeVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.service.PmProUserRelService;
import com.gz.pm.service.PmProjectService;
import com.gz.pm.service.PmUserService;
import com.gz.pm.util.UUIDBuild;

@Controller
@RequestMapping("/project")
public class PmProjectController {
	
	@Autowired
	private PmProjectService pmProjectService;
	
	@Autowired
	private PmUserService pmUserService;
	
	@Autowired
	private PmProUserRelService pmProUserRelService;
	
	/**
	 * 跳转到用户管理页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/manageproject")
	public ModelAndView manageproject()throws Exception{
		ModelAndView mv = new ModelAndView("/base/project/manageproject");
		return mv;
	}
	
	/**
	 * 查询项目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryprojecttree")
	@ResponseBody
	public Object queryprojecttree(String name)throws Exception{
		List<PmProjectTreeVo> list = new ArrayList<PmProjectTreeVo>();
		PmProjectTabExample example = new PmProjectTabExample();
		example.setOrderByClause("createtime desc");
		PmProjectTabExample.Criteria cri = example.createCriteria();
		if(name!=null&&name.length()>0){
			cri.andNameLike("%"+name+"%");
		}
		List<PmProjectTab> projectList = pmProjectService.findList(example);
		PmProjectTreeVo vo = null;
		for(PmProjectTab project:projectList){
			vo = new PmProjectTreeVo();
			vo.setId(project.getId());
			vo.setName(project.getName());
			vo.setMemo(project.getDescription());
			List<PmProjectTreeVo> children = pmUserService.getProjectTreeUser(vo.getId());
			vo.setChildren(children);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 跳转到用户新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toaddproject")
	public ModelAndView toaddproject()throws Exception{
		ModelAndView view = new ModelAndView("/base/project/addproject");
		return view;
	}
	/**
	 * 跳转到用户新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveproject",method={RequestMethod.POST})
	@ResponseBody
	public Object saveproject(PmProjectTab project)throws Exception{
		ReturnObj result = pmProjectService.saveproject(project);
		return result;
	}
	
	/**
	 * 跳转到用户修改页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toeditproject")
	public ModelAndView toeditproject(String id)throws Exception{
		ModelAndView view = new ModelAndView("/base/project/editproject");
		try {
			PmProjectTab project = pmProjectService.get(id);
			view.addObject("project", project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	/**
	 * 删除项目或用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteproject",method={RequestMethod.GET})
	@ResponseBody
	public Object deleteproject(HttpServletRequest request)throws Exception{
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.DELETE_SUCCESS_MSG,null);
		try {
			String idStr = request.getParameter("ids");
			String[] ids = idStr.split(",");
			int i = 0;

			PmProUserRelTabExample example = new PmProUserRelTabExample();
			PmProUserRelTabExample.Criteria cri = null;
			
			for(String id:ids){
				i = pmProjectService.delete(id);
				if(i==1){  //如果删除项目成功，则要删除项目角色关系表里的关系
					cri = example.createCriteria();
					cri.andProjectidEqualTo(id);
					pmProUserRelService.deleteByExample(example);
				}else{//如果删除项目不成功，则id为项目用户关系表的id，直接删除关系即可
					pmProUserRelService.delete(id);
				}
			}
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			result.setMsg(ReturnObj.DELETE_FAIL_MSG);
		}
		return result;
	}

	/**
	 * 跳转添加用户页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toaddprojectuser")
	public ModelAndView toaddprojectuser(String projectId)throws Exception{
		ModelAndView view = new ModelAndView("/base/project/addprojectuser");
		view.addObject("projectId", projectId);
		return view;
	}
	@RequestMapping("/addprojectuser")
	@ResponseBody
	public Object addprojectuser(String userId,String projectId)throws Exception{
		ReturnObj ret = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.ADD_SUCCESS_MSG,null);
		try {
			PmProUserRelTab tab = null;
			String[] ids = userId.split(",");
			for(String id:ids){
				List<PmProUserRelTab> list = pmProUserRelService.getProUserRelList(projectId,id); //判断项目和用户关系是否存在
				if(list!=null&&list.size()>0){
					continue;
				}
				tab = new PmProUserRelTab();
				tab.setId(UUIDBuild.getUUID());
				tab.setUserid(id);
				tab.setProjectid(projectId);
				tab.setRole(0);
				pmProUserRelService.save(tab);
			}
		} catch (Exception e) {
			ret.setSuccess(ReturnObj.FAIL);
			ret.setMsg(ReturnObj.ADD_FAIL_MSG);
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 判断项目中是否存在组长，因为，一个组只能有一个组长
	 */
	@RequestMapping("/existsLeader")
	@ResponseBody
	public Object existsLeader(String projectid)throws Exception{
		ReturnObj ret = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.SET_SUCCESS_MSG,null);
		try {
			PmProUserRelTabExample example = new PmProUserRelTabExample();
			PmProUserRelTabExample.Criteria cri = example.createCriteria();
			cri.andProjectidEqualTo(projectid);
			cri.andRoleEqualTo(2);//2表示组长
			List<PmProUserRelTab> list = pmProUserRelService.findList(example);
			if(list==null||list.size()==0){ //表示不存在
				ret.setSuccess(ReturnObj.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 设置组长
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public Object updateRole(String id,String projectid,Integer role)throws Exception{
		ReturnObj ret = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.SET_SUCCESS_MSG,null);
		try {
			if(role==2){  //如果是设置组长，则需要先取消原有组长，然后再设置为新组长
				pmProUserRelService.setLeader(id,projectid);
			}else{
				PmProUserRelTab tab = pmProUserRelService.get(id);
				tab.setRole(role);
				pmProUserRelService.updateSelective(tab);
			}
		} catch (Exception e) {
			ret.setSuccess(ReturnObj.FAIL);
			ret.setMsg(ReturnObj.SET_FAIL_MSG);
			e.printStackTrace();
		}
		return ret;
	}
}
