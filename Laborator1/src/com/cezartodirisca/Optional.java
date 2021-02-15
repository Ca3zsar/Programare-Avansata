package com.cezartodirisca;

public class Optional {
    static int visited[];
    static int treeMatrix[][];

    public static void main(String[] args) {
        int n;
        try{
            n = Integer.parseInt(args[0]);
        } catch(NumberFormatException nfe){
            System.out.println("Invalid input!");
            return;
        }
        long startTime = System.nanoTime();

        if(n % 2 == 0)
        {
            System.out.println("Input not odd!");
            return;
        }

        int adjacency[][];
        adjacency = generateMatrix(n);
        drawMatrix(adjacency,n);

        //Check if connected.;
        visited = new int[n];
        treeMatrix = new int[n][n];

        int comp=1;
        for(int i=0;i<n;i++)
        {
            if(visited[i]==0)
            {
                DFS(i,adjacency,n,comp);
                comp++;
            }
        }
        if(comp == 2)
        {
            System.out.println("The graph is connected");
            System.out.println("The adjacency matrix of the spanning tree is : \n");

            drawMatrix(treeMatrix,n);
        }else{
            System.out.println("The graph is not connected");
        }

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }

    public static int[][] generateMatrix(int n)
    {
        int matrix[][] = new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=i;j<n;j++)
            {
                if(i==j)matrix[i][j]=0;
                else{
                    int prob = (int) (Math.random() * 2);
                    matrix[i][j] = prob;
                    matrix[j][i] = prob;
                }
            }
        }
        return matrix;
    }

    public static void drawMatrix(int [][]adjacency, int n)
    {
        char topLeftCorner = '\u250C';
        char topRightCorner = '\u2510';
        char bottomLeftCorner = '\u2514';
        char bottomRightCorner = '\u2518';
        char horizontal = '\u2500';
        char vertical ='\u2502';

        System.out.print(" ");
        for(int i=0;i<n;i++)
        {
            System.out.print(" " + i);
        }
        System.out.print(" \n");

        System.out.print(" " + topLeftCorner);
        for(int i=0;i<n;i++)
        {
            System.out.print(horizontal + "" + horizontal);
        }
        System.out.print(topRightCorner + "\n");

        for(int i=0;i<n;i++)
        {
            if(i!=0) {
                System.out.print(" " + vertical);
                for (int j = 0; j < n; j++) {
                    System.out.print("  ");
                }
                System.out.print(vertical + "\n");
            }
            System.out.print(i + "" + vertical);
            for(int j=0;j<n;j++)
            {
                System.out.print(adjacency[i][j] + " ");
            }
            System.out.print(vertical + "\n");
        }
        System.out.print(" " + bottomLeftCorner);
        for(int i=0;i<n;i++)
        {
            System.out.print(horizontal + "" + horizontal);
        }
        System.out.print(bottomRightCorner + "\n");
    }
    public static void DFS(int x,int [][]adjacency,int n, int comp)
    {
        visited[x]=comp;
        for(int j=0;j<n;j++)
        {
            if(adjacency[x][j]==1)
            {
                if(visited[j]==0)
                {
                    treeMatrix[x][j] = 1;
                    treeMatrix[j][x] = 1;
                    DFS(j,adjacency,n,comp);
                }
            }
        }
    }
}
