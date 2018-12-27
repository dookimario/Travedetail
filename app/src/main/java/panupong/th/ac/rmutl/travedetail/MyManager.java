package panupong.th.ac.rmutl.travedetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyManager {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public MyManager(Context context) {
        this.context = context;
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
    }

    public long addValueSQlite(String nameString,
                               String surnameString,
                               String genderString,
                               String ageString,
                               String travelString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", nameString);
        contentValues.put("Surname", surnameString);
        contentValues.put("Gender", genderString);
        contentValues.put("Age", ageString);
        contentValues.put("Travel", travelString);

        return sqLiteDatabase.insert("userTABLE", null, contentValues);

    }

}
