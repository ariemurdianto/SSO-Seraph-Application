package net.ariemurdianto.mule.sso.manager;

import net.ariemurdianto.mule.sso.entity.SsoConfig;

public interface ConfigManager {
	public void storeConfig(String key, String value);
	public String getConfig(String configKey);

}
