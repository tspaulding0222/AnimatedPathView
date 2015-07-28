package com.realart.tomspaulding.animatedpathview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;

import com.realart.tomspaulding.animatedpathviewlibrary.AnimatedPathView;


public class MainActivity extends Activity {

    private Handler pathAnimationHandler;
    private Runnable pathAnimationRunnable;
    private float pathPercentage = 0;
    private float previousPathPercentage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AnimatedPathView view = (AnimatedPathView)findViewById(R.id.animated_path);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                Path p = new Path();
                p.moveTo(0, 0);
                p.lineTo(view.getWidth(), 0);
                p.close();

                view.setPath(p);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                pathAnimationHandler = new Handler();

                pathAnimationRunnable = new Runnable() {
                    @Override
                    public void run() {
                        pathPercentage = pathPercentage + .1f;
                        if(pathPercentage > 1){
                            pathPercentage = 0;
                        }

                        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "percentage", previousPathPercentage, pathPercentage);
                        anim.setDuration(100);
                        anim.setInterpolator(new LinearInterpolator());
                        anim.start();

                        previousPathPercentage = pathPercentage;

                        pathAnimationHandler.postDelayed(pathAnimationRunnable, 120);
                    }
                };

                pathAnimationHandler.postDelayed(pathAnimationRunnable, 0);

            }
        });
    }
}
