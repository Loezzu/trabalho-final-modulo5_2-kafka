package com.tindev.tindevapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tindev.tindevapi.dto.like.LikeDTO;
import com.tindev.tindevapi.dto.log.LogDTO;
import com.tindev.tindevapi.entities.LikeEntity;
import com.tindev.tindevapi.entities.UserEntity;
import com.tindev.tindevapi.enums.TipoLog;
import com.tindev.tindevapi.repository.exceptions.RegraDeNegocioException;
import com.tindev.tindevapi.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ObjectMapper objectMapper;
    private final MatchService matchService;
    private final UserService userService;
    private final LogService logService;

    public List<LikeDTO> listAllLikes() throws JsonProcessingException {
//        logService.logPost(TipoLog.LIKE,"Like list found");
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("Like list found");
        logService.logPost(logDTO);

        return likeRepository.findAll()
                .stream()
                .map(like ->  objectMapper.convertValue(like, LikeDTO.class))
                .collect(Collectors.toList());
    }

    public List<LikeDTO> listAllLikesByUser(Integer id) throws RegraDeNegocioException, JsonProcessingException {
        userService.getUserById(id);
//        logService.logPost(TipoLog.LIKE,"Found likes of the user with ID: "+id);
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("Found likes of the user with ID: "+id);
        logService.logPost(logDTO);

        return likeRepository.findAllByUserId(id)
                .stream()
                .map(like -> objectMapper.convertValue(like, LikeDTO.class))
                .collect(Collectors.toList());
    }

    public LikeDTO giveLike(Integer userId, Integer likedUserId) throws Exception {
        if (likeRepository.findByUserIdAndLikedUserId(userId, likedUserId) != null) {
            throw new RegraDeNegocioException("like already exists");

        }
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setUserId(userId);
        likeEntity.setUsernameUser(userService.getUserById(userId).getUsername());
        likeEntity.setLikedUserId(likedUserId);
        likeEntity.setUsernameLikedUser(userService.getUserById(likedUserId).getUsername());
        likeEntity.setUserEntity(objectMapper.convertValue(userService.getUserById(userId), UserEntity.class));
        likeEntity.setUserEntityLiked(objectMapper.convertValue(userService.getUserById(likedUserId), UserEntity.class));
        likeRepository.save(likeEntity);
        if (likeRepository.findByUserIdAndLikedUserId(userId, likedUserId) != null &&
                likeRepository.findByLikedUserIdAndUserId(userId, likedUserId) != null) {
            matchService.addMatch(userId, likedUserId);
        }
//        logService.logPost(TipoLog.LIKE,"User with ID: " + userId +" liked user with ID: "+likedUserId);
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("User with ID: " + userId +" liked user with ID: "+likedUserId);
        logService.logPost(logDTO);

        return objectMapper.convertValue(likeEntity, LikeDTO.class);
    }

    public void deleteLike(Integer id) throws RegraDeNegocioException, JsonProcessingException {
        likeRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("ID not found"));
//        logService.logPost(TipoLog.LIKE,"Like with ID: "+id+" deleted");
        LogDTO logDTO = new LogDTO();
        logDTO.setDescricao("Like with ID: "+id+" deleted");
        logDTO.setTipoLog(TipoLog.LIKE);
        logService.logPost(logDTO);

        likeRepository.deleteById(id);
    }

    public void deleteLikeByUserId(Integer id) throws RegraDeNegocioException, JsonProcessingException {
        userService.getUserById(id);
//        logService.logPost(TipoLog.LIKE,"Delete likes of the user with ID: "+id);
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("Delete likes of the user with ID: "+id);
        logService.logPost(logDTO);

        likeRepository.deleteAll(likeRepository.findAllByUserId(id));
    }

    public void deleteLikesByLogedUser() throws RegraDeNegocioException, JsonProcessingException {
//        logService.logPost(TipoLog.LIKE,"Likes of the loged user deleted");
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("Likes of the loged user deleted");
        logService.logPost(logDTO);

        deleteLikeByUserId(userService.getIdUserLoged());
    }

    public LikeDTO giveLikeByLogedUser(Integer likedUserId) throws Exception {
//        logService.logPost(TipoLog.LIKE,"Loged user gived like in the user with ID: " + likedUserId);
        LogDTO logDTO = new LogDTO();
        logDTO.setTipoLog(TipoLog.LIKE);
        logDTO.setDescricao("Loged user gived like in the user with ID: " + likedUserId);
        logService.logPost(logDTO);

        return giveLike(userService.getIdUserLoged(), likedUserId);
    }
}



