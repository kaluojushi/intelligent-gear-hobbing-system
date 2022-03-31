package com.ruoyi.web.controller.device;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.device.domain.DeviceHob;
import com.ruoyi.device.service.IDeviceHobService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 滚刀库Controller
 * 
 * @author mzj
 * @date 2022-03-30
 */
@RestController
@RequestMapping("/device/hob")
public class DeviceHobController extends BaseController
{
    @Autowired
    private IDeviceHobService deviceHobService;

    /**
     * 查询滚刀库列表
     */
    @PreAuthorize("@ss.hasPermi('device:hob:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceHob deviceHob)
    {
        startPage();
        List<DeviceHob> list = deviceHobService.selectDeviceHobList(deviceHob);
        return getDataTable(list);
    }

    /**
     * 导出滚刀库列表
     */
    @PreAuthorize("@ss.hasPermi('device:hob:export')")
    @Log(title = "滚刀库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceHob deviceHob)
    {
        List<DeviceHob> list = deviceHobService.selectDeviceHobList(deviceHob);
        ExcelUtil<DeviceHob> util = new ExcelUtil<DeviceHob>(DeviceHob.class);
        util.exportExcel(response, list, "滚刀库数据");
    }

    /**
     * 获取滚刀库详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:hob:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deviceHobService.selectDeviceHobById(id));
    }

    /**
     * 新增滚刀库
     */
    @PreAuthorize("@ss.hasPermi('device:hob:add')")
    @Log(title = "滚刀库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceHob deviceHob)
    {
        return toAjax(deviceHobService.insertDeviceHob(deviceHob));
    }

    /**
     * 修改滚刀库
     */
    @PreAuthorize("@ss.hasPermi('device:hob:edit')")
    @Log(title = "滚刀库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceHob deviceHob)
    {
        return toAjax(deviceHobService.updateDeviceHob(deviceHob));
    }

    /**
     * 删除滚刀库
     */
    @PreAuthorize("@ss.hasPermi('device:hob:remove')")
    @Log(title = "滚刀库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceHobService.deleteDeviceHobByIds(ids));
    }
}
