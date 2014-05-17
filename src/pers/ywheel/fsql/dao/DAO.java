package pers.ywheel.fsql.dao;

public interface DAO {
	public void query(String sql);
	
	public void createTable(String sql);
	
	public void update(String sql);
}
