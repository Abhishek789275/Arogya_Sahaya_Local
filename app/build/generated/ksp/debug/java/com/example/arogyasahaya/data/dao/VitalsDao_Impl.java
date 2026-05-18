package com.example.arogyasahaya.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.arogyasahaya.data.entities.Vitals;
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
public final class VitalsDao_Impl implements VitalsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Vitals> __insertionAdapterOfVitals;

  public VitalsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitals = new EntityInsertionAdapter<Vitals>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vitals` (`id`,`userId`,`bloodPressure`,`heartRate`,`bloodSugar`,`weight`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Vitals entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getBloodPressure());
        statement.bindLong(4, entity.getHeartRate());
        statement.bindDouble(5, entity.getBloodSugar());
        statement.bindDouble(6, entity.getWeight());
        statement.bindLong(7, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insertVitals(final Vitals vitals, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVitals.insert(vitals);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Vitals>> getLastSevenDaysVitals(final int userId) {
    final String _sql = "SELECT * FROM vitals WHERE userId = ? ORDER BY timestamp DESC LIMIT 7";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vitals"}, new Callable<List<Vitals>>() {
      @Override
      @NonNull
      public List<Vitals> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfBloodPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodPressure");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfBloodSugar = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodSugar");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<Vitals> _result = new ArrayList<Vitals>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Vitals _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpUserId;
            _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
            final String _tmpBloodPressure;
            _tmpBloodPressure = _cursor.getString(_cursorIndexOfBloodPressure);
            final int _tmpHeartRate;
            _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            final float _tmpBloodSugar;
            _tmpBloodSugar = _cursor.getFloat(_cursorIndexOfBloodSugar);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new Vitals(_tmpId,_tmpUserId,_tmpBloodPressure,_tmpHeartRate,_tmpBloodSugar,_tmpWeight,_tmpTimestamp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
