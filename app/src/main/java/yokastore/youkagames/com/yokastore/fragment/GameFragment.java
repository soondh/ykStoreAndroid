package yokastore.youkagames.com.yokastore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseFragment;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.module.gameList.GameListPresenter;
import yokastore.youkagames.com.yokastore.module.gameList.adapter.GameListAdapter;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameListModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.OnItemClickListener;
import yokastore.youkagames.com.yokastore.view.WrapContentLinearLayoutManager;
import yokastore.youkagames.com.yokastore.view.rollpagerview.RollPagerView;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.module.gameList.activity.GameDetailActivity;


/**
 * Created by songdehua on 2018/6/1.
 */

public class GameFragment extends BaseFragment implements IBaseView {

    private GameListPresenter mPresenter;
    private XRecyclerView mRecyclerView;
    private ArrayList<GameListModel.GameInfoData> mGameListData;
    //private RollPagerView mViewPager;
    private ViewGroup container;
    private boolean isInRefresh = false;
    private GameListAdapter mGameListAdapter;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        this.container = container;
        View view = inflater.inflate(R.layout.fragment_game,null);
        return view;
    }

    @Override
    protected void initFindViewById(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        TextView tv_title_text  = view.findViewById(R.id.tv_title_text);

        tv_title_text.setText("Yoka Store");
        initRecycleView();
    }

    @Override
    public void initDataFragment() {
        mGameListAdapter = new GameListAdapter(mGameListData,this.getActivity());
        mGameListAdapter.setClickCallBack(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (!CommonUtil.fastClick()) {
                    GameDetailActivity.startGameDetailActivity(getActivity(),mGameListData.get(pos).projectId,mGameListData.get(pos).title);
                }
            }
        });

        mRecyclerView.setAdapter(mGameListAdapter);
        mPresenter.getGameListDatas();
    }

    @Override
    public void RequestSuccess(BaseModel data) {

        isInRefresh = false;
        if (data.code == 0) {
            if (data instanceof GameListModel) {
                GameListModel model = (GameListModel)data;
                if (model.data != null && model.data.size() > 0){
                    mGameListData.clear();
                    mGameListData.addAll(model.data);
                    mGameListAdapter.setData(mGameListData);
                    mRecyclerView.refreshComplete();
                }
            }
        }
    }

    private void initRecycleView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);

        mPresenter = new GameListPresenter(getActivity(),this,this);
        mGameListData = new ArrayList<>();
        mRecyclerView.getDefaultFootView().setLoadingHint("");
        mRecyclerView.getDefaultFootView().setNoMoreHint("") ;
        mRecyclerView.setLoadingListener(new GameFragment.RecycleLoadingListener());

    }

    public void refreshDataState(){
        if(mRecyclerView != null && !isInRefresh) {
            isInRefresh = true;
            mRecyclerView.scrollToPosition(0);
            mRecyclerView.refresh();
        }
    }

    public class RecycleLoadingListener implements XRecyclerView.LoadingListener{

        @Override
        public void onRefresh() {
            mPresenter.getGameListDatas();
        }
        @Override
        public void onLoadMore() {
            mPresenter.getGameListDatas();
        }
    }
    @Override
    public void NetWorkError() {
        isInRefresh = false;
        if(mRecyclerView != null) {
            mRecyclerView.refreshComplete();
        }
        CustomToast.showToast(getActivity(),R.string.net_error, Toast.LENGTH_SHORT);
    }
    @Override
    public void RequestError(Throwable e) {
        super.RequestError(e);
        isInRefresh = false;
        if (mRecyclerView != null) {
            mRecyclerView.refreshComplete();
        }
    }

}
