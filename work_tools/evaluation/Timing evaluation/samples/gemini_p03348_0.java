import java.util.*;

public class Main {
    static int N;
    static List<Integer>[] adj;

    // Calculates the centroid(s) of a tree
    static List<Integer> getCentroids(int n, List<Integer>[] graph) {
        int[] subtreeSize = new int[n + 1];
        int[] maxSubtreeSize = new int[n + 1];
        List<Integer> centroids = new ArrayList<>();
        dfsCentroid(1, 0, n, graph, subtreeSize, maxSubtreeSize, centroids);
        return centroids;
    }

    static void dfsCentroid(int u, int p, int n, List<Integer>[] graph, int[] subtreeSize, int[] maxSubtreeSize, List<Integer> centroids) {
        subtreeSize[u] = 1;
        maxSubtreeSize[u] = 0;
        for (int v : graph[u]) {
            if (v == p) continue;
            dfsCentroid(v, u, n, graph, subtreeSize, maxSubtreeSize, centroids);
            subtreeSize[u] += subtreeSize[v];
            maxSubtreeSize[u] = Math.max(maxSubtreeSize[u], subtreeSize[v]);
        }
        maxSubtreeSize[u] = Math.max(maxSubtreeSize[u], n - subtreeSize[u]);
        if (maxSubtreeSize[u] <= n / 2) {
            centroids.add(u);
        }
    }

    // Checks if two rooted trees are isomorphic
    static boolean isIsomorphic(int u1, int p1, int u2, int p2, List<Integer>[] g1, List<Integer>[] g2) {
        if (g1[u1].size() - (p1 != 0 ? 1 : 0) != g2[u2].size() - (p2 != 0 ? 1 : 0)) {
            return false;
        }

        List<String> childrenHashes1 = new ArrayList<>();
        for (int v1 : g1[u1]) {
            if (v1 == p1) continue;
            childrenHashes1.add(getTreeHash(v1, u1, g1));
        }
        Collections.sort(childrenHashes1);

        List<String> childrenHashes2 = new ArrayList<>();
        for (int v2 : g2[u2]) {
            if (v2 == p2) continue;
            childrenHashes2.add(getTreeHash(v2, u2, g2));
        }
        Collections.sort(childrenHashes2);

        return childrenHashes1.equals(childrenHashes2);
    }

