package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 加工路径模块对象 algorithm_path
 * 
 * @author mzj
 * @date 2022-04-20
 */
public class AlgorithmPath extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 路径名称 */
    @Excel(name = "路径名称")
    private String pathName;

    /** 路径参数 */
    @Excel(name = "路径参数")
    private String pathParam;

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
    public void setPathName(String pathName) 
    {
        this.pathName = pathName;
    }

    public String getPathName() 
    {
        return pathName;
    }
    public void setPathParam(String pathParam) 
    {
        this.pathParam = pathParam;
    }

    public String getPathParam() 
    {
        return pathParam;
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
            .append("pathName", getPathName())
            .append("pathParam", getPathParam())
            .append("notes", getNotes())
            .toString();
    }
}
