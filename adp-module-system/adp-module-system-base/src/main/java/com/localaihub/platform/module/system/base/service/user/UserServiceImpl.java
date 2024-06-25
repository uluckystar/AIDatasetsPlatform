package com.localaihub.platform.module.system.base.service.user;

// 导入需要的库和框架

import com.alibaba.fastjson2.JSONObject;
import com.localaihub.platform.framework.common.develop.redis.JedisPool2;
import com.localaihub.platform.module.system.base.convert.DtoConverter;
import com.localaihub.platform.module.system.base.dao.user.RoleRepository;
import com.localaihub.platform.module.system.base.dao.user.UserDao;
import com.localaihub.platform.module.system.base.dto.UserDto;
import com.localaihub.platform.module.system.base.entity.user.RoleEntity;
import com.localaihub.platform.module.system.base.entity.user.UserEntity;
import com.localaihub.platform.module.system.base.service.jwt.AuthenticationFacade;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.localaihub.platform.framework.common.config.userRedis.UserRedisConfig11;

/**
 * 实现用户服务接口，提供用户相关的业务逻辑处理。
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 22:36
 */
@Service("UserService") // 声明这是一个服务层组件，Spring容器将其管理
@Transactional // 声明该类中所有的公共方法都被事务管理
public class UserServiceImpl implements UserService, UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class); // 创建日志对象，用于记录日志
    final JedisPool2 userJedis = new JedisPool2(UserRedisConfig11); // 初始化Redis连接池，用于获取Redis连接

    @Resource
    private UserDao userDao; // 通过依赖注入获取UserDao对象，用于数据库操作
    @Resource
    private RoleService roleService;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 注入PasswordEncoder

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public long countUsers() {
        return userDao.countUsers();
    }

    @Override
    public List<Integer> getDailyIncreases(int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<Object[]> result = userDao.getDailyIncreases(startDate);
        return result.stream()
                .map(row -> ((Long) row[1]).intValue())
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        UserEntity user = userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        System.out.println("User loaded: " + user.getUsername());
        user.getRoles().forEach(role -> System.out.println("User Role: " + role.getName()));
        return user;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userDao.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")); // 通过用户名查询用户，如果未找到则抛出异常
                return userEntity;
            }
        };
    }

    /**
     * 注册用户并加密密码
     * @param user
     */
    @Override
    public void registerNewUser(UserEntity user) {
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(roleService.findByName("ROLE_USER").orElseThrow(() -> new IllegalStateException("ROLE_USER role not found")));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll(); // 调用UserDao的findAll方法，获取所有用户列表
    }
    @Override
    public Page<UserDto> findAllByPage(Integer deptId, String username, String phone, Integer status, Pageable pageable) {
        Page<UserEntity> userPage = userDao.findAllByParams(deptId, username, phone, status, pageable);
        List<UserDto> userDtos = userPage.stream()
                .map(DtoConverter::convertUserEntityToUserDto)
                .collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageable, userPage.getTotalElements());
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        Assert.notNull(user, "更新的用户对象不能为null"); // 检查用户对象不为null
        Assert.notNull(user.getId(), "用户ID不能为null"); // 检查用户ID不为null

        // 先根据id查找用户
        UserEntity existingUser = userDao.findById(user.getId()).orElse(null); // 通过ID查找用户，如果未找到则返回null
        if (existingUser != null) {
            // 更新字段
            existingUser.setUsername(user.getUsername()); // 更新用户名
            existingUser.setEmail(user.getEmail()); // 更新电子邮件
            existingUser.setSex(user.getSex()); // 更新电子邮件
            existingUser.setDept(user.getDept()); // 更新电子邮件
            existingUser.setNickname(user.getNickname()); // 更新电子邮件
            existingUser.setPhone(user.getPhone()); // 更新电话号码
            existingUser.setStatus(user.getStatus()); // 更新状态
            existingUser.setRemark(user.getRemark()); // 更新备注
            // 可能还有其他字段更新

            // 保存更新后的用户信息
            return userDao.save(existingUser); // 调用UserDao的save方法，保存用户信息
        }
        return null; // 如果未找到用户，返回null
    }

    @Override
    public UserEntity updateUserStatus(UserEntity user) {
        Assert.notNull(user, "更新的用户对象不能为null"); // 检查用户对象不为null
        Assert.notNull(user.getId(), "用户ID不能为null"); // 检查用户ID不为null

        // 先根据id查找用户
        UserEntity existingUser = userDao.findById(user.getId()).orElse(null); // 通过ID查找用户，如果未找到则返回null
        if (existingUser != null) {
            // 更新字段
            existingUser.setStatus(user.getStatus()); // 更新状态
            return userDao.save(existingUser); // 调用UserDao的save方法，保存用户信息
        }
        return null; // 如果未找到用户，返回null
    }

    @Override
    public long getTotalUsers() {
        return userDao.count(); // 调用UserDao的count方法，获取用户总数
    }

    /**
     * 根据用户ID获取单个用户信息。
     * @param id 用户ID
     * @return 用户信息，如果用户不存在则返回null
     */
    @Override
    public UserEntity getOne(int id) {
        if (userDao.existsById(id)) {
            return userDao.getById(id); // 如果用户存在，通过ID获取用户信息
        }
        return null; // 如果用户不存在，返回null
    }

    /**
     * 根据用户名查找用户信息。
     * @param userName 用户名
     * @return 用户信息，如果用户不存在则抛出异常
     */
    @Override
    public UserEntity findByUsername(String userName) {
        return userDao.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found")); // 通过用户名查找用户，如果未找到则抛出异常
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    /**
     * 根据用户DTO保存用户信息。
     * @param userDto 用户数据传输对象
     * @return 保存后的用户信息
     */
    @Override
    public UserEntity saveUser(UserDto userDto) {

        // 验证必填字段
        validateUserDto(userDto);

        // 验证用户名唯一性
        if (userDao.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 验证邮箱格式
        if (!isValidEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("邮箱格式不正确");
        }

        // 验证密码长度和复杂度
        if (userDto.getPassword().length() < 6) {
            throw new IllegalArgumentException("密码长度至少为6位");
        }

        Set<RoleEntity> roles = new HashSet<>();
        UserEntity currentUser = getCurrentUser();

        if (currentUser.getRoles().contains(roleService.findByName("ROLE_SUPER_ADMIN").orElse(null))) {
            roles.add(roleService.findByName("ROLE_ADMIN").orElseThrow(() -> new IllegalStateException("ROLE_ADMIN role not found")));
        } else if (currentUser.getRoles().contains(roleService.findByName("ROLE_ADMIN").orElse(null))) {
            roles.add(roleService.findByName("ROLE_USER").orElseThrow(() -> new IllegalStateException("ROLE_USER role not found")));
        } else {
            throw new IllegalStateException("你没有权限添加用户");
        }

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .nickname(userDto.getNickname())
                .phone(userDto.getPhone())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(roles)
                .email(userDto.getEmail())
                .status(userDto.getStatus())
                .sex(userDto.getSex())
                .remark(userDto.getRemark())
                .dept(userDto.getDept())
                .createTime(LocalDateTime.now())
                .build();
        return userDao.save(user);

    }

    // 验证必填字段
    private void validateUserDto(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (userDto.getPhone() == null || userDto.getPhone().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }
    }

    // 验证邮箱格式
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return true; // 邮箱非必填，如果为空则不验证格式
        }
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    @Override
    public UserEntity save(UserEntity user) {
        Set<RoleEntity> persistentRoles = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            RoleEntity persistentRole = roleRepository.findByName(role.getName()).orElseThrow(
                    () -> new IllegalArgumentException("Role not found: " + role.getName()));
            persistentRoles.add(persistentRole);
        }
        user.setRoles(persistentRoles);
        return userDao.save(user);
    }

    /**
     * 根据用户实体删除用户信息。
     * @param user 用户实体
     * @return 删除的用户信息
     */
    @Override
    public UserEntity delete(UserEntity user) {
        return delete(user.getId()); // 调用删除方法，通过用户ID执行删除操作
    }

    /**
     * 根据用户ID删除用户信息。
     * @param id 用户ID
     * @return 删除的用户信息，如果用户不存在则返回null
     */
    @Override
    public UserEntity delete(int id) {
        if (userDao.existsById(id)) {
            UserEntity userEntity = userDao.getById(id); // 获取用户信息
            userDao.deleteById(id); // 执行删除操作
            return userEntity; // 返回删除的用户信息
        }
        return null; // 如果用户不存在，返回null
    }

    /**
     * 更新用户信息。
     * @param user 用户实体
     * @return 更新后的用户信息，如果用户不存在则返回null
     */
    @Override
    public UserEntity update(UserEntity user) {
        if (userDao.existsById(user.getId())) {
            return userDao.save(user); // 如果用户存在，保存用户信息
        }
        return null; // 如果用户不存在，返回null
    }

    @Override
    public UserEntity getUserByToken(String token) {
        logger.debug("登录用户的 Authorization " + token); // 记录日志，显示获取的token信息
        if (token == null) {
            return null; // 如果token为空，直接返回null
        }
        Jedis jedis = userJedis.getJedis(); // 从Redis连接池中获取一个Jedis连接
        String businessJedis = jedis.get(token); // 使用Jedis获取与token关联的用户信息
        jedis.close(); // 关闭Jedis连接
        return JSONObject.parseObject(businessJedis, UserEntity.class); // 将获取的JSON字符串转换为UserEntity对象并返回
    }

    public UserEntity getCurrentUser() {
        String username = authenticationFacade.getAuthentication().getName();
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
