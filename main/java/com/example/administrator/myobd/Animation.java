package com.example.administrator.myobd;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by DWF on 2018/2/2.
 */

public class Animation {
    private AlertDialog pd=null;
    public void Alpha (View view,float start,float end,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"alpha",new float[]{start,end} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void AlphaReverse (View view,float end,float start,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"alpha",new float[]{start,end} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }

    public void TranslateX(View view,float startx,float endx,int time,int count){
/*        TranslateAnimation translateAnimation=new TranslateAnimation ( android.view.animation.Animation.RELATIVE_TO_SELF,startx,
                android.view.animation.Animation.RELATIVE_TO_SELF,endx,
                android.view.animation.Animation.RELATIVE_TO_SELF,0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF,0.0f );
        translateAnimation.setDuration ( time );
        translateAnimation.setFillAfter(true);
        view.startAnimation (translateAnimation);*/
        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat ( view,"translationX",new float[]{startx,endx} );
        objectAnimator.setInterpolator (new OvershootInterpolator (  ));
        objectAnimator.setDuration ( time );
        objectAnimator.setRepeatCount ( count );
        objectAnimator.start ();
    }

    public void TranslateXBounce(View view,float startx,float endx,int time,int count){
/*        TranslateAnimation translateAnimation=new TranslateAnimation ( android.view.animation.Animation.RELATIVE_TO_SELF,startx,
                android.view.animation.Animation.RELATIVE_TO_SELF,endx,
                android.view.animation.Animation.RELATIVE_TO_SELF,0.0f,
                android.view.animation.Animation.RELATIVE_TO_SELF,0.0f );
        translateAnimation.setDuration ( time );
        translateAnimation.setFillAfter(true);
        view.startAnimation (translateAnimation);*/
        ObjectAnimator objectAnimator =  ObjectAnimator.ofFloat ( view,"translationX",new float[]{startx,endx} );
        objectAnimator.setInterpolator (new BounceInterpolator (  ));
        objectAnimator.setDuration ( time );
        objectAnimator.setRepeatCount ( count );
        objectAnimator.start ();
    }
    public void TranslateY(View view,float starty,float endy,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"translationY",new float[]{starty,endy} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void TranslateXReverse(View view,float endx,float startx,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"translationX",new float[]{startx,endx} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void TranslateYReverse(View view,float endy,float starty,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"translationY",new float[]{starty,endy} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }

    public void ScaleX(View view,float startx,float endx,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleX",new float[]{startx,endx} );
        view.setPivotX ( view.getX ()/2 );
        objectAnimator.setInterpolator ( new BounceInterpolator ( ) );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void ScaleY(View view,float starty,float endy,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleY",new float[]{starty,endy} );
        view.setPivotY ( view.getY ()/2 );
        objectAnimator.setInterpolator ( new BounceInterpolator ( ) );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void ScaleXReverse(View view,float endx,float startx,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleX",new float[]{startx,endx} );
        view.setPivotX ( view.getX ()/2 );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void ScaleYReverse(View view,float endy,float starty,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleY",new float[]{starty,endy} );
        view.setPivotY ( view.getY ()/2 );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }

    public void Rotate(View view,float start,float end,int time,int b){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"rotation",new float[]{start,end} );
        objectAnimator.setDuration ( time );
        objectAnimator.setRepeatCount ( b );
        objectAnimator.start ();
    }
    public void RotateReverse(View view,float end,float start,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"rotation",new float[]{start,end} );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void Color(View view,int start,int end,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt( view,"backgroundColor",start,end );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void ColorReverse(View view,int end,int start,int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt( view,"backgroundColor",start,end );
        objectAnimator.setDuration ( time );
        objectAnimator.start ();
    }
    public void ac1_usr_click(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleX",new float[]{1.0f,0.8f} );
        view.setPivotX ( 50f);
        objectAnimator.setDuration ( 100 );
        objectAnimator.start ();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat ( view,"scaleY",new float[]{1.0f,0.8f} );
        view.setPivotY ( 400f );
        objectAnimator1.setDuration ( 100 );
        objectAnimator1.start ();
    }
    public void ac1_usr_click_back(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat ( view,"scaleX",new float[]{0.8f,1f} );
        view.setPivotX ( 50f);
        objectAnimator.setDuration ( 100 );
        objectAnimator.start ();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat ( view,"scaleY",new float[]{0.8f,1f} );
        view.setPivotY ( 400f );
        objectAnimator1.setDuration ( 100 );
        objectAnimator1.start ();
    }







    public void alpha(View view,float start,float end,int time,int mode,int count){
       AlphaAnimation alphaAnimation = new AlphaAnimation ( start,end );
        alphaAnimation.setDuration ( time );
        alphaAnimation.setRepeatMode ( mode );
        alphaAnimation.setRepeatCount ( count );
        view.startAnimation (alphaAnimation);
    }

   /* public void fly2000_fans_rotate(View view,int time){
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 359f);//最好是0f到359f，0f和360f的位置是重复的
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator ());
        rotation.setDuration(time);
        rotation.start();
    }*/
}
