package com.example.hp.bunkpoll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView aboutus=(TextView)findViewById(R.id.aboutUsTextView);
        String aboutusText=
                "    <font color=#cc0029>Bunk Planner</font> for planning them successfully.\n"     +
                "    We know it is hassle to scroll through those\n"     +
                "    painful message while planning for \"bunk\".\n"     +
                "    So we decided to make it simpler for us and you.\n" +
                "    By creating this awesome app that\n"                +
                "    lets you plan for your bunk..\n"                    +
                "    with utmost Accuracy,Efficiency and Simplicity.\n"  +
                "    So get all your friend on our network\n"            +
                "    and start planning for your next play-troll\n"      +
                "    for your next class.\n"                             +
                "    with just three steps\n"                            +
                "    Initiate,Vote,Finalise.\n"                          +
                "    you can plan a successfull bunk.\n"                 +
                "    \n"                                                 +
                "    Mail your expierence and question at\n"             +
                "    varun1907@gmail.com\n"                              +
                "    or\n"                                               +
                "    adityasaxena602@gmail.com";

        aboutus.setText(Html.fromHtml(aboutusText));

    }
}
