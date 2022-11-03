import Status from "@/utils/SVG/Status";
import CommonLib from "@/utils/SVG/CommonLib";
import MathLib from "@/utils/SVG/MathLib";

export default class SpurGear {
  constructor(form) {
    this.params = CommonLib.stringObjToNumberObj(form);
  }

  // 计算齿轮所有参数
  calculateParams() {
    const [m, z, alpha, ha$, c$] = CommonLib.getGearParams(this.params, ["modulus", "toothNumber", "pressureAngle", "addendumCoefficient", "clearanceCoefficient"]);
    // 齿顶高、顶隙
    const ha = this.params.gearAddendum = MathLib.floatCalculation(ha$ * m);
    const c = this.params.gearClearance = MathLib.floatCalculation(c$ * m);
    // 分度圆、齿顶圆、齿根圆、基圆
    const d = this.params.gearPitchDiameter = MathLib.floatCalculation(m * z);
    const r = this.params.gearPitchRadius = MathLib.floatCalculation(d / 2);
    const da = this.params.gearAddendumDiameter = MathLib.floatCalculation(d + 2 * ha);
    const ra = this.params.gearAddendumRadius = MathLib.floatCalculation(da / 2);
    const hf = this.params.gearDedendum = MathLib.floatCalculation(ha + c);
    const df = this.params.gearDedendumDiameter = MathLib.floatCalculation(d - 2 * hf);
    const rf = this.params.gearDedendumRadius = MathLib.floatCalculation(df / 2);
    const h = this.params.gearWholeDepth = MathLib.floatCalculation(ha + hf);
    const db = this.params.gearBaseCircleDiameter = MathLib.floatCalculation(d * Math.cos(MathLib.degToRad(alpha)));
    const rb = this.params.gearBaseCircleRadius = MathLib.floatCalculation(db / 2);
    // 中心孔
    const dh = this.params.gearCenterHoleDiameter;
    const rh = this.params.gearCenterHoleRadius = MathLib.floatCalculation(dh / 2);
    // 齿距、齿厚、齿槽宽
    const p = this.params.gearCircularPitch = MathLib.floatCalculation(Math.PI * m);
    const s = this.params.gearToothThickness = MathLib.floatCalculation(p / 2);
    const e = this.params.gearSpaceWidth = MathLib.floatCalculation(p / 2);
    // 其他
    this.params.angleToothToTooth = MathLib.floatCalculation(MathLib.degToRad(360 / z));

    return CommonLib.numberObjToStringObj(this.params);
  }

  // 验证参数
  checkParams() {
    const [m, z, alpha, ha$, c$, dh] = CommonLib.getGearParams(this.params, ["modulus", "toothNumber", "pressureAngle", "addendumCoefficient", "clearanceCoefficient", "centerHoleDiameter"]);
    const errorMessage = [];

    if (m <= 0) {
      errorMessage.push("模数必须大于0");
    }
    if (z < 3) {
      errorMessage.push("齿数必须不少于3");
    }
    if (alpha <= 0) {
      errorMessage.push("压力角必须大于0");
    }
    const df$ = z - 2 * ha$ - 2 * c$;
    if (df$ <= 0) {
      errorMessage.push("齿顶高/顶隙系数过大");
    }
    if (df$ > 0 && (dh >= Math.min(df$ * m, m * z * Math.cos(MathLib.degToRad(alpha))))) {
      errorMessage.push("中心孔直径过大");
    }

    // if () {
    //   errorMessage.push("请输入完整参数");
    // }

    if (errorMessage.length > 0) {
      return Status.createError(errorMessage.join("；"));
    }
    return Status.OK;
  }
}
