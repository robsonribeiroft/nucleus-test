package robsonribeiroft.nucleus.singleton;


import modelo.User;

public class Singleton {

    private static Singleton uniqueInstance;

    public User currentUser;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new Singleton();

        return uniqueInstance;
    }
}
