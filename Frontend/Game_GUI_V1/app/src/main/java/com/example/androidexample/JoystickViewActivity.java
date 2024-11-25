package com.example.androidexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickViewActivity extends View {

    private float centerX, centerY; // Center of the joystick
    private float baseRadius, knobRadius; // Radius of the base and knob
    private float knobX, knobY; // Position of the knob
    private JoystickListener joystickListener; // Listener for joystick events

    public JoystickViewActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        centerX = 0;
        centerY = 0;
        baseRadius = 150;
        knobRadius = 75;
        knobX = centerX;
        knobY = centerY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint basePaint = new Paint();
        basePaint.setARGB(255, 150, 150, 150); // Light gray
        Paint knobPaint = new Paint();
        knobPaint.setARGB(255, 100, 100, 100); // Darker gray

        // Draw the joystick base
        canvas.drawCircle(centerX, centerY, baseRadius, basePaint);

        // Draw the joystick knob
        canvas.drawCircle(knobX, knobY, knobRadius, knobPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // Constrain knob to stay within the base
                float dx = x - centerX;
                float dy = y - centerY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                if (distance < baseRadius) {
                    knobX = x;
                    knobY = y;
                } else {
                    knobX = centerX + (dx / distance) * baseRadius;
                    knobY = centerY + (dy / distance) * baseRadius;
                }

                // Notify listener
                if (joystickListener != null) {
                    float angle = (float) Math.toDegrees(Math.atan2(centerY - knobY, knobX - centerX));
                    float strength = Math.min(distance / baseRadius, 1.0f); // Normalize strength to [0, 1]
                    joystickListener.onJoystickMoved(angle, strength);
                }
                break;

            case MotionEvent.ACTION_UP:
                // Reset knob position to the center
                knobX = centerX;
                knobY = centerY;

                // Notify listener of reset
                if (joystickListener != null) {
                    joystickListener.onJoystickMoved(0, 0); // Angle = 0, strength = 0
                }
                break;
        }

        invalidate(); // Redraw the view
        return true;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // Set joystick dimensions dynamically
        centerX = width / 2f;
        centerY = height / 2f;
        baseRadius = Math.min(width, height) / 3f;
        knobRadius = baseRadius / 2f;
        knobX = centerX;
        knobY = centerY;
    }

    public void setOnJoystickMoveListener(JoystickListener listener) {
        this.joystickListener = listener;
    }

    public interface JoystickListener {
        void onJoystickMoved(float angle, float strength);
    }
}


