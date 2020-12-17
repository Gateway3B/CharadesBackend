package com.g3productions.charades;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

public class Session {

    private LinkedHashMap<String, User> usersTeamOne;

    private LinkedHashMap<String, User> usersTeamTwo;

    private ArrayList<User> usersOne;

    private ArrayList<User> usersTwo;

    private int userIndexOne;

    private int userIndexTwo;

    private String sessionId;

    private boolean started;

    private boolean readyOne;

    private boolean readyTwo;

    private boolean scoreboard;

    private ArrayList<String> teamOneWords;

    private ArrayList<String> teamTwoWords;

    private ArrayList<Word> wordTimeOne;

    private ArrayList<Word> wordTimeTwo;

    private String currentWord;

    private String currentUser;

    private int currentIndexOne;

    private int currentIndexTwo;

    private boolean currentTeam;

    Session() {
        sessionId = generateId(3);
        started = false;
        teamOneWords = new ArrayList<String>();
        teamTwoWords = new ArrayList<String>();
        currentTeam = true;
        usersTeamOne = new LinkedHashMap<String, User>();
        usersTeamTwo = new LinkedHashMap<String, User>();
        readyOne = false;
        readyTwo = false;
        scoreboard = false;
        userIndexOne = 0;
        userIndexTwo = 0;
        wordTimeOne = new ArrayList<Word>();
        wordTimeTwo = new ArrayList<Word>();
    }

    private String generateId(int targetStringLength) {
  
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void addUser(User user) {
        if(!usersTeamOne.containsKey(user.getUsername()) && !usersTeamTwo.containsKey(user.getUsername())) {
            if(user.getTeam()) {
                usersTeamOne.put(user.getUsername(), user);
            } else {
                usersTeamTwo.put(user.getUsername(), user);
            }
        }
    }

    public void changeTeam(User user) {
        if(user.getTeam()) {
            usersTeamOne.remove(user.getUsername());
            user.setTeam(!user.getTeam());
            usersTeamTwo.put(user.getUsername(), user);
        } else {
            usersTeamTwo.remove(user.getUsername());
            user.setTeam(!user.getTeam());
            usersTeamOne.put(user.getUsername(), user);
        }
    }

    public User getUser(String username) {
        User user;
        if((user = usersTeamOne.get(username)) != null) {
            return user;
        }
        return usersTeamTwo.get(username);
    }

    public void addWord(User user, String word) {
        if(user.getTeam()) {
            teamOneWords.add(word);
        } else {
            teamTwoWords.add(word);
        }
    }

    public void removeWord(User user, String word) {
        if(user.getTeam()) {
            if(teamOneWords.contains(word))
                teamOneWords.remove(word);
        } else {
            if(teamTwoWords.contains(word))
                teamTwoWords.remove(word);
        }
    }

    public void randomTeams(User userin) {
        if(userin.getOwner()) {
            ArrayList<User> users = new ArrayList<User>(usersTeamOne.values());
            users.addAll(usersTeamTwo.values());
            usersTeamOne = new LinkedHashMap<String, User>();
            usersTeamTwo = new LinkedHashMap<String, User>();
            users.forEach((user) -> {
                if(new Random().nextBoolean() && usersTeamOne.size() < users.size()/2 || usersTeamTwo.size() >= users.size()/2) {
                    usersTeamOne.put(user.getUsername(), user);
                } else {
                    usersTeamTwo.put(user.getUsername(), user);
                }
            });
        }
    }

    public void startGame(User user) {
        if(user.getOwner())
            this.started = true;
        usersOne = new ArrayList<User>(usersTeamOne.values());
        usersTwo = new ArrayList<User>(usersTeamTwo.values());
    }

    public void ready(User user) {
        if(user.getTeam()) {
            readyOne = true;
        } else {
            readyTwo = true;
        }

        if(readyOne && readyTwo) {
            if(teamOneWords.size() == 1) {
                currentIndexOne = 0;
            } else {
                currentIndexOne = new Random().nextInt(teamOneWords.size() - 1);
            }
            currentWord = teamOneWords.get(currentIndexOne);
            currentUser = usersTwo.get(userIndexTwo).getUsername();
        }
    }

    public void nextWord(int time) {
        
        if(currentTeam) {
            wordTimeOne.add(new Word(teamOneWords.get(currentIndexOne), time, usersTwo.get(userIndexTwo)));
            teamOneWords.remove(currentIndexOne);

            if(teamTwoWords.size() == 0) {
                if(teamOneWords.size() == 0) {
                    scoreboard = true;
                } else {
                    teamTwoWordsOne();
                }
            } else {
                currentTeam = false;
                teamOneWordsTwo();
            }
        } else {
            wordTimeTwo.add(new Word(teamTwoWords.get(currentIndexTwo), time, usersOne.get(userIndexOne)));
            teamTwoWords.remove(currentIndexTwo);

            
            if(teamOneWords.size() == 0) {
                if(teamTwoWords.size() == 0) {
                    scoreboard = true;
                } else {
                    teamOneWordsTwo();
                }
            } else {
                currentTeam = true;
                teamTwoWordsOne();
            }            
        }

    }

    private void teamOneWordsTwo() {
        if(userIndexTwo < usersTwo.size()-1) {
            userIndexTwo++;
        } else {
            userIndexTwo = 0;
        }
        currentUser = usersOne.get(userIndexOne).getUsername();

        if(teamTwoWords.size() == 1) {
            currentIndexTwo = 0;
        } else {
            currentIndexTwo = new Random().nextInt(teamTwoWords.size() - 1);
        }
        currentWord = teamTwoWords.get(currentIndexTwo);
    }

    private void teamTwoWordsOne() {
        if(userIndexOne < usersOne.size() - 1) {
            userIndexOne++;
        } else {
            userIndexOne = 0;
        }
        currentUser = usersTwo.get(userIndexTwo).getUsername();

        if(teamOneWords.size() == 1) {
            currentIndexOne = 0;
        } else {
            currentIndexOne = new Random().nextInt(teamOneWords.size() - 1);
        }
        currentWord = teamOneWords.get(currentIndexOne);
    }

    public ArrayList<Word> getWordTimeTwo() {
        return wordTimeTwo;
    }

    public ArrayList<Word> getWordTimeOne() {
        return wordTimeOne;
    }

    public boolean getScoreboard() {
        return scoreboard;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LinkedHashMap<String, User> getUsersTeamOne() {
        return usersTeamOne;
    }

    public LinkedHashMap<String, User> getUsersTeamTwo() {
        return usersTeamTwo;
    }

    public boolean getStarted() {
        return started;
    }

    public boolean getReadyOne() {
        return readyOne;
    }

    public boolean getReadyTwo() {
        return readyTwo;
    }

    public boolean getCurrentTeam() {
        return currentTeam;
    }

    public ArrayList<String> getTeamOneWords() {
        return teamOneWords;
    }

    public ArrayList<String> getTeamTwoWords() {
        return teamTwoWords;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public int getCurrentIndexOne() {
        return currentIndexOne;
    }

    public int getCurrentIndexTwo() {
        return currentIndexTwo;
    }
}