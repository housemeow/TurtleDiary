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
			Type type = new Type();
			type.setName("印度星龜");
			getTypeDao().create(type);
			type = new Type();
			type.setName("緬甸星龜");
			getTypeDao().create(type);
			
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
	
	// Pet get dao
	public RuntimeExceptionDao<Pet, Integer> getPetDao() throws SQLException
	{
		if (null == petDao) 
		{
			petDao = getRuntimeExceptionDao(Pet.class);
		}
		return petDao;
	}
	
	// Pet add
	public void addPet(Pet pet) throws SQLException
	{
		getPetDao().create(pet);
	}
	
	// Pet get
	public Pet getPet(int pid) throws SQLException
	{
		Pet pet = getPetDao().queryForId(pid);
		return pet;
	}
	
	// Pet update
	public void updatePet(Pet pet) throws SQLException
	{
		getPetDao().update(pet);
	}
	
	// Pet get all
	public List<Pet> getPets() throws SQLException
	{
		return getPetDao().queryForAll();
	}
	
	// Type get dao
	public RuntimeExceptionDao<Type, Integer> getTypeDao() throws SQLException
	{
		if (null == typeDao) 
		{
			typeDao = getRuntimeExceptionDao(Type.class);
		}
		return typeDao;
	}
	
	// Type get
	public Type getType(int tid) throws SQLException
	{
		Type type = getTypeDao().queryForId(tid);
		return type;
	}
	
	// Environment get dao
	public RuntimeExceptionDao<Environment, Integer> getEnvironmentDao() throws SQLException
	{
		if (null == environmentDao) 
		{
			environmentDao = getRuntimeExceptionDao(Environment.class);
		}
		return environmentDao;
	}
	
	// Environment add
	public void addEnvironment(Environment environment) throws SQLException
	{
		getEnvironmentDao().create(environment);
	}
	
	// Environment get
	public Environment getEenvironment(int eid) throws SQLException
	{
		Environment environment = getEnvironmentDao().queryForId(eid);
		return environment;
	}
	
	// Environment update
	public void updateEnvironment(Environment environment) throws SQLException
	{
		getEnvironmentDao().update(environment);
	}
	
	// Environment get count
	public int getEenvironmentsCount() throws SQLException
	{
		int environmentCount = 0;
		getEnvironmentDao().queryForAll().size();
		return environmentCount;
	}
	
	// Environment get all
	public List<Environment> getEenvironments() throws SQLException
	{
		return getEnvironmentDao().queryForAll();
	}
	
	// Food get dao
	public RuntimeExceptionDao<Food, Integer> getFoodDao() throws SQLException
	{
		if (null == foodDao) 
		{
			foodDao = getRuntimeExceptionDao(Food.class);
		}
		return foodDao;
	}
	
	// Food get all
	public List<Food> getFoods() throws SQLException
	{
		return getFoodDao().queryForAll();
	}
	
	// FeedLog get dao
	public RuntimeExceptionDao<FeedLog, Integer> getFeedLogDao() throws SQLException
	{
		if (null == feedLogDao) 
		{
			feedLogDao = getRuntimeExceptionDao(FeedLog.class);
		}
		return feedLogDao;
	}
	
	// FeedLog add
	public void addFeedLog(FeedLog feedLog) throws SQLException
	{
		getFeedLogDao().create(feedLog);
	}
	
	// FeedLogContainFood get dao
	public RuntimeExceptionDao<FeedLogContainFood, Integer> getFeedLogContainFoodDao() throws SQLException
	{
		if (null == feedLogContainFoodDao) 
		{
			feedLogContainFoodDao = getRuntimeExceptionDao(FeedLogContainFood.class);
		}
		return feedLogContainFoodDao;
	}
	
	// FeedLogContainFood add
	public void addFeedLogContainFood(FeedLogContainFood feedLogContainFood) throws SQLException
	{
		feedLogContainFoodDao.create(feedLogContainFood);
	}
	
	// HealthyLog get dao
	public RuntimeExceptionDao<HealthyLog, Integer> getHealthyLogDao() throws SQLException
	{
		if (null == healthyLogDao) 
		{
			healthyLogDao = getRuntimeExceptionDao(HealthyLog.class);
		}
		return healthyLogDao;
	}
	
	// HealthyLog add
	public void addHealthyLog(HealthyLog healthyLog) throws SQLException
	{
		getHealthyLogDao().create(healthyLog);
	}
	
	// MeasureLog get dao
	public RuntimeExceptionDao<MeasureLog, Integer> getMeasureLogDao() throws SQLException
	{
		if (null == measureLogDao) 
		{
			measureLogDao = getRuntimeExceptionDao(MeasureLog.class);
		}
		return measureLogDao;
	}
	
	// MeasureLog add
	public void addMeasureLog(MeasureLog measureLog) throws SQLException
	{
		getMeasureLogDao().create(measureLog);
	}

	public List<Type> getTypes() {
		return getTypeDao().queryForAll();
	}
}