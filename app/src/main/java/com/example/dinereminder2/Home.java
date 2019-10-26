package com.example.dinereminder2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {

    Button addMeeting;
    ArrayList<Meeting> meetsArr = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addMeeting = (Button) getView().findViewById(R.id.addMeeting);

        final SharedPreferences pref = this.getContext().getSharedPreferences("MyPref", 0);

        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getString("locations", null) == null) {
                    Toast.makeText(getContext(), "Please add atleast one place for meeting", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), AddMeeting.class );
                startActivity(intent);
            }
        });
    }

    public void loadData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Meeting>>() {}.getType();
        meetsArr = gson.fromJson(json, type);

        if (meetsArr == null) {
            meetsArr = new ArrayList<>();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On Resume");
        loadData();
        ListView listView = (ListView) getView().findViewById(R.id.meetings);
        MeetingAdapter meetingAdapter = new MeetingAdapter(this.getContext(), R.layout.adapter_view_layout, meetsArr);
        listView.setAdapter(meetingAdapter);
    }
}
