package com.amit.android.facetracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amit.android.facetracker.FaceTrackerActivity;
import com.amit.android.facetracker.R;

public class ShowPictureActivity extends AppCompatActivity {

    private static final String IMAGE_PATH = "extra_path";

    public static Intent createIntent(Context context, String path) {
        Intent intent = new Intent(context, ShowPictureActivity.class);
        intent.putExtra(IMAGE_PATH, path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        setContentView(R.layout.activity_display_image);
        String path = getIntent().getStringExtra(IMAGE_PATH);
        ImageView ivShowImage = findViewById(R.id.img);
        Bitmap bitmap = BitmapFactory.decodeFile(path, new BitmapFactory.Options());
        ivShowImage.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, FaceTrackerActivity.class));
        finish();
    }
}
