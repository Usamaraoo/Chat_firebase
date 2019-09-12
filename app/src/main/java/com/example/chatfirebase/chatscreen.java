package com.example.chatfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class chatscreen extends AppCompatActivity {
    FirebaseAuth auth;
    TextView user;
    EditText enterText;
    String sender, message;
    ListView listView;
    ArrayList<String> mList = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDOcRef = db.collection("users").document("messages");

    @Override
    protected void onStart() {
        super.onStart();
        mDOcRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    sender = documentSnapshot.getString("Sender");
                    message = documentSnapshot.getString("Text");
                    mList.add(sender + "\n" + message);
                    mAdapter.notifyDataSetChanged();

                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatscreen);
        listView = findViewById(R.id.list);
        user = findViewById(R.id.user);
        enterText = findViewById(R.id.entertext);
        auth = FirebaseAuth.getInstance();
        user.setText(auth.getCurrentUser().getEmail());


        mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, mList);
        listView.setAdapter(mAdapter);
    }



    public void send(View view) {
        // Access a Cloud FireStore instance from your Activity
        Map<String, Object> user = new HashMap<>();
        user.put("Sender", auth.getCurrentUser().getEmail());
        user.put("Text", enterText.getText().toString());

        mDOcRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("success", "saved the doc");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failed msg", "Failed");
            }
        });
        enterText.setText("");
     }


}
