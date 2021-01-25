package com.example.uygulama1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.jovanovic.stefan.sqlitetutorial.R;

public class EkleActivity extends AppCompatActivity {

    EditText etBaslik, etYazar, etSayfaSayisi;
    Button btnEkle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);

        etBaslik = findViewById(R.id.etBaslik);
        etYazar = findViewById(R.id.etYazar);
        etSayfaSayisi = findViewById(R.id.etSayfaSayisi);
        btnEkle = findViewById(R.id.btnEkle);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VeriTabani myDB = new VeriTabani(EkleActivity.this);
                myDB.addBook(etBaslik.getText().toString().trim(),
                        etYazar.getText().toString().trim(),
                        Integer.valueOf(etSayfaSayisi.getText().toString().trim()));
            }
        });
    }
}
