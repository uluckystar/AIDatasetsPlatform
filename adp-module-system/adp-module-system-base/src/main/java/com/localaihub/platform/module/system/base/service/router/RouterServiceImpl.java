package com.localaihub.platform.module.system.base.service.router;

import com.localaihub.platform.framework.common.enu.Role;
import com.localaihub.platform.framework.common.result.router.Router;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 路由服务实现类，用于获取异步路由
 * 作者：Jiaxing Jiang
 * 版本：0.1.0-SNAPSHOT
 * 日期：2024/5/15 14:22
 */
@Service
public class RouterServiceImpl implements RouterService {

    /**
     * 获取异步路由列表
     * @return 异步路由列表
     */
    @Override
    public List<Router> getAsyncRoutes() {
        // 创建并返回各个路由
        Router systemManagementRouter = createSystemManagementRouter();
        Router systemMonitorRouter = createSystemMonitorRouter();
        Router permissionRouter = createPermissionRouter();
        Router frameRouter = createFrameRouter();
//        Router tabsRouter = createTabsRouter();
        return Arrays.asList(systemManagementRouter, frameRouter); //permissionRouter,tabsRouter,systemMonitorRouter
    }

    /**
     * 创建系统管理路由
     * @return 系统管理路由
     */
    @Override
    public Router createSystemManagementRouter() {
        // 系统管理主路由
        Router systemManagementRouter = new Router();
        systemManagementRouter.setPath("/system");
        systemManagementRouter.setMeta(new Router.Meta());
        systemManagementRouter.getMeta().setIcon("ri:settings-3-line");
        systemManagementRouter.getMeta().setTitle("menus.pureSysManagement");
        systemManagementRouter.getMeta().setRank(12);

        // 系统用户子路由
        Router systemUser = new Router();
        systemUser.setPath("/system/user/index");
        systemUser.setName("SystemUser");
        systemUser.setMeta(new Router.Meta());
        systemUser.getMeta().setIcon("ri:admin-line");
        systemUser.getMeta().setTitle("menus.pureUser");
        systemUser.getMeta().setRoles(Arrays.asList(Role.ROLE_ADMIN,Role.ROLE_SUPER_ADMIN));

        // 系统角色子路由
        Router systemRole = new Router();
        systemRole.setPath("/system/role/index");
        systemRole.setName("SystemRole");
        systemRole.setMeta(new Router.Meta());
        systemRole.getMeta().setIcon("ri:admin-fill");
        systemRole.getMeta().setTitle("menus.pureRole");
        systemRole.getMeta().setRoles(Arrays.asList(Role.ROLE_ADMIN,Role.ROLE_SUPER_ADMIN));

//        // 系统菜单子路由
//        Router systemMenu = new Router();
//        systemMenu.setPath("/system/menu/index");
//        systemMenu.setName("SystemMenu");
//        systemMenu.setMeta(new Router.Meta());
//        systemMenu.getMeta().setIcon("ep:menu");
//        systemMenu.getMeta().setTitle("menus.pureSystemMenu");
//        systemMenu.getMeta().setShowLink(false);
//        systemMenu.getMeta().setRoles(Arrays.asList(Role.ADMIN,Role.SUPER_ADMIN));

        // 系统部门子路由
        Router systemDept = new Router();
        systemDept.setPath("/system/dept/index");
        systemDept.setName("SystemDept");
        systemDept.setMeta(new Router.Meta());
        systemDept.getMeta().setIcon("ri:git-branch-line");
        systemDept.getMeta().setTitle("menus.pureDept");
        systemDept.getMeta().setRoles(Arrays.asList(Role.ROLE_ADMIN,Role.ROLE_SUPER_ADMIN));

        // 将子路由添加到主路由中
        systemManagementRouter.setChildren(Arrays.asList(systemUser, systemRole, systemDept));
        return systemManagementRouter;
    }

