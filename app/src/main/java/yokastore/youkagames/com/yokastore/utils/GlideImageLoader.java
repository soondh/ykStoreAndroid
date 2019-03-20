package yokastore.youkagames.com.yokastore.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.support.image.MyImageLoaderHelper;

/**
 * Created by songdehua on 2018/5/28.
 * Glide加载图片方式
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        MyImageLoaderHelper.Builder.create(activity)
                .load(path)
                .placeHolder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .target(imageView)
                .execute();
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height){
        MyImageLoaderHelper.Builder.create(activity)
                .load(path)
                .placeHolder(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .target(imageView)
                .execute();
    }

    @Override
    public void clearMemoryCache() {
        //清除缓存
    }

}
