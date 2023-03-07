package com.example.player.service; 
import javax.validation.OverridesAttribute;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;

import org.springframework.http.HttpStatus; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; 
import org.springframework.stereotype.Service;


import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
public class PlayerH2Service{  

    @Autowired 
    private JdbcTemplate db;

     
    public ArrayList<Player> getPlayers(){

        List<Player> playersList = db.query("select * from team",new PlayerRowMapper());
        ArrayList<Player> players = new ArrayList<>(playersList);
        return players;
        
    }
    
    public Player getPlayerById(int playerId){
        try{
            Player player =db.queryForObject("select * from team where playerId=?",new PlayerRowMapper(), playerId);
        return player;
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        
    }

    
    public Player addPlayer(Player player){
        db.update("insert into team(playerName,jerseyNumber,role) values(?,?,?)",player.getPlayerName(),
        player.getJerseyNumber(),player.getRole());

        Player addedBook = db.queryForObject("select * from team where playerName=? and jerseyNumber=?", new PlayerRowMapper(),player.getPlayerName(),player.getJerseyNumber() );
        return addedBook;
    }


     

    public Player updatePlayer(int playerId,Player player){
        if(player.getPlayerName() != null){
            db.update("update team set playerName=? where playerId=? ",player.getJerseyNumber(),playerId);
        }

        if (player.getJerseyNumber() != 0){
            db.update("update team set playerName=? where playerId=? ",player.getJerseyNumber(),playerId);
           
        }

         if (player.getRole() != null){
            db.update("update team set role=? where playerId=?",player.getRole(),playerId);
        }

        return getPlayerById(playerId);

    } 

     
    public void deletePlayer(int playerId){
        db.update("delete from team where playerId=?",playerId);
    }

} 
































