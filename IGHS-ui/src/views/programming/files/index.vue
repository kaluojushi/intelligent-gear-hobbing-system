<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件编号" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件编号"
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
      <el-col :span="1.5">
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['programming:files:add']"-->
<!--        >新增</el-button>-->
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-zoom-in"
          size="mini"
          :disabled="single"
          @click="handleCheck"
          v-hasPermi="['programming:files:check']"
        >查看</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['programming:files:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['programming:files:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="filesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="主键id" align="center" prop="id" />-->
      <el-table-column label="序号" align="center" type="index" :index="indexMethod"/>
      <el-table-column label="文件编号" align="center" prop="fileName" />
      <el-table-column label="加工信息" align="center" prop="fileInfo">
        <template slot-scope="scope">
          <span style="margin-right: 5px">
             {{ gearTypes[scope.row.fileInfo.gearType] }} / {{ scope.row.fileInfo.gearTeeth }}齿 / {{ scope.row.fileInfo.gearModulus }}mm
          </span>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleInfo(scope.row)"
          >更多
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="加工代码" align="center" prop="fileCode">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-zoom-in"
            @click="handleCode(scope.row)"
          >点击查看
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="notes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-zoom-in"
            @click="handleCheck(scope.row)"
            v-hasPermi="['programming:files:check']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['programming:files:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-add"
            @click="handleApply(scope.row)"
            v-hasPermi="['programming:files:apply']"
          >加入实例库</el-button>
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

    <!-- 添加或修改文件库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件编号" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件编号" />
        </el-form-item>
        <el-form-item label="加工信息" prop="fileInfo">
          <span>{{ gearTypes[form.fileInfo ? form.fileInfo.gearType : null] }} / {{ form.fileInfo ? form.fileInfo.gearTeeth : "" }}齿 / {{ form.fileInfo ? form.fileInfo.gearModulus : "" }}mm</span><br>
          <el-button type="text" icon="el-icon-more" @click="handleInfo">更多信息</el-button>
        </el-form-item>
        <el-form-item label="加工代码" prop="fileCode">
          <el-button type="text" icon="el-icon-zoom-in" @click="handleCode">查看代码</el-button>
          <el-button type="text" icon="el-icon-download" @click="handleCodeDownload">下载代码</el-button>
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

    <!-- 查看信息对话框 -->
    <el-dialog :title="infoTitle" :visible.sync="infoOpen" width="1000px" append-to-body>
      <div v-if="form.fileInfo">
        <el-descriptions title="齿轮数据与修形数据" direction="vertical" :column="8" border>
          <el-descriptions-item label="齿轮类型" :span="1" :label-style="textAlign" :content-style="textAlign">{{ gearTypes[form.fileInfo.gearType] }}</el-descriptions-item>
          <el-descriptions-item label="齿轮齿数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearTeeth }}齿</el-descriptions-item>
          <el-descriptions-item label="齿轮模数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearModulus }}mm</el-descriptions-item>
          <el-descriptions-item label="压力角" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearPressureAngle }}°</el-descriptions-item>
          <el-descriptions-item label="齿顶高系数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearAddendumCoefficient }}</el-descriptions-item>
          <el-descriptions-item label="顶隙系数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearClearanceCoefficient }}</el-descriptions-item>
          <el-descriptions-item label="齿宽" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearBreadth }}mm</el-descriptions-item>
          <el-descriptions-item label="外径" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearOuterDiameter }}mm</el-descriptions-item>
          <el-descriptions-item label="螺旋角" :span="4" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearHelixAngle }}°</el-descriptions-item>
          <el-descriptions-item label="旋向" :span="4" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.gearHelixDirection }}</el-descriptions-item>
        </el-descriptions>
        <el-descriptions title="设备数据" direction="vertical" :column="8" border style="margin-top: 20px">
          <el-descriptions-item label="机床型号" :span="2" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.machineName }}</el-descriptions-item>
          <el-descriptions-item label="机床描述" :span="2" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.machineDescribe }}</el-descriptions-item>
          <el-descriptions-item label="最大工件外径" :span="1" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.maxWorkpieceDiameter }}mm</el-descriptions-item>
          <el-descriptions-item label="最大工件模数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.maxWorkpieceModulus }}mm</el-descriptions-item>
          <el-descriptions-item label="数控系统名称" :span="2" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.cncName }}</el-descriptions-item>
          <el-descriptions-item label="滚刀名称" :span="2" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobName }}</el-descriptions-item>
          <el-descriptions-item label="滚刀头数" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobHeads }}</el-descriptions-item>
          <el-descriptions-item label="滚刀模数" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobModulus }}mm</el-descriptions-item>
          <el-descriptions-item label="滚刀压力角" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobPressureAngle }}°</el-descriptions-item>
          <el-descriptions-item label="滚刀螺旋升角" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobSpiralAngle }}°</el-descriptions-item>
          <el-descriptions-item label="滚刀长度" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobLength }}mm</el-descriptions-item>
          <el-descriptions-item label="滚刀外径" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.hobOuterDiameter }}mm</el-descriptions-item>
        </el-descriptions>
        <el-descriptions title="加工数据" direction="vertical" :column="9" border style="margin-top: 20px">
          <el-descriptions-item label="安装角" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.mountingAngle }}°</el-descriptions-item>
          <el-descriptions-item label="滚刀初始坐标" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hobInitialPoint }}</el-descriptions-item>
          <el-descriptions-item label="安全距离" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.safeDistance }}</el-descriptions-item>
          <el-descriptions-item label="切削循环类型" :span="1" :label-style="textAlign" :content-style="textAlign">{{ currentContextInfo.pathName }}</el-descriptions-item>
          <el-descriptions-item label="切削次数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.cuttingFrequency }}</el-descriptions-item>
          <el-descriptions-item label="吃刀深度" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.cuttingDepth }}mm</el-descriptions-item>
          <el-descriptions-item label="进给量" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.feedRate }}</el-descriptions-item>
          <el-descriptions-item label="滚刀转速" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hobRotationSpeed }}</el-descriptions-item>
          <el-descriptions-item label="滚刀旋转方向" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hobRotationDirection }}</el-descriptions-item>
          <el-descriptions-item label="轴向进给方向" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.axialFeedDirection }}</el-descriptions-item>
          <el-descriptions-item label="径向快速进给速度" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.radialRapidFeedSpeed }}</el-descriptions-item>
          <el-descriptions-item label="移动后暂停时间" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.pauseTimeAfterMove }}</el-descriptions-item>
          <el-descriptions-item label="切削后窜刀" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.isFleeCutter ? "是" : "否" }}</el-descriptions-item>
          <el-descriptions-item label="窜刀量" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.fleeCutterAmounts }}</el-descriptions-item>
          <el-descriptions-item label="窜刀齿轮数" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.fleeCutterGears }}</el-descriptions-item>
          <el-descriptions-item label="是否外支架" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hasOuterSupport ? "是" : "否" }}</el-descriptions-item>
          <el-descriptions-item label="是否夹具" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hasFixture ? "是" : "否" }}</el-descriptions-item>
          <el-descriptions-item label="是否冷却液" :span="1" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.hasCoolant ? "是" : "否" }}</el-descriptions-item>
          <el-descriptions-item label="是否同步取消" :span="2" :label-style="textAlign" :content-style="textAlign">{{ form.fileInfo.isSyncCanceled ? "是" : "否" }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="infoCancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看代码对话框 -->
    <el-dialog :title="codeTitle" :visible.sync="codeOpen" width="800px" append-to-body>
      <div class="code-content">{{ codeContentFormat(form.fileCode) }}</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" icon="el-icon-download" @click="handleCodeDownload">下载代码</el-button>
        <el-button @click="codeCancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 下载代码对话框 -->
    <el-dialog :title="codeDownloadTitle" :visible.sync="codeDownloadOpen" width="400px" append-to-body>
      <el-form ref="codeDownloadForm" :model="codeDownloadForm" :rules="codeDownloadRules" label-width="80px">
        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="codeDownloadForm.fileName" placeholder="请输入文件名" />
        </el-form-item>
        <el-form-item label="文件后缀" prop="fileSuffix">
          <el-input v-model="codeDownloadForm.fileSuffix" placeholder="请输入文件后缀" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" icon="el-icon-download" @click="handleCodeDownloadToLocal">下 载</el-button>
        <el-button @click="codeDownloadCancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 加入实例库对话框 -->
    <el-dialog :title="applyTitle" :visible.sync="applyOpen" width="500px" append-to-body>
      <el-form ref="applyForm" :model="applyForm" :rules="applyRules" label-width="80px">
        <el-form-item label="实例编号" prop="caseName">
          <el-input v-model="applyForm.caseName" placeholder="请输入实例编号" />
          <el-button
            size="mini"
            type="text"
            @click="useFileName"
          >使用文件编号</el-button>
        </el-form-item>
        <el-form-item label="加工时间" prop="caseProcessTime">
          <el-date-picker
            clearable
            v-model="applyForm.caseProcessTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择加工时间"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="applyForm.notes" type="textarea" placeholder="请输入内容" />
          <el-button
            size="mini"
            type="text"
            @click="useFileNotes"
          >使用文件备注</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitApplyForm">确 定</el-button>
        <el-button @click="applyCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addFiles, delFiles, getFiles, listFiles, updateFiles} from "@/api/programming/files";
import {addCases} from "@/api/knowledge/cases";
import {parseTime} from "@/utils/ruoyi";
import {listMachine} from "@/api/device/machine";
import {listHob} from "@/api/device/hob";
import {listPath} from "@/api/algorithm/path";
import {listCnc} from "@/api/algorithm/cnc";

export default {
  name: "Files",
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
      // 文件库表格数据
      filesList: [],
      // 其他表格数据
      machineList: [],
      hobList: [],
      pathList: [],
      cncList: [],
      // 弹出层标题
      title: "",
      infoTitle: "",
      codeTitle: "",
      codeDownloadTitle: "",
      applyTitle: "",
      // 是否显示弹出层
      open: false,
      infoOpen: false,
      codeOpen: false,
      codeDownloadOpen: false,
      applyOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fileName: null,
        fileInfo: null,
        fileCode: null,
        notes: null
      },
      // 表单参数
      form: {},
      codeDownloadForm: {},
      applyForm: {},
      currentContextInfo: {},
      // 表单校验
      rules: {
      },
      codeDownloadRules: {},
      applyRules: {},
      // 齿轮类型表
      gearTypes: {
        straight: "直齿圆柱齿轮",
        oblique: "斜齿圆柱齿轮",
        bevel: "小锥度齿轮",
        drum: "鼓形齿轮",
        spline: "花键",
        worm: "蜗轮"
      },
      // 居中对象
      textAlign: {"text-align": "center"}
    };
  },
  created() {
    this.getList();
    this.getContextList();
  },
  methods: {
    indexMethod(index) {
      return index + 1;
    },
    /** 查询文件库列表 */
    getList() {
      this.loading = true;
      listFiles(this.queryParams).then(response => {
        this.filesList = response.rows;
        this.filesList.forEach(file => file.fileInfo = JSON.parse(file.fileInfo));
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 机床、滚刀、路径、数控列表 */
    getContextList() {
      listMachine().then(response => {
        this.machineList = response.rows;
      });
      listHob().then(response => {
        this.hobList = response.rows;
      });
      listPath().then(response => {
        this.pathList = response.rows;
      });
      listCnc().then(response => {
        this.cncList = response.rows;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    infoCancel() {
      this.infoOpen = false;
    },
    codeCancel() {
      this.codeOpen = false;
    },
    codeDownloadCancel() {
      this.codeDownloadOpen = false;
      this.codeDownloadReset();
    },
    applyCancel() {
      this.applyOpen = false;
      this.reset();
      this.applyReset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        fileName: null,
        fileInfo: null,
        fileCode: null,
        notes: null
      };
      this.currentContextInfo = {};
      this.resetForm("form");
    },
    codeDownloadReset() {
      this.codeDownloadForm = {
        fileName: null,
        fileSuffix: null
      };
      this.resetForm("codeDownloadForm");
    },
    applyReset() {
      this.applyForm = {
        caseName: null,
        caseProcessTime: null,
        notes: null
      };
      this.resetForm("applyForm");
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
      this.title = "添加文件库";
    },
    /** 查看文件操作 */
    handleCheck(row) {
      this.reset();
      const id = row.id || this.ids
      getFiles(id).then(response => {
        this.form = response.data;
        this.form.fileInfo = JSON.parse(this.form.fileInfo);
        this.open = true;
        this.title = `查看文件${this.form.fileName}`;
      });
    },
    /** 加入实例库操作 **/
    handleApply(row) {
      this.applyReset();
      const id = row.id;
      getFiles(id).then(response => {
        this.form = response.data;
        this.applyOpen = true;
        this.applyTitle = `将${this.form.fileName}加入实例库`;
      });
    },
    // 使用文件编号
    useFileName() {
      this.applyForm.caseName = this.form.fileName;
    },
    // 使用文件备注
    useFileNotes() {
      this.applyForm.notes = this.form.notes;
    },
    /** 查看信息操作 **/
    handleInfo(row) {
      if (row.id) {
        getFiles(row.id).then(response => {
          this.form = response.data;
          this.form.fileInfo = JSON.parse(this.form.fileInfo);
          this.getCurrentContextInfo();
          this.infoOpen = true;
          this.infoTitle = `文件${this.form.fileName}加工信息`;
        });
      } else {
        this.getCurrentContextInfo();
        this.infoOpen = true;
        this.infoTitle = `文件${this.form.fileName}加工信息`;
      }
    },
    // 获得机床、滚刀、路径、数控信息
    getCurrentContextInfo() {
      const machine = this.machineList.find(machine => machine.id === this.form.fileInfo.machineId) || {};
      const hob = this.hobList.find(hob => hob.id === this.form.fileInfo.hobId) || {};
      const path = this.pathList.find(path => path.id === this.form.fileInfo.pathId) || {};
      const cnc = this.cncList.find(cnc => cnc.id === machine.cncId) || {};
      this.currentContextInfo = {
        machineName: machine.machineName,
        machineDescribe: machine.machineDescribe,
        maxWorkpieceDiameter: machine.maxWorkpieceDiameter,
        maxWorkpieceModulus: machine.maxWorkpieceModulus,
        cncName: cnc.cncName,
        hobName: hob.hobName,
        hobHeads: hob.hobHeads,
        hobModulus: hob.hobModulus,
        hobPressureAngle: hob.hobPressureAngle,
        hobSpiralAngle: hob.hobSpiralAngle,
        hobLength: hob.hobLength,
        hobOuterDiameter: hob.hobOuterDiameter,
        pathName: path.pathName
      };
    },
    /** 查看代码操作 **/
    handleCode(row) {
      if (row.id) {
        getFiles(row.id).then(response => {
          this.form = response.data;
          this.codeOpen = true;
          this.codeTitle = `文件${this.form.fileName}加工代码`;
        });
      } else {
        this.codeOpen = true;
        this.codeTitle = `文件${this.form.fileName}加工代码`;
      }
    },
    codeContentFormat(content) {
      if (content) {
        return content;
      }
      return "";
    },
    handleCodeDownload() {
      if (this.form.fileInfo) {
        this.codeDownloadForm = {
          fileName: `${this.form.fileName}_${new Date().getTime()}`,
          fileSuffix: "txt"
        };
        this.codeDownloadOpen = true;
        this.codeDownloadTitle = `确定下载文件${this.form.fileName}加工代码吗？`;
      }
    },
    handleCodeDownloadToLocal() {
      this.$message.success("正在下载中……");
      const blob = new Blob([this.form.fileCode], {type: 'text/plain;charset=utf-8'});
      this.$download.saveAs(blob, `${this.codeDownloadForm.fileName}.${this.codeDownloadForm.fileSuffix}`);
      this.codeDownloadOpen = false;
      // const objUrl = URL.createObjectURL(blob);
      // const a = document.createElement("a");
      // document.body.appendChild(a);
      // a.setAttribute("style", "display: none");
      // a.setAttribute("href", objUrl);
      // a.setAttribute("download", `${this.codeDownloadForm.fileName}.${this.codeDownloadForm.fileSuffix}`);
      // a.click();
      // URL.revokeObjectURL(objUrl);
      // document.body.removeChild(a);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.fileInfo = JSON.stringify(this.form.fileInfo);
            updateFiles(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFiles(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    submitApplyForm() {
      this.$refs["applyForm"].validate(valid => {
        if (valid) {
          const res = {
            caseName: this.applyForm.caseName,
            caseInfo: this.form.fileInfo,
            caseCode: this.form.fileCode,
            caseProcessTime: this.applyForm.caseProcessTime,
            caseEntryTime: parseTime(new Date()),
            notes: this.applyForm.notes
          }
          addCases(res).then(response => {
            this.$modal.msgSuccess("加入实例库成功");
            this.applyOpen = false;
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除文件库编号为"' + ids + '"的数据项？').then(function() {
        return delFiles(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('programming/files/export', {
        ...this.queryParams
      }, `files_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
.code-content {
  margin: 10px;
  font-size: 18px;
  white-space: pre-wrap;
}
</style>
