package com.ruoyi.algorithm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.algorithm.mapper.AlgorithmCncMapper;
import com.ruoyi.algorithm.domain.AlgorithmCnc;
import com.ruoyi.algorithm.service.IAlgorithmCncService;

/**
 * 数控系统模块Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class AlgorithmCncServiceImpl implements IAlgorithmCncService 
{
    @Autowired
    private AlgorithmCncMapper algorithmCncMapper;

    /**
     * 查询数控系统模块
     * 
     * @param id 数控系统模块主键
     * @return 数控系统模块
     */
    @Override
    public AlgorithmCnc selectAlgorithmCncById(Long id)
    {
        return algorithmCncMapper.selectAlgorithmCncById(id);
    }

    /**
     * 查询数控系统模块列表
     * 
     * @param algorithmCnc 数控系统模块
     * @return 数控系统模块
     */
    @Override
    public List<AlgorithmCnc> selectAlgorithmCncList(AlgorithmCnc algorithmCnc)
    {
        return algorithmCncMapper.selectAlgorithmCncList(algorithmCnc);
    }

    /**
     * 新增数控系统模块
     * 
     * @param algorithmCnc 数控系统模块
     * @return 结果
     */
    @Override
    public int insertAlgorithmCnc(AlgorithmCnc algorithmCnc)
    {
        return algorithmCncMapper.insertAlgorithmCnc(algorithmCnc);
    }

    /**
     * 修改数控系统模块
     * 
     * @param algorithmCnc 数控系统模块
     * @return 结果
     */
    @Override
    public int updateAlgorithmCnc(AlgorithmCnc algorithmCnc)
    {
        return algorithmCncMapper.updateAlgorithmCnc(algorithmCnc);
    }

    /**
     * 批量删除数控系统模块
     * 
     * @param ids 需要删除的数控系统模块主键
     * @return 结果
     */
    @Override
    public int deleteAlgorithmCncByIds(Long[] ids)
    {
        return algorithmCncMapper.deleteAlgorithmCncByIds(ids);
    }

    /**
     * 删除数控系统模块信息
     * 
     * @param id 数控系统模块主键
     * @return 结果
     */
    @Override
    public int deleteAlgorithmCncById(Long id)
    {
        return algorithmCncMapper.deleteAlgorithmCncById(id);
    }
}
