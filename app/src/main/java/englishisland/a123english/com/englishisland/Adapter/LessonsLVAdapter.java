package englishisland.a123english.com.englishisland.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import englishisland.a123english.com.englishisland.R;
import englishisland.a123english.com.englishisland.TO.Lesson;

/**
 * Created by Mohamad on 6/27/2016.
 */
public class LessonsLVAdapter extends BaseAdapter {


    Context context;
    ArrayList<Lesson> lesson;

    LayoutInflater inflater;

    public LessonsLVAdapter(Context context, ArrayList<Lesson> lessons) {
        this.context = context;
        this.lesson = lessons;
        inflater = LayoutInflater.from(context);
    }




    @Override
    public int getCount() {
        return lesson.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.staff_list_item, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.ivPic = (ImageView) convertView.findViewById(R.id.ivPic);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvName.setText(lesson.get(position).getName());
        //  holder.ivPic.setImageResource(context.getResources().getIdentifier("drawable/" + "anani.png", null, context.getPackageName()));

        if (lesson.get(position).getIsPremium() == 0){
            holder.ivPic.setImageResource(R.drawable.ic_lock_black_24dp);
        }else if (lesson.get(position).getIsPremium() == 1){
            holder.ivPic.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
        return convertView;

    }

    private class ViewHolder {

        ImageView ivPic;
        TextView tvName;

    }
}

