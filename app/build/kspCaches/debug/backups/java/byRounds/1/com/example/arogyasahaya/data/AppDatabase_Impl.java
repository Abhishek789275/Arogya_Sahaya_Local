package com.example.arogyasahaya.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.example.arogyasahaya.data.dao.MedicationDao;
import com.example.arogyasahaya.data.dao.MedicationDao_Impl;
import com.example.arogyasahaya.data.dao.UserDao;
import com.example.arogyasahaya.data.dao.UserDao_Impl;
import com.example.arogyasahaya.data.dao.VitalsDao;
import com.example.arogyasahaya.data.dao.VitalsDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile MedicationDao _medicationDao;

  private volatile VitalsDao _vitalsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(8) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `contact` TEXT NOT NULL, `password` TEXT NOT NULL, `age` INTEGER NOT NULL, `emergencyContact` TEXT NOT NULL, `chronicConditions` TEXT NOT NULL, `sosNumber` TEXT NOT NULL, `preferredLanguage` TEXT NOT NULL, `isDarkMode` INTEGER NOT NULL, `isLoggedIn` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medications` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `medicineName` TEXT NOT NULL, `dosage` TEXT NOT NULL, `timeOfDay` TEXT NOT NULL, `specificTime` TEXT NOT NULL, `timeInMillis` INTEGER NOT NULL, `isTaken` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vitals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `bloodPressure` TEXT NOT NULL, `heartRate` INTEGER NOT NULL, `bloodSugar` REAL NOT NULL, `weight` REAL NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '24f44f1cb6aaf3891b0821ef8d6cb021')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `medications`");
        db.execSQL("DROP TABLE IF EXISTS `vitals`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(11);
        _columnsUsers.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("contact", new TableInfo.Column("contact", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("emergencyContact", new TableInfo.Column("emergencyContact", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("chronicConditions", new TableInfo.Column("chronicConditions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("sosNumber", new TableInfo.Column("sosNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("preferredLanguage", new TableInfo.Column("preferredLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isDarkMode", new TableInfo.Column("isDarkMode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isLoggedIn", new TableInfo.Column("isLoggedIn", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.arogyasahaya.data.entities.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsMedications = new HashMap<String, TableInfo.Column>(8);
        _columnsMedications.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("medicineName", new TableInfo.Column("medicineName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("dosage", new TableInfo.Column("dosage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("timeOfDay", new TableInfo.Column("timeOfDay", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("specificTime", new TableInfo.Column("specificTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("timeInMillis", new TableInfo.Column("timeInMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedications.put("isTaken", new TableInfo.Column("isTaken", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedications = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedications = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedications = new TableInfo("medications", _columnsMedications, _foreignKeysMedications, _indicesMedications);
        final TableInfo _existingMedications = TableInfo.read(db, "medications");
        if (!_infoMedications.equals(_existingMedications)) {
          return new RoomOpenHelper.ValidationResult(false, "medications(com.example.arogyasahaya.data.entities.Medication).\n"
                  + " Expected:\n" + _infoMedications + "\n"
                  + " Found:\n" + _existingMedications);
        }
        final HashMap<String, TableInfo.Column> _columnsVitals = new HashMap<String, TableInfo.Column>(7);
        _columnsVitals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("bloodPressure", new TableInfo.Column("bloodPressure", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("heartRate", new TableInfo.Column("heartRate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("bloodSugar", new TableInfo.Column("bloodSugar", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVitals.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVitals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVitals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVitals = new TableInfo("vitals", _columnsVitals, _foreignKeysVitals, _indicesVitals);
        final TableInfo _existingVitals = TableInfo.read(db, "vitals");
        if (!_infoVitals.equals(_existingVitals)) {
          return new RoomOpenHelper.ValidationResult(false, "vitals(com.example.arogyasahaya.data.entities.Vitals).\n"
                  + " Expected:\n" + _infoVitals + "\n"
                  + " Found:\n" + _existingVitals);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "24f44f1cb6aaf3891b0821ef8d6cb021", "4f1fed3a33bebe01b47b81c3b45af837");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","medications","vitals");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `medications`");
      _db.execSQL("DELETE FROM `vitals`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicationDao.class, MedicationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VitalsDao.class, VitalsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public MedicationDao medicationDao() {
    if (_medicationDao != null) {
      return _medicationDao;
    } else {
      synchronized(this) {
        if(_medicationDao == null) {
          _medicationDao = new MedicationDao_Impl(this);
        }
        return _medicationDao;
      }
    }
  }

  @Override
  public VitalsDao vitalsDao() {
    if (_vitalsDao != null) {
      return _vitalsDao;
    } else {
      synchronized(this) {
        if(_vitalsDao == null) {
          _vitalsDao = new VitalsDao_Impl(this);
        }
        return _vitalsDao;
      }
    }
  }
}
