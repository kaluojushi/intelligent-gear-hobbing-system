<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="参数名称" prop="standardName">
        <el-input
          v-model="queryParams.standardName"
          placeholder="请输入参数名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数变量" prop="standardParam">
        <el-input
          v-model="queryParams.standardParam"
          placeholder="请输入参数变量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数单位" prop="standardUnit">
        <el-input
          v-model="queryParams.standardUnit"
          placeholder="请输入参数单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['knowledge:standard:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['knowledge:standard:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['knowledge:standard:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['knowledge:standard:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="standardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键id" align="center" prop="id" />-->
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-descriptions :column="1" style="margin-left: 20px">
            <el-descriptions-item label="标准值">{{ arrayFormFormat(props.row.standardValue) }}</el-descriptions-item>
            <el-descriptions-item label="推荐值">{{ arrayFormFormat(props.row.standardRecommend) }}</el-descriptions-item>
            <el-descriptions-item label="其他值">{{ arrayFormFormat(props.row.standardOther) }}</el-descriptions-item>
          </el-descriptions>
        </template>
      </el-table-column>
      <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="参数名称" align="center" prop="standardName" />
      <el-table-column label="参数变量" align="center" prop="standardParam" />
      <el-table-column label="参数单位" align="center" prop="standardUnit" />
      <el-table-column label="标准值" align="center" prop="standardValue" :formatter="arrayFormat" />
      <el-table-column label="推荐值" align="center" prop="standardRecommend" :formatter="arrayFormat" />
      <el-table-column label="其他值" align="center" prop="standardOther" :formatter="arrayFormat" />
      <el-table-column label="备注" align="center" prop="notes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['knowledge:standard:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['knowledge:standard:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改标准参数库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数名称" prop="standardName">
          <el-input v-model="form.standardName" placeholder="请输入参数名称" />
        </el-form-item>
        <el-form-item label="参数变量" prop="standardParam">
          <el-input v-model="form.standardParam" placeholder="请输入参数变量" />
        </el-form-item>
        <el-form-item label="参数单位" prop="standardUnit">
          <el-input v-model="form.standardUnit" placeholder="请输入参数单位" />
        </el-form-item>
        <el-form-item label="标准值" prop="standardValue">
          {{ arrayFormFormat(form.standardValue) }}<br>
        </el-form-item>
        <el-form-item label="推荐值" prop="standardRecommend">
          {{ arrayFormFormat(form.standardRecommend) }}<br>
        </el-form-item>
        <el-form-item label="其他值" prop="standardOther">
          {{ arrayFormFormat(form.standardOther) }}<br>
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleEdit">编辑</el-button>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="form.notes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 编辑标准值对话框 -->
    <el-dialog :title="editTitle" :visible.sync="editOpen" width="500px" append-to-body>
      <div class="edit-notes">
        请以英文半角逗号分隔不同值，并不要加额外的符号和空格，如“1,2,3”。
      </div>
      <el-form ref="editForm" :model="editForm" :rules="editRules" label-width="60px">
        <el-form-item label="标准值" prop="standardValue">
          <el-input v-model="editForm.standardValue" type="textarea" placeholder="请输入标准值" />
        </el-form-item>
        <el-form-item label="推荐值" prop="standardRecommend">
          <el-input v-model="editForm.standardRecommend" type="textarea" placeholder="请输入推荐值" />
        </el-form-item>
        <el-form-item label="其他值" prop="standardOther">
          <el-input v-model="editForm.standardOther" type="textarea" placeholder="请输入其他值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitEditForm">确 定</el-button>
        <el-button @click="editCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStandard, getStandard, delStandard, addStandard, updateStandard } from "@/api/knowledge/standard";

export default {
  name: "Standard",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 标准参数库表格数据
      standardList: [],
      // 弹出层标题
      title: "",
      editTitle: "",
      // 是否显示弹出层
      open: false,
      editOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        standardName: null,
        standardParam: null,
        standardUnit: null,
        standardValue: null,
        standardRecommend: null,
        standardOther: null,
        notes: null
      },
      // 表单参数
      form: {},
      editForm: {},
      // 表单校验
      rules: {
      },
      editRules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    indexMethod(index) {
      return index + 1;
    },
    /** 查询标准参数库列表 */
    getList() {
      this.loading = true;
      listStandard(this.queryParams).then(response => {
        this.standardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表格数组格式化
    arrayFormat(row, column) {
      const property = column.property, array = JSON.parse(row[property]);
      if (array.length === 0) {
        return "无";
      } else {
        const str = [array[0]];
        for (let i = 1; i < 5 && i < array.length; i++) {
          str.push("、");
          str.push(array[i]);
        }
        if (array.length > 5) {
          str.push("等");
        }
        return str.join("");
      }
    },
    // 表单数组格式化
    arrayFormFormat(array) {
      if (array) {
        array = JSON.parse(array);
        if (array.length === 0) {
          return "无";
        } else {
          const str = [array[0]];
          for (let i = 1; i < array.length; i++) {
            str.push("、");
            str.push(array[i]);
          }
          return str.join("");
        }
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    editCancel() {
      this.editOpen = false;
      this.editReset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        standardName: null,
        standardParam: null,
        standardUnit: null,
        standardValue: null,
        standardRecommend: null,
        standardOther: null,
        notes: null
      };
      this.resetForm("form");
    },
    editReset() {
      this.editForm = {
        standardValue: null,
        standardRecommend: null,
        standardOther: null
      };
      this.resetForm("editForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加标准参数库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStandard(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改标准参数";
      });
    },
    /** 编辑信息操作 */
    handleEdit() {
      this.editForm = {
        standardValue: this.form.standardValue.slice(1, -1),
        standardRecommend: this.form.standardRecommend.slice(1, -1),
        standardOther: this.form.standardOther.slice(1, -1)
      }
      this.editOpen = true;
      this.editTitle = `修改${this.form.standardName}参数值`;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStandard(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStandard(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitEditForm() {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.form.standardValue = '[' + this.editForm.standardValue + ']';
          this.form.standardRecommend = '[' + this.editForm.standardRecommend + ']';
          this.form.standardOther = '[' + this.editForm.standardOther + ']';
          this.editOpen = false;
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除标准参数库编号为"' + ids + '"的数据项？').then(function() {
        return delStandard(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('knowledge/standard/export', {
        ...this.queryParams
      }, `standard_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
.edit-notes {
  margin-bottom: 20px;
  font-size: 14px;
}
</style>
