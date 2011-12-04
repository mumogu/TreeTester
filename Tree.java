public abstract class Tree {
	public abstract int getValue();

	/**
	 * Liefert eine textuelle Repraesentation des Baums als String, wobei die
	 * Elemente des Baums durch Kommata separiert hintereinander stehen. Die
	 * Reihenfolge der elemente ist dabei die sogenannte
	 * depth-first-left-to-right Reihenfolge, d.h. linke Nachfolger kommen vor
	 * dem aktuellen Wert und rechte Nachfolger erst danach.
	 */
	public abstract String toString();

	/**
	 * Liefert das linkeste Element des Baums.
	 */
	public abstract Tree getMin();
}