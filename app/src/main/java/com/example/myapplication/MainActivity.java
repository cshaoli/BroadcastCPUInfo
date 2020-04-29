package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {



    private Button button;
    BufferedReader reader = null;


    private static final String TAG = "DeviceUtils";


    private final String CPUpath = "/sdcard/CPU/CPUUsage.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("Click: ","on");

                BroadcastReceiver broadcastReceiver = new BroadcastCPU();
                IntentFilter filter = new IntentFilter("com.example.myapplication");
                registerReceiver(broadcastReceiver, filter);

            }
        });



        BufferedReader reader = null;
        String content = "start";

        try {
            Process process = Runtime.getRuntime().exec("cat /sys/class/kgsl/kgsl-3d0/gpu_busy_percentage");
//            Process process = Runtime.getRuntime().exec("top -n 1");
//            Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("GPU Content after",content);


//        Log.d("CPU Content:", String.valueOf(getProcessCpuRate()));

//        getProcessCpuRate();


//        getCpuUsage();

//        getCPUFile(CPUpath);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private int getProcessCpuRate() {

        StringBuilder tv = new StringBuilder();
        int rate = 0;

        try {
            String Result;
            Process p;


            Log.d("Cat","Cat");

            p = Runtime.getRuntime().exec("cat /proc/cpuinfo");

            Log.d("Dog","Dog");

//            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            while ((Result = br.readLine()) != null) {
//                if (Result.trim().length() < 1) {
//                    continue;
//                } else {
//                    String[] CPUusr = Result.split("%");
//
//
//                    tv.append("USER:" + CPUusr[0] + "\n");
//                    String[] CPUusage = CPUusr[0].split("User");
//                    String[] SYSusage = CPUusr[1].split("System");
//                    tv.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
//                    tv.append("SYS:" + SYSusage[1].trim() + " length:" + SYSusage[1].trim().length() + "\n");
//
//                    rate = Integer.parseInt(CPUusage[1].trim()) + Integer.parseInt(SYSusage[1].trim());
//                    break;
//                }
//            }



            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((Result = br.readLine()) != null) {
                Log.d("Mouse","Mouse");
                Log.d("Content Read line",Result);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(rate + "");
        return rate;
    }


    public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }





    public static int getCpuUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat",
                    "r");
            String load = reader.readLine();

            Log.d(TAG, "line1 -- " + load);

            String[] toks = load.split(" ");
            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
                    + Long.parseLong(toks[4]) + Long.parseLong(toks[6])
                    + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            Log.d(TAG, "line1 -- " + load);
            Log.d(TAG, "idle1 -- " + idle1);
            Log.d(TAG, "cpu1 -- " + cpu1);
            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" ");
            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
                    + Long.parseLong(toks[4]) + Long.parseLong(toks[6])
                    + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            Log.d(TAG, "line2 -- " + load);
            Log.d(TAG, "idle2 -- " + idle2);
            Log.d(TAG, "cpu2 -- " + cpu2);
            Log.d(TAG,
                    "usage -- "
                            + Long.toString((cpu2 - cpu1) * 100
                            / ((cpu2 + idle2) - (cpu1 + idle1))));
            long usage = cpu2 - cpu1;
            long total = (cpu2 + idle2) - (cpu1 + idle1);
            return (int) (usage * 100 / total);
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }



    public static void getCPUFile(String filePath) {

        File file = new File(filePath);
        String str = null;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            while ((str = reader.readLine()) != null) {
                Log.d(TAG, str);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
