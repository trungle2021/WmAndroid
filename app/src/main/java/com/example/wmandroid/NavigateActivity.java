package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wmandroid.Fragment.HomeFragment;
import com.example.wmandroid.Fragment.OrderCalendarFragment;
import com.example.wmandroid.Fragment.ProfileFragment;
import com.example.wmandroid.Fragment.SearchFragment;
import com.example.wmandroid.databinding.ActivityNavigateBinding;

public class NavigateActivity extends AppCompatActivity {
    ActivityNavigateBinding navigateBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigateBinding = ActivityNavigateBinding.inflate(getLayoutInflater());

        setContentView(navigateBinding.getRoot());
        replaceFragment(new HomeFragment());

        navigateBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
             switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.profile:
                      replaceFragment(new ProfileFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;

            }
            return true;
        });

//set booking button



    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_navigate,fragment);
        fragmentTransaction.commit();

    }

}