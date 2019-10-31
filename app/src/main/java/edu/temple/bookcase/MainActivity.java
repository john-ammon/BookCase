package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment bdf;
    Boolean twoFragment;
    ArrayList<String> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        books.addAll(Arrays.asList(res.getStringArray(R.array.book_array)));
        twoFragment = (findViewById(R.id.detailFragment) != null);

        if(!twoFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.listFragment, new ViewPagerFragment())
                    .commit();
        } else {
            BookListFragment blf = BookListFragment.newInstance(books);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.listFragment, blf)
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

        if(twoFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.detailFragment, bdf)
                    .commit();
        }
    }
}
