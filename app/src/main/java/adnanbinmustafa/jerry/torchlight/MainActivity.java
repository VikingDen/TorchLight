package adnanbinmustafa.jerry.torchlight;

import android.app.Activity;

import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends Activity {


    private boolean isTorchOn = false;
    private boolean isLCDOn = false;
    private Camera camera;
    private Camera.Parameters p;

    ImageButton imgTorchOn, imgTorchOff, imgLCDOn, imgLCDOff, imgInfo;
    RelativeLayout myTorchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getCamera();
        imgTorchOn = (ImageButton) findViewById(R.id.imageTorchOn);
        imgTorchOff = (ImageButton) findViewById(R.id.imageTorchOff);
        imgLCDOn = (ImageButton) findViewById(R.id.imageFrontLCDOn);
        imgLCDOff = (ImageButton) findViewById(R.id.imageFrontLCDOff);
        imgInfo = (ImageButton) findViewById(R.id.imageInfo);

        myTorchLayout=(RelativeLayout)findViewById(R.id.torchLayout);

        turnTorchOn();


        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });


        imgTorchOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                turnTorchOff();
            }
        });

        imgTorchOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                turnTorchOn();

            }
        });

        imgLCDOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isTorchOn)
                {
                    turnTorchOff();
                    turnLCDOn();

                    imgLCDOff.setVisibility(View.VISIBLE);

                    imgLCDOn.setVisibility(View.INVISIBLE);
                    imgTorchOff.setVisibility(View.INVISIBLE);
                    imgTorchOn.setVisibility(View.INVISIBLE);
                    imgInfo.setVisibility(View.INVISIBLE);

                    myTorchLayout.setBackgroundColor(Color.WHITE);



                }
                else
                {
                    turnLCDOn();
                    imgLCDOff.setVisibility(View.VISIBLE);

                    imgLCDOn.setVisibility(View.INVISIBLE);
                    imgTorchOff.setVisibility(View.INVISIBLE);
                    imgTorchOn.setVisibility(View.INVISIBLE);
                    imgInfo.setVisibility(View.INVISIBLE);

                    myTorchLayout.setBackgroundColor(Color.WHITE);

                }
            }
        });

        imgLCDOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                turnLCDOff();

                imgLCDOff.setVisibility(View.INVISIBLE);

                imgLCDOn.setVisibility(View.VISIBLE);
                imgTorchOff.setVisibility(View.VISIBLE);

                imgInfo.setVisibility(View.VISIBLE);

               myTorchLayout.setBackgroundResource(R.drawable.torch_background);




            }
        });
    }//end of onCreate

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
        }
    }

    // on activity stop
    @Override
    protected void onStop() {
        super.onStop();
        if(isTorchOn)
        {
           turnTorchOn();
        }
        else
        {
            return;
        }
    }


    //Display info as toast
    private void showToast() {
        Toast.makeText(getApplicationContext(), "Developed by\nAdnan Bin Mustafa", Toast.LENGTH_LONG).show();

    }

    private void getCamera() {
        if (camera == null) {
            try {

                camera = Camera.open();
                p = camera.getParameters();

            } catch (RuntimeException e) {
                Log.e(" Camera Error.\n Error:", e.getMessage());

            }
        }

    }

    private void turnTorchOn() {

        p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
        isTorchOn = true;
        imgTorchOn.setVisibility(View.VISIBLE);
        imgTorchOff.setVisibility(View.INVISIBLE);

    }


    private void turnTorchOff() {



            p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
            isTorchOn = false;
            imgTorchOff.setVisibility(View.VISIBLE);
            imgTorchOn.setVisibility(View.INVISIBLE);



    }


    private void turnLCDOn()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 1.0f;
        getWindow().setAttributes(params);
        isLCDOn=true;
    }
    private  void turnLCDOff()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = -1.0f;
        getWindow().setAttributes(params);
        isLCDOn=false;
        isTorchOn=false;


    }

}
