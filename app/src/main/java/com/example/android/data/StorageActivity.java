package com.example.android.data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageActivity extends AppCompatActivity {

    public static final String FILE_NAME = "cogito_ergo_sum.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        Button btnWrite = (Button) findViewById(R.id.btnWriteFile);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = getString(R.string.cogito_ergo);
               FileOutputStream fileOutputStream = null;

                try {
                    File file = new File(FILE_NAME);
                    fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fileOutputStream.write(str.getBytes());
                    Toast.makeText(v.getContext(), "writted: "+FILE_NAME, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "e: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button btnDelete = (Button) findViewById(R.id.btnDeleteFile);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(getFilesDir(), FILE_NAME);
                if(file.exists()){
                    deleteFile(FILE_NAME);
                    Toast.makeText(v.getContext(), "file deteled", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(v.getContext(), "File not exist!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // bawah adalah bentuk modifikasi pemanggilan button click tanpa findbyId
    public void onReadButtonClick(View view){
        Toast.makeText(view.getContext(), "Read!", Toast.LENGTH_SHORT).show();
    }
}
