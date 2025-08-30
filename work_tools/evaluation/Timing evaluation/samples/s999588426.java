

//Pradeep Ghosh 2017076;Lab5-Q-3;
 
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
import java.io.BufferedReader;
import java.util.Comparator; 
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.awt.*;
 
public class Main
{    
    public static long[] array = new long[100001];
 
    // public static HashMap<long, long> map = new HashMap<>();
    
    public static int swap1(int[][] student, int[][] professor, int[] student_list, int[] professor_list,int curr_prof,int stud, int prof, int n, int flag){
 
                        int j3=1;
        
                        // int flag=0;
        
                        while(j3<n+1){
        
                            if(student[stud][j3]==prof){
        
                                flag=1;
        
                                break;
        
                            }
        
                            else if(student[stud][j3]==curr_prof){
        
                                break;
        
                            }
        
                            j3++;
        
                        }
                        return flag;
 
    }        
    
    public static void swap3(int[] student_list, int[] professor_list, int stud, int prof, int curr_prof){
     
        student_list[stud]=prof; professor_list[curr_prof]=0; professor_list[prof]=1;
    
    }
 
    public static void swap5(int[] student_list, int[] professor_list, int[][] student, int[][] professor, int n, int total){
 
        int num=0;
            
        while(num!=total){
        
                int prof = 0;
        
                int j1=1;
        
                prof=swap2(j1,professor_list,prof,n);
        
                int j2=1;
                
                ArrayList<Integer> list = new ArrayList<Integer>();
 
                while(j2<n+1){
        
                    int stud = professor[prof][j2];
        
                    if(student_list[stud]!=0){
        
                        int curr_prof = student_list[stud];
                        
                        int flag=0;
        
                        flag=swap1(student,professor,student_list,professor_list,curr_prof,stud,prof,n,flag);
 
 
                        if(flag!=0){
        
                            swap3(student_list,professor_list,stud,prof,curr_prof);
 
                            break;
        
                        }
        
                    }
        
                    else{
                        
                        total = swap4(professor_list,student_list,prof,stud,total);
        
                        break;
        
                    }
        
                    j2++;
        
                }
        
            }
        
    }
 
    public static int max_sum(int[][] array,int i, int j,int n,boolean[][] array2){
        
        int sum=array[i][j];
        if(i>n || j>n){
            // int sum = 0;
            return 0;
        }else if(array2[i][j]==true){
            // System.out.println(i+" "+j);
            return sum;
        }else{
            array[i][j]=Math.max(max_sum(array,i+1,j,n,array2),max_sum(array,i,j+1,n,array2));
            array2[i][j]=true;
            return array[i][j];
        }
        
    }
 
    public static void update(int[] a1, int[] a2 , int n){
        
        for(int i=1;i<n+1;i++){
        
            a1[a2[i]]=i;
        
        }
    }
 
    public static long checking(long j){
        if(j==1){
            return 0;
        }
        else{
            if(j%2!=0){
                return 1+checking(3*j+1);
            }else{
                return 1+checking(j/2);
            }
        }
    }
 
    public static long horse_path(long n, long m, long start1, long start2){
        if(n==start1 && start2==m){
            return 0;
        }else if(start1>n || start2>m){
            return -1;
        }else{
            long a,b;
            if(horse_path(n,m,start1+2,start2+1)==-1){
                a = 0+horse_path(n,m,start1+2,start2+1);
            }else{
                a=1+horse_path(n,m,start1+2,start2+1);
            }
            if(horse_path(n,m,start1+1,start2+2)==-1){
                b = 0+horse_path(n,m,start1+1,start2+2);
            }else{
                b=1+horse_path(n,m,start1+1,start2+2);
            }
            return min_val(a,b);
        }
    }
 
    public static long looping(long[] array, int n, int i, long maximum, long value1, long value2){
        while(i<n){
            long temp=value2+array[i];
            maximum =  max(temp,value1);
            value2=store(array,n,value1);
            value1=store(array,n,maximum);
            i++;
        }
        return maximum;
    }
 
    public static void Result(long value){
        
        System.out.println(value);
    
    }
 
    public static long store(long[] array,int n,long a){
        return a;
    }
 
    public static long storing(long n){
        long count = 0;
        while(n!=1){
            if(n%2==0){
                count++;
                n=n/2;
            }else{
                count++;
                n=3*n+1;
            }
        }
        return count;        
    }
 
    public static long min_val(long a, long b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }
 
