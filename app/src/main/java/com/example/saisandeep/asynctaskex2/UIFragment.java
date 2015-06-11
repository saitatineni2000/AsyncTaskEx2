package com.example.saisandeep.asynctaskex2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by saisandeep on 3/11/2015.
 */
public class UIFragment extends Fragment {

    MyTask task;
    Activity activity;

    public UIFragment(){

    }

    public void usebeginTasks(String ... params)
    {

        task=new MyTask(activity);
        task.execute(params);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;

        if(task != null)
        {
            task.onAttach(activity);
        }
        Log.d("Sandeep","Fragment onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Sandeep","Fragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Sandeep","Fragment onCreateView");
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Sandeep","Fragment onActivityCreated");
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Sandeep","Fragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Sandeep","Fragment onResume");
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("Sandeep","Fragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Sandeep","Fragment onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Sandeep","Fragment onSavedInstance");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Sandeep","Fragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Sandeep","Fragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(task != null)
        {
            task.onDetach();
        }
        Log.d("Sandeep","Fragment onDetach");
    }


}
