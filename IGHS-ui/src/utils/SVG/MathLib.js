export default class MathLib {
  // 浮点数计算处理
  static floatCalculation(res) {
    return parseFloat(res.toFixed(10));
  }

  // 弧度转角度
  static radToDeg(rad) {
    return rad / Math.PI * 180;
  }

  // 角度转弧度
  static degToRad(deg) {
    return deg / 180 * Math.PI;
  }

  // 点
  static createPoint(x, y) {
    return {X: x, Y: y};
  }

  // SVG点
  static createSVGPoint(point) {
    return {x: point.X, y: point.Y};
  }

  // 克隆点
  static clonePoint(point) {
    return {X: point.X, Y: point.Y};
  }

  // 平移点
  static translatePoint(point, dx, dy) {
    return {X: point.X + dx, Y: point.Y + dy};
  }

  // 平移路径（点集）
  static translatePath(path, dx, dy) {
    return path.map(point => MathLib.translatePoint(point, dx, dy));
  }

  // 根据向量平移点
  static translatePointByVector(point, vector) {
    return {X: point.X + vector.X, Y: point.Y + vector.Y};
  }

  // 根据向量平移点集
  static translatePathByVector(path, vector) {
    return path.map(point => MathLib.translatePointByVector(point, vector));
  }

  // 平方
  static square(x) {
    return x * x;
  }

  // 两点距离的平方
  static squaredPointsDistance(p1, p2) {
    return MathLib.square(p1.X - p2.X) + MathLib.square(p1.Y - p2.Y);
  }

  // 两点距离
  static  pointsDistance(p1, p2) {
    return Math.sqrt(MathLib.squaredPointsDistance(p1, p2));
  }

  // 向量长度的平方
  static squaredVectorLength(vector) {
    return MathLib.square(vector.X) + MathLib.square(vector.Y);
  }

  // 向量长度
  static vectorLength(vector) {
    return Math.sqrt(MathLib.squaredVectorLength(vector));
  }

  // 向量之和
  static addVectors(v1, v2) {
    return {X: v1.X + v2.X, Y: v1.Y + v2.Y};
  }

  // 向量之差
  static subtractVectors(v1, v2) {
    return MathLib.addVectors(v1, MathLib.numericalMultiplyVector(-1, v2));
  }

  // 向量数乘
  static numericalMultiplyVector(a, vector) {
    return {X: a * vector.X, Y: a * vector.Y};
  }

  // 画圆
  static drawCircle(parent, center, radius) {
    parent.circle(2 * radius).cx(center.X).cy(center.Y);
  }

  // 画多个圆（相同半径）
  static drawCircles(parent, centers, radius) {
    centers.forEach(center => MathLib.drawCircle(parent, center, radius));
  }

  // 画线（根据起点和终点）
  static drawLine(parent, start, end) {
    parent.line(start.X, start.Y, end.X, end.Y);
  }

  // 画交叉线
  static drawCross(parent, center, length) {
    const half = length / 2;
    const start1 = MathLib.addVectors(center, MathLib.createPoint(-half, 0));
    const end1 = MathLib.addVectors(center, MathLib.createPoint(half, 0));
    const start2 = MathLib.addVectors(center, MathLib.createPoint(0, -half));
    const end2 = MathLib.addVectors(center, MathLib.createPoint(0, half));
    MathLib.drawLine(parent, start1, end1);
    MathLib.drawLine(parent, start2, end2);
  }

  // 将点旋转一个角度（弧度制），逆时针为正，顺时针为负
  static rotatePointAroundCenter(point, center, angle) {
    const cosAngle = Math.cos(angle);
    const sinAngle = Math.sin(angle);
    const movedPoint = MathLib.subtractVectors(point, center);
    const rotatedPoint = {
      X: movedPoint.X * cosAngle - movedPoint.Y * sinAngle,
      Y: movedPoint.X * sinAngle + movedPoint.Y * cosAngle
    }
    return MathLib.addVectors(rotatedPoint, center);
  }

  // 将路径（点集）旋转一个角度
  static rotatePathAroundCenter(path, center, angle) {
    return path.map(point => MathLib.rotatePointAroundCenter(point, center, angle));
  }

  // 将线段添加到SVG路径
  static addLineSegmentsToPath(SVGPath, points, moveToFirst = false) {
    for (let i = 0; i < points.length; i++) {
      const SVGPoint = MathLib.createSVGPoint(points[i]);
      if (i === 0 && moveToFirst) {
        SVGPath.M(SVGPoint);
      } else {
        SVGPath.L(SVGPoint);
      }
    }
  }
}