    public static void merge1(long a[],long temp[], long perm[], int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
 
        for (int k = lo; k <= hi; k++) {
            temp[k] = perm[k];
        }
 
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                perm[k] = temp[j++];
            } else if (j > hi) {
                perm[k] = temp[i++];
            } else if (compare(a[(int)perm[i]], a[(int)perm[j]])) {
                perm[k] = temp[i++];
            } else {
                perm[k] = temp[j++];
            }
        }
    }
 
    public static void mergeSort(long a[], long temp[], long perm[], int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = (hi + lo) / 2;
        mergeSort(a, temp, perm, lo, mid);
        mergeSort(a, temp, perm, mid+1, hi-1);
        merge1(a, temp, perm, lo, mid, hi-1);
    }
 
    public static void indexSort(long nums[], long perm[]) {
        long temp[] = new long[nums.length+1];
        long temp2[] = new long[nums.length+1];
        mergeSort(nums, temp, perm, 1, nums.length);
    }
    
    static HashSet<Integer> set = new HashSet<Integer>();
    static boolean[] known;
    static ArrayList<Integer> jaadu = new ArrayList<Integer>();
    static int[] child;
 
    public static void main(String[] args) throws Exception{
        // OutputStream outputStream = System.out;
        // PrintWriter out = new PrintWriter(outputStream);
        // InputStreamReader r = new InputStreamReader(System.in);
        // BufferedReader br = new BufferedReader(r);
        Scanner sc= new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n+1];
        for(int i=1;i<n+1;i++){
        	array[i]=sc.nextInt();
        }
        if(n%2!=0){
        	System.out.println(0);
        }else{	
        	Arrays.sort(array);	
        	System.out.println(-array[n/2]+array[n/2+1]);
        }
    }
 
    public static void dfs(ArrayList<Point>[] array,int current){
        known[current]=true;
        child[current]=-1;
        ArrayList<Point> list = new ArrayList<Point>();
        for(int i=0;i<array[current].size();i++){
            if(!known[array[current].get(i).x]){
                list.add(array[current].get(i));
                dfs(array,array[current].get(i).x);
            }
        }
        for(int i=0;i<list.size();i++){
            if(list.get(i).y!=1 && child[list.get(i).x]!=-1){
                child[current]=child[list.get(i).x];
            }else if(list.get(i).y==1){
                if(child[list.get(i).x]!=-1){
                    child[current]=child[list.get(i).x];
                    if(!set.contains(child[current])){
                        jaadu.add(child[current]);
                        set.add(child[current]);
                    }    
                    
                }else{
                    child[current]=list.get(i).x;
                    if(!set.contains(child[current])){
                        jaadu.add(child[current]);
                        set.add(child[current]);
                    }
                }
            }
        }
    }
 
    public static boolean isTidy(BigInteger num){ 
        // To store previous digit  
        // (Assigning initial value  
            // which is more than any 
        // digit) 
        BigInteger prev = new BigInteger("10"); 
        BigInteger ten = new BigInteger("10");
        BigInteger zero = new BigInteger("0");
        // Traverse all digits from right to 
        // left and check if any digit is 
        // smaller than previous. 
        while (num.compareTo(zero)==1) 
        { 
            BigInteger rem = num.mod(ten); 
            num = num.divide(ten); 
            if (rem.compareTo(prev)==1) 
               return false; 
            prev = rem; 
        } 
       
        return true; 
    } 
 
    // public static long starpower(int[][] dp,int l,int r,int[] array){
    //     int sum=0;
    //     for(int i=l;i<r;i++){
    //         sum+=
    //     }
    // }
    public static long countNonDecreasing(int n) 
    { 
        int N = 10;  
        long count = 1; 
           
        for (int i = 1; i <= n; i++) 
        { 
            count *= (N + i - 1); 
            count /= i; 
        } 
       
        return count; 
    }
 
    public static int[] bfs(ArrayList<Integer>[] array,int a,int n){
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(a);
        int[] level = new int[n+1];
        for(int i=0;i<n+1;i++){
            if(i!=a){
                level[i]=-2;
            }
        }
        boolean[] known = new boolean[n+1];
        while(!queue.isEmpty()){
            int lol = queue.poll();
            known[lol]=true;
            int c=0;
            while(c<array[lol].size()){
                if(!known[array[lol].get(c)]){
                    known[array[lol].get(c)]=true;
                    level[array[lol].get(c)]=level[lol]+1;
                    queue.add(array[lol].get(c));
                }
                c++;
            }
        }
        return level;
    }
 
    public static int digitsum(int a){
        int sum=0;
        while(a!=0){
            sum+=a%10;
            a=a/10;
        }
        return sum;
    }
 
    public static long countSubarray(long arr[], 
                             long n, long k) 
    { 
        long start = 0, end = 0; 
        long count = 0, sum = arr[0]; 
  
        while (start < n && end < n) { 
  
            if (sum < k) { 
                end++; 
  
                if (end >= start) 
                    count += end - start; 
 
                if (end < n) 
                    sum += arr[(int)end]; 
            } 
            else { 
                sum -= arr[(int)start]; 
                start++; 
            } 
        } 
  
        return count; 
    } 
 
    public static long dp(long n,long m){
        if(m==n){
            return 0;
        }else if(n>m){
            return 100000000;
        }else{
            return 1+Math.min(dp(n*2,m),dp(n*3,m));
        }
    }
 
    public static BigInteger chefIngredients(BigInteger n,BigInteger k,BigInteger two,BigInteger zero,BigInteger one){
        BigInteger last = two.multiply(k).subtract(k.add(n));
        if(last.compareTo(one)>=0){
            BigInteger d = n.subtract(one);
            BigInteger n1 = last.add(d.divide(two));
            n1 = n1.subtract(n1.mod(d));
            if(n1.compareTo(last)==-1){
                n1= last.add(d.divide(two));
            }
            if(n1.mod(d).compareTo(zero)==0){
                n1=n1.divide(d);
            }else{
                n1=n1.divide(d).add(one);
            }
            BigInteger a = last.subtract((d.multiply(n1.subtract(one))));
            BigInteger ans = (n1.multiply(a.add(last))).divide(two);
            return ans.add((k.subtract(one)));
        }else{
            return k.subtract(one);   
        }
    }
 
    public static BigInteger power(BigInteger n,BigInteger a,BigInteger prime,BigInteger zero,BigInteger one,BigInteger two){
        BigInteger temp = new BigInteger("0");
        if(a.compareTo(zero)==0){
            return one;
        }
        temp = power(n,a.divide(two),prime,zero,one,two).mod(prime);
        if(a.mod(two).compareTo(zero)==0){
            return (temp.multiply(temp)).mod(prime);
        }else{
            return (n.multiply((temp.multiply(temp)).mod(prime))).mod(prime);
        }
    }
 
    public static BigInteger digsum1(BigInteger n,BigInteger ten,BigInteger zero){
        BigInteger b = new BigInteger("0");
        while(n.compareTo(zero)!=0){
            b=b.add(n.mod(ten));
            n=n.divide(ten);
        }
        return b;
    }
 
    public static long divide(long n){
        long c=0;
        while(n>1){
            if(n%5==0){
                n=4*n/5;
                c++;
            }else if(n%3==0){
                n=2*n/3;
                c++;
            }else if(n%2==0){
                n=n/2;
                c++;
            }else{
                break;
            }
        }
        if(n==1){
            return c;
        }else{
            return -1;
        }
    }
 
    public static void apush(long[] array,HashSet<Long> set){
        Iterator it = set.iterator();
        int p=0;
        while(it.hasNext()){
            array[p]=(long)it.next();
            p++;
        }
    }
 
    public static void squarenum(HashSet<Long> set,long val){
        long i1=0;
        while(i1<=(long)Math.sqrt(val)){
            long i2=0;
            while(i2<=(long)Math.sqrt(val)){
                if(!set.contains(i1*i1+i2*i2)){
                    set.add(i1*i1+i2*i2);
                }
 
                i2++;
            }
            i1++;
        }
    }
 
    public static long vague1(long k,long m,long m1,long n,long count){
        for(k=m;k<m1+1;k++){
            if(k>n){
                break;
            }
            if(k-digsum(k)>m-1){
            count++;
            }
        }
        return count;
    }
 
    public static long vague2(long k,long m,long m1,long n,long count){
        for(k=m;k<m1+1;k++){
            if(k>n){
                break;
            }
            if(k-digsum(k)>m-1){
                count++;
            }
        }
        return k;
    }
 
    public static void mover(PriorityQueue<people> list,int m){
        while(m!=0){
            people mon = list.peek();
            list.remove(mon);
            mon.money=mon.money+1;
            list.add(mon);
            m--;
        }
    }
 
    public static void stor(people[] array,PriorityQueue<people> list,int n){
        for(int j=0;j<n;j++){
            people p = list.peek();
            array[j]=p;
            list.remove(p);
        }
    }
 
    public static void printing(people[] array,int n){
        for(int jj=0;jj<n;jj++){
            System.out.print(array[jj].money+" ");
        }
        System.out.println();
    }
 
    public static void count2(int n,ArrayList<Integer> list, ArrayList<Integer> list1){
        int val = (int)Math.sqrt(n);
        int val1=n;
        int count=0;
        int j=0;
        while(list.get(j)<val+1){
            if(count>2){
                count++;
                break;
            }else{
                if(val1%list.get(j)==0){
                    count++;
                    list1.add(list.get(j));
                    while(val1%list.get(j)==0){
                        val1=val1/list.get(j);
                    }
                }
            }
            j++;
        }
        if(val1!=1){
            count++;
            list1.add(val1);
        }
    }
 
    public static int count1(int n,ArrayList<Integer> list){
        int val = (int)Math.sqrt(n);
        int val1=n;
        int count=0;
        int j=0;
        while(list.get(j)<val+1){
            if(count>2){
                count++;
                break;
            }else{
                if(val1%list.get(j)==0){
                    count++;
                    while(val1%list.get(j)==0){
                        val1=val1/list.get(j);
                    }
                }
            }
            j++;
        }
        if(val1!=1){
            count++;
        }
        return count;
    }
 
    public static double energy2(int[] array,double x){
        double left=0;
        double right=1000;
        double result=0;
        int i1=0;
        while(i1<100){
            double mid = (left+right)/2;
            if(energy1(array,mid,x)==-1){
                right=mid;
            }else{
                left=mid;
                result=mid;
            }
            i1++;
        }
        return result;
    }
 
    public static int energy1(int[] array, double d1, double d2){
        double result=0;
        int i1=0;
        while(i1<array.length){
            if(d1<array[i1]){
                result=result+(array[i1]-d1);
            }
            i1++;
        }
 
        result = result - (result*d2)/100;
        int i2=0;
        while(i2<array.length){
            if(d1>array[i2]){
                result=result-(d1-array[i2]);
            }
            i2++;
        }
 
        if(result>=0){
            return 1;
        }else{
            return -1;
        }
    }
 
    public static long newbin(long left,long right,long m){
        if(right==left){
            return right;
        }
        long mid = left+(right-left)/2;
        long sum = digsum(mid);
        if(mid-sum>=m){
            return newbin(left,mid,m);
        }else{
            return newbin(mid+1,right,m);
        }
    }
    
 
    public static long digsum(long n){
        long sum=0;
        while(n!=0){
            sum=sum+n%10;
            n=n/10;
        }
        return sum;
    }
 
    public static long square(long n) 
    { 
        long i=0;
        while(i*i<n+1){
            long val = n-(i*i);
            if(binsearch(0,val,val)==1){
                return 1;
            }
            i++;
        }
        return 0; 
    } 
 
    public static long binsearch(long a, long b, long n) 
    { 
  
        if(a<=b){ 
            long mid =a+(b-a)/2;
            long mid1=mid*mid; 
            if(mid1==n){
                return 1;
            }else if(mid1>n){
                return binsearch(a,mid-1,n);
            }else{
                return binsearch(mid+1,b,n);
            }
        }
        return 0;  
    }
 
    public static class sorting implements Comparator<type>{
        public int compare(type s1,type s2){
            if(s1.point>s2.point){
                return s2.point-s1.point;
            }else if(s1.point==s2.point){
                if(s1.penalty>s2.penalty){
                    return s1.penalty-s2.penalty;
                }else{
                    return s1.penalty-s2.penalty;
                }
            }else{
                return s2.point-s1.point;
            }
        }
    }
 
    public static void primeno(ArrayList<Integer> list){
        int n = (100000-2)/2;
        int[] boo = new int[n+1];
        int i=1;
        while(i<n+1){
            int j=1;
            while(j+i+2*i*j<n+1){
                boo[i+j+2*i*j]=1;
                j++;
            }
            i++;
        }
        list.add(2);
        int k=1;
        while(k<n+1){
            if(boo[k]!=1){
                list.add(2*k+1);
                k++;
            }else{
                k++;
            }
        }
    }
 
    public static long ribbon(long n,long a,long b,long c){
        long max1=0;
        long max2=0;
        long max3=0;
        if(n<a){
            return 0;
        }
        else{
            max1=1+ribbon(n-a,a,b,c);
            max2=1+ribbon(n-b,a,b,c);
            max3=1+ribbon(n-c,a,b,c);
        }    
        long max4=Math.max(max1,max2);
        return Math.max(max3,max4);
    }
 
    public static long gcd1(long a, long b) 
    { 
        if (a == 0) 
            return b; 
          
        return gcd1(b%a, a); 
    } 
 
    public static long collatz(long n){
        if(n==1){
            return 1;
        }else{
            if(n%2==0){
                return 1+collatz(n/2);
            }else{
                return 1+collatz(3*n+1);
            }
        }
    }
 
    public static boolean pallindromic(String s){
        int i2=s.length()-1;
        int c=0;
        for(int i1=0;i1<i2+1;i1++){
            if(s.substring(i1,i1+1).equals(s.substring(i2,i2+1))){
                c++;
                i2--;
            }else{
                break;
            }
        }
        if(c==i2+1){
            return true;
        }else{
            return false;
        }
    }
 
    public static void merge(long[] array,long left,long mid,long right,long size,long[] aux){
        long[] array1 = new long[3];
        array1[0]=left;
        array1[1]=mid;
        array1[2]=left;
        long count=0;
        while((array1[0]<mid) && (array1[1]<right+1)){
            if(compare(array[(int)array1[0]],array[(int)array1[1]])){
                aux[(int)array1[2]]=array[(int)array1[1]];
                // indx[(int)array1[2]]=array1[1];
                array1[2]=array1[2]+1;
                array1[1]=array1[1]+1;
                count+=mid-array1[0];
            }else{
                aux[(int)array1[2]]=array[(int)array1[0]];
                // indx[(int)array1[2]]=array1[0];
                array1[2]=array1[2]+1;
                array1[0]=array1[0]+1;
            }
        }
        filling3(array1,array,aux,right);
        filling2(array1,array,aux,mid);
        filling1(array1,left,right,array,aux);
        // return count;
    }
 
    public static void mergesort(long[] array, long left, long right, long size,long[] aux){
        long count=0;
        if(left<right){
            long mid = (left+right)/2;
            mergesort(array,mid+1,right,size,aux);
            mergesort(array,left,mid,size,aux);            
            merge(array,left,mid+1,right,size,aux);
            // count=count1+count2+count3;
        }
        // return count;
    }
 
    public static void filling1(long[] array1,long left,long right,long[] array,long[] aux){
        array1[0]=left;
        while(array1[0]<right+1){
            array[(int)array1[0]]=aux[(int)array1[0]];
            // indx[(int)array1[0]]=array1[0];
            array1[0]=array1[0]+1;
        }
    }
 
    public static void filling2(long[] array1,long[] array,long[] aux,long mid){
        while(array1[0]<mid){
            aux[(int)array1[2]]=array[(int)array1[0]];
            // indx[(int)array1[2]]=array1[0];
            array1[2]=array1[2]+1;
            array1[0]=array1[0]+1;
        }
    }
 
    public static void filling3(long[] array1,long[] array,long[] aux,long right){
        while(array1[1]<right+1){
            aux[(int)array1[2]]=array[(int)array1[1]];
            // indx[(int)array1[2]]=array1[1];
            array1[2]=array1[2]+1;
            array1[1]=array1[1]+1;
        }
    }
    
    public static long win(long x,long y){
        if(y%x==0){
            return 1;
        }else{
            if(y/x>1){
                return 1;
            }else{
                long val=x/2;
                if(y-x<=val){
                    return 0;
                }else{
                    return 1;
                }
            }
        }
    }
 
    public static long calculateMex(HashSet<Long> Set)  
    { 
        long Mex = 0; 
  
        while (Set.contains(Mex))  
        { 
            Mex++; 
        } 
        return (Mex); 
    } 
    public static long calculateGrundy(long x,long y)  
    {   
        // long a=y/x;
        // long j=x*a;
        // if(j==y){
        //     return j;
        // }
        for(long j=x;j<=y;j=j+x){
            if(j==y){
                return j;
            }
        } 
        HashSet<Long> Set = new HashSet<Long>();  
  
        for (long  i = x; i <=y; i=i+x)  
        { 
            Set.add(calculateGrundy(y-i,x)); 
        } 
        return (calculateMex(Set)); 
    } 
 
    public static long pile(long x,long y){
        if(x<y){
            if(y%x==0){
                return 1;
            }else{
                long div=y/x;
                long c=div;
                long ans=0;
                long max=0;
                while(c>0){
                    ans=y-(c*x);
                    if(ans%x==0 || x%ans==0){
                        c--;
                    }else{
                        max=c;
                        break;
                    }
                }
                // System.out.println(max+" "+c);
                if(max==0){
                    ans=y-(x*div);
                }else{
                    ans=y-(x*max);
                }
                // System.out.println(max+" "+c);
                return 1+pile(x,ans);
            }
        }else{
            if(x%y==0){
                return 1;
            }else{
                long div=x/y;
                long c=div;
                long ans=0;
                long max=0;
                while(c<=div){
                    ans=x-(c*y);
                    if(ans%y==0 || y%ans==0){
                        c--;
                    }else{
                        max=c;
                        break;
                    }
                }
                // System.out.println(max+" "+c);
                if(max==0){
                    ans=x-(y*div);
                }else{
                    ans=x-(y*max);
                }
                // System.out.println(max+" "+c);
                return 1+pile(y,ans);
            }
        }
    }
 
    public static long digprod(long n){
        if(n==0){
            return 1;
        }else if(n<10){
            return n;
        }else{
            return Math.max(digprod(n/10)*(n%10),digprod(n/10-1)*9);
        }
    }
 
    public static long sumodd(long[] array,int i,int n){
        long sum=0;
        for(int i1=i;i1<n+1;i1++){
            if(i1%2!=0){
                sum=sum+array[i1];
            }
            
        }
        return sum;
    }
    
    public static long sumeven(long[] array,int i,int n){
        long sum=0;
        for(int i1=i;i1<n+1;i1++){
            if(i1%2==0){
                sum=sum+array[i1];
            }
            
        }
        return sum;
    }
 
    public static int height(int[] array,int n,int indx){
        if(indx>=n-1){
            return 0;
        }else if(indx==n-2){
            return Math.abs(array[indx]-array[indx+1]);
        }
        else{
            return Math.min(Math.abs(array[indx]-array[indx+1])+height(array,n,indx+1),Math.abs(array[indx]-array[indx+2])+height(array,n,indx+2));
        }
    }
 
    public static boolean comparing(String s,int odd, int k){
        if(odd<=k && s.length()>=k){
            return true;
        }else{
            return false;
        }
    }  
 
    public static int oddcount(int[] array, String s, int n){
        for(int i=0;i<s.length();i++){
            if(s.substring(i,i+1).equals("a")){
                array[1]++;
            }else if(s.substring(i,i+1).equals("b")){
               array[2]++;
            }else if(s.substring(i,i+1).equals("c")){
               array[3]++;
            }else if(s.substring(i,i+1).equals("d")){
               array[4]++;
            }else if(s.substring(i,i+1).equals("e")){
               array[5]++;
            }else if(s.substring(i,i+1).equals("f")){
               array[6]++;
            }else if(s.substring(i,i+1).equals("g")){
               array[7]++;
            }else if(s.substring(i,i+1).equals("h")){
               array[8]++;
            }else if(s.substring(i,i+1).equals("i")){
               array[9]++;
            }else if(s.substring(i,i+1).equals("j")){
                array[10]++;
            }else if(s.substring(i,i+1).equals("k")){
                array[11]++;
            }else if(s.substring(i,i+1).equals("l")){
                array[12]++;
            }else if(s.substring(i,i+1).equals("m")){
                array[13]++;
            }else if(s.substring(i,i+1).equals("n")){
                array[14]++;
            }else if(s.substring(i,i+1).equals("o")){
                array[15]++;
            }else if(s.substring(i,i+1).equals("p")){
                array[16]++;
            }else if(s.substring(i,i+1).equals("q")){
                array[17]++;
            }else if(s.substring(i,i+1).equals("r")){
                array[18]++;
            }else if(s.substring(i,i+1).equals("s")){
                array[19]++;
            }else if(s.substring(i,i+1).equals("t")){
                array[20]++;
            }else if(s.substring(i,i+1).equals("u")){
                array[21]++;
            }else if(s.substring(i,i+1).equals("v")){
                array[22]++;
            }else if(s.substring(i,i+1).equals("w")){
                array[23]++;
            }else if(s.substring(i,i+1).equals("x")){
                array[24]++;
            }else if(s.substring(i,i+1).equals("y")){
                array[25]++;
            }else if(s.substring(i,i+1).equals("z")){
                array[26]++;
            }
        }
        return oddchecking(array, n);
    }
 
    public static int oddchecking(int[] array, int n){
        int odd=0;
        for(int j=1;j<n+1;j++){
            if(array[j]%2!=0){
                odd++;
            }
        }
        return odd;   
    }
 
    public static int printDivisors(int n) 
    { int c=0;
        // Note that this loop runs till square root 
        for (int i=1; i<=Math.sqrt(n); i++) 
        { 
            if (n%i==0) 
            { 
                if(n/i==i){
                    c++;
                }else{
                    c=c+2;
                }
            } 
        } 
        return c;
    } 
 
    public static long gcd(long a, long b) 
    { 
      if (b == 0){
        return a;
      }
      else{
        return gcd(b,a%b);
      }  
    } 
 
    public static long money(long[] array,long n,long sum,long i1,long i2){
        
        for(int k=(int)n-1;k>=0;k--){
            i1++;
            array[k]=array[k]%1000000007;
            sum=(sum+(array[k]*((i1*i2)%1000000007)))%1000000007;
            i2=(i1*i2)%1000000007;
        }
 
        return sum;
    }
 
    public static int largestsum(int[][] array,int i,int j,int n){
        if(i>n || j>n){
            return 0;
        }
        else{
            return array[i][j]+Math.max(largestsum(array,i+1,j,n),largestsum(array,i+1,j+1,n));
        }
    }
 
    public static void LongestIncreasingSubsequence(long[] array, long n){
        boolean flag=true;
        for(int i=2;i<=n;i++){
            if(array[i]>=array[i-1])
            {
                flag=false;
                break;
            }
        }
        if(flag)
            System.out.println(0);
        else{
        long count=0;
        long maximum=0;
        for(int i1=1;i1<=n;i1++){
            long m1=0;
            long l1=0;
            for(int i2=1;i2<i1;i2++){
                if(array[i2]<=array[i2+1] && array[i2+1]<=array[i1]){
                    l1++;
                }else{
                    if(l1!=0){
                        l1++;
                    }
                    if(m1<l1){
                        m1=l1;
                        l1=0;
                    }else{
                        l1=0;
                    }
                }
            }
            if(m1<l1){
                m1=l1;
            }
 
            long m2=0;
            long l2=0;
            for(int i2=i1;i2<=n;i2++){
                if(i2==n && array[i2]>=array[i1]){
                    l2++;
                    if(m2<=l2){
                        m2=l2;
                        l2=0;
                    }
                }
                else if(i2==n && array[i2]<array[i1]){
                    if(m2<l2){
                        m2=l2;
                        l2=0;
                    }
                }
                else if(i2!=n && array[i2]<=array[i2+1] && array[i2]>=array[i1]){
                    l2++;
                }
                else{
                    l2++;
                    if(m2<l2){
                        m2=l2;
                        l2=0;
                    }
                    else{
                        l2=0;
                    }
                }
            }
            if(m2<l2){
                m2=l2;
            }
            // System.out.println(m1+" "+m2);
            count = m1+m2;
            if(maximum<=count){
                maximum=count;
            }
 
        }
        System.out.println(maximum);}
 
        
    }
 
    public static int max_sum(int[][] array,int i, int j,int n){
        // System.out.println(i+" "+j);
        if(i>n || j>n){
            // int sum = 0;
            return 0;
        }
        return array[i][j]+Math.max(max_sum(array,i+1,j,n),max_sum(array,i,j+1,n));
    }
 
    public static boolean isPrime(long num){
    if ( num > 2 && num%2 == 0 ) {
        // System.out.println(num + " is not prime");
        return false;
    }
    long top = (long)Math.sqrt(num) + 1;
    for(int i = 3; i < top; i+=2){
        if(num % i == 0){
            // System.out.println(num + " is not prime");
            return false;
        }
    }
    // System.out.println(num + " is prime");
    return true; 
}
 
    public static BigInteger gcd(BigInteger a, BigInteger b) 
    {   
        BigInteger b1 = new BigInteger("0");
        // Everything divides 0  
        if (a.equals(b1) || b.equals(b1)) 
           return b1; 
       
        // base case 
        if (a.equals(b)) 
            return a; 
       
        // a is greater 
        if (a.compareTo(b)==1) 
            return gcd(a.subtract(b), b); 
        return gcd(a, b.subtract(a)); 
    } 
      
    // method to return LCM of two numbers 
    public static BigInteger lcm(BigInteger a, BigInteger b) 
    { 
        
        return (a.multiply(b)).divide(gcd(a, b)); 
    }
 
    public static boolean compare(long a, long b){
        if(a>b){
            return true;
        }else{
            return false;
        }
    }
 
    public static long modu(long n, long a, long b, long temp){
        long m = n/2;
        if(m<1){
            return 0;
        }else if(m>temp){
            return 0;
        }
        else{
            if(m%a==0 && m%b!=0 || m%b==0 && m%a!=0){
                return 1+modu(m-1,a,b,temp)+modu(m+1,a,b,temp);
            }else{
                return modu(m-1,a,b,temp)+modu(m+1,a,b,temp);
            }
        }
    }
    
    public static double distance(double a, double b, double c, double d){
        double d1 = (a-c)*(a-c);
        double d2 = (b-d)*(b-d);
        double d3 = d1+d2;
        double d4 = Math.sqrt(d3);
        return d4;
    }
 
    public static long weigh(long n){
        if(n<=30){
            return 40;
        }else if(n>30 && n<51){
            return 55;
        }else{
            if(n%50==0){
                long temp = n/50;
                long value = 25*(temp-1);
                return value+55;
            }else{
                long temp = n/50;
                temp++;
                // System.out.println(temp);
                long value = 25*(temp-1);
                return value+55;
            }
        }
    }
 
    public static long max_val(long[] array, int n, int start, int end){
        long maximum=0;
        long value1=array[0];
        long value2=0;
        int i=1;
        long result = looping(array,n,i,maximum,value1,value2);
        return result;
    }
 
    public static long max(long a , long b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }
    
    // public static void counting(int[] array, int a, int b, int n){
    //         int indx1 = Binary(array,1,n,a);
       
    //         int indx2 = Binary(array,1,n,b);
       
    //         int diff = indx2-indx1;
       
    //         int result = diff+1;
       
    //         Result(result);      
    // }
 
    public static long result(long n, long m){
        long value = horse_path(n,m,1,1);
        return value;
    }
 
    public static int permutations(int n,int i, int count){
        while(i<n+1){
            int j=1;
            while(j<n+1){
                int prod = 0;
                prod = i*j;
                if(n%prod==0){
                    count++;
                }
                j++;
            }
            i++;
        }
        return count;
    }
 
    public static int swap2(int j1,int[] professor_list,int prof,int n){
 
                while(j1<n+1)
                {
        
                    if(professor_list[j1]==0){
        
                        prof=j1;
        
                        break;
        
                    }
        
                    j1++;
        
                }
                return prof;
    
    }
 
    public static boolean check(int[] array,int n, int sum, boolean boo, int max){
        int temp=max;
        boolean curr = boo;
        if(sum>0 && n==0){
            return false;
        }else if(sum==0){
            return true;
        }else if(array[n-1]>sum){
            return check(array,n-1,sum,curr,temp);
        }
        else{
            return check(array,n-1,sum,curr,temp) || check(array,n-1,sum-array[n-1],curr,temp);
        }
    }
 
    public static int swap4(int[] professor_list, int[] student_list, int prof, int stud, int total){
 
            professor_list[prof]=1;
        
            student_list[stud]=prof;
 
            total--;
 
            return total;
        
    }
 
 
    public static void show(int[] array,int n){
        
        for(int i=1;i<n+1;i++){
        
            System.out.println(i+" "+array[i]);
        
        }
    }
   
    public static int Binary(long[] array , int min, int max, long num){
        
        if(max<min){
        
            return -1;
        
        }else if(max==min){
            return min;
        }
 
        else
        {
        
            int mid = min + (max-min)/2;
        
            if(array[mid]==num){
        
                return mid;
        
            }
            else if(array[mid]>num){
                return Binary(array,min,mid-1,num);
            
            }
            else
            {
            
                return Binary(array,mid+1,max,num);
            
            }
        }
    }
 
}
 
