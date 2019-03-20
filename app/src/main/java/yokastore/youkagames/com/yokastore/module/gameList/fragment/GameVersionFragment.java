package yokastore.youkagames.com.yokastore.module.gameList.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseFragment;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.model.DataBooleanMode;
import yokastore.youkagames.com.yokastore.module.gameList.GameListPresenter;
import yokastore.youkagames.com.yokastore.module.gameList.activity.GameDetailActivity;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GamePackageModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.WrapContentLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by songdehua on 2018/6/27.
 */

/*
public class GameVersionFragment extends BaseFragment implements IBaseView{
    private GameListPresenter mPresenter;
    private XRecyclerView mRecyclerView;
    private int m_Page = 1;
    private GameDetailModel mGameDetailDatas;
    private ArrayList<GamePackageModel.DataBean> mPackageDatas;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_package_list, null);
        return view;
    }

    @Override
    public void initFindViewById(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mPresenter = new GameListPresenter(getActivity(), this, this);
    }

    private void initRecycleView(){
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        mRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
//        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycle_line);
//        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(dividerDrawable));
        mRecyclerView.getDefaultFootView().setLoadingHint("");
        mRecyclerView.getDefaultFootView().setVisibility(View.INVISIBLE);
        mRecyclerView.getDefaultFootView().setNoMoreHint("");
        mRecyclerView.setLoadingListener(new RecycleLoadingListener());

    }

    public class RecycleLoadingListener implements XRecyclerView.LoadingListener{

        @Override
        public void onRefresh() {
            m_Page = 1;
            mPresenter.getGameVeriosnDatas(mGameDetailDatas.game_id, m_Page);
        }

        @Override
        public void onLoadMore() {
            m_Page ++;
            mPresenter.getCommentList(mGameDetailData.game_id, orderBy, m_Page);
        }
    }

}*/

