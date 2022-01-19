import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Scheduler {
    private int courses ;
    private int[] studentsPerCourse ;
    private int[] degree;
    private int[] blame;

    private LinkedList[] edgeForConflict ;
    private LinkedList<Integer> forChain ;

    private ArrayList<Integer> sequence;

    private int[] slotForCourse ;
    private boolean[] available;

    private int row ;
    private int column;
    private int[][] weightedArray;

    private int[] markThatKemped;
    private int currentChain;

    private LinkedList<TwoNodes> alreadyKemped ;

    public int[] getSlots()
    {
        return slotForCourse;
    }

    public Scheduler(int courses)
    {
        this.courses = courses ;
        this.row = courses;
        this.column = courses;
        studentsPerCourse = new int[courses];
        slotForCourse = new int[courses];
        edgeForConflict = new LinkedList[courses];
        forChain = new LinkedList<>();
        degree = new int[courses];

        blame = new int[courses];

        sequence = new ArrayList<>();

        weightedArray = new int[row][column];

        this.currentChain = 1;
        markThatKemped = new int[courses];

        alreadyKemped = new LinkedList<>();

        for(int i=0 ; i<row ; i++)
        {
            for(int j=0 ; j<column ; j++)
            {
                weightedArray[i][j] = 0;
            }
        }

        for(int i=0 ; i<courses ; i++)
        {
            edgeForConflict[i] = new LinkedList<Integer>();
            degree[i] = 0;
            //markThatKemped[i] = 0;
            blame[i] = 0;
        }

        resetSlot();

        /*for(int i=0 ; i<courses ; i++)
        {
            slotForCourse[i] = -1 ;
        }*/
    }

    public void resetSlot()
    {
        for(int i=0 ; i<courses ; i++)
        {
            slotForCourse[i] = -1 ;
        }
    }

    public void studentCount(int course , int students)
    {
        studentsPerCourse[course] = students ;
    }

    public void addConflict(int course1 , int course2)
    {
        weightedArray[course1][course2]++;
        weightedArray[course2][course1]++;

        if(!edgeForConflict[course1].contains(course2)){
            edgeForConflict[course1].add(course2);
            edgeForConflict[course2].add(course1);

            degree[course1]++;
            degree[course2]++;
        }
    }

    public void assignSlot(int selectedCourse)
    {
        available = new boolean[this.courses];

        Arrays.fill(available , true);

        Iterator<Integer> iterator = edgeForConflict[selectedCourse].iterator();

        while(iterator.hasNext()) {
            int conflictedCourse = iterator.next();

            if (slotForCourse[conflictedCourse] != -1) {
                available[slotForCourse[conflictedCourse]] = false;
            }
        }

        for(int i=0 ; i<this.courses ; i++)
        {
            if(available[i]){
                slotForCourse[selectedCourse] = i ;
                break;
            }
        }

        /*if(this.countSlots() == 0)
        {
            slotForCourse[selectedCourse] = 0 ;
            return;
        }
        int rightNow = this.countSlots();
        LinkedList<Integer> allAvailable = new LinkedList<>();
        for(int i=0 ; i<rightNow ; i++)
        {
            if(available[i]){
                allAvailable.add(i);
                //slotForCourse[selectedCourse] = i ;
                //break;
            }
        }
        if(allAvailable.size() == 0)
        {
            slotForCourse[selectedCourse] = rightNow;
            return;
        }
        Random random = new Random();
        int choose = random.nextInt(allAvailable.size());
        int takeOne = allAvailable.get(choose);
        slotForCourse[selectedCourse] = takeOne;*/

    }

    public int countSlots()
    {
        //int max = 0;
        int max = -1 ;

        for(int i=0 ; i<this.courses ; i++)
        {
            if(max < slotForCourse[i])
                max = slotForCourse[i];
        }

        return max+1 ;
    }

    public void show(String filename) throws IOException {
        File file = new File(filename + ".sol");

        FileWriter fileWriter = new FileWriter(file);

        for (int i=0 ; i<this.courses ; i++)
        {
            fileWriter.write("Course " + (i + 1) + " -> Slot " + (slotForCourse[i] + 1) + "\n");
            //System.out.println("Course " + (i + 1) + " -> Slot " + (slotForCourse[i] + 1));
        }

        fileWriter.close();
    }

    public boolean hasSameColorNeighbor(int course)
    {
        int color = slotForCourse[course];

        for(int i=0 ; i<edgeForConflict[course].size() ; i++)
        {
            int neighbor = (int) edgeForConflict[course].get(i);
            int colorOfNeighbor = slotForCourse[neighbor];

            if(colorOfNeighbor == color)
            {
                //System.out.println((course+1) + " " + (color+1)+ " " + (neighbor+1) + " " + (colorOfNeighbor+1));
                return true;
            }
        }

        return  false;
    }

    public boolean isValid()
    {
        for(int i=0 ; i<this.courses ; i++)
        {
            if(hasSameColorNeighbor(i))
            {
                return false;
            }
        }

        return true;
    }

    public void normal()
    {
        for (int i=0;i<courses;i++)
        {
            assignSlot(i);
        }
    }

    public void random()
    {

        resetSlot();
        //Random random = new Random();
        //int theCourse = random.nextInt(this.courses);

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0 ; i<this.courses ; i++)
        {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        for(int i=0 ; i<this.courses ; i++)
        {
            sequence.add(i);
            assignSlot(list.get(i));
            //System.out.println(list.get(i));
        }
    }

    public void highestDegree()
    {
        resetSlot();

        for(int i=0 ; i<this.courses ; i++)
        {
            int max = -1;
            int maxedNode = 0;

            for(int j=0 ; j<this.courses ; j++)
            {
                if(max < degree[j] & slotForCourse[j] == -1)
                {
                    max = degree[j];
                    maxedNode = j;
                }
            }
            //System.out.println(maxedNode + " " + max);

            sequence.add(maxedNode);
            assignSlot(maxedNode);
        }
    }

    public void largestEnrollment()
    {
        resetSlot();

        for(int i=0 ; i<this.courses ; i++)
        {
            int max = -1;
            int maxedNode = 0;

            for(int j=0 ; j<this.courses ; j++)
            {
                if(max < studentsPerCourse[j] & slotForCourse[j] == -1)
                {
                    max = studentsPerCourse[j];
                    maxedNode = j;
                }
            }
            //System.out.println(maxedNode + " " + max);

            sequence.add(maxedNode);
            assignSlot(maxedNode);
        }
    }

    public void largestWeightedDegree()
    {

        resetSlot();
        /*int row = this.courses ;
        int column = this.courses;

        int[][] weightedArray = new int[row][column];*/

        int[] sumArray = new int[this.courses];

        //int max = -1;
        //int maxedNode = 0;

        for(int i=0 ; i<this.courses ; i++)
        {
            sumArray[i] = 0;
        }

        for(int i=0 ; i<row ; i++)
        {
            for(int j=0 ; j<column ; j++)
            {
                //System.out.print(weightedArray[i][j] + " ");
                sumArray[i] += weightedArray[i][j];
            }
            //System.out.print(sumArray[i]);
            //System.out.println();
        }

        for(int k=0 ; k<this.courses ; k++)
        {
            int max = -1;
            int maxedNode = 0;

            for(int l=0 ; l<this.courses ; l++)
            {
                if(max < sumArray[l] && slotForCourse[l] == -1)
                {
                    max = sumArray[l];
                    maxedNode = l;
                }
            }
            //System.out.println(maxedNode);
            assignSlot(maxedNode);
        }
    }

    public void DSatur()
    {
        resetSlot();

        for (int k = 0; k < this.courses; k++) {

            LinkedList<Integer> maxNodes = new LinkedList<>();
            int max = -1;
            int maxDegree = -1;

            Set<Integer> set = new HashSet<Integer>();

            for (int i = 0; i < this.courses; i++) {
                //Set<Integer> set = new HashSet<Integer>();

                if (slotForCourse[i] == -1) {
                    int adjacentNumber = edgeForConflict[i].size();
                    //Set<Integer> set = new HashSet<Integer>();

                    for (int j = 0; j < adjacentNumber; j++) {
                        int adjacent = (int) edgeForConflict[i].get(j);

                        if (slotForCourse[adjacent] != -1) {
                            set.add(slotForCourse[adjacent]);
                        }
                    }


                    if (max < set.size()) {
                        max = set.size();
                        maxNodes.clear();
                        maxNodes.add(i);
                    } else if (max == set.size()) {
                        maxNodes.add(i);
                    }
                }

                set.clear();
            }

            int selectedNode = -1;

            //System.out.println(maxNodes.size());

            for (int maxNode : maxNodes) {
                if (maxDegree < this.degree[maxNode]) {
                    maxDegree = this.degree[maxNode];
                    selectedNode = maxNode;
                }
            }

            assignSlot(selectedNode);

        }
    }

    public void chaining(int course , int alternateColor)
    {
        //System.out.println();
        //System.out.println(course);
        forChain.add(course);

        int length = edgeForConflict[course].size();
        //System.out.println(course + " " + length);

        for(int i=0 ; i<length ; i++)
        {
            int adjacent = (int) edgeForConflict[course].get(i);
            //System.out.println(adjacent);

            if(!forChain.contains(adjacent) && slotForCourse[adjacent] == alternateColor)
            {
                chaining(adjacent , slotForCourse[course]);
            }
        }
    }

    public void KempeChain()
    {
        //DSatur();
        //largestEnrollment();
        forChain.clear();

        Random random = new Random();
        //Random random2 = new Random();
        //random1 %= this.courses;
        //int randomCourse = 100 ;
        int randomCourse = random.nextInt(this.courses) ;
        int randomNeighbor = -1 ;
        //int temp = random.nextInt(edgeForConflict[randomCourse].size());
        //int randomNeighbor = (int) edgeForConflict[randomCourse].get(2);
        //int randomNeighbor = (int) edgeForConflict[randomCourse].get(temp);

        while(true)
        {
            if(edgeForConflict[randomCourse].size() == 0)
                return;

            int flag = 0;
            //System.out.println(edgeForConflict[randomCourse].size());
            int temp = random.nextInt(edgeForConflict[randomCourse].size());
            //System.out.println(temp);
            randomNeighbor = (int) edgeForConflict[randomCourse].get(temp);

            /*if(alreadyKemped.size() == 0)
                break;*/

            TwoNodes aChain = new TwoNodes(randomCourse , randomNeighbor);

            if(alreadyKemped.size() == 0)
            {
                alreadyKemped.add(aChain);
                break;
            }

            for(int i=0 ; i<alreadyKemped.size() ; i++)
            {
                if(aChain.checkMatch(alreadyKemped.get(i)))
                {
                    flag = 1;
                    break;
                }
            }

            if(flag == 0)
            {
                alreadyKemped.add(aChain);
                break;
            }

            //System.out.println("go");
        }

        /*while(true) {
            int temp = random.nextInt(edgeForConflict[randomCourse].size());
            //int randomNeighbor = (int) edgeForConflict[randomCourse].get(2);
            //int randomNeighbor = (int) edgeForConflict[randomCourse].get(temp);
            randomNeighbor = (int) edgeForConflict[randomCourse].get(temp);

            if(markThatKemped[randomCourse] != markThatKemped[randomNeighbor])
            {
                break;
            }
            else if(markThatKemped[randomCourse] == 0 && markThatKemped[randomNeighbor] == 0)
            {
                break;
            }
        }*/
        //int randomNeighbor = 5 ;

        int color1 = slotForCourse[randomCourse];
        int color2 = slotForCourse[randomNeighbor];

        //chaining(randomCourse , slotForCourse[randomNeighbor]);
        chaining(randomCourse , color2);

        /*System.out.println("chain : " + forChain.size());

        for(int temp : forChain)
        {
            System.out.println(temp+1 + " " + (slotForCourse[temp]+1));
        }*/

        for(int node : forChain)
        {
            //markThatKemped[node] = this.currentChain ;
            //System.out.println(node);
            if(slotForCourse[node] == color1)
            {
                slotForCourse[node] = color2;
            }
            else
            {
                slotForCourse[node] = color1;
            }
        }

        /*System.out.println();

        for(int temp : forChain)
        {
            System.out.println(temp+1 + " " + (slotForCourse[temp]+1));
        }*/
    }

    public void MultipleKempeChain(int how)
    {
        DSatur();

        for(int i=0 ; i<how ; i++)
        {
            KempeChain();
        }
    }

    /*public boolean[] getAdjacentColors(int node)
    {
        int theColor = slotForCourse[node];

        boolean[] adjacentColor = new boolean[this.countSlots()];

        for(int i=0 ; i<adjacentColor.length ; i++)
        {
            adjacentColor[i] = false;
        }

        for(int i=0 ; i<edgeForConflict[node].size() ; i++)
        {
            int adj = (int) edgeForConflict[theColor].get(i);
            int color = slotForCourse[adj];
            adjacentColor[color] = true ;
        }

        return adjacentColor;
    }*/

    public boolean hasAdjacentColor(int node , int color)
    {
        //System.out.println("god");

        for(int i=0 ; i<edgeForConflict[node].size() ; i++)
        {
            int temp = (int) edgeForConflict[node].get(i);
            if(slotForCourse[temp] == color)
            {
                return true;
            }
        }
        return false;
    }

    public void PairSwap()
    {
        //DSatur();
        //random();
        //highestDegree();

        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0 ; i<this.courses ; i++)
        {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        //int firstNode = list.get(0);
        //int firstColor = slotForCourse[firstNode] ;
        ArrayList<Integer> firstNode = new ArrayList<>();

        //System.out.println(list.size());

        for(int i=0 ; i<list.size() ; i++)
        {
            Set<Integer> set = new HashSet<Integer>();
            int temp = list.get(i);

            for(int j=0 ; j<edgeForConflict[temp].size() ; j++)
            {
                int neighbor = (int) edgeForConflict[temp].get(j);
                set.add(slotForCourse[neighbor]);
            }

            //System.out.println(set.size());

            if(set.size() < countSlots()-1)
            {
                firstNode.add(temp) ;
                //break;
            }
        }

        int secondNode = -1;

        for(int fn=0 ; fn<firstNode.size() ; fn++) {
            int firstColor = slotForCourse[firstNode.get(fn)];

            System.out.println((firstNode.get(fn) + 1) + " " + (firstColor + 1));

            ArrayList<Integer> secondColor = new ArrayList<>();
            //int secondNode = -1;

            boolean[] adjacentColor = new boolean[this.countSlots()];

            //adjacentColor = getAdjacentColors(firstNode);

            for (int i = 0; i < adjacentColor.length; i++) {
                adjacentColor[i] = false;
            }

            //System.out.println(edgeForConflict[firstNode].size());

            for (int i = 0; i < edgeForConflict[firstNode.get(fn)].size(); i++) {
                int node = (int) edgeForConflict[firstNode.get(fn)].get(i);
                int color = slotForCourse[node];
                adjacentColor[color] = true;
            }

            adjacentColor[firstColor] = true;

            int why = 0;

            for (int i = 0; i < adjacentColor.length; i++) {
                if (adjacentColor[i]) {
                    System.out.print(i + " ");
                    why++;
                } else {
                    System.out.print("(" + i + ")" + " ");
                }
                //System.out.println();
            }

            //System.out.println();
            //System.out.println(why);
            //System.out.println();

            for (int i = 0; i < adjacentColor.length; i++) {
                if (adjacentColor[i] == false) {
                    secondColor.add(i);
                    //System.out.println(secondColor);
                    //break ;
                }
            }

            int finalSecondColor = 0;

            for (int sc = 0; sc < secondColor.size(); sc++) {
                for (int i = 0; i < list.size(); i++) {
                    int temp = list.get(i);

                    if (slotForCourse[temp] == secondColor.get(sc) && !hasAdjacentColor(temp, firstColor)) {
                        System.out.println("hell");
                        secondNode = temp;
                        break;
                    }
                }
                if (secondNode == -1) {
                    continue;
                } else {
                    finalSecondColor = sc;
                    break;
                }
            }

            if (secondNode != -1) {

                System.out.println((secondNode + 1) + " " + (secondColor.get(finalSecondColor) + 1));

                slotForCourse[firstNode.get(fn)] = secondColor.get(finalSecondColor);
                slotForCourse[secondNode] = firstColor;

                System.out.println((firstNode.get(fn) + 1) + " " + (slotForCourse[firstNode.get(fn)] + 1));
                System.out.println((secondNode + 1) + " " + (slotForCourse[secondNode] + 1));

                break;
            } else {
                continue;
            }
        }

        if(secondNode == -1)
        {
            System.out.println();
            System.out.println("Pair Swap not possible");
        }
    }

    public void SWO()
    {
        //random();
        //highestDegree();
        largestEnrollment();

        double alpha = 0.8;
        int b = 5 ;
        int max = Integer.MAX_VALUE;

        for(int i=0 ; i<10 ; i++)
        {
            resetSlot();

            for(int j=0 ; j<this.courses ; j++)
            {
                assignSlot(sequence.get(j));
            }

            int current = this.countSlots();

            if(current < max) {
                //System.out.println(max);
                max = current;
                //System.out.println(max);
            }

            for(int j=0 ; j<this.courses ; j++)
            {
                blame[j] = j;

                if((double) slotForCourse[sequence.get(j)] > alpha*(double)current )
                {
                    blame[j] += b;
                }
            }

            for(int j=0 ; j<this.courses-1 ; j++)
            {
                for(int k=j+1 ; k<this.courses ; k++)
                {
                    if(blame[k] < blame[j])
                    {
                        int temp = sequence.get(j);
                        int temp2 = sequence.get(k);

                        sequence.set(j , temp2);
                        sequence.set(k , temp);

                        int temp3 = blame[j];
                        blame[j] = blame[k];
                        blame[k] = temp3;
                    }
                }
            }
        }

        //System.out.println(max);
    }

}
