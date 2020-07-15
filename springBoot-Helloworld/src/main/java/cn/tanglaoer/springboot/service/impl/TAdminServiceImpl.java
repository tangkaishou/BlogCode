package cn.tanglaoer.springboot.service.impl;

import cn.tanglaoer.springboot.bean.TAdmin;
import cn.tanglaoer.springboot.mapper.TAdminMapper;
import cn.tanglaoer.springboot.service.TAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) // 作用在这个类
public class TAdminServiceImpl implements TAdminService {
    @Autowired
    TAdminMapper adminMapper;

    //@Transactional(readOnly = true) 作用在当个函数
    @Override
    public TAdmin getTAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
