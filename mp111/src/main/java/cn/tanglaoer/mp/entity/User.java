package cn.tanglaoer.mp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import sun.jvm.hotspot.oops.Field;

import java.util.Date;

@Data // 自动生成get / set
public class User {
    //@TableId(type = IdType.ID_WORKER)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    /**
     * 自动填写字段
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;  //创建时间在添加数据时候

    /**
     * 自动填写字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //修改时候，修改时间跟着修改

    /**
     * 乐观锁版本号
     */
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;
}
