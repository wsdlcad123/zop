<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<table cellpadding="2" cellspacing="2" class="table_form" width="100%">
    <tbody>
    <tr>
        <td width="180">网站名称：</td>
        <td><input type="text" class="input" name="site_name" value="${pluginData.site_name}"><span class="must_red">*</span>
        </td>
    </tr>
    <tr>
        <td>网站域名：</td>
        <td><input type="text" class="input" name="site_host" value="${pluginData.site_host}"><span class="must_red">*</span></td>
    </tr>
    <tr>
        <td>备案信息：</td>
        <td><input type="text" class="input" name="site_icp" value="${pluginData.site_icp}"></td>
    </tr>
    <tr>
        <td>站长邮箱：</td>
        <td><input type="text" class="input" name="site_admin_email" value="${pluginData.site_admin_email}"></td>
    </tr>

    <tr>
        <td>统计代码：</td>
        <td><textarea name="site_tongji" rows="5" cols="57">${pluginData.site_tongji}</textarea></td>
    </tr>
    <tr>
        <td>版权信息：</td>
        <td><textarea name="site_copyright" rows="5" cols="57">${pluginData.site_copyright}</textarea></td>
    </tr>
    </tbody>
</table>
