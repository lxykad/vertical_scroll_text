package text.lxy.com.scrolltext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper mFlipper;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private List<NewsBean> mList;
    LinkedBlockingDeque<NewsBean> mQueue = new LinkedBlockingDeque<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);

        tv1.setTextSize(25);
        tv2.setTextSize(25);
        tv3.setTextSize(25);

        tv1.setText("aaa");
        tv2.setText("bbb");
        tv3.setText("ccc");

        mFlipper.addView(tv1);
        mFlipper.addView(tv2);
        mFlipper.addView(tv3);

        mFlipper.setInAnimation(this, R.anim.headline_in);
        mFlipper.setOutAnimation(this, R.anim.headline_out);
        mFlipper.setFlipInterval(2000);
        mFlipper.startFlipping();


        //recyclerview
        mList = new ArrayList<>();
        //mList.add(new NewsBean("11111"));
        // mList.add(new NewsBean("22222"));
        // mList.add(new NewsBean("33333"));
        mAdapter = new NewsAdapter(R.layout.list_item_news, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        startLoop();
    }

    //
    int count = 222;

    public void add(View view) {

        NewsBean bean = new NewsBean(count + "");
        count++;
        try {
            mQueue.put(bean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //3秒轮询一次消息队列
    public void startLoop() {
        Observable.interval(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("loop==========:" + mQueue.size());
                        showMsg();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void showMsg() {
        if (mQueue.size() == 0) {
            return;
        }

        try {
            NewsBean bean = mQueue.take();

            if (mList.size() > 2) {
                mAdapter.addData(bean);
                mAdapter.remove(0);

            } else {
                mAdapter.addData(bean);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
