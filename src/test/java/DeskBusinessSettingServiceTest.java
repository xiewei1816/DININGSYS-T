import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by mrren on 2016/11/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
public class DeskBusinessSettingServiceTest {

    @Resource
    private DeskBusinessSettingService deskBusinessSettingService;


    @Test
    public void testCreateFlowNumber(){

        System.out.println("------------"+deskBusinessSettingService.createFlowNumber("","",3, SerialRulEnum.FW));
    }

}
