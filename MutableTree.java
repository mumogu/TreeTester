public abstract class MutableTree extends Tree {
	public abstract MutableTree getLeft();

	public abstract void setLeft(MutableTree left);

	public abstract MutableTree getRight();

	public abstract void setRight(MutableTree right);

	public abstract void setValue(int value);

	/**
	 * Abweichend von der urspruenglichen Aufgabenstellung eine Factory-Methode.
	 */
	public abstract MutableTree newInstance(int value);

	/**
	 * Liefert eine Kopie des Baums. Modifikationen an der Kopie verursachen
	 * keine Modifikation am urspruenglichen Baum.
	 */
	public abstract Tree copy();

	/**
	 * Fuegt den uebergebenen Wert in den Baum ein, so dass dieser in seiner
	 * textuellen Repraesentation aufsteigend sortiert ist. Hier ist keine
	 * Umstrukturierung des Baums erlaubt.
	 */
	public abstract void sortedInsert(int newValue);

	/**
	 * Liefert einen neuen Baum, in welchem das Element aus dem uebergebenen
	 * Baum geloescht wurde. Bei mehreren Vorkommen dieses Wertes wird nur einer
	 * geloescht und falls kein solches Element existiert, wird einfach kein
	 * Element geloescht. Dabei soll die textuelle Repraesentation des
	 * resultierenden Baums aufsteigend sortiert sein, falls sie es vorher war.
	 */
	public abstract MutableTree sortedDelete(MutableTree deleteFrom, int toDelete);
}
