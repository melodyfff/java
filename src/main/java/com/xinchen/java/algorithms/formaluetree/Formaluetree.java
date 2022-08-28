package com.xinchen.java.algorithms.formaluetree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 利用Java实现表达式二叉树:  https://blog.csdn.net/maling000_3/article/details/76096110
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/28 22:14
 */
public class Formaluetree {
    private String tmp = "";
    private Node root;

    public Formaluetree createTree(String str) {
        // 存放操作符 +-*/
        List<String> operatorList = new ArrayList<>();
        // 存放数据*/
        List<Node> dataList = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            // 读取字符
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                tmp += ch;
            } else {
                dataList.add(new Node(tmp));
                tmp = "";
                operatorList.add(ch + "");
            }
        }
        // 最后一位构建节点
        dataList.add(new Node(tmp));

        // 构建树
        while (operatorList.size() > 0) {
            Node left = dataList.remove(0);
            Node right = dataList.remove(0);
            String operator = operatorList.remove(0);

            Node node = new Node(operator, left, right);
            // 将最新节点头插为首位
            dataList.add(0, node);
        }
        root = dataList.get(0);
        return this;
    }

    public void printPre() {
        printPreTree(root);
    }
    public void printIn() {
        printInTree(root);
    }
    private void printPreTree(Node node) {
        // 前序遍历
        if (null != node.getLeftNode()) {
            printPreTree(node.getLeftNode());
        }
        System.out.println(node.getData());
        if (null != node.getRightNode()) {
            printPreTree(node.getRightNode());
        }
    }

    private void printInTree(Node node) {
        // 中序遍历
        System.out.println(node.getData());
        if (null != node.getLeftNode()) {
            printInTree(node.getLeftNode());
        }
        if (null != node.getRightNode()) {
            printInTree(node.getRightNode());
        }
    }

    public void beautyPrint(){
        TreePrinter.show(root);
    }
    public int depth(){
        return TreePrinter.getTreeDepth(root);
    }



    public static void main(String[] args) {
        final Formaluetree formaluetree = new Formaluetree();
        formaluetree
                .createTree("1|(2&3)|4&5")
                .printIn();
//        formaluetree
//                .createTree("1|(2&3)|4&5")
//                .printPre();

        formaluetree
                .createTree("1|(2&3)|4&5")
                .beautyPrint();

        System.out.println(formaluetree
                .createTree("1|(2|3)&4&5")
                .depth());

    }
}
