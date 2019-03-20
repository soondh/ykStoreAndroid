package yokastore.youkagames.com.yokastore.module.gameList.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import yokastore.youkagames.com.support.utils.LogUtil;
import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.base.activity.BaseFragment;
import yokastore.youkagames.com.yokastore.model.BaseModel;
import yokastore.youkagames.com.yokastore.module.gameList.activity.GameDetailActivity;
import yokastore.youkagames.com.yokastore.module.gameList.adapter.FragmentGameCusPackageAdapter;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameDetailModel;
import yokastore.youkagames.com.yokastore.module.gameList.GameListPresenter;
import yokastore.youkagames.com.support.image.ImageLoaderUtils;
import yokastore.youkagames.com.yokastore.module.gameList.model.GamePackageModel;
import yokastore.youkagames.com.yokastore.module.gameList.model.GameTypePackageModel;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;
import yokastore.youkagames.com.yokastore.view.CustomToast;
import yokastore.youkagames.com.yokastore.view.IBaseView;
import yokastore.youkagames.com.yokastore.view.IBaseControl;
import yokastore.youkagames.com.yokastore.view.UpdateDialog;
import yokastore.youkagames.com.yokastore.view.WrapContentLinearLayoutManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by songdehua on 2018/8/8.
 */

public class GameCusPackFragment extends BaseFragment implements IBaseView{
    final int REQUEST_WRITE_EXTERNAL_STORAGE=1;//申请权限的请求码
    private GameListPresenter mPresenter;
    private XRecyclerView mRecyclerView;
    private GameDetailModel.GameDetailData mGameDetailData;
    private FragmentGameCusPackageAdapter mAdapter;
    private ArrayList<GamePackageModel.DataBean> mGamePackageDatas;
    private DownloadAPK mDownloadAPK;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_package_list,null);
        return view;
    }

    @Override
    protected void initFindViewById(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mPresenter = new GameListPresenter(getActivity(), this, this);
    }

    private void initRecycleView() {
        WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        mRecyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        mRecyclerView.getDefaultFootView().setLoadingHint("");
        mRecyclerView.getDefaultFootView().setVisibility(View.INVISIBLE);
        mRecyclerView.getDefaultFootView().setNoMoreHint("");
        mRecyclerView.setLoadingListener(new RecycleLoadingListener());
        mAdapter = new FragmentGameCusPackageAdapter(getActivity(),mGameDetailData);

        mAdapter.setClickCallBack(new FragmentGameCusPackageAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                GamePackageModel.DataBean data = mGamePackageDatas.get(pos);
                String download_url = data.urlOfDownload;
                if (!CommonUtil.fastClick()) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(getActivity(),"请打开相关权限，否则无法安装已下载应用！",Toast.LENGTH_SHORT).show();
                        }
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_WRITE_EXTERNAL_STORAGE);
                    }

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("提示");
                    progressDialog.setMessage("正在下载...");
                    progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDownloadAPK.cancel(true);
                        }
                    });
                    progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "浏览器下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDownloadAPK.cancel(true);

                            CommonUtil.openBrowser(getActivity(),download_url);
                        }
                    });
                    progressDialog.setIndeterminate(false);
                    progressDialog.setMax(100);
                    progressDialog.setCancelable(false);                    //设置不可点击界面之外的区域让对话框消失
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);         //进度条类型
                    progressDialog.show();
                    String downloadUrl = download_url; //这里写你的apk url地址
                    mDownloadAPK = new DownloadAPK(progressDialog);
                    mDownloadAPK.execute(downloadUrl);
                }
            }
        });
        /*
        mAdapter.setClickCallBack(new FragmentGameCusPackageAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                //下载对应url的apk包
                GamePackageModel.DataBean data = mGamePackageDatas.get(pos);
                String url = data.urlOfDownload;
                //下载对应url的apk包
                if (!CommonUtil.fastClick()) {
                    UpdateDialog dialog = new UpdateDialog(getActivity() , getString(R.string.confirm_download) , "安装包:" + data.originalFileName,
                            getString(R.string.dialog_notify_positive) ,
                            getString(R.string.dialog_notify_negative));
                    dialog.setListener(new UpdateDialog.OnNormalDialogClickListener() {
                        @Override
                        public void onPositive() {
                            CommonUtil.openBrowser(getActivity(), url);
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegative() {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });
        */
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initDataFragment() {
        if (mGameDetailData == null) {
            mGameDetailData = getArguments().getParcelable(GameDetailActivity.GAMEDETAIL);
        }

        if (mGameDetailData == null) {
            return;
        }
        mPresenter.getGamePackageDatas(mGameDetailData.projectId,"Android","other");
        initRecycleView();
    }

    public void updateGameDetailData(GameDetailModel.GameDetailData gameDetailData){
        mGameDetailData = gameDetailData;
    }

    @Override
    public void NetWorkError() {
        super.NetWorkError();
        if(mRecyclerView != null){
            mRecyclerView.refreshComplete();
        }else {
            mRecyclerView.setNoMore(true);
        }
    }
    @Override
    public void RequestSuccess(BaseModel data){
        if (data.code == 0) {
            if (data instanceof GamePackageModel) {
                GamePackageModel model = (GamePackageModel)data;
                if (model.data != null && model.data.size() > 0) {
                    mGamePackageDatas = model.data;
                    mAdapter.updateGameCusPackageData(mGamePackageDatas);
                    if (mRecyclerView != null) {
                        if (model.data.size() < 25) {
                            mRecyclerView.getDefaultFootView().setNoMoreHint("已加载全部");
                            mRecyclerView.getDefaultFootView().setVisibility(View.VISIBLE);
                        }
                        mRecyclerView.refreshComplete();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mRecyclerView != null){
            mRecyclerView.destroy();
            mRecyclerView = null;
        }
    }

    public class RecycleLoadingListener implements XRecyclerView.LoadingListener {
        @Override
        public void onRefresh() {
            mPresenter.getGamePackageDatas(mGameDetailData.pkey,"Android","other");
            //mPresenter.getGamePackageDatas_dev(mGameDetailData.pkey);
        }

        @Override
        public void onLoadMore() {
            mPresenter.getGamePackageDatas(mGameDetailData.pkey,"Android","other");
            //mPresenter.getGamePackageDatas_dev(mGameDetailData.pkey);
        }

    }


    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        File file;

        public DownloadAPK(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                LogUtil.i(String.valueOf(fileLength));
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/magkare/action.apk";
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(file);                 //打开安装apk文件操作
            progressDialog.dismiss();     //关闭对话框
        }

        private void openFile(File file) {
            if (file!=null){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }

    }
}
