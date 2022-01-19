public class Goal {
    int[][] goal(int n)
    {
        //int n = 4;

        int[][] goal = new int[n][n];

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
        }

        return goal;
    }
}
