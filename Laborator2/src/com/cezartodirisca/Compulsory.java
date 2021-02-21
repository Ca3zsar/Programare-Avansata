package com.cezartodirisca;

import java.util.Arrays;

public class Compulsory {

    public static void main(String[] args) {
	    Source[] sources = new Source[3];
	    sources[0] = new Source(SourceType.FACTORY, "S1", 10);
	    sources[1] = new Source(SourceType.WAREHOUSE, "S2", 35);
	    sources[2] = new Source(SourceType.WAREHOUSE, "S3", 25);

		System.out.println(Arrays.toString(sources));

	    Destination[] dests = new Destination[3];
	    dests[0] = new Destination("D1",20);
	    dests[1] = new Destination("D2",25);
	    dests[2] = new Destination("D3",25);

		System.out.println(Arrays.toString(dests));

	    int sourceNumber = sources.length;
	    int destinationNumber = dests.length;

	    int[][] costs = {
	                    {2, 3, 1},
                        {5, 4, 8},
                        {5, 6, 8}
	                    };

	    Problem problem = new Problem(sourceNumber, destinationNumber, sources, dests, costs);

		System.out.println(problem.toString());
    }
}
