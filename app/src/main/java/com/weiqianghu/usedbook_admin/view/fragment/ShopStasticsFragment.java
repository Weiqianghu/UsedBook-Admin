package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.presenter.QueryShopPresenter;
import com.weiqianghu.usedbook_admin.util.AuditUtil;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.ThreadPool;
import com.weiqianghu.usedbook_admin.util.TimeUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;
import com.weiqianghu.usedbook_admin.view.view.IQueryView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopStasticsFragment extends BaseFragment implements IQueryView {
    public static final String TAG = ShopStasticsFragment.class.getSimpleName();
    private Context mContext;
    private boolean first = true;

    private QueryShopPresenter mQueryShopPresenter;


    private PieChart mShopRegisterTimeChart;
    private List<Entry> mShopRegisterCount = new ArrayList<>(4);
    List<String> mShopRegisterTime = new ArrayList<>(4);

    private HorizontalBarChart mShopProvinceChart;
    private List<BarEntry> mShopProvinceCount = new ArrayList<>();
    List<String> mShopProvince = new ArrayList<>();

    private PieChart mShopVerifyStateChart;
    private List<Entry> mShopVerifyStateCount = new ArrayList<>(4);
    List<String> mShopVerifyState = new ArrayList<>(4);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_stastics;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mContext = getActivity();
        if (first) {
            initView(savedInstanceState);
            initData();
            first = false;
        }
    }

    private void initData() {
        mQueryShopPresenter.queryShop(mContext);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mQueryShopPresenter = new QueryShopPresenter(this, queryShopHandler);

        mShopRegisterTimeChart = (PieChart) mRootView.findViewById(R.id.chart_shop_register_time);
        mShopRegisterTimeChart.setDescription("季度注册用户数");
        mShopRegisterTime.add("第一季度");
        mShopRegisterTime.add("第二季度");
        mShopRegisterTime.add("第三季度");
        mShopRegisterTime.add("第四季度");

        mShopProvinceChart = (HorizontalBarChart) mRootView.findViewById(R.id.chart_shop_province);
        mShopProvinceChart.setDescription(mContext.getResources().getString(R.string.shop_province_statistics));

        mShopVerifyStateChart = (PieChart) mRootView.findViewById(R.id.chart_shop_verify_state);
        mShopVerifyStateChart.setDescription("商铺审核信息统计");


    }


    CallBackHandler queryShopHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);
                    new AsyncTask<List<ShopBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<ShopBean>... params) {
                            List<Integer> count = new ArrayList<Integer>(4);
                            for (int i = 0; i < 4; i++) {
                                count.add(0);
                            }
                            List<ShopBean> shops = params[0];
                            for (ShopBean shop : shops) {
                                int quarter = TimeUtil.getQuarter(shop.getCreatedAt());
                                if (quarter != -1) {
                                    count.set(quarter - 1, count.get(quarter - 1) + 1);
                                }
                            }

                            return count;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> integers) {
                            super.onPostExecute(integers);
                            for (int i = 0, length = integers.size(); i < length; i++) {
                                Entry entry = new Entry(integers.get(i), i);
                                mShopRegisterCount.add(entry);
                            }

                            PieDataSet set = new PieDataSet(mShopRegisterCount, mContext.getResources().getString(R.string.shop_register_time_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mShopRegisterTime, set);
                            mShopRegisterTimeChart.setData(data);
                            mShopRegisterTimeChart.invalidate();
                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<ShopBean>, Void, Map<String, Integer>>() {

                        @Override
                        protected Map<String, Integer> doInBackground(List<ShopBean>... params) {
                            Map<String, Integer> map = new HashMap<String, Integer>();
                            List<ShopBean> shops = params[0];

                            for (ShopBean shop : shops) {
                                if (map.get(shop.getProvince()) == null) {
                                    map.put(shop.getProvince(), 1);
                                } else {
                                    map.put(shop.getProvince(), map.get(shop.getProvince()) + 1);
                                }
                            }
                            return map;
                        }

                        @Override
                        protected void onPostExecute(Map<String, Integer> map) {
                            super.onPostExecute(map);
                            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
                            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                                @Override
                                public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                                    return lhs.getValue() - rhs.getValue();
                                }
                            });

                            for (int i = 0, length = list.size(); i < length; i++) {
                                Map.Entry<String, Integer> entry = list.get(i);
                                BarEntry barEntry = new BarEntry(entry.getValue(), i);
                                mShopProvinceCount.add(barEntry);
                                mShopProvince.add(entry.getKey());
                            }

                            BarDataSet set = new BarDataSet(mShopProvinceCount, mContext.getResources().getString(R.string.shop_province_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                            dataSets.add(set);

                            BarData data = new BarData(mShopProvince, set);
                            mShopProvinceChart.setData(data);
                            mShopProvinceChart.invalidate();

                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<ShopBean>, Void, Map<Integer, Integer>>() {

                        @Override
                        protected Map<Integer, Integer> doInBackground(List<ShopBean>... params) {
                            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                            List<ShopBean> shops = params[0];

                            for (ShopBean shop : shops) {
                                if (map.get(shop.getVerifyState()) == null) {
                                    map.put(shop.getVerifyState(), 1);
                                } else {
                                    map.put(shop.getVerifyState(), map.get(shop.getVerifyState()) + 1);
                                }
                            }
                            return map;
                        }

                        @Override
                        protected void onPostExecute(Map<Integer, Integer> map) {
                            super.onPostExecute(map);

                            int i = 0;
                            for (Map.Entry<Integer, Integer> mapEntry : map.entrySet()) {
                                Entry entry = new Entry(mapEntry.getValue(), i++);
                                mShopVerifyStateCount.add(entry);
                                mShopVerifyState.add(AuditUtil.getStrByFailureCode(mapEntry.getKey()));
                            }

                            PieDataSet set = new PieDataSet(mShopVerifyStateCount, "商铺审核信息统计");
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.lightGray,R.color.mainColor, R.color.cool_blank,  R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mShopVerifyState, set);
                            mShopVerifyStateChart.setData(data);
                            mShopVerifyStateChart.invalidate();
                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };


}
