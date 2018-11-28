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
    <!--
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onCheck
        }
    };

    function onCheck(e, treeId, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getCheckedNodes(true);
        var ids = "";
        for(var i=0;i<nodes.length;i++)
        {
            ids = ids+nodes[i].id +"," ;
        }
        if(ids.length>0){
            ids = ids.substring(0,ids.length-1);
        }
        $("#menu_value").val(ids);
    }

    var zNodes =[<s:property value="menuTree" escape="false" />];
    $(document).ready(function(){
        $.fn.zTree.init($("#tree"), setting, zNodes);
    });
    //-->
</script>
</head>

<body class="J_scroll_fixed">
<div class="wrap jj">
    <ul class="nav nav-tabs">
        <li><a href="<zop:webinfo type="ctx"/>admin/auth.action">所有权限</a></li>
        <li class="active"><a href="<zop:webinfo type="ctx"/>admin/auth.action?action=add">添加权限</a></li>
    </ul>
    <div class="common-form">
        <form method="post" class="form-horizontal J_ajaxForm" action="<zop:webinfo type="ctx"/>admin/auth.action">
            <input type="hidden" name="action" value="${action}">
            <input type="hidden" name="auth.id" value="${auth.id}">
            <input type="hidden" id="menu_value" name="auth.menu_value" value="${auth.menu_value}">
            <div class="table_list">
                <table cellpadding="2" cellspacing="2" width="100%">
                    <tbody>
                    <tr>
                        <td width="180">名称:</td>
                        <td><input type="text" class="input" name="auth.name" value="${auth.name}"><span class="must_red">*</span></td>
                    </tr>
                    <tr>
                        <td>权限菜单:</td>
                        <td>
                            <div>
                                <ul id="tree" class="ztree"></ul>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary btn_submit  J_ajax_submit_btn">添加</button>
                <a class="btn" href="<zop:webinfo type="ctx"/>admin/auth.action">返回</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>