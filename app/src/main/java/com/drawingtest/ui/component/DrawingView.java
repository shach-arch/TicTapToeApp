package com.drawingtest.ui.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View
{
	private Path mDrawPath;
	private Paint mBackgroundPaint;
	private Paint mDrawPaint;
	private Canvas mDrawCanvas;
	private Bitmap mCanvasBitmap;

	private ArrayList<Path> mPaths = new ArrayList<>();
	private ArrayList<Paint> mPaints = new ArrayList<>();
	private ArrayList<Path> mUndonePaths = new ArrayList<>();
	private ArrayList<Paint> mUndonePaints = new ArrayList<>();

	// Set default values
	private int mBackgroundColor = 0xFFFFFFFF;
	private int mPaintColor = 0xFF660000;
	private int mStrokeWidth = 10;

	// Drawing Shapes
	private Paint mDrawPaintFinal;
	public int mCurrentShape = 0;
	public static final int JustDraw = 0;
	public static final int RECTANGLE = 1;
	public static final int TRIANGLE = 2;
	public static final int CIRCLE = 3;

	protected float mStartX;
	protected float mStartY;

	protected float touchX;
	protected float touchY;


	/**
	 * Indicates if you are drawing
	 */
	protected boolean isDrawing = false;


	public DrawingView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mDrawPath = new Path();
		mBackgroundPaint = new Paint();
		initPaint();
	}

	private void initPaint()
	{
		mDrawPaint = new Paint();
		mDrawPaint.setColor(mPaintColor);
		mDrawPaint.setAntiAlias(true);
		mDrawPaint.setDither(true);
		mDrawPaint.setStrokeWidth(mStrokeWidth);
		mDrawPaint.setStyle(Paint.Style.STROKE);
		mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
		mDrawPaint.setStrokeCap(Paint.Cap.ROUND);

		mDrawPaintFinal = new Paint();
		mDrawPaintFinal.setColor(mPaintColor);
		mDrawPaintFinal.setAntiAlias(true);
		mDrawPaintFinal.setStrokeWidth(mStrokeWidth);
		mDrawPaintFinal.setDither(true);
		mDrawPaintFinal.setStyle(Paint.Style.STROKE);
		mDrawPaintFinal.setStrokeJoin(Paint.Join.ROUND);
		mDrawPaintFinal.setStrokeCap(Paint.Cap.ROUND);
	}

	private void drawBackground(Canvas canvas)
	{
		mBackgroundPaint.setColor(mBackgroundColor);
		mBackgroundPaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), mBackgroundPaint);
	}

	private void drawPaths(Canvas canvas)
	{
		int i = 0;
		for (Path p : mPaths)
		{
			canvas.drawPath(p, mPaints.get(i));
			i++;
		}
	}

	public void setShape(int newShape) {
		mCurrentShape = newShape;
	}// setmCurrentShape

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mDrawCanvas = new Canvas(mCanvasBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawPath(mDrawPath, mDrawPaint);
		super.onDraw(canvas);
		canvas.drawBitmap(mCanvasBitmap, 0, 0, mDrawPaint);
		drawBackground(canvas);
		drawPaths(canvas);


		//draw your element
		if (isDrawing){
			switch (mCurrentShape) {
				case RECTANGLE:
					onDrawRectangle(canvas);
					break;
				case TRIANGLE:
					onDrawTriangle(canvas);
					break;
				case CIRCLE:
					onDrawCircle(canvas);
					break;
			}
		}
	}// onDraw

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		touchX = event.getX();
		touchY = event.getY();

		switch (mCurrentShape) {
			case JustDraw:
				onTouchEventNormalDraw(event);
				break;
			case RECTANGLE:
				onTouchEventRectangle(event);
				break;
			case TRIANGLE:
				onTouchEventTriangle(event);
				break;
			case CIRCLE:
				onTouchEventCircle(event);
				break;
		}
		return true;
	}// onTouchEvent


	public void clearCanvas()
	{
		mPaths.clear();
		mPaints.clear();
		mUndonePaths.clear();
		mUndonePaints.clear();
		mDrawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}

	public void setPaintColor(int color)
	{
		mPaintColor = color;
		mDrawPaint.setColor(mPaintColor);
	}

	public void setPaintStrokeWidth(int strokeWidth)
	{
		mStrokeWidth = strokeWidth;
		mDrawPaint.setStrokeWidth(mStrokeWidth);
	}

	public void setBackgroundColor(int color)
	{
		mBackgroundColor = color;
		mBackgroundPaint.setColor(mBackgroundColor);
		invalidate();
	}

	public Bitmap getBitmap()
	{
		drawBackground(mDrawCanvas);
		drawPaths(mDrawCanvas);
		return mCanvasBitmap;
	}

	public void undo()
	{
		if (mPaths.size() > 0)
		{
			mUndonePaths.add(mPaths.remove(mPaths.size() - 1));
			mUndonePaints.add(mPaints.remove(mPaints.size() - 1));
			invalidate();
		}
	}// undo

	public void redo()
	{
		if (mUndonePaths.size() > 0)
		{
			mPaths.add(mUndonePaths.remove(mUndonePaths.size() - 1));
			mPaints.add(mUndonePaints.remove(mUndonePaints.size() - 1));
			invalidate();
		}
	}// redo

	//------------------------------------------------------------------
	// Normal Draw
	//------------------------------------------------------------------
	private void onTouchEventNormalDraw(MotionEvent event) {
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				//First Touch. Store the initial point
				mDrawPath.moveTo(touchX, touchY);
				//mDrawPath.addCircle(touchX, touchY, mStrokeWidth/10, Path.Direction.CW);
				break;
			case MotionEvent.ACTION_MOVE:
				// We are drawing
				mDrawPath.lineTo(touchX, touchY);
				break;
			case MotionEvent.ACTION_UP:
				//We are finished the draw. Draw on canvas
				mDrawPath.lineTo(touchX, touchY);
				mPaths.add(mDrawPath);
				mPaints.add(mDrawPaint);
				mDrawPath = new Path();
				initPaint();
				break;
		}
		invalidate();
	}// onTouchEventNormalDraw

	//------------------------------------------------------------------
	// Rectangle
	//------------------------------------------------------------------
	private void onTouchEventRectangle(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isDrawing = true;
				mStartX = touchX;
				mStartY = touchY;
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				isDrawing = false;
				drawRectangle(mDrawCanvas, mDrawPaintFinal);
				mPaths.add(mDrawPath);
				mPaints.add(mDrawPaint);
				mDrawPath = new Path();
				initPaint();
				invalidate();
				break;
		}
	}// onTouchEventRectangle

	private void onDrawRectangle(Canvas canvas) {
		drawRectangle(canvas, mDrawPaint);
	}// onDrawRectangle

	private void drawRectangle(Canvas canvas,Paint paint){
		float right = Math.max(mStartX, touchX);
		float left = Math.min(mStartX, touchX);
		float bottom = Math.max(mStartY, touchY);
		float top = Math.min(mStartY, touchY);
		canvas.drawRect(left, top , right, bottom, paint);
	}// drawRectangle


	//------------------------------------------------------------------
	// Triangle
	//------------------------------------------------------------------

	int countTouch =0;
	float basexTriangle =0;
	float baseyTriangle =0;

	private void onDrawTriangle(Canvas canvas){

		if (countTouch<3){
			canvas.drawLine(mStartX,mStartY,touchX,touchY,mDrawPaint);
		}else if (countTouch==3){
			canvas.drawLine(touchX,touchY,mStartX,mStartY,mDrawPaint);
			canvas.drawLine(touchX,touchY,basexTriangle,baseyTriangle,mDrawPaint);
		}
	}

	private void onTouchEventTriangle(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				countTouch++;
				if (countTouch==1){
					isDrawing = true;
					mStartX = touchX;
					mStartY = touchY;
				} else if (countTouch==3){
					isDrawing = true;
				}
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				countTouch++;
				isDrawing = false;
				if (countTouch<3){
					basexTriangle=touchX;
					baseyTriangle=touchY;
					mDrawCanvas.drawLine(mStartX,mStartY,touchX,touchY,mDrawPaintFinal);
				} else if (countTouch>=3){
					mDrawCanvas.drawLine(touchX,touchY,mStartX,mStartY,mDrawPaintFinal);
					mDrawCanvas.drawLine(touchX,touchY,basexTriangle,baseyTriangle,mDrawPaintFinal);
					countTouch =0;
				}
				mPaths.add(mDrawPath);
				mPaints.add(mDrawPaint);
				mDrawPath = new Path();
				initPaint();
				invalidate();
				break;
		}
	}


	//------------------------------------------------------------------
	// Circle
	//------------------------------------------------------------------
	private void onDrawCircle(Canvas canvas){
		canvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX, mStartY, touchX, touchY), mDrawPaint);
	}// onDrawCircle

	private void onTouchEventCircle(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isDrawing = true;
				mStartX = touchX;
				mStartY = touchY;
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				isDrawing = false;
				mDrawCanvas.drawCircle(mStartX, mStartY,
						calculateRadius(mStartX,mStartY, touchX, touchY), mDrawPaintFinal);
				mPaths.add(mDrawPath);
				mPaints.add(mDrawPaint);
				mDrawPath = new Path();
				initPaint();
				invalidate();
				break;
		}
	}// onTouchEventCircle

	/**
	 *
	 * @return
	 */
	protected float calculateRadius(float x1, float y1, float x2, float y2) {

		return (float) Math.sqrt(
				Math.pow(x1 - x2, 2) +
						Math.pow(y1 - y2, 2)
		);
	}// calculateRadius

}
