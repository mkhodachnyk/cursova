import com.wolfram.alpha.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main1 {

    public static void main(String[] args) {
        List<String> strFromFile = new ArrayList<String>();
        List<String> strU = new ArrayList<String>();
        List<String> strT = new ArrayList<String>();
        List<String> strU1 = new ArrayList<String>();
        List<String> strU2 = new ArrayList<String>();
        List<String> strU3 = new ArrayList<String>();

        List<Double> doubleU = new ArrayList<Double>();
        List<Double> doubleT = new ArrayList<Double>();
        List<Double> doubleU1 = new ArrayList<Double>();
        List<Double> doubleU2 = new ArrayList<Double>();
        List<Double> doubleU3 = new ArrayList<Double>();
        BufferedReader reader = null;
        int counter = 0;
        try {
            reader = new BufferedReader(new FileReader(new File("C:\\Users\\Mariana\\Desktop\\1.txt")));

            counter = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                strFromFile.add(line);
                counter++;
            }

            reader.close();
            strFromFile.remove(strFromFile.size() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //System.out.println(strFromFile.get(0).replaceAll("[\\s]{2,}", " ").split(" ")[2]);
        for (int i = 0; i < strFromFile.size(); i++) {
            strU.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[1]);
            strT.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[2]);
            strU1.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[3]);
            strU2.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[4]);
            strU3.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[5]);
        }
        for (int i = 0; i < strFromFile.size(); i++) {
            System.out.println(strU.get(i) + " " + strT.get(i) + strU1.get(i) + " " + strU2.get(i) + " " + strU3.get(i));
        }
        System.out.println(counter);
        for (int i = 0; i < strFromFile.size(); i++) {
            doubleU.add(Double.parseDouble(strU.get(i)));
            doubleT.add(Double.parseDouble(strT.get(i)));
            doubleU1.add(Double.parseDouble(strU1.get(i)));
            doubleU2.add(Double.parseDouble(strU2.get(i)));
            doubleU3.add(Double.parseDouble(strU3.get(i)));

        }
        double minU = Collections.min(doubleU);
        double minT = Collections.min(doubleU);

        double maxU = Collections.max(doubleU);
        double maxT = Collections.max(doubleU);
        System.out.println(minU+" "+ minT+ " "+ maxU+ " " + maxT);

    }

}
