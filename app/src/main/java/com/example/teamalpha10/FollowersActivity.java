package com.example.teamalpha10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.teamalpha10.Adpter.UserAdapter;
import com.example.teamalpha10.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivity extends AppCompatActivity {

    String id;
    String title;
    List<String> idList;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        title=intent.getStringExtra("title");

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView=findViewById(R.id.recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList=new ArrayList<>();
        userAdapter=new UserAdapter(this,userList,false);
        recyclerView.setAdapter(userAdapter);

        idList=new ArrayList<>();

        switch (title){
            case "going":
                getLikes();
                break;
            case "following":
                getFollowing();
                break;
            case "followers":
                getFollowers();
                break;
            case "not going":
                getNotgoing();
                break;
            case "intrasts":
                getIntrast();
                break;
        }
    }
    private void getLikes(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("response").child("going")
                .child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void getNotgoing(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("response").child("notgoing")
                .child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void getIntrast(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("response").child("intrast")
                .child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void getFollowers(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getFollowing(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Follow")
                .child(id).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    idList.add(snapshot.getKey());
                }
                showUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showUser(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    for(String id:idList){
                        if(user.getId().equals(id)){
                            userList.add(user);}
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



