package com.weiqianghu.usedbook_admin.view.fragment;


import android.content.Context;
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
import com.weiqianghu.usedbook_admin.model.eneity.UserBean;
import com.weiqianghu.usedbook_admin.presenter.QueryUserPresenter;
import com.weiqianghu.usedbook_admin.util.CallBackHandler;
import com.weiqianghu.usedbook_admin.util.Constant;
import com.weiqianghu.usedbook_admin.util.ThreadPool;
import com.weiqianghu.usedbook_admin.util.TimeUtil;
import com.weiqianghu.usedbook_admin.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserStasticsFragment extends BaseFragment {
    public static final String TAG = UserStasticsFragment.class.getSimpleName();

    private QueryUserPresenter mQueryUserPresenter;

    private PieChart mUserRegisterTimeChart;
    private PieChart mUserRoleChart;
    private HorizontalBarChart mUserAgeChart;
    private PieChart mUserSexChart;

    private Context mContext;

    private List<Entry> mUserRegisterCount = new ArrayList<>(4);
    List<String> mUserRegisterTime = new ArrayList<>(4);

    private List<Entry> mUserRoleCount = new ArrayList<>(2);
    List<String> mUserRole = new ArrayList<>(2);

    private List<Entry> mUserSexCount = new ArrayList<>(4);
    List<String> mUserSex = new ArrayList<>(4);

    private List<BarEntry> mUserAgeCount = new ArrayList<>(2);
    List<String> mUserAge = new ArrayList<>(4);

    private boolean first = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_stastics;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        if (first) {
            initView(savedInstanceState);
            initData();
            first = false;
        }
    }

    private void initData() {
        mQueryUserPresenter.queryUser(mContext);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mContext = getActivity();
        mQueryUserPresenter = new QueryUserPresenter(queryUserHandler);

        mUserRegisterTimeChart = (PieChart) mRootView.findViewById(R.id.chart_user_register_time);
        mUserRegisterTimeChart.setDescription("季度注册用户数");
        mUserRegisterTime.add("第一季度");
        mUserRegisterTime.add("第二季度");
        mUserRegisterTime.add("第三季度");
        mUserRegisterTime.add("第四季度");

        mUserRoleChart = (PieChart) mRootView.findViewById(R.id.chart_user_role);
        mUserRoleChart.setDescription("用户角色");
        mUserRole.add("普通用户");
        mUserRole.add("商铺用户");

        mUserAgeChart = (HorizontalBarChart) mRootView.findViewById(R.id.chart_user_age);
        mUserAgeChart.setDescription("用户年龄分布");
        mUserAge.add("0~18");
        mUserAge.add("19~25");
        mUserAge.add("26~45");
        mUserAge.add("46~");

        mUserSexChart = (PieChart) mRootView.findViewById(R.id.chart_user_sex);
        mUserSexChart.setDescription("用户性别分布");
        mUserSex.add("男");
        mUserSex.add("女");

    }

    CallBackHandler queryUserHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    List list = bundle.getParcelableArrayList(Constant.LIST);

                    new AsyncTask<List<UserBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<UserBean>... params) {
                            List<Integer> count = new ArrayList<Integer>(4);
                            for (int i = 0; i < 4; i++) {
                                count.add(0);
                            }
                            List<UserBean> users = params[0];
                            for (UserBean user : users) {
                                int quarter = TimeUtil.getQuarter(user.getCreatedAt());
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
                                mUserRegisterCount.add(entry);
                            }

                            PieDataSet set = new PieDataSet(mUserRegisterCount, mContext.getResources().getString(R.string.user_register_time_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);


                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mUserRegisterTime, set);
                            mUserRegisterTimeChart.setData(data);
                            mUserRegisterTimeChart.invalidate();
                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<UserBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<UserBean>... params) {
                            List<Integer> count = new ArrayList<Integer>(2);
                            for (int i = 0; i < 2; i++) {
                                count.add(0);
                            }
                            List<UserBean> users = params[0];
                            for (UserBean user : users) {
                                if (!user.isShop()) {
                                    count.set(0, count.get(0) + 1);
                                } else if (user.isShop()) {
                                    count.set(1, count.get(1) + 1);
                                }
                            }
                            return count;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> integers) {
                            super.onPostExecute(integers);
                            for (int i = 0, length = integers.size(); i < length; i++) {
                                Entry entry = new Entry(integers.get(i), i);
                                mUserRoleCount.add(entry);
                            }

                            PieDataSet set = new PieDataSet(mUserRoleCount, mContext.getResources().getString(R.string.user_role_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);


                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mUserRole, set);
                            mUserRoleChart.setData(data);
                            mUserRoleChart.invalidate();
                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<UserBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<UserBean>... params) {
                            List<Integer> count = new ArrayList<Integer>(4);
                            for (int i = 0; i < 4; i++) {
                                count.add(0);
                            }
                            List<UserBean> users = params[0];
                            for (UserBean user : users) {
                                int age = user.getAge();
                                if (age >= 0 && age <= 18) {
                                    count.set(0, count.get(0) + 1);
                                } else if (age >= 19 && age <= 25) {
                                    count.set(1, count.get(1) + 1);
                                } else if (age >= 16 && age <= 45) {
                                    count.set(2, count.get(2) + 1);
                                } else if (age >= 46) {
                                    count.set(3, count.get(3) + 1);
                                }
                            }
                            return count;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> integers) {
                            super.onPostExecute(integers);
                            for (int i = 0, length = integers.size(); i < length; i++) {
                                BarEntry entry = new BarEntry(integers.get(i), i);
                                mUserAgeCount.add(entry);
                            }

                            BarDataSet set = new BarDataSet(mUserAgeCount, mContext.getResources().getString(R.string.user_age_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank, R.color.lightGray, R.color.link}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                            dataSets.add(set);

                            BarData data = new BarData(mUserAge, set);
                            mUserAgeChart.setData(data);
                            mUserAgeChart.invalidate();
                        }
                    }.executeOnExecutor(ThreadPool.getThreadPool(), list);

                    new AsyncTask<List<UserBean>, Void, List<Integer>>() {

                        @Override
                        protected List<Integer> doInBackground(List<UserBean>... params) {
                            List<Integer> count = new ArrayList<Integer>(2);
                            for (int i = 0; i < 2; i++) {
                                count.add(0);
                            }
                            List<UserBean> users = params[0];
                            for (UserBean user : users) {
                                if (user.isSex()) {
                                    count.set(0, count.get(0) + 1);
                                } else if (!user.isSex()) {
                                    count.set(1, count.get(1) + 1);
                                }
                            }
                            return count;
                        }

                        @Override
                        protected void onPostExecute(List<Integer> integers) {
                            super.onPostExecute(integers);
                            for (int i = 0, length = integers.size(); i < length; i++) {
                                Entry entry = new Entry(integers.get(i), i);
                                mUserSexCount.add(entry);
                            }

                            PieDataSet set = new PieDataSet(mUserSexCount, mContext.getResources().getString(R.string.user_sex_statistics));
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);


                            ArrayList<Integer> colors = new ArrayList<Integer>();

                            set.setColors(new int[]{R.color.mainColor, R.color.cool_blank}, mContext);
                            set.setValueTextColor(mContext.getResources().getColor(R.color.white));

                            PieData data = new PieData(mUserSex, set);
                            mUserSexChart.setData(data);
                            mUserSexChart.invalidate();
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
