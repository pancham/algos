package misc;

import java.util.PriorityQueue;

/**
 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
 *
 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
 *
 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
 *
 * Input: points = [[1,3],[-2,2]], k = 1
 * Output: [[-2,2]]
 * Explanation:
 * The distance between (1, 3) and the origin is sqrt(10).
 * The distance between (-2, 2) and the origin is sqrt(8).
 * Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
 * We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
 */
public class KClosestPointsToOrigin {

    public static void main(String[] args) {
        int[][] points = new int[][] {
                {1,3},{-2,2}   // {-2, 2} with k = 1
//                {3,3},{5,-1},{-2,4} // [3,3],[-2,4] with k = 2
        };
        int k = 1;

        KClosestPointsToOrigin kc = new KClosestPointsToOrigin();
        int[][] ret = kc.kClosest(points, k);

        for (int i = 0; i < ret.length; i++) {
            System.out.println(ret[i][0] + "," + ret[i][1]);
        }
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        double distance() {
            return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
    }

    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<Point> p = new PriorityQueue<>((a, b) -> {
            double diff = a.distance() - b.distance();
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            }

            return 0;
        });

        for (int i = 0; i < points.length; i++) {
            p.add(new Point(points[i][0], points[i][1]));
        }

        // get top k elements
        int[][] ret = new int[k][2];
        for (int i = 0; i < k; i++) {
            Point point = p.poll();
            ret[i][0] = point.x;
            ret[i][1] = point.y;
        }

        return ret;

    }
}
