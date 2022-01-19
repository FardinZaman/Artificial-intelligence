import java.util.ArrayList;
import java.util.Stack;

public class Node {

    int n;
    int[][] state;
    //int[][] goal;

    int rowOfZero;
    int columnOfZero;

    int depth;

    int move;

    Node parentNode;

    public Node(int n , int[][] state)
    {
        this.n = n;
        this.state = state;

        this.depth = 0;

        this.move = 0;

        this.parentNode = null;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(this.state[i][j] == 0)
                {
                    rowOfZero = i;
                    columnOfZero = j;
                }
            }
        }

        /*goal = new int[n][n];

        int k = 1;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(k == n*n)
                    goal[i][j] = 0;
                else
                {
                    goal[i][j] = k;
                    k++;
                }
            }
        }*/
    }

    void print()
    {
        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                System.out.print(this.state[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("");
    }

    int countInversion()
    {
        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                list.add(this.state[i][j]);
            }
        }

        int invcount = 0;

        for(int i=0 ; i<list.size()-1 ; i++)
        {
            for(int j=i+1 ; j<list.size() ; j++)
            {
                if(list.get(i) != 0 && list.get(j) != 0 && list.get(i) > list.get(j))
                {
                    invcount++;
                }
            }
        }

        return  invcount;
    }

    int findZeroFromBottom()
    {
        return this.n - this.rowOfZero;
    }

    boolean isSolvable()
    {
        int count = this.countInversion();

        if(n%2 == 1)
        {
            if(count%2 == 0)
                return true;
        }

        else
        {
            int zero = this.findZeroFromBottom();

            if(zero%2 == 0 && count%2 == 1)
                return true;

            if(zero%2 == 1 && count%2 == 0)
                return true;
        }

        return false;
    }

    Node moveUp()
    {
        if(this.rowOfZero == 0)
            return null;

        int[][] newState = new int[this.n][this.n];

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                newState[i][j] = this.state[i][j];
            }
        }

        int temp = newState[this.rowOfZero-1][this.columnOfZero];
        newState[this.rowOfZero-1][this.columnOfZero] = 0;
        newState[this.rowOfZero][this.columnOfZero] = temp;

        Node aChild = new Node(n , newState);

        aChild.columnOfZero = this.columnOfZero;
        aChild.rowOfZero = this.rowOfZero - 1;
        aChild.depth = this.depth + 1;

        aChild.move = 1;

        aChild.parentNode = this;

        //aChild.print();

        return aChild;
    }

    Node moveDown()
    {
        if(this.rowOfZero == this.n-1)
            return null;

        int[][] newState = new int[this.n][this.n];

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                newState[i][j] = this.state[i][j];
            }
        }

        int temp = newState[this.rowOfZero+1][this.columnOfZero];
        newState[this.rowOfZero+1][this.columnOfZero] = 0;
        newState[this.rowOfZero][this.columnOfZero] = temp;

        Node aChild = new Node(n , newState);

        aChild.columnOfZero = this.columnOfZero;
        aChild.rowOfZero = this.rowOfZero + 1;
        aChild.depth = this.depth + 1;

        aChild.move = 2;

        aChild.parentNode = this;

        //aChild.print();

        return aChild;
    }

    Node moveRight()
    {
        if(this.columnOfZero == this.n-1)
            return null;

        int[][] newState = new int[this.n][this.n];

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                newState[i][j] = this.state[i][j];
            }
        }

        int temp = newState[this.rowOfZero][this.columnOfZero+1];
        newState[this.rowOfZero][this.columnOfZero+1] = 0;
        newState[this.rowOfZero][this.columnOfZero] = temp;

        Node aChild = new Node(n , newState);

        aChild.columnOfZero = this.columnOfZero + 1;
        aChild.rowOfZero = this.rowOfZero;
        aChild.depth = this.depth + 1;

        aChild.move = 3;

        aChild.parentNode = this;

        //aChild.print();

        return aChild;
    }

    Node moveLeft()
    {
        if(this.columnOfZero == 0)
            return null;

        int[][] newState = new int[this.n][this.n];

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                newState[i][j] = this.state[i][j];
            }
        }

        int temp = newState[this.rowOfZero][this.columnOfZero-1];
        newState[this.rowOfZero][this.columnOfZero-1] = 0;
        newState[this.rowOfZero][this.columnOfZero] = temp;

        Node aChild = new Node(n , newState);

        aChild.columnOfZero = this.columnOfZero - 1;
        aChild.rowOfZero = this.rowOfZero;
        aChild.depth = this.depth + 1;

        aChild.move = 4;

        aChild.parentNode = this;

        //aChild.print();

        return aChild;
    }

    int displacementHeuristic()
    {
        /*int[][] goal = new int[n][n];

        int k = 1;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(k == n*n)
                    goal[i][j] = 0;
                else
                {
                    goal[i][j] = k;
                    k++;
                }
            }
        }*/

        int h = 0;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(this.state[i][j] != Container.goal[i][j] && this.state[i][j] != 0)
                    h++;
            }
        }

        return this.depth + h;
    }

    int manhattanHeuristic()
    {
        int h = 0;

        int goalRow;
        int goalColumn;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(this.state[i][j] != 0) {
                    goalRow = (this.state[i][j] - 1) / n;
                    goalColumn = (this.state[i][j] - 1) % n;

                /*if(this.state[i][j] == 0)
                {
                    goalRow = n-1;
                    goalColumn = n-1;
                }*/

                    int temp = Math.abs(goalRow - i) + Math.abs(goalColumn - j);
                    h += temp;
                }
            }
        }

        return h + this.depth;
    }

    boolean match()
    {
        /*int[][] goal = new int[n][n];

        int k = 1;

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(k == n*n)
                    goal[i][j] = 0;
                else
                {
                    goal[i][j] = k;
                    k++;
                }
            }
        }*/

        for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                if(this.state[i][j] != Container.goal[i][j])
                    return false;
            }
        }

        return true;
    }

    public void solve()
    {
        int exploredNodes = 1;

        if(!isSolvable())
        {
            System.out.println("Not solvable");
            return;
        }

        while(!Container.pq.isEmpty())
        {
            Node node = Container.pq.poll();

            //node.print();

            if(node.match()) {
                node.printPath();
                System.out.println("Explored Nodes : " + exploredNodes);
                break;
            }

            Node tempNode = node.moveUp();
            if(tempNode != null && node.move != 2) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveDown();
            if(tempNode != null && node.move != 1) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveRight();
            if(tempNode != null && node.move != 4) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveLeft();
            if(tempNode != null && node.move != 3) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq.add(tempNode);
                //exploredNodes++;
            }
        }
    }

    public void solve2()
    {
        int exploredNodes = 1;

        if(!isSolvable())
        {
            System.out.println("Not solvable");
            return;
        }

        while(!Container.pq2.isEmpty())
        {
            Node node = Container.pq2.poll();

            //node.print();

            if(node.match()) {
                node.printPath();
                System.out.println("Explored Nodes : " + exploredNodes);
                break;
            }

            Node tempNode = node.moveUp();
            if(tempNode != null && node.move != 2) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq2.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveDown();
            if(tempNode != null && node.move != 1) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq2.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveRight();
            if(tempNode != null && node.move != 4) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq2.add(tempNode);
                //exploredNodes++;
            }

            tempNode = node.moveLeft();
            if(tempNode != null && node.move != 3) {
                exploredNodes++;
                if(tempNode.match())
                {
                    tempNode.printPath();
                    System.out.println("Explored Nodes : " + exploredNodes);
                    break;
                }
                Container.pq2.add(tempNode);
                //exploredNodes++;
            }
        }
    }

    void printPath()
    {
        Stack<Node> stack = new Stack<>();

        Node node = this;

        int cost = 0;

        while(node.parentNode != null)
        {
            stack.push(node);
            node = node.parentNode;
        }

        System.out.println("The path : ");

        while(!stack.empty())
        {
            stack.pop().print();
            cost++;

            System.out.println("");
        }

        System.out.println("Cost : " + cost);
    }

}
