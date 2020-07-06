package com.xinchen.java.algorithms;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 遍历列表移除不满足条件的节点
 * <p>
 * 如：  -2 -> 1 -> -2 -> 3 移除不等于-2的节点
 * <p>
 * {@link AbstractQueuedSynchronizer.ConditionObject#unlinkCancelledWaiters()}
 *
 * @author xinchen
 * @version 1.0
 * @date 06/07/2020 16:36
 */
public class LinkedListConditionRemove {

    private transient Node firstWaiter;
    private transient Node lastWaiter;

    static class Node {
        static final int CONDITION = -2;
        volatile int waitStatus;
        volatile Node nextWaiter;

        Node(int waitStatus) {
            this.waitStatus = waitStatus;
        }
    }

    private static void removeCondition(LinkedListConditionRemove nodes){
        Node t = nodes.firstWaiter;
        // 用于保存最后一个符合条件的节点
        Node travel = null;
        while (null != t){
            Node next = t.nextWaiter;
            if (t.waitStatus != Node.CONDITION){
                // 去除该节点引用
                t.nextWaiter = null;
                if (null ==travel){
                    // travel为null说明头节点开始就没匹配到
                    nodes.firstWaiter = next;
                } else {
                    // 挂载link到一次符合条件的节点
                    travel.nextWaiter = next;
                }
                if (null == next){
                    // 如果已经是最后一个节点，将设置尾节点为最后一个有效节点
                    nodes.lastWaiter = travel;
                }
            } else {
                // 满足条件，保存该节点
                travel = t;
            }
            t = next;
        }
    }

    private static LinkedListConditionRemove fakeData() {
        // 构建节点： -2 -> 1 -> -2 -> 3
        final LinkedListConditionRemove data = new LinkedListConditionRemove();
        data.firstWaiter = new Node(Node.CONDITION);
        data.firstWaiter.nextWaiter = new Node(1);
        data.firstWaiter.nextWaiter.nextWaiter = new Node(Node.CONDITION);
        data.firstWaiter.nextWaiter.nextWaiter.nextWaiter = new Node(3);
        data.lastWaiter = data.firstWaiter.nextWaiter.nextWaiter.nextWaiter;
        return data;
    }

    private static LinkedListConditionRemove fakeData2() {
        // 构建节点： 1 -> -2 -> 2 -> -2
        final LinkedListConditionRemove data = new LinkedListConditionRemove();
        data.firstWaiter = new Node(1);
        data.firstWaiter.nextWaiter = new Node(Node.CONDITION);
        data.firstWaiter.nextWaiter.nextWaiter = new Node(2);
        data.firstWaiter.nextWaiter.nextWaiter.nextWaiter = new Node(Node.CONDITION);
        data.lastWaiter = data.firstWaiter.nextWaiter.nextWaiter.nextWaiter;
        return data;
    }

    private static void display(Node node){
        while (null != node){
            System.out.print(node.waitStatus + (null == node.nextWaiter ?"":" -> "));
            node = node.nextWaiter;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        final LinkedListConditionRemove fakeData1 = LinkedListConditionRemove.fakeData();
        display(fakeData1.firstWaiter);
        removeCondition(fakeData1);
        display(fakeData1.firstWaiter);
        //-2 -> 1 -> -2 -> 3
        //-2 -> -2

        final LinkedListConditionRemove fakeData2 = LinkedListConditionRemove.fakeData2();
        display(fakeData2.firstWaiter);
        removeCondition(fakeData2);
        display(fakeData2.firstWaiter);
        //1 -> -2 -> 2 -> -2
        //-2 -> -2
    }

}
