package surface.impl;

import exceptions.*;
import formula.*;
import formula.implementation.*;
import surface.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;


public class Surface implements ISurface{
    private int myCountT, myCountU;
    private Point3D[][] myGridOfPoint;
    private IFormula myFormula;
    private ConstantCountExpression myCountUExpression, myCountTExpression;
    private boolean isValid = true;
    private String myStringCountU, myStringCountT;
    public IFormula getMyFormula() {return myFormula;}
    public Surface(IFormula formula, String stringCountU, String stringCountT){
        myFormula = formula;
        myStringCountU = stringCountU;
        myStringCountT = stringCountT;
        if (!myFormula.isValid()) {
            isValid = false;
        } else {
            try {
                myCountTExpression = new ConstantCountExpression(stringCountT);
                myCountUExpression = new ConstantCountExpression(stringCountU);

                myCountU = (int)myCountUExpression.value();
                myCountT = (int)myCountTExpression.value();
                myGridOfPoint = new Point3D[myCountU][myCountT];
                init();
            } catch(MyException e) {
                isValid = false;
                System.out.println(e);
            }
        }
    }

    public IFormula getFormula() {
        return myFormula;
    }

    public String getStringCountU() {
        return myStringCountU;
    }

    public String getStringCountT() {
        return myStringCountT;
    }

    private void init() {
        for (int i = 0; i < myCountU; i++) {

            double u = (myFormula.getEndU() - myFormula.getStartU()) / (myCountU - 1) * i
                    + myFormula.getStartU();

            for (int j = 0; j < myCountT; j++) {
                double t = (myFormula.getEndT() - myFormula.getStartT()) / (myCountT - 1) * j
                        + myFormula.getStartT();
                myGridOfPoint[i][j] = myFormula.evaluate(u, t);
            }
        }

        /*List<String> strFromFile = new ArrayList<String>();
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

        for (int i = 0; i < strFromFile.size(); i++) {
            strU.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[1]);
            strT.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[2]);
            strU1.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[3]);
            strU2.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[4]);
            strU3.add(strFromFile.get(i).replaceAll("[\\s]{2,}", " ").split(" ")[5]);
        }

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
        Collections.sort(doubleU);
        Collections.sort(doubleT);
        for (int i = 0; i <counter-1; i++) {

            double u = doubleU.get(i);

            for (int j = 0; j < counter-1; j++) {
                double t = doubleT.get(i);
                myGridOfPoint[i][j] = myFormula.evaluate(u, t);
            }

        }*/
    }

    public boolean isValid() {
        return isValid;
    }

    public int getTCount() {
        return myCountT;
    }

    public int getUCount() {
        return myCountU;
    }

    public Point3D getValue(int i, int j) {
        return myGridOfPoint[i][j];
    }

    public void setMyFormula(IFormula formula){this.myFormula = formula;}
}
