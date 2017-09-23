package text.lxy.com.scrolltext;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxy on 2017/9/23.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {

    private List<NewsBean> mList=new ArrayList<>();

    public void addItem(NewsBean bean){
        mList.add(bean);
        notifyDataSetChanged();
    }

    public NewsAdapter(@LayoutRes int layoutResId, @Nullable List<NewsBean> data) {
        super(layoutResId, data);
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsBean item) {
        holder.setText(R.id.tv_msg,item.title);
    }
}
