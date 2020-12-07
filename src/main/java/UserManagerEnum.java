import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum UserManagerEnum {

    INSTANCE;

    UserManagerImpl userManagerImplementation;

    public void setUserManagerImplementation(String className, String usersFile){
        try {
            Class<?> userManagerImplementationClass = Class.forName(className);
            // Call parameterized constructor
            Class<?>[] type = { String.class};
            //Get parameterized constructor which takes 2 Strings as parameters
            Constructor<?> userManagerConstructor = userManagerImplementationClass.getConstructor(type);
            //String arguments
            Object[] files = {usersFile};
            //Instantiate object
            userManagerImplementation = (UserManagerImpl) userManagerConstructor.newInstance(files);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Could not set User Manager Implementation");
            e.printStackTrace();
        }
    }

    public UserManagerImpl getUserManagerImplementation(){
        return userManagerImplementation;
    }
}