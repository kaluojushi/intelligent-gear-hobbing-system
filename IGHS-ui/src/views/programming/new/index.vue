<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div slot="header" class="card-title">
            新代码生成
          </div>
          <el-tabs v-model="activeName">
            <el-tab-pane label="齿轮与齿形" name="shape">
              <div class="form-subtitle" style="margin-top: 10px">
                齿轮类型选择
              </div>
              <el-form ref="form_gearType" :model="form">
                <el-row>
                  <el-col :span="12">
                    <el-form-item>
                      <el-cascader
                        v-model="form.gearType"
                        :options="gearTypeOptions"
                        :props="{expandTrigger: 'hover'}"
                        :show-all-levels="false"
                        clearable
                        placeholder="请选择齿轮类型"
                      ></el-cascader>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div class="form-subtitle">
                齿轮数据
              </div>
              <el-form ref="form_gearBasic" :model="form" label-width="85px">
                <el-row :gutter="10">
                  <el-col v-for="item in gearBasicItems" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label" clearable></el-input>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div class="form-subtitle">
                齿形数据
              </div>
              <div v-if="!form.gearType" class="card-note">
                请先选择齿轮类型
              </div>
              <el-form ref="form_gearShape" :model="form" label-width="75px"
                       v-else-if="['helical', 'bevel', 'drum'].includes(form.gearType[1])">
                <el-row :gutter="10">
                  <el-col v-for="item in gearShapeItems" v-if="item.gearType === form.gearType[1]" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label"></el-input>
                        <el-select v-else-if="item.type === 'select'" v-model="form[item.prop]"
                                   :placeholder="'请选择' + item.label">
                          <el-option
                            v-for="it in item.options"
                            :key="it.value"
                            :label="it.label"
                            :value="it.value">
                          </el-option>
                        </el-select>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
              <div v-else class="card-note">
                {{ gearTypes[form.gearType[1]] }}不需要设置齿形数据
              </div>
            </el-tab-pane>

            <el-tab-pane label="设备与安装" name="device">
              <div class="form-subtitle">
                加工设备选择
              </div>
              <el-form ref="form_device" :model="form" label-width="65px">
                <el-row :gutter="10">
                  <el-col :span="12">
                    <el-form-item label="机床">
                      <el-select
                        v-model="form.machineId"
                        filterable
                        @change="machineChange"
                        placeholder="请选择或搜索机床名称">
                        <el-option
                          v-for="machine in machineList"
                          :key="machine.id"
                          :label="machine.machineName"
                          :value="machine.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="12">
                    <el-form-item label="滚刀">
                      <el-select
                        v-model="form.hobId"
                        filterable
                        @change="hobChange"
                        placeholder="请选择或搜索滚刀名称">
                        <el-option
                          v-for="hob in hobList"
                          :key="hob.id"
                          :label="hob.hobName"
                          :value="hob.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-descriptions direction="vertical" :column="8" border style="margin-bottom: 25px">
                  <el-descriptions-item label="机床型号" :span="2" :label-style="textAlign" :content-style="textAlign">
                    {{ currentMachine ? currentMachine.machineName : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="机床描述" :span="2" :label-style="textAlign" :content-style="textAlign">
                    {{ currentMachine ? currentMachine.machineDescribe : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="最大工件外径" :span="1" :label-style="textAlign" :content-style="textAlign">
                    {{ currentMachine ? currentMachine.maxWorkpieceDiameter + "mm" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="最大工件外径" :span="1" :label-style="textAlign" :content-style="textAlign">
                    {{ currentMachine ? currentMachine.maxWorkpieceModulus + "mm" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="数控系统名称" :span="2" :label-style="textAlign" :content-style="textAlign">
                    {{ currentCnc ? currentCnc.cncName : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀名称" :span="2" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobName : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀头数" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobHeads : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀模数" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobModulus + "mm" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀压力角" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobPressureAngle + "°" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀螺旋升角" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobSpiralAngle + "°" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀长度" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobLength + "mm" : "/" }}
                  </el-descriptions-item>
                  <el-descriptions-item label="滚刀外径" :label-style="textAlign" :content-style="textAlign">
                    {{ currentHob ? currentHob.hobOuterDiameter + "mm" : "/" }}
                  </el-descriptions-item>
                </el-descriptions>
              </el-form>

              <div class="form-subtitle">
                安装参数
              </div>
              <el-form ref="form_mounting" :model="form" label-width="100px">
                <el-row :gutter="10">
                  <el-col v-for="item in mountingItems" :span="item.col">
                    <el-form-item :label="item.label" v-if="item.label">
                      <el-row v-if="item.type === 'input'">
                        <el-col :span="20">
                          <el-input v-model="form[item.prop]"
                                    :placeholder="'请输入' + item.label"></el-input>
                        </el-col>
                        <el-col :span="4" class="item-unit" style="padding-left: 2px">
                          {{ item.unit }}
                        </el-col>
                      </el-row>
                      <el-row v-else-if="item.type === 'array'" :gutter="5">
                        <el-col :span="7" v-for="i in 3">
                          <el-col :span="20">
                            <el-input v-model="form[item.prop][i - 1]"
                                      :placeholder="'请输入坐标' + ['X', 'Y', 'Z'][i - 1]" size="small"></el-input>
                          </el-col>
                          <el-col :span="4" class="item-unit" style="padding-left: 2px">
                            {{ item.unit }}
                          </el-col>
                        </el-col>
                      </el-row>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </el-tab-pane>

            <el-tab-pane label="切削与进给" name="process">
              <div class="form-subtitle">
                切削参数
              </div>
              <el-form ref="form_cutting" :model="form" label-width="100px">
                <el-row :gutter="10">
                  <el-col v-for="item in cuttingItems" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label"></el-input>
                        <el-select v-else-if="item.type === 'select'" v-model="form[item.prop]"
                                   :placeholder="'请选择' + item.label">
                          <el-option
                            v-for="it in item.options"
                            :key="it.value"
                            :label="it.label"
                            :value="it.value">
                          </el-option>
                        </el-select>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div class="form-subtitle">
                进给参数
              </div>
              <el-form ref="form_feed" :model="form" label-width="125px">
                <el-row :gutter="10">
                  <el-col v-for="item in feedItems" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label"></el-input>
                        <el-select v-else-if="item.type === 'select'" v-model="form[item.prop]"
                                   :placeholder="'请选择' + item.label">
                          <el-option
                            v-for="it in item.options"
                            :key="it.value"
                            :label="it.label"
                            :value="it.value">
                          </el-option>
                        </el-select>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div class="form-subtitle">
                窜刀设置
              </div>
              <el-form ref="form_fleeCutter" :model="form" label-width="100px">
                <el-row :gutter="10">
                  <el-col v-for="item in fleeCutterItems" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label"></el-input>
                        <el-select v-else-if="item.type === 'select'" v-model="form[item.prop]"
                                   :placeholder="'请选择' + item.label">
                          <el-option
                            v-for="it in item.options"
                            :key="it.value"
                            :label="it.label"
                            :value="it.value">
                          </el-option>
                        </el-select>
                        <el-radio-group v-else-if="item.type === 'radio'" v-model="form[item.prop]">
                          <el-radio :label="1">是</el-radio>
                          <el-radio :label="0">否</el-radio>
                        </el-radio-group>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div class="form-subtitle">
                其他设置
              </div>
              <el-form ref="form_other" :model="form" label-width="100px">
                <el-row :gutter="10">
                  <el-col v-for="item in otherItems" :span="item.col">
                    <el-form-item :label="item.label">
                      <el-col :span="20">
                        <el-input v-if="item.type === 'input'" v-model="form[item.prop]"
                                  :placeholder="'请输入' + item.label"></el-input>
                        <el-select v-else-if="item.type === 'select'" v-model="form[item.prop]"
                                   :placeholder="'请选择' + item.label">
                          <el-option
                            v-for="it in item.options"
                            :key="it.value"
                            :label="it.label"
                            :value="it.value">
                          </el-option>
                        </el-select>
                        <el-radio-group v-else-if="item.type === 'radio'" v-model="form[item.prop]">
                          <el-radio :label="1">是</el-radio>
                          <el-radio :label="0">否</el-radio>
                        </el-radio-group>
                      </el-col>
                      <el-col :span="4" class="item-unit" style="padding-left: 2px">
                        {{ item.unit }}
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </el-tab-pane>
          </el-tabs>

          <div style="margin-bottom: 15px;"></div>
          <el-form ref="bottom" label-width="95px" :inline="true">
            <el-form-item>
              <el-button type="primary" @click="handleUpdateSVG">更新模型</el-button>
              <el-button type="success" @click="">生成路径</el-button>
              <el-button type="danger" @click="reset">清空</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div slot="header" class="card-title">
            SVG模型
          </div>
<!--          <el-image :src="require('@/assets/images/gear.png')"></el-image>-->
          <SVGDiv ref="SVGDiv"></SVGDiv>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div slot="header" class="card-title">
            加工路径
          </div>
          <el-row>
            <el-col :span="8">
              <el-image :src="require('@/assets/images/one_up_milling.png')"></el-image>
            </el-col>
            <el-col :span="16">
              <el-form label-position="left" class="demo-table-expand" label-width="115px">
                <el-form-item label="起始坐标">
                  <span>[ 80, 400 ]</span>
                </el-form-item>
                <el-form-item label="加工准备坐标">
                  <span>[ 56.875, 242.21677930063402 ]</span>
                </el-form-item>
                <el-form-item label="加工起始坐标A">
                  <span>[ 41.875, 242.21677930063402 ]</span>
                </el-form-item>
                <el-form-item label="加工结束坐标B">
                  <span>[ 41.875, 192.21677930063402 ],</span>
                </el-form-item>
                <el-form-item label="加工退出坐标">
                  <span>[ 61.875, 192.21677930063402 ],</span>
                </el-form-item>
                <el-form-item label="终点坐标">
                  <span>[ 80, 400 ]</span>
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <el-form ref="bottom" label-width="95px" :inline="true">
            <el-form-item>
              <el-button type="success" @click="">生成代码</el-button>
              <el-button type="danger">返回</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listMachine} from "@/api/device/machine";
import {listHob} from "@/api/device/hob";
import {listPath} from "@/api/algorithm/path";
import {listCnc} from "@/api/algorithm/cnc";
import SVGDiv from "@/components/SVG/SVGDiv";

export default {
  name: "New",
  components: {SVGDiv},
  data() {
    return {
      // 标签卡激活内容
      activeName: "shape",
      // 表单
      form: {},
      // 齿轮类型选项
      gearTypeOptions: [{
        value: "cylinder",
        label: "圆柱齿轮",
        children: [{
          value: "spur",
          label: "直齿圆柱齿轮"
        }, {
          value: "helical",
          label: "斜齿圆柱齿轮"
        }]
      }, {
        value: "abnormal",
        label: "轴向异型齿轮",
        children: [{
          value: "bevel",
          label: "小锥度齿轮"
        }, {
          value: "drum",
          label: "鼓形齿轮"
        }]
      }, {
        value: "others",
        label: "其他",
        children: [{
          value: "spline",
          label: "花键"
        }, {
          value: "worm",
          label: "蜗轮"
        }]
      }],
      // 齿轮类型表
      gearTypes: {
        spur: "直齿圆柱齿轮",
        helical: "斜齿圆柱齿轮",
        bevel: "小锥度齿轮",
        drum: "鼓形齿轮",
        spline: "花键",
        worm: "蜗轮"
      },
      // 机床、滚刀、路径、数控列表
      machineList: [],
      hobList: [],
      pathList: [],
      cncList: [],
      // 当前对象
      currentMachine: null,
      currentHob: null,
      currentPath: null,
      currentCnc: null,
      // 居中对象
      textAlign: {"text-align": "center"},
      // 齿轮参数项
      gearBasicItems: [
        {label: "齿轮齿数", prop: "gearToothNumber", unit: "", type: "input", default: "18", col: 12},
        {label: "齿轮模数", prop: "gearModulus", unit: "mm", type: "input", default: "2.5", col: 12},
        {label: "压力角", prop: "gearPressureAngle", unit: "°", type: "input", default: "20", col: 12},
        {label: "变位系数", prop: "gearModificationCoefficient", unit: "", type: "input", default: "0", col: 12},
        {label: "齿顶高系数", prop: "gearAddendumCoefficient", unit: "", type: "input", default: "1", col: 12},
        {label: "顶隙系数", prop: "gearClearanceCoefficient", unit: "", type: "input", default: "0.25", col: 12},
        {label: "齿宽", prop: "gearFaceWidth", unit: "mm", type: "input", col: 12},
        {label: "外径", prop: "gearOuterDiameter", unit: "mm", type: "input", col: 12},
        {label: "中心孔直径", prop: "gearCenterHoleDiameter", unit: "mm", type: "input", default: "5", col: 12},
      ],
      gearShapeItems: [
        {label: "螺旋角", prop: "gearHelixAngle", unit: "°", type: "input", col: 12, gearType: 'helical'},
        {
          label: "旋向",
          prop: "gearHelixDirection",
          unit: "",
          type: "select",
          options: [{value: "right", label: "右旋"}, {value: "left", label: "左旋"},],
          col: 12,
          gearType: 'helical'
        },
        {label: "顶锥角", prop: "gearTopConeAngle", unit: "°", type: "input", col: 8, gearType: 'bevel'},
        {label: "根锥角", prop: "gearRootConeAngle", unit: "°", type: "input", col: 8, gearType: 'bevel'},
        {label: "锥距", prop: "gearConeDistance", unit: "mm", type: "input", col: 8, gearType: 'bevel'},
        {label: "鼓形量", prop: "gearDrumShapedSize", unit: "mm", type: "input", col: 8, gearType: 'drum'},
        {label: "鼓齿外径", prop: "gearDrumOuterDiameter", unit: "mm", type: "input", col: 8, gearType: 'drum'},
        {label: "中心半径", prop: "gearCenterRadius", unit: "mm", type: "input", col: 8, gearType: 'drum'},
      ],
      mountingItems: [
        {label: "安装角", prop: "mountingAngle", unit: "°", type: "input", col: 12},
        {col: 12},
        {label: "滚刀初始坐标", prop: "hobInitialPoint", unit: "mm", type: "array", default: new Array(3).fill(""), col: 24},
        {label: "安全距离", prop: "safeDistance", unit: "mm", type: "array", default: new Array(3).fill(""), col: 24},
      ],
      cuttingItems: [
        {label: "切削循环类型", prop: "pathId", unit: "", type: "select", options: [], col: 16},
        {label: "切削次数", prop: "cuttingFrequency", unit: "次", type: "input", col: 12},
        {label: "吃刀深度", prop: "cuttingDepth", unit: "mm", type: "input", col: 12},
        {label: "滚刀转速", prop: "hobRotationSpeed", unit: "r/min", type: "input", col: 12},
        {
          label: "滚刀旋转方向",
          prop: "hobRotationDirection",
          unit: "",
          type: "select",
          options: [{value: "right", label: "右旋"}, {value: "left", label: "左旋"},],
          col: 12
        },
      ],
      feedItems: [
        {label: "进给量", prop: "feedRate", unit: "mm/r", type: "input", col: 12},
        {
          label: "轴向进给方向",
          prop: "axialFeedDirection",
          unit: "",
          type: "select",
          options: [{value: "plus", label: "正向"}, {value: "minus", label: "负向"},],
          col: 12
        },
        {label: "径向快速进给速度", prop: "radialRapidFeedSpeed", unit: "mm/min", type: "input", col: 12},
        {label: "移动后暂停时间", prop: "pauseTimeAfterMove", unit: "s", type: "input", col: 12},
      ],
      fleeCutterItems: [
        {label: "切削后窜刀", prop: "isFleeCutter", unit: "", type: "radio", default: 1, col: 16},
        {label: "窜刀量", prop: "fleeCutterAmounts", unit: "mm", type: "input", col: 12},
        {label: "窜刀齿轮数", prop: "fleeCutterGears", unit: "", type: "input", col: 12}
      ],
      otherItems: [
        {label: "是否外支架", prop: "hasOuterSupport", unit: "", type: "radio", default: 1, col: 12},
        {label: "是否夹具", prop: "hasFixture", unit: "", type: "radio", default: 1, col: 12},
        {label: "是否冷却液", prop: "hasCoolant", unit: "", type: "radio", default: 1, col: 12},
        {label: "是否同步取消", prop: "isSyncCanceled", unit: "", type: "radio", default: 1, col: 12},
      ]
    }
  },
  created() {
    this.getContextList();
    this.init();
  },
  mounted() {
    // this.init();
  },
  methods: {
    /** 初始化 */
    init() {
      this.form.gearType = ["cylinder", "spur"];
      // this.gearBasicItems.forEach(item => {
      //   this.form[item.prop] = item.default !== undefined ? item.default : "";
      // });
      for (const items of [this.gearBasicItems, this.gearShapeItems, this.mountingItems, this.cuttingItems, this.feedItems, this.fleeCutterItems, this.otherItems]) {
        items.forEach(item => {
          this.form[item.prop] = item.default !== undefined ? item.default : "";
        });
      }
      this.form = Object.assign({}, this.form, this.form);
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
        this.pathList.forEach(i => {
          this.cuttingItems[0].options.push({ value: i.id, label: i.pathName });
        });
      });
      listCnc().then(response => {
        this.cncList = response.rows;
      });
    },
    /** 更新模型按钮 */
    handleUpdateSVG() {
      console.log(this.form)
      this.$refs.SVGDiv.paramForm = this.form;
      this.$refs.SVGDiv.handleUpdate();
    },
    /** 清空按钮 */
    reset() {
      this.form = {
        hobInitialPoint: [null, null, null],
        safeDistance: [null, null, null],
      };
      this.currentMachine = null;
      this.currentHob = null;
      this.currentPath = null;
      this.currentCnc = null;
    },
    /** 输入处理 */
    // 输入事件
    // onInput($event) {
    //   this.$forceUpdate();
    // },
    // 选择齿轮设备
    machineChange() {
      this.currentMachine = this.machineList.find(machine => machine.id === this.form.machineId);
      this.currentCnc = this.cncList.find(cnc => cnc.id === this.currentMachine.cncId);
    },
    hobChange() {
      this.currentHob = this.hobList.find(hob => hob.id === this.form.hobId);
    },
    pathChange() {
      this.currentPath = this.pathList.find(path => path.id === this.form.pathId);
    }
  }
}
</script>

<style scoped>
.card-title {
  font: 20px bold;
}

.form-subtitle {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
}

.item-unit {
  font-size: 14px;
  color: #606266;
  font-weight: 700;
}

.card-note {
  margin-left: 10px;
  margin-bottom: 10px;
}
</style>
