import java.util.*;

public class BinaryTreesLab5 {

    static class Node {
        int znach;
        Node lev, prav;
        Node(int v){znach=v;}
    }

    static void preOrder(Node uzel){
        if(uzel==null) return;
        System.out.print(uzel.znach+" ");
        preOrder(uzel.lev);
        preOrder(uzel.prav);
    }

    static void inOrder(Node uzel){
        if(uzel==null) return;
        inOrder(uzel.lev);
        System.out.print(uzel.znach+" ");
        inOrder(uzel.prav);
    }

    static void postOrder(Node uzel){
        if(uzel==null) return;
        postOrder(uzel.lev);
        postOrder(uzel.prav);
        System.out.print(uzel.znach+" ");
    }

    static void levelOrder(Node koren){
        if(koren==null) return;
        Queue<Node> q = new ArrayDeque<>();
        q.add(koren);
        while(!q.isEmpty()){
            Node cur = q.poll();
            System.out.print(cur.znach+" ");
            if(cur.lev!=null) q.add(cur.lev);
            if(cur.prav!=null) q.add(cur.prav);
        }
    }

    static int height(Node uzel){
        if(uzel==null) return 0;
        return 1 + Math.max(height(uzel.lev), height(uzel.prav));
    }

    static boolean isFull(Node uzel){
        if(uzel==null) return true;
        if((uzel.lev==null) ^ (uzel.prav==null)) return false;
        return isFull(uzel.lev) && isFull(uzel.prav);
    }

    static Node buildFullTreeMinHeight3(){
        Node a1=new Node(1), a2=new Node(2), a3=new Node(3), a4=new Node(4),
             a5=new Node(5), a6=new Node(6), a7=new Node(7);
        a1.lev=a2; a1.prav=a3;
        a2.lev=a4; a2.prav=a5;
        a3.lev=a6; a3.prav=a7;
        return a1;
    }

    static Node insertBST(Node koren,int val){
        if(koren==null) return new Node(val);
        if(val<koren.znach) koren.lev = insertBST(koren.lev,val);
        else koren.prav = insertBST(koren.prav,val);
        return koren;
    }

    static Node buildBSTFromSequence(int[] seq){
        Node koren=null;
        for(int v:seq) koren = insertBST(koren,v);
        return koren;
    }

    static Node buildBalancedFromSorted(int[] a){
        return buildBalancedRec(a,0,a.length-1);
    }

    static Node buildBalancedRec(int[] a,int l,int r){
        if(l>r) return null;
        int m=(l+r)/2;
        Node uzel=new Node(a[m]);
        uzel.lev = buildBalancedRec(a,l,m-1);
        uzel.prav = buildBalancedRec(a,m+1,r);
        return uzel;
    }

    static class LevelOrderTree {
        Node koren;

        void insert(int val){
            Node n=new Node(val);
            if(koren==null){ koren=n; return; }
            Queue<Node> q=new ArrayDeque<>();
            q.add(koren);
            while(!q.isEmpty()){
                Node cur=q.poll();
                if(cur.lev==null){ cur.lev=n; return; } else q.add(cur.lev);
                if(cur.prav==null){ cur.prav=n; return; } else q.add(cur.prav);
            }
        }

        boolean delete(int key){
            if(koren==null) return false;
            if(koren.lev==null && koren.prav==null){
                if(koren.znach==key){ koren=null; return true; }
                return false;
            }
            Queue<Node> q=new ArrayDeque<>();
            Map<Node,Node> parent=new IdentityHashMap<>();
            q.add(koren); parent.put(koren,null);
            Node target=null, last=null;
            while(!q.isEmpty()){
                Node cur=q.poll();
                if(cur.znach==key) target=cur;
                last=cur;
                if(cur.lev!=null){ parent.put(cur.lev,cur); q.add(cur.lev); }
                if(cur.prav!=null){ parent.put(cur.prav,cur); q.add(cur.prav); }
            }
            if(target==null) return false;
            Node lastParent = parent.get(last);
            target.znach = last.znach;
            if(lastParent==null) koren=null;
            else {
                if(lastParent.lev==last) lastParent.lev=null;
                else if(lastParent.prav==last) lastParent.prav=null;
            }
            return true;
        }
    }

    public static void main(String[] args){
        Node full = buildFullTreeMinHeight3();
        System.out.print("preOrder: "); preOrder(full); System.out.println();
        System.out.print("inOrder: "); inOrder(full); System.out.println();
        System.out.print("postOrder: "); postOrder(full); System.out.println();
        System.out.print("levelOrder: "); levelOrder(full); System.out.println();
        System.out.println("height: "+height(full));
        System.out.println("isFull: "+isFull(full));

        int[] seq = {8,3,10,1,6,14,4,7,13};
        Node bst = buildBSTFromSequence(seq);
        System.out.print("BST inOrder: "); inOrder(bst); System.out.println();
        System.out.print("BST levelOrder: "); levelOrder(bst); System.out.println();
        System.out.println("BST height: "+height(bst));

        int[] sorted = {1,2,3,4,5,6,7,8,9};
        Node bal = buildBalancedFromSorted(sorted);
        System.out.print("balanced levelOrder: "); levelOrder(bal); System.out.println();
        System.out.println("balanced height: "+height(bal));
        System.out.println("balanced isFull: "+isFull(bal));

        LevelOrderTree lot = new LevelOrderTree();
        int[] toInsert = {1,2,3,4,5,6,7,8};
        for(int v:toInsert) lot.insert(v);
        System.out.print("levelOrder after inserts: "); levelOrder(lot.koren); System.out.println();
        System.out.println("delete 3: "+lot.delete(3));
        System.out.print("levelOrder after delete: "); levelOrder(lot.koren); System.out.println();
        System.out.println("delete 100: "+lot.delete(100));
    }
}
