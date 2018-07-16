package com.example.bgock.myapplication;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import 	android.widget.Toast;

import com.example.bgock.myapplication.adapter.SimpleAdapter;
import com.example.bgock.myapplication.model.SimpleViewModel;

public class MainActivity extends AppCompatActivity {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleAdapter adapter = new SimpleAdapter(generateSimpleList(),this.getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.simple_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public void onClick(View v) {

        switch (v.getId()) {

            /*case R.id.button:
                startVoiceRecognitionActivity();

                break;*/


        }

    }

    private List<SimpleViewModel> generateSimpleList() {
        List<SimpleViewModel> simpleViewModelList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            simpleViewModelList.add(new SimpleViewModel(String.format(Locale.US, "This is itemJoe %d", i)));
        }

        return simpleViewModelList;
    }


    void startVoiceRecognitionActivity() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String wordStr = null;
        String[] words = null;
        String firstWord = null;
        String secondWord = null;
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
                && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            wordStr = matches.get(0);
            words = wordStr.split(" ");
            firstWord = words[0];
            secondWord = words[1];
        }
        if (firstWord.equals("open")) {
            PackageManager packageManager = getPackageManager();
            List<PackageInfo> packs = packageManager
                    .getInstalledPackages(0);
            int size = packs.size();
            boolean uninstallApp = false;
            boolean exceptFlg = false;
            for (int v = 0; v < size; v++) {
                PackageInfo p = packs.get(v);
                String tmpAppName = p.applicationInfo.loadLabel(
                        packageManager).toString();
                String pname = p.packageName;
                //urlAddress = urlAddress.toLowerCase();
                tmpAppName = tmpAppName.toLowerCase();
                if (tmpAppName.trim().toLowerCase().
                        equals(secondWord.trim().toLowerCase())) {
                    PackageManager pm = this.getPackageManager();
                    Intent appStartIntent = pm.getLaunchIntentForPackage(pname);
                    if (null != appStartIntent) {
                        try {
                            this.startActivity(appStartIntent);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } // end of open app code
    } // end of activityOnResult method
}
