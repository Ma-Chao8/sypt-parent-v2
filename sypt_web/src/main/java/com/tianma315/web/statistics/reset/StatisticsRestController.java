package com.tianma315.web.statistics.reset;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.series.Series;
import com.tianma315.core.coderecord.service.ScanRecordService;
import com.tianma315.core.base.Result;
import com.tianma315.core.wxuserinfo.service.WxUserInfoService;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * 统计数据接口
 * <p>
 * Description
 * <p>
 * Created by zcm on 2019/7/2.
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsRestController extends BaseController {

    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private ScanRecordService scanRecordService;


    /**
     * 微信用户列表
     *
     * @param pageNumber
     * @param pageSize
     * @param searchKey
     * @return
     */
    @GetMapping("/wechat")
    Result wechatUsers(int pageNumber, int pageSize, String searchKey) {
        Page page = wxUserInfoService.getPage(getUserId(), pageNumber, pageSize, searchKey);
        return success(page);
    }


    /**
     * 各地区用户分布数据
     *
     * @return
     */
    @GetMapping("/user/scan/year")
    Result userScanYear(int year) {
        Map<String, String> map = scanRecordService.getCountByYear(year, getUserId());
        Option option = new Option();
        option.title(new Title() {{
            text(String.format("近%s年扫码量变化图", year));
        }});

        //
        option.xAxis().add(new CategoryAxis() {{
            data(map.keySet().toArray());
            name("年份");
        }});

        option.yAxis().add(new ValueAxis() {{
            name("扫码量");
        }});

        List<Series> seriesList = new ArrayList<>();
        seriesList.add(new Line() {{
            data(map.values().toArray());
        }});
        option.series(seriesList);
        return success(JSON.toJSONString(option));
    }

    @GetMapping("/user/scan/month")
    Result userScanMonth(String date) {

        Map<String, String> map = scanRecordService.getCountByMonth(date, getUserId());

        Option option = new Option();
        option.title(new Title() {{
            text(String.format("%s年每月扫码量变化图", date));
        }});

        //
        option.xAxis().add(new CategoryAxis() {{
            data(map.keySet().toArray());
            name("月份");
        }});

        option.yAxis().add(new ValueAxis() {{
            name("扫码量");
        }});

        List<Series> seriesList = new ArrayList<>();
        seriesList.add(new Line() {{
            data(map.values().toArray());
        }});
        option.series(seriesList);
        return success(JSON.toJSONString(option));
    }

    @GetMapping("/user/scan/day")
    Result userScanDay(String date) {
        Map<String, String> map = scanRecordService.getCountByDay(date, getUserId());

        Option option = new Option();
        option.title(new Title() {{
            text(String.format("%s月每日扫码量变化图", date));
        }});

        //
        option.xAxis().add(new CategoryAxis() {{
            data(map.keySet().toArray());
            name("日期");
        }});

        option.yAxis().add(new ValueAxis() {{
            name("扫码量");
        }});

        List<Series> seriesList = new ArrayList<>();
        seriesList.add(new Line() {{
            data(map.values().toArray());
        }});
        option.series(seriesList);
        return success(JSON.toJSONString(option));
    }


    /**
     * 用户类型统计
     *
     * @return
     */
    @GetMapping("/user/repurchase")
    Result repurchase() {
        Map<String, Object> map = wxUserInfoService.getRepurchase(getUserId());

        List<Series> linSeries = new ArrayList<>();
        linSeries.add(new Bar() {{
            name("用户数量");
            data(((HashMap) map.get("bar")).values().toArray());
        }});
        Option linChart = new Option()
                .title(new Title() {{
                    text("各类型客户数量");
                    x(X.center);
                }})
                .xAxis(new CategoryAxis() {{
                    data(((HashMap) map.get("bar")).keySet().toArray());
                }})
                .yAxis(new ValueAxis())
                .series(linSeries);


        List<Series> pieSeries = new ArrayList<>();
        pieSeries.add(new Pie() {{
            name("客户类型:");
            radius("55%");
            center(new Object[]{"50%", "50%"});
            data(((List) map.get("pie")).toArray());
        }});
        Option pieChart = new Option()
                .title(new Title() {{
                    text("客户类型占比");
                    x(X.center);
                }})
                .tooltip(new Tooltip(){{
                    trigger(Trigger.item);
                    formatter("{a} <br/>{b} : {c} ({d}%)");
                }})
                .series(pieSeries);


        return success(JSON.toJSONString(new HashMap<String, Object>() {{
            put("barChart", linChart);
            put("pieChart", pieChart);
        }}));
    }


}
