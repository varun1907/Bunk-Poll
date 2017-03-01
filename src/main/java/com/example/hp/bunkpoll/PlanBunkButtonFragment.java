package com.example.hp.bunkpoll;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PlanBunkButtonFragment extends Fragment {

    public PlanBunkButtonFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


     //   Button planBunk=(Button)getView().findViewById(R.id.plan_bunk);

    //   planBunk.setOnClickListener(new View.OnClickListener() {
    //       @Override
    //       public void onClick(View view) {

   //        }
   //    });



        return inflater.inflate(R.layout.fragment_plan_bunk_button, container, false);
    }



}
