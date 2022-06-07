package com.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.test2.Adapter.AndroidAdapter;
import com.test2.Adapter.DepartMentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sprDepartment;
    String departMent[] = {"Select Department","Android","Ios","Web"};
    public static RecyclerView mainRecyclerView;
    public static List<String> mainNameStore = new ArrayList<>();
    String getDepName;
    public static int disandroid=0,disios=0,disweb=0;
    public static boolean android_flag = false;
    public static boolean ios_flag = false;
    public static boolean web_flag = false;
    public static Spinner spinner;

    public static List<String> spinnerlist;
    public static ArrayAdapter<String> arrayadapter;

    public static AppCompatButton btnSaveall;
    public static boolean save_error_flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        btnSaveall = findViewById(R.id.btnSaveall);

        spinner = (Spinner) findViewById(R.id.sprDepartment);

        SharedPreferences sharedPreferences = getSharedPreferences("MainActvityDetails", Context.MODE_PRIVATE);
        String arraylist = sharedPreferences.getString("mainarraystore","");
        String depandroid = sharedPreferences.getString("depandroid","");
        String depios = sharedPreferences.getString("depios","");
        String depweb = sharedPreferences.getString("depweb","");

        String disandroid_switch = sharedPreferences.getString("disandroid_switch","");
        String disios_switch = sharedPreferences.getString("disios_switch","");
        String disweb_switch = sharedPreferences.getString("disweb_switch","");

        if (!TextUtils.isEmpty(arraylist) && arraylist.length() > 0){

            disandroid = sharedPreferences.getInt("disandroid",0);
            disios = sharedPreferences.getInt("disios",0);
            disweb = sharedPreferences.getInt("disweb",0);

            spinner.setSelection(0);

            String[] animalsArray2 = arraylist.split(",");
            Collections.addAll(mainNameStore, animalsArray2);

            if (!TextUtils.isEmpty(depandroid) && depandroid.length() > 0) {
                String[] anroidarr = depandroid.split(",");
                Collections.addAll(DepartMentAdapter.android, anroidarr);
            }

            if (!TextUtils.isEmpty(disandroid_switch) && disandroid_switch.length() > 0) {
                String[] stringandroid = disandroid_switch.split(",");
                Collections.addAll(DepartMentAdapter.android_switch, stringandroid);
            }


            if (!TextUtils.isEmpty(disios_switch) && disios_switch.length() > 0) {
                String[] stringios = disios_switch.split(",");
                Collections.addAll(DepartMentAdapter.ios_switch, stringios);
            }

            if (!TextUtils.isEmpty(disweb_switch) && disweb_switch.length() > 0) {
                String[] stringweb = disweb_switch.split(",");
                Collections.addAll(DepartMentAdapter.web_switch, stringweb);
            }


            if (!TextUtils.isEmpty(depios) && depios.length() > 0) {
                String[] iosarry = depios.split(",");
                Collections.addAll(DepartMentAdapter.ios, iosarry);
            }


            if (!TextUtils.isEmpty(depweb) && depweb.length() > 0) {
                String[] webarry = depweb.split(",");
                Collections.addAll(DepartMentAdapter.web, webarry);
            }

            DepartMentAdapter departMentAdapter = new DepartMentAdapter(mainNameStore,MainActivity.this);
            mainRecyclerView.setAdapter(departMentAdapter);
        }


        spinnerlist = new ArrayList<>(Arrays.asList(departMent));

        arrayadapter = new ArrayAdapter<String>(MainActivity.this,R.layout.spinner_item_textview,spinnerlist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0 || position == disandroid || position == disios || position == disweb )
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View spinnerview = super.getDropDownView(position, convertView, parent);

                TextView spinnertextview = (TextView) spinnerview;

                if(position == 0 || position == disandroid || position == disios || position == disweb ){

                    //Set the disable spinner item color fade .
                    spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                }
                else {

                    spinnertextview.setTextColor(Color.WHITE);

                }
                return spinnerview;
            }
        };

        arrayadapter.setDropDownViewResource(R.layout.spinner_item_textview);

        spinner.setAdapter(arrayadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getDepName = departMent[i];
                Log.d("getname", ": "+getDepName);
                if (!"Select Department".equals(getDepName)){

                    if ("Android".equals(getDepName))
                        disandroid = i;
                    if ("Ios".equals(getDepName))
                        disios = i;
                    if ("Web".equals(getDepName))
                        disweb = i;

                    Collections.reverse(mainNameStore);

                    mainNameStore.add(getDepName);

                    Log.d("TAG", "onItemSelectedss: "+mainNameStore);

                    Collections.reverse(mainNameStore);

                    Log.d("TAG", "onItemSelectedssreverse: "+mainNameStore);
                    if (android_flag) {
                        android_flag = false;
                    }
                    if (ios_flag) {
                        ios_flag = false;
                    }if (web_flag) {
                        web_flag = false;
                    }

                    DepartMentAdapter departMentAdapter = new DepartMentAdapter(mainNameStore,MainActivity.this);
                    mainRecyclerView.setAdapter(departMentAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSaveall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainNameStore.size() != 0){

                    if (save_error_flag) {

                        String mainarr = TextUtils.join(",", mainNameStore);
                        String depandroid = TextUtils.join(",", DepartMentAdapter.android);
                        String depios = TextUtils.join(",", DepartMentAdapter.ios);
                        String depweb = TextUtils.join(",", DepartMentAdapter.web);

                        String depandroid_switch = TextUtils.join(",", DepartMentAdapter.android_switch);
                        String depios_switch = TextUtils.join(",", DepartMentAdapter.ios_switch);
                        String depweb_switch = TextUtils.join(",", DepartMentAdapter.web_switch);

                        SharedPreferences sharedPreferences = getSharedPreferences("MainActvityDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mainarraystore", mainarr);
                        editor.putInt("disandroid", disandroid);
                        editor.putInt("disios", disios);
                        editor.putInt("disweb", disweb);

                        editor.putString("depandroid", depandroid);
                        editor.putString("depios", depios);
                        editor.putString("depweb", depweb);

                        editor.putString("disandroid_switch", depandroid_switch);
                        editor.putString("disios_switch", depios_switch);
                        editor.putString("disweb_switch", depweb_switch);

                        editor.apply();


                        Toast.makeText(MainActivity.this, "Data save successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(MainActivity.this, "Please enter valid email and save", Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    SharedPreferences preferences =getSharedPreferences("MainActvityDetails",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    Toast.makeText(MainActivity.this, "All data clear", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}