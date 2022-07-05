package com.ruoyi.knowledge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.knowledge.mapper.KnowledgeStandardMapper;
import com.ruoyi.knowledge.domain.KnowledgeStandard;
import com.ruoyi.knowledge.service.IKnowledgeStandardService;

/**
 * 标准参数库Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class KnowledgeStandardServiceImpl implements IKnowledgeStandardService 
{
    @Autowired
    private KnowledgeStandardMapper knowledgeStandardMapper;

    /**
     * 查询标准参数库
     * 
     * @param id 标准参数库主键
     * @return 标准参数库
     */
    @Override
    public KnowledgeStandard selectKnowledgeStandardById(Long id)
    {
        return knowledgeStandardMapper.selectKnowledgeStandardById(id);
    }

    /**
     * 查询标准参数库列表
     * 
     * @param knowledgeStandard 标准参数库
     * @return 标准参数库
     */
    @Override
    public List<KnowledgeStandard> selectKnowledgeStandardList(KnowledgeStandard knowledgeStandard)
    {
        return knowledgeStandardMapper.selectKnowledgeStandardList(knowledgeStandard);
    }

    /**
     * 新增标准参数库
     * 
     * @param knowledgeStandard 标准参数库
     * @return 结果
     */
    @Override
    public int insertKnowledgeStandard(KnowledgeStandard knowledgeStandard)
    {
        return knowledgeStandardMapper.insertKnowledgeStandard(knowledgeStandard);
    }

    /**
     * 修改标准参数库
     * 
     * @param knowledgeStandard 标准参数库
     * @return 结果
     */
    @Override
    public int updateKnowledgeStandard(KnowledgeStandard knowledgeStandard)
    {
        return knowledgeStandardMapper.updateKnowledgeStandard(knowledgeStandard);
    }

    /**
     * 批量删除标准参数库
     * 
     * @param ids 需要删除的标准参数库主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeStandardByIds(Long[] ids)
    {
        return knowledgeStandardMapper.deleteKnowledgeStandardByIds(ids);
    }

    /**
     * 删除标准参数库信息
     * 
     * @param id 标准参数库主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeStandardById(Long id)
    {
        return knowledgeStandardMapper.deleteKnowledgeStandardById(id);
    }
}
