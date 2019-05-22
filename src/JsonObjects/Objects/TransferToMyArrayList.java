package JsonObjects.Objects;


import java.util.ArrayList;

/**
 * Interficie que transfereix de Array a JsonObjects.Objects.MyArrayList
 * @author Alexander Roca
 * @version 0.1
 */
public interface TransferToMyArrayList {
    static void transferInfoToMyArraylist(User[] users){
        for(int i = 0; i < users.length; i++)
            users[i].setMy_to_follow(users[i].getTo_follow());
    }

    static void transferInfoToMyArraylist(Post[] posts){
        for(int i = 0; i < posts.length; i++)
            posts[i].setMy_ArrayLists(posts[i].getHashtags(), posts[i].getLiked_by());
    }

    static ArrayList transferInfoToArrayList(MyArrayList myArrayList){
        ArrayList arrayList = new ArrayList();

        for(int i = 0; i < myArrayList.getSize(); i++)
            arrayList.add(myArrayList.get(i));

        return arrayList;
    }
}
