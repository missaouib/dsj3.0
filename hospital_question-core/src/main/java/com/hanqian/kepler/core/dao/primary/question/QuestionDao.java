package com.hanqian.kepler.core.dao.primary.question;

import com.hanqian.kepler.common.base.dao.BaseDao;
import com.hanqian.kepler.common.bean.NameValueVo;
import com.hanqian.kepler.core.entity.primary.question.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionDao extends BaseDao<Question, String> {

//    /**
//     * 无条件查询
//     */
//    @Query(value = "SELECT q.:itemName as \"name\",CONCAT(count(*),'') as \"value\" FROM hp_question q where q.state='Enable' GROUP BY q.:itemName", nativeQuery = true)
//    List<NameValueVo> findGroupData(String itemName);

}