    /**
     * 创建系统监控路由
     * @return 系统监控路由
     */
    @Override
    public Router createSystemMonitorRouter() {
        // 系统监控主路由
        Router systemMonitorRouter = new Router();
        systemMonitorRouter.setPath("/monitor");
        systemMonitorRouter.setMeta(new Router.Meta());
        systemMonitorRouter.getMeta().setIcon("ep:monitor");
        systemMonitorRouter.getMeta().setTitle("menus.pureSysMonitor");
        systemMonitorRouter.getMeta().setRank(13);

        // 在线用户子路由
        Router onlineUser = new Router();
        onlineUser.setPath("/monitor/online-user");
        onlineUser.setName("OnlineUser");
        onlineUser.setComponent("monitor/online/index"); // 根据前端组件路径调整
        onlineUser.setMeta(new Router.Meta());
        onlineUser.getMeta().setIcon("ri:user-voice-line");
        onlineUser.getMeta().setTitle("menus.pureOnlineUser");
        onlineUser.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN));

        // 登录日志子路由
        Router loginLog = new Router();
        loginLog.setPath("/monitor/login-logs");
        loginLog.setName("LoginLog");
        loginLog.setComponent("monitor/logs/login/index"); // 根据前端组件路径调整
        loginLog.setMeta(new Router.Meta());
        loginLog.getMeta().setIcon("ri:window-line");
        loginLog.getMeta().setTitle("menus.pureLoginLog");
        loginLog.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN));

        // 操作日志子路由
        Router operationLog = new Router();
        operationLog.setPath("/monitor/operation-logs");
        operationLog.setName("OperationLog");
        operationLog.setComponent("monitor/logs/operation/index");
        operationLog.setMeta(new Router.Meta());
        operationLog.getMeta().setIcon("ri:history-fill");
        operationLog.getMeta().setTitle("menus.pureOperationLog");
        operationLog.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN));

        // 系统日志子路由
        Router systemLog = new Router();
        systemLog.setPath("/monitor/system-logs");
        systemLog.setName("SystemLog");
        systemLog.setComponent("monitor/logs/system/index"); // 根据前端组件路径调整
        systemLog.setMeta(new Router.Meta());
        systemLog.getMeta().setIcon("ri:file-search-line");
        systemLog.getMeta().setTitle("menus.pureSystemLog");
        systemLog.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN));

        // 将子路由添加到主路由中
        systemMonitorRouter.setChildren(Arrays.asList(onlineUser, loginLog, operationLog, systemLog));
        return systemMonitorRouter;
    }

    /**
     * 创建权限路由
     * @return 权限路由
     */
    @Override
    public Router createPermissionRouter() {
        // 权限主路由
        Router permissionRouter = new Router();
        permissionRouter.setPath("/permission");
        permissionRouter.setMeta(new Router.Meta());
        permissionRouter.getMeta().setIcon("ep:lollipop");
        permissionRouter.getMeta().setTitle("menus.purePermission");
        permissionRouter.getMeta().setRank(12);

        // 权限页面子路由
        Router permissionPage = new Router();
        permissionPage.setPath("/permission/page/index");
        permissionPage.setName("PermissionPage");
        permissionPage.setMeta(new Router.Meta());
        permissionPage.getMeta().setTitle("menus.purePermissionPage");
        permissionPage.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));

        // 权限按钮子路由
        Router permissionButton = new Router();
        permissionButton.setPath("/permission/button/index");
        permissionButton.setName("PermissionButton");
        permissionButton.setMeta(new Router.Meta());
        permissionButton.getMeta().setTitle("menus.purePermissionButton");
        permissionButton.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));
        permissionButton.getMeta().setAuths(Arrays.asList("permission:btn:add", "permission:btn:edit", "permission:btn:delete"));

        // 将子路由添加到主路由中
        permissionRouter.setChildren(Arrays.asList(permissionPage, permissionButton));
        return permissionRouter;
    }

    /**
     * 创建外部页面框架路由
     * @return 外部页面框架路由
     */
    @Override
    public Router createFrameRouter() {
        // 外部页面框架主路由
        Router frameRouter = new Router();
        frameRouter.setPath("/iframe");
        frameRouter.setMeta(new Router.Meta());
        frameRouter.getMeta().setIcon("ri:links-fill");
//        frameRouter.getMeta().setTitle("menus.pureExternalPage");
        frameRouter.getMeta().setTitle("接口文档");
        frameRouter.getMeta().setRank(14);

        // 嵌入文档子路由
        Router frameEmbedded = new Router();
        frameEmbedded.setPath("/iframe/embedded");
        frameEmbedded.setMeta(new Router.Meta());
        frameEmbedded.getMeta().setTitle("menus.pureEmbeddedDoc");

//        // ColorHunt 子路由
//        Router frameColorHunt = new Router();
//        frameColorHunt.setPath("/iframe/colorhunt");
//        frameColorHunt.setName("FrameColorHunt");
//        frameColorHunt.setMeta(new Router.Meta());
//        frameColorHunt.getMeta().setTitle("menus.pureColorHuntDoc");
//        frameColorHunt.getMeta().setFrameSrc("https://colorhunt.co/");
//        frameColorHunt.getMeta().setKeepAlive(true);
//        frameColorHunt.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));

//        // UI Gradients 子路由
//        Router frameUiGradients = new Router();
//        frameUiGradients.setPath("/iframe/uigradients");
//        frameUiGradients.setName("FrameUiGradients");
//        frameUiGradients.setMeta(new Router.Meta());
//        frameUiGradients.getMeta().setTitle("menus.pureUiGradients");
//        frameUiGradients.getMeta().setFrameSrc("https://uigradients.com/");
//        frameUiGradients.getMeta().setKeepAlive(true);
//        frameUiGradients.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));

        // swagger 子路由
        Router frameEp = new Router();
        frameEp.setPath("/iframe/swagger");
        frameEp.setName("swagger");
        frameEp.setMeta(new Router.Meta());
        frameEp.getMeta().setTitle("OpenAPI文档（swagger）");
//        frameEp.getMeta().setFrameSrc("http://192.168.1.103:8088/swagger-ui/index.html");
        frameEp.getMeta().setFrameSrc("http://127.0.0.1:8088/swagger-ui/index.html");
        frameEp.getMeta().setKeepAlive(true);
//        frameEp.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
        frameEp.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN));

