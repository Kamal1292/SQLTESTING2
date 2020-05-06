package com.example.sqltesting2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sqltesting2.Adapter.AdapterUser;
import com.example.sqltesting2.Pojo.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String LOG_TAG = "my logs";

    private Button btnClear, btnLoad;
    private OkHttpClient client;
    private Request request;
    private String jsonString;
    private AdapterUser adapterUser;
    private RecyclerView usersRecyclerView;
    private SQLiteDatabase database;

    private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(this);
        btnLoad = findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        initRecyclerView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clearSql();
                break;
            case R.id.btn_load:
                get();
                break;
        }
    }

    private void initRecyclerView() {
        usersRecyclerView = findViewById(R.id.recycler_view_users);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AdapterUser.OnUserClickListener onUserClickListener = new AdapterUser.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {

            }
        };
        adapterUser = new AdapterUser(onUserClickListener);
        usersRecyclerView.setAdapter(adapterUser);
    }

    private void get() {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constans.URL.GET_USERS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                jsonString = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playForTime();
                    }
                });
            }
        });
    }

    private void playForTime() {
        if (jsonString != null && !jsonString.isEmpty()) {
            Constans.LIST_RESPONSE = parseJson(jsonString);
            //searchUsers();
        }
    }

   /* private void searchUsers() {
        List<User> users = Constans.LIST_RESPONSE;
        if (users != null && !users.isEmpty()) {
            adapterUser.setItems(users);
        }
    }*/

    private List<User> parseJson(String response) {

        List<User> userList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            Log.d(LOG_TAG, "parseJson: " + array);
            for (int a = 0; a < array.length(); a++) {
                JSONObject joUser = array.getJSONObject(a);
                String userId = joUser.getString(Constans.KEY_USER_ID);
                Log.d(LOG_TAG, "parseJson:  ID " + userId);
                String userName = joUser.getString(Constans.KEY_USER_NAME);
                Log.d(LOG_TAG, "parseJson: NAME " + userName);
                String userNickName = joUser.getString(Constans.KEY_USER_NICK);
                String emailAddress = joUser.getString(Constans.KEY_EMAIL);
                JSONObject joAddress = joUser.getJSONObject(Constans.KEY_ADDRESS);
                String street = joAddress.getString(Constans.KEY_STREET);
                String suite = joAddress.getString(Constans.KEY_SUITE);
                String city = joAddress.getString(Constans.KEY_CITY);
                String zipcode = joAddress.getString(Constans.KEY_ZIPCODE);
                String phone = joUser.getString(Constans.KEY_PHONE);
                String website = joUser.getString(Constans.KEY_WEBSITE);
                JSONObject joCompany = joUser.getJSONObject(Constans.KEY_COMPANY);
                String nameCompany = joCompany.getString(Constans.KEY_COMPANY_NAME);
                String catchPhrase = joCompany.getString(Constans.KEY_PHRASE);
                String bs = joCompany.getString(Constans.KEY_BS);

                User user = new User(userId, userName, userNickName, emailAddress,
                        street, suite, city, zipcode, phone, website, nameCompany,
                        catchPhrase, bs);
                userList.add(user);
                saveToSQL(user);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;

    }

    private void saveToSQL(User user) {
      //  List<User> users = new ArrayList<>();

        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COLUMN_ID, user.getId());
        contentValues.put(DBHelper.COLUMN_NAME, user.getName());
        contentValues.put(DBHelper.COLUMN_NICKNAME, user.getNickname());
        contentValues.put(DBHelper.COLUMN_EMAIL, user.getEmail());
        contentValues.put(DBHelper.COLUMN_STREET, user.getStreet());
        contentValues.put(DBHelper.COLUMN_SUITE, user.getSuite());
        contentValues.put(DBHelper.COLUMN_CITY, user.getCity());
        contentValues.put(DBHelper.COLUMN_ZIPCODE, user.getZipcode());
        contentValues.put(DBHelper.COLUMN_PHONE, user.getPhone());
        contentValues.put(DBHelper.COLUMN_WEBSITE, user.getWebsite());
        contentValues.put(DBHelper.COLUMN_COMPANY_NAME, user.getCompanyName());
        contentValues.put(DBHelper.COLUMN_CATCHPHRASE, user.getCatchPhrase());
        contentValues.put(DBHelper.COLUMN_BS, user.getBs());
       // long rowID = database.insert(DBHelper.TABLE_NAME, null, contentValues);
        //Log.d(LOG_TAG, "row inserted, ID = " + rowID);

        dbHelper.close();
        readSQL();

    }

    private void readSQL(){
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        String[] columns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_NICKNAME,
                DBHelper.COLUMN_EMAIL, DBHelper.COLUMN_STREET, DBHelper.COLUMN_SUITE, DBHelper.COLUMN_CITY,
                DBHelper.COLUMN_ZIPCODE, DBHelper.COLUMN_PHONE, DBHelper.COLUMN_WEBSITE,
                DBHelper.COLUMN_COMPANY_NAME, DBHelper.COLUMN_CATCHPHRASE, DBHelper.COLUMN_BS};

        //database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);

        List<User> users = new ArrayList<>();

        if (cursor.moveToFirst()) {


            int idColIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
            int nameColIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            int nicknameColIndex = cursor.getColumnIndex(DBHelper.COLUMN_NICKNAME);
            int emailColIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMAIL);
            int streetColIndex = cursor.getColumnIndex(DBHelper.COLUMN_STREET);
            int suiteColIndex = cursor.getColumnIndex(DBHelper.COLUMN_SUITE);
            int cityColIndex = cursor.getColumnIndex(DBHelper.COLUMN_CITY);
            int zipCodeColIndex = cursor.getColumnIndex(DBHelper.COLUMN_ZIPCODE);
            int phoneColIndex = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);
            int websiteColIndex = cursor.getColumnIndex(DBHelper.COLUMN_WEBSITE);
            int companyNameColIndex = cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME);
            int catchPhraseColIndex = cursor.getColumnIndex(DBHelper.COLUMN_CATCHPHRASE);
            int bsColIndex = cursor.getColumnIndex(DBHelper.COLUMN_BS);

            String id = cursor.getString(idColIndex);
            String name = cursor.getString(nameColIndex);
            String nickName = cursor.getString(nicknameColIndex);
            String email = cursor.getString(emailColIndex);
            String street = cursor.getString(streetColIndex);
            String suite = cursor.getString(suiteColIndex);
            String city = cursor.getString(cityColIndex);
            String zipCode = cursor.getString(zipCodeColIndex);
            String phone = cursor.getString(phoneColIndex);
            String website = cursor.getString(websiteColIndex);
            String companyName = cursor.getString(companyNameColIndex);
            String catchPhrase = cursor.getString(catchPhraseColIndex);
            String bs = cursor.getString(bsColIndex);

            User user = new User(id, name, nickName, email, street,
                    suite, city, zipCode, phone, website, companyName, catchPhrase, bs);
            users.add(user);

            do {

                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + cursor.getInt(idColIndex) +
                                ", name = " + cursor.getString(nameColIndex) +
                                ", nickname = " + cursor.getString(nicknameColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (cursor.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");

        cursor.close();
        adapterUser.setItems(users);
    }

    private void clearSql() {
        Log.d(LOG_TAG, "--- Clear mytable: ---");
        int clearCount = database.delete(DBHelper.TABLE_NAME, null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }
}

