import java.util.*;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] t_max = new int[n];
        ArrayList<PriorityQueue<Integer>> p_que = new ArrayList<>();
        for(int i = 0;i < n;i++){
            p_que.add(new PriorityQueue<>(Collections.reverseOrder()));
        }

        int q = sc.nextInt();
        for(int i = 0;i < q;i++){
            int num = sc.nextInt();
            int t = sc.nextInt();
            switch(num){
                case 0:
                    int x = sc.nextInt();
                    p_que.get(t).add(x);
                    break;
                case 1:
                    if(p_que.get(t).size() > 0)
                        System.out.println(p_que.get(t).peek());
                    break;
                case 2:
                    if(p_que.get(t).size() > 0)
                        p_que.get(t).poll();
                    break;
            }
        }
    }
}

class MyComparator implements Comparator{

    public int compare(Object obj1,Object obj2){
        int num1 = (int)obj1;
        int num2 = (int)obj2;

        if(num1 < num2) return 1;
        else if(num1 > num2)    return -1;
        else return 0;
    }
}
