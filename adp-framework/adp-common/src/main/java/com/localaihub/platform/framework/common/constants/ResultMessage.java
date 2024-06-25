package com.localaihub.platform.framework.common.constants;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/7 19:44
 */
public class ResultMessage {
    public static final String OK = "请求成功";
    public static final int ERROR = 201;
    public static final int COMPILEERROR = 202;
    public static final int EXEERROR = 203;
    public static final int COMPILEOK = 204;
    public static final int FORMATERROR = 205;
    public static final int EXETIMEOUT = 206;
    public static final int RIGHTCOUNT = 207;
    public static final String LOGINERROR = "用户名或密码错误";
    public static final String CODEERROR = "验证码错误";
    public static final String PARAMETERERROR = "参数错误";
    public static final String GONE = "资源不存在";
    public static final String SERVER_ERROR = "系统内部错误";
    public static final String NOT_IMPLEMENTED = "服务器不支持请求的功能";

    public ResultMessage() {
    }
}
