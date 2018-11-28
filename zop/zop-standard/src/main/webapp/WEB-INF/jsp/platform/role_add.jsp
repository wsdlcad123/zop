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
<div class="wrap jj">
    <ul class="nav nav-tabs">
        <li><a href="<zop:webinfo type="ctx"/>admin/role.action">所有角色</a></li>
        <li class="active"><a href="<zop:webinfo type="ctx"/>admin/role.action?action=add">添加角色</a></li>
    </ul>
    <div class="common-form">
        <form method="post" class="form-horizontal J_ajaxForm" action="<zop:webinfo type="ctx"/>admin/role.action">
            <input type="hidden" name="action" value="${action}">
            <div class="table_list">
                <table cellpadding="2" cellspacing="2" width="100%">
                    <tbody>
                    <tr>
                        <td width="180">名称:</td>
                        <td><input type="text" class="input" name="role.name" value="${role.name}"><span class="must_red">*</span></td>
                    </tr>
                    <tr>
                        <td>权限:</td>
                        <td>
                            <s:property value="authCheckbox" escape="false" />
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary btn_submit  J_ajax_submit_btn">添加</button>
                <a class="btn" href="<zop:webinfo type="ctx"/>admin/role.action">返回</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>