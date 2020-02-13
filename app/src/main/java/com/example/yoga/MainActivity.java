package com.example.yoga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
    static String tableCreate = "CREATE TABLE IF NOT EXISTS Yoga (Sequence INT, Name VARCHAR(40), IsLR BIT(1))";
    static String [] insertQueries = {"INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (1, 'Upward Facing Dog', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (2, 'Downward Facing Dog', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (3, 'Revolved Chair Variation', 1)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (4, 'Standing Half Forward Bend', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (5, 'Camel Pose', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (6, 'Head-to-Knee Forward Bend', 1)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (7, 'Extended Triangle Pose', 1)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (8, 'Pigeon Pose', 1)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (9, 'Standing Back Bend', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (10, 'Warrior I', 1)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (11, 'Upward (Reverse) Plank Pose', 0)",
            "INSERT INTO Yoga (Sequence, Name, IsLR) VALUES (12, 'Seated Forward Bend', 0)"};
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this;

        SQLiteDatabase mydatabase = openOrCreateDatabase("Workout", MODE_PRIVATE, null);
        Cursor c = mydatabase.rawQuery("SELECT Sequence, Name, IsLR FROM Yoga ORDER BY Sequence", new String[]{});
        /*
        if (!c.moveToFirst()) {
            for (String s: insertQueries){mydatabase.execSQL(s);}
            c = mydatabase.rawQuery("SELECT Sequence, Name, IsLR FROM Yoga ORDER BY Sequence", new String[]{});
        }
        */
        final Integer count = c.getCount();
        c.moveToFirst();
        ArrayList<String> outputList = new ArrayList<String>(12);
        do {
            StringBuilder nextString = new StringBuilder(c.getString(c.getColumnIndex("Sequence")));
            nextString.append(". ");
            nextString.append(c.getString(c.getColumnIndex("Name")));
            if (c.getInt(c.getColumnIndex("IsLR")) == 1) {
                nextString.append("-L/R");
            }
            outputList.add(nextString.toString());
        } while (c.moveToNext());
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.listitem, outputList);

        ListView listView = (ListView) findViewById(R.id.posesList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {
                Intent intent = new Intent(context, PoseActivity.class);
                intent.putExtra("SEQUENCE", position+1);
                intent.putExtra("COUNT", count);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
