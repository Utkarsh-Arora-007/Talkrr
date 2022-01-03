package com.example.talkrr.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.talkrr.Adapters.UsersAdapter;
import com.example.talkrr.Models.Users;
import com.example.talkrr.R;
import com.example.talkrr.SignInActivity;
import com.example.talkrr.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {
    FragmentChatsBinding binding;
    ArrayList<Users> list= new ArrayList<>();
    int x=0;
    public ChatsFragment() {



        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChatsBinding.inflate(inflater, container, false);

        UsersAdapter adapter=new UsersAdapter(list,getContext());
        binding.ChatRecyclerView.setAdapter(adapter);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.ChatRecyclerView.setLayoutManager(layoutManager);

        DatabaseReference rootref=FirebaseDatabase.getInstance().getReference();

        rootref.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               //list.clear();
                if(x==0){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Users users = dataSnapshot.getValue(Users.class);
                        users.setUserId(dataSnapshot.getKey());
                        if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())) {

                            list.add(users);

                        }
                        Log.e("USERS", users.toString());
                    }
                    adapter.notifyDataSetChanged();
                    x=1;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();

            }
        });




        return binding.getRoot();
    }
}