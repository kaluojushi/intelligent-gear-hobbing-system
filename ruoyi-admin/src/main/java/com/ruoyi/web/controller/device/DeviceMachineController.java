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
import com.ruoyi.device.domain.DeviceMachine;
import com.ruoyi.device.service.IDeviceMachineService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机床库Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/device/machine")
public class DeviceMachineController extends BaseController
{
    @Autowired
    private IDeviceMachineService deviceMachineService;

    /**
     * 查询机床库列表
     */
    @PreAuthorize("@ss.hasPermi('device:machine:list')")
    @GetMapping("/list")
    public TableDataInfo list(DeviceMachine deviceMachine)
    {
        startPage();
        List<DeviceMachine> list = deviceMachineService.selectDeviceMachineList(deviceMachine);
        return getDataTable(list);
    }

    /**
     * 导出机床库列表
     */
    @PreAuthorize("@ss.hasPermi('device:machine:export')")
    @Log(title = "机床库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceMachine deviceMachine)
    {
        List<DeviceMachine> list = deviceMachineService.selectDeviceMachineList(deviceMachine);
        ExcelUtil<DeviceMachine> util = new ExcelUtil<DeviceMachine>(DeviceMachine.class);
        util.exportExcel(response, list, "机床库数据");
    }

    /**
     * 获取机床库详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:machine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(deviceMachineService.selectDeviceMachineById(id));
    }

    /**
     * 新增机床库
     */
    @PreAuthorize("@ss.hasPermi('device:machine:add')")
    @Log(title = "机床库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceMachine deviceMachine)
    {
        return toAjax(deviceMachineService.insertDeviceMachine(deviceMachine));
    }

    /**
     * 修改机床库
     */
    @PreAuthorize("@ss.hasPermi('device:machine:edit')")
    @Log(title = "机床库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceMachine deviceMachine)
    {
        return toAjax(deviceMachineService.updateDeviceMachine(deviceMachine));
    }

    /**
     * 删除机床库
     */
    @PreAuthorize("@ss.hasPermi('device:machine:remove')")
    @Log(title = "机床库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceMachineService.deleteDeviceMachineByIds(ids));
    }
}
