package edu.temple.bookcase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class BookDetailsFragment extends Fragment {

    String title;
    TextView tv;

    public BookDetailsFragment() {}

    public static BookDetailsFragment newFragment(String title) {
        BookDetailsFragment bdf = new BookDetailsFragment();
        Bundle bundle = new Bundle();

        bundle.putString("book", title);
        bdf.setArguments(bundle);

        return bdf;
    }

    public void getBook(String name) {
        tv.setText(name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString("book");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tv = (TextView) inflater.inflate(R.layout.fragment_book_details, container, false);

        if(title != null) {getBook(title);}

        return tv;
    }
}
