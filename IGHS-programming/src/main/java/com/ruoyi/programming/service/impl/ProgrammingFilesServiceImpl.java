package com.ruoyi.programming.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.programming.mapper.ProgrammingFilesMapper;
import com.ruoyi.programming.domain.ProgrammingFiles;
import com.ruoyi.programming.service.IProgrammingFilesService;

/**
 * 文件库Service业务层处理
 * 
 * @author mzj
 * @date 2022-04-20
 */
@Service
public class ProgrammingFilesServiceImpl implements IProgrammingFilesService 
{
    @Autowired
    private ProgrammingFilesMapper programmingFilesMapper;

    /**
     * 查询文件库
     * 
     * @param id 文件库主键
     * @return 文件库
     */
    @Override
    public ProgrammingFiles selectProgrammingFilesById(Long id)
    {
        return programmingFilesMapper.selectProgrammingFilesById(id);
    }

    /**
     * 查询文件库列表
     * 
     * @param programmingFiles 文件库
     * @return 文件库
     */
    @Override
    public List<ProgrammingFiles> selectProgrammingFilesList(ProgrammingFiles programmingFiles)
    {
        return programmingFilesMapper.selectProgrammingFilesList(programmingFiles);
    }

    /**
     * 新增文件库
     * 
     * @param programmingFiles 文件库
     * @return 结果
     */
    @Override
    public int insertProgrammingFiles(ProgrammingFiles programmingFiles)
    {
        return programmingFilesMapper.insertProgrammingFiles(programmingFiles);
    }

    /**
     * 修改文件库
     * 
     * @param programmingFiles 文件库
     * @return 结果
     */
    @Override
    public int updateProgrammingFiles(ProgrammingFiles programmingFiles)
    {
        return programmingFilesMapper.updateProgrammingFiles(programmingFiles);
    }

    /**
     * 批量删除文件库
     * 
     * @param ids 需要删除的文件库主键
     * @return 结果
     */
    @Override
    public int deleteProgrammingFilesByIds(Long[] ids)
    {
        return programmingFilesMapper.deleteProgrammingFilesByIds(ids);
    }

    /**
     * 删除文件库信息
     * 
     * @param id 文件库主键
     * @return 结果
     */
    @Override
    public int deleteProgrammingFilesById(Long id)
    {
        return programmingFilesMapper.deleteProgrammingFilesById(id);
    }
}
