package com.feeljcode.wordlearn;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    //AppCompatActivity
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageInfo pkg;
        int versionCode = 0;
        String versionName = "";
        try {
            pkg = this.getPackageManager().getPackageInfo(this.getApplication().getPackageName(), 0);
            versionCode = pkg.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        getSupportActionBar().hide();// 隐藏ActionBar

        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_more:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new BaseFragment(this,"单词"));
        adapter.addFragment(new BaseFragment(this,"图书"));
        adapter.addFragment(new BaseFragment(this,"发现"));
        adapter.addFragment(new BaseFragment(this,"更多"));
        viewPager.setAdapter(adapter);
    }

}
