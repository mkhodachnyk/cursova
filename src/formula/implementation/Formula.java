package formula.implementation;

import java.util.ArrayList;

import exceptions.MyException;
import formula.*;
import formula.node.TreeBuilder;
import formula.token.IToken;
import formula.token.TokenFactory;

public class Formula implements IFormula{
    private Expression myXExpression, myYExpression, myZExpression;
    private ConstantExpression myUStartExpression;
    private ConstantExpression myUEndExpression;
    private ConstantExpression myTStartExpression;
    private ConstantExpression myTEndExpression;
    private boolean isValid = true;
    private String myStringX, myStringY, myStringZ, myStringStartT;
    private String myStringEndT, myStringStartU, myStringEndU;

    public Formula(String stringX, String stringY, String stringZ, String stringStartT,
                   String stringEndT, String stringStartU, String stringEndU){
        myStringX = stringX;
        myStringY = stringY;
        myStringZ = stringZ;
        myStringStartT = stringStartT;
        myStringEndT = stringEndT;
        myStringStartU = stringStartU;
        myStringEndU = stringEndU;

        try{
            myXExpression = new Expression(stringX);
            myYExpression = new Expression(stringY);
            myZExpression = new Expression(stringZ);

            myUStartExpression = new ConstantExpression(stringStartU);
            myUEndExpression = new ConstantExpression(stringEndU);
            myTStartExpression = new ConstantExpression(stringStartT);
            myTEndExpression = new ConstantExpression(stringEndT);
        } catch(MyException e){
            System.out.println(e);
            isValid = false;
        }
    }

    public String getXFormula() {
        return myStringX;
    }

    public String getYFormula() {
        return myStringY;
    }

    public String getZFormula() {
        return myStringZ;
    }

    public String getStringStartT() {
        return myStringStartT;
    }

    public String getStringEndT() {
        return myStringEndT;
    }

    public String getStringStartU() {
        return myStringStartU;
    }

    public String getStringEndU() {
        return myStringEndU;
    }

    public double getStartT() {
        return myTStartExpression.value();
    }

    public double getEndT() {
        return myTEndExpression.value();
    }

    public double getStartU() {
        return myUStartExpression.value();
    }

    public double getEndU() {
        return myUEndExpression.value();
    }

    public boolean isValid() {
        return isValid;
    }

    public void setMyStringX(String myStringX){this.myStringX = myStringX;}
    public void setMyStringY(String myStringY){this.myStringY = myStringY;}
    public void setMyStringZ(String myStringZ){this.myStringZ = myStringZ;}

    public Point3D evaluate(double u, double t){
        return new Point3D(
                myXExpression.evaluate(u, t),
                myYExpression.evaluate(u, t),
                myZExpression.evaluate(u, t)
        );
    }
}
