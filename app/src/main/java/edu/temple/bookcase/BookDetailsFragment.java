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

    public static BookDetailsFragment newInstance(String title) {
        BookDetailsFragment bdf = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("book", title);
        bdf.setArguments(bundle);

        return bdf;
    }

    public void displayBook(String title) {
        tv.setText(title);
        tv.setTextSize(25);
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
        tv = (TextView) inflater.inflate(R.layout.fragment_details, container, false);

        if(title != null) {displayBook(title);}

        return tv;
    }
}
