package com.example.linyuming.viewfilpperdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private int[] images = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,R.drawable.icon5};
    private GestureDetector gestureDetector;
    private ViewFlipper viewFlipper=null;
    private static final int FING_MIN_DISTANCE=100;
    private static final int FING_MIN_VELOCITY=100;
    private Activity mActivity = null;
    private Animation rInAnim,rOutAnim,lInAnim,lOutAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity=this;
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFilipper);
        gestureDetector = new GestureDetector(this,this);//声明检查手势事件
        //AnimationUtils类用于定义常用的处理动画功能
        //loadAnimation(Context context,int id) 从资源文件中加载一个Animation对象
        //向右滑动左侧进入的渐变效果(alpha 0.1->1.0)
        rInAnim = AnimationUtils.loadAnimation(mActivity,R.anim.push_right_in);
        //向右滑动右侧滑出的渐变效果(alpha 1.0->0.1)
        rOutAnim = AnimationUtils.loadAnimation(mActivity,R.anim.push_right_out);
        //向左滑动左侧进入的渐变效果(alpha 0.1->1.0)
        lInAnim = AnimationUtils.loadAnimation(mActivity,R.anim.push_left_in);
        //向右滑动右侧滑出的渐变效果(alpha 1.0->0.1)
        lOutAnim = AnimationUtils.loadAnimation(mActivity,R.anim.push_left_out);
        for(int i = 0;i < images.length;i++){
            //添加图片源
            ImageView iv = new ImageView(this);
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(iv,i,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }
        viewFlipper.setAutoStart(true);//设置自动播放功能
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping();//点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        //调用GestureDetector的onTouchEvent方法
        //将捕捉到的MontionEvent交给GestureDetector来分析是否有合适的callback函数来处理用户手势
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //从左向右滑动（左进右出），x轴的坐标位移大于FING_MIN_DISTANCE,且移动速度大于FING_MIN_VELOCITY个像素/秒
        if(e2.getX()-e1.getX()>FING_MIN_DISTANCE && Math.abs(velocityX)>FING_MIN_VELOCITY){
            viewFlipper.setInAnimation(lInAnim);
            viewFlipper.setOutAnimation(rOutAnim);
            viewFlipper.showPrevious();
            setTitle("相片编号："+viewFlipper.getDisplayedChild());
            return true;
        }else if(e1.getX()-e2.getX()>FING_MIN_DISTANCE && Math.abs(velocityX)>FING_MIN_VELOCITY){
            //从右向左滑动（右进左出）
            viewFlipper.setInAnimation(rInAnim);
            viewFlipper.setOutAnimation(lOutAnim);
            viewFlipper.showPrevious();
            setTitle("相片编号："+viewFlipper.getDisplayedChild());
            return true;
        }
        return true;
    }
}