    // Computes a hash for a rooted tree
    static String getTreeHash(int u, int p, List<Integer>[] graph) {
        List<String> childrenHashes = new ArrayList<>();
        for (int v : graph[u]) {
            if (v == p) continue;
            childrenHashes.add(getTreeHash(v, u, graph));
        }
        Collections.sort(childrenHashes);
        StringBuilder sb = new StringBuilder("(");
        for (String hash : childrenHashes) {
            sb.append(hash);
        }
        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        // Calculate initial centroids
        List<Integer> initialCentroids = getCentroids(N, adj);

        int minColors = Integer.MAX_VALUE;
        long minLeaves = Long.MAX_VALUE;

        // Case 1: The original tree is the final tree T
        int distinctRootedTrees = 0;
        Set<String> uniqueHashes = new HashSet<>();
        for (int i = 1; i <= N; i++) {
            uniqueHashes.add(getTreeHash(i, 0, adj));
        }
        distinctRootedTrees = uniqueHashes.size();

        if (distinctRootedTrees < minColors) {
            minColors = distinctRootedTrees;
            minLeaves = countLeaves(N, adj);
        } else if (distinctRootedTrees == minColors) {
            minLeaves = Math.min(minLeaves, countLeaves(N, adj));
        }

        // Case 2: Add a new vertex to make the tree a star-like structure
        // This makes all original vertices roots of isomorphic trees
        if (initialCentroids.size() == 1) { // If original tree has a unique centroid
            int c = initialCentroids.get(0);
            minColors = Math.min(minColors, 2); // Centroid and leaves
            if (minColors == 2) {
                // Number of leaves becomes N - 1 (all original nodes become leaves if we attach a new central node)
                // Or if we attach to a centroid, and then attach new nodes to the original nodes
                // The problem phrasing suggests adding one new vertex at a time.
                // To achieve 2 colors, we can attach a new node to the centroid.
                // Then the centroid and the new node are one color, and all other nodes are another color.
                // This would mean original N-1 nodes (non-centroid) are leaves + the new node.
                // This is complex. Let's reconsider the definition of colorfulness.
                // If we connect a new vertex to a centroid, then the centroid and the new vertex form one part.
                // All other initial vertices (N-1 of them) become leaves attached to the centroid.
                // The new vertex is also a leaf. So total leaves = N - 1 + 1 = N.
                // This gives 2 colors: {centroid, new_node} and {all N-1 original neighbors}.
                // This structure has N leaves.
                minLeaves = Math.min(minLeaves, N);
            }
        } else if (initialCentroids.size() == 2) { // If original tree has two centroids (edge in between)
            minColors = Math.min(minColors, 2);
            if (minColors == 2) {
                // If we have an edge (c1, c2), connect a new node to c1.
                // Then c1 and c2 are in one color, and all other N-2 nodes are in another color.
                // The new node is also in the first color.
                // All nodes in the original tree except c1 and c2 become leaves.
                // The new node is also a leaf.
                // Leaves = N - 2 + 1 = N - 1.
                minLeaves = Math.min(minLeaves, N - 1);
            }
        }

        // The problem asks for the minimum colorfulness of T that *can be constructed*.
        // The most symmetric tree that can be constructed by adding nodes is a star.
        // If we can form a star, we can achieve 2 colors: center and leaves.
        // A star graph with K leaves has K leaves.
        // If we want to transform the original tree into a star, we "grow" new branches from a central point.
        // Let's consider the core idea:
        // The minimum colorfulness is related to the "automorphism group" of the tree.
        // If a tree has a single centroid, we can make it the center of a star.
        // If it has two centroids, we can make the edge between them the center.

        // The problem is asking for minimum colorfulness AND minimum leaves.
        // A "good coloring" means that for any u, v of the same color, rooted(G, u) is isomorphic to rooted(G, v).
        // This implies that all vertices of the same color must have the same rooted tree isomorphism class.

        // Let's consider the idea of finding the "center" of the tree.
        // The minimum colorfulness is 1 if and only if the tree is path symmetric (e.g., path graph, star graph)
        // and all nodes are isomorphic when rooted. This only happens for K_1 (single node).
        // For N >= 2, min colorfulness is at least 2.

        // The "alternative method" implies we should think about the structure of the tree T.
        // A tree T achieving minimum colorfulness often has a high degree of symmetry.
        // Such symmetric trees are typically stars or paths.

        // If we can construct a tree T which is a star graph:
        //  - If N is the number of vertices in the original tree.
        //  - We can pick one vertex 'c' from the original tree as the center.
        //  - We add new vertices to 'c' such that all existing branches become isomorphic.
        //  - This is not what the problem asks. It asks to add new vertices by connecting them to *one* of the vertices.
        //  - The "operation" is: "Add a new vertex to the tree by connecting it to one of the vertices in the current tree with an edge."

        // The most symmetric tree we can target is a star graph.
        // If we construct a star graph where the center is one of the original nodes, say 'c'.
        // And all other N-1 original nodes are leaves attached to 'c'.
        // We can then add new vertices, each attached to 'c'.
        // To make all leaves symmetric, we need to make all branches from 'c' isomorphic.
        // This implies a star structure.
        // A star graph with X leaves has 2 colors: 1 for the center, 1 for all X leaves.
        // The number of leaves is X.
        // What is the minimum number of leaves in such a star T?
        // We can always form a star by choosing an arbitrary vertex 'v' from the original graph,
        // and attaching N-1 new leaves to 'v'. This makes 'v' the center, and all N-1 original vertices are leaves.
        // Plus the N-1 newly added leaves. So total leaves = 2(N-1). This is a lot of leaves.

        // Let's analyze the properties of a tree with minimum colorfulness.
        // A tree has colorfulness 2 if it's "balanced" around a central point or edge.
        // For example, a path graph P_n has colorfulness 2 if n is odd (center is a vertex),
        // and colorfulness 2 if n is even (center is an edge).
        // A star graph K_{1,k} has colorfulness 2.

        // The core insight here is that the minimum colorfulness is related to the "symmetry center" of the tree.
        // For a tree G, its colorfulness is the number of distinct isomorphism classes of rooted trees (G, v) where v is a vertex of G.
        // The problem states that for every pair of two vertices u and v painted in the same color,
        // picking u as the root and picking v as the root would result in isomorphic rooted trees.
        // This means each color corresponds to a unique rooted tree isomorphism class.
        // So, colorfulness is simply the number of distinct rooted tree isomorphism classes present in the tree.

        // The "alternative method" might be to use properties of centroids.
        // A tree has either one centroid or two adjacent centroids.
        // If a tree has a single centroid `c`, then rooting at `c` gives a "balanced" tree.
        // If a tree has two centroids `c1, c2`, then rooting at `c1` or `c2` gives a "balanced" tree.

        // Consider the "automorphism group" of the tree.
        // If we can construct a tree T such that all vertices are symmetric (e.g., path P2), then colorfulness is 1.
        // But for N >= 2, colorfulness is at least 2.
        // For N=2, P2: 1-2. Rooted at 1: (()), Rooted at 2: (()). Isomorphic. Colorfulness = 1.
        // No, the condition is "for every pair of two vertices u and v painted in the same color".
        // If we paint both 1 and 2 the same color, they must be isomorphic when rooted. They are.
        // So for N=2, colorfulness is 1. Leaves = 2.
        // This contradicts Sample 1 Output (2 4 for N=5).
        // Let's re-read: "painting all the vertices in a single color is not a good coloring".
        // This implies that the total number of colors must be > 1 if N > 1.
        // So, for N=2, colorfulness is 2. (e.g., color 1 red, color 2 blue). This makes no sense.
        // "painting all the vertices in a single color is not a good coloring"
        // This implies that if all vertices are painted the same color, then they must all be isomorphic when rooted.
        // If they are not all isomorphic when rooted, then it's not a good coloring.
        // If they ARE all isomorphic when rooted, then it *is* a good coloring with 1 color.
        // The sample output says "Since painting all the vertices in a single color is not a good coloring, we can see that the colorfulness of this tree is 2."
        // This implies that for the sample tree (N=5 path), not all rooted trees are isomorphic.
        // For a path P5 (1-2-3-4-5), rooted at 1 is different from rooted at 2 etc.
        // So they cannot all be the same color. Hence >1 colors.
        // And they found a way to make it 2 colors.

        // The minimum colorfulness is the minimum number of distinct rooted tree isomorphism classes we can achieve.
        // We can always attach a new node to any existing node. This operation can increase symmetry.

        // Let's consider the "center" of the original tree.
        // If it has a single centroid 'c':
        // We can attach a new node 'x' to 'c'.
        // Now, the tree rooted at 'c' is different. The tree rooted at 'x' is just 'c' with its branches.
        // The original branches attached to 'c' are still there.
        // This new tree has N+1 vertices.

        // What if we try to make the tree a "star-like" structure where all branches from a central node are isomorphic?
        // This is the common strategy for minimum colorfulness.
        // If we take the "center" of the tree (centroid(s)), and attach new nodes to make all branches symmetric.

        // Let's calculate the "symmetry classes" of the original tree.
        // For each node u, compute its rooted tree hash. Group nodes by hash.
        // The number of groups is the colorfulness.
        // Minimum leaves is just the number of nodes with degree 1.

        // The problem asks for the minimum colorfulness of *T*.
        // And the minimum number of leaves in a tree T that achieves it.

        // A key property: if a tree has a unique centroid 'c', then (G, c) is the most "balanced" rooted tree.
        // If it has two centroids 'c1, c2', then (G, c1) and (G, c2) are the most "balanced".

        // What if we choose one of the original nodes, say `v`, and attach a new node `x` to `v`?
        // This creates a new tree `T`.
        // We need to find the minimum `distinct_rooted_tree_hashes(T)` and `leaves(T)`.

        // This problem seems to be about finding the "automorphism group" of the tree.
        // The number of colors is the number of orbits of the automorphism group on the vertices.
        // For a tree, the orbits are symmetric structures.

        // Let's consider the "root" of the tree.
        // If the tree has a unique centroid `c`.
        // We can attach a new vertex `x` to `c`. Now `c` has degree `deg(c)+1`. `x` has degree 1.
        // The new tree has `N+1` vertices.
        // We need to calculate colorfulness and leaves for this new tree.
        // The rooted tree structure changes.
        // Does this make it more symmetric?

        // The classic result for minimum colorfulness of a tree G is related to its "characteristic polynomial" or "canonical form".
        // However, this problem allows us to *add* vertices.

        // Consider the "center" of the tree:
        // 1. One centroid `c`.
        // 2. Two centroids `c1, c2` connected by an edge.

        // If we add a new vertex `x` to `c` (if `c` is a centroid):
        // The new tree `T` has `N+1` vertices.
        // `T_adj` will be `adj` plus `x` connected to `c`.
        // Calculate colorfulness and leaves for `T_adj`.

        // If we add `x` to a leaf `l`:
        // `l` is no longer a leaf. `x` is a new leaf.
        // This doesn't seem to increase symmetry much.

        // The sample output suggests that often 2 colors are enough.
        // How to achieve 2 colors with minimum leaves?
        // A tree has colorfulness 2 if it can be partitioned into two sets of vertices,
        // such that all vertices in one set yield isomorphic rooted trees, and all vertices in the other set yield isomorphic rooted trees.
        // Example: Star graph. Center is one color. Leaves are another color.
        // A path graph P_n (n > 2). Center(s) vs. non-center(s).

        // If we connect a new vertex `x` to vertex `v` in the original tree.
        // The new graph `G'` has `N+1` vertices.
        // The leaves of `G'` are original leaves that are not `v`, plus `x` if `v` wasn't a leaf.
        // If `v` was a leaf, `v` is no longer a leaf, `x` is a new leaf.

        // Let's analyze the sample 1: N=5, Path 1-2-3-4-5.
        // Centroid is 3.
        // Rooted hashes:
        // Rooted at 1: ( ( () ) )
        // Rooted at 2: ( () ( () ) )
        // Rooted at 3: ( ( ) ( ) )
        // Rooted at 4: ( () ( () ) )
        // Rooted at 5: ( ( () ) )
        // Distinct hashes: ( ( () ) ), ( () ( () ) ), ( ( ) ( ) ) -> 3 colors. Leaves = 2 (1, 5).

        // Sample says: connect vertex 6 to vertex 2.
        // New tree: 1-2-3-4-5, and 2-6.
        // Graph: 1-2-3-4-5, 2-6
        // Degrees: deg(1)=1, deg(2)=3, deg(3)=2, deg(4)=2, deg(5)=1, deg(6)=1.
        // Leaves: 1, 5, 6. Total 3 leaves.
        // Let's check hashes for this new tree (N=6):
        // Rooted at 1: (())
        // Rooted at 2: ( (()) (()) () ) -- This is messy. Need to compute.
        // Let's represent hashes as string:
        // (x) means a leaf
        // ((x)(y)) means a node with two children, one is a leaf, one is a tree with hash y.
        // Tree: 1-2-3-4-5, 2-6
        // Children of 2: {1, 3, 6}
        // Children of 3: {2, 4}
        // Children of 4: {3, 5}
        // Children of 5: {4}
        // Children of 6: {2}

        // Hash(u, p)
        // H(1, 2) = "()"
        // H(5, 4) = "()"
        // H(6, 2) = "()"

        // H(4, 3) = "(" + H(5, 4) + ")" = "(())"
        // H(3, 2) = "(" + H(4, 3) + ")" = "((()))"

        // H(2, 1): children {3,6}. H(3,2)="((()))", H(6,2)="()". Sorted: "()((()))". Hash = "(()((()))())"
        // H(2, 3): children {1,6}. H(1,2)="()", H(6,2)="()". Sorted: "()()". Hash = "(()())"
        // H(2, 6): children {1,3}. H(1,2)="()", H(3,2)="((()))". Sorted: "()((()))". Hash = "(()((()))())"

        // Let's compute all hashes for the sample output tree (N=6):
        // Vertices: 1,2,3,4,5,6. Edges: (1,2),(2,3),(3,4),(4,5),(2,6)
        // Rooted at 1: Tree is 1-2(3-4-5, 6)
        //   H(1,0):
        //     H(2,1): children {3,6}
        //       H(3,2): children {4}. H(4,3): children {5}. H(5,4): children {}. H(5,4) = "()". H(4,3) = "(())". H(3,2) = "((()))".
        //       H(6,2): children {}. H(6,2) = "()".
        //     Sorted children hashes of 2: ["()", "((()))"]. Hash for (2,1) = "(()((()))())"
        //   Hash for (1,0) = "(()((()))())"

        // Rooted at 2:
        //   H(2,0): children {1,3,6}
        //     H(1,2) = "()"
        //     H(3,2) = "((()))" (computed above)
        //     H(6,2) = "()"
        //   Sorted children hashes: ["()", "()", "((()))"]. Hash for (2,0) = "(()()((()))())"

        // Rooted at 3:
        //   H(3,0): children {2,4}
        //     H(2,3): children {1,6}. H(1,2)="()", H(6,2)="()". Sorted: ["()", "()"]. Hash for (2,3) = "(()())"
        //     H(4,3): children {5}. H(5,4)="()". Hash for (4,3) = "(())"
        //   Sorted children hashes: ["(()())", "(())"]. Hash for (3,0) = "((()()) (()))"

        // Rooted at 4:
        //   H(4,0): children {3,5}
        //     H(3,4): children {2}. H(2,3)="(()())" (computed above). Hash for (3,4) = "(((()())())"
        //     H(5,4): children {}. Hash for (5,4) = "()"
        //   Sorted children hashes: ["()", "(((()())())"]. Hash for (4,0) = "(()(((()())())())"

        // Rooted at 5:
        //   H(5,0): children {4}. H(4,5): children {3}. H(3,4): children {2}. etc.
        //     H(4,5) = "(((()((()))())())" (complex, path 4-3-2-1, 4-3-2-6)
        //     This is getting too complicated to compute by hand.

        // The sample output says painting (1,4,5,6) red and (2,3) blue is a good coloring.
        // This means:
        // H(1,0) == H(4,0) == H(5,0) == H(6,0)
        // H(2,0) == H(3,0)
        // This implies 2 colors. Leaves are 1, 5, 6. What about 4?
        // Sample says 4 leaves. This means a different configuration.
        // "connect a new vertex 6 to vertex 2, painting the vertices (1,4,5,6) red and painting the vertices (2,3) blue"
        // My interpretation of leaves: vertices with degree 1.
        // In the sample output tree (1-2-3-4-5, 2-6), leaves are {1, 5, 6}.
        // The sample implies vertex 4 is a leaf. How?
        // This means the tree in sample output is not 1-2-3-4-5, 2-6.
        // It must be a different structure that results in 4 leaves and 2 colors.
        // Sample tree is a path 1-2-3-4-5.
        // "If we connect a new vertex 6 to vertex 2, painting the vertices (1,4,5,6) red..."
        // This is the key. The sample tree is a path. The new vertex 6 is connected to 2.
        // Original leaves are 1 and 5.
        // New leaves are 1, 5, 6. And 4 is a leaf? This is impossible.
        // Wait, "leaves" are vertices with degree 1.
        // The sample output description must be for a different tree than the one I assumed.
        // Ah, the sample input is for N=5, 1-2, 2-3, 3-4, 3-5. This is a "star-like" graph centered at 3.
        // 1-2-3-4
        //     |
        //     5
        // Original leaves: 1, 4, 5. (3 leaves)
        // Centroid is 3.

        // Sample 1: Input tree
        //   1
        //   |
        //   2
        //   |
        //   3 -- 4
        //   |
        //   5
        // Leaves: 1, 4, 5 (3 leaves). Centroid: 3.
        // Rooted hashes:
        // H(1,2) = "()"
        // H(4,3) = "()"
        // H(5,3) = "()"
        // H(2,3) = "(" + H(1,2) + ")" = "(())"
        // H(3,2) = "(" + H(4,3) + H(5,3) + ")" = "(()())"
        // H(3,4) = "(" + H(2,3) + H(5,3) + ")" = "((())())"
        // H(3,5) = "(" + H(2,3) + H(4,3) + ")" = "((())())"

        // Now compute hashes for all nodes rooted at themselves:
        // H(1,0) = "(()(()())())" (root 1, child 2. 2 has children 3. 3 has children 4,5. 3 has parent 2. 2 has parent 1.)
        //   H(2,1): children {3}. H(3,2): children {4,5}. H(4,3)="()", H(5,3)="()". Sorted: "()()". H(3,2)="(()())".
        //   H(2,1) = "(((()())())"
        //   H(1,0) = "((((()())())())"

        // H(2,0): children {1,3}
        //   H(1,2)="()"
        //   H(3,2)="(()())"
        //   Sorted: ["()", "(()())"]. H(2,0) = "(()(()())())"

        // H(3,0): children {2,4,5}
        //   H(2,3)="((())" (computed above)
        //   H(4,3)="()"
        //   H(5,3)="()"
        //   Sorted: ["()", "()", "(())"]. H(3,0) = "(()()(()))"

        // H(4,0): children {3}. H(3,4): children {2,5}. H(2,3)="(()())", H(5,3)="()". Sorted: ["()", "(()())"]. H(3,4)="(()(()())())"
        //   H(4,0) = "(()(()(()())())())"

        // H(5,0): children {3}. H(3,5): children {2,4}. H(2,3)="(()())", H(4,3)="()". Sorted: ["()", "(()())"]. H(3,5)="(()(()())())"
        //   H(5,0) = "(()(()(()())())())"

        // Unique hashes for original tree:
        // H(1,0) = "((((()())())())"
        // H(2,0) = "(()(()())())"
        // H(3,0) = "(()()(()))"
        // H(4,0) = "(()(()(()())())())"
        // H(5,0) = "(()(()(()())())())"
        // This yields 5 distinct hashes. So 5 colors. Leaves = 3.

        // Sample explanation: "If we connect a new vertex 6 to vertex 2, painting the vertices (1,4,5,6) red and painting the vertices (2,3) blue is a good coloring."
        // New tree: 1-2-3-4, 2-6, 3-5
        //   1   6
        //   | /
        //   2
        //   |
        //   3 -- 4
        //   |
        //   5
        // Degrees: deg(1)=1, deg(2)=3, deg(3)=3, deg(4)=1, deg(5)=1, deg(6)=1.
        // Leaves: 1, 4, 5, 6 (4 leaves).
        // Now let's recompute hashes for this new tree (N=6):
        // H(1,2)="()"
        // H(4,3)="()"
        // H(5,3)="()"
        // H(6,2)="()"

        // Hashes for nodes rooted at themselves:
        // H(1,0): child 2. H(2,1): children {3,6}. H(3,2): children {4,5}. H(4,3)="()", H(5,3)="()". Sorted "()()". H(3,2)="(()())".
        //   H(6,2)="()". Sorted children of 2: ["()", "()", "(()())"]. H(2,1)="(()()(()())())".
        //   H(1,0) = "(()(()()(()())())())"

        // H(2,0): children {1,3,6}.
        //   H(1,2)="()", H(3,2)="(()())", H(6,2)="()". Sorted: ["()", "()", "(()())"].
        //   H(2,0) = "(()()(()())())"

        // H(3,0): children {2,4,5}.
        //   H(2,3): children {1,6}. H(1,2)="()", H(6,2)="()". Sorted: ["()", "()"]. H(2,3)="(()())".
        //   H(4,3)="()", H(5,3)="()".
        //   Sorted children of 3: ["()", "()", "(()())"].
        //   H(3,0) = "(()()(()())())"

        // H(4,0): children {3}. H(3,4): children {2,5}. H(2,3)="(()())", H(5,3)="()". Sorted: ["()", "(()())"]. H(3,4)="(()(()())())".
        //   H(4,0) = "(()(()(()())())())"

        // H(5,0): children {3}. H(3,5): children {2,4}. H(2,3)="(()())", H(4,3)="()". Sorted: ["()", "(()())"]. H(3,5)="(()(()())())".
        //   H(5,0) = "(()(()(()())())())"

        // H(6,0): children {2}. H(2,6): children {1,3}. H(1,2)="()", H(3,2)="(()())". Sorted: ["()", "(()())"]. H(2,6)="(()(()())())".
        //   H(6,0) = "(()(()(()())())())"

        // Unique hashes:
        // H(1,0) = "(()(()()(()())())())"
        // H(4,0) = "(()(()(()())())())"
        // H(5,0) = "(()(()(()())())())"
        // H(6,0) = "(()(()(()())())())"
        // These 4 are the same! So they can be red.
        // H(2,0) = "(()()(()())())"
        // H(3,0) = "(()()(()())())"
        // These 2 are the same! So they can be blue.
        // Total 2 colors. Leaves = 4. This matches sample output.

        // The strategy is: try adding a new vertex `x` connected to each existing vertex `v` (1 to N).
        // For each such new tree, calculate its colorfulness and leaves.
        // Keep track of the minimum colorfulness and corresponding minimum leaves.

        // Minimum colorfulness for N=2 is 1 (P2). No. Sample implies 2.
        // "Since painting all the vertices in a single color is not a good coloring"
        // This implies if all vertices are isomorphic when rooted, it's not a good coloring.
        // This is a weird condition. It means `colorfulness >= 2` if `N > 1`.
        // If N=2, path 1-2. Rooted at 1: (()). Rooted at 2: (()). Both are isomorphic.
        // By problem statement, this means they can be painted the same color.
        // But then it says "painting all the vertices in a single color is not a good coloring".
        // This is a contradiction or a misunderstanding.
        // Let's assume the sample explanation means:
        // If there exists *any* pair of vertices (u,v) such that rooted(G,u) is NOT isomorphic to rooted(G,v),
        // THEN you cannot paint all vertices in one color.
        // If all vertices ARE isomorphic when rooted, then you *can* paint all vertices in one color.
        // But the sample says "not a good coloring" in this case.
        // This could mean: a good coloring must use at least 2 colors if N > 1.
        // If N=1, colorfulness is 1. If N>1, colorfulness is at least 2.
        // If this interpretation holds, then for N=2, colorfulness is 2. Leaves = 2.

        // Let's re-run the code with this minimum colorfulness constraint.
        // If calculated_colors == 1 and N > 1, then set calculated_colors = 2.
        // This seems to be the simplest interpretation that matches samples.

        // Recalculate original tree values with this constraint:
        // For sample 1 (N=5 path 1-2-3-4-5, 2-3, 3-4, 3-5):
        // 5 distinct hashes, so 5 colors. Leaves = 3.
        // So minColors = 5, minLeaves = 3 initially.

        // Now, iterate through all possible new trees T:
        // T is formed by adding a new vertex (N+1) connected to one of {1..N}.
        // For each `v` from 1 to N:
        //   Create `new_adj` by adding edge (v, N+1).
        //   Calculate `current_colors` and `current_leaves` for `new_adj`.
        //   If `current_colors == 1` and `N+1 > 1`, set `current_colors = 2`.
        //   Update `minColors`, `minLeaves`.

        minColors = Integer.MAX_VALUE;
        minLeaves = Long.MAX_VALUE;

        // Case 0: Original tree
        int currentColors = countDistinctRootedTrees(N, adj);
        if (N > 1 && currentColors == 1) currentColors = 2; // Apply weird rule
        if (currentColors < minColors) {
            minColors = currentColors;
            minLeaves = countLeaves(N, adj);
        } else if (currentColors == minColors) {
            minLeaves = Math.min(minLeaves, countLeaves(N, adj));
        }

        // Case 1: Add one new vertex
        for (int i = 1; i <= N; i++) { // Try connecting new vertex (N+1) to each vertex i
            List<Integer>[] newAdj = new ArrayList[N + 2];
            for (int k = 1; k <= N + 1; k++) {
                newAdj[k] = new ArrayList<>();
            }
            for (int u = 1; u <= N; u++) {
                for (int v : adj[u]) {
                    newAdj[u].add(v);
                }
            }
            newAdj[i].add(N + 1);
            newAdj[N + 1].add(i);

            currentColors = countDistinctRootedTrees(N + 1, newAdj);
            if ((N + 1) > 1 && currentColors == 1) currentColors = 2; // Apply weird rule
            long currentLeaves = countLeaves(N + 1, newAdj);

            if (currentColors < minColors) {
                minColors = currentColors;
                minLeaves = currentLeaves;
            } else if (currentColors == minColors) {
                minLeaves = Math.min(minLeaves, currentLeaves);
            }
        }

        System.out.println(minColors + " " + minLeaves);
        sc.close();
    }

    static long countLeaves(int n, List<Integer>[] graph) {
        long leaves = 0;
        if (n == 1) return 1; // A single node is a leaf
        for (int i = 1; i <= n; i++) {
            if (graph[i].size() == 1) {
                leaves++;
            }
        }
        return leaves;
    }

    static int countDistinctRootedTrees(int n, List<Integer>[] graph) {
        Set<String> uniqueHashes = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            uniqueHashes.add(getTreeHash(i, 0, graph));
        }
        return uniqueHashes.size();
    }
}