import java.util.*;

/**
 * Detect squares (any orientation) from a set of integer 2D points.
 * Example usage in main() demonstrates detection and prints found squares.
 */
public class DetectSquares {

    // Simple integer 2D point with proper equals/hashCode
    static class Point {
        final int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }

        Point add(Vector v) { return new Point(x + v.dx, y + v.dy); }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    // Integer vector for convenience
    static class Vector {
        final int dx, dy;
        Vector(int dx, int dy) { this.dx = dx; this.dy = dy; }

        // perpendicular vector rotated 90 degrees (dx,dy) -> (-dy, dx)
        Vector perpendicular() { return new Vector(-dy, dx); }

        // multiply vector by integer scalar
        Vector scale(int k) { return new Vector(dx * k, dy * k); }
    }

    /**
     * Find all unique squares (each returned as a List<Point> of size 4).
     * Each square is returned with its 4 vertices sorted lexicographically so duplicates can be avoided.
     *
     * Complexity: O(n^2) pairs checked, O(1) average lookup for point existence using HashSet.
     */
    public static List<List<Point>> findSquares(Collection<Point> points) {
        List<Point> pts = new ArrayList<>(points);
        Set<Point> pointSet = new HashSet<>(points);

        Set<String> seen = new HashSet<>(); // canonical key for a square to avoid duplicates
        List<List<Point>> result = new ArrayList<>();

        int n = pts.size();
        for (int i = 0; i < n; ++i) {
            Point a = pts.get(i);
            for (int j = 0; j < n; ++j) {
                if (i == j) continue;
                Point b = pts.get(j);

                // vector from a to b (side candidate)
                Vector v = new Vector(b.x - a.x, b.y - a.y);

                // length zero skip
                if (v.dx == 0 && v.dy == 0) continue;

                // perpendicular vector (one direction)
                Vector p = v.perpendicular();

                // For a square, the other two points are a + p and b + p (if p has same length as v, but using integer point grid we'll check membership)
                Point c = a.add(p);
                Point d = b.add(p);

                if (pointSet.contains(c) && pointSet.contains(d)) {
                    // we found A,B,C,D as a square; produce canonical ordering to avoid duplicates
                    List<Point> quad = Arrays.asList(a, b, c, d);
                    String key = canonicalKey(quad);
                    if (!seen.contains(key)) {
                        seen.add(key);
                        result.add(sortedCopy(quad));
                    }
                }

                // also check the other perpendicular direction (-p)
                Vector pm = new Vector(-p.dx, -p.dy);
                Point c2 = a.add(pm);
                Point d2 = b.add(pm);
                if (pointSet.contains(c2) && pointSet.contains(d2)) {
                    List<Point> quad = Arrays.asList(a, b, c2, d2);
                    String key = canonicalKey(quad);
                    if (!seen.contains(key)) {
                        seen.add(key);
                        result.add(sortedCopy(quad));
                    }
                }
            }
        }
        return result;
    }

    // Return a lexicographically sorted copy of the list (so presentation is consistent)
    private static List<Point> sortedCopy(List<Point> pts) {
        List<Point> copy = new ArrayList<>(pts);
        copy.sort((p1, p2) -> {
            if (p1.x != p2.x) return Integer.compare(p1.x, p2.x);
            return Integer.compare(p1.y, p2.y);
        });
        return copy;
    }

    // Produce a canonical key from 4 points (sorted lexicographically joined by '|')
    private static String canonicalKey(List<Point> pts) {
        List<Point> s = sortedCopy(pts);
        StringBuilder sb = new StringBuilder();
        for (Point p : s) {
            sb.append(p.x).append(',').append(p.y).append('|');
        }
        return sb.toString();
    }

    // Demo / main method
    public static void main(String[] args) {
        // Example 1: simple axis-aligned squares
        List<Point> example1 = Arrays.asList(
            new Point(1,2), new Point(5,6), new Point(4,5), new Point(1,1), // unit square
            new Point(4,1), new Point(9,3), new Point(2,6), new Point(1,2)  // extra points (makes other squares)
        );

        System.out.println("Example 1 points: " + example1);
        List<List<Point>> squares1 = findSquares(example1);
        System.out.println("Found squares (" + squares1.size() + "):");
        for (List<Point> sq : squares1) {
            System.out.println(sq);
        }

        // Example 2: rotated square of side sqrt(2) with integer coordinates
        List<Point> example2 = Arrays.asList(
            new Point(0,1), new Point(1,2), new Point(2,1), new Point(1,0)
        );
        System.out.println("\nExample 2 points: " + example2);
        List<List<Point>> squares2 = findSquares(example2);
        System.out.println("Found squares (" + squares2.size() + "):");
        for (List<Point> sq : squares2) {
            System.out.println(sq);
        }

        // Example 3: combined set
        Set<Point> combined = new HashSet<>();
        combined.addAll(example1);
        combined.addAll(example2);
        System.out.println("\nCombined point set size: " + combined.size());
        List<List<Point>> squaresCombined = findSquares(combined);
        System.out.println("Found squares in combined set (" + squaresCombined.size() + "):");
        for (List<Point> sq : squaresCombined) {
            System.out.println(sq);
        }
    }
}
