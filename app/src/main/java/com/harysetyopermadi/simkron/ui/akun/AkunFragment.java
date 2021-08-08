package com.harysetyopermadi.simkron.ui.akun;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harysetyopermadi.simkron.Login;
import com.harysetyopermadi.simkron.MenuUtama;
import com.harysetyopermadi.simkron.R;
import com.harysetyopermadi.simkron.ui.notifications.NotificationsViewModel;

public class AkunFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseDatabase fd;
    FirebaseUser fu;
    Button btnkeluar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        fAuth=FirebaseAuth.getInstance();
        fd=FirebaseDatabase.getInstance();
        fu=fAuth.getCurrentUser();

        final Button vkeluar=root.findViewById(R.id.keluar);
        final TextView vnama= root.findViewById(R.id.Anama);
        final TextView vemail= root.findViewById(R.id.Aemail);
        final ImageView edNama= root.findViewById(R.id.edtnama);

        edNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        DatabaseReference dr=fd.getReference("Users").child(fAuth.getCurrentUser().getUid()).child("namauser");


        vemail.setText(fu.getEmail());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String val=snapshot.getValue(String.class);

                vnama.setText(val);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

vkeluar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i =new Intent(getActivity(),Login.class);
        startActivity(i);
       // startActivity(new Intent(getApplicationContext(), Login.class));
        //Toast.makeText(MenuUtama.this, "Berhasil Logout",Toast.LENGTH_LONG).show();
Toast.makeText(getActivity(),"Berhasil Logut",Toast.LENGTH_SHORT).show();
finishActivity();
    }
});

        return root;
    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}
