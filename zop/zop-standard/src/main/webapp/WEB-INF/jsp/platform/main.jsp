<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统后台</title>
<s:include value="../header.jsp" />
    <style>
        .home_info li em {
            float: left;
            width: 100px;
            font-style: normal;
        }
        li {
            list-style: none;
        }
    </style>
</head>

<body>
<div class="wrap">
    <div id="home_toptip"></div>
    <h4 class="well">系统信息</h4>
    <div class="home_info">
        <ul>
            <li> <em>key</em> <span>value</span> </li>
        </ul>
    </div>
    <h4 class="well">发起团队</h4>
    <div class="home_info" id="home_devteam">
        <ul>
            <li> <em>ZOP团队</em> <span>guokr.xicp.net</span> </li>
            <li> <em>团队成员</em> <span>张宝超</span> </li>
        </ul>
    </div>
</div>
</body>
</html>