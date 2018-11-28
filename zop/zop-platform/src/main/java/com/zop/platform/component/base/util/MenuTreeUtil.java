package com.zop.platform.component.base.util;

import com.zop.platform.component.base.bean.Menu;
import com.zop.utils.util.ReflectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangBaochao on 14-9-28.
 */
public class MenuTreeUtil {
    private List icon = Arrays.asList("│", "├", "└");

    private List menuTreeList;
    private String template;

    public MenuTreeUtil(List menuTreeList,String template){
        this.menuTreeList = menuTreeList;
        this.template = template;
    }

    public void setIcon(List icon) {
        this.icon = icon;
    }

    public void setMenuTreeList(List menuTreeList) {
        this.menuTreeList = menuTreeList;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public  String getTree(int sid){
        StringBuffer sb = new StringBuffer();
        sb.append(getChildrenTree(this.menuTreeList,sid));
        return sb.toString();
    }

    private String getChildrenTree(List childrenList,int sid){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<childrenList.size();i++){
            Menu m = (Menu) childrenList.get(i);
            Map mm = ReflectionUtil.po2AllMap(m);
            mm.put("spacer",getSpacer(m.getDepth(),i==(childrenList.size()-1)));
            mm.put("selected",m.getId() == sid?"selected":"");
            mm.put("status",m.getStatus()==1?"显示":"隐藏");
            sb.append(formatTemplate(mm));
            if(m.getHasChildren()){
                sb.append(getChildrenTree(m.getChildren(),sid));
            }
        }
        return sb.toString();
    }

    private String getSpacer(int depth,boolean end){
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<depth;i++){
            if(i==(depth-1)){
                sb.append(end?icon.get(2):icon.get(1));
            }else{
                sb.append(icon.get(0));
            }
        }
        return sb.toString();
    }

    private String formatTemplate(Map<String,Object> mm){
        String s = this.template;
        for (String key : mm.keySet()) {
            s = s.replaceAll("\\{"+key+"\\}",mm.get(key).toString());
        }
        return s;
    }

    public static void main(String[] args){

    }

}
