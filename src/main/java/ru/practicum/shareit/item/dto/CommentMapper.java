package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentDto commentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthorName(comment.getText());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }

    public static Comment commentDtoRequestToComment(CommentDtoRequest commentDtoRequest, Item item, User user) {
        Comment comment = new Comment();
        comment.setText(commentDtoRequest.getText());
        comment.setCreated(LocalDateTime.now());
        comment.setItem(item);
        comment.setAuthor(user);
        return comment;
    }
}
