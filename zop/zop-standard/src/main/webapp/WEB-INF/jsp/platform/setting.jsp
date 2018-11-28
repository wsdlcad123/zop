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
        <s:iterator id="tab" value="%{pluginTab}">
        <li class="${tab.active}"><a href="#${tab.id}" data-toggle="tab">${tab.name}</a></li>
        </s:iterator>
    </ul>
    <div class="common-form">
    <form method="post" class="form-horizontal J_ajaxForm" action="<zop:webinfo type="ctx"/>admin/setting.action">
        <input type="hidden" name="action" value="${action}">
        <fieldset>
            <div class="tabbable">
                <div class="tab-content">
                    <s:iterator id="tab" value="%{pluginTab}">
                    <div class="tab-pane ${tab.active}" id="${tab.id}">
                        <s:include value="${tab.url}" />
                    </div>
                    </s:iterator>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary btn_submit  J_ajax_submit_btn">提交</button>
            </div>
        </fieldset>
    </form>
</div>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>