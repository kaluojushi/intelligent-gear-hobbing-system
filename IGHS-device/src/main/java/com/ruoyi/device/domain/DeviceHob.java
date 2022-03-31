package com.ruoyi.device.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 滚刀库对象 device_hob
 * 
 * @author mzj
 * @date 2022-03-30
 */
public class DeviceHob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 滚刀名称 */
    @Excel(name = "滚刀名称")
    private String hobName;

    /** 滚刀头数 */
    @Excel(name = "滚刀头数")
    private String hobHeads;

    /** 滚刀模数(mm) */
    @Excel(name = "滚刀模数(mm)")
    private String hobModule;

    /** 滚刀压力角(°) */
    @Excel(name = "滚刀压力角(°)")
    private String hobPressureAngle;

    /** 滚刀螺旋升角(°) */
    @Excel(name = "滚刀螺旋升角(°)")
    private String hobSpiralAngle;

    /** 滚刀长度(mm) */
    @Excel(name = "滚刀长度(mm)")
    private String hobLength;

    /** 滚刀外径(mm) */
    @Excel(name = "滚刀外径(mm)")
    private String hobOuterDiameter;

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
    public void setHobName(String hobName) 
    {
        this.hobName = hobName;
    }

    public String getHobName() 
    {
        return hobName;
    }
    public void setHobHeads(String hobHeads) 
    {
        this.hobHeads = hobHeads;
    }

    public String getHobHeads() 
    {
        return hobHeads;
    }
    public void setHobModule(String hobModule) 
    {
        this.hobModule = hobModule;
    }

    public String getHobModule() 
    {
        return hobModule;
    }
    public void setHobPressureAngle(String hobPressureAngle) 
    {
        this.hobPressureAngle = hobPressureAngle;
    }

    public String getHobPressureAngle() 
    {
        return hobPressureAngle;
    }
    public void setHobSpiralAngle(String hobSpiralAngle) 
    {
        this.hobSpiralAngle = hobSpiralAngle;
    }

    public String getHobSpiralAngle() 
    {
        return hobSpiralAngle;
    }
    public void setHobLength(String hobLength) 
    {
        this.hobLength = hobLength;
    }

    public String getHobLength() 
    {
        return hobLength;
    }
    public void setHobOuterDiameter(String hobOuterDiameter) 
    {
        this.hobOuterDiameter = hobOuterDiameter;
    }

    public String getHobOuterDiameter() 
    {
        return hobOuterDiameter;
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
            .append("hobName", getHobName())
            .append("hobHeads", getHobHeads())
            .append("hobModule", getHobModule())
            .append("hobPressureAngle", getHobPressureAngle())
            .append("hobSpiralAngle", getHobSpiralAngle())
            .append("hobLength", getHobLength())
            .append("hobOuterDiameter", getHobOuterDiameter())
            .append("notes", getNotes())
            .toString();
    }
}