class type{
    int point;
    int penalty;
    String name;
    public type(int point,int penalty,String name){
        this.point=point;
        this.penalty=penalty;
        this.name=name;
    }
}
class discomp implements Comparator<people>{
    public int compare(people p1,people p2){
        if(p1.distance>p2.distance){
            return 1;
        }return -1;
    }
}
class people{
    int money;
    int distance;
    public people(int money,int distance){
        this.money=money;
        this.distance=distance;
    }
}
 
class peoplecomp implements Comparator<people>{
    public int compare(people p1,people p2){
        if(p1.money==p2.money){
            if(p1.distance>p2.distance){
                return 1;
            }else{
                return -1;
            }
        }else if(p1.money>p2.money){
            return 1;
        }else{
            return -1;
        }
    }
}
 
// class ArrayIndexComparator implements Comparator<Integer>
// {
//     int[] array;
 
//     public ArrayIndexComparator(int[] array)
//     {
//         this.array = array;
//     }
 
//     public Integer[] createIndexArray()
//     {
//         Integer[] indexes = new Integer[array.length];
//         for (int i = 0; i < array.length; i++)
//         {
//             indexes[i] = i; // Autoboxing
//         }
//         return indexes;
//     }
 
//     @Override
//     public int compare(Integer index1, Integer index2)
//     {
//          // Autounbox from Integer to int to use as array indexes
//         return array[index1].compareTo(array[index2]);
//     }
// }
 
class sub{
    long total;
    long maximum;
    public sub(long total,long maximum){
        this.total=total;
        this.maximum=maximum;
    } 
}
 
class KeyComparator implements Comparator<Key>{ 
        public int compare(Key k1, Key k2) { 
            if(k1.freq==k2.freq){
                return 0;
            }else if(k1.freq>k2.freq){
                return 1; 
            }else{
                return -1;
            }
        } 
    } 
 
class foodcomparator implements Comparator<food>{
    public int compare(food f1,food f2){
        if(f1.expire>f2.expire){
            return f1.expire-f2.expire;
        }else{
            return f1.expire-f2.expire;
        }
    }
}
 
class food{
        int time;
        int expire;
        public food(int time,int expire){
            this.time=time;
            this.expire=expire;
        }
    }
  
    class Key { 
        int freq;
        int num; 
        public Key(int freq, int num) 
        { 
            this.freq = freq; 
            this.num = num; 
        }    
    } 
