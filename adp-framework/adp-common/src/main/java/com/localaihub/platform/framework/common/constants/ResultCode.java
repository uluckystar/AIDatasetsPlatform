package com.localaihub.platform.framework.common.constants;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/7 19:44
 */
public class ResultCode {
    public static final int OK = 200;
    public static final int ERROR = 201;
    public static final int LOGINERROR = 202;
    public static final int ACCESSERROR = 203;
    public static final int NOLOGINERROR = 205;
    public static final int SECONDLOGINCODE = 206;
    public static final int BadRequest = 400;
    public static final int Unauthorized = 401;
    public static final int GONE = 410;
    public static final int ServerError = 500;
    public static final int NotImplemented = 501;

    public ResultCode() {
    }
}
