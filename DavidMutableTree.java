public class DavidMutableTree extends MutableTree {
	private int value;
	private MutableTree left;
	private MutableTree right;

	public DavidMutableTree(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}

	public MutableTree newInstance(int value) {
		MutableTree tr = new DavidMutableTree(value);
		tr.setLeft(null);
		tr.setRight(null);
		return tr;
	}

	public int getValue() {
		return this.value;
	}

	public MutableTree getLeft() {
		return this.left;
	}

	public MutableTree getRight() {
		return this.right;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setLeft(MutableTree left) {
		this.left = left;
	}

	public void setRight(MutableTree right) {
		this.right = right;
	}

	public String toString() {
		String retStr = "";

		if (this.left != null) {
			retStr += this.left.toString() + ", ";
		}
		retStr += this.value;
		if (this.right != null) {
			retStr += ", " + this.right.toString();
		}

		return retStr;
	}

	public MutableTree copy() {
		DavidMutableTree cp = new DavidMutableTree(this.value); // wurzel kopieren
		cp.left = (MutableTree) (this.left != null ? this.left.copy() : null); // kinder
																// kopieren,
																// wenn nicht
																// null
		cp.right = (MutableTree) (this.right != null ? this.right.copy() : null);
		return cp;
	}

	public MutableTree getMin() {
		return (MutableTree) (this.left != null ? this.left.getMin() : this);
	}

	public void sortedInsert(int insertVal) {
		if (insertVal < this.value) { // wenn kleiner links
			if (this.left == null) { // kein kind links
				this.left = new DavidMutableTree(insertVal); // neuen knoten direkt
														// anfuegen
			} else { // sonst im linken teilbaum
				this.left.sortedInsert(insertVal);
			}
		} else { // nicht kleiner rechts analog zu links
			if (this.right == null) {
				this.right = new DavidMutableTree(insertVal);
			} else {
				this.right.sortedInsert(insertVal);
			}
		}
	}

	public MutableTree sortedDelete(MutableTree root, int deleteVal) {
		if (root == null)
			return null;

		if (root.getValue() == deleteVal) { // wurzel soll geloescht werden
			if (root.getLeft() == null && root.getRight() == null) { // nur root
				// leeren baum zurueckgeben
				return null;
			} else { // wurzel hat kinder
				root = (MutableTree) root.copy(); // ab hier brauchen wir eine kopie

				if (root.getRight() != null) { // rechter teilbaum vorhanden.
					MutableTree rightMin = (MutableTree) root.getRight().getMin();
					root.setValue(rightMin.getValue()); // kleinstes Element in die
													// Wurzel kopieren
					root.setRight(sortedDelete(root.getRight(), rightMin.getValue())); // und
																			// aus
																			// rechtem
																			// teilbaum
																			// loeschen
				} else { // kein rechter teilbaum
					root = root.getLeft(); // loeschen "wie in liste"
				}
				// der sonderfall (right != null && left==null) wird nicht
				// betrachtet
				// so spaart man sich ein paar vergleiche, einigen dublicate
				// code
				// mit einem kleinen risiko etwas mehraufwand zu betreiben
			}
		} else { // wurzel wird nicht geloescht
			MutableTree newRoot = new DavidMutableTree(root.getValue()); // kopie anlegen
			if (deleteVal < root.getValue()) { // element zum loeschen ist links
				newRoot.setLeft(sortedDelete(root.getLeft(), deleteVal)); // element
																	// links
																	// loeschen
				newRoot.setRight(root.getRight()); // rechts bleibt unveraendert
			} else { // analog mit rechts
				newRoot.setRight(sortedDelete(root.getRight(), deleteVal));
				newRoot.setLeft(root.getLeft());
			}

			root = newRoot; // alte referenz mit neuem baum ueberschreiben
		}

		return root; // root, den "neuen baum", zurueckgeben.
	}

}
