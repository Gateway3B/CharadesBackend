package com.g3productions.charades;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://remote-charades-backend.herokuapp.com")
@RestController
public class Controller {

    private HashMap<String, Session> sessions = new HashMap<String, Session>();

    @GetMapping(value = "/api/makesession/{username}")
    public Session makeSession(@PathVariable(value = "username") String username) {
        Session ses = new Session();
        ses.addUser(new User(username, true));
        sessions.put(ses.getSessionId(), ses);
        return ses;
    }

    @GetMapping(value = "/api/{sessionId}")
    public Session getSession(@PathVariable(value = "sessionId") String sessionId) {
        return sessions.get(sessionId);
    }

    @GetMapping(value = "/api/joinsession/{sessionId}/{username}")
    public Session joinSession(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username) {
        Session ses = sessions.get(sessionId);
        ses.addUser(new User(username, false));
        return ses;
    }

    @GetMapping(value = "/api/changeteam/{sessionId}/{username}")
    public Session changeTeam(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username) {
        Session ses = sessions.get(sessionId);
        ses.changeTeam(ses.getUser(username));
        return ses;
    }

    @GetMapping(value = "/api/randomteams/{sessionId}/{username}")
    public Session randomTeams(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username) {
        Session ses = sessions.get(sessionId);
        ses.randomTeams(ses.getUser(username));
        return ses;
    }

    @GetMapping(value = "/api/startgame/{sessionId}/{username}")
    public Session startGame(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username) {
        Session ses = sessions.get(sessionId);
        ses.startGame(ses.getUser(username));
        return ses;
    }

    @GetMapping(value = "/api/addword/{sessionId}/{username}/{word}")
    public Session addWord(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username, @PathVariable(value = "word") String word) {
        Session ses = sessions.get(sessionId);
        ses.addWord(ses.getUser(username), word);
        return ses;
    }

    @GetMapping(value = "/api/removeword/{sessionId}/{username}/{word}")
    public Session removeWord(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username, @PathVariable(value = "word") String word) {
        Session ses = sessions.get(sessionId);
        ses.removeWord(ses.getUser(username), word);
        return ses;
    }

    @GetMapping(value = "/api/ready/{sessionId}/{username}")
    public Session ready(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "username") String username) {
        Session ses = sessions.get(sessionId);
        ses.ready(ses.getUser(username));
        return ses;
    }

    @GetMapping(value = "/api/nextword/{sessionId}/{time}")
    public Session nextWord(@PathVariable(value = "sessionId") String sessionId, @PathVariable(value = "time") int time) {
        Session ses = sessions.get(sessionId);
        ses.nextWord(time);
        return ses;
    }
}