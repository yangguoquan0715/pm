package com.gz.pm.pojo.vo;

import java.util.ArrayList;
import java.util.List;

public class PmProjectTreeVo {
	
	private String id;
	private String name;
	private String memo;
	private String role;
	private String iconCls;
	private String parent;
	
	private List<PmProjectTreeVo> children = new ArrayList<PmProjectTreeVo>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<PmProjectTreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<PmProjectTreeVo> children) {
		this.children = children;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	
}
