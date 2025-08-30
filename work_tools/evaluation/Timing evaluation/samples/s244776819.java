import sun.security.provider.certpath.Vertex;

import java.util.*;


public class Main {
    static class Vertex {
        private int x, y, value;
        private boolean hasValue = false;
        private ArrayList<Edge> edges = new ArrayList<Edge>();

        Vertex() {
        }

        Vertex(int x) {
            this.x = x;
        }

        Vertex(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void setX(int x) {
            this.x = x;
        }

        void setY(int y) {
            this.y = y;
        }

        void setValue(int v) {
            this.value = v;
            this.hasValue = true;
        }

        void setEdge(Vertex v) {
            this.edges.add(new Edge(v));
        }

        void setEdge(Vertex v, int cost) {
            this.edges.add(new Edge(v, cost));
        }

        void setEdge(Edge e) {
            this.edges.add(e);
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }

        int getValue() {
            return this.value;
        }

        int getDegree() {
            return this.edges.size();
        }

        Edge getEdge(int index) {
            return this.edges.get(index);
        }

        boolean hasValue(){
            return this.hasValue;
        }
    }

    static class Edge {
        private Vertex v;
        private int cost;

        Edge() {
            this.v = v;
        }

        Edge(Vertex v) {
            this.v = v;
        }

        Edge(Vertex v, int c) {
            this.v = v;
            this.cost = c;
        }

        Vertex getVertex() {
            return this.v;
        }

        int getCost() {
            return this.cost;
        }
    }


    static class Graph {
        private Vertex[] vertices;
        private int verticesNum;

        Graph(int verticesNum) {
            this.verticesNum = verticesNum;
            this.vertices = new Vertex[verticesNum];
            for (int i = 0; i < verticesNum; i++) {
                this.vertices[i] = new Vertex();
            }
        }

        int getVerticesNum(){
            return this.verticesNum;
        }

        void setVertex(int index, Vertex v) {
            this.vertices[index] = v;
        }

        Vertex getVertex(int index) {
            return this.vertices[index];
        }
    }

    static void setAdjacentValue(Vertex v){
        for(int i = 0; i < v.getDegree(); i++){
            Vertex nextVertex = v.getEdge(i).getVertex();
            if(!nextVertex.hasValue()){
                int nextValue = v.getValue() + v.getEdge(i).getCost();
                nextVertex.setValue(nextValue);
                setAdjacentValue(nextVertex);
            }
        }
    }

    static void solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Graph g = new Graph(n + 1);

        int m = sc.nextInt();
        HashSet<Integer> appearedIndex = new HashSet<Integer>();
        for (int i = 0; i < m; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            int d = sc.nextInt();
            appearedIndex.add(l);
            appearedIndex.add(r);
            Vertex vl = g.getVertex(l);
            Vertex vr = g.getVertex(r);
            vl.setEdge(vr, d);
            vr.setEdge(vl, -d);
        }

        for(int i : appearedIndex){
            Vertex v = g.getVertex(i);
            if(!v.hasValue){
                v.setValue(0);
                setAdjacentValue(v);
            }
        }

        String ans = "Yes";
        for(int i : appearedIndex){
            Vertex v = g.getVertex(i);
            for(int j = 0; j < v.getDegree(); j++){
                int adjacentValue = v.getEdge(j).getVertex().getValue();
                int expectedValue = v.getValue() + v.getEdge(j).getCost();
                if(adjacentValue != expectedValue){
                    ans = "No";
                    break;
                }
            }
        }
        System.out.println(ans);
    }

    public static void main(String[] args) {
        solve();
    }
}
