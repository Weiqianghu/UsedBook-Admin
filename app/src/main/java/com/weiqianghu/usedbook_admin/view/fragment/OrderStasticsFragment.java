package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.usedbook_admin.weiqianghu.usedbook_admin.R;
import com.weiqianghu.usedbook_admin.model.eneity.AddressBean;
import com.weiqianghu.usedbook_admin.model.eneity.OrderBean;
import com.weiqianghu.usedbook_admin.model.eneity.ShopBean;
import com.weiqianghu.usedbook_admin.presenter.QueryOrderPresenter;
import com.weiqianghu.usedbook_admin.util.AuditUtil;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.OrderStateUtil;
import com.weiqianghu.usedbook_admin.util.ThreadPool;
import com.weiqianghu.usedbook_admin.util.TimeUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderStasticsFragment extends BaseFragment {
    public static final String TAG = OrderStasticsFragment.class.getSimpleName();
    private Context mContext;
    private boolean first = true;

    private QueryOrderPresenter mQueryOrderPresenter;

    private BarChart mOrderMonthChart;
    private List<BarEntry> mOrderMonthCount = new ArrayList<>(12);
    List<String> mOrderMonth = new ArrayList<>(12);

    private PieChart mOrderStateChart;
    private List<Entry> mOrderStateCount = new ArrayList<>(5);
    List<String> mOrderState = new ArrayList<>(5);

    private HorizontalBarChart mOrderAddressChart;
    private List<BarEntry> mOrderAddressCount = new ArrayList<>(32);
    List<String> mOrderAddress = new ArrayList<>(32);

    private PieChart mOrderPriceChart;
    private List<Entry> mOrderPriceCount = new ArrayList<>(5);
    private List<String> mOrderPrice = new ArrayList<>(5);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_stastics;
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
        mQueryOrderPresenter.queryOrder(mContext);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mQueryOrderPresenter = new QueryOrderPresenter(queryOrderHandler);

        mOrderMonthChart = (BarChart) mRootView.findViewById(R.id.chart_order_month);
        mOrderMonthChart.setDescription("按月份统计销量");
        initOrderMonth();
        XAxis xAxis1 = mOrderMonthChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);

        mOrderStateChart = (PieChart) mRootView.findViewById(R.id.chart_order_state);
        mOrderStateChart.setDescription("订单状态统计");

        mOrderAddressChart = (HorizontalBarChart) mRootView.findViewById(R.id.chart_order_address);
        mOrderAddressChart.setDescription("订单收货地址统计");

        mOrderPriceChart = (PieChart) mRootView.findViewById(R.id.chart_order_price);
        mOrderPriceChart.setDescription("订单总价统计");
        initOrderPrice();
    }

    private void initOrderPrice() {
        mOrderPrice.add("0~30￥");
        mOrderPrice.add("31~50￥");
        mOrderPrice.add("50~80￥");
        mOrderPrice.add("80~200￥");
        mOrderPrice.add("200~￥");
    }

    private void initOrderMonth() {
        mOrderMonth.add("一月份");
        mOrderMonth.add("二月份");
        mOrderMonth.add("三月份");
        mOrderMonth.add("四月份");
        mOrderMonth.add("五月份");
        mOrderMonth.add("六月份");
        mOrderMonth.add("七月份");
        mOrderMonth.add("八月份");
        mOrderMonth.add("九月份");
        mOrderMonth.add("十月份");
        mOrderMonth.add("十一月份");
        mOrderMonth.add("十二月份");
    }

    CallBackHandler queryOrderHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    final Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);

                    new AsyncTask<List<OrderBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<OrderBean>... params) {
                            List<Integer> list = new ArrayList<Integer>();
                            for (int i = 0; i < 12; i++) {
                                list.add(0);
                            }
                            List<OrderBean> orders = params[0];

                            for (OrderBean order : orders) {
                                int month = TimeUtil.getMonth(order.getCreatedAt());
                                if (month != -1) {
                                    list.set(month - 1, list.get(month - 1) + 1);
                                }
                            }

                            return list;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> list) {
                            super.onPostExecute(list);

                            for (int i = 0, length = list.size(); i < length; i++) {
                                BarEntry barEntry = new BarEntry(list.get(i), i);
                                mOrderMonthCount.add(barEntry);
                            }

                            BarDataSet set = new BarDataSet(mOrderMonthCount, mContext.getResources().getString(R.string.shop_province_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                            dataSets.add(set);

                            BarData data = new BarData(mOrderMonth, set);
                            mOrderMonthChart.setData(data);
                            mOrderMonthChart.invalidate();

                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<OrderBean>, Void, Map<String, Integer>>() {

                        @Override
                        protected Map<String, Integer> doInBackground(List<OrderBean>... params) {
                            Map<String, Integer> map = new HashMap<String, Integer>();
                            List<OrderBean> orders = params[0];

                            for (OrderBean order : orders) {
                                if (map.get(order.getOrderState()) == null) {
                                    map.put(order.getOrderState(), 1);
                                } else {
                                    map.put(order.getOrderState(), map.get(order.getOrderState()) + 1);
                                }
                            }
                            return map;
                        }

                        @Override
                        protected void onPostExecute(Map<String, Integer> map) {
                            super.onPostExecute(map);

                            int i = 0;
                            for (Map.Entry<String, Integer> mapEntry : map.entrySet()) {
                                Entry entry = new Entry(mapEntry.getValue(), i++);
                                mOrderStateCount.add(entry);
                                mOrderState.add(OrderStateUtil.getStrByOrderState(mapEntry.getKey()));
                            }

                            PieDataSet set = new PieDataSet(mOrderStateCount, "订单状态");
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.lightGray, R.color.mainColor, R.color.cool_blank, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mOrderState, set);
                            mOrderStateChart.setData(data);
                            mOrderStateChart.invalidate();

                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<OrderBean>, Void, Map<String, Integer>>() {

                        @Override
                        protected Map<String, Integer> doInBackground(List<OrderBean>... params) {
                            Map<String, Integer> map = new HashMap<String, Integer>();
                            List<OrderBean> Orders = params[0];

                            for (OrderBean order : Orders) {
                                AddressBean address = order.getAddress();
                                if (address == null) {
                                    break;
                                }

                                if (map.get(address.getProvince()) == null) {
                                    map.put(address.getProvince(), 1);
                                } else {
                                    map.put(address.getProvince(), map.get(address.getProvince()) + 1);
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
                                mOrderAddressCount.add(barEntry);
                                mOrderAddress.add(entry.getKey());
                            }

                            BarDataSet set = new BarDataSet(mOrderAddressCount, mContext.getResources().getString(R.string.address_province_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                            dataSets.add(set);

                            BarData data = new BarData(mOrderAddress, set);
                            mOrderAddressChart.setData(data);
                            mOrderAddressChart.invalidate();

                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<OrderBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<OrderBean>... params) {
                            List<Integer> list = new ArrayList<Integer>();
                            for (int i = 0; i < 5; i++) {
                                list.add(0);
                            }
                            List<OrderBean> orders = params[0];

                            for (OrderBean order : orders) {
                                if (order.getTotalPrice() <= 30) {
                                    list.set(0, list.get(0) + 1);
                                } else if (order.getTotalPrice() > 30 && order.getTotalPrice() <= 50) {
                                    list.set(1, list.get(1) + 1);
                                } else if (order.getTotalPrice() > 50 && order.getTotalPrice() <= 80) {
                                    list.set(2, list.get(2) + 1);
                                } else if (order.getTotalPrice() > 80 && order.getTotalPrice() <= 200) {
                                    list.set(3, list.get(3) + 1);
                                } else {
                                    list.set(4, list.get(4) + 1);
                                }
                            }

                            return list;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> list) {
                            super.onPostExecute(list);

                            for (int i = 0, length = list.size(); i < length; i++) {
                                Entry entry = new Entry(list.get(i), i);
                                mOrderPriceCount.add(entry);
                            }

                            PieDataSet set = new PieDataSet(mOrderPriceCount, "订单价格");
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link, R.color.cool_green}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mOrderPrice, set);
                            mOrderPriceChart.setData(data);
                            mOrderPriceChart.invalidate();

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
