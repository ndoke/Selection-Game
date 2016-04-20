package com.example.selectiongame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	public Integer[] mThumbIds = new Integer[25];

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	public void putFruit(Integer[] mThumbIds, String nameFru, int ind) {
		if (nameFru.equals("Apples"))
			mThumbIds[ind] = R.drawable.apple;
		else if (nameFru.equals("Lemons"))
			mThumbIds[ind] = R.drawable.lemon;
		else if (nameFru.equals("Mangoes"))
			mThumbIds[ind] = R.drawable.mango;
		else if (nameFru.equals("Peaches"))
			mThumbIds[ind] = R.drawable.peach;
		else if (nameFru.equals("Strawberrys"))
			mThumbIds[ind] = R.drawable.strawberry;
		else
			mThumbIds[ind] = R.drawable.tomato;
	}

	public void setImageview(int numFru, String nameFru, String fruits[]) {
		int temp = 0;
		Random rnd = new Random();
		while (temp < numFru) {
			int ind = rnd.nextInt(25);
			while (mThumbIds[ind] != null)
				ind = rnd.nextInt(25);
			putFruit(mThumbIds, nameFru, ind);
			temp++;
		}

		while (temp < 25) {
			int ind = rnd.nextInt(25);
			int ind1 = rnd.nextInt(6);
			while (mThumbIds[ind] != null)
				ind = rnd.nextInt(25);
			while (fruits[ind1].equals(nameFru))
				ind1 = rnd.nextInt(6);
			putFruit(mThumbIds, fruits[ind1], ind);
			temp++;
		}
		
	}
	
	public void shuffleImages(int numFru, int remFru, String nameFru, String fruits[], GridView gd) {
		Random rnd = new Random();
		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < numFru; i++) {
			temp.add(nameFru);
		}
		
		for (int i = 0; i < remFru; i++) {
			int ind = rnd.nextInt(6);
			while (fruits[ind].equals(nameFru))
				ind = rnd.nextInt(6);
			temp.add(fruits[ind]);
		}
		
		Collections.shuffle(temp);
		while(temp.size() < 25){
			temp.add("null");
		}

		Log.d("list", temp + "");
		int tempInd = 0;
		for (int i = 0; i < 25; i++) {
			if (gd.getChildAt(i).getAlpha() != 0.5f) {
				putFruit(mThumbIds, temp.get(tempInd++), i);
			} else {
				putFruit(mThumbIds, nameFru, i);
			}
		}
		
		Log.d("thumbs", Arrays.toString(mThumbIds) + "");
	}

}