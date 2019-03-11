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
                if (temp.getParentId().equals(new Long("-1"))) {
                    result.addAll(getChild(temp.getId(), rootMenu));
                }
            }
        }
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
}
