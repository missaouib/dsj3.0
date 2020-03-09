package com.hanqian.kepler.web.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.common.bean.result.AjaxResult;
import com.hanqian.kepler.common.enums.BaseEnumManager;
import com.hanqian.kepler.common.enums.DictEnum;
import com.hanqian.kepler.common.jpa.specification.Rule;
import com.hanqian.kepler.common.jpa.specification.SpecificationFactory;
import com.hanqian.kepler.core.entity.primary.sys.Department;
import com.hanqian.kepler.core.service.flow.ProcessBriefService;
import com.hanqian.kepler.core.service.flow.ProcessStepService;
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

}
