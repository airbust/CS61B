public class HelloNumbers {
	public static void main(String[] args) {
		int x = 0;
		int increment = 1;
		while (increment <= 10) {
			System.out.print(x + " ");
			x += increment;
			increment++;
		}
	}
}
