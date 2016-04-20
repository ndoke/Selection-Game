package com.example.selectiongame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public TextView tx, tx1;
	public final String[] fruits = { "Apples", "Lemons", "Mangoes", "Peaches", "Strawberrys", "Tomatoes" };
	public int numFru, remFru, divNum;
	public String nameFru;
	public final HashSet<Integer> clickPos = new HashSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button bt1 = (Button) findViewById(R.id.button1);
		final Random rndFru = new Random();
		final Random rndNumFru = new Random();
		tx = (TextView) findViewById(R.id.textView2);
		nameFru = fruits[rndFru.nextInt(fruits.length - 1)];
		numFru = rndNumFru.nextInt(8) + 1;
		remFru = 25 - numFru;
		divNum = numFru;
		tx.setText(" " + nameFru + " (" + numFru + ")");

		bt1.post(new Runnable() {
			@Override
			public void run() {
				bt1.performClick();
			}
		});

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final long cTime = System.currentTimeMillis();
				nameFru = fruits[rndFru.nextInt(fruits.length - 1)];
				numFru = rndNumFru.nextInt(8) + 1;
				remFru = 25 - numFru;
				divNum = numFru;
				tx.setText(" " + nameFru + " (" + numFru + ")");
				clickPos.clear();

				final GridView gd = (GridView) findViewById(R.id.gridView1);
				final ImageAdapter img = new ImageAdapter(MainActivity.this);
				gd.setAdapter(img);
				img.setImageview(numFru, nameFru, fruits);

				final HashMap<String, Integer> fruitMap = new HashMap<String, Integer>();
				fruitMap.put("Apples", R.drawable.apple);
				fruitMap.put("Lemons", R.drawable.lemon);
				fruitMap.put("Mangoes", R.drawable.mango);
				fruitMap.put("Peaches", R.drawable.peach);
				fruitMap.put("Strawberrys", R.drawable.strawberry);
				fruitMap.put("Tomatoes", R.drawable.tomato);

				gd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if (img.mThumbIds[position].equals(fruitMap.get(nameFru))) {
							numFru--;
							if (numFru == 0) {
								tx.setText(" " + nameFru + " (" + numFru + ")");
								view.setOnClickListener(null);
								view.setAlpha(0.5f);
								long eTime = System.currentTimeMillis();
								long tim = (eTime - cTime) / 1000;
								double score = (double) divNum / tim * 100;
								new AlertDialog.Builder(MainActivity.this)
										.setTitle("Congratulations, your score is " + Math.round(score) + "!")
										.setMessage("Do you want to exit").setCancelable(false)
										.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
									}
								}).setNegativeButton("New Game", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										bt1.post(new Runnable() {
											@Override
											public void run() {
												bt1.performClick();
											}
										});
									}
								}).setIcon(android.R.drawable.ic_dialog_alert).show();
							} else {
								tx.setText(" " + nameFru + " (" + numFru + ")");
								view.setOnClickListener(null);
								view.setAlpha(0.5f);

								// shuffle the view
								img.shuffleImages(numFru, remFru, nameFru, fruits, gd);
								img.notifyDataSetChanged();
							}
						} else {
							new AlertDialog.Builder(MainActivity.this).setTitle("Wrong guess!")
									.setMessage("Do you want to exit").setCancelable(false)
									.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									finish();
									System.exit(0);
								}
							}).setNegativeButton("Reset", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									bt1.post(new Runnable() {
										@Override
										public void run() {
											bt1.performClick();
										}
									});
								}
							}).setIcon(android.R.drawable.ic_dialog_alert).show();
						}
					}
				});
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});

	}
}
