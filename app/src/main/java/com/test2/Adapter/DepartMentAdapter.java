package com.test2.Adapter;

import static com.test2.MainActivity.android_flag;
import static com.test2.MainActivity.ios_flag;
import static com.test2.MainActivity.web_flag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test2.R;

import java.util.ArrayList;
import java.util.List;

public class DepartMentAdapter extends RecyclerView.Adapter<DepartMentAdapter.viewHolder> {
    View view;
    List<String> departMentList;
    Activity activity;
    public static List<String> android = new ArrayList<>();
    public static List<String> ios = new ArrayList<>();
    public static List<String> web = new ArrayList<>();

    public static List<String> android_switch = new ArrayList<>();
    public static List<String> ios_switch = new ArrayList<>();
    public static List<String> web_switch = new ArrayList<>();

    public DepartMentAdapter(List<String> departMentList, Activity activity) {
        this.departMentList = departMentList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.department_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtname.setText(departMentList.get(position));

            if ("Android".equals(departMentList.get(position))) {
                Log.e("getandroidflag", ": " + android_flag);
                if (android_flag) {
                    android_flag = false;
                    holder.childRecyclerView.setVisibility(View.GONE);
                }
                else {
                    android_flag = true;
                    holder.childRecyclerView.setVisibility(View.VISIBLE);

                    AndroidAdapter employeelistAdapter1 = new AndroidAdapter(android, android_switch, activity);
                    holder.childRecyclerView.setAdapter(employeelistAdapter1);
                }
            }

        if ("Ios".equals(departMentList.get(position))) {

            if (ios_flag) {

                ios_flag = false;
                holder.childRecyclerView.setVisibility(View.GONE);
            } else {
                ios_flag = true;
                holder.childRecyclerView.setVisibility(View.VISIBLE);

                IocAdapter employeelistAdapter1 = new IocAdapter(ios, ios_switch, activity);
                holder.childRecyclerView.setAdapter(employeelistAdapter1);
            }
        }

        if ("Web".equals(departMentList.get(position))) {

            if (web_flag) {

                web_flag = false;
                holder.childRecyclerView.setVisibility(View.GONE);
            } else {
                web_flag = true;
                holder.childRecyclerView.setVisibility(View.VISIBLE);

                WebAdapter employeelistAdapter1 = new WebAdapter(web, web_switch, activity);
                holder.childRecyclerView.setAdapter(employeelistAdapter1);
            }
        }
        //}
       /* holder.callMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpositionclick = position;
                if ("Android".equals(departMentList.get(position))) {
                    if (android_flag) {
                        android_flag = false;
                        holder.childRecyclerView.setVisibility(View.GONE);
                    } else {
                        android_flag = true;
                        holder.childRecyclerView.setVisibility(View.VISIBLE);
                        AndroidAdapter employeelistAdapter1 = new AndroidAdapter(android, activity);
                        holder.childRecyclerView.setAdapter(employeelistAdapter1);
                    }
                }
                if ("Ios".equals(departMentList.get(position))) {

                    if (ios_flag) {

                        ios_flag = false;
                        holder.childRecyclerView.setVisibility(View.GONE);
                    } else {
                        ios_flag = true;
                        holder.childRecyclerView.setVisibility(View.VISIBLE);

                        IocAdapter employeelistAdapter1 = new IocAdapter(ios, activity);
                        holder.childRecyclerView.setAdapter(employeelistAdapter1);
                    }
                }

                if ("Web".equals(departMentList.get(position))) {

                    if (web_flag) {

                        web_flag = false;
                        holder.childRecyclerView.setVisibility(View.GONE);
                    } else {
                        web_flag = true;
                        holder.childRecyclerView.setVisibility(View.VISIBLE);

                        WebAdapter employeelistAdapter1 = new WebAdapter(web, activity);
                        holder.childRecyclerView.setAdapter(employeelistAdapter1);
                    }
                }
            }
        });*/

        holder.addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("Android".equals(departMentList.get(position))) {
                    android.add("enter email");
                    android_switch.add("yes");

                    AndroidAdapter androidAdapter = new AndroidAdapter(android, android_switch, activity);
                    holder.childRecyclerView.setAdapter(androidAdapter);
                }
                if ("Ios".equals(departMentList.get(position))) {
                    ios.add("enter email");
                    ios_switch.add("yes");

                    IocAdapter iocAdapter = new IocAdapter(ios,ios_switch, activity);
                    holder.childRecyclerView.setAdapter(iocAdapter);
                }

                if ("Web".equals(departMentList.get(position))) {

                    web.add("enter email");
                    web_switch.add("yes");

                    WebAdapter webAdapter = new WebAdapter(web,web_switch, activity);
                    holder.childRecyclerView.setAdapter(webAdapter);
                }

                // Create an alert builder
               /* AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add Employee");

                // set the custom layout
                final View customLayout = activity.getLayoutInflater().inflate(R.layout.addemployee_row, null);
                builder.setView(customLayout);

                Button save = customLayout.findViewById(R.id.save);
                EditText edtemail = customLayout.findViewById(R.id.edtemail);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = edtemail.getText().toString().trim();

                        if (TextUtils.isEmpty(email)) {
                            edtemail.setError("Required filed");
                            edtemail.requestFocus();
                            return;
                        }
                        if (email.matches(emailPattern) && email.length() > 0) {
                        } else {
                            Toast.makeText(activity, "Invalid email address", Toast.LENGTH_SHORT).show();
                            return;

                        }

                        if ("Android".equals(departMentList.get(position))) {

                            for (int i = 0; i < android.size(); i++) {
                                if (email.equals(android.get(i))) {
                                    android_exist_flag = false;
                                    Toast.makeText(activity, "Already exist", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    android_exist_flag = true;
                                }

                            }
                            if (android_exist_flag) {
                                android.add(email);
                                android_flag=true;
                                AndroidAdapter employeelistAdapter1 = new AndroidAdapter(android, activity);
                                holder.childRecyclerView.setAdapter(employeelistAdapter1);
                            }
                        }
                        if ("Ios".equals(departMentList.get(position))) {

                            for (int i = 0; i < ios.size(); i++) {
                                if (email.equals(ios.get(i))) {
                                    ios_exist_flag = false;
                                    Toast.makeText(activity, "Already exist", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    ios_exist_flag = true;
                                }

                            }

                            if (ios_exist_flag) {

                                ios.add(email);
                                ios_flag=true;
                                IocAdapter employeelistAdapter1 = new IocAdapter(ios, activity);
                                holder.childRecyclerView.setAdapter(employeelistAdapter1);
                            }
                        }

                        if ("Web".equals(departMentList.get(position))) {

                            for (int i = 0; i < web.size(); i++) {
                                if (email.equals(web.get(i))) {
                                    web_exist_flag = false;
                                    Toast.makeText(activity, "Already exist", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    web_exist_flag = true;
                                }

                            }

                            if (web_exist_flag) {
                                web.add(email);
                                web_flag=true;
                                WebAdapter employeelistAdapter1 = new WebAdapter(web, activity);
                                holder.childRecyclerView.setAdapter(employeelistAdapter1);
                            }
                        }

                    }
                });
                // create and show
                // the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                */
            }
        });
    }

    @Override
    public int getItemCount() {
        return departMentList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView txtname;
        ImageView addEmployee;
        RecyclerView childRecyclerView;
        LinearLayout callMain;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txtname = itemView.findViewById(R.id.txtname);
            addEmployee = itemView.findViewById(R.id.addEmployee);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
            callMain = itemView.findViewById(R.id.callMain);
        }
    }
}
