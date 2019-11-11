package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment bdf;
    Boolean twoFragment;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        twoFragment = (findViewById(R.id.detailFragment) != null);

        Book test = new Book(1, "Harry Potter", "Rowling", 2000, "url");
        Book test2 = new Book(2, "Harry Potter 2", "Rowling", 2001, "url");
        books.add(test);
        books.add(test2);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("BookList", books);

        ViewPagerFragment vpf = new ViewPagerFragment();
        vpf.setArguments(bundle);
        BookDetailsFragment bdf = new BookDetailsFragment();
        bdf.setArguments(bundle);


        if(!twoFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.listFragment, vpf)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.detailFragment, bdf)
                    .commit();
            BookListFragment blf = BookListFragment.newInstance(books);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.listFragment, blf)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(int position) {
        Book book = books.get(position);
        bdf.displayBook(book);
    }
}
