package yokastore.youkagames.com.yokastore.module.gameList.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.YokaApplication;
import yokastore.youkagames.com.yokastore.module.gameList.GameListPresenter;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameListModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.view.OnItemClickListener;
import yokastore.youkagames.com.yokastore.view.rollpagerview.StaticPagerAdapter;
import yokastore.youkagames.com.support.image.ImageLoaderUtils;

import java.util.ArrayList;

/**
 * Created by songdehua on 2018/6/28.
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
    public ArrayList<GameListModel.GameInfoData> mDatas;
    private Context mContext;
    private OnItemClickListener clickCallBack;

    public void setClickCallBack(OnItemClickListener clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    //public ArrayList<String> datas = null;

    public GameListAdapter( ArrayList<GameListModel.GameInfoData> datas, Context context){
        mContext = context;
        mDatas = datas;
    }

    public void setData(ArrayList<GameListModel.GameInfoData> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gamelistitem, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.nameText.setText(mDatas.get(position).title);
        ImageLoaderUtils.loadImgWithCorner(mContext, mDatas.get(position).icon,viewHolder.coverView,17, R.drawable.ic_img_loading);
        viewHolder.cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击游戏icon,跳转至游戏详情页
                        if(clickCallBack != null){
                            clickCallBack.onItemClick(position);
                        }
                    }
                }
        );
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView coverView;
        public TextView nameText;
        public View colorView;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.grid_item_card_view);
            coverView = (ImageView) v.findViewById(R.id.grid_item_cover);
            nameText = (TextView) v.findViewById(R.id.text);
            colorView = v.findViewById(R.id.grid_item_color);
        }
    }
}