package com.ruoyi.programming.service;

import java.util.List;
import com.ruoyi.programming.domain.ProgrammingFiles;

/**
 * 文件库Service接口
 * 
 * @author mzj
 * @date 2022-04-20
 */
public interface IProgrammingFilesService 
{
    /**
     * 查询文件库
     * 
     * @param id 文件库主键
     * @return 文件库
     */
    public ProgrammingFiles selectProgrammingFilesById(Long id);

    /**
     * 查询文件库列表
     * 
     * @param programmingFiles 文件库
     * @return 文件库集合
     */
    public List<ProgrammingFiles> selectProgrammingFilesList(ProgrammingFiles programmingFiles);

    /**
     * 新增文件库
     * 
     * @param programmingFiles 文件库
     * @return 结果
     */
    public int insertProgrammingFiles(ProgrammingFiles programmingFiles);

    /**
     * 修改文件库
     * 
     * @param programmingFiles 文件库
     * @return 结果
     */
    public int updateProgrammingFiles(ProgrammingFiles programmingFiles);

    /**
     * 批量删除文件库
     * 
     * @param ids 需要删除的文件库主键集合
     * @return 结果
     */
    public int deleteProgrammingFilesByIds(Long[] ids);

    /**
     * 删除文件库信息
     * 
     * @param id 文件库主键
     * @return 结果
     */
    public int deleteProgrammingFilesById(Long id);
}
