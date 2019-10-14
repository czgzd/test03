package com.sinocarbon.test.client.dao;

import com.sinocarbon.test.client.pojo.Girl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */

public interface GirlMapper extends BaseMapper<Girl> {
	 /**
	    * 单表查询女孩信息
	    */
		List<Girl> findGirlByAge(Integer age);
		
		public List<Girl> listGirls();
		
		/**
		 * 单表插入女孩信息
		 */
		public int insertGirl(Girl girl);
		
		/**
		 * 通过女孩的id删除女孩信息
		 */
		public int deleteGirl(Integer id);
		
		/**
		 * 更新女孩的信息
		 */
		public int updateGirl(Girl girl);
	}

