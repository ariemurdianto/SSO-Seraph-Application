package net.ariemurdianto.mule.sso.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import net.ariemurdianto.mule.sso.ConstantCollection;
import net.ariemurdianto.mule.sso.entity.SsoUser;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DefaultUserManager implements UserManager{
	
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private DataSource dataSourceReference;
	private ConfigManager configManager;
	public ConfigManager getConfigManager() {
		return configManager;
	}

	public void setConfigManager(ConfigManager configManager) {
		this.configManager = configManager;
	}

	public DataSource getDataSourceReference() {
		return dataSourceReference;
	}

	public void setDataSourceReference(DataSource dataSourceReference) {
		this.dataSourceReference = dataSourceReference;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int getNumberOfUser(String username) {
		String query = "select count(*) from users where username = ?";
		Object[] parameter = new Object[]{username};
		return jdbcTemplate.queryForInt(query, parameter);
	}
	
	public void addLocalUser(String username, String password) {
		String query = "insert into users (username, password) values( ? , ? )";
		Object[] valueToInsert = new Object[]{username, password};
		jdbcTemplate.update(query, valueToInsert);	
	}

	public SsoUser getLocalUser(String username) {
		String query = "select username, password from users where username = ?";
		Object[] parameter = new Object[]{username};
		SsoUser user = (SsoUser)jdbcTemplate.queryForObject(query, parameter, new RowMapper(){

			public Object mapRow(ResultSet rs, int numRow) throws SQLException {
//				if(numRow == 0) return null;
				SsoUser ssoUser = new SsoUser();
				ssoUser.setUsername(rs.getString(ConstantCollection.COLUMN_USERNAME));
				ssoUser.setPassword(rs.getString(ConstantCollection.COLUMN_PASSWORD));
				return ssoUser;
			}			
		});
		return user;
	}
}
