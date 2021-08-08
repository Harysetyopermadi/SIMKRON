package com.harysetyopermadi.simkron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Regis extends AppCompatActivity {
    TextView pindahlogin;
    TextInputEditText email,namapengguna,password;
    Button daftar;
    ProgressBar pb;


    private FirebaseAuth mAuth;
    private FirebaseDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);
    pindahlogin=findViewById(R.id.txtlogin);


        email=findViewById(R.id.emailregistrasi);
        namapengguna=findViewById(R.id.namaregistrasi);
        password=findViewById(R.id.passwordregistrasi);
        daftar=findViewById(R.id.btnregistrasi);
        pb=findViewById(R.id.pb1);
        pb.setVisibility(View.GONE);


        mAuth = FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();



        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vemail= email.getText().toString().trim();
                String vpassword=password.getText().toString().trim();
                final String vnamapengguna=namapengguna.getText().toString().trim();


                if (TextUtils.isEmpty(vemail)){
                    email.setError("Email Wajib di isi");
                    return;
                }
                if (TextUtils.isEmpty(vpassword)){
                    password.setError("Password wajib di isi");
                    return;
                }

                if (TextUtils.isEmpty(vnamapengguna)){
                    namapengguna.setError("Nama Pengguna wajib di isi");
                    return;
                }
                pb.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(vemail,vpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            fd.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("namauser").setValue(vnamapengguna).addOnCompleteListener(new OnCompleteListener<Void>() {



                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Regis.this, "User Berhasil Ditambah",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                            Toast.makeText(Regis.this, "Registrasi Berhasil",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }else {
                            Toast.makeText(Regis.this,"Gagal Regis" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



    pindahlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(Regis.this,Login.class);
            startActivity(i);
            finish();
        }
    });
    }
}