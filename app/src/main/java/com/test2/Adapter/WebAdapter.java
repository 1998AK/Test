package com.test2.Adapter;

import static com.test2.MainActivity.android_flag;
import static com.test2.MainActivity.ios_flag;
import static com.test2.MainActivity.spinner;
import static com.test2.MainActivity.web_flag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test2.MainActivity;
import com.test2.R;

import java.util.ArrayList;
import java.util.List;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.viewHolder> {
    View view;
    public static List<String> webList;
    public static List<String> web_switch;
    Activity activity;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    boolean validation_flag = false;
    boolean touch_flag = true;
    int clickw = 99999;
    int anohterpossion;
    public WebAdapter(List<String> webList, List<String> web_switch ,Activity activity) {
        this.webList = webList;
        this.web_switch = web_switch;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employeelist_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (!"enter email".equals(webList.get(position))){
            holder.emailid.setText(webList.get(position));
        }

        if ("yes".equals(web_switch.get(position))){
            holder.userStatus.setChecked(true);
        }
        else {
            holder.userStatus.setChecked(false);
        }

        holder.emailid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (touch_flag) {
                    touch_flag = false;
                    Log.d("TAG", "onTouch123: " + position);
                    clickw = position;
                }
                else {
                    anohterpossion = position;
                }
                return false;
            }
        });

        holder.emailid.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    // If view having focus.
                    if (clickw == position){
                        Log.d("TAG", "123onFocusChangeif: "+clickw+" po "+position);
                        validation_flag = true;
                    }
                }
                else {

                    if (validation_flag) {


                        String email = holder.emailid.getText().toString().trim();
                        MainActivity.save_error_flag = false;
                        if (TextUtils.isEmpty(email)) {
                            holder.email_error.setVisibility(View.VISIBLE);
                            holder.email_error.setText("Please enter email");
                            return;
                        }
                        if (email.matches(emailPattern) && email.length() > 0) {
                            holder.email_error.setVisibility(View.GONE);
                        } else {

                            holder.email_error.setVisibility(View.VISIBLE);
                            holder.email_error.setText("Invalid email address");
                            return;

                        }
                        MainActivity.save_error_flag = true;
                        webList.set(position,email);
                        DepartMentAdapter.web.set(position,email);
                        Log.d("TAG", "listtest: "+webList.get(position)+" pos "+position);
                        touch_flag = true;
                        validation_flag = true;
                        Toast.makeText(activity, "Email Validate Successfully", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });


        holder.removeemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(activity)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation


                                webList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, webList.size());

                                if (webList.size() == 0) {
                                    if (MainActivity.mainNameStore.size() != 0) {
                                        for (int i = 0; i < MainActivity.mainNameStore.size(); i++) {

                                            Log.d("TAG", "onClickforloop: " + MainActivity.mainNameStore.get(i));

                                            if ("Web".equals(MainActivity.mainNameStore.get(i))) {
                                                MainActivity.mainNameStore.remove(i);

                                                MainActivity.disweb = 0;

                                                if (android_flag) {
                                                    android_flag = false;
                                                }
                                                if (ios_flag) {
                                                    ios_flag = false;
                                                }
                                                spinner.setSelection(0);
                                                DepartMentAdapter departMentAdapter = new DepartMentAdapter(MainActivity.mainNameStore, activity);
                                                MainActivity.mainRecyclerView.setAdapter(departMentAdapter);
                                            }
                                        }
                                    }
                                }

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        holder.userStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.userStatus.isChecked()){
                    DepartMentAdapter.web_switch.set(position,"yes");
                }
                else {
                    DepartMentAdapter.web_switch.set(position,"no");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return webList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView emailid, removeemp,email_error;
        Switch userStatus;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            emailid = itemView.findViewById(R.id.emailid);
            removeemp = itemView.findViewById(R.id.removeemp);
            email_error = itemView.findViewById(R.id.email_error);
            userStatus = itemView.findViewById(R.id.userStatus);
        }
    }
}
