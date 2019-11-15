package edu.temple.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment bdf;
    BookListFragment blf;
    ViewPagerFragment vpf;
    Boolean twoFragment;
    ArrayList<Book> books = new ArrayList<>();
    Bundle bundle;
    String search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpf = new ViewPagerFragment();
        bdf = new BookDetailsFragment();
        blf = new BookListFragment();

        if(books.isEmpty()) {
            getBooksFromAPI();
        }

        //Bundle with list of books
        bundle = new Bundle();
        bundle.putParcelableArrayList("BookList", books);
        //Create fragments and bundle books
        vpf.setArguments(bundle);
        bdf.setArguments(bundle);
        blf.setArguments(bundle);
        //Display the fragments
        displayFragments();


        final Button button = findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText text = findViewById(R.id.searchText);
                search = text.getText().toString();
                getBooksFromAPI();
                bundle.putParcelableArrayList("BookList", books);
                //Create fragments and bundle books
                vpf = new ViewPagerFragment();
                vpf.setArguments(bundle);
                bdf = new BookDetailsFragment();
                bdf.setArguments(bundle);
                displayFragments();
            }
        });
    }

    @Override
    public void onFragmentInteraction(int position) {
        bdf.displayBook(books.get(position));
        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(books.get(position).coverURL);
    }

    public void displayFragments() {
        //check if one or two fragments are being displayed
        twoFragment = (findViewById(R.id.detailFragment) != null);
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.listFragment, blf)
                    .commit();
        }

    }

    public void getBooksFromAPI() {
        //thread for getting books from URL
        final Thread t = new Thread(){
            @Override
            public void run(){
                books = new ArrayList<>();
                URL url;

                try {
                    if(search.equals("")) {
                        url = new URL("https://kamorris.com/lab/audlib/booksearch.php");
                    } else {
                        url = new URL("https://kamorris.com/lab/audlib/booksearch.php?search=" + search);
                    }
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    String response = "", tmpResponse;

                    tmpResponse = reader.readLine();
                    while (tmpResponse != null) {
                        response = response + tmpResponse;
                        tmpResponse = reader.readLine();
                    }

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
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String coverURL = urls[0];
            Bitmap cover = null;
            try {
                InputStream inputStream = new java.net.URL(coverURL).openStream();
                cover = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return cover;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
