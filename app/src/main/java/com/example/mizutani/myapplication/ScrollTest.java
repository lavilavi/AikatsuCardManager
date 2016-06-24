package com.example.mizutani.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ListIterator;

public class ScrollTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
    }

    /**
     * 画像のスクロールを試すための画面を表します。
     */
    public static class TestImageScrollActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
        /**
         * 画像の表示モードが CENTER であることを示すテキスト。
         */
        private static final String DISPLAY_MODE_CENTER = "Center";

        /**
         * 画像の表示モードが FIT_CENTER であることを示すテキスト。
         */
        private static final String DISPLAY_MODE_FIT_CENTER = "Fit Center";

        /**
         * 画像の表示方法。
         */
        private ImageView.ScaleType mImageScaleType = ImageView.ScaleType.CENTER;

        /**
         * 画像が表示領域における、X 軸方向の一辺からはみ出る量。
         * 例えば画像の幅が 1080、表示領域は 480 の場合、「( 1080 - 480 ) / 2 = 300」とする。
         * 画像より表示領域が大きいならば、この値はゼロとなる。
         */
        private int mOverX;

        /**
         * 画像が表示領域における、Y 軸方向の一辺からはみ出る量。
         * 例えば画像の高さが 720、表示領域は 480 の場合、「( 720 - 480 ) / 2 = 120」とする。
         * 画像より表示領域が大きいならば、この値はゼロとなる。
         */
        private int mOverY;

        /**
         * 画像を表示するための View。
         */
        private ImageView mImageView;

        /**
         * 画像の表示モードとなるテキスト。
         */
        private TextView mDisplayModeTextView;

        /**
         * タッチの始点となる X 座標。
         */
        private float mTouchBeginX;

        /**
         * タッチの始点となる Y 座標。
         */
        private float mTouchBeginY;
        private int mSwitchFlag;
        private ListIterator mPictureManager;

        /**
         * 画面と画像のサイズを元に、一辺からはみ出る量を算出します。
         *
         * @param display 画面のサイズ。
         * @param image   画像のサイズ。
         *
         * @return 一辺からはみ出る量。画面に画像が収まる場合はゼロ。
         */
        private static int calcOverValue( int display, int image ) {
            return ( display < image ? ( image - display ) / 2 : 0 );
        }

        /**
         * 画面の設定が変更された時に発生します。
         *
         * @param newConfig 新しい設定。
         */
        @Override
        public void onConfigurationChanged( Configuration newConfig ) {
            super.onConfigurationChanged( newConfig );

            this.updateOverSize();
            this.mImageView.scrollTo( 0, 0 );
        }

        /**
         * 画面が作成された時に発生します。
         *
         * @param savedInstanceState 保存されたインスタンスの状態。
         */
        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );
            this.setContentView( R.layout.activity_scroll_test );

            this.mDisplayModeTextView = ( TextView )this.findViewById( R.id.display_mode );
            this.mDisplayModeTextView.setText( DISPLAY_MODE_CENTER );
            this.mDisplayModeTextView.setOnClickListener( this );

            this.mImageView = ( ImageView )this.findViewById( R.id.image_view );
            this.mImageView.setScaleType( this.mImageScaleType );
            this.mImageView.setOnTouchListener( this );

            this.updateOverSize();
        }

        /**
         * View がクリックされた時に発生します。
         *
         * @param v クリックされた View。
         */
        public void onClick( View v ) {
            if( this.mImageScaleType == ImageView.ScaleType.CENTER ) {
                this.mImageScaleType = ImageView.ScaleType.FIT_CENTER;
                this.mDisplayModeTextView.setText( DISPLAY_MODE_FIT_CENTER );

            } else {
                this.mImageScaleType = ImageView.ScaleType.CENTER;
                this.mDisplayModeTextView.setText( DISPLAY_MODE_CENTER );
            }

            this.mImageView.setScaleType( this.mImageScaleType );
            this.mImageView.scrollTo( 0, 0 );
        }

        /**
         * View がタッチされた時に発生します。
         *
         * @param v     タッチされた View。
         * @param event イベント データ。
         *
         * @return タッチ操作を他の View へ伝搬しないなら true。する場合は false。
         */
        public boolean onTouch( View v, MotionEvent event ) {
            switch( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    this.mTouchBeginX = event.getX();
                    this.mTouchBeginY = event.getY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    float x = event.getX(), y = event.getY();
                    this.scrollImage( x, y, true );

                    this.mTouchBeginX = x;
                    this.mTouchBeginY = y;
                    break;

                case MotionEvent.ACTION_UP:
                    this.scrollImage( event.getX(), event.getY(), false );
                    break;
            }

            return true;
        }

        /**
         * 画像のスクロールを実行します。
         *
         * @param x        スクロール基準となる X 座標。
         * @param y        スクロール基準となる Y 座標。
         * @param isMoving 移動中の場合は true。それ以外は false。
         *
         * @return 画像を切り替えない場合は 0。前に戻すなら -1、次に進めるときは 1。
         */
        private void scrollImage( float x, float y, boolean isMoving ) {
            if( isMoving ) {
                int moveX = ( int )( this.mTouchBeginX - x );
                int moveY = ( int )( this.mTouchBeginY - y );
                this.mImageView.scrollBy( moveX, moveY );

            } else if ( this.mImageScaleType == ImageView.ScaleType.FIT_CENTER ) {
                this.scrollFinish( x, y, 0, 0 );

            } else {
                this.scrollFinish( x, y, this.mOverX, this.mOverY );
            }
        }

        /**
         * 画像のスクロールが完了した時の処理を実行します。
         *
         * @param x     スクロール基準となる X 座標。
         * @param y     スクロール基準となる Y 座標。
         * @param overX 画面を基準として、画像の表示領域が X 軸にはみ出ている量。
         * @param overY 画面を基準として、画像の表示領域が Y 軸にはみ出ている量。
         */
        private void scrollFinish( float x, float y, int overX, int overY ) {
            int moveX = this.calcScrollValue( ( int )( this.mTouchBeginX - x ), this.mImageView.getScrollX(), overX );

            switch( this.mSwitchFlag ) {
                case -1: this.updateImage( this.mPictureManager.prev() ); return;
                case  1: this.updateImage( this.mPictureManager.next() ); return;

                default:
                    int moveY = this.calcScrollValue( ( int )( this.mTouchBeginY - y ), this.mImageView.getScrollY(), overY );
                    this.mImageView.scrollBy( moveX, moveY );
                    break;
            }
        }

        /**
         * スクロール量を算出します。
         *
         * @param move 移動する予定の量。
         * @param pos  現在のスクロール座標
         * @param over 画像が表示領域の一辺からはみ出る量。
         *
         * @return スクロール量。
         */
        private int calcScrollValue( int move, int pos, int over ) {
            int newPos = pos + move;
            if( newPos < -over ) {
                move = -( over + pos );
                this.mSwitchFlag = ( newPos < -( over + this.mSwitchSize ) ? -1 : 0 );

            } else if( over < newPos ) {
                move = over - pos;
                this.mSwitchFlag = ( ( over + this.mSwitchSize ) < newPos ? 1 : 0 );

            } else {
                this.mSwitchFlag=0;
            }

            return move;
        }

        /**
         * 画像と表示領域を比較し、はみ出る量を算出します。
         */
        private void updateOverSize() {
            Display display = ( (WindowManager)this.getSystemService( Context.WINDOW_SERVICE ) ).getDefaultDisplay();
            Drawable image   = this.mImageView.getDrawable();

            this.mOverX = calcOverValue( display.getWidth(),  image.getIntrinsicWidth()  );
            this.mOverY = calcOverValue( display.getHeight(), image.getIntrinsicHeight() );
        }
    }

}
