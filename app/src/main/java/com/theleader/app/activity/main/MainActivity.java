package com.theleader.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import com.theleader.app.R;
import com.theleader.app.activity.main.fragments.DashboardFragment;
import com.theleader.app.activity.main.fragments.EmployeeFragment;
import com.theleader.app.activity.main.fragments.FeedbackFragment;

import R.helper.BaseActivity;
import R.helper.BaseFragment;

public class MainActivity extends BaseActivity {

    public interface SECTIONS {
        final static int DASHBOARD = 0;
        final static int FEEDBACK = 1;
        final static int EMPLOYEE = 2;
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        String[] sections;
        TypedArray icons;
        Context context;
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public SectionsPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            context = ctx;
            sections = context.getResources().getStringArray(R.array.array_section_name);
            icons = context.getResources().obtainTypedArray(R.array.array_section_icon);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = null;
            switch (position) {
                case SECTIONS.DASHBOARD:
                    f = BaseFragment.newInstance(DashboardFragment.class);
                    break;
                case SECTIONS.FEEDBACK:
                    f = BaseFragment.newInstance(FeedbackFragment.class);
                    break;
                case SECTIONS.EMPLOYEE:
                    f = BaseFragment.newInstance(EmployeeFragment.class);
                    break;
                default:
                    break;
            }
            registeredFragments.append(position, f);
            return f;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = icons.getDrawable(position);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            SpannableString sb = new SpannableString(" \n" + sections[position]);
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }

        public BaseFragment getFragment(int position) {
            return (BaseFragment)registeredFragments.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case EmployeeFragment.CONTACT_PICKER_RESULT:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String result = res.getString("result");
                    EmployeeFragment f = (EmployeeFragment) mSectionsPagerAdapter.getFragment(SECTIONS.EMPLOYEE);
                    f.onSelectedContactList(result);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}
