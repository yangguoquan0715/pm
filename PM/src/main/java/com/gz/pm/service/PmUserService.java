package com.gz.pm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.dao.PmUserTabCusMapper;
import com.gz.pm.dao.PmUserTabMapper;
import com.gz.pm.pojo.po.PmUserTab;
import com.gz.pm.pojo.po.PmUserTabExample;
import com.gz.pm.pojo.vo.PmProjectTreeVo;
import com.gz.pm.pojo.vo.PmUserVo;
import com.gz.pm.process.ReturnObj;
import com.gz.pm.util.Page;
import com.gz.pm.util.Pinyin4j;
import com.gz.pm.util.PinyinUtil;
import com.gz.pm.util.UUIDBuild;

@Transactional
@Service
public class PmUserService  extends BaseService<PmUserTabMapper,PmUserTabExample,PmUserTab>{
	
	@Autowired
	public void setPmUserTabMapper(PmUserTabMapper PmUserTabMapper) {
		super.setMapper(PmUserTabMapper);
	}
	
	@Autowired
	private PmUserTabCusMapper pmUserTabCusMapper;
	
	/**
	 * 分页查询用户
	 * @param vo
	 * @param pb
	 * @return
	 */
	public Page findUserPage(PmUserVo vo, PageBounds pb) {
		if(vo.getName()!=null&&!"".equals(vo.getName())){
			vo.setName("%"+vo.getName()+"%");
		}
		List list= pmUserTabCusMapper.findUserPageList(vo, pb);
		int total = pmUserTabCusMapper.findUserPageCount(vo);
		Page page = new Page(pb,list,total);
		return page;
	}
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public ReturnObj saveuser(PmUserTab user) {
		ReturnObj result = new ReturnObj(ReturnObj.SUCCESS,ReturnObj.SAVE_SUCCESS_MSG,null);
		try{
			String name = user.getName();
			if(name!=null&&!"".equals(name)){
				user.setPinyin(PinyinUtil.getFullSpell(name));
				user.setJianpin(PinyinUtil.getFirstSpell(name));
			}
			if(user.getId()!=null&&user.getId().length()>0){ //id存在，做更新操作
				this.update(user);
				result.setMsg(ReturnObj.UPDATE_SUCCESS_MSG);
			}else{  //做保存操作
				user.setId(UUIDBuild.getUUID());
				user.setPwd("123456");
				this.save(user);
				result.setMsg(ReturnObj.SAVE_SUCCESS_MSG);
			}
		}catch(Exception e){
			result.setSuccess(ReturnObj.FAIL);
			if(user.getId()!=null&&user.getId().length()>0){
				result.setMsg(ReturnObj.UPDATE_FAIL_MSG);
			}else{
				result.setMsg(ReturnObj.SAVE_FAIL_MSG);
			}
			e.printStackTrace();
		}
		return result;
	}
	public List<PmProjectTreeVo> getProjectTreeUser(String projectId) {
		return pmUserTabCusMapper.getProjectTreeUser(projectId);
	}
	public PmUserVo getUserWithProjectRole(PmUserVo userVo) {
		return pmUserTabCusMapper.getUserWithProjectRole(userVo);
	}
	public List<PmUserVo> getUserListByProjectId(String projectid) {
		return pmUserTabCusMapper.getUserListByProjectId(projectid);
	}
	
	private List search(){
		List<PmUserTab> list = null;
		PmUserTabExample example = new PmUserTabExample();
		PmUserTabExample.Criteria cri = example.createCriteria();
		cri.andUsernameEqualTo("yangugoquan");
		mapper.selectByExample(example, new PageBounds());
		return null;
	}
	
}
