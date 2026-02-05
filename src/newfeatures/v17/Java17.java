package newfeatures.v17;

import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

public class Java17 {
    public static void main(String[] args) {
        // 2.1. Restore Always-Strict Floating-Point Semantics (JEP 306)
        // This JEP is mainly for scientific applications, and it makes floating-point operations consistently strict. The default floating-point operations are strict or strictfp, both of which guarantee the same results from the floating-point calculations on every platform.
        // Before Java 1.2, strictfp behavior was the default one as well. However, because of hardware issues, the architects changed, and the keyword strictfp was necessary to re-enable such behavior. So, there is no need to use this keyword anymore.

        // 2.2. Enhanced Pseudo-Random Number Generators (JEP 356)

    }

    public IntStream getPseudoInts(String algorithm, int streamSize) {
        // returns an IntStream with size @streamSize of random numbers generated using the @algorithm
        // where the lower bound is 0 and the upper is 100 (exclusive)
        return RandomGeneratorFactory.of(algorithm)
                .create()
                .ints(streamSize, 0, 100);
    }
}
