<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<s:include value="../header.jsp" />
</head>

<body style="min-width:900px;" screen_capture_injected="true">
<div id="loading"><i class="loadingicon"></i><span>正在加载...</span></div>
<div id="right_tools_wrapper">
<span id="refresh_wrapper" title="刷新当前页" ><i class="fa fa-refresh right_tool_icon"></i></span>
</div>
<div class="navbar">
<div class="navbar-inner">
    <div class="container-fluid">
        <a href="<zop:webinfo type="ctx"/>admin/index.action" class="brand"> <small>
            <img src="<zop:webinfo type="images"/>icon/logo-18.png">
            ZOP 后台
        </small>
        </a>
        <div class="pull-left nav_shortcuts" >
            <a class="btn btn-small btn-success" href="javascript:openapp('#','index_termlist','分类管理');" title="分类管理">
                <i class="fa fa-th"></i>
            </a>

            <a class="btn btn-small btn-info" href="javascript:openapp('#','index_postlist','文章管理');" title="文章管理">
                <i class="fa fa-pencil"></i>
            </a>

            <a class="btn btn-small btn-warning" href="#" title="前台首页" target="_blank">
                <i class="fa fa-home"></i>
            </a>

            <a class="btn btn-small btn-danger" href="javascript:openapp('#','index_clearcache','清除缓存');" title="清除缓存">
                <i class="fa fa-trash-o"></i>
            </a>
        </div>

        <ul class="nav simplewind-nav pull-right">
            <li class="light-blue">
                <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                    <img class="nav-user-photo" src="<zop:webinfo type="images"/>icon/logo-18.png" alt="{$admin.user_login}">
                        <span class="user-info">
                            <small>欢迎,</small>admin
                        </span>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
                    <li><a href="javascript:openapp('#','index_site','站点管理');"><i class="fa fa-cog"></i>站点管理</a></li>
                    <li><a href="javascript:openapp('#','index_userinfo','个人资料');"><i class="fa fa-user"></i>个人资料</a></li>
                    <li class="divider"></li>
                    <li><a href="#"><i class="fa fa-off"></i>退出</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>
</div>

<div class="main-container container-fluid">

<div class="sidebar" id="sidebar">
    <!-- <div class="sidebar-shortcuts" id="sidebar-shortcuts">
    </div> -->
    <div id="nav_wraper">
        <ul class="nav nav-list">
            <!-- 开始 -->
            <s:iterator id="m" value="%{menuList}" status="st" >
            <li>
                <s:if test="#m.hasChildren == true">
                    <a href="#" class="dropdown-toggle">
                        <i class="fa fa-<s:property value="#m.icon"/> normal"></i>
                        <span class="menu-text normal"><s:property value="#m.name"/></span>
                        <b class="arrow fa fa-angle-right normal"></b>
                        <i class="fa fa-reply back"></i>
                        <span class="menu-text back">返回</span>
                    </a>
                </s:if>
                <s:else>
                    <a href="javascript:openapp('<zop:webinfo type="ctx"/><s:property value="#m.url"/>','<s:property value="#m.id"/>','<s:property value="#m.name"/>');">
                        <i class="fa fa-<s:property value="#m.icon"/>"></i>
                        <span class="menu-text"><s:property value="#m.name"/></span>
                    </a>
                </s:else>
                <s:if test="#m.hasChildren == true">
                    <ul  class="submenu">
                    <s:iterator id="cm" value="#m.children" status="st" >
                        <li>
                            <s:if test="#cm.hasChildren == true">
                                <a href="#" class="dropdown-toggle">
                                    <i class="fa fa-caret-right"></i>
                                    <span class="menu-text"><s:property value="#cm.name"/></span>
                                    <b class="arrow fa fa-angle-right"></b>
                                </a>
                            </s:if>
                            <s:else>
                                <a href="javascript:openapp('<zop:webinfo type="ctx"/><s:property value="#cm.url"/>','<s:property value="#cm.id"/>','<s:property value="#cm.name"/>');">
                                    <i class="fa fa-caret-right"></i>
                                    <span class="menu-text"><s:property value="#cm.name"/></span>
                                </a>
                            </s:else>
                            <s:if test="#cm.hasChildren == true">
                            <ul  class="submenu">
                                <s:iterator id="ccm" value="#cm.children" status="st" >
                                <li>
                                    <a href="javascript:openapp('<zop:webinfo type="ctx"/><s:property value="#ccm.url"/>','<s:property value="#ccm.id"/>','<s:property value="#ccm.name"/>');">
                                        <i class="fa fa-angle-double-right"></i>
                                        <span class="menu-text"><s:property value="#ccm.name"/></span>
                                    </a>
                                </li>
                                </s:iterator>
                            </ul>
                            </s:if>
                        </li>
                    </s:iterator>
                    </ul>

                </s:if>
            </li>
            </s:iterator>
            <!--结束-->
        </ul>
    </div>

</div>

<div class="main-content">
    <div class="breadcrumbs" id="breadcrumbs">
        <a id="task-pre" class="task-changebt">←</a>
        <div id="task-content">
            <ul class="macro-component-tab" id="task-content-inner">
                <li class="macro-component-tabitem noclose" app-id="0" app-url="main.html" app-name="首页">
                    <span class="macro-tabs-item-text">首页</span>
                </li>
            </ul>
            <div style="clear:both;"></div>
        </div>
        <a id="task-next" class="task-changebt">→</a>
    </div>

    <div class="page-content" id="content">
        <iframe src="<zop:webinfo type="ctx"/>admin/main.action" style="width:100%;height: 100%;" frameborder="0" id="appiframe-0" class="appiframe"></iframe>
    </div>
</div>
</div>


<script>

</script>
<script src="<zop:webinfo type="js"/>index.js"></script>
</body>
</html>