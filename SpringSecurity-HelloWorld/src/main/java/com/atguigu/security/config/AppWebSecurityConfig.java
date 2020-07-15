package com.atguigu.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true) //开启细粒度全局方法级别权限控制功能
@Configuration //声明当前类是一个配置类。相当与XML配置文件作用。
@EnableWebSecurity  //声明式配置，启用SpringSecurity安全机制。
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;//用户详情查询服务组件的接口

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 认证登录
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		//默认认证
//		//super.configure(auth);
//
//		//实验四：自定义认证用户信息 - 基于内存认证方式
//		auth.inMemoryAuthentication()
//                // 授予角色
//			.withUser("zhangsan").password("123456").roles("学徒","大师")
//			.and()
//			.withUser("lisi").password("123123").authorities("罗汉拳","武当长拳");

//		//采用数据库认证方式
//		auth.userDetailsService(userDetailsService);  //默认密码校验，按照明文进行校验。
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder); //自实现的一个密码编码器
//		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());  //高级的密码编码器
	}

	/**
	 * 授权访问及登录跳转控制
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http); //默认权限规则。所有请求都受限制
		
		//实验一：授权首页和静态资源
		//http.authorizeRequests()
		//		.antMatchers("/layui/**","/index.jsp").permitAll() // 只允许静态资源跟首页
		//		.anyRequest().authenticated();


		//实验二：默认及自定义登录页
		//http.formLogin();  //默认登录页。很丑
		//http.formLogin().loginPage("/index.jsp"); //自定义登录页
		http.formLogin().loginPage("/index.jsp")
					.loginProcessingUrl("/index.jsp") // 登录逻辑处理的请求路径
					.usernameParameter("loginacct") // username
					.passwordParameter("userpswd") // password
					.defaultSuccessUrl("/main.html"); // 成功之后跳转

		//实验6，授权访问
		http.authorizeRequests()
		.antMatchers("/layui/**","/index.jsp").permitAll()
		//.antMatchers("/level1/**").hasRole("学徒")  //相当于调用hasAuthority("ROLE_学徒")
		//.antMatchers("/level2/**").hasRole("大师")
		//.antMatchers("/level3/**").hasRole("宗师")
		//.antMatchers("/level3/**").hasAuthority("葵花宝典")
		.anyRequest().authenticated(); //通过认证的都可以访问



		//http.logout(); //默认注销请求   请求路径："/logout"
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/index.jsp");

		//禁用CSRF 跨站请求攻击
		//http.csrf().disable();

		//访问拒绝的处理,处理403
		//无权访问的时候，跳转到这个页面
		http.exceptionHandling().accessDeniedPage("/unauth.html");
		
		//开启记住我功能。
		//http.rememberMe();  //基于Cookie的方式实现记住我功能->内存版

		//JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();

		JdbcTokenRepositoryImpl ptr = new JdbcTokenRepositoryImpl();
		ptr.setDataSource(dataSource);
		http.rememberMe().tokenRepository(ptr);
		
	}

	//MD5+盐+随机数
	//$2a$10$.gt2E3i5WHU6XrDQ/tbJ2uhLtutfCpNMX1I.CH8LSiKjgSc41o1hy
	//$2a$10$6e8Tv0Z/kp.xDMJirgm1jepkllup0z7Fet8XVGMV/ZqXUfTCN2XR.
	//$2a$10$sJrXoUlOp3es.IAFH6YPGuQVwD74iot79T4rc8AhkW7THJFEPr226
	//public static void main(String[] args) {
	//	BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
	//	String encode = bcpe.encode("123456");
	//	System.out.println(encode);
	//}
}
