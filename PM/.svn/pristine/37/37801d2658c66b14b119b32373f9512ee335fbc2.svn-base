package com.gz.pm.dao;

import java.util.List;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.gz.pm.pojo.po.PmTaskTab;
import com.gz.pm.pojo.po.PmTaskTabExample;
import com.gz.pm.pojo.vo.PmTaskVo;

public interface PmTaskTabCusMapper extends BaseMapper<PmTaskTab, PmTaskTabExample, String> {

	List findTaskPageList(PmTaskVo vo, PageBounds pb);

	int findTaskPageCount(PmTaskVo vo);
}