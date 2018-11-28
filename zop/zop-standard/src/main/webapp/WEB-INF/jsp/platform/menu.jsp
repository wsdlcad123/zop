<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统后台</title>
<s:include value="../header.jsp" />
</head>

<body class="J_scroll_fixed">
<div class="wrap J_check_wrap">
    <ul class="nav nav-tabs">
        <li class="active"><a href="<zop:webinfo type="ctx"/>admin/menu.action">所有菜单</a></li>
        <li><a href="<zop:webinfo type="ctx"/>admin/menu.action?action=add">添加菜单</a></li>
    </ul>
        <div class="table_list">
            <table width="100%" class="table table-hover">
                <thead>
                <tr>
                    <th width="50">ID</th>
                    <th width="200">组件</th>
                    <th>访问地址</th>
                    <th>菜单名称</th>
                    <th width="80">状态</th>
                    <th width="200">管理操作</th>
                </tr>
                </thead>
                <s:property value="menuTree" escape="false" />
            </table>
        </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>