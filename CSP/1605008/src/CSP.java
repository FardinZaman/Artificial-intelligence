import java.util.LinkedList;
import java.util.Queue;

public class CSP {
    public Square square ;

    CSP(Square square)
    {
        this.square = square;
    }

    public boolean checkConsistency(Box var , int value)
    {
        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == value && i != row)
            {
                return false;
            }
            if(this.square.square[row][i].getAssignedNumber() == value && i != column)
            {
                return false;
            }
        }

        return true;
    }

    public Box randomChoice()
    {
        Box chosen;

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                if(this.square.square[i][j].getAssignedNumber() == 0)
                {
                    chosen = this.square.square[i][j];
                    return chosen;
                }
            }
        }

        return null;
    }

    public void removeFromDomain(Box var , int value)
    {
        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row) {
                this.square.square[i][column].removeAnAvailableValue(value);
            }

            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column) {
                this.square.square[row][i].removeAnAvailableValue(value);
            }
        }
    }

    public boolean removeFromDomainAndCheck(Box var , int value)
    {
        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row) {
                this.square.square[i][column].removeAnAvailableValue(value);

                if(this.square.square[i][column].valuesAvailable.size() == 0)
                    return false;
            }

            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column) {
                this.square.square[row][i].removeAnAvailableValue(value);

                if(this.square.square[row][i].valuesAvailable.size() == 0)
                    return false;
            }
        }

        return true;
    }

    public void addToDomain(Box var , int value)
    {
        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row && checkConsistency(this.square.square[i][column] , value) == true) {
                if(this.square.square[i][column].valuesAvailable.contains(value) == false) {
                    this.square.square[i][column].valuesAvailable.add(value);
                }
            }

            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column && checkConsistency(this.square.square[row][i] , value) == true) {
                if(this.square.square[row][i].valuesAvailable.contains(value) == false) {
                    this.square.square[row][i].valuesAvailable.add(value);
                }
            }
        }
    }

    public Box smallestDomainFirst()
    {
        Box chosen = null;
        int smallestDomain = 100;

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                if(this.square.square[i][j].getAssignedNumber() == 0 && this.square.square[i][j].valuesAvailable.size() < smallestDomain)
                {
                    chosen = this.square.square[i][j];
                    smallestDomain = this.square.square[i][j].valuesAvailable.size();
                }
            }
        }

        return chosen;
    }

    public int countUnassignedDegree(Box var)
    {
        int counter = 0;
        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row)
            {
                counter++;
            }
            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column)
            {
                counter++;
            }
        }

        return counter;
    }

    public Box maximumDynamicDegree()
    {
        Box chosen = null;
        int maxDynDeg = -1;

//        int unchanged = 10;
//        double minimumratio = 100.0;

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                //double ratio;
                //int unassignedDegree = countUnassignedDegree(this.square.square[i][j]);
                if(this.square.square[i][j].getAssignedNumber() == 0)
                {
                    int unassignedDegree = countUnassignedDegree(this.square.square[i][j]);

//                    if(unassignedDegree == 0) {
//                        //System.out.println("Zero");
//                        ratio = 90.0;
//                    }
//                    else
//                    {
//                        ratio = unchanged / unassignedDegree;
//                    }
//
//                    if(ratio < minimumratio)
//                    {
//                        minimumratio = ratio;
//                        chosen = this.square.square[i][j];
//                    }

                    if(unassignedDegree > maxDynDeg) {
                        System.out.println("go");
                        maxDynDeg = unassignedDegree;
                        chosen = this.square.square[i][j];
                    }
                }
            }
        }

        return chosen;
    }

    public Box brelaz()
    {
        Box chosen = null;
        int smallestDomain = 100;
        int maxDynDeg = -1;
        LinkedList<Box> sameDomainVariables = new LinkedList<>();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                if(this.square.square[i][j].getAssignedNumber() == 0)
                {
                    if(this.square.square[i][j].valuesAvailable.size() < smallestDomain) {
                        smallestDomain = this.square.square[i][j].valuesAvailable.size();
                        sameDomainVariables.clear();
                        sameDomainVariables.add(this.square.square[i][j]);
                    }
                    else if(this.square.square[i][j].valuesAvailable.size() == smallestDomain) {
                        sameDomainVariables.add(this.square.square[i][j]);
                    }
                }
            }
        }

        for(int k=0 ; k<sameDomainVariables.size() ; k++)
        {
            if(countUnassignedDegree(sameDomainVariables.get(k)) > maxDynDeg)
            {
                maxDynDeg = countUnassignedDegree(sameDomainVariables.get(k));
                chosen = sameDomainVariables.get(k);
            }
        }

        return chosen;
    }

    public Box domddeg()
    {
        Box chosen = null;
        double minimumRatio = 100.0;

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                double ratio ;
                if(this.square.square[i][j].getAssignedNumber() == 0)
                {
                    int unassignedDegree = countUnassignedDegree(this.square.square[i][j]);
                    if(unassignedDegree == 0) {
                        ratio = 90.0;
                    }
                    else {
                        ratio = this.square.square[i][j].valuesAvailable.size() / unassignedDegree;
                    }
                    if(ratio < minimumRatio)
                    {
                        minimumRatio = ratio;
                        chosen = this.square.square[i][j];
                    }
                }
            }
        }

        return chosen;
    }

    public boolean Backtracking()
    {
        //Container.numberOfNodes++;

        //System.out.println(this.square.stillToBeAssigned);
        if(this.square.stillToBeAssigned == 0)
            return true;

        //Box var = this.randomChoice();
        //Box var = this.smallestDomainFirst();
        //Box var = this.maximumDynamicDegree();
        //Box var = this.brelaz();
        Box var = this.domddeg();

        if(var.valuesAvailable.size() == 0) {
            Container.numberOfFails++;
            return false;
        }

        for(int i=0 ; i<var.valuesAvailable.size() ; i++)
        {
            int value = var.valuesAvailable.get(i);

            if(this.checkConsistency(var , value) == true)
            {
                Container.numberOfNodes++;

                var.setAssignedNumber(value);
                this.square.alreadyAssigned++;
                this.square.stillToBeAssigned--;
                removeFromDomain(var , value);

                boolean result = Backtracking();
                if(result == true)
                    return true;

                var.setAssignedNumber(0);
                this.square.alreadyAssigned--;
                this.square.stillToBeAssigned++;
                addToDomain(var , value);

                Container.numberOfBackTrack++;
            }
        }
        return false;
    }

    public boolean ForwardChecking()
    {
        //Container.numberOfNodes++;

        if(this.square.stillToBeAssigned == 0)
            return true;

        //Box var = this.randomChoice();
        //Box var = this.smallestDomainFirst();
        //Box var = this.maximumDynamicDegree();
        //Box var = this.brelaz();
        Box var = this.domddeg();

        if(var.valuesAvailable.size() == 0) {
            Container.numberOfFails++;
            return false;
        }

        for(int i=0 ; i<var.valuesAvailable.size() ; i++)
        {
            int value = var.valuesAvailable.get(i);

            if(this.checkConsistency(var , value) == true)
            {
                Container.numberOfNodes++;

                var.setAssignedNumber(value);
                this.square.alreadyAssigned++;
                this.square.stillToBeAssigned--;
                boolean stillOK = removeFromDomainAndCheck(var , value);

                if(stillOK == true) {
                    //System.out.println("Go");
                    boolean result = ForwardChecking();
                    if (result == true)
                        return true;
                }
                else
                {
                    Container.numberOfFails++;
                    //System.out.println("Early Backtracking......");
                }

                var.setAssignedNumber(0);
                this.square.alreadyAssigned--;
                this.square.stillToBeAssigned++;
                addToDomain(var , value);

                Container.numberOfBackTrack++;
            }
        }
        return false;
    }

    public boolean Revise(BoxPair boxPair)
    {
        boolean revised = false;
        Box first = boxPair.first;
        Box second = boxPair.second;

        if(second.valuesAvailable.size() == 1) {
            //System.out.println(second.valuesAvailable.size());
            for (int i = 0; i < first.valuesAvailable.size(); i++) {
                //System.out.println(second.valuesAvailable.size());
                int value = first.valuesAvailable.get(i);
                //System.out.println(second.valuesAvailable.size());

//                if(second.valuesAvailable.size() == 0)
//                    System.out.println("What ??");

                if (second.valuesAvailable.get(0) == value) {
                    //System.out.println(second.valuesAvailable.size());
                    first.removeAnAvailableValue(value);
                    revised = true;
                    //System.out.println(second.valuesAvailable.size());
                }
            }
        }

        return revised;
    }

    public boolean AC3(Box var)
    {
        Queue<BoxPair> queue = new LinkedList<>();

//        for(int i=0 ; i<this.square.getArmSize() ; i++)
//        {
//            for(int j=0 ; j<this.square.getArmSize() ; j++)
//            {
//                if(this.square.square[i][j].getAssignedNumber() == 0)
//                {
//                    Box first = this.square.square[i][j];
//                    int row = first.getRow();
//                    int column = first.getColumn();
//
//                    for(int k=0 ; k<this.square.getArmSize() ; k++)
//                    {
//                        if(this.square.square[k][column].getAssignedNumber() == 0 && k != row)
//                        {
//                            Box second = this.square.square[k][column];
//                            BoxPair boxPair = new BoxPair(first , second);
//                            queue.add(boxPair);
//                        }
//                        if(this.square.square[row][k].getAssignedNumber() == 0 && k != column)
//                        {
//                            Box second = this.square.square[row][k];
//                            BoxPair boxPair = new BoxPair(first , second);
//                            queue.add(boxPair);
//                        }
//                    }
//                }
//            }
//        }

        int row = var.getRow();
        int column = var.getColumn();

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row)
            {
                Box first = this.square.square[i][column];
                int rowOfFirst = first.getRow();
                int columnOfFirst = first.getColumn();

                for(int j=0 ; j<this.square.getArmSize() ; j++)
                {
                    if(this.square.square[j][columnOfFirst].getAssignedNumber() == 0 && j != rowOfFirst)
                    {
                        Box second = this.square.square[j][columnOfFirst];
                        BoxPair boxPair = new BoxPair(first , second);
                        queue.add(boxPair);
                    }
                    if(this.square.square[rowOfFirst][j].getAssignedNumber() == 0 && j != columnOfFirst)
                    {
                        Box second = this.square.square[rowOfFirst][j];
                        BoxPair boxPair = new BoxPair(first , second);
                        queue.add(boxPair);
                    }
                }
            }
            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column)
            {
                Box first = this.square.square[row][i];
                int rowOfFirst = first.getRow();
                int columnOfFirst = first.getColumn();

                for(int j=0 ; j<this.square.getArmSize() ; j++)
                {
                    if(this.square.square[j][columnOfFirst].getAssignedNumber() == 0 && j != rowOfFirst)
                    {
                        Box second = this.square.square[j][columnOfFirst];
                        BoxPair boxPair = new BoxPair(first , second);
                        queue.add(boxPair);
                    }
                    if(this.square.square[rowOfFirst][j].getAssignedNumber() == 0 && j != columnOfFirst)
                    {
                        Box second = this.square.square[rowOfFirst][j];
                        BoxPair boxPair = new BoxPair(first , second);
                        queue.add(boxPair);
                    }
                }
            }
        }

        while(queue.isEmpty() == false)
        {
            BoxPair head = queue.remove();

            if(Revise(head) == true)
            {
                if(head.first.valuesAvailable.size() == 0) {
                    return false;
                }

                Box first = head.first;
                int row2 = first.getRow();
                int column2 = first.getColumn();

                for(int i=0 ; i<this.square.getArmSize() ; i++)
                {
                    if(this.square.square[i][column2] != head.second && this.square.square[i][column2].getAssignedNumber() == 0 && i != row2)
                    {
                        Box neighbor = this.square.square[i][column2];
                        BoxPair boxPair = new BoxPair(neighbor , first);
                        queue.add(boxPair);
                    }
                    if(this.square.square[row2][i] != head.second && this.square.square[row2][i].getAssignedNumber() == 0 && i != column2)
                    {
                        Box neighbor = this.square.square[row2][i];
                        BoxPair boxPair = new BoxPair(neighbor , first);
                        queue.add(boxPair);
                    }
                }
            }
        }

        return true;
    }

    public void restorePreviousVersion()
    {
        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            for(int j=0 ; j<this.square.getArmSize() ; j++)
            {
                if(this.square.square[i][j].getAssignedNumber() == 0)
                {
                    Box box = this.square.square[i][j];

                    for(int k=0 ; k<this.square.getArmSize() ; k++)
                    {
                        int value = k+1;
                        if(checkConsistency(box , value) == true && box.valuesAvailable.contains(value) == false)
                        {
                            box.valuesAvailable.add(value);
                        }
                    }
                }
            }
        }
    }

    public boolean alarmNeighbors(Box box)
    {
        int row = box.getRow();
        int column = box.getColumn();
        int variable = box.valuesAvailable.get(0);

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row)
            {
                if(this.square.square[i][column].valuesAvailable.size() == 1)
                {
                    if(this.square.square[i][column].valuesAvailable.get(0) == variable)
                    {
                        return false;
                    }
                }

                if(this.square.square[i][column].valuesAvailable.contains(variable) == true)
                {
                    this.square.square[i][column].removeAnAvailableValue(variable);
                }
            }
            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column)
            {
                if(this.square.square[row][i].valuesAvailable.size() == 1)
                {
                    if(this.square.square[row][i].valuesAvailable.get(0) == variable)
                    {
                        return false;
                    }
                }

                if(this.square.square[row][i].valuesAvailable.contains(variable) == true)
                {
                    this.square.square[row][i].removeAnAvailableValue(variable);
                }
            }
        }

        return true;
    }

    public boolean AC3LA(Box var)
    {
        int row = var.getRow();
        int column = var.getColumn();

        //boolean checker = true;

        for(int i=0 ; i<this.square.getArmSize() ; i++)
        {
            if(this.square.square[i][column].getAssignedNumber() == 0 && i != row)
            {
                if(this.square.square[i][column].valuesAvailable.size() == 1)
                {
                    boolean watch = alarmNeighbors(this.square.square[i][column]);
                    if(watch == false)
                        return false;
                }
            }
            if(this.square.square[row][i].getAssignedNumber() == 0 && i != column)
            {
                if(this.square.square[row][i].valuesAvailable.size() == 1)
                {
                    boolean watch = alarmNeighbors(this.square.square[row][i]);
                    if(watch == false)
                        return false;
                }
            }
        }

        return true;
    }

    public boolean MAC()
    {
        //Container.numberOfNodes++;
//        for(int i=0 ; i<this.square.getArmSize() ; i++)
//        {
//            for(int j=0 ; j<this.square.getArmSize() ; j++)
//            {
//                this.square.square[i][j].printAvailableValues();
//            }
//        }
//        System.out.println("\n\n\n");

        if(this.square.stillToBeAssigned == 0)
            return true;

        //Box var = this.randomChoice();
        //Box var = this.smallestDomainFirst();
        Box var = this.maximumDynamicDegree();
        //Box var = this.brelaz();
        //Box var = this.domddeg();

        if(var.valuesAvailable.size() == 0) {
            //System.out.println("why ???");
            Container.numberOfFails++;
            return false;
        }

        for(int i=0 ; i<var.valuesAvailable.size() ; i++)
        {
            int value = var.valuesAvailable.get(i);

            if(this.checkConsistency(var , value) == true)
            {
                Container.numberOfNodes++;

                var.setAssignedNumber(value);
                this.square.alreadyAssigned++;
                this.square.stillToBeAssigned--;
                boolean stillOK = removeFromDomainAndCheck(var , value);

                if(stillOK == false)
                {
                    //System.out.println("early go back ....");
                    var.setAssignedNumber(0);
                    this.square.alreadyAssigned--;
                    this.square.stillToBeAssigned++;
                    addToDomain(var , value);

                    Container.numberOfBackTrack++;
                    Container.numberOfFails++;
                }
                else
                {
                    LinkedList[][] store = new LinkedList[this.square.getArmSize()][this.square.getArmSize()];
                    for(int u=0 ; u<this.square.getArmSize() ; u++)
                    {
                        for(int v=0 ; v<this.square.getArmSize() ; v++)
                        {
                            store[u][v] = new LinkedList<Integer>();
                            Box box = this.square.square[u][v];
                            if(box.getAssignedNumber() == 0)
                            {
                                for(int w=0 ; w<box.valuesAvailable.size() ; w++)
                                {
                                    int theValue = box.valuesAvailable.get(w);
                                    store[u][v].add(theValue);
                                }
                            }
                        }
                    }

                    boolean check = AC3(var);
                    //boolean check = AC3LA(var);
                    if(check == true)
                    {
                        //System.out.println("yo");
                        boolean result = MAC();
                        if(result == true)
                            return true;
                    }
                    else
                    {
                        Container.numberOfFails++;
                    }

                        System.out.println("going back ....");
                        var.setAssignedNumber(0);
                        this.square.alreadyAssigned--;
                        this.square.stillToBeAssigned++;
                        //restorePreviousVersion();
                        addToDomain(var , value);

                        Container.numberOfBackTrack++;

                        for(int x=0 ; x<this.square.getArmSize() ; x++)
                        {
                            for(int y=0 ; y<this.square.getArmSize() ; y++)
                            {
                                //LinkedList linkedList = store[x][y];
                                if(store[x][y].size() != 0)
                                {
                                    //Box box = this.square.square[x][y];
                                    //box.valuesAvailable = new LinkedList<>();
                                    for(int z=0 ; z<store[x][y].size() ; z++)
                                    {
                                        int theValue = (Integer) store[x][y].get(z);
                                        if(this.square.square[x][y].valuesAvailable.contains(theValue) == false) {
                                            this.square.square[x][y].valuesAvailable.add(theValue);
                                        }
                                    }
                                }
                            }
                        }

                }
            }
        }

        //System.out.println("why???");
        return false;
    }

}
