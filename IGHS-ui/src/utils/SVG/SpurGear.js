import Status from "./Status";
import CommonLib from "./CommonLib";
import MathLib from "./MathLib";
import ClipperLib from "./lib/clipper_unminified";
import "@/utils/SVG/lib/svg";
import "./lib/svg.pan-zoom";
import "./lib/svg.path";
import "./lib/svg.export";

export default class SpurGear {
  constructor(form) {
    this.params = CommonLib.stringObjToNumberObj(form);
    this.position = {};
    this.ORIGIN = {X: 0, Y: 0};
    this.CLIPPER_SCALE = 100000;
    this.CLIPPER_LIGHTEN_FACTOR = 0.0005;
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

    if (errorMessage.length > 0) {
      return Status.createError(errorMessage.join("；"))
    }
    return Status.OK;
  }

  // 设置中心点
  setCenter(center) {
    this.position.center = MathLib.clonePoint(center);
  }

  // 更新齿轮坐标与点集
  update() {
    const ra = CommonLib.getGearParam(this.params, "addendumRadius");
    this.position.topLeft = MathLib.addVectors(this.position.center, MathLib.createPoint(-ra, ra));
    this.position.bottomRight = MathLib.addVectors(this.position.center, MathLib.createPoint(ra, -ra));
    this.position.width = this.position.bottomRight.X - this.position.topLeft.X;
    this.position.height = this.position.bottomRight.X - this.position.topLeft.X;
    this.position.left = this.position.topLeft.X;
    this.position.top = this.position.topLeft.Y;

    this.toothPointsTemplate = this.createToothPath();
    // console.log(this.toothPointsTemplate)
  }

  // 绘画图形
  createGraphics(parent, crossMarkerLength, regularLinesStyle, helperLinesStyle, markerLinesStyle) {
    const gearGroup = parent.group();
    const helperGroup = gearGroup.group();
    helperGroup.stroke(helperLinesStyle).fill("none");

    // 绘画分度圆、齿顶圆、基圆、齿根圆
    MathLib.drawCircle(helperGroup, this.ORIGIN, this.params.gearPitchRadius);
    MathLib.drawCircle(helperGroup, this.ORIGIN, this.params.gearAddendumRadius);
    MathLib.drawCircle(helperGroup, this.ORIGIN, this.params.gearBaseCircleRadius);
    MathLib.drawCircle(helperGroup, this.ORIGIN, this.params.gearDedendumRadius);

    // 绘画标记线
    const markerGroup = gearGroup.group();
    markerGroup.stroke(markerLinesStyle).fill("none");
    MathLib.drawCross(markerGroup, this.ORIGIN, crossMarkerLength);

    // 绘画中心孔
    const regularGroup = gearGroup.group();
    regularGroup.stroke(regularLinesStyle).fill("none");
    if (this.params.gearCenterHoleRadius > 0) {
      MathLib.drawCircle(regularGroup, this.ORIGIN, this.params.gearCenterHoleRadius);
    }
    // 插入齿轮路径
    this.insertGearSVGPath(regularGroup);

    // 齿轮移动到中心点
    gearGroup.move(this.position.center.X, this.position.center.Y);
  }

  // 创建齿刀
  createToothCutter() {
    const [s, ha, hf, alpha, r] = CommonLib.getGearParams(this.params, ["toothThickness", "addendum", "dedendum", "pressureAngle", "pitchRadius"]);
    const cutterDepth = hf;
    const cutterOutsideLength = 3 * ha;
    const cosAlpha = Math.cos(MathLib.degToRad(alpha));
    const tanAlpha = Math.tan(MathLib.degToRad(alpha));

    const dx = 0;
    const yBottom = r - cutterDepth;
    const yTop = r + cutterOutsideLength;

    const bottomRightCorner = MathLib.createPoint(s / 2 + dx - tanAlpha * cutterDepth, yBottom);
    const topRightCorner = MathLib.createPoint(s / 2 + dx + tanAlpha * cutterOutsideLength, yTop);
    const topLeftCorner = MathLib.createPoint(-topRightCorner.X, topRightCorner.Y);
    const bottomLeftCorner = MathLib.createPoint(-bottomRightCorner.X, bottomRightCorner.Y);

    const cutterPath = [bottomLeftCorner, topLeftCorner, topRightCorner, bottomRightCorner];
    const bottomLeftCornerIndex = 0;
    return {cutterPath, bottomLeftCornerIndex};
  }

