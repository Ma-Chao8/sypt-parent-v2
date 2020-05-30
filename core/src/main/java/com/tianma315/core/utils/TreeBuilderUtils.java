package com.tianma315.core.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * list转tree结构工具类
 * <p>
 * Created by zcm on 2019/6/13.
 */
public final class TreeBuilderUtils {

    private TreeBuilderUtils() {

    }


    /**
     * list结构数据转换成tree数据结构
     *
     * @param treeNodes
     * @return
     */
    public static <E extends TreeNode> List<E> buildTree(List<? extends E> treeNodes) {
        if (treeNodes == null)
            return null;
        //
        List<E> trees = new ArrayList<>();
        for (TreeNode treeNode : treeNodes) {
            if (treeNode.isRoot()) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode
     * @param treeNodes
     * @return
     */
    private static <E extends TreeNode> E findChildren(TreeNode treeNode, List<? extends TreeNode> treeNodes) {
        for (TreeNode it : treeNodes) {
            Serializable id = treeNode.getId();
            Serializable parentId = it.getParentId();
            if (id.equals(parentId)) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return (E) treeNode;
    }

    public interface TreeNode {
        /**
         * 判断当前节点是否为根节点
         *
         * @return
         */
        boolean isRoot();

        /**
         * 父节点唯一标识
         *
         * @return
         */
        Serializable getParentId();

        /**
         * 子节点唯一标识 须和父节点存在父子关系，否则无法做区分
         *
         * @return
         */
        Serializable getId();

        /**
         * 获取当前节点下的子节点
         *
         * @return
         */
        List<? extends TreeNode> getChildren();

        /**
         * 设置当前节点下的子节点
         *
         * @param children
         */
        void setChildren(List<? extends TreeNode> children);
    }
}
