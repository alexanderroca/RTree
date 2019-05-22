import JsonObjects.Objects.MyArrayList;
import JsonObjects.Objects.Post;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * R-Tree class
 *
 * @author Alexander Roca
 * @version 12/04/2019 - 0.1
 */

public class RTree {
    private final double SMOOTH_ADDITION = 0.000000000000000001;
    private final int DIMENSION = 4;    //Dimensio de coordenades
    private NodeR root;
    private final int size; //Maxima capacitat que pot contenir cada node
    private int pos;
    private MyArrayList<Post> posts;

    public RTree(int size, double[] coord_total) {

        this.root = new NodeR(coord_total, null);
        this.size = size;
        pos = 0;
        posts = new MyArrayList<>(1);
    }

    public void preOrder(NodeR n){
        if(n == null)
            return;
        if(n.getObject() instanceof Post)
            System.out.println("ID: " + ((Post) n.getObject()).getId() + "- User: " +
                    ((Post) n.getObject()).getPublished_by());
        if(allElementsAreNull(n))
            preOrder(n.getNodes().get(pos));
        //preOrder(n.getRight());
    }

    public boolean allElementsAreNull(NodeR n){

        for(int i = 0; i < n.getNodes().getSize(); i++){
            if(n.getNodes().get(i) != null)
                return true;
        }

        return false;
    }

    public boolean checkInterval(NodeR n, Point2D point1, Point2D point2){
        return n.getCoords()[0].getX() >= point1.getX() &&
                n.getCoords()[0].getY() >= point1.getY() &&
                n.getCoords()[0].getX() <= point2.getX() &&
                n.getCoords()[0].getY() <= point2.getY();
    }

    public void search(double[] interval, NodeR n){

        Point2D point1 = new Point2D.Double(interval[0], interval[1]);
        Point2D point2 = new Point2D.Double(interval[2], interval[3]);

        if(!n.isSquare() && checkInterval(n, point1, point2)) {
            for(int i = 0; i < n.getNodes().getSize(); i++){
                if(n.getNodes().get(i).getCoords()[0].getX() >= point1.getX() &&
                    n.getNodes().get(i).getCoords()[0].getY() >= point1.getY() &&
                    n.getNodes().get(i).getCoords()[0].getX() <= point2.getX() &&
                    n.getNodes().get(i).getCoords()[0].getY() <= point2.getY()){
                    posts.add((Post) n.getNodes().get(i).getObject());
                }
            }   //for
        }   //if

        for(int i = 0; i < n.getNodes().getSize(); i++) {

            if (n.isSquare() && checkIfItsInside(point1, n))
                search(interval, n.getNodes().get(i));
            else if (n.isSquare() && checkIfItsInside(point2, n))
                search(interval, n.getNodes().get(i));
        }   //for

    }

    public boolean insert_T(double[] coord, Object obj){

        try{
            root = insert(root, coord, obj);

            while(root.getParent() != null)
                root = root.getParent();

            return true;
        }catch(NodeAlreadyExists e){
            System.out.println(e.getMessage() + coord[0] + "-" + coord[1]);
            return false;
        }
    }

    public NodeR insert(NodeR n, double[] coord, Object obj) throws NodeAlreadyExists{

        Point2D aux_point = new Point2D.Double();
        aux_point.setLocation(coord[0], coord[1]);

        //Comprovo que el punt estigui dins d'un quadrat
        if(checkIfItsInside(aux_point, n)){

            //Cas que el node contingui quadrats
            if(n.isSquare())
                insert(bestCandidate(aux_point, n), coord, obj);    //Busco un node que contingui punts

            //Cas que el node contingui punts
            else {
                if (n.getNodes().getSize() == size) {

                    checkIfItsExists(n, coord); //Comprovar que el nou element ja existeix o no
                    n = partition(n);   //Realitzacio de la particio del quadrat
                    insert(bestCandidate(aux_point, n), coord, obj);    //Busco un node que contingui punts

                }   //if
                //Add on the ArrayList
                else {

                    checkIfItsExists(n, coord); //Comprovar que el nou element ja existeix o no
                    n.getNodes().add(new NodeR(coord, obj));
                    n.getNodes().get(n.getNodes().getSize() - 1).setParent(n);
                    n = sortNode(n);
                }   //else
            }   //else
            return n;
        }   //if
        else
            n = expandSquare(n, aux_point); //Amplio el quadrat mes ben posicionat per poder inserir-se correctament el nou punt

        return n;
    }

    public void cloneNodeR(NodeR a, NodeR b){

        a.setSquare(b.isSquare());
        a.setParent(b.getParent());
        a.setCoords(b.getCoords());
        a.setNodes(b.getNodes());
        a.setObject(b.getObject());
    }

