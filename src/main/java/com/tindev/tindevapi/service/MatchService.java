package com.tindev.tindevapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.dto.match.MatchDTO;
import com.tindev.tindevapi.entities.MatchEntity;
import com.tindev.tindevapi.entities.UserEntity;
import com.tindev.tindevapi.enums.TipoLog;
import com.tindev.tindevapi.repository.exceptions.RegraDeNegocioException;
import com.tindev.tindevapi.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public List<MatchDTO> list() throws JsonProcessingException {
//        logService.logPost(TipoLog.MATCH,"Match list found");
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.MATCH);
        logDTO.setDescricao("Match list found");
        logService.logPost(logDTO);

        return matchRepository.findAll()
                .stream()
                .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                .collect(Collectors.toList());
    }

    public List<MatchDTO> listByUserId(Integer userid) throws RegraDeNegocioException, JsonProcessingException {
        userService.getUserById(userid);
//        logService.logPost(TipoLog.MATCH,"Matches with user ID: " + userid);
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.MATCH);
        logDTO.setDescricao("Matches with user ID: " + userid);
        logService.logPost(logDTO);

        return matchRepository.findByMatchedUserFirstOrAndMatchedUserSecond(userid)
                .stream()
                .map(match -> objectMapper.convertValue(match, MatchDTO.class))
                .collect(Collectors.toList());
    }

    public MatchDTO addMatch(Integer userid1, Integer userid2) throws Exception {
        MatchEntity match = new MatchEntity();
        if (matchRepository.findByMatchedUserFirstAndMatchedUserSecond(userid1, userid2) != null || matchRepository.findByMatchedUserFirstAndMatchedUserSecond(userid2, userid1) != null) {
            throw new RegraDeNegocioException("Match already exists");
        } else if(userid1.equals(userid2)) {
            throw new RegraDeNegocioException("You can't match with yourself");
        }
        if(userService.getUserById(userid1).getProgLangs().equals(userService.getUserById(userid2).getProgLangs())){
            match.setMatchedUserFirst(userid1);
            match.setNameFirst(userService.getUserById(userid1).getUsername());
            match.setMatchedUserSecond(userid2);
            match.setNameSecond(userService.getUserById(userid2).getUsername());
            match.setUserEntityFirst(objectMapper.convertValue(userService.getUserById(userid1), UserEntity.class));
            match.setUserEntitySecond(objectMapper.convertValue(userService.getUserById(userid2), UserEntity.class));
//            logService.logPost(TipoLog.MATCH, "Match between user with ID " + userid1 + " and " + userid2);
            LogDTO logDTO = new LogDTO();
            logDTO.setTipoLog(TipoLog.MATCH);
            logDTO.setDescricao("Match between user with ID " + userid1 + " and " + userid2);
            logService.logPost(logDTO);

            return objectMapper.convertValue(matchRepository.save(match), MatchDTO.class);
        } else {
            throw new RegraDeNegocioException("Didn't match this time!");
        }
    }

    public void deleteMatch(Integer matchId) throws Exception {
        matchRepository.findById(matchId).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
//        logService.logPost(TipoLog.MATCH,"Match with ID: " +matchId + " deleted!");
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.MATCH);
        logDTO.setDescricao("Match with ID: " +matchId + " deleted!");
        logService.logPost(logDTO);

        matchRepository.deleteById(matchId);
    }

}
