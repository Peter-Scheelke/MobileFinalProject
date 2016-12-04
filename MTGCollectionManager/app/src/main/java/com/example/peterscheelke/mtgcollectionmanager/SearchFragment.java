package com.example.peterscheelke.mtgcollectionmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Peter Scheelke on 12/3/2016.
 */

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setUpCheckboxBackgrounds();
    }

    private void setUpCheckboxBackgrounds()
    {
        // White
        ImageView imageView = (ImageView) getView().findViewById(R.id.whiteCheckboxBackground);
        imageView.setImageResource(R.drawable.whitemana);

        imageView = (ImageView) getView().findViewById(R.id.whiteIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.whitemana);

        // Blue
        imageView = (ImageView) getView().findViewById(R.id.blueCheckboxBackground);
        imageView.setImageResource(R.drawable.bluemana);

        imageView = (ImageView) getView().findViewById(R.id.blueIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.bluemana);

        // Black
        imageView = (ImageView) getView().findViewById(R.id.blackCheckboxBackground);
        imageView.setImageResource(R.drawable.blackmana);

        imageView = (ImageView) getView().findViewById(R.id.blackIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.blackmana);

        // Red
        imageView = (ImageView) getView().findViewById(R.id.redCheckboxBackground);
        imageView.setImageResource(R.drawable.redmana);

        imageView = (ImageView) getView().findViewById(R.id.redIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.redmana);

        // Green
        imageView = (ImageView) getView().findViewById(R.id.greenCheckboxBackground);
        imageView.setImageResource(R.drawable.greenmana);

        imageView = (ImageView) getView().findViewById(R.id.greenIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.greenmana);

        // Colorless
        imageView = (ImageView) getView().findViewById(R.id.colorlessCheckboxBackground);
        imageView.setImageResource(R.drawable.colorlessmana);

        imageView = (ImageView) getView().findViewById(R.id.colorlessIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.colorlessmana);
    }

}
