public class Tree {
	private int value;
	private Tree left;
	private Tree right;
	
	public Tree(int value) {
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public int getValue() {return this.value;}
	public Tree getLeft() {return this.left;}
	public Tree getRight() {return this.right;}
	
	public void setValue(int value) {this.value = value;}
	public void setLeft(Tree left) {this.left = left;}
	public void setRight(Tree right) {this.right = right;}
	
	public String toString(){
		String retStr = "";
		
		if ( this.left != null) {
			retStr += this.left.toString() + ", ";
		}
		retStr += this.value;
		if ( this.right != null) {
			retStr += ", " + this.right.toString();
		}
		
		return retStr;
	}
	
	public Tree copy() {
		Tree cp = new Tree(this.value); //wurzel kopieren
		cp.left = this.left != null ? this.left.copy() : null; //kinder kopieren, wenn nicht null
		cp.right = this.right != null ? this.right.copy() : null;
		return cp;
	}
	
	public Tree getMin() {
		return this.left != null ? this.left.getMin() : this;
	}
	
	public void sortedInsert(int insertVal) {
		if (insertVal < this.value) { // wenn kleiner links
			if (this.left == null) { //kein kind links
				this.left = new Tree(insertVal); //neuen knoten direkt anfuegen
			} else { //sonst im linken teilbaum
				this.left.sortedInsert(insertVal);
			}
		} else { // nicht kleiner rechts analog zu links
			if (this.right == null) {
				this.right = new Tree(insertVal);
			} else {
				this.right.sortedInsert(insertVal);
			}
		}
	}
	
	public static Tree sortedDelete(Tree root, int deleteVal) {
		if (root == null) return null;
		
		if( root.value == deleteVal) { //wurzel soll geloescht werden
			if( root.left == null && root.right == null) { //nur root
				//leeren baum zurueckgeben
				return null;
			} else { //wurzel hat kinder
				root = root.copy(); //ab hier brauchen wir eine kopie
				
				if (root.right != null) { //rechter teilbaum vorhanden.
					Tree rightMin = root.right.getMin();
					root.value = rightMin.value; //kleinstes Element in die Wurzel kopieren
					root.right = sortedDelete(root.right, rightMin.value); //und aus rechtem teilbaum löschen
				} else { //kein rechter teilbaum
					root = root.left; //loeschen "wie in liste"
				}
				//der sonderfall (right != null && left==null) wird nicht betrachtet
				//so spaart man sich ein paar vergleiche, einigen dublicate code
				//mit einem kleinen risiko etwas mehraufwand zu betreiben
			}
		} else { //wurzel wird nicht geloescht
			Tree newRoot = new Tree(root.value); //kopie anlegen
			if(deleteVal < root.value) { //element zum loeschen ist links
				newRoot.left = sortedDelete(root.left, deleteVal); //element links loeschen
				newRoot.right = root.right; //rechts bleibt unveraendert
			} else { //analog mit rechts
				newRoot.right = sortedDelete(root.right, deleteVal);
				newRoot.left = root.left;
			}
			
			root = newRoot; //alte referenz mit neuem baum ueberschreiben
		}
		
		return root; //root, den "neuen baum", zurueckgeben.
	}
}