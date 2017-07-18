import cn.net.ibingo.common.utils.HttpRespons;
import cn.net.ibingo.common.utils.HttpUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by lqj on 2017-07-12.
 */
public class testTest {
    private static HttpUtil httpUtil = new HttpUtil();
    @Test
    public void main() throws Exception {
    }

    @Test
    public void testVoluum() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("trafficSourceId","08a3f13e-4806-4818-9a99-d99fb154c2ab");
        params.put("offerId","90d766e8-a3cc-4d40-a913-f65790c04082");
        params.put("affiliateNetworkId","35d41ce1-04c4-4a24-807f-74e705e3d631");
        params.put("campaignId","5df5161a-eb4b-47af-8f42-a4102a22be5d");

        params.put("payout",0.5F);
        params.put("country","IR");
        for(int i = 0;i<50;i++){
            params.put("clickId","w0PP8A8ABO1ARDA6H71LDKG"+i);
            HttpRespons sr = httpUtil.sendPost("http://localhost:9090/advert_new_manager/voluum/notify", params, null);
            if(sr.getCode()==200){
                System.out.println("-----------------------HttpRespons-------------------"+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
            }

        }
    }



    @Test
    public void downloadFile1() throws Exception {
    }

}