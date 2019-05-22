import JsonObjects.Objects.MyArrayList;
import JsonObjects.Objects.Post;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class NodeR implements Cloneable{
    private final int DIMENSION = 2;
    private Point2D[] coords;
    private MyArrayList<NodeR> nodes;
    private Object object;
    private boolean square;
    private NodeR parent;
    private int pos;

    public NodeR() {
        nodes = new MyArrayList<>(1);
        object = null;
        square = false;
        parent = null;
        pos = 0;
    }

    public NodeR(double[] coords, Object object){

        this.coords = new Point2D[coords.length / 2];

        for(int i = 0; i < coords.length / 2; i++)
            this.coords[i] = new Point2D.Double();

        int aux = 0;
        for(int i = 0; i < this.coords.length; i++) {
            this.coords[i].setLocation(coords[aux], coords[aux + 1]);
            aux += 2;
        }   //for

        this.object = object;
        nodes = new MyArrayList<>(1);

        square = false;
        parent = null;
    }

    protected NodeR clone() throws CloneNotSupportedException {

        NodeR n = (NodeR) super.clone();
        return n;
    }

    public double getMaxX() {
        double max = nodes.get(0).getCoords()[0].getX();

        for (int i = 1; i < nodes.getSize(); i++)
            if (max < nodes.get(i).getCoords()[0].getX())
                max = nodes.get(i).getCoords()[0].getX();

        return max;
    }

    public double getMaxXSquare(){
        double max = nodes.get(0).getCoords()[1].getX();

        for (int i = 1; i < nodes.getSize(); i++)
            if (max < nodes.get(i).getCoords()[1].getX())
                max = nodes.get(i).getCoords()[1].getX();

        return max;
    }

    public double getMinX(){
        double min = nodes.get(0).getCoords()[0].getX();

        for (int i = 1; i < nodes.getSize(); i++)
            if (min > nodes.get(i).getCoords()[0].getX())
                min = nodes.get(i).getCoords()[0].getX();

        return min;
    }

    public double getMaxY() {
        double max = nodes.get(0).getCoords()[0].getY();

        for (int i = 1; i < nodes.getSize(); i++)
            if (max < nodes.get(i).getCoords()[0].getY())
                max = nodes.get(i).getCoords()[0].getY();

        return max;
    }

    public double getMaxYSquare(){
        double max = nodes.get(0).getCoords()[1].getY();

        for (int i = 1; i < nodes.getSize(); i++)
            if (max < nodes.get(i).getCoords()[1].getY())
                max = nodes.get(i).getCoords()[1].getY();

        return max;
    }

    public double getMinY(){
        double min = nodes.get(0).getCoords()[0].getY();

        for (int i = 1; i < nodes.getSize(); i++)
            if (min > nodes.get(i).getCoords()[0].getY())
                min = nodes.get(i).getCoords()[0].getY();

        return min;
    }

    public double[] averageCoord(){
        double[] average = new double[DIMENSION];

        for(int i = 0; i < nodes.getSize(); i++){
            average[0] += nodes.get(i).getCoords()[0].getX();
            average[1] += nodes.get(i).getCoords()[0].getY();
        }   //for

        average[0] /= nodes.getSize();
        average[1] /= nodes.getSize();

        return average;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Point2D[] getCoords() {
        return coords;
    }

    public void setCoords(Point2D[] coords) {
        this.coords = coords;
    }

    public MyArrayList<NodeR> getNodes() {
        return nodes;
    }

    public void setNodes(MyArrayList<NodeR> nodes) {

        this.nodes = nodes;
    }

    public boolean isSquare() {
        return square;
    }

    public void setSquare(boolean square) {
        this.square = square;
    }

    public NodeR getParent() {
        return parent;
    }

    public void setParent(NodeR parent) {
        this.parent = parent;
    }

    public int getDIMENSION() {
        return DIMENSION;
    }
}
