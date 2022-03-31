package com.ruoyi.device.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.device.mapper.DeviceHobMapper;
import com.ruoyi.device.domain.DeviceHob;
import com.ruoyi.device.service.IDeviceHobService;

/**
 * 滚刀库Service业务层处理
 * 
 * @author mzj
 * @date 2022-03-30
 */
@Service
public class DeviceHobServiceImpl implements IDeviceHobService 
{
    @Autowired
    private DeviceHobMapper deviceHobMapper;

    /**
     * 查询滚刀库
     * 
     * @param id 滚刀库主键
     * @return 滚刀库
     */
    @Override
    public DeviceHob selectDeviceHobById(Long id)
    {
        return deviceHobMapper.selectDeviceHobById(id);
    }

    /**
     * 查询滚刀库列表
     * 
     * @param deviceHob 滚刀库
     * @return 滚刀库
     */
    @Override
    public List<DeviceHob> selectDeviceHobList(DeviceHob deviceHob)
    {
        return deviceHobMapper.selectDeviceHobList(deviceHob);
    }

    /**
     * 新增滚刀库
     * 
     * @param deviceHob 滚刀库
     * @return 结果
     */
    @Override
    public int insertDeviceHob(DeviceHob deviceHob)
    {
        return deviceHobMapper.insertDeviceHob(deviceHob);
    }

    /**
     * 修改滚刀库
     * 
     * @param deviceHob 滚刀库
     * @return 结果
     */
    @Override
    public int updateDeviceHob(DeviceHob deviceHob)
    {
        return deviceHobMapper.updateDeviceHob(deviceHob);
    }

    /**
     * 批量删除滚刀库
     * 
     * @param ids 需要删除的滚刀库主键
     * @return 结果
     */
    @Override
    public int deleteDeviceHobByIds(Long[] ids)
    {
        return deviceHobMapper.deleteDeviceHobByIds(ids);
    }

    /**
     * 删除滚刀库信息
     * 
     * @param id 滚刀库主键
     * @return 结果
     */
    @Override
    public int deleteDeviceHobById(Long id)
    {
        return deviceHobMapper.deleteDeviceHobById(id);
    }
}
