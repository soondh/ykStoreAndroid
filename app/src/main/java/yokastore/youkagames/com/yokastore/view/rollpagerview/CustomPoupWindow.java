package yokastore.youkagames.com.yokastore.view.rollpagerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import yokastore.youkagames.com.yokastore.R;
import yokastore.youkagames.com.yokastore.utils.CommonUtil;

/**
 * Created by liyun on 2018/2/7.
 */

public class CustomPoupWindow {
    private static CustomPoupWindow instance;
    private PopupWindow popupWindow;
    public static CustomPoupWindow getInstance(){
        if(instance == null){
            synchronized (CustomPoupWindow.class) {
                instance = new CustomPoupWindow();
            }

        }
        return instance;
    }
    public void showPopWindow(Context context,View view, View defview,int distancex,int distancey){
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnim);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(defview, -CommonUtil.dip2px(context,distancex), -CommonUtil.dip2px(context,distancey));
    }
    public void hidePopWindow(){
        if(popupWindow != null){
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}
