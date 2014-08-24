package ro.j.cache;

class DoubleLinkedList<K, T> {
    private DoubleLinkedCacheValue<K, T> head, tail;

    void addFirst(DoubleLinkedCacheValue<K, T> node) {
        node.clean();
        DoubleLinkedCacheValue<K, T> first = head;
        head = node;
        if (first == null) {
            tail = node;
        } else {
            node.next = first;
            first.prev = node;
        }
    }

    void remove(DoubleLinkedCacheValue<K, T> node) {
        if (node.isRemoved()) {
            return;
        }
        DoubleLinkedCacheValue<K, T> next = node.next;
        DoubleLinkedCacheValue<K, T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        node.removed();

    }

    void moveFirst(DoubleLinkedCacheValue<K, T> node) {
        if (node.prev == null) {
            return;
        }
        if (node.isRemoved()) {
            return;
        }
        remove(node);
        addFirst(node);
    }

    DoubleLinkedCacheValue<K, T> removeLast() {
        if (tail == null) {
            return null;
        }
        DoubleLinkedCacheValue<K, T> node = tail;
        DoubleLinkedCacheValue<K, T> last = tail.prev;
        tail = last;
        if (last == null)
            head = null;
        else
            last.next = null;
        node.removed();
        return node;
    }

//    void swap(DoubleLinkedCacheValue<K, T> node1, DoubleLinkedCacheValue<K, T> node2) {
//        if(node1 == node2) {
//            return;
//        }
//        DoubleLinkedCacheValue<K, T> head1 = node1.prev;
//        DoubleLinkedCacheValue<K, T> tail1 = node1.next;
//        
//        DoubleLinkedCacheValue<K, T> head2 = node2.prev;
//        DoubleLinkedCacheValue<K, T> tail2 = node2.next;
//        
//        if(head1 == node2) {//head2 -> node2 -> node1 -> tail1
//            swapWithPrev(node1);
//        }
//        else if(head2 == node1) {//head1 -> node1 -> node2 -> tail2
//            swapWithPrev(node2);
//        }
//        else {//head1 -> node1 -> tail1 / head2 -> node2 -> tail2
//              //head1 -> node2 -> tail1 / head2 -> node1 -> tail2
//            insertAfter(head1, node2);
//            insertBefore(tail1, node2);
//            
//            insertAfter(head2, node1);
//            insertBefore(tail2, node1);
//
//        }
//    }

    private void insertAfter(DoubleLinkedCacheValue<K, T> first, DoubleLinkedCacheValue<K, T> node) {
        if(first != null) {
            first.next = node;
        } else {
            this.head = node;
        }
        node.prev = first;
    }
    private void insertBefore(DoubleLinkedCacheValue<K, T> last, DoubleLinkedCacheValue<K, T> node) {
        if(last != null) {
            last.prev = node;
        } else {
            this.tail = node;
        }
        node.next = last;
    }
//    private void swapWithPrev(DoubleLinkedCacheValue<K, T> node)
//    {
//        DoubleLinkedCacheValue<K, T> prev = node.prev;
//        DoubleLinkedCacheValue<K, T> head = prev.prev;
//        DoubleLinkedCacheValue<K, T> tail = node.next;
//
//        insertAfter(head, node);
//        insertBefore(tail, prev);
//        
//        node.next = prev;
//        prev.prev = node;
//    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (DoubleLinkedCacheValue<K, T> node = head; node != null; node = node.next) {
            builder.append(node + " -> ");
        }
        return builder.toString();
    }

    public void modeAfter(DoubleLinkedCacheValue<K, T> node, DoubleLinkedCacheValue<K, T> first) {
        if(node == first.next) {
            return;
        }
        remove(node);
        DoubleLinkedCacheValue<K, T> last = first.next;
        insertAfter(first, node);
        insertBefore(last, node);
    }
}
