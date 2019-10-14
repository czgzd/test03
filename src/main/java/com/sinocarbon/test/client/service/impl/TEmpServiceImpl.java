package com.sinocarbon.test.client.service.impl;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.pojo.TEmp;
import com.sinocarbon.test.client.dao.GirlMapper;
import com.sinocarbon.test.client.dao.TEmpMapper;
import com.sinocarbon.test.client.service.TEmpService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

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
public class TEmpServiceImpl extends ServiceImpl<TEmpMapper, TEmp> implements TEmpService {

	 @Autowired
	 private TEmpMapper empMapper;
	 
	@Override
	public TEmp selectById(Integer id) {
		return  empMapper.selectById(id);
	}

}
