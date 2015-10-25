package view;

import formula.Point2D;
import formula.Point3D;
import surface.ISurface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class MyComponent extends JFrame {
    public MyComponent(ISurface surface){
        super("Surface");
        JPanel panel = new MyPanel(surface);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
        setSize(700, 600);

        invalidate();
        validate();
        repaint();
    }

    private class MyPanel extends JPanel{
        private Point2D[][] myGridOfSurface;
        private double[][] myMatrix = new double[3][3];
        private ISurface mySurface;

        private double myXAngle = 0;
        private double myYAngle = 0;
        private int startXPosition;
        private int startYPosition;

        MyPanel(ISurface surface){
            mySurface = surface;
            init();
            addMouseListener(new MouseL());
            addMouseMotionListener(new MouseMotionL());
        }

        private class MouseMotionL extends MouseMotionAdapter{
            public void mouseDragged(MouseEvent me){
                if ((startXPosition - me.getX())/5 >= 1){
                    startXPosition = me.getX();
                    myXAngle -= Math.PI / 50;
                    repaint();
                }

                if ((startXPosition - me.getX())/5 <= -1){
                    startXPosition = me.getX();
                    myXAngle += Math.PI / 50;
                    repaint();
                }

                if ((startYPosition - me.getY())/5 >= 1){
                    startYPosition = me.getY();
                    myYAngle -= Math.PI / 50;
                    repaint();
                }

                if ((startYPosition - me.getY())/5 <= -1){
                    startYPosition = me.getY();
                    myYAngle += Math.PI / 50;
                    repaint();
                }
            }
        }

        private class MouseL extends MouseAdapter{
            public void mousePressed(MouseEvent me){
                startXPosition = me.getPoint().x;
                startYPosition = me.getPoint().y;
            }
        }
        public class MouseWheel implements MouseWheelListener {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {


                }
            }
        }
        private void init(){
            initMatrix();
            myGridOfSurface = new Point2D[mySurface.getUCount()][mySurface.getTCount()];
            for(int i = 0; i < mySurface.getUCount(); i++){
                for(int j = 0; j < mySurface.getTCount(); j++){
                    myGridOfSurface[i][j] = projection(mySurface.getValue(i, j), 300.0, 300.0);
                }
            }
        }
        @Override
        public void paint(Graphics g){
            super.paint(g);
            init();
            drawMySurface(Color.red, g);
        }

        private void drawMySurface(Color color, Graphics g){
            g.setColor(color);
            for (int i = 0; i < mySurface.getUCount(); i++ ){
                for (int j = 0; j < mySurface.getTCount()-1; j++){
                    drawMyLine(myGridOfSurface[i][j], myGridOfSurface[i][j+1], g);
                }
            }

            for (int j = 0; j < mySurface.getTCount(); j++ ){
                for (int i = 0; i < mySurface.getUCount()-1; i++){
                    drawMyLine(myGridOfSurface[i+1][ j], myGridOfSurface[i][j], g);
                }
            }

//		 draw axis
            /*g.setColor(Color.blue);
            Point2D p2d = projection(new Point3D(200, 0, 0), 150, 150);
            Point2D p2d0 = projection(new Point3D(0, 0, 0), 150, 150);
            drawMyLine(p2d, p2d0, g);
            p2d = projection(new Point3D(0, 200, 0), 150, 150);
            drawMyLine(p2d, p2d0, g);
            p2d = projection(new Point3D(0, 0, 200), 150, 150);
            drawMyLine(p2d, p2d0, g);*/
        }

        private void drawMyLine(Point2D point1, Point2D point2, Graphics g){
            int x0 = (int)Math.round(point1.u);
            int y0 = (int)Math.round(point1.v);

            int x1 = (int)Math.round(point2.u);
            int y1 = (int)Math.round(point2.v);

            g.drawLine(x1, y1, x0, y0);
        }

        private Point2D projection(Point3D point, double u0, double v0){
            Point3D p3dX = new Point3D(point.x * Math.cos(myXAngle) + point.y * Math.sin(myXAngle),
                    -point.x * Math.sin(myXAngle) + point.y * Math.cos(myXAngle),
                    point.z
            );
            Point3D p3d = new Point3D(p3dX.x,
                    p3dX.y * Math.cos(myYAngle) + p3dX.z * Math.sin(myYAngle),
                    -p3dX.y * Math.sin(myYAngle) + p3dX.z * Math.cos(myYAngle)
            );
            double u = u0 + myMatrix[0][0] * p3d.x + myMatrix[0][1] * p3d.y + myMatrix[0][2] * p3d.z;
            double v = v0 + myMatrix[1][0] * p3d.x + myMatrix[1][1] * p3d.y + myMatrix[1][2] * p3d.z;
            return new Point2D(u, v);
        }

        private void initMatrix(){
            final int typeOfMatrix = 3;
            final double theta = Math.PI / 6;

            if(typeOfMatrix == 1){
                // isometric
                myMatrix[0][0] = -1.0 / Math.sqrt(2.0);
                myMatrix[0][1] = - myMatrix[0][0];
                myMatrix[0][2] = 0.0;
                myMatrix[1][0] = -1.0 / Math.sqrt(6.0);
                myMatrix[1][1] = myMatrix[1][0];
                myMatrix[1][2] = -2.0 * myMatrix[1][0];
                myMatrix[2][0] = 1.0 / Math.sqrt(3.0);
                myMatrix[2][1] = myMatrix[2][0];
                myMatrix[2][2] = -myMatrix[2][0];
            }

            if(typeOfMatrix == 2){
                // usefull
                myMatrix[0][0] = Math.cos(theta);
                myMatrix[0][1] = 1.0;
                myMatrix[0][2] = 0.0;
                myMatrix[1][0] = -Math.sin(theta);
                myMatrix[1][1] = 0.0;
                myMatrix[1][2] = -1.0;
            }

            if(typeOfMatrix == 3){
                // usefull
                myMatrix[0][0] = Math.cos(theta);
                myMatrix[0][1] = Math.cos(theta);
                myMatrix[0][2] = 0.0;
                myMatrix[1][0] = -Math.sin(theta);
                myMatrix[1][1] = Math.sin(theta);
                myMatrix[1][2] = -1.0;
            }
        }


    }
}