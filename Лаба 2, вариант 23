import java.util.*;

public class SortingTest {

    static final int SMALL = 500;
    static final int MEDIUM = 2000;
    static final int LARGE = 10000;
    static final int RUNS = 5;

    enum Algo { SELECTION, HEAP, COMB }

    public static void main(String[] args) {
        int[] sizes = { SMALL, MEDIUM, LARGE };
        String[] names = { "Малый", "Средний", "Большой" };

        for (int si = 0; si < sizes.length; si++) {
            int n = sizes[si];
            System.out.println("\n=== " + names[si] + " массив (n=" + n + ") ===");

            Map<String, int[]> tests = new LinkedHashMap<>();
            tests.put("Случайный", random(n));
            tests.put("Частично 75%", partiallySorted(n, 0.75));
            tests.put("Обратно", reverse(n));
            tests.put("Дубликаты 10%", duplicates(n, 0.10));
            tests.put("Почти 90%", partiallySorted(n, 0.90));

            for (var e : tests.entrySet()) {
                String caseName = e.getKey();
                int[] base = e.getValue();

                System.out.println("\n" + caseName);

                Map<Algo, long[]> results = new EnumMap<>(Algo.class);

                for (Algo algo : Algo.values()) {
                    long[] times = new long[RUNS];
                    for (int r = 0; r < RUNS; r++) {
                        int[] arr = Arrays.copyOf(base, base.length);
                        long t0 = System.nanoTime();
                        sort(arr, algo);
                        long t1 = System.nanoTime();
                        times[r] = t1 - t0;
                    }
                    results.put(algo, times);
                }
                print(results);
            }
        }
    }

    static void sort(int[] a, Algo algo) {
        switch (algo) {
            case SELECTION -> selection(a);
            case HEAP -> heap(a);
            case COMB -> comb(a);
        }
    }

    static void selection(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int m = i;
            for (int j = i + 1; j < a.length; j++)
                if (a[j] < a[m]) m = j;
            int t = a[i]; a[i] = a[m]; a[m] = t;
        }
    }

    static void heap(int[] a) {
        int n = a.length;
        for (int i = n/2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = a[0]; a[0] = a[i]; a[i] = t;
            heapify(a, i, 0);
        }
    }

    static void heapify(int[] a, int n, int i) {
        int l = 2*i+1, r = 2*i+2, m = i;
        if (l < n && a[l] > a[m]) m = l;
        if (r < n && a[r] > a[m]) m = r;
        if (m != i) {
            int t = a[i]; a[i] = a[m]; a[m] = t;
            heapify(a, n, m);
        }
    }

    static void comb(int[] a) {
        int gap = a.length;
        double sh = 1.3;
        boolean sorted = false;
        while (!sorted) {
            gap = (int)(gap / sh);
            if (gap < 1) { gap = 1; sorted = true; }
            for (int i = 0; i + gap < a.length; i++) {
                if (a[i] > a[i+gap]) {
                    int t = a[i]; a[i] = a[i+gap]; a[i+gap] = t;
                    sorted = false;
                }
            }
        }
    }

    static int[] random(int n) {
        Random r = new Random(1);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(n*10);
        return a;
    }

    static int[] reverse(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        return a;
    }

    static int[] partiallySorted(int n, double p) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        int k = (int)(n * (1 - p));
        Random r = new Random((int)(p*1000));
        for (int i = 0; i < k; i++) {
            int x = r.nextInt(n), y = r.nextInt(n);
            int t = a[x]; a[x] = a[y]; a[y] = t;
        }
        return a;
    }

    static int[] duplicates(int n, double frac) {
        int u = Math.max(1, (int)(n * frac));
        Random r = new Random(7);
        int[] p = new int[u];
        for (int i = 0; i < u; i++) p[i] = r.nextInt(u*10);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = p[r.nextInt(u)];
        return a;
    }

    static void print(Map<Algo, long[]> res) {
        for (Algo a : Algo.values()) {
            long[] t = res.get(a);
            double avg = 0, best = Long.MAX_VALUE;
            for (long x : t) {
                avg += x;
                if (x < best) best = x;
            }
            avg /= t.length;
            System.out.printf("%-10s  best=%.3f ms  avg=%.3f ms\n",
                    a.name(),
                    best/1_000_000.0,
                    avg/1_000_000.0
            );
        }
    }
}
