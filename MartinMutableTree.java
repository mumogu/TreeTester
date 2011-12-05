public class MartinMutableTree extends MutableTree {
	private int value;
	private MutableTree left, right;

	public MartinMutableTree(int value) {
		this.value = value;
		left = right = null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value; 
	}

	public MutableTree getLeft() {
		return left;
	}

	public void setLeft(MutableTree left) {
		this.left = left; 
	}

	public MutableTree getRight() {
		return right;
	}

	public void setRight(MutableTree right) {
		this.right = right; 
	}

	public String toString() {
		return (left != null ? left + "," : "") + value + (right != null ? "," + right : "");
	}

	public MutableTree copy() {
		MutableTree copy = new MartinMutableTree(value);
		
		if (left != null) {
			copy.setLeft(left.copy());
		}

		if (right != null) {
			copy.setRight(right.copy());
		}

		return copy;
	}

	public MutableTree getMin() {
		return (MutableTree) (left != null ? left.getMin() : this); // Hochcasten
	}

	public void sortedInsert(int value) {
		if (value < this.value) {
			if (left != null) {
				left.sortedInsert(value);
			} else {
				left = new MartinMutableTree(value);
			}
		} else if (value > this.value) {
			if (right != null) {
				right.sortedInsert(value);
			} else {
				right = new MartinMutableTree(value);
			}
		}

		// Ansonsten ist der Wert gleich; keine Aktion durchführen
	}

/* 
 *
 * BEWARE: UGLY CODE AHEAD 
 *
 * Ich glaub den Code da unten kann man noch was hübscher machen, oder?
 * Innere Schleifen sind leider nicht erlaubt =(
 *
 */

	public MutableTree sortedDelete(MutableTree tree, int value) {
		MutableTree result;

		if (value == tree.getValue()) {
			// Knoten entfernen
			if (tree.getRight() == null && tree.getLeft() == null) {
				return null;
			} else {
				result = tree.copy();

				// Wenn rechter zweig nicht leer, kleinstes Element von dort
				// nehmen und hochkopieren, um sortierung beizubehalten
				if (result.getRight() != null) {
					MutableTree rightMin = (MutableTree) result.getRight().getMin(); // WTF braucht man diesen cast?!?!
					result.setValue(rightMin.getValue());
					result.setRight(sortedDelete(result.getRight(), rightMin.getValue()));
				} else {
					// Anderenfalls einfach linken Zweig hochkopieren (ist
					// sicher vorhanden wegen left == right == null prüfung am Anfang)
					result = result.getLeft();
				}
			}
		} else {	
			// Wurzel kopieren und kinder je nach value direkt kopieren oder
			// Löschoperation auf ihnen durchführen
			result = new MartinMutableTree(tree.getValue());

			if (tree.getRight() != null) {
				result.setRight(value < tree.getValue() ? tree.getRight().copy() : sortedDelete(tree.getRight(), value));
			}

			if (tree.getLeft() != null) {
				result.setLeft(value > tree.getValue() ? tree.getLeft().copy() : sortedDelete(tree.getLeft(), value));
			}
		}

		return result;
	}
}
