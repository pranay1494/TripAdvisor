package com.example.tripadvisor;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class Activity_Card_Holder extends RecyclerView.Adapter<Activity_Card_Holder.ViewHolder> {
	
	static int[] newdelhi={R.drawable.cp,R.drawable.jm,R.drawable.newdelhi,R.drawable.ip,R.drawable.ak_tem,R.drawable.bangla,R.drawable.chandni_chowk_bharat_monuments1,R.drawable.about_pic,R.drawable.jama_masjid,R.drawable.red_fort,R.drawable.purana_quila1,R.drawable.humayun_tomb,R.drawable.iskcon_temple};
	static String[] name;
	static String[] type;
	static String[] open_hrs;
	static String[] fair;
	static String[] fphotography;
	int alength;
    private int rowLayout;
    static Context mContext;
    static String state;

    public Activity_Card_Holder(int alength,String state1,String[] afair,String[] photography_fair,String[] name1,String[] type1,String[] open_hrs1, int rowLayout, Context context) {
        name = name1;
        type = type1;
        open_hrs = open_hrs1;
        mContext=context;
        state=state1;
        fair=afair;
        fphotography=photography_fair;
        this.alength=alength;
        this.rowLayout=rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        
        viewHolder.activityName.setText(name[i]);
        viewHolder.activityType.setText(type[i]);
        viewHolder.activityOpenHrs.setText(open_hrs[i]);
        viewHolder.activityImage.setImageResource(newdelhi[i]);
        Log.i("fair",fair[i]);
    //    viewHolder.activityfair.setText(fair[i]);
      //  viewHolder.activityphoto_fair.setText(fphotography[i]);
        Log.i("newdelhi",Integer.toString(newdelhi[i]));
        
     //   Ion.with(viewHolder.activityImage).placeholder(R.drawable.placeholder).error(R.drawable.error).load(newdelhi[i]);
        //.animateLoad(spinAnimation)
        //.animateIn(fadeInAnimation)
        
    }

    @Override
    public int getItemCount() {
        return  alength;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView activityName;
        public TextView activityType;
        public TextView activityOpenHrs;
        public ImageView activityImage;
     //   public TextView activityfair;
       // public TextView activityphoto_fair;

        public ViewHolder(View itemView) {
            super(itemView);
            activityName = (TextView) itemView.findViewById(R.id.placeName);
            activityImage = (ImageView)itemView.findViewById(R.id.place_Image);
            activityType = (TextView) itemView.findViewById(R.id.place_type);
            activityOpenHrs = (TextView) itemView.findViewById(R.id.open_hrs_time);
      //      activityfair= (TextView) itemView.findViewById(R.id.fair_text);
       //     activityphoto_fair = (TextView) itemView.findViewById(R.id.photography_text);
            itemView.setOnClickListener(this);
            
        }

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Intent i = new Intent(mContext,About_Activity.class);
			i.putExtra("state_name",state);
			int pos1=getPosition();
			i.putExtra("place_name",name[pos1].toString());
			i.putExtra("place_type",type[pos1].toString());
			i.putExtra("place_open",open_hrs[pos1].toString());
			i.putExtra("place_photo",newdelhi[pos1]);
			i.putExtra("place_fair", fair[pos1]);
			i.putExtra("place_photo_fair", fphotography[pos1]);
		     mContext.startActivity(i);
		}
    }
}