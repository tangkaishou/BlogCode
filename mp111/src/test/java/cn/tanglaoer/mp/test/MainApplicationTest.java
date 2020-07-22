package cn.tanglaoer.mp.test;

import cn.tanglaoer.mp.entity.User;
import cn.tanglaoer.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 4.名称模糊查询
     */
    @Test
    public void testLikeName(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //wrapper.like("name", "j");
        wrapper.select("id", "name", "age"); // 查询指定的列
        wrapper.likeRight("name", "j");
        wrapper.orderByDesc("age");

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 3.年龄在20-40 between and 的范围是 >=  <=
     */
    @Test
    public void testAgeBetweenAnd(){
        QueryWrapper<User> wrapper= new QueryWrapper<>();
        wrapper.between("age", 20, 40);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 2.查询用户名称是jack，并且年龄是33的记录
     */
    @Test
    public void testAgeEq33(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name", "jack");
        userQueryWrapper.eq("age", 33);

        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 1.查询用户年龄大于20岁
     */
    @Test
    public void testSelectConditionAge(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age", 20);
        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 10.逻辑删除
     */
    @Test
    public void testLogicDelete(){
        int result = userMapper.deleteById(1285793291832475649L);
        System.out.println(result);
    }

    /**
     * 9.根据id实现简单删除
     *
     */
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(4L);
        System.out.println(result);
    }

    /**
     * 8.mp实现分页的操作
     */
    @Test
    public void testPageListDemo(){
        Page<User> page= new Page<>(1, 3);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);

        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
    }

    /**
     * 7.mp实现分页操作
     */
    @Test
    public void testPageList(){
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPage(page, null);

        List<User> records = page.getRecords();
        for (User record : records) {
            System.out.println(record);
        }

        long current = page.getCurrent();
        long pages = page.getPages();
        long total = page.getTotal();
        long size = page.getSize();
        boolean b = page.hasPrevious();
        boolean b1 = page.hasNext();

        System.out.println(records);
        System.out.println(current);
        System.out.println(pages);
        System.out.println(total);
        System.out.println(size);
        System.out.println(b);
        System.out.println(b1);

    }

    /**
     * 6.做简单的条件查询
     */
    @Test
    public void testConditionSelect(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "mary");
        map.put("age", 22);

        List<User> users = userMapper.selectByMap(map);
        for (User user : users) {
            System.out.println(user);
        }

    }

    /**
     * 5.根据id实现批量查询
     */
    @Test
    public void testBathIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4));
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 4.测试乐观锁
     */
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById(1285541414498799617L);
        user.setName("令狐冲xxx");

        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    /**
     * 3.新建方式填入id式修改
     */
    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setId(1285542144261545985L);
        user.setName("update岳不群");
        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    //添加操作
    @Test
    public void testAddUser(){
        User user = new User();
        user.setName("岳不群2");
        user.setAge(12);
        user.setEmail("yyy@11.com");

        //自动使用雪花算法生成id
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testGetAllUser(){
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
