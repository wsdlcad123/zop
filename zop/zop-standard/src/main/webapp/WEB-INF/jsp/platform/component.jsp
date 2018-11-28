<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统后台</title>
<s:include value="../header.jsp" />
<script type="text/javascript">
    $(document).ready(function(){
        $('[title]').colorTip({color:'white'});
    });
</script>
</head>

<body class="J_scroll_fixed">
<div class="wrap J_check_wrap">
    <ul class="nav nav-tabs">
        <li class="active"><a href="<zop:webinfo type="ctx"/>admin/component.action">所有组件</a></li>
    </ul>
        <div class="table_list">
            <table width="100%" class="table table-hover">
                <thead>
                <tr>
                    <th width="200">组件视图ID</th>
                    <th>组件名称</th>
                    <th width="50">版本</th>
                    <th width="150">作者</th>
                    <th width="80">安装状态</th>
                    <th width="80">启用状态</th>
                    <th width="100">管理操作</th>
                </tr>
                </thead>
                <tbody>
                <s:iterator id="c" value="%{componentList}" status="st" >
                    <tr>
                        <td><s:property value="#c.id"/></td>
                        <td>
                            <a title="
                            <s:iterator id="plugin" value="#c.pluginList">
                            <li><s:property value="#plugin.name"/></li>
                            </s:iterator>
                            <s:iterator id="widget" value="#c.widgetList">
                            <li><s:property value="#widget.name"/></li>
                            </s:iterator>
                            "><s:property value="#c.name"/></a>
                        </td>
                        <td><s:property value="#c.version"/></td>
                        <td><s:property value="#c.author"/></td>
                        <td>
                            <s:if test="#c.install_status==0">未安装</s:if>
                            <s:if test="#c.install_status==1">已安装</s:if>
                        </td>
                        <td>
                            <s:if test="#c.enable_status==0">已停用</s:if>
                            <s:if test="#c.enable_status==1">已启用</s:if>
                        </td>
                        <td>
                            <s:if test="#c.install_status==0">
                                <a class="J_ajax_refresh" href="<zop:webinfo type="ctx"/>admin/component.action?action=install&view_id=<s:property value="#c.id"/>">安装</a>
                            </s:if>
                            <s:if test="#c.install_status==1">
                                <s:if test="#c.enable_status==0">
                                    <a class="J_ajax_refresh" href="<zop:webinfo type="ctx"/>admin/component.action?action=enable&view_id=<s:property value="#c.id"/>">启用</a>
                                    <a class="J_ajax_refresh" href="<zop:webinfo type="ctx"/>admin/component.action?action=un_install&view_id=<s:property value="#c.id"/>">卸载</a>
                                </s:if>
                                <s:if test="#c.enable_status==1">
                                    <a class="J_ajax_refresh" href="<zop:webinfo type="ctx"/>admin/component.action?action=disable&view_id=<s:property value="#c.id"/>">停用</a>
                                </s:if>
                            </s:if>

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