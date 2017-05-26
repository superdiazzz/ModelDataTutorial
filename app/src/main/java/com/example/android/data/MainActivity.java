package com.example.android.data;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.data.database.DBHelper;
import com.example.android.data.database.DataSource;
import com.example.android.data.model.DataItem;
import com.example.android.data.sample.DataItemAdapter;
import com.example.android.data.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREF = "my_global_pref";
    public SQLiteDatabase database;
    public DataSource dataSource;

    //        TextView tvOut;
        List<DataItem> dataItemList = SampleDataProvider.dataItemList;
        List<String> itemNames = new ArrayList<>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //DataItem item = new DataItem(null, "my item", "a desc", "category", 0, 9.0, "image.jpg");

            dataSource = new DataSource(this);
            dataSource.open();
            Toast.makeText(this, "database Acuired", Toast.LENGTH_SHORT).show();
            dataSource.seedDatabase(dataItemList);





//            tvOut = (TextView) findViewById(R.id.out);
//            tvOut.setText("");

//            Collections.sort(dataItemList, new Comparator<DataItem>() {
//                @Override
//                public int compare(DataItem dataItem, DataItem t1) {
//                    return dataItem.getItemName().compareTo(t1.getItemName());
//                }
//            });

//            for (DataItem dataItem: dataItemList) {
// //               tvOut.append(dataItem.getItemName()+"\n");
//                itemNames.add(dataItem.getItemName());
//            }

            // Ket: Untuk memasang ListView
 //           ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemNames);

            // Ket: ini adapter yang lama
//            DataItemAdapterListView adapter = new DataItemAdapterListView(this, dataItemList);
//            ListView listView = (ListView) findViewById(android.R.id.list);
//           listView.setAdapter(adapter);

            List<DataItem> dataFromDB = dataSource.getAllItems();

            DataItemAdapter adapter = new DataItemAdapter(this, dataFromDB);

            SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean grid = defaultPref.getBoolean(getString(R.string.pref_display_grid), false);

            RecyclerView listView = (RecyclerView) findViewById(R.id.rvItems);

            if(grid){
                listView.setLayoutManager(new GridLayoutManager(this, 3));
            }
            listView.setAdapter(adapter);

        }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){

            case R.id.action_signin:
                // lakukan pertukaran activity
                i = new Intent(this, SigninActivity.class);
                startActivityForResult(i, SIGNIN_REQUEST);
                return true;
            case R.id.action_setting:
                i = new Intent(this, PrefsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_storage_data:
                i= new Intent(this, StorageActivity.class);
                startActivity(i);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Toast.makeText(this, "req code"+requestCode+" resultCode "+resultCode, Toast.LENGTH_SHORT).show();

        if(resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST ){
            String email = data.getStringExtra(SigninActivity.EMAIL_KEY);
            Toast.makeText(this, "You log as "+email, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = getSharedPreferences(MY_GLOBAL_PREF, MODE_PRIVATE).edit();
            editor.putString(SigninActivity.EMAIL_KEY, email);
            editor.apply();

        }

    }
}
