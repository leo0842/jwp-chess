package chess.service;

import chess.dao.UserDAO;
import chess.dto.user.JoinUserDTO;
import chess.dto.user.UserDTO;
import chess.dto.user.UsersDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class UserService {
    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UsersDTO usersParticipatedInGame(final String roomId) {
        return userDAO.findUsersByRoomId(roomId);
    }

    public int userIdByNickname(final String nickname) {
        return userDAO.findUserIdByNickname(nickname);
    }

    public UsersDTO participatedUsers(String id) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setBlackUser(blackUserParticipatedInGame(id));
        usersDTO.setWhiteUser(whiteUserParticipatedInGame(id));
        return usersDTO;
    }

    public String blackUserParticipatedInGame(final String roomId) {
        return userDAO.findBlackUserByRoomId(roomId);
    }

    public String whiteUserParticipatedInGame(final String roomId) {
        return userDAO.findWhiteUserByRoomId(roomId);
    }

    public int registerUser(final JoinUserDTO joinUserDTO) {
        try {
            validatesDuplicatedUser(joinUserDTO);
            return userDAO.save(joinUserDTO);
        } catch (IllegalStateException e) {
            return login(joinUserDTO);
        }
    }

    private void validatesDuplicatedUser(final JoinUserDTO joinUserDTO) {
        userDAO.findByNickname(joinUserDTO.getNickname())
                .ifPresent(user -> {
                    throw new IllegalStateException("중복된 이름입니다.");
                });
    }

    private int login(final JoinUserDTO user) {
        int userId = userDAO.findIdByNicknameAndPassword(user.getNickname(), user.getPassword());
        if (userId == 0) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        return userId;
    }

    public boolean checkPassword(final String blackUserId, final String password) {
        Optional<UserDTO> user = userDAO.findPlayerByIdAndPassword(blackUserId, password);
        return !Optional.empty().equals(user);
    }
}
