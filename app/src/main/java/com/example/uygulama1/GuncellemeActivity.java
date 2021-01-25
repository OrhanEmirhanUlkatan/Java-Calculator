package com.example.uygulama1;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jovanovic.stefan.sqlitetutorial.R;

public class GuncellemeActivity extends AppCompatActivity {

    EditText etBaslik, etYazar, etSayfaSayisi;
    Button btnKitapGuncelle, btnKitapSil ;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelleme);

        etBaslik = findViewById(R.id.etBaslik);
        etYazar = findViewById(R.id.etYazar);
        etSayfaSayisi = findViewById(R.id.etSayfaSayisi);
        btnKitapGuncelle = findViewById(R.id.btnKitapGuncelle);
        btnKitapSil  = findViewById(R.id.btnKitapSil );

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        btnKitapGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                VeriTabani myDB = new VeriTabani(GuncellemeActivity.this);
                title = etBaslik.getText().toString().trim();
                author = etYazar.getText().toString().trim();
                pages = etSayfaSayisi.getText().toString().trim();
                myDB.updateData(id, title, author, pages);
            }
        });
        btnKitapSil .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            //Setting Intent Data
            etBaslik.setText(title);
            etYazar.setText(author);
            etSayfaSayisi.setText(pages);
            Log.d("stev", title+" "+author+" "+pages);
        }else{
            Toast.makeText(this, "Veri Yok.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sil " + title + " ?");
        builder.setMessage("Silmek İstediğine Emin Misin " + title + " ?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VeriTabani myDB = new VeriTabani(GuncellemeActivity.this);
                myDB.deleteOneRow(id);
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
