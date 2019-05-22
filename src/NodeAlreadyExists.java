public class NodeAlreadyExists extends Exception {
    public static final String MESSAGE = "A node already exists that contains the key value: ";

    public NodeAlreadyExists() {
        super(MESSAGE);
    }
}
