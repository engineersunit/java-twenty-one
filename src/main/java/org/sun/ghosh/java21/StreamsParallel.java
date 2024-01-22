import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

void main() {
    // first we create a set of 10 million numbers
    //(int) (Math.random() * Integer.MAX_VALUE)) (2,147,483,647)
    int maxValue = 10_000_000;
    Set<Integer> millionsOfNumbers = new HashSet<>(maxValue);
    IntStream.rangeClosed(1, maxValue).
            forEach(millionsOfNumbers::add);
    millionsOfNumbers.parallelStream().forEach(number ->
            System.out.println(number + " " + Thread.currentThread().getName())
    );


}