//        // Tailwind CSS 子路由
//        Router frameTailwindcss = new Router();
//        frameTailwindcss.setPath("/iframe/tailwindcss");
//        frameTailwindcss.setName("FrameTailwindcss");
//        frameTailwindcss.setMeta(new Router.Meta());
//        frameTailwindcss.getMeta().setTitle("menus.pureTailwindcssDoc");
//        frameTailwindcss.getMeta().setFrameSrc("https://tailwindcss.com/docs/installation");
//        frameTailwindcss.getMeta().setKeepAlive(true);
//        frameTailwindcss.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // Vue.js 子路由
//        Router frameVue = new Router();
//        frameVue.setPath("/iframe/vue3");
//        frameVue.setName("FrameVue");
//        frameVue.setMeta(new Router.Meta());
//        frameVue.getMeta().setTitle("menus.pureVueDoc");
//        frameVue.getMeta().setFrameSrc("https://cn.vuejs.org/");
//        frameVue.getMeta().setKeepAlive(true);
//        frameVue.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // Vite 子路由
//        Router frameVite = new Router();
//        frameVite.setPath("/iframe/vite");
//        frameVite.setName("FrameVite");
//        frameVite.setMeta(new Router.Meta());
//        frameVite.getMeta().setTitle("menus.pureViteDoc");
//        frameVite.getMeta().setFrameSrc("https://cn.vitejs.dev/");
//        frameVite.getMeta().setKeepAlive(true);
//        frameVite.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // Pinia 子路由
//        Router framePinia = new Router();
//        framePinia.setPath("/iframe/pinia");
//        framePinia.setName("FramePinia");
//        framePinia.setMeta(new Router.Meta());
//        framePinia.getMeta().setTitle("menus.purePiniaDoc");
//        framePinia.getMeta().setFrameSrc("https://pinia.vuejs.org/zh/index.html");
//        framePinia.getMeta().setKeepAlive(true);
//        framePinia.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // Vue Router 子路由
//        Router frameRouterDoc = new Router();
//        frameRouterDoc.setPath("/iframe/vue-router");
//        frameRouterDoc.setName("FrameRouter");
//        frameRouterDoc.setMeta(new Router.Meta());
//        frameRouterDoc.getMeta().setTitle("menus.pureRouterDoc");
//        frameRouterDoc.getMeta().setFrameSrc("https://router.vuejs.org/zh/");
//        frameRouterDoc.getMeta().setKeepAlive(true);
//        frameRouterDoc.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));

