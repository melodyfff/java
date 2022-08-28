package com.xinchen.java.algorithms.formaluetree;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/28 22:08
 */
public class Node {
    private String data;
    private Node leftNode;
    private Node rightNode;

    public Node(String data) {
        this.data = data;
    }

    public Node(String data, Node leftNode, Node rightNode) {
        this.data = data;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public String getData() {
        return data;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }
}
