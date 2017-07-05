package com.gz.pm.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.dao.PmTaskTabCusMapper;
import com.gz.pm.dao.PmTaskTabMapper;
import com.gz.pm.pojo.po.PmTaskTab;
import com.gz.pm.pojo.po.PmTaskTabExample;
import com.gz.pm.pojo.vo.PmTaskVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.util.Page;
import com.gz.pm.util.UUIDBuild;

@Transactional
@Service
public class PmTaskService extends BaseService<PmTaskTabMapper,PmTaskTabExample,PmTaskTab>{

	@Autowired
	public void setPmTaskTabMapper(PmTaskTabMapper PmTaskTabMapper) {
		super.setMapper(PmTaskTabMapper);
	}
	
	@Autowired
	private PmTaskTabCusMapper pmTaskTabCusMapper;
	
	public Page findTaskPage(PmTaskVo vo, PageBounds pb) {
		List list= pmTaskTabCusMapper.findTaskPageList(vo, pb);
		int total = pmTaskTabCusMapper.findTaskPageCount(vo);
		Page page = new Page(pb,list,total);
		return page;
	}

	public ReturnObj savetask(PmTaskVo task) {
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.SAVE_SUCCESS_MSG,null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(task.getId()!=null&&task.getId().length()>0){
				PmTaskTab tab = mapper.selectByPrimaryKey(task.getId());
				tab.setName(task.getName());
				tab.setJhstarttime(sdf.parse(task.getStarttime())); //转换时间
				tab.setJhendtime(sdf.parse(task.getEndtime()));
				tab.setUserid(task.getUserid());
				tab.setDescription(task.getDescription());
				this.update(tab);
				result.setMsg(ReturnObj.UPDATE_SUCCESS_MSG);
			}else{
				PmTaskTab tab = new PmTaskTab();
				BeanUtils.copyProperties(task, tab);
				tab.setId(UUIDBuild.getUUID());
				tab.setJhstarttime(sdf.parse(task.getStarttime())); //转换时间
				tab.setJhendtime(sdf.parse(task.getEndtime()));
				tab.setState(0);//初始化状态
				this.save(tab);
				result.setMsg(ReturnObj.SAVE_SUCCESS_MSG);
			}
		} catch (Exception e) {
			result.setSuccess(ReturnObj.FAIL);
			if(task.getId()!=null&&task.getId().length()>0){
				result.setMsg(ReturnObj.UPDATE_FAIL_MSG);
			}else{
				result.setMsg(ReturnObj.SAVE_FAIL_MSG);
			}
			e.printStackTrace();
		}
		return result;
	}
	
}
