import JsonObjects.Objects.MyArrayList;
import JsonObjects.Objects.Post;
import JsonObjects.Objects.User;

import java.awt.geom.Point2D;

import static JsonObjects.Objects.TransferToMyArrayList.transferInfoToMyArraylist;

public class Main {
    public static void main(String[] args){

        final String PATH_TEST = "jsons/users.json";
        final String PATH_TEST2 = "jsons/posts.json";

        Post aux2 = new Post();
        Post[] posts = (Post[]) aux2.deserializeJSON(PATH_TEST2);
        transferInfoToMyArraylist(posts);

        double[] coords = {-1000000, -1000000, 1000000, 1000000};
        RTree rtree = new RTree(2, coords);

        double[] coords_aux = new double[2];
        for(int i = 0; i < posts.length; i++) {

            coords_aux[0] = (double) posts[i].getLocation().get(0);
            coords_aux[1] = (double) posts[i].getLocation().get(1);

            System.out.println(i);
            if(i == 119)
                System.out.println("HERE");
            rtree.insert_T(coords_aux, posts[i]);
            rtree.preOrder(rtree.getRoot());
        }   //for

        double[] interval = {-1, -1, -1, -1};

        System.out.println("\n\nSEARCH:");

        rtree.search(interval, rtree.getRoot());

        for(int i = 0; i < rtree.getPosts().getSize(); i++)
            System.out.println(rtree.getPosts().get(i).getId() + " -" + rtree.getPosts().get(i).getPublished_by());

        rtree.setPosts(new MyArrayList<>(1));

        System.out.println("END");
    }
}
