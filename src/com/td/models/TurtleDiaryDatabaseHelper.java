package com.td.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class TurtleDiaryDatabaseHelper extends OrmLiteSqliteOpenHelper 
{

	private static final String DATABASE_NAME = "turtleDiary.db";
	private static final int DATABASE_VERSION = 1;
	private RuntimeExceptionDao<Pet, Integer> petDao = null;
	private RuntimeExceptionDao<Type, Integer> typeDao = null;
	private RuntimeExceptionDao<Environment, Integer> environmentDao = null;
	private RuntimeExceptionDao<Food, Integer> foodDao = null;
	private RuntimeExceptionDao<FeedLog, Integer> feedLogDao = null;
	private RuntimeExceptionDao<FeedLogContainFood, Integer> feedLogContainFoodDao = null;
	private RuntimeExceptionDao<HealthyLog, Integer> healthyLogDao = null;
	private RuntimeExceptionDao<MeasureLog, Integer> measureLogDao = null;
	
	public TurtleDiaryDatabaseHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) 
	{
		try
		{
			TableUtils.createTable(connectionSource, Pet.class);
			TableUtils.createTable(connectionSource, Type.class);
			TableUtils.createTable(connectionSource, Environment.class);
			TableUtils.createTable(connectionSource, Food.class);
			TableUtils.createTable(connectionSource, FeedLog.class);
			TableUtils.createTable(connectionSource, FeedLogContainFood.class);
			TableUtils.createTable(connectionSource, HealthyLog.class);
			TableUtils.createTable(connectionSource, MeasureLog.class);
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		} 
		catch (java.sql.SQLException e) 
		{
			e.printStackTrace();
		} 
	}
	
	public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) 
	{
		try
		{
			// TableUtils.dropTable(connectionSource, Environment.class, true);
			onCreate( db, connectionSource);
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		} 
		/*
		catch (java.sql.SQLException e) 
		{
			e.printStackTrace();
		}
		*/
	}
	
	public RuntimeExceptionDao<Environment, Integer> getEnvironmentDao() throws SQLException
	{
		if (null == environmentDao) 
		{
			environmentDao = getRuntimeExceptionDao(Environment.class);
		}
		return environmentDao;
	}
	
	public void addEnvironment(Environment environment) throws SQLException
	{
		RuntimeExceptionDao<Environment, Integer> dao = getEnvironmentDao();
		dao.create(environment);
	}
}