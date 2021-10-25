package com.example.httpinfo;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httpinfo.adapter.DiagnoseAdapter;
import com.google.gson.Gson;
import com.yh.network.tools.DiagnoseProxy;
import com.yh.network.tools.ToolsListener;
import com.yh.network.tools.diagnose.DefaultDomainDiagnose;

public class DiagnoseActivity extends AppCompatActivity {

    private ContentLoadingProgressBar clpbLoading;
    private RecyclerView rvResult;
    private volatile int index = 0;
    private DiagnoseAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle(getString(R.string.result_data));
        clpbLoading = findViewById(R.id.result_activity_pb);
        clpbLoading.bringToFront();
        rvResult = findViewById(R.id.result_activity_rv);
        rvResult.setHasFixedSize(true);
        resultAdapter = new DiagnoseAdapter(getApplicationContext(), R.layout.item_activity_result_rv);
        rvResult.setAdapter(resultAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvResult.setLayoutManager(linearLayoutManager);
        clpbLoading.show();
        DiagnoseProxy.Companion.newInstance(this)
                .add(new DefaultDomainDiagnose(this).address("glmh.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("pc-mid-p.productcenterview.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("pc-mid-p.usercenter.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("pc-mid-p.other-view.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("cp-b2byjapp-sh-prod.tob-trading-platform.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("glzx.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("scapi.yonghuivip.com"))
                .add(new DefaultDomainDiagnose(this).address("pc-mid-p.suppliercenter-view.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("glreport.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("pc-mid-p.certcenter-view.apis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("supplier.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("sit.productcenter.sitgw.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("supplier-master.ys.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("glzx-sup-prod.yh-glzx-vss-view.fzapis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("glzx-sup-prod.yh-glzx-food-safety-front-to-h5.fzapis.yonghui.cn"))
                .add(new DefaultDomainDiagnose(this).address("glzxsitserver.yonghui.cn"))
                .load(new ToolsListener<Object>() {
                    @Override
                    public void start(String address) {
                        index++;
                        System.out.println("DiagnoseActivity DiagnoseProxy start address=" + address);
                    }

                    @Override
                    public void end(String address, Object bean) {
                        index--;
                        System.out.println("DiagnoseActivity DiagnoseProxy end address=" + address);
                        resultAdapter.insertData(new ResultBean(address, new Gson().toJson(bean)));
                        if (index <= 0) {
                            if (clpbLoading != null && clpbLoading.isShown()) {
                                clpbLoading.hide();
                            }
                        }
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (index <= 0) {
                return super.onKeyDown(keyCode, event);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.result_wait), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
