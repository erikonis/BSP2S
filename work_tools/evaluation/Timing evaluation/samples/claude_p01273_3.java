import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            
            if (N == 0 && M == 0) break;
            
            List<Packet> packets = new ArrayList<>();
            for (int i = 0; i < M; i++) {
                int t = sc.nextInt();
                int s = sc.nextInt();
                int d = sc.nextInt();
                packets.add(new Packet(t, s, d));
            }
            
            // Sort packets by time
            packets.sort((a, b) -> Integer.compare(a.time, b.time));
            
            Set<Integer> infected = new HashSet<>();
            infected.add(1); // Computer 1 is initially infected
            
            // Process packets in chronological order
            for (Packet p : packets) {
                if (infected.contains(p.source)) {
                    infected.add(p.destination);
                }
            }
            
            System.out.println(infected.size());
        }
        
        sc.close();
    }
    
    static class Packet {
        int time, source, destination;
        
        Packet(int time, int source, int destination) {
            this.time = time;
            this.source = source;
            this.destination = destination;
        }
    }
}