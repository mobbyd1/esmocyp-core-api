package esmocyp.core.api.controller;

import esmocyp.core.api.dto.ReasonerDTO;
import esmocyp.core.api.dto.StreamDTO;
import esmocyp.core.api.model.User;
import esmocyp.core.api.service.ReasoningService;
import esmocyp.core.api.service.UserService;
import esmocyp.core.api.service.exception.ReasoningServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api/reasoner")
public class ReasoningController {

    @Autowired
    UserService userService;

    @Autowired
    ReasoningService reasoningService;

    @PostMapping(value = "create")
    public ResponseEntity createReasoner(@RequestBody ReasonerDTO reasonerDTO, Principal principal) {
        User user = getUser(principal);

        try {

            reasoningService.create(user, reasonerDTO);

        } catch ( ReasoningServiceException e ) {
           return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity( HttpStatus.OK );
    }

    @PutMapping(value = "update")
    public ResponseEntity updateReasoner(@RequestBody ReasonerDTO reasonerDTO, Principal principal) {
        User user = getUser(principal);

        try {

            reasoningService.update(user, reasonerDTO);

        } catch ( ReasoningServiceException e ) {
            return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );
        }

        return new ResponseEntity( HttpStatus.OK );
    }

    @PostMapping(value = "destroy")
    public void destroyReasoner(Principal principal) {
        User user = getUser(principal);
        reasoningService.destroy(user);
    }

    @PostMapping(value = "stream")
    public void stream(@RequestBody StreamDTO streamDTO, Principal principal) {
        User user = getUser(principal);
        reasoningService.insertTriple(user, streamDTO);
    }

    private User getUser( Principal principal ) {
        String name = principal.getName();
        return userService.findByUserName( name );
    }
}
