package com.rawdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.rawdata.domain.FileUpload;


public class FileDaoImpl implements FileDao 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public int insertFile(FileUpload file) 
	{
		String sql="INSERT INTO zipfiles(name,filedata) VALUES(?,?)";
		int count=jdbcTemplate.update(sql,file.getFileName(),file.getData());
		return count;
	}
	public List<FileUpload> list() 
	{
		String sql ="SELECT * FROM zipfiles";
		RowMapper<FileUpload> rowMapper=new RowMapper<FileUpload>()
		{

			public FileUpload mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Integer id=rs.getInt("id");
				String name=rs.getString("name");
				return new FileUpload(id,name);
			}
			
		};
		return jdbcTemplate.query(sql,rowMapper);
	}
	public FileUpload find(final Integer id) 
	{
		String sql="SELECT * FROM zipfiles WHERE id="+id;
		ResultSetExtractor<FileUpload> extractor=new ResultSetExtractor<FileUpload>() 
		{

			public FileUpload extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next())
				{
					String name=rs.getString("name");
					byte data[]=rs.getBytes("filedata");
					return new FileUpload(id,data,name);
				}
				return null;
			}
			
		};
		return jdbcTemplate.query(sql,extractor);
	}

}
