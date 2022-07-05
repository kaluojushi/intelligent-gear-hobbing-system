package com.ruoyi.algorithm.service;

import java.util.List;
import com.ruoyi.algorithm.domain.AlgorithmCnc;

/**
 * 数控系统模块Service接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface IAlgorithmCncService 
{
    /**
     * 查询数控系统模块
     * 
     * @param id 数控系统模块主键
     * @return 数控系统模块
     */
    public AlgorithmCnc selectAlgorithmCncById(Long id);

    /**
     * 查询数控系统模块列表
     * 
     * @param algorithmCnc 数控系统模块
     * @return 数控系统模块集合
     */
    public List<AlgorithmCnc> selectAlgorithmCncList(AlgorithmCnc algorithmCnc);

    /**
     * 新增数控系统模块
     * 
     * @param algorithmCnc 数控系统模块
     * @return 结果
     */
    public int insertAlgorithmCnc(AlgorithmCnc algorithmCnc);

    /**
     * 修改数控系统模块
     * 
     * @param algorithmCnc 数控系统模块
     * @return 结果
     */
    public int updateAlgorithmCnc(AlgorithmCnc algorithmCnc);

    /**
     * 批量删除数控系统模块
     * 
     * @param ids 需要删除的数控系统模块主键集合
     * @return 结果
     */
    public int deleteAlgorithmCncByIds(Long[] ids);

    /**
     * 删除数控系统模块信息
     * 
     * @param id 数控系统模块主键
     * @return 结果
     */
    public int deleteAlgorithmCncById(Long id);
}
