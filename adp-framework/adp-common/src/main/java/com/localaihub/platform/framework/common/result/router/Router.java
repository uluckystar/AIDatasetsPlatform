package com.localaihub.platform.framework.common.result.router;

import com.localaihub.platform.framework.common.enu.Role;
import org.apache.htrace.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * 路由配置类
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/15 14:21
 */
public class Router {
    private String path;
    private String name;
    private String redirect;
    private String component;
    private Meta meta;
    private List<Router> children;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    // Getters and Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Router> getChildren() {
        return children;
    }

    public void setChildren(List<Router> children) {
        this.children = children;
    }

    public static class Meta {
        private String title;
        private String icon;
        private String extraIcon;
        private boolean showLink;
        private boolean showParent;
        private List<Role> roles;
        private List<String> auths;
        private boolean keepAlive;
        private String frameSrc;
        private boolean frameLoading;
        private Transition transition;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private boolean hiddenTag;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private int dynamicLevel;
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        private int rank;
        private String activePath;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        // Getters and Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getExtraIcon() {
            return extraIcon;
        }

        public void setExtraIcon(String extraIcon) {
            this.extraIcon = extraIcon;
        }

        public boolean isShowLink() {
            return showLink;
        }

        public void setShowLink(boolean showLink) {
            this.showLink = showLink;
        }

        public boolean isShowParent() {
            return showParent;
        }

        public void setShowParent(boolean showParent) {
            this.showParent = showParent;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }

        public List<String> getAuths() {
            return auths;
        }

        public void setAuths(List<String> auths) {
            this.auths = auths;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public String getFrameSrc() {
            return frameSrc;
        }

        public void setFrameSrc(String frameSrc) {
            this.frameSrc = frameSrc;
        }

        public boolean isFrameLoading() {
            return frameLoading;
        }

        public void setFrameLoading(boolean frameLoading) {
            this.frameLoading = frameLoading;
        }

        public Transition getTransition() {
            return transition;
        }

        public void setTransition(Transition transition) {
            this.transition = transition;
        }

        public boolean isHiddenTag() {
            return hiddenTag;
        }

        public void setHiddenTag(boolean hiddenTag) {
            this.hiddenTag = hiddenTag;
        }

        public int getDynamicLevel() {
            return dynamicLevel;
        }

        public void setDynamicLevel(int dynamicLevel) {
            this.dynamicLevel = dynamicLevel;
        }

        public String getActivePath() {
            return activePath;
        }

        public void setActivePath(String activePath) {
            this.activePath = activePath;
        }
    }

    public static class Transition {
        private String name;
        private String enterTransition;
        private String leaveTransition;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnterTransition() {
            return enterTransition;
        }

        public void setEnterTransition(String enterTransition) {
            this.enterTransition = enterTransition;
        }

        public String getLeaveTransition() {
            return leaveTransition;
        }

        public void setLeaveTransition(String leaveTransition) {
            this.leaveTransition = leaveTransition;
        }
    }
}