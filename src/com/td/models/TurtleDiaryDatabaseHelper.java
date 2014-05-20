package com.td.models;

import java.util.List;

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
			onCreate( db, connectionSource);
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	// Pet add
	public void addPet(Pet pet) throws SQLException
	{
		petDao.create(pet);
	}
	
	// Pet get
	public Pet getPet(int pid) throws SQLException
	{
		Pet pet = petDao.queryForId(pid);
		return pet;
	}
	
	// Pet update
	public void updatePet(Pet pet) throws SQLException
	{
		petDao.update(pet);
	}
	
	// Pet get all
	public List<Pet> getPets() throws SQLException
	{
		return petDao.queryForAll();
	}
	
	// Type get
	public Type getType(int tid) throws SQLException
	{
		Type type = typeDao.queryForId(tid);
		return type;
	}
	
	// Environment add
	public void addEnvironment(Environment environment) throws SQLException
	{
		environmentDao.create(environment);
	}
	
	// Environment get
	public Environment getEenvironment(int eid) throws SQLException
	{
		Environment environment = environmentDao.queryForId(eid);
		return environment;
	}
	
	// Environment update
	public void updateEnvironment(Environment environment) throws SQLException
	{
		environmentDao.update(environment);
	}
	
	// Environment get count
	public int getEenvironmentsCount() throws SQLException
	{
		int environmentCount = 0;
		environmentDao.queryForAll().size();
		return environmentCount;
	}
	
	// Environment get all
	public List<Environment> getEenvironments() throws SQLException
	{
		return environmentDao.queryForAll();
	}
	
	// Food get all
	public List<Food> getFoods() throws SQLException
	{
		return foodDao.queryForAll();
	}
	
	// FeedLog add
	public void addFeedLog(FeedLog feedLog) throws SQLException
	{
		feedLogDao.create(feedLog);
	}
	
	// FeedLogContainFood add
	public void addFeedLogContainFood(FeedLogContainFood feedLogContainFood) throws SQLException
	{
		feedLogContainFoodDao.create(feedLogContainFood);
	}
	
	// HealthyLog add
	public void addHealthyLog(HealthyLog healthyLog) throws SQLException
	{
		healthyLogDao.create(healthyLog);
	}
	
	// MeasureLog add
	public void addMeasureLog(MeasureLog measureLog) throws SQLException
	{
		measureLogDao.create(measureLog);
	}
}