  // 创建齿刀路径
  createToothCutterPaths() {
    const [r, ra] = CommonLib.getGearParams(this.params, ["pitchRadius", "addendumRadius"]);

    const angleStepSize = Math.PI / 600;
    const {cutterPath, bottomLeftCornerIndex} = this.createToothCutter();
    const cutterPaths = [cutterPath];

    let stepCounter = 0;
    while (true) {
      const angle = stepCounter * angleStepSize;
      const xTranslation = angle * r;
      let transformedCutterPath = MathLib.translatePath(cutterPath, xTranslation, 0);
      transformedCutterPath = MathLib.rotatePathAroundCenter(transformedCutterPath, this.ORIGIN, angle);
      cutterPaths.push(transformedCutterPath);

      transformedCutterPath = MathLib.translatePath(cutterPath, -xTranslation, 0);
      transformedCutterPath = MathLib.rotatePathAroundCenter(transformedCutterPath, this.ORIGIN, -angle);
      cutterPaths.unshift(transformedCutterPath);

      stepCounter++;
      if (MathLib.vectorLength(transformedCutterPath[bottomLeftCornerIndex]) > ra) {
        break;
      }
    }

    // console.log(cutterPaths)
    return {cutterPaths, bottomLeftCornerIndex};
  }

  // 创建齿切割路径
  createToothCutoutPath() {
    const {cutterPaths, bottomLeftCornerIndex} = this.createToothCutterPaths();

    const cornersPath = cutterPaths.map(path => MathLib.clonePoint(path[bottomLeftCornerIndex]));
    cornersPath.reverse();

    const combinedPaths = [...cutterPaths, cornersPath];
    const clipper = new ClipperLib.Clipper();
    combinedPaths.forEach(path => {
      ClipperLib.JS.ScaleUpPath(path, this.CLIPPER_SCALE);
      clipper.AddPath(path, ClipperLib.PolyType.ptSubject, true);
    });

    const solutionPaths = new ClipperLib.Paths();
    const succeeded = clipper.Execute(ClipperLib.ClipType.ctUnion, solutionPaths, ClipperLib.PolyFillType.pftNonZero, ClipperLib.PolyFillType.pftNonZero);

    // console.log(solutionPaths)
    return solutionPaths[0];
  }

