export default class CommonLib {
  // 对象转换
  static objConverse(obj, type) {
    return Object.fromEntries(Object.entries(obj).map(([k, v]) => [k, type(v)]));
  }

  // 字符串对象转数字对象（用于计算）
  static stringObjToNumberObj(obj) {
    return CommonLib.objConverse(obj, Number);
  }

  // 数字对象转字符串对象（用于表格显示）
  static numberObjToStringObj(obj) {
    return CommonLib.objConverse(obj, String);
  }

  // 根据字符串获得齿轮参数
  static getGearParam(params, prop) {
    return params["gear" + prop[0].toUpperCase() + prop.slice(1)];
  }

  // 根据多个字符串获得齿轮参数
  static getGearParams(params, props) {
    return props.map(prop => CommonLib.getGearParam(params, prop));
  }
}
