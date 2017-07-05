package com.gz.pm.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.pojo.vo.PmProjectTreeVo;
import com.gz.pm.pojo.vo.PmUserVo;

public interface PmUserTabCusMapper{

	List findUserPageList(PmUserVo vo, PageBounds pb);

	int findUserPageCount(PmUserVo vo);

	List<PmProjectTreeVo> getProjectTreeUser(String projectId);

	PmUserVo getUserWithProjectRole(PmUserVo userVo);

	List<PmUserVo> getUserListByProjectId(String projectid);
	
}