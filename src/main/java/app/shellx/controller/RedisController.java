package app.shellx.controller;

import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.model.User;
import app.shellx.security.RedisUtil;

@RestController
@RequestMapping("/redis")
public class RedisController {

	@GetMapping("/get/all")
	public Set<String> getMembersByKey() {
		if (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == 1)
			return RedisUtil.INSTANCE.smembers("validjwt");
		else
			return null;
	}
}
