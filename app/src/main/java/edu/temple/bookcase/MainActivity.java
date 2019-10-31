package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment bdf;
    ArrayList<String> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        books.addAll(Arrays.asList(res.getStringArray(R.array.book_array)));
        Fragment bookFragment = getSupportFragmentManager().findFragmentById(R.id.vpFragment);

        if(bookFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.vpFragment, new ViewPagerFragment())
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(int position) {
        String title = books.get(position);
        bdf = new BookDetailsFragment();

        Bundle detailsBook = new Bundle();
        detailsBook.putString("book", title);
        bdf.setArguments(detailsBook);
    }
}
