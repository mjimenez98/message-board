import models.Group;

import java.util.List;

public interface UserManager {

    public List<Group> getGroupMemberships(String user);
    public boolean isAuthenticated(String username, String password);

}
