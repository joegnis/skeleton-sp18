package byog.Core;

public class Utils {
    private static final double EPS = 1E-14;

    public static double compareToDouble(double val1, double val2) {
        if (val2 >= val1 - EPS && val2 <= val1 + EPS) {
            return 0;
        }
        return val2 - val1;
    }

    public static int rowColTo1D(int row, int column, int rowLength) {
        return row * rowLength + column;
    }
}
