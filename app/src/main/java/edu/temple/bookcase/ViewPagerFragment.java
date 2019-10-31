package edu.temple.bookcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerFragment extends Fragment {

    ViewPager vp;
    PagerAdapter pa;

    public ViewPagerFragment() {}
    public static ViewPagerFragment newInstance() {return new ViewPagerFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container,false);
        vp = v.findViewById(R.id.bookPager);
        pa = new vpAdapter(getChildFragmentManager());
        vp.setAdapter(pa);
        return v;
    }

    private class vpAdapter extends FragmentStatePagerAdapter {

        vpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return BookDetailsFragment.newInstance(getResources().getStringArray(R.array.book_array)[position]);
                default:
                    return null;
            }
        }

        public int getCount() {
            return getResources().getStringArray(R.array.book_array).length;
        }
    }
}
