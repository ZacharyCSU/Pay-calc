import java.io.*;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        String inputFile = "pay.csv";
        String outputFile = "output.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            // Header row
            writer.write("EmpID,Rate,Hours,Weekly Pay");
            writer.newLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equalsIgnoreCase("EmpID")) {
                    continue; // skip original header
                }

                String empID = parts[0];
                double rate = Double.parseDouble(parts[1]);
                double hours = Double.parseDouble(parts[2]);

                double pay;
                if (hours > 40) {
                    pay = 40 * rate + (hours - 40) * (rate * 1.5);
                } else {
                    pay = hours * rate;
                }

                DecimalFormat df = new DecimalFormat("$#,##0.00");
                String formattedPay = df.format(pay);

                String outputLine = empID + "," + rate + "," + hours + "," + formattedPay;
                writer.write(outputLine);
                writer.newLine();
            }

            System.out.println("Data processed and written to " + outputFile);

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}