package com.zop.platform.component.base.service.impl;



import com.zop.platform.component.base.bean.Menu;
import com.zop.platform.component.base.dao.IMenuDAO;
import com.zop.platform.component.base.service.IMenuService;

import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements IMenuService {
    private IMenuDAO menuDAO;

    public void setMenuDAO(IMenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    /**
     * 保存
     */
    public void save(Menu menu){
        int id = this.menuDAO.save(menu);
        menu.setId(id);
        //判断是否是顶级类似别，如果parentd为0则为顶级类似别
        if (menu.getParent_id() != 0) { //不是顶级类别，有父类
            Menu parent = this.menuDAO.get(menu.getParent_id());
            menu.setDepth(parent.getDepth()+1);
        }else{
            menu.setDepth(1);
        }
        this.menuDAO.update(menu);
    }

    /**
     * 更新
     */
    public void update(Menu menu){
        //判断是否是顶级类似别，如果parentd为0则为顶级类似别
        if (menu.getParent_id() != 0) { //不是顶级类别，有父类
            Menu parent = this.menuDAO.get(menu.getParent_id());
            menu.setDepth(parent.getDepth()+1);
        }else{
            menu.setDepth(1);
        }
        this.menuDAO.update(menu);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id){
        this.menuDAO.delete(id);
    }

    /**
     * 个数
     * @param parent_id
     * @return
     */
    public int countByPid(int parent_id){
        return this.menuDAO.countByPid(parent_id);
    }

    /**
     * 获取
     */
    public Menu get(int id){
        return this.menuDAO.get(id);
    }

    /**
     * 列表
     */
    public List list(String orderby, String ascOrDesc){
        return this.menuDAO.list(orderby,ascOrDesc);
    }

    /**
     * 读取菜单并形成Tree格式
     */
    public List listTreeByPid(int parent_id,String orderby, String ascOrDesc){
        List menuList = this.list(orderby, ascOrDesc);
        List topMenuList = new ArrayList();
        for(int i=0;i<menuList.size();i++){
            Menu menu = (Menu) menuList.get(i);
            if (menu.getParent_id()==parent_id) {
                List children = this.getChildren(menuList, menu.getId());
                menu.setChildren(children);
                topMenuList.add(menu);
            }
        }
        return topMenuList;
    }

    /*
 * 在一个集合中查找子集合
 */
    private List getChildren(List menuList,int parent_id) {
        List children = new ArrayList();
        for(int i=0;i<menuList.size();i++){
            Menu menu = (Menu) menuList.get(i);
            if (menu.getParent_id()==parent_id) {
                menu.setChildren(this.getChildren(menuList, menu.getId()));
                children.add(menu);
            }
        }
        return children;
    }
}