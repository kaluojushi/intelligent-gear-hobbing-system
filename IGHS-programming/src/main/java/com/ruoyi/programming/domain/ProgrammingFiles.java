package com.ruoyi.programming.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件库对象 programming_files
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class ProgrammingFiles extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 文件编号 */
    @Excel(name = "文件编号")
    private String fileName;

    /** 加工信息 */
    @Excel(name = "加工信息")
    private String fileInfo;

    /** 加工代码 */
    @Excel(name = "加工代码")
    private String fileCode;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileInfo(String fileInfo) 
    {
        this.fileInfo = fileInfo;
    }

    public String getFileInfo() 
    {
        return fileInfo;
    }
    public void setFileCode(String fileCode) 
    {
        this.fileCode = fileCode;
    }

    public String getFileCode() 
    {
        return fileCode;
    }
    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("fileInfo", getFileInfo())
            .append("fileCode", getFileCode())
            .append("notes", getNotes())
            .toString();
    }
}
