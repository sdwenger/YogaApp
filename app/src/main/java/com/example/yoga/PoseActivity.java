package com.example.yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pose);
        final Context context = this;

        SQLiteDatabase mydatabase = openOrCreateDatabase("Workout", MODE_PRIVATE, null);
        final Integer sequence = getIntent().getIntExtra("SEQUENCE", 0);
        Cursor c = mydatabase.rawQuery("SELECT Sequence, Name, IsLR FROM Yoga WHERE Sequence=?", new String[]{sequence.toString()});
        c.moveToFirst();
        final TextView titleView = (TextView) findViewById(R.id.title);
        final String poseName = c.getString(c.getColumnIndex("Name"));
        titleView.setText(c.getString(c.getColumnIndex("Sequence")) + ". " + poseName);
        Boolean isLR = (c.getInt(c.getColumnIndex("IsLR")) == 1);

        final ImageView symmetric = (ImageView) findViewById(R.id.symmetric);
        final ImageView leftPose = (ImageView) findViewById(R.id.leftPose);
        final ImageView rightPose = (ImageView) findViewById(R.id.rightPose);
        final String poseAsFileName = poseName.toLowerCase().replaceAll("[\\s\\)\\(\\-]","");
        if (isLR) {
            symmetric.setVisibility(View.INVISIBLE);
            leftPose.setImageResource(getResources().getIdentifier(poseAsFileName+"left", "drawable", getPackageName()));
            leftPose.setVisibility(View.VISIBLE);
            rightPose.setImageResource(getResources().getIdentifier(poseAsFileName+"right", "drawable", getPackageName()));
            rightPose.setVisibility(View.VISIBLE);
        } else {
            symmetric.setImageResource(getResources().getIdentifier(poseAsFileName, "drawable", getPackageName()));
            symmetric.setVisibility(View.VISIBLE);
            leftPose.setVisibility(View.INVISIBLE);
            rightPose.setVisibility(View.INVISIBLE);
        }

        final Button mainMenuButton = (Button) findViewById(R.id.toMain);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class); startActivity(intent);
            }
        });

        final Integer count = getIntent().getIntExtra("COUNT", 0);

        final Button prevButton = (Button) findViewById(R.id.previous);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, PoseActivity.class);
                intent.putExtra("SEQUENCE", (sequence==1)?count:sequence-1);
                intent.putExtra("COUNT", count);
                startActivity(intent);
            }
        });

        final Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, PoseActivity.class);
                intent.putExtra("SEQUENCE", (sequence==count)?1:sequence+1);
                intent.putExtra("COUNT", count);
                startActivity(intent);
            }
        });
    }
}
