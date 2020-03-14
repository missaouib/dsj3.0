package com.hanqian.kepler.web.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.*;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.bean.result.AjaxResult;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.common.enums.DictEnum;
import com.hanqian.kepler.common.jpa.specification.Rule;
import com.hanqian.kepler.common.jpa.specification.SpecificationFactory;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.entity.primary.sys.Department;
import com.hanqian.kepler.core.service.flow.ProcessBriefService;
import com.hanqian.kepler.core.service.flow.ProcessStepService;
import com.hanqian.kepler.core.service.question.QuestionService;
import com.hanqian.kepler.core.service.sys.DepartmentService;
import com.hanqian.kepler.core.service.sys.UserService;
import com.hanqian.kepler.flow.entity.ProcessStep;
import com.hanqian.kepler.flow.entity.User;
import com.hanqian.kepler.flow.utils.FlowUtil;
import com.hanqian.kepler.flow.vo.FlowTaskEntity;
import com.hanqian.kepler.flow.vo.ProcessLogVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * newFile
 * ============================================================================
 * author : dzw
 * createDate:  2020/1/7 。
 * ============================================================================
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KeplerTest {

	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProcessBriefService processBriefService;
	@Autowired
	private ProcessStepService processStepService;
	@Autowired
	private QuestionService questionService;

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void myTest1(){
//		User user = userService.getUserByUsernameOrPhoneOrEmailEquals("xiaoming");
//		System.out.println(user.getName());

//		User user = userService.getOne("8a8a8aa56f7eefb4016f7eefc2e40000");
//		System.out.println("---------------------");
//		System.out.println(user);

		System.out.println("00000000000000000000");
		System.out.println(departmentService.isParentDepartment(null));

	}

	/**
	 * 批量随机插入调查表数据
	 */
	@Test
	public void testInsertQuestion(){
		String[] hospitalNameArr = new String[]{"JZ","HD","SY","DYRM","YY","LH","XK","XH","LY"};
		Integer[] ageArr = new Integer[]{1, 2, 4, 6};
		List<String> objectTypeNameList = EnumUtil.getNames(BaseEnumManager.ObjectTypeEnum.class);
		List<String> sexNameList = EnumUtil.getNames(BaseEnumManager.SexEnum.class);
		for(int i=0; i<300; i++){
			Question question = new Question();
			question.setHospitalName(hospitalNameArr[RandomUtil.randomInt(0, hospitalNameArr.length)]);
			question.setObjectType(BaseEnumManager.ObjectTypeEnum.valueOf(objectTypeNameList.get(RandomUtil.randomInt(0, objectTypeNameList.size()))));
			question.setSex(BaseEnumManager.SexEnum.valueOf(sexNameList.get(RandomUtil.randomInt(0, sexNameList.size()))));
			question.setAgeField(ageArr[RandomUtil.randomInt(0, ageArr.length-1)]);
			question.setQualityIndoor(RandomUtil.randomInt(1,7));
			question.setQualityOutdoor(RandomUtil.randomInt(1,7));
			question.setToiletHygiene(RandomUtil.randomInt(1,7));
			question.setCleanService(RandomUtil.randomInt(1,7));
			question.setDailySecurity(RandomUtil.randomInt(1,7));
			question.setAccidentalDisposal(RandomUtil.randomInt(1,7));
			question.setSecurityService(RandomUtil.randomInt(1,7));
			question.setDishPrice(RandomUtil.randomInt(1,7));
			question.setDiningEnvironment(RandomUtil.randomInt(1,7));
			question.setFoodService(RandomUtil.randomInt(1,6));
			question.setDeliveryTimeliness(RandomUtil.randomInt(1,7));
			question.setFoodNutrition(RandomUtil.randomInt(1,7));
			question.setDiningAttitude(RandomUtil.randomInt(1,7));
			question.setTransportTimeliness(RandomUtil.randomInt(1,7));
			question.setTransportAccuracy(RandomUtil.randomInt(1,7));
			question.setTransportService(RandomUtil.randomInt(1,7));
			question.setRepairTimeliness(RandomUtil.randomInt(1,7));
			question.setRepairQuality(RandomUtil.randomInt(1,7));
			question.setElevatorStatus(RandomUtil.randomInt(1,7));
			question.setOperationService(RandomUtil.randomInt(1,7));

			question.setObjectTypeSort(question.getObjectType().key());
			questionService.save(question);
		}
	}

}
