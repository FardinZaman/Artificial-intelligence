public class Square {
    public Box[][] square;
    public int armSize;

    public int alreadyAssigned;
    public int stillToBeAssigned;

    Square(int number)
    {
        this.armSize = number;
        this.square = new Box[this.armSize][this.armSize];

        this.alreadyAssigned = 0;
        this.stillToBeAssigned = 0;
    }

    public int getArmSize()
    {
        return this.armSize;
    }

    public void setSquare(int[][] givenSquare)
    {
        for(int i=0 ; i<this.armSize ; i++)
        {
            for(int j=0 ; j<this.armSize ; j++)
            {
                this.square[i][j] = new Box();
                this.square[i][j].setRow(i);
                this.square[i][j].setColumn(j);
                this.square[i][j].setAssignedNumber(givenSquare[i][j]);
                this.square[i][j].initializeValues(this.armSize);
            }
        }

        for(int i=0 ; i<this.armSize ; i++)
        {
            for(int j=0 ; j<this.armSize ; j++)
            {
                if(this.square[i][j].getAssignedNumber() != 0)
                {
                    int toBeRemoved = this.square[i][j].getAssignedNumber();

                    for(int k=0 ; k<this.armSize ; k++)
                    {
                        this.square[i][k].removeAnAvailableValue(toBeRemoved);
                        this.square[k][j].removeAnAvailableValue(toBeRemoved);
                    }

                    this.square[i][j].clearAvailableValues();

                    this.alreadyAssigned++;
                }
            }
        }

        this.stillToBeAssigned = (this.getArmSize()*this.getArmSize())-this.alreadyAssigned;
    }

    public void printSquare()
    {
        for(int i=0 ; i<this.armSize ; i++)
        {
            for(int j=0 ; j<this.armSize ; j++)
            {
                System.out.print(this.square[i][j].getAssignedNumber() + " ");
            }
            System.out.println();
        }
    }
}
