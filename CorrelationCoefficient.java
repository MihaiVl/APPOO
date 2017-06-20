import com.csvreader.CsvReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CorrelationCoefficient {
    private File file;

    public CorrelationCoefficient(File file) {
        this.file = file;
    }

    public void calculatecorecation(){

        Double[] x = getData("x");
        Double[] y = getData("y");
        Double[] z = getData("z");

        printCorrelationCoefficient(x,y);
        printCorrelationCoefficient(x,z);
        printCorrelationCoefficient(y,z);


    }

    public void printCorrelationCoefficient(Double[] x, Double[] y) {


        double r;
        double nr;
        double dr_1;
        double dr_2;
        double dr_3;
        double dr;
        double[] xx;
        double yy[];


        xx = new double[7];
        yy = new double[7];

        double sum_y = 0;
        double sum_yy = 0;
        double sum_xy = 0;
        double sum_x = 0;
        double sum_xx = 0;



        int i;
        int n = 7;

        for (i = 0; i < n; i++) {
            xx[i] = x[i] * x[i];
            yy[i] = y[i] * y[i];
        }
        for (i = 0; i < n; i++) {
            sum_x += x[i];
            sum_y += y[i];
            sum_xx += xx[i];
            sum_yy += yy[i];
            sum_xy += x[i] * y[i];
        }
        nr = (n * sum_xy) - (sum_x * sum_y);
        double sum_x2 = sum_x * sum_x;
        double sum_y2 = sum_y * sum_y;
        dr_1 = (n * sum_xx) - sum_x2;
        dr_2 = (n * sum_yy) - sum_y2;
        dr_3 = dr_1 * dr_2;
        dr = Math.sqrt(dr_3);
        r = (nr / dr);

        if (r > 0.85) {
            System.out.println("Classifier: " + r);
        }
        if (r < 0.85) {
            System.out.println("Classifier: " + r);
        }

        System.out.println("Total Numbers: " + n + ";" + " Correlation Coefficient: " + r);
    }

    private Double[] getData(String columnName) {
        CsvReader reader = null;
        List<Double> list = new ArrayList<>();

        try {
            reader = new CsvReader(String.valueOf(file));
            reader.setDelimiter(';');
            reader.readHeaders();
            while (reader.readRecord()) {
                list.add(Double.valueOf(reader.get(columnName)));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
       finally {
            if (reader != null)
                reader.close();
        }
        return list.toArray(new Double[list.size()]);
    }

    public static void main(String[] args) {
        File file = new File("E:\\corr.csv");
        CorrelationCoefficient coefficient = new CorrelationCoefficient(file);
        coefficient.calculatecorecation();

    }
}