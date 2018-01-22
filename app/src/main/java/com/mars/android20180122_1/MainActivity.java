package com.mars.android20180122_1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
/*
使用sqlite brower來建立sqlite檔案 ,然後放到手機裡面
裝完之後執行 建立資料庫和資料表 會產生一個檔案
1.拷貝到res ==>raw 資料夾裡面
2.用程式把它搬到手機裡(產生在手機裡)
API23版本才能找到data API26有bug
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View v)
    {
        //將資料檔案從raw copy到手機裡
        File dbFile = new File(getFilesDir(), "student.db");
        InputStream is = getResources().openRawResource(R.raw.student);
        try {
            OutputStream os = new FileOutputStream(dbFile);
            int r;
            while ((r = is.read()) != -1)
            {
                os.write(r);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void click2(View v)
    {   //select讀取資料庫的方法
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //使用Cursor物件的讀取資料
        Cursor c = db.rawQuery("Select * from students", null);
        //移到第 1 筆資料
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        while (c.moveToNext())
        {
            Log.d("DB", c.getString(1) + "," + c.getInt(2));
        }
        //移到下一筆
        /*
        c.moveToNext();

        */
    }

    public void click3(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        String strSql = "Select * from students where _id=?";
        //rawQuery方法中的selectionArgs 是放陣列字串,index:0的字會帶入問號中
        Cursor c = db.rawQuery(strSql, new String[] {"2"});
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }
    public void click4(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //query的方法也可以查詢資料庫
        /*
        query ( )資料查詢 rawQuery ( )在參數中直接以字串方式設定 SQL 指令,
        而 query ( )是將 SQL 語法的結構拆解為參數,包含了要查詢的資料表名稱、
        要選取的欄位、 WHERE 篩選條件、篩選條件參數名、篩選條件參數值、
        GROUPBY 條件、 HAVING 條件。其中除了資料表名稱外,其他參數可以使用 nu |來取代。
        完成查詢後,最後以 Cursor的型別回傳資料.
        */
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, null, null, null, null, null);
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }
    public void click5(View v)
    {
        //select * from students where id = 2, 在selection參數輸入要查的條件 輸入的內容一樣是用字串陣列new String[] {"2"}
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, "_id=?", new String[] {"2"}, null, null, null);
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }

    public void click9(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //execSQL方法可以直接執行sql語法
        db.execSQL("Insert into students (_id, name, score) values (8, 'Bob', 95)");
        db.close();


    }
    public void click10(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("_id", 9);
        cv.put("name", "Mary");
        cv.put("score", 92);
        /*
        //使用SQLiteDatabase的insert方法新增記錄至表格，
        第一個參數為表格名稱，第三個則是「資料包」，
        使用ContentValues類別，就像是Java的Map集合類別，
        專門儲存Key-Value的一組對應資料組，其中Key鍵值使用欄位的名稱，
        Value則是該欄位的值
        */
        db.insert("students", null, cv);
        db.close();
    }
}

