package yudiwbs.cs.upi.edu.cobaasynctask;
/*
    untuk tutorial
    todo: menangani persistensi AsyncTask saat activity di destroy



 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        tvHasil = (TextView) findViewById(R.id.tvHasil);
    }

    public void klikMulai(View v) {
        pb.setMax(100);
        new KerjakanBackgroundTask().execute(); //tidak ada parameter
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


    private class KerjakanBackgroundTask extends AsyncTask<Void, Integer, String> //Params, Progress, Result
/*
 Tiga parameter (Void, Integer, String):
1. Parameter ke background task;
2. Pogress saat background task dijalankan
3. Result hasil dari background task
Untuk kasus di app ini (untuk app lain bisa berbeda):
1.Param ke bacground task: Void (tidak ada)
2.Progress saat background task dijalankan: Integer (setiap loop diincrement dan dikirim ke progressbar
3.Result: String (output)
*/
        
    {


        //sebelum proses
        protected void onPreExecute() {
            pb.setProgress(0);
            tvHasil.setText("mulai");
        }

        //proses
        protected String doInBackground(Void... v) {
            String hasil="-";
            try {
                for (int i = 0; i<100;i++) {
                    Thread.sleep(50);  //delay 0.05 detik
                    //update user interface dengan progress
                    //JANGAN ada update user interface langsung, kecuali di onProgressUpdate
                    publishProgress(i);
                    if (isCancelled()) {
                        hasil = "batal";
                        break; //bisa dibuat user cancel ditengah
                    }
                }
                hasil = "Sukses";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return hasil;  //result
        }

        //progress
        protected void onProgressUpdate(Integer... progress) {
        //Update user interface disini

        //Integer... artinya parameter bisa inteteger lebih dari satu
        //dan bentuknya array

            pb.setProgress(progress[0]);
        }

        //selesai
        protected void onPostExecute(String result) {
            tvHasil.setText(result);
        }
    }



}
