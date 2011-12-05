import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.lang.reflect.Constructor;

public class TreeTester {
	private static final String[] players = { "David", "Martin" };
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
		long start = System.nanoTime() / 1000000;

		for (int i = 0; i < length; i++)
			mt.sortedInsert(testVector[i]);

		for (int i = 0; i < length; i++)
			mt.sortedDelete(mt, testVector[i]);

		return System.nanoTime() / 1000000 - start;
	}

	private static long testImmutable(ImmutableTree imt, int[] testVector, int length) {
		long start = System.nanoTime() / 1000000;

		for (int i = 0; i < length; i++)
			imt = imt.sortedInsert(testVector[i]);

		for (int i = 0; i < length; i++)
			imt = imt.sortedDelete(testVector[i]);


		return System.nanoTime() / 1000000 - start;
	}

	private static void runTests(String filename, int size, int step) throws Exception {
		int[] testVector = generateTestVector(size);

		FileWriter writer = new FileWriter(filename);
		writer.append("Size;");
		for (String player : players) {
			writer.append(player + "Mutable" + ";" + player + "Immutable;");
		}
		writer.append("\n");

		for (int i = step; i <= size; i += step) {
			System.out.print("#: " + i + "\t");
			writer.append(i + ";");

			for (String player : players) {
				System.out.print(" | " + player + " ");

				// Mutable
				Class mTreeClass = Class.forName(player + "MutableTree");
				Constructor mCtor = mTreeClass.getDeclaredConstructor(Integer.class);
				MutableTree mTreeObj = (MutableTree) mCtor.newInstance(new Integer(42));

				long mTreeTime = testMutable(mTreeObj, testVector, i);
				System.out.print("m:" + mTreeTime + "ms ");

				// Immutable
				Class imTreeClass = Class.forName(player + "ImmutableTree");
				Constructor imCtor = imTreeClass.getDeclaredConstructor(Integer.class, ImmutableTree.class, ImmutableTree.class);
				ImmutableTree imTreeObj = (ImmutableTree) imCtor.newInstance(new Integer(42), null, null);

				long imTreeTime = testImmutable(imTreeObj, testVector, i);
				System.out.print("im:" + imTreeTime + "ms");

				writer.append(mTreeTime + ";" + imTreeTime + ";");
			}

			System.out.println("");

			writer.append("\n");
			writer.flush();
		}

		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("Usage: TreeTester filename size step" + "\nTests different implementations of a binary search tree."
					+ "\nThe results are written to <filename> in csv format. Separator is a semicolon (;)");
			return;
		}

		String filename = args[0];
		int size = Integer.parseInt(args[1]);
		int step = Integer.parseInt(args[2]);

		runTests(filename, size, step);
	}
}