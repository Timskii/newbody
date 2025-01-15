package kz.coach.bot.service;


import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.User;
import kz.coach.bot.mapping.UserMapper;
import kz.coach.bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDTO getUser(String username){
        return mapper.toDto(repository.findByUsername(username).orElse(null));
    }

    public UserDTO getUser(Long chatId){
        return mapper.toDto(repository.findByChatId(chatId).orElse(null));
    }

    public User getUserEntity(Long chatId){
        return repository.findByChatId(chatId).orElse(null);
    }

    public String addUser(UserDTO userDTO) {
        repository.save(mapper.toDomain(userDTO));
        return "ok";
    }

    public void addUserFromChat(Chat chat) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(chat.getUserName());
        userDTO.setChatId(chat.getId());
        userDTO.setStatus(Status.CREATED);
        userDTO.setFirstName(chat.getFirstName());
        userDTO.setLastName(chat.getLastName());
        repository.save(mapper.toDomain(userDTO));
    }

    public List<UserDTO> getAllUser() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public void setStatus(Status status, UserDTO userDTO) {
        User user;
        Optional<User> userOptional = repository.findByChatId(userDTO.getChatId());
        if (userOptional.isPresent()){
            user = userOptional.get();
            user.setStatus(status);
            repository.save(user);
        }
    }

    public Status getUserStatus(Long chatId) {
        return repository.findByChatId(chatId).orElse(new User()).getStatus();
    }

    public List<UserDTO> getAllUserAfterDate(OffsetDateTime date) {
        return repository.findAllByCreatedAtAfter(date)
                .stream().map(mapper::toDto).toList();
    }

    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public List<User> getAllActive() {
        return repository.findByStatus(Status.ACTIVE);
    }
}
