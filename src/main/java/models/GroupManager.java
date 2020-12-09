package models;

import java.util.List;

public class GroupManager {
    public static boolean containsGroup(List<Group> memberships, String membership) {
        for (Group group : memberships) {
            if (group.getGroupName().equals(membership))
                return true;
        }

        return false;
    }
}
