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
        <li><a href="<zop:webinfo type="ctx"/>admin/menu.action">后台菜单</a></li>
        <li><a href="<zop:webinfo type="ctx"/>admin/menu.action?action=add">添加菜单</a></li>
        <li class="active"><a href="#">更新菜单</a></li>
    </ul>
    <div class="common-form">
        <form method="post" class="form-horizontal J_ajaxForm" action="<zop:webinfo type="ctx"/>admin/menu.action">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="menu.id" value="${menu.id}">
            <div class="table_list">
                <table cellpadding="2" cellspacing="2" width="100%">
                    <tbody>
                    <tr>
                        <td width="180">上级:</td>
                        <td><select name="menu.parent_id" class="normal_select">
                            <option value="0">作为一级菜单</option>
                            <s:property value="menuTree" escape="false" />
                        </select></td>
                    </tr>
                    <tr>
                        <td>组件视图:</td>
                        <td><input type="text" class="input" name="menu.component_view_id" value="${menu.component_view_id}"><span class="must_red">*</span></td>
                    </tr>
                    <tr>
                        <td>名称:</td>
                        <td><input type="text" class="input" name="menu.name" value="${menu.name}"><span class="must_red">*</span></td>
                    </tr>
                    <tr>
                        <td>访问地址:</td>
                        <td><input type="text" class="input" name="menu.url" id="url" value="${menu.url}"></td>
                    </tr>
                    <tr>
                        <td>排序:</td>
                        <td><input type="text" class="input" name="menu.orders" value="${menu.orders}"></td>
                    </tr>
                    <tr>
                        <td>图标:</td>
                        <td><input type="text" class="input" name="menu.icon" id="icon" value="${menu.icon}"></td>
                    </tr>
                    <tr>
                        <td>状态:</td>
                        <td><select name="menu.status" class="normal_select">
                            <option value="1"  <s:if test="%{menu.status==1}">selected</s:if>>显示</option>
                            <option value="0"  <s:if test="%{menu.status==0}">selected</s:if>>隐藏</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>类型:</td>
                        <td><select name="menu.type" class="normal_select">
                            <option value="1" <s:if test="%{menu.type==1}">selected</s:if>>权限认证+菜单</option>
                            <option value="0" <s:if test="%{menu.type==0}">selected</s:if>>只作为菜单</option>
                        </select>
                            注意：“权限认证+菜单”表示加入后台权限管理，纯碎是菜单项请不要选择此项。</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary btn_submit  J_ajax_submit_btn">更新</button>
                <a class="btn" href="<zop:webinfo type="ctx"/>admin/menu.action">返回</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>