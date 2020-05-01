package com.example.swagger.web.rest;

import com.example.swagger.dto.UserDTO;
import com.example.swagger.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserResource
 *
 * @author star
 */
@RestController
@RequestMapping("/api")
@Api(tags = "用户资源")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ApiOperation(value = "获取用户列表", notes = "获取用户的列表信息")
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<UserDTO> userDTOS = userService.listUsers();
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据指定id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = "Integer", required = true)
    public ResponseEntity<UserDTO> getUser(@PathVariable(value = "id") Integer id) {
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/users")
    @ApiOperation(value = "保存用户", notes = "保存用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号",  required = true),
            @ApiImplicitParam(name = "userName", value = "用户姓名",required = true),
            @ApiImplicitParam(name = "age", value = "用户年龄",  required = true),
            @ApiImplicitParam(name = "email", value = "用户邮箱", required = true)
    })
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "删除用户", notes = "根据指定id删除用户")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
