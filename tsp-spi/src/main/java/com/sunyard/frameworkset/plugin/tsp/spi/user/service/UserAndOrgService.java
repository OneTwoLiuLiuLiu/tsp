package com.sunyard.frameworkset.plugin.tsp.spi.user.service;

import java.util.List;

/**
 * 获取用户和用户机构的对外接口,需要具体的应用实现
 * @author whj
 *
 */
public interface UserAndOrgService {

	/**
	 * 获取当前登录的用户
	 * @return
	 */
	public UserVo getCurrentUser();
	
	/**
	 * 根据用户的唯一标识符,查找用户
	 * @param code 用户的唯一标识符。例如ID之类的
	 * @return
	 */
	public UserVo findByCode(String userCode);
	
	/**
	 * 根据上级机构返回所有子机构
	 * @param orgcode orgcode为null时,一般返回最上一级机构
	 * @return
	 */
	public List<OrgVo> getSubOrgs(String orgCode);
	
	/**
	 * 根据机构唯一标识符,返回该机构下的所有人员
	 * @param orgCode
	 * @return
	 */
	public List<UserVo> getUserByOrg(String orgCode);
	
}
