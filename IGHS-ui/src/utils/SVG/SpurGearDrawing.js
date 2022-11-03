import CommonLib from "@/utils/SVG/CommonLib";
import MathLib from "@/utils/SVG/MathLib";
import ClipperLib from "@/utils/SVG/lib/clipper_unminified";
import "@/utils/SVG/lib/svg";
import "@/utils/SVG/lib/svg.pan-zoom";
import "@/utils/SVG/lib/svg.path";
import "@/utils/SVG/lib/svg.export";

export default class SpurGearDrawing {
  constructor(gear) {
    this.gear = gear;
    this.position = {};
    this.ORIGIN = { X: 0, Y: 0 };
    this.CLIPPER_SCALE = 100000;
    this.CLIPPER_LIGHTEN_FACTOR = 0.0005;
  }

  // 设置中心点
  setCenter(center) {
    this.position.center = MathLib.clonePoint(center);
  }

  // 更新齿轮坐标与点集
  update() {
    const ra = CommonLib.getGearParam(this.gear.params, "addendumRadius");
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
    const [r, ra, rb, rf, rh] = CommonLib.getGearParams(this.gear.params, ["pitchRadius", "addendumRadius", "baseCircleRadius", "dedendumRadius", "centerHoleRadius"]);
    const gearGroup = parent.group();
    const helperGroup = gearGroup.group();
    helperGroup.stroke(helperLinesStyle).fill("none");

    // 绘画分度圆、齿顶圆、基圆、齿根圆
    MathLib.drawCircle(helperGroup, this.ORIGIN, r);
    MathLib.drawCircle(helperGroup, this.ORIGIN, ra);
    MathLib.drawCircle(helperGroup, this.ORIGIN, rb);
    MathLib.drawCircle(helperGroup, this.ORIGIN, rf);

    // 绘画标记线
    const markerGroup = gearGroup.group();
    markerGroup.stroke(markerLinesStyle).fill("none");
    MathLib.drawCross(markerGroup, this.ORIGIN, crossMarkerLength);

    // 绘画中心孔
    const regularGroup = gearGroup.group();
    regularGroup.stroke(regularLinesStyle).fill("none");
    if (rh > 0) {
      MathLib.drawCircle(regularGroup, this.ORIGIN, rh);
    }
    // 插入齿轮路径
    this.insertGearSVGPath(regularGroup);

    // 齿轮移动到中心点
    gearGroup.move(this.position.center.X, this.position.center.Y);
  }

  // 创建齿刀
  createToothCutter() {
    const [s, ha, hf, alpha, r] = CommonLib.getGearParams(this.gear.params, ["toothThickness", "addendum", "dedendum", "pressureAngle", "pitchRadius"]);
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
    return { cutterPath, bottomLeftCornerIndex };
  }

  // 创建齿刀路径
  createToothCutterPaths() {
    const [r, ra] = CommonLib.getGearParams(this.gear.params, ["pitchRadius", "addendumRadius"]);

    const angleStepSize = Math.PI / 600;
    const { cutterPath, bottomLeftCornerIndex } = this.createToothCutter();
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
    return { cutterPaths, bottomLeftCornerIndex };
  }

  // 创建齿切割路径
  createToothCutoutPath() {
    const { cutterPaths, bottomLeftCornerIndex } = this.createToothCutterPaths();

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
    const [p, ra, ha, r] = CommonLib.getGearParams(this.gear.params, ["circularPitch", "addendumRadius", "addendum", "pitchRadius"]);
    const toothCutoutPath = this.createToothCutoutPath();

    const angle = this.gear.params.angleToothToTooth / 2;
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
    const [z, ra] = CommonLib.getGearParams(this.gear.params, ["toothNumber", "addendumRadius"]);
    const SVGPath = group.path();
    const angleOffset = -Math.PI / 2 - this.gear.params.angleToothToTooth / 2;
    // const angleOffset = 0;

    let firstSVGPoint;
    for (let i = 0; i < z; i++) {
      const angle = i * this.gear.params.angleToothToTooth + angleOffset;
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
