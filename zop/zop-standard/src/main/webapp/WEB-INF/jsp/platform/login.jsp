<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统后台</title>
<meta http-equiv="X-UA-Compatible" content="chrome=1,IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="robots" content="noindex,nofollow">
<link rel="stylesheet" href="<zop:webinfo type="css"/>login.css" />
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="ctx"/>admin/global.action?action=gv"></script>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>wind.js"></script>
</head>
<body>
<div class="wrap">
    <h1><a>后台管理中心</a></h1>
    <form method="post" name="login" action="<zop:webinfo type="ctx"/>admin/login.action?action=login" autoComplete="off" class="J_ajaxForm">
        <div class="login">
            <ul>
                <li>
                    <input class="input" id="J_username" type="text" required name="username" placeholder="请输入用户名" title="用户名" value="admin"/>
                </li>
                <li>
                    <input class="input" id="J_password" type="password" required name="password" placeholder="请输入密码" title="密码" />
                </li>
                <li>
                    <div id="J_verify_code">
                        <img class="yanzheng_img" id="code_img" alt="" src="<zop:webinfo type="ctx"/>authimg?width=240&height=50"/>
                    </div>
                </li>
                <li>
                    <input class="input" type="text" name="verifycode" placeholder="请输入验证码" title="验证码" />
                </li>
            </ul>
            <div id="login_btn_wraper">
                <button class="btn btn_submit J_ajax_submit_btn">登录</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" charset="utf-8" src="<zop:webinfo type="js"/>common.js"></script>
</body>
</html>