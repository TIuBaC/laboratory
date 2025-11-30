public class LinkedListsDemoRus {

    public static void main(String[] args) {
        // Демонстрация работы списков
        SinglyLinkedList odn = new SinglyLinkedList();
        odn.addLast(3); odn.addFirst(1); odn.addLast(4); odn.addLast(1); odn.display();
        System.out.println("contains 4: " + odn.contains(4));
        System.out.println("removeFirst: " + odn.removeFirst());
        System.out.println("remove(1): " + odn.remove(1));
        odn.display();
        System.out.println("size: " + odn.size());
        odn.clear();
        System.out.println("empty: " + odn.isEmpty());

        DoublyLinkedList<String> dvo = new DoublyLinkedList<>();
        dvo.addLast("a"); dvo.addLast("b"); dvo.addFirst("z"); dvo.add(1, "x");
        dvo.display();
        System.out.println("get(2): " + dvo.get(2));
        System.out.println("remove(1): " + dvo.remove(1));
        dvo.displayReverse();
        System.out.println("getFirst: " + dvo.getFirst() + " getLast: " + dvo.getLast());

        CircularLinkedList krug = new CircularLinkedList();
        for (int i = 1; i <= 7; i++) krug.addLast(i);
        krug.display();
        krug.rotate();
        System.out.println("after rotate:");
        krug.display();
        System.out.println("findCycle: " + krug.findCycle());
        System.out.println("find 5 -> index: " + krug.find(5));
        CircularLinkedList[] chasti = krug.splitIntoTwo();
        System.out.println("part1:");
        chasti[0].display();
        System.out.println("part2:");
        chasti[1].display();
    }

    //Односвязный список (int) 
    static class SinglyLinkedList {
        // Узел односвязного списка
        private static class Node { int znach; Node sled; Node(int v){znach=v;} }
        private Node golova;
        private int razmer = 0;

        public void addFirst(int data) {
            Node n = new Node(data);
            n.sled = golova;
            golova = n;
            razmer++;
        }

        public void addLast(int data) {
            Node n = new Node(data);
            if (golova == null) { golova = n; }
            else {
                Node tek = golova;
                while (tek.sled != null) tek = tek.sled;
                tek.sled = n;
            }
            razmer++;
        }

        public Integer removeFirst() {
            if (golova == null) return null;
            int v = golova.znach;
            golova = golova.sled;
            razmer--;
            return v;
        }

        public Integer removeLast() {
            if (golova == null) return null;
            if (golova.sled == null) {
                int v = golova.znach;
                golova = null;
                razmer = 0;
                return v;
            }
            Node tek = golova;
            while (tek.sled.sled != null) tek = tek.sled;
            int v = tek.sled.znach;
            tek.sled = null;
            razmer--;
            return v;
        }

        public boolean remove(int data) {
            if (golova == null) return false;
            if (golova.znach == data) { golova = golova.sled; razmer--; return true; }
            Node tek = golova;
            while (tek.sled != null && tek.sled.znach != data) tek = tek.sled;
            if (tek.sled == null) return false;
            tek.sled = tek.sled.sled;
            razmer--;
            return true;
        }

        public boolean contains(int data) {
            Node tek = golova;
            while (tek != null) { if (tek.znach == data) return true; tek = tek.sled; }
            return false;
        }

        public int size() { return razmer; }
        public boolean isEmpty() { return razmer == 0; }

        public void display() {
            Node tek = golova;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (tek != null) {
                sb.append(tek.znach);
                if (tek.sled != null) sb.append(", ");
                tek = tek.sled;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public void clear() { golova = null; razmer = 0; }
    }

    //Двусвязный список (generic) 
    static class DoublyLinkedList<T> {
        static class Node<T> { T znach; Node<T> pred, sled; Node(T v){znach=v;} }
        private Node<T> golova, hvost;
        private int razmer = 0;

        public void addFirst(T data) {
            Node<T> n = new Node<>(data);
            if (golova == null) { golova = hvost = n; }
            else {
                n.sled = golova;
                golova.pred = n;
                golova = n;
            }
            razmer++;
        }

        public void addLast(T data) {
            Node<T> n = new Node<>(data);
            if (hvost == null) { golova = hvost = n; }
            else {
                hvost.sled = n;
                n.pred = hvost;
                hvost = n;
            }
            razmer++;
        }

        public T removeFirst() {
            if (golova == null) return null;
            T v = golova.znach;
            golova = golova.sled;
            if (golova != null) golova.pred = null; else hvost = null;
            razmer--;
            return v;
        }

        public T removeLast() {
            if (hvost == null) return null;
            T v = hvost.znach;
            hvost = hvost.pred;
            if (hvost != null) hvost.sled = null; else golova = null;
            razmer--;
            return v;
        }

        public boolean remove(T data) {
            Node<T> tek = golova;
            while (tek != null && (tek.znach == null ? data != null : !tek.znach.equals(data))) tek = tek.sled;
            if (tek == null) return false;
            if (tek.pred != null) tek.pred.sled = tek.sled; else golova = tek.sled;
            if (tek.sled != null) tek.sled.pred = tek.pred; else hvost = tek.pred;
            razmer--;
            return true;
        }

        public boolean contains(T data) {
            Node<T> tek = golova;
            while (tek != null) {
                if (tek.znach == null ? data == null : tek.znach.equals(data)) return true;
                tek = tek.sled;
            }
            return false;
        }

        public int size() { return razmer; }
        public boolean isEmpty() { return razmer == 0; }

        public void display() {
            Node<T> tek = golova;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (tek != null) {
                sb.append(tek.znach);
                if (tek.sled != null) sb.append(", ");
                tek = tek.sled;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public void clear() { golova = hvost = null; razmer = 0; }

        public void add(int index, T data) {
            if (index < 0 || index > razmer) throw new IndexOutOfBoundsException();
            if (index == 0) { addFirst(data); return; }
            if (index == razmer) { addLast(data); return; }
            Node<T> tek = golova;
            for (int i = 0; i < index; i++) tek = tek.sled;
            Node<T> n = new Node<>(data);
            n.pred = tek.pred;
            n.sled = tek;
            tek.pred.sled = n;
            tek.pred = n;
            razmer++;
        }

        public T remove(int index) {
            if (index < 0 || index >= razmer) return null;
            if (index == 0) return removeFirst();
            if (index == razmer - 1) return removeLast();
            Node<T> tek = golova;
            for (int i = 0; i < index; i++) tek = tek.sled;
            T v = tek.znach;
            tek.pred.sled = tek.sled;
            tek.sled.pred = tek.pred;
            razmer--;
            return v;
        }

        public T get(int index) {
            if (index < 0 || index >= razmer) return null;
            Node<T> tek;
            if (index < razmer / 2) {
                tek = golova;
                for (int i = 0; i < index; i++) tek = tek.sled;
            } else {
                tek = hvost;
                for (int i = razmer - 1; i > index; i--) tek = tek.pred;
            }
            return tek.znach;
        }

        public void displayReverse() {
            Node<T> tek = hvost;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (tek != null) {
                sb.append(tek.znach);
                if (tek.pred != null) sb.append(", ");
                tek = tek.pred;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public T getFirst() { return golova == null ? null : golova.znach; }
        public T getLast() { return hvost == null ? null : hvost.znach; }
    }

    // Циклический односвязный список (int)
    static class CircularLinkedList {
        private static class Node { int znach; Node sled; Node(int v){znach=v;} }
        private Node hvost;
        private int razmer = 0;

        public void addFirst(int data) {
            Node n = new Node(data);
            if (hvost == null) { hvost = n; hvost.sled = hvost; }
            else {
                n.sled = hvost.sled;
                hvost.sled = n;
            }
            razmer++;
        }

        public void addLast(int data) {
            addFirst(data);
            hvost = hvost.sled;
        }

        public Integer removeFirst() {
            if (hvost == null) return null;
            Node golova = hvost.sled;
            int v = golova.znach;
            if (golova == hvost) { hvost = null; razmer = 0; return v; }
            hvost.sled = golova.sled;
            razmer--;
            return v;
        }

        public Integer removeLast() {
            if (hvost == null) return null;
            if (hvost.sled == hvost) { int v = hvost.znach; hvost = null; razmer = 0; return v; }
            Node tek = hvost.sled;
            while (tek.sled != hvost) tek = tek.sled;
            int v = hvost.znach;
            tek.sled = hvost.sled;
            hvost = tek;
            razmer--;
            return v;
        }

        public boolean remove(int data) {
            if (hvost == null) return false;
            Node pred = hvost;
            Node tek = hvost.sled;
            for (int i = 0; i < razmer; i++) {
                if (tek.znach == data) {
                    if (tek == hvost.sled) { removeFirst(); }
                    else if (tek == hvost) { removeLast(); }
                    else { pred.sled = tek.sled; razmer--; }
                    return true;
                }
                pred = tek; tek = tek.sled;
            }
            return false;
        }

        public boolean contains(int data) {
            if (hvost == null) return false;
            Node tek = hvost.sled;
            for (int i = 0; i < razmer; i++) {
                if (tek.znach == data) return true;
                tek = tek.sled;
            }
            return false;
        }

        public int size() { return razmer; }
        public boolean isEmpty() { return razmer == 0; }

        public void display() {
            if (hvost == null) { System.out.println("[]"); return; }
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Node tek = hvost.sled;
            for (int i = 0; i < razmer; i++) {
                sb.append(tek.znach);
                if (i != razmer - 1) sb.append(", ");
                tek = tek.sled;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public void clear() { hvost = null; razmer = 0; }

        public void rotate() {
            if (hvost != null) hvost = hvost.sled;
        }

        public boolean findCycle() {
            return true;
        }

        public int find(int data) {
            if (hvost == null) return -1;
            Node tek = hvost.sled;
            for (int i = 0; i < razmer; i++) {
                if (tek.znach == data) return i;
                tek = tek.sled;
            }
            return -1;
        }

        public CircularLinkedList[] splitIntoTwo() {
            CircularLinkedList a = new CircularLinkedList();
            CircularLinkedList b = new CircularLinkedList();
            if (hvost == null) return new CircularLinkedList[]{a, b};
            int mid = razmer / 2;
            Node tek = hvost.sled;
            for (int i = 0; i < mid; i++) { a.addLast(tek.znach); tek = tek.sled; }
            for (int i = mid; i < razmer; i++) { b.addLast(tek.znach); tek = tek.sled; }
            return new CircularLinkedList[]{a, b};
        }
    }
}
