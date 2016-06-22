package com.example.mizutani.myapplication;

/**
 * Created by mizutani on 2016/06/22.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {

        /*
            ここで任意のデータベースファイル名と、バージョンを指定する
            データベースファイル名 = MyTable.db
            バージョン = 1
         */
        super(context, "MyTable.db", null, 1);
    }

    //onCreateメソッド
    /*
    onCreateメソッドはデータベースを初めて使用する時に実行される処理
    ここでテーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルの作成
        db.execSQL("CREATE TABLE MyTable " +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", Pref TEXT" +
                ", City0 TEXT" +
                ", City1 TEXT" +
                ", City2 TEXT" +
                ", City3 TEXT" +
                ", City4 TEXT" +
                ", Clear INTEGER" +
                ")");

        // 初期データ投入
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('北海道','札幌','青森','盛岡','仙台','札幌',1);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('山形県','山形','山形','宇都宮','前橋','東京',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('群馬県','前橋','横浜','前橋','京都','水戸',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('福井県','福井','秋田','盛岡','仙台','福井',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('石川県','金沢','前橋','京都','金沢','盛岡',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('兵庫県','神戸','神戸','京都','和歌山','盛岡',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('山梨県','甲府','前橋','京都','金沢','甲府',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('長野県','長野','前橋','東京','長野','盛岡',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('岐阜県','岐阜','前橋','岐阜','仙台','札幌',0);");
        db.execSQL("INSERT INTO MyTable(Pref,City0, City1, City2, City3, City4, Clear) values ('静岡県','静岡','静岡','神戸','京都','和歌山',0);");
    }

    /*
     * onUpgradeメソッド
     * onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれる
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // とりあえず今回は空でOK
    }
}