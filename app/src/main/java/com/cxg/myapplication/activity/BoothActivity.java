package com.cxg.myapplication.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cxg.myapplication.R;
import com.cxg.myapplication.pojo.Ztwm004;
import com.cxg.myapplication.utils.Bluetooth;
import com.cxg.myapplication.utils.ExitApplication;
import com.cxg.myapplication.utils.MessageBox;
import com.cxg.myapplication.utils.StatusBox;
import com.cxg.myapplication.utils.lable_sdk;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 蓝牙打印首页
 */
public class BoothActivity extends AppCompatActivity {

    /*蓝牙适配器*/
    public static BluetoothAdapter myBluetoothAdapter;
    /*远程连接地址*/
    public String SelectedBDAddress;
    /*打印机盒子状态*/
    public StatusBox statusBox;
    /*盒子信息*/
    public MessageBox megBox;

    private Ztwm004 ztwm004;
    public static String ErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booth);

        Button Button1 = (Button) findViewById(R.id.button1);
        statusBox = new StatusBox(this, Button1);
        megBox = new MessageBox(this);
        SelectedBDAddress = "";

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        ztwm004 = dataFmort(bundle);

        /*判断设备是否支持蓝牙设备*/
        boolean bluetoothDevice = ListBluetoothDevice();
        if (!bluetoothDevice) {
            String mags = "与蓝牙设备匹配有问题，请检查后重试!";
            showMessage(mags);
            finish();//用于结束一个Activity的生命周期
        }

        /*循环多张打印*/
        Button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                //Print1(SelectedBDAddress, ztwm004);
                Print1_1(SelectedBDAddress, ztwm004);
            }
        });

        /*返回*/
        Button Button3 = (Button) findViewById(R.id.button3);
        Button3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                Intent intent = new Intent(BoothActivity.this, BlueBoothPinterActivity.class);

                //用Bundle携带数据
                Bundle ztwm004_001 = new Bundle();

                ztwm004_001.putString("IZipcode", ztwm004.getZipcodelist().get(0) + ", ...");
                ztwm004_001.putString("Zcupno",ztwm004.getZcupno());
                ztwm004_001.putString("Charg", ztwm004.getCharg());
                ztwm004_001.putString("Zgrdate", ztwm004.getZgrdate());
                ztwm004_001.putString("Werks", ztwm004.getWerks());
                ztwm004_001.putString("Zkurno", ztwm004.getZkurno());
                ztwm004_001.putString("Zbc", ztwm004.getZbc());
                ztwm004_001.putString("Zlinecode", ztwm004.getZlinecode());
                ztwm004_001.putString("Matnr", ztwm004.getMatnr());
                ztwm004_001.putString("Menge", ztwm004.getMenge());
                ztwm004_001.putString("Meins", ztwm004.getMeins());
                ztwm004_001.putString("EMaktx", ztwm004.getEMaktx());
                ztwm004_001.putString("EName1", ztwm004.getEName1());
                ztwm004_001.putString("Zcupno", ztwm004.getZcupno());

                intent.putExtras(ztwm004_001);

                //进入到下一个Activity
                startActivity(intent);
                //Activity过场动画
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

                Toast.makeText(getApplicationContext(), "返回至打印数据预览", Toast.LENGTH_SHORT).show();
            }
        });

        ExitApplication.getInstance().addActivity(this);
    }

    /**
     * 将传输过来的数据封装成对象
     *
     * @param bundle 数据
     * @return ztwm004
     */
    private Ztwm004 dataFmort(Bundle bundle) {
        List<String> localList1 = extractMessageByRegular(bundle.getString("zipcodeList"));
        List<String> localArrayList1 = new ArrayList<>();
        String[] arrayOfString1 = new String[0];
        for (int i = 0; ; i++) {
            int j = localList1.size();
            if (i >= j) {
                break;
            }
            arrayOfString1 = (localList1.get(i)).split(", ");
        }
        int k = arrayOfString1.length;
        for (int m = 0; m < k; m++) {
            localArrayList1.add(arrayOfString1[m]);
        }
        String Zcupno = bundle.getString("Zcupno");
        String Charg = bundle.getString("Charg");
        String Werks = bundle.getString("Werks");
        String Zkurno = bundle.getString("Zkurno");
        String Zbc = bundle.getString("Zbc");
        String Zlinecode = bundle.getString("Zlinecode");
        String Matnr = bundle.getString("Matnr");
        String Menge = bundle.getString("Menge");
        String Meins = bundle.getString("Meins");
        String Zproddate = bundle.getString("Zproddate");//生产日期
        String Zgrdate = bundle.getString("Zgrdate");//入库时间
        String EMaktx = bundle.getString("EMaktx");
        String EName1 = bundle.getString("EName1");

        ztwm004 = new Ztwm004();
        ztwm004.setZipcodelist(localArrayList1);
        ztwm004.setWerks(Werks);
        ztwm004.setZkurno(Zkurno);
        ztwm004.setZbc(Zbc);
        ztwm004.setZlinecode(Zlinecode);
        ztwm004.setMatnr(Matnr);
        ztwm004.setMenge(Menge);
        ztwm004.setMeins(Meins);
        ztwm004.setCharg(Charg);
        ztwm004.setZcupno(Zcupno);
        ztwm004.setZproddate(Zproddate);
        ztwm004.setZgrdate(Zgrdate);
        ztwm004.setEMaktx(EMaktx);
        ztwm004.setEName1(EName1);

        return ztwm004;
    }

    /**
     * description:获取上一个Activity的list绑定值
     * author: xg.chen
     * date: 2017/8/3 13:21
     * version: 1.0
     */
    public static List<String> extractMessageByRegular(String paramString) {
        ArrayList localArrayList = new ArrayList();
        Matcher localMatcher = Pattern.compile("(\\[[^\\]]*\\])").matcher(paramString);
        while (localMatcher.find()) {
            localArrayList.add(localMatcher.group().substring(1, -1 + localMatcher.group().length()));
        }
        return localArrayList;
    }

    /**
     * 新版的循环打印张打印
     * @param BDAddress
     * @param ztwm004
     */
    public void Print1_1(String BDAddress,Ztwm004 ztwm004) {
        statusBox.Show("正在打印...");
        if (!Bluetooth.OpenPrinter(BDAddress)) {
            Toast.makeText(this, Bluetooth.ErrorMessage, Toast.LENGTH_LONG).show();
            statusBox.Close();
            return;
        }
        List localList = ztwm004.getZipcodelist();
        for (int i = 0; i < localList.size(); i++) {
            String str = (String) localList.get(i);
            DrawContent_1(ztwm004,str);
        }
        Bluetooth.close();
    }

    /**
     * 打印数据输出时间设置
     *
     * @param timeout
     * @return
     */
    public static int zp_realtime_status(int timeout) {
        byte[] status = new byte[8];
        byte[] buf = new byte[11];
        buf[0] = 0x1f;
        buf[1] = 0x00;
        buf[2] = 0x06;
        buf[3] = 0x00;
        buf[4] = 0x07;
        buf[5] = 0x14;
        buf[6] = 0x18;
        buf[7] = 0x23;
        buf[8] = 0x25;
        buf[9] = 0x32;
        buf[10] = 0x00;
        Bluetooth.SPPWrite(buf, 10);
        if (Bluetooth.SPPReadTimeout(status, 1, timeout) == false) {
            return -1;
        }
        return status[0];
    }

    /**
     * 新版本页面布局
     *
     * @param ztwm004
     */
    public void DrawContent_1(Ztwm004 ztwm004,String zipcode){
        try {
            Bluetooth.SPPWrite(new byte[]{0x1B, 0x040}); // 打印机复位
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1B, 0x33, 0x00}); // 设置行间距为0
            zp_realtime_status(1000);
            Bluetooth.SPPWrite("\n".getBytes("GBK"));
            zp_realtime_status(1000);
            //加粗变大内容
            Bluetooth.SPPWrite(new byte[]{0x1B, 0x61, 0x00}); // 设置不居中
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1d, 0x21, 0x01}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1D, 0x21, 0x11 }); // 3倍字体大小
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x45, 0x01 }); // 粗体
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format("客户:" + ztwm004.getZkurno()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            if (StringUtils.isNotEmpty(ztwm004.getEName1())) {
                Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
                zp_realtime_status(100);
                Bluetooth.SPPWrite(String.format(ztwm004.getEName1()+"\n").getBytes("GBK"));
                zp_realtime_status(1000);
            } else {
                Bluetooth.SPPWrite(String.format("         \n").getBytes("GBK"));
                zp_realtime_status(1000);
            }
            Bluetooth.SPPWrite(new byte[]{0x1B, 0x61, 0x00}); // 设置不居中
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1d, 0x21, 0x11}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format(ztwm004.getEMaktx()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1B, 0x61, 0x00}); // 设置不居中
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1D, 0x21, 0x11 }); // 3倍字体大小
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format("生产日期:" + ztwm004.getZgrdate()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            //不需要变更字符内容
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x21, 0x00 }); // 还原默认字体大小，取消下划线，取消粗体模式
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1D, 0x21, 0x01}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format("批次编号:" + ztwm004.getZcupno()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1D, 0x21, 0x01}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format("ERP批次号:" + ztwm004.getCharg()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1D, 0x21, 0x01}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1B, 0x24, 0x28, (byte) 0x80 }); // 设置绝对打印位置5mm，80表示00
            zp_realtime_status(100);
            Bluetooth.SPPWrite(String.format("数量:" + ztwm004.getMenge() + " " + ztwm004.getMeins()+"\n").getBytes("GBK"));
            zp_realtime_status(1000);
            // 打印code128条码
            Bluetooth.SPPWrite(new byte[]{0x1b, 0x61, 0x01}); // 设置居中
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[]{0x1d, 0x21, 0x01}); // 设置倍高
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1d, 0x48, 0x02 }); // 设置条码内容打印在条码下方
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1d, 0x77, 0x03 }); // 设置条码宽度0.375
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1d, 0x68, 0x79 }); // 设置条码高度64
            zp_realtime_status(1000);
            Bluetooth.SPPWrite(new byte[] { 0x1D, 0x6B, 0x08 });
            Bluetooth.SPPWrite(String.format(zipcode+"\0").getBytes("GBK"));
            zp_realtime_status(1000);

            Bluetooth.SPPWrite("\n\n\n\n".getBytes("GBK"));
            if (zp_realtime_status(5000) > 0) {
                showMessage(ErrorMessage);
            }
            statusBox.Close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 页面布局
     *
     * @param ztwm004 打印对象
     */
    private void DrawContent(Ztwm004 ztwm004) {
        try {
            lable_sdk.DrawText(1 * 8, 8 * 8, "客户:" + ztwm004.getZkurno(), 0x02, 2);
            zp_realtime_status(1000);
            if (StringUtils.isNotEmpty(ztwm004.getEName1())) {
                lable_sdk.DrawText(1 * 8, 12 * 8, ztwm004.getEName1(), 0x02, 3);
                zp_realtime_status(1000);
            } else {
                lable_sdk.DrawText(1 * 8, 12 * 8, "        ", 0x02, 3);
                zp_realtime_status(1000);
            }
            lable_sdk.DrawText(1 * 8, 16 * 8, ztwm004.getEMaktx(), 0x02, 3);
            zp_realtime_status(1000);
            lable_sdk.DrawText(1 * 8, 20 * 8, "生产日期:" + ztwm004.getZgrdate(), 0, 2);
            zp_realtime_status(1000);
            lable_sdk.DrawText(1 * 8, 24 * 8, "批次编号:" + ztwm004.getZcupno(), 0, 2);
            zp_realtime_status(1000);
            lable_sdk.DrawText(1 * 8, 28 * 8, "ERP批次号:" + ztwm004.getCharg(), 0, 2);
            zp_realtime_status(1000);
            lable_sdk.DrawText(1 * 8, 32 * 8, "数量:" + ztwm004.getMenge() + " " + ztwm004.getMeins(), 0, 2);
            zp_realtime_status(1000);
            lable_sdk.DrawCode1D(12 * 8, 36 * 8, ztwm004.getZipcode(), 0x1, 0x03, (18 * 8));
            zp_realtime_status(1000);
            lable_sdk.DrawText(20 * 8, 56 * 8, ztwm004.getZipcode(), 0, 0);
            zp_realtime_status(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }// DrawContent

    /**
     * 远程连接设备的蓝牙列表
     *
     * @return
     */
    public boolean ListBluetoothDevice() {
        final List<Map<String, String>> list = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.listView1);
        SimpleAdapter m_adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"DeviceName", "BDAddress"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(m_adapter);

        if ((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()) == null) {
            Toast.makeText(this, "没有找到蓝牙适配器", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
        }

        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() <= 0) {
            return false;
        }
        for (BluetoothDevice device : pairedDevices) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("DeviceName", device.getName());
            map.put("BDAddress", device.getAddress());
            list.add(map);
        }
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedBDAddress = list.get(position).get("BDAddress");
                if (((ListView) parent).getTag() != null) {
                    ((View) ((ListView) parent).getTag()).setBackgroundDrawable(null);
                }
                ((ListView) parent).setTag(view);
                view.setBackgroundColor(Color.YELLOW);
            }
        });
        return true;
    }// ListBluetoothDevice

    /**
     * 输出信息
     *
     * @param str
     */
    public void showMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
