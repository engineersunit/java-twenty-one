import java.util.stream.IntStream;
import java.util.stream.Stream;

void main() {
    IntStream s = IntStream.of(1, 2, 3, 4);
    long count = s.peek(System.out::println).count();

    print(Stream.of(1, 1, 2, 3, 5)
            .peek(System.out::println)
            .count());

    /*print(Stream.of(1, 1, 2, 3, 5)
            .peek(System.out::println)
            .filter(x -> x == 8)
            .count());*/
}

void print(Object o) {
    System.out.println(o);
}