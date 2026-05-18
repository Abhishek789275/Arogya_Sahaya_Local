package com.example.arogyasahaya.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.arogyasahaya.data.entities.User;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLoginStatus;

  private final SharedSQLiteStatement __preparedStmtOfLogoutAll;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`username`,`contact`,`password`,`age`,`emergencyContact`,`chronicConditions`,`sosNumber`,`preferredLanguage`,`isDarkMode`,`isLoggedIn`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getContact());
        statement.bindString(4, entity.getPassword());
        statement.bindLong(5, entity.getAge());
        statement.bindString(6, entity.getEmergencyContact());
        statement.bindString(7, entity.getChronicConditions());
        statement.bindString(8, entity.getSosNumber());
        statement.bindString(9, entity.getPreferredLanguage());
        final int _tmp = entity.isDarkMode() ? 1 : 0;
        statement.bindLong(10, _tmp);
        final int _tmp_1 = entity.isLoggedIn() ? 1 : 0;
        statement.bindLong(11, _tmp_1);
      }
    };
    this.__preparedStmtOfUpdateLoginStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isLoggedIn = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfLogoutAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isLoggedIn = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLoginStatus(final int userId, final boolean loggedIn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLoginStatus.acquire();
        int _argIndex = 1;
        final int _tmp = loggedIn ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, userId);
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
          __preparedStmtOfUpdateLoginStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object logoutAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfLogoutAll.acquire();
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
          __preparedStmtOfLogoutAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<User> getUser() {
    final String _sql = "SELECT * FROM users LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfContact = CursorUtil.getColumnIndexOrThrow(_cursor, "contact");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfEmergencyContact = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContact");
          final int _cursorIndexOfChronicConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "chronicConditions");
          final int _cursorIndexOfSosNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "sosNumber");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpContact;
            _tmpContact = _cursor.getString(_cursorIndexOfContact);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpEmergencyContact;
            _tmpEmergencyContact = _cursor.getString(_cursorIndexOfEmergencyContact);
            final String _tmpChronicConditions;
            _tmpChronicConditions = _cursor.getString(_cursorIndexOfChronicConditions);
            final String _tmpSosNumber;
            _tmpSosNumber = _cursor.getString(_cursorIndexOfSosNumber);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final boolean _tmpIsDarkMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDarkMode);
            _tmpIsDarkMode = _tmp != 0;
            final boolean _tmpIsLoggedIn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp_1 != 0;
            _result = new User(_tmpId,_tmpUsername,_tmpContact,_tmpPassword,_tmpAge,_tmpEmergencyContact,_tmpChronicConditions,_tmpSosNumber,_tmpPreferredLanguage,_tmpIsDarkMode,_tmpIsLoggedIn);
          } else {
            _result = null;
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
  public Object login(final String contact, final String password,
      final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE contact = ? AND password = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, contact);
    _argIndex = 2;
    _statement.bindString(_argIndex, password);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfContact = CursorUtil.getColumnIndexOrThrow(_cursor, "contact");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfEmergencyContact = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContact");
          final int _cursorIndexOfChronicConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "chronicConditions");
          final int _cursorIndexOfSosNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "sosNumber");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpContact;
            _tmpContact = _cursor.getString(_cursorIndexOfContact);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpEmergencyContact;
            _tmpEmergencyContact = _cursor.getString(_cursorIndexOfEmergencyContact);
            final String _tmpChronicConditions;
            _tmpChronicConditions = _cursor.getString(_cursorIndexOfChronicConditions);
            final String _tmpSosNumber;
            _tmpSosNumber = _cursor.getString(_cursorIndexOfSosNumber);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final boolean _tmpIsDarkMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDarkMode);
            _tmpIsDarkMode = _tmp != 0;
            final boolean _tmpIsLoggedIn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp_1 != 0;
            _result = new User(_tmpId,_tmpUsername,_tmpContact,_tmpPassword,_tmpAge,_tmpEmergencyContact,_tmpChronicConditions,_tmpSosNumber,_tmpPreferredLanguage,_tmpIsDarkMode,_tmpIsLoggedIn);
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

  @Override
  public Flow<User> getLoggedInUser() {
    final String _sql = "SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfContact = CursorUtil.getColumnIndexOrThrow(_cursor, "contact");
          final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfEmergencyContact = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContact");
          final int _cursorIndexOfChronicConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "chronicConditions");
          final int _cursorIndexOfSosNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "sosNumber");
          final int _cursorIndexOfPreferredLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "preferredLanguage");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final int _cursorIndexOfIsLoggedIn = CursorUtil.getColumnIndexOrThrow(_cursor, "isLoggedIn");
          final User _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpContact;
            _tmpContact = _cursor.getString(_cursorIndexOfContact);
            final String _tmpPassword;
            _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            final String _tmpEmergencyContact;
            _tmpEmergencyContact = _cursor.getString(_cursorIndexOfEmergencyContact);
            final String _tmpChronicConditions;
            _tmpChronicConditions = _cursor.getString(_cursorIndexOfChronicConditions);
            final String _tmpSosNumber;
            _tmpSosNumber = _cursor.getString(_cursorIndexOfSosNumber);
            final String _tmpPreferredLanguage;
            _tmpPreferredLanguage = _cursor.getString(_cursorIndexOfPreferredLanguage);
            final boolean _tmpIsDarkMode;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDarkMode);
            _tmpIsDarkMode = _tmp != 0;
            final boolean _tmpIsLoggedIn;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsLoggedIn);
            _tmpIsLoggedIn = _tmp_1 != 0;
            _result = new User(_tmpId,_tmpUsername,_tmpContact,_tmpPassword,_tmpAge,_tmpEmergencyContact,_tmpChronicConditions,_tmpSosNumber,_tmpPreferredLanguage,_tmpIsDarkMode,_tmpIsLoggedIn);
          } else {
            _result = null;
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
