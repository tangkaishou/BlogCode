package cn.tanglaoer.springboot.mapper;

import cn.tanglaoer.springboot.bean.TAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TAdminMapper {
    @Select("select * from t_admin where id=#{id}")
    TAdmin selectByPrimaryKey(Integer id);
}
