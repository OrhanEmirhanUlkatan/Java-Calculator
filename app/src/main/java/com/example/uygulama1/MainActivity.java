package com.example.uygulama1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jovanovic.stefan.sqlitetutorial.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvGoster;
    FloatingActionButton btnEkle;
    ImageView ivVeriYok;
    TextView tvVeriYok;

    VeriTabani veriTabani;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    Donustur donustur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGoster  = findViewById(R.id.rvGoster);
        btnEkle  = findViewById(R.id.btnEkle);
        ivVeriYok = findViewById(R.id.ivVeriYok);
        tvVeriYok = findViewById(R.id.tvVeriYok);
        btnEkle .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EkleActivity.class);
                startActivity(intent);
            }
        });

        veriTabani = new VeriTabani(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();

        donustur = new Donustur(MainActivity.this,this, book_id, book_title, book_author,
                book_pages);
        rvGoster .setAdapter(donustur);
        rvGoster .setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = veriTabani.readAllData();
        if(cursor.getCount() == 0){
            ivVeriYok.setVisibility(View.VISIBLE);
            veriTabani.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
            ivVeriYok.setVisibility(View.GONE);
            veriTabani.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tamamını Sil?");
        builder.setMessage("Tüm Veriyi Silmek İstediğinize Emin Misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VeriTabani myDB = new VeriTabani(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
