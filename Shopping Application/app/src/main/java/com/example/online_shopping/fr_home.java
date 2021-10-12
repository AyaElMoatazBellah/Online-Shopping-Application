package com.example.online_shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fr_home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fr_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fr_home extends Fragment {
    private fragmentAlistener alistener;

    public interface fragmentAlistener{
        void onInputASent(CharSequence input);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fr_home, container, false);
        final EditText searchbar = (EditText)v.findViewById(R.id.SearchBar);
        final ImageButton ShoppingCart = (ImageButton)v.findViewById(R.id.Btn_ShoppingCart);
        final Spinner sp_Gender = (Spinner) v.findViewById(R.id.SP_Gender);
        ShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shoppingActivity = new Intent(getActivity(),ShoppingCartActivity.class);
                startActivity(shoppingActivity);
            }
        });
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchactivity = new Intent(getActivity(),SearchActivity.class);
                startActivity(searchactivity);

            }
        });
        return v;
    }


}
