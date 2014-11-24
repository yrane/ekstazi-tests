class BubbleSort
{
	public static<E extends Comparable<E>> void bubble(E[] array) {
		for (int j = array.length - 1; j >= 0; j--) {
		        for (int i = 1; i <= j; i++) {
		                if (array[i-1].compareTo(array[i]) < 0) {
		                        E temp = array[i-1];
		                        array[i-1] = array[i];
		                        array[i] = temp;
		                }
		        }
		}
	}
}
