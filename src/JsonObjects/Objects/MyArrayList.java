package JsonObjects.Objects;

/**
 * Classe del ArrayList personalitzat
 * @param <E> : Objecte que emmagatzemara l'ArrayList
 * @author Alexander Roca
 * @version 0.1
 */

public class MyArrayList<E> {

    public int CAPACITY_DEFAULT;
    private Object[] objects;
    private int size = 0;

    /**
     * Constructor que necessita la dimensio inicial de l'ArrayList
     * @param dimension
     */
    public MyArrayList(int dimension){
        objects = new Object[dimension];
        CAPACITY_DEFAULT = dimension;
    }

    /**
     * Procediment que afageix l'objecte a l'estructura
     * @param e : <E>
     */
    public void add(E e){
        if(size == CAPACITY_DEFAULT)
            ensureCapacity();
        objects[size++] = e;
    }

    /**
     * Procediment que amplia la capacitat de l'estructura un cop arribat al seu limit
     */
    private void ensureCapacity(){
        int newCAPACITY_DEFAULT = objects.length * 2;
        Object[] newObjects = new Object[newCAPACITY_DEFAULT];

        for(int i = 0; i < objects.length; i++)
            newObjects[i] = objects[i];

        setCapacityDefault(newCAPACITY_DEFAULT);
        setObjects(newObjects);
    }

    /**
     * Funcio que cerca l'objecte per l'estructura
     * @param index : int
     * @return <E>
     */
    public E get(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        return (E) objects[index];
    }

    /**
     * Procediment que comprova que l'index no sigui superior o igual a la capacitat de l'estructura
     * @param index : int
     */
    public void rangeCheck(int index){
        if(index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * Funcio que sobreescriu l'element per un altre
     * @param index : int
     * @param element : <E> (El valor nou)
     * @return <E> (El valor antic)
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = (E) objects[index];
        objects[index] = element;
        return oldValue;
    }

    /**
     * Procediment que elimina tots els elements l'estructura
     */
    public void removeAll(){

        for(int i = 0; i < objects.length; i++)
            remove(objects[i]);

        size = 0;
    }

    public MyArrayList clone(){
        MyArrayList aux = new MyArrayList(1);

        for(int i = 0; i < this.getSize(); i++)
            aux.add(this.get(i));

        return aux;
    }

    /**
     * Procediment que elimina l'element desitjat de l'estructura
     * @param element : Object
     */
    public void remove(Object element){
        boolean found = false;
        int pos = 0;

        if(size < 0)
            throw new IndexOutOfBoundsException("Size: " + size);

        for(int i = 0; i < objects.length && !found; i++){
            if(objects[i].equals(element)) {
                found = true;
                pos = i;
            }   //if
        }   //for

        if(found && size > 0) {
            for (int i = pos; i < size - 1; i++)
                objects[i] = objects[i + 1];

            size--;
        }   //if
        else
            System.out.println("This element doesn't exist");
    }

    /**
     * Procediment que mostra tot el contingut de l'estructura
     */
    public void display(){
        for (int i = 0; i < size; i++)
            System.out.println(objects[i]);
    }

    public int getCapacityDefault() {
        return CAPACITY_DEFAULT;
    }

    public void setCapacityDefault(int capacityDefault) {
        CAPACITY_DEFAULT = capacityDefault;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
