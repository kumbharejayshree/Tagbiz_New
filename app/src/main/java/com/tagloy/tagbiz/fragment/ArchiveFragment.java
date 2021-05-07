package com.tagloy.tagbiz.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.tagloy.tagbiz.R;


public class ArchiveFragment extends Fragment {
  
    TextView txtHeading1, txtHeading2, txtHeading3, txtHeading4, txtHeading5, txtHeading6, txtHeading7, txtHeading8, txtHeading9, txtHeading10, txtFooter, txthoriZontal1, txthoriZontal2, txthoriZontal3, txthoriZontal4, txthoriZontal5, txthoriZontal6;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_archive, container, false);
       /* Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/aaleway_regular.ttf");
        mContext = getActivity();
        txtHeading1 = (TextView) view.findViewById(R.id.heading1);
        Typeface heading1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/DKBreakfastBurrito.otf");
        txtHeading1.setTypeface(heading1);
        txtHeading2 = (TextView) view.findViewById(R.id.heading2);
        txtHeading2.setTypeface(heading1);
        txtHeading3 = (TextView) view.findViewById(R.id.heading3);
        txtHeading3.setTypeface(custom_font);
        txtHeading4 = (TextView) view.findViewById(R.id.heading4);
        txtHeading4.setTypeface(custom_font);
        txtHeading5 = (TextView) view.findViewById(R.id.heading5);
        txtHeading5.setTypeface(custom_font);
        txtHeading6 = (TextView) view.findViewById(R.id.heading6);
        txtHeading6.setTypeface(custom_font);
        txtHeading7 = (TextView) view.findViewById(R.id.heading7);
        txtHeading7.setTypeface(custom_font);
        txtHeading8 = (TextView) view.findViewById(R.id.heading8);
        txtHeading8.setTypeface(custom_font);
        txtHeading9 = (TextView) view.findViewById(R.id.heading9);
        txtHeading9.setTypeface(custom_font);
        txtHeading10 = (TextView) view.findViewById(R.id.heading10);
        txtHeading10.setTypeface(custom_font);
        txtFooter = (TextView) view.findViewById(R.id.footer);
        txtFooter.setTypeface(custom_font);

*/
        return view;
    }


}
