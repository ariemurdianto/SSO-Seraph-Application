package net.ariemurdianto.mule.sso.manager;

import net.ariemurdianto.mule.sso.entity.SsoUser;

public interface UserManager {
	public int getNumberOfUser(String username);
	public void addLocalUser(String username, String password);
	public SsoUser getLocalUser(String username);
}
