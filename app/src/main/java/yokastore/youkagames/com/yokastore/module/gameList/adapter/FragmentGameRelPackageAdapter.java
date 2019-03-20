package yokastore.youkagames.com.yokastore.module.gameList.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.viewholder.BaseViewHolder;
import yokastore.youkagames.com.yokastore.client.engine.bean.BeanFactory;
import yokastore.youkagames.com.yokastore.client.engine.callback.BaseHttpCallBack;
import yokastore.youkagames.com.yokastore.module.gameList.fragment.GameRelPackFragment;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.support.utils.time.TimeUtils;
import yokastore.youkagames.com.yokastore.module.gameList.model.GamePackageModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.support.image.ImageLoaderUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by songdehua on 2018/8/8.
 */

public class FragmentGameRelPackageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ItemClickCallBack clickCallBack;
    private Activity mContext;
    private GameDetailModel.GameDetailData gameDetailData;
    private ArrayList<GamePackageModel.DataBean> gameRelPackageData;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public FragmentGameRelPackageAdapter(Activity context,GameDetailModel.GameDetailData data){
        mContext = context;
        gameDetailData = data;
    }

    public void updateGameRelPackageData(ArrayList<GamePackageModel.DataBean> dataBeans){
        gameRelPackageData  = dataBeans;
    }

    public void updateGameDetailData(GameDetailModel.GameDetailData data) {
        gameDetailData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.package_item, parent, false);
        holder = new ViewTypeTwoHolderScore(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (gameDetailData == null) {
            return;
        }

        if (holder instanceof ViewTypeTwoHolderScore) {
            setGameDetailData(position,(ViewTypeTwoHolderScore) holder);
        }
    }

    private void setGameDetailData(int position, ViewTypeTwoHolderScore holder){
        if (gameRelPackageData != null && gameRelPackageData.size() > 0){
            GamePackageModel.DataBean dataBean = gameRelPackageData.get(position);
            holder.tv_pname.setText(dataBean.originalFileName);
            holder.tv_purl.setText(dataBean.urlOfDownload);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(dataBean.gmtModified);
            Date date = new Date(lt);
            String update_time = sdf.format(date);

            holder.tv_date.setText(update_time);
            ImageLoaderUtils.loadRoundImg(mContext, gameDetailData.icon,holder.iv_icon);

            holder.tv_download.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (!CommonUtil.fastClick()){
                        if (clickCallBack != null){
                            clickCallBack.onItemClick(position);
                        }
                    }
                }
            });
        }
    }



    /*
    *游戏安装包列表布局
     */
    public class ViewTypeTwoHolderScore extends BaseViewHolder{
        ImageView iv_icon;
        TextView tv_pname;
        TextView tv_purl;
        TextView tv_download;
        TextView tv_date;

        public ViewTypeTwoHolderScore(View view){
            super(view);
            iv_icon = view.findViewById(R.id.iv_header);
            tv_pname = view.findViewById(R.id.tv_pname);
            tv_purl = view.findViewById(R.id.tv_purl);
            tv_download = view.findViewById(R.id.tv_download);
            tv_date = view.findViewById(R.id.tv_date);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (gameRelPackageData != null && gameRelPackageData.size() > 0) {
            count += gameRelPackageData.size();
        }
        return count;
    }


    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }
}
