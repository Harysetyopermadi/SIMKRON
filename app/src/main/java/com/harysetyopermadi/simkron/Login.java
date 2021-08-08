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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextView pindahdaftar;
    ProgressBar pbl;
    FirebaseAuth fAuth;
    FirebaseDatabase fd;

    private TextInputEditText emaillog,passwordlog;
    Button masuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pindahdaftar=findViewById(R.id.txtdaftar);
        emaillog=findViewById(R.id.emaillogin);
        passwordlog=findViewById(R.id.passwordlogin);
        pbl=findViewById(R.id.pb2);
        masuk=findViewById(R.id.btnlogin);
        fAuth=FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();



        if (fAuth.getCurrentUser()!=null) {

            startActivity(new Intent(getApplicationContext(),MenuUtama.class));
           finish();


         /*  pbl.setVisibility(View.VISIBLE);
            DatabaseReference dr=fd.getReference("Users").child(fAuth.getCurrentUser().getUid()).child("namauser");

            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String val=snapshot.getValue(String.class);

                    Intent intent= new Intent(getApplicationContext(),MenuUtama.class);
                    intent.putExtra("namauser",val);
                    intent.putExtra("emailuser",fAuth.getCurrentUser().getEmail().toString());
                    pbl.setVisibility(View.VISIBLE);

                    startActivity(intent);
                    finish();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/


        }
        pbl.setVisibility(View.GONE);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temail= emaillog.getText().toString().trim();
                String tpassword=passwordlog.getText().toString().trim();


                if (TextUtils.isEmpty(temail)){
                    emaillog.setError("Email Wajib di isi");
                    return;
                }
                if (TextUtils.isEmpty(tpassword)){
                    passwordlog.setError("Password wajib di isi");
                    return;
                }
                pbl.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(temail,tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DatabaseReference dr=fd.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("namauser");
                            dr.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String val=snapshot.getValue(String.class);
                                    Toast.makeText(Login.this,"Berhasil Login" +val,Toast.LENGTH_SHORT).show();
                                    pbl.setVisibility(View.GONE);
                                    Intent intent= new Intent(Login.this,MenuUtama.class);
                                    intent.putExtra("namauser",val);
                                    intent.putExtra("emailuser",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }else {
                            Toast.makeText(Login.this,"Gagal Login" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pbl.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });




        pindahdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Regis.class);
                startActivity(i);
                finish();
            }
        });
    }
}