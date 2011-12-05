public class DavidImmutableTree extends ImmutableTree {
	private final int value;
	private final ImmutableTree left;
	private final ImmutableTree right;

	public DavidImmutableTree(Integer value, ImmutableTree left, ImmutableTree right) {

		this.value = value;
		this.left = left;
		this.right = right;
	}
	
	public ImmutableTree newInstance(int value) {
		return new DavidImmutableTree(value, null, null);
	}

	public int getValue() {
		return this.value;
	}

	public ImmutableTree getLeft() {
		return this.left;
	}

	public ImmutableTree getRight() {
		return this.right;
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

	public ImmutableTree getMin() {
		return (ImmutableTree) (this.left != null ? this.left.getMin() : this);
	}

	public ImmutableTree sortedInsert(int insertVal) {
		if (insertVal < this.value) { // wenn kleiner links
			if (this.left == null) { // kein kind links
				return new DavidImmutableTree(this.value, new DavidImmutableTree(insertVal, null, null), this.right); // neuen
				// knoten
				// als
				// blatt
				// direkt
				// anfuegen
			} else { // sonst rekursiv im linken teilbaum
				return new DavidImmutableTree(this.value, this.left.sortedInsert(insertVal), this.right);
			}
		} else { // nicht kleiner rechts analog zu links
			if (this.right == null) {
				return new DavidImmutableTree(this.value, this.left, new DavidImmutableTree(insertVal, null, null));
			} else {
				return new DavidImmutableTree(this.value, this.left, this.right.sortedInsert(insertVal));
			}
		}
	}

	public ImmutableTree sortedDelete(int deleteVal) {
		if (this.value == deleteVal) { // this soll geloescht werden
			if (this.left == null && this.right == null) { // nur this
				// leeren baum zurueckgeben
				return null;
			} else { // this hat kinder
				if (this.right != null) { // rechter teilbaum vorhanden.
					// kleinstes element von rechts in die wurzel kopieren und
					// aus rechtem teilbaum lšschen
					return new DavidImmutableTree(this.right.getMin().getValue(), this.left,
							this.right.sortedDelete(this.right.getMin().getValue()));
				} else { // kein rechter teilbaum
					return this.left; // linkes kind ist neue wurzel des
										// teilbaumes mit this als wurzel
				}
				// der sonderfall (right != null && left==null) wird nicht
				// betrachtet
				// so spaart man sich ein paar vergleiche, einigen dublicate
				// code
				// mit einem kleinen risiko etwas mehraufwand zu betreiben
			}
		} else { // wurzel wird nicht geloescht
			if (deleteVal < this.value) { // element zum loeschen ist links
				return new DavidImmutableTree(this.value, this.left != null ? this.left.sortedDelete(deleteVal) : null,
						this.right); // element links loeschen
			} else { // analog mit rechts
				return new DavidImmutableTree(this.value, this.left,
						this.right != null ? this.right.sortedDelete(deleteVal) : null);
			}
		}

		// hier kein return noetig, weil jedes if ein else hat und in jedem if
		// und else block returned wird.
	}
	// feststellung: Mit immutables ist das alles so schoen endrekursiv :)
}
