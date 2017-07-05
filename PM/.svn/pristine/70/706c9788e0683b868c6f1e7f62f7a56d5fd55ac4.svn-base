package com.gz.pm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gz.pm.dao.PmProjectTabMapper;
import com.gz.pm.pojo.po.PmProjectTab;
import com.gz.pm.pojo.po.PmProjectTabExample;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.util.UUIDBuild;

@Transactional
@Service
public class PmProjectService  extends BaseService<PmProjectTabMapper,PmProjectTabExample,PmProjectTab>{
	
	@Autowired
	public void setPmProjectTabMapper(PmProjectTabMapper PmProjectTabMapper) {
		super.setMapper(PmProjectTabMapper);
	}

	public ReturnObj saveproject(PmProjectTab project) {
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.SAVE_SUCCESS_MSG,null);
		try{
			if(project.getId()!=null&&project.getId().length()>0){ //id存在，做更新操作
				this.updateSelective(project);
				result.setMsg(ReturnObj.UPDATE_SUCCESS_MSG);
			}else{  //做保存操作
				project.setId(UUIDBuild.getUUID());
				project.setCreatetime(new Date());
				this.save(project);
				result.setMsg(ReturnObj.SAVE_SUCCESS_MSG);
			}
		}catch(Exception e){
			result.setSuccess(ReturnObj.FAIL);
			if(project.getId()!=null&&project.getId().length()>0){
				result.setMsg(ReturnObj.UPDATE_FAIL_MSG);
			}else{
				result.setMsg(ReturnObj.SAVE_FAIL_MSG);
			}
			e.printStackTrace();
		}
		return result;
	}
	
}
