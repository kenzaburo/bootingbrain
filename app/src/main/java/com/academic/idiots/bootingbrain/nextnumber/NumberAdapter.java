package com.academic.idiots.bootingbrain.nextnumber;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.academic.idiots.bootingbrain.LogDebug;
import com.academic.idiots.bootingbrain.R;
import java.util.ArrayList;
import java.util.Random;

public class NumberAdapter extends BaseAdapter {
    private LogDebug logger = new LogDebug(this.getClass().getName());
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private Context context;
    private int nextNumber = 0;

    public NumberAdapter(Context c, int level){
        this.context = c;
        int[] intArray = new int[level * level];
        for (int i = 0; i < level * level; i++) {
                intArray[i] = i;
        }
        shuffleArray(intArray, this.arrayList);
        logger.logDebug(arrayList.toString());
    }

    public boolean removeItem(int position){
        int value = arrayList.get(position);
        if((value != -1) && (value == nextNumber)){
            arrayList.set(position, -1);
            nextNumber = nextNumber + 1;
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmpty(){
        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i) != -1){
                return false;
            }
        }
        return true;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView;

        if(convertView == null){
            itemView = (View)inflater.inflate(R.layout.grid_item,null);
            TextView textView = (TextView) itemView.findViewById(R.id.number);
            textView.setText(arrayList.get(position).toString());

        }else{
            if(arrayList.get(position).intValue() != -1){
                // Do not things
            }else{
                //Add animation
                convertView.setBackgroundColor(0);
                convertView.setActivated(false);
                TextView textView = (TextView) convertView.findViewById(R.id.number);
                textView.setText("");
            }
            itemView = (View) convertView;
        }
        return itemView;
    }


    // Implementing Fisher–Yates shuffle
    static void shuffleArray(int[] ar, ArrayList<Integer> list)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i >= 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        for(int i = 0; i < ar.length; i++ ){
            list.add(ar[i]);
        }
    }
}
