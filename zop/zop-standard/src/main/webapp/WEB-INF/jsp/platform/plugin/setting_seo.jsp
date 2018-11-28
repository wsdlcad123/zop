<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-tags.tld" prefix="s"%>
<%@taglib uri="/WEB-INF/zop.tld" prefix="zop"%>
<table cellpadding="2" cellspacing="2" class="table_form"
       width="100%">
    <tbody>
    <tr>
        <td width="180">SEO标题:</td>
        <td><input type="text" class="input" name="site_seo_title" value="${pluginData.site_seo_title}"></td>
    </tr>
    <tr>
        <td>SEO关键字:</td>
        <td><input type="text" class="input" name="site_seo_keywords" value="${pluginData.site_seo_keywords}"></td>
    </tr>
    <tr>
        <td>SEO描述:</td>
        <td><textarea name="site_seo_description" rows="5" cols="57">${pluginData.site_seo_description}</textarea></td>
    </tr>
    </tbody>
</table>
