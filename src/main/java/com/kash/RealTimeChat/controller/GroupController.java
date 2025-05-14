package com.kash.RealTimeChat.controller;

import com.kash.RealTimeChat.model.Group;
// import com.kash.RealTimeChat.model.User;
import com.kash.RealTimeChat.repository.GroupRepository;
import com.kash.RealTimeChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "https://cwave.netlify.com")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public Group createGroup(@RequestBody Group group) {
        System.out.println(group);
        return groupRepository.save(group);
    }
    
    @GetMapping("/all")
    public List<Group> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups;
    }
}

class GroupCreationRequest {
    private String name;
    private List<String> memberUsernames;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getMemberUsernames() { return memberUsernames; }
    public void setMemberUsernames(List<String> memberUsernames) { this.memberUsernames = memberUsernames; }
}