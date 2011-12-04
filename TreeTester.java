import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;

public class TreeTester {
	private static int[] generateTestVector(int size) {
		System.out.println("Generating testvector with " + size + " elements...");
		long start = System.currentTimeMillis();
		
		Random generator = new Random(42);
		Set<Integer> set = new HashSet<Integer>();
		int[] testVector = new int[size];
		for(int i=0; i<size; i++) {			
			int candidate;

			do candidate = generator.nextInt();
			while(set.contains(candidate));
				
			set.add(candidate);
			testVector[i] = candidate;					
		}
		System.out.println("Done generating testvector. Took " + (System.currentTimeMillis() - start) + "ms.");
		return testVector;
	}

	private static long testMutable(int[] testVector, int length) {
		long start = System.currentTimeMillis();
		MutableTree mt = new DavidMutableTree(23);

		for(int i=0; i<length; i++)
			mt.sortedInsert(testVector[i]);

		for(int i=0; i<length; i++) 
			mt.sortedDelete(mt, testVector[i]);

		return System.currentTimeMillis() - start;
	}

	private static long testImmutable(int[] testVector, int length) {
		long start = System.currentTimeMillis();
		
		ImmutableTree imt = new DavidImmutableTree(23, null, null);

		for(int i=0; i<length; i++)
			imt = imt.sortedInsert(testVector[i]);

		for(int i=0; i<length; i++)
			imt = imt.sortedDelete(testVector[i]);		
		
		return System.currentTimeMillis() - start;			
	}

	public static void main(String[] args) throws Exception{
		
		if(args.length != 3) {
			System.out.println("Usage: TreeTester filename size step\nThe results are written to filename in csv format. Separator is ;");
			return;
		}

		String filename = args[0];
		int size = Integer.parseInt(args[1]);
		int step = Integer.parseInt(args[2]);

		int[] testVector = generateTestVector(size);
		
		FileWriter writer = new FileWriter(filename);
		writer.append("Size;Mutable;Immutable\n");

		for(int i=step; i<=size; i+=step) {
			System.out.print("#: " + i + "\t");
			
			long m = testMutable(testVector, i);
			System.out.print("mutable: " + m + "ms" + "\t");

			long im = testImmutable(testVector, i);
			System.out.println("immutable: " + im + "ms");

			writer.append(i + ";" + m + ";" + im + "\n");
			writer.flush();
		}

		writer.flush();
		writer.close();
		
	}
}