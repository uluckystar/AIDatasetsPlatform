package com.localaihub.platform.gateway.enu;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 21:20
 */
public enum ServerRouteEnum {

    /**
     * 路由信息
     */
    CORE_ROUTE("core-route", "开放平台鉴权配置接口");

    private String routeId;
    private String swaggerInfo;

    ServerRouteEnum(String routeId, String swaggerInfo) {
        this.routeId = routeId;
        this.swaggerInfo = swaggerInfo;
    }

    /**
     * 根据路由id获取swagger信息
     *
     * @param routId 路由id
     * @return swagger信息
     */
    public static String getSwaggerInfoByRoutId(String routId) {
        for (ServerRouteEnum routeEnum : ServerRouteEnum.values()) {
            if (routId.equals(routeEnum.getRouteId())) {
                return routeEnum.getSwaggerInfo();
            }
        }
        return null;
    }

    /**
     * @return the routeId
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * @param routeId : the routeId to set
     */
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the swaggerInfo
     */
    public String getSwaggerInfo() {
        return swaggerInfo;
    }

    /**
     * @param swaggerInfo : the swaggerInfo to set
     */
    public void setSwaggerInfo(String swaggerInfo) {
        this.swaggerInfo = swaggerInfo;
    }
}
