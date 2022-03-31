package com.ruoyi.device.mapper;

import java.util.List;
import com.ruoyi.device.domain.DeviceHob;

/**
 * 滚刀库Mapper接口
 * 
 * @author mzj
 * @date 2022-03-30
 */
public interface DeviceHobMapper 
{
    /**
     * 查询滚刀库
     * 
     * @param id 滚刀库主键
     * @return 滚刀库
     */
    public DeviceHob selectDeviceHobById(Long id);

    /**
     * 查询滚刀库列表
     * 
     * @param deviceHob 滚刀库
     * @return 滚刀库集合
     */
    public List<DeviceHob> selectDeviceHobList(DeviceHob deviceHob);

    /**
     * 新增滚刀库
     * 
     * @param deviceHob 滚刀库
     * @return 结果
     */
    public int insertDeviceHob(DeviceHob deviceHob);

    /**
     * 修改滚刀库
     * 
     * @param deviceHob 滚刀库
     * @return 结果
     */
    public int updateDeviceHob(DeviceHob deviceHob);

    /**
     * 删除滚刀库
     * 
     * @param id 滚刀库主键
     * @return 结果
     */
    public int deleteDeviceHobById(Long id);

    /**
     * 批量删除滚刀库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceHobByIds(Long[] ids);
}
