import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    public static int randint(int a, int b) {
        return (int) (Math.random() * (b - a) + a);
    }

    public static double randdouble(double a, double b) {
        return (Math.random() * (b - a) + a);
    }

    public static String objectToString(Object something) {
        if (!something.getClass().isArray()) {
            return something.toString();
        } else {
            return "[" +
                    IntStream.range(0, Array.getLength(something))
                            .mapToObj(i -> Objects.toString(Array.get(something, i)))
                            .reduce("", (l, r) -> (l != "" ? l + ", " : "") + r)
                    + "]";
        }
    }

    public static String makeFunctionCallString(String fnName, Object... args) {
        StringBuilder result = new StringBuilder();
        result.append(fnName);
        result.append("(");
        for (Object arg : args) {
            result.append(objectToString(arg));
            result.append(", ");
        }
        result.delete(result.length()-2, result.length());
        result.append(")");
        return result.toString();
    }

    @Test
    @DisplayName("max3(int, int, int)")
    void max3Ints() {
        final int NTRIALS = 100;
        for (int i = 0; i < NTRIALS; i++) {
            int a = randint(0, 100);
            int b = randint(0, 100);
            int c = randint(0, 100);

            assertEquals(Reference.max3(a, b, c), Student.max3(a, b, c),
                    makeFunctionCallString("max3", a, b, c));
        }
    }

    @Test
    @DisplayName("max3(double, double, double)")
    void max3Doubles() {
        final int NTRIALS = 100;
        for (int i = 0; i < NTRIALS; i++) {
            double a = randdouble(0, 100);
            double b = randdouble(0, 100);
            double c = randdouble(0, 100);

            assertEquals(Reference.max3(a, b, c), Student.max3(a, b, c),
                    makeFunctionCallString("max3", a, b, c));
        }
    }

    @Test
    @DisplayName("Odd function")
    void odd() {
        final int NTRIALS = 100;
        for (int i = 0; i < NTRIALS; i++) {
            boolean a = Math.random() > 0.5;
            boolean b = Math.random() > 0.5;
            boolean c = Math.random() > 0.5;

            assertEquals(Reference.odd(a, b, c), Student.odd(a, b, c),
                    makeFunctionCallString("odd", a, b, c));

        }
    }

    @Test
    void eq() {
        final int NTRIALS = 100;
        for (int i = 0; i < NTRIALS; i++) {
            int[] a = Stream.generate(() -> randint(1, 5))
                    .limit(randint(1, 4))
                    .mapToInt(Integer::intValue)
                    .toArray();
            int[] b = Stream.generate(() -> randint(1, 5))
                    .limit(randint(1, 4))
                    .mapToInt(Integer::intValue)
                    .toArray();

            assertEquals(Reference.eq(a, b), Student.eq(a, b),
                    makeFunctionCallString("eq", a, b));
        }

    }

    @Test
    void sigmoid() {
        final int NTRIALS = 100;
        DoubleStream.generate(() -> randdouble(-5, 5))
                .limit(NTRIALS)
                .forEach(el -> assertEquals(Reference.sigmoid(el), Student.sigmoid(el), 1e-8,
                        makeFunctionCallString("sigmoid", el)));
    }

    @Test
    void scale() {
        final int NTRIALS = 100;
        for (int t = 0; t < NTRIALS; t++) {
            double[] aRef = Stream.generate(() -> randdouble(0, 10))
                    .limit(randint(2, 5))
                    .mapToDouble(Double::doubleValue)
                    .toArray();
            double[] aStud = aRef.clone();
            double[] orig = aRef.clone();
            Reference.scale(aRef);
            Student.scale(aStud);
            assertArrayEquals(aRef, aStud, 1e-8,
                    makeFunctionCallString("scale", orig));
        }
    }

    @Test
    void any() {
        final int NTRIALS = 100;
        for (int t = 0; t < NTRIALS; t++) {
            boolean[] a = new boolean[randint(2, 6)];
            for (int i = 0; i < a.length; i++) {
                a[i] = Math.random() < .3;
            }
            assertEquals(Reference.any(a),
                    Student.any(a),
                    makeFunctionCallString("any", a));
        }
    }

    @Test
    void all() {
        final int NTRIALS = 100;
        for (int t = 0; t < NTRIALS; t++) {
            boolean[] a = new boolean[randint(2, 6)];
            for (int i = 0; i < a.length; i++) {
                a[i] = Math.random() < .8;
            }
            assertEquals(Reference.all(a),
                    Student.all(a),
                    makeFunctionCallString("all", a));
        }
    }

}