package view;

import com.wolfram.alpha.*;
import exceptions.*;
import formula.*;
import formula.implementation.*;
import io.*;
import surface.*;
import surface.impl.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;


public class View extends JFrame{
    private static String appid = "W7REGA-5TKJ9J27G4";
    private ISurface mySurface;
    private ISurface mySurfaceTransformed;
    private IFormula myFormula;
    private IFormula myFormulaTransform;
    private FormulaTextField myTextXFormula, myTextYFormula, myTextZFormula,myTextUTransformationFormula,myTextVTransformationFormula,myTextWTransformationFormula;
    private FormulaTextField myTextStartU, myTextEndU, myTextStartT, myTextEndT;
    private FormulaTextField myTextCountU, myTextCountT;
    private JButton myDrawButton;
    private JButton myTransformButton;
    private JPanel myPanel;
    private boolean myIsSaved = true;
    private int myWrongFieldCounter = 0;
    private String d1X1 = " ";
    private String d2X1 = " ";
    private String d1X2 = " ";
    private String d2X2 = " ";
    private String d1X3 = " ";
    private String d2X3 = " ";
    private String s = " ";
    private String s1 = " ";
    private String s2 = " ";
    public View(){
        super("3dGeometry");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new ExitListener());
        myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());
        myPanel.add(createFormulaPanel(), BorderLayout.WEST);
        getContentPane().add(myPanel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
        init();
        setVisible(true);
        pack();
    }

    private JMenu createFileMenu(){
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        //open.addActionListener(new OpenListener());

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new SaveListener());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if (!myIsSaved) {
                    int result = JOptionPane.showConfirmDialog(null, "Do you want to save?");
                    if (result == JOptionPane.YES_OPTION) {
                        saveAction();
//					getToolkit().getSystemEventQueue().postEvent(
//					new ActionEvent(save, ActionEvent.ACTION_PERFORMED, null));
                    }
                    if (result != JOptionPane.CANCEL_OPTION) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);
                }
            }
        });
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(exit);

        return file;
    }

    private class ExitListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            if (!myIsSaved) {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to save?");
                if (result == JOptionPane.YES_OPTION) {
                    saveAction();
//				getToolkit().getSystemEventQueue().postEvent(
//				new ActionEvent(save, ActionEvent.ACTION_PERFORMED, null));
                }
                if (result != JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
    }
	
	/*private class OpenListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser(); 
			fileChooser.setDialogTitle("Open");
			fileChooser.addChoosableFileFilter(new MyFilesFilter());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(View.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
					XMLReader1 xmlReader1 = new XMLReader1(fileInputStream);
					myFormula = xmlReader1.getFormula();
					mySurface = xmlReader1.getSurface();
					initAfterReading();
				} catch(Exception exception) {
					System.out.println("OpenListener " + exception);
				}
			}
		}
	}*/

    private class SaveListener extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            myIsSaved = true;
            saveAction();
        }
    }

    private void saveAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as");
        fileChooser.addChoosableFileFilter(new MyFilesFilter());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = fileChooser.showOpenDialog(View.this);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                myFormula = new Formula(myTextXFormula.getText(),myTextYFormula.getText(),
                        myTextZFormula.getText(), myTextStartT.getText(), myTextEndT.getText(),
                        myTextStartU.getText(), myTextEndU.getText());
                myFormulaTransform = new Formula(myTextUTransformationFormula.getText(),myTextVTransformationFormula.getText(),
                        myTextWTransformationFormula.getText(), myTextStartT.getText(), myTextEndT.getText(),
                        myTextStartU.getText(), myTextEndU.getText());
                mySurface = new Surface(myFormula, myTextCountU.getText(), myTextCountT.getText());
                mySurfaceTransformed = new Surface(myFormulaTransform, myTextCountU.getText(), myTextCountT.getText());
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith("srf")) {
                    file = new File(file.getAbsolutePath() + ".srf");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                new XMLWriter().write(mySurface, fileOutputStream);
                new XMLWriter().write(mySurfaceTransformed, fileOutputStream);
            } catch(Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private class MyFilesFilter extends FileFilter {
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            return file.getName().endsWith("srf");
        }
        public String getDescription() {
            return "XML files(*.srf)";
        }
    }


    private FormulaTextField createVariablePanel(String string, JPanel main, String content, int fieldSize,
                                                 boolean constant, boolean isCount){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel name = new JLabel(string + " = ");
        panel.add(name);
        panel.add(Box.createVerticalStrut(12));
        FormulaTextField textField = constant ?
                isCount ?
                        new ConstantCountFormulaTextField(content, fieldSize, this) :
                        new ConstantFormulaTextField(content, fieldSize, this) :
                new NonConstantFormulaTextField(content, fieldSize, this);
        textField.addCaretListener(new MyCaretListener(textField));
        setTextFieldSize(textField);
        panel.add(textField);
        main.add(panel);

        return textField;
    }

    private void setTextFieldSize(JTextField textField){
        Dimension size = textField.getPreferredSize();
        size.width = textField.getMaximumSize().width;
        textField.setMaximumSize(size);
    }

    private FormulaTextField[] createBoundPanel(String startString, String startContent,
                                                String endString, String endContent,
                                                JPanel main, int FieldSize, boolean isCount){

        JPanel commonPanel = new JPanel();
        commonPanel.setLayout(new BoxLayout(commonPanel, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        FormulaTextField startField = createVariablePanel(startString, panel, startContent, FieldSize, true, isCount);
        panel.add(Box.createHorizontalStrut(3));
        FormulaTextField endField = createVariablePanel(endString, panel, endContent, FieldSize, true, isCount);

        commonPanel.add(Box.createVerticalStrut(6));
        commonPanel.add(panel);

        main.add(commonPanel);

        return new FormulaTextField[] { startField, endField };

    }

    private class MyCaretListener implements CaretListener {
        FormulaTextField myField;

        MyCaretListener(FormulaTextField field) {
            myField = field;
        }

        public void caretUpdate(CaretEvent e) {
            myIsSaved = false;
            myField.check();
            //init();
            myDrawButton.setEnabled(myWrongFieldCounter == 0);
            if (myWrongFieldCounter == 0) {
                myFormula = new Formula(myTextXFormula.getText(),myTextYFormula.getText(),
                        myTextZFormula.getText(), myTextStartT.getText(), myTextEndT.getText(),
                        myTextStartU.getText(), myTextEndU.getText());
                mySurface = new Surface(myFormula, myTextCountU.getText(), myTextCountT.getText());
            }

            myTransformButton.setEnabled(myWrongFieldCounter == 0);
            if (myWrongFieldCounter == 0) {
                //**********************************************************************************

                //**********************************************************************************
                myFormulaTransform = new Formula(myTextXFormula.getText()+ " + (" + s
                        + ")*(" + d1X1 + ") + (" + s1 + ") * (" + d2X1 + ") + (" + s2
                        + ") * ((" + d1X2 + ")*(" + d2X3 + ") - (" + d2X2 + ")*(" + d1X3 + "))",myTextYFormula.getText()+ " + (" + s
                        + ")*(" + d1X2 + ") + (" + s1 + ") * (" + d2X2 + ") + (" + s2
                        + ") * ((" + d1X3 + ")*(" + d2X1 + ") - (" + d2X3 + ")*(" + d1X1 + "))",
                        myTextZFormula.getText()+ " + (" + s
                                + ")*(" + d1X3 + ") + (" + s1 + ") * (" + d2X3 + ") + (" + s2
                                + ") * ((" + d1X1 + ")*(" + d2X2 + ") - (" + d2X1 + ")*(" + d1X2 + "))", myTextStartT.getText(), myTextEndT.getText(),
                        myTextStartU.getText(), myTextEndU.getText());
                mySurfaceTransformed = new Surface(myFormulaTransform, myTextCountU.getText(), myTextCountT.getText());
            }
        }
    }


    private JPanel createFormulaPanel(){

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        myTextXFormula = createVariablePanel("x", main, "20*sin(u)*cos(t)", 15, false, false);
        main.add(Box.createVerticalStrut(12));
        myTextYFormula = createVariablePanel("y", main, "20*sin(u)*sin(t)", 15, false, false);
        main.add(Box.createVerticalStrut(12));
        myTextZFormula = createVariablePanel("z", main, "20*cos(u)", 15, false, false);

        main.add(new JLabel("transformation options"), CENTER_ALIGNMENT);

        myTextUTransformationFormula = createVariablePanel("u", main, "sin(u)*cos(t)", 15, false, false);
        main.add(Box.createVerticalStrut(12));
        myTextVTransformationFormula = createVariablePanel("v", main, "sin(u)*sin(t)", 15, false, false);
        main.add(Box.createVerticalStrut(12));
        myTextWTransformationFormula = createVariablePanel("w", main, "cos(u)", 15, false, false);
        main.add(Box.createVerticalStrut(12));
        main.add(Box.createVerticalStrut(27));


        main.add(new JLabel("bounds of variables"), CENTER_ALIGNMENT);
        FormulaTextField[] textFields = createBoundPanel("u:   from", "0", "to", "1", main, 3, false);
        myTextStartU = textFields[0];
        myTextEndU = textFields[1];
        main.add(Box.createVerticalStrut(10));

        textFields = createBoundPanel("t:   from", "0", "to", "1", main, 3, false);
        myTextStartT = textFields[0];
        myTextEndT = textFields[1];
        main.add(Box.createVerticalStrut(35));

        textFields = createBoundPanel("count U", "261", "count T", "261", main, 3, true);
        myTextCountU = textFields[0];
        myTextCountT = textFields[1];
        main.add(Box.createVerticalStrut(35));

        main.add(Box.createVerticalStrut(12));

        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        myDrawButton = new JButton("draw");
        myTransformButton = new JButton("transform");
        myDrawButton.addActionListener(new MyActionListener());
        myTransformButton.addActionListener(new MyActionListenerTransform());
        flow.add(myDrawButton);
        flow.add(myTransformButton);
        main.add(flow);
        return main;
    }

    private class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            new MyComponent(mySurface);
        }
    }
    private class MyActionListenerTransform implements ActionListener{
        public void actionPerformed(ActionEvent e){
            d1X1 = getFromWA(myTextXFormula.getText());
            d2X1 = getFromWA("second derivative of " + myTextXFormula.getText());
            d1X2 = getFromWA(myTextYFormula.getText());
            d2X2 = getFromWA("second derivative of " + myTextYFormula.getText());
            d1X3 = getFromWA(myTextZFormula.getText());
            d2X3 = getFromWA("second derivative of " + myTextZFormula.getText());
            s = new String(myTextUTransformationFormula.getText());
            s1 = new String(myTextVTransformationFormula.getText());
            s2 = new String(myTextWTransformationFormula.getText());
            new MyComponent(mySurfaceTransformed);
        }
    }

    private String getFromWA(String input) {

        //myTextUTransformationFormula.setText(myTextXFormula.getText()+ " + " +myTextUTransformationFormula.getText());
        String output = "";
        // The WAEngine is a factory for creating WAQuery objects,
        // and it also used to perform those queries. You can set properties of
        // the WAEngine (such as the desired API output format types) that will
        // be inherited by all WAQuery objects created from it. Most applications
        // will only need to crete one WAEngine object, which is used throughout
        // the life of the application.
        WAEngine engine = new WAEngine();

        // These properties will be set in all the WAQuery objects created from this WAEngine.
        engine.setAppID(appid);
        engine.addFormat("plaintext");
        // Create the query.
        WAQuery query = engine.createQuery();

        // Set properties of the query.
        query.setInput(input);

        try {
            // For educational purposes, print out the URL we are about to send:
            System.out.println("Query URL:");
            System.out.println(engine.toURL(query));
            System.out.println("");

            // This sends the URL to the Wolfram|Alpha server, gets the XML result
            // and parses it into an object hierarchy held by the WAQueryResult object.
            WAQueryResult queryResult = engine.performQuery(query);

            if (queryResult.isError()) {
                System.out.println("Query error");
                System.out.println("  error code: " + queryResult.getErrorCode());
                System.out.println("  error message: " + queryResult.getErrorMessage());
            } else if (!queryResult.isSuccess()) {
                System.out.println("Query was not understood; no results available.");
            } else {
                // Got a result.
                //System.out.println("Successful query. Pods follow:\n");
                for (WAPod pod : queryResult.getPods()) {
                    if (!pod.isError()&& pod.getTitle().equals("Derivative") ) {
                        //System.out.println(pod.getTitle());
                        //System.out.println("------------");
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    //System.out.println(((WAPlainText) element).getText().split("=")[1].replace(' ', '*').substring(1));
                                    //System.out.println("");
                                    output = ((WAPlainText) element).getText().split("=")[1].replace(' ', '*').substring(1);
                                }
                            }
                        }
                        //System.out.println("");
                    }
                }
                // We ignored many other types of Wolfram|Alpha output, such as warnings, assumptions, etc.
                // These can be obtained by methods of WAQueryResult or objects deeper in the hierarchy.
            }
        } catch (WAException e) {
            e.printStackTrace();
        }
       return output;
    }
    public void init(){
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
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
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
        myTextStartU.setText(Integer.toString((int)minU));
        myTextStartT.setText(Integer.toString((int)minT));
        myTextEndU.setText(Integer.toString((int)maxU));
        myTextEndT.setText(Integer.toString((int)maxT));
        /*d1X1 = getFromWA(myTextXFormula.getText());
        d2X1 = getFromWA("second derivative of " + myTextXFormula.getText());
        d1X2 = getFromWA(myTextYFormula.getText());
        d2X2 = getFromWA("second derivative of " + myTextYFormula.getText());
        d1X3 = getFromWA(myTextZFormula.getText());
        d2X3 = getFromWA("second derivative of " + myTextZFormula.getText());
        s = new String(myTextUTransformationFormula.getText());
        s1 = new String(myTextVTransformationFormula.getText());
        s2 = new String(myTextWTransformationFormula.getText());*/
        myDrawButton.setEnabled(myWrongFieldCounter == 0);
        if (myWrongFieldCounter == 0) {
            myFormula = new Formula(myTextXFormula.getText(),myTextYFormula.getText(),
                    myTextZFormula.getText(), myTextStartT.getText(), myTextEndT.getText(),
                    myTextStartU.getText(), myTextEndU.getText());
            mySurface = new Surface(myFormula, myTextCountU.getText(), myTextCountT.getText());
        }

        myTransformButton.setEnabled(myWrongFieldCounter == 0);
        if (myWrongFieldCounter == 0) {
            //**********************************************************************************

            //**********************************************************************************
            myFormulaTransform = new Formula(myTextXFormula.getText()+ " + (" + s
                    + ")*(" + d1X1 + ") + (" + s1 + ") * (" + d2X1 + ") + (" + s2
                    + ") * ((" + d1X2 + ")*(" + d2X3 + ") - (" + d2X2 + ")*(" + d1X3 + "))",myTextYFormula.getText()+ " + (" + s
                    + ")*(" + d1X2 + ") + (" + s1 + ") * (" + d2X2 + ") + (" + s2
                    + ") * ((" + d1X3 + ")*(" + d2X1 + ") - (" + d2X3 + ")*(" + d1X1 + "))",
                    myTextZFormula.getText()+ " + (" + s
                            + ")*(" + d1X3 + ") + (" + s1 + ") * (" + d2X3 + ") + (" + s2
                            + ") * ((" + d1X1 + ")*(" + d2X2 + ") - (" + d2X1 + ")*(" + d1X2 + "))", myTextStartT.getText(), myTextEndT.getText(),
                    myTextStartU.getText(), myTextEndU.getText());
            mySurfaceTransformed = new Surface(myFormulaTransform, myTextCountU.getText(), myTextCountT.getText());
        }

    }

    private void initAfterReading() {

        myTextXFormula.setText(myFormula.getXFormula());
        myTextXFormula.check();
        myTextUTransformationFormula.setText(myTextXFormula.getText()+ " + (" + s
                + ")*(" + d1X1 + ") + (" + s1 + ") * (" + d2X1 + ") + (" + s2
                + ") * ((" + d1X2 + ")*(" + d2X3 + ") - (" + d2X2 + ")*(" + d1X3 + "))");
        myTextUTransformationFormula.check();
        myTextYFormula.setText(myFormula.getYFormula());
        myTextYFormula.check();
        myTextVTransformationFormula.setText(myTextYFormula.getText()+ " + (" + s
                + ")*(" + d1X2 + ") + (" + s1 + ") * (" + d2X2 + ") + (" + s2
                + ") * ((" + d1X3 + ")*(" + d2X1 + ") - (" + d2X3 + ")*(" + d1X1 + "))");
        myTextVTransformationFormula.check();
        myTextZFormula.setText(myFormula.getZFormula());
        myTextZFormula.check();
        myTextWTransformationFormula.setText(myTextZFormula.getText()+ " + (" + s
                + ")*(" + d1X3 + ") + (" + s1 + ") * (" + d2X3 + ") + (" + s2
                + ") * ((" + d1X1 + ")*(" + d2X2 + ") - (" + d2X1 + ")*(" + d1X2 + "))");
        myTextWTransformationFormula.check();
        myTextStartU.setText(myFormula.getStringStartU());
        myTextStartU.check();
        myTextEndU.setText(myFormula.getStringEndU());
        myTextEndU.check();
        myTextStartT.setText(myFormula.getStringStartT());
        myTextStartT.check();
        myTextEndT.setText(myFormula.getStringEndT());
        myTextEndT.check();
        myTextCountU.setText(mySurface.getStringCountU());
        myTextCountU.check();
        myTextCountT.setText(mySurface.getStringCountT());
        myTextCountT.check();
    }

    public void increaseWrongFieldCounter() {
        myWrongFieldCounter++;
    }

    public void decreaseWrongFieldCounter() {
        myWrongFieldCounter--;
    }
}







