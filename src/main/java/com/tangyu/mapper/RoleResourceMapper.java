package com.tangyu.mapper;

import com.tangyu.model.RoleResource;

public interface RoleResourceMapper {

	int deleteByPrimaryKey(Long id);

	int insert(RoleResource record);

	int insertSelective(RoleResource record);

	RoleResource selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(RoleResource record);

	int updateByPrimaryKey(RoleResource record);
}