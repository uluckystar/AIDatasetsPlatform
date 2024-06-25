package com.localaihub.platform.module.system.base.service.router;

import com.localaihub.platform.framework.common.result.router.Router;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/15 14:22
 */
public interface RouterService {
    List<Router> getAsyncRoutes();
    Router createSystemManagementRouter();
    Router createSystemMonitorRouter();
    Router createPermissionRouter();
    Router createFrameRouter();
    Router createTabsRouter();
}
