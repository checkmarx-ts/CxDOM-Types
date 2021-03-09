// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html

class ArrayDemo {
    public static void main(String[] args) {
        // declares an array of integers
        int[] anArray;

        // allocates memory for 10 integers
        anArray = new int[10];

        // initialize first element
        anArray[0] = 100;

        System.out.println("Element at index 0: " + anArray[0]);
    }
}