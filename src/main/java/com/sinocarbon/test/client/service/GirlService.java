package com.sinocarbon.test.client.service;

import com.sinocarbon.test.client.pojo.Girl;
import java.util.List;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
public interface GirlService extends IService<Girl> {
	/**
	 * 根据女孩年纪查询女孩信息
	 */
    public List<Girl> findByAge(Integer age);
    
    /**
     * 插入女孩信息
     */
    public Girl insertGril(Girl girl);
    
    
    public List<Girl> listGirl();
    
    /**
     * 更新女孩信息
     */
    public int updateGirl(Girl girl);
    
    /**
     * 删除女孩信息
     */
    public int delete(Integer id);
}

