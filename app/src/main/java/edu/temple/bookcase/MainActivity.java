package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment bdf;
    Boolean twoFragment;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        twoFragment = (findViewById(R.id.detailFragment) != null);

        //thread for getting books from URL
        Thread t = new Thread(){
            @Override
            public void run(){

                URL url;

                try {

                    url = new URL("https://kamorris.com/lab/audlib/booksearch.php");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    String response = "", tmpResponse;

                    tmpResponse = reader.readLine();
                    while (tmpResponse != null) {
                        response = response + tmpResponse;
                        tmpResponse = reader.readLine();
                    }
                    Log.v("test", response);

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonobject = jsonArray.getJSONObject(i);
                        int id = jsonobject.getInt("book_id");
                        String title = jsonobject.getString("title");
                        String author = jsonobject.getString("author");
                        int published = jsonobject.getInt("published");
                        String coverURL = jsonobject.getString("cover_url");
                        Book newBook = new Book(id, title, author, published, coverURL);
                        books.add(newBook);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try { t.join(); }
        catch (Exception e) { System.out.println(e); }

        //Bundle with list of books
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("BookList", books);
        //Create fragments and bundle books
        ViewPagerFragment vpf = new ViewPagerFragment();
        vpf.setArguments(bundle);
        bdf = new BookDetailsFragment();
        bdf.setArguments(bundle);

        //check if one or two fragments are being displayed
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
        bdf.displayBook(books.get(position));
    }

}
