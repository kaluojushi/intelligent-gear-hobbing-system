package com.ruoyi.web.controller.knowledge;

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
import com.ruoyi.knowledge.domain.KnowledgeCases;
import com.ruoyi.knowledge.service.IKnowledgeCasesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 加工实例库Controller
 * 
 * @author mzj
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/knowledge/cases")
public class KnowledgeCasesController extends BaseController
{
    @Autowired
    private IKnowledgeCasesService knowledgeCasesService;

    /**
     * 查询加工实例库列表
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:list')")
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeCases knowledgeCases)
    {
        startPage();
        List<KnowledgeCases> list = knowledgeCasesService.selectKnowledgeCasesList(knowledgeCases);
        return getDataTable(list);
    }

    /**
     * 导出加工实例库列表
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:export')")
    @Log(title = "加工实例库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KnowledgeCases knowledgeCases)
    {
        List<KnowledgeCases> list = knowledgeCasesService.selectKnowledgeCasesList(knowledgeCases);
        ExcelUtil<KnowledgeCases> util = new ExcelUtil<KnowledgeCases>(KnowledgeCases.class);
        util.exportExcel(response, list, "加工实例库数据");
    }

    /**
     * 获取加工实例库详细信息
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(knowledgeCasesService.selectKnowledgeCasesById(id));
    }

    /**
     * 新增加工实例库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:add')")
    @Log(title = "加工实例库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KnowledgeCases knowledgeCases)
    {
        return toAjax(knowledgeCasesService.insertKnowledgeCases(knowledgeCases));
    }

    /**
     * 修改加工实例库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:edit')")
    @Log(title = "加工实例库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KnowledgeCases knowledgeCases)
    {
        return toAjax(knowledgeCasesService.updateKnowledgeCases(knowledgeCases));
    }

    /**
     * 删除加工实例库
     */
    @PreAuthorize("@ss.hasPermi('knowledge:cases:remove')")
    @Log(title = "加工实例库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(knowledgeCasesService.deleteKnowledgeCasesByIds(ids));
    }
}
