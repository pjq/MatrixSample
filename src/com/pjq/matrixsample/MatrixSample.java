package com.pjq.matrixsample;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory; //import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Canvas;
import android.graphics.Paint;

public class MatrixSample extends Activity implements OnClickListener
{
	// 新建Bitmap,Canvas和Paint
	private Bitmap		img, r_img;
	private Canvas		canvas;
	private Paint		paint;

	private ImageView	imageView1;
	private ImageView	imageView2;

	private Button		addButton;
	private Button		reduceButton;
	private Chronometer	chronometer;

	public static float	rotateDegrees	= 0;

	// 由于是自定义view,所以不需要调用Layout文件
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 调用自定义View
		setContentView(R.layout.main);

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		addButton = (Button) findViewById(R.id.addButton);
		reduceButton = (Button) findViewById(R.id.reduceButton);
		chronometer = (Chronometer) findViewById(R.id.chronometer);

		addButton.setOnClickListener(this);
		reduceButton.setOnClickListener(this);

		img = BitmapFactory.decodeResource(getResources(), R.drawable.photo1);

		imageView1.setBackgroundResource(R.drawable.photo1);

	}

	public Bitmap RotateBitmap(Bitmap bitmap, float degrees)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);

		int width = img.getWidth();
		int height = img.getHeight();
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

	}

	public class MyView extends View
	{
		// View的初始化
		public MyView(Context context)
		{
			super(context);

			// BitmapFactory：从源创建一个Bitmap对象，这些源包括：文件，流或者数组
			img = BitmapFactory.decodeResource(getResources(),
					R.drawable.photo1);
			// 新建一个Matrix对象
			Matrix matrix = new Matrix();
			// 让矩阵实现翻转，参数为FLOAT型
			matrix.postRotate(180);
			// matrix.postRotate(0);
			// 获取Bitmap的高与宽
			int width = img.getWidth();
			int height = img.getHeight();
			// 源Bitmap通过一个Matrix变化后，返回一个不可变的Bitmap
			r_img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
			paint = new Paint();

		}

		// 在自定义VIEW时，必须实现此方法
		public void onDraw(Canvas canvas)
		{
			// 在重写父类的方法时，必须先调用父类的方法
			super.onDraw(canvas);
			// 利用Canvas在View上绘制一个Bitmap，并设置它的样式和颜色
			canvas.drawBitmap(r_img, 10, 10, paint);

			// 该方法是用来更新View的方法，多与线程结合使用。
			// this.invalidate ()
			// 下面三段代码用于在View上绘制一个实心矩形，设置颜色为绿色，
			// paint.setColor(Color.GREEN);
			// paint.setAntiAlias(true);
			// canvas.drawRect(new Rect(30,30,100,100), paint);

		}
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		int id = v.getId();

		switch (id)
		{
		case R.id.addButton:

			rotateDegrees += 5;
			imageView2.setImageBitmap(RotateBitmap(img, rotateDegrees));
			if (rotateDegrees % 5 == 0)
			{
				chronometer.start();
			} else
			{
				chronometer.stop();
			}
			break;

		case R.id.reduceButton:
			rotateDegrees -= 5;
			imageView2.setImageBitmap(RotateBitmap(img, --rotateDegrees));
			canvas.drawBitmap(img, rotateDegrees, rotateDegrees, null);
			chronometer.setBase(SystemClock.elapsedRealtime());

			break;

		default:
			break;
		}

	}

}