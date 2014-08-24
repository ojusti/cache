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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (DoubleLinkedCacheValue<K, T> node = head; node != null; node = node.next) {
            builder.append(node + " -> ");
        }
        return builder.toString();
    }
}
