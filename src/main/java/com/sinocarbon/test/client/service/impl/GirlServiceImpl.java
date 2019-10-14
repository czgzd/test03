package com.sinocarbon.test.client.service.impl;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.dao.GirlMapper;
import com.sinocarbon.test.client.service.GirlService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */

@Service
public class GirlServiceImpl extends ServiceImpl<GirlMapper, Girl> implements GirlService 
{
    @Autowired
    private GirlMapper girlMapper;
	 
	@Override
	public List<Girl> findByAge(Integer age) {
		return girlMapper.findGirlByAge(age) ;
	}

	@Override
	public Girl insertGril(Girl girl) {
		baseMapper.insert(girl);
		return girl;
	}

	@Override
	public List<Girl> listGirl() {
		return girlMapper.listGirls();
	}

	@Override
	public int delete(Integer id) {
		return girlMapper.deleteGirl(id);
	}

	@Override
	public int updateGirl(Girl girl) {
		return girlMapper.updateGirl(girl);
	}
	
}

