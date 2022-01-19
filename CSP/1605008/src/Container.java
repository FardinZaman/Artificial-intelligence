import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Container {
    static int size = 0;

    static int numberOfNodes = 1;
    static int numberOfBackTrack = 0;
    static int numberOfFails = 0;

    public static void main(String[] args) {
//        size = 4;
//        int[][] arr = {{1,0,0,0} , {0,0,2,0} , {3,0,1,0} , {0,3,0,0}};

        //int[][] arr = {{0,0,0,0} , {0,0,0,0} , {0,0,0,0} , {0,0,0,0}};

//        int n = 10;
//        int[][] arr = {{0, 0, 6, 0, 0, 3, 4, 0, 10, 0} ,
//                {2, 6, 4, 0, 0, 0, 0, 0, 9, 0} ,
//                {0, 2, 10, 0, 0, 0, 0, 0, 5, 9} ,
//                {10, 1, 5, 4, 2, 0, 0, 0, 0, 0} ,
//                {0, 0, 0, 0, 1, 9, 8, 4, 0, 0} ,
//                {0, 0, 3, 2, 9, 0, 0, 1, 0, 0} ,
//                {6, 0, 0, 0, 0, 7, 0, 10, 0, 5} ,
//                {0, 0, 0, 0, 0, 8, 6, 5, 0, 7} ,
//                {1, 3, 0, 6, 0, 0, 5, 0, 0, 2} ,
//                {0, 5, 0, 9, 6, 2, 0, 0, 8, 0}};


        int[][] arr = readFile("d-15-01.txt.txt");
        Square square = new Square(size);
        square.setSquare(arr);

        square.printSquare();
        System.out.println();

        /*for(int i=0 ; i<n ; i++)
        {
            for(int j=0 ; j<n ; j++)
            {
                square.square[i][j].printAvailableValues();
            }
        }*/

        CSP csp = new CSP(square);
        //boolean result = csp.Backtracking();
        //boolean result = csp.ForwardChecking();
        boolean result = csp.MAC();
        System.out.println(result);
        System.out.println();
        square.printSquare();

        System.out.println("\nNumber of Nodes : " + numberOfNodes);
        System.out.println("\nNumber of Backtrack : " + numberOfBackTrack);
        System.out.println("\nNumber of fails : " + numberOfFails);
    }

    public static int[][] readFile(String name)
    {
        int[][] array = null;
        try
        {
            File file = new File(name);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();

            line = line.replaceAll(";","");

            String [] temp = line.split("=");

            size = Integer.parseInt(temp[1]);
            array = new int[size][size];

            System.out.println(size);

            reader.readLine();
            reader.readLine();

            for (int i=0;i<size;i++)
            {
                line = reader.readLine();
                line = line.replaceAll(",", "");
                line = line.replaceAll("|", "");
                line = line.replaceAll("];", "");
                temp = line.split(" ");
                for (int j=0;j<size;j++)
                {
                    array[i][j] = Integer.parseInt(temp[j]);
                }
            }

            reader.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return array;
    }
}
