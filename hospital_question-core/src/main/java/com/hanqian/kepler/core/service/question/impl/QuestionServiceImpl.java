package com.hanqian.kepler.core.service.question.impl;

import com.hanqian.kepler.common.base.dao.BaseDao;
import com.hanqian.kepler.common.base.service.BaseServiceImpl;
import com.hanqian.kepler.core.dao.primary.question.QuestionDao;
import com.hanqian.kepler.core.entity.primary.question.Question;
import com.hanqian.kepler.core.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, String> implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public BaseDao<Question, String> getBaseDao() {
        return questionDao;
    }
}
