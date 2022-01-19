import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Container {

    static PriorityQueue<Node> pq = new PriorityQueue<Node>(new NodeComparator());
    static PriorityQueue<Node> pq2 = new PriorityQueue<Node>(new NodeComparator2());
    //static Goal goal = new Goal();
    static int[][] goal = new int[4][4];

    public static void main(String[] args) throws FileNotFoundException {

        int[][] state;
        Node node;
        Scanner scanner = new Scanner(new File("input.txt"));

        int n = scanner.nextInt();

        for(int i=0 ; i<4 ; i++)
        {
            for(int j=0 ; j<4 ; j++)
            {
                goal[i][j] = scanner.nextInt();
            }
        }

        for(int k=0 ; k<n-1 ; k++) {

            state = new int[4][4];

            //Scanner scanner = new Scanner(System.in);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    state[i][j] = scanner.nextInt();
                }
            }

            node = new Node(4, state);
            node.print();

            System.out.println(node.countInversion());
            System.out.println(node.displacementHeuristic());
            System.out.println(node.manhattanHeuristic());

            long startTime = System.currentTimeMillis();
            pq.add(node);
            node.solve();
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Elapsed time : " + elapsedTime);

            long startTime2 = System.currentTimeMillis();
            pq2.add(node);
            node.solve2();
            long elapsedTime2 = System.currentTimeMillis() - startTime2;
            System.out.println("Elapsed time : " + elapsedTime2);

            System.out.println();
            System.out.println();

            pq.clear();
            pq2.clear();
        }

    }
}
