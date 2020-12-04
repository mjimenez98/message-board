import java.util.List;

public interface UserManager {

    public List<String> getGroupMemberships(String user);
    public boolean isAuthenticated(String username, String password);

}
