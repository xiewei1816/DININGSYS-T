package com.yqsh.diningsys.web.controller.base;

import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysMenu;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-07-20 17:02
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController{

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request){
        return new ModelAndView("system/menuManager/menu_index");
    }

    @RequestMapping("/showMenuAddPage")
    public ModelAndView toMenuAddPage(Integer id) {
        ModelAndView modelAndView = new ModelAndView("system/menuManager/menu_add");
        SysMenu sysMenu = sysMenuService.getMenuById(id);
        Integer order = sysMenuService.selectNextOrderByMenuId(id);
        order = order == null ? 1 : order+1;
        modelAndView.addObject("sysMenu",sysMenu);
        modelAndView.addObject("order",order);
        return modelAndView;
    }

    @RequestMapping("/showMenuEditPage")
    public ModelAndView toMenuEditPage(Integer id) {
        ModelAndView modelAndView = new ModelAndView("system/menuManager/menu_update");
        List<SysMenu> allParentSysMenu = sysMenuService.selectAllMenuWithOutSelf(id);
        SysMenu sysMenu = sysMenuService.getMenuById(id);
        modelAndView.addObject("SysMenu",sysMenu);
        modelAndView.addObject("allParentSysMenu",allParentSysMenu);
        if(sysMenu.getMenuType().equals(1)){
            modelAndView.addObject("type","菜单");
        }else modelAndView.addObject("type","按钮");

        return modelAndView;
    }

    @RequestMapping("/getMenuData")
    @ResponseBody
    public String getMenuData(){
        try {
            List<SysMenu> list = sysMenuService.getAllMenus();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("menuData",list);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/addMenu")
    @ResponseBody
    public ResultInfo addMenu(SysMenu sysMenu){
        try {
            int res = sysMenuService.addMenu(sysMenu);
            if (res == 1){
                return returnSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnFail(e.toString());
        }
        return returnFail();
    }

    @RequestMapping("/editMenu")
    @ResponseBody
    public ResultInfo editMenu(SysMenu sysMenu){
        try {
            int res = sysMenuService.editMenu(sysMenu);
            if (res == 1){
                return returnSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnFail(e.toString());
        }
        return returnFail();
    }

    @RequestMapping("/delMenu")
    @ResponseBody
    public ResultInfo delMenu(Integer id){
        try {
            List<SysRoleMenu> sysRoleMenus = sysMenuService.selectRoleByMenuId(id);
            if(sysRoleMenus.size() > 0){
                return returnFail("删除失败，该菜单有角色关联，请先取消关联。");
            }
            sysMenuService.delMenu(id);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

    @RequestMapping("/modifyMenuState")
    @ResponseBody
    public ResultInfo modifyMenuState(Integer id, String state){
        try {
            int res = sysMenuService.modifyMenuState(id,state);
            if (res == 1){
                return returnSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnFail(e.toString());
        }
        return returnFail();
    }

}
