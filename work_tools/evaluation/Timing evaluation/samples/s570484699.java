import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        List<Integer> lst = new LinkedList<Integer>();
        while(input.hasNext()){
        	int a = input.nextInt();
        	if(a == 0){
        		System.out.println(lst.get(lst.size()-1));
        		lst.remove(lst.size()-1);
        	}else{
        		lst.add(a);
        	}
        }
    }    
}