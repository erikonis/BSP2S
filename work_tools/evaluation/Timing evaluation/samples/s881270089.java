import java.io.*;
import java.util.*;
 
class Main{
    public static void main(String[] args){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            String input_str;
            while((input_str = br.readLine()) != null){
                String[] input_strs = input_str.split(" ");
 
                // まずノードを生成
                Node node1 = new Node(input_strs[1]);
                Node node2 = new Node(input_strs[2]);
                Node result_node = new Node();
 
                // intersection
                if(input_strs[0].equals("i")){
                    result_node.intersection(node1, node2);
                }
 
                // union
                if(input_strs[0].equals("u")){
                    result_node.union(node1, node2);
                }
 
                System.out.println(result_node.toString());
            }
        }catch(Exception e){
            System.out.println("Error");
        }
    }
}
 
class Node{
    Node left;
    Node right;
 
    Node(){
        left = null;
        right = null;
    }
 
    Node(String str){
        int lvl = 0;
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(ch == '(') lvl++;
            if(ch == ')') lvl--;
            if(ch == ',' && lvl == 1){
                // 左辺
                String left_str = str.substring(1, i);
                if(left_str.length() == 0){
                    left = null;
                }else{
                    left = new Node(left_str);
                }
 
                // 右辺
                String right_str = str.substring(i+1, str.length()-1);
                if(right_str.length() == 0){
                    right = null;
                }else{
                    right = new Node(right_str);
                }
            }
        }
    }
 
    public String toString(){
        String result = "(";
        if(left != null) result += left.toString();
        result += ",";
        if(right != null) result += right.toString();
        result += ")";
        return result;
    }
 
    public Node intersection(Node node1, Node node2){
        // left
        if(node1.left != null && node2.left != null){
            left = new Node();
            left.intersection(node1.left, node2.left);
        }
        // right
        if(node1.right != null && node2.right != null){
            // どちらも null でない
            right = new Node();
            right.intersection(node1.right, node2.right);
        }
        return this;
    }
 
    public Node union(Node node1, Node node2){
        // left
        if(node1.left == null){
            left = node2.left;
        }else if(node2.left == null){
            left = node1.left;
        }else{
            left = new Node();
            left.union(node1.left, node2.left);
        }
        // right
        if(node1.right == null){
            right = node2.right;
        }else if(node2.right == null){
            right = node1.right;
        }else{
            right = new Node();
            right.union(node1.right, node2.right);
        }
        return this;
    }
}