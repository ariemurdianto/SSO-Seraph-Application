package net.ariemurdianto.mule.sso.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.ariemurdianto.mule.sso.ConstantCollection;


public class DefaultConfigManager implements ConfigManager{
	Properties properties;
	public DefaultConfigManager(){
		try
		{
			File file = new File(".");
			System.out.println("the current directory: "+file.getAbsolutePath());
			properties = new Properties();
			properties.load(new FileInputStream(ConstantCollection.PROPERTIES_FILENAME));
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
	}
	public String getConfig(String configKey) {
		return properties.getProperty(configKey);
	}

	public void storeConfig(String key, String value) {
		properties.setProperty(key, value);
	}

}
