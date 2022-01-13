package com.example.teamalpha10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.teamalpha10.Fragment.HomeFragment;
import com.example.teamalpha10.Fragment.NotificationFragment;
import com.example.teamalpha10.Fragment.ProfileFragment;
import com.example.teamalpha10.Fragment.SeachFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView=findViewById(R.id.bottom_Navigation);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectdLintener);

        Bundle intent=getIntent().getExtras();
        if(intent !=null){
            String publisher=intent.getString("publisher");
            SharedPreferences.Editor editor=getSharedPreferences("PERFS",MODE_PRIVATE).edit();
            editor.putString("publisher",publisher);
            editor.apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit() ;
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit() ;
        }


    }
   private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectdLintener=
           new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   switch (menuItem.getItemId()){
                       case R.id.nav_home:
                           selectedFragment=new HomeFragment();
                           break;
                       case R.id.nav_search:
                           selectedFragment=new SeachFragment();
                           break;
                       case R.id.nav_add:
                           selectedFragment=null;
                           startActivity(new Intent(MainActivity.this,PostActivity.class));

                           break;
                       case R.id.nav_notification:
                           selectedFragment=new NotificationFragment();
                           break;
                       case R.id.nav_profile:
                           SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                           editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                           editor.apply();
                           selectedFragment=new ProfileFragment();
                           break;
                   }
                   if(selectedFragment !=null){
                       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                               selectedFragment).commit() ;
                   }
                   return true;
               }
           };


}
