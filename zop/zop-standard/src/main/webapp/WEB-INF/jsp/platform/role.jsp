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
        <li class="active"><a href="<zop:webinfo type="ctx"/>admin/role.action">所有角色</a></li>
        <li><a href="<zop:webinfo type="ctx"/>admin/role.action?action=add">添加角色</a></li>
    </ul>
        <div class="table_list">
            <table width="100%" class="table table-hover">
                <thead>
                <tr>
                    <th width="50">ID</th>
                    <th>名称</th>
                    <th width="200">管理操作</th>
                </tr>
                </thead>
                <tbody>
                <s:iterator id="r" value="%{roleList}" status="st" >
                <tr>
                    <td><s:property value="#r.id"/></td>
                    <td><s:property value="#r.name"/></td>
                    <td>
                        <a href="<zop:webinfo type="ctx"/>admin/role.action?action=edit&id=<s:property value="#r.id"/>">修改</a>
                        <a class="J_ajax_del" href="<zop:webinfo type="ctx"/>admin/role.action?action=delete&id=<s:property value="#r.id"/>">删除</a>
                    </td>
                </tr>
                </s:iterator>
                </tbody>
            </table>
        </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>