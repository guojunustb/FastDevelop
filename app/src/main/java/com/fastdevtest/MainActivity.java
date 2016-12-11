
package com.fastdevtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fastdevtest.utils.Des;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Des des=new Des();
        String src="FQi1N-B9cJugzSUvqo_kDwy0F153c-kJTNi1DCWl:US75Irl4t8QoeQVvoIjQAE3J53I=:eyJzY29wZSI6InRlc3QiLCJkZWFkbGluZSI6MTQ2MzY1NTIzMn0=";
        String c="fjQ8Cic0ChaIuNtPIMcMT1ChD5abEJGojaxuToGioCTelKKqyhiUqHCsWzxD9zbSXKj3SKeMXTffrHtPcqLnb4GMCZi3QcyBOj5c1YqupjWHGi5wPl69xC+kRBw0FEINpoVgwM5+C7rTIc2zusUzjh8jdt5MGePv8r2Q98vQ=";
        String encode=des.getEnc(src);
        Log.d("MainActivity", "re encode=="+c.equals(encode) +"    "+encode);

        //解密
        String dec=des.getDec(encode);
        boolean is=src.equals(dec);
        Log.d("MainActivity", "re dec=="+is +"    "+dec);
    }

}
