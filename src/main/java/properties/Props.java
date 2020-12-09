package properties;

import java.util.ResourceBundle;

public class Props {
    public static String GetValue(String key) {
        ResourceBundle properties = ResourceBundle.getBundle("MessageBoard");
        return properties.getString(key);
    }
}
