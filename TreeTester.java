import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;

public class TreeTester {
	private static int[] generateTestVector(int size) {
		System.out.println("Generating testvector with " + size + " elements...");
		long start = System.currentTimeMillis();

		Random generator = new Random(42);
		Set<Integer> set = new HashSet<Integer>();
		int[] testVector = new int[size];
		for (int i = 0; i < size; i++) {
			int candidate;

			do
				candidate = generator.nextInt();
			while (set.contains(candidate));

			set.add(candidate);
			testVector[i] = candidate;
		}
		System.out.println("Done generating testvector. Took " + (System.currentTimeMillis() - start) + "ms.");
		return testVector;
	}

	private static long testMutable(MutableTree mt, int[] testVector, int length) {
		long start = System.currentTimeMillis();

		for (int i = 0; i < length; i++)
			mt.sortedInsert(testVector[i]);

		for (int i = 0; i < length; i++)
			mt.sortedDelete(mt, testVector[i]);

		return System.currentTimeMillis() - start;
	}

	private static long testImmutable(ImmutableTree imt, int[] testVector, int length) {
		long start = System.currentTimeMillis();

		for (int i = 0; i < length; i++)
			imt = imt.sortedInsert(testVector[i]);

		for (int i = 0; i < length; i++)
			imt = imt.sortedDelete(testVector[i]);

		return System.currentTimeMillis() - start;
	}

	public static void main(String[] args) throws Exception {

		if (args.length != 3) {
			System.out.println("Usage: TreeTester filename size step"
					+ "\nTests different implementations of a binary search tree."
					+ "\nThe results are written to <filename> in csv format. Separator is a semicolon (;)");
			return;
		}

		String filename = args[0];
		int size = Integer.parseInt(args[1]);
		int step = Integer.parseInt(args[2]);

		int[] testVector = generateTestVector(size);

		FileWriter writer = new FileWriter(filename);
		writer.append("Size;DavidMutable;DavidImmutable;MartinMutable;MartinImmutable\n");

		for (int i = step; i <= size; i += step) {
			System.out.print("#: " + i + "\t");

			// David
			MutableTree dmt = new DavidMutableTree(23);
			long dm = testMutable(dmt, testVector, i);
			System.out.print("DavidMutable: " + dm + "ms" + "\t");

			ImmutableTree dimt = new DavidImmutableTree(23, null, null);
			long dim = testImmutable(dimt, testVector, i);
			System.out.print("DavidImmutable: " + dim + "ms" + "\t");

			// Martin
			MutableTree mmt = new MartinMutableTree(23);
			long mm = testMutable(mmt, testVector, i);
			System.out.print("MartinMutable: " + mm + "ms" + "\t");

			ImmutableTree mimt = new MartinImmutableTree(23, null, null);
			long mim = testImmutable(mimt, testVector, i);
			System.out.println("MartinImmutable: " + mim + "ms");
			
			writer.append(i + ";" + dm + ";" + dim + ";" + mm + ";" + mim + "\n");
			writer.flush();
		}

		writer.flush();
		writer.close();

	}
}