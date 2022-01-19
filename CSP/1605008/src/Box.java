import java.util.LinkedList;

public class Box {
    public int assignedNumber;
    public LinkedList<Integer> valuesAvailable;

    public int row;
    public int column;

    Box()
    {
        this.assignedNumber = 0;
        this.valuesAvailable = new LinkedList<>();

        this.row = -1;
        this.column = -1;
    }

    public void setAssignedNumber(int number)
    {
        this.assignedNumber = number;
    }

    public int getAssignedNumber()
    {
        return  this.assignedNumber;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getRow()
    {
        return this.row;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public int getColumn()
    {
        return this.column;
    }

    public void initializeValues(int armOfSquare)
    {
        for(int i=0 ; i<armOfSquare ; i++)
        {
            this.valuesAvailable.add(i+1);
        }
    }

    public void removeAnAvailableValue(int toBeRemoved)
    {
        for(int i=0 ; i<this.valuesAvailable.size() ; i++)
        {
            if(this.valuesAvailable.get(i) == toBeRemoved)
            {
                this.valuesAvailable.remove(i);

                break;
            }
        }
    }

    public void clearAvailableValues()
    {
        this.valuesAvailable.clear();
    }

    public void printAvailableValues()
    {
        if(this.valuesAvailable.size() == 0)
        {
            System.out.println(this.getAssignedNumber() + " - Null");
            return;
        }

        System.out.print(this.getAssignedNumber() + " - ");
        for(int i=0 ; i<this.valuesAvailable.size() ; i++)
        {
            System.out.print(this.valuesAvailable.get(i) + " ");
        }
        System.out.println();
    }
}
