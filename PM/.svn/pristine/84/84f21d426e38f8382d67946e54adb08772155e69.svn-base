package com.gz.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.dao.PmProUserRelTabCusMapper;
import com.gz.pm.dao.PmProUserRelTabMapper;
import com.gz.pm.pojo.po.PmProUserRelTab;
import com.gz.pm.pojo.po.PmProUserRelTabExample;

@Transactional
@Service
public class PmProUserRelService extends BaseService<PmProUserRelTabMapper,PmProUserRelTabExample,PmProUserRelTab>{
	
	@Autowired
	public void setPmProUserRelTabMapper(PmProUserRelTabMapper PmProUserRelTabMapper) {
		super.setMapper(PmProUserRelTabMapper);
	}

	@Autowired
	private PmProUserRelTabCusMapper pmProUserRelTabCusMapper;
	
	public List<PmProUserRelTab> getProUserRelList(String projectId, String id) {
		PmProUserRelTabExample example = new PmProUserRelTabExample();
		PmProUserRelTabExample.Criteria cri = example.createCriteria();
		cri.andUseridEqualTo(id);
		cri.andProjectidEqualTo(projectId);
		return mapper.selectByExample(example, new PageBounds());
	}

	public void setLeader(String id,String projectid) {
		pmProUserRelTabCusMapper.cancelLeader(projectid);
		pmProUserRelTabCusMapper.setLeader(id);
	}

}