  // 创建一半的齿路径
  createHalfToothPath(group) {
    const [p, ra, ha, r] = CommonLib.getGearParams(this.params, ["circularPitch", "addendumRadius", "addendum", "pitchRadius"]);
    const toothCutoutPath = this.createToothCutoutPath();

    const angle = this.params.angleToothToTooth / 2;
    const cosAngle = Math.cos(angle);
    const sinAngle = Math.sin(angle);

    const halfPointOnCircle = {
      X: -ra * sinAngle,
      Y: ra * cosAngle
    };
    const tangentIntercept = {
      X: 0,
      Y: ra * (cosAngle + sinAngle * sinAngle / cosAngle)
    };

    const intersectPath = [
      this.ORIGIN,
      halfPointOnCircle,
      tangentIntercept
    ];
    ClipperLib.JS.ScaleUpPath(intersectPath, this.CLIPPER_SCALE);

    const clipper = new ClipperLib.Clipper();
    clipper.AddPath(toothCutoutPath, ClipperLib.PolyType.ptSubject, true);
    clipper.AddPath(intersectPath, ClipperLib.PolyType.ptClip, true);

    const solutionPaths = new ClipperLib.Paths();
    const succeeded = clipper.Execute(ClipperLib.ClipType.ctIntersection, solutionPaths, ClipperLib.PolyFillType.pftNonZero, ClipperLib.PolyFillType.pftNonZero);

    const lightenedPaths = ClipperLib.JS.Lighten(solutionPaths[0], p * this.CLIPPER_LIGHTEN_FACTOR * this.CLIPPER_SCALE);
    const clippedToothCutoutPath = lightenedPaths[0];

    ClipperLib.JS.ScaleDownPath(clippedToothCutoutPath, this.CLIPPER_SCALE);

    const dedendumStartIndex = clippedToothCutoutPath.findIndex(point => Math.abs(point.X) < 0.01 * ha && point.Y < r);
    const halfToothPath = [clippedToothCutoutPath[dedendumStartIndex]];
    let curIdx = dedendumStartIndex;
    const squaredOuterRadius = MathLib.square(ra);
    const getNextIndex = (index) => (index - 1 + clippedToothCutoutPath.length) % clippedToothCutoutPath.length;
    while (true) {
      const nextIdx = getNextIndex(curIdx);
      if (MathLib.squaredVectorLength(clippedToothCutoutPath[nextIdx]) >= squaredOuterRadius) {
        break;
      }
      curIdx = nextIdx;
      halfToothPath.push(clippedToothCutoutPath[curIdx]);
    }

    const lastInsidePoint = clippedToothCutoutPath[curIdx];
    const lastInsideLength = MathLib.vectorLength(lastInsidePoint);
    const firstOnOrOutsidePoint = clippedToothCutoutPath[getNextIndex(curIdx)];
    const firstOnOrOutsideLength = MathLib.vectorLength(firstOnOrOutsidePoint);
    const ratio = (ra - lastInsideLength) / (firstOnOrOutsideLength - lastInsideLength);
    const vectorBetweenPoints = MathLib.subtractVectors(firstOnOrOutsidePoint, lastInsidePoint);
    const pointOnOuterRadius = MathLib.addVectors(lastInsidePoint, MathLib.numericalMultiplyVector(ratio, vectorBetweenPoints));
    halfToothPath.push(pointOnOuterRadius);

    return halfToothPath;
  }

  // 创建完整齿路径
  createToothPath() {
    const halfToothPath = this.createHalfToothPath();
    const mirroredHalfTooth = [...halfToothPath];
    mirroredHalfTooth.reverse().pop();
    const toothPath = [...mirroredHalfTooth.map(point => MathLib.createPoint(-point.X, point.Y)), ...halfToothPath];
    return toothPath;
  }

  // 插入齿轮SVG路径
  insertGearSVGPath(group) {
    const [z, ra] = CommonLib.getGearParams(this.params, ["toothNumber", "addendumRadius"]);
    const SVGPath = group.path();
    const angleOffset = -Math.PI / 2 - this.params.angleToothToTooth / 2;
    // const angleOffset = 0;

    let firstSVGPoint;
    for (let i = 0; i < z; i++) {
      const angle = i * this.params.angleToothToTooth + angleOffset;
      const rotatedToothPoints = MathLib.rotatePathAroundCenter(this.toothPointsTemplate, this.ORIGIN, angle);
      if (i === 0) {
        firstSVGPoint = MathLib.createSVGPoint(rotatedToothPoints[0]);
        MathLib.addLineSegmentsToPath(SVGPath, rotatedToothPoints.slice(1), true);
      } else {
        SVGPath.A(ra, ra, 0, 0, 1, MathLib.createSVGPoint(rotatedToothPoints[0]));
        MathLib.addLineSegmentsToPath(SVGPath, rotatedToothPoints.slice(1));
      }
    }

    SVGPath.A(ra, ra, 0, 0, 1, firstSVGPoint);
    SVGPath.Z();
  }
}
