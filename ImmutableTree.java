public abstract class ImmutableTree extends Tree {
	public abstract ImmutableTree getLeft();

	public abstract ImmutableTree getRight();

	/**
	 * Fuegt den uebergebenen Wert in den Baum ein, so dass dieser in seiner
	 * textuellen Repraesentation aufsteigend sortiert ist. Hier ist keine
	 * Umstrukturierung des Baums erlaubt.
	 */
	public abstract ImmutableTree sortedInsert(int insertVal);

	/**
	 * Liefert einen neuen Baum, in welchem das Element aus dem uebergebenen
	 * Baum geloescht wurde. Bei mehreren Vorkommen dieses Wertes wird nur einer
	 * geloescht und falls kein solches Element existiert, wird einfach kein
	 * Element geloescht. Dabei soll die textuelle Repraesentation des
	 * resultierenden Baums aufsteigend sortiert sein, falls sie es vorher war.
	 */
	public abstract ImmutableTree sortedDelete(int deleteVal);
}