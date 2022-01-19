import java.util.Comparator;

public class NodeComparator2 implements Comparator<Node>{

    @Override
    public int compare(Node n1, Node n2) {

        if(n1.manhattanHeuristic() < n2.manhattanHeuristic())
            return -1;
        else if(n1.manhattanHeuristic() > n2.manhattanHeuristic())
            return 1;

        return 0;
    }
}
