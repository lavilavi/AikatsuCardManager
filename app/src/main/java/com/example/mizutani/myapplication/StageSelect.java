package com.example.mizutani.myapplication;

/**
 * Created by mizutani on 2016/06/22.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class StageSelect extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //タイトルバーを非表示にする（必ずsetContentViewの前にすること）
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // レイアウトをセットする
        setContentView(R.layout.activity_stage_select);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ボタン処理呼び出し
        setIcon();

    }

    // ボタンをクリア済みかどうかで色分け＆クリック不可処理
    private void setIcon(){
        // 作成したDatabaseHelperクラスに読み取り専用でアクセス
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        /*
        SELECT文
        テーブル名 MyTableから Clearの項目を検索してくる条件式
         */
        String sql = "SELECT Clear FROM MyTable";

        // 上記のSELECT文を実行してカーソルを取得
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();

        // CLEAR_FLAGを入れる配列を作成。 配列の要素数はデータの数だけ
        int[] ClearFlg = new int[c.getCount()];

        // クリア状況取得して配列CLEAR_FLAGに入れていく
        for(int i = 0; i < c.getCount(); i++){
            //なんでgetInt[0]？ゼロ？
            ClearFlg[i] = c.getInt(0);
            c.moveToNext();
        }
        // データベースからデータを取り終わったのでクローズ処理
        c.close();
        db.close();

        // ボタンの個数だけ繰り返す
        for(int i=1;i<=10;i++){
            // layoutでbuttonのidをbutton1～10で連番を振っているのでそれを利用する
            String resViewName = "button" + i;
            int viewId = getResources().getIdentifier(resViewName, "id", getPackageName());
            Button button = (Button)findViewById(viewId);
            // Clearの値によって処理を分ける
            //ButtonのIDは1,2,3,4,5~、配列のIDは0,1,2,3,4~なので、ClearFlag[i-1]
            if(ClearFlg[i-1] == 1){
                // Clearの値が1の場合
                button.setText(String.valueOf(i)); // ボタンテキストに問題番号を表示
                button.setTextColor(0xff58BE89); // テキストの色
                button.setBackgroundColor(0xffffffff); // ボタンの色
                button.setEnabled(true); // ボタン選択可能にする
            }else{
                // Clearの値が0の場合
                button.setText(String.valueOf(i)); // ボタンテキストに問題番号を表示
                button.setTextColor(0xffffffff); // テキストの色
                button.setBackgroundColor(0xffB7B7B7); // ボタンの色
                button.setEnabled(false); // ボタン選択不可にする
            }
        }
    }



    // ボタンクリックでゲーム画面へ遷移
    public void onClick(View v){

        // 遷移先のActivityを指定
        // Intent intent = new　Intent(このクラスから, このクラスへ)
        Intent intent = new Intent(StageSelect.this, MainGame.class);

        // 選択されたステージをボタンのテキストから取得
        intent.putExtra("QuestionNo", ((Button) v).getText());

        // 遷移開始
        startActivity(intent);
    }
}
