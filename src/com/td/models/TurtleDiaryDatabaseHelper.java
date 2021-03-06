package com.td.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;

public class TurtleDiaryDatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "turtleDiary.db";
	private static final int DATABASE_VERSION = 2;
	private RuntimeExceptionDao<Pet, Integer> petDao = null;
	private RuntimeExceptionDao<Type, Integer> typeDao = null;
	private RuntimeExceptionDao<Environment, Integer> environmentDao = null;
	private RuntimeExceptionDao<Food, Integer> foodDao = null;
	private RuntimeExceptionDao<FeedLog, Integer> feedLogDao = null;
	private RuntimeExceptionDao<FeedLogContainFood, Integer> feedLogContainFoodDao = null;
	private RuntimeExceptionDao<HealthyLog, Integer> healthyLogDao = null;
	private RuntimeExceptionDao<MeasureLog, Integer> measureLogDao = null;
	private Context context;

	public TurtleDiaryDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Pet.class);
			TableUtils.createTable(connectionSource, Type.class);
			TableUtils.createTable(connectionSource, Environment.class);
			TableUtils.createTable(connectionSource, Food.class);
			TableUtils.createTable(connectionSource, FeedLog.class);
			TableUtils.createTable(connectionSource, FeedLogContainFood.class);
			TableUtils.createTable(connectionSource, HealthyLog.class);
			TableUtils.createTable(connectionSource, MeasureLog.class);

			// 讀Food csv檔

			TurtleDiaryCsvReader reader = new TurtleDiaryCsvReader();
			List<Food> foods = reader.getFoodsFromCsv(context);
			for (Food food : foods) {
				getFoodDao().create(food);
			}

			// 讀Type csv檔

			List<Type> types = reader.getTypesFromCsv(context);
			for (Type type : types) {
				type.setRecommendFood1(127);
				type.setRecommendFood2(128);
				type.setRecommendFood3(129);
				getTypeDao().create(type);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			try {
				TableUtils.dropTable(connectionSource, Food.class, true);
				TableUtils.dropTable(connectionSource, Type.class, true);

			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Pet get dao
	public RuntimeExceptionDao<Pet, Integer> getPetDao() throws SQLException {
		if (null == petDao) {
			petDao = getRuntimeExceptionDao(Pet.class);
		}
		return petDao;
	}

	// Pet add
	public void addPet(Pet pet) throws SQLException {
		getPetDao().create(pet);
	}

	// Pet get
	public Pet getPet(int pid) throws SQLException {
		Pet pet = getPetDao().queryForId(pid);
		return pet;
	}

	// Pet update
	public void updatePet(Pet pet) throws SQLException {
		getPetDao().update(pet);
	}

	// Pet get all
	public List<Pet> getPets() throws SQLException {
		return getPetDao().queryForAll();
	}

	// Type get dao
	public RuntimeExceptionDao<Type, Integer> getTypeDao() throws SQLException {
		if (null == typeDao) {
			typeDao = getRuntimeExceptionDao(Type.class);
		}
		return typeDao;
	}

	// Type get
	public Type getType(int tid) throws SQLException {
		Type type = getTypeDao().queryForId(tid);
		return type;
	}

	// Type get all
	public List<Type> getTypes() {
		return getTypeDao().queryForAll();
	}

	// Environment get dao
	public RuntimeExceptionDao<Environment, Integer> getEnvironmentDao()
			throws SQLException {
		if (null == environmentDao) {
			environmentDao = getRuntimeExceptionDao(Environment.class);
		}
		return environmentDao;
	}

	// Environment add
	public void addEnvironment(Environment environment) throws SQLException {
		getEnvironmentDao().create(environment);
	}

	// Environment get
	public Environment getEnvironment(int eid) throws SQLException {
		Environment environment = getEnvironmentDao().queryForId(eid);
		return environment;
	}

	// Environment update
	public void updateEnvironment(Environment environment) throws SQLException {
		getEnvironmentDao().update(environment);
	}

	// Environment get count
	public long getEnvironmentsCount() throws SQLException {
		return getEnvironmentDao().countOf();
	}

	// Environment get all
	public List<Environment> getEnvironments() throws SQLException {
		return getEnvironmentDao().queryForAll();
	}

	// Food get dao
	public RuntimeExceptionDao<Food, Integer> getFoodDao() throws SQLException {
		if (null == foodDao) {
			foodDao = getRuntimeExceptionDao(Food.class);
		}
		return foodDao;
	}

	// Food get
	public Food getFood(int fid) throws SQLException {
		Food food = getFoodDao().queryForId(fid);
		return food;
	}

	// Food get all
	public List<Food> getFoods() throws SQLException {
		return getFoodDao().queryForAll();
	}

	// FeedLog get dao
	public RuntimeExceptionDao<FeedLog, Integer> getFeedLogDao()
			throws SQLException {
		if (null == feedLogDao) {
			feedLogDao = getRuntimeExceptionDao(FeedLog.class);
		}
		return feedLogDao;
	}

	// FeedLog add
	public void addFeedLog(FeedLog feedLog) throws SQLException {
		getFeedLogDao().create(feedLog);
	}

	// FeedLog get pet's feed log
	public List<FeedLog> getPetFeedLog(int pid) throws SQLException {
		return getFeedLogDao().queryForEq(FeedLog.PID_FIELD_NAME, pid);
	}

	// FeedLog get Last
	public FeedLog getLastFeedLog(int pid) throws SQLException {
		QueryBuilder<FeedLog, Integer> queryBuilder = getFeedLogDao()
				.queryBuilder();
		queryBuilder.limit(1L);
		queryBuilder.orderBy("flid", false);
		try {
			queryBuilder.where().eq("pid", pid);
			FeedLog lastFeedLog = getFeedLogDao().queryForFirst(
					queryBuilder.prepare());
			if (lastFeedLog == null) {
				return null;
			} else {
				return lastFeedLog;
			}
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// FeedLogContainFood get dao
	public RuntimeExceptionDao<FeedLogContainFood, Integer> getFeedLogContainFoodDao()
			throws SQLException {
		if (null == feedLogContainFoodDao) {
			feedLogContainFoodDao = getRuntimeExceptionDao(FeedLogContainFood.class);
		}
		return feedLogContainFoodDao;
	}

	// FeedLogContainFood add
	public void addFeedLogContainFood(FeedLogContainFood feedLogContainFood)
			throws SQLException {
		getFeedLogContainFoodDao().create(feedLogContainFood);
	}

	// FeedLogContainFood get foods weight from same feed log
	public double getFoodsWeight(int flid) throws SQLException {

		try {

			QueryBuilder<FeedLogContainFood, Integer> queryBuilder = getFeedLogContainFoodDao()
					.queryBuilder();
			queryBuilder.selectRaw("SUM(weight)");
			queryBuilder.groupBy("flid");
			queryBuilder.where().eq("flid", flid);
			GenericRawResults<String[]> results = getFeedLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				String[] result = results.getResults().get(0);
				return Double.parseDouble(result[0]);
			} else {
				return 0;
			}

		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	// HealthyLog get dao
	public RuntimeExceptionDao<HealthyLog, Integer> getHealthyLogDao()
			throws SQLException {
		if (null == healthyLogDao) {
			healthyLogDao = getRuntimeExceptionDao(HealthyLog.class);
		}
		return healthyLogDao;
	}

	// HealthyLog add
	public void addHealthyLog(HealthyLog healthyLog) throws SQLException {
		getHealthyLogDao().create(healthyLog);
	}

	// HealthyLog get all by pid
	public List<HealthyLog> getHealthyLogs(int pid) {
		return getHealthyLogDao().queryForEq(HealthyLog.PID_FIELD_NAME, pid);
	}

	// MeasureLog get dao
	public RuntimeExceptionDao<MeasureLog, Integer> getMeasureLogDao()
			throws SQLException {
		if (null == measureLogDao) {
			measureLogDao = getRuntimeExceptionDao(MeasureLog.class);
		}
		return measureLogDao;
	}

	// MeasureLog add
	public void addMeasureLog(MeasureLog measureLog) throws SQLException {
		getMeasureLogDao().create(measureLog);
	}

	// MeasureLog get all by pid
	public List<MeasureLog> getMeasureLogs(int pid) {
		return getMeasureLogDao().queryForEq(MeasureLog.PID_FIELD_NAME, pid);
	}

	// Report page helper get Protein GraphViewDataList
	public List<GraphViewData> getProteinGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getProteinPercentage());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	private List<FeedLogContainFood> getFeedLogContainFoods(int flid) {
		return getFeedLogContainFoodDao().queryForEq(
				FeedLogContainFood.FLID_FIELD_NAME, flid);
	}

	// Report page helper get Fat GraphViewDataLis
	public List<GraphViewData> getFatGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getFatPercentage());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	// Report page helper get Fabric GraphViewDataList
	public List<GraphViewData> getFabricGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getFabricPercentage());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	// Report page helper get Ca GraphViewDataList
	public List<GraphViewData> getCaGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getCaPercentage());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	// Report page helper get P GraphViewDataList
	public List<GraphViewData> getPGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getPPercentage());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	// Report page helper get CaPRatio GraphViewDataList
	public List<GraphViewData> getCaPRatioGraphViewDataList(int pid) {
		List<GraphViewData> graphViewDataList = new ArrayList<GraphViewData>();
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		int index = 1;
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition nutrition = getFeedLogNutrition(feedLog.getFlid());
			GraphViewData graphViewData = new GraphViewData(index++, nutrition.getCaPRatio());
			graphViewDataList.add(graphViewData);
		}
		return graphViewDataList;
	}

	// Report page helper get ShellLength GraphViewSeries
	public List<GraphViewData> getShellLengthGraphViewDataList(int pid) {
		QueryBuilder<MeasureLog, Integer> queryBuilder = getMeasureLogDao()
				.queryBuilder();
		queryBuilder.selectRaw("shellLength");
		try {
			queryBuilder.where().eq("pid", pid);
			GenericRawResults<String[]> results = getMeasureLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				List<GraphViewData> graphViewDataArray = new ArrayList<GraphViewData>();
				int index = 1;
				for (String[] result : results) {
					double shellLength = Double.parseDouble(result[0]);
					GraphViewData graphViewData = new GraphViewData(index++,
							shellLength);
					graphViewDataArray.add(graphViewData);
				}
				return graphViewDataArray;
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<GraphViewData>();
	}

	// Report page helper get Weight GraphViewSeries
	public GraphViewSeries getWeightGraphViewSeries(int pid) {
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		// 根據每筆FeedLog算出的集合做出營養報表頁面六個圖表需要的graphViewSeries並回傳
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				petFeedLogs.toArray(new GraphViewData[petFeedLogs.size()]));
		return graphViewSeries;
	}

	// Report page helper get Jackson GraphViewSeries
	public GraphViewSeries getJacksonGraphViewSeries(int pid) {
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		// 根據每筆FeedLog算出的集合做出營養報表頁面六個圖表需要的graphViewSeries並回傳
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				petFeedLogs.toArray(new GraphViewData[petFeedLogs.size()]));
		return graphViewSeries;
	}

	// Report page helper getPetNutrition
	public Nutrition getPetNutrition(int pid) {
		List<FeedLog> petFeedLogs = getPetFeedLog(pid);
		Nutrition nutrition = new Nutrition();
		for (FeedLog feedLog : petFeedLogs) {
			Nutrition feedLogNutrition = getFeedLogNutrition(feedLog.getFlid());
			nutrition.setDryWeight(nutrition.getDryWeight() + feedLogNutrition.getDryWeight());
			nutrition.setProtein(nutrition.getProtein() + feedLogNutrition.getProtein());
			nutrition.setFat(nutrition.getFat() + feedLogNutrition.getFat());
			nutrition.setFabric(nutrition.getFabric() + feedLogNutrition.getFabric());
			nutrition.setCa(nutrition.getCa() + feedLogNutrition.getCa());
			nutrition.setP(nutrition.getP() + feedLogNutrition.getP());
		}
		return nutrition;
	}
	
	// Report page helper getFeedLogNutrition
	public Nutrition getFeedLogNutrition(int flid) {
		List<FeedLogContainFood> feedLogContainFoods = getFeedLogContainFoods(flid);
		return getFeedLogContainFoodsNutrition(feedLogContainFoods);
	}

	// Report page helper getFeedLogContainFoodsNutrition
	public Nutrition getFeedLogContainFoodsNutrition(
			List<FeedLogContainFood> feedLogContainFoodList) {
		Nutrition nutrition = new Nutrition();
		for (FeedLogContainFood feedLogContainFood : feedLogContainFoodList)
		{
			Nutrition foodNutrition = getFeedLogContainFoodNutrition(feedLogContainFood);
			nutrition.setDryWeight(nutrition.getDryWeight() + foodNutrition.getDryWeight());
			nutrition.setProtein(nutrition.getProtein() + foodNutrition.getProtein());
			nutrition.setFat(nutrition.getFat() + foodNutrition.getFat());
			nutrition.setFabric(nutrition.getFabric() + foodNutrition.getFabric());
			nutrition.setCa(nutrition.getCa() + foodNutrition.getCa());
			nutrition.setP(nutrition.getP() + foodNutrition.getP());
		}
		return nutrition;
	}

	// Report page helper getFeedLogContainFoodNutrition
	public Nutrition getFeedLogContainFoodNutrition(
			FeedLogContainFood feedLogContainFood) {
		Nutrition nutrition = new Nutrition();
		Food food = getFood(feedLogContainFood.getFid());
		double foodWeight = feedLogContainFood.getWeight();
		double waterWeight = food.getWater();
		double dryWeight = foodWeight * (100 - waterWeight) / 100;	// 乾重 (g)
		double protein = foodWeight * food.getProtein() / 100; 		// 蛋白質 (g)
		double fat = foodWeight * food.getFat() / 100; 				// 脂肪 (g)
		double fabric = foodWeight * food.getFabric() / 100; 		// 纖維 (g)
		double ca = foodWeight * food.getCa() / 100000; 			// 鈣 (g)
		double p = foodWeight * food.getP() / 100000; 				// 磷 (g)
		nutrition.setDryWeight(dryWeight);
		nutrition.setProtein(protein);
		nutrition.setFat(fat);
		nutrition.setFabric(fabric);
		nutrition.setCa(ca);
		nutrition.setP(p);
		return nutrition;
	}

	public Food getFood(String string) {
		List<Food> foods = getFoodDao()
				.queryForEq(Food.NAME_FIELD_NAME, string);
		if (foods.size() > 0) {
			return foods.get(0);
		}
		return null;
	}

	public long getMeasureLogCount(int pid) {
		return getMeasureLogDao().countOf();
	}

