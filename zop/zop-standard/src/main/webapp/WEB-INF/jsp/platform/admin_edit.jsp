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
        <li><a href="<zop:webinfo type="ctx"/>admin/admin.action">所有管理员</a></li>
        <li><a href="<zop:webinfo type="ctx"/>admin/admin.action?action=add">添加管理员</a></li>
        <li class="active"><a href="#">更新管理员</a></li>
    </ul>
    <div class="common-form">
        <form method="post" class="form-horizontal J_ajaxForm" action="<zop:webinfo type="ctx"/>admin/admin.action">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="admin.id" value="${admin.id}">
            <div class="table_list">
                <table cellpadding="2" cellspacing="2" width="100%">
                    <tbody>
                    <tr>
                        <td width="180">用户名:</td>
                        <td><input type="text" class="input" name="admin.username" value="${admin.username}"><span class="must_red">*</span></td>
                    </tr>
                    <tr>
                        <td width="180">密码:</td>
                        <td><input type="text" class="input" name="admin.password" value="${admin.password}"></td>
                    </tr>
                    <tr>
                        <td>状态:</td>
                        <td><select name="admin.status" class="normal_select">
                            <option value="0" <s:if test="%{admin.status==0}">selected</s:if>>待审核</option>
                            <option value="1" <s:if test="%{admin.status==1}">selected</s:if>>己通过</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>角色:</td>
                        <td><select name="admin.role_id" class="normal_select">
                            <s:property value="roleOption" escape="false" />
                        </select></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary btn_submit  J_ajax_submit_btn">更新</button>
                <a class="btn" href="<zop:webinfo type="ctx"/>admin/admin.action">返回</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>