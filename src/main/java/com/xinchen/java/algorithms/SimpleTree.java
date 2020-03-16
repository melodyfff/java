package com.xinchen.java.algorithms;


/**
 *
 * 树
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/3/16 21:47
 */
public class SimpleTree<T> {


    private static class TreeNode<T>{
        T data;
        TreeNode<T> leftNode;
        TreeNode<T> rightNode;

        public TreeNode(T data, TreeNode<T> leftNode, TreeNode<T> rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
    }

}