//        // 将子路由添加到嵌入文档路由中
//        frameEmbedded.setChildren(Arrays.asList(frameColorHunt, frameUiGradients, frameEp, frameTailwindcss, frameVue, frameVite, framePinia, frameRouterDoc));
        frameEmbedded.setChildren(Arrays.asList(frameEp));

//        // 外部文档子路由
//        Router externalDocs = new Router();
//        externalDocs.setPath("/iframe/external");
//        externalDocs.setMeta(new Router.Meta());
//        externalDocs.getMeta().setTitle("menus.pureExternalDoc");
//
//        // 外部链接子路由
//        Router externalLink = new Router();
//        externalLink.setPath("/external");
//        externalLink.setName("https://pure-admin.github.io/pure-admin-doc");
//        externalLink.setMeta(new Router.Meta());
//        externalLink.getMeta().setTitle("menus.pureExternalLink");
//        externalLink.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // 实用工具链接子路由
//        Router utilsLink = new Router();
//        utilsLink.setPath("/pureUtilsLink");
//        utilsLink.setName("https://pure-admin.github.io/pure-admin-doc");
//        utilsLink.setMeta(new Router.Meta());
//        utilsLink.getMeta().setTitle("menus.pureUtilsLink");
//        utilsLink.getMeta().setRoles(Arrays.asList(Role.SUPER_ADMIN, Role.ADMIN, Role.USER));
//
//        // 将子路由添加到外部文档路由中
//        externalDocs.setChildren(Arrays.asList(externalLink, utilsLink));

        // 将嵌入文档和外部文档路由添加到主路由中
//        frameRouter.setChildren(Arrays.asList(frameEmbedded, externalDocs));
        frameRouter.setChildren(Arrays.asList(frameEmbedded));
        return frameRouter;
    }

    /**
     * 创建标签页路由
     * @return 标签页路由
     */
    @Override
    public Router createTabsRouter() {
        // 标签页主路由
        Router tabsRouter = new Router();
        tabsRouter.setPath("/tabs");
        tabsRouter.setMeta(new Router.Meta());
        tabsRouter.getMeta().setIcon("ri:bookmark-2-line");
        tabsRouter.getMeta().setTitle("menus.pureTabs");
        tabsRouter.getMeta().setRank(15);

        // 标签页索引子路由
        Router tabsIndex = new Router();
        tabsIndex.setPath("/tabs/index");
        tabsIndex.setName("Tabs");
        tabsIndex.setMeta(new Router.Meta());
        tabsIndex.getMeta().setTitle("menus.pureTabs");
        tabsIndex.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));

        // 查询详情子路由
        Router tabQueryDetail = new Router();
        tabQueryDetail.setPath("/tabs/query-detail");
        tabQueryDetail.setName("TabQueryDetail");
        tabQueryDetail.setMeta(new Router.Meta());
        tabQueryDetail.getMeta().setShowLink(false);
        tabQueryDetail.getMeta().setActivePath("/tabs/index");
        tabQueryDetail.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));

        // 参数详情子路由
        Router tabParamsDetail = new Router();
        tabParamsDetail.setPath("/tabs/params-detail/:id");
        tabParamsDetail.setName("TabParamsDetail");
        tabParamsDetail.setComponent("params-detail"); // 根据前端组件路径调整
        tabParamsDetail.setMeta(new Router.Meta());
        tabParamsDetail.getMeta().setShowLink(false);
        tabParamsDetail.getMeta().setActivePath("/tabs/index");
        tabParamsDetail.getMeta().setRoles(Arrays.asList(Role.ROLE_SUPER_ADMIN, Role.ROLE_ADMIN, Role.ROLE_USER));

        // 将子路由添加到主路由中
        tabsRouter.setChildren(Arrays.asList(tabsIndex, tabQueryDetail));
        return tabsRouter;
    }
}
