public class MartinImmutableTree extends ImmutableTree {
	private final int value;
	private final ImmutableTree left, right;

	public MartinImmutableTree(int value, ImmutableTree left, ImmutableTree right) {
		this.value = value;
		this.left = left;
		this.right = right;
	}

	public int getValue() {
		return value;
	}

	public ImmutableTree getLeft() {
		return left;
	}

	public ImmutableTree getRight() {
		return right;
	}

	public String toString() {
		return (left != null ? left + "," : "") + value + (right != null ? "," + right : "");
	}

	public ImmutableTree getMin() {
		return (ImmutableTree) (left != null ? left.getMin() : this);
	}

	public ImmutableTree sortedInsert(int value) {
		if (value < this.value) {
			return new MartinImmutableTree(
					this.value, 
					left != null ? left.sortedInsert(value) : new MartinImmutableTree(value, null, null), 
					right
				);
		} else if (value > this.value) {
			return new MartinImmutableTree(
					this.value, 
					left, 
					right != null ? right.sortedInsert(value) : new MartinImmutableTree(value, null, null)
				);
		}

		// Ansonsten ist der Wert gleich; keine Aktion durchführen
		return this;	
	}

	public ImmutableTree sortedDelete(int value) {
		if (value == this.value) {
			if (left == null && right == null) {
				return null;
			}

			if (right != null) {
				return new MartinImmutableTree(
						right.getMin().getValue(),
						left,
						right.sortedDelete(right.getMin().getValue())
					);
			} else {
				return left;
			}
		} else {
			return new MartinImmutableTree(
					this.value,
					(left != null) && (value < this.value) ? left.sortedDelete(value) : left,
					(right != null) && (value > this.value) ? right.sortedDelete(value) : right
				);
		}
	}
}
