package com.bdxh.common.helper.tree.utils;

import com.bdxh.common.helper.tree.bean.TreeBean;

import java.util.*;

/**
 * @Description: 遍历，循环帮助类
 * @Author: Kang
 * @Date: 2019/3/1 17:41
 */
public class TreeLoopUtils<E extends TreeBean> {


    /**
     * @Description: 获取树结构
     * @Author: Kang
     * @Date: 2019/3/11 12:11
     */
    public List<E> getTree(List<E> rootMenu) {
        List<E> result = new ArrayList<>();
        for (E temp : rootMenu) {
            if (LongUtils.isNotEmpty(temp.getParentId())) {
                if (temp.getParentId().equals(new Long("-1")) || temp.getParentId().toString().equals("-1")) {
                    temp.setChildren(getChild(temp.getId(), rootMenu));
                    result.add(temp);
                }
            }
        }
        //按照正序排列
        Collections.sort(result, (o1, o2) -> {
            if (o1.getSort() > o2.getSort()) {
                return 1;
            }
            if (o1.getSort() == o2.getSort()) {
                if (o1.getCreateDate().getTime() > o2.getCreateDate().getTime()) {
                    return 1;
                } else if (o1.getCreateDate().getTime() < o2.getCreateDate().getTime()) {
                    return -1;
                } else {
                    return 0;
                }
            }
            return -1;
        });
        return result;
    }

    /**
     * @Description: 递归查找子节点
     * @Author: Kang
     * @Date: 2019/3/1 18:06
     */
    public List<E> getChild(Long id, List<E> rootMenu) {
        // 子节点
        List<E> childList = new ArrayList<>();
        for (E temp : rootMenu) {
            // 遍历所有节点，将父节点id与传过来的id比较
            if (LongUtils.isNotEmpty(temp.getParentId())) {
                if (temp.getParentId().equals(id)) {
                    childList.add(temp);
                }
            }
        }
        //按照正序排列
        Collections.sort(childList, (o1, o2) -> {
            if (o1.getSort() > o2.getSort()) {
                return 1;
            }
            if (o1.getSort() == o2.getSort()) {
                if (o1.getCreateDate().getTime() > o2.getCreateDate().getTime()) {
                    return 1;
                } else if (o1.getCreateDate().getTime() < o2.getCreateDate().getTime()) {
                    return -1;
                } else {
                    return 0;
                }
            }
            return -1;
        });
        rootMenu.remove(childList);
        // 把子节点的子节点再循环一遍
        for (E temp1 : childList) {
            if (LongUtils.isNotEmpty(temp1.getId())) {
                // 递归
                temp1.setChildren(getChild(temp1.getId(), rootMenu));
            }
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return new ArrayList<E>();
        }
        return childList;
    }


    /**
     * @Description: 获取父节点
     * @Author: Kang
     * @Date: 2019/3/22 17:08
     */
    public List<E> getParent(Long parentId, List<E> rootMenu) {
        List<E> parentList = new ArrayList<>();
        // 节点信息
        for (E temp : rootMenu) {
            // 遍历所有节点，将节点id与传过来的父id比较
            if (LongUtils.isNotEmpty(temp.getId())) {
                if (temp.getId().equals(parentId)) {
                    parentList.add(temp);
                    break;
                }
            }
        }
        rootMenu.remove(parentList);
        // 把父节点的父节点再循环一遍
        for (E temp1 : parentList) {
            if (LongUtils.isNotEmpty(temp1.getId())) {
                // 递归
                temp1.setChildren(getParent(temp1.getParentId(), rootMenu));
            }
        }
        return parentList;
    }
}
