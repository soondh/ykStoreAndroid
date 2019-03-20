package yokastore.youkagames.com.yokastore.base.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import yokastore.youkagames.com.yokastore.R;

/**
 * Created by songdehua on 2018/5/30.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
        itemView.setBackgroundResource(R.drawable.recycle_bg_selector);
    }
}
