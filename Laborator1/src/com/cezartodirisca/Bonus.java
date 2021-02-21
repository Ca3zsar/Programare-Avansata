package com.cezartodirisca;

public class Bonus {
    static int currentNode = 0;
    public static void main(String[] args)
    {
        int maxLevels = Integer.parseInt(args[0]);
        int maxChildren = Integer.parseInt(args[1]);
        generateTree(0, maxLevels, maxChildren);
    }

    public static void generateTree(int currentLevel, int maxLevel, int maxChildren)
    {
        if(currentLevel == 0)
        {
            System.out.println("+node" + currentNode);
            currentNode++;

            int goNext = (int)(Math.random() * 2);
            if (goNext == 1)
                generateTree(currentLevel+1,maxLevel,maxChildren);
        }else{
            if(currentLevel <= maxLevel) {
                int children = (int) (Math.random() * (maxChildren + 1));
                for (int i = 0; i < children; i++) {
                    for (int j = 0; j < currentLevel; j++) {
                        System.out.printf(" ");
                    }
                    int goNext = (int) (Math.random() * 2);
                    if (goNext == 1) {
                        if(currentLevel < maxLevel) {
                            System.out.printf("+node" + currentNode + '\n');
                            currentNode++;
                            generateTree(currentLevel + 1, maxLevel, maxChildren);
                        }else {
                            System.out.printf("-node" + currentNode + '\n');
                            currentNode++;
                        }
                    } else {
                        System.out.printf("-node" + currentNode + '\n');
                        currentNode++;
                    }
                }
            }
        }
    }
}
