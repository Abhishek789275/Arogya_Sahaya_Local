package com.example.arogyasahaya.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.arogyasahaya.data.entities.Medication;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MedicationDao_Impl implements MedicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Medication> __insertionAdapterOfMedication;

  private final EntityDeletionOrUpdateAdapter<Medication> __updateAdapterOfMedication;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMedication;

  public MedicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedication = new EntityInsertionAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medications` (`id`,`userId`,`medicineName`,`dosage`,`timeOfDay`,`specificTime`,`timeInMillis`,`isTaken`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getMedicineName());
        statement.bindString(4, entity.getDosage());
        statement.bindString(5, entity.getTimeOfDay());
        statement.bindString(6, entity.getSpecificTime());
        statement.bindLong(7, entity.getTimeInMillis());
        final int _tmp = entity.isTaken() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__updateAdapterOfMedication = new EntityDeletionOrUpdateAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medications` SET `id` = ?,`userId` = ?,`medicineName` = ?,`dosage` = ?,`timeOfDay` = ?,`specificTime` = ?,`timeInMillis` = ?,`isTaken` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getMedicineName());
        statement.bindString(4, entity.getDosage());
        statement.bindString(5, entity.getTimeOfDay());
        statement.bindString(6, entity.getSpecificTime());
        statement.bindLong(7, entity.getTimeInMillis());
        final int _tmp = entity.isTaken() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteMedication = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM medications WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertMedication(final Medication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMedication.insert(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateMedication(final Medication medication,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMedication(final int id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMedication.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteMedication.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Medication>> getAllMedications(final int userId) {
    final String _sql = "SELECT * FROM medications WHERE userId = ? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medications"}, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMedicineName = CursorUtil.getColumnIndexOrThrow(_cursor, "medicineName");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfTimeOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOfDay");
          final int _cursorIndexOfSpecificTime = CursorUtil.getColumnIndexOrThrow(_cursor, "specificTime");
          final int _cursorIndexOfTimeInMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timeInMillis");
          final int _cursorIndexOfIsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "isTaken");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpMedicineName;
            _tmpMedicineName = _cursor.getString(_cursorIndexOfMedicineName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            final String _tmpSpecificTime;
            _tmpSpecificTime = _cursor.getString(_cursorIndexOfSpecificTime);
            final long _tmpTimeInMillis;
            _tmpTimeInMillis = _cursor.getLong(_cursorIndexOfTimeInMillis);
            final boolean _tmpIsTaken;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTaken);
            _tmpIsTaken = _tmp != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpMedicineName,_tmpDosage,_tmpTimeOfDay,_tmpSpecificTime,_tmpTimeInMillis,_tmpIsTaken);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Medication>> getMedicationsByTime(final String timeOfDay, final int userId) {
    final String _sql = "SELECT * FROM medications WHERE timeOfDay = ? AND userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, timeOfDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medications"}, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMedicineName = CursorUtil.getColumnIndexOrThrow(_cursor, "medicineName");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfTimeOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOfDay");
          final int _cursorIndexOfSpecificTime = CursorUtil.getColumnIndexOrThrow(_cursor, "specificTime");
          final int _cursorIndexOfTimeInMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timeInMillis");
          final int _cursorIndexOfIsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "isTaken");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpMedicineName;
            _tmpMedicineName = _cursor.getString(_cursorIndexOfMedicineName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            final String _tmpSpecificTime;
            _tmpSpecificTime = _cursor.getString(_cursorIndexOfSpecificTime);
            final long _tmpTimeInMillis;
            _tmpTimeInMillis = _cursor.getLong(_cursorIndexOfTimeInMillis);
            final boolean _tmpIsTaken;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTaken);
            _tmpIsTaken = _tmp != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpMedicineName,_tmpDosage,_tmpTimeOfDay,_tmpSpecificTime,_tmpTimeInMillis,_tmpIsTaken);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getMedicationById(final int id,
      final Continuation<? super Medication> $completion) {
    final String _sql = "SELECT * FROM medications WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Medication>() {
      @Override
      @Nullable
      public Medication call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfMedicineName = CursorUtil.getColumnIndexOrThrow(_cursor, "medicineName");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfTimeOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "timeOfDay");
          final int _cursorIndexOfSpecificTime = CursorUtil.getColumnIndexOrThrow(_cursor, "specificTime");
          final int _cursorIndexOfTimeInMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "timeInMillis");
          final int _cursorIndexOfIsTaken = CursorUtil.getColumnIndexOrThrow(_cursor, "isTaken");
          final Medication _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpMedicineName;
            _tmpMedicineName = _cursor.getString(_cursorIndexOfMedicineName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            final String _tmpSpecificTime;
            _tmpSpecificTime = _cursor.getString(_cursorIndexOfSpecificTime);
            final long _tmpTimeInMillis;
            _tmpTimeInMillis = _cursor.getLong(_cursorIndexOfTimeInMillis);
            final boolean _tmpIsTaken;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsTaken);
            _tmpIsTaken = _tmp != 0;
            _result = new Medication(_tmpId,_tmpUserId,_tmpMedicineName,_tmpDosage,_tmpTimeOfDay,_tmpSpecificTime,_tmpTimeInMillis,_tmpIsTaken);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
