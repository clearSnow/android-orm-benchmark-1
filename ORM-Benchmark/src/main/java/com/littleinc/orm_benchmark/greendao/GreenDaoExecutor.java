package com.littleinc.orm_benchmark.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.littleinc.orm_benchmark.BenchmarkExecutable;
import com.littleinc.orm_benchmark.greendao.MessageDao.Properties;
import com.littleinc.orm_benchmark.util.Util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.littleinc.orm_benchmark.util.Util.getRandomString;

public class GreenDaoExecutor implements BenchmarkExecutable {

    private static final String TAG = "GreenDaoExecutor";

    private static final String DB_NAME = "greendao_db";

    private DaoMaster mDaoMaster;
    private DaoSession mSession;

    @Override
    public void init(Context context, boolean useInMemoryDb) {
        Log.d(TAG, "Creating DataBaseHelper");
        SQLiteOpenHelper helper = new DataBaseHelper(context, (useInMemoryDb ? null : DB_NAME), null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster.dropAllTables(db, true);
        mDaoMaster = new DaoMaster(db);
        mSession = mDaoMaster.newSession();
    }

    @Override
    public long createDbStructure() throws SQLException {
        long start = System.nanoTime();
        DaoMaster.createAllTables(mDaoMaster.getDatabase(), true);
        return System.nanoTime() - start;
    }

    @Override
    public long writeWholeData() throws SQLException {
        final List<User> users = new LinkedList<>();
        for (int i = 0; i < NUM_USER_INSERTS; i++) {
            User newUser = new User(getRandomString(10), getRandomString(10), null);
            users.add(newUser);
        }

        final List<Message> messages = new LinkedList<>();
        for (long i = 0; i < NUM_MESSAGE_INSERTS; i++) {
            Message newMessage = new Message(null);
            newMessage.setCommand_id(i);
            newMessage.setSorted_by((double) System.nanoTime());
            newMessage.setContent(Util.getRandomString(100));
            newMessage.setClient_id(System.currentTimeMillis());
            newMessage.setSender_id(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setChannel_id(Math.round(Math.random() * NUM_USER_INSERTS));
            newMessage.setCreated_at((int) (System.currentTimeMillis() / 1000L));

            messages.add(newMessage);
        }

        long start = System.nanoTime();
        mSession.runInTx(new Runnable() {

            @Override
            public void run() {
                UserDao userDao = mSession.getUserDao();
                for (User user : users) {
                    userDao.insertOrReplace(user);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_USER_INSERTS + " users");

                MessageDao messageDao = mSession.getMessageDao();
                for (Message message : messages) {
                    messageDao.insertOrReplace(message);
                }
                Log.d(GreenDaoExecutor.class.getSimpleName(), "Done, wrote "
                        + NUM_MESSAGE_INSERTS + " messages");
                mSession.clear();
            }
        });
        return System.nanoTime() - start;
    }

    @Override
    public long readWholeData() throws SQLException {
        long start = System.nanoTime();
        MessageDao messageDao = mSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(), "Read, "
                + messageDao.queryBuilder().list().size() + " rows");
        mSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long readIndexedField() throws SQLException {
        long start = System.nanoTime();
        MessageDao messageDao = mSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(),
                "Read, "
                        + messageDao
                        .queryBuilder()
                        .where(Properties.Command_id
                                .eq(LOOK_BY_INDEXED_FIELD)).list()
                        .size() + " rows");
        mSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long readSearch() throws SQLException {
        long start = System.nanoTime();
        MessageDao messageDao = mSession.getMessageDao();
        Log.d(GreenDaoExecutor.class.getSimpleName(),
                "Read, "
                        + messageDao
                        .queryBuilder()
                        .limit((int) SEARCH_LIMIT)
                        .where(Properties.Content.like("%"
                                + SEARCH_TERM + "%")).list().size()
                        + " rows");
        mSession.clear();
        return System.nanoTime() - start;
    }

    @Override
    public long dropDb() throws SQLException {
        long start = System.nanoTime();
        DaoMaster.dropAllTables(mDaoMaster.getDatabase(), true);
        return System.nanoTime() - start;
    }

    @Override
    public String getOrmName() {
        return "GreenDAO";
    }

}
