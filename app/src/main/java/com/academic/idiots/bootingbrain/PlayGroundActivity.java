package com.academic.idiots.bootingbrain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.matheclipse.parser.client.eval.DoubleEvaluator;

import math.generator.MathExpressionGenerator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.InputFilter.LengthFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PlayGroundActivity extends FragmentActivity implements
		View.OnClickListener {

	public Fragment[] Items = { new Fragment(), new Fragment() };
	int[] colors = { Color.BLUE, Color.GREEN, Color.CYAN, Color.RED,
			Color.WHITE, Color.YELLOW, Color.GRAY };
	ViewPager viewPager;
	FragmentPagerAdapter mAdapter;
	Random rand;
	TextView tv_math_expression, tv_timer, tv_rs;
	EditText ed_result;
	FragmentManager mFrgMan;
	Button btn_next, btn_back;

	Timer timer;
	MyTimerTask myTimerTask;
	int count = 0;
	Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8,
			btn_9, btn_dot;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_ground);
		mFrgMan = getSupportFragmentManager();
		rand = new Random();
		// replaceFragment(Items[0]);
		mp = MediaPlayer.create(this, R.raw.beep_01a);
		btn_next = (Button) findViewById(R.id.btn_next);
		tv_math_expression = (TextView) findViewById(R.id.tv_math_expression);
		btn_9 = (Button) findViewById(R.id.button9);
		btn_8 = (Button) findViewById(R.id.button8);
		btn_7 = (Button) findViewById(R.id.button7);
		btn_6 = (Button) findViewById(R.id.button6);
		btn_5 = (Button) findViewById(R.id.button5);
		btn_4 = (Button) findViewById(R.id.button4);
		btn_3 = (Button) findViewById(R.id.button3);
		btn_2 = (Button) findViewById(R.id.button2);
		btn_1 = (Button) findViewById(R.id.button1);
		btn_0 = (Button) findViewById(R.id.button0);
		btn_dot = (Button) findViewById(R.id.button_dot);
		findViewById(R.id.btn_back).setOnClickListener(this);
		
		btn_9.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_1.setOnClickListener(this);
		btn_0.setOnClickListener(this);
		btn_dot.setOnClickListener(this);

		String mathExp = MathExpressionGenerator.mathExpression(99, 0, 4);
		tv_math_expression.setText(mathExp);
		tv_timer = (TextView) findViewById(R.id.tv_timer);
		ed_result = (EditText) findViewById(R.id.ed_result);
		ed_result.setFocusable(false);
		tv_rs = (TextView) findViewById(R.id.tv_rs);
		btn_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// replaceFragment(new ItemFragment());
				try {
					DoubleEvaluator engine = new DoubleEvaluator();
					double d = engine.evaluate(tv_math_expression.getText()
							.toString());
					double rs = Double.parseDouble(ed_result.getText()
							.toString());
					if (rs == d) {
						count++;
						tv_rs.setText(count + "");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String mathExp = MathExpressionGenerator.mathExpression();
				tv_math_expression.setText(mathExp);
				ed_result.setText("");
				ed_result.setFocusable(true);
			}
		});

		// timer = new Timer();
		// myTimerTask = new MyTimerTask();
		//
		// if (false) {
		// // singleshot delay 1000 ms
		// timer.schedule(myTimerTask, 1000);
		// } else {
		// // delay 1000ms, repeat in 5000ms
		// timer.schedule(myTimerTask, 1000, 5000);
		// }
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startGame();
	}

	public void startGame() {
		btn_next.setEnabled(true);
		new CountDownTimer(60000, 1000) {

			public void onTick(long millisUntilFinished) {
				tv_timer.setText("seconds remaining: " + millisUntilFinished
						/ 1000);
				mp.start();
				
			}

			public void onFinish() {
				tv_timer.setText("Time out!");
				btn_next.setEnabled(false);
				dialog = createAdialog(count);				
				dialog.show();
			}
		}.start();
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mp!= null)
			mp.release();
	}
	// public void replaceFragment(Fragment fragment) {
	// FragmentTransaction transaction = mFrgMan.beginTransaction();
	// transaction.replace(R.id.container, fragment);
	// transaction.setCustomAnimations(android.R.anim.slide_in_left,
	// android.R.anim.slide_out_right);
	// transaction.commit();
	// }

	// class FragAdapter extends FragmentPagerAdapter {
	// public FragAdapter(FragmentManager fm) {
	// super(fm);
	// // TODO Auto-generated constructor stub
	// }
	//
	// @Override
	// public Fragment getItem(int position) {
	// // TODO Auto-generated method stub
	// return Items[position];
	// }
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return Items.length;
	// }
	//
	// }

	public class ItemFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.item_fragment, container,
					false);
			rootView.setBackgroundColor(colors[rand.nextInt(7)]);
			return rootView;
		}
	}

	class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd:MMMM:yyyy HH:mm:ss a");
			final String strDate = simpleDateFormat.format(calendar.getTime());
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					tv_timer.setText(strDate);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button0:

		case R.id.button1:
			// break;
		case R.id.button2:
			// break;
		case R.id.button3:
			// break;
		case R.id.button5:
			// break;
		case R.id.button6:
			// break;
		case R.id.button7:
			// break;
		case R.id.button8:
			// break;
		case R.id.button9:
			// break;
		case R.id.button_dot:
			String string = ed_result.getText().toString()+ ((Button) v).getText().toString();
			ed_result.setText(string);
			break;			
		case R.id.btn_back:
			onBackspacePress();
			break;
		default:
			break;
		}
	}
		
	private void onBackspacePress()
	{
		String string = ed_result.getText().toString();
		if (string != null && string.length() != 0){
			ed_result.setText(string.substring(0, string.length()-1));
		}
	}

	private Dialog dialog;
	private Dialog createAdialog(int count) {
		String mess;
		if (count > 1) {
			mess = "You have " + count + " true calculations";
		} else {
			if (count == 1) {
				mess = "You have " + count + " true calculation";
			} else {
				mess = "You have no true calculation";
			}
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Your result").setMessage(mess)
		.setNegativeButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		return alertDialogBuilder.create();
	}

}
