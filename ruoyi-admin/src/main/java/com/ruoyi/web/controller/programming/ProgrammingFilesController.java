package com.ruoyi.web.controller.programming;

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
import com.ruoyi.programming.domain.ProgrammingFiles;
import com.ruoyi.programming.service.IProgrammingFilesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件库Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/programming/files")
public class ProgrammingFilesController extends BaseController
{
    @Autowired
    private IProgrammingFilesService programmingFilesService;

    /**
     * 查询文件库列表
     */
    @PreAuthorize("@ss.hasPermi('programming:files:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProgrammingFiles programmingFiles)
    {
        startPage();
        List<ProgrammingFiles> list = programmingFilesService.selectProgrammingFilesList(programmingFiles);
        return getDataTable(list);
    }

    /**
     * 导出文件库列表
     */
    @PreAuthorize("@ss.hasPermi('programming:files:export')")
    @Log(title = "文件库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProgrammingFiles programmingFiles)
    {
        List<ProgrammingFiles> list = programmingFilesService.selectProgrammingFilesList(programmingFiles);
        ExcelUtil<ProgrammingFiles> util = new ExcelUtil<ProgrammingFiles>(ProgrammingFiles.class);
        util.exportExcel(response, list, "文件库数据");
    }

    /**
     * 获取文件库详细信息
     */
    @PreAuthorize("@ss.hasPermi('programming:files:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(programmingFilesService.selectProgrammingFilesById(id));
    }

    /**
     * 新增文件库
     */
    @PreAuthorize("@ss.hasPermi('programming:files:add')")
    @Log(title = "文件库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProgrammingFiles programmingFiles)
    {
        return toAjax(programmingFilesService.insertProgrammingFiles(programmingFiles));
    }

    /**
     * 修改文件库
     */
    @PreAuthorize("@ss.hasPermi('programming:files:edit')")
    @Log(title = "文件库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProgrammingFiles programmingFiles)
    {
        return toAjax(programmingFilesService.updateProgrammingFiles(programmingFiles));
    }

    /**
     * 删除文件库
     */
    @PreAuthorize("@ss.hasPermi('programming:files:remove')")
    @Log(title = "文件库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(programmingFilesService.deleteProgrammingFilesByIds(ids));
    }
}