//	public String getFirstMeasureLogDateString(int pid) {
//		List<MeasureLog> measureLogList = getMeasureLogDao().queryForAll();
//		if (measureLogList.size() > 0) {
//			Date date = measureLogList.get(0).getTimeStamp();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			int year = calendar.get(Calendar.YEAR);
//			int month = calendar.get(Calendar.MONTH) + 1;
//			int day = calendar.get(Calendar.DAY_OF_MONTH);
//			return year + "-" + month + "-" + day;
//		}
//		return "";
//	}
	
	// getFeedLogDateString
	public String getFeedLogDateString(int pid, boolean isFirst) {
		QueryBuilder<FeedLog, Integer> queryBuilder = getFeedLogDao()
				.queryBuilder();
		queryBuilder.selectRaw(FeedLog.TIME_STAMP_FILED_NAME);
		try {
			queryBuilder.limit(1L);
			queryBuilder.orderBy(FeedLog.TIME_STAMP_FILED_NAME, isFirst);
			queryBuilder.where().eq("pid", pid);
			GenericRawResults<String[]> results = getFeedLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				String[] result = results.getResults().get(0);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				Date date = sdf.parse(result[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				return year + "-" + month + "-" + day;
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// get getMeasureLogDateString
	public String getMeasureLogDateString(int pid, boolean isFirst) {
		QueryBuilder<MeasureLog, Integer> queryBuilder = getMeasureLogDao()
				.queryBuilder();
		queryBuilder.selectRaw(MeasureLog.TIME_STAMP_FILED_NAME);
		try {
			queryBuilder.limit(1L);
			queryBuilder.orderBy(MeasureLog.TIME_STAMP_FILED_NAME, isFirst);
			queryBuilder.where().eq("pid", pid);
			GenericRawResults<String[]> results = getFeedLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				String[] result = results.getResults().get(0);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				Date date = sdf.parse(result[0]);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				return year + "-" + month + "-" + day;
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public List<GraphViewData> getWeightGraphViewDataList(int pid) {
		QueryBuilder<MeasureLog, Integer> queryBuilder = getMeasureLogDao()
				.queryBuilder();
		queryBuilder.selectRaw("weight");
		try {
			queryBuilder.where().eq("pid", pid);
			GenericRawResults<String[]> results = getMeasureLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				List<GraphViewData> graphViewDataArray = new ArrayList<GraphViewData>();
				int index = 1;
				for (String[] result : results) {
					double weight = Double.parseDouble(result[0]);
					GraphViewData graphViewData = new GraphViewData(index++,
							weight);
					graphViewDataArray.add(graphViewData);
				}
				return graphViewDataArray;
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<GraphViewData>();
	}

	public List<GraphViewData> getJacksonGraphViewDataList(int pid) {
		QueryBuilder<MeasureLog, Integer> queryBuilder = getMeasureLogDao()
				.queryBuilder();
		queryBuilder.selectRaw("weight/(shellLength*shellLength*shellLength)");
		try {
			queryBuilder.where().eq("pid", pid);
			GenericRawResults<String[]> results = getMeasureLogDao().queryRaw(
					queryBuilder.prepareStatementString());
			if (results != null) {
				List<GraphViewData> graphViewDataArray = new ArrayList<GraphViewData>();
				int index = 1;
				for (String[] result : results) {
					double weight = Double.parseDouble(result[0]);
					GraphViewData graphViewData = new GraphViewData(index++,
							weight);
					graphViewDataArray.add(graphViewData);
				}
				return graphViewDataArray;
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<GraphViewData>();
	}
}