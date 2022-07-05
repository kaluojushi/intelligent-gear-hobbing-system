package com.ruoyi.knowledge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.knowledge.mapper.KnowledgeCasesMapper;
import com.ruoyi.knowledge.domain.KnowledgeCases;
import com.ruoyi.knowledge.service.IKnowledgeCasesService;

/**
 * 加工实例库Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class KnowledgeCasesServiceImpl implements IKnowledgeCasesService 
{
    @Autowired
    private KnowledgeCasesMapper knowledgeCasesMapper;

    /**
     * 查询加工实例库
     * 
     * @param id 加工实例库主键
     * @return 加工实例库
     */
    @Override
    public KnowledgeCases selectKnowledgeCasesById(Long id)
    {
        return knowledgeCasesMapper.selectKnowledgeCasesById(id);
    }

    /**
     * 查询加工实例库列表
     * 
     * @param knowledgeCases 加工实例库
     * @return 加工实例库
     */
    @Override
    public List<KnowledgeCases> selectKnowledgeCasesList(KnowledgeCases knowledgeCases)
    {
        return knowledgeCasesMapper.selectKnowledgeCasesList(knowledgeCases);
    }

    /**
     * 新增加工实例库
     * 
     * @param knowledgeCases 加工实例库
     * @return 结果
     */
    @Override
    public int insertKnowledgeCases(KnowledgeCases knowledgeCases)
    {
        return knowledgeCasesMapper.insertKnowledgeCases(knowledgeCases);
    }

    /**
     * 修改加工实例库
     * 
     * @param knowledgeCases 加工实例库
     * @return 结果
     */
    @Override
    public int updateKnowledgeCases(KnowledgeCases knowledgeCases)
    {
        return knowledgeCasesMapper.updateKnowledgeCases(knowledgeCases);
    }

    /**
     * 批量删除加工实例库
     * 
     * @param ids 需要删除的加工实例库主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeCasesByIds(Long[] ids)
    {
        return knowledgeCasesMapper.deleteKnowledgeCasesByIds(ids);
    }

    /**
     * 删除加工实例库信息
     * 
     * @param id 加工实例库主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeCasesById(Long id)
    {
        return knowledgeCasesMapper.deleteKnowledgeCasesById(id);
    }
}