    public NodeR sortNode(NodeR n){

        for(int i = 0; i < n.getNodes().getSize() - 1; i++){

            int index = i;
            for(int j = i + 1; j < n.getNodes().getSize(); j++)
                if(Double.compare(n.getNodes().get(j).getCoords()[0].getX(), n.getNodes().get(i).getCoords()[0].getX()) < 0)
                    index = j;

            NodeR aux  = null;
            try {
                aux = n.getNodes().get(index).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            cloneNodeR(n.getNodes().get(index), n.getNodes().get(i));
            cloneNodeR(n.getNodes().get(i), aux);

        }   //for

        return n;
    }

    public void checkIfItsExists(NodeR n, double[] coord) throws NodeAlreadyExists{

        //Check if location exists inside of NodeR
        for(int i = 0; i < n.getNodes().getSize(); i++){
            if(n.getNodes().get(i).getCoords()[0].getX() == coord[0] &&
                    n.getNodes().get(i).getCoords()[0].getY() == coord[1])
                throw new NodeAlreadyExists();
        }   //for
    }

    public NodeR bestCandidate(Point2D coord, NodeR n){
        boolean found = false;
        NodeR best = new NodeR();

        for(int i = 0; i < n.getNodes().getSize() && !found; i++)

            if(checkIfItsInside(coord, n.getNodes().get(i))){
                found = true;
                best = n.getNodes().get(i);
            }   //if

        //Cas que no esta dins de cap quadrat
        if(!found){
            best = expandSquare(n, coord);
        }   //if

        return best;
    }

    public NodeR expandSquare(NodeR n, Point2D coord){

        double best_distance = Double.MAX_VALUE;    //Valor maxim d'un double
        NodeR best = new NodeR();
        int pos_distancia = 0;  //Posicio del punt de l quadrat a establir la distancia

        for(int i = 0; i < n.getNodes().getSize(); i++)
            for(int j = 0; j < n.getNodes().get(i).getCoords().length; j++){

                double distance_candidate = Math.sqrt(Math.pow((n.getNodes().get(i).getCoords()[j].getX() -
                        coord.getX()), 2) + Math.pow((n.getNodes().get(i).getCoords()[j].getY() - coord.getY()), 2));   //Distancia del candidat
                if(best_distance > distance_candidate){

                    best = n.getNodes().get(i);
                    best_distance = distance_candidate;
                    pos_distancia = j;
                }   //if
            }   //for

        //Expandim el quadrat
        if(pos_distancia == 0)
            best.getCoords()[0].setLocation(coord);
        else if(pos_distancia == 1)
            best.getCoords()[1].setLocation(coord);

        return best;
    }

    public boolean T_insert(){
        return true;
    }

    public NodeR partition(NodeR n){

        double[] average = n.averageCoord();
        double maxX = n.getMaxX();
        double minX = n.getMinX();
        double maxY = n.getMaxY();
        double minY = n.getMinY();

        //Clone elements from square
        MyArrayList aux = n.getNodes().clone();

        //Remove elements from actual node
        n.setNodes(new MyArrayList<>(1));

        //Indicates that now this node will contain squares
        n.setSquare(true);
        //Add new squares
        double[] coords = new double[DIMENSION];

        coords[0] = minX;
        coords[1] = minY;
        coords[2] = average[0];
        coords[3] = maxY;
        n.getNodes().add(new NodeR(coords, null));

        coords[0] = average[0] + SMOOTH_ADDITION;
        coords[1] = minY + SMOOTH_ADDITION;
        coords[2] = maxX;
        coords[3] = maxY;

        n.getNodes().add(new NodeR(coords, null));

        n = newParent(n, aux);

        n = distribute(n, aux);
        n.setSquare(true);  //El node actual passa de contenir punts a contenir quadrats

        n = reStruct(n);

        if(n.getParent() != null)
            n = n.getParent();

        return n;
    }

    public NodeR newParent(NodeR n, MyArrayList<NodeR> aux){

        for(int i = 0; i < n.getNodes().getSize(); i++)
            n.getNodes().get(i).setParent(aux.get(0).getParent());

        return n;
    }

    public NodeR reStruct(NodeR n){

        boolean found = false;

        NodeR aux_n = new NodeR();

        cloneNodeR(aux_n, n);

        //Control del root
        if(n.getParent() != null){
            //Comprovacio que un node que conte quadrats no estigui ple
            if(n.getParent().getNodes().getSize() < size) {

                //Cerquem el quadrat que engloba la particio realitzada
                for (int i = 0; i < n.getParent().getNodes().getSize() && !found; i++) {

                    if (n.getParent().getNodes().get(i).isSquare()) {

                        NodeR aux_parent = n.getParent();
                        found = true;
                        n.getParent().getNodes().remove(n.getParent().getNodes().get(i));
                        n.getParent().getNodes().add(n.getNodes().get(0));
                        sortNode(n.getParent());
                        n.getParent().getNodes().add(n.getNodes().get(1));
                        sortNode(n.getParent());

                        for(int j = n.getParent().getNodes().getSize() - 2; j < n.getParent().getNodes().getSize(); j++)
                            n.getParent().getNodes().get(j).setParent(aux_parent);

                    }   //if
                }   //for
            }   //if
        }   //if

        //Cas que cal realitzar una particio de quadrats
        if(n.getParent() != null && n.isSquare() &&
                n.getParent().isSquare() && n.getParent().getNodes().getSize() == DIMENSION && !found){

            //Cerquem el node que conte un node que conte quadrats
            for(int i = 0; i < n.getParent().getNodes().getSize(); i++){

                if(n.getParent().getNodes().get(i).isSquare()){

                    NodeR aux_parent = n.getParent();
                    n.getParent().getNodes().remove(n.getParent().getNodes().get(i));
                    n.getParent().getNodes().add(n.getNodes().get(0));
                    n.getParent().getNodes().get(n.getParent().getNodes().getSize() - 1).setParent(aux_parent);
                    sortNode(n.getParent());
                    n.getParent().getNodes().add(n.getNodes().get(1));
                    n.getParent().getNodes().get(n.getParent().getNodes().getSize() - 1).setParent(aux_parent);

                    sortNode(n.getParent());

                    n = particioSquare(n.getParent());
                    break;
                }   //if
            }   //for
        }   //if

        return n;
    }

    public Point2D[] initPoint2D(NodeR n){
        Point2D[] coord_aux = new Point2D[n.getDIMENSION()];

        //Inicialitzo l'array de Point2D
        for(int i = 0; i < coord_aux.length; i++)
            coord_aux[i] = new Point2D.Double();

        return coord_aux;
    }

    public NodeR particioSquare(NodeR n){

        n.setSquare(true);
        NodeR left_n = new NodeR();
        left_n.setSquare(true);
        int count = 0;
        //Dividim el node de quadrats en dos
        for(int i = 0; count < (DIMENSION / 2); i++){
            left_n.getNodes().add(n.getNodes().get(i));
            n.getNodes().remove(i);
            count++;
            i--;
        }   //for

        NodeR new_root = new NodeR();
        new_root.setSquare(true);
        new_root.setCoords(n.getCoords());

        Point2D[] coord_aux = initPoint2D(left_n);

        coord_aux[0].setLocation(left_n.getMinX(), left_n.getMinY());
        coord_aux[1].setLocation(left_n.getMaxXSquare(), left_n.getMaxYSquare());

        left_n.setCoords(coord_aux);

        coord_aux = initPoint2D(n);

        coord_aux[0].setLocation(n.getMinX(), n.getMinY());
        coord_aux[1].setLocation(n.getMaxXSquare(), n.getMaxYSquare());

        n.setCoords(coord_aux);

        n.setParent(new_root);
        left_n.setParent(new_root);

        new_root.getNodes().add(left_n);
        new_root.getNodes().add(n);

        return n;
    }

    public NodeR distribute(NodeR n, MyArrayList<NodeR> candidates){

        for(int i = 0; i < candidates.getSize(); i++)
            for(int j = 0; j < n.getNodes().getSize(); j++)
                if(checkIfItsInside(candidates.get(i).getCoords()[0], n.getNodes().get(j))) {
                    n.getNodes().get(j).getNodes().add(candidates.get(i));
                    n.getNodes().get(j).getNodes().
                            get(n.getNodes().get(j).getNodes().getSize() - 1).setParent(n.getNodes().get(j));
                }   //if

        return n;
    }

    public boolean checkIfItsInside(Point2D point, NodeR n){
        boolean result = false;

        int compare1 = Double.compare(point.getX(), n.getCoords()[0].getX());
        int compare2 = Double.compare(n.getCoords()[1].getX(), point.getX());
        int compare3 = Double.compare(point.getY(), n.getCoords()[0].getY());
        int compare4 = Double.compare(n.getCoords()[1].getY(), point.getY());

        if((compare1 == 1 || compare1 == 0) && (compare2 == 1 || compare2 == 0) &&
                (compare3 == 1 || compare3 == 0) && (compare4 == 1 || compare4 == 0))
            result = true;

        return result;
    }

    public NodeR getRoot() {
        return root;
    }

    public void setRoot(NodeR root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public MyArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(MyArrayList<Post> posts) {
        this.posts = posts;
    }
}
