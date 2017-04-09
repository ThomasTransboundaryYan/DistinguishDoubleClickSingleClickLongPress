package com.example.liuk.justjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {
    private int countA = 0;
    //TextView txtView = (TextView) findViewById(R.id.textview_barcoderesult);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button = (Button) findViewById(R.id.button_order);
        final Button buttonToast = (Button)findViewById(R.id.button_toast);
        final ImageView barcodeImageView = (ImageView)findViewById(R.id.imageview_barcode);
        ImageView myImageView = (ImageView) findViewById(R.id.imageview_barcode);
        final TextView txtView = (TextView) findViewById(R.id.textview_barcoderesult);
        final Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.barcode_test);
        myImageView.setImageBitmap(myBitmap);



        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                TextView numberOfOrders = (TextView) findViewById(R.id.textView_numberOfOrder);
                numberOfOrders.setText(""+(++countA));

            }
        });

       buttonToast.setOnLongClickListener(new View.OnLongClickListener(){
           @Override
           public boolean onLongClick(View v) {
               Toast.makeText(MainActivity.this,"这是一个长按事件！",Toast.LENGTH_LONG).show();

               return false;
           }
       });

        barcodeImageView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this,"正在识别二维码", Toast.LENGTH_SHORT).show();
                BarcodeDetector detector =
                        new BarcodeDetector.Builder(getApplicationContext())
                                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                                .build();
                if(!detector.isOperational()){
                    txtView.setText("Could not set up the detector!");
                    return true;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);
                Barcode thisCode = barcodes.valueAt(0);
                txtView.setText(thisCode.rawValue);
                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add_item){
            Toast.makeText(MainActivity.this,"Add success",Toast.LENGTH_LONG).show();
        }else if(item.getItemId()==R.id.remove_item){
            ((TextView)findViewById(R.id.textview_barcoderesult)).setText("NULL");
            Toast.makeText(MainActivity.this,"Remove success",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}

