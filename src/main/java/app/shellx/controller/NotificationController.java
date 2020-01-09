package app.shellx.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.shellx.model.Notification;
import app.shellx.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/get/all/{id}")
	public Set<Notification> getNotifications(@PathVariable long id) {
		return this.notificationService.getAll(id);
	}
	
	@PostMapping("/delete")
	public boolean deleteById(@RequestBody long idNotif) {
		return this.notificationService.deleteById(idNotif);
	}
}
