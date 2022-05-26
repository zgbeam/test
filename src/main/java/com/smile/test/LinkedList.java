package com.smile.test;

/**
 * @author smile
 */
public class LinkedList {
    public static void main(String[] args) {
    }

    public ListNode insertionSortList(ListNode head) {
        ListNode newHead = new ListNode(Integer.MIN_VALUE);
        ListNode current = head;

        while (current != null) {
            ListNode tmpPrev = newHead, tmp = newHead.next;
            while (tmp != null && tmp.val <= current.val) {
                tmpPrev = tmp;
                tmp = tmp.next;
            }

            ListNode next = current.next;
            tmpPrev.next = current;
            current.next = tmp;
            current = next;
        }

        return newHead.next;
    }

    public static ListNode mergeSort(ListNode head) {
        return mergeSort(head, null);
    }

    public static ListNode mergeSort(ListNode head, ListNode tail) {
        if (head == tail || head.next == tail) {
            head.next = null;
            return head;
        }

        ListNode fast = head, slow = head;
        while (fast != tail && fast.next != tail) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode first = mergeSort(head, slow);
        ListNode second = mergeSort(slow, tail);
        return merge(first, second);
    }

    public static ListNode merge(ListNode first, ListNode second) {
        ListNode head = new ListNode(), tail = head;
        while (first != null && second != null) {
            if (first.val < second.val) {
                tail.next = first;
                first = first.next;
            } else {
                tail.next = second;
                second = second.next;
            }
            tail = tail.next;
        }

        while (first != null) {
            tail.next = first;
            first = first.next;
            tail = tail.next;
        }

        while (second != null) {
            tail.next = second;
            second = second.next;
            tail = tail.next;
        }
        tail.next = null;

        return head.next;
    }

    private static class ListNode {
        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        private int val;
        private ListNode next;
    }
}
