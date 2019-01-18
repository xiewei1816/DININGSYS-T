package com.yqsh.diningsys.core.util;

import com.yqsh.diningsys.web.model.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree解析工具类
 */
public class TreeUtil {

    private List<SysMenu> treeNodeList = new ArrayList<SysMenu>();

    public TreeUtil(List<SysMenu> list) {
        treeNodeList = list;
    }

    /**
     * @param nodeId
     * @return
     */
    public SysMenu getNodeById(int nodeId) {
        SysMenu sysMenu = new SysMenu();
        for (SysMenu item : treeNodeList) {
            if (item.getId() == nodeId) {
                sysMenu = item;
                break;
            }
        }
        return sysMenu;
    }

    /**
     * @param nodeId
     * @return
     */
    public List<SysMenu> getChildrenNodeById(int nodeId) {
        List<SysMenu> childrenSysMenu = new ArrayList<SysMenu>();
        for (SysMenu item : treeNodeList) {
            if (item.getParentId() == nodeId) {
                childrenSysMenu.add(item);
            }
        }
        return childrenSysMenu;
    }

    /**
     * 递归生成Tree结构数据
     *
     * @param rootId
     * @return
     */
    public SysMenu generateTreeNode(int rootId) {
        SysMenu root = this.getNodeById(rootId);
        List<SysMenu> childrenTreeNode = this.getChildrenNodeById(rootId);
        for (SysMenu item : childrenTreeNode) {
            SysMenu sysMenu = this.generateTreeNode(item.getId());
            root.getChild().add(sysMenu);
        }
        return root;
    }
}
